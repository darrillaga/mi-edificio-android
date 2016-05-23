package com.miedificio.miedificio.networking.payload;

public class Post {

    private String text;
    private Long buildingUserId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getBuildingUserId() {
        return buildingUserId;
    }

    public void setBuildingUserId(Long buildingUserId) {
        this.buildingUserId = buildingUserId;
    }
}
