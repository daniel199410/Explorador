package explorador;

import javax.swing.JLabel;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Daniel
 */
public class Directorio extends Elemento{
    
    private boolean lectura, edicion; //True si es público, false si es privado
    private String dueño;

    public Directorio(int id, String nombre, String tipo, boolean lectura, boolean edicion, String dueño) {
        super(id, nombre, tipo);
        this.edicion = edicion;
        this.lectura = lectura;
        this.dueño = dueño;
    }
    
    @Override
    public void agregar(){
        Element directorio = Explorador.arbol.createElement("directorio");
        Explorador.currentDir.appendChild(directorio);
        Attr attr = Explorador.arbol.createAttribute("id");
        attr.setValue(String.valueOf(Explorador.getCurrentId()));
        Explorador.setCurrentId(Explorador.getCurrentId() + 1);      

        directorio.setAttributeNode(attr);

        Attr attNombre = Explorador.arbol.createAttribute("nombre");
        attNombre.setValue(this.getNombre());
        directorio.setAttributeNode(attNombre);
        
        Attr attLectura = Explorador.arbol.createAttribute("lectura");
        attLectura.setValue(lectura ? "Público" : "Privado");
        directorio.setAttributeNode(attLectura); 
        
        Attr attEscritura = Explorador.arbol.createAttribute("escritura");
        attEscritura.setValue(edicion ? "Público" : "Privado");
        directorio.setAttributeNode(attEscritura); 
        
        Attr attDueño = Explorador.arbol.createAttribute("dueño");
        attDueño.setValue(this.getDueño());
        directorio.setAttributeNode(attDueño); 

        XMLManager xml = new XMLManager();
        xml.guardar();        
    }
    
    public void editar(){                 
        JLabel nombreElemento = (JLabel) Interfaz.ultimoClickeado.getComponent(1);
        Element elemento = (Element) Directorio.obtenerNodoHijo(nombreElemento.getText()).cloneNode(true);
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
            if(hijos.item(i).getNodeName().equals("directorio") && hijos.item(i).getAttributes().getNamedItem("nombre").getTextContent().equals(nombre))               
                return (Element) hijos.item(i);
        }
        return null;
    }
    
    public void clonar(){
        Explorador.currentDir.appendChild(Explorador.elementoaPegar.cloneNode(true));
        XMLManager xml = new XMLManager();
        xml.guardar();  
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