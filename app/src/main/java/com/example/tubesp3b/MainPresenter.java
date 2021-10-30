package com.example.tubesp3b;

import android.app.Activity;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainPresenter{
    private Activity activity;
    private FragmentManager fragmentManager;
    private MainFragment fragment_main;
    private FragmentListFilm fragment_list_film;
    private FragmentTambahFilm fragment_Tambah_Film;
    private int fragmentContainerId;
    private List<Movie> movies = new ArrayList<Movie>();
    private FilmListAdapter adapter;
    private String sharedPrefFile = "com.example.tubesp3b";
    private DetailFragment fragment_detail;
    private EditDetailFragment fragment_edit_detail;

    public MainPresenter(MainActivity activity){
        this.activity = activity;
        this.fragmentManager = activity.getSupportFragmentManager();
        this.fragment_main = MainFragment.newInstance();
        this.fragment_Tambah_Film = FragmentTambahFilm.newInstance();
        this.fragmentContainerId = activity.getFrameLayoutId();
        this.fragment_list_film = FragmentListFilm.newInstance();
        loadList();
        this.fragment_detail = DetailFragment.newInstance();
        this.fragment_edit_detail = EditDetailFragment.newInstance();
    }
    public void getMainPage(){
        FragmentTransaction ft =  this.fragmentManager.beginTransaction();
        ft.add(fragmentContainerId,fragment_main);
        ft.commit();

        SharedPreferences prefs = activity.getSharedPreferences("prefs", activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }
    public void changePage(int page){
        FragmentTransaction ft =  this.fragmentManager.beginTransaction();
        if(page==1){
            if(fragment_main.isAdded()){
                ft.show(fragment_main);
            }else{
                ft.replace(fragmentContainerId,fragment_main)
                    .addToBackStack(null);
            }
        } else if(page==2){
            if(fragment_list_film.isAdded()){
                ft.show(fragment_list_film);
            }else{
                ft.replace(fragmentContainerId,fragment_list_film)
                        .addToBackStack(null);
            }
        } else if(page==5) {
            closeApplication();
        } else if(page==6){
            if(fragment_Tambah_Film.isAdded()){
                ft.show(fragment_Tambah_Film);
            }else{
                ft.replace(fragmentContainerId,fragment_Tambah_Film)
                        .addToBackStack(null);
            }
        } else if(page==7){
            if(fragment_detail.isAdded()){
                ft.show(fragment_detail);
            }else{
                ft.replace(fragmentContainerId,fragment_detail)
                        .addToBackStack(null);
            }
        } else if(page==8){
            if(fragment_edit_detail.isAdded()){
                ft.show(fragment_edit_detail);
            }else{
                ft.replace(fragmentContainerId,fragment_edit_detail)
                        .addToBackStack(null);
            }
        }
        ft.commit();
    }
    public void closeApplication(){
        activity.moveTaskToBack(true);
        activity.finish();
    }
    public void addList(Movie newMovie){
        this.movies.add(newMovie);
        sortAscending();
        this.fragment_list_film.setList(movies);
    }
    public void deleteList(Movie movie){
        movies.remove(movie);
        FragmentListFilm f2 = new FragmentListFilm();
        f2.setList(movies);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(fragmentContainerId, f2);
        ft.addToBackStack(null);
        ft.commit();
    }
    public void sortAscending() {
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie m1, Movie m2) {
                return m1.getTitle().compareTo(m2.getTitle());
            }
        });
    }
    public void changeList(Movie movie, int moviePosition){
        movies.set(moviePosition, movie);
    }
    public void saveList(){
        SharedPreferences sp = this.activity.getSharedPreferences(sharedPrefFile, activity.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(movies);
        preferencesEditor.putString("movies",json);
        preferencesEditor.apply();
    }
    public void loadList(){
        SharedPreferences sp = this.activity.getSharedPreferences(sharedPrefFile, activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("movies",null);
        Type type = new TypeToken<ArrayList<Movie>>() {}.getType();
        movies = gson.fromJson(json,type);

        if (movies == null){
            movies = new ArrayList<>();
        }
        this.fragment_list_film.setList(movies);
    }
    public FilmListAdapter getFilmListAdapter(){
        return fragment_list_film.adapter;
    }
}
