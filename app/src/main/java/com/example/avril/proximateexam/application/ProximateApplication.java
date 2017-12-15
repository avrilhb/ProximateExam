package com.example.avril.proximateexam.application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by avrilhb on 11/12/17.
 */

public class ProximateApplication extends Application{

    public static String URL_BASE ="https://serveless.proximateapps-services.com.mx";
    private static ProximateApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        //Inicializar Realm
        Realm.init(getApplicationContext());

        //Crear configuración de Realm
        RealmConfiguration realmConfiguration = new RealmConfiguration.
                Builder().
                deleteRealmIfMigrationNeeded().
                build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
