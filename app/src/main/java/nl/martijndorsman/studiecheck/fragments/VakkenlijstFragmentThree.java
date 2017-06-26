package nl.martijndorsman.studiecheck.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nl.martijndorsman.studiecheck.R;
import nl.martijndorsman.studiecheck.models.ECTS;
import nl.martijndorsman.studiecheck.models.Vakkenlijst;

import static nl.martijndorsman.studiecheck.database.DatabaseInfo.CourseTables.Jaar3en4;

/**
 * Created by Martijn on 25/06/17.
 */

public class VakkenlijstFragmentThree extends Fragment {
    public RecyclerView rv;
    Vakkenlijst vakkenlijst;
    ECTS ects;
    Context context;
    TextView titleText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.mRecycler);
        titleText = (TextView) rootView.findViewById(R.id.tvVoortgang);
        titleText.setText(R.string.jaar3en4);
        vakkenlijst = new Vakkenlijst(context);
        vakkenlijst.create(Jaar3en4, rv);
        return rootView;
    }
}
