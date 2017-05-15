package com.example.pandrajus.onlinecareercentre.extensions.candidate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pandrajus.onlinecareercentre.MainActivity;
import com.example.pandrajus.onlinecareercentre.R;
import com.example.pandrajus.onlinecareercentre.extensions.employer.JobListActivity;
import com.example.pandrajus.onlinecareercentre.utils.Commands;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;

import sfs2x.client.requests.ExtensionRequest;

import static com.example.pandrajus.onlinecareercentre.MainActivity.sfsClient;

/**
 * Created by Pandrajus on 12/1/2016.
 */
public class CandidateJobDetailActivity extends AppCompatActivity implements View.OnClickListener {

    Button okBtn,acceptBtn,rejectBtn;
    ISFSObject sfsObj = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobdetailcandidate_activity);

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

        TextView state = (TextView)findViewById(R.id.state);
        state.setText(sfsObj.getUtfString("state"));

        TextView city = (TextView)findViewById(R.id.city);
        city.setText(sfsObj.getUtfString("city")+",");

        TextView category = (TextView)findViewById(R.id.category);
        category.setText(sfsObj.getUtfString("category"));

        TextView requiredskills = (TextView)findViewById(R.id.requiredskills);
        requiredskills.setText(sfsObj.getUtfString("required_skills"));

        TextView qualification = (TextView)findViewById(R.id.qualification);
        qualification.setText(sfsObj.getUtfString("education_qualification"));

        TextView jobtype = (TextView)findViewById(R.id.jobtype);
        jobtype.setText(sfsObj.getUtfString("job_type"));

        okBtn = (Button) findViewById(R.id.ok_btn);
        acceptBtn = (Button) findViewById(R.id.accept_btn);
        rejectBtn = (Button) findViewById(R.id.reject_btn);

        if(sfsObj.getUtfString("isRejected").equals("0") && sfsObj.getUtfString("isAccepted").equals("0"))
        {
            //Display Accept & Reject Buttons & hide OK btn
            okBtn.setVisibility(View.INVISIBLE);
            acceptBtn.setVisibility(View.VISIBLE);
            acceptBtn.setOnClickListener(this);
            rejectBtn.setVisibility(View.VISIBLE);
            rejectBtn.setOnClickListener(this);
        }
        else
        {
            //Display Ok Btn and add listeners
            acceptBtn.setVisibility(View.INVISIBLE);
            rejectBtn.setVisibility(View.INVISIBLE);
            okBtn.setVisibility(View.VISIBLE);
            okBtn.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ok_btn)
        {
            Intent intent = new Intent(this, CandidateJobListView.class);
            startActivity(intent);
        }
        else if(v.getId() == R.id.accept_btn)
        {
            ISFSObject sfso = new SFSObject();
            sfso.putInt("candiddate_job_id", sfsObj.getInt("candiddate_job_id"));
            sfso.putInt("isAccepted", 1);
            sfso.putInt("isRejected", 0);
            sfsClient.send(new ExtensionRequest(Commands.UPDATE_CANDIDATE_JOB, sfso));
        }
        else if(v.getId() == R.id.reject_btn)
        {
            ISFSObject sfso = new SFSObject();
            sfso.putInt("candiddate_job_id", sfsObj.getInt("candiddate_job_id"));
            sfso.putInt("isAccepted", 0);
            sfso.putInt("isRejected", 1);
            sfsClient.send(new ExtensionRequest(Commands.UPDATE_CANDIDATE_JOB, sfso));
        }

    }
}
