package com.dmgpersonal.lesson6;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CoatOfArmsFragment extends Fragment {

    public static final String ARG_CITY = "city";
    private City city;

    public static CoatOfArmsFragment newInstance(City city) {
        CoatOfArmsFragment f = new CoatOfArmsFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_CITY, city);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            city = getArguments().getParcelable(ARG_CITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coat_of_arms, container, false);

        AppCompatImageView imageCoatOfArms = view.findViewById(R.id.coat_of_arms);
        TypedArray images = getResources().obtainTypedArray(R.array.coat_of_arms_imgs);
        imageCoatOfArms.setImageResource(images.getResourceId(city.getImageIndex(), -1));

        TextView cityNameView = view.findViewById(R.id.textView);
        cityNameView.setText(city.getCityName());

        return view;
    }
}