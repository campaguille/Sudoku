package botones;

import java.awt.Button;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class BotonXCierre extends Button {
    Image imagenFondo = (Toolkit.getDefaultToolkit()).createImage("src/imagenes/imagenBotonXCierre.png"); //Se extrae la imagen

    @Override
    public void paint(Graphics g) { //Se graba la imagen en el boton
        super.paint(g); 
        
        g.drawImage(imagenFondo,0,0, getWidth(), getHeight(), this);
    }
    
    
}
