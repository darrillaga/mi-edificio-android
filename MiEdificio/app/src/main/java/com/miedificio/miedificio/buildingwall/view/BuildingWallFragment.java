package com.miedificio.miedificio.buildingwall.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.miedificio.miedificio.Application;
import com.miedificio.miedificio.ApplicationComponent;
import com.miedificio.miedificio.buildingwall.viewmodel.CreatePostViewModel;
import com.miedificio.miedificio.buildingwall.viewmodel.PostsViewModel;
import com.miedificio.miedificio.databinding.FragmentBuildingWallBinding;
import com.miedificio.miedificio.ui.ActivityFragmentsInteractionsHelper;
import com.miedificio.miedificio.ui.ErrorsHandler;
import com.trello.navi.component.support.NaviFragment;
import com.trello.rxlifecycle.FragmentLifecycleProvider;
import com.trello.rxlifecycle.navi.NaviLifecycle;

import rx.functions.Actions;

@FragmentWithArgs
public class BuildingWallFragment extends NaviFragment implements CreatePostEventsHandler {

    @Arg
    Long buildingId;

    @Arg
    Long buildingUserId;

    private FragmentLifecycleProvider mFragmentLifecycleProvider = NaviLifecycle.createFragmentLifecycleProvider(this);

    private FragmentBuildingWallBinding mFragmentBuildingWallBinding;

    private PostsViewModel mPostsViewModel;
    private CreatePostViewModel mCreatePostViewModel;

    private ErrorsHandler mErrorsHandler;

    private Interactions mInteractions;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        BuildingWallFragmentBuilder.injectArguments(this);

        mInteractions = ActivityFragmentsInteractionsHelper.
                ensureFragmentHasAttachedRequiredClassObject(this, Interactions.class);

        mErrorsHandler = new ErrorsHandler(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPostsViewModel = new PostsViewModel(buildingId);
        mCreatePostViewModel = new CreatePostViewModel(buildingId, buildingUserId);

        ApplicationComponent applicationComponent = Application.get(getContext()).getComponent();
        applicationComponent.inject(mPostsViewModel);
        applicationComponent.inject(mCreatePostViewModel);
    }

    @Override
    public void onStart() {
        super.onStart();

        mPostsViewModel.fetchPosts()
                .compose(mFragmentLifecycleProvider.bindToLifecycle())
                .subscribe(
                        Actions.empty(),
                        mErrorsHandler::handle
                );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentBuildingWallBinding = FragmentBuildingWallBinding
                .inflate(inflater, container, false);

        mFragmentBuildingWallBinding.setViewModel(mPostsViewModel);
        mFragmentBuildingWallBinding.setCreateViewModel(mCreatePostViewModel);
        mFragmentBuildingWallBinding.setCreateEventsHandler(this);

        return mFragmentBuildingWallBinding.getRoot();
    }

    @Override
    public void onCreatePostAction(View view) {
        mCreatePostViewModel.create()
                .compose(mFragmentLifecycleProvider.bindToLifecycle())
                .subscribe(
                        mPostsViewModel::addPost,
                        mErrorsHandler::handle
                );
    }

    public interface Interactions {

    }
}
