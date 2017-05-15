package com.example.pandrajus.onlinecareercentre.extensions.candidate;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pandrajus.onlinecareercentre.MainActivity;
import com.example.pandrajus.onlinecareercentre.R;
import com.example.pandrajus.onlinecareercentre.model.CandidateCV;
import com.smartfoxserver.v2.entities.data.SFSArray;
import com.smartfoxserver.v2.entities.data.SFSObject;

import java.util.ArrayList;

/**
 * Created by Pandrajus on 11/19/2016.
 */
public class ViewCandidateCV extends AppCompatActivity implements View.OnClickListener{

    FloatingActionButton fab_create_project;
    LinearLayout projectsLL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_resume);

        CandidateCV candidateCV = new CandidateCV();
        candidateCV = MainActivity.userClass.getCandidateCV();

        TextView username = (TextView)findViewById(R.id.username);
        username.setText(MainActivity.userClass.getUsername());

        TextView qualification = (TextView)findViewById(R.id.candidate_qualification);
        qualification.setText(MainActivity.userClass.getQualification());

        TextView email = (TextView)findViewById(R.id.candidate_email);
        email.setText(MainActivity.userClass.getEmail());

        TextView contactNo = (TextView)findViewById(R.id.candidate_contactno);
        contactNo.setText(MainActivity.userClass.getContactNo());

        TextView city = (TextView)findViewById(R.id.city);
        city.setText(candidateCV.getCity()+",");

        TextView state = (TextView)findViewById(R.id.state);
        state.setText(candidateCV.getState());

        TextView designation = (TextView)findViewById(R.id.candidate_designation);
        designation.setText(candidateCV.getDesignation());

        TextView knownskills = (TextView)findViewById(R.id.candidate_skill);
        knownskills.setText(candidateCV.getKnown_skills());

        TextView totalexperience = (TextView)findViewById(R.id.candidate_total_exp);
        totalexperience.setText(candidateCV.getTotalExperience()+" Years");

        TextView jobtype = (TextView)findViewById(R.id.candidate_jobtype);
        jobtype.setText(candidateCV.getJobType());

        fab_create_project = (FloatingActionButton) findViewById(R.id.fab_create_project);
        fab_create_project.setOnClickListener(this);

        TextView tv = (TextView)findViewById(R.id.project_heading);

        projectsLL = (LinearLayout)findViewById(R.id.projectsLL);
        SFSArray projects_list = new SFSArray();
        projects_list = (SFSArray) candidateCV.getProjects_list();
        if(projects_list.size() > 0)
        {
            //TODO : Add Projects
            tv.setVisibility(View.VISIBLE);

            for(int i=0; i < projects_list.size(); i++)
            {
                SFSObject sfsObj = new SFSObject();
                sfsObj = (SFSObject) projects_list.getSFSObject(i);

                projectsLL.addView(generateView(sfsObj));
                Log.e("projects_list"," : "+sfsObj.getUtfString("name"));
                /*ImageView iv = new ImageView(this);
                iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                iv.getLayoutParams().height = 70;
                iv.getLayoutParams().width = 70;
                iv.setPadding(8, 4, 2, 4);
                iv.setImageResource(getImageResource());
                ll.addView(iv);*/

            }
        }
        else
        {
            //TODO : Do Nothing
            tv.setVisibility(View.INVISIBLE);
        }

    }

    private View generateView(SFSObject sfsObj) {

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        ll.setLayoutParams(LLParams);

        View vi = new View(this);
        vi.setMinimumHeight(10);
        ll.addView(vi);

        TextView tv1 = new TextView(this);
        tv1.setText("Title");
        tv1.setTextSize(20);
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setTextColor(Color.BLACK);
        ll.addView(tv1);

        TextView tv2 = new TextView(this);
        tv2.setText("\t\t"+sfsObj.getUtfString("name"));
        tv2.setTextSize(30);
        tv2.setTextColor(Color.GRAY);
        ll.addView(tv2);

        TextView tv3 = new TextView(this);
        tv3.setText("Description");
        tv3.setTextSize(20);
        tv3.setTextColor(Color.BLACK);
        ll.addView(tv3);

        TextView tv4 = new TextView(this);
        tv4.setText("\t\t"+sfsObj.getUtfString("description"));
        tv4.setTextSize(30);
        tv4.setTextColor(Color.GRAY);
        ll.addView(tv4);

        TextView tv5 = new TextView(this);
        tv5.setText("Tenure");
        tv5.setTextSize(20);
        tv5.setTextColor(Color.BLACK);
        ll.addView(tv5);

        TextView tv6 = new TextView(this);
        tv6.setText("\t\t"+sfsObj.getUtfString("tenure"));
        tv6.setTextSize(30);
        tv6.setTextColor(Color.GRAY);
        ll.addView(tv6);

        TextView tv7 = new TextView(this);
        tv7.setText("Technologies");
        tv7.setTextSize(20);
        tv7.setTextColor(Color.BLACK);
        ll.addView(tv7);

        TextView tv8 = new TextView(this);
        tv8.setText("\t\t"+sfsObj.getUtfString("technologies"));
        tv8.setTextSize(30);
        tv8.setTextColor(Color.GRAY);
        ll.addView(tv8);

        TextView tv9 = new TextView(this);
        tv9.setText("Members");
        tv9.setTextSize(20);
        tv9.setTextColor(Color.BLACK);
        ll.addView(tv9);

        TextView tv10 = new TextView(this);
        tv10.setText("\t\t"+sfsObj.getUtfString("members"));
        tv10.setTextSize(30);
        tv10.setTextColor(Color.GRAY);
        ll.addView(tv10);

        TextView tv11 = new TextView(this);
        tv11.setText("Role");
        tv11.setTextSize(20);
        tv11.setTextColor(Color.BLACK);
        ll.addView(tv11);

        TextView tv12 = new TextView(this);
        tv12.setText("\t\t"+sfsObj.getUtfString("role"));
        tv12.setTextSize(30);
        tv12.setTextColor(Color.GRAY);
        ll.addView(tv12);

        if(sfsObj.getUtfString("url").length() > 0) {
            TextView tv13 = new TextView(this);
            tv13.setText("Url");
            tv13.setTextSize(20);
            tv13.setTextColor(Color.BLACK);
            ll.addView(tv13);

            TextView tv14 = new TextView(this);
            tv14.setText("\t\t" + sfsObj.getUtfString("url"));
            tv14.setTextSize(30);
            tv14.setTextColor(Color.GRAY);
            ll.addView(tv14);
        }

        View vi1 = new View(this);
        vi.setMinimumHeight(10);
        ll.addView(vi1);

        View vi2 = new View(this);
        vi.setMinimumHeight(10);
        ll.addView(vi2);

        return ll;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, CreateCandidateProject.class);
        startActivity(intent);
    }

    public String getImageName() {
        return imagename;
    }

    public void setImageName(String imagename) {
        this.imagename = imagename;
    }

    private int getImageResource() {
        return this.getResources().getIdentifier(imagename, null, this.getPackageName());
    }

    String imagename = "drawable/contacts_icon";

}
