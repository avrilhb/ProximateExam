package com.example.avril.proximateexam.webClient;

import android.util.Log;

import com.example.avril.proximateexam.application.ProximateApplication;
import com.example.avril.proximateexam.models.TokenEntity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.SSLContext;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by avrilhb on 11/12/17.
 */

public class WSClient {

    public static final String TAG = WSClient.class.getName();
    private HttpURLConnection connection = null;
    private static String UTF8 = "UTF-8";
    public static final int GET = 1;
    public static final int POST = 2;
    private static final int READ_TIME_OUT = 60000;
    private static final int CONNECTION_TIME_OUT = 60000;
    private Realm realm;
    private String token;

    public Object WSClientConsumerBasic(String url, String params, String method)
            throws Exception {
        String URL_SERVER = ProximateApplication.URL_BASE;
        //disableSSLCertificateChecking();
        String dataBack = "";
        String urlConnect = "";
        int methodo = Integer.parseInt(method);
        InputStream mInputStream = null;
        SSLContext context = SSLContext.getInstance("TLS");

        if (!url.matches("(?i).*https.*")) {
            url = URL_SERVER + url;
        }

        Log.wtf(TAG, "ejecutando---->> " + url
                + params);

        try {
            switch (methodo) {
                case GET:
                    urlConnect = url + params;
                    connection = (HttpURLConnection) new URL(urlConnect)
                            .openConnection();
                    if (url.contains("auth")) {
                        connection.addRequestProperty("Authorization", "Bearer " + token);
                    }

                    connection.setReadTimeout(READ_TIME_OUT);
                    connection.setConnectTimeout(CONNECTION_TIME_OUT);
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.connect();
                    Log.d(TAG, "PULL CONNECTION");
                    if (connection.getResponseCode() == 200 || connection.getResponseCode() == 201|| connection.getResponseCode() == 202) {
                        mInputStream = connection.getInputStream();
                        dataBack = otherMethodToRead(mInputStream);

                    } else {
                        dataBack =otherMethodToRead(connection.getErrorStream());
                        break;
                    }
                    break;

                case POST:
                    urlConnect = url;
                    connection = (HttpURLConnection) new URL(urlConnect)
                            .openConnection();

                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setReadTimeout(READ_TIME_OUT);
                    connection.setConnectTimeout(CONNECTION_TIME_OUT);
                    connection.setRequestMethod("POST");
                    connection.addRequestProperty("Content-type", "application/json");

                    //recuperar token si existe para la consulta de los datos del usuario
                    realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    RealmResults<TokenEntity> tokenResult = realm.where(TokenEntity.class).findAll();
                    realm.commitTransaction();

                    if(!tokenResult.isEmpty()){
                        token = tokenResult.get(0).getToken();
                        connection.addRequestProperty("Authorization", token);
                    }

                    DataOutputStream writer = new DataOutputStream(
                            connection.getOutputStream());
                    byte[] utf8JsonString = params.getBytes("UTF8");
                    writer.write(utf8JsonString, 0, utf8JsonString.length);
                    writer.flush();
                    writer.close();
                    Log.wtf("Code Con", connection.getResponseCode() + "");
                    Log.wtf("Code Message", connection.getResponseMessage());
                    if (connection.getResponseCode() == 200 || connection.getResponseCode() == 201|| connection.getResponseCode() == 202) {
                    mInputStream = connection.getInputStream();
                    dataBack = otherMethodToRead(mInputStream);

                    } else {
                        dataBack =otherMethodToRead(connection.getErrorStream());
                        break;
                }
                break;

                default:
                    Log.d(TAG, "BAD PARAMETER SENDED FOR SEND METHOD");
                    break;
            }

            printAllResponse(dataBack);
            return dataBack;

        } finally {
            if (mInputStream != null) {
                mInputStream.close();
            }
        }

    }

    public String otherMethodToRead(InputStream stream) throws IOException {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            br = new BufferedReader(new InputStreamReader(stream));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    private void printAllResponse(String response){
        int maxLogSize = 2000;
        for(int i = 0; i <= response.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > response.length() ? response.length() : end;
            Log.w(TAG, response.substring(start, end));
        }

    }
}
