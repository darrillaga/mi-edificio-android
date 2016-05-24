package com.miedificio.miedificio;

import com.miedificio.miedificio.buildingwall.viewmodel.CreatePostViewModel;
import com.miedificio.miedificio.buildingwall.viewmodel.PostsViewModel;
import com.miedificio.miedificio.createbuildinguser.viewmodel.CreateBuildingUserViewModel;
import com.miedificio.miedificio.viewmodel.BuildingViewModel;
import com.miedificio.miedificio.findbuilding.viewmodel.FindBuildingViewModel;
import com.miedificio.miedificio.networking.RestServicesModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(
        modules = { ApplicationModule.class, RestServicesModule.class }
)
@Singleton
public interface ApplicationComponent {

    void inject(FindBuildingViewModel findBuildingViewModel);
    void inject(BuildingViewModel buildingViewModel);
    void inject(CreateBuildingUserViewModel createBuildingUserViewModel);
    void inject(PostsViewModel postsViewModel);
    void inject(CreatePostViewModel createPostViewModel);
}
