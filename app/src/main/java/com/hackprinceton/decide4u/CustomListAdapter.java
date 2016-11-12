package com.hackprinceton.decide4u;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.hackprinceton.decide4u.model.Question;

import java.util.ArrayList;

/**
 * Created by sherr on 11/12/2016.
 */

public class CustomListAdapter extends ArrayAdapter<Question> {
    public CustomListAdapter(Context context, ArrayList<Question> questions) {
        super(context, 0, questions);
    }

}
