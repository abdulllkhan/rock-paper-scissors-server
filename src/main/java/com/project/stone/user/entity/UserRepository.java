package com.project.stone.user.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(int userId);

    User findByEmail(String email);

    User findByUsername(String username);

}
