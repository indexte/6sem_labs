package servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import service.CourseService;
import util.JsonConverter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/courses")

public class CourseServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(LoginServlet.class);
    private final CourseService service = new CourseService();

    public CourseServlet(){
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Executing room controller");
        try {
            JsonConverter.makeJsonAnswer(service.findAllCourses(), resp);
        } catch (Exception e) {
            logger.error("Something went wrong in room controller");
            resp.sendError(400, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Executing reservation post controller");
        try {
            JSONObject jsonObject = JsonConverter.jsonBodyFromRequest(req, resp);
            Long courseId = Long.parseLong(jsonObject.get("courseId").toString());
            Long userId = Long.parseLong(jsonObject.get("userId").toString());
            service.save(courseId,userId);
        } catch (Exception e) {
            logger.error("Can`t create reservation: {}", e.getMessage());
            resp.sendError(400, "Something went wrong on server");
        }
    }
}
