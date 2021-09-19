package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.TablesInfo.AllTablesInfo;
import com.example.myproject.TablesInfo.DbHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView signup ;
    EditText user;
    EditText pass;
    TextView tvuser;
    TextView tvpass;
    DbHelper dbHelper;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signup = findViewById(R.id.tvSign);
        user = findViewById(R.id.etUser);
        pass = findViewById(R.id.etPass);
        tvuser = findViewById(R.id.tvUser);
        tvpass = findViewById(R.id.tvPass);
        login = findViewById(R.id.btLogin);
        dbHelper = new DbHelper(getApplicationContext());
        login.setOnClickListener(this);
        signup.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        tvuser.setVisibility(View.INVISIBLE);
        tvpass.setVisibility(View.INVISIBLE);
        if(view.getId() == R.id.tvSign){
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        }
        if(view.getId() == R.id.btLogin){
            Cursor c = getUsers();
            if(!c.moveToFirst()){
                tvuser.setVisibility(View.VISIBLE);
            }
            else if(!c.getString(0).equals(pass.getText().toString())){
                tvpass.setVisibility(View.VISIBLE);
            }
            else {
                Intent intent = new Intent(this, Edit1.class);
                startActivity(intent);
            }
        }
    }
    public Cursor getUsers(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                AllTablesInfo.LogIn.COLUMN_PASSWORD
        };

// Filter results WHERE "title" = 'My Title'
        String selection = AllTablesInfo.LogIn.COLUMN_USER_NAME + " = ?";
        String[] selectionArgs = { user.getText().toString() };

        Cursor cursor = db.query(
                AllTablesInfo.LogIn.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        return cursor;
    }
}