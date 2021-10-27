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

import com.example.tubesp3b.databinding.FragmentEditDetailBinding;

import java.io.ByteArrayOutputStream;

public class EditDetailFragment extends Fragment implements View.OnClickListener{
    private FragmentEditDetailBinding binding;
    private EditDetailPresenter presenter;
    private int moviePosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentEditDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        this.presenter = new EditDetailPresenter(this);
        this.getParentFragmentManager().setFragmentResultListener("getEditDetail", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(String requestKey , Bundle result){
                Movie movie = result.getParcelable("movie");
                moviePosition = result.getInt("position");
                binding.etTitle.setText(movie.getTitle());
                binding.etSynopsis.setText(movie.getSynopsis());

                byte[] decodedString = Base64.decode(movie.getPoster().getBytes(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                binding.ivPoster.setImageBitmap(decodedByte);

                binding.etStatus.setText(movie.getStatus());
                binding.etReview.setText(movie.getReview());
            }
        });
        binding.btnSave.setOnClickListener(this);
        return view;
    }
    public static EditDetailFragment newInstance(){
        EditDetailFragment fragment = new EditDetailFragment();
        return fragment;
    }

    @Override
    public void onClick(View view) {
        String title = binding.etTitle.getText().toString();
        String synopsis = binding.etSynopsis.getText().toString();

        binding.ivPoster.buildDrawingCache();
        Bitmap bm = binding.ivPoster.getDrawingCache();
        String poster = getEncoded64ImageStringFromBitmap(bm);

        String status = binding.etStatus.getText().toString();
        String review = binding.etReview.getText().toString();
        Movie editedMovie = new Movie(title, synopsis, poster, status, review);
        presenter.saveDetails(editedMovie, moviePosition);
    }
    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return imgString;
    }
}