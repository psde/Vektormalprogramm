package de.hska.faki.app;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class ShapeView extends JPanel {
	
	private ShapeManager manager;
	
	public ShapeView(int width, int height, ShapeManager manager)
	{
		super();
		this.setPreferredSize(new Dimension(width, height));
		this.manager = manager;
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D gr2D = (Graphics2D)g;
		gr2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		gr2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr2D.clearRect(0, 0, getWidth(), getHeight());
		
		/*ArrayList<Shape> shapes = manager.getShapes();
		for(int i = 0; i < shapes.size(); i++)
		{
			shapes.get(i).paintComponent(gr2D);
		}*/
		
		for (Shape curShape : manager.getShapes()) {
			curShape.paintComponent(gr2D);
		}
	}
	
	//TODO: DELETE THIS
	public void updateRect()
	{
		this.updateUI();
		this.repaint();
	}
	
	public void updateRect(Point origin, Dimension dimension)
	{
		
	}
}
