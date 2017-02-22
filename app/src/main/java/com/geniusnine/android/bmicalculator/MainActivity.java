package com.geniusnine.android.bmicalculator;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    TextView textviewbmi, textviewbmr, textviewFAT, textviewbmiinterpret, textviewFATinterpret, textViewIdealWeight,TextViewIdealWeightfake,TextViewbmrfake;
    EditText edittextAge, edittextheight, edittextfeet, edittextInch, edittextweight, edittextWaist;
     Button butttonCalculate;
    ImageView buttonAge, buttonheight, buttonweight;
    private RadioGroup radioGroupSex,radioGroupHeight;
    private RadioButton radioButtonSex,radioButtonHeight;
    String sexValue, heightValue, strHeigthfeet, strHeightInch, strAge, strWeight, strHeight, strWaist;
    View alertLayout;
    static final int MALEOFFSET = 5;  // Miffin St-Jeor equation is: (10 * weight (kg)) + (6.25 * height (cm)) + (5 * age) + OFFSET, where offset is 5 for males, -161 for females.
    static final int FEMALEOFFSET = -161;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        edittextAge = (EditText) findViewById(R.id.edittextAge);
        edittextweight = (EditText) findViewById(R.id.edittextWeight);
        edittextheight = (EditText) findViewById(R.id.edittextHeight);
        edittextfeet = (EditText) findViewById(R.id.edittextFeet);
        edittextInch = (EditText) findViewById(R.id.edittextInch);
       // edittextWaist = (EditText) findViewById(R.id.edittextWaist);

        buttonAge = (ImageView) findViewById(R.id.buttonAge);
        buttonheight = (ImageView) findViewById(R.id.ButtonChooserheight);
        buttonweight = (ImageView) findViewById(R.id.ButtonChooserWeight);
        butttonCalculate = (Button) findViewById(R.id.buttoncalculate);
        textviewbmi = (TextView) findViewById(R.id.TextViewbmi);
        TextViewbmrfake = (TextView) findViewById(R.id.TextViewbmrfake);
        textviewbmiinterpret = (TextView) findViewById(R.id.TextViewbmiinterpret);
        textviewbmr = (TextView) findViewById(R.id.TextViewbmr);
        textviewFAT = (TextView) findViewById(R.id.TextViewFAT);
        textviewFATinterpret = (TextView) findViewById(R.id.TextViewFATInterpr);
        textViewIdealWeight = (TextView) findViewById(R.id.TextViewIdealWeight);
        TextViewIdealWeightfake = (TextView) findViewById(R.id.TextViewIdealWeightfake);
        buttonAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                alertLayout = inflater.inflate(R.layout.dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Gender:");

                radioGroupSex = (RadioGroup)  alertLayout.findViewById(R.id.radioSex);
                radioGroupSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        radioButtonSex = (RadioButton)  alertLayout.findViewById(radioGroup.getCheckedRadioButtonId());
                        sexValue = radioButtonSex.getText().toString().trim();
                        Toast.makeText(getApplication(), sexValue, Toast.LENGTH_SHORT).show();
                        if (sexValue.equals("Male")||sexValue.equals("")) {
                            buttonAge.setImageResource(R.drawable.gender_m);

                        } else {
                            buttonAge.setImageResource(R.drawable.gender_f);
                        }
                    }

                });
                alertDialogBuilder.setView(alertLayout);
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        buttonheight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                alertLayout = inflater.inflate(R.layout.dialogheight, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Height In:");
                radioGroupHeight = (RadioGroup) alertLayout.findViewById(R.id.radioSex);
                radioGroupHeight.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        radioButtonHeight = (RadioButton) alertLayout.findViewById(radioGroup.getCheckedRadioButtonId());
                        heightValue = radioButtonHeight.getText().toString().trim();
                        Toast.makeText(getApplication(), heightValue, Toast.LENGTH_SHORT).show();
                        if (heightValue.equals("CM")||heightValue.equals("")) {
                            edittextheight.setVisibility(View.VISIBLE);
                            edittextfeet.setVisibility(View.GONE);
                            edittextInch.setVisibility(View.GONE);
                            buttonheight.setImageResource(R.drawable.btn_cm);


                        } else {
                            edittextheight.setVisibility(View.GONE);
                            edittextfeet.setVisibility(View.VISIBLE);
                            edittextInch.setVisibility(View.VISIBLE);
                            buttonheight.setImageResource(R.drawable.btn_ft_in);
                        }

                    }

                });
                alertDialogBuilder.setView(alertLayout);
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });


        butttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heightValue = radioButtonHeight.getText().toString().trim();
               if(heightValue.equals("CM")) {
                   strAge = edittextAge.getText().toString();
                   strWeight = edittextweight.getText().toString();
                   strHeight = edittextheight.getText().toString();
                   //   strWaist = edittextheight.getText().toString();
                   if (TextUtils.isEmpty(strAge)) {
                       edittextAge.setError("Please enter your Age");
                       edittextAge.requestFocus();
                       return;
                   }

                   if (TextUtils.isEmpty(strWeight)) {
                       edittextweight.setError("Please enter your weight");
                       edittextweight.requestFocus();
                       return;
                   }

                   if (TextUtils.isEmpty(strHeight)) {
                       edittextheight.setError("Please enter your height");
                       edittextheight.requestFocus();
                       return;
                   }

                   //Get the user values from the widget reference
                   float ageInYears = Integer.parseInt(strAge);
                   float weight = Float.parseFloat(strWeight);
                   float height = Float.parseFloat(strHeight) / 100;
                   //  float waist = Float.parseFloat(strWaist);
                   int height1 = Integer.parseInt(strHeight);
                   //Calculate BMI  and BMR value
                   float bmiValue = calculateBMI(weight, height);
                   float bmrValue = calculateBMR(weight, height, ageInYears);
                   float FATValue = calculateFAT(ageInYears, bmiValue);
                   int IdealWeightValue = calculateIdealWeight(height1);
                   Log.d("bmrvalue", String.valueOf(bmrValue));

                   //Define the meaning of the bmi value
                   String bmiInterpretation = interpretBMI(bmiValue);
                   String FatInterpretation = interpretFAT(FATValue);


                   //set value to the textview
                   DecimalFormat f = new DecimalFormat("##.00");
                   textviewbmi.setText(f.format(bmiValue));
                   TextViewbmrfake.setText("-");
                   textviewbmiinterpret.setText(String.valueOf(bmiInterpretation));
                   textviewbmr.setText(f.format(bmrValue));
                   textviewFAT.setText(String.valueOf(Math.round(FATValue))+"%");
                   textviewFATinterpret.setText(String.valueOf(FatInterpretation));
                   textViewIdealWeight.setText(String.valueOf(IdealWeightValue)+"kg");
                   TextViewIdealWeightfake.setText("-");

               }else{
                   //ft+in
                   strAge = edittextAge.getText().toString();
                   strWeight = edittextweight.getText().toString();
                   //   strWaist = edittextheight.getText().toString();
                   //Get the user values from the widget reference
                   float ageInYears = Integer.parseInt(strAge);
                   float weight = Float.parseFloat(strWeight);
                   //  float waist = Float.parseFloat(strWaist);
                   if (TextUtils.isEmpty(strAge)) {
                       edittextAge.setError("Please enter your Age");
                       edittextAge.requestFocus();
                       return;
                   }

                   if (TextUtils.isEmpty(strWeight)) {
                       edittextweight.setError("Please enter your weight");
                       edittextweight.requestFocus();
                       return;
                   }

                   strHeigthfeet = edittextfeet.getText().toString();
                   strHeightInch = edittextInch.getText().toString();
                   float heightfeet = Float.parseFloat(strHeigthfeet);
                   float heightinches = Float.parseFloat(strHeightInch);
                   float heightfeetvalue = (float) ((heightfeet * 30.48) + (heightinches * 2.54));
                   //Calculate BMI  and BMR value
                   float bmiValue = calculateBMI(weight, (heightfeetvalue/100));
                   float bmrValue = calculateBMR(weight, (heightfeetvalue/100), ageInYears);
                   float FATValue = calculateFAT(ageInYears, bmiValue);
                   int IdealWeightValue = calculateIdealWeight(Math.round(heightfeetvalue));
                   Log.d("bmrvalue", String.valueOf(bmrValue));

                   //Define the meaning of the bmi value
                   String bmiInterpretation = interpretBMI(bmiValue);
                   String FatInterpretation = interpretFAT(FATValue);


                   //set value to the textview
                   DecimalFormat f = new DecimalFormat("##.00");
                   textviewbmi.setText(f.format(bmiValue));
                   TextViewbmrfake.setText("-");
                   textviewbmiinterpret.setText(String.valueOf(bmiInterpretation));
                   textviewbmr.setText(f.format(bmrValue));
                   textviewFAT.setText(String.valueOf(Math.round(FATValue))+"%");
                   textviewFATinterpret.setText(String.valueOf(FatInterpretation));
                   textViewIdealWeight.setText(String.valueOf(IdealWeightValue)+"kg");
                   TextViewIdealWeightfake.setText("-");
                }

            }
        });
    }

    //Calculate BMI
    private float calculateBMI(float weight, float height) {
        return (float) (weight / (height * height));
    }

    //Calculate Ideal Weight
    private int calculateIdealWeight(int height) {
        int idealweight=0;
        if (radioButtonSex.getText().toString().trim().equals("Male")) {
            idealweight = (height - 100 - ((height - 150) / 4));

        } else {
            idealweight = (height - 100 - ((height - 150) / 2));
        }
        return (int) (idealweight+1);
    }


    private float calculateBMR(float weight, float height, float ageInYears) {

        int offset = radioButtonSex.getText().toString().trim().equals("Male") ? MALEOFFSET : FEMALEOFFSET;
        return (int) (Math.round((10 * weight) + (6.25 * height) - (5 * ageInYears) + offset));
    }

    private float calculateFAT(float ageInYears, float bmiValue) {
        float FAT_percentage;
        if(radioButtonSex.getText().toString().trim().equals("Male")) {
            FAT_percentage= (float) ((1.20 * bmiValue) + (0.23 * ageInYears) - (10.8 * 1) - 5.4);
        }else{
           FAT_percentage = (float) ((1.20 * bmiValue) + (0.23 * ageInYears) - (10.8 * 0) - 5.4);
        }
        return (int) (FAT_percentage);
    }


    // Interpret what BMI means
    private String interpretBMI(float bmiValue) {

        if (sexValue == "Male") {
            if (bmiValue < 16) {
                return "Severely underweight";
            } else if (bmiValue < 18.5) {
                return "Underweight";
            } else if (bmiValue < 25) {
                return "Normal";
            } else if (bmiValue < 30) {
                return "Overweight";
            } else if (bmiValue < 35) {
                return "Obese Class I";
            } else if (bmiValue < 40) {
                return "Obese Class II";
            } else {
                return "Obese Class III";
            }
        } else {
            if (bmiValue < 17.5) {
                return "Underweight";
            } else if (bmiValue < 24.9) {
                return "Normal";
            } else if (bmiValue < 40) {
                return "Overweight";
            } else {
                return "Obese Class ";
            }

        }
    }


    private String interpretFAT(float FATValue) {

        if (sexValue == "Female") {
            if (FATValue < 14) {
                return "Athletes";
            } else if (FATValue < 24) {

                return "Fitness";
            } else if (FATValue < 31) {

                return "Acceptable";
            } else {

                return "Obese";
            }
        } else {
            if (FATValue < 6) {
                return "Athletes";
            } else if (FATValue < 17) {

                return "Fitness";
            } else if (FATValue < 25) {

                return "Acceptable";
            } else {

                return "Obese";
            }
        }

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
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_remove_ads) {
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_moreapps) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}