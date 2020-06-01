package com.example.myapplication;

import android.content.Context;

public class RecyclerView_Config {
    private Context mContext;
    //private CommentAdapter mCommentAdapter;
    private static final String TAG = "Jay Populate to Recycler";

//    public void setConfig(RecyclerView recyclerView, Context context, List<obj_comment> comments, List<String> keys) {
//        Log.i(TAG, "On Set Config");
//        mContext = context;
//        mCommentAdapter = new CommentAdapter(comments, keys);
//        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        recyclerView.setAdapter(mCommentAdapter);
//    }
//
//
//    //  Start Create Adapter
//    public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
//        private List<obj_comment> mCommentList;
//        private List<String> mKeys;
//        private Context mContext;
//
//        // Adapter
//        public CommentAdapter(List<obj_comment> mCommentList, List<String> mKeys) {
//            this.mCommentList = mCommentList;
//            this.mKeys = mKeys;
//            Log.i(TAG, "On CommentAdapter");
//        }
//
//        @Override
//        public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
//                                                            int viewType) {
//            Log.i(TAG, "On MyViewHolder onCreateViewHolder");
//            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_item, parent, false);
//            return new ViewHolder(v);
//
//        }
//
//        // BindViewHolder
//        // Replace the contents of a view (invoked by the layout manager)
//        @Override
//        public void onBindViewHolder(ViewHolder holder, int position) {
//            // - get element from your dataset at this position
//            // - replace the contents of the view with that element
//            obj_comment comment = mCommentList.get(position);
//            holder.mName.setText(comment.getName());
//            holder.mCreatedDate.setText(comment.getCreateddate());
//            holder.mComment.setText(comment.getComment());
//            //this.key = key;
////            Log.i(TAG, "On MyViewHolder bind");
////            holder.bind(mCommentList.get(position), mKeys.get(position));
//            Log.i(TAG, "On onBindViewHolder");
//        }
//
//        //Get Item Count
//        // Return the size of your dataset (invoked by the layout manager)
//        @Override
//        public int getItemCount() {
//            Log.i(TAG, "On getItemCount");
//            return mCommentList.size();
//        }
//
//        //ViewHolder
////        public static class MyViewHolder extends RecyclerView.ViewHolder {
//        public  class ViewHolder extends RecyclerView.ViewHolder {
//            private View view;
//            private TextView mName;
//            private TextView mComment;
//            private TextView mCreatedDate;
//            private String key;
//
//            public ViewHolder(view view) {
//                super(view);
//                this.view = view;
////                super(LayoutInflater.from(mContext).
////                        inflate(R.layout.comment_list_item, parent, false));
//
//                mName = (TextView) itemView.findViewById(R.id.name_textview);
//                mComment = (TextView) itemView.findViewById(R.id.comment_textview);
//                mCreatedDate = (TextView) itemView.findViewById(R.id.createddate_textview);
//                Log.i(TAG, "On MyViewHolder ViewGroup");
//            }
//        }
//    }
}