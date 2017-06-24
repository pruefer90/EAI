package dhbw.eai.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(@NonNull final Context context, final Intent intent) {
        AlarmSetterService.setAlarm(context);
    }
}
