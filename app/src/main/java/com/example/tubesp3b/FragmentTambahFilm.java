package com.example.tubesp3b;

import android.content.Context;
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
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.tubesp3b.databinding.TambahFilmBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FragmentTambahFilm extends Fragment implements View.OnClickListener{
    TambahFilmBinding binding;
    private Button btn_tambahFilm;
    private EditText et_judulFilm;
    private EditText et_SinopsisFilm;
    private ImageView poster;
    private PenyimpananNilai pencatat_judul , pencatat_sinopsis;
    private final static String FILE_NAME = "Test.txt";

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

           // this.storeInternal(this.binding.etJudul.getEditableText().toString() + this.binding.etSinopsis.getEditableText().toString());

        String judul = this.et_judulFilm.getText().toString();
        String sinopsis = this.et_SinopsisFilm.getText().toString();
        String txt = judul + "" +sinopsis+ "";
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = getActivity().openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fileOutputStream.write(txt.getBytes());
            //this.et_judulFilm.getText().clear();
            //this.et_SinopsisFilm.getText().clear();
           // Toast.makeText(this,"Save to "+ getFilesDir()+"/"+FILE_NAME,Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if(fileOutputStream!=null){
                try {
                    {
                        fileOutputStream.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        }
    }
    @Override
    public void onPause(){
        super.onPause();
        this.pencatat_judul.saveJudul(et_judulFilm.getText().toString());
        this.pencatat_sinopsis.saveSinopsis(et_SinopsisFilm.getText().toString());
    }
    @Override
    public void onResume(){
        super.onResume();
        this.et_judulFilm.setText(this.pencatat_judul.getJudul());
        this.et_SinopsisFilm.setText(this.pencatat_sinopsis.getSinopsis());
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
