package com.miedificio.miedificio.buildingwall.viewmodel;

import android.text.TextUtils;

import com.miedificio.miedificio.model.BuildingUser;
import com.miedificio.miedificio.model.Post;

import java8.util.stream.Collectors;
import java8.util.stream.RefStreams;

public class PostViewModel {

    private Post mPost;

    public PostViewModel(Post post) {
        mPost = post;
    }

    public String getText() {
        return mPost.getText();
    }

    public String getUserDetails() {
        BuildingUser buildingUser = mPost.getBuildingUser();

        return RefStreams.of(
                buildingUser.getName(),
                buildingUser.getApartment(),
                buildingUser.getRoleDescription()
        )
                .filter(s -> !TextUtils.isEmpty(s))
                .collect(Collectors.joining(" - "));
    }
}
