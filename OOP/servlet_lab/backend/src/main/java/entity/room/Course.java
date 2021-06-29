package entity.room;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Course {

    private Long id;

    private String name;

    private String description;


    public Course(Long id,  String description,  String name) {
        this.id = id;
        this.description = description;
        this.name = name;
    }

    public Course() {
    }

}
