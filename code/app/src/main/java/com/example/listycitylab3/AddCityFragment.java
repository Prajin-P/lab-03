package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.io.Serializable;

public class AddCityFragment extends DialogFragment {

    interface AddCityDialogListener {
        void addCity(City city);
    }

    private AddCityDialogListener listener;

    // creates a new field to hold the city being edited.
    private City edit;
    // creates a new empty fragment constructor.
    public AddCityFragment() { }

    //store the edit City in the empty Fragment as an instance variable.
    public AddCityFragment(City city) {
        this.edit = city;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");

        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).
                    inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // if edit is not null, this code fills up the fields
        if (edit != null) {
            editCityName.setText(edit.getName());
            editProvinceName.setText(edit.getProvince());
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add a city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();

                    // if edit is not null, this sets the new information
                    if (edit != null) {
                        edit.setName(cityName);
                        edit.setProvince(provinceName);

                        // reloads the list
                        ((MainActivity) getActivity()).reloadList();

                    }

                    else {
                        listener.addCity(new City(cityName, provinceName));
                    }
                })
                .create();
    }
}
