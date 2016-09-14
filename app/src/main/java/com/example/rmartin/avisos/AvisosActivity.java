package com.example.rmartin.avisos;

import android.app.Dialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class AvisosActivity extends AppCompatActivity {

    private ListView mListView;
    // private AvisosDBAdapter mAvisosDBAdapter;
    private AvisosSimpleCursorAdapter mCursorAdapter;
    private AvisosDBAdapter mDbAdapter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mClient;

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
        if (savedInstanceState == null) {
            mDbAdapter.deleteAllReminders();
            mDbAdapter.createReminder("Visitar el Centro de Recogida de los amigos del pueblo de Valladolid", true);
            mDbAdapter.createReminder("Enviar los regalos prometidos", false);
            mDbAdapter.createReminder("Hacer la compra semanal", false);
            mDbAdapter.createReminder("Comprobar el correo", false);
        }

        Cursor cursor = mDbAdapter.fetchAllReminders();

        String[] from = new String[]{
                AvisosDBAdapter.COL_CONTENT
        };

        int[] to = new int[]{R.id.row_text};

        mCursorAdapter = new AvisosSimpleCursorAdapter(
                AvisosActivity.this,
                R.layout.avisos_row,
                cursor,
                from,
                to,
                0);

        mListView.setAdapter(mCursorAdapter);

        mListView.setOnItemClickListener((masterParent, masterView, masterListPosition, masterId) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(AvisosActivity.this);
            ListView modeListView = new ListView(AvisosActivity.this);
            String[] modes = new String[]{"Editar Aviso", "Borrar Aviso"};
            ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(AvisosActivity.this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, modes);
            modeListView.setAdapter(modeAdapter);
            builder.setView(modeListView);
            final Dialog dialog = builder.create();
            dialog.show();

            modeListView.setOnItemClickListener((parent, view, position, id) -> {
                //editar aviso
                if (position == 0) {
                    Toast.makeText(AvisosActivity.this, "editar " + masterListPosition, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AvisosActivity.this, "borrar " + masterListPosition, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            });

        });


        // cuando pulsamos en un item individual en la listview

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AvisosActivity.this, "pulsado " + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_avisos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean status = false;
        switch (item.getItemId()) {
            case R.id.action_nuevo:
                Log.d(getLocalClassName(), "Crear Nuevo");
                status = true;
            case R.id.action_salir:
                finish();
                status = true;
            default:
                status = false;
        }
        return status;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
