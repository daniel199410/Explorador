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
    
    public void eliminar(){
    NodeList nodeList = Explorador.currentDir.getChildNodes();
        for(int i = 0; i < nodeList.getLength(); i++){
            if(nodeList.item(i).getAttributes().getNamedItem("id").getTextContent().equals(String.valueOf(this.getId()))){
                Explorador.currentDir.removeChild(nodeList.item(i));
                Explorador.id_soltados.add(this.getId());
                System.out.println(this.getId());
            }     
        }
        
        XMLManager xml = new XMLManager();       
        xml.guardar();
    }
}
