package com.example.at3_mydata;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.CharArrayWriter;

public class MainActivity extends AppCompatActivity {

    private Button btnAdd, btnView;

    private EditText nomText, prenomText;
    MyDataHelper mdh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.button);
        btnView = (Button) findViewById(R.id.btnView);
        nomText = (EditText) findViewById(R.id.editTextNom);
        prenomText = (EditText) findViewById(R.id.editTextPrenom);
        mdh = new MyDataHelper(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom, prenom;

                nom = nomText.getText().toString();
                prenom = prenomText.getText().toString();
                if(nom.length() == 0){
                    toastMessage("Remplir le nom stp");
                }else if(prenom.length() == 0){
                    toastMessage("Remplir le prenom stp");
                }else {
                    mdh.addData(nom, prenom);
                    toastMessage("Remplir succeés!");

                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewData.class);
                startActivity(intent);
            }
        });
    }

    public void addData(String nom, String prenom){
        boolean insertedData = mdh.addData(nom, prenom);

        if(insertedData){
            toastMessage("Bien enregistrer");
        }else {
            toastMessage("Erreur!");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}

class MyDataHelper extends SQLiteOpenHelper {

    private static final String table_name = "MYDATA";
    private static final String TAG = "MyDataHelper";
    private static final String col_1 = "NOM";
    private static final String col_2 = "PRENOM";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + table_name + " (" +
                    "ID INTEGER PRIMARY KEY," +
                    col_1 + " TEXT," +
                    col_2 + "  TEXT)";

    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + table_name;


    public MyDataHelper(@Nullable Context context) {
        super(context, table_name, null, 1);
    }

    public MyDataHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_DELETE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean addData(String nom, String prenom){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_1, nom);
        contentValues.put(col_2, prenom);

        Log.d(TAG, "addData: Adding " + nom + " - " + prenom + table_name);

        long result = db.insert(table_name, null, contentValues);

        return result != -1;

    }


    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + table_name;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
