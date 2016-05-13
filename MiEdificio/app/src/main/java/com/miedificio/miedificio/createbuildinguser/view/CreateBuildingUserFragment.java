package com.miedificio.miedificio.createbuildinguser.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.miedificio.miedificio.Application;
import com.miedificio.miedificio.createbuildinguser.viewmodel.CreateBuildingUserViewModel;
import com.miedificio.miedificio.databinding.FragmentCreateBuildingUserBinding;
import com.miedificio.miedificio.model.BuildingUser;
import com.miedificio.miedificio.ui.ActivityFragmentsInteractionsHelper;
import com.miedificio.miedificio.ui.ErrorsHandler;
import com.trello.navi.component.support.NaviFragment;
import com.trello.rxlifecycle.FragmentLifecycleProvider;
import com.trello.rxlifecycle.navi.NaviLifecycle;

import rx.android.schedulers.AndroidSchedulers;

@FragmentWithArgs
public class CreateBuildingUserFragment extends NaviFragment implements CreateBuildingUserEventsHandler {

    @Arg
    Long buildingId;

    private FragmentLifecycleProvider mFragmentLifecycleProvider = NaviLifecycle.createFragmentLifecycleProvider(this);

    private FragmentCreateBuildingUserBinding mFragmentCreateBuildingUserBinding;
    private CreateBuildingUserViewModel mCreateBuildingUserViewModel;
    private ErrorsHandler mErrorsHandler;

    private Interactions mInteractions;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        CreateBuildingUserFragmentBuilder.injectArguments(this);

        mInteractions = ActivityFragmentsInteractionsHelper.
                ensureFragmentHasAttachedRequiredClassObject(this, Interactions.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCreateBuildingUserViewModel = new CreateBuildingUserViewModel(buildingId);
        Application.get(getContext()).getComponent().inject(mCreateBuildingUserViewModel);

        mErrorsHandler = new ErrorsHandler(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentCreateBuildingUserBinding = FragmentCreateBuildingUserBinding.inflate(inflater, container, false);

        mFragmentCreateBuildingUserBinding.setViewModel(mCreateBuildingUserViewModel);
        mFragmentCreateBuildingUserBinding.setEventsHandler(this);

        return mFragmentCreateBuildingUserBinding.getRoot();
    }

    @Override
    public void onCreateBuildingUserAction(View view) {
        createBuildingUser();
    }

    @Override
    public void onRetryAction(View view) {
        createBuildingUser();
    }

    private void createBuildingUser() {
        mCreateBuildingUserViewModel.create()
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mFragmentLifecycleProvider.bindToLifecycle())
                .subscribe(this::onBuildingUserCreated, mErrorsHandler::handle);
    }

    private void onBuildingUserCreated(BuildingUser buildingUser) {
        mInteractions.onBuildingUserCreated(buildingUser.getId());
    }

    public interface Interactions {
        void onBuildingUserCreated(Long buildingUserId);
    }
}
