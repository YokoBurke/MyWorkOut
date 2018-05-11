package com.example.android.myworkout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        EditText mNameProfile = (EditText) findViewById(R.id.name_profile);
        EditText mSpouseProfile = (EditText) findViewById(R.id.spouse_profile);
        EditText mSpouseEmailProfile = (EditText) findViewById(R.id.spouse_email_profile);
        EditText mWeightProfile = (EditText) findViewById(R.id.weight_profile);

        Button mUpdate = (Button) findViewById(R.id.updte_profile);
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //ここにSaveしたらShared Preferenceに保存されるように入れる。　https://www.tutorialspoint.com/android/android_shared_preferences.htm
            }

        });
    }
}
