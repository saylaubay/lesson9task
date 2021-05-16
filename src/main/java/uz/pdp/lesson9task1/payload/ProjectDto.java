package uz.pdp.lesson9task1.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {

    private String name;

    private Long spaceId;

    private boolean accessType;

    private boolean archived;

    private String color;

}
