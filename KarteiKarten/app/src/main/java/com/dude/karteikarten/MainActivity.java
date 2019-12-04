package com.dude.karteikarten;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FireBaseManager.getTitleList();
        //Toast.makeText(this, "size: "+FireBaseManager.titleList.size(), Toast.LENGTH_SHORT).show();
    }

    public void buttonStartClick(View view){
        //Starte eine neue Activity
        Intent intent = new Intent(this, CardsActivity.class);
        startActivity(intent);
    }

    public void buttonEditClick(View view){
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }

    public void buttonEndClick(View view){
        System.exit(0);
    }

}
