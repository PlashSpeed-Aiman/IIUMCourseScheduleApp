package com.example.recyclerviewtest;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private ArrayList<String> mSubjectCode;
    private ArrayList<String> mSubjectTitle;
    private List<List<String>> mGroupBind;
    private Context mContext;


    public RecycleViewAdapter(ArrayList<String> subjectCode, ArrayList<String> subjectTitle, Context context, List<List<String>> GroupBind) {
        mSubjectCode = subjectCode;
        mSubjectTitle = subjectTitle;
        mContext = context;
        mGroupBind = GroupBind;
    }
    public void ClearData(){

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
                .setText((mGroupBind.get(position).get(0)));
        holder.subjectCode.setText(mGroupBind.get(position).get(2));
//        holder.subjectCode
//                .setText(mSubjectCode
//                        .get(position));
        holder.lecturerName2
                .setText(mGroupBind.get(position).get(4));
        holder.lecturerName
                .setText(mGroupBind.get(position).get(3));

        if (holder.lecturerName2 == holder.lecturerName) {

            holder.lecturerName2
                    .setVisibility(View.INVISIBLE);

        }else{
            holder.lecturerName2
                    .setText(mGroupBind.get(position).get(4));
        }
        holder.venueVal
                .setText(mGroupBind.get(position).get(6));
        holder.sectionVal
                .setText(mGroupBind.get(position).get(1));
        holder.cardView
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, mGroupBind.get(position).toString(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    public int getItemCount() {
        return mGroupBind.size();

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView subjectCode, subjectTitle, lecturerName, lecturerName2,venueVal,sectionVal;
        CardView cardView;
        LinearLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectCode = itemView.findViewById(R.id.textView2);
            subjectTitle = itemView.findViewById(R.id.textView);
            lecturerName = itemView.findViewById(R.id.textView3);
            lecturerName2 = itemView.findViewById(R.id.textView4);
            venueVal = itemView.findViewById(R.id.venue_val);
            sectionVal = itemView.findViewById(R.id.section_value);
            cardView = itemView.findViewById(R.id.cardView);

        }

    }
}