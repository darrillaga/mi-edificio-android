package com.miedificio.miedificio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.miedificio.miedificio.findbuilding.view.FindBuildingFragment;
import com.miedificio.miedificio.findbuilding.view.JoinBuildingFlowFragment;

public class HomeActivity extends AppCompatActivity implements JoinBuildingFlowFragment.Interactions {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new JoinBuildingFlowFragment())
                .commit();
    }

    @Override
    public void onBuildingJoinFlowCompleted(Long buildingId, Long buildingUserId) {
        finish();

        startActivity(new BuildingWallActivityIntentBuilder(buildingId, buildingUserId).build(this));
    }
}
