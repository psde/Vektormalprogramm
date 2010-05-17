package de.hska.faki.app;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public final class IOHandler {

	public void write(ArrayList<Shape> shapes, String file) {
		try {
			FileOutputStream out = new FileOutputStream(file);
			ObjectOutputStream str = new ObjectOutputStream(out);

			for (Shape curShape : shapes) {
				str.writeObject(curShape);
			}

			out.close();
			str.close();
		}
		catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
	}


	public ArrayList<Shape> read(String file) {
		ArrayList<Shape> shapes = new ArrayList<Shape>();

		FileInputStream in = null;
		ObjectInputStream str = null;
		try {
			in = new FileInputStream(file);
			str = new ObjectInputStream(in);

			while (str != null) {
				shapes.add((Shape) str.readObject());
			}

			in.close();
			str.close();

			return shapes;
		}
		catch (IOException ex) {
			if (shapes.size() == 0) {
				System.err.println(ex.getMessage());
			}
			else {
				return shapes;
			}
		}
		catch (ClassNotFoundException ex1) {
			System.err.println(ex1.getMessage());
		}
		finally {
			try {
				if (in != null) {
					in.close();
				}
			}
			catch (IOException e) {
				System.err.println(e.getMessage());
			}
			finally {
				try {
					if (str != null) {
						str.close();
					}
				}
				catch (IOException e) {
					System.err.println(e.getMessage());
				}
			}
		}
		return null;
	}
}
