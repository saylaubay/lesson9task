package uz.pdp.lesson9task1.payload;

import lombok.Data;
import uz.pdp.lesson9task1.entity.Attachment;
import uz.pdp.lesson9task1.entity.Icon;
import uz.pdp.lesson9task1.entity.User;
import uz.pdp.lesson9task1.entity.Workspace;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class SpaceDto {

    @NotNull
    private String name;

    @NotNull
    private String color;

    @NotNull
    private Long workspaceId;

    private UUID iconId;

    private UUID avatarId;

    @NotNull
    private boolean accessType;

}
