package com.example.kirk.testfirebase2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ProjectCreationActivity extends AppCompatActivity {

    private Button mFirebaseBtn;
    private EditText mNameField;
    private EditText mDescription;
    private DatabaseReference mDatabase;

    // These are for spinner for group member choice
    Spinner memberSpinner;
    ArrayAdapter<CharSequence> memberAdapter;

    //This for Frequency Spinner
    Spinner frequencySpinner;
    ArrayAdapter<CharSequence> frequencyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_creation);

        //These next few lines for spinner members when connected to group list delete array
        // created in strings.xml file
        memberSpinner = (Spinner) findViewById(R.id.spinnerMember);
        memberAdapter = ArrayAdapter.createFromResource(this, R.array.Member_Names, android.R.layout.simple_spinner_item);
        memberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        memberSpinner.setAdapter(memberAdapter);
        memberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position)+" was selected", Toast.LENGTH_LONG).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
             // This is the end of code for spinner/ remove toast when connected to real group members


        frequencySpinner = (Spinner) findViewById(R.id.spinnerFrequency);
        frequencyAdapter = ArrayAdapter.createFromResource(this, R.array.Frequency, android.R.layout.simple_spinner_item);
        frequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequencySpinner.setAdapter(frequencyAdapter);
        frequencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position)+" was selected", Toast.LENGTH_LONG).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        mFirebaseBtn = (Button) findViewById(R.id.firebase_btn);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mNameField = (EditText) findViewById(R.id.txtInputName) ;
        mDescription = (EditText) findViewById(R.id.txtInputDescription);

        mFirebaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            // Create a child in root object
            // assign value to child object

                String name = mNameField.getText().toString().trim();
                String description = mDescription.getText().toString().trim();

                HashMap<String, String> datamap = new HashMap<String, String>();

                datamap.put("Name", name);
                datamap.put("Description", description);


                mDatabase.push().setValue(datamap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(ProjectCreationActivity.this, "Saved successfully",Toast.LENGTH_LONG).show();
                        } else
                        {
                            Toast.makeText(ProjectCreationActivity.this, "Failed, Loser!!!!",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }


}
