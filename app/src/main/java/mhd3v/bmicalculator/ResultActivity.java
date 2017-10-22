package mhd3v.bmicalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class ResultActivity extends AppCompatActivity {

    boolean entry = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();

        double weight = Double.parseDouble(intent.getStringExtra("weight"));

        double height = Double.parseDouble(intent.getStringExtra("height"));

        boolean storeInLog = intent.getExtras().getBoolean("putInLog");

        String calculationMethod = intent.getStringExtra("calculationMethod");

        calculateBmi(weight, height, storeInLog, calculationMethod);
    }

    void calculateBmi(double w, double h, boolean storeInLog, String calculationMethod) {

        double bmi;
        String status;

        if(calculationMethod.equals("metric"))
         bmi = w/((h*h));
        else
         bmi = (w*703)/(h*h);

        String bmiResult = String.format("%.2f", bmi);

        TextView result = (TextView) findViewById(R.id.bmiTextView);

        TextView textRes = (TextView) findViewById(R.id.textResult);

        if(bmi<18.5){
            textRes.setTextColor(Color.parseColor("#E1E638"));
            textRes.setText("You are underweight,you should consider eating more!");
            status = "Underweight";
        }

        else if( bmi >= 18.5 && bmi <= 24.9 ){
            textRes.setTextColor(Color.parseColor("#4DA947"));
            textRes.setText("You are normal weight. You are doing great, keep it up!");
            status = "Normal Weight";
        }

        else if( bmi >= 25 && bmi <= 29.9 ){
            textRes.setTextColor(Color.parseColor("#BA455D"));
            textRes.setText("You are overweight, you should probably start eating less.");
            status = "Overweight";
        }

        else{
            textRes.setTextColor(Color.parseColor("#E9103B"));
            textRes.setText("You are obese, you're at a great risk! Please consider dieting " +
                    "and exercising!");
            status = "Obese";
        }

        result.setText(bmiResult);

        if(storeInLog) {   //if user wants to store data in record

            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

            SharedPreferences.Editor editor = preferences.edit();

            String dateString = "date";

            String bmiString = "bmi";

            String statusString = "status";

            int count=0;

            while(!entry){

                if(preferences.getString(dateString,"").equals("")){
                    editor.putString(dateString, currentDateTimeString);
                    editor.putString(bmiString, bmiResult);
                    editor.putString(statusString, status);
                    entry = true;
                }

                else{
                    String c = Integer.toString(count);
                    dateString = "date" + c;
                    bmiString = "bmi" + c;
                    statusString = "status" + c;
                    count++;
                }

            }

            if(editor.commit())
            Toast.makeText(ResultActivity.this,"BMI Stored in Log!",Toast.LENGTH_LONG).show();
        }


    }

    public void bmiLog(View view) {

        startActivity(new Intent(this, BMILog.class));
    }
}
