package com.example.waichun.restaurant;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class Restaurant extends AppCompatActivity {

    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<String> arrayLocation = new ArrayList<>();
    private ArrayList<String> arrayURI = new ArrayList<>();
    private ArrayList<String> suffleLocation = new ArrayList<>();
    private ArrayList<Integer> list=new ArrayList<>();

    private ACProgressFlower dialog;

    private ArrayList<TextView> tv;
    private ArrayList<ImageView> iv;

    private LinearLayout ll1;
    private LinearLayout ll2;
    private LinearLayout ll3;

    private String selectedRestaurant;

    private boolean click;

    private Button btn1;

    private Random rand = new Random();
    private int randNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        // Firebase initialize
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference myRef = db.getReference();

        // Hide all UI to have greater display experience
        // other option : hide root linear layout
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        btn1 = (Button) findViewById(R.id.btn1);

        ll1.setVisibility(View.GONE);
        ll2.setVisibility(View.GONE);
        ll3.setVisibility(View.GONE);
        btn1.setVisibility(View.GONE);


        // button first time click = false
        click=false;

        // loading dialog
        dialog = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Loading")
                .fadeColor(Color.DKGRAY).build();

        dialog.show();

        // initialize arraylist
        tv = new ArrayList<TextView>();
        iv=new ArrayList<ImageView>();
        for (int i = 0; i < 9; i++) {
            suffleLocation.add("");
        }

        for (int id : text) {
            TextView m = (TextView) findViewById(id);
            tv.add(m);
        }

        for (int id : img) {
            ImageView image = (ImageView) findViewById(id);
            iv.add(image);
        }

        // firebase function, retrieve data
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String value = dataSnapshot.child("Name").getValue(String.class);
                String location = dataSnapshot.child("Location").getValue(String.class);
                String uri = dataSnapshot.child("Image").getValue(String.class);
                arrayList.add(value);
                arrayLocation.add(location);
                arrayURI.add(uri);

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

        // facebase function, done retrieve data
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                addRestaurant();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        // button function, a constraint here, button only able click once
        // set click to true as button had been clicked once
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(click)
                    Toast.makeText(getApplicationContext(),"You Going To Have "+selectedRestaurant+" later.",Toast.LENGTH_LONG).show();
                else
                    selectRestaurant();
                click=true;
            }
        });



    }


    //this function add restaurant to the view
    private void addRestaurant() {

        // add every node as index to the array list
        // 0, 1, 2, 3, 4, .....

        for (int i = 0; i < 9; i++) {
            list.add(i);
        }

        // Shuffle the array list
        // 2, 3, 6, 1, 9, .....
        Collections.shuffle(list);

        // select the first 9 node
        // set the textview and imageview and location accordingly
        for (int i = 0; i <9; i++) {
            tv.get(list.get(i)).setText(arrayList.get(i));
            Glide.with(getApplicationContext()).load(arrayURI.get(i)).into(iv.get(list.get(i)));
            suffleLocation.set(list.get(i),arrayLocation.get(i));
        }

        // set laylout visible
        ll1.setVisibility(View.VISIBLE);
        ll2.setVisibility(View.VISIBLE);
        ll3.setVisibility(View.VISIBLE);

        btn1.setVisibility(View.VISIBLE);


    }


    // randomly select from 0-8
    // set imageview of randNum to clickable to open URI location
    private void selectRestaurant() {
        tv.get(randNum).setTextColor(Color.BLACK);
        randNum = rand.nextInt(9);
        tv.get(randNum).setTextColor(Color.RED);

        selectedRestaurant=tv.get(randNum).getText().toString();
        iv.get(randNum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse(suffleLocation.get(randNum));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }


    // in develop
    /*
    private void random() {

        ArrayList<Integer> list = new ArrayList<Integer>();

        for (int i = 0; i < firebaseChild; i++)
            list.add(i);

        Collections.shuffle(list);

        for (int i = 0; i < 9; i++)
            randomNumber.add(list.get(i));

    }
    */

    // Reference to TextView
    private static final int[] text = {
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

    // Reference to ImageView
    private static final int[]img={
            R.id.iv1,
            R.id.iv2,
            R.id.iv3,
            R.id.iv4,
            R.id.iv5,
            R.id.iv6,
            R.id.iv7,
            R.id.iv8,
            R.id.iv9,
    };

}
