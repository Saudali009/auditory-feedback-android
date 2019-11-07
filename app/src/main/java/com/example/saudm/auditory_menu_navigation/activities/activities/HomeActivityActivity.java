package com.example.saudm.auditory_menu_navigation.activities.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.saudm.auditory_menu_navigation.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.example.saudm.auditory_menu_navigation.activities.models.NavigationPath;
import com.example.saudm.auditory_menu_navigation.activities.models.NavigationWithExecutionTime;
import com.example.saudm.auditory_menu_navigation.activities.models.TaskTracking;
import com.example.saudm.auditory_menu_navigation.activities.models.UserInformation;
import com.example.saudm.auditory_menu_navigation.activities.utils.DropDownOptionsHandler;
import com.example.saudm.auditory_menu_navigation.activities.utils.IntentHelper;
import com.example.saudm.auditory_menu_navigation.activities.utils.MyCustomSpinnerView;
import com.example.saudm.auditory_menu_navigation.activities.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.saudm.auditory_menu_navigation.activities.constants.Constants.ADAPTIVE;
import static com.example.saudm.auditory_menu_navigation.activities.constants.Constants.IS_ADAPTIVE_FEEDBACK;
import static com.example.saudm.auditory_menu_navigation.activities.constants.Constants.SINGLE_CLICKED;
import static com.example.saudm.auditory_menu_navigation.activities.constants.Constants.SPEARCONS;
import static com.example.saudm.auditory_menu_navigation.activities.constants.Constants.TEXT_TO_SPEECH;
import static com.example.saudm.auditory_menu_navigation.activities.constants.Constants.TRACKING_INFORMATION;
import static com.example.saudm.auditory_menu_navigation.activities.constants.Constants.USER_INFO;
import static com.example.saudm.auditory_menu_navigation.activities.utils.DropDownOptionsHandler.getCameraMenuList;
import static com.example.saudm.auditory_menu_navigation.activities.utils.DropDownOptionsHandler.getGalleryMenuList;
import static com.example.saudm.auditory_menu_navigation.activities.utils.DropDownOptionsHandler.getMessagingMenuList;
import static com.example.saudm.auditory_menu_navigation.activities.utils.DropDownOptionsHandler.getToolsMenuList;

public class HomeActivityActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener, MediaPlayer.OnCompletionListener {

