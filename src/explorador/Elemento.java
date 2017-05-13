package explorador;

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
}
