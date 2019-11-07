package com.example.saudm.auditory_menu_navigation.activities.utils;

import java.util.ArrayList;

public class DropDownOptionsHandler {

    public static ArrayList<String> getCameraMenuList(boolean isAdaptive) {
        ArrayList<String> cameraMenuArrayList = new ArrayList<>();
        cameraMenuArrayList.add("Camera");
        if (!isAdaptive) {
            cameraMenuArrayList.add("Capture");
            cameraMenuArrayList.add("Send");
            cameraMenuArrayList.add("Delete");
            cameraMenuArrayList.add("Help");
        } else {
            cameraMenuArrayList.add("Delete");
        }
        return cameraMenuArrayList;
    }

    public static ArrayList<String> getMessagingMenuList(boolean isAdaptive) {
        ArrayList<String> messagingMenuArrayList = new ArrayList<>();
        messagingMenuArrayList.add("Messaging");
        if (!isAdaptive) {
            messagingMenuArrayList.add("Inbox");
            messagingMenuArrayList.add("Drafts");
            messagingMenuArrayList.add("Sent");
            messagingMenuArrayList.add("Outbox");
        } else {
            messagingMenuArrayList.add("Inbox");
        }
        return messagingMenuArrayList;
    }

    public static ArrayList<String> getGalleryMenuList(boolean isAdaptive) {
        ArrayList<String> galleryMenuArrayList = new ArrayList<>();
        galleryMenuArrayList.add("Gallery");
        if (!isAdaptive) {
            galleryMenuArrayList.add("Images");
            galleryMenuArrayList.add("Videos");
            galleryMenuArrayList.add("Tracks");
            galleryMenuArrayList.add("Presentations");
        } else {
            galleryMenuArrayList.add("Videos");
        }
        return galleryMenuArrayList;
    }

    public static ArrayList<String> getToolsMenuList(boolean isAdaptive) {
        ArrayList<String> toolsMenuArrayList = new ArrayList<>();
        toolsMenuArrayList.add("Tools");
        if (!isAdaptive) {
            toolsMenuArrayList.add("Profiles");
            toolsMenuArrayList.add("Themes");
            toolsMenuArrayList.add("Applications");
            toolsMenuArrayList.add("Settings");
        } else {
            toolsMenuArrayList.add("Settings");
        }
        return toolsMenuArrayList;
    }
}
