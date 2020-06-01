package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class comment extends AppCompatActivity {

    private RecyclerView JayRecyclerView;
    private RecyclerView.Adapter adapter;

    private static final String TAG = "Jay CommentGetData Activity_Comment";
    private RecyclerView cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        JayRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_comments);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        int Datalimit=100;
        String SearchOrderby="place";
        String SearchEqual=message;

        ArrayList<City> list = new ArrayList<>();
        new FirebaseDatabaseHelper().readComments(new FirebaseDatabaseHelper.Datastatus() {
            private RecyclerView cities;

            @Override
            public void DataIsLoaded(List<obj_comment> comments, List<String> keys) {
                ArrayList<obj_comment> list = new ArrayList<>();
                //list.add(new City("Cinque Terre", "The ", "Test Image 1"));
                for (int i = 0; i < comments.size(); i++) {
                    list.add(new obj_comment(comments.get(i).getName()
                            , comments.get(i).getComment()
                            , comments.get(i).getCreateddate()
                            , comments.get(i).getPlace()));
                    Log.i(TAG, comments.get(i).getName());
                    Log.i(TAG, comments.get(i).getCreateddate());
                    Log.i(TAG, comments.get(i).getComment());
                }
                ArrayList<obj_comment> cities = list;
                this.cities = (RecyclerView) findViewById(R.id.recyclerview_comments);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(comment.this);
                this.cities.setLayoutManager(mLayoutManager);
                adapter = new MyCommentAdapter(cities);
                this.cities.setAdapter(adapter);
            }

            @Override
            public void DataIsDeleted() {

            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

        }
                ,Datalimit
                , SearchEqual
                ,SearchOrderby);

    }

//    private ArrayList<City> initCities() {
//        ArrayList<City> list = new ArrayList<>();
//
//        list.add(new City("Cinque Terre", "The coastline, the five villages in Italy.", "Test Image 1"));
//        list.add(new City("Paris", "Paris is the capital city of France", "Test Image 2"));
//        list.add(new City("Rio de Janeiro", "Rio has been one of Brazil's most popular destinations.", "Test Image 3"));
//        list.add(new City("Sydney", "Sydney is the state capital of New South Wales.", "Test Image 4"));
//        return list;
//    }

//    private ArrayList<City> initCities2() {
//        final ArrayList<City> list = new ArrayList<>();
//
//        new FirebaseDatabaseHelper().readComments(new FirebaseDatabaseHelper.Datastatus() {
//            @Override
//            public void DataIsLoaded(List<obj_comment> comments, List<String> keys) {
//                //list.add(new City("Cinque Terre", "The ", "Test Image 1"));
//                for (int i = 0; i < comments.size(); i++) {
//                    list.add(new City(comments.get(i).getName()
//                            , comments.get(i).getComment()
//                            , comments.get(i).getCreateddate()));
//                    Log.i(TAG, comments.get(i).getName());
//                    Log.i(TAG, comments.get(i).getCreateddate());
//                    Log.i(TAG, comments.get(i).getComment());
//                }
//
//            }
//
//            @Override
//            public void DataIsDeleted() {
//
//            }
//
//            @Override
//            public void DataIsInserted() {
//
//            }
//
//            @Override
//            public void DataIsUpdated() {
//
//            }
//
//        });
//
//        return list;
//    }

}
