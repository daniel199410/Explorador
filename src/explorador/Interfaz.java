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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.w3c.dom.NodeList;

/**
 *
 * @author Daniel
 */
public class Interfaz extends JFrame implements MouseListener, ActionListener{
    private final JPanel ventana, menu, contenido;
    private final JButton atras, abrir, crear, copiar, cortar, pegar;
    private final JScrollPane jsp;
    private JPanel ultimoClickeado;
    
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
        crear = new JButton("Crear");      
        abrir = new JButton("Abrir");        
        copiar = new JButton("Copiar");        
        cortar = new JButton("Cortar");        
        pegar = new JButton("Pegar");
        atras.setEnabled(false);
        abrir.setEnabled(false);
        copiar.setEnabled(false);
        cortar.setEnabled(false);
        pegar.setEnabled(false);       
        atras.addActionListener(this);
        crear.addActionListener(this);
        pegar.addActionListener(this);
        abrir.addActionListener(this);
        copiar.addActionListener(this);
        cortar.addActionListener(this); 
        menu.add(atras);
        menu.add(crear);
        menu.add(abrir);
        menu.add(copiar);
        menu.add(cortar);
        menu.add(pegar);
               
        ventana.add(menu);
        ventana.add(jsp);
        
        this.add(ventana);
        
        pintar();
        
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
            //lista_paneles.add(elemento);
            elemento.addMouseListener(this);
            contenido.add(elemento);
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if(ultimoClickeado != null)
            ultimoClickeado.setBackground(Color.LIGHT_GRAY);
        me.getComponent().setBackground(Color.yellow);
        abrir.setEnabled(true);
        cortar.setEnabled(true);
        copiar.setEnabled(true);
        ultimoClickeado = (JPanel) me.getComponent();
        this.setTitle("Hola");
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