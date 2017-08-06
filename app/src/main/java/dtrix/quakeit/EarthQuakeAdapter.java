package dtrix.quakeit;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Soumya on 09-07-2017.
 */

public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake> {

    private List<EarthQuake> quakeList =null;
    private int rowlayout =0;
    public EarthQuakeAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<EarthQuake> objects) {
        super(context, resource, objects);
        this.quakeList=objects;
        this.rowlayout=resource;
    }

    @Nullable
    @Override
    public EarthQuake getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        EarthQuakeHolder holder =null;

        if(convertView==null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(rowlayout, parent, false);

            holder =new EarthQuakeHolder();

            holder.textView1= (TextView) convertView.findViewById(R.id.textView);
            holder.textView2= (TextView) convertView.findViewById(R.id.textView5);
            holder.textView3= (TextView) convertView.findViewById(R.id.textView6);
            holder.textView4= (TextView) convertView.findViewById(R.id.textView7);
            holder.textView5= (TextView) convertView.findViewById(R.id.textV);

            convertView.setTag(holder);
        }else{
            holder= (EarthQuakeHolder) convertView.getTag();
        }

        EarthQuake quake = quakeList.get(position)  ;

        String date = modifydate(quake.getTime());
        String time = modifytime(quake.getTime());
//        String str = quake.getLocation();
        String[] location = splitter(quake.getLocation());
        double mag = format(quake.getMagnitude());


        holder.textView1.setText(String.valueOf(mag));
        holder.textView3.setText(date);
        holder.textView4.setText(time);
		holder.textView2.setText(location[1]);
		holder.textView5.setText(location[0]);

        GradientDrawable drawable = (GradientDrawable)holder.textView1.getBackground();
        int magcircle = setmagcolor(quake.getMagnitude());
        drawable.setColor(magcircle);

        return convertView;
    }

    private static class EarthQuakeHolder {
        private TextView textView1,textView2,textView3,textView4,textView5;
    }

    private static String modifydate(long time){
        Date object = new Date(time);
        SimpleDateFormat dateFormat =new SimpleDateFormat("LLL dd ,yyyy");
        return dateFormat.format(object);
    }

    private static String modifytime (long time){
        Date object = new Date(time);
        SimpleDateFormat dateFormat =new SimpleDateFormat("hh:mm a");
        return dateFormat.format(object);
    }

    private String[] splitter(String string){
        String[] array =null;
        int i =0;
        if(string.contains("of ")){
            i = string.indexOf("of");
            array= new String[]{string.substring(0, i), string.substring(i + 3)};
        }
        else {
            array = new String[]{"", string};
        }
        return array;
    }

    private double format(double d){
        DecimalFormat format = new DecimalFormat("0.0");
        d= Double.parseDouble(format.format(d));
        return d;
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

        return ContextCompat.getColor(getContext(),id);
    }
}
