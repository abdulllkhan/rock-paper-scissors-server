package com.project.stone.game.entity;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends CrudRepository<Room, Integer>{

    @Query("SELECT * FROM rooms WHERE id = :id")
    Room findById(@Param("id") int roomId);

    @Query("SELECT * FROM rooms WHERE session_code = :session_code")
    Room findBySessionCode(@Param("session_code") String sessionCode);

    
    
}
