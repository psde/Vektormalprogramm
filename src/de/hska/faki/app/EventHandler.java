package de.hska.faki.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import de.hska.faki.gui.drawing.DrawingListener;

public class EventHandler implements DrawingListener {
	
	private ShapeManager manager;
	private ShapeView view;
	
	private Point moveOffset;
	
	public EventHandler(ShapeManager manager, ShapeView view)
	{
		this.manager = manager;
		this.view = view;
		this.moveOffset = new Point(0,0);
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
		return this.manager.getSelectedShapesCount();
	}

	@Override
	public void groupFigures() {
		this.manager.groupSelectedShapes();
	}

	@Override
	public boolean isGroupSelected() {
		if(this.manager.getSelectedGroupsCount() > 0) return true;
		return false;
	}

	@Override
	public boolean isModelChanged() {
		// TODO DEBUG
		return false; //this.manager.hasChanged();
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
		if(!shiftPressed)
			this.manager.deselectAll();
		
		Shape selectedShape = this.manager.getShapeAtPoint(pos);
		if(selectedShape != null)
			this.manager.selectShape(selectedShape);

		this.view.updateRect();
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
		// TODO Auto-generated method stub

	}

	@Override
	public void workCreateFigure(Point pos) {
		Shape topShape = this.manager.getTopShape();
		
		topShape.setDimension(new Dimension(pos.x - topShape.getOrigin().x, pos.y - topShape.getOrigin().y));
		topShape.update();
		
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
