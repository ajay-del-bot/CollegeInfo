package com.example.collegeinfo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.HashSet;


public class AttendanceFragment extends Fragment {

    private FirebaseRemoteConfig firebaseRemoteConfig;
    private TextView textViewAttendance;

    public AttendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_attendance, container, false);
        setView(view);
        setFirebaseRemoteConfig();
        fetchFirebaseRemoteConfig();
        return view;
    }

    private void setView(View view){
        textViewAttendance = view.findViewById(R.id.textViewAttendance);

    }

    public void setFirebaseRemoteConfig() {
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(1000)
                .build();
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings);
    }

    private void fetchFirebaseRemoteConfig(){
        HashMap<String, Object> defaults = new HashMap<>();
        defaults.put("ATTENDANCE", 0);
        defaults.put("IS_UPDATE", false);
        firebaseRemoteConfig.setDefaultsAsync(defaults);

        firebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(task -> {
                   if(task.isSuccessful()){
                        textViewAttendance.setText("Attendance: " + firebaseRemoteConfig
                        .getLong("ATTENDANCE"));
                   }
                   else{
                       Toast.makeText(getContext(), "Firebase error", Toast.LENGTH_LONG).show();
                   }
                });
    }
}