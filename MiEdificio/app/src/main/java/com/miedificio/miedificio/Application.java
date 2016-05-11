package com.miedificio.miedificio;

import android.content.Context;

public class Application extends android.app.Application {

    private ApplicationComponent mComponent;

    public static Application get(Context context) {
        return (Application) context.getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mComponent = DaggerApplicationComponent.create();
    }

    public ApplicationComponent getComponent() {
        return mComponent;
    }

    public void setComponent(ApplicationComponent component) {
        mComponent = component;
    }
}
