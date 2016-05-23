package com.miedificio.miedificio.buildingwall.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;

import com.miedificio.miedificio.networking.MiEdificioServerClient;
import com.miedificio.miedificio.networking.payload.Post;

import javax.inject.Inject;

import me.darrillaga.databinding.utils.BindableString;
import rx.Observable;
import rx.schedulers.Schedulers;

import static com.miedificio.miedificio.ui.BindingViewModelUtils.setProgressFlag;

public class CreatePostViewModel extends BaseObservable {

    public final BindableString text;
    public final ObservableBoolean creatingPost;

    @Inject
    transient MiEdificioServerClient miEdificioServerClient;

    private Long mBuildingUserId;
    private Long mBuildingId;

    public CreatePostViewModel(Long buildingId, Long buildingUserId) {
        text = new BindableString();
        creatingPost = new ObservableBoolean();

        mBuildingUserId = buildingUserId;
        mBuildingId = buildingId;
    }

    public Observable<PostViewModel> create() {
        Post post = new Post();

        post.setText(text.get());
        BindableString.clearString(text);

        post.setBuildingUserId(mBuildingUserId);

        return Observable.defer(() ->
                miEdificioServerClient.createBuildingPost(mBuildingId, post)
                        .map(this::processPost)
                        .compose(setProgressFlag(creatingPost))
                        .subscribeOn(Schedulers.io())
        ).cache();
    }

    private PostViewModel processPost(com.miedificio.miedificio.model.Post post) {
        return new PostViewModel(post);
    }
}
