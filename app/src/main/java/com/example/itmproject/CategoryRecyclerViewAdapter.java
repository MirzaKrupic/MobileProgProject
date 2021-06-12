package com.example.itmproject;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.itmproject.Entities.Category;

import java.util.List;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {

    private final List<Category> _categoryList;

    public CategoryRecyclerViewAdapter(List<Category> items) {
        _categoryList = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = _categoryList.get(position);
        holder.checkBox.setText(_categoryList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return _categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final CheckBox checkBox;
        public Category mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            checkBox = (CheckBox) view.findViewById(R.id.check_box);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + checkBox.getText() + "'";
        }
    }
}