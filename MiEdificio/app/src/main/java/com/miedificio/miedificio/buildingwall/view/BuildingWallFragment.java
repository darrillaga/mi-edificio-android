package com.miedificio.miedificio.buildingwall.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.miedificio.miedificio.buildingwall.viewmodel.PostsViewModel;
import com.miedificio.miedificio.databinding.FragmentBuildingWallBinding;
import com.miedificio.miedificio.ui.ActivityFragmentsInteractionsHelper;
import com.trello.navi.component.support.NaviFragment;

@FragmentWithArgs
public class BuildingWallFragment extends NaviFragment {

    @Arg
    Long buildingId;

    @Arg
    Long buildingUserId;

    private FragmentBuildingWallBinding mFragmentBuildingWallBinding;
    private PostsViewModel mPostsViewModel;

    private Interactions mInteractions;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        BuildingWallFragmentBuilder.injectArguments(this);

        mInteractions = ActivityFragmentsInteractionsHelper.
                ensureFragmentHasAttachedRequiredClassObject(this, Interactions.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPostsViewModel = new PostsViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentBuildingWallBinding = FragmentBuildingWallBinding
                .inflate(inflater, container, false);

        mFragmentBuildingWallBinding.setViewModel(mPostsViewModel);

        return mFragmentBuildingWallBinding.getRoot();
    }

    public interface Interactions {

    }
}
