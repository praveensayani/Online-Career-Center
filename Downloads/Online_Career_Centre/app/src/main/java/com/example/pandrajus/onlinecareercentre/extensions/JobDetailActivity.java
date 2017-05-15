package com.example.pandrajus.onlinecareercentre.extensions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pandrajus.onlinecareercentre.MainActivity;
import com.example.pandrajus.onlinecareercentre.R;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;

/**
 * Created by Pandrajus on 11/5/2016.
 */
public class JobDetailActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobdetail_activity);

        Intent intent = getIntent();
        int positionNo = 0;
        positionNo = intent.getIntExtra("positionNo",0);

        /*Toast.makeText(getApplicationContext(),
                "positionNo :"+positionNo, Toast.LENGTH_LONG)
                .show();*/

        ISFSObject sfsObj = null;
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

        TextView zipcode = (TextView)findViewById(R.id.zipcode);
        zipcode.setText(sfsObj.getUtfString("zipcode"));

        TextView state = (TextView)findViewById(R.id.state);
        state.setText(sfsObj.getUtfString("state"));

        TextView city = (TextView)findViewById(R.id.city);
        city.setText(sfsObj.getUtfString("city"));

        TextView category = (TextView)findViewById(R.id.category);
        category.setText(sfsObj.getUtfString("category"));

        TextView requiredskills = (TextView)findViewById(R.id.requiredskills);
        requiredskills.setText(sfsObj.getUtfString("requiredskills"));

        TextView qualification = (TextView)findViewById(R.id.qualification);
        qualification.setText(sfsObj.getUtfString("qualification"));

        TextView jobtype = (TextView)findViewById(R.id.jobtype);
        jobtype.setText(sfsObj.getUtfString("jobtype"));

    }
}
