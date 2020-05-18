package com.example.recyclerviewtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    private ArrayList<String> mSubjectCode = new ArrayList<>();
    private ArrayList<String> mSubjectTitle = new ArrayList<>();
    List<List<String>> mGroupBind = new ArrayList<>();
    Dialog mDialog;
    Button btn, btn2;
    Spinner kulliyyahSelect;
    Spinner pageSelect;
    Spinner semesterSelect;
    int kulliyyahSelectPosition;
    int pageSelectPosition;
    int semesterSelectPosition;
    WebscrapperAsyncTask webscrapperAsyncTask = new WebscrapperAsyncTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDialog = new Dialog(this);
        btn = findViewById(R.id.button);
//        btn2 = findViewById(R.id.button2);//
        kulliyyahSelect = findViewById(R.id.spinner5);
        pageSelect = findViewById(R.id.spinner);
        semesterSelect = findViewById(R.id.spinner2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kulliyyahSelectPosition =
                        kulliyyahSelect
                                .getSelectedItemPosition();
                pageSelectPosition =
                        pageSelect
                                .getSelectedItemPosition();
                semesterSelectPosition =
                        semesterSelect
                            .getSelectedItemPosition();
                syncTask();
            }
        });
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                webscrapperAsyncTask = new WebscrapperAsyncTask();
//                initRecyclerViewAdapter();
//            }
//        });
//        initRecyclerViewAdapter();
//        subjectProcess();
    }

    //    private void subjectProcess(){
//        mSubjectCode.add("MECH1301");
//        mSubjectTitle.add("Statics");
//
//        mSubjectCode.add("MECH13022");
//        mSubjectTitle.add("Dynamics");
//
//        mSubjectCode.add("MATH1310");
//        mSubjectTitle.add("Engineering Mathematics 1");
//
//        mSubjectCode.add("MATH1320");
//        mSubjectTitle.add("Engineering Mathematics 2");
//        initRecyclerViewAdapter();
//    }
    private void initRecyclerViewAdapter() {
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecycleViewAdapter adapter = new RecycleViewAdapter(mSubjectCode, mSubjectTitle, this, mGroupBind, mDialog);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                recyclerView.getRecycledViewPool().clear();
//            }
//        });

    }

    private void syncTask() {
        try {
            if (webscrapperAsyncTask.getStatus() != AsyncTask.Status.RUNNING) {   // check if asyncTasks is running
                webscrapperAsyncTask.cancel(true); // asyncTasks not running => cancel it
                webscrapperAsyncTask = new WebscrapperAsyncTask(); // reset task
                webscrapperAsyncTask.execute(); // execute new task (the same task)
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MainActivity_TSK", "Error: " + e.toString());
        }

    }

    private class WebscrapperAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Connecting to IIUM Course Schedule");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                mGroupBind.clear();
                mGroupBind = WebScraper.URLManipFunc(kulliyyahSelectPosition,pageSelectPosition,semesterSelectPosition);
                Log.d("TAG", "String.valueOf(GroupBind)");

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            // Set title into TextView
            initRecyclerViewAdapter();
//            initRecyclerViewAdapter();
            mProgressDialog.dismiss();
        }
    }
}





