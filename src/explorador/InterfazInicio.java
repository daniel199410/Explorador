package explorador;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Daniel
 */
public class InterfazInicio extends JFrame implements ActionListener{
    
    private JPanel ventana, campoNombre, campoPass, campoBotones, divisor;
    private JLabel header, nombre, pass;
    private JButton ingresar, crearCuenta;
    private JTextField tnombre;
    private JPasswordField Ppass;
    
    public InterfazInicio(){
        super("Login");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int xx = screenSize.width;
        int yy = screenSize.height;
        int x = 300;
        int y = 150;
        this.setSize(x, y);
        this.setLocation((xx - x) / 2, (yy - y) / 2);  
        
        ventana = new JPanel();
        ventana.setLayout(new BoxLayout(ventana, BoxLayout.Y_AXIS));
        ventana.setSize(x, y);
        
        header = new JLabel("Inicio sesión");
        
        campoNombre = new JPanel(new GridLayout(1, 2));
        campoNombre.setMaximumSize(new Dimension(x, 20));
        nombre = new JLabel("Nombre de usuario");
        tnombre = new JTextField();
        campoNombre.add(nombre);
        campoNombre.add(tnombre);
        
        campoPass = new JPanel(new GridLayout(1, 2));
        campoPass.setMaximumSize(new Dimension(x, 20));
        pass = new JLabel("Contraseña");
        Ppass = new JPasswordField();
        campoPass.add(pass);
        campoPass.add(Ppass);
        
        divisor = new JPanel();
        divisor.setMaximumSize(new Dimension(x, 20));
        
        ingresar = new JButton("Ingresar");
        crearCuenta = new JButton("Crear cuenta");
        ingresar.addActionListener(this);
        crearCuenta.addActionListener(this);
        campoBotones = new JPanel();
        campoBotones.setLayout(new GridLayout(1, 2));
        campoBotones.setMaximumSize(new Dimension((int) (x * 0.8), 20));
        campoBotones.add(ingresar);
        campoBotones.add(crearCuenta);
        
        ventana.add(header);
        ventana.add(campoNombre);
        ventana.add(campoPass);  
        ventana.add(divisor);
        ventana.add(campoBotones);
        
        this.add(ventana);       
        this.setResizable(false);
        this.setBackground(Color.LIGHT_GRAY);
        this.setVisible(true);       
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String nombre = tnombre.getText();
        String pass = Ppass.getText();
        if(ae.getSource() == ingresar){
            if(!nombre.isEmpty() && !pass.isEmpty()){
                Usuario usuario = new Usuario(Usuario.getCurrentId(), nombre, "", pass);
                if(usuario.existeUsuario()){
                    usuario.iniciarSesion();
                    this.dispose();
                    new Interfaz();
                }else{
                    JOptionPane.showMessageDialog(null, "El usuario no existe", "El usuario no existe", JOptionPane.ERROR_MESSAGE);
                }
            }else
                JOptionPane.showMessageDialog(null, "Ingresa un nombre y una contraseña", "Datos incompletos", JOptionPane.ERROR_MESSAGE);
        }
        if(ae.getSource() == crearCuenta){
            this.dispose();
            new InterfazRegistro();
        }
    }
}
