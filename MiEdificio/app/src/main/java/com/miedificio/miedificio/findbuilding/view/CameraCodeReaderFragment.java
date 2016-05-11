package com.miedificio.miedificio.findbuilding.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.Result;
import com.miedificio.miedificio.ui.ActivityFragmentsInteractionsHelper;
import com.trello.navi.component.support.NaviFragment;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CameraCodeReaderFragment extends NaviFragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private Interactions mInteractions;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mInteractions = ActivityFragmentsInteractionsHelper.
                 ensureFragmentHasAttachedRequiredClassObject(this, Interactions.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mScannerView = new ZXingScannerView(getContext());
        return mScannerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        mInteractions.onCodeRead(rawResult.getText());
    }

    public interface Interactions {
        void onCodeRead(String text);
    }
}
