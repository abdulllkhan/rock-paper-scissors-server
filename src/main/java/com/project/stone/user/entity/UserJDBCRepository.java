package com.project.stone.user.entity;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserJDBCRepository extends CrudRepository<User, Integer> {

    // not possible for non-null optional fields
    // @Query("SELECT * FROM users WHERE id = :id")
    // User findById(@Param("id") Integer id);

    @Query("SELECT * FROM users WHERE username = :username")
    User findByUsername(@Param("username") String username);

}