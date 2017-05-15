package com.example.pandrajus.onlinecareercentre.extensions.employer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pandrajus.onlinecareercentre.MainActivity;
import com.example.pandrajus.onlinecareercentre.R;
import com.example.pandrajus.onlinecareercentre.adapters.ListViewAdapter;
import com.example.pandrajus.onlinecareercentre.utils.Commands;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSArray;
import com.smartfoxserver.v2.entities.data.SFSObject;

import sfs2x.client.requests.ExtensionRequest;

import static com.example.pandrajus.onlinecareercentre.MainActivity.sfsClient;

/**
 * Created by Pandrajus on 10/27/2016.
 */
public class JobListActivity  extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton fab_create_job;
    ListView listView;
    public static SFSArray mData;
    public static ListViewAdapter listviewadapter;
    TextView job_list_txt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joblist);

        fab_create_job = (FloatingActionButton) findViewById(R.id.fab_create_job);
        fab_create_job.setOnClickListener(this);

        job_list_txt = (TextView) findViewById(R.id.job_list_msg);

        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewMessageActivity.class);
                startActivity(intent);
            }
        });*/

        listView = (ListView) findViewById(R.id.listView);
        mData = new SFSArray();
        listviewadapter = new ListViewAdapter(JobListActivity.this,mData);
        listView.setAdapter(listviewadapter);
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);*/

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // Show Alert
                /*Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition, Toast.LENGTH_LONG)
                        .show();*/
                Intent intent = new Intent(JobListActivity.this, JobDetailActivity.class);
                intent.putExtra("positionNo", itemPosition);
                startActivity(intent);

            }

        });

        //TODO: Call Jobs by username.
        ISFSObject sfso = new SFSObject();
        sfso.putUtfString("name",MainActivity.userClass.getUsername());
        sfso.putUtfString("email",MainActivity.userClass.getEmail());
        sfsClient.send(new ExtensionRequest(Commands.JOBS_LIST, sfso));

    }

    public void loadView(ISFSArray jobList){
        ISFSArray job_list = new SFSArray();
        ISFSArray job_list_reverse = new SFSArray();
        job_list = jobList;

        Log.e("Check ARRAY","job_list ................ "+job_list);
        Log.e("Check job_list Size","................ "+job_list.size());
        Log.e("Check ","MainActivity.userClass.job_list Size................ "+MainActivity.userClass.getJobList().size());
        //int joblistSize = MainActivity.userClass.getJobList().size();
        mData = new SFSArray();
        //listView.removeAllViewsInLayout();
        listviewadapter.clear();
        int adapter_joblistsize = listviewadapter.mData.size();
        for (int x = job_list.size()-1; x >=0 ; x--) {
            ISFSObject obj = job_list.getSFSObject(x);
            mData.addSFSObject(obj);
            if(adapter_joblistsize == 0)
                listviewadapter.mData.addSFSObject(obj);

            job_list_reverse.addSFSObject(obj);
        }
        MainActivity.userClass.setJobList(job_list_reverse);
        listviewadapter.notifyDataSetChanged();
        if(job_list_txt != null)
        {
            if(jobList.size() == 0)
                job_list_txt.setVisibility(View.VISIBLE);
            else
                job_list_txt.setVisibility(View.INVISIBLE);
        }

        Log.e("Check listviewadapter","job_list ................ "+listviewadapter.mData.size());
        Log.e("AFTER ","MainActivity.userClass.job_list Size................ "+MainActivity.userClass.getJobList().size());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, CreateJobActivity.class);
        startActivity(intent);
    }
}
