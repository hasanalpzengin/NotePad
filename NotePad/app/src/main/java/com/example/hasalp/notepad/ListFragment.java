package com.example.hasalp.notepad;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    SwipeMenuListView listView;
    public HashMap<Integer, Integer> idList = new HashMap<Integer, Integer>();
    SwipeMenuCreator creator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        listView = (SwipeMenuListView) view.findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, listCreator()));
        initSwipeMenu();
        listView.setMenuCreator(creator);
        listView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {
                listView.smoothOpenMenu(position);
            }

            @Override
            public void onSwipeEnd(int position) {

            }
        });
        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        swipeButtonClicked_Handler();

        return view;
    }

    @Override
    public void onResume() {
        listView.setAdapter(new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, listCreator()));
        super.onResume();
    }

    private ArrayList<String> listCreator() {
        idList.clear();
        ArrayList<String> titles = new ArrayList<>();
        DBCrud crud = new DBCrud(this.getContext());

        String[][] datas = crud.getAll();

        for (int i = 0; i < datas.length; i++) {
            titles.add(datas[i][1]);
            idList.put(i, Integer.parseInt(datas[i][0]));
        }

        try {
            crud.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return titles;
    }

    private void initSwipeMenu() {
        creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(90);
                // set item title
                openItem.setTitle(R.string.edit);
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(90);
                // set a icon
                deleteItem.setIcon(android.R.drawable.ic_menu_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
    }

    private void deleteMessage(int pos) {
        DBCrud crud = new DBCrud(this.getContext());
        crud.remove(idList.get(pos));
        this.onResume();
        Toast toast = Toast.makeText(this.getContext(), "Note Deleted", Toast.LENGTH_LONG);
        try {
            crud.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void swipeButtonClicked_Handler() {
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                if (index == 1) {
                    deleteMessage(position);
                } else if (index == 0) {
                    editMessage(position);
                }

                Log.i("BUTTON CLICKED", "Pos = " + position + ", Index = " + index);
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                DBCrud crud = new DBCrud(getContext());

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    ViewPager pager = (ViewPager) getActivity().findViewById(R.id.mainPane);
                    pager.setCurrentItem(1);
                }

                crud.setNote(idList.get(pos));

                try {
                    crud.finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }

    public void editMessage(int pos) {
        DBCrud crud = new DBCrud(this.getContext());
        crud.setEditNote(idList.get(pos));
        Intent editActivity = new Intent(getContext(), EditActivity.class);
        startActivity(editActivity);
    }
}
