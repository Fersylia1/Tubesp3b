package com.example.tubesp3b;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.tubesp3b.databinding.TambahFilmBinding;

import java.io.ByteArrayOutputStream;

public class FragmentTambahFilm extends Fragment implements View.OnClickListener{
    TambahFilmBinding binding;
    private Button btn_tambahFilm;
    private EditText et_judulFilm;
    private EditText et_SinopsisFilm;
    private ImageView poster;
    private PenyimpananNilai pencatat_judul ,  pencatat_sinopsis;

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container,
                             Bundle savedInstanceState){
        this.binding = TambahFilmBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        this.btn_tambahFilm = this.binding.btnTambahfilm;
        this.btn_tambahFilm.setOnClickListener(this);

        this.pencatat_judul = new PenyimpananNilai(this.binding.etJudul.getContext());
        this.pencatat_sinopsis = new PenyimpananNilai(this.binding.etSinopsis.getContext());

        return view;
    }
    public void onClick(View view){
        if(view == this.btn_tambahFilm){
            Bundle bundle = new Bundle();
            bundle.putString("judul",et_judulFilm.getText().toString());
            bundle.putString("sinopsis",et_SinopsisFilm.getText().toString());

            poster.buildDrawingCache();
            Bitmap bmap = poster.getDrawingCache();
            String strPoster = getEncoded64ImageStringFromBitmap(bmap);
            bundle.putString("poster",strPoster);

            getParentFragmentManager().setFragmentResult("addMovie",bundle);

            int opt =1; // permanen(1)/temporary(2)
           //this.storeInternal(this.binding.etJudul.getEditableText().toString() + this.binding.etSinopsis.getEditableText().toString());
           // this.binding.tvTambahFilm.setText(this.loadInternal("test.txt",opt));
        }
    }
    @Override
    public  void onPause(){
        super.onPause();
        this.pencatat_judul.saveJudul(this.binding.etJudul.getText().toString());
        this.pencatat_sinopsis.saveJudul(this.binding.etSinopsis.getText().toString());
    }
    @Override
    public void onResume(){
        super.onResume();
        this.binding.etJudul.setText(this.pencatat_judul.getJudul());
        this.binding.etSinopsis.setText(this.pencatat_sinopsis.getSinopsis());
    }

    public static FragmentTambahFilm newInstance(){
        FragmentTambahFilm fragment = new FragmentTambahFilm();
        return fragment;
    }
    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return imgString;
    }

}
