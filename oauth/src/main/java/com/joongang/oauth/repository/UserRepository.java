package com.joongang.oauth.repository;

import com.joongang.oauth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByName(String username);

    boolean existsByName(String username);

}
