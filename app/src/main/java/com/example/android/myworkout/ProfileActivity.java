package com.example.android.myworkout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ProfileActivity extends AppCompatActivity {

    private static final String LOG_TAG = ProfileActivity.class.getSimpleName();

    public static final String MyPreferences = "MyPrefs";
    public static final String Name = "nameKey";
    public static final String Spouse = "spouseKey";
    public static final String SpouseEmail = "emailKey";
    public static final String MyGoal = "goalKey";
    public static final String MyPic = "picKey";

    SharedPreferences sharedPreferences;

    private EditText mNameProfile, mSpouseProfile, mSpouseEmailProfile, mWeightProfile;
    private ImageView mImageProfile;

    private static final int PICK_IMAGE_REQUEST = 0;

    private static final String STATE_URI = "STATE_URI";
    private Uri mUriPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mNameProfile = (EditText) findViewById(R.id.name_profile);
        mSpouseProfile = (EditText) findViewById(R.id.spouse_profile);
        mSpouseEmailProfile = (EditText) findViewById(R.id.spouse_email_profile);
        mWeightProfile = (EditText) findViewById(R.id.weight_profile);
        mImageProfile = (ImageView) findViewById(R.id.pic_profile);



       sharedPreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(Name)) {
            mNameProfile.setText(sharedPreferences.getString(Name, ""));
            mSpouseProfile.setText(sharedPreferences.getString(Spouse, ""));
            mSpouseEmailProfile.setText(sharedPreferences.getString(SpouseEmail, ""));
            mWeightProfile.setText(sharedPreferences.getString(MyGoal, ""));

            String getURI = (sharedPreferences.getString(MyPic, ""));
            mImageProfile.setImageURI(Uri.parse(getURI));


        }

        mUriPic = Uri.parse(sharedPreferences.getString(MyPic, ""));

        final Button mUpdatePic = (Button) findViewById(R.id.browse_pic_profile);
        mUpdatePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageSelector();
            }
        });

        Button mUpdateProfile = (Button) findViewById(R.id.updte_profile);
        mUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                SharedPreferences.Editor editor = sharedPreferences.edit();

                String myUri = mUriPic.toString();
                editor.putString(Name, mNameProfile.getText().toString());
                editor.putString(Spouse, mSpouseProfile.getText().toString());
                editor.putString(SpouseEmail, mSpouseEmailProfile.getText().toString());
                editor.putString(MyGoal, mWeightProfile.getText().toString());
                editor.putString(MyPic, myUri.toString());


                editor.commit();
                Toast.makeText(ProfileActivity.this, "Your profile is now updated.", Toast.LENGTH_LONG).show();

            }

        });
    }


    public void openImageSelector() {
        Intent intent;

        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if (requestCode == PICK_IMAGE_REQUEST && resultCode ==RESULT_OK) {
            if (data != null) {
                mUriPic = data.getData();
                Log.i(LOG_TAG, "Uri: " + mUriPic.toString());

                mImageProfile.setImageBitmap(getBitmapFromUri(mUriPic));
            }
        }
    }

    public Bitmap getBitmapFromUri(Uri uri) {

        if (uri == null || uri.toString().isEmpty()){
            return null;
        }

        //ここ、できたらあとで原因究明して
        int targetW = 80;
        int targetH = 80;

        Log.i("Image Size Is: ", targetW + " " + targetH );

        InputStream input = null;

        try {
            input = this.getContentResolver().openInputStream(uri);

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(input, null, bmOptions);
            input.close();

            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            input = this.getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(input, null, bmOptions);


            input.close();
            return bitmap;

        } catch (FileNotFoundException fne) {
            Log.e(LOG_TAG, "Failed to load image.", fne);
            return null;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Failed to load image.", e);
            return null;
        } finally {
            try {
                input.close();
            } catch (IOException ioe) {

            }
        }
    }

    public String getHerEmail() {

        sharedPreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
        String returnThis = "";
        if (sharedPreferences.contains(SpouseEmail)) {
            returnThis = sharedPreferences.getString(SpouseEmail, "");
        }
        return returnThis;
    }
}
