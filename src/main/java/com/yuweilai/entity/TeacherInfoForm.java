package com.yuweilai.entity;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Created by 87734 on 2017/12/13.
 */
public class TeacherInfoForm {
    private String name;

    private List<String> grade;

    private List<String> course;

    private List<Time> time;

    private String mobileNum;

    private String gender;

    private String subject1;

    private String subject2;

    private String motto;

    private String intro;

    private String school;

    private String address;

    private String identity;

    private String avater;

    private TeacherInfo teacherInfo;

    public List<String> getGrade() {
        return grade;
    }

    public void setGrade(List<String> grade) {
        this.grade = grade;
    }

    public List<String> getCourse() {
        return course;
    }

    public void setCourse(List<String> course) {
        this.course = course;
    }

    public List<Time> getTime() {
        return time;
    }

    public void setTime(List<Time> time) {
        this.time = time;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSubject1() {
        return subject1;
    }

    public void setSubject1(String subject1) {
        this.subject1 = subject1;
    }

    public String getSubject2() {
        return subject2;
    }

    public void setSubject2(String subject2) {
        this.subject2 = subject2;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public TeacherInfo getTeacherInfo(){
        teacherInfo=new TeacherInfo();
        ObjectMapper objectMapper=new ObjectMapper();
        try{
            if(name!=null){
                teacherInfo.setUserName(name);
            }
            if(grade!=null){
                teacherInfo.setGrade(objectMapper.writeValueAsString(grade));
            }
            if(time!=null){
                teacherInfo.setTeachTime(objectMapper.writeValueAsString(time));
            }
            if(course!=null){
                teacherInfo.setCourse(objectMapper.writeValueAsString(course));
            }
            if(mobileNum!=null){
                teacherInfo.setMobileNum(mobileNum);
            }
            if(gender!=null){
                if(gender=="male"){
                    teacherInfo.setGender(0);
                }else if(gender=="female"){
                    teacherInfo.setGender(1);
                }
            }
            if(subject1!=null){
                teacherInfo.setSubject(subject1);
            }
            if(motto!=null){
                teacherInfo.setMotto(motto);
            }
            if(avater!=null){
                teacherInfo.setProfile(avater);
            }
            if(identity!=null){
                teacherInfo.setAuthPic(identity);
            }
            if(school!=null){
                teacherInfo.setSchool(school);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return teacherInfo;

    }
}
