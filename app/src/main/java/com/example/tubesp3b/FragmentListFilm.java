package com.example.tubesp3b;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.tubesp3b.databinding.ListFilmBinding;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class FragmentListFilm extends Fragment implements View.OnClickListener{
    ListFilmBinding binding;
    private Button btnPlus;
    private FilmListAdapter adapter;
    private ListFilmPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container,
                             Bundle savedInstanceState){
        this.binding = ListFilmBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        this.btnPlus= this.binding.btnPlus;

        this.presenter = new ListFilmPresenter(this);
        this.adapter = new FilmListAdapter(this,presenter);
        binding.listMovie.setAdapter(this.adapter);

        if(!(this.getArguments() == null)){
            adapter.initList(this.getArguments().getParcelableArrayList("movieList"));
        }
//        else{
//            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
//            Gson gson = new Gson();
//            String json = sharedPrefs.getString("movies", "");
//            Type type = new TypeToken<List<Movie>>() {}.getType();
//            List<Movie> arrayList = gson.fromJson(json, type);
//            adapter.initList(arrayList);
//        }

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
