package com.example.assignment2;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyService extends Service{
    private final IBinder binder = new LocalBinder();

    public static final String DATA_SAVED = "com.example.assignment2.DATA_SAVED";

    // 클라이언트에게 반환할 바인더 클래스 정의
    public class LocalBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

    // 현재 시간을 반환하는 메서드
    public String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date currentTime = new Date();
        return dateFormat.format(currentTime);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
