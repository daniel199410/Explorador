package explorador;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Daniel
 */
public class Interfaz extends JFrame implements MouseListener, ActionListener{
    private final JPanel ventana, menu, contenido;
    private final JButton atras, abrir, crearArchivo, crearDirectorio, copiar, cortar, pegar, eliminar, editar, cerrarSesion;
    private final JScrollPane jsp;
    public static JPanel ultimoClickeado;
    
    public Interfaz(){
        super("Explorador de archivos");
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int xx = screenSize.width;
        int yy = screenSize.height;
        this.setSize(1360, 720);
        this.setLocation((xx - 1360) / 2, (yy - 720) / 2);       
        
        ventana = new JPanel();
        ventana.setLayout(new BoxLayout(ventana, BoxLayout.Y_AXIS));
        ventana.setSize(1360, 720);
        ventana.setBackground(Color.WHITE);       
        
        menu = new JPanel();
        menu.setMaximumSize(new Dimension(1360, 50));
        menu.setBackground(new Color(187, 211, 249));
        contenido = new JPanel(new GridLayout(0, 10));
        contenido.setBackground(Color.WHITE);
        jsp = new JScrollPane();
        jsp.setViewportView(contenido);
        
        atras = new JButton("Atrás");
        crearArchivo = new JButton("Crear archivo");      
        crearDirectorio = new JButton("Crear directorio");
        abrir = new JButton("Abrir");        
        copiar = new JButton("Copiar");        
        cortar = new JButton("Cortar");        
        pegar = new JButton("Pegar");
        eliminar = new JButton("Eliminar");
        editar = new JButton("Editar");
        cerrarSesion = new JButton("Cerrar sesión");
        abrir.setEnabled(false);
        copiar.setEnabled(false);
        cortar.setEnabled(false);
        pegar.setEnabled(Explorador.estadoPegar);
        eliminar.setEnabled(false);
        editar.setEnabled(false);
        atras.addActionListener(this);
        crearArchivo.addActionListener(this);
        crearDirectorio.addActionListener(this);
        pegar.addActionListener(this);
        abrir.addActionListener(this);
        copiar.addActionListener(this);
        cortar.addActionListener(this); 
        eliminar.addActionListener(this);
        editar.addActionListener(this);
        cerrarSesion.addActionListener(this);
        menu.add(atras);
        menu.add(crearArchivo);
        menu.add(crearDirectorio);
        menu.add(abrir);
        menu.add(copiar);
        menu.add(cortar);
        menu.add(pegar);
        menu.add(eliminar);
        menu.add(editar);
        menu.add(cerrarSesion);
        
        pintar();
               
        ventana.add(menu);
        ventana.add(jsp);
        
        this.add(ventana);       
        
        this.setResizable(false);
        this.setBackground(Color.LIGHT_GRAY);
        this.setVisible(true);       
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void pintar(){
        NodeList listaNodos = Explorador.currentDir.getChildNodes();
        for(int i = 0; i < listaNodos.getLength(); i++){
            Panel panel = new Panel(listaNodos.item(i).getNodeName(), listaNodos.item(i).getAttributes().getNamedItem("nombre").getTextContent());
            JPanel elemento = panel.crear();
            elemento.addMouseListener(this);
            contenido.add(elemento);
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if(ultimoClickeado != null)
            ultimoClickeado.setBackground(Color.WHITE);
        me.getComponent().setBackground(new Color(187, 211, 249));
        ultimoClickeado = (JPanel) me.getComponent();
        JLabel tipoComponente = (JLabel) ultimoClickeado.getComponent(0);
        if(tipoComponente.getText().equals("directorio")) 
            abrir.setEnabled(true);
        else
            abrir.setEnabled(false);
        cortar.setEnabled(true);
        copiar.setEnabled(true);  
        eliminar.setEnabled(true);
        editar.setEnabled(true);
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == crearArchivo){
            new InterfazCrearElemento("Archivo");  
            this.dispose();
        }
        if(ae.getSource() == crearDirectorio){       
            new InterfazCrearElemento("Directorio");  
            this.dispose();
        }
        if(ae.getSource() == abrir){
            JLabel nombreDirectorio = (JLabel) ultimoClickeado.getComponent(1);
            Element ultimo = Directorio.obtenerNodoHijo(nombreDirectorio.getText());
            if(ultimo.getAttribute("lectura").equals("Público") || ultimo.getAttribute("dueño").equals(Explorador.currentUser.getAttribute("nombre"))){
                Explorador.currentDir = Directorio.obtenerNodoHijo(nombreDirectorio.getText());
                this.dispose();               
                new Interfaz();
                Explorador.setCurrentLevel(Explorador.getCurrentLevel() + 1);
            }else{
                JOptionPane.showMessageDialog(null, "No tienes permisos de lectura de este fichero", "Error de acceso", JOptionPane.ERROR_MESSAGE);
            }        
        }
        if(ae.getSource() == atras){
            if(Explorador.getCurrentLevel() > 1){
                Explorador.currentDir = (Element) Explorador.currentDir.getParentNode();
                this.dispose();
                new Interfaz();
                Explorador.setCurrentLevel(Explorador.getCurrentLevel() - 1);
            }
        }
        if(ae.getSource() == copiar){
            JLabel nombreElemento = (JLabel) ultimoClickeado.getComponent(1);
            JLabel tipoElemento = (JLabel) ultimoClickeado.getComponent(0);           
            if(tipoElemento.getText().equals("archivo"))
                Explorador.elementoaPegar = Archivo.obtenerNodoHijo(nombreElemento.getText());
            else
                Explorador.elementoaPegar = Directorio.obtenerNodoHijo(nombreElemento.getText());
            if(Explorador.elementoaPegar.getAttribute("escritura").equals("Público") || Explorador.elementoaPegar.getAttribute("dueño").equals(Explorador.currentUser.getAttribute("nombre"))){
                pegar.setEnabled(true);
                Explorador.estadoPegar = true;
            }else{
                JOptionPane.showMessageDialog(null, "No tienes permisos para copiar este elemento", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(ae.getSource() == cortar){
            JLabel nombreElemento = (JLabel) ultimoClickeado.getComponent(1);
            JLabel tipoElemento = (JLabel) ultimoClickeado.getComponent(0);
            if(tipoElemento.getText().equals("archivo"))
                Explorador.elementoaPegar = Archivo.obtenerNodoHijo(nombreElemento.getText());
            else
                Explorador.elementoaPegar = Directorio.obtenerNodoHijo(nombreElemento.getText());
            if(Explorador.elementoaPegar.getAttribute("escritura").equals("Público") || Explorador.elementoaPegar.getAttribute("dueño").equals(Explorador.currentUser.getAttribute("nombre"))){
                int id_removido = Integer.parseInt(Explorador.elementoaPegar.getAttribute("id"));
                Elemento removido = new Elemento(id_removido, nombreElemento.getText(), tipoElemento.getText());
                removido.eliminar();
                pegar.setEnabled(true);
                Explorador.estadoPegar = true;   
            }else{
                JOptionPane.showMessageDialog(null, "No tienes permisos para cortar este elemento", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
            }      
        }
        if(ae.getSource() == pegar){
            System.out.println(Explorador.elementoaPegar);
            if(Explorador.elementoaPegar.getNodeName().equals("archivo")){
                Archivo archivo = new Archivo(Explorador.getCurrentId(), Explorador.elementoaPegar.getAttribute("nombre"), "Archivo", "Lorem", true, true, "adm");
                archivo.agregar();
                this.dispose();
                new Interfaz();
            }else{
                Directorio directorio = new Directorio(Explorador.getCurrentId(), Explorador.elementoaPegar.getAttribute("nombre"), "directorio", true, true, "Adm");
                directorio.clonar();
                this.dispose();
                new Interfaz();
            }
        }
        if(ae.getSource() == eliminar){
            JLabel nombreElemento = (JLabel) ultimoClickeado.getComponent(1);
            JLabel tipoElemento = (JLabel) ultimoClickeado.getComponent(0);
            if(tipoElemento.getText().equals("archivo")){
                Element removido = Archivo.obtenerNodoHijo(nombreElemento.getText());
                if(removido.getAttribute("escritura").equals("Público") || removido.getAttribute("dueño").equals(Explorador.currentUser.getAttribute("nombre"))){
                    Archivo a = new Archivo(Integer.parseInt(removido.getAttribute("id")), removido.getAttribute("nombre"), removido.getNodeName(), "Lorem", true, true, "adm");
                    a.eliminar();
                    pegar.setEnabled(false);
                    Explorador.estadoPegar = false;
                    ultimoClickeado = null;            
                    this.dispose();
                    new Interfaz();
                }else{
                    JOptionPane.showMessageDialog(null, "No tienes permisos para cortar este elemento", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                Element removido = Directorio.obtenerNodoHijo(nombreElemento.getText());
                if(removido.getAttribute("escritura").equals("Público") || removido.getAttribute("dueño").equals(Explorador.currentUser.getAttribute("nombre"))){  
                    Directorio d = new Directorio(Integer.parseInt(removido.getAttribute("id")), removido.getAttribute("nombre"), removido.getNodeName(), true, true, "admn");
                    d.eliminar();
                    pegar.setEnabled(false);
                    Explorador.estadoPegar = false;
                    ultimoClickeado = null;            
                    this.dispose();
                    new Interfaz();
                }else{
                    JOptionPane.showMessageDialog(null, "No tienes permisos para cortar este elemento", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if(ae.getSource() == editar){                         
            JLabel nombreDirectorio = (JLabel) ultimoClickeado.getComponent(1);
            JLabel tipoElemento = (JLabel) ultimoClickeado.getComponent(0);
            Element ultimo;
            if(tipoElemento.getText().equals("directorio")){
                ultimo = Directorio.obtenerNodoHijo(nombreDirectorio.getText());
            }else{
                ultimo = Archivo.obtenerNodoHijo(nombreDirectorio.getText());
            }
            if(ultimo.getAttribute("escritura").equals("Público") || ultimo.getAttribute("dueño").equals(Explorador.currentUser.getAttribute("nombre"))){                    
                this.dispose();
                new InterfazEditarElemento(tipoElemento.getText());
            }else{
            JOptionPane.showMessageDialog(null, "No tienes permisos para cortar este elemento", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(ae.getSource() == cerrarSesion){
            Usuario.cerrarSesion();
            this.dispose();
            new InterfazInicio();
        }
    }
    
    class Panel{
        private final String tipo, nombre;
        
        public Panel(String tipo, String nombre){
            this.tipo = tipo;
            this.nombre = nombre;
        }
        
        public JPanel crear(){
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            JLabel nombre = new JLabel(this.nombre);
            JLabel tipo = new JLabel(this.tipo);
            panel.add(tipo);
            panel.add(nombre);
            panel.setBackground(Color.WHITE);
            return panel;
        }
    }
}