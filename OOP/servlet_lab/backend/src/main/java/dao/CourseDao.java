package dao;

import entity.AdminDTO;
import entity.room.Course;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class CourseDao implements GenericDao{
    private final Logger logger = LogManager.getLogger(CourseDao.class);
    private final Connection connection;

    public CourseDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional create(Object entity) throws Exception {
        return Optional.empty();
    }

    public Set<Course> findCoursesByUserId(long userId) {
        Set<Course> courses = new HashSet<>();
        try {
            PreparedStatement firstStatement = connection.prepareStatement("SELECT id,name,description FROM course_user left join course on course.id=course_id WHERE user_id=?");
            firstStatement.setLong(1, userId);
            ResultSet resultSet = firstStatement.executeQuery();

            while (resultSet.next()) {
                courses.add(new Course()
                        .setId(resultSet.getLong("id"))
                        .setName(resultSet.getString("name"))
                        .setDescription(resultSet.getString("description")));
            }
            return courses;
        } catch (SQLException e) {
            logger.error("Course can`t be found: {}", e.getMessage());
        }
        return courses;
    }


    public List<Course> findAll() throws Exception {
        ResultSet set;
        List<Course> courses = new ArrayList<>();
        try {
            PreparedStatement sql= connection.prepareStatement("SELECT* FROM course");
            set = sql.executeQuery();
            while (set.next()) {
                courses.add(
                        new Course()
                        .setId(set.getLong("id"))
                        .setName(set.getString("name"))
                        .setDescription(set.getString("description")));
            }
        } catch (SQLException ex) {
            logger.warn("Course can`t be found: {}", ex.getMessage());
        }
        return courses;
    }

    @Override
    public Optional update(Object entity) throws SQLException {
        return Optional.empty();
    }

    public boolean subscribeOnCourse(Long courseId, Long userId){
        ResultSet set = null;
        try {
            PreparedStatement sql= connection.prepareStatement("INSERT INTO course_user (course_id, user_id) VALUES (?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            sql.setLong(1, courseId);
            sql.setLong(2, userId);
            sql.executeUpdate();

            return true;

        } catch (SQLException ex) {
            logger.warn("Reservation can`t be created: {}", ex.getMessage());
        }
        return false;
    }

    public void close() throws Exception {
        connection.close();
    }

    public Set<AdminDTO> findAllCoursesWithCourseId() {
        Set<AdminDTO> courses = new HashSet<>();
        try {
            PreparedStatement firstStatement = connection.prepareStatement("SELECT id,name,description, user_id FROM course_user left join course on course.id=course_id ");

            ResultSet resultSet = firstStatement.executeQuery();

            while (resultSet.next()) {
                courses.add(new AdminDTO()
                        .setId(resultSet.getLong("id"))
                        .setName(resultSet.getString("name"))
                        .setDescription(resultSet.getString("description"))
                        .setUserId(resultSet.getLong("user_id")));
            }
            return courses;
        } catch (SQLException e) {
            logger.error("Course can`t be found: {}", e.getMessage());
        }
        return courses;
    }
}
