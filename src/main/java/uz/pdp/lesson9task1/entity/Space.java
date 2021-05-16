package uz.pdp.lesson9task1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.lesson9task1.entity.template.AbsLongEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.swing.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Space extends AbsLongEntity {

    private String name;

    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    private Workspace workspace;

    private String initialLetter;

    @ManyToOne(fetch = FetchType.LAZY)
    private Icon icon;

    @ManyToOne(fetch = FetchType.LAZY)
    private Attachment avatar;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    private boolean accessType;

    public Space(String name, String color, Workspace workspace, Icon icon, Attachment avatar, User owner, boolean accessType) {
        this.name = name;
        this.color = color;
        this.workspace = workspace;
        this.icon = icon;
        this.avatar = avatar;
        this.owner = owner;
        this.accessType = accessType;
    }
}
