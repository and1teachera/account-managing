package org.zlatenov.accountmanaging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zlatenov.accountmanaging.model.entity.User;

import java.util.Optional;

/**
 * @author Angel Zlatenov
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);
}
