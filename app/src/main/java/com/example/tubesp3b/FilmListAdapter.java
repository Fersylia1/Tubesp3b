package com.example.tubesp3b;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.tubesp3b.databinding.ItemListFilmBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FilmListAdapter extends BaseAdapter implements Filterable {
    private ItemListFilmBinding binding;
    private ArrayList<Movie> listItems;
    private ArrayList<Movie> listItemsFull;
    private FragmentListFilm fragment;
    private ListFilmPresenter presenter;

    public FilmListAdapter(FragmentListFilm fragment, ListFilmPresenter presenter){
        this.fragment=fragment;
        this.listItems=new ArrayList<Movie>();
        this.listItemsFull=new ArrayList<Movie>();
        this.presenter = presenter;
    }

    public void initList(List<Movie> items){
        for(Movie item : items){
            this.addLine(item);
        }
    }
    public void addLine(Movie newItem){
        this.listItems.add(newItem);
        this.listItemsFull.add(newItem);
        this.notifyDataSetChanged();
    }

    public void sortAlphabetically(String order){
        if(order == "ascending"){
            Collections.sort(listItems, new Comparator<Movie>() {
                @Override
                public int compare(Movie m1, Movie m2) {
                    return m1.getTitle().compareTo(m2.getTitle());
                }
            });
        }
        else{
            Collections.sort(listItems, new Comparator<Movie>() {
                @Override
                public int compare(Movie m1, Movie m2) {
                    return m2.getTitle().compareTo(m1.getTitle());
                }
            });

        }
        this.listItemsFull=new ArrayList<>(listItems);
        notifyDataSetChanged();
    }

    public void clearData(){
        listItems.clear();
        listItemsFull.clear();
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

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Movie> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listItemsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Movie item : listItemsFull) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listItems.clear();
            listItems.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    private class ViewHolder implements View.OnClickListener{
        protected ItemListFilmBinding binding;
        private Movie item;
        private ListFilmPresenter presenter;

        public ViewHolder(ItemListFilmBinding binding, ListFilmPresenter presenter){
            this.binding = binding;
            this.binding.rlRoot.setOnClickListener(this);
            this.binding.ibDelete.setOnClickListener(this);
            this.presenter = presenter;
        }

        public void updateView(Movie item){
            this.item = item;
            this.binding.tvTitle.setText(item.getTitle());
        }

        @Override
        public void onClick(View view) {
            if(view == binding.rlRoot){
                int position = listItems.indexOf(item);
                presenter.getDetailPage(this.item, position);
            }
            else{
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setTitle("Delete entry");
                alert.setMessage("Are you sure you want to delete?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.deleteList(item);
                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        }
    }
}