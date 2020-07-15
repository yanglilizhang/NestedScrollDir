package com.cn.lenny.androidhighlights.diff;



import androidx.annotation.NonNull;

import com.cn.lenny.androidhighlights.adapter.IElement;
import com.cn.lenny.androidhighlights.bean.ElementRecord;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class IDHelper {
    /**
     * 强制刷新标记
     */
    public static final String FORCE_REFRESH = "force_refresh";
    @NonNull
    public static String getUniqueId(IElement t) {
        String content = t.diffContent();
        if(content == null || content.equals("")){
            return FORCE_REFRESH;
        }else {
            return IDHelper.stringMD5(t.diffContent());
        }
    }

    /**
     * 是否强制刷新
     * @param elementRecord
     * @return
     */
    public static boolean forceRefresh(ElementRecord elementRecord){
        return elementRecord == null || FORCE_REFRESH.equals(elementRecord.getUniqueId());
    }
    /**
     * 获取字符串MD5
     * @param input
     * @return
     */
    public static String stringMD5(String input) {
        if(input!=null){
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                byte[] inputByteArray = input.getBytes();
                messageDigest.update(inputByteArray);
                byte[] resultByteArray = messageDigest.digest();
                return byteArrayToHex(resultByteArray);
            } catch (NoSuchAlgorithmException e) {
            }
        }
        return "";
    }

    private static String byteArrayToHex(byte[] byteArray) {

        char[] hexDigits = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };

        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        return new String(resultCharArray);
    }
}
