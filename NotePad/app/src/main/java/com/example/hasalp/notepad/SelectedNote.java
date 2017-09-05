package com.example.hasalp.notepad;

/**
 * Created by hasalp on 02.09.2017.
 */

public class SelectedNote {
    private static String title="",note="";

    public static void setTitle(String title) {
        SelectedNote.title = title;
    }

    public static void setNote(String note) {
        SelectedNote.note = note;
    }

    public static String getTitle() {
        return title;
    }

    public static String getNote() {
        return note;
    }
}
