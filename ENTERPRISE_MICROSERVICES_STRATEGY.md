# 🏛️ CHIẾN LƯỢC KIẾN TRÚC MICROSERVICES CHUẨN DOANH NGHIỆP
## Dự án: Enterprise Digital Music Store (Chinook MSS)

---

## 🎯 1. TỔNG QUAN KIẾN TRÚC DOANH NGHIỆP (HIGH-LEVEL ARCHITECTURE)

Hệ thống **Chinook Music Store Microservices (MSS)** được thiết kế theo đúng các chuẩn mực kiến trúc doanh nghiệp cấp Tier-1:

```
                            +----------------------------------------+
                            |       Clients (Web / Mobile App)       |
                            +-------------------+--------------------+
                                                | HTTP / REST
                                                v
                            +----------------------------------------+
                            |     API Gateway (Spring Cloud 8080)     |
                            |   - Routing, Rate Limit, Circuit Breaker|
                            +---------+-------------------+----------+
                                      |                   |
            +-------------------------+                   +-------------------------+
            | Service Discovery                           | Service Discovery
            v                                             v
  +--------------------+                        +--------------------+
  |  Discovery Server  |                        |  Discovery Server  |
  |  (Eureka - 8761)   |                        |  (Eureka - 8761)   |
  +---------+----------+                        +---------+----------+
            ^                                             ^
            | (Heartbeat & Registry)                      | (Heartbeat & Registry)
    +-------+--------------------+----------------+-------+-------------------+
    |                            |                |                           |
+---v-------------+    +---------v-----+    +-----v----------+       +--------v--------+
| Catalog Service |    |Customer Serv. |    |Invoice Service |       | Library Service |
|   (Port 8081)   |    |  (Port 8082)  |    |  (Port 8083)   |       |   (Port 8085)   |
+--------+--------+    +-------+-------+    +-------+--------+       +--------+--------+
         |                     |                    |                         |
         v                     v                    v                         v
  [chinook_catalog]    [chinook_customer]   [chinook_invoice]         [chinook_library]
                                                    | (Saga / Feign)          ^
                                                    v                         |
                                            +-------+--------+                |
                                            |Payment Service |----------------+
                                            |  (Port 8084)   | (Grant Entitlement)
                                            +-------+--------+
                                                    |
                                                    v
                                            [chinook_payment]
```

---

## 💎 2. CÁC NGUYÊN TẮC CỐT LÕI (ENTERPRISE PRINCIPLES)

### 2.1. Database-Per-Service (Tuyệt đối không dùng Foreign Key giữa các DB)
- **Vấn đề Monolith truyền thống**: Bảng `InvoiceLine` liên kết khóa ngoại trực tiếp sang bảng `Track` và `Customer`. Khi tách microservice, việc giữ FK sẽ phá vỡ tính tự chủ của service.
- **Chuẩn Doanh nghiệp**: Mỗi service sở hữu database độc lập (`chinook_catalog`, `chinook_customer`, `chinook_invoice`, `chinook_payment`, `chinook_library`).
- **Ranh giới**: Không có bất kỳ kết nối chéo DB hoặc truy vấn JOIN liên server nào.

### 2.2. Data Snapshot & Projection Pattern (Chống Biến động Giá và Lịch sử)
- **Bài toán**: Nếu hôm nay khách hàng mua bài hát giá `$0.99`. Tuần sau nghệ sĩ tăng giá lên `$2.99`. Khi in lại hóa đơn cũ, nếu truy vấn sang `CatalogService` thì hóa đơn sẽ bị sai lệch tổng tiền!
- **Giải pháp Doanh nghiệp**:
  - `InvoiceService` áp dụng **Snapshot/Projection Pattern**: Lưu cứng bản sao thông tin tại thời điểm giao dịch (`track_name`, `unit_price`, `artist_name`, `customer_email`, `customer_name`) ngay trong bảng `InvoiceLine` và `Invoice`.
  - Hóa đơn hoàn toàn bất biến (Immutable), không bị phụ thuộc vào sự thay đổi sau này của Catalog hay Customer.

