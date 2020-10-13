package com.StackBuffers.baqala.ModelClasses;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class BitmapList {
    public static List<Bitmap> list = new ArrayList<>();
    public static int time;
    public static String name;
    public static String address;
    public static long mLastStopTime=0;
    public void addToList(Bitmap bitmap) {
        list.add(bitmap);
    }

    public static List<Bitmap> getList() {
        return list;
    }



    public static int getTime() {
        return time;
    }

    public static void setTime(int time) {
        BitmapList.time = time;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        BitmapList.name = name;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        BitmapList.address = address;
    }

    public static void setList(List<Bitmap> list) {
        BitmapList.list = list;
    }

    public static long getmLastStopTime() {
        return mLastStopTime;
    }

    public static void setmLastStopTime(long mLastStopTime) {
        BitmapList.mLastStopTime = mLastStopTime;
    }
}
