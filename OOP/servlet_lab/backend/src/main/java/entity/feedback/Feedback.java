package entity.feedback;

import entity.room.Course;
import entity.user.User;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Feedback {
    private Long id;

    private Integer score;

    private String description;

    private User userId;

    private Course courseId;


    public Feedback() {
    }

    public Feedback(Long id, User userId, Course courseId, Integer score, String description) {
        this.id = id;
        this.description = description;
        this.score = score;
        this.userId = userId;
        this.courseId = courseId;

    }

}
