package com.example.avril.proximateexam.asyncTask;

import android.app.Activity;
import android.util.Log;
import com.example.avril.proximateexam.interfaces.ServiceCallback;
import com.example.avril.proximateexam.webClient.WSClient;

/**
 * Created by avrilhb on 11/12/17.
 */

public class AsyncTask extends android.os.AsyncTask<String, Integer, Object> {

    protected String cualWS;
    protected int tipo;
    protected String parametros = "";
    protected Activity app;
    protected ServiceCallback mCallback;

    public AsyncTask(String cualWS, int tipoSend, String parametros, Activity app) {
        this.cualWS = cualWS;
        this.parametros = parametros;
        this.tipo = tipoSend;
        this.app = app;
    }

    @Override
    protected Object doInBackground(String... strings) {
        WSClient ws = new WSClient();
        try {
            return (ws.WSClientConsumerBasic(cualWS, parametros, tipo + ""));
        } catch (Exception e) {
            e.printStackTrace();
            return "exc";
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        if (this.mCallback != null) {
            try {
                this.mCallback.onServiceCallback(o);
            } catch (Exception e ){
                Log.d(getClass().getSimpleName(), "Exception:" + e.getMessage());
            }
        }
    }

    public void setmCallback(ServiceCallback mCallback) {
        this.mCallback = mCallback;
    }
}
