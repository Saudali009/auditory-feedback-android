package com.example.saudm.auditory_menu_navigation.activities.models;

public class NavigationPath {

    private String fromOption;
    private String toOption;

    public NavigationPath() {
    }

    public NavigationPath(String fromOption, String toOption) {
        this.fromOption = fromOption;
        this.toOption = toOption;
    }

    public String getFromOption() {
        return fromOption;
    }

    public void setFromOption(String fromOption) {
        this.fromOption = fromOption;
    }

    public String getToOption() {
        return toOption;
    }

    public void setToOption(String toOption) {
        this.toOption = toOption;
    }
}
