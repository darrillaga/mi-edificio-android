package com.miedificio.miedificio.buildingwall.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import java8.util.stream.Collectors;
import java8.util.stream.IntStreams;

public class PostsViewModel {

    public final ObservableList<PostViewModel> postViewModelList;

    public PostsViewModel() {
        postViewModelList = new ObservableArrayList<>();
        postViewModelList.addAll(IntStreams.range(0, 1000).mapToObj(value -> new PostViewModel()).collect(Collectors.toList()));
    }
}
