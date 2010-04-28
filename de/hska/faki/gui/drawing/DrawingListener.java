/*
 * Created on 02.07.2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package de.hska.faki.gui.drawing;

import java.awt.Point;
import java.util.EventListener;

/**
 * Ein konkreter Beobachter der Zeichenereignisse muss diese
 * Schnittstelle implementieren.
 * @author H. Vogelsang
 */
public interface DrawingListener extends EventListener {
    
    /**
     * Art der Verschiebung der Figur innerhalb der Ebenen.
     */
    public enum LAYER_MOVE { TOP, BOTTOM, UP, DOWN };
    
    /**
     * Reaktion auf einen Mausklick in die Zeichenflaeche. Dabei wird eine neue
     * Figur erzeugt.
     * @param figureType Zu erzeugende Figur:
     *                   <ul>
     *                     <li><code>"circle"</code> Es soll ein neuer Kreis erzeugt werden.
     *                     <li><code>"rect"</code> Es soll ein neues Rechteck erzeugt werden.
     *                     <li><code>"line"</code> Es soll eine neue Linie erzeugt werden.
     *                   </ul>
     * @param pos Position des Mauszeigers waehrend des Klicks. 
     */
    void startCreateFigure(String figureType, Point pos);

    /**
     * Die Figur an der mit <code>pos</code> gekennzeichneten Stelle
     * soll verschoben werden.
     * @param pos Position des Mauszeigers waehrend des Klicks.
     */
    void startMoveFigure(Point pos);
    
    
    /**
     * Der Mauszeiger wird mit gedrueckter Maustaste ueber die
     * Zeichenflaeche bewegt. Dabei wird die neu mit
     * <code>startCreateFigure</code> erzeugte Figur in der Groesse
     * veraendert.
     * @param pos Aktuelle Mausposition.
     */
    void workCreateFigure(Point pos);
    
    /**
     * Der Mauszeiger wird mit gedrueckter Maustaste ueber die
     * Zeichenflaeche bewegt. Dabei wird eine mit
     * <code>startMoveFigure</code> gewaehlte Figur verschoben.
     * @param pos Aktuelle Mausposition.
     */
    void workMoveFigure(Point pos);
   
    /**
     * Der Mauszeiger wird wieder losgelassen. Das Erzeugen
     * der Figur ist somit beendet.
     * @param pos Aktuelle Mausposition.
     */
    void endCreateFigure(Point pos);
    
    /**
     * Der Mauszeiger wird wieder losgelassen. Das Verschieben
     * der Figur ist somit beendet.
     * @param pos Aktuelle Mausposition.
     */
    void endMoveFigure(Point pos);
    
    /**
     * Selektionsereignis: Die Maustaste wurde (eventuell auf einer Figur)
     * gedrueckt und wieder losgelassen.
     * @param pos Position des Mauszeigers waehrend des Klicks.
     * @param shiftPressed <code>true</code>: Die Shift-Taste wurde waehrend des
     *                     Mausklicks gedrueckt. 
     */
    void selectFigure(Point pos, boolean shiftPressed);
    
    /**
     * Aufforderung zum Loeschen der selektierten Figur.
     */
    void deleteFigures();

    /**
     * Aufforderung zum Kopieren der selektierten Figur.
     */
    void copyFigures();

    /**
     * Aufforderung zum Einfuegen einer kopierten Figur.
     */
    void pasteFigures();

    /**
     * Aufforderung zum Speichern aller Figuren.
     * @param fileName Name der Datei, unter der die Figuren gespeichert werden sollen.
     */
    void saveModel(String fileName);
    
    /**
     * Aufforderung zum Laden eines Bildes.
     * @param fileName Name der Datei, aus der die Figuren geladen werden sollen.
     */
    void loadModel(String fileName);
    
    /**
     * Selektierte Figur innerhalb der Ebenen verschieben.
     * @param move Art der Verschiebung.
     */
    void moveFiguresInLayers(LAYER_MOVE move);
    
    /**
     * Alle Figuren anhand der BoundingBox-Groesse sortieren.
     * Die groesste Figur wandert in die unterste, die kleinste in die oberste Ebene.
     */
    void sortFigures();

    /**
     * Alle selektierten Figuren zu einer Gruppe zusammenfassen.
     */
    void groupFigures();
    
    /**
     * Alle selektierten Gruppen aufloesen.
     */
    void ungroupFigures();
    
    /**
     * Wurde das Modell seit dem letzten Speichervorgang veraendert?
     * @return <code>true</code>: Es wurde veraendert.
     */
    boolean isModelChanged();
    
    /**
     * Anzahl selektierter Figuren ermitteln.
     * @return Anzahl selektierter Figuren.
     */
    int getSelectedFigureCount();
    
    /**
     * Ist eine Gruppe momentan selektiert?
     * @return <code>true</code> Ja, es ist mindestens eine Gruppe selektiert.
     */
    boolean isGroupSelected();
}
