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
@Table(name = "Track")
@SQLDelete(sql = "UPDATE Track SET IsDeleted = 1 WHERE TrackId = ?")
@SQLRestriction("IsDeleted = 0")
public class Track extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TrackId")
    private Integer trackId;

    @Column(name = "Name", nullable = false, length = 200)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AlbumId")
    private Album album;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MediaTypeId", nullable = false)
    private MediaType mediaType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GenreId")
    private Genre genre;

    @Column(name = "Composer", length = 220)
    private String composer;

    @Column(name = "Milliseconds", nullable = false)
    private Integer milliseconds;

    @Column(name = "Bytes")
    private Integer bytes;

    @Column(name = "UnitPrice", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "PreviewUrl", length = 500)
    private String previewUrl;

    @Column(name = "StreamUrl", length = 500)
    private String streamUrl;

    @Column(name = "TrackNumber")
    private Integer trackNumber;

    @Column(name = "DiscNumber")
    private Integer discNumber;

    @Column(name = "IsrcCode", length = 15)
    private String isrcCode;

    @Column(name = "IsExplicit", nullable = false)
    private boolean explicit;

    @Column(name = "Lyrics")
    private String lyrics;

    @Column(name = "PlayCount", nullable = false)
    private long playCount;

    @Builder.Default
    @Column(name = "Status", nullable = false, length = 20)
    private String status = "PUBLISHED";

    @Column(name = "IsDeleted", nullable = false)
    private boolean deleted;
}
