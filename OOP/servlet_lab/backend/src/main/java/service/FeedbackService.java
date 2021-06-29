package service;

import dao.FeedbackDAO;
import dataConnection.ConnectionPool;
import entity.feedback.Feedback;

import java.util.List;

public class FeedbackService {

    private final ConnectionPool connectionPool = new ConnectionPool();

    public FeedbackService() {
    }

    public Feedback createNewFeedback(Feedback feedback) throws Exception {
        try (FeedbackDAO feedbackDAO = new FeedbackDAO(connectionPool.getConnection())) {
            return feedbackDAO.create(feedback)
                    .orElseThrow(() -> new Exception("reservation cannot be created"));
        }
    }

    public List<Feedback> findAllFeedbacksByUser(Long userId) throws Exception{
        try (FeedbackDAO feedbackDAO = new FeedbackDAO(connectionPool.getConnection())){
            return feedbackDAO.findFeedbacksByUser(userId);
        }
    }
}
