package com.example.saudm.auditory_menu_navigation.activities.utils;

import java.util.HashMap;

public class IntentHelper {

    private HashMap<String,Object> hashMap;
    private static IntentHelper instance;

    private IntentHelper(){
        hashMap = new HashMap<String, Object>();
    }

    public static IntentHelper getInstance(){
        if (instance == null){
            instance = new IntentHelper();
        }
        return instance;
    }

    public void addObject(String key, Object object){
        hashMap.remove(key);
        hashMap.put(key,object);
    }

    public Object getObject(String key){
        Object object;
        if (hashMap != null){
            if (hashMap.containsKey(key)){
                object = hashMap.get(key);
                return object;
            }
        }
        return null;
    }

    public void clearStoredIntents(){
        if(hashMap != null){
            hashMap.clear();
        }
    }

    public void removeKey(String key){
        if (hashMap != null){
            if (hashMap.containsKey(key)){
                hashMap.remove(key);
                hashMap = null;
            }
        }
    }

    public boolean hasKey(String key){
        return hashMap.containsKey(key);
    }
}
