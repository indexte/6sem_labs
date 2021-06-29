package com.example.oop.hotel.services;

import com.example.oop.hotel.entities.Feedback;
import com.example.oop.hotel.entities.User;
import com.example.oop.hotel.repositories.FeedbackRepository;
import com.example.oop.hotel.repositories.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Reservation service class
 */
@Service
@Slf4j
public class FeedbackService {
    private final CourseRepository courseRepository;
    private final FeedbackRepository feedbackRepository;
    private final UserService userService;



    public FeedbackService(FeedbackRepository feedbackRepository, UserService userService, CourseRepository courseRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userService = userService;
        this.courseRepository = courseRepository;
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }


    public Feedback createNewFeedback(Feedback feedback)
            throws Exception {
        try {
            return feedbackRepository.save(feedback);

        } catch (Exception e) {
            log.error("User cannot create order");
            throw new Exception("User cannot create order");
        }
    }

    public List<Feedback> findAllFeedbacksByUser(Long id) throws Exception {
        try {
           return feedbackRepository.getAllByUserId(new User().setId(id));
        } catch (Exception e) {
            log.error("Feedback id must not be null, {}", e.getMessage());
            throw new Exception("Feedback id must not be null");
        }
    }


}
