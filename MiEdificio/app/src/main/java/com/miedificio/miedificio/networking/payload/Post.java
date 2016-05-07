package com.miedificio.miedificio.networking.payload;

public class Post {

    private String text;
    private Long buildingUser;
    private Long building;

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

    public Long getBuilding() {
        return building;
    }

    public void setBuilding(Long building) {
        this.building = building;
    }
}
