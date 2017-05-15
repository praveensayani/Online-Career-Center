package com.example.pandrajus.onlinecareercentre.extensions.candidate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pandrajus.onlinecareercentre.MainActivity;
import com.example.pandrajus.onlinecareercentre.R;
import com.example.pandrajus.onlinecareercentre.utils.Commands;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;

import sfs2x.client.requests.ExtensionRequest;

/**
 * Created by Pandrajus on 11/27/2016.
 */
public class CandidateProfile extends AppCompatActivity implements View.OnClickListener{

    Button backBtn,viewCVBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.candidate_profile);

        TextView username = (TextView)findViewById(R.id.username);
        username.setText(MainActivity.userClass.getUsername());

        TextView qualification = (TextView)findViewById(R.id.candidate_qualification);
        qualification.setText(MainActivity.userClass.getQualification());

        TextView email = (TextView)findViewById(R.id.candidate_email);
        email.setText(MainActivity.userClass.getEmail());

        TextView contactNo = (TextView)findViewById(R.id.candidate_contactno);
        contactNo.setText(MainActivity.userClass.getContactNo());

        backBtn = (Button) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);

        viewCVBtn = (Button) findViewById(R.id.view_cv);
        viewCVBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.view_cv)
        {
            ISFSObject sfso = new SFSObject();
            sfso.putUtfString("email", MainActivity.userClass.getEmail());
            MainActivity.sfsClient.send(new ExtensionRequest(Commands.GET_CANDIDATE_CV, sfso));
        }
        else if(v.getId() == R.id.change_pwd)
        {

        }
        else if(v.getId() == R.id.back_btn)
        {
            ISFSObject sfso = new SFSObject();
            sfso.putUtfString("name",MainActivity.userClass.getUsername());
            sfso.putUtfString("email", MainActivity.userClass.getEmail());
            MainActivity.sfsClient.send(new ExtensionRequest(Commands.GET_SUBSCRIBE_TECHNOLOGIES, sfso));
        }
    }
}
