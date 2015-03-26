package com.hahattpro.dubaothoitiet;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;

/**
 * Created by haha on 3/26/2015.
 */
public class ForecastFragment extends Fragment {
    public ForecastFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        new ForecastDataFetch().execute("http://api.openweathermap.org/data/2.5/forecast/daily?q=Thanh%20pho%20Ho%20Chi%20Minh&mode=json&units=metric&cnt=16");

        return rootView;
    }


    /**
     * an AsyncTask will fetch forecast data from internet
     * @author Haha TTpro
     * input: the link to json
     * output: a array of String which contain 7 day forecast
     */
    public class ForecastDataFetch extends AsyncTask<String,Void,String[]>
    {
        private String LOG_TAG = ForecastDataFetch.class.getSimpleName();
        @Override
        protected String[] doInBackground(String... params) {

            String JsonString = null;
            BufferedReader reader;

            try {
                URL url = new URL(params[0]);//open url from input outside of this class
                InputStream inputStream = url.openStream();//fetch json
                StringBuffer buffer = new StringBuffer();//string here

                reader = new BufferedReader(new InputStreamReader(inputStream));//reader will read from inputStream

                String tmp; //temp string

                while ((tmp = reader.readLine())!=null)//when reader is succesfully read and put value into tmp
                {
                    buffer.append(tmp);//append tmp into StringBuffer
                }
                JsonString = buffer.toString();// json is here

            JsonParse parse = new JsonParse();
            String[] ret =     parse.getWeatherDataFromJson(JsonString,16);
                Log.i(LOG_TAG,"suceed");
                return ret;
            }
            catch (MalformedURLException e)
            {
                Log.i(LOG_TAG,e.getMessage());
            }
            catch (IOException e)
            {
                Log.i(LOG_TAG,e.getMessage());
            }
            catch (JSONException e)
            {
                Log.i(LOG_TAG,e.getMessage());
            }


            return null;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
            for (int i = 0; i<strings.length;i++)
                Log.i(LOG_TAG,strings[i]);
        }
    }



}

