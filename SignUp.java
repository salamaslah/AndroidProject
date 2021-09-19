package com.example.myproject;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.TablesInfo.AllTablesInfo;
import com.example.myproject.TablesInfo.DbHelper;

public class SignUp extends AppCompatActivity implements View.OnClickListener{
    EditText user;
    EditText pass;
    EditText retrypass;
    Button signup;
    TextView tvuser;
    TextView tvpass;
    DbHelper dbHelper ;
    TextView emptyuser;
    TextView emptypass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        user = findViewById(R.id.etUser);
        pass = findViewById(R.id.etnewPass);
        retrypass = findViewById(R.id.etretryPass);
        signup = findViewById(R.id.btSignup);
        tvuser = findViewById(R.id.tverroruser);
        tvpass = findViewById(R.id.tverrorpass);
        dbHelper = new DbHelper(getApplicationContext());
        emptypass = findViewById(R.id.tverrorpass2);
        emptyuser = findViewById(R.id.tverroruser2);
        signup.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        tvpass.setVisibility(View.INVISIBLE);
        tvuser.setVisibility(View.INVISIBLE);
        emptyuser.setVisibility(View.INVISIBLE);
        emptypass.setVisibility(View.INVISIBLE);
        if(user.getText().toString().isEmpty())emptyuser.setVisibility(View.VISIBLE);
        else if(pass.getText().toString().isEmpty()) emptypass.setVisibility(View.VISIBLE);
        else if(!pass.getText().toString().equals(retrypass.getText().toString()))
            tvpass.setVisibility(View.VISIBLE);
        else {
            Cursor c = getUsers();
            if(c.moveToFirst())
                tvuser.setVisibility(View.VISIBLE);
            else {
                AddUsers();
            }
        }

    }

    public void AddUsers(){
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(AllTablesInfo.LogIn.COLUMN_USER_NAME, user.getText().toString());
        values.put(AllTablesInfo.LogIn.COLUMN_PASSWORD, pass.getText().toString());

// Insert the new row, returning the primary key value of the new row
        long newRow = db.insert(AllTablesInfo.LogIn.TABLE_NAME, null, values);
        Toast.makeText(SignUp.this,"the user number "+newRow+" added successfully",Toast.LENGTH_LONG).show();
    }
    public Cursor getUsers(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                AllTablesInfo.LogIn.COLUMN_USER_NAME
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