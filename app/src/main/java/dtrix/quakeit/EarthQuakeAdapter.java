package dtrix.quakeit;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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


        holder.textView1.setText(String.valueOf(quake.getMagnitude()));
        holder.textView3.setText(date);
        holder.textView4.setText(time);
		holder.textView2.setText(location[1]);
		holder.textView5.setText(location[0]);

        return convertView;
    }

    private static class EarthQuakeHolder {
        private TextView textView1,textView2,textView3,textView4,textView5;
    }

    private String modifydate(long time){
        Date object = new Date(time);
        SimpleDateFormat dateFormat =new SimpleDateFormat("LLL dd ,yyyy");
        return dateFormat.format(object);
    }

    private String modifytime (long time){
        Date object = new Date(time);
        SimpleDateFormat dateFormat =new SimpleDateFormat("h:m a");
        return dateFormat.format(object);
    }

    private String[] splitter(String string){
        String[] array =null;
        int i =0;
        if(string.contains(", ")){
            i = string.indexOf(",");
            array= new String[]{string.substring(0, i), string.substring(i + 2)};
        }
        else {
            array = new String[]{"", string};
        }
        return array;
    }
}
