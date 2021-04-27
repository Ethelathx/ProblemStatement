package com.example.problemstatement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class InfoMainScreen extends AppCompatActivity {

    //---------------------Initial Setting--------------
    Button btnRP, btnEmail, btnAdd;
    int requestCodeForC347 = 1;
    ListView lv;
    CustomAdapter aa;
    ArrayList<Module> module;
    //---------------------Initial Setting--------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_main_screen);


        //---------------------List View Implementation--------------
        lv = findViewById(R.id.lvShow);

        //Create Food item/object in Food array
        module = new ArrayList<>();
        module.add(new Module( 1, "B"));
        module.add(new Module( 2, "C"));
        module.add(new Module(3, "A"));

        //Link this Activity object, the row.xml layout for each
        //row and the food String array together
        aa = new CustomAdapter(this, R.layout.row, module);
        lv.setAdapter(aa);
        //---------------------List View Implementation--------------


        //---------------------Website Button--------------
        btnRP = findViewById(R.id.buttonInfo);
        btnRP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Intent to display data
                Intent rpIntent = new Intent(Intent.ACTION_VIEW);
                // Set the URL to be used.
                rpIntent.setData(Uri.parse("http://www.rp.edu.sg"));
                startActivity(rpIntent);
            }
        });
        //---------------------Website Button--------------


        //---------------------Email Button--------------
        btnEmail = (Button) findViewById(R.id.buttonEmail);
        btnEmail.setOnClickListener(new View.OnClickListener(){

            public String getResult(ArrayList<Module> array){
                String summary = "" ;
                for (int i = 0; i < module.size(); i++) {
                    summary += "Week " + (i + 1) + ". DG." + module.get(i).getModuleGrade() + "\n";
                }
                return summary;
            }

            @Override
            public void onClick(View arg0) {

                // The action you want this intent to do;
                // ACTION_SEND is used to indicate sending text
                Intent email = new Intent(Intent.ACTION_SEND);

                // Put essentials like email address, subject & body text
                email.putExtra(Intent.EXTRA_EMAIL,
                        new String[]{"jason_lim@rp.edu.sg"});
                email.putExtra(Intent.EXTRA_TEXT,
                        "Hi faci, \n\nI am... \nPlease see my remarks so far, thank you!\n\n " + getResult(module));
                // This MIME type indicates email
                email.setType("message/rfc822");
                // createChooser shows user a list of app that can handle
                // this MIME type, which is, email
                startActivity(Intent.createChooser(email,
                        "Choose an Email client :"));
            }});
        //---------------------Email Button--------------


        //---------------------Add Button--------------
        btnAdd = (Button) this.findViewById(R.id.buttonAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoMainScreen.this,addData.class);
                intent.putExtra("arrayCount",module.size()+1);
                startActivityForResult(intent,requestCodeForC347);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Only handle when 2nd activity closed normally
        //  and data contains something
        if(resultCode == RESULT_OK){
            if (data != null) {
                // Get data passed back from 2nd activity
                String grade = data.getStringExtra("grade");

                // If it is activity started by clicking
                //  Superman, create corresponding String
                if(requestCode == requestCodeForC347){
                    module.add(new Module(module.size()+1,grade));
                    aa.notifyDataSetChanged();
                }
                // If 2nd activity started by clicking
            }
        }
    }
    //---------------------Add Button--------------
}


