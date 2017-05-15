package explorador;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Daniel
 */
public class Directorio extends Elemento{

    public Directorio(int id, String nombre, String tipo) {
        super(id, nombre, tipo);
    }
    
    @Override
    public void agregar(){
        Element directorio = Explorador.arbol.createElement("directorio");
        Explorador.currentDir.appendChild(directorio);
       
        Attr attr = Explorador.arbol.createAttribute("id");
        if(Explorador.id_soltados.isEmpty()){
            attr.setValue(String.valueOf(Explorador.getCurrentId()));
            Explorador.setCurrentId(Explorador.getCurrentId() + 1);
        }else{
            attr.setValue(String.valueOf(Explorador.id_soltados.get(0)));
            Explorador.id_soltados.remove(0);
        }           
        
        directorio.setAttributeNode(attr);
        
        Attr attNombre = Explorador.arbol.createAttribute("nombre");
        attNombre.setValue(this.getNombre());
        directorio.setAttributeNode(attNombre);            
               
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
}