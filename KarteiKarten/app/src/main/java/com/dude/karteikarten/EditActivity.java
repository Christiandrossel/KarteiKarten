package com.dude.karteikarten;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity implements View.OnClickListener{

    FloatingActionButton fabMain, fabNewLesson;
    ListView lv;
    TextView newLessonText;
    Float translationY = 100f;

    ArrayList<String> list;

    OvershootInterpolator interpolator = new OvershootInterpolator();

    int id;

    private static final String TAG = "EditActivity";

    Boolean isMenuOpen = false;

    public static final String[] model = {
            "Läd Daten aus der Datenbank...",
            "Läd Daten aus der Datenbank...",
            "Läd Daten aus der Datenbank...",
            "..."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        initFabMenu();

        list = new ArrayList<String>();
        readData();
        lv = (ListView) findViewById(R.id.editListView);
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , model));

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
                for(int i=0; i<list.size();i++){
                    model[i] = list.get(i);
                }
                lv.setAdapter(new ArrayAdapter<String>(EditActivity.this, android.R.layout.simple_list_item_1 , list));
                lv.setClickable(true);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String title = list.get(position);
                        Intent intent = new Intent(EditActivity.this, DeckEditActivity.class);
                        Toast.makeText(EditActivity.this, "You Clicked: " + position + " " + list.get(position), Toast.LENGTH_SHORT).show();
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


    View.OnClickListener btnClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Object tag = v.getTag();
            Toast.makeText(getApplicationContext(), "clicked button", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(EditActivity.this, DeckEditActivity.class);
            //TODO ID mit übergebn

            startActivity(intent);
        }
    };

    private void initFabMenu(){
        fabMain = findViewById(R.id.fabMain);
        fabNewLesson = findViewById(R.id.fabNewLesson);
        newLessonText = findViewById(R.id.textView5);

        fabNewLesson.setAlpha(0f);
        newLessonText.setAlpha(0f);

        fabNewLesson.setTranslationY(translationY);

        fabMain.setOnClickListener(this);
        fabNewLesson.setOnClickListener(this);

    }

    private void openMenu(){
        isMenuOpen = !isMenuOpen;

        fabMain.animate().setInterpolator(interpolator).rotation(45f).setDuration(300).start();
        fabNewLesson.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        newLessonText.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
    }

    private void closeMenu(){
        isMenuOpen = !isMenuOpen;

        fabMain.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start();
        fabNewLesson.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        newLessonText.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.fabMain:
                if(isMenuOpen){
                    closeMenu();
                }else{
                    openMenu();
                }
                break;
            case R.id.fabNewLesson:
                Intent intent = new Intent(this, LessonEditActivity.class);
                intent.putExtra("titleString", "");
                startActivity(intent);
                break;
        }
    }

}
