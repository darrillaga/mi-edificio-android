package com.miedificio.miedificio.networking.payload;

public class Comment {

    private String text;
    private Long buildingUser;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getBuildingUser() {
        return buildingUser;
    }

    public void setBuildingUser(Long buildingUser) {
        this.buildingUser = buildingUser;
    }
}
