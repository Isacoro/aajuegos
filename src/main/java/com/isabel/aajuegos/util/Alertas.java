package com.isabel.aajuegos.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Alertas {

    public static void avisarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setContentText(mensaje);
        alerta.show();
    }

    public static void avisarAviso(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setContentText(mensaje);
        alerta.show();
    }

    public static void avisarConfirmacion(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setContentText(mensaje);
        Optional<ButtonType> respuesta = alerta.showAndWait();

        if (respuesta.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE)
            return;
    }
}

