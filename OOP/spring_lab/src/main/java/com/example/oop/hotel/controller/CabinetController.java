package com.example.oop.hotel.controller;

import com.example.oop.hotel.entities.Course;
import com.example.oop.hotel.services.CourseService;
import com.example.oop.hotel.services.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@Controller
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")

public class CabinetController {

    private final CourseService courseService;

    public CabinetController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/cabinet")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity getAllReservsByUser(@RequestParam Long userId) {
        try {
            Set<Course> courses = courseService.findAllCoursesByUserId(userId);
            return ResponseEntity.ok(courses);

        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
