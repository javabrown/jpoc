package com.jbrown.swing.demo.graph;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PiChartDemo {
	public static void main(String[] args) {
		JFrame frame = new JFrame();

		final int FRAME_WIDTH = 300;
		final int FRAME_HEIGHT = 400;

		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setTitle("BarChart");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		List<Double> values = new ArrayList<Double>();
		values.add(new Double(10));
		values.add(new Double(20));
		values.add(new Double(70));
		
		List<Color> colors = new ArrayList<Color>();
		colors.add(Color.RED);
		colors.add(Color.GREEN);
		colors.add(Color.BLUE);
		
		PieChart component = new PieChart(values, colors);
		frame.add(component);

		frame.setVisible(true);
	}
}

class PieChart extends JPanel {
	enum Type {
		STANDARD, SIMPLE_INDICATOR, GRADED_INDICATOR
	}

	private Type type = null; // the type of pie chart

	private List<Double> values;
	private List<Color> colors;
	private List<Double> gradingValues;
	private List<Color> gradingColors;

	double percent = 0; // percent is used for simple indicator and graded
						// indicator

	public PieChart(int percent) {

		type = Type.SIMPLE_INDICATOR;
		this.percent = percent;
	}

	public PieChart(List<Double> values, List<Color> colors) {
		type = Type.STANDARD;

		this.values = values;
		this.colors = colors;
	}

	public PieChart(int percent, List<Double> gradingValues,
			List<Color> gradingColors) {
		type = Type.GRADED_INDICATOR;

		this.gradingValues = gradingValues;
		this.gradingColors = gradingColors;
		this.percent = percent;
	}

	@Override
	protected void paintComponent(Graphics g) {

		int width = getSize().width;

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
				RenderingHints.VALUE_ANTIALIAS_ON);

		if (type == Type.SIMPLE_INDICATOR) {

			// colours used for simple indicator
			Color backgroundColor = Color.WHITE;
			Color mainColor = Color.BLUE;

			g2d.setColor(backgroundColor);
			g2d.fillOval(0, 0, width, width);
			g2d.setColor(mainColor);
			Double angle = (percent / 100) * 360;
			g2d.fillArc(0, 0, width, width, -270, -angle.intValue());

		} else if (type == Type.STANDARD) {

			int lastPoint = -270;

			for (int i = 0; i < values.size(); i++) {
				g2d.setColor(colors.get(i));

				Double val = values.get(i);
				Double angle = (val / 100) * 360;

				g2d.fillArc(0, 0, width, width, lastPoint, -angle.intValue());
				System.out.println("fill arc " + lastPoint + " "
						+ -angle.intValue());

				lastPoint = lastPoint + -angle.intValue();
			}
		} else if (type == Type.GRADED_INDICATOR) {

			int lastPoint = -270;

			double gradingAccum = 0;

			for (int i = 0; i < gradingValues.size(); i++) {
				g2d.setColor(gradingColors.get(i));
				Double val = gradingValues.get(i);
				gradingAccum = gradingAccum + val;
				Double angle = null;
				/**
				 * * If the sum of the gradings is greater than the percent,
				 * then we want to recalculate * the last wedge, and break out
				 * of drawing.
				 */
				if (gradingAccum > percent) {

					System.out.println("gradingAccum > percent");

					// get the previous accumulated segments. Segments minus
					// last one
					double gradingAccumMinusOneSegment = gradingAccum - val;

					// make an adjusted calculation of the last wedge
					angle = ((percent - gradingAccumMinusOneSegment) / 100) * 360;

					g2d.fillArc(0, 0, width, width, lastPoint,
							-angle.intValue());

					lastPoint = lastPoint + -angle.intValue();

					break;

				} else {

					System.out.println("normal");
					angle = (val / 100) * 360;

					g2d.fillArc(0, 0, width, width, lastPoint,
							-angle.intValue());

					System.out.println("fill arc " + lastPoint + " "
							+ -angle.intValue());

					lastPoint = lastPoint + -angle.intValue();
				}
			}
		}
	}
}