package com.example.pandrajus.onlinecareercentre.extensions;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
 * Created by Pandrajus on 11/13/2016.
 */
public class CandidateJobListView extends AppCompatActivity implements View.OnClickListener {

    ListView listView;
    public static SFSArray mData_Candidate;
    public static ListViewAdapter candidatelistviewadapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.candidatejoblist);

        listView = (ListView) findViewById(R.id.candidatelistView);
        mData_Candidate = new SFSArray();
        candidatelistviewadapter = new ListViewAdapter(CandidateJobListView.this,mData_Candidate);
        listView.setAdapter(candidatelistviewadapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;
                /*Intent intent = new Intent(JobListActivity.this, JobDetailActivity.class);
                intent.putExtra("positionNo", itemPosition);
                startActivity(intent);*/
            }
        });
        //TODO: Call Jobs by username.
        ISFSObject sfso = new SFSObject();
        sfso.putUtfString("username", MainActivity.userClass.getUsername());
        sfso.putUtfString("email", MainActivity.userClass.getEmail());
        sfsClient.send(new ExtensionRequest(Commands.CANDIDATE_JOBS_LIST, sfso));

    }

    public void loadCandidateJobListView(ISFSArray jobList){
        ISFSArray candidate_job_list = new SFSArray();
        candidate_job_list = jobList;
        Log.e("Check ARRAY","candidate_job_list ................ "+candidate_job_list);
        Log.e("candidate_job_list Size","................ "+candidate_job_list.size());
        Log.e("Check ","MainActivity.userClass.candidate_job_list Size................ "+MainActivity.userClass.getJobList().size());
        //int joblistSize = MainActivity.userClass.getJobList().size();
        mData_Candidate = new SFSArray();
        //listView.removeAllViewsInLayout();
        candidatelistviewadapter.clear();
        int adapter_joblistsize = candidatelistviewadapter.mData.size();
        for (int x = candidate_job_list.size()-1; x >=0 ; x--) {
            ISFSObject obj = candidate_job_list.getSFSObject(x);
            mData_Candidate.addSFSObject(obj);
            if(adapter_joblistsize == 0)
                candidatelistviewadapter.mData.addSFSObject(obj);
        }
        MainActivity.userClass.setJobList(jobList);
        candidatelistviewadapter.notifyDataSetChanged();
        Log.e("Check","candidatelistviewadapter candidate_job_list ................ "+candidatelistviewadapter.mData.size());
        Log.e("AFTER ","MainActivity.userClass.candidate_job_list Size................ "+MainActivity.userClass.getJobList().size());
    }

    @Override
    public void onClick(View v) {

    }
}
