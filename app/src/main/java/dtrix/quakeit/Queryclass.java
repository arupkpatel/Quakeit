package dtrix.quakeit;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Soumya on 15-07-2017.
 */

public class Queryclass extends AsyncTask<URL,Void, EarthQuake> {

    private final static String Log_Tag= Queryclass.class.getSimpleName();
    private final static String dataurl = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=";

    @Override
    protected EarthQuake doInBackground(URL... params) {
        return null;
    }

    @Override
    protected void onPostExecute(EarthQuake earthQuake) {
        super.onPostExecute(earthQuake);
    }

    private URL createurl(String string){
        /*Date obj = new Date();
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = dateFormat.format(new Date());*/
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.add(Calendar.DATE,-1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String start = dateFormat.format(cal.getTime());
        cal.add(Calendar.DATE,1);
        String end = dateFormat.format(cal.getTime());

        String strurl = string+start+"&endtime="+end;
        URL rtime=null;
        try {
            rtime = new URL(strurl);
        } catch (MalformedURLException e) {
            Log.e(Log_Tag, "createurl: ",e );
        }
        return rtime;
    }

    private void makehttprequest(URL url) throws IOException {
        String jsonbuffer ="";
        HttpURLConnection urlconnection=null;
        InputStream input=null;
        try {
            urlconnection=(HttpURLConnection) url.openConnection();
            urlconnection.setRequestMethod("GET");
            urlconnection.setReadTimeout(10000);
            urlconnection.setConnectTimeout(15000);
            urlconnection.connect();
            input=urlconnection.getInputStream();
            jsonbuffer=readfromjson();
        } catch (IOException e) {
            Log.e(Log_Tag, "makehttprequest: ", e );
        }finally {
            if(urlconnection!=null)
                urlconnection.disconnect();

            if(input!=null)
                input.close();
        }

    }


}
