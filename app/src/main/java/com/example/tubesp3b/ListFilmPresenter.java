package com.example.tubesp3b;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

public class ListFilmPresenter {
    private FragmentManager fragmentManager;
    private FragmentListFilm fragment_list_film;

    public ListFilmPresenter(FragmentListFilm fragment_list_film){
        this.fragmentManager = fragment_list_film.getParentFragmentManager();
        this.fragment_list_film = fragment_list_film;
    }
    public void getDetailPage(Movie movie, int position){
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie",movie);
        bundle.putInt("position", position);
        fragmentManager.setFragmentResult("getDetail",bundle);

        bundle = new Bundle();
        bundle.putInt("page",7);
        fragmentManager.setFragmentResult("changePage",bundle);
    }
}
