package com.dude.karteikarten;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//Zum Anzeigen des Decks welches Bearbeitet werden soll
public class DeckEditActivity extends AppCompatActivity {

    String lessonTitle;
    TextView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_edit);

        lessonTitle = getIntent().getExtras().getString("titleName");

        titleView=(TextView) findViewById(R.id.titleTextView);
        titleView.setText(lessonTitle);

        //Toast.makeText(this, "Lektion: "+CardObject.lessonTitle, Toast.LENGTH_SHORT).show();

    }

    public void buttonLessonEditClicked(View view){
        Intent intent = new Intent(this, LessonEditActivity.class);
        intent.putExtra("titleName", lessonTitle);
        startActivity(intent);
    }

    public void buttonAddCardClicked(View view){
        Intent intent = new Intent(this, AddCardActivity.class);
        intent.putExtra("titleName", lessonTitle);
        startActivity(intent);
    }

    public void buttonDeleteCardClicked(View view){
        Intent intent = new Intent(this, AddCardActivity.class);
        intent.putExtra("titleName", lessonTitle);
        startActivity(intent);
    }

    public void buttonDeleteLesson(View view){
        Intent intent = new Intent(this, AddCardActivity.class);
        intent.putExtra("titleName", lessonTitle);
        startActivity(intent);
    }


}
