package com.example.martinlamby.parking;

/**
 * Created by martinlamby on 25.08.15.
 */
public class Contact {

    private String name;
    private String mobilePhoneNumber;
    private String emailAddress;
    private String emailType;
    private boolean isSelected;


    public Contact(String name /*String mobilePhoneNumber*/, String emailAddress, String emailType) {
        this.name = name;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.emailAddress = emailAddress;
        this.emailType = emailType;
        isSelected = false;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setMobilePhoneNumber(String mobilePhoneNumber){
        this.mobilePhoneNumber = mobilePhoneNumber;
    }
    public void setIsSelected(boolean selectedValue){
        isSelected = selectedValue;
    }
    public String getName(){
        return name;
    }
    public String getMobilePhoneNumber(){
        return mobilePhoneNumber;
    }
    public String getEmailAddress(){
        return emailAddress;
    }

    public String getEmailType(){
        return emailType;
    }

    public boolean getIsSelected(){
        return isSelected;
    }


}
