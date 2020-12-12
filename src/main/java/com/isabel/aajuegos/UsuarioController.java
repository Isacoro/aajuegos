package com.isabel.aajuegos;

import com.isabel.aajuegos.domain.Usuario;
import com.isabel.aajuegos.util.Alertas;
import com.isabel.aajuegos.util.R;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class UsuarioController {

    public TextField tfUsuario;
    public PasswordField pfContrasena;
    public Button btAceptar;

    private UsuarioDAO usuarioDAO;


    public UsuarioController(){
        usuarioDAO = new UsuarioDAO();

        try{
            usuarioDAO.conectarBD();
        }catch (SQLException | ClassNotFoundException | IOException sqle){
            Alertas.avisarError("No se ha podido conectar con la base de datos");
        }
    }

    //Método para acceder a la aplicación
    @FXML
    public void aceptarUsuario(Event event) {

        Usuario usuario = new Usuario();
        String nombre = tfUsuario.getText();
        String contrasena = pfContrasena.getText();


        try {
            usuario.setNombre(nombre);
            usuario.setContrasena(contrasena);
            boolean valido = usuarioDAO.verificarUsuario(usuario);

            if(valido){
                cargaPantallaJuegos();
            }else{
                Alertas.avisarAviso("Acceso denegado, el usuario no existe");
            }

        } catch (SQLException sqle) {
            Alertas.avisarError("No se ha podido acceder");;
        }
    }


    //Método para cargar la pantalla de los juegos
    public void cargaPantallaJuegos(){
        AppController controller = new AppController();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(R.getUI("juegos.fxml")); //Cargo pantalla

        //Clase que tiene el código que va a usar. En este caso, AppController
        loader.setController(controller);

        //Carga en un vbox (un layout)
        VBox vbox = null;

        try {
            vbox = loader.load();
        } catch (IOException e) {
            Alertas.avisarError("Error al cargar la pantalla");
        }


        controller.columnasTabla();
        controller.cargarLista();

        Scene scene = new Scene(vbox);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        //Cerrar la pantalla de inicio
        stage = (Stage) btAceptar.getScene().getWindow();
        stage.close();
    }


    //Método para salir
    @FXML
    public void salir(Event event){
        System.exit(0);
    }
}
