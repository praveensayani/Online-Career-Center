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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pandrajus.onlinecareercentre.MainActivity;
import com.example.pandrajus.onlinecareercentre.R;
import com.example.pandrajus.onlinecareercentre.adapters.MainChipViewAdapter;
import com.example.pandrajus.onlinecareercentre.chipviewpackage.Chip;
import com.example.pandrajus.onlinecareercentre.chipviewpackage.ChipView;
import com.example.pandrajus.onlinecareercentre.chipviewpackage.ChipViewAdapter;
import com.example.pandrajus.onlinecareercentre.chipviewpackage.OnChipClickListener;
import com.example.pandrajus.onlinecareercentre.model.Tag;
import com.example.pandrajus.onlinecareercentre.utils.Commands;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;

import java.util.ArrayList;
import java.util.List;

import sfs2x.client.requests.ExtensionRequest;

import static com.example.pandrajus.onlinecareercentre.MainActivity.sfsClient;

/**
 * Created by Pandrajus on 11/12/2016.
 */
public class CandidateSubscriptions extends AppCompatActivity  implements AdapterView.OnItemSelectedListener,View.OnClickListener,OnChipClickListener{

    Spinner spinner_required_skills;
    Button subscribe_btn;
    String reqSkills="";

    private List<Chip> mTagList1;
    private List<String> subscribedlist;
    private ChipView mTextChipLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscribe_technology);

        subscribedlist = new ArrayList<>();

        Intent intent = getIntent();
        subscribedlist = intent.getStringArrayListExtra("subscribed_technologies_arr");

        addTag();
        drawChip();

        spinner_required_skills = (Spinner) findViewById(R.id.list_technologies);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_req_skills = ArrayAdapter.createFromResource(this,
                R.array.required_skills_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter_req_skills.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_required_skills.setAdapter(adapter_req_skills);
        spinner_required_skills.setOnItemSelectedListener(this);

        subscribe_btn = (Button) findViewById(R.id.subscribe_btn);
        subscribe_btn.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.e("position ",""+position+", "+parent.getId());
        Log.e("view.getId() ",""+view.getId()+" , category : "+R.id.category+" , required_skills : "+R.id.required_skills+", education_qualification : "+R.id.education_qualification);
        TextView tv = (TextView) view;
        switch(parent.getId()) {
            case R.id.list_technologies:
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    reqSkills = "";
                }
                else {
                    tv.setTextColor(Color.BLACK);
                    reqSkills = spinner_required_skills.getSelectedItem().toString();
                    if(!isExist(reqSkills))
                    {
                        subscribedlist.add(reqSkills);
                        addTag();
                        drawChip();
                    }
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void addTag()
    {
        mTagList1 = new ArrayList<>();
        for(int i=0; i<subscribedlist.size(); i++)
        {
            Log.e("Subscribedlist","-----------"+subscribedlist.get(i));
            mTagList1.add(new Tag(subscribedlist.get(i)));
        }
        /*mTagList1.add(new Tag("Lorem"));
        mTagList1.add(new Tag("Ipsum dolor"));
        mTagList1.add(new Tag("Sit amet"));
        mTagList1.add(new Tag("Consectetur"));
        mTagList1.add(new Tag("adipiscing elit"));*/
    }

    private void drawChip(){

        // Adapter
        ChipViewAdapter adapterLayout = new MainChipViewAdapter(this);

        // Custom layout and background colors
        mTextChipLayout = (ChipView) findViewById(R.id.text_chip_layout);
        mTextChipLayout.setAdapter(adapterLayout);
        mTextChipLayout.setChipLayoutRes(R.layout.chip_close);
        mTextChipLayout.setChipBackgroundColor(getResources().getColor(R.color.blue));
        mTextChipLayout.setChipBackgroundColorSelected(getResources().getColor(R.color.green));
        mTextChipLayout.setChipList(mTagList1);
        mTextChipLayout.setOnChipClickListener(this);
    }


    @Override
    public void onChipClick(Chip chip) {
        Log.e("Name","onChipClick");
        removeFromSubscribe(chip.getText());
        addTag();
        //mTagList1.add(new Tag("NAGA"));
        drawChip();
        Log.e("Name","onChipClick"+chip.getText());
    }

    private boolean isExist(String reqSkills) {
        boolean isSkillExist = false;
        for(int i = 0; i<subscribedlist.size(); i++)
        {
            if(subscribedlist.get(i).equals(reqSkills))
            {
                isSkillExist = true;
                break;
            }
        }

        return isSkillExist;
    }

    private void removeFromSubscribe(String text) {

        List<String> temp_subscription = new ArrayList<>();

        for(int i = 0; i<subscribedlist.size(); i++)
        {
            if(subscribedlist.get(i).equals(text))
            {
                //TODO : Do Nothing
            }
            else
            {
                temp_subscription.add(subscribedlist.get(i));
            }
        }
        subscribedlist = new ArrayList<>();
        subscribedlist = temp_subscription;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "onClick.", Toast.LENGTH_SHORT).show();
        if((v.getId()) == R.id.subscribe_btn){
            //boolean isValid = checkSubscribtions();
            boolean isValid = true;
            if(isValid)
            {
                //TODO: Send SFSObject for JobCreation
                Toast.makeText(this, "Subscribtion.", Toast.LENGTH_SHORT).show();
                ISFSObject sfso = new SFSObject();
                sfso.putUtfString("username", MainActivity.userClass.getUsername());
                sfso.putUtfString("email",MainActivity.userClass.getEmail());
                sfso.putUtfStringArray("subscribed_technologies",subscribedlist);
                sfsClient.send(new ExtensionRequest(Commands.SUBSCRIBE_TECHNOLOGIES, sfso));
            }
            else
            {
                Toast.makeText(this,"Please Fill Details.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
