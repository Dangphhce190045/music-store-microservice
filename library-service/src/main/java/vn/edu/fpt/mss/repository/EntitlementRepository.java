package vn.edu.fpt.mss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.mss.entity.Entitlement;
import java.util.List;
import java.util.Optional;

@Repository
public interface EntitlementRepository extends JpaRepository<Entitlement, Integer> {
    List<Entitlement> findByCustomerId(Integer customerId);
    Optional<Entitlement> findByCustomerIdAndTrackId(Integer customerId, Integer trackId);
    boolean existsByCustomerIdAndTrackId(Integer customerId, Integer trackId);
}
