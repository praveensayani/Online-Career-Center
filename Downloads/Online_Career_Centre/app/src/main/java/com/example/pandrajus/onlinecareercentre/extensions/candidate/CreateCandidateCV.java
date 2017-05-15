package com.example.pandrajus.onlinecareercentre.extensions.candidate;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pandrajus.onlinecareercentre.MainActivity;
import com.example.pandrajus.onlinecareercentre.R;
import com.example.pandrajus.onlinecareercentre.utils.Commands;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;

import sfs2x.client.requests.ExtensionRequest;

/**
 * Created by Pandrajus on 11/27/2016.
 */
public class CreateCandidateCV extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    Button createCVBtn;
    Spinner spinner_jobtype;
    String designation,currentskill,totalexp,jobtype,city,state,zipcode,knownskills;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_candidate_cv);

        spinner_jobtype = (Spinner) findViewById(R.id.jobtype);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.job_type_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_item);
        // Apply the adapter to the spinner
        spinner_jobtype.setAdapter(adapter);
        spinner_jobtype.setOnItemSelectedListener(this);

        createCVBtn = (Button) findViewById(R.id.createcv_btn);
        createCVBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.createcv_btn)
        {
            boolean isValid = validateCV_Data();
            if(isValid)
            {
                knownskills = getKnownSkill();

                ISFSObject sfso = new SFSObject();
                sfso.putUtfString("email", MainActivity.userClass.getEmail());
                sfso.putUtfString("designation", designation);
                sfso.putUtfString("current_working_skill", currentskill);
                sfso.putUtfString("known_skills", knownskills);
                sfso.putUtfString("total_experience", totalexp);
                sfso.putUtfString("job_type", jobtype);
                sfso.putUtfString("city", city);
                sfso.putUtfString("state", state);
                sfso.putUtfString("zipcode", zipcode);
                MainActivity.sfsClient.send(new ExtensionRequest(Commands.CREATE_CANDIDATE_CV, sfso));
            }
            else
            {
                Toast.makeText(this,"Incorrect Details.",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getKnownSkill() {
        String knownskill = "";
        if(MainActivity.userClass.getSubscribed_technologies_arr().size() > 0)
        {
            for(int i=0; i< MainActivity.userClass.getSubscribed_technologies_arr().size(); i++)
            {
                if(i == 0)
                    knownskill = MainActivity.userClass.getSubscribed_technologies_arr().get(i);
                else //if(i > 0 && i < (MainActivity.userClass.getSubscribed_technologies_arr().size()-1))
                    knownskill = knownskill+","+MainActivity.userClass.getSubscribed_technologies_arr().get(i);
            }
        }
        else
            knownskill = currentskill;
        return knownskill;
    }

    private boolean validateCV_Data() {
        boolean isValid = false;

        EditText designation_txt = (EditText)findViewById(R.id.designation);
        if(designation_txt.getText().toString().length() > 2)
        {
            designation = designation_txt.getText().toString();
        }
        EditText currentskill_txt = (EditText)findViewById(R.id.current_working_skill);
        if(currentskill_txt.getText().toString().length() > 2)
        {
            currentskill = currentskill_txt.getText().toString();
        }
        EditText totalexp_txt = (EditText)findViewById(R.id.totalExperience);
        if(totalexp_txt.getText().toString().length() >= 1)
        {
            totalexp = totalexp_txt.getText().toString();
        }
        EditText city_txt = (EditText)findViewById(R.id.city);
        if(city_txt.getText().toString().length() > 2)
        {
            city = city_txt.getText().toString();
        }
        EditText state_txt = (EditText)findViewById(R.id.state);
        if(state_txt.getText().toString().length() > 2)
        {
            state = state_txt.getText().toString();
        }
        EditText zipcode_txt = (EditText)findViewById(R.id.zipcode);
        if(zipcode_txt.getText().toString().length() >= 5)
        {
            zipcode = zipcode_txt.getText().toString();
        }

        if(designation.length() > 0 && currentskill.length() > 0 && totalexp.length() > 0 && jobtype.length() > 0 && city.length() > 0 && state.length() > 0 && zipcode.length() > 0)
        {
            isValid = true;
        }

        return isValid;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView tv = (TextView) view;
        if(position == 0){
            // Set the hint text color gray
            //tv.setTextColor(Color.GRAY);
            jobtype = "";
        }
        else {
            tv.setTextColor(Color.BLACK);
            jobtype = spinner_jobtype.getSelectedItem().toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
