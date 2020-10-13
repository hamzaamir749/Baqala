package com.StackBuffers.baqala.ModelClasses;

public class AddStoreModel {

String sName;
    String cName;
    String sPhone;

    public AddStoreModel(String sName, String cName, String sPhone, String sAddress) {
        this.sName = sName;
        this.cName = cName;
        this.sPhone = sPhone;
        this.sAddress = sAddress;
    }

    String sAddress;

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getsPhone() {
        return sPhone;
    }

    public void setsPhone(String sPhone) {
        this.sPhone = sPhone;
    }

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }
}
