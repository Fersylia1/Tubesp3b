package com.example.tubesp3b;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.tubesp3b.databinding.ListFilmBinding;

public class FragmentListFilm extends Fragment implements View.OnClickListener{
    ListFilmBinding binding;
    private Button btnPlus;

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container,
                             Bundle savedInstanceState){
        this.binding = ListFilmBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        this.btnPlus= this.binding.btnPlus;
        this.btnPlus.setOnClickListener(this);
        return view;

    }
    public static FragmentListFilm newInstance(){
        FragmentListFilm fragment = new FragmentListFilm();
        return fragment;
    }
    public void onClick(View view){
        if(view == this.btnPlus){
            Bundle bundle = new Bundle();
            bundle.putInt("page",6);
            getParentFragmentManager().setFragmentResult("changePage",bundle);
        }
    }
}
