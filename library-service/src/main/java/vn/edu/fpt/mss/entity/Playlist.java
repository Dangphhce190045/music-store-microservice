package vn.edu.fpt.mss.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "UserPlaylist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PlaylistId")
    private Integer playlistId;

    @Column(name = "CustomerId", nullable = false)
    private Integer customerId;

    @Column(name = "Name", nullable = false, length = 120)
    private String name;

    @Column(name = "Description", length = 500)
    private String description;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PlaylistTrack> tracks = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
