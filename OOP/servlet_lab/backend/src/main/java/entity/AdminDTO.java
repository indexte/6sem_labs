package entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AdminDTO {
    private Long id;

    private String name;

    private String description;
    private Long userId;
}
