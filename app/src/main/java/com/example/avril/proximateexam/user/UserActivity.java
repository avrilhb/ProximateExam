package com.example.avril.proximateexam.user;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.avril.proximateexam.MainActivity;
import com.example.avril.proximateexam.R;
import com.example.avril.proximateexam.asyncTask.AsyncTask;
import com.example.avril.proximateexam.constantes.URLService;
import com.example.avril.proximateexam.interfaces.ServiceCallback;
import com.example.avril.proximateexam.login.user.UserResponse;
import com.example.avril.proximateexam.models.UserEntity;
import com.example.avril.proximateexam.webClient.WSClient;
import com.google.gson.Gson;
import io.realm.Realm;

/**
 * Created by avrilhb on 12/12/17.
 */

public class UserActivity extends Activity {

    private TextView nombre;
    private TextView correo;
    private TextView numeroDocumento;
    private TextView ultimaSesion;
    private TextView tipoDocumento;
    private TextView estadoUsuario;
    private ImageView fotoUsuario;
    private TextView locationTxt;
    private Realm realm;
    private static final int CAMERA_REQUEST = 1888;
    protected Location lastLocation;
    private LocationManager manager;
    private TextView cerrarSesion;
    private ImageView cameraImg;
    static final int REQUEST_LOCATION = 1;
    private TextView latitud;
    private TextView longitud;
    private Double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);

        nombre = findViewById(R.id.name);
        correo = findViewById(R.id.mail);
        numeroDocumento = findViewById(R.id.numero_documento);
        ultimaSesion = findViewById(R.id.ultima_sesion);
        tipoDocumento = findViewById(R.id.tipo_documento);
        estadoUsuario = findViewById(R.id.estado_usuario);
        fotoUsuario = findViewById(R.id.perfil_img);
        locationTxt = findViewById(R.id.photo_location);
        cerrarSesion = findViewById(R.id.cerrar_sesion);
        cerrarSesion.setOnClickListener(clickCloseSesion);
        cameraImg = findViewById(R.id.camera_img);
        cameraImg.setOnClickListener(clickGetPhoto);
        latitud = findViewById(R.id.photo_latitude);
        longitud = findViewById(R.id.photo_longitude);

        realm = Realm.getDefaultInstance();

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Lama al Asynctask
        AsyncTask task = new AsyncTask(URLService.UserDataService, WSClient.POST, "", UserActivity.this);
        task.setmCallback(new ServiceCallback() {
            @Override
            public void onServiceCallback(Object o) {
                String result = (String) o;
                if (!result.equals("")) {
                    Gson gson = new Gson();
                    UserResponse userResponse = gson.fromJson(result, UserResponse.class);

                    //Guardar en la base de datos del usuario
                    realm.beginTransaction();
                    UserEntity user = realm.createObject(UserEntity.class);
                    user.setSuccess(userResponse.getSuccess());
                    user.setError(userResponse.getError());
                    user.setMessage(userResponse.getMessage());
                    user.setNombres(userResponse.getData().get(0).getNombres());
                    user.setApellidos(userResponse.getData().get(0).getApellidos());
                    user.setCorreo(userResponse.getData().get(0).getCorreo());
                    user.setNumeroDocumento(userResponse.getData().get(0).getNumero_documento());
                    user.setUltimaSesion(userResponse.getData().get(0).getUltima_sesion());
                    user.setEliminado(userResponse.getData().get(0).getEliminado());
                    user.setDocumentosId(userResponse.getData().get(0).getDocumentos_id());
                    user.setDocumentosAbrev(userResponse.getData().get(0).getDocumentos_abrev());
                    user.setDocumentosLabel(userResponse.getData().get(0).getDocumentos_label());
                    user.setEstadosUsuarios(userResponse.getData().get(0).getEstados_usuarios_label());
                    user.setIdSeccion(userResponse.getData().get(0).getSecciones().get(0).getId());
                    user.setSeccion(userResponse.getData().get(0).getSecciones().get(0).getSeccion());
                    user.setAbrev(userResponse.getData().get(0).getSecciones().get(0).getAbrev());
                    realm.commitTransaction();
                    realm.close();

                    //Setear Datos a mostrar
                    nombre.setText(userResponse.getData().get(0).getNombres() + " " +
                        userResponse.getData().get(0).getApellidos());
                    correo.setText(userResponse.getData().get(0).getCorreo());
                    numeroDocumento.setText("No. documento: " + userResponse.getData().get(0).getNumero_documento());
                    ultimaSesion.setText("Última sesión: " + userResponse.getData().get(0).getUltima_sesion());
                    tipoDocumento.setText("Documento: " + userResponse.getData().get(0).getDocumentos_label());
                    estadoUsuario.setText("Estado: " + userResponse.getData().get(0).getEstados_usuarios_label());
                }
            }
        });
        task.execute();
    }
    //Tomar foto
    private View.OnClickListener clickGetPhoto = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            if (ActivityCompat.checkSelfPermission(UserActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(UserActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(UserActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            }else{
                Location location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        }
    };
    //Cerrar Sesión
    private View.OnClickListener clickCloseSesion = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(UserActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

    //Acción para tomar foto de usuario
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            fotoUsuario.setImageBitmap(photo);

            if (manager != null) {
                locationTxt.setVisibility(View.VISIBLE);
                latitud.setVisibility(View.VISIBLE);
                longitud.setVisibility(View.VISIBLE);
                latitud.setText("Latitud: " + Double.toString(latitude));
                longitud.setText("Longitud: " + Double.toString(longitude));
            }
        }
    }

    @Override
    public void onBackPressed() {
        final AlertDialog alertDialog = new AlertDialog.Builder(UserActivity.this).create();
        alertDialog.setTitle("Proximate App");
        alertDialog.setMessage("¿Deseas salir de la aplicación? Cierra Sesión");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
