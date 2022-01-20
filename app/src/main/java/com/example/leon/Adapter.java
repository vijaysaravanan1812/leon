package com.example.leon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends  RecyclerView.Adapter<Adapter.viewHolder>{

    public ArrayList<commands> Commands = new ArrayList<>();
    public Context context;

    //constructor
    public Adapter(Context context){
        this.context = context;
    }
    public void setCommands(ArrayList<commands> Commands) {
        this.Commands = Commands;
        notifyDataSetChanged(); //Number of Data changing every time
    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content , parent , false);
        // context  = parent
        // viewGroup is parent of Relative layout, constraint layout, and linear layout
        // viewGroup is to group the different view inside it

        viewHolder holder = new viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull  viewHolder holder, int position) {
        holder.commands.setText(Commands.get(position).getTxtCommand());
        holder.time.setText(Commands.get(position).getTxtTime());
    }

    @Override
    public int getItemCount() {
        return Commands.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        private TextView commands;
        private TextView time;
        private CardView parent;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            commands = itemView.findViewById(R.id.txtcommand);
            time = itemView.findViewById(R.id.txttime);
            parent = itemView.findViewById(R.id.parent);
        }



    }



}
