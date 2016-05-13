package com.miedificio.miedificio.findbuilding.view;

import android.Manifest;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miedificio.miedificio.Application;
import com.miedificio.miedificio.R;
import com.miedificio.miedificio.databinding.FragmentFindBuildingBinding;
import com.miedificio.miedificio.findbuilding.viewmodel.FindBuildingViewModel;
import com.miedificio.miedificio.model.Building;
import com.miedificio.miedificio.ui.ActivityFragmentsInteractionsHelper;
import com.miedificio.miedificio.ui.ErrorsHandler;
import com.miedificio.miedificio.ui.KeyboardUtils;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.trello.navi.Event;
import com.trello.navi.component.support.NaviFragment;
import com.trello.navi.rx.RxNavi;
import com.trello.rxlifecycle.FragmentLifecycleProvider;
import com.trello.rxlifecycle.navi.NaviLifecycle;

import rx.android.schedulers.AndroidSchedulers;

public class FindBuildingFragment extends NaviFragment
        implements FindBuildingEventsHandler, CameraCodeReaderFragment.Interactions {

    private FragmentLifecycleProvider mFragmentLifecycleProvider = NaviLifecycle.createFragmentLifecycleProvider(this);
    private FragmentFindBuildingBinding mFragmentFindBuildingBinding;
    private FindBuildingViewModel mFindBuildingViewModel;
    private ErrorsHandler mErrorsHandler;
    private Interactions mInteractions;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mInteractions = ActivityFragmentsInteractionsHelper
                .ensureFragmentHasAttachedRequiredClassObject(this, Interactions.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFindBuildingViewModel = new FindBuildingViewModel();
        mErrorsHandler = new ErrorsHandler(getContext());

        Application.get(getContext()).getComponent().inject(mFindBuildingViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mFragmentFindBuildingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_find_building, container, false);

        mFragmentFindBuildingBinding.setViewModel(mFindBuildingViewModel);
        mFragmentFindBuildingBinding.setEventsHandler(this);

        return mFragmentFindBuildingBinding.getRoot();
    }

    @Override
    public void onLoginAction(View view) {
        mFindBuildingViewModel.checkCode()
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mFragmentLifecycleProvider.bindToLifecycle())
                .subscribe(this::goToBuilding, mErrorsHandler::handle);
    }

    private void goToBuilding(Building building) {
        mInteractions.onBuildingFoundAction(building);
    }

    private void startCameraCodeReaderInLifecycle() {
        if (isResumed()) {
            startCameraCodeReader();
        } else {
            RxNavi.observe(this, Event.RESUME)
                    .subscribe(aVoid -> startCameraCodeReader());
        }
    }

    @Override
    public void onScanCodeAction(View view) {
        RxPermissions.getInstance(getContext()).request(Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (granted) {
                        startCameraCodeReaderInLifecycle();
                    } else {
                        mErrorsHandler.handle(new Exception("Camera permission was not granted"));
                    }
                });

    }

    private void startCameraCodeReader() {
        KeyboardUtils.hideKeyboard(getContext(), getView());

        Fragment fragment = new CameraCodeReaderFragment();
        fragment.setTargetFragment(this, 0);

        getFragmentManager().beginTransaction()
                .replace(R.id.cameraReaderContainer, fragment, CameraCodeReaderFragment.class.getName())
                .addToBackStack(CameraCodeReaderFragment.class.getName())
                .commit();
    }

    @Override
    public void onCodeRead(String text) {
        getFragmentManager().popBackStack();
        mFindBuildingViewModel.code.set(text);
        onLoginAction(null);
    }

    public interface Interactions {
        void onBuildingFoundAction(Building building);
    }
}
