package com.example.oop.hotel.controller;


import com.example.oop.hotel.entities.Course;
import com.example.oop.hotel.entities.Feedback;
import com.example.oop.hotel.entities.User;
import com.example.oop.hotel.services.CourseService;
import com.example.oop.hotel.services.FeedbackService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
    private final FeedbackService feedbackService;
    private final CourseService courseService;

    public AdminController(FeedbackService feedbackService, CourseService courseService) {
        this.feedbackService = feedbackService;
        this.courseService = courseService;
    }


    @PostMapping()
    @PreAuthorize("hasAnyRole('admin')")
    public ResponseEntity updateReservation(@RequestBody ObjectNode objectNode) {
        try {
            Long courseId = Long.parseLong(objectNode.get("courseId").toString());
            Long userId = Long.parseLong(objectNode.get("userId").toString());
            String description = objectNode.get("description").toString();
            Integer score = Integer.parseInt(objectNode.get("score").toString());
            feedbackService.createNewFeedback(
                    new Feedback()
                            .setCourseId(new Course().setId(courseId))
                            .setUserId(new User().setId(userId))
                            .setDescription(description)
                            .setScore(score));
            return ResponseEntity.ok(200);
        } catch (Exception e) {
            log.error("{} Can`t update order", e.getMessage());
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    @GetMapping()
    @PreAuthorize("hasAnyRole('admin')")
    public ResponseEntity deleteOrder(@RequestParam Long orderId) {
        try {
            return ResponseEntity.ok(courseService.findAllCoursesWithCourseId());
        } catch (Exception e) {
            log.error("The order cannot be deleted: {}", e.getMessage());
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
