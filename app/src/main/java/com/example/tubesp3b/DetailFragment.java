package com.example.tubesp3b;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.tubesp3b.databinding.FragmentDetailBinding;

public class DetailFragment extends Fragment implements View.OnClickListener{
    private FragmentDetailBinding binding;
    private Movie currMovie;
    private DetailPresenter presenter;
    private int moviePosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        this.presenter = new DetailPresenter(this);
        if(currMovie != null){
            initDetail();
        }
        this.getParentFragmentManager().setFragmentResultListener("getDetail", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(String requestKey , Bundle result){
                Movie movie = result.getParcelable("movie");
                moviePosition = result.getInt("position");
                currMovie = movie;
                binding.tvTitle.setText(currMovie.getTitle());
                binding.tvSynopsis.setText(currMovie.getSynopsis());

                byte[] decodedString = Base64.decode(currMovie.getPoster().getBytes(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                binding.ivPoster.setImageBitmap(decodedByte);

                binding.tvStatus.setText(currMovie.getStatus());
                binding.rbRating.setRating(currMovie.getRating());
                binding.tvReview.setText(currMovie.getReview());
            }
        });
        this.getParentFragmentManager().setFragmentResultListener("changeDetail", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(String requestKey , Bundle result){
                Movie movie = result.getParcelable("editedMovie");
                currMovie = movie;
                binding.tvTitle.setText(currMovie.getTitle());
                binding.tvSynopsis.setText(currMovie.getSynopsis());

                byte[] decodedString = Base64.decode(currMovie.getPoster().getBytes(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                binding.ivPoster.setImageBitmap(decodedByte);

                binding.tvStatus.setText(currMovie.getStatus());
                binding.rbRating.setRating(currMovie.getRating());
                binding.tvReview.setText(currMovie.getReview());
            }
        });
        binding.btnEdit.setOnClickListener(this);
        return view;
    }
    public static DetailFragment newInstance(){
        DetailFragment fragment = new DetailFragment();
        return fragment;
    }

    public void initDetail(){
        binding.tvTitle.setText(currMovie.getTitle());
        binding.tvSynopsis.setText(currMovie.getSynopsis());

        byte[] decodedString = Base64.decode(currMovie.getPoster().getBytes(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        binding.ivPoster.setImageBitmap(decodedByte);
        binding.tvStatus.setText(currMovie.getStatus());
        binding.tvReview.setText(currMovie.getReview());
    }

    @Override
    public void onClick(View view) {
        presenter.getEditDetailPage(currMovie, moviePosition);
    }
}