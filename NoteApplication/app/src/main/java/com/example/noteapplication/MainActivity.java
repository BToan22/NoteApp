    package com.example.noteapplication;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.app.AlertDialog;
    import androidx.constraintlayout.widget.ConstraintLayout;
    import androidx.recyclerview.widget.RecyclerView;
    import androidx.recyclerview.widget.StaggeredGridLayoutManager;

    import android.content.Intent;
    import android.database.Cursor;
    import android.os.Bundle;
    import android.text.Editable;
    import android.text.TextWatcher;
    import android.view.LayoutInflater;
    import android.view.MenuItem;
    import android.view.MotionEvent;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.LinearLayout;
    import android.widget.PopupMenu;

    import com.example.noteapplication.Adapter.NoteAdapter;
    import com.example.noteapplication.Domain.Note;

    import java.util.ArrayList;

    public class MainActivity extends AppCompatActivity {
        ImageView them;
        RecyclerView list;

        NoteAdapter noteAdapter;
        DbHelper db;
        ArrayList<String> title, datetime, content, nid;

        ConstraintLayout chinh;
        private static final int NOTE_ACTIVITY_REQUEST_CODE = 1; // Request code for starting NoteActivity

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            them = findViewById(R.id.add);
            list = findViewById(R.id.listtNode);


            chinh = findViewById(R.id.main);
            db = new DbHelper(this);

            // Tạo adapter và đặt nó vào RecyclerView
            noteAdapter = new NoteAdapter(this, db.getALLNote());
            list.setAdapter(noteAdapter);

            // Đặt layout manager
            list.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

            displaydata();



            them.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                    startActivityForResult(intent, NOTE_ACTIVITY_REQUEST_CODE);
                }
            });

            noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    // Lấy dữ liệu cho phần tử đã nhấp vào
                    Note clickedNote = noteAdapter.getItemAtPosition(position);

                    // Truyền dữ liệu đến NoteActivity
                    Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                    intent.putExtra("noteId", clickedNote.getN_ID()); // Pass noteId to NoteActivity
                    intent.putExtra("title", clickedNote.getTITLE());
                    intent.putExtra("content", clickedNote.getNOTETEXT());
                    startActivityForResult(intent, NOTE_ACTIVITY_REQUEST_CODE);
                }
            });

            noteAdapter.setOnItemLongClickListener(new NoteAdapter.OnItemLongClickListener() {
                @Override
                public void onItemLongClick(int position, View view) {
                    Note clickedNote = noteAdapter.getItemAtPosition(position);
                    showPopupMenu(clickedNote, view);
                }
            });
        }

        private void displaydata() {
            title = new ArrayList<>();
            datetime = new ArrayList<>();
            content = new ArrayList<>();
            nid = new ArrayList<>();
//            status= new ArrayList<>();
            Cursor cursor = db.getdataNote();
            int idColumnIndex = cursor.getColumnIndex("N_ID");
            int titleColumnIndex = cursor.getColumnIndex("title");
            int datetimeColumnIndex = cursor.getColumnIndex("datetime");
            int contentColumnIndex = cursor.getColumnIndex("notetext");

            while (cursor.moveToNext()) {
                int id = cursor.getInt(idColumnIndex);
                String noteTitle = cursor.getString(titleColumnIndex);
                String noteDatetime = cursor.getString(datetimeColumnIndex);
                String noteContent = cursor.getString(contentColumnIndex);

                Note note = new Note(id, noteTitle, noteDatetime, noteContent);
                nid.add(String.valueOf(id));
                title.add(noteTitle);
                datetime.add(noteDatetime);
                content.add(noteContent);
            }
            cursor.close();
        }

        private void showPopupMenu(Note note, View view) {
            if (note != null) {
                LayoutInflater inflater = LayoutInflater.from(this);
                View popupView = inflater.inflate(R.layout.menu, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(popupView);
                AlertDialog dialog = builder.create();

                dialog.show();
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                LinearLayout deleteLayout = popupView.findViewById(R.id.xoaaaaa);
                LinearLayout blockLayout = popupView.findViewById(R.id.blockkkk);

                deleteLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.deleteNote(note.getN_ID());

                        noteAdapter.updatenote(db.getALLNote());
                        dialog.dismiss();
                    }
                });

            }
        }
    }
