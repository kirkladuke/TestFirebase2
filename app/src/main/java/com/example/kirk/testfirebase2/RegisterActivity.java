package com.example.kirk.testfirebase2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {

    private EditText mFirstNameField;
    private EditText mLastNameField;
    private EditText mEmailField;
    private EditText mPasswordField;

    private Button mBtnRegister;

    private FirebaseAuth mauth;
    private DatabaseReference mDatabase;

    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mauth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mProgress = new ProgressDialog(this);

        //  Textboxes data transfer
        mFirstNameField = (EditText) findViewById(R.id.txtInputFirstName);
        mLastNameField = (EditText) findViewById(R.id.txtInputLastName);
        mEmailField = (EditText) findViewById(R.id.txtInputEmail);
        mPasswordField = (EditText) findViewById(R.id.txtInputPassword);
        mBtnRegister = (Button) findViewById(R.id.btnRegister);

        //  Set button to listen for click
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startRegister();


            }
        });

    }

    private void startRegister()
    {
        final String firstname = mFirstNameField.getText().toString().trim();
        final String lastname = mLastNameField.getText().toString().trim();
        String email = mEmailField.getText().toString().trim();
        String password = mPasswordField.getText().toString().trim();

        if(!TextUtils.isEmpty(firstname) && !TextUtils.isEmpty(lastname) && !TextUtils.isEmpty(email)
            && !TextUtils.isEmpty(password))
        {



            if(password.length() >= 8) {

                mProgress.setMessage("Signing Up...");
                mProgress.show();

                mauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            String user_id = mauth.getCurrentUser().getUid();

                            //mDatabase.child(user_id);
                            DatabaseReference current_user_db = mDatabase.child(user_id);

                            current_user_db.child("first name").setValue(firstname);
                            current_user_db.child("LastName").setValue(lastname);

                            mProgress.dismiss();
                            Intent openMainActivity = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(openMainActivity);

                        } else {
                                //  Make exception
                            Toast.makeText(getApplicationContext(), "Did not work", Toast.LENGTH_LONG).show();
                        }


                    }

                });

            } else
                {
                    //  Make exception
                    Toast.makeText(getApplicationContext(), "Password must be at least 8 characters long", Toast.LENGTH_LONG).show();
                }
        }


    }
}
