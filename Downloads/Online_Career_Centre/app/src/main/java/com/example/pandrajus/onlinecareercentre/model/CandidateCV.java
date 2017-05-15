package com.example.pandrajus.onlinecareercentre.model;

import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.SFSArray;

/**
 * Created by Pandrajus on 11/27/2016.
 */
public class CandidateCV {

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getTotalExperience() {
        return totalExperience;
    }

    public void setTotalExperience(String totalExperience) {
        this.totalExperience = totalExperience;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCurrent_working_skill() {
        return current_working_skill;
    }

    public void setCurrent_working_skill(String current_working_skill) {
        this.current_working_skill = current_working_skill;
    }

    public String getKnown_skills() {
        return known_skills;
    }

    public void setKnown_skills(String known_skills) {
        this.known_skills = known_skills;
    }

    public ISFSArray getProjects_list() {
        return projects_list;
    }

    public void setProjects_list(ISFSArray projects_list) {
        this.projects_list = projects_list;
    }

    public String getCandidate_name() {
        return candidate_name;
    }

    public void setCandidate_name(String candidate_name) {
        this.candidate_name = candidate_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getEducation_qualification() {
        return education_qualification;
    }

    public void setEducation_qualification(String education_qualification) {
        this.education_qualification = education_qualification;
    }

    //Basic Information
    String candidate_name = "";
    String email = "";
    String contact_number = "";
    String education_qualification = "";

    //Profile Information
    String zipcode = "";
    String state = "";
    String city = "";
    String designation = "";
    String current_working_skill = "";
    String known_skills = "";
    String totalExperience = "";
    String jobType = "";
    ISFSArray projects_list = new SFSArray();
}
