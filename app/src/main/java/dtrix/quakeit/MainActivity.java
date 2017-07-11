package dtrix.quakeit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<EarthQuake> quakeArrayList =new ArrayList<>();
        /*quakeArrayList.add(new EarthQuake(1.2f,"India"," 14 march 2017","4:00pm") );
        quakeArrayList.add(new EarthQuake(2.2f,"America"," 16 march 2017","6:00am" ));
        quakeArrayList.add(new EarthQuake(3.2f,"UK"," 18 march 2017","2:00pm" ));
        quakeArrayList.add(new EarthQuake(4.2f,"Russia"," 19 march 2017","8:00am" ));
        quakeArrayList.add(new EarthQuake(5.2f,"Japan"," 20 march 2017","11:00pm" ));
        quakeArrayList.add(new EarthQuake(6.2f,"Somlia"," 24 march 2017","01:00am" ));*/

        EarthQuake quake=null;
        JSONObject object=null;
        JSONArray jsonArray=null;

        try {
            object = new JSONObject(getJSONFile(R.raw.data));
            jsonArray = object.getJSONArray("features");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i=0;i<jsonArray.length();i++){
            JSONObject obj = null;
            String location=null;
            long time=0;
            double mag=0;
            try {
                obj =jsonArray.getJSONObject(i).getJSONObject("properties");
                mag=obj.getDouble("mag");
                DecimalFormat dff = new DecimalFormat("#.#");
                mag= Double.parseDouble(dff.format(mag));
                location = obj.getString("place");
                time=obj.getLong("time");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            quake = new EarthQuake(mag,location,"date",time);
            quakeArrayList.add(quake);

        }


        EarthQuakeAdapter adapter =new EarthQuakeAdapter(getApplicationContext(),R.layout.row2,quakeArrayList);

        listView =(ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);



    }

    private String getJSONFile(int resid){

        InputStream inputStream = null;
        String json =null;

        try{
            inputStream=getResources().openRawResource(resid);
            int size = inputStream.available();
            byte[] buffer =new byte[size];
            inputStream.read(buffer);
            json = new String(buffer,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        return json;
    }
}
