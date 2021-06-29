package com.example.oop.hotel.repositories;

import com.example.oop.hotel.dto.AdminDTO;
import com.example.oop.hotel.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;


public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query(value = "INSERT INTO course_user (course_id, user_id) VALUES (?1,?2)", nativeQuery = true)
    void subscribeOnCourse(Long courseId, Long userId);

    @Query(value = "SELECT id,name,description FROM course_user left join course on course.id=course_id WHERE user_id=?1", nativeQuery = true)
    Set<Course> findCoursesByUserId(long userId);

    @Query(value = "SELECT id,name,description, user_id FROM course_user left join course on course.id=course_id ", nativeQuery = true)
    Set<AdminDTO> findAllCoursesWithCourseId();
}
