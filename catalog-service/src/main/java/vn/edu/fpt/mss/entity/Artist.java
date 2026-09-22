package vn.edu.fpt.mss.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.fpt.mss.common.entity.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Artist")
public class Artist extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ArtistId")
    private Integer artistId;

    @Column(name = "Name", length = 120)
    private String name;

    @Column(name = "ImageUrl", length = 500)
    private String imageUrl;

    @Column(name = "BannerUrl", length = 500)
    private String bannerUrl;

    @Column(name = "Bio")
    private String bio;

    @Column(name = "IsVerified", nullable = false)
    private boolean verified;

    @Column(name = "Country", length = 50)
    private String country;
}
