package com.example.nota;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note , NoteAdapter.NoteHolder> {

    Onitemclicklistner listner;

    public void setListner(Onitemclicklistner listner) {
        this.listner = listner;
    }


    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Note model) {
        holder.title.setText(model.getTitle());
        holder.priority.setText(String.valueOf(model.getPriority()));
        holder.description.setText(model.getDescription());
    }

    public void deleteitem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item_row,parent,false);
        return new NoteHolder(view);
    }

    public class NoteHolder extends RecyclerView.ViewHolder {
        TextView title , priority , description;
        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            priority=itemView.findViewById(R.id.priority);
            description=itemView.findViewById(R.id.description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int postion=getAdapterPosition();
                    if (postion != RecyclerView.NO_POSITION && listner != null){
                        listner.onclick(getSnapshots().getSnapshot(postion) ,  postion);
                    }
                }
            });
        }
    }
    public interface Onitemclicklistner{
        public void onclick(DocumentSnapshot documentSnapshot , int postion);
    }
}
