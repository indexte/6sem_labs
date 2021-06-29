package com.example.oop.hotel.entities;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Data
@Accessors(chain = true)
@Table(name = "users")
public class User{
    public enum Roles {
        ROLE_STUDENT,
        ROLE_TEACHER
    }
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "email", unique = true)
    private String email;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "password")
    private String password;

    @Basic
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Roles role;


}
