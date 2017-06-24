package dhbw.eai.background;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import dhbw.eai.Const;

public class AlarmSetterIntent extends IntentService {

    public AlarmSetterIntent() {
        super("AlarmSetter");
    }

    @Override
    protected void onHandleIntent(@NonNull final Intent intent) {
        Log.d(Const.TAG, "onHandleIntent");
        AlarmSetterService.setAlarm(this);
    }
}
