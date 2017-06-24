package dhbw.eai.background;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Manuel Wagenbach on 24.06.2017.
 */

public class SaveSharedPreference {
    static final String pref_time = "time";
    static final String pref_link = "link";
    static final String pref_way = "way";
    static final String pref_status = "status";

    static SharedPreferences getSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setPrefTime(Context context, int time){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(pref_time, time);
        editor.commit();
    }

    public static void setPrefLink(Context context, String link){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(pref_link, link);
        editor.commit();
    }

    public static void setPrefWay(Context context, int way){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(pref_way, way);
        editor.commit();
    }

    public static void setPrefStatus(Context context, int status){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(pref_status, status);
        editor.commit();
    }

    public static void setAll(Context context, int time, String link, int way){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(pref_time, time);
        editor.putString(pref_link, link);
        editor.putInt(pref_way, way);
        editor.commit();
    }

    public static int getPrefTime(Context context){
        return getSharedPreferences(context).getInt(pref_time, 0);
    }

    public static int getPrefWay(Context context){
        return getSharedPreferences(context).getInt(pref_way, 0);
    }

    public static String getPrefLink(Context context){
        return getSharedPreferences(context).getString(pref_link, "");
    }

    public static int getPrefStatus(Context context){
        return getSharedPreferences(context).getInt(pref_status, 0);
    }

    public static void clear(Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(pref_time);
        editor.remove(pref_link);
        editor.remove(pref_way);
        editor.commit();
    }

    public static void clearAlarm(Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(pref_status);
        editor.commit();
    }
}
