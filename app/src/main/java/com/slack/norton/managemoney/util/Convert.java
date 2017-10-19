package com.slack.norton.managemoney.util;

import android.util.Log;

/**
 * Created by norton on 08/10/2017.
 */

public class Convert {

    public static String money(int number){
        String str = "";

        if(number >= 1000000){
            int n_tr = number/1000000;
            str += Convert.addPoint(n_tr)+"tr ";
            number =  number%1000000;
        }

        number = number/1000;
        str +=  String.valueOf(number)+"k ";
        return str;
    }

    public static String addPoint(int number){
        String str = String.valueOf(number);
        int len = str.length();
        int counter = 3;
        String result = "";
        while (len - counter >= 0)
        {
            String con =  str.substring(len - counter ,len - counter + 3);
            Log.d("substring", con);
            result = ","+con+result;
            counter+= 3;
        }
        String con = str.substring( 0 , 3 - (counter - len));
        result = con+result;
        if(result.substring(0,1).contains(",")){
            result=result.substring(1);
        }
        return result;
    }
}
