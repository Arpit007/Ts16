package com.nitkkr.gawds.ts16;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaControllerCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class eventResultTab extends Fragment implements eventData.eventDataListener
{
	private int EventID;
	static eventData data;
	View view;

	public void setUpFragment(int eventID, Context context)
	{
		EventID=eventID;
		dbHelper helper=new dbHelper(context);
		data=helper.GetEventById(helper.getReadableDatabase(),EventID);
		helper.close();
		data.addEventDataListener(this);
		eventUpdated(data);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		view= inflater.inflate(R.layout.fragment_event_result_tab, container, false);
		eventUpdated(data);
		return view;
	}

	@Override
	public void eventUpdated(eventData event)
	{
		EventID=event.eventID;
		if(view== null)
			return;
		if(!data.isResultDeclared())
		{
			(view.findViewById(R.id.NoResult)).setVisibility(View.VISIBLE);
		}
		else
		{
			view.findViewById(R.id.NoResult).setVisibility(View.INVISIBLE);
			((TextView)view.findViewById(R.id.resultView)).setText(Html.fromHtml(data.Result));
			Typeface font = Typeface.createFromAsset(getContext().getAssets(),
					"fonts/Font1.ttf");
			(( TextView)view.findViewById(R.id.resultView)).setTypeface(font);
		}
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		data.removeDataListener(this);
	}

	@Override
	public void onStop()
	{
		super.onStop();
		data.removeDataListener(this);
	}
}