package com.isabel.aajuegos;

import com.isabel.aajuegos.domain.Usuario;
import com.isabel.aajuegos.util.R;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class UsuarioDAO {

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

    //Método para buscar usuario
    public boolean verificarUsuario(Usuario usuario) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE nombre = ? AND contrasena = ?";

        boolean valido = false;

        PreparedStatement sentencia = conexion.prepareStatement(sql);


        sentencia.setString(1, usuario.getNombre());
        sentencia.setString(2, usuario.getContrasena());

        ResultSet resultado = sentencia.executeQuery();

        if(resultado.next())
            valido = true;
        return valido;
    }
}

