package com.dannextech.apps.daktari_online.adapter;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.dannextech.apps.daktari_online.R;
import com.dannextech.apps.daktari_online.model.PharmacyModel;

import java.math.BigDecimal;
import java.util.ArrayList;

public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyAdapter.ViewHolder>{

    private PharmacyModel[] pharmModel;
    private Location myloc;

    public PharmacyAdapter(ArrayList<PharmacyModel> ambList, Location myLoc) {
        this.pharmModel = new PharmacyModel[ambList.size()];
        this.pharmModel = ambList.toArray(this.pharmModel);
        this.myloc = myLoc;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pharmacy_list_details,viewGroup,false);
        return new PharmacyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Location loc2 = new Location("");
        loc2.setLatitude(Double.parseDouble(pharmModel[i].getLattitude()));
        loc2.setLongitude(Double.parseDouble(pharmModel[i].getLongitude()));

        float distanceInMeters = myloc.distanceTo(loc2)/1000;

        viewHolder.tvName.setText(pharmModel[i].getName());
        viewHolder.tvTown.setText(pharmModel[i].getTown());
        viewHolder.tvDistance.setText(BigDecimal.valueOf(distanceInMeters).setScale(2, BigDecimal.ROUND_HALF_UP).toString()+" KM");
    }

    @Override
    public int getItemCount() {
        return pharmModel.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvTown, tvDistance;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvPharmListName);
            tvTown = itemView.findViewById(R.id.tvPharmListTown);
            tvDistance = itemView.findViewById(R.id.tvPharmListDistance);
        }
    }
}
