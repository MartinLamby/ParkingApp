package com.example.martinlamby.parking;

/**
 * Created by martinlamby on 25.08.15.
 */
public class Contact {

    private String name;
    private String mobilePhoneNumber;
    private boolean isSelected;


    public Contact(String name, String mobilePhoneNumber) {
        this.name = name;
        this.mobilePhoneNumber = mobilePhoneNumber;
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

    public boolean getIsSelected(){
        return isSelected;
    }


}
