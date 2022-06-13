package com.example.videocall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class showData_Activity extends AppCompatActivity {


    DatabaseReference myRef;
    RecyclerView recyclerView;

    FirebaseAuth myAuth;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        recyclerView = findViewById(R.id.recycler);

//        String currentloginId = getIntent().getStringExtra("currentloginId");
//        Toast.makeText(this, currentloginId, Toast.LENGTH_SHORT).show();


//        myAuth = FirebaseAuth.getInstance();
//        uid = myAuth.getCurrentUser().getUid();

        myRef = FirebaseDatabase.getInstance().getReference("User");
        ArrayList<MyData> arrayList = new ArrayList<>();
        myDataAdapter adapter = new myDataAdapter(showData_Activity.this, arrayList);




        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                     arrayList.add(snapshot.getValue(MyData.class));
                     recyclerView.setLayoutManager(new LinearLayoutManager(showData_Activity.this));
                }
                recyclerView.setAdapter(adapter);
            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
               // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


//        if(!uid.isEmpty()){
//            myRef.child(uid).addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    MyData myData = new MyData();
//                    myData = snapshot.getValue(MyData.class);
//                    Toast.makeText(showData_Activity.this, myData.getName()+"....", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }
    }
}