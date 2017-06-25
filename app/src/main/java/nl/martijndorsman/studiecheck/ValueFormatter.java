package nl.martijndorsman.studiecheck;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by Martijn on 25/06/17.
 */

public class ValueFormatter implements IValueFormatter {

    private DecimalFormat mFormat;

    public ValueFormatter() {
        mFormat = new DecimalFormat("###,###,##0"); // geen decimalen
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return mFormat.format(value);
    }
}
