package nl.martijndorsman.studiecheck.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.widget.DefaultItemAnimator;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;


import nl.martijndorsman.studiecheck.ViewAdapter;
import nl.martijndorsman.studiecheck.database.DatabaseAdapter;

import static nl.martijndorsman.studiecheck.database.DatabaseInfo.CourseTables.Keuze;

/**
 * Created by Martijn on 25/06/17.
 */

public class Vakkenlijst {
    LinearLayoutManager mLayoutManager;
    ViewAdapter adapter;
    ECTS ects;
    SharedPreferences prefs;

    Context context;
    public ArrayList<CourseModel> courses;
    public Vakkenlijst(Context context){
        this.context = context;
        courses = new ArrayList<>();
    }

    public void create(String tabel, RecyclerView rv){
        mLayoutManager = new LinearLayoutManager(context);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setItemAnimator(new DefaultItemAnimator());
        ects = new ECTS(context);
        ects.getECTS(tabel);

        retrieve(tabel, context);
        adapter = new ViewAdapter(tabel, context, courses);
        rv.setAdapter(adapter);
        }

    public void retrieve(String tabel, Context context) {
        DatabaseAdapter dbAdapter;
        courses = new ArrayList<>();
        dbAdapter = new DatabaseAdapter(context);
        dbAdapter.openDB();
        courses.clear();
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String item1 = prefs.getString("item1", "");
        String item2 = prefs.getString("item2", "");
        String item3 = prefs.getString("item3", "");
        String item4 = prefs.getString("item4", "");
        Log.d("Keuzevakkenxxx2", item1);
        Cursor c = dbAdapter.getAllData(tabel);
        //Loop en voeg aan ArrayList toe
        while (c.moveToNext()) {
            String name = c.getString(0);
            String ects = c.getString(1);
            String period = c.getString(2);
            String grade = c.getString(3);
            String status = "Niet behaald";
            Double gradeDouble = Double.parseDouble(grade);
            if (gradeDouble>=5.5){
                status = "Behaald";
            }
            CourseModel p = new CourseModel(name, ects, period, grade, status);
            if(tabel == Keuze){
                if(name.equals(item1) || name.equals(item2) || name.equals(item3) || name.equals(item4)) {
                    //Voeg toe aan ArrayList
                    courses.add(p);
                    Log.d("Keuzevakkenxxx", item1);
                }
            }
            else {
                //Voeg toe aan de ArrayList
                courses.add(p);
            }
        }
        c.close();
        dbAdapter.closeDB();

    }
}
