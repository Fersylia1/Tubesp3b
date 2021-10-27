package com.example.tubesp3b;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;

public class DetailPresenter {
    private FragmentManager fragmentManager;
    private DetailFragment fragment_detail;

    public DetailPresenter(DetailFragment fragment_detail){
        this.fragmentManager = fragment_detail.getParentFragmentManager();
        this.fragment_detail = fragment_detail;
    }
    public void getEditDetailPage(Movie movie, int moviePosition){
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie", movie);
        bundle.putInt("position", moviePosition);
        fragmentManager.setFragmentResult("getEditDetail",bundle);

        bundle = new Bundle();
        bundle.putInt("page",8);
        fragmentManager.setFragmentResult("changePage",bundle);
    }
}
