/*
 * Created on 19.06.2006
 */
package de.hska.faki.gui.drawing;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ResourceBundle;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

/**
 * Erzeugt das Hauptfenster der Anwendung.
 * @author Holger Vogelsang
 */
public class MainFrame extends JFrame implements ActionListener, MouseListener, 
                                                 MouseMotionListener {
    
    private JToggleButton  selectButton;
    private ShapeButton    circleButton;
    private ShapeButton    rectButton;
    private ShapeButton    lineButton;
    
    // Tasten und Menueeintraege, die nur im Falle einer Selektion aktiv sind
    private AbstractButton[] selectionEnabledButtons = new AbstractButton[ 11 ];
    
    // Tasten und Menueeintraege, die nur im Falle einer Gruppen-Selektion aktiv sind
    private AbstractButton[] selectionGroupEnabledButtons = new AbstractButton[ 2 ];
    
    // Tasten und Menueeintraege, die nur im Falle eines veraenderten Modells aktiv sind
    private AbstractButton[] changedModelEnabledButtons = new AbstractButton[ 2 ];
    
    // Button zur Gruppierung
    private AbstractButton[] groupButtons = new AbstractButton[ 2 ];
    
    private ButtonGroup  typeGroup;
    private JPanel       panel;
    private JScrollPane  scrollPane;
    
    // Verwaltung des einzigen Listeners
    private transient DrawingListener listener;

    // Aktueller Speicherpfad
    private String currentPath = System.getProperty("user.dir");
    
    // Startposition der Maus beim Verschieben
    private Point  mouseStartMove;
    
    // Modus: Figur erzeugen oder verschieben
    private boolean interactiveCreation = false;
    
    private JToolBar bar;
    private JMenuBar menuBar;
    
    private ResourceBundle bundle;
    
    // Alle definierten Aktionstypen
    private static final String ACTION_LOAD_MODEL = "loadModel";
    private static final String ACTION_SAVE_MODEL = "saveModel";
    private static final String ACTION_SORT_FIGURES = "sortFigures";
    private static final String ACTION_DELETE_FIGURES = "deleteFigures";
    private static final String ACTION_PASTE_FIGURES = "pasteFigures";
    private static final String ACTION_COPY_FIGURES = "copyFigures";
    private static final String ACTION_UNGROUP_FIGURES = "ungroupFigures";
    private static final String ACTION_GROUP_FIGURES = "groupFigures";
    private static final String ACTION_BOTTOM = "bottom";
    private static final String ACTION_DOWN = "down";
    private static final String ACTION_UP = "up";
    private static final String ACTION_TOP = "top";

    /**
     * Erzeugt das Hauptfenster.
     * @param newTitle Titelzeile des Fensters.
     * @param newPanel Zeichenflaeche, in der die Figuren gezeichnet werden.
     */
    public MainFrame(String newTitle, JPanel newPanel) {
        super(newTitle);
        panel = newPanel;
        createUI();
        pack();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                checkForUnsafedModel();
                System.exit(0);
            }
        });
        setVisible(true);
    }
    
    /**
     * Zugriff auf die Ressource-Datei. Ein Mnemonic wird
     * dabei ueberlesen. Bastelloesung: Das Mnemonic muss
     * '&' sein, ein escapen ist nicht moeglich und
     * es wird nur der erste Treffer untersucht.
     * @param key Schluessel, dessen Wert gesucht wird.
     * @return Wert zum Schluessel.
     */
    private String getResource(String key) {
        if (bundle == null) {
            bundle = ResourceBundle.getBundle("i18n/lang");
        }
        String text = bundle.getString(key);
        int index = text.indexOf('&');
        if (index >= 0) {
            return text.substring(0, index) + text.substring(index + 1);
        }
        return text;
    }
    
    /**
     * Zugriff auf die Ressource-Datei. Mnemonic eines
     * Textes auslesen.
     * @param key Schluessel, dessen Menmonic gesucht wird.
     * @return Mnemonic zum Schluessel.
     */
    private char getResourceMnemonic(String key) {
        if (bundle == null) {
            bundle = ResourceBundle.getBundle("i18n/lang");
        }
        String text = bundle.getString(key);
        int index = text.indexOf('&');
        return (index >= 0) ? text.charAt(index + 1) : ' ';
    }
    
    /**
     * Bau der GUI.
     */
    private void createUI() {
        bar = new JToolBar();

        changedModelEnabledButtons[ 0 ] = createButton(bar, getResource("Save.Image"), ACTION_SAVE_MODEL,
                                                       getResource("Save.Tooltip"));
        bar.addSeparator();
        createButton(bar, "sort.gif", ACTION_SORT_FIGURES, "Figuren sortiert in den Ebenen anordnen");
        
        // Figur in die oberste Ebene verschieben
        selectionEnabledButtons[ 0 ] = createButton(bar, getResource("Top.Image"), ACTION_TOP,
                                                    getResource("Top.Tooltip"));
        // Figur eine Ebene noch oben verschieben
        selectionEnabledButtons[ 1 ] = createButton(bar, getResource("Up.Image"), ACTION_UP,
                                                    getResource("Up.Tooltip"));
        // Figur eine Ebene noch unten verschieben
        selectionEnabledButtons[ 2 ] = createButton(bar, getResource("Down.Image"), ACTION_DOWN,
                                                    getResource("Down.Tooltip"));
        // Figur in die unterste Ebene verschieben
        selectionEnabledButtons[ 3 ] = createButton(bar, getResource("Bottom.Image"), ACTION_BOTTOM,
                                                    getResource("Bottom.Tooltip"));
        bar.addSeparator();
        
        // Figuren gruppieren
        groupButtons[ 0 ] = createButton(bar, getResource("Group.Image"), ACTION_GROUP_FIGURES,
                                         getResource("Group.Tooltip"));
        // Gruppe aufloesen
        selectionGroupEnabledButtons[ 0 ] = createButton(bar, getResource("Ungroup.Image"), ACTION_UNGROUP_FIGURES,
                                                         getResource("Ungroup.Tooltip"));
        bar.addSeparator();
        
        // Figur auswaehlen
        selectButton = new JToggleButton(URLIcon.createIcon(getResource("Select.Image")));
        selectButton.setToolTipText(getResource("Select.Tooltip"));
        
        // Kreis erstellen
        circleButton = new ShapeButton(ShapeButton.TYPE.CIRCLE, getResource("CreateCircle.Tooltip"), true);
        // Rechteck erstellen
        rectButton = new ShapeButton(ShapeButton.TYPE.RECTANGLE, getResource("CreateRectangle.Tooltip"), false);
        // Linie erstellen
        lineButton = new ShapeButton(ShapeButton.TYPE.LINE, getResource("CreateLine.Tooltip"), false);
        
        typeGroup = new ButtonGroup();
        typeGroup.add(circleButton);
        typeGroup.add(rectButton);
        typeGroup.add(lineButton);
        typeGroup.add(selectButton);
        
        bar.add(selectButton);
        bar.add(circleButton);
        bar.add(rectButton);
        bar.add(lineButton);
        
        bar.add(Box.createGlue());
        
        add(bar, BorderLayout.PAGE_START);
        
        // Zeichenflaeche in ScrollPane packen
        scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);
        
        // Mausereignisse abfangen
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
        
        // Menue bauen (Datei)
        JMenu    fileMenu = new JMenu(getResource("File.Text"));
        fileMenu.setMnemonic(getResourceMnemonic("File.Text"));
        createMenuItem(fileMenu, getResource("Open.Text"), null, getResource("Open.Acc"),
                       getResourceMnemonic("Open.Text"), ACTION_LOAD_MODEL);
        changedModelEnabledButtons[ 1 ] = createMenuItem(fileMenu, getResource("Save.Text"),
                                                         getResource("Save.Image"),
                                                         getResource("Save.Acc"),
                                                         getResourceMnemonic("Save.Text"), ACTION_SAVE_MODEL);

        // Menue bauen (Bearbeiten)
        JMenu    editMenu = new JMenu(getResource("Edit.Text"));
        editMenu.setMnemonic(getResourceMnemonic("Edit.Text"));
        selectionEnabledButtons[ 4 ] = createMenuItem(editMenu, getResource("Copy.Text"),
                                                      getResource("Copy.Image"),
                                                      getResource("Copy.Acc"),
                                                      getResourceMnemonic("Copy.Text"), ACTION_COPY_FIGURES);
        selectionEnabledButtons[ 5 ] = createMenuItem(editMenu, getResource("Paste.Text"),
                                                      getResource("Paste.Image"),
                                                      getResource("Paste.Acc"),
                                                      getResourceMnemonic("Paste.Text"), ACTION_PASTE_FIGURES);
        selectionEnabledButtons[ 6 ] = createMenuItem(editMenu, getResource("Delete.Text"),
                                                      getResource("Delete.Image"),
                                                      getResource("Delete.Acc"),
                                                      getResourceMnemonic("Delete.Text"), ACTION_DELETE_FIGURES);

        // Menue bauen (Figur)
        JMenu    figureMenu = new JMenu(getResource("Figure.Text"));
        figureMenu.setMnemonic(getResourceMnemonic("Figure.Text"));
        selectionEnabledButtons[  7 ] = createMenuItem(figureMenu, getResource("Top.Text"),
                                                      getResource("Top.Image"),
                                                      getResource("Top.Acc"),
                                                      getResourceMnemonic("Top.Text"), ACTION_TOP);
        selectionEnabledButtons[  8 ] = createMenuItem(figureMenu, getResource("Up.Text"),
                                                      getResource("Up.Image"),
                                                      getResource("Up.Acc"),
                                                      getResourceMnemonic("Up.Text"), ACTION_UP);
        selectionEnabledButtons[  9 ] = createMenuItem(figureMenu, getResource("Down.Text"),
                                                      getResource("Down.Image"),
                                                      getResource("Down.Acc"),
                                                      getResourceMnemonic("Down.Text"), ACTION_DOWN);
        selectionEnabledButtons[ 10 ] = createMenuItem(figureMenu, getResource("Bottom.Text"),
                                                        getResource("Bottom.Image"),
                                                        getResource("Bottom.Acc"),
                                                        getResourceMnemonic("Bottom.Text"), ACTION_BOTTOM);
        createMenuItem(figureMenu, getResource("Sort.Text"), getResource("Sort.Image"),
                       getResource("Sort.Acc"), getResourceMnemonic("Sort.Text"), ACTION_SORT_FIGURES);
        figureMenu.addSeparator();
        groupButtons[ 1 ] = createMenuItem(figureMenu, getResource("Group.Text"),
                                           getResource("Group.Image"),
                                           getResource("Group.Acc"),
                                           getResourceMnemonic("Group.Text"), ACTION_GROUP_FIGURES);
        selectionGroupEnabledButtons[ 1 ] = createMenuItem(figureMenu, getResource("Ungroup.Text"),
                                                           getResource("Ungroup.Image"),
                                                           getResource("Ungroup.Acc"),
                                                           getResourceMnemonic("Ungroup.Text"), ACTION_UNGROUP_FIGURES);
        
        menuBar = new JMenuBar();
        
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(figureMenu);
        
        setJMenuBar(menuBar);
        enableButtons();
    }

    /**
     * Erzeugt einen Menueeintrag und registriert die Hauptanwendung als Listener.
     * @param menu          Menue, zu dem der Eintrag hinzugefuegt werden soll.
     * @param label         Bezeichnung fuer den Eintrag
     * @param iconName      Name des zu verwendenen Bildes aus dem Ordner "images".
     *                      Der Name darf <code>null</code> sein, wenn kein Bild
     *                      verwendet werden soll.
     * @param keyStroke     Tastaturkuerzel wie z.B. "control c"
     * @param mnemonic      Menomic, muss ein Buchstabe aus <code>label</code> sein.
     * @param actionCommand Textuelles Kommando, das bei Aktivierung auslgeloest wird.
     * @return Erzeugter Menueeintrag.
     */
    private JMenuItem createMenuItem(JMenu menu, String label, String iconName, String keyStroke,
                                     char mnemonic, String actionCommand) {
        JMenuItem item = new JMenuItem(label);
        if (iconName != null) {
            item.setIcon(URLIcon.createIcon("Images/" + iconName));
        }
        item.setAccelerator(KeyStroke.getKeyStroke(keyStroke));
        item.setMnemonic(mnemonic);
        item.setActionCommand(actionCommand);
        item.addActionListener(this);
        menu.add(item);
        return item;
    }

    /**
     * Erzeugt einen neuen Button ohne Text.
     * @param bar           Toolbar-Leiste, zu der der Button hinzugefuegt wird.
     * @param iconName      Name des zu verwendenen Bildes aus dem Ordner "images".
     * @param actionCommand Textuelles Kommando, das bei Aktivierung auslgeloest wird.
     * @param toolTipText   ToolTip-Text fuer den Button
     * @return              Erzeugter Button
     */
    private JButton createButton(JToolBar bar, String iconName, String actionCommand, String toolTipText) {
        JButton button = new JButton(URLIcon.createIcon("Images/" + iconName));
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        button.addActionListener(this);
        bar.add(button);
        return button;
    }

    
    /**
     * Eine Methode auf dem Beobachter aufrufen.
     * @param methodName Name der aufzurufenden Methode.
     * @param paramTypes Uebergabeparametertypen der Methode.
     * @param params Uebergabeparameter an die Methode.
     */
