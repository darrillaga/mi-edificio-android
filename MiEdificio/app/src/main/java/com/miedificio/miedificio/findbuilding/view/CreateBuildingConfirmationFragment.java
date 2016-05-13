package com.miedificio.miedificio.findbuilding.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.miedificio.miedificio.Application;
import com.miedificio.miedificio.databinding.FragmentBuildingCreateConfirmationBinding;
import com.miedificio.miedificio.findbuilding.viewmodel.BuildingViewModel;
import com.miedificio.miedificio.ui.ActivityFragmentsInteractionsHelper;
import com.miedificio.miedificio.ui.ErrorsHandler;
import com.trello.navi.Event;
import com.trello.navi.component.support.NaviFragment;
import com.trello.rxlifecycle.FragmentLifecycleProvider;
import com.trello.rxlifecycle.navi.NaviLifecycle;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Actions;

@FragmentWithArgs
public class CreateBuildingConfirmationFragment extends NaviFragment
        implements CreateBuildingConfirmationEventsHandler {

    @Arg
    Long buildingId;

    private FragmentLifecycleProvider mFragmentLifecycleProvider = NaviLifecycle.createFragmentLifecycleProvider(this);

    private FragmentBuildingCreateConfirmationBinding mFragmentBuildingCreateConfirmationBinding;
    private BuildingViewModel mBuildingViewModel;
    private ErrorsHandler mErrorsHandler;

    private Interactions mInteractions;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        CreateBuildingConfirmationFragmentBuilder.injectArguments(this);

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

        mFragmentBuildingCreateConfirmationBinding = FragmentBuildingCreateConfirmationBinding.inflate(inflater, container, false);

        mFragmentBuildingCreateConfirmationBinding.setViewModel(mBuildingViewModel);

        mFragmentBuildingCreateConfirmationBinding.setEventsHandler(this);

        return mFragmentBuildingCreateConfirmationBinding.getRoot();
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
    public void onGoToMyBuildingAction(View view) {
        mInteractions.onGoToMyBuildingAction(buildingId);
    }

    public interface Interactions {
        void onGoToMyBuildingAction(Long buildingId);
    }
}
