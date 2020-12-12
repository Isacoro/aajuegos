package com.isabel.aajuegos.util;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class R {

    //Recoger del directorio resources-ui donde están los fxml (ventanas hechas con scene builder).
    public static URL getUI(String name){
        return Thread.currentThread().getContextClassLoader().getResource("ui" + File.separator + name);
    }

    //Recoger del directorio resources-configuracion donde están las claves de la conexión a la base de datos.
    public static InputStream getProperties(String name){
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("configuracion" + File.separator + name);
    }

}
