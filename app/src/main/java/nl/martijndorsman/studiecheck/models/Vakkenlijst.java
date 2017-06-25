package nl.martijndorsman.studiecheck.models;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import nl.martijndorsman.studiecheck.R;
import nl.martijndorsman.studiecheck.ViewAdapter;
import nl.martijndorsman.studiecheck.database.DatabaseAdapter;
import nl.martijndorsman.studiecheck.database.DatabaseHelper;
import nl.martijndorsman.studiecheck.models.CourseModel;

import static nl.martijndorsman.studiecheck.database.DatabaseInfo.CourseTables.Jaar1;

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
    }

    public void create(String tabel, ViewGroup rootView, RecyclerView rv){
        rv = (RecyclerView) rootView.findViewById(R.id.mRecycler);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                mLayoutManager.getOrientation());
        rv.addItemDecoration(dividerItemDecoration);
        ects = new ECTS(context);
        ects.getECTS(Jaar1);
        adapter = new ViewAdapter(Jaar1, context, courses);
        retrieve(tabel, context, adapter, rv);
        }

    public void retrieve(String tabel, Context context, ViewAdapter adapter, RecyclerView rv) {
        DatabaseHelper helper;
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
