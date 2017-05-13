package explorador;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Daniel
 */
public class Archivo extends Elemento{
    
    private String contenido;
    
    public Archivo(int id, String nombre, String tipo, String contenido) {
        super(id, nombre, tipo);
        this.contenido = contenido;
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
        
        Attr attContenido = Explorador.arbol.createAttribute("contenido");
        attContenido.setValue(this.getContenido());
        archivo.setAttributeNode(attContenido);             
               
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
