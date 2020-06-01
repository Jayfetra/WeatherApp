package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyCommentAdapter extends RecyclerView.Adapter<MyCommentAdapter.ViewHolder> {
    private ArrayList<obj_comment> cities;
    private Context mcontext;
    private MyCommentAdapter mcityadapter;
   // private List<obj_comment> mCommentList;
    //private List<String> mKeys;
    private static final String TAG = "Jay Populate to Recycler";
    public void SetConfig(RecyclerView recyclerView, Context context, ArrayList<obj_comment> cities)
    {
        mcontext=context;
        mcityadapter= new MyCommentAdapter(cities);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mcityadapter);
    }

    public MyCommentAdapter(ArrayList<obj_comment> cities) {
        this.cities = cities;
        //this.mKeys = mKeys;
        Log.i(TAG, "On CommentAdapter");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        obj_comment comment = cities.get(position);

        holder.name.setText(comment.getName());
        holder.comment.setText(comment.getComment());
        holder.CreatedDate.setText(comment.getCreateddate());
   //     holder.Place.setText(comment.getPlace());
        
//        Picasso.get().load(comment.getImageURL()).into(holder.CreatedDate);
    }

    @Override
    public int getItemCount() {
        if (cities != null) {
            return cities.size();
        }
        else {
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView name;
        public final TextView comment;
        public final TextView CreatedDate;
//        public final TextView Place;

        //public final ImageView CreatedDate;

        public ViewHolder(View view) {
            super(view);
            this.view = view;

            name = view.findViewById(R.id.name_textview);
            comment = view.findViewById(R.id.comment_textview);
            CreatedDate = view.findViewById(R.id.createddate_textview);
        }
    }
}
