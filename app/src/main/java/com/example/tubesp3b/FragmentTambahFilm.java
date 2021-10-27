package com.example.tubesp3b;

import android.content.SharedPreferences;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.tubesp3b.databinding.TambahFilmBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class FragmentTambahFilm extends Fragment implements View.OnClickListener{
    TambahFilmBinding binding;
    private Button btn_tambahFilm;
    private EditText et_judulFilm;
    private EditText et_SinopsisFilm;
    private ImageView poster;
    private ActivityResultLauncher<Intent> intentLauncher;
    private PenyimpananNilai pencatat;

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

        this.pencatat = new PenyimpananNilai(this.binding.etJudul.getContext());
        this.pencatat = new PenyimpananNilai(this.binding.etSinopsis.getContext());

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

           // this.storeInternal(this.binding.etJudul.getEditableText().toString() + this.binding.etSinopsis.getEditableText().toString());

        }
        else{
            Intent chooseImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intentLauncher.launch(chooseImageIntent);
        }
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
