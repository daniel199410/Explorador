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
    private final JButton atras, abrir, crearArchivo, crearDirectorio, copiar, cortar, pegar;
    private final JScrollPane jsp;
    private static JPanel ultimoClickeado;
    
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
        contenido = new JPanel(new GridLayout(0, 10));
        jsp = new JScrollPane();
        jsp.setViewportView(contenido);
        
        atras = new JButton("Atrás");
        crearArchivo = new JButton("Crear archivo");      
        crearDirectorio = new JButton("Crear directorio");
        abrir = new JButton("Abrir");        
        copiar = new JButton("Copiar");        
        cortar = new JButton("Cortar");        
        pegar = new JButton("Pegar");
        abrir.setEnabled(false);
        copiar.setEnabled(false);
        cortar.setEnabled(false);
        pegar.setEnabled(Explorador.estadoPegar);       
        atras.addActionListener(this);
        crearArchivo.addActionListener(this);
        crearDirectorio.addActionListener(this);
        pegar.addActionListener(this);
        abrir.addActionListener(this);
        copiar.addActionListener(this);
        cortar.addActionListener(this); 
        menu.add(atras);
        menu.add(crearArchivo);
        menu.add(crearDirectorio);
        menu.add(abrir);
        menu.add(copiar);
        menu.add(cortar);
        menu.add(pegar);
        
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
            ultimoClickeado.setBackground(Color.LIGHT_GRAY);
        me.getComponent().setBackground(Color.yellow);
        ultimoClickeado = (JPanel) me.getComponent();
        JLabel tipoComponente = (JLabel) ultimoClickeado.getComponent(0);
        if(tipoComponente.getText().equals("directorio")) 
            abrir.setEnabled(true);
        else
            abrir.setEnabled(false);
        cortar.setEnabled(true);
        copiar.setEnabled(true);      
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
            String respuesta = JOptionPane.showInputDialog("Escribe el nombre del archivo");
            if(!"".equals(respuesta)){
                Archivo archivo = new Archivo(Explorador.getCurrentId(), respuesta, "Archivo", "Lorem");
                archivo.agregar();
                this.dispose();
                new Interfaz();
            }
        }
        if(ae.getSource() == crearDirectorio){
            String respuesta = JOptionPane.showInputDialog("Escribe el nombre del directorio");
            if(!"".equals(respuesta)){
                Directorio directorio = new Directorio(Explorador.getCurrentId(), respuesta, "Directorio");
                directorio.agregar();
                this.dispose();              
                new Interfaz();
            }
        }
        if(ae.getSource() == abrir){
            JLabel nombreDirectorio = (JLabel) ultimoClickeado.getComponent(1);
            Explorador.currentDir = Directorio.obtenerNodoHijo(nombreDirectorio.getText());
            this.dispose();               
            new Interfaz();
            Explorador.setCurrentLevel(Explorador.getCurrentLevel() + 1);
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
            if(tipoElemento.getText().equals("archivo")){
                Explorador.elementoaPegar = Archivo.obtenerNodoHijo(nombreElemento.getText());
            }
            else
                Explorador.elementoaPegar = Directorio.obtenerNodoHijo(nombreElemento.getText());
            pegar.setEnabled(true);
            Explorador.estadoPegar = true;
        }
        if(ae.getSource() == cortar){
            JLabel nombreElemento = (JLabel) ultimoClickeado.getComponent(1);
            JLabel tipoElemento = (JLabel) ultimoClickeado.getComponent(0);   
            if(tipoElemento.getText().equals("archivo")){
                Explorador.elementoaPegar = Archivo.obtenerNodoHijo(nombreElemento.getText());
            }
            else
                Explorador.elementoaPegar = Directorio.obtenerNodoHijo(nombreElemento.getText());
            int id_removido = Integer.parseInt(Explorador.elementoaPegar.getAttribute("id"));
            Elemento removido = new Elemento(id_removido, nombreElemento.getText(), tipoElemento.getText());
            removido.eliminar();
            pegar.setEnabled(true);
            Explorador.estadoPegar = true;
            
        }
        if(ae.getSource() == pegar){
            System.out.println(Explorador.elementoaPegar);
            if(Explorador.elementoaPegar.getNodeName().equals("archivo")){
                Archivo archivo = new Archivo(Explorador.getCurrentId(), Explorador.elementoaPegar.getAttribute("nombre"), "Archivo", "Lorem");
                archivo.agregar();
                this.dispose();
                new Interfaz();
            }else{
                Directorio directorio = new Directorio(Explorador.getCurrentId(), Explorador.elementoaPegar.getAttribute("nombre"), "directorio");
                directorio.clonar();
                this.dispose();
                new Interfaz();
            }
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
            panel.setBackground(Color.LIGHT_GRAY);
            return panel;
        }
    }
}