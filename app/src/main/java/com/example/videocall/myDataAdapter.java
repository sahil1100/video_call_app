package com.example.videocall;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class myDataAdapter extends RecyclerView.Adapter<myDataAdapter.ViewHolder> {

    Context context;
    ArrayList<MyData> arrayList;
    FirebaseAuth myAuth;
    DatabaseReference myRef;
    String uid;


    public myDataAdapter(Context context, ArrayList<MyData> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.showdata_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyData user = arrayList.get(position);

        holder.name.setText(user.getName());
        holder.email.setText(user.getEmail());
        holder.pass.setText(user.getPassword());

        //Glide.with(context).load(user.getProfile()).into(holder.imgurl);


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myAuth = FirebaseAuth.getInstance();
                uid = myAuth.getCurrentUser().getUid();

                myRef = FirebaseDatabase.getInstance().getReference("User");


                if (!uid.isEmpty()) {
                    myRef.child(uid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            MyData myData = new MyData();
                            myData = snapshot.getValue(MyData.class);
                            String currentUserName = myData.getName();

                            Intent i = new Intent(context, CallActivity_sd.class);
                            i.putExtra("username", user.getName());
                            i.putExtra("currentUserName", currentUserName);
                            context.startActivity(i);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

//               // Toast.makeText(context, user.getUid()+" ", Toast.LENGTH_SHORT).show();
//
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView email, name, pass;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //context = itemView.getContext();
            email = itemView.findViewById(R.id.show_email);
            name = itemView.findViewById(R.id.show_name);
            pass = itemView.findViewById(R.id.show_pass);
            layout = itemView.findViewById(R.id.show_layout);

        }
    }
}
