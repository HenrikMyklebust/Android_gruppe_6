package com.example.android_gruppe_6.showtide;

import com.anychart.chart.common.dataentry.ValueDataEntry;

public class CustomDataEntry extends ValueDataEntry {
    CustomDataEntry(String x, Number value, Number value2, Number value3) {
        super(x, value);
        setValue("value2", value2);
        setValue("value3", value3);
    }
}
