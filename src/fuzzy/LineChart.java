package fuzzy;

/* -------------------
 * LineChart.java
 * -------------------
 * (C) Copyright 2002-2005, by Object Refinery Limited.
 *
 */
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

/**
 * A simple demonstration application showing how to create a line chart using
 * data from an {@link XYDataset}.
 * <p>
 * IMPORTANT NOTE: THIS DEMO IS DOCUMENTED IN THE JFREECHART DEVELOPER GUIDE. DO
 * NOT MAKE CHANGES WITHOUT UPDATING THE GUIDE ALSO!!
 */
public class LineChart extends JFrame {
    XYDataset dataset;
    /**
     * Creates a new demo.
     *     
     * @param title the frame title.
     * @param var
     */
    public LineChart(String title, Variabel var) {
        super(title);
        this.dataset = createDataset(var);
        JFreeChart chart = createChart(title, dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * Creates a sample dataset.
     *
     * @return a sample dataset.
     */
    private XYDataset createDataset(Variabel var) {
        int jumlahMember = var.getMember().length;
        XYSeries[]series = new XYSeries[jumlahMember];
        
        for(int i=0;i<jumlahMember;i++){
            series[i] = new XYSeries(var.getMember()[i].getName());
            double selisih = var.getMemberRangeValue(i,1)-var.getMemberRangeValue(i,0);
            for(int j=0;j<var.getMemberRange(i).length;j++){
                double value = 0;
                if(var.getMemberBottom(i)){
                    if(j==0){
                        value = 1;
                        series[i].add(var.getMemberRangeValue(i, j)-selisih,value);
                    }
                }else if(var.getMemberTop(i)){
                    if(j==1){
                        value = 1;
                        series[i].add(var.getMemberRangeValue(i, j)+selisih,value);
                    }
                }else{
                    if(j==var.getMemberRange(i).length-2 || j==1){
                        value = 1;
                    }
                }
                //System.out.println(var.getMemberRangeValue(i, j)+" "+value);
                series[i].add(var.getMemberRangeValue(i, j),value);
            }
        }
        
        XYSeriesCollection dataSeries = new XYSeriesCollection();
        for (XYSeries serie : series) {
            dataSeries.addSeries(serie);
        }
        return dataSeries;
    }

    /**
     * Creates a chart.
     * @param dataset the data for the chart.
     * @return a chart.
     */
    private JFreeChart createChart(String title, XYDataset dataset) {
    // create the chart...
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Diagram "+title, // chart title
                "X", // x axis label
                "Y", // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips
                true // urls
        );
    // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);
    // get a reference to the plot for further customisation...
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        XYLineAndShapeRenderer renderer
                = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setShapesVisible(true);
        renderer.setShapesFilled(true);
        // change the auto tick unit selection to integer units only...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.
        return chart;
    }

    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     * @param title
     * @return A panel.
     */
    public JPanel createDemoPanel(String title) {
        JFreeChart chart = createChart(title, this.dataset);
        return new ChartPanel(chart);
    }
}
