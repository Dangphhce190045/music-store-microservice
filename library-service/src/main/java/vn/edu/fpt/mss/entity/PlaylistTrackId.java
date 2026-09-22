package vn.edu.fpt.mss.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistTrackId implements Serializable {

    @Column(name = "PlaylistId")
    private Integer playlistId;

    @Column(name = "TrackId")
    private Integer trackId;
}
