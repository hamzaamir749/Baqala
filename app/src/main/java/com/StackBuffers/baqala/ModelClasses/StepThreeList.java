package com.StackBuffers.baqala.ModelClasses;

import java.util.ArrayList;
import java.util.List;

public class StepThreeList {
    public static List<CheckinStepsModel2> list = new ArrayList<>();

    public static List<CheckinStepsModel2> getList() {
        return list;
    }

    public void setList(CheckinStepsModel2 model2) {
        list.add(model2);
    }
}
