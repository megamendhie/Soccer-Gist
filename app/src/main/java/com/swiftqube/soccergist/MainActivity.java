package com.swiftqube.soccergist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

import datafiles.Message;
import datafiles.MessageAdapter;

public class MainActivity extends AppCompatActivity {
    FirebaseUser user;
    FirebaseFirestore database;
    Query query;
    private FirestoreRecyclerAdapter<Message, MessageAdapter.MessageHolder> adapter;
    private FloatingActionButton btnSend;
    private RecyclerView list;
    private MultiAutoCompleteTextView input;
    private ProgressBar pgBar;
    private String userId, message, userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSend = findViewById(R.id.btnSend);
        input = findViewById(R.id.input);
        pgBar = findViewById(R.id.loader);
        list = findViewById(R.id.list);

        //Check if user has signed in before opening chatroom. Else redirect to login page
        user = FirebaseAuth.getInstance().getCurrentUser();
        userName = user.getDisplayName();
        if(user==null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        userId = user.getUid();
        database = FirebaseFirestore.getInstance();
        query = database.collection("messages");
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(!queryDocumentSnapshots.isEmpty()){
                    pgBar.setVisibility(View.GONE);
                }
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = input.getText().toString();
                if(TextUtils.isEmpty(message)){
                    Toast.makeText(MainActivity.this, "Post is post", Toast.LENGTH_LONG);
                    return;
                }
                database.collection("messages").add(new Message(userName, message, userId, 0, null));
            }
        });

        adapter = new MessageAdapter(MainActivity.this,query, userId);
        list.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(adapter!=null){
            adapter.stopListening();
        }
    }
}
