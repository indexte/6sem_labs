package com.example.oop.hotel.services;

import com.example.oop.hotel.dto.AdminDTO;
import com.example.oop.hotel.entities.Course;
import com.example.oop.hotel.repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * course service class
 */
@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public boolean subscribeOnCourse(Long courseId, Long userId) {
        try {
            courseRepository.subscribeOnCourse(courseId, userId);
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    public Set<Course> findAllCoursesByUserId(Long userId) throws Exception {
            return courseRepository.findCoursesByUserId(userId);

    }

    public Set<AdminDTO> findAllCoursesWithCourseId() throws Exception {
            return courseRepository.findAllCoursesWithCourseId();
    }
}
