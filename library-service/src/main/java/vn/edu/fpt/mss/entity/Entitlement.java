package vn.edu.fpt.mss.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Entitlement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entitlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EntitlementId")
    private Integer entitlementId;

    @Column(name = "CustomerId", nullable = false)
    private Integer customerId;

    @Column(name = "TrackId", nullable = false)
    private Integer trackId;

    @Column(name = "InvoiceId", nullable = false)
    private Integer invoiceId;

    @Column(name = "GrantedAt", nullable = false)
    private LocalDateTime grantedAt;

    @PrePersist
    public void prePersist() {
        this.grantedAt = LocalDateTime.now();
    }
}
