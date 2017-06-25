package nl.martijndorsman.studiecheck.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.martijndorsman.studiecheck.R;
import nl.martijndorsman.studiecheck.ViewAdapter;
import nl.martijndorsman.studiecheck.models.Vakkenlijst;

import static nl.martijndorsman.studiecheck.database.DatabaseInfo.CourseTables.Jaar1;

/**
 * Created by Martijn on 25/06/17.
 */

public class VakkenlijstFragmentOne extends Fragment {
    Vakkenlijst vakkenLijst;
    Context context;
    ViewAdapter adapter = new ViewAdapter(context, courses)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        ViewAdapter adapter = new ViewAdapter(context, courses)
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
        vakkenLijst = new Vakkenlijst(context);
        vakkenLijst.retrieve(Jaar1, context,adapter,);
        return rootView;
    }
}
