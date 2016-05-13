package com.miedificio.miedificio.createbuildinguser.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.miedificio.miedificio.model.BuildingUser;
import com.miedificio.miedificio.networking.MiEdificioServerClient;

import javax.inject.Inject;

import me.darrillaga.databinding.utils.BindableString;
import rx.Observable;
import rx.schedulers.Schedulers;

public class CreateBuildingUserViewModel {

    public final BindableString name;
    public final BindableString apartment;
    public final BindableString roleDescription;
    public final ObservableBoolean creating;

    @Inject
    transient MiEdificioServerClient miEdificioServerClient;

    private Long mBuildingId;

    public CreateBuildingUserViewModel(Long buildingId) {
        name = new BindableString();
        apartment = new BindableString();
        roleDescription = new BindableString();
        creating = new ObservableBoolean();

        mBuildingId = buildingId;
    }

    public Observable<BuildingUser> create() {
        com.miedificio.miedificio.networking.payload.BuildingUser buildingUserPayload =
                new com.miedificio.miedificio.networking.payload.BuildingUser();

        buildingUserPayload.setName(name.get());
        buildingUserPayload.setApartment(apartment.get());
        buildingUserPayload.setRoleDescription(roleDescription.get());

        return Observable.defer(() ->
                miEdificioServerClient.createBuildingUser(mBuildingId, buildingUserPayload)
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(() -> creating.set(true))
                        .doAfterTerminate(() -> creating.set(false))
        ).cache();
    }
}
