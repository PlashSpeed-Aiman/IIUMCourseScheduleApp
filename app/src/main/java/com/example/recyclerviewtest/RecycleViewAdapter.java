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

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private final List<Course> mGroupBind;
    private Context mContext;
    private int mPos;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView subjectCode, subjectTitle, lecturerName, lecturerName2,venueVal,sectionVal;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectCode = itemView.findViewById(R.id.textView2);
            subjectTitle = itemView.findViewById(R.id.textView);
            lecturerName = itemView.findViewById(R.id.textView3);
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
                intent.putExtra("key", (Serializable) mGroupBind);
                v.getContext().startActivity(intent);
            }
            if (v == subjectCode){
                Toast.makeText(mContext,"test", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public RecycleViewAdapter(Context context, List<Course> GroupBind) {
//        this.mContext = context;
        this.mGroupBind = GroupBind;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_adapter, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.subjectTitle
                .setText((mGroupBind.get(position).getName_val()));
        holder.subjectCode
                .setText(mGroupBind.get(position).getCode_val());
        holder.lecturerName2
                .setText(mGroupBind.get(position).getLectures().toString());
        holder.lecturerName
                .setText("Placeholder");
        holder.venueVal
                .setText(mGroupBind.get(position).getVenue().toString());
        holder.sectionVal
                .setText(mGroupBind.get(position).getSection_val());
        getPos(holder);
    }

    @Override
    public int getItemCount() {
        return mGroupBind.size();

    }
    public void getPos(ViewHolder viewHolder){
        mPos =  viewHolder.getAdapterPosition();
    }


}