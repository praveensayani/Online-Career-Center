package com.example.pandrajus.onlinecareercentre;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;

import sfs2x.client.requests.ExtensionRequest;

import static com.example.pandrajus.onlinecareercentre.MainActivity.sfsClient;


public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = this.getClass().getSimpleName();
    private final static String TAB_TAG_COMPANY = "Company";
    private final static String TAB_TAG_CANDIDATE = "Candidate";

    TabHost mTabHost;
    Button companySignUpBtn,candidateSignUpBtn;
    EditText name,email,pwd,cnfpwd,contactNo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        addTab();
        MainActivity.currentactivity = SignupActivity.this;
    }

    private void addTab() {
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        // The tabs
        mTabHost.setup();
        mTabHost.addTab(newTab(TAB_TAG_COMPANY, R.string.company, R.id.tab1));
        mTabHost.addTab(newTab(TAB_TAG_CANDIDATE, R.string.candidate, R.id.tab2));

        Spinner spinner = (Spinner) findViewById(R.id.candidate_qualification);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.qualification_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        addButtonListeners();
    }

    private void addButtonListeners() {
        companySignUpBtn = (Button) findViewById(R.id.company_sign_up);
        companySignUpBtn.setOnClickListener(this);
        /*companySignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }


        });*/

        candidateSignUpBtn = (Button) findViewById(R.id.candidate_sign_up);
        candidateSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * Create a TabSpec with the given tag, label and content
     *
     * @param tag
     * @param labelId
     * @param tabContentId
     * @return
     */
    private TabHost.TabSpec newTab(String tag, int labelId, int tabContentId) {
        View indicator = LayoutInflater.from(this).inflate(R.layout.tab_header,
                (ViewGroup) findViewById(android.R.id.tabs), false);
        TextView label = (TextView) indicator.findViewById(android.R.id.title);
        label.setText(labelId);
       /* if (TAB_TAG_CANDIDATE.equals(tag)) {
            labelTagUsers = label;
        }*/
        TabHost.TabSpec tabSpec = mTabHost.newTabSpec(tag);
        tabSpec.setIndicator(indicator);
        tabSpec.setContent(tabContentId);
        return tabSpec;
    }

    @Override
    public void onClick(View v) {
        Log.e(TAG, String.valueOf(R.id.company_sign_up)+"onClick: "+String.valueOf(v.getId()) );
        if((v.getId()) == R.id.company_sign_up){
            /*Toast.makeText(this, "company_sign_up", Toast.LENGTH_SHORT).show();*/
            boolean isValid = checkCompanyValidations();
            if(isValid)
            {
                if(sfsClient.isConnected())
                {
                    Toast.makeText(this, "Validation Successful.", Toast.LENGTH_SHORT).show();
                    ISFSObject sfso = new SFSObject();
                    sfso.putUtfString("name",name.getText().toString());
                    sfso.putUtfString("email",email.getText().toString());
                    sfso.putUtfString("pwd",pwd.getText().toString());
                    sfso.putUtfString("contactNo",contactNo.getText().toString());
                    sfso.putUtfString("type",TAB_TAG_COMPANY);
                    sfsClient.send(new ExtensionRequest("create_user", sfso));
                }
                else
                {
                    Toast.makeText(this, "Validation Successful, Connection Lost", Toast.LENGTH_LONG).show();
                    sfsClient.connect(MainActivity.DEFAULT_SERVER_ADDRESS, Integer.parseInt(MainActivity.DEFAULT_SERVER_PORT));
                    if(sfsClient.isConnected())
                    {
                        ISFSObject sfso = new SFSObject();
                        sfso.putUtfString("name",name.getText().toString());
                        sfso.putUtfString("email",email.getText().toString());
                        sfso.putUtfString("pwd",pwd.getText().toString());
                        sfso.putUtfString("contactNo",contactNo.getText().toString());
                        sfso.putUtfString("type",TAB_TAG_COMPANY);
                        sfsClient.send(new ExtensionRequest("create_user", sfso));
                    }
                }
            }
            else {
                Toast.makeText(this, "Validation Failed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkCompanyValidations() {
        boolean isValid = false;
        boolean isName = false;
        boolean isEmail = false;
        boolean isPwd = false;
        boolean isCnfPwd = false;
        boolean isContactNo = false;

        name = (EditText)findViewById(R.id.company_name);
        if(name.getText().toString().length() > 2)
        {
            isName = true;
        }
        email = (EditText)findViewById(R.id.company_email);
        String emailValidate = email.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(email.getText().toString().length() > 0 && emailValidate.matches(emailPattern))
        {
            isEmail = true;
        }
        pwd = (EditText)findViewById(R.id.company_pwd);
        if(pwd.getText().toString().length() > 5)
        {
            isPwd = true;
        }
        cnfpwd = (EditText)findViewById(R.id.company_cnfpwd);
        if(cnfpwd.getText().toString().length() > 5)
        {
            if(pwd.getText().toString().equals(cnfpwd.getText().toString()))
                isCnfPwd = true;

        }
        contactNo = (EditText)findViewById(R.id.company_contactno);
        if(contactNo.getText().toString().length() == 10)
        {
            isContactNo = true;
        }

        if(isName && isEmail && isPwd && isCnfPwd && isContactNo)
            isValid = true;
        Log.e(TAG, "checkCompanyValidations: "+name.getText().toString() );

        return isValid;
    }
}
