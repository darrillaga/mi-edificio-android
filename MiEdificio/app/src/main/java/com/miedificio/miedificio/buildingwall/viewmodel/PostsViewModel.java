package com.miedificio.miedificio.buildingwall.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import com.miedificio.miedificio.model.Post;
import com.miedificio.miedificio.networking.MiEdificioServerClient;

import java.util.List;

import javax.inject.Inject;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import me.darrillaga.databinding.utils.ObservableDispatcherList;
import rx.Observable;
import rx.schedulers.Schedulers;

import static com.miedificio.miedificio.ui.BindingViewModelUtils.setProgressFlag;

public class PostsViewModel {

    public final ObservableList<PostViewModel> postViewModelList;
    public final ObservableBoolean fetchingPosts;

    @Inject
    transient MiEdificioServerClient miEdificioServerClient;

    private Long mBuildingId;

    public PostsViewModel(Long buildingId) {
        postViewModelList = ObservableDispatcherList.createMainThreadObservableList(new ObservableArrayList<>());
        fetchingPosts = new ObservableBoolean();

        mBuildingId = buildingId;
    }

    public Observable<? extends List<PostViewModel>> fetchPosts() {
        return Observable.defer(() ->
            miEdificioServerClient.getBuildingPosts(mBuildingId)
                .doOnNext(this::processPosts)
                .map(postList -> postViewModelList)
                .compose(setProgressFlag(fetchingPosts))
                .subscribeOn(Schedulers.io())
        ).cache();
    }

    public void addPost(PostViewModel postViewModel) {
        postViewModelList.add(0, postViewModel);
    }

    private void processPosts(List<Post> postList) {
        postViewModelList.addAll(createPostsViewModels(postList));
    }

    private List<PostViewModel> createPostsViewModels(List<Post> postList) {
        return StreamSupport.stream(postList)
                .map(this::createPostViewModel)
                .collect(Collectors.toList());
    }

    private PostViewModel createPostViewModel(Post post) {
        return new PostViewModel(post);
    }
}
