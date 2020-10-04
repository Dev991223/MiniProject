package com.example.miniproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowDetails extends AppCompatActivity {

    TextView tx_name,tx_dob,tx_city,tx_blood,spinblood,tx_phone,tx_duration;
    ImageView back_img_2;
    Button up_btn,del_btn,home_btn;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        back_img_2 = (ImageView) findViewById(R.id.backimgview2);

        back_img_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Open_add_details();
            }
        });

        final Intent intent = getIntent();
        String ntxt = intent.getStringExtra(AddReqBlood.Name);
        String ctxt = intent.getStringExtra(AddReqBlood.City);
        String dotxt = intent.getStringExtra(AddReqBlood.Date_O_Birth);
        String btxt = intent.getStringExtra(AddReqBlood.BloodGro);
        String bspin = intent.getStringExtra(AddReqBlood.BloodGro2);
        String ptxt = intent.getStringExtra(AddReqBlood.Phone);
        String dtxt = intent.getStringExtra(AddReqBlood.Duration);

        tx_name = (TextView) findViewById(R.id.nametx);
        tx_city = (TextView) findViewById(R.id.citytx);
        tx_dob = (TextView) findViewById(R.id.dob_tx);
        tx_blood = (TextView) findViewById(R.id.bloodtx);
        spinblood = (TextView) findViewById(R.id.bloodspin);
        tx_phone = (TextView) findViewById(R.id.phonetx);
        tx_duration = (TextView)findViewById(R.id.durationtx);

        tx_name.setText(ntxt);
        tx_city.setText(ctxt);
        tx_dob.setText(dotxt);
        tx_blood.setText(btxt);
        spinblood.setText(bspin);
        tx_phone.setText(ptxt);
        tx_duration.setText(dtxt);

        del_btn = (Button)findViewById(R.id.dltbtn);
        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference del_Ref = FirebaseDatabase.getInstance().getReference().child("RequestBlood");
                del_Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild("D1")){
                            dbRef = FirebaseDatabase.getInstance().getReference().child("RequestBlood").child("D1");
                            dbRef.removeValue();

                            Toast.makeText(getApplicationContext(), "Data Deleted Successfully",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), AddReqBlood.class);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "No Source To Delete",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        up_btn = (Button)findViewById(R.id.upd_btn);
        up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Open_update_Details();
            }
        });

        home_btn = (Button)findViewById(R.id.homebtn);
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Open_ReqBlood();
            }
        });
    }

    private void Open_ReqBlood() {
        Intent i3 = new Intent(this,NeedHelp.class);
    }


    private void Open_add_details() {
        Intent i2 = new Intent(this, AddReqBlood.class);
        startActivity(i2);
    }

    private void Open_update_Details() {
        Intent i = new Intent(this,UpdateDetails.class);
        startActivity(i);
    }
}