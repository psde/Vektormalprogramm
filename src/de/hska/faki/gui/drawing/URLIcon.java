package de.hska.faki.gui.drawing;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

/**
    Die Quelle einer Ressource kann sehr unterschiedlich sein:
    <ul>
      <li> eine Datei im Klassenpfad.</li>
      <li> eine URL.</li>
      <li> eine Datei im angegebenen Pfad.</li>
    </ul>
    Die folgende kleine Hilfsklasse testet einfach alle Moeglichkeiten.
*/

public class URLIcon {
    /**
     * Erzeugt ein neues Icon aus verschiedenen Quellen.
     * @param spec URL oder Dateiname.
     * @return     Erzeugtes Icon.
     */
    public static ImageIcon createIcon(String spec) {
        URL url = ClassLoader.getSystemResource(spec); // 1.
        if (url != null) {
            return new ImageIcon(url); // 1.
        }
        try {
            return new ImageIcon(new URL(spec)); // 2.
        }
        catch (MalformedURLException ex) {
            return new ImageIcon(spec); // 3.
        }
    }
}
