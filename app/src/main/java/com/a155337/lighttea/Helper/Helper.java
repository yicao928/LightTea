package com.a155337.lighttea.Helper;

import android.content.Context;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import static android.content.Context.MODE_PRIVATE;
import static com.a155337.lighttea.Activity.MainActivity.memberList;

public class Helper {
    public static void showMessage(String msg, Context context) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static boolean isFloat(String s){
        if(s == null || s.equals(""))
            return false;
        int decimalPoint = 0;
        for(int i = 0; i < s.length(); i++){
            if(!Character.isDigit(s.charAt(i)) && s.charAt(i) != '.'){
                return false;
            }
            else if(s.charAt(i) == '.'){
                decimalPoint++;
                if(decimalPoint > 1)
                    return false;
            }
        }
        return true;
    }

    public static int categoryIndex(String target){
        for(int i = 0; i < Constant.category.length; i++){
            if(Constant.category[i].equals(target))
                return i;
        }
        return -1;
    }
}
