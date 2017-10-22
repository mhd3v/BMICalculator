package mhd3v.bmicalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static mhd3v.bmicalculator.R.menu.menu_main;


public class MainActivity extends AppCompatActivity {
    Intent launchResult;
    Menu menu;
    String weightString = "", heightString = "";
    String calculationMethod ="metric";
    EditText weightField, heightField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState != null) {
            weightField = (EditText) findViewById(R.id.weight);
            heightField = (EditText) findViewById(R.id.height);

            weightField.setText(savedInstanceState.getString("weight"));
            heightField.setText(savedInstanceState.getString("height"));
        }

        else {

            weightField = (EditText) findViewById(R.id.weight);
            heightField = (EditText) findViewById(R.id.height);
        }

        launchResult = new Intent(this, ResultActivity.class);


    }


    public void calculateBmi(View view) {

        //check if any fields empty
        if (weightField.getText().toString().equals("") && heightField.getText().toString().equals(""))
            Toast.makeText(MainActivity.this, "Please enter both your height and weight!", Toast.LENGTH_LONG).show();

        else if (heightField.getText().toString().equals(""))
            Toast.makeText(MainActivity.this, "Please enter your height!", Toast.LENGTH_LONG).show();

        else if (weightField.getText().toString().equals(""))
            Toast.makeText(MainActivity.this, "Please enter your weight!", Toast.LENGTH_LONG).show();

            //no field empty, put data into intent and launch result activity
        else {

            weightString = weightField.getText().toString();

            heightString = heightField.getText().toString();

            launchResult.putExtra("weight", weightString);

            launchResult.putExtra("height", heightString);

            launchResult.putExtra("calculationMethod", calculationMethod);

            startActivity(launchResult);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("weight", weightField.getText().toString());
        outState.putString("height", heightField.getText().toString());

    }


    public void storeInLog(View view) {

        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        if (checkBox.isChecked())
            launchResult.putExtra("putInLog", true);
        else
            launchResult.putExtra("putInLog", false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(menu_main, menu);
        this.menu = menu;
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.log) {

            startActivity(new Intent(this,BMILog.class));

            return true;

        }

        if(id == R.id.Imperial) {

            if(calculationMethod.equals("metric")) {

                TextView weightLabel = (TextView) findViewById(R.id.textView);
                TextView heightLabel = (TextView) findViewById(R.id.textView2);

                heightField.setText("");
                weightField.setText("");

                weightLabel.setText("Weight (lb):");
                heightLabel.setText("Height (in):");

                MenuItem menuItem = menu.findItem(R.id.Imperial);

                menuItem.setTitle("Metric");

                calculationMethod = "imperial";
            }

            else {

                TextView weightLabel = (TextView) findViewById(R.id.textView);
                TextView heightLabel = (TextView) findViewById(R.id.textView2);

                heightField.setText("");
                weightField.setText("");

                weightLabel.setText("Weight (Kg):");
                heightLabel.setText("Height (m):");

                MenuItem menuItem = menu.findItem(R.id.Imperial);

                menuItem.setTitle("Imperial");

                calculationMethod = "metric";

            }

        }

        return false;
    }

}