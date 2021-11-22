package com.example.assignment3;

import android.app.Application;

public class myApp extends Application {
    private StoreManager storeManager = new StoreManager();
    public StoreManager getStoreManager() {
        return storeManager;
    }
}
