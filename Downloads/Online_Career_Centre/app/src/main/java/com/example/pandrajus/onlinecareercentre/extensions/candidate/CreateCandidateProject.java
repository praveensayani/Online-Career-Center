package com.example.pandrajus.onlinecareercentre.extensions.candidate;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pandrajus.onlinecareercentre.MainActivity;
import com.example.pandrajus.onlinecareercentre.R;
import com.example.pandrajus.onlinecareercentre.utils.Commands;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;

import sfs2x.client.requests.ExtensionRequest;

/**
 * Created by Pandrajus on 11/29/2016.
 */
public class CreateCandidateProject extends AppCompatActivity implements View.OnClickListener{

    Button createProjectBtn;
    String name,description,tenure,technologies,members,role,url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_candidate_project);

        createProjectBtn = (Button) findViewById(R.id.create_project_btn);
        createProjectBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        boolean isValid = validateProject_Data();
        if(isValid)
        {
            ISFSObject sfso = new SFSObject();
            sfso.putUtfString("email", MainActivity.userClass.getEmail());
            sfso.putUtfString("name", name);
            sfso.putUtfString("description", description);
            sfso.putUtfString("tenure", tenure);
            sfso.putUtfString("technologies", technologies);
            sfso.putUtfString("members", members);
            sfso.putUtfString("role", role);
            sfso.putUtfString("url", url);
            MainActivity.sfsClient.send(new ExtensionRequest(Commands.CREATE_CANDIDATE_PROJECT, sfso));
        }
        else
        {
            Toast.makeText(this,"Incorrect Details.",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateProject_Data() {
        boolean isValid = false;
        boolean isName = false,isDescription = false,isTenure = false,isTechnologies = false,isMembers = false,isRole = false;

        EditText name_txt = (EditText)findViewById(R.id.project_name);
        if(name_txt.getText().toString().length() > 3)
        {
            isName = true;
            name = name_txt.getText().toString();
        }

        EditText desc_txt = (EditText)findViewById(R.id.project_desc);
        if(desc_txt.getText().toString().length() > 10)
        {
            isDescription = true;
            description = desc_txt.getText().toString();
        }

        EditText tenure_txt = (EditText)findViewById(R.id.project_tenure);
        if(tenure_txt.getText().toString().length() >= 1)
        {
            isTenure = true;
            tenure = tenure_txt.getText().toString();
        }

        EditText technologies_txt = (EditText)findViewById(R.id.project_tech);
        if(technologies_txt.getText().toString().length() >= 1)
        {
            isTechnologies = true;
            technologies = technologies_txt.getText().toString();
        }

        EditText members_txt = (EditText)findViewById(R.id.project_members);
        if(members_txt.getText().toString().length() >= 1)
        {
            isMembers = true;
            members = members_txt.getText().toString();
        }

        EditText role_txt = (EditText)findViewById(R.id.project_role);
        if(role_txt.getText().toString().length() > 2)
        {
            isRole = true;
            role = role_txt.getText().toString();
        }

        EditText url_txt = (EditText)findViewById(R.id.project_url);
        url = url_txt.getText().toString();

        if(isName && isDescription && isTenure && isTechnologies && isMembers && isRole )
            isValid = true;

        return  isValid;
    }
}
