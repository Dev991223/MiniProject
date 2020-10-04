package com.example.miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddReqBlood extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "AddReqBlood";
    public static final String Name = "com.example.miniproject.example.Name";
    public static final String City = "com.example.miniproject.example.City";
    public static final String Date_O_Birth = "com.example.miniproject.example.Date_O_Birth";
    public static final String BloodGro2 = "com.example.miniproject.example.BloodGro2";
    public static final String Phone= "com.example.miniproject.example.Phone";
    public static final String Duration = "com.example.miniproject.example.Duration";



    EditText txtname,txtcity,txtphone,txtduration,txtdob;
    Spinner spinB;
    Button addbtn;
    ImageView back_need_help;
    DatabaseReference dbRef;
    NeedHelpJ ned;
    AwesomeValidation awesomeValidation;
    DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinB = (Spinner) findViewById(R.id.spin_blood_group_2);

        back_need_help = (ImageView) findViewById(R.id.backimgview);

        back_need_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenReqBlood();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Bloodgroup,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinB.setAdapter(adapter);
        spinB.setOnItemSelectedListener(this);;

        txtname =(EditText) findViewById(R.id.name);
        txtcity =(EditText) findViewById(R.id.city);
        txtphone =(EditText) findViewById(R.id.c_no);
        txtduration =(EditText) findViewById(R.id.duration);
        txtdob = (EditText) findViewById(R.id.date_o_B);

        addbtn = findViewById(R.id.confirmbtn);

        ned = new NeedHelpJ();



        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this,R.id.name, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.city, RegexTemplate.NOT_EMPTY,R.string.invalid_city);
        awesomeValidation.addValidation(this,R.id.date_o_B,RegexTemplate.NOT_EMPTY,R.string.invalid_Birthday);
        awesomeValidation.addValidation(this,R.id.spin_blood_group_2,RegexTemplate.NOT_EMPTY,R.string.Invalid_blood2);
        awesomeValidation.addValidation(this,R.id.c_no, "[0-9]{10}$",R.string.invalid_mobile);
        awesomeValidation.addValidation(this,R.id.duration, RegexTemplate.NOT_EMPTY,R.string.invalid_duration);

        txtdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddReqBlood.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG,"OnDateSet: mm/dd/yyyy" + year + "/" + month + "/" + day);
                String date = month + "/" + day + "/" + year;
                txtdob.setText(date);
            }
        };

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate()){
                    dbRef = FirebaseDatabase.getInstance().getReference().child("RequestBlood");

                    ned.setName(txtname.getText().toString().trim());
                    ned.setCity(txtcity.getText().toString().trim());
                    ned.setDob(txtdob.getText().toString().trim());
                    ned.setBlood2(spinB.getSelectedItem().toString().trim());
                    ned.setPhone(txtphone.getText().toString().trim());
                    ned.setDuration(txtduration.getText().toString().trim());
                    openShowDetails();

                    dbRef.child("D1").setValue(ned);


                    Toast.makeText(getApplicationContext(),"Data Inserted Successfully",Toast.LENGTH_SHORT).show();
                    clear_controls();



                }
                else {
                        Toast.makeText(getApplicationContext(),"Validation Failed",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void OpenReqBlood() {
        Intent i = new Intent (this,NeedHelp.class);
        startActivity(i);
    }

    private void openShowDetails() {
        EditText name_t = (EditText)findViewById(R.id.name);
        String name = name_t.getText().toString();

        EditText city_t =(EditText)findViewById(R.id.city);
        String city = city_t.getText().toString();

        EditText dob_t = (EditText)findViewById(R.id.date_o_B);
        String dob = dob_t.getText().toString();

        Spinner blood_s = (Spinner)findViewById(R.id.spin_blood_group_2);
        String blood2 = blood_s.getSelectedItem().toString();

        EditText phone_t =(EditText)findViewById(R.id.c_no);
        String phone = phone_t.getText().toString();

        EditText dur_t =(EditText)findViewById(R.id.duration);
        String dur = dur_t.getText().toString();

        Intent intent = new Intent(this, ShowDetails.class);

        intent.putExtra(Name,name);
        intent.putExtra(City,city);
        intent.putExtra(Date_O_Birth,dob);
        intent.putExtra(BloodGro2,blood2);
        intent.putExtra(Phone,phone);
        intent.putExtra(Duration,dur);
        startActivity(intent);
    }

    private void clear_controls(){
        txtname.setText("");
        txtcity.setText("");
        txtdob.setText("");
        txtphone.setText("");
        txtduration.setText("");

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}