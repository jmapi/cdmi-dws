package pw.cdmi.cse.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.cdmi.cse.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
