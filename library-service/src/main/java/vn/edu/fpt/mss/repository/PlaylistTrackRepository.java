package vn.edu.fpt.mss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.mss.entity.PlaylistTrack;
import vn.edu.fpt.mss.entity.PlaylistTrackId;
import java.util.List;

@Repository
public interface PlaylistTrackRepository extends JpaRepository<PlaylistTrack, PlaylistTrackId> {
    List<PlaylistTrack> findByIdPlaylistId(Integer playlistId);
}
