package com.example.rodrigo.postal_rate_calculator;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private String[] arraySpinnerSource;
    private String[] arraySpinnerDestination;
    private boolean conditionToPass = false;
    private  boolean valid, standard;
    private  double rate;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //****Initializing content in the Spinner***//
        this.arraySpinnerSource = new String[1];
        arraySpinnerSource[0] = "Canada";

        this.arraySpinnerDestination = new String[3];
        arraySpinnerDestination[0] = "Canada";
        arraySpinnerDestination[1] = "United States";
        arraySpinnerDestination[2] = "International";


        Spinner s1 = (Spinner) findViewById(R.id.spinnerSource);
      ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arraySpinnerSource);
        s1.setAdapter(adapter);


        Spinner s2 = (Spinner) findViewById(R.id.spinnerDest);
        ArrayAdapter<String> adapter1=  new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arraySpinnerDestination);
        s2.setAdapter(adapter1);



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





    public void onClick(View v){

        //calling components in the VIEW

        Spinner spinner1 = (Spinner)findViewById(R.id.spinnerSource);
        Spinner spinner2 = (Spinner)findViewById(R.id.spinnerDest);
        TextView txtWidth = (TextView)findViewById(R.id.editTextW);
        TextView txtLength = (TextView)findViewById(R.id.editTextL);
         TextView txtWeight = (TextView)findViewById(R.id.editTextWT);


        //****Getting Items from their respective boxes*****//
        String sourceLocation = spinner1.getSelectedItem().toString();
        String destinationLocation = spinner2.getSelectedItem().toString();
        String width = txtWidth.getText().toString().trim();
        String length = txtLength.getText().toString().trim();
        String weight = txtWeight.getText().toString().trim();

       conditionToPass = checkWidth(width,txtWidth) && checkLength(length,txtLength) &&  checkWeight(weight, txtWeight);

        if(conditionToPass) {
          double widht_new = Double.parseDouble(width);
          double length_new = Double.parseDouble(length);
          double weight_new = Double.parseDouble(weight);

            calculatorController(widht_new,length_new,weight_new,destinationLocation);

        }
    }


     public  boolean checkWidth(String w, TextView v1) {
         TextView t1 = (TextView)findViewById(R.id.txtResult);
         if(w.length() == 0){
             v1.setError("Your parameter is empty");
             t1.setText("Your package is invalid!");
             return false;
         }
         try{
             Double.parseDouble(w);
             return true;
         }
         catch(Exception e){
            v1.setError("You must provide a number");
             t1.setText("Your package is invalid!");
             return false;
         }
     }

    public  boolean checkLength(String l, TextView v2){
        TextView t1 = (TextView)findViewById(R.id.txtResult);
        if(l.length() == 0){
            v2.setError("Your parameter is empty");
            t1.setText("Your package is invalid!");
            return false;
        }
        try{
            Double.parseDouble(l);
            return true;
        }
        catch(Exception e){
            v2.setError("You must provide a number");
            t1.setText("Your package is invalid!");
            return false;
        }

    }

    public  boolean checkHeight(String h, TextView v3) {
        TextView t1 = (TextView)findViewById(R.id.txtResult);
        if (h.length() == 0) {
            v3.setError("Your parameter is empty");
            t1.setText("Your package is invalid!");
            return false;
        }
        try {
            Double.parseDouble(h);
            return true;

        } catch (Exception e) {
            v3.setError("You must provide a number");
            t1.setText("Your package is invalid!");
            return false;
        }
    }

    public  boolean checkWeight(String w,TextView v4 ){
        TextView t1 = (TextView)findViewById(R.id.txtResult);
        if(w.length() == 0){
            v4.setError("Your parameter is empty");
            t1.setText("Your package is invalid!");
            return false;
        }
        try{
            Double.parseDouble(w);
            return true;
        }
        catch(Exception e){
            v4.setError("You must provide a number");
            t1.setText("Your package is invalid!");
            return false;
        }

    }

    //*******This part calculates the rate*****//
    public void calculatorController(double w, double l, double wgt, String dest){
        TextView t1 = (TextView)findViewById(R.id.txtResult);
        isStandard(w, l,0, wgt);
        isValid(w, l,0, wgt);
        //***************THIS IS WHERE THE RATE WILL BE SHOWN TO THE USER***************************//
        if (valid) {
            rate = rateCalculator(wgt, standard, dest);
            t1.setText("$"+Double.toString(rate));
            System.out.println(rate);
        } else {

           t1.setText("Your package is invalid!");
        }
    }

    public void isStandard(double w, double h, double d, double wgt) {
        if (h < 140 || h > 245) {
            standard = false;
        } else if (w < 90 || w > 156) {

            standard = false;
        } else if (wgt < 3 || wgt > 50) {
            standard = false;
        } else {
            standard = true;
        }
    }

    public void isValid(double w, double h,double d, double wgt) {
        if (h > 380 || w > 270 || wgt > 500 || h < 140 ||wgt < 3 || w < 90  ) {
            valid = false;
        } else {
            valid = true;
        }
    }

    public double rateCalculator(double wgt, boolean standard, String dest) {
        double rate = 0.0;
        if (dest == "Canada") {
            if (standard) {
                if (wgt <= 30) {
                    rate = 1.00;
                } else {
                    rate = 1.20;
                }
            } else {
                if (wgt <= 100) {
                    rate = 1.8;
                } else if (wgt > 100 && wgt <= 200) {
                    rate = 2.95;
                } else if (wgt > 200 && wgt <= 300) {
                    rate = 4.10;
                } else if (wgt > 300 && wgt <= 400) {
                    rate = 4.70;
                } else if (wgt > 400 && wgt <= 500) {
                    rate = 5.05;
                }
            }
        } else if (dest == "United States") {
                if (standard) {
                    if (wgt <= 30) {
                      rate = 1.20;
                    } else {
                      rate = 1.80;
                }
            } else {
                    if (wgt <= 100) {
                        rate = 2.95;
                } else if (wgt > 100 && wgt <= 200) {
                        rate = 5.15;
                } else if (wgt > 200 && wgt <= 500) {
                        rate = 10.30;
                }
            }
           } else if (dest == "International") {
            if (standard) {
                        if (wgt <= 30) {
                        rate = 2.50;
                } else {
                        rate = 3.60;
                }
            } else {
                if (wgt <= 100) {
                       rate = 5.90;
                } else if (wgt > 100 && wgt <= 200) {
                       rate = 10.30;
                } else if (wgt > 200 && wgt <= 500) {
                       rate = 20.60;
                }
            }
        }
        return rate;
    }


}
