package com.dude.karteikarten;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CardsActivity extends AppCompatActivity {

    ArrayList<String> list;
    ListView lv;
    ArrayAdapter<String> arrayAdapter;
    int id;

    ArrayList<CardObject> liste;
    ListAdapter adapter;

    public static final String[] model = {
            "Läd Daten aus der Datenbank...",
            "Läd Daten aus der Datenbank...",
            "Läd Daten aus der Datenbank...",
            "..."
    };

    Integer imageId = R.drawable.staple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        list = new ArrayList<String>();

        readData();

        lv = (ListView) findViewById(R.id.list_view);
        //lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , model));
        //lv.setAdapter(listAdapter);



        //liste = new ArrayList<>();
        //adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, liste);
    }

    public void readData(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot childDataSnapshot : dataSnapshot.getChildren()){
                    String var = (String) childDataSnapshot.getKey();
                    list.add((String) var);
                }
                //lv.setAdapter(arrayAdapter);
                //arrayAdapter.notifyDataSetChanged();
                for(int i=0; i<list.size();i++){
                    //Toast.makeText(CardsActivity.this, "Var: "+list.get(i), Toast.LENGTH_SHORT).show();
                    model[i] = list.get(i);
                }
                //lv.setAdapter(new ArrayAdapter<String>(CardsActivity.this, android.R.layout.simple_list_item_1 , list));
                CustomList listAdapter = new CustomList(CardsActivity.this, list, imageId);
                lv.setAdapter(listAdapter);
                lv.setClickable(true);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        String title = list.get(position);
                        Intent intent = new Intent(CardsActivity.this, LearnActivity.class);
                        Toast.makeText(CardsActivity.this, "You Clicked: "+position+ " "+list.get(position), Toast.LENGTH_SHORT).show();
                        intent.putExtra("titleName", title);
                        startActivity(intent);

                    }

                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("The read failed: " ,databaseError.getMessage());
            }
        });

        for(int i = 0; i < list.size(); i++){
            Log.d("DatabaseList", list.get(i).toString());
        }

        int size = list.size();
        Log.d("DatabaseLSize", String.valueOf(size));
    }






    public void btnClick(View view){
        id = 0;
        Intent intent = new Intent(this, LearnActivity.class);
        intent.putExtra("lesson", list.get(id));

        startActivity(intent);
    }
}
