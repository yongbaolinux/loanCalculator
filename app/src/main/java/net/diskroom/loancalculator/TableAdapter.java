package net.diskroom.loancalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


/**
 * Created by yongbaolinux on 2016/11/8.
 */
public class TableAdapter extends BaseAdapter {
    private double[][] data;
    private LayoutInflater inflater;

    public TableAdapter(Context context,double[][] months) {
        this.data = months;
        inflater = LayoutInflater.from(context);
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
        view = inflater.inflate(R.layout.calculate_data_list_view_row,null);
        TextView rowTerm = (TextView)view.findViewById(R.id.rowTerm);
        rowTerm.setText(String.valueOf(getItem(position)[0]));
        //每月应还款项
        TextView rowPerMonthTotal = (TextView)view.findViewById(R.id.rowPerMonthTotal);
        rowPerMonthTotal.setText(String.valueOf(getItem(position)[1]));
        //每月应还利息
        TextView rowPerMonthInterest = (TextView)view.findViewById(R.id.rowPerMonthInterest);
        rowPerMonthInterest.setText(String.valueOf(getItem(position)[2]));
        //每月应还本金
        TextView rowPerMonthPrincipal = (TextView)view.findViewById(R.id.rowPerMonthPrincipal);
        rowPerMonthPrincipal.setText(String.valueOf(getItem(position)[3]));
        return view;
    }
}
