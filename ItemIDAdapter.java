package com.summer.officeDepot.invertoryChecker.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.summer.officeDepot.invertoryChecker.MainActivity;
import com.summer.officeDepot.invertoryChecker.R;


import java.util.List;


/**
 * Created by wawa-3147 on 11/5/2015.
 */
public class ItemIDAdapter extends ArrayAdapter<String> {

    private List<String> itemIDList;

    private int resource;

    private WebView webView;

    private static String itemIDClicked;

    //private LayoutInflater inflater;

    private MainActivity activity;

    public ItemIDAdapter(MainActivity act, int _resource, List<String> list, WebView v){
        super(act,_resource, list);
        resource = _resource;
        itemIDList = list;
        webView = v;
        activity=act;
    }
    @Override
    public int getCount() {
        return this.itemIDList.size();
    }

    @Override
    public boolean areAllItemsEnabled ()
    {
        return true;
    }


/*    @Override
    public long getItemId(int pos) {
        return pos;
    }*/

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        LinearLayout newView;

        String itemID = getItem(pos);

        if (convertView == null) {
            newView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li;
            li = (LayoutInflater)getContext().getSystemService(inflater);
            li.inflate(resource, newView, true);
        } else {
// Otherwise we¡¯ll update the existing View
            newView = (LinearLayout)convertView;
        }

        TextView itemIDView = (TextView) newView.findViewById(R.id.samllTextView);
        itemIDView.setText(itemID);

        itemIDView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for(int j = 0; j<activity.listItemIDView.getChildCount(); j++){

                    activity.listItemIDView.getChildAt(j).setBackgroundColor(activity.getResources().getColor(R.color.unselected));
                }
                itemIDClicked = itemIDView.getText().toString().trim();


                newView.setBackgroundColor(activity.getResources().getColor(R.color.selected2));


                webView.getSettings().setBuiltInZoomControls(true);
                webView.getSettings().setDisplayZoomControls(false);

                webView.setInitialScale(100);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                webView.setWebViewClient(new WebViewClient(){

                    @Override
                    public void onPageFinished(WebView view, String url)
                    {
                        //view.loadUrl("javascript:document.getElementsByTagName('head')[0].style.display='none'");
                        view.loadUrl("javascript:document.getElementById('freeDeliveryOption').style.display='none'");
                        view.loadUrl("javascript:document.getElementById('socialFooter').style.display='none'");

                        //view.loadUrl("javascript:document.getElementsByClassName(\"gs-footer container_24 trackable\").style.display='none'");
                    }
                });
                String url = "http://www.officedepot.com/mb/stores/availability.do?"+"&zip="+activity.getZipSelected() +"&sku=" + itemID;
                webView.loadUrl(url);

            }
        });


        return newView;
    }
    public static String getItemIDClicked(){
        return itemIDClicked;
    }


}
