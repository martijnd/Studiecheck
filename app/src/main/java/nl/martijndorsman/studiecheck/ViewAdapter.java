package nl.martijndorsman.studiecheck;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import nl.martijndorsman.studiecheck.database.DatabaseAdapter;
import nl.martijndorsman.studiecheck.models.CourseModel;
import nl.martijndorsman.studiecheck.models.ECTS;

/**
 * Created by Martijn on 25/06/17.
 */

public class ViewAdapter extends RecyclerView.Adapter<Holder> {
        private EditText gradetxt;
        private String newGrade;
        private String tabel;
        private Context context;
        private ECTS ects;
        // Een arraylist volgens de layout van de Coursemodel klasse
        private ArrayList<CourseModel> courses;

        public ViewAdapter(String tabel, Context context, ArrayList<CourseModel> courses){
            this.tabel = tabel;
            this.context = context;
            this.courses = courses;
        }


        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            // view object
            // Stel de view in met de LayoutInflater en de contxt. Vervolgens inflate de items van de recyclerview
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewitem, null);
            // holder
            return new Holder(v);

        }

        // bind view aan de holder
        @Override
        public void onBindViewHolder(final Holder holder, int position) {
            holder.nametxt.setText(courses.get(position).getName());
            holder.ectstxt.setText(courses.get(position).getEcts());
            holder.periodtxt.setText(courses.get(position).getPeriod());
            holder.gradetxt.setText(courses.get(position).getGrade());
            holder.statustxt.setText(courses.get(position).getStatus());
            if(holder.statustxt.getText() == "Behaald"){
                holder.statustxt.setTextColor(ContextCompat.getColor(context, R.color.behaald));
            }   else {
                holder.statustxt.setTextColor(ContextCompat.getColor(context, R.color.nietbehaald));
            }



            // clicked action
            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(View v, int pos) {

                    String name = courses.get(pos).getName();
                    // Voeg een invoerscherm toe
                    showDialog(tabel, name, pos);
                    // Vertel de adapter dat de huidige dataset is aangepast, en de View dus geupdate moet worden
                    notifyDataSetChanged();
                }
            });
        }
        // Functie om het cijfer invoerscherm te tonen
        private void showDialog(final String tabel, final String name, final int pos){
            final DatabaseAdapter dbAdapter = new DatabaseAdapter(context);
            final Dialog d = new Dialog(context);
            d.requestWindowFeature(Window.FEATURE_NO_TITLE);
            d.setContentView(R.layout.gradeeditwindow);
            gradetxt = (EditText) d.findViewById(R.id.etGradeEdit);
            Button gradeButton = (Button) d.findViewById(R.id.gradeButton);
            Button cancelButton = (Button) d.findViewById(R.id.cancelButton);
//      Keyboard pop up wanneer het dialoog tevoorschijn komt (UX design)
            gradetxt.setOnFocusChangeListener(new View.OnFocusChangeListener(){
                @Override
                public void onFocusChange(View view, boolean focused)
                {
                    if (focused)
                    {
                        d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    }
                }
            });
            // OnClicklistener voor de accepteerbutton in het dialog
            gradeButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    //vraag de waarde van de EditText op
                    newGrade = gradetxt.getText().toString();
                    // Controleer of de EditText niet leeg is
                    if(!newGrade.equals("")) {
                        Double newGradeDouble = Double.parseDouble(newGrade);
                        // Controleer of het ingevoerde cijfer tussen de 1 en 10 ligt
                        if (newGradeDouble <= 10 && newGradeDouble > 0) {
                            // Open de database en update de database met het nieuwe cijfer
                            dbAdapter.openDB();
                            dbAdapter.update(tabel, name, newGrade);
                            d.dismiss();
                            // Vraag de nieuwe info op uit de database
                            ects = new ECTS(context);
                            ects.getECTS(tabel);

                            // Sluit de database weer netjes
                            dbAdapter.closeDB();
                            courses.get(pos).setGrade(newGrade);
                            // Controleer of het cijfer een voldoende is of niet
                            if (newGradeDouble >= 5.5){
                                courses.get(pos).setStatus("Behaald");

                            } else {
                                courses.get(pos).setStatus("Niet behaald");
                            }
                            notifyDataSetChanged();
                            // Show een Toast wanneer de input leeg is of geen legitiem cijfer is
                        } else {
                            Toast.makeText(context, "Geef een cijfer tussen de 1 en 10", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Geef een cijfer tussen de 1 en 10", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            // Maak een onClicklistener voor de annuleerknop die het dialoog weghaalt
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.dismiss();
                }
            });
            d.show();
        }

        @Override
        public int getItemCount() {
            return courses.size();
        }
    }

