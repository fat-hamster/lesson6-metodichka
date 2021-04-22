package com.dmgpersonal.lesson6;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class CitiesFragment extends Fragment {

    private Boolean isLandscape;
    public static final String CURRENT_CITY = "CurrentCity";
    private City currentCity;

    @Override
    // При создании фрагмента укажем его макет
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cities, container, false);
    }

    @Override
    // вызывается после создания макета фрагмента, здесь мы проинициализируем список
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    // создаём список городов на экране из массива в ресурсах
    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        String[] cities = getResources().getStringArray(R.array.cities);
        // В этом цикле создаём элемент TextView,
        // заполняем его значениями,
        // и добавляем на экран.
        // Кроме того, создаём обработку касания на элемент
        for (int i = 0; i < cities.length; i++) {
            String city = cities[i];
            TextView textView = new TextView(getContext());
            textView.setText(city);
            textView.setTextSize(30);
            layoutView.addView(textView);
            final int fi = i;
            textView.setOnClickListener(v -> {
                currentCity = new City(fi, getResources().getStringArray(R.array.cities)[fi]);
                showCoatOfArms(currentCity);
            });
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_CITY, currentCity);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if(savedInstanceState != null) {
            currentCity = savedInstanceState.getParcelable(CURRENT_CITY);
        } else {
            currentCity = new City(0, getResources().getStringArray(R.array.cities)[0]);
        }

        if(isLandscape) {
            showLandCoatOfArms(currentCity);
        }
    }

    private void showCoatOfArms(City currentCity) {
        if(isLandscape) {
            showLandCoatOfArms(currentCity);
        } else {
            showPortCoatOfArms(currentCity);
        }
    }

    private void showLandCoatOfArms(City currentCity) {
        CoatOfArmsFragment detail = CoatOfArmsFragment.newInstance(currentCity);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.coat_of_arms, detail);  // замена фрагмента
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private void showPortCoatOfArms(City currentCity) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), CoatOfArmsActivity.class);
        intent.putExtra(CoatOfArmsFragment.ARG_CITY, currentCity);
        startActivity(intent);
    }
}