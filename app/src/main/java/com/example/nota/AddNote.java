package com.example.nota;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddNote extends AppCompatActivity {
    EditText addtitle, adddesc;
    NumberPicker numberPicker;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference reference=db.collection("Notes");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear);
        setTitle("Add Note");
        addtitle=findViewById(R.id.add_title);
        adddesc=findViewById(R.id.add_description);
        numberPicker=findViewById(R.id.number_picker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_save,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:
                savenote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void savenote() {
        String title=addtitle.getText().toString();
        String desc=adddesc.getText().toString();
        int priority=numberPicker.getValue();

        if (title.trim().isEmpty() || desc.trim().isEmpty()){
            Toast.makeText(this, "please enter title and description", Toast.LENGTH_SHORT).show();
            return;
        }
        Note note=new Note(title,desc,priority);
        reference.add(note);
        finish();
    }
}
