package com.dude.karteikarten;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LessonEditActivity extends AppCompatActivity {

    TextView textTitle;
    TextView activityTextView;
    String titleString;
    boolean isNewLesson = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_edit);


        titleString = getIntent().getExtras().getString("titleName");

        textTitle = (TextView)findViewById(R.id.TitleText);
        activityTextView = (TextView) findViewById(R.id.textView7);
        if(titleString != null) {   //Überprüfe ob neue Lektion Wenn dann keine Neue Lektion
            isNewLesson = false;
            textTitle.setText(titleString);
            activityTextView.setText("Titel bearbeiten");
        }
    }

    public void btnSaveClicked(View view){

        //text aus Textfelder holen
        String newTitle;
        newTitle = textTitle.getText().toString();
        //String in Datenbank Speichern
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        if(isNewLesson){
            ref.child(newTitle).setValue(newTitle);

            //CardObject.lessonTitle = newTitle;

            Toast.makeText(this, "Neue Lektion erstellt!", Toast.LENGTH_SHORT).show();

        }
        else if(isNewLesson == false){
            //TODO Aktuelle Lektion bearbeiten
            Toast.makeText(this, "Lektion bearbeitet", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, DeckEditActivity.class);
        intent.putExtra("titleName", newTitle);
        startActivity(intent);
    }
}
