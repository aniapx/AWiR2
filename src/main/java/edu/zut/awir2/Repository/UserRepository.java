package edu.zut.awir2.Repository;

import edu.zut.awir2.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(long id);
    long deleteUserById(long id);
}
