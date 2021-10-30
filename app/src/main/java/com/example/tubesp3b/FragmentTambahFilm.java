package com.example.tubesp3b;

import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.tubesp3b.databinding.TambahFilmBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    private ActivityResultLauncher<Intent> intentLauncher;
    private PenyimpananNilai pencatat_judul , pencatat_sinopsis;
    private final static String FILE_NAME = "Test.txt";

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container,
                             Bundle savedInstanceState){
        this.binding = TambahFilmBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        this.btn_tambahFilm = this.binding.btnTambahfilm;
        this.et_judulFilm = this.binding.etJudul;
        this.et_SinopsisFilm = this.binding.etSinopsis;
        this.poster = this.binding.gambar1;

        this.btn_tambahFilm.setOnClickListener(this);
        binding.uploadPoster.setOnClickListener(this);

        this.pencatat_judul = new PenyimpananNilai(this.binding.etJudul.getContext());
        this.pencatat_sinopsis = new PenyimpananNilai(this.binding.etSinopsis.getContext());

        this.intentLauncher = this.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent reply = result.getData();
                            Uri selectedImage = reply.getData();
                            try (ParcelFileDescriptor pfd = getContext().getContentResolver().openFileDescriptor(selectedImage, "r")) {
                                if (pfd != null) {
                                    Bitmap bitmap = BitmapFactory.decodeFileDescriptor(pfd.getFileDescriptor());
                                    binding.gambar1.setImageBitmap(bitmap);
                                }
                            } catch (IOException ex) {

                            }
                        }
                    }
                });

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
        else{
            Intent chooseImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intentLauncher.launch(chooseImageIntent);
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
