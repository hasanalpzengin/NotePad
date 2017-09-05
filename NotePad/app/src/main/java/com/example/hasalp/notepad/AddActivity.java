package com.example.hasalp.notepad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        add_button = (Button) findViewById(R.id.button);

        eventHandler();
    }

    private void eventHandler(){

        final DBCrud dbCrud = new DBCrud(getApplicationContext());
        final EditText titleEdit = (EditText) findViewById(R.id.titleEdit);
        final EditText messageEdit = (EditText) findViewById(R.id.messageEdit);


        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleEdit.getText().toString().isEmpty()){
                    dbCrud.insert("No Title", messageEdit.getText().toString());
                }else{
                    dbCrud.insert(titleEdit.getText().toString(), messageEdit.getText().toString());
                }

                Toast toast = Toast.makeText(getApplicationContext(), "NOTE INSERTED", Toast.LENGTH_SHORT);
                try {
                    dbCrud.finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    Log.e("finalize_dbCRUD","ERROR while Finalize DBCRUD class");
                }

                Intent mainScreen = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainScreen);
            }
        });
    }
}
