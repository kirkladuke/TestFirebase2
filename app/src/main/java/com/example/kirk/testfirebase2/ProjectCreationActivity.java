package com.example.kirk.testfirebase2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_creation);


        mFirebaseBtn = (Button) findViewById(R.id.firebase_btn);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mNameField = (EditText) findViewById(R.id.txtInputName) ;
        mDescription = (EditText) findViewById(R.id.txtInputDescription);

        mFirebaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            // Create a child in root object
            // assign value to child

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
