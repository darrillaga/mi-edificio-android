package com.miedificio.miedificio.findbuilding.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.miedificio.miedificio.Application;
import com.miedificio.miedificio.R;
import com.miedificio.miedificio.databinding.FragmentJoinBuildingConfirmationBinding;
import com.miedificio.miedificio.viewmodel.BuildingViewModel;
import com.miedificio.miedificio.ui.ActivityFragmentsInteractionsHelper;
import com.miedificio.miedificio.ui.ErrorsHandler;
import com.trello.navi.Event;
import com.trello.navi.component.support.NaviFragment;
import com.trello.rxlifecycle.FragmentLifecycleProvider;
import com.trello.rxlifecycle.navi.NaviLifecycle;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Actions;

@FragmentWithArgs
public class JoinBuildingConfirmationFragment extends NaviFragment
        implements JoinBuildingConfirmationEventsHandler {

    @Arg
    Long buildingId;

    private FragmentLifecycleProvider mFragmentLifecycleProvider = NaviLifecycle.createFragmentLifecycleProvider(this);

    private FragmentJoinBuildingConfirmationBinding mJoinBuildingConfirmationBinding;
    private BuildingViewModel mBuildingViewModel;
    private ErrorsHandler mErrorsHandler;

    private Interactions mInteractions;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        JoinBuildingConfirmationFragmentBuilder.injectArguments(this);

        mInteractions = ActivityFragmentsInteractionsHelper
                .ensureFragmentHasAttachedRequiredClassObject(this, Interactions.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentArgs.inject(this);

        mBuildingViewModel = new BuildingViewModel(buildingId);
        mErrorsHandler = new ErrorsHandler(getContext());

        Application.get(getContext()).getComponent().inject(mBuildingViewModel);

        addListener(Event.START, aVoid -> doBuildingFetch());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mJoinBuildingConfirmationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_join_building_confirmation, container, false);

        mJoinBuildingConfirmationBinding.setViewModel(mBuildingViewModel);

        mJoinBuildingConfirmationBinding.setEventsHandler(this);

        return mJoinBuildingConfirmationBinding.getRoot();
    }

    private void doBuildingFetch() {
        mBuildingViewModel.fetch()
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mFragmentLifecycleProvider.bindToLifecycle())
                .subscribe(
                        Actions.empty(),
                        throwable -> {}
                );
    }

    @Override
    public void onJoinBuildingAction(View view) {
        mInteractions.onJoinBuildingAction(buildingId);
    }

    @Override
    public void onRetryAction(View view) {
        doBuildingFetch();
    }

    public interface Interactions {
        void onJoinBuildingAction(Long buildingId);
    }
}
