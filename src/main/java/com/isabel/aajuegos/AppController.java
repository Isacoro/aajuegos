package com.isabel.aajuegos;

import com.isabel.aajuegos.domain.Juego;
import com.isabel.aajuegos.util.Alertas;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;



public class AppController {

    public TextField tfNombre;
    public TextField tfDesarrollador;
    public ComboBox<String> cbPlataforma;
    public TableView<Juego> tvJuegos;
    public Label lbEstado;
    public Button btNuevo, btModificar, btEliminar, btGuardar, btCancelar, btBuscar;

    //Creo la variable del dao para usar
    private JuegoDAO juegoDAO;

    private Juego juegoSeleccionado;


    public AppController() {
        juegoDAO = new JuegoDAO();

        try {
            juegoDAO.conectarBD();
        } catch (SQLException | ClassNotFoundException | IOException sqle) {
            Alertas.avisarError("No se ha podido conectar con la base de datos");
        }

       // System.out.println(System.getProperty("user.home"));
    }

    //Método para fijar las columnas de la tabla
    public void columnasTabla() {
        Field[] fields = Juego.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("id"))
                continue;

            TableColumn<Juego, String> columna = new TableColumn<>(field.getName());
            columna.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
            tvJuegos.getColumns().add(columna);
        }

        tvJuegos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    //Método para cargar la lista al iniciar la aplicación
    public void cargarLista() {
        try {
            List<Juego> juegos = juegoDAO.listarJuegos();
            tvJuegos.setItems(FXCollections.observableList(juegos));

            //Rellenamos el ComboBox
            String[] plataformas = new String[]{"<Plataforma>", "Nintendo Switch", "PlayStation", "Xbox One", "Pc", "Todas"};
            cbPlataforma.setItems(FXCollections.observableArrayList(plataformas));

        } catch (SQLException sqle) {
            Alertas.avisarError("Error al cargar la lista");
        }
    }


    //Método botón nuevo
    @FXML
    public void nuevoJuego(Event event) {
        limpiarCajas();

        //Pongo el cursor en el TextField de nombre
        tfNombre.requestFocus();

        btNuevo.setDisable(true);
        btBuscar.setDisable(true);
        btEliminar.setDisable(true);
        btModificar.setDisable(true);
    }


    //Método botón modificar
    @FXML
    public void modificarJuego(Event event) {
       Juego juego = tvJuegos.getSelectionModel().getSelectedItem();
        if (juego == null) {
            Alertas.avisarAviso("Debes seleccionar un juego de la lista");
        }

        String nombre = tfNombre.getText();
        String desarrollador = tfDesarrollador.getText();
        String plataforma = cbPlataforma.getSelectionModel().getSelectedItem();

        Juego juego1 = new Juego(nombre, desarrollador, plataforma);

        try {
            juegoDAO.modificarJuego(juegoSeleccionado, juego1);

        } catch (SQLException sqle) {
            Alertas.avisarError("El juego no se ha modificado");
        }

        cargarLista();
        limpiarCajas();

    }


    //Método botón eliminar
    @FXML
    public void eliminarJuego(Event event) {
        //El juego que tengo seleccionado
        Juego juego = tvJuegos.getSelectionModel().getSelectedItem();

        //Si no hay seleccionado ningún juego
        if (juego == null) {
            Alertas.avisarAviso("Debes seleccionar un juego de la lista");
        }

        try {
            Alertas.avisarConfirmacion("¿Seguro que quieres eliminar el juego?");

            //Lo borro
            juegoDAO.eliminarJuego(juego);

            //Mensaje
            lbEstado.setText("Juego eliminado con éxito");

            //Devuelvo la lista modificada
            cargarLista();
            limpiarCajas();

        } catch (SQLException sqle) {
            Alertas.avisarError("No se ha podido eliminar el juego");
        }
    }


    //Método botón guardar
    @FXML
    public void guardarJuego(Event event) {

        String nombre = tfNombre.getText();
        String desarrollador = tfDesarrollador.getText();
        String plataforma = cbPlataforma.getSelectionModel().getSelectedItem();
        Juego juego = new Juego(nombre, desarrollador, plataforma);

        try {
            juegoDAO.insertarJuego(juego);

            lbEstado.setText("Juego guardado con éxito");

            limpiarCajas();
            cargarLista();
            activarBotones();

        } catch (SQLException sqle) {
            Alertas.avisarError("No se ha podido guardar el juego");
        }

    }


    //Método para poder seleccionar juego de la lista
    @FXML
    public void seleccionarJuego(Event event) {
        juegoSeleccionado = tvJuegos.getSelectionModel().getSelectedItem();
        cargarJuegoSeleccionado(juegoSeleccionado); //Llamo al método cargarJuegoSeleccionado

        btGuardar.setDisable(true);
    }


    //Método botón cancelar
    @FXML
    public void cancelar(Event event) {

        Alertas.avisarConfirmacion("¿Estás seguro de que quieres cancelar?");

        limpiarCajas();
        activarBotones();
    }


    //Método para buscar un juego por nombre
    @FXML
    public void buscarDesarrollador(Event event) {

        String desarrollador = tfDesarrollador.getText();


        try {
            List<Juego> juegos = juegoDAO.buscarPorDesarrollador(desarrollador);

            for (Juego j : juegos) {
                j.getNombre();
                j.getDesarrollador();
                j.getPlataforma();

                tvJuegos.setItems(FXCollections.observableList(juegos));
            }
        } catch (SQLException sqle) {
            Alertas.avisarError("No se ha podido cargar la tabla");
        }
    }


    //Método para mostrar el juego seleccionado
    private void cargarJuegoSeleccionado(Juego juego) {
        tfNombre.setText(juego.getNombre());
        tfDesarrollador.setText(juego.getDesarrollador());
        cbPlataforma.setValue(juego.getPlataforma());
    }


    //Método para limpiar las cajas de texto
    private void limpiarCajas() {
        tfNombre.setText("");
        tfDesarrollador.setText("");
        cbPlataforma.setValue("<Plataforma>");
    }


    //Método para eliminar todos los datos
    @FXML
    public void eliminarDatos(Event event){
        try {
            Alertas.avisarConfirmacion("¿Seguro que quieres eliminar todos los juegos?");

            //Lo borro
            juegoDAO.eliminarTodo();

            //Mensaje
            lbEstado.setText("Juegos eliminados con éxito");

            //Devuelvo la lista modificada
            cargarLista();

        } catch (SQLException sqle) {
            Alertas.avisarError("No se han podido eliminar los juegos");
        }
    }


    //Método para salir del programa
    @FXML
    public void salir(Event event){
        System.exit(0);
    }


    //Método de exportar los datos a un fichero
    @FXML
    public void exportarDatos(Event event){
        try {
            //Elijo fichero donde se guardarán los datos
            FileChooser fileChooser = new FileChooser();
            File fichero = fileChooser.showSaveDialog(null);

            //Objeto que pintará el fichero
            FileWriter fileWriter = new FileWriter(fichero);

            //Pinta el fichero
            CSVPrinter printer = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader("Id - Nombre - Desarrollador - Plataforma"));

            //Hago salto de línea para separar la cabecera de los resultados
            printer.println();

            //Traigo la lista de juegos
            List<Juego> juegos = juegoDAO.listarJuegos();

            //Recorre los datos para grabar
            for (Juego juego : juegos)
                printer.printRecord(juego.getId() + " - " + juego.getNombre() + " - " + juego.getDesarrollador() + " - " + juego.getPlataforma());


            //Aviso de que ha terminado
            Alertas.avisarConfirmacion("Terminada exportación");

            //Termina cuando acabe de pintar
            printer.close();

        } catch (SQLException  | IOException sqle) {
            Alertas.avisarError("Error al exportar los datos");
        }
    }


    //Método para activar todos los buttons
    private void activarBotones(){
        btNuevo.setDisable(false);
        btModificar.setDisable(false);
        btEliminar.setDisable(false);
        btGuardar.setDisable(false);
        btCancelar.setDisable(false);
        btBuscar.setDisable(false);
    }
}
