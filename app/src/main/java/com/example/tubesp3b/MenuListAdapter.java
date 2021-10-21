package com.example.tubesp3b;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.tubesp3b.databinding.ItemListMenuBinding;

import java.util.ArrayList;

public class MenuListAdapter extends BaseAdapter {
    private ItemListMenuBinding binding;
    private ArrayList<String> listItems;
    private Activity activity;

    public MenuListAdapter(Activity activity){
        this.activity=activity;
        this.listItems=new ArrayList<String>();
    }

    public void initList(String[] items){
        for(String item : items){
            this.add(item);
        }
    }
    public void add(String newItem){
        this.listItems.add(newItem);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public String getItem(int i) {
        return listItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if(view == null){
            this.binding = ItemListMenuBinding.inflate(this.activity.getLayoutInflater());
            view = this.binding.getRoot();
            viewHolder = new ViewHolder(binding);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.updateView(this.getItem(i));

        return view;
    }

    private class ViewHolder{
        protected ItemListMenuBinding binding;
        private String item;

        public ViewHolder(ItemListMenuBinding binding){
            this.binding = binding;
        }

        public void updateView(String item){
            this.item = item;
            this.binding.tvItem.setText(item);
        }
    }
}