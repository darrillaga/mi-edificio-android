package com.miedificio.miedificio.model;

public class BuildingUser {

    private Long id;
    private String name;
    private String apartment;
    private String roleDescription;
    private Boolean buildingCreator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Boolean getBuildingCreator() {
        return buildingCreator;
    }

    public void setBuildingCreator(Boolean buildingCreator) {
        this.buildingCreator = buildingCreator;
    }
}
