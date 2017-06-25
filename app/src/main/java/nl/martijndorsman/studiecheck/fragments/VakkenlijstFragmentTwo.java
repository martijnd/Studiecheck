package nl.martijndorsman.studiecheck.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import nl.martijndorsman.studiecheck.R;
import nl.martijndorsman.studiecheck.ViewAdapter;
import nl.martijndorsman.studiecheck.database.DatabaseAdapter;
import nl.martijndorsman.studiecheck.models.CourseModel;
import nl.martijndorsman.studiecheck.models.ECTS;
import nl.martijndorsman.studiecheck.models.Vakkenlijst;

import static nl.martijndorsman.studiecheck.database.DatabaseInfo.CourseTables.Jaar2;

/**
 * Created by Martijn on 25/06/17.
 */

public class VakkenlijstFragmentTwo extends Fragment {
    public RecyclerView rv;
    Vakkenlijst vakkenlijst;
    ECTS ects;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
        vakkenlijst = new Vakkenlijst(context);
        vakkenlijst.create(Jaar2, rootView, rv);
        return rootView;
    }
}
