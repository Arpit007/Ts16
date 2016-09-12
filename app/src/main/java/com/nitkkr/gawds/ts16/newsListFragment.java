package com.nitkkr.gawds.ts16;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class newsListFragment extends Fragment
{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		MessageDbHelper helper=new MessageDbHelper(getContext());
		SQLiteDatabase sqLiteDatabase=helper.getReadableDatabase();
		ArrayList<String> messageList=helper.ReadDatabaseMessage(sqLiteDatabase);
		helper.close();;
		return inflater.inflate(R.layout.fragment_news_list, container, false);
	}

}
