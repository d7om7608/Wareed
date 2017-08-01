package com.example.d7om7.wareed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.example.d7om7.wareed.menagerModel.donor;

public class RequstActivity extends AppCompatActivity {
    RequestBlood requestBlood;
    EditText requestBloodText;
    EditText pantienNameText;
    EditText fileNumberText;
    EditText countBloodText;
    EditText reasonOfRequistText;
    List<String> spinnerArray;
    static String positon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_requst);
        final Spinner spinner = (Spinner) findViewById(R.id.planets_spiner);
        spinnerArray = new ArrayList<String>();
        spinnerArray.add("O+");
        spinnerArray.add("O+");
        spinnerArray.add("A+");
        spinnerArray.add("A-");
        spinnerArray.add("B+");
        spinnerArray.add("B-");
        spinnerArray.add("AB+");
        spinnerArray.add("AB-");

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positon = spinnerArray.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner.setSelection(0);
            }

        });
    }

    public void onclick(View view) {
        requestBloodText = (EditText) findViewById(R.id.reasonOfRequist);
        pantienNameText = (EditText) findViewById(R.id.pantienName);
        fileNumberText = (EditText) findViewById(R.id.fileNumber);
        countBloodText = (EditText) findViewById(R.id.countBlood);
        reasonOfRequistText = (EditText) findViewById(R.id.reasonOfRequist);


        if ((requestBloodText.getText().toString()).equals("") || pantienNameText.getText().toString().equals("") ||
                fileNumberText.getText().toString().equals("") || countBloodText.getText().toString().equals("") ||
                reasonOfRequistText.getText().toString().equals("")) {
        } else {

            requestBlood = new RequestBlood(pantienNameText.getText().toString(),
                    Integer.parseInt(fileNumberText.getText().toString()),
                    Integer.parseInt(countBloodText.getText().toString()),
                    reasonOfRequistText.getText().toString(),
                    positon, "makkah", "sssssss", "dffdsff", 2, donor.getUserID(), 0);
            donor.requestBlood.add(requestBlood);

           Intent startChildActivityIntent = new Intent(this, EmergencyListActivity.class);
            startActivity(startChildActivityIntent);
              txetEmpty();

        }

    }

    public void txetEmpty() {
        requestBloodText.setText("");
        fileNumberText.setText("");
        countBloodText.setText("");
        reasonOfRequistText.setText("");


    }
}
