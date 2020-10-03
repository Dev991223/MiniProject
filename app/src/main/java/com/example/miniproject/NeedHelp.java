package com.example.miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NeedHelp extends AppCompatActivity {
    Button addDbtn,viewDbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_help);

        addDbtn =findViewById(R.id.add_D_btn);
        viewDbtn = findViewById(R.id.view_D_btn);


        addDbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenAddDetails();
            }
        });
        viewDbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Open_View_Req_D();
            }
        });
    }

    private void Open_View_Req_D() {
        Intent intent = new Intent(this,viewRequestBlood.class);
        startActivity(intent);
    }


    private void OpenAddDetails() {
        Intent intent = new Intent(this, AddReqBlood.class);
        startActivity(intent);
    }

}