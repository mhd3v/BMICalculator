package mhd3v.bmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class BMILog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmilog);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        boolean EOF = false;

        String dateString = "date";
        String bmiString ="bmi";
        String statusString ="status";

        String finalDataString="";

        int c = 0;

        while(!EOF){
            if(preferences.getString(dateString, "").equals(""))
                EOF = true;

            else{
                finalDataString += "\nDate/Time: " + preferences.getString(dateString, "") +
                        "\nBMI: " + preferences.getString(bmiString, "") +
                        "\nStatus: " + preferences.getString(statusString, "") +"\n\n";

                dateString = "date" + Integer.toString(c);
                bmiString = "bmi" + Integer.toString(c);
                statusString ="status" + Integer.toString(c);
                c++;
            }

        }


        TextView ResultTextView = (TextView) findViewById(R.id.textView5);

        ResultTextView.setMovementMethod(new ScrollingMovementMethod());

        ResultTextView.setText(finalDataString);


    }
}
