package com.example.hasalp.notepad;

/**
 * Created by hasalp on 04.09.2017.
 */

public class EditNote {
    private static String title, note;
    private static int id;

    public static String getTitle() {
        return title;
    }

    public static String getNote() {
        return note;
    }

    public static int getId() {
        return id;
    }

    public static void setTitle(String title) {
        EditNote.title = title;
    }

    public static void setNote(String note) {
        EditNote.note = note;
    }

    public static void setId(int id) {
        EditNote.id = id;
    }
}
