package de.hska.faki.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import de.hska.faki.gui.drawing.MainFrame;

public class Main {

	public static void main(String[] args) {
		ShapeManager manager = new ShapeManager();
		ShapeView view = new ShapeView(500, 500, manager);
		MainFrame window = new MainFrame("Test?", view);
		
		EventHandler eventHandler = new EventHandler(manager, view);
		window.setDrawingListener(eventHandler);
		
		Circle foo = new Circle(new Point(10, 10), 50, Color.blue);
		manager.addShape(foo);
		
		Circle foo2 = new Circle(new Point(30, 35), 50, Color.blue);
		manager.addShape(foo2);
	}

}
