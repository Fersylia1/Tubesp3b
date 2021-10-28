package com.example.tubesp3b;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.tubesp3b.databinding.FragmentLeftBinding;

public class LeftFragment extends Fragment{
    private MenuListAdapter adapter;
    private FragmentLeftBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentLeftBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ListView listView = binding.lvMenu;
        this.adapter = new MenuListAdapter(this.getActivity());
        String[] items = {"Home", "Daftar", "History", "Pengaturan", "Exit"};
        this.adapter.initList(items);
        listView.setAdapter(this.adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position, long id) {
                String item = (String) adapter.getItemAtPosition(position);
                Bundle result = new Bundle();
                if(item == "Home"){
                    result.putInt("page", 1);
                    getParentFragmentManager().setFragmentResult("changePage", result);
                }
                else if(item == "Daftar"){
                    result.putInt("page", 2);
                    getParentFragmentManager().setFragmentResult("changePage", result);
                }
                else if(item == "History"){
                    result.putInt("page", 3);
                    getParentFragmentManager().setFragmentResult("changePage", result);
                }
                else if(item == "Pengaturan"){
                    result.putInt("page", 4);
                    getParentFragmentManager().setFragmentResult("changePage", result);
                }
                else{
                    result.putInt("page", 5);
                    getParentFragmentManager().setFragmentResult("changePage", result);
                }
            }
        });

        return view;
    }
}