package com.example.pandrajus.onlinecareercentre;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.pandrajus.onlinecareercentre.extensions.candidate.CandidateJobListView;
import com.example.pandrajus.onlinecareercentre.extensions.candidate.CandidateSubscriptions;
import com.example.pandrajus.onlinecareercentre.extensions.candidate.CreateCandidateCV;
import com.example.pandrajus.onlinecareercentre.extensions.candidate.ViewCandidateCV;
import com.example.pandrajus.onlinecareercentre.extensions.employer.CandidatesAcceptedJobList;
import com.example.pandrajus.onlinecareercentre.extensions.employer.JobListActivity;
import com.example.pandrajus.onlinecareercentre.extensions.SignInActivity;
import com.example.pandrajus.onlinecareercentre.model.CandidateCV;
import com.example.pandrajus.onlinecareercentre.model.User;
import com.example.pandrajus.onlinecareercentre.utils.Commands;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSArray;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

import sfs2x.client.SmartFox;
import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSEvent;
import sfs2x.client.requests.ExtensionRequest;
import sfs2x.client.requests.LoginRequest;

public class MainActivity extends AppCompatActivity implements IEventListener {

    private final String TAG = this.getClass().getSimpleName();

    private final static boolean DEBUG_SFS = true;
    private final static boolean VERBOSE_MODE = true;

    public static Context currentactivity= null;

    //private final static String DEFAULT_SERVER_ADDRESS = "10.0.2.2";
    //public final static String DEFAULT_SERVER_ADDRESS = "192.168.1.4";
    public final static String DEFAULT_SERVER_ADDRESS = "196.12.41.58";
    public final static String DEFAULT_SERVER_PORT = "9933";

    public static User userClass;

    private enum Status {
        DISCONNECTED, CONNECTED, CONNECTING, CONNECTION_ERROR, CONNECTION_LOST, LOGGED, IN_A_ROOM
    }

    Status currentStatus = null;

    public static SmartFox sfsClient;

    // Initializing a String Array
    String[] plants = new String[]{
            "Select an item...",
            "California sycamore",
            "Mountain mahogany",
            "Butterfly weed",
            "Carrot weed"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

        System.setProperty("java.net.preferIPv6Addresses", "false");
        initSmartFox();
        //initUI();
        loadSignInView();
        userClass = new User();
    }

    private void initSmartFox() {

        // Instantiate SmartFox client
        sfsClient = new SmartFox(DEBUG_SFS);

        // Add event listeners
        sfsClient.addEventListener(SFSEvent.CONNECTION, this);
        sfsClient.addEventListener(SFSEvent.CONNECTION_LOST, this);
        sfsClient.addEventListener(SFSEvent.LOGIN, this);
        sfsClient.addEventListener(SFSEvent.LOGIN_ERROR, this);
        sfsClient.addEventListener(SFSEvent.ROOM_JOIN, this);
        sfsClient.addEventListener(SFSEvent.USER_ENTER_ROOM, this);
        sfsClient.addEventListener(SFSEvent.USER_EXIT_ROOM, this);
        sfsClient.addEventListener(SFSEvent.PUBLIC_MESSAGE, this);
        //sfsClient.addEventListener(SFSEvent.EXTENSION_RESPONSE, onExtensionResponse);
        sfsClient.addEventListener(SFSEvent.EXTENSION_RESPONSE,this);

        if (VERBOSE_MODE)
            Log.v(TAG, "SmartFox created:" + sfsClient.isConnected() + " BlueBox enabled="
                    + sfsClient.useBlueBox());

        sfsClient.useBlueBox();
        //OnlineCareerCentre.zone
        connect(DEFAULT_SERVER_ADDRESS, DEFAULT_SERVER_PORT);
        //setStatus(Status.CONNECTING);
    }

    public void connect(String serverIP, String serverPort) {
        Log.e(TAG, "MainActivity -> serverIP : "+serverIP);
        Log.e(TAG, "serverPort: "+serverPort);
        // if the user have entered port number it uses it...
        if (serverPort.length() > 0) {
            int serverPortValue = Integer.parseInt(serverPort);
            // tries to connect to the server
            // connectToServer(serverIP, serverPortValue);
            sfsClient.connect(serverIP, serverPortValue);
        }
        // ...otherwise uses the default port number
        else {
            // tries to connect to the server
            // connectToServer(serverIP);
            sfsClient.connect(serverIP);
        }
    }

