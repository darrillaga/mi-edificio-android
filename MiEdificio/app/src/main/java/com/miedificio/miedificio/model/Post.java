package com.miedificio.miedificio.model;

import java.util.Date;

public class Post {

    private Long id;
    private String text;
    private BuildingUser buildingUser;
    private Integer commentsCount;
    private Long buildingId;
    private Date lastCommentDate;

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public BuildingUser getBuildingUser() {
        return buildingUser;
    }

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public Date getLastCommentDate() {
        return lastCommentDate;
    }
}
