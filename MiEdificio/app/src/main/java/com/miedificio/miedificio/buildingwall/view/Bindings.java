package com.miedificio.miedificio.buildingwall.view;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.miedificio.miedificio.BR;
import com.miedificio.miedificio.R;
import com.miedificio.miedificio.buildingwall.viewmodel.PostViewModel;
import com.miedificio.miedificio.ui.DataBindingAdapterUtils;

import me.darrillaga.databinding.utils.ObservableListAdapterObservers;

public class Bindings {

    @BindingAdapter(value = { "app:dataSet" }, requireAll = false)
    public static void setupPostsAdapter(RecyclerView recyclerView,
                                         ObservableList<PostViewModel> postViewModelList) {

        if (recyclerView.getTag(R.id.adapterAdded) != null) {
            return;
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false);

        layoutManager.setAutoMeasureEnabled(true);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(
                DataBindingAdapterUtils.createAdapter(
                        postViewModelList, R.layout.layout_post_list_item, BR.viewModel
                )
        );

        postViewModelList.addOnListChangedCallback(
                ObservableListAdapterObservers.createObservableListAdapterBridge(
                        postViewModelList, null, recyclerView.getAdapter()
                )
        );

        recyclerView.setTag(R.id.adapterAdded, new Object());
    }
}
