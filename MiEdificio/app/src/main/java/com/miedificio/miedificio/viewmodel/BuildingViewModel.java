package com.miedificio.miedificio.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.miedificio.miedificio.model.Building;
import com.miedificio.miedificio.networking.MiEdificioServerClient;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

public class BuildingViewModel extends BaseObservable {

    private static Building createBuildingWithId(Long buildingId) {
        Building building = new Building();
        building.setId(buildingId);
        return building;
    }

    @Inject
    transient MiEdificioServerClient miEdificioServerClient;

    private Long mBuildingId;
    private Building mBuilding;

    public final ObservableBoolean fetching;
    public final ObservableField<String> error;

    public BuildingViewModel(Building building) {
        mBuildingId = building.getId();
        mBuilding = building;
        fetching = new ObservableBoolean();
        error = new ObservableField<>();
    }

    public BuildingViewModel(Long buildingId) {
        this(createBuildingWithId(buildingId));
    }

    public String getName() {
        return mBuilding.getName();
    }

    public String getAddress() {
        return mBuilding.getAddress();
    }

    public String getContactEmail() {
        return mBuilding.getContactEmail();
    }

    public String getCode() {
        return mBuilding.getCode();
    }

    public Observable<Building> fetch() {
        return Observable.defer(() ->
                miEdificioServerClient.getBuilding(mBuildingId)
                        .doOnSubscribe(() -> fetching.set(true))
                        .doAfterTerminate(() -> fetching.set(false))
                        .doOnError(throwable -> error.set(throwable.getMessage()))
                        .doOnNext(this::onServerBuildingArrived)
                        .subscribeOn(Schedulers.io())
        ).cache();
    }

    private void onServerBuildingArrived(Building building) {
        mBuilding = building;
        mBuildingId = building.getId();

        notifyChange();
        error.set(null);
    }
}
