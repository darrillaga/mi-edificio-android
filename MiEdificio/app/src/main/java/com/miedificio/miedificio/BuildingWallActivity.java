package com.miedificio.miedificio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.miedificio.miedificio.buildingwall.view.BuildingWallFragment;
import com.miedificio.miedificio.buildingwall.view.BuildingWallFragmentBuilder;

import se.emilsjolander.intentbuilder.Extra;
import se.emilsjolander.intentbuilder.IntentBuilder;

@IntentBuilder
public class BuildingWallActivity extends AppCompatActivity implements BuildingWallFragment.Interactions {

    @Extra
    Long buildingId;

    @Extra
    Long buildingUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BuildingWallActivityIntentBuilder.inject(getIntent(), this);

        setContentView(R.layout.activity_building_wall);

        getSupportFragmentManager().beginTransaction()
                .replace(
                        R.id.container,
                        BuildingWallFragmentBuilder.newBuildingWallFragment(buildingId, buildingUserId),
                        BuildingWallFragment.class.getName()
                ).commit();
    }
}
