package uz.pdp.lesson9task1.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class WorkspaceDTO {
    @NotNull
    private String name;

    @NotNull
    private String color;

    private UUID avatarId;

}
