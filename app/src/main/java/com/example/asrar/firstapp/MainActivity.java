package com.example.asrar.firstapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.restservice.HttpResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    public  final static String PAR_KEY = "com.mycompany.myfirstapp.par";
    public static final String PREFS_NAME = "MyPrefsFile";

    public String HttpData = "";

    private void setHttpData(String data){
        this.HttpData = data;
    }

    private String getHttpData(){
        return this.HttpData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = (EditText) findViewById(R.id.edit_message);
        EditText editText2 = (EditText) findViewById(R.id.edit_message2);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String firstName = settings.getString("firstName", "");
        String lastName = settings.getString("lastName", "");

        editText.setText(firstName, TextView.BufferType.EDITABLE);
        editText2.setText(lastName, TextView.BufferType.EDITABLE);

        Spinner spinnerPlanet = (Spinner) findViewById(R.id.spinner_planet);
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, Arrays.asList(getResources().getStringArray(R.array.planets_array)));
        spinnerPlanet.setAdapter(adapter);

        Button sendButton = (Button) findViewById(R.id.button_send);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.edit_message);
                EditText editText2 = (EditText) findViewById(R.id.edit_message2);

                CheckBox chkGraudate = (CheckBox) findViewById(R.id.graduate);
                CheckBox chkPostGraudate = (CheckBox) findViewById(R.id.post_graduate);

                RadioGroup radioGender;
                RadioButton radioGenderButton;
                radioGender = (RadioGroup) findViewById(R.id.radioGroupGender);
                int selectedId = radioGender.getCheckedRadioButtonId();
                radioGenderButton = (RadioButton) findViewById(selectedId);

                Person mPerson = new Person();
                mPerson.setFirstName(editText.getText().toString());
                mPerson.setLastName(editText2.getText().toString());

                mPerson.setGraduate(chkGraudate.isChecked());
                mPerson.setPostGraduate(chkPostGraudate.isChecked());

                mPerson.setGender(radioGenderButton.getText().toString());

                Intent mIntent = new Intent(v.getContext(),DisplayMessageActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putParcelable(PAR_KEY, mPerson);
                mIntent.putExtras(mBundle);

                String url = "http://maps.googleapis.com/maps/api/geocode/json?address=udaipur";
                RestWebService restObj = new RestWebService(MainActivity.this){
                    @Override
                    public void onSuccess(String data, HttpResponse response) {
                        super.onSuccess(data, response);
                        MainActivity.this.setHttpData(data);
                    }
                };
                restObj.getCall(url);
                JSONObject jsonObj;
                try {
                    jsonObj = new JSONObject(MainActivity.this.HttpData);
                }
                catch (Exception e){
                    jsonObj = null;
                }

                if(jsonObj != null) {
                    try {
                        Toast.makeText(v.getContext(), jsonObj.getString("results"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                startActivity(mIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop(){
        super.onStop();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        EditText editText = (EditText) findViewById(R.id.edit_message);
        EditText editText2 = (EditText) findViewById(R.id.edit_message2);

        editor.putString("firstName", editText.getText().toString());
        editor.putString("lastName", editText2.getText().toString());

        // Commit the edits!
        editor.commit();
    }

    /** Called when the user clicks the Send button */
   /* public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        EditText editText2 = (EditText) findViewById(R.id.edit_message2);
        String message = editText.getText().toString() + " " + editText2.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }*/
}
