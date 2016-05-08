package com.miedificio.miedificio.commons;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.miedificio.miedificio.R;


/**
 * Created by asegurola on 2/16/16.
 */
public abstract class BaseActivityWithToolbar extends AppCompatActivity {
    private static final String TAG = BaseActivityWithToolbar.class.getSimpleName();

    private FrameLayout mContentFL;
    private RelativeLayout mLoadingRL;
    private int mLoadingCounter = 0;

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getBaseActivityLayout());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (isShowBackButton()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        mContentFL = (FrameLayout) findViewById(R.id.contentFL);
        mLoadingRL = (RelativeLayout) findViewById(R.id.loadingRL);

        View contentView = getLayoutInflater().inflate(getContentLayoutId(), mContentFL, false);
        mContentFL.addView(contentView);
    }

    protected int getBaseActivityLayout() {
        return R.layout.activity_base;
    }

    public void showLoadingLayer() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoadingCounter++;
                if (mLoadingCounter == 1) {
                    mLoadingRL.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void hideLoadingLayer() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoadingCounter--;
                if (mLoadingCounter < 0) {
                    mLoadingCounter = 0;
                }
                if (mLoadingCounter == 0) {
                    mLoadingRL.setVisibility(View.GONE);
                }
            }
        });
    }

    public void showError(final Throwable throwable) {
        Log.e(TAG, "Error", throwable);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivityWithToolbar.this, throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showError(final String errorMessage) {
        Log.e(TAG, "Error: " + errorMessage);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivityWithToolbar.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    @LayoutRes
    protected abstract int getContentLayoutId();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isLoading() {
        return mLoadingCounter > 0;
    }

    public boolean isShowBackButton() {
        return true;
    }
}
