package dtrix.quakeit;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Soumya on 10-08-2017.
 */

public class Earthquakeloader extends AsyncTaskLoader<List<EarthQuake>> {

    private final String dataurl = "https://earthquake.usgs.gov/fdsnws/event/1/query?starttime=2017-07-09T14:51:51&endtime=2017-07-09T18:51:51&format=geojson";

    public Earthquakeloader(Context context) {
        super(context);
    }

    @Override
    public ArrayList<EarthQuake> loadInBackground() {
        Log.i("check","ocl");
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
            Thread.sleep(2000);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
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
}
