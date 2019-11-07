package com.example.saudm.auditory_menu_navigation.activities.models;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskTracking {

    private String feedback;

    private ArrayList<NavigationPath> pathNavigationMap;

    public TaskTracking() {
        pathNavigationMap = new ArrayList<>();
    }

    public TaskTracking(String feedback) {
        this.feedback = feedback;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public ArrayList<NavigationPath> getPathNavigationMap() {
        return pathNavigationMap;
    }

    public void setPathNavigationMap(ArrayList<NavigationPath> pathNavigationMap) {
        this.pathNavigationMap = pathNavigationMap;
    }

    public void clearNavigationMap(){
        if (pathNavigationMap != null){
            pathNavigationMap.clear();
        }
    }
}
