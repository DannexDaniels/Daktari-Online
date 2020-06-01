package com.dannextech.apps.daktari_online.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dannextech.apps.daktari_online.R;
import com.dannextech.apps.daktari_online.model.AmbulanceModel;
import com.dannextech.apps.daktari_online.model.DiagnosisModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.math.BigDecimal;
import java.util.ArrayList;

public class AmbulanceAdapter extends RecyclerView.Adapter<AmbulanceAdapter.ViewHolder>  {
    private AmbulanceModel[] ambModel;
    private Location myloc;

    public AmbulanceAdapter(ArrayList<AmbulanceModel> ambModel, Location myLoc) {
        this.ambModel = new AmbulanceModel[ambModel.size()];
        this.ambModel = ambModel.toArray(this.ambModel);
        this.myloc = myLoc;
    }


    @NonNull
    @Override
    public AmbulanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ambulance_list_details,viewGroup,false);
        return new AmbulanceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AmbulanceAdapter.ViewHolder viewHolder, int i) {
        Location loc2 = new Location("");
        loc2.setLatitude(Double.parseDouble(ambModel[i].getLattitude()));
        loc2.setLongitude(Double.parseDouble(ambModel[i].getLongitude()));

        float distanceInMeters = myloc.distanceTo(loc2)/1000;

        viewHolder.tvName.setText(ambModel[i].getOrganization());
        viewHolder.tvTown.setText(ambModel[i].getTown());
        viewHolder.tvDistance.setText(BigDecimal.valueOf(distanceInMeters).setScale(2, BigDecimal.ROUND_HALF_UP).toString()+" KM");
    }


    @Override
    public int getItemCount() {
        return ambModel.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvTown, tvDistance;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvAmbListName);
            tvTown = itemView.findViewById(R.id.tvambListTown);
            tvDistance = itemView.findViewById(R.id.tvambListDistance);
        }
    }
}
