package com.dannextech.apps.daktari_online.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dannextech.apps.daktari_online.R;
import com.dannextech.apps.daktari_online.model.DiagnosisModel;

import java.util.ArrayList;

public class DiagnosisAdapter extends RecyclerView.Adapter<DiagnosisAdapter.ViewHolder> {
    private DiagnosisModel[] diagResults;

    public DiagnosisAdapter(ArrayList<DiagnosisModel> diagResults) {
        this.diagResults = new DiagnosisModel[diagResults.size()];
        this.diagResults = diagResults.toArray(this.diagResults);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.diagnosis_detail,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvName.setText(diagResults[i].getName());
        viewHolder.tvAccuracy.setText(diagResults[i].getAccuracy());
        viewHolder.tvNumber.setText(""+(i+1));
    }

    @Override
    public int getItemCount() {
        return diagResults.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvAccuracy,tvNumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNumber = itemView.findViewById(R.id.tvDiagResultNumber);
            tvAccuracy = itemView.findViewById(R.id.tvDiagResultAccuracy);
            tvName = itemView.findViewById(R.id.tvDiagResultName);
        }
    }
}
