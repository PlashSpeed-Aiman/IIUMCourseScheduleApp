package com.example.recyclerviewtest;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.recyclerviewtest.Model.Course;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    ProgressDialog mProgressDialog;
    private List<Course> courses = new ArrayList<>();
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    Button btn;
    Spinner kulliyyahSelect;
    Spinner pageSelect;
    Spinner semesterSelect;
    private int kulliyyahSelectPosition;
    private int pageSelectPosition;
    private int semesterSelectPosition;
    WebscrapperAsyncTask webscrapperAsyncTask;
    Button buttonNext, buttonPrev, buttonReset;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);
        kulliyyahSelect = findViewById(R.id.spinner5);
        pageSelect = findViewById(R.id.spinner);
        semesterSelect = findViewById(R.id.spinner2);
        mDrawerList = findViewById(R.id.navList);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        buttonNext = findViewById(R.id.buttonNext);
        buttonPrev = findViewById(R.id.buttonPrev);
        buttonReset = findViewById(R.id.buttonReset);
        linearLayout = findViewById(R.id.buttonLayout);
        webscrapperAsyncTask = new WebscrapperAsyncTask();
        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        kulliyyahSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!(position == kulliyyahSelectPosition)) {
                    btn.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        semesterSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!(position == semesterSelectPosition))
                    btn.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
//                syncTask();
                performWebScraping();

                linearLayout.setVisibility(View.VISIBLE);
                btn.setVisibility(View.INVISIBLE);
            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageSelectPosition++;
                pageSelect.setSelection(pageSelectPosition);
//                syncTask();
                performWebScraping();

            }
        });
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(pageSelectPosition == 0)) {
                    pageSelectPosition--;
                    pageSelect.setSelection(pageSelectPosition);
//                    syncTask();
                performWebScraping();
                }
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(pageSelectPosition == 0)) {
                    pageSelectPosition = 0;
                    pageSelect.setSelection(pageSelectPosition);
//                    syncTask();
                performWebScraping();
                }

            }
        });

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

    public static class startInfoActivity extends MainActivity {
        public void startInfoAct (View v){
            Intent intent = new Intent(v.getContext(),MainActivity2.class);
            startActivity(intent);
        }

        @Override
        public void onPointerCaptureChanged(boolean hasCapture) {

        }
    }
    private void initRecyclerViewAdapter() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);

        RecycleViewAdapter adapter = new RecycleViewAdapter(this, courses);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void performWebScraping() {
        // Show the progress dialog
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Connecting to IIUM Course Schedule");
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();

        // Execute background task using ExecutorService
        executorService.execute(() -> {
            try {
                courses.clear();
                courses = WebScraper.URLManipFunc(kulliyyahSelectPosition, pageSelectPosition, semesterSelectPosition);
                Log.d("TAG", "Web scraping completed");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Post UI update on the main thread
            mainHandler.post(() -> {
                initRecyclerViewAdapter();
                mProgressDialog.dismiss();
            });
        });
    }
    public class WebscrapperAsyncTask extends AsyncTask<Void, Void, Void> {

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
                courses.clear();
                courses = WebScraper.URLManipFunc(kulliyyahSelectPosition, pageSelectPosition, semesterSelectPosition);
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

    private void addDrawerItems() {
        String[] osArray = {"Schedule", "About", "Donations"};
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 && mDrawerList.getSelectedItemPosition()!= 0){

                }

                if (position == 1 && mDrawerList.getSelectedItemPosition() != 1) {
                    Toast.makeText(MainActivity.this, "Developed & Designed By: Aiman Rahim (GitHub: PlashSpeed-Aiman) & Shahrul Afiq", Toast.LENGTH_SHORT).show();

                }
                if (position == 2) {
                    Toast.makeText(MainActivity.this, "LOL", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}






