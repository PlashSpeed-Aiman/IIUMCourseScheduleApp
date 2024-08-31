package com.example.recyclerviewtest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewtest.Model.Course;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private final List<Course> courseList;
    private Context mContext;
    private int mPos;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView subjectCode, subjectTitle, lecturerName2,venueVal,sectionVal;
        CardView cardView;
        Context _context;
        public ViewHolder(@NonNull View itemView,Context context) {
            super(itemView);
            _context = context;
            subjectCode = itemView.findViewById(R.id.textView2);
            subjectTitle = itemView.findViewById(R.id.textView);
            lecturerName2 = itemView.findViewById(R.id.textView4);
            venueVal = itemView.findViewById(R.id.venue_val);
            sectionVal = itemView.findViewById(R.id.section_value);
            cardView = itemView.findViewById(R.id.cardView);
            cardView.setOnClickListener(this);
            subjectCode.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(v == cardView){
                Intent intent = new Intent(v.getContext(),MainActivity2.class);
                intent.putExtra("pos",ViewHolder.super.getAdapterPosition());
                intent.putExtra("key", (Serializable) courseList);
                v.getContext().startActivity(intent);
            }
            if (v == subjectCode){
                Toast.makeText(mContext,"test", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public RecycleViewAdapter(Context context, List<Course> courses) {
        this.mContext = context;
        this.courseList = courses;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_adapter, parent, false);
        ViewHolder holder = new ViewHolder(view,mContext);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Course course = courseList.get(position);
        holder.subjectTitle
                .setText(course.getName_val());
        holder.subjectCode
                .setText(course.getCode_val());
        String lecturers = course.getLectures().stream()
                .filter(lecturer -> !lecturer.trim().isEmpty()) // Remove empty strings
                .map(lecturer -> lecturer + "\n") // Append new line after each lecturer
                .collect(Collectors.joining());

        holder.lecturerName2.setText(lecturers);


        String venue = course.getVenue().stream()
                        .filter(ven -> !ven.trim().isEmpty())
                                .map(ven -> ven + "\n")
                                        .collect(Collectors.joining());
        holder.venueVal
                .setText(venue);
        holder.sectionVal
                .setText(course.getSection_val());
    }

    @Override
    public int getItemCount() {
        return courseList.size();

    }



}