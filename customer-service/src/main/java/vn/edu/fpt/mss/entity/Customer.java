package vn.edu.fpt.mss.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import vn.edu.fpt.mss.common.entity.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Customer")
@SQLDelete(sql = "UPDATE Customer SET IsDeleted = 1 WHERE CustomerId = ?")
@SQLRestriction("IsDeleted = 0")
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerId")
    private Integer customerId;

    @Column(name = "FirstName", nullable = false, length = 40)
    private String firstName;

    @Column(name = "LastName", nullable = false, length = 20)
    private String lastName;

    @Column(name = "Company", length = 80)
    private String company;

    @Column(name = "Address", length = 70)
    private String address;

    @Column(name = "City", length = 40)
    private String city;

    @Column(name = "State", length = 40)
    private String state;

    @Column(name = "Country", length = 40)
    private String country;

    @Column(name = "PostalCode", length = 10)
    private String postalCode;

    @Column(name = "Phone", length = 24)
    private String phone;

    @Column(name = "Fax", length = 24)
    private String fax;

    @Column(name = "Email", nullable = false, length = 60)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SupportRepId")
    private Employee supportRep;

    @Column(name = "IdentityId", length = 100)
    private String identityId;

    @Builder.Default
    @Column(name = "Status", nullable = false, length = 20)
    private String status = "ACTIVE";

    @Builder.Default
    @Column(name = "Tier", nullable = false, length = 20)
    private String tier = "FREE";

    @Column(name = "SubscriptionExpiresAt")
    private LocalDateTime subscriptionExpiresAt;

    @Column(name = "AvatarUrl", length = 500)
    private String avatarUrl;

    @Column(name = "IsDeleted", nullable = false)
    private boolean deleted;
}
