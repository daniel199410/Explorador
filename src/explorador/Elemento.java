package explorador;

import org.w3c.dom.NodeList;

/**
 *
 * @author Daniel
 */
public class Elemento {
    private int id;
    private String nombre;
    private String tipo;
    
    public Elemento(int id, String nombre, String tipo){
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public void agregar(){
        
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
