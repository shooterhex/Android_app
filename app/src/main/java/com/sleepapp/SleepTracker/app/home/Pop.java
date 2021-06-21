package com.cachecats.PetPet.app.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.cachecats.PetPet.R;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class Pop extends AppCompatActivity {

    GraphView graphDetail;
    BarGraphSeries<DataPoint> series_detail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);


        graphDetail = findViewById(R.id.graph_detail);

        double y;
        DataPoint[]dp = new DataPoint[30];
        for (int x=0;x<30;x++){
            y = x;
            dp[x] = new DataPoint(x,y);
        }
        series_detail = new BarGraphSeries<>(dp);
        graphDetail.addSeries(series_detail);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout(width,(int)(height*0.5f));
        initGraph();
    }

    public void initGraph(){

        //title
        double maxY_double = series_detail.getHighestValueY();
        double maxX_double = series_detail.getHighestValueX();
        double minY_double = series_detail.getLowestValueY();
        double minX_double = series_detail.getLowestValueX();


        graphDetail.setTitle(String.format("Your Sleep mark: %.2f", maxY_double));
        graphDetail.setTitleTextSize(90);
        graphDetail.setTitleColor(Color.GREEN);

        series_detail.setSpacing(20);
        //series.setDrawValuesOnTop(true);
        series_detail.setValuesOnTopColor(Color.BLACK);


        // data graph line
        graphDetail.getGridLabelRenderer().setVerticalLabelsVisible(false);
        //graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graphDetail.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.NONE );

        graphDetail.getViewport().setYAxisBoundsManual(true);
        graphDetail.getViewport().setMinY(0);
        graphDetail.getViewport().setMaxY(maxY_double);

        graphDetail.getViewport().setXAxisBoundsManual(true);
        graphDetail.getViewport().setMinX(minX_double);
        graphDetail.getViewport().setMaxX(maxX_double);

        // enable scaling and scrolling
        graphDetail.getViewport().setScalable(true);
        //graph.getViewport().setScalableY(true);

        //set label
        graphDetail.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value,boolean isValueX){
                if(isValueX){
                    int minute = ((int)(value))/60;
                    int second = ((int)(value))%60;
                    return String.format("%02d:%02d",minute,second);
                }
                return super.formatLabel(value,isValueX);
            }
        });
        graphDetail.getGridLabelRenderer().setTextSize(25);

        //set legend
        /*
        series.setTitle("Sleep quality");
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setTextSize(40);
        graph.getLegendRenderer().setTextColor(Color.BLACK);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        */

        series_detail.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return getCurrentColor((float)(data.getY()/series_detail.getHighestValueY()));
            }
        });

    }

    private int getCurrentColor(float f) {
        int startColor;
        int endColor;
        float fraction;
        if(f<=0.5){
            startColor = Color.RED;
            endColor = Color.YELLOW;
            fraction = f*2;
        }
        else {
            startColor = Color.YELLOW;
            endColor = Color.GREEN;
            fraction = (f-0.5f)*2;
        }
        int redCurrent;
        int blueCurrent;
        int greenCurrent;
        int alphaCurrent;

        int redStart = Color.red(startColor);
        int blueStart = Color.blue(startColor);
        int greenStart = Color.green(startColor);
        int alphaStart = Color.alpha(startColor);

        int redEnd = Color.red(endColor);
        int blueEnd = Color.blue(endColor);
        int greenEnd = Color.green(endColor);
        int alphaEnd = Color.alpha(endColor);

        int redDifference = redEnd - redStart;
        int blueDifference = blueEnd - blueStart;
        int greenDifference = greenEnd - greenStart;
        int alphaDifference = alphaEnd - alphaStart;

        redCurrent = (int) (redStart + fraction * redDifference);
        blueCurrent = (int) (blueStart + fraction * blueDifference);
        greenCurrent = (int) (greenStart + fraction * greenDifference);
        alphaCurrent = (int) (alphaStart + fraction * alphaDifference);

        return Color.argb(alphaCurrent, redCurrent, greenCurrent, blueCurrent);
    }
}
