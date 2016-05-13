package com.miedificio.miedificio.networking;

import com.miedificio.miedificio.model.Building;
import com.miedificio.miedificio.model.BuildingUser;
import com.miedificio.miedificio.model.Comment;
import com.miedificio.miedificio.model.Post;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface MiEdificioServerClient {

    // MiEdificio

    // BuildingUsers

    // Get building users
    @GET("buildings/{buildingId}/buildingUsers")
    Observable<List<BuildingUser>> getBuildingsUsers(@Path("buildingId") long buildingId);

    // Create building user
    @POST("buildings/{buildingId}/buildingUsers")
    Observable<BuildingUser> createBuildingUser(@Path("buildingId") Long buildingId, @Body com.miedificio.miedificio.networking.payload.BuildingUser buildingUser);

    // Buildings

    // Get Buildings
    @GET("buildings")
    Observable<List<Building>> getBuildings();

    // Get Building
    @GET("buildings/{id}")
    Observable<Building> getBuilding(@Path("id") long buildingId);

    // Get Building
    @GET("buildings/by-code/{code}")
    Observable<Building> getBuildingByCode(@Path("code") String code);

    // Create Building
    @POST("buildings")
    Observable<Building> createBuilding(@Body com.miedificio.miedificio.networking.payload.Building building);

    // Update Building
    @PUT("buildings/{id}")
    Observable<Building> updateBuilding(@Path("id") long id, @Body com.miedificio.miedificio.networking.payload.Building building);

    // Delete Building
    @DELETE("buildings/{id}")
    Observable<Void> deleteBuilding(@Path("id") long id);

    // Comments

    // Get comments
    @GET("buildings/{buildingId}/posts/{postId}/comments")
    Observable<List<Comment>> getBuildingPostComments(@Path("buildingId") long buildingId, @Path("postId") long postId);

    // Create comment
    @POST("buildings/{buildingId}/posts/{postId}/comments")
    Observable<Comment> createBuildingPostComments(@Path("buildingId") long buildingId, @Path("postId") long postId, @Body com.miedificio.miedificio.networking.payload.Comment comment);

    // Posts

    // Get posts
    @GET("buildings/{buildingId}/posts")
    Observable<List<Post>> getBuildingPosts(@Path("buildingId") long buildingId);

    //Create post
    @POST("buildings/{buildingId}/posts")
    Observable<List<Post>> createBuildingPosts(@Path("buildingId") long buildingId, @Body com.miedificio.miedificio.networking.payload.Post post);


}
