package com.example.testapplication.adapters;

import android.content.Context;


import android.graphics.Typeface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


import com.example.testapplication.R;
import com.example.testapplication.models.Weather;
import com.example.testapplication.models.WeatherViewHolder;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.List;
import java.util.TimeZone;




public class WeatherRecyclerAdapter extends RecyclerView.Adapter<WeatherViewHolder> {
    private List<Weather> itemList;
    private Context context;

    public WeatherRecyclerAdapter(Context context, List<Weather> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);

        WeatherViewHolder viewHolder = new WeatherViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder customViewHolder, int i) {
        Weather weatherItem = itemList.get(i);


        // Temperature
        float temperature = Float.parseFloat(weatherItem.getTemperature());

            temperature = Math.round(temperature);


        // Rain
        //double rain = Double.parseDouble(weatherItem.getRain());





        // Pressure
        double pressure = (float) Double.parseDouble(weatherItem.getPressure());

        TimeZone tz = TimeZone.getDefault();
        String dateString;
        try {
            SimpleDateFormat resultFormat = new SimpleDateFormat("E dd.MM.yyyy - HH:mm");
            resultFormat.setTimeZone(tz);
            dateString = resultFormat.format(weatherItem.getDate());
        } catch (IllegalArgumentException e) {
            dateString = context.getResources().getString(R.string.error_dateFormat);
        }



        customViewHolder.itemDate.setText(dateString);
        customViewHolder.itemTemperature.setText(new DecimalFormat("0.#").format(temperature) + " " +  "°C");

       //description
        customViewHolder.itemDescription.setText(weatherItem.getDescription().substring(0,1).toUpperCase() +
                weatherItem.getDescription().substring(1));

        //icon
        Typeface weatherFont = Typeface.createFromAsset(context.getAssets(), "fonts/weather.ttf");
        customViewHolder.itemIcon.setTypeface(weatherFont);
        customViewHolder.itemIcon.setText(weatherItem.getIcon());

        //wind
        double wind = Double.parseDouble(weatherItem.getWind());
        customViewHolder.itemyWind.setText(context.getString(R.string.wind) + ": " + new DecimalFormat("#.0").format(wind)+ " " + "m/s");


        customViewHolder.itemPressure.setText(context.getString(R.string.pressure) + ": " + new DecimalFormat("0.0").format(pressure) + " " + "hPa");
        customViewHolder.itemHumidity.setText(context.getString(R.string.humidity) + ": " + weatherItem.getHumidity() + " %");
    }

    @Override
    public int getItemCount() {
        return (null != itemList ? itemList.size() : 0);
    }
}
