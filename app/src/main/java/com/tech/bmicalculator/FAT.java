package com.tech.bmicalculator;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.DecimalFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class FAT extends Fragment {
    EditText weight1,wrist,waist,hip,forearm;
    RadioButton female,male;
    TextView fat1;
    Button calculate;
    CardView details;


    public FAT() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_f_a_t, container, false);

        weight1 = view.findViewById(R.id.weight1);
        wrist = view.findViewById(R.id.wrist);
        waist = view.findViewById(R.id.waist);
        hip = view.findViewById(R.id.hips);
        forearm = view.findViewById(R.id.forearm);
        male = view.findViewById(R.id.male);
        female = view.findViewById(R.id.female);
        fat1 = view.findViewById(R.id.fat1);
        calculate = view.findViewById(R.id.calculate1);
        details = view.findViewById(R.id.details1);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    DecimalFormat df = new DecimalFormat("#.##");
                    double lbm = 0,fw = 0, fp = 0;
                    Float wei = Float.parseFloat(weight1.getText().toString());
                    Float wr = Float.parseFloat(wrist.getText().toString());
                    Float wa = Float.parseFloat(waist.getText().toString());
                    Float hi = Float.parseFloat(hip.getText().toString());
                    Float fo = Float.parseFloat(forearm.getText().toString());
                    if (male.isChecked()){
                        lbm = (wei * 1.082) + 94.42 - (wa * 4.15);
                    }

                    if (female.isChecked()){
                        lbm = (wei * 0.732) + 8.987 + wr / 3.140  - wa * 0.157 - hi * 0.249 + fo * 0.434;
                    }
                    details.setVisibility(View.VISIBLE);
                    fw = wei - lbm;
                    fp = fw * 100 / wei;
                    fat1.setText(df.format(fp) + " %");
                }catch (Exception e){e.getMessage();}


            }
        });

        return view;
    }
}
