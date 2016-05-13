package com.miedificio.miedificio.findbuilding.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miedificio.miedificio.R;
import com.miedificio.miedificio.createbuildinguser.view.CreateBuildingUserFragment;
import com.miedificio.miedificio.createbuildinguser.view.CreateBuildingUserFragmentBuilder;
import com.miedificio.miedificio.model.Building;
import com.miedificio.miedificio.ui.ActivityFragmentsInteractionsHelper;
import com.trello.navi.Event;
import com.trello.navi.component.support.NaviFragment;

import icepick.Icepick;
import icepick.State;

public class JoinBuildingFlowFragment extends NaviFragment
        implements FindBuildingFragment.Interactions,
        JoinBuildingConfirmationFragment.Interactions,
        CreateBuildingConfirmationFragment.Interactions,
        CreateBuildingUserFragment.Interactions {

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        addListener(Event.START, aVoid -> attachFindBuilding());

        return inflater.inflate(R.layout.fragment_join_building_flow, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onJoinBuildingAction(Long buildingId) {
        this.buildingId = buildingId;
        attachCreateUser();
    }

    private void attachFindBuilding() {
        if (getFragmentManager().findFragmentByTag(FindBuildingFragment.class.getName()) != null) {
            return;
        }

        Fragment fragment = new FindBuildingFragment();
        fragment.setTargetFragment(this, 0);

        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentsContainer, fragment, FindBuildingFragment.class.getName())
                .commit();
    }

    private void attachJoinBuildingConfirmation(Building building) {
        if (getFragmentManager().findFragmentByTag(JoinBuildingConfirmationFragment.class.getName()) != null) {
            return;
        }

        Fragment fragment = new JoinBuildingConfirmationFragmentBuilder(building.getId()).build();

        fragment.setTargetFragment(this, 0);

        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentsContainer, fragment, JoinBuildingConfirmationFragment.class.getName())
                .addToBackStack(JoinBuildingConfirmationFragment.class.getName())
                .commit();
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
    public void onBuildingFoundAction(Building building) {
        attachJoinBuildingConfirmation(building);
    }

    @Override
    public void onGoToMyBuildingAction(Long buildingId) {
        mInteractions.onBuildingJoinFlowCompleted(buildingId, buildingUserId);
    }

    @Override
    public void onBuildingUserCreated(Long buildingUserId) {
        this.buildingUserId = buildingUserId;
        attachCreateBuildingConfirmation();
    }

    public interface Interactions {
        void onBuildingJoinFlowCompleted(Long buildingId, Long buildingUserId);
    }
}
