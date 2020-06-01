package com.dannextech.apps.daktari_online.adapter;

import android.support.annotation.NonNull;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.dannextech.apps.daktari_online.R;
import com.dannextech.apps.daktari_online.model.SymptomModel;

import java.util.ArrayList;

public class SymptomsAdapter extends RecyclerView.Adapter<SymptomsAdapter.ViewHolder> {
    private SymptomModel[] symptoms;
    private ChipGroup cgSymptoms;

    String sym = "ss";

    private ColorGenerator generator = ColorGenerator.MATERIAL;

    public SymptomsAdapter(ArrayList<SymptomModel> symptoms, ChipGroup cgSymptoms) {
        this.symptoms = new SymptomModel[symptoms.size()];
        this.symptoms = symptoms.toArray(this.symptoms);
        this.cgSymptoms = cgSymptoms;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.symptom_details,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.symName.setText(symptoms[position].getName());
        //Get the first letter of list item
        String letter = String.valueOf(symptoms[position].getName().charAt(0));
        //Create a new TextDrawable for our image's background
        final TextDrawable drawable = TextDrawable.builder().buildRound(letter,generator.getRandomColor());
        holder.letter.setImageDrawable(drawable);



        final int[] clicks = {0};
        holder.sym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicks[0]++;

                if (clicks[0] % 2 == 0){
                    holder.letter.setImageDrawable(drawable);
                    final Chip chip = new Chip(cgSymptoms.getContext());
                    chip.setText(symptoms[position].getName());
                    chip.setCloseIconResource(R.drawable.ic_close_black_10dp);
                    chip.setCloseIconVisible(true);
                    cgSymptoms.removeView(chip);
                }
                else {
                    holder.letter.setImageResource(R.drawable.ok_filled);
                    final Chip chip = new Chip(cgSymptoms.getContext());
                    chip.setText(symptoms[position].getName());
                    chip.setCloseIconResource(R.drawable.ic_close_black_10dp);
                    chip.setCloseIconVisible(true);
                    chip.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cgSymptoms.removeView(chip);
                        }
                    });
                    //chip.setCloseIconEnabled(true);
                    //chip.setCloseIconResource(R.drawable.your_icon);
                    //chip.setChipIconResource(R.drawable.your_icon);
                    //chip.setChipBackgroundColorResource(R.color.red);
                    //chip.setTextAppearanceResource(R.style.ChipTextStyle);
                    //chip.setElevation(15);
                    setSym(symptoms[position].getId());
                    cgSymptoms.addView(chip);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return symptoms.length;
    }

    public String getSym() {
        return sym;
    }

    public void setSym (String symptom){
        sym += ","+symptom;
        Log.e("DannexDaniels", "setSym: "+sym );
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView letter;
        private TextView symName;
        private CardView sym;
        public ViewHolder(View itemView) {
            super(itemView);
            letter = itemView.findViewById(R.id.ivSymLetter);
            symName = itemView.findViewById(R.id.tvSymptomTitle);
            sym = itemView.findViewById(R.id.cvSym);
        }
    }
}
