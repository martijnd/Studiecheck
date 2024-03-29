package nl.martijndorsman.studiecheck;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import nl.martijndorsman.studiecheck.database.DatabaseAdapter;
import nl.martijndorsman.studiecheck.models.Vakkenlijst;

import static nl.martijndorsman.studiecheck.database.DatabaseInfo.CourseTables.Jaar1;
import static nl.martijndorsman.studiecheck.database.DatabaseInfo.CourseTables.Jaar2;
import static nl.martijndorsman.studiecheck.database.DatabaseInfo.CourseTables.Jaar3en4;
import static nl.martijndorsman.studiecheck.database.DatabaseInfo.CourseTables.Keuze;

public class MainActivity extends Activity {
    SharedPreferences prefs;
    boolean vakkenGekozen = false;
    private SwipeRefreshLayout swipeContainer;
    boolean dataBaseAangemaakt = false;
    ProgressDialog pd;
    boolean check = false;
    DatabaseAdapter dbAdapter = new DatabaseAdapter(this);
    public JSONArray jaar1;
    public JSONArray jaar2;
    public JSONArray jaar3en4;
    public JSONArray keuze;
    ArrayList<String> keuzevakken;
    ArrayList<String> geselecteerdeVakken;

    private String TAG = MainActivity.class.getSimpleName();
    private boolean success = true;
    CharSequence text;
    // De url waar het json-bestand op staat
    private static String url = "http://martijndorsman.nl/vakken_lijst.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        keuzevakken = new ArrayList<>();
        geselecteerdeVakken = new ArrayList<>();
        dbAdapter = new DatabaseAdapter(getApplicationContext());
        dbAdapter.openDB();
        prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        final SharedPreferences.Editor editor = prefs.edit();
        Cursor c = dbAdapter.getAllData(Keuze);
        // Bind de button aan de onClickListener met de startActivity methode
        Button vakkenButton = (Button) findViewById(R.id.vakkenbutton);
        Button voortgangButton = (Button) findViewById(R.id.voortgangbutton);
        while(c.moveToNext()){
            String name = c.getString(0);
            keuzevakken.add(name);
        }
        String dbFile = getApplicationContext().getDatabasePath("vakkenlijst2.db").toString();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getJSON();
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        vakkenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VakkenlijstSlideActivity.class));

            }
        });

        voortgangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,VoortgangActivity.class));
            }
        });
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            // Maak een dialoog voor tijdens het wachten (User Feedback)
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpHandler sh = new HttpHandler();
            // Stuur een request naar de url, en wacht op antwoord
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            // Als de json string nog niet binnengehaald is
            if (jsonStr != null) {
                //  Maak een JSONObject aan waarmee de string ontleed kan worden
                JSONObject reader = null;
                try {
                    reader = new JSONObject(jsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // ontleed de ontvangen json string in stukken van elk jaar
                try {
                    if (reader != null) {
                        jaar1 = reader.getJSONArray("jaar1");
                        jaar2 = reader.getJSONArray("jaar2");
                        jaar3en4 = reader.getJSONArray("jaar3en4");
                        keuze = reader.getJSONArray("keuze");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Voeg de JSON string toe aan de database mbh de addFromJson functie
                try {
                    dbAdapter.openDB();
                    dbAdapter.addFromJson(jaar1, Jaar1);
                    dbAdapter.addFromJson(jaar2, Jaar2);
                    dbAdapter.addFromJson(jaar3en4, Jaar3en4);
                    dbAdapter.addFromJson(keuze, Keuze);
                    dbAdapter.closeDB();

                } catch(NullPointerException e){
                    e.printStackTrace();
                }
            } else {
                Log.d(TAG, "Couldn't get json from server.");
                success = false;
            }
            dataBaseAangemaakt = true;
            final SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("dbAangemaakt", dataBaseAangemaakt).apply();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Verwijder de dialoog na het binnehalen
            if (pd.isShowing()) {
                pd.dismiss();
                // Verschillende tekst afhankelijk van het resultaat
                if (success) {
                    text = "Ophalen vakkenlijst succesvol";
                    check = true;
                } else {
                    text = "Ophalen vakkenlijst mislukt";
                }

                // Maak een Toast voor de User Experience
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        }

    }

//    private static boolean doesDatabaseExist(Context context) {
//        File dbFile = context.getDatabasePath("vakkenlijst2.db");
//        return dbFile.exists();
//    }

    private void getJSON(){
        boolean dataBaseAangemaaktx = prefs.getBoolean("dbAangemaakt", false);
        if(!dataBaseAangemaaktx) {
            new JsonTask().execute(url);
        }
        else {
            Toast.makeText(MainActivity.this, "Vakkenlijst is al opgehaald", Toast.LENGTH_SHORT).show();
        }
        swipeContainer.setRefreshing(false);
    }
}
