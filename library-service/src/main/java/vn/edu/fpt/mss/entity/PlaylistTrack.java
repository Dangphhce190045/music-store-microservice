package vn.edu.fpt.mss.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "UserPlaylistTrack")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaylistTrack {

    @EmbeddedId
    private PlaylistTrackId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("playlistId")
    @JoinColumn(name = "PlaylistId")
    private Playlist playlist;

    @Column(name = "AddedAt", nullable = false)
    private LocalDateTime addedAt;

    @PrePersist
    public void prePersist() {
        this.addedAt = LocalDateTime.now();
    }
}
