package com.miedificio.miedificio.buildingwall.view;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.trello.navi.component.support.NaviFragment;

@FragmentWithArgs
public class BuildingWallFragment extends NaviFragment {

    @Arg
    Long buildingId;

    @Arg
    Long buildingUserId;
}
