package com.project.stone.user.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "password")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Password {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    
    @Column(name = "digest")
    private String digest;
    
    @Column(name = "salt")
    private String salt;

}