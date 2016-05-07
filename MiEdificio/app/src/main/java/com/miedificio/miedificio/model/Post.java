package com.miedificio.miedificio.model;

import java.util.Date;

public class Post {

    private Long id;
    private String text;
    private BuildingUser buildingUser;
    private Integer commentsCount;
    private Building building;
    private Date lastCommentDate;
}
