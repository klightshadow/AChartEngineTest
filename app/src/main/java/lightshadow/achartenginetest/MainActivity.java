package lightshadow.achartenginetest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] titles = new String[]{"折線1"}; // 定義折線的名稱 , "折線2"
        List<double[]> x = new ArrayList<double[]>(); // 點的x坐標
        List<double[]> y = new ArrayList<double[]>(); // 點的y坐標
        // 數值X,Y坐標值輸入
        x.add(new double[]{1, 3, 5, 7, 9, 11});
//        x.add(new double[] { 0, 2, 4, 6, 8, 10 });
        y.add(new double[]{3, 14, 8, 22, 16, 18});
//        y.add(new double[] { 20, 18, 15, 12, 10, 8 });
        XYMultipleSeriesDataset dataset = buildDatset(titles, x, y); // 儲存座標值

        int[] colors = new int[]{Color.BLUE};// 折線的顏色 , Color.GREEN
        PointStyle[] styles = new PointStyle[]{PointStyle.CIRCLE, PointStyle.DIAMOND}; // 折線點的形狀
        XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles, true);
        renderer.setClickEnabled(true);
        renderer.setSelectableBuffer(50);

        setChartSettings(renderer, "折線圖展示", "X", "Y", 0, 12, 0, 25, Color.BLACK);// 定義折線圖
        final GraphicalView chart = ChartFactory.getLineChartView(this, dataset, renderer);
        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeriesSelection seriesSelection = chart.getCurrentSeriesAndPoint();
                if (seriesSelection != null) {
                    // display information of the clicked point
                    Toast.makeText(
                            MainActivity.this,
                            "Chart element in series index " + seriesSelection.getSeriesIndex()
                                    + " data point index " + seriesSelection.getPointIndex() + " was clicked"
                                    + " closest point value X=" + seriesSelection.getXValue() + ", Y="
                                    + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_chart);
        linearLayout.addView(chart);
//        setContentView(chart);
    }

    // 定義折線圖名稱
    protected void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle,
                                    String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor) {
        renderer.setChartTitle(title); // 折線圖名稱
        renderer.setChartTitleTextSize(30); // 折線圖名稱字型大小
        renderer.setXTitle(xTitle); // X軸名稱
        renderer.setYTitle(yTitle); // Y軸名稱
        renderer.setPointSize(20); //點大小
        renderer.setMargins(new int[] { 50, 50, 50, 50 }); //Top, Left, Right, Bottom
        renderer.setYLabelsPadding(20);
        renderer.setLegendTextSize(30); //說明字型大小
        renderer.setLabelsTextSize(30); //座標字型大小
        renderer.setAxisTitleTextSize(30); //XY軸字型大小
        renderer.setXAxisMin(xMin); // X軸顯示最小值
        renderer.setXAxisMax(xMax); // X軸顯示最大值
        renderer.setXLabelsColor(Color.BLACK); // X軸線顏色
        renderer.setYAxisMin(yMin); // Y軸顯示最小值
        renderer.setYAxisMax(yMax); // Y軸顯示最大值
        renderer.setAxesColor(axesColor); // 設定坐標軸顏色
        renderer.setYLabelsColor(0, Color.BLACK); // Y軸線顏色
        renderer.setLabelsColor(Color.BLACK); // 設定標籤顏色
        renderer.setMarginsColor(Color.WHITE); // 設定背景顏色
        renderer.setShowGrid(true); // 設定格線
    }

    // 定義折線圖的格式
    private XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles, boolean fill) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        int length = colors.length;
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(colors[i]);
            r.setPointStyle(styles[i]);
            r.setFillPoints(fill);
            renderer.addSeriesRenderer(r); //將座標變成線加入圖中顯示
        }
        return renderer;
    }

    // 資料處理
    private XYMultipleSeriesDataset buildDatset(String[] titles, List<double[]> xValues,
                                                List<double[]> yValues) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        int length = titles.length; // 折線數量
        for (int i = 0; i < length; i++) {
            // XYseries對象,用於提供繪製的點集合的資料
            XYSeries series = new XYSeries(titles[i]); // 依據每條線的名稱新增
            double[] xV = xValues.get(i); // 獲取第i條線的資料
            double[] yV = yValues.get(i);
            int seriesLength = xV.length; // 有幾個點

            for (int k = 0; k < seriesLength; k++) // 每條線裡有幾個點
            {
                series.add(xV[k], yV[k]);
            }
            dataset.addSeries(series);
        }
        return dataset;
    }
}
