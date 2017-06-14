package dhbw.eai.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String DHBW_KARLSUHE_ID = "ChIJ15FBwAYHl0cRwn_nSiwjXWI";

    @Override
    public void onReceive(final Context context, final Intent intent) {
       AlarmSetterService.setAlarm(context);
    }
}
