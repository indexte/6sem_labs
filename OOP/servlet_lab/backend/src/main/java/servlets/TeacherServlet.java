package servlets;

import entity.AdminDTO;
import entity.feedback.Feedback;
import entity.room.Course;
import entity.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import service.CourseService;
import service.FeedbackService;
import util.JsonConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@WebServlet(urlPatterns = "/admin")
public class TeacherServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(LoginServlet.class);
    private final FeedbackService service = new FeedbackService();
    private final CourseService courseService = new CourseService();

    public TeacherServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Executing reservation post controller");
        try {
            Set<AdminDTO> allCourses = courseService.findAllCoursesWithCourseId();

            JsonConverter.makeJsonAnswer(allCourses, resp);
        } catch (Exception e) {
            logger.error("Can`t update reservation:{}", e.getMessage());
            resp.sendError(400);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            JSONObject jsonObject = JsonConverter.jsonBodyFromRequest(req, resp);
            Long courseId = Long.parseLong(jsonObject.get("courseId").toString());
            Long userId = Long.parseLong(jsonObject.get("userId").toString());
            String description = jsonObject.get("description").toString();
            Integer score = Integer.parseInt(jsonObject.get("score").toString());

            service.createNewFeedback(
                    new Feedback()
                            .setCourseId(new Course().setId(courseId))
                            .setUserId(new User().setId(userId))
                            .setDescription(description)
                            .setScore(score)
            );
        } catch (Exception e) {
            logger.error("Can`t create feedback:{}", e.getMessage());
            resp.sendError(400);

        }
    }
}
