package com.example.tubesp3b;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.tubesp3b.databinding.FragmentDetailBinding;

public class DetailFragment extends Fragment{
    private FragmentDetailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        this.getParentFragmentManager().setFragmentResultListener("getDetail", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(String requestKey , Bundle result){
                Movie movie = result.getParcelable("movie");
                binding.tvTitle.setText(movie.getTitle());
                binding.tvSynopsis.setText(movie.getSynopsis());
            }
        });
        return view;
    }
    public static DetailFragment newInstance(){
        DetailFragment fragment = new DetailFragment();
        return fragment;
    }
}