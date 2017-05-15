package com.example.pandrajus.onlinecareercentre.extensions;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pandrajus.onlinecareercentre.MainActivity;
import com.example.pandrajus.onlinecareercentre.R;
import com.example.pandrajus.onlinecareercentre.utils.Commands;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;

import sfs2x.client.requests.ExtensionRequest;

import static com.example.pandrajus.onlinecareercentre.MainActivity.sfsClient;

/**
 * Created by Pandrajus on 10/27/2016.
 */
public class CreateJobActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{

    Spinner spinner_category,spinner_required_skills,spinner_EQ;
    RadioGroup radioJobTypeGroup,radioJobEndTimeGroup;
    String qualification = "",category="",reqSkills="",jobType="",jobEndTime="";
    EditText position,description,zipcode,state,city;
    Button createJobBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createjob_activity);

        loadSpinners();
        addListenersToRadioGroup();

        createJobBtn = (Button)findViewById(R.id.create_job);
        createJobBtn.setOnClickListener(this);

    }

    private void addListenersToRadioGroup() {
        radioJobTypeGroup = (RadioGroup) findViewById(R.id.radioJobTypeGroup);
        // get selected radio button from radioGroup
       /* int selectedId = radioJobTypeGroup.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        RadioButton radioJobType = (RadioButton) findViewById(selectedId);
        jobType = String.valueOf(radioJobType.getText());*/
        jobType = "temporary";
        /*radioJobEndTimeGroup = (RadioGroup) findViewById(R.id.radioJobEndTimeGroup);
        // get selected radio button from radioGroup
        int selectedJobEndTime = radioJobEndTimeGroup.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        RadioButton radioJobEndTime = (RadioButton) findViewById(selectedJobEndTime);
        jobEndTime = String.valueOf(radioJobEndTime.getText());*/
        jobEndTime = "12";
        Toast.makeText(this,jobEndTime, Toast.LENGTH_SHORT).show();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.temporary:
                if (checked)
                    jobType = getString(R.string.hint_temporary);
                    break;
            case R.id.permanent:
                if (checked)
                    jobType = getString(R.string.hint_permanent);
                    break;
           /* case R.id.hours_12:
                if (checked)
                    jobEndTime = "12";
                    break;
            case R.id.hours_24:
                if (checked)
                    jobEndTime = "24";
                    break;
            case R.id.hours_48:
                if (checked)
                    jobEndTime = "48";
                    break;
            case R.id.hours_72:
                if (checked)
                    jobEndTime = "72";
                    break;*/
        }
    }

    private void loadSpinners() {

        spinner_category = (Spinner) findViewById(R.id.category);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_category.setAdapter(adapter);
        spinner_category.setOnItemSelectedListener(this);

        spinner_required_skills = (Spinner) findViewById(R.id.required_skills);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_req_skills = ArrayAdapter.createFromResource(this,
                R.array.required_skills_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter_req_skills.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_required_skills.setAdapter(adapter_req_skills);
        spinner_required_skills.setOnItemSelectedListener(this);

        spinner_EQ = (Spinner) findViewById(R.id.education_qualification);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_eq = ArrayAdapter.createFromResource(this,
                R.array.qualification_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter_eq.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_EQ.setAdapter(adapter_eq);
        spinner_EQ.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.e("position ",""+position+", "+parent.getId());
        Log.e("view.getId() ",""+view.getId()+" , category : "+R.id.category+" , required_skills : "+R.id.required_skills+", education_qualification : "+R.id.education_qualification);
        TextView tv = (TextView) view;
        switch(parent.getId()) {
            case R.id.category:
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    category = "";
                }
                else {
                    tv.setTextColor(Color.BLACK);
                    category = spinner_category.getSelectedItem().toString();
                }
                    break;
            case R.id.required_skills:
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    reqSkills = "";
                }
                else {
                    tv.setTextColor(Color.BLACK);
                    reqSkills = spinner_required_skills.getSelectedItem().toString();
                }
                break;
            case R.id.education_qualification:
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    qualification = "";
                }
                else {
                    tv.setTextColor(Color.BLACK);
                    qualification = spinner_EQ.getSelectedItem().toString();
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        Log.e("onClick","ENTERED........................................................................");
        if((v.getId()) == R.id.create_job){
            boolean isValid = checkCreateJobValidations();
            if(isValid)
            {
                //TODO: Send SFSObject for JobCreation
                Toast.makeText(this, "Validation Successful.", Toast.LENGTH_SHORT).show();
                ISFSObject sfso = new SFSObject();
                sfso.putUtfString("createdby", MainActivity.userClass.getUsername());
                sfso.putUtfString("position",position.getText().toString());
                sfso.putUtfString("description",description.getText().toString());
                sfso.putUtfString("zipcode",zipcode.getText().toString());
                sfso.putUtfString("state",state.getText().toString());
                sfso.putUtfString("city",city.getText().toString());
                sfso.putUtfString("category",category);
                sfso.putUtfString("requiredskills",reqSkills);
                sfso.putUtfString("qualification",qualification);
                sfso.putUtfString("jobtype",jobType);
                sfso.putUtfString("jobendtime",jobEndTime);
                sfsClient.send(new ExtensionRequest(Commands.CREATE_JOB, sfso));
            }
            else
            {
                Toast.makeText(this,"Please Fill Details.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkCreateJobValidations() {
        boolean isValid = false;
        boolean isPosition = false;
        boolean isDescription = false;
        boolean isZipcode = false;
        boolean isState = false;
        boolean isCity = false;
        boolean isCategory = false;
        boolean isRequiredSkills = false;
        boolean isQualification = false;
        boolean isJobType = false;
        boolean isJobEndTime = false;

        position = (EditText)findViewById(R.id.position);
        if(position.getText().toString().length() > 2)
        {
            isPosition = true;
        }
        description = (EditText)findViewById(R.id.description);
        if(description.getText().toString().length() > 5)
        {
            isDescription = true;
        }
        zipcode = (EditText)findViewById(R.id.zipcode);
        if(zipcode.getText().toString().length() == 5)
        {
            isZipcode = true;
        }
        state = (EditText)findViewById(R.id.state);
        if(state.getText().toString().length() >= 2)
        {
            isState = true;
        }
        city = (EditText)findViewById(R.id.city);
        if(city.getText().toString().length() >= 2)
        {
            isCity = true;
        }
        if(category.length() > 0)
        {
            isCategory = true;
        }
        if(reqSkills.length() > 0)
        {
            isRequiredSkills = true;
        }
        if(qualification.length() > 0)
        {
            isQualification = true;
        }
        if(jobType.length() > 0)
        {
            isJobType = true;
        }
        if(jobEndTime.length() > 0)
        {
            isJobEndTime = true;
        }

        Log.e("Validation ",""+isPosition+" && "+isDescription+" && "+isZipcode+" && "+isState+" && "+isCity+" && "+isCategory+" && "+isRequiredSkills+" && "+isQualification+" && "+isJobType+" && "+isJobEndTime);

        if(isPosition && isDescription && isZipcode && isState && isCity && isCategory && isRequiredSkills && isQualification && isJobType && isJobEndTime)
        {
            isValid = true;
        }
        return isValid;
    }
}
