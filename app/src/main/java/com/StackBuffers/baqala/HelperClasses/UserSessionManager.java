package com.StackBuffers.baqala.HelperClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.StackBuffers.baqala.ModelClasses.BitmapList;
import com.StackBuffers.baqala.ModelClasses.ClientCategoryList;
import com.StackBuffers.baqala.ModelClasses.StepThreeList;

import java.util.ArrayList;
import java.util.List;

public class UserSessionManager {
    SharedPreferences userDB;
    public SharedPreferences.Editor editor;
    public Context context;
    int private_mode = 0;
    public static BitmapList bitmapList;
    public static StepThreeList stepThreeList;
    public static ClientCategoryList clientCategoryList;

    private static final String userDBName = "userData";

    public UserSessionManager(Context context) {
        this.context = context;
        userDB = context.getSharedPreferences(userDBName, private_mode);
    }

    public void setSessionDetails(Session sessionDetails) {


        SharedPreferences.Editor DataDetails = userDB.edit();
        DataDetails.putInt("id", sessionDetails.getId());
        DataDetails.putString("name", sessionDetails.getName());
        DataDetails.putString("image", sessionDetails.getImage());
        DataDetails.putString("address", sessionDetails.getAddress());

        DataDetails.apply();

    }

    public Session getSessionDetails() {

        int id = userDB.getInt("id", 0);

        String name = userDB.getString("name", "");
        String image = userDB.getString("image", "");
        String address = userDB.getString("address", "");
        Session sessionDetails = new Session(id, image, name, address);
        return sessionDetails;
    }

    public boolean isLoggedIn() {
        return userDB.getBoolean("loggedIn", false);
    }



    public boolean isservicerunning() {
        return userDB.getBoolean("isservicerunning", false);
    }


    public int getcounter() {
        return userDB.getInt("mycounter5", 0);
    }


    public String getdate() {
        return userDB.getString("getdate", "");
    }

    public void clearSessionData() {
        SharedPreferences.Editor clientSpEditor = userDB.edit();
        clientSpEditor.clear();
        clientSpEditor.apply();
    }




    public void addcounter() {
        int lastv=getcounter();
        lastv++;
        SharedPreferences.Editor edit = userDB.edit();
        edit.putInt("mycounter5", lastv);
        edit.apply();
    }

    public void clearcounter() {
        SharedPreferences.Editor edit = userDB.edit();
        edit.putInt("mycounter5", 0);
        edit.apply();
    }


    public void setLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor edit = userDB.edit();
        edit.putBoolean("loggedIn", loggedIn);
        edit.apply();
    }


    public void setservicerunning(boolean loggedIn) {
        SharedPreferences.Editor edit = userDB.edit();
        edit.putBoolean("isservicerunning", loggedIn);
        edit.apply();
    }


    public void setdate(String loggedIn) {
        SharedPreferences.Editor edit = userDB.edit();
        edit.putString("getdate", loggedIn);
        edit.apply();
    }

}
