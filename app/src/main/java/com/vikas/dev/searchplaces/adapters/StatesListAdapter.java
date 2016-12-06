package com.vikas.dev.searchplaces.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vikas.dev.searchplaces.R;
import com.vikas.dev.searchplaces.model.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikasmalhotra on 12/6/16.
 */
public class StatesListAdapter extends RecyclerView.Adapter<StatesListAdapter.MyViewHolder> {

    private List<State> statesList;

    public StatesListAdapter() {
        statesList = new ArrayList<>();
    }

    public void setData(List<State> statesList) {
        this.statesList = statesList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        State state = statesList.get(position);

        if (TextUtils.isEmpty(state.getCountry())) {
            holder.country.setText(R.string.na);
        } else {
            holder.country.setText(state.getCountry());
        }

        if (TextUtils.isEmpty(state.getName())) {
            holder.name.setText(R.string.na);
        } else {
            holder.name.setText(state.getName());
        }

        if (TextUtils.isEmpty(state.getAbbr())) {
            holder.abbr.setText(R.string.na);
        } else {
            holder.abbr.setText(state.getAbbr());
        }

        if (TextUtils.isEmpty(state.getArea())) {
            holder.area.setText(R.string.na);
        } else {
            holder.area.setText(state.getArea());
        }

        if (TextUtils.isEmpty(state.getLargestCity())) {
            holder.largest_city.setText(R.string.na);
        } else {
            holder.largest_city.setText(state.getLargestCity());
        }

        if (TextUtils.isEmpty(state.getCapital())) {
            holder.capital.setText(R.string.na);
        } else {
            holder.capital.setText(state.getCapital());
        }
    }

    @Override
    public int getItemCount() {
        return statesList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView country, name, abbr, area, largest_city, capital;

        public MyViewHolder(View view) {
            super(view);
            country = (TextView) view.findViewById(R.id.country);
            name = (TextView) view.findViewById(R.id.name);
            abbr = (TextView) view.findViewById(R.id.abbr);
            area = (TextView) view.findViewById(R.id.area);
            largest_city = (TextView) view.findViewById(R.id.largest_city);
            capital = (TextView) view.findViewById(R.id.capital);
        }
    }

}