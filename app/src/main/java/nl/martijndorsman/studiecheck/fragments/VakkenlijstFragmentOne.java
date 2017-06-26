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
import android.widget.TextView;

import java.util.ArrayList;

import nl.martijndorsman.studiecheck.R;
import nl.martijndorsman.studiecheck.models.Vakkenlijst;

import static nl.martijndorsman.studiecheck.database.DatabaseInfo.CourseTables.Jaar1;

/**
 * Created by Martijn on 25/06/17.
 */

public class VakkenlijstFragmentOne extends Fragment {
    public RecyclerView rv;
    Vakkenlijst vakkenlijst;
    TextView titleText;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.mRecycler);
        titleText = (TextView) rootView.findViewById(R.id.tvVoortgang);
        titleText.setText(R.string.jaar1);
        vakkenlijst = new Vakkenlijst(context);
        vakkenlijst.create(Jaar1, rv);
        return rootView;
    }
}
