package com.example.tubesp3b;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tubesp3b.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FragmentManager fragmentManager;
    private MainFragment fragment_main;
    private FragmentListFilm fragment_list_film;
    private Toolbar toolbar;
    private FragmentTambahFilm fragment_Tambah_Film;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityMainBinding.inflate(this.getLayoutInflater());
        setContentView(binding.getRoot());



        this.toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        ActionBarDrawerToggle abdt = new ActionBarDrawerToggle(this, drawer, this.toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawer.addDrawerListener(abdt);
        abdt.syncState();

        this.fragment_main = MainFragment.newInstance();
        this.fragment_list_film = FragmentListFilm.newInstance();
        this.fragmentManager = getSupportFragmentManager();
        this.fragment_Tambah_Film = new FragmentTambahFilm();
        changePage(1);
        this.getSupportFragmentManager().setFragmentResultListener("addMovie", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(String requestKey , Bundle result){
                String judul = result.getString("judul");
                String sinopsis = result.getString("sinopsis");
                //nunggu mvp ya mar..

            }
        });
        this.getSupportFragmentManager().setFragmentResultListener("changePage", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                int page = result.getInt("page");
                changePage(page);
                drawer.closeDrawers();
            }
        });
    }
    public void changePage(int page){
        FragmentTransaction ft =  this.fragmentManager.beginTransaction();
        if(page==1){
            if(fragment_main.isAdded()){
                ft.show(fragment_main);
            }else{
                ft.add(binding.fragmentContainer.getId(),fragment_main);
            }
            if(fragment_list_film.isAdded()){
                ft.hide(fragment_list_film);
            }
        } else if(page==2){
            if(fragment_list_film.isAdded()){
                ft.show(fragment_list_film);
            }else{
                ft.add(binding.fragmentContainer.getId(),fragment_list_film)
                        .addToBackStack(null);
            }
            if(fragment_main.isAdded()){
                ft.hide(fragment_main);
            }
        } else if(page==5) {
            closeApplication();
        } else if(page==6){
            if(fragment_Tambah_Film.isAdded()){
                ft.show(fragment_Tambah_Film);
            }else{
                ft.add(binding.fragmentContainer.getId(),fragment_Tambah_Film);
            }
            if(fragment_list_film.isAdded()){
                ft.hide(fragment_list_film);
            }
        }
        ft.commit();
    }
    public void closeApplication(){
        moveTaskToBack(true);
        finish();
    }
}