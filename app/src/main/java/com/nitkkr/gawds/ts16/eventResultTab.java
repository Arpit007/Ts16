package com.nitkkr.gawds.ts16;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaControllerCompat;
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

	public static eventResultTab CreateFragment(Bundle bundle)
	{
		eventResultTab tab = new eventResultTab();
		tab.EventID=bundle.getInt(tab.getString(R.string.EventID));
		eventData data = eventDatabase.Database.getEventData(tab.EventID);
		data.addEventDataListener(tab);
		return tab;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		eventUpdated(eventDatabase.Database.getEventData(EventID));
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_event_result_tab, container, false);
	}

	@Override
	public void eventUpdated(eventData event)
	{
		EventID=event.eventID;
		View view=getView();
		if(!eventDatabase.Database.getEventData(EventID).isResultDeclared())
		{
			(view.findViewById(R.id.NoResult)).setVisibility(View.VISIBLE);
		}
		else
		{
			view.findViewById(R.id.NoResult).setVisibility(View.INVISIBLE);
			WebView webView=(WebView)view.findViewById(R.id.resultView);
			webView.setWebViewClient(new Callback());
			webView.loadData(eventDatabase.Database.getEventData(EventID).Result,"text/html","UTF-8");
		}
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		eventDatabase.Database.getEventData(EventID).removeDataListener(this);
	}

	@Override
	public void onStop()
	{
		super.onStop();
		eventDatabase.Database.getEventData(EventID).removeDataListener(this);
	}

	private class Callback extends WebViewClient
	{
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url)
		{
			return false;
		}
	}
}