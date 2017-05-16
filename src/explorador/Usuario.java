package explorador;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Daniel
 */
public class Usuario extends Elemento{

    private String pass;
    private static int currentId = 1;
    
    public Usuario(int id, String nombre, String tipo, String pass) {
        super(id, nombre, tipo);
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }  
    
    public static int getCurrentId() {
        return currentId;
    }
    
    public static void setCurrentId(int aCurrentId) {
        currentId = aCurrentId;
    }
     
    @Override
    public void agregar(){
        Element usuario = Explorador.arbolUsuarios.createElement("usuario");
        Explorador.usersContainer.appendChild(usuario);

        Attr attr = Explorador.arbolUsuarios.createAttribute("id");
        attr.setValue(String.valueOf(Usuario.getCurrentId()));
        Usuario.setCurrentId(Usuario.getCurrentId() + 1);         

        usuario.setAttributeNode(attr);     

        Attr attNombre = Explorador.arbolUsuarios.createAttribute("nombre");
        attNombre.setValue(this.getNombre());
        usuario.setAttributeNode(attNombre);

        Attr attContenido = Explorador.arbolUsuarios.createAttribute("pass");
        attContenido.setValue(this.getPass());
        usuario.setAttributeNode(attContenido);
        
        Attr attCuenta = Explorador.arbolUsuarios.createAttribute("tipo_cuenta");
        attCuenta.setValue(this.getTipo());
        usuario.setAttributeNode(attCuenta);

        XMLManager xml = new XMLManager();
        xml.guardarUsuario();
    }
    
    public boolean existeUsuario(){
        NodeList usuarios = Explorador.usersContainer.getChildNodes();
        for(int i = 0; i < usuarios.getLength(); i++){
            if(usuarios.item(i).getAttributes().getNamedItem("nombre").getTextContent().equals(this.getNombre()) &&
                usuarios.item(i).getAttributes().getNamedItem("pass").getTextContent().equals(this.getPass()))
                return true;
        }
        return false;
    }
    
    public boolean existeNombre(){
        NodeList usuarios = Explorador.usersContainer.getChildNodes();
        for(int i = 0; i < usuarios.getLength(); i++){
            if(usuarios.item(i).getAttributes().getNamedItem("nombre").getTextContent().equals(this.getNombre()))
                return true;
        }
        return false;
    }
    
    public void iniciarSesion(){
        NodeList usuarios = Explorador.usersContainer.getChildNodes();
        for(int i = 0; i < usuarios.getLength(); i++){
            if(usuarios.item(i).getAttributes().getNamedItem("nombre").getTextContent().equals(this.getNombre())){
                Explorador.currentUser = (Element) usuarios.item(i);
            }
        }
    }
    
    public static void cerrarSesion(){
        Explorador.currentUser = null;
    }
}