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
import java.math.BigDecimal;
import java.time.LocalDate;
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
@Table(name = "Album")
public class Album extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AlbumId")
    private Integer albumId;

    @Column(name = "Title", nullable = false, length = 160)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ArtistId", nullable = false)
    private Artist artist;

    @Column(name = "CoverArtUrl", length = 500)
    private String coverArtUrl;

    @Builder.Default
    @Column(name = "AlbumType", nullable = false, length = 20)
    private String albumType = "ALBUM";

    @Column(name = "ReleaseDate")
    private LocalDate releaseDate;

    @Column(name = "RecordLabel", length = 150)
    private String recordLabel;

    @Column(name = "Price", precision = 10, scale = 2)
    private BigDecimal price;
}
