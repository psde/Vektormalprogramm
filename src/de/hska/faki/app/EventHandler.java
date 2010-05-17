package de.hska.faki.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import de.hska.faki.gui.drawing.DrawingListener;

public class EventHandler implements DrawingListener {
	
	private ShapeManager manager;
	private ShapeView view;
	
	private Point moveOffset;
	
	private ArrayList<Shape> clipboard;
	
	public EventHandler(ShapeManager manager, ShapeView view)
	{
		this.manager = manager;
		this.view = view;
		this.moveOffset = new Point(0,0);
		this.clipboard = new ArrayList<Shape>();
	}

	@Override
	public void copyFigures() {
		this.clipboard.clear();
		ArrayList<Shape> shapes = this.manager.getSelection();
		for(Shape curShape : shapes)
		{
			this.clipboard.add(curShape.clone());
		}
		this.view.updateRect();
	}

	@Override
	public void pasteFigures() {
		if(this.clipboard.isEmpty())
			return;
		
		for(Shape curShape : this.clipboard)
		{
			Point newOrigin = curShape.getOrigin();
			newOrigin.x += 10;
			newOrigin.y += 10;
			curShape.setOrigin(newOrigin); 
			this.manager.addShape(curShape);
		}
		
		this.clipboard.clear();
		
		this.view.updateRect();
	}

	@Override
	public void deleteFigures() {
		ArrayList<Shape> shapes = this.manager.getSelection();
		for(Shape curShape : shapes)
		{
			this.manager.erase(curShape);
		}
		this.view.updateRect();
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
		return this.manager.getSelectedShapesCount();
	}

	@Override
	public void groupFigures() {
		this.manager.groupSelectedShapes();
		this.view.updateRect();
	}

	@Override
	public boolean isGroupSelected() {
		if(this.manager.getSelectedGroupsCount() > 0) return true;
		return false;
	}

	@Override
	public boolean isModelChanged() {
		return this.manager.hasChanged();
	}
	
	@Override
	public void saveModel(String fileName) {
		IOHandler io = new IOHandler();
		io.write(this.manager.getShapes(), fileName);
		this.manager.setChanges(false);
	}

	@Override
	public void loadModel(String fileName) {
		IOHandler io = new IOHandler();
		this.manager.reset(io.read(fileName));
		this.manager.setChanges(false);
		this.view.updateRect();
	}

	@Override
	public void moveFiguresInLayers(LAYER_MOVE move) {
		if (move == DrawingListener.LAYER_MOVE.TOP) {
			this.manager.moveToTop(this.manager.getSelection());
		}
		else if (move == DrawingListener.LAYER_MOVE.BOTTOM) {
			this.manager.moveToBottom(this.manager.getSelection());
		}
		else if (move == DrawingListener.LAYER_MOVE.UP) {
			this.manager.moveUp(this.manager.getSelection());
		}
		else if (move == DrawingListener.LAYER_MOVE.DOWN) {
			this.manager.moveDown(this.manager.getSelection());
		}
		this.view.updateRect();
	}



	@Override
	public void selectFigure(Point pos, boolean shiftPressed) {
		if(!shiftPressed)
			this.manager.deselectAll();
		
		Shape selectedShape = this.manager.getShapeAtPoint(pos);
		if(selectedShape != null)
			this.manager.selectShape(selectedShape);

		this.view.updateRect();
	}

	@Override
	public void sortFigures() {
		this.manager.sort();
		this.view.updateRect();
	}

	@Override
	public void startCreateFigure(String figureType, Point pos) {
		Shape newShape = null;
		
		if(figureType == "circle") newShape = new Circle(pos, 0, Color.blue);
		if(figureType == "rect") newShape = new Rect(pos, new Dimension(0, 0), Color.red);
		if(figureType == "line") newShape = new Line(pos, new Dimension(0, 0), Color.magenta);
		
		if(newShape == null) return;
		
		manager.addShape(newShape);
		this.view.updateRect();
	}

	@Override
	public void startMoveFigure(Point pos) {
		this.selectFigure(pos, false);
		
		Shape selectedShape = this.manager.getFirstSelectedShape();
		if(selectedShape == null) return;
		
		this.moveOffset = new Point(pos.x - selectedShape.getOrigin().x, pos.y - selectedShape.getOrigin().y);
	}

	@Override
	public void ungroupFigures() {
		manager.ungroupSelectedShapes();
		this.view.updateRect();
	}

	@Override
	public void workCreateFigure(Point pos) {
		Shape topShape = this.manager.getTopShape();
		
		topShape.setDimension(new Dimension(pos.x - topShape.getOrigin().x, pos.y - topShape.getOrigin().y));
		
		this.view.updateRect();
	}

	@Override
	public void workMoveFigure(Point pos) {
		if(this.manager.getSelectedShapesCount() != 1) return;
		
		Point newPosition = new Point(pos.x - moveOffset.x, pos.y - moveOffset.y);
		
		for (Shape curShape : manager.getShapes()) {
			if(curShape.isSelected())
			{
				curShape.setOrigin(newPosition);
			}
		}
		this.view.updateRect();
	}

}
