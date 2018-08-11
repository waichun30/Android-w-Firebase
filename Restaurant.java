package com.example.waichun.restaurant;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Restaurant extends AppCompatActivity {

    private ArrayList<String>arrayList=new ArrayList<>();
    private ArrayList<String>arrayLocation=new ArrayList<>();
    ArrayList<TextView>tv;
    TextView tw;
    Button btn1;
    String a;
    int count=1;
    boolean t=true;
    Random rand=new Random();
    int randNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference myRef=db.getReference();

        tv = new ArrayList<TextView>();
        for (int id : text) {
            TextView m = (TextView) findViewById(id);
            tv.add(m);
        }

                myRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String key=dataSnapshot.getKey();
                        String value=dataSnapshot.child("Name").getValue(String.class);
                        String location=dataSnapshot.child("Location").getValue(String.class);
                        arrayList.add(value);
                        arrayLocation.add(location);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        btn1=(Button)findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRestaurant();

            }
        });
    }


    private static final int[]text={
            R.id.tv1,
            R.id.tv2,
            R.id.tv3,
            R.id.tv4,
            R.id.tv5,
            R.id.tv6,
            R.id.tv7,
            R.id.tv8,
            R.id.tv9,
    };

    //this function add
    private void addRestaurant(){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < arrayList.size(); i++) {
            list.add(i);
        }
        Collections.shuffle(list);

        for(int i=8;i>=0;i--){
            tv.get(i).setText(arrayList.get(list.get(i)));
        }
        selectRestaurant();
    }

    private void selectRestaurant(){
        tv.get(randNum).setTextColor(Color.BLACK);
        randNum=rand.nextInt(9);
        tv.get(randNum).setTextColor(Color.RED);
    }

}
