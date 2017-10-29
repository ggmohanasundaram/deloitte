package org.com.deloitte.raml.impl.jpa;

import org.com.deloitte.raml.impl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByUsername(String username);
}
