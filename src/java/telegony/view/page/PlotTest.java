package telegony.view.page;

import net.sf.click.chart.Chart;
import net.sf.click.chart.flot.FlotLineChart;
import net.sf.click.chart.flot.FlotSeries;
import net.sf.click.chart.jqplot.JQPlotChart;
import net.sf.click.chart.jqplot.JQPlotDataSet;

/**
 * Тест графиков
 * @author Ivashin Alexey
 */
public class PlotTest extends FramePage {

    public PlotTest(String title) {
        super(title);
//        Chart chart = new FlotLineChart("plotter");
//        FlotSeries series = new FlotSeries("label");
//        series.add(1.0, 1.0);
//        series.add(2.0, 2.0);
//        series.add(3.0, 3.0);
//        series.add(4.0, 4.0);
//        series.add(5.0, 5.0);
//        chart.add(series);
//        JQPlotChart chart = new JQPlotChart("plotter");
        JQPlotChart chart = new JQPlotChart("plotter");
        JQPlotDataSet series = new JQPlotDataSet();
        series.add(1.0, 1.0);
        series.add(2.0, 2.0);
        series.add(3.0, 3.0);
        series.add(4.0, 4.0);
        series.add(5.0, 5.0);
        chart.add(series);
        addControl(chart);
    }

    public PlotTest() {
        this("График");
    }

}
