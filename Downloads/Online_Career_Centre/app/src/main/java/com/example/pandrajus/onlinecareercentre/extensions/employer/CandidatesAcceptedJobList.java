package com.example.pandrajus.onlinecareercentre.extensions.employer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pandrajus.onlinecareercentre.MainActivity;
import com.example.pandrajus.onlinecareercentre.R;
import com.example.pandrajus.onlinecareercentre.adapters.CandidatesAcceptListViewAdapter;
import com.example.pandrajus.onlinecareercentre.adapters.ListViewAdapter;
import com.example.pandrajus.onlinecareercentre.model.CandidateCV;
import com.example.pandrajus.onlinecareercentre.utils.Commands;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSArray;
import com.smartfoxserver.v2.entities.data.SFSObject;

import sfs2x.client.requests.ExtensionRequest;

import static com.example.pandrajus.onlinecareercentre.MainActivity.sfsClient;

/**
 * Created by Raju on 12/6/2016.
 */

public class CandidatesAcceptedJobList extends AppCompatActivity implements View.OnClickListener {

    TextView candidates_accept_list_msg_txt;
    ListView listView;
    public static SFSArray mData;
    public static CandidatesAcceptListViewAdapter candidateacceptlistviewadapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.candidates_accept_joblist_activity);

        Intent intent = getIntent();
        int jobId = 0;
        jobId = intent.getIntExtra("job_id",0);

        candidates_accept_list_msg_txt = (TextView) findViewById(R.id.candidates_accept_list_msg);

        listView = (ListView) findViewById(R.id.candidates_accept_list);
        mData = new SFSArray();
        candidateacceptlistviewadapter = new CandidatesAcceptListViewAdapter(CandidatesAcceptedJobList.this,mData);
        listView.setAdapter(candidateacceptlistviewadapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;
                ISFSObject sfso = MainActivity.userClass.getCandidatesAcceptJobList().getSFSObject(position);
                ISFSObject candidate_details = sfso.getSFSObject("candidate_details");
                ISFSObject candidate_cv_details = sfso.getSFSObject("candidate_cv_details");

                CandidateCV candidateCV = new CandidateCV();
                //candidate_details
                candidateCV.setCandidate_name(candidate_details.getUtfString("name"));
                candidateCV.setEmail(candidate_details.getUtfString("email"));
                candidateCV.setContact_number(candidate_details.getUtfString("contact_number"));
                candidateCV.setEducation_qualification(candidate_details.getUtfString("qualification"));
                //candidate_cv_details
                candidateCV.setDesignation(candidate_cv_details.getUtfString("designation"));
                candidateCV.setZipcode(candidate_cv_details.getUtfString("zipcode"));
                candidateCV.setState(candidate_cv_details.getUtfString("state"));
                candidateCV.setCity(candidate_cv_details.getUtfString("city"));
                candidateCV.setCurrent_working_skill(candidate_cv_details.getUtfString("current_working_skill"));
                candidateCV.setKnown_skills(candidate_cv_details.getUtfString("known_skills"));
                candidateCV.setTotalExperience(candidate_cv_details.getUtfString("total_experience"));
                candidateCV.setJobType(candidate_cv_details.getUtfString("job_type"));

                candidateCV.setProjects_list(sfso.getSFSArray("candidate_projects"));
                MainActivity.userClass.setCandidateCV(candidateCV);

                Intent intent = new Intent(CandidatesAcceptedJobList.this, AcceptedCandidatesCV.class);
                intent.putExtra("positionNo", itemPosition);
                startActivity(intent);
            }
        });

        //TODO: Call
        ISFSObject sfso = new SFSObject();
        sfso.putInt("job_id",jobId);
        sfsClient.send(new ExtensionRequest(Commands.GET_CANDIDATES_LIST_APPLIED, sfso));
    }

    public void loadCandidateAcceptView(){
        mData = new SFSArray();
        candidateacceptlistviewadapter.clear();
        int adapter_candidatesAcceptJobList = candidateacceptlistviewadapter.mData_candidate_accept.size();
        ISFSArray candidatesAcceptJobList = new SFSArray();
        candidatesAcceptJobList = MainActivity.userClass.getCandidatesAcceptJobList();
        Log.e("loadCandidateAcceptView",""+candidatesAcceptJobList.size());
        Log.e("adapter_",""+adapter_candidatesAcceptJobList+"::: "+candidates_accept_list_msg_txt);
        for (int x = 0; x < candidatesAcceptJobList.size(); x++) {
            ISFSObject obj = candidatesAcceptJobList.getSFSObject(x);
            mData.addSFSObject(obj);
            if(adapter_candidatesAcceptJobList == 0)
                candidateacceptlistviewadapter.mData_candidate_accept.addSFSObject(obj);

            //job_list_reverse.addSFSObject(obj);
        }
        // MainActivity.userClass.setJobList(job_list_reverse);
        candidateacceptlistviewadapter.notifyDataSetChanged();
        if(candidates_accept_list_msg_txt != null)
        {
            if(candidatesAcceptJobList.size() == 0)
                candidates_accept_list_msg_txt.setVisibility(View.VISIBLE);
            else
                candidates_accept_list_msg_txt.setVisibility(View.INVISIBLE);
        }
        Log.e("Check ","candidateacceptlistviewadapter mData_candidate_accept ................ "+candidateacceptlistviewadapter.mData_candidate_accept.size());
        Log.e("AFTER ","MainActivity.userClass.getCandidatesAcceptJobList Size................ "+MainActivity.userClass.getCandidatesAcceptJobList().size());
    }

    @Override
    public void onClick(View v) {

    }
}
