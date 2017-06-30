package dhbw.eai.background;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import com.google.maps.model.TravelMode;

/**
 * Created by Manuel Wagenbach on 24.06.2017.
 */

public final class SaveSharedPreference {
    private static final String PREF_LINK = "link";
    private static final String PREF_WAY = "way";
    private static final String PREF_TIME = "time";
    private static final String PREF_ACTIVE = "active";

    private SaveSharedPreference() {
    }

    public static void setPrefTime(final Context context, final int time) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(PREF_TIME, time);
        editor.apply();
    }

    private static SharedPreferences getSharedPreferences(final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setPrefLink(final Context context, final String link) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_LINK, link);
        editor.apply();
    }

    public static void setPrefWay(final Context context, @NonNull final TravelMode way) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(PREF_WAY, way.ordinal());
        editor.apply();
    }

    public static void setPrefActive(final Context context, final boolean active) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(PREF_ACTIVE, active);
        editor.apply();
    }

    public static void setAll(final Context context, final int time, final String link, @NonNull final TravelMode way) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(PREF_TIME, time);
        editor.putString(PREF_LINK, link);
        editor.putInt(PREF_WAY, way.ordinal());
        editor.apply();
    }

    public static int getPrefTime(final Context context) {
        return getSharedPreferences(context).getInt(PREF_TIME, 0);
    }

    public static TravelMode getPrefWay(final Context context) {
        return TravelMode.values()[getSharedPreferences(context).getInt(PREF_WAY, 0)];
    }

    public static String getPrefLink(final Context context) {
        return getSharedPreferences(context).getString(PREF_LINK, "");
    }

    public static boolean getPrefActive(final Context context) {
        return getSharedPreferences(context).getBoolean(PREF_ACTIVE, false);
    }
}
