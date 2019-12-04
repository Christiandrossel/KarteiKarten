package com.dude.karteikarten;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FireBaseManager {

    static FirebaseDatabase database;
    static DatabaseReference reference;
    static CardObject cardObject;
    static String text;
    static ArrayList<CardObject> cardObjectsList;
    static ArrayList<String> titleList = new ArrayList<String>();

    public FireBaseManager() {
        //Connect to Firebase
        database = FirebaseDatabase.getInstance();
        //reference = database.getReference("Deck01");
        //reference.child("Card01").setValue("Beispieltext");
        cardObjectsList = new ArrayList<CardObject>();
    }

    public String getText() {


        //addListenerForSingleValueEvent()  //Einmaliges lesen aus datenbank
        return text;
    }

    //Lesen aus der Datenbank und alle Objekte zurückgeben
    public ArrayList getCardtsList(String referencePfad){
        reference = database.getReference(referencePfad);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    cardObject = d.getValue(CardObject.class);
                    cardObjectsList.add(cardObject);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return cardObjectsList;
    }

    public void setText(String refTxt, String childTxt, String text) {
        DatabaseReference ref = database.getReference(refTxt);
        ref.child(childTxt).setValue(text);
        this.text = text;
    }

    /**
     * Static Klassen
     */

    public static void getStartFirebaseConnection(){
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        cardObjectsList = new ArrayList<CardObject>();
    }

    public static ArrayList<String> getTitleList(){
        database = FirebaseDatabase.getInstance();
         reference = database.getReference();


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i=0;
                for(DataSnapshot childDataSnapshot : dataSnapshot.getChildren()){
                    String var = (String) childDataSnapshot.getKey();
                    boolean isAdded = titleList.add(var);
                    Log.i("Firebase List added:", Boolean.toString(isAdded));
                    Log.i("DataVar", titleList.get(i));
                    Log.i("DataVar_Size", Integer.toString(titleList.size()));
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("The read failed: " ,databaseError.getMessage());
            }
        });

        for(int i = 0; i < titleList.size(); i++){
            Log.d("titleList", titleList.get(i).toString());
        }

        int size = titleList.size();
        Log.d("DataVar_Size_after-that", Integer.toString(titleList.size()));

        return titleList;
    }

    public static ArrayList<?> getCardObjectsList(String lessonTitle){
        database=FirebaseDatabase.getInstance();
        reference = database.getReference(lessonTitle);  //Bei diesen Beispiel reicht der Erste Pfad aus, dessen Kinder werden dann ausgelesen
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i=0;
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    CardObject c = d.getValue(CardObject.class);				//Das CardObject ist das entsprechende Objekt das aus String id, Frage, Antwort sowie getter und setter Methoden besteht
                    //Log.i("ID ", c.getId());
                    cardObjectsList.add(c);
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("The read failed: " ,databaseError.getMessage());
            }
        });

        //Toast.makeText(this, ""+cardObjectsList.size(), Toast.LENGTH_SHORT).show(); //Fuktioniert nicht gibt 0 zurück
        //cardObject = cardObjectArrayList.get(id);

        //Toast.makeText(this, ""+cardObject.getFrage(), Toast.LENGTH_SHORT).show();
        return cardObjectsList;
    }
}
