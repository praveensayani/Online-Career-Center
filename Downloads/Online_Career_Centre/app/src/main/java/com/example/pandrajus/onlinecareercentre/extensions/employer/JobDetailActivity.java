package com.example.pandrajus.onlinecareercentre.extensions.employer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pandrajus.onlinecareercentre.MainActivity;
import com.example.pandrajus.onlinecareercentre.R;
import com.example.pandrajus.onlinecareercentre.utils.Commands;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;

import sfs2x.client.requests.ExtensionRequest;

import static com.example.pandrajus.onlinecareercentre.MainActivity.sfsClient;

/**
 * Created by Pandrajus on 11/5/2016.
 */
public class JobDetailActivity extends AppCompatActivity implements View.OnClickListener {

    Button okBtn,candidatesBtn,closejobBtn;
    ISFSObject sfsObj = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobdetail_activity);

        Log.e("JobDetailActivity ","--------------------------------------------------------------------");
        Intent intent = getIntent();
        int positionNo = 0;
        positionNo = intent.getIntExtra("positionNo",0);

        /*Toast.makeText(getApplicationContext(),
                "positionNo :"+positionNo, Toast.LENGTH_LONG)
                .show();*/

        for (int x = MainActivity.userClass.getJobList().size()-1; x >=0 ; x--) {
            if(positionNo == x)
            {
                sfsObj = MainActivity.userClass.getJobList().getSFSObject(x);
                break;
            }
        }

        TextView positionname = (TextView)findViewById(R.id.positionname);
        positionname.setText(sfsObj.getUtfString("position"));

        TextView description = (TextView)findViewById(R.id.description);
        description.setText(sfsObj.getUtfString("description"));

        /*TextView zipcode = (TextView)findViewById(R.id.zipcode);
        zipcode.setText(sfsObj.getUtfString("zipcode"));*/

        TextView state = (TextView)findViewById(R.id.state);
        state.setText(sfsObj.getUtfString("state"));

        TextView city = (TextView)findViewById(R.id.city);
        city.setText(sfsObj.getUtfString("city")+",");

        TextView category = (TextView)findViewById(R.id.category);
        category.setText(sfsObj.getUtfString("category"));

        TextView requiredskills = (TextView)findViewById(R.id.requiredskills);
        requiredskills.setText(sfsObj.getUtfString("requiredskills"));

        TextView qualification = (TextView)findViewById(R.id.qualification);
        qualification.setText(sfsObj.getUtfString("qualification"));

        TextView jobtype = (TextView)findViewById(R.id.jobtype);
        jobtype.setText(sfsObj.getUtfString("jobtype"));

        TextView jobstatus = (TextView)findViewById(R.id.jobstatus);
        jobstatus.setText(sfsObj.getUtfString("jobstatus"));
        //jobstatus

        okBtn = (Button) findViewById(R.id.ok_btn);
        okBtn.setOnClickListener(this);

        candidatesBtn = (Button) findViewById(R.id.candidates_btn);
        candidatesBtn.setOnClickListener(this);

        closejobBtn = (Button) findViewById(R.id.closejob_btn);
        if(sfsObj.getUtfString("jobstatus").equals("closed"))
        {
            closejobBtn.setVisibility(View.INVISIBLE);
        }
        else {
            closejobBtn.setVisibility(View.VISIBLE);
            closejobBtn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ok_btn) {
            Intent intent = new Intent(this, JobListActivity.class);
            startActivity(intent);
        }
        else if(v.getId() == R.id.candidates_btn){
            Intent intent = new Intent(JobDetailActivity.this, CandidatesAcceptedJobList.class);
            intent.putExtra("job_id", sfsObj.getInt("jobid"));
            startActivity(intent);
        }
        else if(v.getId() == R.id.closejob_btn){
            ISFSObject sfso = new SFSObject();
            sfso.putInt("job_id", sfsObj.getInt("jobid"));
            sfso.putUtfString("job_status", "closed");
            sfsClient.send(new ExtensionRequest(Commands.UPDATE_JOB_STATUS, sfso));
        }
    }
}
