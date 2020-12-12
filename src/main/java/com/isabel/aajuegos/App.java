package com.isabel.aajuegos;

import com.isabel.aajuegos.util.R;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

public class App extends Application {

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) throws Exception {
        UsuarioController usuarioController = new UsuarioController();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(R.getUI("usuario.fxml")); //Cargo pantalla

        //Clase que tiene el código que va a usar. En este caso, AppController
        loader.setController(usuarioController);

        //Carga en un vbox (un layout)
        VBox vbox = loader.load();

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args){
        launch();
    }
}
