package com.resq.resqserviceapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    String[] id,date,type,model;

    public RecyclerAdapter(String[] idValues, String[] dateValues, String[] typeValues, String[] modelValues){
        id = idValues;
        date = dateValues;
        type = typeValues;
        model = modelValues;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.idText.setText(id[position]);
        holder.dateText.setText(date[position]);
        holder.modelText.setText(type[position]+" "+model[position]);
    }

    @Override
    public int getItemCount() {
        return id.length;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView idText, dateText, modelText;

        public RecyclerViewHolder(View view) {
            super(view);
            idText = (TextView) view.findViewById(R.id.rowLayoutComplaintID);
            dateText = (TextView) view.findViewById(R.id.rowLayoutDate);
            modelText = (TextView) view.findViewById(R.id.rowLayoutModel);

        }
    }
}
