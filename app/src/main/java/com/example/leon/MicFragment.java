package com.example.leon;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class MicFragment extends Fragment
{

    /**
     * Recycler view
     */
    private RecyclerView commands_recyclerView;
    private ArrayList<commands> Commands = new ArrayList<>();
    Date currentTime;
    String strDate; // strDate to get string form of time
    DatabaseReference databaseReference ;
    FirebaseDatabase firebaseDatabase;


    /**
     * Objects for fetching Resource
     */
    private ImageView iv_mic;
    private TextView tv_Speech_to_text;

    /**
     *  Objects to get text form of Speech
     */
    private ArrayList<String> result;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;

    /**
     * Object to connect Database
     */
    Database database;
    boolean success;

    /**
     * firebase objects
//     */
//    FirebaseDatabase RootNode;
//    DatabaseReference reference;


    public  MicFragment()
    {
        //Empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /**
         * Inflate the layout XML file for this fragment
         */
        View myView = inflater.inflate(R.layout.fragment_mic, container, false);

        /**
         * Fetch the id for Recycler view
         */
        commands_recyclerView = myView.findViewById(R.id.commands);

        /**
         * Fetch the id for Resource
         */
        iv_mic = myView.findViewById(R.id.iv_mic);// Mic picture
        tv_Speech_to_text = myView.findViewById(R.id.tv_speech_to_text);// Text above mic

        /**
         * Speech to text
         * When mic clicked by user this function invoked
        */
        iv_mic = (ImageView) myView.findViewById(R.id.iv_mic);
        iv_mic.setOnClickListener(this::onClick);



        /**
         * Setting Adapter to recycler view
         */
        Adapter adapter = new Adapter(getActivity());
        adapter.setCommands(Commands);
        commands_recyclerView.setAdapter(adapter);
        commands_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return myView;
    }

    public void onClick(View v) {
        /**
         *  getting voice from user when he touch mic
         *
         */
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(getActivity(), " " + e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }

    @Override
     /**
     * Extracting text from speech
     */
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT)
        {
            if (resultCode == RESULT_OK && data != null)
            {
                result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                tv_Speech_to_text.setText(Objects.requireNonNull(result).get(0));

                try {

                    /**
                     *  Get the time when user click mic
                     *  Add text form of speech to recycler view
                     */
                    //getting time
                    currentTime = Calendar.getInstance().getTime();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                           strDate = dateFormat.format(currentTime);

                    /**
                     * inserting the data into adapter through Commands(list)
                     */
                    Commands.add(new commands(  strDate, tv_Speech_to_text.getText().toString()));

                    /**
                     * invoking database
                     */
                    database = new Database(getActivity());



                    success =  database.addOne(tv_Speech_to_text.getText().toString(), strDate);
                    firebase();



//
//                    RootNode = FirebaseDatabase.getInstance();
//


                }
                catch (Exception e)
                {
                    tv_Speech_to_text.setText(e.getMessage());
                }

                /**
                 *  Rewrite the tv_Speech_to_text default text
                 */
                //tv_Speech_to_text.setText(getString(R.string.tap_mic_and_speak));
            }
        }
    }
    private void firebase()
    {
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getInstance("https://leon-ac15d-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("A");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.
                String txt = tv_Speech_to_text.getText().toString();
                switch (txt)
                {
                    case "turn off":
                        databaseReference.child("Commands:").setValue(0);
                        break;
                    case "turn on":
                        databaseReference.child("Commands:").setValue(1);
                        break;
                    case "slow":
                        databaseReference.child("Commands:").setValue(2);
                        break;
                    case "fast":
                        databaseReference.child("Commands:").setValue(3);
                        break;
                    default:
                        databaseReference.child("Commands:").setValue(0);
                        tv_Speech_to_text.setText("Error");

                }

                //databaseReference.child("Commands:").setValue(command);

              //  databaseReference.child("Time:").setValue(strDate);
//                check=snapshot.getValue(Member.class);
//                String value = snapshot.getValue(String.class);

                // after getting the value we are setting
                // our value to our text view in below line.


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(getContext(), "Fail to get data.", Toast.LENGTH_SHORT).show();

            }
        });
    }


}





