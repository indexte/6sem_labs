package dao;

import entity.feedback.Feedback;
import entity.room.Course;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class FeedbackDAO implements GenericDao<Feedback> {
    private final Logger logger = LogManager.getLogger(CourseDao.class);
    private final Connection connection;

    public FeedbackDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Feedback> create(Feedback entity) throws Exception {
        ResultSet set = null;
        try {
            PreparedStatement sql = connection.prepareStatement("INSERT INTO feedback (description, score , user_id , course_id) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            sql.setString(1, entity.getDescription());
            sql.setInt(2, entity.getScore());
            sql.setLong(3, entity.getUserId().getId());
            sql.setLong(4, entity.getCourseId().getId());
            sql.executeUpdate();
            set = sql.getGeneratedKeys();
            if (set.next()) {
                entity.setId(set.getLong("id"));
            }
            return Optional.of(entity);

        } catch (SQLException ex) {
            logger.warn("Reservation can`t be created: {}", ex.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Feedback findCoursesByUserId(long id) {
        return null;
    }

    @Override
    public List<Feedback> findAll() throws Exception {
        return null;
    }

    @Override
    public Optional<Feedback> update(Feedback entity) throws SQLException {
        return Optional.empty();
    }

    @Override
    public void close() throws Exception {

    }

    public List<Feedback> findFeedbacksByUser(Long id) throws SQLException {
        Set<Feedback> feedbackList = new HashSet<Feedback>();
        ResultSet set;
        try {
            PreparedStatement sql = connection.prepareStatement("SELECT * from feedback where user_id=?");
            sql.setLong(1, id);
            set = sql.executeQuery();
            while (set.next()) {
                Feedback feedback = new Feedback()
                        .setId(set.getLong("id"))
                        .setDescription(set.getString("description"))
                        .setScore(set.getInt("score"))
                        .setCourseId(new Course().setId(set.getLong("course_id")));
                feedbackList.add(feedback);
            }

        } catch (SQLException ex) {
            logger.warn("Reservations can`t be find by user: {}", ex.getMessage());
        }
        return new ArrayList<>(feedbackList);
    }
}
