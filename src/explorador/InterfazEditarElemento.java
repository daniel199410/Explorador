package explorador;

import static explorador.Interfaz.ultimoClickeado;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.w3c.dom.Element;

/**
 *
 * @author Daniel
 */
class InterfazEditarElemento extends JFrame implements ActionListener{

    private JLabel nombre, permisos, lectura, escritura;
    private JButton cambiar, cancelar;
    private JPanel ventana, JPpermisos, JPnombre, JPbotones, divisor, divisor2, divisor3;
    private JTextField Tnombre;
    private JComboBox JCBlectura, JCBescritura;
    private static final String[] opciones = {"Público", "Privado"};
    private String tipoElemento;
    
    public InterfazEditarElemento(String tipoElemento){
        super("Editar elemento");
        this.tipoElemento = tipoElemento;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int xx = screenSize.width;
        int yy = screenSize.height;
        int x = 450;
        int y = 200;
        this.setSize(x, y);
        this.setLocation((xx - x) / 2, (yy - y) / 2);   
        
        divisor = new JPanel();
        divisor2 = new JPanel();
        divisor3 = new JPanel();
        
        ventana = new JPanel();
        ventana.setLayout(new BoxLayout(ventana, BoxLayout.Y_AXIS));
        ventana.setSize(x, y);
        
        nombre = new JLabel("Nombre");
        Tnombre = new JTextField();
        JPnombre = new JPanel();
        JPnombre.setLayout(new GridLayout(1,2));
        JPnombre.add(nombre);
        JPnombre.add(Tnombre);      
        
        permisos = new JLabel("Permisos");        
        
        JPpermisos = new JPanel();
        JPpermisos.setLayout(new GridLayout(2, 2));
        lectura = new JLabel("Lectura");
        escritura = new JLabel("Escritura");
        JCBlectura = new JComboBox(opciones);
        JCBescritura = new JComboBox(opciones);
        
        JLabel nombre = (JLabel) ultimoClickeado.getComponent(1);   
        Element elemento;
        if(this.tipoElemento.equalsIgnoreCase("archivo")){
            elemento = Archivo.obtenerNodoHijo(nombre.getText());
        }else{
            elemento = Directorio.obtenerNodoHijo(nombre.getText());
        }
        
        Tnombre.setText(elemento.getAttribute("nombre"));
        JCBlectura.setSelectedItem(elemento.getAttribute("lectura"));
        JCBescritura.setSelectedItem(elemento.getAttribute("escritura"));        
        
        JCBlectura.addActionListener(this);
        JCBescritura.addActionListener(this);
        JPpermisos.add(lectura);
        JPpermisos.add(JCBlectura);       
        JPpermisos.add(escritura);
        JPpermisos.add(JCBescritura);
        
        JPbotones = new JPanel();
        JPbotones.setLayout(new GridLayout(1,2));
        JPbotones.setMaximumSize(new Dimension(x / 2, 20));
        cambiar = new JButton("Editar");
        cancelar = new JButton("Cancelar");
        cambiar.addActionListener(this);
        cancelar.addActionListener(this);
        JPbotones.add(cambiar);
        JPbotones.add(cancelar);
        
        JPnombre.setMaximumSize(new Dimension(x, 25));
        JPpermisos.setMaximumSize(new Dimension(x, 60));
        
        divisor.setMaximumSize(new Dimension(x, 10));
        divisor2.setMaximumSize(new Dimension(x, 10));
        divisor3.setMaximumSize(new Dimension(x, 10));
        
        ventana.add(JPnombre);
        ventana.add(divisor);
        ventana.add(permisos);
        ventana.add(divisor2);      
        ventana.add(JPpermisos);
        ventana.add(divisor3);
        ventana.add(JPbotones);
        
        this.add(ventana);       
        
        this.setResizable(false);
        this.setVisible(true);       
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == cambiar){
            String nombre = Tnombre.getText();
            String lectura = JCBlectura.getSelectedItem().toString();
            String escritura  = JCBescritura.getSelectedItem().toString();
            if(!nombre.isEmpty() && !lectura.isEmpty() && !escritura.isEmpty()){
                if(this.tipoElemento.equalsIgnoreCase("Directorio")){
                    Directorio directorio = new Directorio(Explorador.getCurrentId(), nombre, "Directorio", lectura.equals("Público"), escritura.equals("Público"), Explorador.currentUser.getAttribute("nombre"));
                    directorio.editar();
                }else{
                    Archivo archivo = new Archivo(Explorador.getCurrentId(), nombre, "Archivo", "Lorem", lectura.equals("Público"), escritura.equals("Público"), Explorador.currentUser.getAttribute("nombre"));
                    archivo.editar();
                }
                this.dispose();              
                new Interfaz();
            }else{
                JOptionPane.showMessageDialog(null, "Llena todos los campos del formulario", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(ae.getSource() == cancelar){
            this.dispose();
            new Interfaz();
        }
    }   
}