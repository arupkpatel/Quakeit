package dtrix.quakeit;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private ListView listView =null;
    private static String str = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&minmagnitude=0&starttime=";
    private final String dataurl = "https://earthquake.usgs.gov/fdsnws/event/1/query?starttime=2017-07-09T14:51:51&endtime=2017-07-09T18:51:51&format=geojson";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new getData().execute();

    }

    private class getData extends AsyncTask<URL,Void,ArrayList<EarthQuake>>{

        @Override
        protected void onPreExecute() {
            ConnectivityManager connectivityManager =(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = info!=null & info.isConnectedOrConnecting();
            if(!isConnected){
                Toast.makeText(getApplicationContext(),"No Connection",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected ArrayList<EarthQuake> doInBackground(URL... params) {
            URL url =null;
            try{
                url = new URL(sdate(str));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String jfile = makerequest(url);
            ArrayList<EarthQuake> list = null;
            try {
                list = getjsonfile(jfile);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Notification
            NotificationCompat.Builder mBuilder =
                    (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setContentTitle("Quakeit")
                            .setContentText("EarthQuake Alert!");

            int setNId=1;
            NotificationManager nmgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            nmgr.notify(setNId,mBuilder.build());

            return list;
        }



        @Override
        protected void onPostExecute(final ArrayList<EarthQuake> quakeArrayList) {
            listView = (ListView) findViewById(R.id.listview);
            listView.setAdapter(new EarthQuakeAdapter(MainActivity.this,R.layout.row2,quakeArrayList));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i =new Intent(view.getContext() ,EarthQuakeDetailAdapter.class);
                    EarthQuake quake = quakeArrayList.get(position);
                    String str[] = {String.valueOf(quake.getMagnitude()),quake.getLocation(), String.valueOf(quake.getTsunami_response()),
                            String.valueOf(quake.getLattitude()), String.valueOf(quake.getLongitude())};
                    i.putExtra("TResponse",str);
                    startActivity(i);

                }
            });
        }

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
                if(urlConnection.getResponseCode()==200) {
                    stream = urlConnection.getInputStream();
                    json = inputString(stream);
                }else{
                    Log.e("Quakeit",urlConnection.getResponseMessage());
                }
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
            if(str==null){
                Log.e("Quakeit","Empty String, can't JSONFile");
            }
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
                int t_response=nobj.getInt("tsunami");
                JSONArray array = jarr.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates");
                double lat = array.getDouble(0);
                double lon = array.getDouble(1);
                EarthQuake earthQuake = new EarthQuake(mag,data,time,t_response,lat,lon);
                quakelist.add(earthQuake);
            }
            return quakelist;
        }

        private String sdate(String string){
            Date date =new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            String sda= string+sdf.format(date);
            return sda;
        }

    }
}
