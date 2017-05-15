package explorador;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
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
    public static Document arbol;
    public static Element currentDir, elementoaPegar;
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
        /*Archivo a = new Archivo(1, "Archivo1", "Archivo1", "Lorem");
        a.agregar();
        Archivo a2 = new Archivo(2, "Archivo2", "Archivo2", "Lorem");
        a2.agregar();
        a.eliminar();
        Archivo a3 = new Archivo(3, "Archivo3", "Archivo3", "Lorem");
        a3.agregar(); */  
        
        /*for(int i = 10; i < 30; i++){
            Archivo a = new Archivo(i, "Archivo"+i, "Archivo", "Lorem");
            a.agregar();
            Directorio d = new Directorio(i, "Directorio"+i, "Archivo");
            d.agregar();
        }*/
        
        /*Archivo a3 = new Archivo(3, "Archivo3", "Archivo3", "Lorem");
        a3.eliminar();
        
        Archivo a6 = new Archivo(6, "Archivo6", "Archivo6", "Lorem");
        a6.agregar();*/
        
        /*Directorio d = new Directorio(25, "Directorio1", "Directorio");
        d.eliminar();
        Directorio d1 = new Directorio(25, "Directorio4", "Directorio");
        d1.agregar();*/
        
        Interfaz i = new Interfaz();
    }  
}