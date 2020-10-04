package com.example.miniproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

public class UpdateDetails extends AppCompatActivity {
    public static final String TAG = "AddReqBlood";
    private EditText up_name,up_city,up_dob,up_blood_group,up_phone,up_duration;
    private Button up_btn,ret_btn;
    private DatabaseReference dbRef;
    private AwesomeValidation awesomeValidation;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);



        up_name  = findViewById(R.id.up_name);
        up_city  = findViewById(R.id.up_city);
        up_dob   = findViewById(R.id.up_dob);
        up_blood_group = findViewById(R.id.up_blood_group);
        up_phone = findViewById(R.id.up_c_no);
        up_duration = findViewById(R.id.up_duration);

        up_btn = findViewById(R.id.up_btn);
        ret_btn = findViewById(R.id.ret_btn);

        up_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(UpdateDetails.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
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
                up_dob.setText(date);
            }
        };

        ret_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("RequestBlood").child("D1");
                dbRef = FirebaseDatabase.getInstance().getReference().child("RequestBlood").child("D2");
                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = snapshot.child("name").getValue().toString();
                        String city = snapshot.child("city").getValue().toString();
                        String dob = snapshot.child("dob").getValue().toString();
                        String blood_group = snapshot.child("blood").getValue().toString();
                        String phone = snapshot.child("phone").getValue().toString();
                        String duration = snapshot.child("duration").getValue().toString();

                        up_name.setText(name);
                        up_city.setText(city);
                        up_dob.setText(dob);
                        up_blood_group.setText(blood_group);
                        up_phone.setText(phone);
                        up_duration.setText(duration);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this,R.id.name, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.city, RegexTemplate.NOT_EMPTY,R.string.invalid_city);
        awesomeValidation.addValidation(this,R.id.blood_group, RegexTemplate.NOT_EMPTY,R.string.invalid_blood);
        awesomeValidation.addValidation(this,R.id.c_no, "[0-9]{10}$",R.string.invalid_mobile);
        awesomeValidation.addValidation(this,R.id.duration, RegexTemplate.NOT_EMPTY,R.string.invalid_duration);




        up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate()){
                    String name = up_name.getText().toString();
                    String city = up_city.getText().toString();
                    String dob = up_dob.getText().toString();
                    String blood= up_blood_group.getText().toString();
                    String phone = up_phone.getText().toString();
                    String duration = up_duration.getText().toString();

                    dbRef = FirebaseDatabase.getInstance().getReference().child("RequestBlood");

                    HashMap hashMap = new HashMap();

                    hashMap.put("name",name);
                    hashMap.put("city",city);
                    hashMap.put("dob",dob);
                    hashMap.put("blood",blood);
                    hashMap.put("phone",phone);
                    hashMap.put("duration",duration);

                    dbRef.child("D1").updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {

                            Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(),NeedHelp.class);
                            startActivity(i);
                        }

                    });


                }
                else

                    Toast.makeText(getApplicationContext(),"Validation Failed",Toast.LENGTH_SHORT).show();


            }
        });



    }
}