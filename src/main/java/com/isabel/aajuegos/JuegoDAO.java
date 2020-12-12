package com.isabel.aajuegos;

import com.isabel.aajuegos.domain.Juego;
import com.isabel.aajuegos.util.R;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JuegoDAO {

    private Connection conexion;

    //Método para conectar con la base de datos
    public void conectarBD() throws ClassNotFoundException, SQLException, IOException {
        Properties configuracion = new Properties();
        configuracion.load(R.getProperties("database.properties"));
        String host = configuracion.getProperty("host");
        String port = configuracion.getProperty("port");
        String nombreBD = configuracion.getProperty("nombreBD");
        String username = configuracion.getProperty("username");
        String password = configuracion.getProperty("password");

            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + nombreBD + "?serverTimezone=UTC", username, password);
    }

    //Método para desconectar de la base de datos
    public void desconectarBD() throws SQLException {
        conexion.close();
    }


    //*****************************MÉTODOS PARA USAR LA BASE DE DATOS*********************


    //Método para mostrar todos los juegos que hay en la base de datos
    public List<Juego> listarJuegos() throws SQLException {
        //Objeto juego para crear el arraylist
        List<Juego> juegos = new ArrayList<>();

        //Sentencia SQL
        String sql = "SELECT * FROM juegos";

            PreparedStatement sentencia = conexion.prepareStatement(sql);

            //Guarda el resultado
            ResultSet resultado = sentencia.executeQuery();

            //Muestra el resultado
            while(resultado.next()){
                Juego juego = new Juego();

                juego.setId(resultado.getInt(1));
                juego.setNombre(resultado.getString(2));
                juego.setDesarrollador(resultado.getString(3));
                juego.setPlataforma(resultado.getString(4));

                juegos.add(juego);
            }

        return juegos;
    }


    //Método para insertar juego nuevo
    public void insertarJuego(Juego juego) throws SQLException {
        String sql = "INSERT INTO juegos (nombre, desarrollador, plataforma) VALUES (?, ?, ?)";

        PreparedStatement sentencia = conexion.prepareStatement(sql);

        sentencia.setString(1, juego.getNombre());
        sentencia.setString(2, juego.getDesarrollador());
        sentencia.setString(3, juego.getPlataforma());

        sentencia.executeUpdate();
    }


    //Método para modificar juego
    public void modificarJuego(Juego juegoViejo, Juego juegoNuevo) throws SQLException{
        String sql = "UPDATE juegos SET nombre=?, desarrollador=?, plataforma=? WHERE id=?";

        PreparedStatement sentencia = conexion.prepareStatement(sql);

        sentencia.setString(1, juegoNuevo.getNombre());
        sentencia.setString(2, juegoNuevo.getDesarrollador());
        sentencia.setString(3, juegoNuevo.getPlataforma());
        sentencia.setInt(4, juegoViejo.getId());

        sentencia.executeUpdate();
    }


    //Método para eliminar juego
    public void eliminarJuego(Juego juego) throws SQLException{
        String sql = "DELETE FROM juegos WHERE nombre=?";

        PreparedStatement sentencia = conexion.prepareStatement(sql);

        sentencia.setString(1, juego.getNombre());

        sentencia.executeUpdate();
    }


    //Método para buscar juego por desarrollador
    public List<Juego> buscarPorDesarrollador(String desarrollador) throws SQLException {
        List<Juego> juegos = new ArrayList<>();

        String sql = "SELECT nombre, desarrollador, plataforma FROM juegos WHERE desarrollador = ?";

        PreparedStatement sentencia = conexion.prepareStatement(sql);

        sentencia.setString(1, desarrollador);

        ResultSet resultado = sentencia.executeQuery();

        while (resultado.next()){
            Juego juego = new Juego();

            juego.setNombre(resultado.getString(1));
            juego.setDesarrollador(resultado.getString(2));
            juego.setPlataforma(resultado.getString(3));

            juegos.add(juego);
        }

        return juegos;
    }


    //Método para eliminar todos los juegos
    public void eliminarTodo() throws SQLException{
        String sql = "DELETE * FROM juegos";

        PreparedStatement sentencia = conexion.prepareStatement(sql);

        Juego juego = new Juego();

        juego.setId(juego.getId());
        juego.setNombre(juego.getNombre());
        juego.setDesarrollador(juego.getDesarrollador());
        juego.setPlataforma(juego.getPlataforma());

        sentencia.executeUpdate();
    }
}
