package com.mcanererdem.myworkmanagertest;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class WorkDatabase extends Worker {
    private Context myContext;

    public WorkDatabase(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        this.myContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {

        Data recievedData = getInputData();
        int myInteger = recievedData.getInt("myInteger", 1);

        refreshDatabase(myInteger);

        return Result.success();
    }

    public void  refreshDatabase(int myInteger) {
        SharedPreferences mySharedPreferences = myContext.getSharedPreferences("com.mcanererdem.myworkmanagertest",Context.MODE_PRIVATE);
        int myNumber = mySharedPreferences.getInt("myNumber", 0);
        myNumber = myNumber + myInteger;
        System.out.println("My Number: " + myNumber);
        mySharedPreferences.edit().putInt("myNumber", myNumber).apply();
    }
}
