package com.example.tubesp3b;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;

public class PenyimpananNilai {
    protected SharedPreferences sharedPreferences;
    protected  final static String NAMA_SHARED_PREF ="sp_nilai";
    protected final static String KEY_JUDUL ="JUDUL";
    protected  final static  String KEY_SINOPSIS = "SINOPSIS";

    public PenyimpananNilai(Context context){
        this.sharedPreferences = context.getSharedPreferences(NAMA_SHARED_PREF,0);
    }

    public void saveJudul(String judul){
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(KEY_JUDUL,judul);
        editor.commit();
    }
    public void saveSinopsis(String sinopsis){
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(KEY_SINOPSIS,sinopsis);
        editor.commit();
    }
    public String getJudul(){
        return sharedPreferences.getString(KEY_JUDUL,"");
    }
    public  String getSinopsis(){
        return sharedPreferences.getString(KEY_SINOPSIS,"");
    }

}
