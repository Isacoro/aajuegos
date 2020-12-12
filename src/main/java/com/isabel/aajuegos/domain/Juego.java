package com.isabel.aajuegos.domain;

import javafx.scene.control.ComboBox;

public class Juego {

    private int id;
    private String nombre;
    private String desarrollador;
    private String plataforma;


    public Juego (int id, String nombre, String desarrollador, String plataforma){
        this.id = id;
        this.nombre = nombre;
        this.desarrollador = desarrollador;
        this.plataforma = plataforma;
    }

    //Constructor sin id para poder usarlo en algunas sentencias
    public Juego (String nombre, String desarrollador, String plataforma){
        this.nombre = nombre;
        this.desarrollador = desarrollador;
        this.plataforma = plataforma;
    }

    //Constructor en blanco para usar en otros métodos como el de ListarJuegos
    public Juego() {

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

    public String getDesarrollador() {

        return desarrollador;
    }

    public void setDesarrollador(String desarrollador) {

        this.desarrollador = desarrollador;
    }

    public String getPlataforma() {

        return plataforma;
    }

    public void setPlataforma(String plataforma) {

        this.plataforma = plataforma;
    }

    @Override
    public String toString() {
        return id + "   " + nombre + "   " + desarrollador + "   " + plataforma;
    }

}
