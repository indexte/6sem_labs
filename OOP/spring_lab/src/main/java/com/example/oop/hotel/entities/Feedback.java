package com.example.oop.hotel.entities;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Accessors(chain = true)
public class Feedback {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "score")
    private Integer score;

    @Basic
    @Column(name = "description")
    private String description;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User userId;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Course courseId;


}
