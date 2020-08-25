package com.tech.bmicalculator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class About extends Fragment {

    TextView github,rate,share,more;

    public About() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        github = view.findViewById(R.id.github);
        rate = view.findViewById(R.id.rate);
        share = view.findViewById(R.id.share);
        more = view.findViewById(R.id.more);

        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);

        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.setData(Uri.parse("https://github.com/Badech"));
                startActivity(intent);
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {;
                intent.setData(Uri.parse("https://play.google.com/store/apps"));
                startActivity(intent);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String share_body = "Share with your friends";
                String share_subject = "Cool App";
                intent.putExtra(Intent.EXTRA_TEXT,share_body);
                intent.putExtra(Intent.EXTRA_SUBJECT,share_subject);
                startActivity(Intent.createChooser(intent,"Share via"));
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setData(Uri.parse("https://play.google.com/store/apps"));
                startActivity(intent);
            }
        });

        return view;
    }
}
