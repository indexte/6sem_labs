package service;

import dao.CourseDao;
import dataConnection.ConnectionPool;
import entity.AdminDTO;
import entity.room.Course;
import org.postgresql.copy.CopyOut;

import java.util.List;
import java.util.Set;

public class CourseService {

    private final ConnectionPool connectionPool = new ConnectionPool();

    public CourseService() {
    }

    public List<Course> findAllCourses() throws Exception {
        try (CourseDao courseDao = new CourseDao(connectionPool.getConnection())) {
            return courseDao.findAll();
        }
    }

    public boolean save(Long courseId, Long userId) throws Exception {
        try (CourseDao courseDao = new CourseDao(connectionPool.getConnection())) {
            return courseDao.subscribeOnCourse(courseId, userId);
        }
    }

    public Set<Course> findAllCoursesByUserId(Long userId) throws Exception {
        try (CourseDao courseDao = new CourseDao(connectionPool.getConnection())) {
            return courseDao.findCoursesByUserId(userId);
        }
    }

    public Set<AdminDTO> findAllCoursesWithCourseId() throws Exception {
        try (CourseDao courseDao = new CourseDao(connectionPool.getConnection())) {
            return courseDao.findAllCoursesWithCourseId();
        }
    }

}
