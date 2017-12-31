package es.esy.android_inyourhand.mykamus;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
  Created by robby on 30/12/17.
 */

class AppPreference {

    private SharedPreferences sharedPreferences;

    AppPreference(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    void setFirstRun(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("AppFirstRun", false);
        editor.apply();
    }

    Boolean getFirstRun(){
        return sharedPreferences.getBoolean("AppFirstRun", true);
    }

}
