package com.example.oop.hotel.repositories;

import com.example.oop.hotel.entities.Feedback;
import com.example.oop.hotel.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> getAllByUserId(User userId);


}
