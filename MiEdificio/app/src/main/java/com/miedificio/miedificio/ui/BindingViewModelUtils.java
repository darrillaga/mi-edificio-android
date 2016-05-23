package com.miedificio.miedificio.ui;

import android.databinding.ObservableBoolean;

import rx.Observable;

public class BindingViewModelUtils {

    public static <T> Observable.Transformer<T, T> setProgressFlag(ObservableBoolean flag) {
        return observable -> observable
                .doOnSubscribe(() -> flag.set(true))
                .doAfterTerminate(() -> flag.set(false));
    }
}
