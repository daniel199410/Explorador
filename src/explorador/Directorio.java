package explorador;

/**
 *
 * @author Daniel
 */
public class Directorio extends Elemento{
    private static int currentId = 0;

    public static int getCurrentId() {
        return currentId;
    }

    public static void setCurrentId(int aId) {
        currentId = aId;
    }
    public Directorio(int id, String nombre, String tipo) {
        super(id, nombre, tipo);
    }  
}
