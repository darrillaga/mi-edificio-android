package com.miedificio.miedificio.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java8.util.stream.StreamSupport;

public class ActivityFragmentsInteractionsHelper {

    public static <T> T ensureActivityHasAttachedRequiredClassObject(Activity activity,
                                                                     Class<T> requiredClass) {

        try {
            return requiredClass.cast(activity);
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + requiredClass.getCanonicalName());
        }
    }

    public static <T> T ensureFragmentHasAttachedRequiredClassObject(Fragment fragment,
                                                             Class<T> requiredClass) {

        Object requiredObject;

        if (fragment.getTargetFragment() != null && requiredClass.isInstance(fragment.getTargetFragment())) {
            requiredObject = fragment.getTargetFragment();
        } else if (fragment.getParentFragment() != null && requiredClass.isInstance(fragment.getParentFragment())) {
            requiredObject = fragment.getParentFragment();
        } else {
            requiredObject = fragment.getActivity();
        }

        try {
            return requiredClass.cast(requiredObject);
        } catch (ClassCastException e) {
            throw new ClassCastException(requiredObject.toString()
                    + " must implement " + requiredClass.getCanonicalName());
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static <T> T ensureFragmentHasAttachedRequiredClassObject(android.app.Fragment fragment,
                                                                     Class<T> requiredClass) {

        Object requiredObject;

        if (fragment.getTargetFragment() != null && requiredClass.isInstance(fragment.getParentFragment())) {
            requiredObject = fragment.getTargetFragment();
        } else if (fragment.getParentFragment() != null && requiredClass.isInstance(fragment.getParentFragment())) {
            requiredObject = fragment.getParentFragment();
        } else {
            requiredObject = fragment.getActivity();
        }

        try {
            return requiredClass.cast(requiredObject);
        } catch (ClassCastException e) {
            throw new ClassCastException(requiredObject.toString()
                    + " must implement " + requiredClass.getCanonicalName());
        }
    }

    public static void checkResultOnHierarchy(FragmentManager fragmentManager, int requestCode, int resultCode, Intent data) {
        StreamSupport.stream(fragmentManager.getFragments()).
                filter(f -> f != null).
                forEach(f -> f.onActivityResult(requestCode, resultCode, data));
    }
}
