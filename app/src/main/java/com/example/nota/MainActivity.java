package com.example.nota;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity implements NoteAdapter.Onitemclicklistner {
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference notes=db.collection("Notes");
    NoteAdapter noteAdapter;
    FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=findViewById(R.id.floating_addnote);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , AddNote.class));
            }
        });
        setupRecyclerview();

    }

    private void setupRecyclerview() {
        Query query=notes.orderBy("priority",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Note> options=new  FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query, Note.class)
                .build();
        noteAdapter=new NoteAdapter(options);
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(noteAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteAdapter.deleteitem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);
    }
    @Override
    protected void onStart() {
        super.onStart();
        noteAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        noteAdapter.stopListening();
    }

    @Override
    public void onclick(DocumentSnapshot documentSnapshot, int postion) {
     Note note=documentSnapshot.toObject(Note.class);
        Intent intent=new Intent(MainActivity.this , NoteDetails.class);
        intent.putExtra("title",note.getTitle());
        intent.putExtra("desc", note.getDescription());
        startActivity(intent);
    }
}
