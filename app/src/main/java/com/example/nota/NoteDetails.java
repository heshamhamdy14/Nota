package com.example.nota;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NoteDetails extends AppCompatActivity {
    TextView title , description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        title=findViewById(R.id.title_details);
        description=findViewById(R.id.description_details);
        Bundle bundle=getIntent().getExtras();
        String Title=bundle.getString("title");
        String Desc=bundle.getString("desc");
        title.setText(Title);
        description.setText(Desc);

    }
}
