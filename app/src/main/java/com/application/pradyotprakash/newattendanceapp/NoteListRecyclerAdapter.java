package com.application.pradyotprakash.newattendanceapp;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.util.List;

public class NoteListRecyclerAdapter extends RecyclerView.Adapter<NoteListRecyclerAdapter.ViewHolder> {

    private List<NoteList> noteList;
    private Context context;
    private FirebaseAuth mAuth;
    private static String user_id;
    private String noteId, fileLink;
    private long queueId;
    private DownloadManager downloadManager;
    private FirebaseFirestore mFirestore;

    public NoteListRecyclerAdapter(List<NoteList> noteList, Context context) {
        this.noteList = noteList;
        this.context = context;
    }

    @Override
    public NoteListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item, parent, false);
        return new NoteListRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NoteListRecyclerAdapter.ViewHolder holder, final int position) {
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        String uploadedBy = noteList.get(position).getUploadedBy();
        mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("Faculty").document(uploadedBy).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        holder.noteUploadedBy.setText(task.getResult().getString("name"));
                    }
                }
            }
        });
        holder.noteName.setText(noteList.get(position).getTitle());
        holder.noteDescription.setText(noteList.get(position).getDescription());
        holder.noteUploadedOn.setText(noteList.get(position).getUploadedOn());
        BroadcastReceiver reciever1 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                    DownloadManager.Query request_query = new DownloadManager.Query();
                    request_query.setFilterById(queueId);
                    Cursor c = downloadManager.query(request_query);
                    if (c.moveToFirst()) {
                        int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                            String fileUri = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                            File mFile = new File(Uri.parse(fileUri).getPath());
                            String fileName = mFile.getAbsolutePath();
                            Toast.makeText(context, "File Stored in: " + fileName, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        };
        context.registerReceiver(reciever1, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteId = noteList.get(position).noteId;
                fileLink = noteList.get(position).getNoteLink();
                downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileLink));
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, noteList.get(position).getName());
                queueId = downloadManager.enqueue(request);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private TextView noteName, noteDescription, noteUploadedOn, noteUploadedBy;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            noteName = mView.findViewById(R.id.noteName);
            noteDescription = mView.findViewById(R.id.noteDescription);
            noteUploadedOn = mView.findViewById(R.id.noteUploadedOn);
            noteUploadedBy = mView.findViewById(R.id.noteUploadedBy);
        }
    }
}