//    private void fireEvent(String methodName, Class<?>[] paramTypes, Object[] params) {
//        try {
//            Method eventMethod = DrawingListener.class.getMethod(methodName, paramTypes);
//            eventMethod.invoke(listener, params);
//        } 
//        // Tritt z.B. als InvocationTargetExeption auf
//        catch (Throwable thr) {
//            thr.getCause().printStackTrace(System.err);
//        }
//    }

    /**
     * Ereignisbehandlung fuer alle Buttons. Implementiert ohne Reflection
     * wegen des einfacheren Moeglichkeit, einen Obfuscator einzusetzen.
     * @param event Eingetretenes Ereignis.
     */
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals(ACTION_UP)) {
            listener.moveFiguresInLayers(DrawingListener.LAYER_MOVE.UP);
        }
        else if (event.getActionCommand().equals(ACTION_DOWN)) {
            listener.moveFiguresInLayers(DrawingListener.LAYER_MOVE.DOWN);
        }
        else if (event.getActionCommand().equals(ACTION_TOP)) {
            listener.moveFiguresInLayers(DrawingListener.LAYER_MOVE.TOP);
        }
        else if (event.getActionCommand().equals(ACTION_BOTTOM)) {
            listener.moveFiguresInLayers(DrawingListener.LAYER_MOVE.BOTTOM);
        }
        else if (event.getActionCommand().equals(ACTION_SAVE_MODEL)) {
            saveModel();
        }
        else if (event.getActionCommand().equals(ACTION_LOAD_MODEL)) {
            checkForUnsafedModel();
            loadModel();
        }
        else if (event.getActionCommand().equals(ACTION_DELETE_FIGURES)) {
            listener.deleteFigures();
        }
        else if (event.getActionCommand().equals(ACTION_COPY_FIGURES)) {
            listener.copyFigures();
        }
        else if (event.getActionCommand().equals(ACTION_PASTE_FIGURES)) {
            listener.pasteFigures();
        }
        else if (event.getActionCommand().equals(ACTION_SORT_FIGURES)) {
            listener.sortFigures();
        }
        else if (event.getActionCommand().equals(ACTION_GROUP_FIGURES)) {
            listener.groupFigures();
        }
        else if (event.getActionCommand().equals(ACTION_UNGROUP_FIGURES)) {
            listener.ungroupFigures();
        }
        enableButtons();
    }

    /**
     * Model auf Nachfrage sichern, wenn es seit der letzten Sicherung
     * veraendert wurde.
     */
    private void checkForUnsafedModel() {
        if (listener.isModelChanged()) {
            String[] options = {getResource("SaveQuestion.YesOption"), getResource("SaveQuestion.NoOption")};
            if (JOptionPane.showOptionDialog(this, getResource("SaveQuestion.Text"),
                                                 getResource("SaveQuestion.Title"),
                                             JOptionPane.YES_NO_OPTION,
                                             JOptionPane.QUESTION_MESSAGE,
                                             null, options, options[ 0 ]) == 0) {
                saveModel();
            }
        }
    }

    /**
     * Versucht, ein neues Modell zu laden.
     */
    private void loadModel() {
        JFileChooser chooser = new JFileChooser(currentPath);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            currentPath = chooser.getSelectedFile().getPath();
          listener.loadModel(currentPath);
        }
    }

    /**
     * Versucht, das aktuelle Modell zu speichern.
     */
    private void saveModel() {
        JFileChooser chooser = new JFileChooser(currentPath);
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            currentPath = chooser.getSelectedFile().getPath();
          // Existiert die Datei schon?
          File savedFile = new File(currentPath);
          if (savedFile.exists()) {
              String[] options = {getResource("FileExistsQuestion.YesOption"),
                                  getResource("FileExistsQuestion.NoOption")};
              if (JOptionPane.showOptionDialog(this, getResource("FileExistsQuestion.Text"),
                                               getResource("FileExistsQuestion.Title"),
                                               JOptionPane.YES_NO_OPTION,
                                               JOptionPane.QUESTION_MESSAGE,
                                               null, options, options[ 0 ]) == 1) {
                  return;
              }
          }
          listener.saveModel(currentPath);
        }
    }

    /**
     * Einen Listener registrieren.
     * In dieser Implementierung wird nur noch ein Listener unterstuetzt.
     * @param listener Neuer Listener, der im Falle von Ereignissen
     *                 informiert werden soll.
     */
    public void setDrawingListener(DrawingListener listener) {
        this.listener = listener;
        enableButtons();
    }
    

    /**
     * Tasten sperren und entsperren.
     * In Abhaengigkeit der Selektion in der Anwendung werden tasten nur
     * dann entsperrt, wenn sie auch sinnvoll bedienbar sind.
     *
     */
    private void enableButtons() {
        for (AbstractButton button : selectionEnabledButtons) {
            button.setEnabled(listener != null ? listener.getSelectedFigureCount() > 0 : false);
        }
        for (AbstractButton button : selectionGroupEnabledButtons) {
            button.setEnabled(listener != null ? listener.isGroupSelected() : false);
        }
        for (AbstractButton button : changedModelEnabledButtons) {
            button.setEnabled(listener != null ? listener.isModelChanged() : false);
        }
        for (AbstractButton button : groupButtons) {
            button.setEnabled(listener != null ? listener.getSelectedFigureCount() > 1 : false);
        }
    }

    /**
     * Mausklick zur Selektion: Listener informieren.
     * @param event Aktuelles Mausereignis.
     */
    public void mouseClicked(MouseEvent event) {
        if (selectButton.isSelected()) {
            listener.selectFigure(event.getPoint(), (event.getModifiers() & InputEvent.SHIFT_MASK) != 0);
        }
        enableButtons();
    }

    /**
     * Maus betritt die Anwendung.
     * @param event Aktuelles Mausereignis.
     * 
     */
    public void mouseEntered(MouseEvent event) {
    }

    /**
     * Maus verlaesst die Anwendung.
     * @param event Aktuelles Mausereignis.
     * 
     */
    public void mouseExited(MouseEvent event) {
    }

    /**
     * Maustaste wurde gedrueckt und ist noch gedrueckt.
     * @param event Aktuelles Mausereignis.
     */
    public void mousePressed(MouseEvent event) {
        String actionType = "";
        mouseStartMove = null;
        if (circleButton.isSelected()) {
            actionType = "circle";
        }
        else if (rectButton.isSelected()) {
            actionType = "rect";
        }
        else if (lineButton.isSelected()) {
            actionType = "line";
        }
        
        if (selectButton.isSelected()) {
            mouseStartMove = event.getPoint();
            interactiveCreation = false;
        }
        else {
            interactiveCreation = true;
            listener.startCreateFigure(actionType, event.getPoint());
        }
        enableButtons();
    }

    /**
     * Maustaste war gedrueckt und wurde wieder losgelassen.
     * @param event Aktuelles Mausereignis.
     */
    public void mouseReleased(MouseEvent event) {
//        if (mouseStartMove == null) {
            if (interactiveCreation) {
                listener.endCreateFigure(event.getPoint());
            }
            else {
                listener.endMoveFigure(event.getPoint());
            }
//        }
//        else {
            mouseStartMove = null;
//        }
        //selectButton.setSelected(true);
        enableButtons();
    }

    /**
     * Dragging mit der Maus: Listener informieren.
     * @param event Aktuelles Mausereignis.
     */
    public void mouseDragged(MouseEvent event) {
        // Erste Mausbewegung beim Verschieben -> Start einleiten
        if (mouseStartMove != null) {
            listener.startMoveFigure(mouseStartMove);
            mouseStartMove = null;
        }
        if (interactiveCreation) {
            listener.workCreateFigure(event.getPoint());
        }
        else {
            listener.workMoveFigure(event.getPoint());
        }
        enableButtons();
    }

    /**
     * Mauszeiger wurde bewegt.
     * @param event Aktuelles Mausereignis.
     * 
     */
    public void mouseMoved(MouseEvent event) {
    }
}
