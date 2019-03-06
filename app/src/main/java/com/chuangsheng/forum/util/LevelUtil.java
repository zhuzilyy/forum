package com.chuangsheng.forum.util;

import com.chuangsheng.forum.R;

public class LevelUtil {
    public static int  userLevel(String point){
        int intPoint = Integer.parseInt(point);
        if (0<=intPoint && intPoint<=19){
            return R.mipmap.kanong;
        }else if(20<=intPoint && intPoint<=49){
            return R.mipmap.kashi;
        }else if(50<=intPoint && intPoint<=99){
            return R.mipmap.kajun;
        }else if(100<=intPoint && intPoint<=199){
            return R.mipmap.kawang;
        }else if(200<=intPoint && intPoint<=499){
            return R.mipmap.kadi;
        }else{
            return R.mipmap.kajiezhizun;
        }
    }
}
