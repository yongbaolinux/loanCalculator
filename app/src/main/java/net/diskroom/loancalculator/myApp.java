package net.diskroom.loancalculator;

import android.app.Application;

/**
 * Created by jsb-hdp-0 on 2016/12/21.
 */

public class myApp extends Application {
    private Common common;

    public myApp(){
        common = new Common();
    }
}
