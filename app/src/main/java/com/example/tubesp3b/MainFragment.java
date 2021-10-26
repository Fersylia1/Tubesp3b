package com.example.tubesp3b;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.tubesp3b.databinding.MainFragmentBinding;

public class MainFragment extends Fragment  {
    private Button add,start;
    private MainFragmentBinding binding;

    public MainFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container,
                             Bundle savedInstanceState){
        this.binding = MainFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        this.start = binding.BtnStart;
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle result = new Bundle();
                result.putInt("page", 2);
                getParentFragmentManager().setFragmentResult("changePage", result);
            }
        });
        return view;
    }
    public static MainFragment newInstance(){
        MainFragment fragment = new MainFragment();
        return fragment;
    }
}
