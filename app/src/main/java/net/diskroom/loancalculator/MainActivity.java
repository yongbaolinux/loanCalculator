package net.diskroom.loancalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;


import static android.view.KeyEvent.KEYCODE_BACK;


public class MainActivity extends BaseActivity {

    private PopupMenu mainMenu;
    private long mClickTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.show_menu) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "【推荐】贷款计算君,一款帮你计算贷款月供的工具。http://www.baidu.com");
            startActivity(Intent.createChooser(shareIntent, "分享到"));
            //showShare();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 连续按两次回退键退出应用
     * @param keyCode   按键代码
     * @param keyEvent
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent){
        if(keyCode == KEYCODE_BACK){
            long currentTime = System.currentTimeMillis();
            if((currentTime - mClickTime) > 2000){
                mClickTime = currentTime;
                Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
            } else {
                finish();
                System.exit(0);
            }
        }
        return true;
    }


}
