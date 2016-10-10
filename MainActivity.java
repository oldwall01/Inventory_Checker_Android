package com.summer.officeDepot.invertoryChecker;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;

import com.summer.officeDepot.invertoryChecker.Adapter.ItemIDAdapter;
import com.summer.officeDepot.invertoryChecker.itemIDEdit.ItemIDEditActivity;
import org.w3c.dom.Text;


import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends Activity
{
    private WebView webView;

    public ListView listItemIDView;

    private TextView[] zipView = new TextView[16];

    private static List<String> itemIDList = new ArrayList<String>();

    private String zipSelected;

    private String itemIDSelected;

    private TextView config;

    private File file;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initiateApp();
    }

    public void initiateApp() {
        webView = (WebView) findViewById(R.id.webView);
        listItemIDView = (ListView) findViewById(R.id.listItemIDView);

        zipView[0] = (TextView) findViewById(R.id.zipView0);
        zipView[1] = (TextView) findViewById(R.id.zipView1);
        zipView[2] = (TextView) findViewById(R.id.zipView2);
        zipView[3] = (TextView) findViewById(R.id.zipView3);
        zipView[4] = (TextView) findViewById(R.id.zipView4);
        zipView[5] = (TextView) findViewById(R.id.zipView5);
        zipView[6] = (TextView) findViewById(R.id.zipView6);
        zipView[7] = (TextView) findViewById(R.id.zipView7);
        zipView[8] = (TextView) findViewById(R.id.zipView8);
        zipView[9] = (TextView) findViewById(R.id.zipView9);
        zipView[10] = (TextView) findViewById(R.id.zipView10);
        zipView[11] = (TextView) findViewById(R.id.zipView11);
        zipView[12] = (TextView) findViewById(R.id.zipView12);
        zipView[13] = (TextView) findViewById(R.id.zipView13);
        zipView[14] = (TextView) findViewById(R.id.zipView14);
        zipView[15] = (TextView) findViewById(R.id.zipView15);

        //inflater = LayoutInflater.from(this);

        config = (TextView) findViewById(R.id.config);

        config.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, ItemIDEditActivity.class);
                startActivity(intent);
            }
        });

        final File dir = new File(getFilesDir() + "/com/summer/officeDepot/invertoryChecker/itemIDfile");
        dir.mkdirs(); //create folders where write files
        this.file = new File(dir, "itemID_file.txt");

        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while((line = br.readLine()) != null) {
                line = line.trim();
                itemIDList.add(line);
            }
            br.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        ItemIDAdapter myAdapter = new ItemIDAdapter(this, R.layout.itemidrow, itemIDList, webView);
        listItemIDView.setAdapter(myAdapter);

        zipSelected = zipView[0].getText().toString().trim();

        itemIDSelected = ItemIDAdapter.getItemIDClicked();

        for(int j=0; j<16; j++){
            zipView[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int i=0; i<16; i++){
                        zipView[i].setBackgroundColor(getResources().getColor(R.color.unselected));
                    }
                    view.setBackgroundColor(getResources().getColor(R.color.selected2));

                    zipSelected = ((TextView) view).getText().toString().trim();

                    webView.getSettings().setBuiltInZoomControls(true);
                    webView.getSettings().setDisplayZoomControls(false);

                    webView.setInitialScale(100);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                    webView.setWebViewClient(new WebViewClient() {

                        @Override
                        public void onPageFinished(WebView view, String url) {
                            //view.loadUrl("javascript:document.getElementsByTagName('head')[0].style.display='none'");
                            view.loadUrl("javascript:document.getElementById('freeDeliveryOption').style.display='none'");
                            view.loadUrl("javascript:document.getElementById('socialFooter').style.display='none'");

                            //view.loadUrl("javascript:document.getElementsByClassName(\"gs-footer container_24 trackable\").style.display='none'");
                        }
                    });
                    String url = "http://www.officedepot.com/mb/stores/availability.do?"+"&zip="+zipSelected+"&sku=" + ItemIDAdapter.getItemIDClicked();
                    webView.loadUrl(url);
                }
            });
        }

    }



    public String getZipSelected(){
        return zipSelected;
    }

    public static List<String> getItemIDList(){
        return itemIDList;
    }

    public static void setItemIDList(List<String> ls){
        itemIDList = ls;
    }

    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            itemIDList.clear();
            while((line = br.readLine()) != null) {
                line = line.trim();
                itemIDList.add(line);
            }
            br.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        ItemIDAdapter myAdapter = new ItemIDAdapter(this, R.layout.itemidrow, itemIDList, webView);
        listItemIDView.setAdapter(myAdapter);

    }



}
