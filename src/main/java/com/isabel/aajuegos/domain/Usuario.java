package com.isabel.aajuegos.domain;

public class Usuario {

    private int id;
    private String nombre;
    private String contrasena;


    public Usuario (int id, String nombre, String contrasena){
        this.id = id;
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    //Constructor para usar en el método de entrada
    public Usuario(String nombre, String contrasena) {
    }

    public Usuario() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", contrasena='" + contrasena + '\'' +
                '}';
    }
}