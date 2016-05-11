package com.miedificio.miedificio.findbuilding.viewmodel;

import com.miedificio.miedificio.model.Building;
import com.miedificio.miedificio.networking.MiEdificioServerClient;

import javax.inject.Inject;

import me.darrillaga.databinding.utils.BindableString;
import rx.Observable;
import rx.schedulers.Schedulers;

public class FindBuildingViewModel {

    public final BindableString code;

    @Inject
    transient MiEdificioServerClient miEdificioServerClient;

    public FindBuildingViewModel() {
        code = new BindableString();
    }

    public Observable<Building> checkCode() {
        String codeString = code.get();

        return Observable.defer(() ->
            miEdificioServerClient.getBuildingByCode(codeString)
                    .subscribeOn(Schedulers.io())
        ).cache();
    }
}
