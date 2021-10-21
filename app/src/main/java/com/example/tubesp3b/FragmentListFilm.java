package com.example.tubesp3b;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.tubesp3b.databinding.ListFilmBinding;

public class FragmentListFilm extends Fragment {
    ListFilmBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container,
                             Bundle savedInstanceState){
        this.binding = ListFilmBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;

    }
    public static FragmentListFilm newInstance(){
        FragmentListFilm fragment = new FragmentListFilm();
        return fragment;
    }
}
