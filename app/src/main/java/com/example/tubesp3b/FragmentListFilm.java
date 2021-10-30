package com.example.tubesp3b;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.tubesp3b.databinding.ListFilmBinding;

import java.util.ArrayList;
import java.util.List;

public class FragmentListFilm extends Fragment implements View.OnClickListener{
    ListFilmBinding binding;
    private Button btnPlus;
    FilmListAdapter adapter;
    private ListFilmPresenter presenter;
    private Button btn_sortOrder;
    private List<Movie> movies = new ArrayList<Movie>();

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container,
                             Bundle savedInstanceState){
        this.binding = ListFilmBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        this.btnPlus= this.binding.btnPlus;
        this.btn_sortOrder = binding.btnSortOrder;

        this.presenter = new ListFilmPresenter(this);
        this.adapter = new FilmListAdapter(this,presenter);
        binding.listMovie.setAdapter(this.adapter);

        if(movies != null){
            adapter.initList(movies);
        }

        this.btnPlus.setOnClickListener(this);
        btn_sortOrder.setOnClickListener(this);
        return view;
    }
    public static FragmentListFilm newInstance(){
        FragmentListFilm fragment = new FragmentListFilm();
        return fragment;
    }
    @Override
    public void onClick(View view){
        if(view == this.btnPlus){
            Bundle bundle = new Bundle();
            bundle.putInt("page",6);
            getParentFragmentManager().setFragmentResult("changePage",bundle);
        }
        else{
            if(btn_sortOrder.getText().toString() == "Ascending"){
                binding.btnSortOrder.setText("Descending");
                adapter.sortAlphabetically("descending");
            }
            else{
                binding.btnSortOrder.setText("Ascending");
                adapter.sortAlphabetically("ascending");
            }
        }
    }
    public void setList(List<Movie> movies){
        this.movies = movies;
    }
}
