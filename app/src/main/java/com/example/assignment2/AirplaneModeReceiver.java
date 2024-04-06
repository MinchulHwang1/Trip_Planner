package com.example.assignment2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AirplaneModeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 비행기 모드 변경 이벤트를 처리하는 코드를 여기에 작성합니다.
        if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
            boolean isAirplaneModeEnabled = intent.getBooleanExtra("state", false);
            if (isAirplaneModeEnabled) {
                // 비행기 모드가 활성화되었을 때 수행할 작업
                Toast.makeText(context, "비행기 모드가 활성화되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                // 비행기 모드가 비활성화되었을 때 수행할 작업
                Toast.makeText(context, "비행기 모드가 비활성화되었습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