    /**
     * Frees the resources.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        disconnect();
        if (VERBOSE_MODE) Log.v(TAG, "Removing Listeners");
        sfsClient.removeAllEventListeners();
    }

    private void disconnect() {
        if (VERBOSE_MODE) Log.v(TAG, "Disconnecting");
        if (sfsClient.isConnected()) {
            if (VERBOSE_MODE) Log.v(TAG, "Disconnect: Disconnecting client");
            sfsClient.disconnect();
            if (VERBOSE_MODE) Log.v(TAG, "Disconnect: Disconnected ? " + !sfsClient.isConnected());
            // initSmartFox();
        }
    }



    @Override
    public void dispatch(final BaseEvent event) throws SFSException {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (VERBOSE_MODE)
                    Log.v(TAG,
                            "Dispatching " + event.getType() + " (arguments="
                                    + event.getArguments() + ")");
                if (event.getType().equalsIgnoreCase(SFSEvent.CONNECTION)) {
                    if (event.getArguments().get("success").equals(true)) {
                        Log.i(TAG, "run: Connection Success");
                        //setStatus(Status.CONNECTED, sfsClient.getConnectionMode());
                        // Login as guest in current zone
                        //showLayout(layoutLogin);
                        // sfsClient.send(new LoginRequest("", "",
                        // getString(R.string.example_zone)));
                        Random rand = new Random();
                        int index = 0;
                        index = rand.nextInt(1000);

                        String userNick = "guest"+index+"_OCC";
                        String zoneName = getString(R.string.ZONE_NAME);
                        Log.e(TAG, "onClick: "+userNick);
                        if (VERBOSE_MODE) Log.v(TAG, "Login as '" + userNick + "' into " + zoneName);
                        LoginRequest loginRequest = new LoginRequest(userNick, "", zoneName);
                        sfsClient.send(loginRequest);
                    }else {
                        //setStatus(Status.CONNECTION_ERROR);
                        //showLayout(layoutConnector);
                    }
                }else if (event.getType().equalsIgnoreCase(SFSEvent.CONNECTION_LOST)) {
                    Log.e(TAG,"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    Log.e(TAG,"CONNECTION_LOST " + event.getType() + " (arguments="+ event.getArguments() + ")");
                    Log.e(TAG,"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    disconnect();
                }else {
                    if (event.getType().equalsIgnoreCase(SFSEvent.LOGIN)) {
                        //setStatus(Status.LOGGED, sfsClient.getCurrentZone());
                        //sfsClient.send(new JoinRoomRequest(getString(R.string.example_lobby)));
                        Log.e(TAG, "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        Log.e(TAG, "Dispatching " + event.getType() + " (arguments=" + event.getArguments() + ")");
                        Log.e(TAG, "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    /*ISFSObject sfso = new SFSObject();
                    sfso.putBool("onStrikesResult", false);
                    sfso.putUtfString("tableid", "raju");
                    sfsClient.send(new ExtensionRequest("create_user", sfso));*/
                    } else {
                        Log.e(TAG, "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        Log.e(TAG, "create_user " + event.getType() + " (arguments=" + event.getArguments() + ")");
                        //Log.e(TAG, "create_user " + event.getType() + " (arguments=" + event.getArguments().get("cmd") + ")");
                        Log.e(TAG, "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        //Log.e(TAG, ""+event.getArguments().get("cmd").equals("create_user"));
                        if (event.getArguments().get("cmd").equals(Commands.CREATE_USER)) {
                            SFSObject sfso = new SFSObject();
                            sfso = (SFSObject) event.getArguments().get("params");
                            if(sfso.getBool("isInserted"))
                            {
                                loadSignInView();
                            }
                            else
                            {
                                if(sfso.getBool("isEmailExist"))
                                {
                                    showToast("Email Already Exists.");

                                   /* // 1. Instantiate an AlertDialog.Builder with its constructor
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.currentactivity);

                                    // 2. Chain together various setter methods to set the dialog characteristics
                                    builder.setMessage(R.string.candidate)
                                            .setTitle(R.string.app_name);

                                    // 3. Get the AlertDialog from create()
                                    AlertDialog dialog = builder.create();*/
                                }
                            }
                            Log.e(TAG, "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                            Log.e(TAG, "create_user " + event.getType() + " (arguments=" + sfso.getBool("isInserted") + ")");
                            Log.e(TAG, "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        }
                        else if (event.getArguments().get("cmd").equals(Commands.VALIDATE_USER_LOGIN)) {
                            SFSObject sfso = new SFSObject();
                            sfso = (SFSObject) event.getArguments().get("params");
                            if(sfso.getBool("isUserValid"))
                            {
                                //showToast("Login Successful");
                                //Set User Details
                                setUserDetails(sfso);
                                //if(userClass.getUserType().equals(getString(R.string.company)))
                                //showToast("BEFORE : "+userClass.getUserType());
                                if(sfso.getUtfString("user_type").equals("Company"))
                                {
                                    //showToast("AFTER : "+userClass.getUserType());
                                    //Load Job List
                                    loadJobListView();
                                }
                                else
                                {
                                    //Todo: Candidate View
                                   getSubscribedTechnologies();
                                }
                            }
                            else
                            {
                                showToast("Invalid Details");
                            }
                        }
                        else if (event.getArguments().get("cmd").equals(Commands.GET_SUBSCRIBE_TECHNOLOGIES)) {
                            SFSObject sfso = new SFSObject();
                            sfso = (SFSObject) event.getArguments().get("params");
                            Log.e("SFSARRAY",""+sfso.getSFSArray("subscribed_technologies_arr"));
                            loadCandidateSubscription(sfso.getSFSArray("subscribed_technologies_arr"));
                        }
                        else if (event.getArguments().get("cmd").equals(Commands.CREATE_JOB)) {
                            SFSObject sfso = new SFSObject();
                            sfso = (SFSObject) event.getArguments().get("params");
                            if(sfso.getBool("isInserted"))
                            {
                                showToast("Job Created Successfully");
                                loadJobListView();
                            }
                            else
                            {
                                showToast("Job Not Created.");
                            }
                        }
                        else if (event.getArguments().get("cmd").equals(Commands.JOBS_LIST)) {
                            Log.e("JOBS_LIST","params"+"----------------------------------------------------------------------");
                            SFSObject sfso = new SFSObject();
                            sfso = (SFSObject) event.getArguments().get("params");
                            //userClass.setJobList(sfso.getSFSArray("job_list"));
                            jobActivity.loadView(sfso.getSFSArray("job_list"));
                            //Log.e("ARRAY",""+sfso.getSFSArray("job_list"));
                        }
                        else if (event.getArguments().get("cmd").equals(Commands.SUBSCRIBE_TECHNOLOGIES)) {
                            Log.e("SUBSCRIBE_TECHNOLOGIES","params"+"----------------------------------------------------------------------");
                            SFSObject sfso = new SFSObject();
                            sfso = (SFSObject) event.getArguments().get("params");
                            if(sfso.getBool("isInserted"))
                            {
                                showToast("Successfully Subscribed.");
                                loadCandidateJobList();
                            }
                            else
                            {
                                showToast("Not Subscribed.");
                            }
                        }
                        else if (event.getArguments().get("cmd").equals(Commands.CANDIDATE_JOBS_LIST)) {
                            Log.e("CANDIDATE_JOBS_LIST",""+"----------------------------------------------------------------------");
                            SFSObject sfso = new SFSObject();
                            sfso = (SFSObject) event.getArguments().get("params");
                            //userClass.setJobList(sfso.getSFSArray("job_list"));
                            candidateJobListActivity.loadCandidateJobListView(sfso.getSFSArray("candidate_job_list_array"));
                            //Log.e("ARRAY",""+sfso.getSFSArray("job_list"));
                        }
                        else if(event.getArguments().get("cmd").equals(Commands.CREATE_CANDIDATE_CV))
                        {
                            Log.e("CREATE_CANDIDATE_CV","params"+"----------------------------------------------------------------------");
                            SFSObject sfso = new SFSObject();
                            sfso = (SFSObject) event.getArguments().get("params");
                            if(sfso.getBool("isInserted"))
                            {
                                showToast("Inserted CV");
                                sfso = new SFSObject();
                                sfso.putUtfString("email", MainActivity.userClass.getEmail());
                                sfsClient.send(new ExtensionRequest(Commands.GET_CANDIDATE_CV, sfso));
                            }
                            else
                            {
                                showToast("Not Created CV");
                            }
                        }
                        else if (event.getArguments().get("cmd").equals(Commands.GET_CANDIDATE_CV)) {
                            Log.e("GET_CANDIDATE_CV","params"+"----------------------------------------------------------------------");
                            SFSObject sfso = new SFSObject();
                            sfso = (SFSObject) event.getArguments().get("params");
                            if(sfso.getBool("isCVCreated"))
                            {
                                showToast("Load CV");
                                //TODO : Update details in CandidateCV
                                CandidateCV candidateCV = new CandidateCV();
                                candidateCV.setDesignation(sfso.getUtfString("designation"));
                                candidateCV.setZipcode(sfso.getUtfString("zipcode"));
                                candidateCV.setState(sfso.getUtfString("state"));
                                candidateCV.setCity(sfso.getUtfString("city"));
                                candidateCV.setCurrent_working_skill(sfso.getUtfString("current_working_skill"));
                                candidateCV.setKnown_skills(sfso.getUtfString("known_skills"));
                                candidateCV.setTotalExperience(sfso.getUtfString("total_experience"));
                                candidateCV.setJobType(sfso.getUtfString("job_type"));
                                candidateCV.setProjects_list(sfso.getSFSArray("projects"));
                                userClass.setCandidateCV(candidateCV);
                                loadCVView();
                            }
                            else
                            {
                                showToast("Not Created CV");
                                createCandidateCV();
                            }
                        }
                        else if (event.getArguments().get("cmd").equals(Commands.CREATE_CANDIDATE_PROJECT)) {
                            Log.e("CREATE__PROJECT",""+"----------------------------------------------------------------------");
                            SFSObject sfso = new SFSObject();
                            sfso = (SFSObject) event.getArguments().get("params");
                            if(sfso.getBool("isInserted"))
                            {
                                showToast("Inserted Project");
                                sfso = new SFSObject();
                                sfso.putUtfString("email", MainActivity.userClass.getEmail());
                                sfsClient.send(new ExtensionRequest(Commands.GET_CANDIDATE_CV, sfso));
                            }
                            else
                            {
                                showToast("Not Created Project");
                            }
                            //candidateJobListActivity.loadCandidateJobList(sfso.getSFSArray("candidate_job_list_array"));
                        }
                        else if (event.getArguments().get("cmd").equals(Commands.UPDATE_CANDIDATE_JOB)) {
                            Log.e("UPDATE_CANDIDATE_JOB","params"+"----------------------------------------------------------------------");
                            SFSObject sfso = new SFSObject();
                            sfso = (SFSObject) event.getArguments().get("params");
                            if(sfso.getInt("isAccepted") == 1)
                            {
                                showToast("Accepted Job Successfully.");
                                loadCandidateJobList();
                            }
                            else if(sfso.getInt("isRejected") == 1)
                            {
                                showToast("Rejected Job Successfully.");
                                loadCandidateJobList();
                            }
                        }
                        else if (event.getArguments().get("cmd").equals(Commands.UPDATE_JOB_STATUS)) {
                            Log.e("UPDATE_JOB_STATUS","params"+"----------------------------------------------------------------------");
                            showToast("Job Closed Successfully.");
                            loadJobListView();
                        }
                        else if (event.getArguments().get("cmd").equals(Commands.GET_CANDIDATES_LIST_APPLIED)) {
                            Log.e("CMD : ","GET_CANDIDATES_LIST_APPLIED"+"----------------------------------------------------------------------");
                            SFSObject sfso = new SFSObject();
                            sfso = (SFSObject) event.getArguments().get("params");
                            MainActivity.userClass.setCandidatesAcceptJobList(sfso.getSFSArray("list_of_candidates_applied"));
                            Log.e("CMD : ","sfso"+ sfso.getSFSArray("list_of_candidates_applied").size());
                            Log.e("CMD : ","getCandidatesAcceptJobList"+ MainActivity.userClass.getCandidatesAcceptJobList().size());
                            //loadCandidateAcceptJobListView();
                            candidateAcceptJobsList = new CandidatesAcceptedJobList();
                            candidateAcceptJobsList.loadCandidateAcceptView();
                        }
                    }
                }
            }
        });
    }


    private CandidatesAcceptedJobList candidateAcceptJobsList;
    /*private void loadCandidateAcceptJobListView() {
        Intent intent = new Intent(this, CandidatesAcceptedJobList.class);
        startActivity(intent);
    }*/

    private void loadCVView() {
        Intent intent = new Intent(this, ViewCandidateCV.class);
        startActivity(intent);
    }

    private void createCandidateCV() {
        Intent intent = new Intent(this, CreateCandidateCV.class);
        startActivity(intent);
    }

    public void getSubscribedTechnologies() {
        ISFSObject sfso = new SFSObject();
        sfso.putUtfString("name",userClass.getUsername());
        sfso.putUtfString("email", userClass.getEmail());
        sfsClient.send(new ExtensionRequest(Commands.GET_SUBSCRIBE_TECHNOLOGIES, sfso));
    }

    private CandidateJobListView candidateJobListActivity;
    private void loadCandidateJobList() {
        Intent intent = new Intent(this, CandidateJobListView.class);
        candidateJobListActivity = new CandidateJobListView();
        startActivity(intent);
    }

    private void loadCandidateSubscription(ISFSArray subscribed_technologies_arr) {
        ArrayList<String> subscribed_tech_arr = new ArrayList<>();
        for(int i=0; i<subscribed_technologies_arr.size(); i++ )
        {
            Log.e("Tech Name",""+subscribed_technologies_arr.getUtfString(i));
            subscribed_tech_arr.add(subscribed_technologies_arr.getUtfString(i));
        }
        userClass.setSubscribed_technologies_arr(subscribed_tech_arr);
        Intent intent = new Intent(this, CandidateSubscriptions.class);
        intent.putStringArrayListExtra("subscribed_technologies_arr",subscribed_tech_arr);
        startActivity(intent);
    }

    private void setUserDetails(SFSObject sfso) {
        SFSObject userDetailsObj = sfso;
        //userClass.setUser_id(userDetailsObj.getInt("user_id"));
        userClass.setUsername(userDetailsObj.getUtfString("name"));
        userClass.setEmail(userDetailsObj.getUtfString("email"));
        userClass.setContactNo(userDetailsObj.getUtfString("contact_number"));
        userClass.setQualification(userDetailsObj.getUtfString("qualification"));
        userClass.setUserType(userDetailsObj.getUtfString("user_type"));
    }

    private void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private void loadSignInView() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    private JobListActivity jobActivity;
    private Intent jobListActivityIntent;

    private void loadJobListView() {
        jobListActivityIntent = new Intent(this, JobListActivity.class);
        jobActivity = new JobListActivity();
        startActivity(jobListActivityIntent);
    }

    public static String encryptPwd(String pwd)
    {
        String passwordToHash = pwd;
        String generatedPassword = null;
        // Create MessageDigest instance for MD5
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //Add password bytes to digest
        md.update(passwordToHash.getBytes());
        //Get the hash's bytes
        byte[] bytes = md.digest();
        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        //Get complete hashed password in hex format
        generatedPassword = sb.toString();
        Log.e("Encrypted Pwd :","-------------------------------"+generatedPassword);
        return generatedPassword;
    }

    /*private void onExtensionResponse(SFSEvent event)
    {
        Log.v(TAG,"onExtensionResponse " + event.getType() + " (arguments="
                        + event.getArguments() + ")");

    }*/
}