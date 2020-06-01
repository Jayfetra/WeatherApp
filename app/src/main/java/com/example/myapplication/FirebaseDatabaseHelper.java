package com.example.myapplication;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceComments;
    private List<obj_comment> comments = new ArrayList<>();
    private static final String TAG = "Jay DatabaseHelper";
    private Query query;

    public interface Datastatus{
        void DataIsLoaded(List<obj_comment> comments, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();

    }
    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceComments = mDatabase.getReference("comment");
 //       query = mReferenceComments.orderByChild("name").equalTo("jayson").limitToLast(1);

//        mReferenceComments.orderByChild("name").equalTo("jayson") = mDatabase.getReference("comment");;

    }

    public void readComments(final Datastatus datastatus,
                             int Datalimit,
                             String SearchEqual,
                             String SearchOrderby)
    {
//        query = mReferenceComments.orderByChild("name").equalTo("jayson").limitToLast(1);
        query = mReferenceComments.orderByChild(SearchOrderby).equalTo(SearchEqual).limitToLast(Datalimit);
        query.addValueEventListener(new ValueEventListener() {

//            mReferenceComments.addValueEventListener(new ValueEventListener() {
            @Override
            //every time data is updated, data will be updated in the application
            // D==Update is asynchronous. therefore we must create public interface

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                comments.clear();

                List<String> keys = new ArrayList<>();
                Log.i(TAG, "Read Data from databasetes1");
                for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());

                    Log.i(TAG, "Read Data from database2");

                    // get data value
                    obj_comment comment = keyNode.getValue(obj_comment.class);


                    comments.add(comment);

                }
                datastatus.DataIsLoaded(comments,keys);
                Log.i(TAG, "Read Data from database3");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i(TAG, "Cancel Read Data from database");
            }
        });
    }
    public void addComments(obj_comment comment, final Datastatus datastatus){
        String key = mReferenceComments.push().getKey();
        mReferenceComments.child(key).setValue(comment)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        datastatus.DataIsInserted();
                    }
                });
    }
}
