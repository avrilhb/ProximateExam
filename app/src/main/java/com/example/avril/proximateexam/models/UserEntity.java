package com.example.avril.proximateexam.models;

import io.realm.RealmObject;

/**
 * Created by avrilhb on 13/12/17.
 */

public class UserEntity extends RealmObject{

    private String id;
    private Boolean success;
    private Boolean error;
    private String message;
    private String nombres;
    private String apellidos;
    private String correo;
    private String numeroDocumento;
    private String ultimaSesion;
    private int eliminado;
    private int documentosId;
    private String documentosAbrev;
    private String documentosLabel;
    private String estadosUsuarios;
    private int idSeccion;
    private String seccion;
    private String abrev;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getUltimaSesion() {
        return ultimaSesion;
    }

    public void setUltimaSesion(String ultimaSesion) {
        this.ultimaSesion = ultimaSesion;
    }

    public int getEliminado() {
        return eliminado;
    }

    public void setEliminado(int eliminado) {
        this.eliminado = eliminado;
    }

    public int getDocumentosId() {
        return documentosId;
    }

    public void setDocumentosId(int documentosId) {
        this.documentosId = documentosId;
    }

    public String getDocumentosAbrev() {
        return documentosAbrev;
    }

    public void setDocumentosAbrev(String documentosAbrev) {
        this.documentosAbrev = documentosAbrev;
    }

    public String getDocumentosLabel() {
        return documentosLabel;
    }

    public void setDocumentosLabel(String documentosLabel) {
        this.documentosLabel = documentosLabel;
    }

    public String getEstadosUsuarios() {
        return estadosUsuarios;
    }

    public void setEstadosUsuarios(String estadosUsuarios) {
        this.estadosUsuarios = estadosUsuarios;
    }

    public int getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getAbrev() {
        return abrev;
    }

    public void setAbrev(String abrev) {
        this.abrev = abrev;
    }
}
