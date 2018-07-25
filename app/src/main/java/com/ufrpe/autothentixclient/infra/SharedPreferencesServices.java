package com.ufrpe.autothentixclient.infra;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import static com.ufrpe.autothentixclient.infra.SharedPreferencesConstante.DEFAULT_ID_USER_PREFERENCES;
import static com.ufrpe.autothentixclient.infra.SharedPreferencesConstante.DEFAULT_LOGIN_PREFERENCES;
import static com.ufrpe.autothentixclient.infra.SharedPreferencesConstante.DEFAULT_PASSWORD_PREFERENCES;
import static com.ufrpe.autothentixclient.infra.SharedPreferencesConstante.DEFAULT_TOKEN_PREFERENCES;
import static com.ufrpe.autothentixclient.infra.SharedPreferencesConstante.ID_USER_PREFERENCES;
import static com.ufrpe.autothentixclient.infra.SharedPreferencesConstante.LOGIN_PREFERENCES;
import static com.ufrpe.autothentixclient.infra.SharedPreferencesConstante.PASSWORD_PREFERENCES;
import static com.ufrpe.autothentixclient.infra.SharedPreferencesConstante.TITLE_PREFERENCES;
import static com.ufrpe.autothentixclient.infra.SharedPreferencesConstante.TOKEN_PREFERENCES;

public class SharedPreferencesServices {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public SharedPreferencesServices(Context context) {
        sharedPreferences = context.getSharedPreferences(TITLE_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void setLong(String preferenceTag, long value){
        editor.putLong(preferenceTag, value);
        editor.commit();
    }

    private void setString(String preferenceTag, String value){
        editor.putString(preferenceTag, value);
        editor.commit();
    }

    public void setIdUserPreferences(long idUser){
        setLong(ID_USER_PREFERENCES, idUser);
    }

    public void setLoginPreferences(String login){
        setString(LOGIN_PREFERENCES, login);
    }

    public void setPasswordPreferences(String password){
        setString(PASSWORD_PREFERENCES, password);
    }

    public void setTokenPreferences(String token){
        setString(TOKEN_PREFERENCES, token);
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

    public String getTokenPreferences(){
        return sharedPreferences.getString(TOKEN_PREFERENCES, DEFAULT_TOKEN_PREFERENCES);
    }


    public void clearPreferences(){
        editor.clear();
        editor.commit();
    }
}
