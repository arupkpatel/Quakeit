package dtrix.quakeit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<EarthQuake>> {

    private ListView listView =null;
    private ProgressBar progressBar=null;
    private TextView tv=null;
//    private boolean isconnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      /*  ConnectivityManager manager =(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        isconnected = info!=null && info.isConnectedOrConnecting();*/
        boolean isconnected = checkconection(getApplicationContext());
        Log.i("check","oc");
        listView = (ListView) findViewById(R.id.listview);
        progressBar= (ProgressBar)findViewById(R.id.pbar);
        tv = (TextView) findViewById(R.id.ltview);
        getSupportLoaderManager().initLoader(1,null,this).forceLoad();
        if(!isconnected)
            tv.setText(R.string.connection);

    }



    @Override
    public Loader<List<EarthQuake>> onCreateLoader(int id, Bundle args) {
        Log.i("check","ocl");

        progressBar.setVisibility(View.VISIBLE);
        return new Earthquakeloader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<List<EarthQuake>> loader, List<EarthQuake> data) {
        Log.i("check","olf");
        if(listView != null) {
            if (data == null) {
                tv.setVisibility(View.VISIBLE);
                listView.setEmptyView(tv);
            }else
                listView.setAdapter(new EarthQuakeAdapter(MainActivity.this,R.layout.row2,data));
            progressBar.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public void onLoaderReset(Loader<List<EarthQuake>> loader) {
        Log.i("check","olr");
        if(listView != null)
            listView.setAdapter(new EarthQuakeAdapter(MainActivity.this,R.layout.row2,new ArrayList<EarthQuake>()));

    }


    private boolean checkconection(Context mcontext){
        ConnectivityManager cm=(ConnectivityManager)mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }
    //innerclass

    /*private class getData extends AsyncTaskLoader<List<EarthQuake>> {

        public getData(Context context) {
            super(context);
        }

        @Override
        public List<EarthQuake> loadInBackground() {
            URL url =null;
            try{
                url = new URL(dataurl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String jfile = makerequest(url);
            ArrayList<EarthQuake> list =null;
            try {
                list = getjsonfile(jfile);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return list;
        }




      *//*  @Override
        protected ArrayList<EarthQuake> doInBackground(URL... params) {
            URL url =null;
            try{
                url = new URL(dataurl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String jfile = makerequest(url);
            ArrayList<EarthQuake> list =null;
            try {
                list = getjsonfile(jfile);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<EarthQuake> quakeArrayList) {

        }*//*

        private String makerequest(URL site){
            InputStream stream =null;
            String json= "";
            HttpURLConnection urlConnection =null;
            try{
                urlConnection = (HttpURLConnection) site.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.connect();
                stream=urlConnection.getInputStream();
                json = inputString(stream);

            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(urlConnection != null)
                    urlConnection.disconnect();
                if(stream!=null)
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            return json;

        }

        private String inputString(InputStream stream) throws IOException {
            StringBuilder output =new StringBuilder("");
            if(stream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(stream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine() ;
                while(line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        private ArrayList<EarthQuake> getjsonfile(String str) throws JSONException {
            ArrayList<EarthQuake> quakelist = new ArrayList<>();
            JSONObject jobj=null;
            JSONArray jarr=null;

            jobj=new JSONObject(str);
            jarr=jobj.getJSONArray("features");
            for(int i=0;i<jarr.length();i++){
                JSONObject nobj = jarr.getJSONObject(i).getJSONObject("properties");
                String data= nobj.getString("place");
                double mag= nobj.getDouble("mag");
                long time =nobj.getLong("time");
                EarthQuake earthQuake = new EarthQuake(mag,data,time);
                quakelist.add(earthQuake);
            }
            return quakelist;
        }
    }*/
}