    private boolean isPlayingAudio = false;
    private MediaPlayer mediaPlayer;
    private long startTime;
    private String fromOption = "";
    private final String TAG = HomeActivityActivity.class.getSimpleName();
    private TaskTracking trackingInformation;
    private boolean singleClicked = false, isAdaptive = false;
    private boolean isToExist = false;
    private android.support.v7.widget.Toolbar mToolBar;
    private ArrayList<String> cameraMenuArrayList, messagingMenuArrayList, galleryMenuArrayList, toolsMenuArrayList;
    private MyCustomSpinnerView cameraSpinner, messagingSpinner, gallerySpinner, toolsSpinner;
    private boolean spinnerTouched = false;
    private FloatingActionButton fabButton;
    private FirebaseDatabase database;
    private ProgressDialog progressLoader;
    private NavigationWithExecutionTime navigationHistory;
    private String executionTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            singleClicked = bundle.getBoolean(SINGLE_CLICKED);
            isAdaptive = bundle.getBoolean(IS_ADAPTIVE_FEEDBACK);
        }

        mToolBar = findViewById(R.id.toolbar);
        if (mToolBar != null) {
            setSupportActionBar(mToolBar);
        }

        database = FirebaseDatabase.getInstance();
        trackingInformation = new TaskTracking();
        String feedbackType = "";
        if (singleClicked){
            trackingInformation.setFeedback(TEXT_TO_SPEECH);
        }
        else if(isAdaptive){
            trackingInformation.setFeedback(ADAPTIVE);
        }else {
            trackingInformation.setFeedback(SPEARCONS);
        }
        initUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setStartTime();
    }

    private void makeMediaPlayerInstance() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
    }

    private void playAudio(String fileName) {
        if (!isPlayingAudio) {
            makeMediaPlayerInstance();
            try {
                AssetFileDescriptor assetFileDescriptor = getAssets().openFd(fileName);
                mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
                assetFileDescriptor.close();
                mediaPlayer.prepare();
                mediaPlayer.start();
                isPlayingAudio = true;
            } catch (Exception e) {
                Log.e(TAG,"Exception in Media :" + e.getLocalizedMessage());
            }
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mediaPlayer.stop();
        mediaPlayer.reset();
        isPlayingAudio = false;
    }

    private void setStartTime() {
        startTime = System.currentTimeMillis();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initUI() {
        progressLoader = new ProgressDialog(this);
        cameraSpinner = findViewById(R.id.cameraSpinner);
        messagingSpinner = findViewById(R.id.messagingSpinner);
        gallerySpinner = findViewById(R.id.gallerySpinner);
        toolsSpinner = findViewById(R.id.toolsSpinner);
        fabButton = findViewById(R.id.fabButton);

        setUpSpinnersData();

        //Setting up touch click listener for dropdown
        cameraSpinner.setOnTouchListener(this);
        messagingSpinner.setOnTouchListener(this);
        gallerySpinner.setOnTouchListener(this);
        toolsSpinner.setOnTouchListener(this);
        fabButton.setOnClickListener(this);

        //Setting up items click listener for dropdown
        cameraSpinner.setOnItemSelectedListener(onCameraSpinnerItemSelectedListener);
        messagingSpinner.setOnItemSelectedListener(onMessagingSpinnerItemSelectedListener);
        gallerySpinner.setOnItemSelectedListener(onGallerySpinnerItemSelectedListener);
        toolsSpinner.setOnItemSelectedListener(onToolsSpinnerItemSelectedListener);
    }

    private void setUpSpinnersData() {

        //Camera Dropdown
        ArrayAdapter<String> cameraSpinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,getCameraMenuList(isAdaptive));
        cameraSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cameraSpinner.setAdapter(cameraSpinnerArrayAdapter);

        //Messaging Dropdown
        ArrayAdapter<String> messagingSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getMessagingMenuList(isAdaptive));
        messagingSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        messagingSpinner.setAdapter(messagingSpinnerAdapter);

        //Gallery Dropdown
        ArrayAdapter<String> gallerySpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getGalleryMenuList(isAdaptive));
        gallerySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gallerySpinner.setAdapter(gallerySpinnerAdapter);

        //Tools Dropdown
        ArrayAdapter<String> toolsSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getToolsMenuList(isAdaptive));
        toolsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toolsSpinner.setAdapter(toolsSpinnerAdapter);
    }

    private Spinner.OnItemSelectedListener onCameraSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            if (spinnerTouched) {
                String itemClicked = getCameraMenuList(isAdaptive).get(position);
                playSound(itemClicked);
            }
            spinnerTouched = false;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private Spinner.OnItemSelectedListener onMessagingSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            if (spinnerTouched) {
                String itemClicked = getMessagingMenuList(isAdaptive).get(position);
                playSound(itemClicked);
            }
            spinnerTouched = false;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private Spinner.OnItemSelectedListener onGallerySpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            if (spinnerTouched) {
                String itemClicked = getGalleryMenuList(isAdaptive).get(position);
                playSound(itemClicked);
            }
            spinnerTouched = false;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private Spinner.OnItemSelectedListener onToolsSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            if (spinnerTouched) {
                String itemClicked = getToolsMenuList(isAdaptive).get(position);
                playSound(itemClicked);
            }
            spinnerTouched = false;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    @Override
    public void onBackPressed() {

        if (!isToExist) {
            isToExist = true;
            Toast.makeText(this, getString(R.string.press_back_to_exit), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isToExist = false;
                }
            }, 1500);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view == cameraSpinner) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                //Utils.playSound(HomeActivityActivity.this, singleClicked ? "camera.mp3" : "s_camera.mp3");
                playAudio(singleClicked ? "camera.mp3" : "s_camera.mp3");
                fromOption = "Camera";
                spinnerTouched = true;
            }
        } else if (view == messagingSpinner) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                //Utils.playSound(HomeActivityActivity.this, singleClicked ? "messaging.mp3" : "s_messaging.mp3");
                playAudio(singleClicked ? "messaging.mp3" : "s_messaging.mp3");
                fromOption = "Messaging";
                spinnerTouched = true;
            }
        } else if (view == gallerySpinner) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                //Utils.playSound(HomeActivityActivity.this, singleClicked ? "gallery.mp3" : "s_gallery.mp3");
                playAudio(singleClicked ? "gallery.mp3" : "s_gallery.mp3");
                fromOption = "Gallery";
                spinnerTouched = true;
            }
        } else if (view == toolsSpinner) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                //Utils.playSound(HomeActivityActivity.this, singleClicked ? "tools.mp3" : "s_tools.mp3");
                playAudio(singleClicked ? "tools.mp3" : "s_tools.mp3");
                fromOption = "Tools";
                spinnerTouched = true;
            }
        }
        return false;
    }

    private void playSound(String itemClicked) {
        Log.e(TAG, "FROM =========>" + fromOption);
        Log.e(TAG, "TO =========>" + itemClicked);
        trackingInformation.getPathNavigationMap().add(new NavigationPath(fromOption, itemClicked));
        Utils.releaseMediaPlayer();
        String soundFileName = itemClicked.toLowerCase() + ".mp3";
        if (!isAdaptive){
            playAudio(singleClicked ? soundFileName : "s_" + soundFileName);
        }else {
            playAudio("s_" + soundFileName);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == fabButton) {
            IntentHelper.getInstance().addObject(TRACKING_INFORMATION, trackingInformation);
            if (Utils.isOnline(this)) {
                saveDataToDB();
            } else {
                Utils.displayMessage(this, "App is not connected to internet, please check network connectivity.");
            }
        }
    }

    private void saveDataToDB() {
        showProgressLoader();
        final UserInformation user = (UserInformation) IntentHelper.getInstance().getObject(USER_INFO);
        final TaskTracking taskTracking = (TaskTracking) IntentHelper.getInstance().getObject(TRACKING_INFORMATION);
        final DatabaseReference experimentsTableReference = database.getReference("users");

        final HashMap<String, String> userInfoMap = new HashMap<>();
        userInfoMap.put("username", user.getUsername());
        executionTime = getTotalExecutionTime();
        navigationHistory = new NavigationWithExecutionTime(trackingInformation.getFeedback(),executionTime, taskTracking.getPathNavigationMap());

        if (experimentsTableReference != null) {

            experimentsTableReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(user.getUserId())) {
                        experimentsTableReference.child(user.getUserId())
                                .child("experiments_history").push().setValue(navigationHistory)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        hideLoader();
                                        taskTracking.clearNavigationMap();
                                        navigationHistory = null;
                                        navigateToTouchInputScreen();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                hideLoader();
                                Log.e(TAG, "Exception has occur" + e.getLocalizedMessage());
                            }
                        });
                    } else {
                        DatabaseReference userReference = experimentsTableReference.child(user.getUserId());
                        userReference.setValue(userInfoMap);
                        userReference.child("experiments").push().setValue(navigationHistory)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        hideLoader();
                                        taskTracking.clearNavigationMap();
                                        navigationHistory = null;
                                        navigateToTouchInputScreen();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                hideLoader();
                                Log.e(TAG, "Exception has occur" + e.getLocalizedMessage());
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void navigateToTouchInputScreen(){
        Intent touchInputIntent = new Intent(this,TouchInputActivity.class);
        touchInputIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        touchInputIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(touchInputIntent);
        finish();
    }

    private String getTotalExecutionTime() {
        long diff = new Date(System.currentTimeMillis()).getTime() - new Date(startTime).getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        String timeToReturn = "";
        if (minutes < 10) {
            timeToReturn = "0" + minutes;
        } else {
            timeToReturn = "" + minutes;
        }

        return timeToReturn + ":" + seconds;
    }

    private void showProgressLoader(){
        progressLoader.setTitle("Please Wait");
        progressLoader.setMessage("Saving information to server.");
        progressLoader.setCancelable(false);
        progressLoader.setIndeterminate(true);
        progressLoader.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressLoader.show();
    }

    private void hideLoader(){
        if (progressLoader != null){
            progressLoader.dismiss();
        }
    }
}
