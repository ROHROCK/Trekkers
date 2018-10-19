package com.example.rohit.gmapslolipop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class TrekAdapter extends RecyclerView.Adapter<TrekAdapter.TrekViewHolder> {
    private Context mCtx;
    private List<Trek> trekList;

    public TrekAdapter(Context mCtx, List<Trek> trekList) {
        this.mCtx = mCtx;
        this.trekList = trekList;
    }

    @Override
    public TrekViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.trek_list, null);
        return new TrekViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrekViewHolder holder, int position) {
        Trek trek = trekList.get(position);
        holder.name.setText(trek.getTname());
        holder.duration.setText(trek.getTduration());
        holder.city.setText(trek.getTcity());
        holder.recommendation.setText(trek.getTrecommendation());
    }

    @Override
    public int getItemCount() {
        return trekList.size();
    }

    class TrekViewHolder extends RecyclerView.ViewHolder {
        TextView name, duration, city, recommendation;

        public TrekViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            duration = view.findViewById(R.id.duration);
            city = view.findViewById(R.id.city);
            recommendation = view.findViewById(R.id.recommendation);
        }
    }
}
