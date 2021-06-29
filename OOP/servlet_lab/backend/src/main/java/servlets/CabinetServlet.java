package servlets;

import entity.room.Course;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.CourseService;
import util.JsonConverter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@WebServlet(urlPatterns = "/cabinet")
public class CabinetServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(CabinetServlet.class);
    private final CourseService service = new CourseService();

    public CabinetServlet() {
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Executing cabinet get controller");
        try {
            Set<Course> courses = service.findAllCoursesByUserId(
                    Long.parseLong(req.getParameter("user")));
            JsonConverter.makeJsonAnswer(courses, resp);

        } catch (Exception ex) {

            logger.info("Reservation can`t be founded: {}", ex.getMessage());
            resp.sendError(400);
        }
    }
}
