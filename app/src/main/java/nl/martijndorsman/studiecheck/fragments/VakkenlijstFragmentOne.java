package nl.martijndorsman.studiecheck.fragments;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import nl.martijndorsman.studiecheck.R;
import nl.martijndorsman.studiecheck.ViewAdapter;
import nl.martijndorsman.studiecheck.database.DatabaseAdapter;
import nl.martijndorsman.studiecheck.database.DatabaseHelper;
import nl.martijndorsman.studiecheck.models.CourseModel;
import nl.martijndorsman.studiecheck.models.ECTS;
import nl.martijndorsman.studiecheck.models.Vakkenlijst;

import static nl.martijndorsman.studiecheck.database.DatabaseInfo.CourseTables.Jaar1;

/**
 * Created by Martijn on 25/06/17.
 */

public class VakkenlijstFragmentOne extends Fragment {
    public RecyclerView rv;
    DatabaseAdapter dbAdapter;
    public ArrayList<CourseModel> courses = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    public static int totaalECTSjaar1;
    public static int totaalECTSjaar2;
    public static int totaalECTSjaar3en4;
    public static int totaalECTSKeuze;
    ViewAdapter adapter;
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
        vakkenlijst.create(Jaar1, rootView, rv);
        return rootView;
    }
}
