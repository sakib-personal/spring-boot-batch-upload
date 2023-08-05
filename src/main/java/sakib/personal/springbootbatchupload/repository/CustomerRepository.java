package sakib.personal.springbootbatchupload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sakib.personal.springbootbatchupload.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
