package com.example.saudm.auditory_menu_navigation.activities.models;

import java.util.ArrayList;

public class NavigationWithExecutionTime {

    private String feedback_type;
    private String execution_time;
    private ArrayList<NavigationPath> task_navigation_track;

    public NavigationWithExecutionTime() {
    }

    public NavigationWithExecutionTime(String feedback_type, String executionTime, ArrayList<NavigationPath> navigationPathArrayList) {
        this.execution_time = executionTime;
        this.task_navigation_track = navigationPathArrayList;
        this.feedback_type = feedback_type;
    }

    public String getExecution_time() {
        return execution_time;
    }

    public void setExecution_time(String execution_time) {
        this.execution_time = execution_time;
    }

    public ArrayList<NavigationPath> getTask_navigation_track() {
        return task_navigation_track;
    }

    public void setTask_navigation_track(ArrayList<NavigationPath> task_navigation_track) {
        this.task_navigation_track = task_navigation_track;
    }

    public String getFeedback_type() {
        return feedback_type;
    }

    public void setFeedback_type(String feedback_type) {
        this.feedback_type = feedback_type;
    }
}
