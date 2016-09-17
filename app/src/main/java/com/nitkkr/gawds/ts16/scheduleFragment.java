package com.nitkkr.gawds.ts16;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by SAHIL SINGLA on 17-09-2016.
 */
public class scheduleFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    	{
    		// Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_schedule, container, false);
    }
}
