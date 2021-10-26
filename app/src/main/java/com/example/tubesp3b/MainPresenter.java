package com.example.tubesp3b;

import android.app.Activity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainPresenter{
    private Activity activity;
    private FragmentManager fragmentManager;
    private MainFragment fragment_main;
    private FragmentListFilm fragment_list_film;
    private FragmentTambahFilm fragment_Tambah_Film;
    private int fragmentContainerId;

    public MainPresenter(MainActivity activity){
        this.activity = activity;
        this.fragmentManager = activity.getSupportFragmentManager();
        this.fragment_main = MainFragment.newInstance();
        this.fragment_list_film = FragmentListFilm.newInstance();
        this.fragment_Tambah_Film = FragmentTambahFilm.newInstance();
        this.fragmentContainerId = activity.getFrameLayoutId();
    }
    public void getMainPage(){
        FragmentTransaction ft =  this.fragmentManager.beginTransaction();
        ft.add(fragmentContainerId,fragment_main);
        ft.commit();
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
        }
        ft.commit();
    }
    public void closeApplication(){
        activity.moveTaskToBack(true);
        activity.finish();
    }
}
