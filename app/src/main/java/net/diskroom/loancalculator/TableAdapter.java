package net.diskroom.loancalculator;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;

/**
 * Created by yongbaolinux on 2016/11/8.
 */
public class TableAdapter extends BaseAdapter {
    private double[][] data;
    private LayoutInflater inflater;
    private Context mContext;

    public TableAdapter(Context context,double[][] months) {
        this.data = months;
        inflater = LayoutInflater.from(context);
        mContext = context;
    }

    public int getCount(){
        return data.length;
    }

    public double[] getItem(int position){
        return data[position];
    }

    public long getItemId(int position){
        return position;
    }

    public View getView(int position,View view,ViewGroup parent){
        /*ViewHolder holder;
        if(view == null) {
            view = inflater.inflate(R.layout.calculate_data_list_view_row, null);
            TextView rowTerm = (TextView)view.findViewById(R.id.rowTerm);
            TextView rowPerMonthTotal = (TextView)view.findViewById(R.id.rowPerMonthTotal);
            TextView rowPerMonthInterest = (TextView)view.findViewById(R.id.rowPerMonthInterest);
            TextView rowPerMonthPrincipal = (TextView)view.findViewById(R.id.rowPerMonthPrincipal);
            holder = new ViewHolder();
            holder.rowTerm = rowTerm;
            holder.rowPerMonthTotal = rowPerMonthTotal;
            holder.rowPerMonthInterest = rowPerMonthInterest;
            holder.rowPerMonthPrincipal = rowPerMonthPrincipal;
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.rowTerm.setText(String.valueOf((int)getItem(position)[0]));
        //每月应还款项
        holder.rowPerMonthTotal.setText(String.format("%.1f",getItem(position)[1]));
        //每月应还利息
        holder.rowPerMonthInterest.setText(String.format("%.1f",getItem(position)[2]));
        //每月应还本金
        holder.rowPerMonthPrincipal.setText(String.format("%.1f",getItem(position)[3]));
        return view;*/
        TableRow tr = new TableRow(mContext);
        TextView rowTerm = new TextView(mContext);
        TextView rowPerMonthTotal = new TextView(mContext);
        TextView rowPerMonthInterest = new TextView(mContext);
        TextView rowPerMonthPrincipal = new TextView(mContext);
        //rowTerm.setGravity(Gravity.CENTER);
        rowTerm.setText(String.valueOf((int) getItem(position)[0]));
        //rowPerMonthTotal.setGravity(Gravity.CENTER);
        rowPerMonthTotal.setText(String.format("%.1f", getItem(position)[1]));
        //rowPerMonthInterest.setGravity(Gravity.CENTER);
        rowPerMonthInterest.setText(String.format("%.1f", getItem(position)[2]));
        //rowPerMonthPrincipal.setGravity(Gravity.CENTER);
        rowPerMonthPrincipal.setText(String.format("%.1f",getItem(position)[3]));

        tr.addView(rowTerm);
        tr.addView(rowPerMonthTotal);
        tr.addView(rowPerMonthInterest);
        tr.addView(rowPerMonthPrincipal);

        return tr;
    }

    private final class ViewHolder{
        public TextView rowTerm;
        public TextView rowPerMonthTotal;
        public TextView rowPerMonthInterest;
        public TextView rowPerMonthPrincipal;
    }
}
