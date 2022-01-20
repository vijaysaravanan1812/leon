package com.example.leon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.fragment.app.Fragment;

public class HistoryFragment extends Fragment {

    Database database;
    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /**
         *  Inflate the XML layout file for this fragment
         */
        View myView = inflater.inflate(R.layout.fragment_history, container, false);

        //list view
        ListView command_list = myView.findViewById(R.id.commands_list);
        view(command_list);
        //Button to clear and view the data
        Button clear_button = myView.findViewById(R.id.clear_button);
        clear_button.setOnClickListener(v -> {
            database.delete();
            view(command_list);
        });

        return myView;
    }


    public void view(ListView command_list)
    {
        database = new Database(getActivity());
        ArrayAdapter<commands> commandAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, database.getEveryone());
        command_list.setAdapter(commandAdapter);
    }
}