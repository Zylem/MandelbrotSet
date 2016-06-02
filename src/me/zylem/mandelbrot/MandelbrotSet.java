package me.zylem.mandelbrot;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MandelbrotSet extends JPanel {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws IOException {
		JFrame frame = new JFrame("The Mandelbrot Set");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MandelbrotSet instance = new MandelbrotSet();
		frame.add(instance);
		frame.setSize(800, 500);
		frame.setVisible(true);
	}

	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		List<Color> colours = new ArrayList<>(); // colour map
		int brightness = 50; // use bright colours away from 0 and 255.
		for (int r = 0 + brightness; r <= 255 - brightness; r += step(r)) // Different steps give different colour patterns
			for (int g = 0 + brightness; g <= 255 - brightness; g += step(g))
				for (int b = 0 + brightness; b <= 255 - brightness; b += step(b))
					colours.add(new Color(r, g, b));
		setBackground(Color.WHITE);
		for (double x = -2; x <= 2; x += 0.005)
			for (double y = -1; y <= 1; y += 0.005) {
				ComplexNumber z = new ComplexNumber(0, 0);
				ComplexNumber c = new ComplexNumber(x, y);
				int iterations = 0;
				for (iterations = 0; iterations < 1000; iterations++) {
					z.iterate(c);
					if (z.getMagnitude() > 2)
						break;
				}
				if (iterations >= colours.size() - 1)
					iterations = iterations % colours.size(); // if there are more interations than the size of the colour map, take the modulus
				if (z.surpassedRange()) // this is classified as above 2 in the Mandelbrot set
					graphics.setColor(colours.get(iterations));
				else
					graphics.setColor(Color.BLACK); // areas within the Mandelbrot set are filled in black
				graphics.fillRect((int) Math.floor(x * 250) + 500, (int) Math.floor(499 - ((y + 1) * 250)), 2, 2);
			}
	}

	private int step(double current) {
//		 return (int) Math.pow(current, 1.29);
		 return (int) Math.pow(current, 0.8);
//		 return (int) Math.pow(current, 0.6);
//		return (int) Math.pow(current, 1.29);
//		 return 30;
	}

	public class ComplexNumber {

		private double a, b, magnitude;

		public ComplexNumber(double a, double b) {
			this.a = a;
			this.b = b;
			this.magnitude = Math.sqrt(a * a + b * b);
		}

		public String toString() {
			return a + " " + b + "i";
		}

		public double getMagnitude() {
			return magnitude;
		}

		public double getReal() {
			return a;
		}

		public double getImaginary() {
			return b;
		}

		public boolean surpassedRange() {
			return magnitude > 2;
		}

		public void iterate(ComplexNumber c) {
			if (surpassedRange())
				return;
			double aTemp = a * a - b * b + c.getReal(); // temporary variable created to make computation instantaneous 
			this.b = 2 * a * b + c.getImaginary();
			this.a = aTemp;
			this.magnitude = Math.sqrt(a * a + b * b); // modulus on Argand diagram
		}

	}

}