package nl.martijndorsman.studiecheck;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nl.martijndorsman.studiecheck.database.DatabaseAdapter;

import static nl.martijndorsman.studiecheck.database.DatabaseInfo.CourseTables.Keuze;

/**
 * Created by Martijn on 25/06/17.
 */

public class KeuzevakDialog extends Activity {
    public static String item1, item2, item3, item4;
    List<String> keuzevakken;
    DatabaseAdapter dbAdapter;
    SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.window_choosesubject);
        keuzevakken = new ArrayList<>();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //Het formaat van de window instellen
        int width = dm.widthPixels;

        getWindow().setLayout((int)(width*.8), WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowManager = getWindow().getAttributes();
        windowManager.dimAmount = 0.75f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = prefs.edit();

        dbAdapter = new DatabaseAdapter(this);
        dbAdapter.openDB();
        Cursor c = dbAdapter.getAllData(Keuze);
        while(c.moveToNext()){
            String name = c.getString(0);
            keuzevakken.add(name);
        }



        ArrayList<String> arraySpinner = new ArrayList<>();
        // Voeg alle keuzevakken toe aan een nieuwe SpinnerArraylist
        for (int i = 0;i < keuzevakken.size();i++){
            arraySpinner.add(keuzevakken.get(i));
        }
        final Spinner s1 = (Spinner) findViewById(R.id.subjectSpinner1);
        final Spinner s2 = (Spinner) findViewById(R.id.subjectSpinner2);
        final Spinner s3 = (Spinner) findViewById(R.id.subjectSpinner3);
        final Spinner s4 = (Spinner) findViewById(R.id.subjectSpinner4);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,
                R.layout.spinner_item, arraySpinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                R.layout.spinner_item, arraySpinner);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this,
                R.layout.spinner_item, arraySpinner);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this,
                R.layout.spinner_item, arraySpinner);

        s1.setAdapter(adapter1);
        s2.setAdapter(adapter2);
        s3.setAdapter(adapter3);
        s4.setAdapter(adapter4);

        final Button subjectButton = (Button) findViewById(R.id.subjectButton);
        subjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item1 = s1.getSelectedItem().toString();
                editor.putString("item1", item1).apply();

                item2 = s2.getSelectedItem().toString();
                editor.putString("item2", item2).apply();

                item3 = s3.getSelectedItem().toString();
                editor.putString("item3", item3).apply();

                item4 = s4.getSelectedItem().toString();
                editor.putString("item4", item4).apply();

                ArrayList<String> items = new ArrayList<>();
                items.add(item1);
                items.add(item2);
                items.add(item3);
                items.add(item4);

                //check of 2 dezelfde keuzevakken geselecteerd zijn
                boolean duplicates=false;
                for (int j=0;j<items.size();j++)
                    for (int k=j+1;k<items.size();k++)
                        if (k!=j && items.get(k) == items.get(j))
                            duplicates=true;

                if(duplicates){
                    Toast.makeText(KeuzevakDialog.this, "Kies 4 verschillende keuzevakken", Toast.LENGTH_SHORT).show();
                } else{
                    Intent go=new Intent(KeuzevakDialog.this,VakkenlijstSlideActivity.class);
                    Bundle b=new Bundle();
                    b.putString("page","3");
                    go.putExtras(b);
                    startActivity(go);
                    KeuzevakDialog.this.finish();
                }
            }
        });
        final Button cancelbutton = (Button) findViewById(R.id.cancelButton2);
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go=new Intent(KeuzevakDialog.this,VakkenlijstSlideActivity.class);
                Bundle b=new Bundle();
                b.putString("page","3");
                go.putExtras(b);
                startActivity(go);
                KeuzevakDialog.this.finish();
            }
        });
    }
}
