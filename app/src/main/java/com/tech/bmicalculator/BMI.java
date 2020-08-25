package com.tech.bmicalculator;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.text.DecimalFormat;

import static androidx.core.content.ContextCompat.getSystemService;


/**
 * A simple {@link Fragment} subclass.
 */
public class BMI extends Fragment {

    String h,w;
    String[] hei,wei;
    Spinner sp_height,sp_weight;
    EditText age,height,weight;
    RadioGroup gander;
    RadioButton female,male;
    TextView result,description,fat,ideal;
    Button calculate;
    CardView details;
    DecimalFormat df;
    Float h1,w1;
    double r = 0, iw =0, ft =0;
    private AdView mAdView;
    public BMI() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_b_m_i, container, false);

        AdView mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        sp_height = view.findViewById(R.id.sp_height);
        sp_weight = view.findViewById(R.id.sp_weight);
        hei = getResources().getStringArray(R.array.h);
        wei = getResources().getStringArray(R.array.w);
        details = view.findViewById(R.id.details);
        age = view.findViewById(R.id.age);
        height = view.findViewById(R.id.height);
        weight = view.findViewById(R.id.weight);
        gander = view.findViewById(R.id.gander);
        female = view.findViewById(R.id.female);
        male = view.findViewById(R.id.male);
        fat = view.findViewById(R.id.fat);
        ideal = view.findViewById(R.id.ideal);
        result = view.findViewById(R.id.result);
        description = view.findViewById(R.id.description);
        calculate = view.findViewById(R.id.calculate);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, hei);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_height.setAdapter(arrayAdapter);

        ArrayAdapter<String> array = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, wei);
        array.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_weight.setAdapter(array);



        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    df = new DecimalFormat("#.#");
                    h = sp_height.getSelectedItem().toString();
                    w = sp_weight.getSelectedItem().toString();
                    h1 = Float.parseFloat(height.getText().toString());
                    w1 = Float.parseFloat(weight.getText().toString());
                    double fe= h1/3.2808;
                    double in = h1*0.0254;
                    if (w.equals("Kilograms")){
                        if (h.equals("Meters")) {
                            r = (w1 / (h1 * h1) / 100) * 100;
                        }
                        if (h.equals("Centimeters")) {
                            r = (w1 / ((h1 * h1) / 100) * 100);
                        }
                        if (h.equals("Inches")) {
                            r = (w1 / ((in * in)));
                        }
                        if (h.equals("Feet")) {
                            r = (w1 / ((fe * fe)));
                        }
                        go(r);
                        ideal_weight();
                        fat();
                        return;
                    }
                    if (w.equals("Pounds")){
                        if (h.equals("Meters")) {
                            r = ((w1 / 2.2046) / (h1 * h1) * 100);
                        }
                        if (h.equals("Centimeters")) {
                            r = ((w1 / 2.2046) / ((h1 * h1) / 100) * 100);
                        }
                        if (h.equals("Inches")) {
                            r = ((w1 / 2.2046) / ((in * in)));
                        }
                        if (h.equals("Feet")) {
                            r = ((w1 / 2.2046) / ((fe * fe)));
                        }
                        go(r);
                        ideal_weight();
                        fat();
                        return;
                    }



                }catch (Exception e){e.getMessage();}


            }
        });


        return view;

    }

    private void results() {
        details.setVisibility(View.VISIBLE);
        fat.setTextColor(Color.parseColor("#F48C8A"));
        ideal.setTextColor(Color.parseColor("#46BB73"));
        hideSoftKeyboard(getActivity());
    }
    public void  go(double res){

        String under = getResources().getString(R.string.under);
        String normal = getResources().getString(R.string.normal);
        String over = getResources().getString(R.string.over);

        if (res == 0){
            Toast.makeText(getActivity(),"This BMI doesn't look right. Make sure the height and weight you entered are correct",Toast.LENGTH_LONG).show();
            hideSoftKeyboard(getActivity());
        }
        if(res < 16.0 || res>=16.0 && res<18.5){
            result.setText("" + df.format(res));
            description.setText(""+under);
            description.setTextColor(Color.parseColor("#4671C6"));
            result.setTextColor(Color.parseColor("#4671C6"));
            results();
        }
        if(res>=18.5 && res<25.0){
            result.setText("" + df.format(res));
            description.setText(""+normal);
            description.setTextColor(Color.parseColor("#46BB73"));
            result.setTextColor(Color.parseColor("#46BB73"));
            results();
        }
        if(res>=25.0 && res <= 50.0){
            result.setText("" + df.format(res));
            description.setText(""+over);
            description.setTextColor(Color.parseColor("#F48C8A"));
            result.setTextColor(Color.parseColor("#F48C8A"));
            results();
        }
        if (res > 50.0){
            Toast.makeText(getActivity(),"This BMI doesn't look right. Make sure the height and weight you entered are correct",Toast.LENGTH_LONG).show();
            hideSoftKeyboard(getActivity());
        }

    }
    public void ideal_weight(){
        double fe= h1/3.2808;
        double in = h1*0.0254;
        if (male.isChecked()){
            if (h.equals("Meters")){
                iw = 22*(h1 * h1);
            }
            if (h.equals("Centimeters")){
                iw = 22*((h1 * h1)/100)/100;
            }
            if (h.equals("Inches")){
                iw = 22*(in * in);
            }
            if (h.equals("Feet")){
                iw = 22*(fe * fe);
            }
        }
        if (female.isChecked()){
            if (h.equals("Meters")){
                iw = 22*(h1 * h1-0.1);
            }
            if (h.equals("Centimeters")){
                iw = 22*((h1 * h1-0.1)/100)/100;
            }
            if (h.equals("Inches")){
                iw = 22*(in * in-0.1);
            }
            if (h.equals("Feet")){
                iw = 22*(fe * fe-0.1);
            }
        }
        if (w.equals("Kilograms")){
            ideal.setText(df.format(iw)+" Kg");
        }
        if (w.equals("Pounds")){
            iw = iw / 2.2046;
            ideal.setText(df.format(iw)+" lbs");
        }
    }

    public void fat(){
        Integer ag = Integer.parseInt(age.getText().toString());
        if (male.isChecked()){
            ft = (1.20*r)+(0.23*ag)-16.2;
        }
        if (female.isChecked()){
            ft = (1.20*r)+(0.23*ag)-5.4;
        }
        fat.setText(df.format(ft)+" %");
    }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
