package com.geniusnine.android.bmicalculator;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    //Declaration of UI variables
    TextView textviewbmi, textviewbmr, textviewFAT, textviewbmiinterpret, textviewFATinterpret, textViewIdealWeight,TextViewIdealWeightfake,TextViewbmrfake;
    EditText edittextAge, edittextheight, edittextfeet, edittextInch, edittextweight, edittextWaist,edittextWeightInLb,edittextWeightInST,edittextWeightInSTLb;
    Button butttonCalculate;
    ImageView buttonAge, buttonheight, buttonweight;
    private RadioGroup radioGroupSex,radioGroupHeight,radioGroupWeight;
    private RadioButton radioButtonSex,radioButtonHeight,radioButtonWeight;

    //Declartion  of varibale which is needed to store values getted from edittext
    String sexValue,heightValue,strHeigthfeet,strHeightInch,strAge,strWeight,strHeight,strWaist,WeightValue;

    //this two for gender value for getting BMR
    static final int MALEOFFSET = 5;  // Miffin St-Jeor equation is: (10 * weight (kg)) + (6.25 * height (cm)) + (5 * age) + OFFSET, where offset is 5 for males, -161 for females.
    static final int FEMALEOFFSET = -161;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting Icon to action Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //Initialising View
        edittextAge = (EditText) findViewById(R.id.edittextAge);
        edittextweight = (EditText) findViewById(R.id.edittextWeight);
        edittextWeightInLb = (EditText) findViewById(R.id.edittextWeightInLb);
        edittextWeightInST = (EditText) findViewById(R.id.edittextWeightInST);
        edittextWeightInSTLb = (EditText) findViewById(R.id.edittextWeightInSTLb);
        edittextheight = (EditText) findViewById(R.id.edittextHeight);
        edittextfeet = (EditText) findViewById(R.id.edittextFeet);
        edittextInch = (EditText) findViewById(R.id.edittextInch);
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


        //alert Dialog Declaration For Gender
        final LayoutInflater inflaterGender = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View alertLayoutGender = inflaterGender.inflate(R.layout.dialog, null);
        final AlertDialog.Builder alertDialogBuilderGender = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderGender.setTitle("Gender :");
        radioGroupSex = (RadioGroup)  alertLayoutGender.findViewById(R.id.radioSex);
        alertDialogBuilderGender.setView(alertLayoutGender);
        final AlertDialog alertDialogGender = alertDialogBuilderGender.create();

        //on Alert Radio Button Gender On click listener
        buttonAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogGender.show();
                radioGroupSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                      alertDialogGender.cancel();
                        radioButtonSex = (RadioButton)  alertLayoutGender.findViewById(radioGroup.getCheckedRadioButtonId());
                        sexValue = radioButtonSex.getText().toString().trim();
                       // Toast.makeText(getApplication(), sexValue, Toast.LENGTH_SHORT).show();
                        if (sexValue.equals("Male")||sexValue.equals("")) {
                            buttonAge.setImageResource(R.drawable.gender_m);
                        } else {
                            buttonAge.setImageResource(R.drawable.gender_f);
                        }
                    }

                });


            }
        });

        //alert Dialog Declaration for Height
        final LayoutInflater inflaterHeight = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View alertLayoutHeight  = inflaterHeight.inflate(R.layout.dialogheight, null);
        final AlertDialog.Builder alertDialogBuilderHeight = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderHeight.setTitle("Height In :");
        radioGroupHeight = (RadioGroup) alertLayoutHeight.findViewById(R.id.radioHeight);
        alertDialogBuilderHeight.setView(alertLayoutHeight);
        final AlertDialog alertDialogHeight = alertDialogBuilderHeight.create();

        //on Alert Radio Button height On click listener
        buttonheight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vgender) {
                alertDialogHeight.show();
                radioGroupHeight.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        alertDialogHeight.cancel();
                        radioButtonHeight = (RadioButton) alertLayoutHeight.findViewById(radioGroup.getCheckedRadioButtonId());
                        heightValue = radioButtonHeight.getText().toString().trim();
                       // Toast.makeText(getApplication(), heightValue, Toast.LENGTH_SHORT).show();
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



            }
        });

        //alert Dialog Declaration for Weight
        final LayoutInflater inflaterWeight = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View alertLayoutWeight  = inflaterWeight.inflate(R.layout.dialogweight, null);
        final AlertDialog.Builder alertDialogBuilderWeight = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderWeight.setTitle("Weight In :");
        radioGroupWeight = (RadioGroup) alertLayoutWeight.findViewById(R.id.radioWeight);
        alertDialogBuilderWeight.setView(alertLayoutWeight);
        final AlertDialog alertDialogWeight = alertDialogBuilderWeight.create();

        //on Alert Radio Button weight On click listener
        buttonweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogWeight.show();
                radioGroupWeight.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        alertDialogWeight.cancel();
                        radioButtonWeight = (RadioButton) alertLayoutWeight.findViewById(radioGroup.getCheckedRadioButtonId());
                        WeightValue = radioButtonWeight.getText().toString().trim();
                       // Toast.makeText(getApplication(), WeightValue, Toast.LENGTH_SHORT).show();
                        if (WeightValue .equals("KG")||WeightValue.equals("")) {
                            edittextweight.setVisibility(View.VISIBLE);
                            edittextWeightInLb.setVisibility(View.GONE);
                            edittextWeightInST.setVisibility(View.GONE);
                            edittextWeightInSTLb.setVisibility(View.GONE);
                            buttonweight.setImageResource(R.drawable.btn_kg);


                        } else if(WeightValue .equals("LB")){
                            edittextweight.setVisibility(View.GONE);
                            edittextWeightInLb.setVisibility(View.VISIBLE);
                            edittextWeightInST.setVisibility(View.GONE);
                            edittextWeightInSTLb.setVisibility(View.GONE);
                            buttonweight.setImageResource(R.drawable.btn_lb);
                        }else{
                            edittextweight.setVisibility(View.GONE);
                            edittextWeightInLb.setVisibility(View.GONE);
                            edittextWeightInST.setVisibility(View.VISIBLE);
                            edittextWeightInSTLb.setVisibility(View.VISIBLE);
                            buttonweight.setImageResource(R.drawable.btn_st_lb);
                        }

                    }

                });


            }
        });

        //Button onclick for final calculation OF BMI,BMR,FAT,IDEAL WEIGHT
        butttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validation for radiobutton if not checked
                if (radioGroupSex.getCheckedRadioButtonId() == -1 ){
                    Toast.makeText(MainActivity.this, "Please Select Gender", Toast.LENGTH_LONG).show();
                } else if(radioGroupHeight.getCheckedRadioButtonId() == -1 ) {
                    Toast.makeText(MainActivity.this, "Please Select Height", Toast.LENGTH_LONG).show();
                }else if(radioGroupWeight.getCheckedRadioButtonId() == -1 ){
                    Toast.makeText(MainActivity.this, "Please Select Weight", Toast.LENGTH_LONG).show();
                }
                else {
                    //getting input from edittext and storing in to varibales
                    heightValue = radioButtonHeight.getText().toString().trim();
                    WeightValue = radioButtonWeight.getText().toString().trim();

                    if (heightValue.equals("CM")) {
                        if (WeightValue.equals("KG") || WeightValue.equals("")) {

                            //Validation for Edittext  if is blank
                            if(edittextAge.getText().toString().equals(""))
                            {
                                edittextAge.setError("Enter Age");
                            }else if(edittextheight.getText().toString().equals("")){
                                edittextheight.setError("Enter Height");
                            }
                            else if(edittextweight.getText().toString().equals("")){
                                edittextweight.setError("Enter Weight");

                            }
                            else {
                                //getting input from edittext and storing in to varibales
                                strAge = edittextAge.getText().toString();
                                strWeight = edittextweight.getText().toString();
                                strHeight = edittextheight.getText().toString();

                                //converting values from String to Float
                                float ageInYears = Integer.parseInt(strAge);
                                float weight = Float.parseFloat(strWeight);
                                float height = Float.parseFloat(strHeight) / 100;
                                int height1 = Integer.parseInt(strHeight);

                                //Calculate BMI  and BMR value and FAT and Ideal Weight
                               // float bmiValue = calculateBMI(weight, height);
                                //Calculate BMI  and BMR value
                                float bmiValue = calculateBMI(weight, height);
                                float bmrValue = calculateBMR(weight,  height1 , ageInYears);
                                float FATValue = calculateFAT(ageInYears, bmiValue);
                                int IdealWeightValue = calculateIdealWeight(height1);
                                Log.d("bmrvalue", String.valueOf(bmrValue));

                                //Define the meaning of the bmi value
                                String bmiInterpretation = interpretBMI(bmiValue);
                                int bmiInterpretationColor=interpretBMICOLOR(bmiValue);
                                String FatInterpretation = interpretFAT(FATValue);
                                int FatInterpretationColor=interpretFATCOLOR(FATValue);



                                //set value to the textview
                                DecimalFormat f = new DecimalFormat("##.00");
                                textviewbmi.setText(f.format(bmiValue));
                                textviewbmiinterpret.setText(String.valueOf(bmiInterpretation));
                                textviewbmiinterpret.setTextColor(bmiInterpretationColor);
                                textviewbmr.setText(f.format(bmrValue));
                                TextViewbmrfake.setText("-");
                                textviewFAT.setText(String.valueOf(Math.round(FATValue)) + "%");
                                textviewFATinterpret.setText(String.valueOf(FatInterpretation));
                                textviewFATinterpret.setTextColor(FatInterpretationColor);
                                textViewIdealWeight.setText(String.valueOf(IdealWeightValue) + "kg");
                                TextViewIdealWeightfake.setText("Required");
                            }
                        }
                        else if (WeightValue.equals("LB")) {

                            //Validation for Edittext  if is blank
                            if(edittextAge.getText().toString().equals(""))
                            {
                                edittextAge.setError("Enter Age");
                            }
                            else if(edittextheight.getText().toString().equals("")){
                                edittextheight.setError("Enter Height");
                            }else if( edittextWeightInLb.getText().toString().equals("")){
                                edittextWeightInLb.setError("Enter Weight");

                            }
                            else {
                                //getting input from edittext and storing in to varibales
                                strAge = edittextAge.getText().toString();
                                strWeight = edittextWeightInLb.getText().toString();
                                strHeight = edittextheight.getText().toString();

                                //converting weight pounds to kilogram
                                float pounds = Float.parseFloat(strWeight);
                                float kilograms = (float) (pounds * 0.454);


                                //Get the user values from the widget reference
                                float ageInYears = Integer.parseInt(strAge);
                                float weight = kilograms;
                                float height = Float.parseFloat(strHeight) / 100;
                                int height1 = Integer.parseInt(strHeight);
                                //Calculate BMI  and BMR value
                                float bmiValue = calculateBMI(weight, height);
                                float bmrValue = calculateBMR(weight,  height1 , ageInYears);
                                float FATValue = calculateFAT(ageInYears, bmiValue);
                                int IdealWeightValue = calculateIdealWeight(height1);
                                Log.d("bmrvalue", String.valueOf(bmrValue));

                                //Define the meaning of the bmi value
                                String bmiInterpretation = interpretBMI(bmiValue);
                                int bmiInterpretationColor=interpretBMICOLOR(bmiValue);
                                String FatInterpretation = interpretFAT(FATValue);
                                int FatInterpretationColor=interpretFATCOLOR(FATValue);



                                //set value to the textview
                                DecimalFormat f = new DecimalFormat("##.00");
                                textviewbmi.setText(f.format(bmiValue));
                                textviewbmiinterpret.setText(String.valueOf(bmiInterpretation));
                                textviewbmiinterpret.setTextColor(bmiInterpretationColor);
                                textviewbmr.setText(f.format(bmrValue));
                                TextViewbmrfake.setText("-");
                                textviewFAT.setText(String.valueOf(Math.round(FATValue)) + "%");
                                textviewFATinterpret.setText(String.valueOf(FatInterpretation));
                                textviewFATinterpret.setTextColor(FatInterpretationColor);
                                textViewIdealWeight.setText(String.valueOf(IdealWeightValue) + "kg");
                                TextViewIdealWeightfake.setText("Required");
                            }
                        }
                        else {
                            //Validation for Edittext  if is blank
                            if (edittextAge.getText().toString().equals("")) {
                                edittextAge.setError("Enter Age");
                            }
                            else if (edittextheight.getText().toString().equals("")) {
                                edittextheight.setError("Enter Height");
                            }else if (edittextWeightInST.getText().toString().equals("")) {
                                edittextWeightInST.setError("Enter Weight ST");

                            } else if (edittextWeightInSTLb.getText().toString().equals("")) {
                                edittextWeightInSTLb.setError("Enter Weight LB");
                            }
                            else {
                                //getting input from edittext and storing in to varibales
                                strAge = edittextAge.getText().toString();
                                String strWeightST = edittextWeightInST.getText().toString();
                                String strWeightSTLb = edittextWeightInSTLb.getText().toString();
                                strHeight = edittextheight.getText().toString();

                                //converting weight stones and pounds to kilogram
                                float stones = Float.parseFloat(strWeightST);
                                float pounds = Float.parseFloat(strWeightSTLb);
                                float kilograms = (float) ((stones * 6.350) + (pounds * 0.454));


                                //Get the user values from the widget reference
                                float ageInYears = Integer.parseInt(strAge);
                                float weight = kilograms;
                                float height = Float.parseFloat(strHeight) / 100;
                                int height1 = Integer.parseInt(strHeight);
                                //Calculate BMI  and BMR value
                                float bmiValue = calculateBMI(weight, height);
                                float bmrValue = calculateBMR(weight,  height1 , ageInYears);
                                float FATValue = calculateFAT(ageInYears, bmiValue);
                                int IdealWeightValue = calculateIdealWeight(height1);
                                Log.d("bmrvalue", String.valueOf(bmrValue));

                                //Define the meaning of the bmi value
                                String bmiInterpretation = interpretBMI(bmiValue);
                                int bmiInterpretationColor=interpretBMICOLOR(bmiValue);
                                String FatInterpretation = interpretFAT(FATValue);
                                int FatInterpretationColor=interpretFATCOLOR(FATValue);



                                //set value to the textview
                                DecimalFormat f = new DecimalFormat("##.00");
                                textviewbmi.setText(f.format(bmiValue));
                                textviewbmiinterpret.setText(String.valueOf(bmiInterpretation));
                                textviewbmiinterpret.setTextColor(bmiInterpretationColor);
                                textviewbmr.setText(f.format(bmrValue));
                                TextViewbmrfake.setText("-");
                                textviewFAT.setText(String.valueOf(Math.round(FATValue)) + "%");
                                textviewFATinterpret.setText(String.valueOf(FatInterpretation));
                                textviewFATinterpret.setTextColor(FatInterpretationColor);
                                textViewIdealWeight.setText(String.valueOf(IdealWeightValue) + "kg");
                                TextViewIdealWeightfake.setText("Required");
                            }
                        }

                    }
                    else {
                        if (WeightValue.equals("KG") || WeightValue.equals("")) {

                            //Validation for Edittext  if is blank
                            if(edittextAge.getText().toString().equals(""))
                            {edittextAge.setError("Enter Age");
                            }else if(edittextfeet.getText().toString().equals("")){
                                edittextfeet.setError("Enter Feet");
                            }else if(edittextInch.getText().toString().equals("")){
                                edittextInch.setError("Enter Inch");
                            } else if(edittextweight.getText().toString().equals("")){
                                edittextweight.setError("Enter Weight");
                            }
                            else {
                                //getting input from edittext and storing in to varibales
                                //ft+in
                                strAge = edittextAge.getText().toString();
                                strWeight = edittextweight.getText().toString();
                                strHeigthfeet = edittextfeet.getText().toString();
                                strHeightInch = edittextInch.getText().toString();

                                //Get the user values from the widget reference
                                float ageInYears = Integer.parseInt(strAge);
                                float weight = Float.parseFloat(strWeight);
                                //  float waist = Float.parseFloat(strWaist);

                                //converting feet and inches to centimetrs
                                float heightfeet = Float.parseFloat(strHeigthfeet);
                                float heightinches = Float.parseFloat(strHeightInch);
                                float heightfeetvalue = (float) ((heightfeet * 30.48) + (heightinches * 2.54));

                                //Calculate BMI  and BMR value
                                float bmiValue = calculateBMI(weight, (heightfeetvalue / 100));
                                float bmrValue = calculateBMR(weight, heightfeetvalue, ageInYears);
                                float FATValue = calculateFAT(ageInYears, bmiValue);
                                int IdealWeightValue = calculateIdealWeight(Math.round(heightfeetvalue));
                                Log.d("bmrvalue", String.valueOf(bmrValue));

                                //Define the meaning of the bmi value
                                String bmiInterpretation = interpretBMI(bmiValue);
                                int bmiInterpretationColor=interpretBMICOLOR(bmiValue);
                                String FatInterpretation = interpretFAT(FATValue);
                                int FatInterpretationColor=interpretFATCOLOR(FATValue);



                                //set value to the textview
                                DecimalFormat f = new DecimalFormat("##.00");
                                textviewbmi.setText(f.format(bmiValue));
                                textviewbmiinterpret.setText(String.valueOf(bmiInterpretation));
                                textviewbmiinterpret.setTextColor(bmiInterpretationColor);
                                textviewbmr.setText(f.format(bmrValue));
                                TextViewbmrfake.setText("-");
                                textviewFAT.setText(String.valueOf(Math.round(FATValue)) + "%");
                                textviewFATinterpret.setText(String.valueOf(FatInterpretation));
                                textviewFATinterpret.setTextColor(FatInterpretationColor);
                                textViewIdealWeight.setText(String.valueOf(IdealWeightValue) + "kg");
                                TextViewIdealWeightfake.setText("Required");
                            }
                        }
                        else if (WeightValue.equals("LB")) {
                            //Validation for Edittext  if is blank
                            if(edittextAge.getText().toString().equals(""))
                            {
                                edittextAge.setError("Enter Age");
                            }else if(edittextfeet.getText().toString().equals("")){
                                edittextfeet.setError("Enter Feet");
                            }else if(edittextInch.getText().toString().equals("")){
                                edittextInch.setError("Enter Inch");
                            } else if( edittextWeightInLb.getText().toString().equals("")){
                                edittextWeightInLb.setError("Enter Weight");
                            }
                            else {
                                //getting input from edittext and storing in to varibales
                                //ft+in
                                strAge = edittextAge.getText().toString();
                                strWeight = edittextWeightInLb.getText().toString();
                                strHeigthfeet = edittextfeet.getText().toString();
                                strHeightInch = edittextInch.getText().toString();

                                //converting pounds to kilogram
                                float pounds = Float.parseFloat(strWeight);
                                float kilograms = (float) (pounds * 0.454);

                                //Get the user values from the widget reference
                                float ageInYears = Integer.parseInt(strAge);
                                float weight = kilograms;
                                float heightfeet = Float.parseFloat(strHeigthfeet);
                                float heightinches = Float.parseFloat(strHeightInch);
                                float heightfeetvalue = (float) ((heightfeet * 30.48) + (heightinches * 2.54));
                                //Calculate BMI  and BMR value
                                float bmiValue = calculateBMI(weight, (heightfeetvalue / 100));
                                float bmrValue = calculateBMR(weight, heightfeetvalue, ageInYears);
                                float FATValue = calculateFAT(ageInYears, bmiValue);
                                int IdealWeightValue = calculateIdealWeight(Math.round(heightfeetvalue));
                                Log.d("bmrvalue", String.valueOf(bmrValue));

                                //Define the meaning of the bmi value
                                String bmiInterpretation = interpretBMI(bmiValue);
                                int bmiInterpretationColor=interpretBMICOLOR(bmiValue);
                                String FatInterpretation = interpretFAT(FATValue);
                                int FatInterpretationColor=interpretFATCOLOR(FATValue);



                                //set value to the textview
                                DecimalFormat f = new DecimalFormat("##.00");
                                textviewbmi.setText(f.format(bmiValue));
                                textviewbmiinterpret.setText(String.valueOf(bmiInterpretation));
                                textviewbmiinterpret.setTextColor(bmiInterpretationColor);
                                textviewbmr.setText(f.format(bmrValue));
                                TextViewbmrfake.setText("-");
                                textviewFAT.setText(String.valueOf(Math.round(FATValue)) + "%");
                                textviewFATinterpret.setText(String.valueOf(FatInterpretation));
                                textviewFATinterpret.setTextColor(FatInterpretationColor);
                                textViewIdealWeight.setText(String.valueOf(IdealWeightValue) + "kg");
                                TextViewIdealWeightfake.setText("Required");
                            }
                        }
                        else {

                            //Validation for Edittext  if is blank
                            if(edittextAge.getText().toString().equals(""))
                            {
                                edittextAge.setError("Enter Age");
                            }else if(edittextfeet.getText().toString().equals("")){
                                edittextfeet.setError("Enter Feet");
                            }else if(edittextInch.getText().toString().equals("")){
                                edittextInch.setError("Enter Inch");
                            } else if (edittextWeightInST.getText().toString().equals("")) {
                                edittextWeightInST.setError("Enter Weight ST");

                            } else if (edittextWeightInSTLb.getText().toString().equals("")) {
                                edittextWeightInSTLb.setError("Enter Weight LB");
                            }
                            else {

                                //getting input from edittext and storing in to varibales
                                strAge = edittextAge.getText().toString();
                                String strWeightST = edittextWeightInST.getText().toString();
                                String strWeightSTLb = edittextWeightInSTLb.getText().toString();
                                strHeigthfeet = edittextfeet.getText().toString();
                                strHeightInch = edittextInch.getText().toString();

                                //converting stones and pounds to kilogram
                                float stones = Float.parseFloat(strWeightST);
                                float pounds = Float.parseFloat(strWeightSTLb);
                                float kilograms = (float) ((stones * 6.350) + (pounds * 0.454));

                                //Get the user values from the widget reference
                                float ageInYears = Integer.parseInt(strAge);
                                float weight = kilograms;

                                //  float waist = Float.parseFloat(strWaist);
                                float heightfeet = Float.parseFloat(strHeigthfeet);
                                float heightinches = Float.parseFloat(strHeightInch);
                                float heightfeetvalue = (float) ((heightfeet * 30.48) + (heightinches * 2.54));

                                //Calculate BMI  and BMR value
                                float bmiValue = calculateBMI(weight, (heightfeetvalue / 100));
                                float bmrValue = calculateBMR(weight, heightfeetvalue, ageInYears);
                                float FATValue = calculateFAT(ageInYears, bmiValue);
                                int IdealWeightValue = calculateIdealWeight(Math.round(heightfeetvalue));
                                Log.d("bmrvalue", String.valueOf(bmrValue));

                                //Define the meaning of the bmi value
                                String bmiInterpretation = interpretBMI(bmiValue);
                                int bmiInterpretationColor=interpretBMICOLOR(bmiValue);
                                String FatInterpretation = interpretFAT(FATValue);
                                int FatInterpretationColor=interpretFATCOLOR(FATValue);



                                //set value to the textview
                                DecimalFormat f = new DecimalFormat("##.00");
                                textviewbmi.setText(f.format(bmiValue));
                                textviewbmiinterpret.setText(String.valueOf(bmiInterpretation));
                                textviewbmiinterpret.setTextColor(bmiInterpretationColor);
                                textviewbmr.setText(f.format(bmrValue));
                                TextViewbmrfake.setText("-");
                                textviewFAT.setText(String.valueOf(Math.round(FATValue)) + "%");
                                textviewFATinterpret.setText(String.valueOf(FatInterpretation));
                                textviewFATinterpret.setTextColor(FatInterpretationColor);
                                textViewIdealWeight.setText(String.valueOf(IdealWeightValue) + "kg");
                                TextViewIdealWeightfake.setText("Required");
                            }
                        }
                    }

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
        } else{
            idealweight = (height - 100 - ((height - 150) / 2));
        }
        return (int) (idealweight+1);
    }

    //Calculate Ideal BMR
    private float calculateBMR(float weight, float height, float ageInYears) {

        int offset = radioButtonSex.getText().toString().trim().equals("Male") ? MALEOFFSET : FEMALEOFFSET;
        return (float) ((10 * weight) + (6.25 * height) - (5 * ageInYears) + offset);
    }
    //Calculate Ideal FAT
    private float calculateFAT(float ageInYears, float bmiValue) {
        float FAT_percentage;
        if(radioButtonSex.getText().toString().trim().equals("Male")) {
            FAT_percentage= (float) ((1.20 * bmiValue) + (0.23 * ageInYears) - (10.8 * 1) - 5.4);
        }else {
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
    // Interpret what BMI COLOR
    private int interpretBMICOLOR(float bmiValue) {

        if (sexValue == "Male") {
            if (bmiValue < 16) {
                return Color.YELLOW;
            } else if (bmiValue < 18.5) {
                return Color.YELLOW;
            } else if (bmiValue < 25) {
                return Color.GREEN;
            } else if (bmiValue < 30) {
                return Color.RED;
            } else if (bmiValue < 35) {
                return  Color.RED;
            } else if (bmiValue < 40) {
                return  Color.RED;
            } else {
                return  Color.RED;
            }
        } else {
            if (bmiValue < 17.5) {
                return  Color.YELLOW;
            } else if (bmiValue < 24.9) {
                return Color.GREEN;
            } else if (bmiValue < 40) {
                return Color.RED;
            } else {
                return Color.RED;
            }

        }
    }

    // Interpret what FAT means
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

    // Interpret what FAT COLOR
    private int interpretFATCOLOR(float FATValue) {

        if (sexValue == "Female") {
            if (FATValue < 14) {
                return Color.YELLOW;
            } else if (FATValue < 24) {

                return Color.GREEN;
            } else if (FATValue < 31) {

                return Color.GREEN;
            } else {

                return Color.RED;
            }
        } else {
            if (FATValue < 6) {
                return Color.YELLOW;
            } else if (FATValue < 17) {

                return Color.GREEN;
            } else if (FATValue < 25) {

                return Color.GREEN;
            } else {

                return Color.RED;
            }
        }

    }

    //used this when mobile orientaion is changed
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            //Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

    //Menus Declaration and intialisation
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