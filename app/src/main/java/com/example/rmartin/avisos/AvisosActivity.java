package com.example.rmartin.avisos;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AvisosActivity extends AppCompatActivity {

    private ListView mListView;
    private AvisosDBAdapter mAvisosDBAdapter;
    private AvisosSimpleCursorAdapter mCursorAdapter;
    private AvisosDBAdapter mDbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisos);
        mListView = (ListView) findViewById(R.id.avisos_list_view);
        findViewById(R.id.avisos_list_view);
        mListView.setDivider(null);
        mDbAdapter = new AvisosDBAdapter(this);
        mDbAdapter.open();

        // DB Default
        if(savedInstanceState == null){
            mDbAdapter.deleteAllReminders();
            mDbAdapter.createReminder("Visitar el Centro de Recogida", true);
            mDbAdapter.createReminder("Enviar los regalos prometidos", false);
            mDbAdapter.createReminder("Hacer la compra semanal", false);
            mDbAdapter.createReminder("Comprobar el correo", false);


        }

        Cursor cursor = mDbAdapter.fetchAllReminders();

        String[] from = new String[]{
          AvisosDBAdapter.COL_CONTENT
        };

        int[] to =  new int[]{
                R.id.row_text
        };

        mCursorAdapter = new AvisosSimpleCursorAdapter(
          AvisosActivity.this,
                R.layout.avisos_row,
                cursor,
                from,
                to,
                0);

        mListView.setAdapter(mCursorAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_avisos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_nuevo:
                Log.d(getLocalClassName(), "Crear Nuevo");
                return true;
            case R.id.action_salir:
                finish();
                return true;
            default:
                return false;
        }
    }
}
