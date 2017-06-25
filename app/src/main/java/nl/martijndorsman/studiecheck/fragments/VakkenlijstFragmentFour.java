package nl.martijndorsman.studiecheck.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import nl.martijndorsman.studiecheck.R;
import nl.martijndorsman.studiecheck.ViewAdapter;
import nl.martijndorsman.studiecheck.database.DatabaseAdapter;
import nl.martijndorsman.studiecheck.models.CourseModel;

import static nl.martijndorsman.studiecheck.KeuzevakDialog.item1;
import static nl.martijndorsman.studiecheck.KeuzevakDialog.item2;
import static nl.martijndorsman.studiecheck.KeuzevakDialog.item3;
import static nl.martijndorsman.studiecheck.KeuzevakDialog.item4;
import static nl.martijndorsman.studiecheck.database.DatabaseInfo.CourseTables.Keuze;

/**
 * Created by Martijn on 25/06/17.
 */

public class VakkenlijstFragmentFour extends Fragment {
    DatabaseAdapter dbAdapter;
    ArrayList<CourseModel> courses = new ArrayList<>();
    RecyclerView rv;
    ViewAdapter adapter;
    Context context;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.mRecycler);
        adapter = new ViewAdapter(Keuze, context, courses);
        return rootView;
    }

    public void retrieveSubject(String tabel, Context context) {
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
            if(name.equals(item1) || name.equals(item2) || name.equals(item3) || name.equals(item4)) {
                CourseModel p = new CourseModel(name, ects, period, grade, status);
                //Voeg toe aan ArrayList
                courses.add(p);
            }
        }
        //Check of ArrayList leeg is
        if (!(courses.size() < 1)) {
            rv.setAdapter(adapter);
        }
        c.close();
        dbAdapter.closeDB();
    }
}
