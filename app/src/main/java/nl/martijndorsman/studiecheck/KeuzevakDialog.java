package nl.martijndorsman.studiecheck;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;

import nl.martijndorsman.studiecheck.database.DatabaseAdapter;

import static nl.martijndorsman.studiecheck.database.DatabaseInfo.CourseTables.Keuze;

/**
 * Created by Martijn on 25/06/17.
 */

public class KeuzevakDialog extends Activity {
    public static String item1, item2, item3, item4;
    ArrayList<String> keuzevakken;
    DatabaseAdapter dbAdapter;
    ArrayList<String> geselecteerdeVakken;
    DialogInterface.OnMultiChoiceClickListener listener;
    CharSequence[] cs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keuzevakken = new ArrayList<>();
        geselecteerdeVakken = new ArrayList<>();
        // Keuzevakken ophalen uit de database
        dbAdapter = new DatabaseAdapter(getApplicationContext());
        dbAdapter.openDB();
        Cursor c = dbAdapter.getAllData(Keuze);
        while(c.moveToNext()){
            String name = c.getString(0);
            keuzevakken.add(name);
        }
        listener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    geselecteerdeVakken.add(keuzevakken.get(which));
                } else if (geselecteerdeVakken.contains(keuzevakken.get(which))) {
                    geselecteerdeVakken.remove(keuzevakken.get(which));
                }
            }
        };
        cs  = keuzevakken.toArray(new CharSequence[keuzevakken.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(KeuzevakDialog.this, R.style.Theme_AppCompat_Dialog_Alert);
        // Titel instellen
        builder.setTitle(R.string.kiesjekeuzevakken)
                .setMultiChoiceItems(cs, null, listener)
                .setPositiveButton(R.string.bevestig, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(KeuzevakDialog.this, VakkenlijstSlideActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.annuleer, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
//        item1 = geselecteerdeVakken.get(0);
//        item2 = geselecteerdeVakken.get(1);
//        item3 = geselecteerdeVakken.get(2);
//        item4 = geselecteerdeVakken.get(3);
        builder.create();
    }
}
