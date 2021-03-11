package be.hepl.rna.charts;

import java.awt.Color;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import be.hepl.rna.common.ILabeledSample;

public class RegressionChart {

	private String title;
	private XYSeries data = new XYSeries("Data");
	private XYSeries regression = new XYSeries("Regression");
	
	public RegressionChart(String title) {
		this.title = title;
	}
	
	public JFrame asJFrame() {
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(data);
		dataset.addSeries(regression);
		return new Frame(this.title, dataset);
	}
	
	public void setData(Iterable<ILabeledSample> data) {
		
	}
	
	public static class Frame extends JFrame {
		private static final long serialVersionUID = 5580874270422611255L;

		public Frame(String title, XYDataset dataset) {
			super(title);

			// Create chart
			JFreeChart chart = ChartFactory.createScatterPlot(this.getTitle(), "X-Axis", "Y-Axis", dataset,
					PlotOrientation.HORIZONTAL, true, true, true);

			// Changes background color
			XYPlot plot = (XYPlot) chart.getPlot();
			plot.setBackgroundPaint(new Color(240, 255, 240));

			// Create Panel
			ChartPanel panel = new ChartPanel(chart);
			setContentPane(panel);
		}
	}
}
