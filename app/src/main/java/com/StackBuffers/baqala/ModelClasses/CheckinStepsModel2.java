package com.StackBuffers.baqala.ModelClasses;

public class CheckinStepsModel2 {

    String  pName,cName, pAvailable_sehlfyes,nearExpiryyes,stockCountoverstock,shelfSpaceyes;

    public CheckinStepsModel2(String pName, String cName, String pAvailable_sehlfyes, String nearExpiryyes, String stockCountoverstock, String shelfSpaceyes) {
        this.pName = pName;
        this.cName = cName;
        this.pAvailable_sehlfyes = pAvailable_sehlfyes;
        this.nearExpiryyes = nearExpiryyes;
        this.stockCountoverstock = stockCountoverstock;
        this.shelfSpaceyes = shelfSpaceyes;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getpAvailable_sehlfyes() {
        return pAvailable_sehlfyes;
    }

    public void setpAvailable_sehlfyes(String pAvailable_sehlfyes) {
        this.pAvailable_sehlfyes = pAvailable_sehlfyes;
    }


    public String getNearExpiryyes() {
        return nearExpiryyes;
    }

    public void setNearExpiryyes(String nearExpiryyes) {
        this.nearExpiryyes = nearExpiryyes;
    }


    public String getStockCountoverstock() {
        return stockCountoverstock;
    }

    public void setStockCountoverstock(String stockCountoverstock) {
        this.stockCountoverstock = stockCountoverstock;
    }


    public String getShelfSpaceyes() {
        return shelfSpaceyes;
    }

    public void setShelfSpaceyes(String shelfSpaceyes) {
        this.shelfSpaceyes = shelfSpaceyes;
    }


}
