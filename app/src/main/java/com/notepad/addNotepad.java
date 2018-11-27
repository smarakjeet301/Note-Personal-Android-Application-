package com.notepad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.note.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
public class addNotepad extends AppCompatActivity {
    private NDb mydb;
    EditText name;
    EditText content;
    private RelativeLayout coordinatorLayout;
    String dateString;
    Bundle extras;
    int id_To_Update = 0;
    Snackbar snackbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewnotepad);
        name = (EditText) findViewById(R.id.txtname);
        content = (EditText) findViewById(R.id.txtcontent);
        Button submit = findViewById(R.id.submit);
        Button back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(addNotepad.this,MainActivity.class));
            }
        });
        final RelativeLayout  relativeLayout=findViewById(R.id
                .coordinatorLayout);

        FloatingActionButton floatingActionButtondelete=findViewById(R.id.fabdelete);
        floatingActionButtondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(addNotepad.this);
                builder.setMessage("Are you sure?")
                        .setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        mydb.deleteNotes(id_To_Update);
                                        Toast.makeText(addNotepad.this, "Deleted Successfully",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(
                                                getApplicationContext(),
                                                MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                        .setNegativeButton("NO",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                    }
                                });
                AlertDialog d = builder.create();
//                d.setTitle("Are you sure");
                d.show();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = getIntent().getExtras();
                Calendar c = Calendar.getInstance();
                System.out.println("Current time => " + c.getTime());
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c.getTime());
                dateString = formattedDate;
                if (extras != null) {
                    int Value = extras.getInt("id");
                    if (Value > 0) {
                        if (content.getText().toString().trim().equals("")
                                || name.getText().toString().trim().equals("")) {
                            snackbar = Snackbar
                                    .make(relativeLayout, "Please fill in name of the note", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        } else {
                            if (mydb.updateNotes(id_To_Update, name.getText()
                                    .toString(), dateString, content.getText()
                                    .toString())) {
                                snackbar = Snackbar
                                        .make(relativeLayout, "Your note Updated Successfully!!!", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            } else {
                                snackbar = Snackbar
                                        .make(relativeLayout, "There's an error. That's all I can tell. Sorry!", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        }
                    } else {
                        if (content.getText().toString().trim().equals("")
                                || name.getText().toString().trim().equals("")) {
                            snackbar = Snackbar
                                    .make(relativeLayout, "Please fill in name of the note", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        } else {
                            if (mydb.insertNotes(name.getText().toString(), dateString,
                                    content.getText().toString())) {
                                snackbar = Snackbar
                                        .make(relativeLayout, "Added Successfully.", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            } else {
                                snackbar = Snackbar
                                        .make(relativeLayout, "Unfortunately Task Failed.", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        }
                    }
                }
            }
        });

        mydb = new NDb(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
//                snackbar = Snackbar
//                        .make(relativeLayout, "Note Id : "+String.valueOf(Value), Snackbar.LENGTH_LONG);
//                snackbar.show();
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();
                String nam = rs.getString(rs.getColumnIndex(NDb.name));
                String contents = rs.getString(rs.getColumnIndex(NDb.remark));
                if (!rs.isClosed()) {
                    rs.close();
                }
                name.setText((CharSequence) nam);
                content.setText((CharSequence) contents);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");

        }
        return true;
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(
                getApplicationContext(),
                MainActivity.class);
        startActivity(intent);
        finish();
        return;
    }
}