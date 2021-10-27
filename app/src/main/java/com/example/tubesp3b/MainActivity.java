package com.example.tubesp3b;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentResultListener;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.tubesp3b.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity{
    private ActivityMainBinding binding;
    private Toolbar toolbar;
    private MainPresenter presenter;
    private PenyimpananNilai pencatat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityMainBinding.inflate(this.getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sp = this.getPreferences(MODE_PRIVATE);

        this.toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        ActionBarDrawerToggle abdt = new ActionBarDrawerToggle(this, drawer, this.toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawer.addDrawerListener(abdt);
        abdt.syncState();

        this.presenter = new MainPresenter(this);
        this.presenter.getMainPage();
        this.getSupportFragmentManager().setFragmentResultListener("addMovie", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(String requestKey , Bundle result){
                String judul = result.getString("judul");
                String sinopsis = result.getString("sinopsis");
                String poster = result.getString("poster");
                Movie newMovie = new Movie(judul,sinopsis, poster, "", "");
                presenter.addList(newMovie);
                presenter.changePage(2);
            }
        });
        this.getSupportFragmentManager().setFragmentResultListener("changeList", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(String requestKey , Bundle result){
                Movie movie = result.getParcelable("editedMovie");
                int moviePosition = result.getInt("position");
                presenter.changeList(movie, moviePosition);
            }
        });
        this.getSupportFragmentManager().setFragmentResultListener("changePage", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                int page = result.getInt("page");
                presenter.changePage(page);
                drawer.closeDrawers();
            }
        });
    }

    public int getFrameLayoutId() {
        return binding.fragmentContainer.getId();
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        this.presenter.saveList();
//    }
}