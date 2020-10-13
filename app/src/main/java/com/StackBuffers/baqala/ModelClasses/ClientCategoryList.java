package com.StackBuffers.baqala.ModelClasses;

import java.util.ArrayList;
import java.util.List;

public class ClientCategoryList {
    public static List<String>  list=new ArrayList<>();

    public static List<String> getList() {
        return list;
    }

    public void setList(String name,String cati) {
       if (cati.equals("cat"))
       {
           list.add(1,name);
       }
       else if (cati.equals("cli"))
       {
           list.add(0,name);
       }
    }
}
