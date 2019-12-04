package com.dude.karteikarten;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CardObjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_object);
        //addCard();
        //getCard();
        getCards();
    }
    //Objekt in die Datenbank schreiben
    public void addCard(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Thema");

        CardObject cards = new CardObject("01", "Theorie des trichromantischen Sehen", "Jeder Farbeindruck kann durch zusammenwirken dreier Primärfarben im roten, grünen und blauen Teil des Spektrums erzeugt werden.");
        CardObject cards01 = new CardObject("02", "Farbscala", "Jeder Farbeindruck kann durch zusammenwirken dreier Primärfarben im roten, grünen und blauen Teil des Spektrums erzeugt werden.");
        CardObject cards02 = new CardObject("03", "3 Eigenschaften der Farben", "Jeder Farbeindruck kann durch zusammenwirken dreier Primärfarben im roten, grünen und blauen Teil des Spektrums erzeugt werden.");
        CardObject cards03 = new CardObject("04", "Lichtheorie", "Jeder Farbeindruck kann durch zusammenwirken dreier Primärfarben im roten, grünen und blauen Teil des Spektrums erzeugt werden.");
        CardObject cards04 = new CardObject("05", "Helmotz Satz", "Jeder Farbeindruck kann durch zusammenwirken dreier Primärfarben im roten, grünen und blauen Teil des Spektrums erzeugt werden.");
        CardObject cards05 = new CardObject("06", "Metamerie", "Jeder Farbeindruck kann durch zusammenwirken dreier Primärfarben im roten, grünen und blauen Teil des Spektrums erzeugt werden.");

        ref.push().setValue(cards);
        ref.push().setValue(cards01);
        ref.push().setValue(cards02);
        ref.push().setValue(cards03);
        ref.push().setValue(cards04);
        ref.push().setValue(cards05);
    }
    //Objekt aus Datenbank lesen
    public void getCard(){
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Thema/-LotOx4LLcJIcvKMrVJA");  //Bei diesen Beispiel muss hier der genaue Pfad des Objektes aus der DB rein
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CardObject c = dataSnapshot.getValue(CardObject.class);
                Log.i("daten ", c.getAntwort());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    //Gibt eine Liste von Objekten
    public void getCards(){
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Thema");  //Bei diesen Beispiel muss hier der genaue Pfad des Objektes aus der DB rein
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    CardObject c = d.getValue(CardObject.class);
                    Log.i("ID ", c.getId());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void childEvent(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Thema");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                CardObject c = dataSnapshot.getValue(CardObject.class);
                String id = s;
                Log.i("Daten", s + c.getFrage());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
