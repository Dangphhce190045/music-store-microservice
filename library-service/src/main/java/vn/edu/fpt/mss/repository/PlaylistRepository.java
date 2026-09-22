package vn.edu.fpt.mss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.mss.entity.Playlist;
import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {
    List<Playlist> findByCustomerId(Integer customerId);
}
