package com.example.pandrajus.onlinecareercentre.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pandrajus.onlinecareercentre.R;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSArray;
import com.smartfoxserver.v2.entities.data.SFSObject;

/**
 * Created by Raju on 12/6/2016.
 */

public class CandidatesAcceptListViewAdapter  extends BaseAdapter {

    private Context mContext;
    public SFSArray mData_candidate_accept;

    public CandidatesAcceptListViewAdapter(Context c, SFSArray data) {
        Log.d("Initializing","CandidatesAcceptListViewAdapter");
        mContext = c;
        this.mData_candidate_accept = data;

    }

    @Override
    public int getCount() {
        return mData_candidate_accept.size();
    }

    @Override
    public ISFSObject getItem(int position) {
        return mData_candidate_accept.getSFSObject(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private String imagename = "drawable/java";

    public String getImageName() {
        return imagename;
    }

    public void setImageName(String imagename) {
        this.imagename = imagename;
    }

    private int getImageResource() {
        return mContext.getResources().getIdentifier(imagename, null, mContext.getPackageName());
    }

    private void setImageNameBySkillValue(String reqskill) {
        String reqSkill = reqskill.toLowerCase();
        if(reqSkill == "unix")
        {
            setImageName("drawable/android");
        }
        else
            setImageName("drawable/"+reqSkill);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        convertView = inflater.inflate(R.layout.candidate_accept_single_row, parent, false);
        SFSObject candidate_data = (SFSObject)getItem(position);
        SFSObject obj = (SFSObject)candidate_data.getSFSObject("candidate_details");
        Log.e("mData_candidate_accept"," NO : "+position);
        Log.e("SFSOBJ"," NO : "+obj.getSFSObject("candidate_details"));
        //setImageNameBySkillValue(obj.getUtfString("requiredskills"));
        //ImageView logo_iv = (ImageView)convertView.findViewById(R.id.logo);
        //logo_iv.setImageResource(R.drawable.java);

        Log.e("name",obj.getUtfString("name"));
        TextView candidateName = (TextView)convertView.findViewById(R.id.candidate_name);
        candidateName.setText(obj.getUtfString("name"));

        Log.e("email",obj.getUtfString("email"));
        TextView email = (TextView)convertView.findViewById(R.id.email);
        email.setText(obj.getUtfString("email"));

        Log.e("contact_number",obj.getUtfString("contact_number"));
        TextView contactno = (TextView)convertView.findViewById(R.id.contactno);
        contactno.setText(obj.getUtfString("contact_number"));

        return convertView;
    }

    public void clear() {

    }
}