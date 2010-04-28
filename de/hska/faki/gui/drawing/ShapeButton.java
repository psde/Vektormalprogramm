/*
 * Created on 21.06.2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package de.hska.faki.gui.drawing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JToggleButton;
import javax.swing.UIManager;

/**
 * Ein Button mit einem selbstgezeichneten Bild als Darstellung.
 * @author Holger Vogelsang
 */
public class ShapeButton extends JToggleButton {

    /**
     * Unterstuetzte Button-Typen.
     */
    public enum TYPE { RECTANGLE, CIRCLE, LINE }
    
    private TYPE type;
    
    /**
     * Erstellt einen Button, in dem verschiedene Zustaende gezeichnet werden.
     * @param type Typ des Buttons (siehe TYPE).
     * @param toolTipText Tooltip-Text
     * @param preSelected <code>true</code>: Die Taste ist vorselektiert.
     */
    public ShapeButton(TYPE type, String toolTipText, boolean preSelected) {
        super(URLIcon.createIcon("Images/empty_edit.gif"));
        this.type = type;
        setSelected(preSelected);
        setToolTipText(toolTipText);
    }

    /**
     * Neuzeichnen des Buttons.
     * @param g Grafikkontext.
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D gr2D = (Graphics2D) g.create();
        gr2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Dimension size = getSize();
        gr2D.setColor((Color) UIManager.get("Button.foreground"));
        
        switch (type) {
            case RECTANGLE:
                gr2D.drawRect(getInsets().left, getInsets().top, 
                              size.width - getInsets().left - getInsets().right - 1, 
                              size.height - getInsets().top - getInsets().bottom - 1);
                break;
            case CIRCLE:
                gr2D.drawOval(getInsets().left, getInsets().top, 
                              size.width - getInsets().left - getInsets().right - 1, 
                              size.height - getInsets().top - getInsets().bottom - 1);
                break;
            case LINE:
                gr2D.drawLine(getInsets().left, getInsets().top, 
                        getInsets().left + size.width - 2 * getInsets().right - 1, 
                        getInsets().top + size.height - 2 * getInsets().bottom - 1);
                break;
            default:
                // Tritt nie auf.
        }
    }
}
