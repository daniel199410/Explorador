package explorador;

import javax.swing.JLabel;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Daniel
 */
public class Archivo extends Elemento{
    
    private String contenido, dueño;
    private boolean lectura, edicion;
    
    public Archivo(int id, String nombre, String tipo, String contenido, boolean lectura, boolean edicion, String dueño) {
        super(id, nombre, tipo);
        this.contenido = contenido;
        this.lectura = lectura;
        this.edicion = edicion;
        this.dueño = dueño;
    }   

    public String getContenido() {
        return contenido;
    }
    
    @Override
    public void agregar(){
        Element archivo = Explorador.arbol.createElement("archivo");
        Explorador.currentDir.appendChild(archivo);

        Attr attr = Explorador.arbol.createAttribute("id");
        if(Explorador.id_soltados.isEmpty()){
            attr.setValue(String.valueOf(Explorador.getCurrentId()));
            Explorador.setCurrentId(Explorador.getCurrentId() + 1);
        }else{
            attr.setValue(String.valueOf(Explorador.id_soltados.get(0)));
            Explorador.id_soltados.remove(0);
        }           
        archivo.setAttributeNode(attr);     

        Attr attNombre = Explorador.arbol.createAttribute("nombre");
        attNombre.setValue(this.getNombre());
        archivo.setAttributeNode(attNombre);
        
        Attr attDueño = Explorador.arbol.createAttribute("dueño");
        attDueño.setValue(this.getDueño());
        archivo.setAttributeNode(attDueño);
        
        Attr attLectura = Explorador.arbol.createAttribute("lectura");
        attLectura.setValue(lectura ? "Público" : "Privado");
        archivo.setAttributeNode(attLectura); 
        
        Attr attEscritura = Explorador.arbol.createAttribute("escritura");
        attEscritura.setValue(edicion ? "Público" : "Privado");
        archivo.setAttributeNode(attEscritura); 

        Attr attContenido = Explorador.arbol.createAttribute("contenido");
        attContenido.setValue(this.getContenido());
        archivo.setAttributeNode(attContenido);             

        XMLManager xml = new XMLManager();
        xml.guardar();       
    }
    
    public void editar(){                 
        JLabel nombreElemento = (JLabel) Interfaz.ultimoClickeado.getComponent(1);
        Element elemento = (Element) obtenerNodoHijo(nombreElemento.getText()).cloneNode(true);
        Elemento viejo = new Elemento(Integer.parseInt(elemento.getAttribute("id")), elemento.getAttribute("nombre"), "Directorio");
        viejo.eliminar();
        
        elemento.setAttribute("id", String.valueOf(this.getId()));
        elemento.setAttribute("nombre", this.getNombre());
        elemento.setAttribute("dueño", this.getDueño());
        elemento.setAttribute("lectura", lectura ? "Público" : "Privado");
        elemento.setAttribute("escritura", edicion ? "Público" : "Privado");       
        
        Explorador.currentDir.appendChild(elemento);
        XMLManager xml = new XMLManager();
        xml.guardar();
    }
    
    public static Element obtenerNodoHijo(String nombre){
        NodeList hijos = Explorador.currentDir.getChildNodes();
        for(int i = 0; i < hijos.getLength(); i++){
            if(hijos.item(i).getNodeName().equals("archivo") && hijos.item(i).getAttributes().getNamedItem("nombre").getTextContent().equals(nombre))               
                return (Element) hijos.item(i);
        }
        return null;
    }

    public boolean isLectura() {
        return lectura;
    }

    public boolean isEdicion() {
        return edicion;
    }

    public String getDueño() {
        return dueño;
    }
}