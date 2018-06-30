package com.example.lenovo.xiottest;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.xiottest.Data.Chat;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Chat> Messagedetails;

    public MyAdapter(List<Chat> Messagedetails) {
        this.Messagedetails = Messagedetails;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.bind(Messagedetails.get(position));
    }

    @Override
    public int getItemCount() {
        return Messagedetails.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View item;
        TextView Message_TextView;

        public ViewHolder(View view) {
            super(view);
            item = view;
            Message_TextView = view.findViewById(R.id.single_message_text);
        }


        public void bind(Chat chat) {
            Message_TextView.setText(chat.getMessage());
        }

    }
}




