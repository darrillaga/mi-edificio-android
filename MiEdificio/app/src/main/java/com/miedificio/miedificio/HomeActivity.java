package com.miedificio.miedificio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.miedificio.miedificio.findbuilding.view.FindBuildingFragment;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new FindBuildingFragment())
                .commit();
    }
}
