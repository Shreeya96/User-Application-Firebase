package android.example.com.hungryuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by Aakash on 02-09-2017.
 */

public class RegisterUser extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private Button register;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    EditText getName;
    EditText getNumber;
    String userId;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeruser);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();

        register = (Button) findViewById(R.id.userSubmit);

        getName = (EditText) findViewById(R.id.userName);
        getNumber = (EditText) findViewById(R.id.userMobile);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//
                final String name = getName.getText().toString().trim();
                final String mobile = getNumber.getText().toString().trim();
//                mDatabase.child("USERS").child(user.getUid()).setValue(userInformation).

                HashMap<String, String> dataMap = new HashMap<String, String>();

                dataMap.put("Mobile", mobile);
                dataMap.put("Name", name);

                mDatabase.child("USERS").child(user.getUid()).setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Log.v("RegisterUSer", "if task is successful");
                            Toast.makeText(RegisterUser.this, "Data Stored Successfully", Toast.LENGTH_LONG).show();
                            getName.setText("");
                            getNumber.setText("");
                            Intent i = new Intent(RegisterUser.this, UserUpdateCount.class);
                            startActivity(i);
                        } else {
                            Log.v("RegisterUser", "if task is NOT successful");
                            Toast.makeText(RegisterUser.this, "Data Not Stored", Toast.LENGTH_LONG).show();
                            getName.setText("");
                            getNumber.setText("");
                        }
                    }
                });


            }

        });


    }
}
