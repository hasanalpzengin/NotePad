package com.example.hasalp.notepad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    private static EditText titleText;
    private static EditText noteText;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        titleText = (EditText) findViewById(R.id.titleEditText);
        noteText = (EditText) findViewById(R.id.noteEditText);

        submit = (Button) findViewById(R.id.applyEdit);

        UpdateContent();

        buttonHandler();

    }

    private void buttonHandler() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBCrud crud = new DBCrud(getApplicationContext());
                Boolean isSuccess = crud.update(EditNote.getId(), titleText.getText().toString(), noteText.getText().toString());
                if(!isSuccess)
                    Log.e("Database Error", "Error while executing update sql");

                Intent toBack = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(toBack);

                try {
                    crud.finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }

    public static void UpdateContent(){
        Log.i("Edit Item Selected","You Set the Title to " + EditNote.getTitle());

        titleText.setText(EditNote.getTitle());
        noteText.setText(EditNote.getNote());
    }
}
