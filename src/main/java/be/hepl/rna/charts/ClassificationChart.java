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

import be.hepl.rna.common.IClassificator;
import be.hepl.rna.common.ILabeledSample;

public class ClassificationChart {

	private String title;
	private XYSeries[] data;
	private XYSeries[] classification;
	private boolean interpreteNoClass = false;
	
	public ClassificationChart(String title, String[] classes, boolean interpreteNoClass) {
		this.title = title;
		this.data = new XYSeries[classes.length];
		this.classification = new XYSeries[classes.length];
		this.interpreteNoClass = interpreteNoClass;
		
		for(int i = 0; i < classes.length; i++) {
			this.data[i] = new XYSeries(classes[i] + "(data)");
			this.classification[i] = new XYSeries(classes[i] + "(model)");
		}
	}
	
	public void setClassificator(IClassificator model, double xMin, double xMax, double xSamplingStep, double yMin, double yMax, double ySamplingStep) {
		for(double x = xMin; x <= xMax; x+= xSamplingStep) {
			for(double y = yMin; y <= yMax; y+= ySamplingStep) {
				
				int classIndex = model.classify(x, y);
				if(interpreteNoClass) {
					classification[classIndex + 1].add(x,y);
				} else if(classIndex >= 0) {
					this.data[classIndex].add(x,y);
				}
			}
		}
	}
	
	public void setData(Iterable<ILabeledSample> data) {
		for(ILabeledSample s : data) {
			double x = s.inputs()[0];
			double y = s.inputs()[1];
			
			double max = Double.MIN_VALUE;
			int maxIndex = -1;
			for(int i = 0; i < s.expectedOutput().length; i++) {
				double elt = s.expectedOutput()[i];
				if(elt >= max /*&& elt > 0.0*/) {
					max = elt;
					maxIndex = i;
				}
			}
			if(interpreteNoClass) {
				this.data[maxIndex+1].add(x,y);
			} else if(maxIndex >= 0) {
				this.data[maxIndex].add(x,y);
			}
		}
	}
	
	public JFrame asJFrame() {
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		for(XYSeries s : this.data) {
			dataset.addSeries(s);
		}
		
		for(XYSeries s : this.classification) {
		 	
			dataset.addSeries(s);
		}
		
		return new Frame(this.title, dataset);
	}
	
	private static class Frame extends JFrame {
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
