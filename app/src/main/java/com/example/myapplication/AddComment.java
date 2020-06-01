package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddComment extends AppCompatActivity {

    private EditText mComment_editTxt;
    private Button mAddComment_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        mComment_editTxt = (EditText) findViewById(R.id.comment_editTxt);
        mAddComment_btn = (Button) findViewById(R.id.btn_AddComment);

        mAddComment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obj_comment comment = new obj_comment();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                String currentDateandTime = sdf.format(new Date());

                comment.setComment(mComment_editTxt.getText().toString());
                comment.setName("GuestJay".toString());
                comment.setCreateddate(currentDateandTime.toString());
                new FirebaseDatabaseHelper().addComments(comment, new FirebaseDatabaseHelper.Datastatus() {
                    @Override
                    public void DataIsLoaded(List<obj_comment> comments, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(AddComment.this, "Your comment has been save", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });


    }
}
