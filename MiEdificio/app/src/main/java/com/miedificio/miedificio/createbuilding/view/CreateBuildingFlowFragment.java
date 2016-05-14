package com.miedificio.miedificio.createbuilding.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.miedificio.miedificio.R;
import com.miedificio.miedificio.createbuildinguser.view.CreateBuildingUserFragment;
import com.miedificio.miedificio.createbuildinguser.view.CreateBuildingUserFragmentBuilder;
import com.miedificio.miedificio.ui.ActivityFragmentsInteractionsHelper;
import com.trello.navi.component.support.NaviFragment;

import icepick.Icepick;
import icepick.State;

public class CreateBuildingFlowFragment extends NaviFragment
        implements CreateBuildingUserFragment.Interactions, CreateBuildingConfirmationEventsHandler {

    @State
    Long buildingId;

    @State
    Long buildingUserId;

    private Interactions mInteractions;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mInteractions = ActivityFragmentsInteractionsHelper.
                ensureFragmentHasAttachedRequiredClassObject(this, Interactions.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    private void attachCreateBuilding() {
    }

    private void attachCreateUser() {
        if (getFragmentManager().findFragmentByTag(CreateBuildingUserFragment.class.getName()) != null) {
            return;
        }

        Fragment fragment = new CreateBuildingUserFragmentBuilder(buildingId).build();

        fragment.setTargetFragment(this, 0);

        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentsContainer, fragment, CreateBuildingUserFragment.class.getName())
                .addToBackStack(CreateBuildingUserFragment.class.getName())
                .commit();
    }

    private void attachCreateBuildingConfirmation() {
        if (getFragmentManager().findFragmentByTag(CreateBuildingConfirmationFragment.class.getName()) != null) {
            return;
        }

        Fragment fragment = new CreateBuildingConfirmationFragmentBuilder(buildingId).build();

        fragment.setTargetFragment(this, 0);

        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentsContainer, fragment, CreateBuildingConfirmationFragment.class.getName())
                .addToBackStack(CreateBuildingConfirmationFragment.class.getName())
                .commit();
    }

    @Override
    public void onBuildingUserCreated(Long buildingUserId) {
        this.buildingUserId = buildingUserId;
        attachCreateBuildingConfirmation();
    }

    @Override
    public void onGoToMyBuildingAction(View view) {
        mInteractions.onBuildingCreateFlowCompleted(buildingId, buildingUserId);
    }

    public interface Interactions {
        void onBuildingCreateFlowCompleted(Long buildingId, Long buildingUserId);
    }
}
