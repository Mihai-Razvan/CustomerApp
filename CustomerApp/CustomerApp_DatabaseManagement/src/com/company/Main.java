package com.company;

import javax.xml.crypto.Data;

public class Main {

    public static void main(String[] args) {

        BillTableManager billTableManager = new BillTableManager();
        Thread billTableManagerThread = new Thread(billTableManager);
        billTableManagerThread.start();

    }
}
