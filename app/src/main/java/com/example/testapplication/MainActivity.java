package com.example.testapplication;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.DialogInterface;

import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;


import com.example.testapplication.JsonUtils.WeatherJson;
import com.example.testapplication.adapters.ViewPagerAdapter;
import com.example.testapplication.adapters.WeatherRecyclerAdapter;
import com.example.testapplication.fragments.RecyclerViewFragment;
import com.example.testapplication.models.Weather;


import com.google.android.material.appbar.AppBarLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.jacksonandroidnetworking.JacksonParserFactory;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    private Weather todayWeather = new Weather();

    private TextView todayTemperature;
    private TextView todayDescription;
    private TextView todayWind;
    private TextView todayPressure;
    private TextView todayHumidity;
    private TextView todaySunrise;
    private TextView todaySunset;
    private TextView todayUvIndex;
    private TextView lastUpdate;
    private TextView todayIcon;
    private TextView tapGraph;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    private View appView;

    private LocationManager locationManager;
    private ProgressDialog progressDialog;


    private List<Weather> longTermWeather = new ArrayList<>();
    private List<Weather> longTermTodayWeather = new ArrayList<>();
    private List<Weather> longTermTomorrowWeather = new ArrayList<>();

    public String recentCity = "delhi";


    private LinearLayout linearLayoutTapForGraphs;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG,"inside Oncrete");

        appView = findViewById(R.id.viewApp);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        AppBarLayout appBarLayout = findViewById(R.id.appBarLayout);

        progressDialog = new ProgressDialog(MainActivity.this);

        // Load toolbar
      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setPopupTheme(R.style.AppTheme_PopupOverlay);






        // Initialize textboxes
        todayTemperature = (TextView) findViewById(R.id.todayTemperature);
        todayDescription = (TextView) findViewById(R.id.todayDescription);
        todayWind = (TextView) findViewById(R.id.todayWind);
        todayPressure = (TextView) findViewById(R.id.todayPressure);
        todayHumidity = (TextView) findViewById(R.id.todayHumidity);
        todaySunrise = (TextView) findViewById(R.id.todaySunrise);
        todaySunset = (TextView) findViewById(R.id.todaySunset);
        todayUvIndex = (TextView) findViewById(R.id.todayUvIndex);
        lastUpdate = (TextView) findViewById(R.id.lastUpdate);
        todayIcon = (TextView) findViewById(R.id.todayIcon);

        linearLayoutTapForGraphs = findViewById(R.id.linearLayout_tap_for_graphs);
        Typeface weatherFont = Typeface.createFromAsset(this.getAssets(), "fonts/weather.ttf");
        todayIcon.setTypeface(weatherFont);

        // Initialize viewPager
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);



        AndroidNetworking.initialize(getApplicationContext());

        AndroidNetworking.setParserFactory(new JacksonParserFactory());

       LoadWeather(recentCity);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               LoadWeather("asansol");
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                // Only allow pull to refresh when scrolled to top
                swipeRefreshLayout.setEnabled(verticalOffset == 0);
            }
        });

    }

    private void searchCities() {
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setMaxLines(1);
        input.setSingleLine(true);

        TextInputLayout inputLayout = new TextInputLayout(this);
        inputLayout.setPadding(32, 0, 32, 0);
        inputLayout.addView(input);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(this.getString(R.string.search_title));
        alert.setView(inputLayout);

        alert.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String result = input.getText().toString().trim();
                if (!result.isEmpty()) {
                     recentCity=result;
                     LoadWeather(result);
                }
            }
        });
        alert.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Cancelled
                Toast.makeText(MainActivity.this, "Cancelled. default city Delhi,IN", Toast.LENGTH_SHORT).show();
            }
        });
        alert.show();
    }


    private String setWeatherIcon(int actualId, int hourOfDay) {
        int id = actualId / 100;
        String icon = "";
        if (actualId == 800) {
            if (hourOfDay >= 7 && hourOfDay < 20) {
                icon = this.getString(R.string.weather_sunny);
            } else {
                icon = this.getString(R.string.weather_clear_night);
            }
        } else {
            switch (id) {
                case 2:
                    icon = this.getString(R.string.weather_thunder);
                    break;
                case 3:
                    icon = this.getString(R.string.weather_drizzle);
                    break;
                case 7:
                    icon = this.getString(R.string.weather_foggy);
                    break;
                case 8:
                    icon = this.getString(R.string.weather_cloudy);
                    break;
                case 6:
                    icon = this.getString(R.string.weather_snowy);
                    break;
                case 5:
                    icon = this.getString(R.string.weather_rainy);
                    break;
            }
        }
        return icon;
    }

    public WeatherRecyclerAdapter getAdapter(int id) {
        WeatherRecyclerAdapter weatherRecyclerAdapter;
        if (id == 0) {
            weatherRecyclerAdapter = new WeatherRecyclerAdapter(this, longTermTodayWeather);
        } else if (id == 1) {
            weatherRecyclerAdapter = new WeatherRecyclerAdapter(this, longTermTomorrowWeather);
        } else {
            weatherRecyclerAdapter = new WeatherRecyclerAdapter(this, longTermWeather);
        }
        return weatherRecyclerAdapter;
    }



    private void ParseLongTermWeather(WeatherJson user) {

        longTermWeather = new ArrayList<>();
        longTermTodayWeather = new ArrayList<>();
        longTermTomorrowWeather = new ArrayList<>();

        for(int i=0;i<40;i++){
         Weather weather=new Weather();

         weather.setDate(Integer.toString(user.getWList().get(i).getDt()));
         weather.setTemperature(Integer.toString(user.getWList().get(i).getMain().getTemp()));
         weather.setDescription(user.getWList().get(i).getWeather().get(0).getDescription());
         weather.setWind(Integer.toString(user.getWList().get(i).getWind().getSpeed()));
         weather.setPressure(Integer.toString(user.getWList().get(i).getMain().getPressure()));
         weather.setHumidity(Integer.toString(user.getWList().get(i).getMain().getHumidity()));
         String idString=Integer.toString(user.getWList().get(i).getWeather().get(0).getId());
         weather.setId(idString);
         weather.setIcon(setWeatherIcon(Integer.parseInt(idString),Calendar.getInstance().get(Calendar.HOUR_OF_DAY)));

         final String dateMsString= user.getWList().get(i).getDt() + "000";

         Calendar cal=Calendar.getInstance();
         cal.setTimeInMillis(Long.parseLong(dateMsString));

         Calendar today= Calendar.getInstance();
         today.set(Calendar.HOUR_OF_DAY,0);
         today.set(Calendar.MINUTE,0);
         today.set(Calendar.SECOND, 0);
         today.set(Calendar.MILLISECOND, 0);

         Calendar tomorrow = (Calendar) today.clone();
            tomorrow.add(Calendar.DAY_OF_YEAR, 1);

         Calendar later =(Calendar) today.clone();
            later.add(Calendar.DAY_OF_YEAR, 2);


            if (cal.before(tomorrow)) {
                longTermTodayWeather.add(weather);

            } else if (cal.before(later)) {
                longTermTomorrowWeather.add(weather);


            } else {
                longTermWeather.add(weather);

            }

        }
        Log.i(TAG,"today weather :-  " +longTermTodayWeather.size() +"   "+ longTermTodayWeather.get(0).getDate());

        Log.i(TAG,"tommorow weather :-  " +longTermTodayWeather.size() + "   "+ longTermTodayWeather.get(0).getDate());

        Log.i(TAG,"later weather :-  " +longTermWeather.size() + "   "+ longTermWeather.get(0).getDate());

        UpdateLongTermWeather();

    }

    private void UpdateLongTermWeather(){


        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());

        Bundle bundleToday = new Bundle();
        bundleToday.putInt("day", 0);
        RecyclerViewFragment recyclerViewFragmentToday=new RecyclerViewFragment();
        recyclerViewFragmentToday.setArguments(bundleToday);
        viewPagerAdapter.addFragment(recyclerViewFragmentToday,getString(R.string.today));

        Bundle bundleTomorrow = new Bundle();
        bundleTomorrow.putInt("day", 1);
        RecyclerViewFragment recyclerViewFragmentTomorrow = new RecyclerViewFragment();
        recyclerViewFragmentTomorrow.setArguments(bundleTomorrow);
        viewPagerAdapter.addFragment(recyclerViewFragmentTomorrow, getString(R.string.tomorrow));

        Bundle bundle = new Bundle();
        bundle.putInt("day", 2);
        RecyclerViewFragment recyclerViewFragment = new RecyclerViewFragment();
        recyclerViewFragment.setArguments(bundle);
        viewPagerAdapter.addFragment(recyclerViewFragment, getString(R.string.later));

        int currentPage = viewPager.getCurrentItem();

        viewPagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        if (currentPage == 0 && longTermTodayWeather.isEmpty()) {
            currentPage = 1;
        }
        viewPager.setCurrentItem(currentPage, false);


    }



    private void ParseTodayWeather(WeatherJson user) {
        todayWeather.setSunrise(Integer.toString(user.getCity().getSunrise()));
        todayWeather.setSunset(Integer.toString(user.getCity().getSunset()));
        todayWeather.setCity(user.getCity().getName());
        todayWeather.setCountry(user.getCity().getCountry());
        todayWeather.setTemperature(Integer.toString(user.getWList().get(0).getMain().getTemp()));
        todayWeather.setDescription(user.getWList().get(0).getWeather().get(0).getDescription());
        todayWeather.setWind(Integer.toString(user.getWList().get(0).getWind().getSpeed()));
        todayWeather.setWindDirectionDegree((double) user.getWList().get(0).getWind().getDeg());
        todayWeather.setPressure(Integer.toString(user.getWList().get(0).getMain().getPressure()));
        todayWeather.setHumidity(Integer.toString(user.getWList().get(0).getMain().getHumidity()));
        final  String idString = Integer.toString(user.getWList().get(0).getWeather().get(0).getId());
        todayWeather.setId(idString);
        todayWeather.setIcon(setWeatherIcon(Integer.parseInt(idString),Calendar.getInstance().get(Calendar.HOUR_OF_DAY)));

        Log.i(TAG,"details  :-   " +todayWeather.getSunrise() + "  " + todayWeather.getCity() + "  " + todayWeather.getTemperature());
        UpdateTodayUI();
    }

    private void UpdateTodayUI(){
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(getApplicationContext());

        //cityName & Country
        String city = todayWeather.getCity();
        String country = todayWeather.getCountry();
        getSupportActionBar().setTitle(city + (country.isEmpty() ? "" : ", " + country));


        //temperature
        float temperature = Float.parseFloat(todayWeather.getTemperature());
        todayTemperature.setText(new DecimalFormat("0.#").format(temperature) + " " +  "°C");

        //Rain
        //double rain = Double.parseDouble(todayWeather.getRain());

        //description
        todayDescription.setText(todayWeather.getDescription().substring(0,1).toUpperCase() +
                todayWeather.getDescription().substring(1));

        //Wind
        double wind = Double.parseDouble(todayWeather.getWind());
        todayWind.setText(getString(R.string.wind) + ": " + new DecimalFormat("#.0").format(wind)+ " " + "m/s");

        //pressure

        double pressure=Double.parseDouble(todayWeather.getPressure());

        todayPressure.setText(getString(R.string.pressure) + ": " + new DecimalFormat("#.0").format(pressure) + " " +"hPa");

        todayHumidity.setText(getString(R.string.humidity) + ": " + todayWeather.getHumidity() + " %");
        todaySunrise.setText(getString(R.string.sunrise) + ": " + timeFormat.format(todayWeather.getSunrise()));
        todaySunset.setText(getString(R.string.sunset) + ": " + timeFormat.format(todayWeather.getSunset()));
        todayIcon.setText(todayWeather.getIcon());



    }

    private void LoadWeather(String city){



        AndroidNetworking.get("https://api.openweathermap.org/data/2.5/forecast")
                .addQueryParameter("q",city)
                .addQueryParameter("appid","21bfe14b3b73202f68890e993e550e8f")
                .addQueryParameter("units","metric")
                .build()
                .getAsObject(WeatherJson.class, new ParsedRequestListener<WeatherJson>() {
                    @Override
                    public void onResponse(WeatherJson user) {
                        int sunrise= user.getCity().getSunrise();


                        ParseTodayWeather(user);
                        ParseLongTermWeather(user);

                    }
                    @Override
                    public void onError(ANError anError) {
                        // handle error
                        Log.i(TAG,"err  " +anError);
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            LoadWeather("delhi");    //for now it's not detecting current location
            return true;
        }

        if (id == R.id.action_search) {
            searchCities();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}