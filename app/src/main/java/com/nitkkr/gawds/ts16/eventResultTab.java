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
	static eventData data;
	public static eventResultTab CreateFragment(Bundle bundle)
	{
		eventResultTab tab = new eventResultTab();
		tab.EventID=bundle.getInt(tab.getString(R.string.EventID));
		dbHelper helper=new dbHelper(tab.getContext());
		data =helper.GetEventById(helper.getReadableDatabase(),tab.EventID);
		helper.close();
		data.addEventDataListener(tab);
		return tab;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		eventUpdated(data);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_event_result_tab, container, false);
	}

	@Override
	public void eventUpdated(eventData event)
	{
		EventID=event.eventID;
		View view=getView();
		if(!data.isResultDeclared())
		{
			(view.findViewById(R.id.NoResult)).setVisibility(View.VISIBLE);
		}
		else
		{
			view.findViewById(R.id.NoResult).setVisibility(View.INVISIBLE);
			WebView webView=(WebView)view.findViewById(R.id.resultView);
			webView.setWebViewClient(new Callback());
			webView.loadData(data.Result,"text/html","UTF-8");
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

	private class Callback extends WebViewClient
	{
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url)
		{
			return false;
		}
	}
}