package nl.martijndorsman.studiecheck.models;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import nl.martijndorsman.studiecheck.ViewAdapter;
import nl.martijndorsman.studiecheck.database.DatabaseAdapter;
import nl.martijndorsman.studiecheck.database.DatabaseHelper;
import nl.martijndorsman.studiecheck.models.CourseModel;

/**
 * Created by Martijn on 25/06/17.
 */

public class Vakkenlijst {

    Context context;
    public ArrayList<CourseModel> courses;
    public Vakkenlijst(Context context){
        this.context = context;
    }


    public void retrieve(String tabel, Context context, ViewAdapter adapter, RecyclerView rv) {
        DatabaseHelper helper;
        DatabaseAdapter dbAdapter;
        courses = new ArrayList<>();
        RecyclerView rv;
        dbAdapter = new DatabaseAdapter(context);
        dbAdapter.openDB();
        courses.clear();

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
            //Voeg toe aan de ArrayList
            courses.add(p);
        }
        //Controleer of de ArrayList leeg is
        if (!(courses.size() < 1)) {
            rv.setAdapter(adapter);
        }
        c.close();
        dbAdapter.closeDB();
    }
}
