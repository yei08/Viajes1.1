/*
* Esta clase contine el metodo main, es decir que funcionara como
* punto de inicio o ejecucion de nuestro programa
 */
package co.edu.udec.software.poo.viajes;

import javax.swing.JFrame;
import co.edu.udec.software.poo.vistas.VentanaPrincipal;

/**
 *
 * @author Reinaldo Quintana
 */
public class Viajes {

    public static void main(String arg[]) {

        VentanaPrincipal ventana = null;
        ventana = new VentanaPrincipal();
// le colocamos un titulo a la ventana
        ventana.setTitle("$$$ Agencia De Viajes $$$");
// hacemos que la ventana se maximice
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
// hacemos visible la ventana
        ventana.setVisible(true);
    }
}
