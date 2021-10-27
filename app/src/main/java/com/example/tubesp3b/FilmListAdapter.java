package com.example.tubesp3b;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.tubesp3b.databinding.ItemListFilmBinding;

import java.util.ArrayList;
import java.util.List;

public class FilmListAdapter extends BaseAdapter {
    private ItemListFilmBinding binding;
    private ArrayList<Movie> listItems;
    private FragmentListFilm fragment;
    private ListFilmPresenter presenter;

    public FilmListAdapter(FragmentListFilm fragment, ListFilmPresenter presenter){
        this.fragment=fragment;
        this.listItems=new ArrayList<Movie>();
        this.presenter = presenter;
    }

    public void initList(List<Movie> items){
        for(Movie item : items){
            this.addLine(item);
        }
    }
    public void addLine(Movie newItem){
        this.listItems.add(newItem);
        this.notifyDataSetChanged();
    }

    public void clearData(){
        listItems.clear();
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Movie getItem(int i) {
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
            this.binding = ItemListFilmBinding.inflate(this.fragment.getLayoutInflater());
            view = this.binding.getRoot();
            viewHolder = new ViewHolder(binding, presenter);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.updateView(this.getItem(i));

        return view;
    }

    private class ViewHolder implements View.OnClickListener{
        protected ItemListFilmBinding binding;
        private Movie item;
        private ListFilmPresenter presenter;

        public ViewHolder(ItemListFilmBinding binding, ListFilmPresenter presenter){
            this.binding = binding;
            this.binding.llRoot.setOnClickListener(this);
            this.presenter = presenter;
        }

        public void updateView(Movie item){
            this.item = item;
            this.binding.tvTitle.setText(item.getTitle());
        }

        @Override
        public void onClick(View view) {
            int position = listItems.indexOf(item);
            presenter.getDetailPage(this.item, position);
        }
    }
}