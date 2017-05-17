package explorador;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * @author Daniel
 */

public class Explorador implements Serializable{
    
    static LinkedList id_soltados = new LinkedList();
    private static int currentId = 1, currentLevel = 1;
    public static Document arbol, arbolUsuarios;
    public static Element currentDir, elementoaPegar, currentUser, usersContainer;
    public static boolean estadoPegar = false;
    
    public static void inicializar(){
        try{
            File xmlFile = new File("arbol.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            arbol = doc;
            arbol.getDocumentElement().normalize();
            currentDir = arbol.getDocumentElement();
            currentId = currentDir.getChildNodes().getLength();
        }catch(ParserConfigurationException | SAXException | IOException e){
            XMLManager xml = new XMLManager();
            xml.crear();
        }
        try{
            File xmlFile = new File("usuarios.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            arbolUsuarios = doc;
            arbolUsuarios.getDocumentElement().normalize();
            usersContainer = arbolUsuarios.getDocumentElement();
        }catch(ParserConfigurationException | SAXException | IOException e){
            XMLManager xml = new XMLManager();
            xml.crearTablaUsuario();
        }
    }

    public static LinkedList getId_soltados() {
        return id_soltados;
    }

    public static int getCurrentId() {
        return currentId;
    }

    public static void setCurrentId(int aCurrentId) {
        currentId = aCurrentId;
    } 

    public static int getCurrentLevel() {
        return currentLevel;
    }

    public static void setCurrentLevel(int aCurrentLevel) {
        currentLevel = aCurrentLevel;
    }
    
    public void agregarDirectorio(){
        
    }

    public static void main(String[] args) {
        inicializar();      
        
        /*String[] nombres = {"Daniel", "Daniela"};
        for(int i = 0; i < 60; i++){
            Archivo a = new Archivo(i, "Archivo"+i, "Archivo", "Lorem", Math.random() < 0.5, Math.random() < 0.5, nombres[Math.round((float) Math.random())]);
            a.agregar();
        }      
        for(int i = 60; i < 100; i++){
            Directorio a = new Directorio(i, "Directorio"+i, "Directorio", Math.random() < 0.5, Math.random() < 0.5, nombres[Math.round((float) Math.random())]);
            a.agregar();
        }*/
        
        /*Archivo a3 = new Archivo(3, "Archivo3", "Archivo3", "Lorem");
        a3.eliminar();
        
        Archivo a6 = new Archivo(6, "Archivo6", "Archivo6", "Lorem");
        a6.agregar();*/
        
        /*Directorio d = new Directorio(25, "Directorio1", "Directorio");
        d.eliminar();
        Directorio d1 = new Directorio(25, "Directorio4", "Directorio");
        d1.agregar();*/
        
        //Interfaz i = new Interfaz();
        //InterfazInicio i = new InterfazInicio();
        //InterfazRegistro i = new InterfazRegistro();
        //InterfazCrearElemento i = new InterfazCrearElemento();
        
        
        InterfazInicio i = new InterfazInicio();
    }  
}