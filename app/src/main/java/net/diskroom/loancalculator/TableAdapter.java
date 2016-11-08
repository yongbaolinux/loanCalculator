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
    private String[][] list;
    private LayoutInflater inflater;

    public void TableAdapter(Context context,String[][] list){
        this.list = list;
        this.inflater = inflater;
    }

    public int getCount(){
        return list.length;
    }

    public String[] getItem(int position){
        return list[position];
    }

    public long getItemId(int position){
        return position;
    }


    public View getView(int position,View view,ViewGroup parent){
        view = inflater.inflate(R.layout.calculate_data_list_view_row,null);
        TextView rowTerm = (TextView)view.findViewById(R.id.rowTerm);
        rowTerm.setText(getItem(position)[0]);
        TextView rowPerMonthTotal = (TextView)view.findViewById(R.id.rowPerMonthTotal);
        rowPerMonthTotal.setText(getItem(position)[1]);
        TextView rowPerMonthInterest = (TextView)view.findViewById(R.id.rowPerMonthInterest);
        rowPerMonthTotal.setText(getItem(position)[2]);

        return view;
    }
}
