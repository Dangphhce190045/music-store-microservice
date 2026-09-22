# =========================================================
# Enterprise Multi-Service Dockerfile for Spring Boot 3 & Java 21
# Single shared Dockerfile parameterized by build arg: APP_NAME
# =========================================================

FROM eclipse-temurin:21-jre-alpine AS runtime

# Cài đặt curl cho healthcheck container và thiết lập múi giờ UTC/Asia:Ho_Chi_Minh
RUN apk add --no-cache curl tzdata && \
    cp /usr/share/zoneinfo/Asia/Ho_Chi_Minh /etc/localtime && \
    echo "Asia/Ho_Chi_Minh" > /etc/timezone

# Thiết lập user phi đặc quyền (non-root) để tăng cường bảo mật container chuẩn CIS/SOC2
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

WORKDIR /app

# Nhận tên module microservice làm tham số khi build (vd: catalog-service)
ARG APP_NAME
ARG JAR_FILE=${APP_NAME}/target/*.jar

COPY --chown=appuser:appgroup ${JAR_FILE} app.jar

# JVM options tối ưu tài nguyên cho container (cgroups v2, GC, Memory limit)
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+UseG1GC -Djava.security.egd=file:/dev/./urandom"

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]
