package com.summer.officeDepot.invertoryChecker.itemIDEdit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.summer.officeDepot.invertoryChecker.MainActivity;
import com.summer.officeDepot.invertoryChecker.R;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by wawa-3147 on 11/12/2015.*/


public class ItemIDEditActivity extends Activity {
    private List<String> itemIDList = MainActivity.getItemIDList();

//    private Button btnSave;

    private Button btnGoback;

    private EditText itemIDListView;

    private static String itemIDFile = "itemID_file.txt";




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuration);
        actGO();
    }

    public void actGO(){
//        btnSave = (Button) findViewById(R.id.btnSave);

        btnGoback = (Button) findViewById(R.id.btnGoback);

        itemIDListView = (EditText) findViewById(R.id.itemIDListView);

        final File dir = new File(getFilesDir() + "/com/summer/officeDepot/invertoryChecker/itemIDfile");
        dir.mkdirs(); //create folders where write files
        final File file = new File(dir, itemIDFile);

        try {
            itemIDListView.setText(readFile(file));
        }catch (IOException e){
            e.printStackTrace();
        }



        btnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = itemIDListView.getText().toString().trim();
                try{
                    PrintWriter pw = new PrintWriter(file);
                    pw.println(string);
                    pw.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
                finish();
            }
        });

    }

    public String changeToString(List<String> stringList){
        StringBuilder sb= new StringBuilder();
        for(String s: stringList){
            sb.append(s+"\n");
        }
        return sb.toString();
    }

    private String readFile( File file ) throws IOException {
        BufferedReader reader = new BufferedReader( new FileReader(file));
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();


        while( ( line = reader.readLine() ) != null ) {
            stringBuilder.append( line );
            stringBuilder.append("\n" );
        }

        return stringBuilder.toString();
    }

}
