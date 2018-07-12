package com.ufrpe.autothentixclient.infra;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import static com.ufrpe.autothentixclient.infra.SharedPreferencesConstante.DEFAULT_ID_USER_PREFERENCES;
import static com.ufrpe.autothentixclient.infra.SharedPreferencesConstante.DEFAULT_LOGIN_PREFERENCES;
import static com.ufrpe.autothentixclient.infra.SharedPreferencesConstante.DEFAULT_PASSWORD_PREFERENCES;
import static com.ufrpe.autothentixclient.infra.SharedPreferencesConstante.ID_USER_PREFERENCES;
import static com.ufrpe.autothentixclient.infra.SharedPreferencesConstante.LOGIN_PREFERENCES;
import static com.ufrpe.autothentixclient.infra.SharedPreferencesConstante.PASSWORD_PREFERENCES;
import static com.ufrpe.autothentixclient.infra.SharedPreferencesConstante.TITLE_PREFERENCES;

public class SharedPreferencesServices {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public SharedPreferencesServices(Context context) {
        sharedPreferences = context.getSharedPreferences(TITLE_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setIdUserPreferences(long idUser){
        editor.putLong(ID_USER_PREFERENCES, idUser);
        editor.commit();
    }

    public void setLoginPreferences(String login){
        editor.putString(LOGIN_PREFERENCES, login);
        editor.commit();
    }

    public void setPasswordPreferences(String password){
        editor.putString(PASSWORD_PREFERENCES, password);
        editor.commit();
    }

    public long getIdUserPreferences(){
        return sharedPreferences.getLong(ID_USER_PREFERENCES, DEFAULT_ID_USER_PREFERENCES);
    }

    public String getLoginPreferences(){
        return sharedPreferences.getString(LOGIN_PREFERENCES, DEFAULT_LOGIN_PREFERENCES);
    }

    public String getPasswordPreferences(){
        return sharedPreferences.getString(PASSWORD_PREFERENCES, DEFAULT_PASSWORD_PREFERENCES);
    }

    public void clearPreferences(){
        editor.clear();
        editor.commit();
    }
}
