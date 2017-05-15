package com.example.pandrajus.onlinecareercentre.model;

import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.SFSArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pandrajus on 10/31/2016.
 */
public class User {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    String username = "";

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    Integer user_id = 0;
    String email = "";
    String contactNo = "";
    String qualification = "";
    String userType = "";
    CandidateCV candidateCV = new CandidateCV();

    public CandidateCV getCandidateCV() {
        return candidateCV;
    }

    public void setCandidateCV(CandidateCV candidateCV) {
        this.candidateCV = candidateCV;
    }

    public void setJobList(ISFSArray jobList) {
        this.jobList = jobList;
    }

    public ISFSArray getJobList() {
        return this.jobList;
    }


    public ISFSArray getCandidatesAcceptJobList() {
        return candidatesAcceptJobList;
    }

    public void setCandidatesAcceptJobList(ISFSArray candidatesAcceptJobList) {
        this.candidatesAcceptJobList = candidatesAcceptJobList;
    }

    public ISFSArray candidatesAcceptJobList = new SFSArray();

    public ISFSArray jobList = new SFSArray();

    public ArrayList<String> getSubscribed_technologies_arr() {
        return subscribed_technologies_arr;
    }

    public void setSubscribed_technologies_arr(ArrayList<String> subscribed_technologies_arr) {
        this.subscribed_technologies_arr = subscribed_technologies_arr;
    }

    public ArrayList<String> subscribed_technologies_arr = new ArrayList<>();
}
