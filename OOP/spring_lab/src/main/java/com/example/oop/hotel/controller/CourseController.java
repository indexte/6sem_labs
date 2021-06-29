package com.example.oop.hotel.controller;

import com.example.oop.hotel.entities.Course;
import com.example.oop.hotel.services.CourseService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/courses")
@CrossOrigin("http://localhost:4200")
@Slf4j
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping()
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<List<Course>> findAllRooms() {
        log.info("find All Rooms");
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity subscribeOnCourse(@RequestBody ObjectNode objectNode) {
        try {
            Long courseId = Long.parseLong(objectNode.get("courseId").asText());
            Long userId = Long.parseLong(objectNode.get("userId").asText());
            courseService.subscribeOnCourse(courseId, userId);
            return ResponseEntity.ok(200);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }


}
