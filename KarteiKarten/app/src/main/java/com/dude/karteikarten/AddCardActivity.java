package com.dude.karteikarten;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCardActivity extends AppCompatActivity {

    String Frage;
    String Antwort;
    EditText FrageTextView;
    EditText AntwortTextView;
    TextView TitleTextView;
    String title;
    CardObject cardObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        FrageTextView = (EditText) findViewById(R.id.questionEditText);
        AntwortTextView = (EditText) findViewById(R.id.AntwortEditText);
        TitleTextView = (TextView) findViewById(R.id.titleTextView);

        title = getIntent().getExtras().getString("titleName");

        TitleTextView.setText(title);

    }

    private void setData(){
        Frage = FrageTextView.getText().toString();
        Antwort = AntwortTextView.getText().toString();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference(title);
        //ref.child("Frage").setValue(Frage);
        //ref.child("Antwort").setValue(Antwort);
        cardObject = new CardObject("01",Frage, Antwort);

        ref.push().setValue(cardObject);

        Toast.makeText(this, "Karte gespeichert!", Toast.LENGTH_SHORT).show();
    }

    public void saveBtnClicked(View view){
        setData();

        FrageTextView.setText("");
        AntwortTextView.setText("");
        
    }
}
