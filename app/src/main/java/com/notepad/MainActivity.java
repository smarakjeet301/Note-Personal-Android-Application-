package com.notepad;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.note.R;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    NDb mydb;
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;
    Context context = this;
    SimpleCursorAdapter adapter;
    Snackbar snackbar;
    RelativeLayout relativeLayout;
    int id_To_Update = 0;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.add:
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);
                Intent intent = new Intent(getApplicationContext(),
                        addNotepad.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new NDb(this);

        listView=findViewById(R.id.list_item);



        relativeLayout=findViewById(R.id.rl);
        FloatingActionButton floatingactionbutton=findViewById(R.id.fab);

        floatingactionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);
                Intent intent = new Intent(getApplicationContext(),
                        addNotepad.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
                finish();
            }
        });
        Cursor c = mydb.fetchAll();
        String[] fieldNames = new String[] { NDb.name, NDb._id, NDb.dates, NDb.remark };
        int[] display = new int[] { R.id.txtnamerow, R.id.txtidrow,
                R.id.txtdate,R.id.txtremark };
        adapter = new SimpleCursorAdapter(this, R.layout.listtemplate, c, fieldNames,
                display, 0);

//        snackbar = Snackbar
//                .make(relativeLayout, "You are awesome I think!", Snackbar.LENGTH_LONG);
//        snackbar.show();

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout linearLayoutParent = (LinearLayout) view;
                LinearLayout linearLayoutChild = (LinearLayout) linearLayoutParent
                        .getChildAt(0);

                TextView m = (TextView) linearLayoutChild.getChildAt(1);
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id",
                        Integer.parseInt(m.getText().toString()));
                Intent intent = new Intent(getApplicationContext(),
                        addNotepad.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
                finish();
            }
        });




    }
}
