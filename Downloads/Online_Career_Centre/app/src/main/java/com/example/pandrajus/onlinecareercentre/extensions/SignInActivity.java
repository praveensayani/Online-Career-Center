package com.example.pandrajus.onlinecareercentre.extensions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import static com.example.pandrajus.onlinecareercentre.MainActivity.sfsClient;

/**
 * Created by Pandrajus on 10/26/2016.
 */
public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    Button signInBtn,signUpBtn;
    EditText email,pwd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);
        initUI();
    }

    private void initUI() {
        // Load the view references
        signInBtn = (Button) findViewById(R.id.sign_in);
        signInBtn.setOnClickListener(this);
        /*signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDo : Connect Zone and Login User
                String userNick = "Raju";
                String zoneName = "OnlineCareerCentre";
                Log.e(TAG, "onClick: "+userNick);
                if (VERBOSE_MODE) Log.v(TAG, "Login as '" + userNick + "' into " + zoneName);
                LoginRequest loginRequest = new LoginRequest(userNick, "", zoneName);
                sfsClient.send(loginRequest);
                //sfsClient.send(new LoginRequest(userVO.nickname, "", sfs.config.zone,null));
            }
        });*/
        signUpBtn = (Button) findViewById(R.id.sign_up);
        signUpBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.e("SignInAct","onClick : "+v.getId());
        if(R.id.sign_up == v.getId())
        {
            //ToDo : Change Activity to Signup View.
            //Toast.makeText(this,"SIGN UP",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        }
        else if(R.id.sign_in == v.getId())
        {
            //Toast.makeText(this,"SIGN IN",Toast.LENGTH_SHORT).show();
            boolean isValid = validateLoginDetails();

            if(isValid)
            {
                ISFSObject sfso = new SFSObject();
                //sfso.putUtfString("name",name.getText().toString().toLowerCase());
                sfso.putUtfString("email",email.getText().toString().toLowerCase());
                sfso.putUtfString("pwd", MainActivity.encryptPwd(pwd.getText().toString()));
                sfsClient.send(new ExtensionRequest(Commands.VALIDATE_USER_LOGIN, sfso));
            }
            else {
                Toast.makeText(this,"Invalid Details",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateLoginDetails() {
        boolean isValid = false;
        boolean isName = false;
        boolean isPwd = false;

        email = (EditText)findViewById(R.id.email);
        if(email.getText().toString().length() > 2)
        {
            isName = true;
        }

        pwd = (EditText)findViewById(R.id.user_Pwd);
        if(pwd.getText().toString().length() > 5)
        {
            isPwd = true;
        }

        if(isName && isPwd)
            isValid = true;

        return isValid;
    }
}
