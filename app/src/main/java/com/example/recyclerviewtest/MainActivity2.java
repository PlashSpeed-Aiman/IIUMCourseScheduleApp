package com.example.recyclerviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.recyclerviewtest.Model.Course;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    TextView subjectCode;
    TextView subjectTitle;
    TextView sectionValue;
    TextView creditHour;
    TextView data_table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        int pos = intent.getIntExtra("pos",0);
        List<Course> subjectInfo = (List<Course>) intent.getSerializableExtra("key");
        subjectCode = findViewById(R.id.subjectCodeAct2);
        subjectTitle = findViewById(R.id.subjectTitleAct2);
        sectionValue = findViewById(R.id.textView15);
        creditHour = findViewById(R.id.textView13);
        data_table = findViewById(R.id.data_table);
        StringBuilder str = new StringBuilder();

        for(String i : subjectInfo.get(pos).getDay_val()){
            str.append(i + "\n\n");
        }
        for(String i : subjectInfo.get(pos).getTime_val()){
            str.append(i + "\n\n");
        }

        subjectCode.setText(subjectInfo.get(pos).getCode_val());
        sectionValue.setText("SEC "+ subjectInfo.get(pos).getSection_val());
        subjectTitle.setText(subjectInfo.get(pos).getName_val());
        creditHour.setText("Credit Hour: " + subjectInfo.get(pos).getCredit_val());
        data_table.setText(str);


    }
}