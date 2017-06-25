package nl.martijndorsman.studiecheck;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Martijn on 25/06/17.
 */

public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView nametxt, ectstxt, periodtxt, gradetxt, statustxt;
    private ItemClickListener itemClickListener;

    // Bind de TextViews aan de id's van de layout
    public Holder(View itemView){
        super(itemView);

        nametxt = (TextView) itemView.findViewById(R.id.nametxt);
        ectstxt = (TextView) itemView.findViewById(R.id.ectstxt);
        periodtxt = (TextView) itemView.findViewById(R.id.periodtxt);
        gradetxt = (TextView) itemView.findViewById(R.id.gradetxt);
        statustxt = (TextView) itemView.findViewById(R.id.behaaldresulttxt);

        itemView.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic){
        this.itemClickListener = ic;
    }
}