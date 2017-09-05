package com.example.hasalp.notepad;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment {

    private static EditText titleText;
    private static EditText noteText;
    private static View fragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_content, container, false);

        titleText = (EditText) fragmentView.findViewById(R.id.titleEditText);
        noteText = (EditText) fragmentView.findViewById(R.id.noteEditText);

        UpdateContent();

        return fragmentView;
    }

    public static void UpdateContent(){

        LinearLayout content = fragmentView.findViewById(R.id.content);
        TextView textView = fragmentView.findViewById(R.id.notselected);

        if (SelectedNote.getTitle()==""){
            content.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.VISIBLE);
            Log.i("Empty", "Yeah it is");
        }else{
            content.setVisibility(View.VISIBLE);
            textView.setVisibility(View.INVISIBLE);
            titleText.setText(SelectedNote.getTitle());
            noteText.setText(SelectedNote.getNote());
        }
    }

}
