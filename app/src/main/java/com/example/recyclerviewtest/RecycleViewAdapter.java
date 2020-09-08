package com.example.recyclerviewtest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private ArrayList<String> mSubjectCode;
    private ArrayList<String> mSubjectTitle;
    private final List<List<String>> mGroupBind;
    private Context mContext;
    private int mPos;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
            cardView.setOnClickListener(this);
            subjectCode.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(v == cardView){
                Intent intent = new Intent(v.getContext(),MainActivity2.class);
                intent.putExtra("pos",ViewHolder.super.getAdapterPosition());
                intent.putExtra("key", (Serializable) mGroupBind);
                mContext.startActivity(intent);
            }
            if (v == subjectCode){
                Toast.makeText(mContext,"tits", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public RecycleViewAdapter(ArrayList<String> subjectCode, ArrayList<String> subjectTitle, Context context, List<List<String>> GroupBind) {
        this.mSubjectCode = subjectCode;
        this.mSubjectTitle = subjectTitle;
        this.mContext = context;
        this.mGroupBind = GroupBind;
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
        holder.subjectCode
                .setText(mGroupBind.get(position).get(2));
        holder.lecturerName2
                .setText(mGroupBind.get(position).get(4));
        holder.lecturerName
                .setText(mGroupBind.get(position).get(3));

//****
        if (holder.lecturerName2.getText().equals(holder.lecturerName.getText()))
        {
            holder.lecturerName2
                    .setVisibility(View.INVISIBLE);

        }else{
            holder.lecturerName2
                    .setText(mGroupBind.get(position).get(4));
        }
        //****

        holder.venueVal
                .setText(mGroupBind.get(position).get(6));
        holder.sectionVal
                .setText(mGroupBind.get(position).get(1));
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