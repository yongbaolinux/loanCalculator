package net.diskroom.loancalculator;

import java.util.ArrayList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
///实现从屏幕底部向上滑出一个view
//import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;
////

////AndroidSlidingUp控件
import com.apkfuns.logutils.LogUtils;
import net.diskroom.loancalculator.slidinguppanel.SlidingUpPanelLayout;
import net.diskroom.loancalculator.wheelview.LoopView;
import net.diskroom.loancalculator.wheelview.OnItemSelectedListener;

public class HouseLoanActivity extends AppCompatActivity {
    private RelativeLayout.LayoutParams layoutParams;
    private RelativeLayout wheelviewContainer;
    private SlidingUpPanelLayout mLayout;
    //private Animation myAnimation_Translate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_loan);

        /////////初始化wheelview
        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        wheelviewContainer = (RelativeLayout) findViewById(R.id.wheelviewContainer);
        LoopView loopView = new LoopView(this);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i < 30; i++) {
            list.add(i + " 年");
        }
        //设置是否循环播放
        //loopView.setNotLoop();
        //滚动监听
        loopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                Log.d("debug", "Item " + index);
            }
        });
        //设置原始数据
        loopView.setItems(list);
        //设置初始位置
        loopView.setInitPosition(5);
        //设置字体大小
        loopView.setTextSize(30);
        wheelviewContainer.addView(loopView, layoutParams);
        ///////

        //////点击呼出wheelview面板
        TextView loanTimeInput = (TextView) findViewById(R.id.loanTimeInput);
        loanTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

}