### 2.3. Shared Kernel (`common-lib`)
Module `common-lib` đóng vai trò chia sẻ quy chuẩn chung giữa các service mà không tạo liên kết logic nghiệp vụ:
- **`ApiResponse<T>`**: Chuẩn hóa cấu trúc JSON trả về thống nhất (`success`, `code`, `message`, `data`, `timestamp`).
- **`GlobalExceptionHandler`**: Bắt lỗi tập trung, mapping các exception nghiệp vụ thành HTTP Status Code tương ứng (404, 400, 409, 500).
- **`BaseEntity`**: Tự động đánh dấu `createdAt`, `updatedAt`, `isDeleted` (Soft Delete).

### 2.4. Service Registry & Dynamic Routing (Eureka + Gateway)
- Không hardcode IP/Port giữa các service.
- **Eureka Server** (`8761`): Đăng ký và theo dõi sức khỏe (Heartbeat) của các instance.
- **API Gateway** (`8080`): Đóng vai trò Reverse Proxy duy nhất, phân phối traffic qua `lb://<service-name>` kèm Circuit Breaker (Resilience4j) tránh lỗi dây chuyền (Cascading Failure).

### 2.5. Distributed Transaction (Saga Choreography / Orchestration)
Quy trình thanh toán và phân phối bản quyền số tuân theo luồng Saga:
1. **Khách hàng Checkout** ➡️ `InvoiceService` tạo hóa đơn trạng thái `PENDING`.
2. **Thanh toán** ➡️ Gọi sang `PaymentService` xử lý qua Cổng thanh toán (Stripe/VNPay/Mock).
3. **Cấp bản quyền** ➡️ Khi thanh toán `SUCCESS`, phát tín hiệu để `LibraryService` cấp quyền sở hữu số (`UserEntitlement`) cho khách hàng.
4. **Bù trừ (Compensating Transaction)**: Nếu thanh toán lỗi, `InvoiceService` đánh dấu `CANCELLED` / `FAILED`, hoàn tiền nếu cần.

---

## 📊 3. DANH MỤC DỊCH VỤ & THÔNG SỐ VẬN HÀNH

| Dịch vụ | Cổng | Cơ sở dữ liệu | Trách nhiệm chính |
| :--- | :---: | :--- | :--- |
| **`discovery-server`** | `8761` | Không | Quản lý Service Registry qua Netflix Eureka |
| **`api-gateway`** | `8080` | Không | Điểm đón request duy nhất, Circuit Breaker, Routing |
| **`catalog-service`** | `8081` | `chinook_catalog` | Quản lý Nghệ sĩ, Album, Bài hát, Thể loại, Media |
| **`customer-service`** | `8082` | `chinook_customer` | Quản lý Khách hàng, Hạng hội viên (Tier), Nhân viên |
| **`invoice-service`** | `8083` | `chinook_invoice` | Đơn hàng, Snapshot hóa đơn bất biến |
| **`payment-service`** | `8084` | `chinook_payment` | Giao dịch thanh toán, kết nối Payment Gateway |
| **`library-service`** | `8085` | `chinook_library` | Bản quyền nhạc số sở hữu (`Entitlements`), Playlists |

---

## 🛠️ 4. QUY TRÌNH KHỞI ĐỘNG CHUẨN

1. **Khởi động Database**:
   ```bash
   docker compose up -d sqlserver sqlserver-init
   ```
2. **Khởi động Discovery Server (Eureka)**:
   ```bash
   cd discovery-server
   mvn spring-boot:run
   # Dashboard quản lý Eureka: http://localhost:8761
   ```
3. **Khởi động API Gateway & Các Dịch vụ Doanh nghiệp**:
   - Khởi động lần lượt: `catalog-service`, `customer-service`, `payment-service`, `library-service`, `invoice-service`, `api-gateway`.
   - Hoặc chạy trọn gói qua Docker:
   ```bash
   docker compose --profile all up -d --build
   ```
