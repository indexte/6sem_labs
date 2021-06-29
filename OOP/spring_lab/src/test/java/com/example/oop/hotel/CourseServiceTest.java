package com.example.oop.hotel;


import com.example.oop.hotel.entities.Course;
import com.example.oop.hotel.repositories.CourseRepository;
import com.example.oop.hotel.services.CourseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class CourseServiceTest {

    @MockBean
    private CourseRepository courseRepository;

    @Autowired
    private CourseService courseService;


    @Test
    public void getAllRooms() {
        List<Course> list = new ArrayList<>();
        when(courseRepository.findAll()).thenReturn(list);
        List<Course> dto = courseService.getAllCourses();
        Assertions.assertEquals(list, dto);
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    public void findRoomById() throws Exception {
        Course course = new Course();
        course.setId(1L);
        course.setDescription("lux");
        course.setName("Standart Luxe");

        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        Assertions.assertEquals(course, courseService.findAllCoursesByUserId(anyLong()));
        verify(courseRepository, times(1)).findById(anyLong());
    }



}
