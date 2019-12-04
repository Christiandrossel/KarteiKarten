package com.dude.karteikarten;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LearnActivity extends AppCompatActivity {

    CardView cardView;
    TextView cardText;
    EditText solutionText;
    Button buttonRight;
    Button buttonWrong;
    Button buttonSolution;
    LearnStats learnStats;
    CardObject cardObject;
    ArrayList<CardObject> cardObjectArrayList;
    FireBaseManager firebaseManager;
    FirebaseDatabase db;
    DatabaseReference ref;
    String antwort;
    String frage;

    int id;
    int fails;
    int rights;
    boolean side = false;

    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        title = getIntent().getExtras().getString("titleName");

        cardView = (CardView) findViewById(R.id.cardView);
        cardText = (TextView) findViewById(R.id.cardview_text);
        solutionText = (EditText) findViewById(R.id.editTextSolution);
        buttonRight = (Button) findViewById(R.id.buttonRight);
        buttonWrong = (Button) findViewById(R.id.buttonWrong);
        buttonSolution = (Button) findViewById(R.id.buttonSolution);

        buttonRight.setVisibility(View.INVISIBLE);
        buttonWrong.setVisibility((View.INVISIBLE));

        learnStats = new LearnStats();
        cardObject = new CardObject();
        cardObjectArrayList = new ArrayList<CardObject>();
        firebaseManager = new FireBaseManager();

        db = FirebaseDatabase.getInstance();

        String text ="das ist ein text";
        cardText.setText(text);
        findText(0);
        setCardText();
/**
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ObjectAnimator oa1 = ObjectAnimator.ofFloat(cardView, "scaleX", 1f, 0f);
                final ObjectAnimator oa2 = ObjectAnimator.ofFloat(cardView, "scaleX", 0f, 1f);
                oa1.setInterpolator(new DecelerateInterpolator());
                oa2.setInterpolator(new AccelerateDecelerateInterpolator());
                oa1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        //cardView.setImageResource(R.drawable.frontSide);
                        oa2.start();
                    }
                });
                oa1.start();
            }
        });
**/
    }

    public void findText(final int id){
        //TODO Text aus JSON oder DATABASE lesen und zurückgeben
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference(title);  //Bei diesen Beispiel reicht der Erste Pfad aus, dessen Kinder werden dann ausgelesen
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i=0;
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    CardObject c = d.getValue(CardObject.class);				//Das CardObject ist das entsprechende Objekt das aus String id, Frage, Antwort sowie getter und setter Methoden besteht
                    //Log.i("ID ", c.getId());
                    if(i==id){
                        cardText.setText(c.getFrage());
                        frage = c.getFrage();
                        antwort = c.getAntwort();
                        if(antwort.length() <= 41){
                            cardText.setTextSize(28f);
                        }
                        if(antwort.length() >= 60){
                            cardText.setTextSize(18f);
                        }
                    }
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //TODO Fehlerbehandlung
                Log.e("The read failed: " ,databaseError.getMessage());
            }
        });

        if(cardObjectArrayList.isEmpty()){
            cardObjectArrayList.addAll(firebaseManager.getCardtsList(title));
        }

        //Toast.makeText(this, ""+cardObjectArrayList.size(), Toast.LENGTH_SHORT).show(); //Fuktioniert nicht gibt 0 zurück
        //cardObject = cardObjectArrayList.get(id);

        //Toast.makeText(this, ""+cardObject.getFrage(), Toast.LENGTH_SHORT).show();
        //cardText.setText(cardObject.getFrage());
    }

    public void buttonSolutionClicked(View view){
        //Toast.makeText(this, "Antwort Klick", Toast.LENGTH_SHORT).show();
        //TODO Zeige Lösung an und mache Button INVISIBLE und die anderen beiden VISISBLE
        //solutionText.setText(antwort);
        setCardTextside();
    }
    public void setButtonRightClicked(View view){
        //Toast.makeText(this, "Wusste ich geklickt", Toast.LENGTH_SHORT).show();
        findText(id++);
        solutionText.setText("");
        buttonSolution.setVisibility(View.VISIBLE);
        buttonRight.setVisibility((View.INVISIBLE));
        buttonWrong.setVisibility(View.INVISIBLE);
        side = false;
    }
    public void setButtonWrongClicked(View view){
        //Toast.makeText(this, "Falsch geklickt", Toast.LENGTH_SHORT).show();
        findText(id++);
        solutionText.setText("");
        buttonSolution.setVisibility(View.VISIBLE);
        buttonRight.setVisibility((View.INVISIBLE));
        buttonWrong.setVisibility(View.INVISIBLE);
        side = false;
    }

    public void setCardText() {

        cardText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                setCardTextside();
                return false;
            }
        });
    }
    public void setCardTextside(){
        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(cardView, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(cardView, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //cardView.setImageResource(R.drawable.frontSide);

                oa2.start();
            }
        });
        oa1.start();


        if(side == false){
            side = true;
            cardText.setText(antwort);
            if(antwort.length() <= 41){
                cardText.setTextSize(28f);
            }
            if(antwort.length() >= 60){
                cardText.setTextSize(18f);
            }
            buttonSolution.setVisibility(View.INVISIBLE);
            buttonRight.setVisibility((View.VISIBLE));
            buttonWrong.setVisibility(View.VISIBLE);
        }
        else if(side == true){
            side = false;
            cardText.setText(frage);
            if(antwort.length() >= 41){
                cardText.setTextSize(24f);
            }
            if(antwort.length() >= 60){
                cardText.setTextSize(18f);
            }
            buttonSolution.setVisibility(View.INVISIBLE);
            buttonRight.setVisibility((View.VISIBLE));
            buttonWrong.setVisibility(View.VISIBLE);
        }
    }


}
