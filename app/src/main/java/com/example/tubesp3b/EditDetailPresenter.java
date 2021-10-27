package com.example.tubesp3b;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;

public class EditDetailPresenter {
    private FragmentManager fragmentManager;
    private EditDetailFragment fragment_edit_detail;

    public EditDetailPresenter(EditDetailFragment fragment_edit_detail){
        this.fragmentManager = fragment_edit_detail.getParentFragmentManager();
        this.fragment_edit_detail = fragment_edit_detail;
    }

    public void saveDetails(Movie movie, int moviePosition) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("editedMovie", movie);
        bundle.putInt("position", moviePosition);
        fragmentManager.setFragmentResult("changeDetail",bundle);
        fragmentManager.setFragmentResult("changeList",bundle);

        bundle = new Bundle();
        bundle.putInt("page",7);
        fragmentManager.setFragmentResult("changePage",bundle);
    }
}
