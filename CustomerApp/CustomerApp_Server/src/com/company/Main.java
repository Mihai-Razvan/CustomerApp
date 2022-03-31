package com.company;

import java.awt.dnd.DropTarget;

public class Main {

    public static void main(String[] args) {

        HttpConnection httpConnection = new HttpConnection();
        httpConnection.createServer();
    }
}
