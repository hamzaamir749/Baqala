package com.StackBuffers.baqala.ModelClasses;

public class StepThreeModel {
    String name, shelf,barcode,id;

    public StepThreeModel(String name, String shelf, String barcode, String id) {
        this.name = name;
        this.shelf = shelf;
        this.barcode = barcode;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getShelf() {
        return shelf;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getId() {
        return id;
    }
}
