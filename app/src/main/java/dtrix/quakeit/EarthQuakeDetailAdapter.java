package dtrix.quakeit;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry on 8/4/2017.
 */

public class EarthQuakeDetailAdapter extends AppCompatActivity {

    private TextView textView1,textView2,textView3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        textView1 = (TextView)findViewById(R.id.textView8);
        textView2 = (TextView)findViewById(R.id.textView9);
        textView3 = (TextView)findViewById(R.id.textView11);
        Bundle bundle = getIntent().getExtras();
        String[] tres = null;
        if(bundle==null){
            Log.e("Switch","Error");
        }
        tres=bundle.getStringArray("TResponse");
        double mag=0,lat=0,lon=0;
        if(tres!=null) {
            mag = Double.parseDouble(tres[0]);
            textView1.setText(tres[0]);
            textView2.setText(tres[1]);
            int trep = Integer.parseInt(tres[2]);
            if (trep == 0) {
                textView3.setText(R.string.No);
            } else {
                textView3.setText(R.string.Yes);
            }
            lat= Double.parseDouble(tres[3]);
            lon= Double.parseDouble(tres[4]);
        }
        final String str="geo:"+lat+","+lon;
        GradientDrawable magcircle = (GradientDrawable)textView1.getBackground();
        magcircle.setColor(setmagcolor(mag));

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent implicitIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(str));

                if(isIntentAvail(implicitIntent)){
                    startActivity(implicitIntent);
                }else{
                    Toast.makeText(v.getContext(),"No App Available",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private int setmagcolor(double mag){
        int i = (int)Math.floor(mag);
        int id ;
        switch (i){
            case 0:
                id= R.color.zero;
                break;
            case 1:
                id= R.color.one;
                break;
            case 2:
                id= R.color.two;
                break;
            case 3:
                id= R.color.three;
                break;
            case 4:
                id= R.color.four;
                break;
            case 5:
                id= R.color.five;
                break;
            case 6:
                id= R.color.six;
                break;
            case 7:
                id= R.color.seven;
                break;
            case 8:
                id= R.color.eight;
                break;
            case 9:
                id= R.color.nine;
                break;
            default:
                id= R.color.tenplus;
                break;
        }

        return ContextCompat.getColor(getApplicationContext(),id);
    }

    public boolean isIntentAvail(Intent intent){
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent,0);
        if(list.size()>0)
            return true;
        return false;
    }
}
