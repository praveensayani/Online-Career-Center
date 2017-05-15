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
 * Created by Pandrajus on 11/30/2016.
 */
public class CandidateListViewAdapter extends BaseAdapter {

    private Context mContext;
    public SFSArray mData_candidate;

    public CandidateListViewAdapter(Context c, SFSArray data) {
        Log.d("Initializing","CandidateListViewAdapter");
        mContext = c;
        this.mData_candidate = data;

    }

    @Override
    public int getCount() {
        return mData_candidate.size();
    }

    @Override
    public ISFSObject getItem(int position) {
        return mData_candidate.getSFSObject(position);
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
        convertView = inflater.inflate(R.layout.single_row, parent, false);
        SFSObject obj = (SFSObject)getItem(position);
        Log.e("Position"," NO : "+position);
        //setImageNameBySkillValue(obj.getUtfString("requiredskills"));
        //ImageView logo_iv = (ImageView)convertView.findViewById(R.id.logo);
        //logo_iv.setImageResource(R.drawable.java);

        Log.e("Position",obj.getUtfString("position"));
        TextView jobPosition = (TextView)convertView.findViewById(R.id.jobposition);
        jobPosition.setText(obj.getUtfString("position"));

        Log.e("Status",obj.getUtfString("createddate"));
        TextView createdDate = (TextView)convertView.findViewById(R.id.status);
        createdDate.setText(obj.getUtfString("createddate"));

        Log.e("city",obj.getUtfString("city"));
        Log.e("state",obj.getUtfString("state"));
        TextView location = (TextView)convertView.findViewById(R.id.location);
        location.setText(obj.getUtfString("city")+", "+obj.getUtfString("state")+".");

        return convertView;
    }

    public void clear() {

    }
}