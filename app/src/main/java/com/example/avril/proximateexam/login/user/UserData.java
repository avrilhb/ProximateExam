package com.example.avril.proximateexam.login.user;

import java.util.List;

/**
 * Created by avrilhb on 12/12/17.
 */

public class UserData {

    private String id;
    private String nombres;
    private String apellidos;
    private String correo;
    private String numero_documento;
    private String ultima_sesion;
    private int eliminado;
    private int documentos_id;
    private String documentos_abrev;
    private String documentos_label;
    private String estados_usuarios_label;
    private List<UserSections> secciones;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getNumero_documento() {
        return numero_documento;
    }

    public void setNumero_documento(String numero_documento) {
        this.numero_documento = numero_documento;
    }

    public String getUltima_sesion() {
        return ultima_sesion;
    }

    public void setUltima_sesion(String ultima_sesion) {
        this.ultima_sesion = ultima_sesion;
    }

    public int getEliminado() {
        return eliminado;
    }

    public void setEliminado(int eliminado) {
        this.eliminado = eliminado;
    }

    public int getDocumentos_id() {
        return documentos_id;
    }

    public void setDocumentos_id(int documentos_id) {
        this.documentos_id = documentos_id;
    }

    public String getDocumentos_abrev() {
        return documentos_abrev;
    }

    public void setDocumentos_abrev(String documentos_abrev) {
        this.documentos_abrev = documentos_abrev;
    }

    public String getDocumentos_label() {
        return documentos_label;
    }

    public void setDocumentos_label(String documentos_label) {
        this.documentos_label = documentos_label;
    }

    public String getEstados_usuarios_label() {
        return estados_usuarios_label;
    }

    public void setEstados_usuarios_label(String estados_usuarios_label) {
        this.estados_usuarios_label = estados_usuarios_label;
    }

    public List<UserSections> getSecciones() {
        return secciones;
    }

    public void setSecciones(List<UserSections> secciones) {
        this.secciones = secciones;
    }

}
