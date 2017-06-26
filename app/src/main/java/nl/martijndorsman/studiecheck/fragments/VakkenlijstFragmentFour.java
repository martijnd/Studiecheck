package nl.martijndorsman.studiecheck.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import nl.martijndorsman.studiecheck.KeuzevakDialog;
import nl.martijndorsman.studiecheck.R;
import nl.martijndorsman.studiecheck.models.Vakkenlijst;
import static nl.martijndorsman.studiecheck.database.DatabaseInfo.CourseTables.Keuze;

/**
 * Created by Martijn on 25/06/17.
 */

public class VakkenlijstFragmentFour extends Fragment {
    public RecyclerView rv;
    Vakkenlijst vakkenlijst;
    TextView titleText, instructionText;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.mRecycler);
        titleText = (TextView) rootView.findViewById(R.id.tvVoortgang);
        titleText.setText(R.string.keuze);
        instructionText = (TextView) rootView.findViewById(R.id.tvVlActivityInstructions);
        instructionText.setText(R.string.vlActivityInstructionsKeuze);
        instructionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), KeuzevakDialog.class));
                getActivity().finish();
            }
        });
        vakkenlijst = new Vakkenlijst(context);
        vakkenlijst.create(Keuze, rv);
        return rootView;
    }
}
