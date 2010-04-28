package de.hska.faki.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import de.hska.faki.gui.drawing.DrawingListener;

public class EventHandler implements DrawingListener {
	
	private ShapeManager manager;
	private ShapeView view;
	
	public EventHandler(ShapeManager manager, ShapeView view)
	{
		this.manager = manager;
		this.view = view;
	}

	@Override
	public void copyFigures() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteFigures() {
		// TODO Auto-generated method stub

	}

	@Override
	public void endCreateFigure(Point pos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endMoveFigure(Point pos) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getSelectedFigureCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void groupFigures() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isGroupSelected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isModelChanged() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void loadModel(String fileName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveFiguresInLayers(LAYER_MOVE move) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pasteFigures() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveModel(String fileName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void selectFigure(Point pos, boolean shiftPressed) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sortFigures() {
		// TODO Auto-generated method stub

	}

	@Override
	public void startCreateFigure(String figureType, Point pos) {
		Shape newShape = null;
		
		if(figureType == "circle") newShape = new Circle(pos, 0, Color.blue);
		if(figureType == "rect") newShape = new Rect(pos, new Dimension(0, 0), Color.red);
		if(figureType == "line") newShape = new Line(pos, new Dimension(0, 0), Color.magenta);
		
		if(newShape == null) return;
		
		manager.addShape(newShape);
		this.view.updateUI();
		this.view.repaint();
	}

	@Override
	public void startMoveFigure(Point pos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ungroupFigures() {
		// TODO Auto-generated method stub

	}

	@Override
	public void workCreateFigure(Point pos) {
		ArrayList<Shape> shapes = this.manager.getShapes();
		Shape topShape = shapes.get(shapes.size() - 1);
		
		topShape.setDimension(new Dimension(pos.x - topShape.getOrigin().x, pos.y - topShape.getOrigin().y));
		this.view.updateUI();
		this.view.repaint();
	}

	@Override
	public void workMoveFigure(Point pos) {
		// TODO Auto-generated method stub

	}

}
