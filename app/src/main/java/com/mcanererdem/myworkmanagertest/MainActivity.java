package com.mcanererdem.myworkmanagertest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLEngineResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Data myData = new Data.Builder().putInt("myInteger", 1).build();
        Constraints myConstraints = new Constraints.Builder().setRequiresCharging(false).build();

        //One Time Request
        WorkRequest myWorkRequest = new OneTimeWorkRequest.Builder(WorkDatabase.class)
                .setConstraints(myConstraints)
                .setInputData(myData)
                .build();

        //Periodic Request
        WorkRequest mySecondRequest = new PeriodicWorkRequest.Builder(WorkDatabase.class,15,TimeUnit.MINUTES)
                .setConstraints(myConstraints)
                .setInputData(myData)
                .build();

        WorkManager.getInstance(this).enqueue(mySecondRequest);

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(mySecondRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if (workInfo.getState() == WorkInfo.State.RUNNING) {
                    System.out.println("running");
                } else if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                    System.out.println("succeeded");
                } else if (workInfo.getState() == WorkInfo.State.CANCELLED) {
                    System.out.println("cancelled");
                }else if (workInfo.getState() == WorkInfo.State.ENQUEUED) {
                    System.out.println("enqueued");
                }else {
                    System.out.println(" something happening");
                }
            }
        });
                /*
        OneTimeWorkRequest myOneTimeWorkRequest = new OneTimeWorkRequest.Builder(WorkDatabase.class)
                .setConstraints(myConstraints)
                .setInputData(myData)
                .build();

        WorkManager.getInstance(this).beginWith(myOneTimeWorkRequest).then(myOneTimeWorkRequest)
                .then(myOneTimeWorkRequest).enqueue();

 */
    }
}