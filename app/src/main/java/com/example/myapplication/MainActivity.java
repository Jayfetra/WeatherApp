package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androdocs.httprequest.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myapplication.MainActivity_message";
    private RecyclerView.Adapter CommentAdapter;

    //private static final String TAG = "Jay CommentGetData Activity_Comment";

    private RecyclerView cities;
    private EditText mComment_editTxt;
    private TextView mSeeAllComments;
//    private Button mAddComment_btn;
    private ImageButton mAddComment_btn;
    private static final String TAG = "MainActivity";
    TextView addressTxt, updated_atTxt, statusTxt, tempTxt, temp_minTxt, temp_maxTxt, sunriseTxt,
            sunsetTxt, windTxt, pressureTxt, humidityTxt;
    public Spinner Loc_spinner;
    String API = "6702f772e0054039461094c2022765b5";
    //String CITY = "dhaka,bd";
    Button btn_call_api,btn_tocomment,btn_toAddComment;

    public static weatherTask loadTextDataTask;
    Spinner spin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI(findViewById(R.id.parent));

        // Initiate Weather Data
        Loc_spinner = (Spinner) findViewById(R.id.list_locations);
        //String[] data = {"Mangga Besar, ID", "Pecenongan, ID", "Tanah Abang, ID"};
        String[] data = getResources().getStringArray(R.array.place_array);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item_selected,data);
//        final ArrayAdapter<CharSequence> adapter = ArrayAdapter(this,
//                R.layout.spinner_item_selected,
//                R.array.place_array);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Loc_spinner.setAdapter(adapter);

        //final String[] countryCodes = getResources().getStringArray(R.array.planets_array);


        //spin.setAdapter(countryCodeAdapter);
        OnSelectedSpinner_location();

        //btn_call_api = (Button) findViewById(R.id.btn_call_api);
        //updated_atTxt = findViewById(R.id.updated_at);
        statusTxt = findViewById(R.id.status);
        tempTxt = findViewById(R.id.temp);
//        new weatherTask().execute();
//        GetComment();
//        final Context context = getApplicationContext();
//        if (isNetworkAvailable(context)) {
//            new weatherTask().execute();
//            GetComment();
//        } else {
//            Toast.makeText(getApplicationContext(),"Please turn on your internet connection"
//                    ,Toast.LENGTH_LONG).show();
//        }


        Loc_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if (isNetworkAvailable(getApplicationContext())) {
                    new weatherTask().execute();
                    GetComment();
                } else {
                    Toast.makeText(getApplicationContext(),"Please turn on your internet connection"
                            ,Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
                if (isNetworkAvailable(getApplicationContext())) {
                    new weatherTask().execute();
                    GetComment();
                } else {
                    Toast.makeText(getApplicationContext(),"Please turn on your internet connection"
                            ,Toast.LENGTH_LONG).show();
                }
            }
        });
        // Add Comment
        mAddComment_btn = (ImageButton) findViewById(R.id.ImageBtn_AddComment);
        mAddComment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddComment();
            }
        });

        mSeeAllComments = (TextView) findViewById(R.id.SeeAllComments);

        mSeeAllComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ditText editText = (EditText) findViewById(R.id.editText);
                    Intent intent = new Intent(MainActivity.this, comment.class);
//                    EditText editText = (EditText) findViewById(R.id.editText);
                    String place = Loc_spinner.getSelectedItem().toString();
                    //String message = editText.getText().toString();
                    intent.putExtra(EXTRA_MESSAGE, place);
                    startActivity(intent);

            }
        });

    }

    public void OnSelectedSpinner_location() {
        String CITY = Loc_spinner.getSelectedItem().toString();
        String Test = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&APPID=" + API);

    }

    private void AddComment(){
        EditText mCommentText;
        mCommentText = findViewById(R.id.comment_editTxt);
        obj_comment comment = new obj_comment();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String currentDateAndTime = sdf.format(new Date());
        String place = Loc_spinner.getSelectedItem().toString();
        comment.setName("Guest");
        comment.setPlace(place);
        comment.setComment(mCommentText.getText().toString());
        comment.setCreateddate(currentDateAndTime.toString());
        new FirebaseDatabaseHelper().addComments(comment, new FirebaseDatabaseHelper.Datastatus() {
            @Override
            public void DataIsLoaded(List<obj_comment> comments, List<String> keys) {
            }

            @Override
            public void DataIsInserted() {
                Toast.makeText(MainActivity.this, "Your comment has been save", Toast.LENGTH_LONG).show();
            }
            @Override
            public void DataIsUpdated() {
            }
            @Override
            public void DataIsDeleted() {
            }
        });
        mCommentText.getText().clear();
    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    private void GetComment(){
        // Get Comment
        int Datalimit=3;
        //String SearchEqual="jayson";
        String SearchOrderby="place";
        String SearchEqual = Loc_spinner.getSelectedItem().toString();


        new FirebaseDatabaseHelper().readComments(new FirebaseDatabaseHelper.Datastatus() {
            private RecyclerView cities;
            @Override
            public void DataIsLoaded(List<obj_comment> comments, List<String> keys) {
                ArrayList<obj_comment> list = new ArrayList<>();
                //list.add(new obj_comment("Cinque Terre", "The ", "Test Image 1"));
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
                this.cities = (RecyclerView) findViewById(R.id.recyclerview_comments);RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
                this.cities.setLayoutManager(mLayoutManager);
                CommentAdapter = new MyCommentAdapter(cities);
                this.cities.setAdapter(CommentAdapter);
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
            },
                Datalimit, SearchEqual,SearchOrderby);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(MainActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    class weatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /* Showing the ProgressBar, Making the main design GONE */
            findViewById(R.id.loader).setVisibility(View.VISIBLE);
            findViewById(R.id.mainContainer).setVisibility(View.GONE);
            findViewById(R.id.errorText).setVisibility(View.GONE);
        }
        protected String doInBackground(String... args) {
            String CITY = Loc_spinner.getSelectedItem().toString();
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&APPID=" + API);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject sys = jsonObj.getJSONObject("sys");
                JSONObject wind = jsonObj.getJSONObject("wind");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                Long updatedAt = jsonObj.getLong("dt");
                String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                String temp = main.getString("temp") + "°C";
                String tempMin = "Min Temp: " + main.getString("temp_min") + "°C";
                String tempMax = "Max Temp: " + main.getString("temp_max") + "°C";
                String pressure = main.getString("pressure");
                String humidity = main.getString("humidity");

                Long sunrise = sys.getLong("sunrise");
                Long sunset = sys.getLong("sunset");
                String windSpeed = wind.getString("speed");
                String weatherDescription = weather.getString("description");

                //String address = jsonObj.getString("name") + ", " + sys.getString("country");


                /* Populating extracted data into our views */
               // addressTxt.setText(address);
//                updated_atTxt.setText(updatedAtText);
                statusTxt.setText(weatherDescription.toUpperCase());
                tempTxt.setText(temp);
//                temp_minTxt.setText(tempMin);
//                temp_maxTxt.setText(tempMax);
//                sunriseTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunrise * 1000)));
//                sunsetTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunset * 1000)));
//                windTxt.setText(windSpeed);
//                pressureTxt.setText(pressure);
//                humidityTxt.setText(humidity);

                /* Views populated, Hiding the loader, Showing the main design */
                findViewById(R.id.loader).setVisibility(View.GONE);
                findViewById(R.id.mainContainer).setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                findViewById(R.id.loader).setVisibility(View.GONE);
                findViewById(R.id.errorText).setVisibility(View.VISIBLE);
            }
        }


    }
    }
