package servlets;

import entity.feedback.Feedback;
import entity.room.Course;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.FeedbackService;
import util.JsonConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/feedback")
public class FeedbackServlet  extends HttpServlet {
    static final Logger logger = LogManager.getLogger(LoginServlet.class);
    private final FeedbackService service = new FeedbackService();

    public FeedbackServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Executing cabinet get controller");
        try {
            List<Feedback> feedbacks = service.findAllFeedbacksByUser(
                    Long.parseLong(req.getParameter("user")));
            JsonConverter.makeJsonAnswer(feedbacks, resp);

        } catch (Exception ex) {

            logger.info("Reservation can`t be founded: {}", ex.getMessage());
            resp.sendError(400);
        }
    }
    }

