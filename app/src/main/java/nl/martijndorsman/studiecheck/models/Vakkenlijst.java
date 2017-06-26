package nl.martijndorsman.studiecheck.models;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;

import nl.martijndorsman.studiecheck.R;
import nl.martijndorsman.studiecheck.ViewAdapter;
import nl.martijndorsman.studiecheck.database.DatabaseAdapter;
import nl.martijndorsman.studiecheck.database.DatabaseHelper;
import nl.martijndorsman.studiecheck.models.CourseModel;

import static nl.martijndorsman.studiecheck.KeuzevakDialog.item1;
import static nl.martijndorsman.studiecheck.KeuzevakDialog.item2;
import static nl.martijndorsman.studiecheck.KeuzevakDialog.item3;
import static nl.martijndorsman.studiecheck.KeuzevakDialog.item4;
import static nl.martijndorsman.studiecheck.database.DatabaseInfo.CourseTables.Jaar1;
import static nl.martijndorsman.studiecheck.database.DatabaseInfo.CourseTables.Keuze;

/**
 * Created by Martijn on 25/06/17.
 */

public class Vakkenlijst {

    public RecyclerView rv;
    DatabaseAdapter dbAdapter;
    LinearLayoutManager mLayoutManager;
    public static int totaalECTSjaar1;
    public static int totaalECTSjaar2;
    public static int totaalECTSjaar3en4;
    public static int totaalECTSKeuze;
    ViewAdapter adapter;
    ECTS ects;

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
                }
            }
            else {
                //Voeg toe aan de ArrayList
                courses.add(p);
            }
        }
        //Controleer of de ArrayList leeg is
        c.close();
        dbAdapter.closeDB();

    }
}
