package com.example.avril.proximateexam;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.avril.proximateexam.asyncTask.AsyncTask;
import com.example.avril.proximateexam.constantes.URLService;
import com.example.avril.proximateexam.interfaces.ServiceCallback;
import com.example.avril.proximateexam.login.LoginRequest;
import com.example.avril.proximateexam.login.LoginResponse;
import com.example.avril.proximateexam.models.TokenEntity;
import com.example.avril.proximateexam.user.UserActivity;
import com.example.avril.proximateexam.webClient.WSClient;
import com.google.gson.Gson;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private EditText correo;
    private EditText contraseña;
    private Button btnLogin;
    private LoginRequest request;
    private ProgressBar progressBar;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correo      = findViewById(R.id.mail);
        contraseña  = findViewById(R.id.password);
        btnLogin    = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(clickLogin);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(10);

        realm = Realm.getDefaultInstance();

        //Borrar token de la base de datos
        realm.beginTransaction();
        RealmResults<TokenEntity> tokenResult = realm.where(TokenEntity.class).findAll();
        tokenResult.deleteAllFromRealm();
        realm.commitTransaction();
    }

    //Acción Botón Login
    private View.OnClickListener clickLogin = new View.OnClickListener(){

        @Override
        public void onClick(View view) {

            String mail = correo.getText().toString();
            String pass = contraseña.getText().toString();

            if (!mail.equals("") && !pass.equals("")) {

                //Request Servicio Login
                request = new LoginRequest();
                request.setCorreo(mail);
                request.setContrasenia(pass);

                //Iniciar Progress Bar
                progressBar.setVisibility(View.VISIBLE);

                //Lama al Asynctask
                AsyncTask task = new AsyncTask(URLService.LoginService, WSClient.POST, new Gson().toJson(request), MainActivity.this);
                task.setmCallback(new ServiceCallback() {
                    @Override
                    public void onServiceCallback(Object o) {
                        String result = (String) o;
                        if (!result.equals("")) {
                            Gson gson = new Gson();
                            LoginResponse loginResponse = gson.fromJson(result, LoginResponse.class);

                            //Guardar en la base de datos el token
                            realm.beginTransaction();
                            TokenEntity token = realm.createObject(TokenEntity.class);
                            token.setToken(loginResponse.getToken());
                            realm.commitTransaction();
                            realm.close();

                            if (loginResponse != null) {
                                progressBar.setVisibility(View.GONE);
                                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                                alertDialog.setTitle("Login");
                                alertDialog.setMessage("¡Autenticación exitosa!");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "continuar",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                                alertDialog.show();

                            }
                        }else{
                            progressBar.setVisibility(View.GONE);
                            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                            alertDialog.setTitle("Login");
                            alertDialog.setMessage("Algo salió mal, intenta más tarde");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                    }
                });
                task.execute();
            }
        }
    };
}
