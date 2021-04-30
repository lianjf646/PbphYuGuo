package com.pbph.yuguo.util;

import android.app.Activity;
import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AMUtils {

    /**
     * 手机号正则表达式
     **/
//    public final static String MOBLIE_PHONE_PATTERN = "^((13[0-9])|(15[0-9])|(18[0-9])|(14[7])|(17[0|6|7|8]))\\d{8}$";
    public final static String MOBLIE_PHONE_PATTERN = "^1[3-9]\\d{9}$";
    public final static String URL_PATTERN = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
    public final static String PUBLIC_NUMBER_PATTERN = "^[\\u4e00-\\u9fa5_a-zA-Z0-9]{2,50}$";
    public final static String CHINESE_PATTERN = "^[\\u4e00-\\u9fa5]$";
    public final static String NUMBER_PATTERN = "^[1-9]\\d*$";
    public final static String LENGTH_PATTERN = "^.{10,50}$";
    public final static String PASSWORD_LENGTH_PATTERN = "^.{6,20}$";
    public final static String WORD_PATTERN = "[\\u4e00-\\u9fa5]+";
    public final static String HIDE_RECOMMENDCODE_PATTERN = "(\\d{1})\\d+(\\d{2})";

//    https://www.iesdouyin.com/share/video/(\\d+)\\S+
//    https://m.weibo.cn/status/(\\d+)\\S+
//    https://m.weibo.cn/p/(\\d+?)

    /**
     * 通过正则验证是否是合法手机号码
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isMobile(String phoneNumber) {
        Pattern p = Pattern.compile(MOBLIE_PHONE_PATTERN);
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    public static boolean isPassword(String password) {
        Pattern p = Pattern.compile(PASSWORD_LENGTH_PATTERN);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public static boolean isChinese(String content) {
        Pattern p = Pattern.compile(WORD_PATTERN);
        Matcher m = p.matcher(content);
        return m.find();
    }

    public static String readFileFromAsset2String(Context context, String fileName) {

        try {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    context.getAssets().open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param data      数据源
     * @param front     左边显示几位
     * @param end       右边显示几位
     * @param hideCount 隐藏多少位
     * @return
     */
    public static String hideText(String data, int front, int end, int hideCount) {
        if (TextUtils.isEmpty(data)) {
            return "";
        }
        String frontStr = data.substring(0, front);
        int endIndex = data.length() - end;
        String endStr = data.substring(endIndex <= 0 ? 0 : endIndex, data.length());

        return String.format(getFormatString(hideCount), frontStr, endStr);
    }

    private static String getFormatString(int hideCount) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("%s");
        for (int i = 0; i < hideCount; i++) {
            stringBuilder.append("*");
        }
        stringBuilder.append("%s");
        return stringBuilder.toString();
    }

    public static String[] hideRecommendCode(String withinText) {
        String code[] = null;
        Pattern pattern = Pattern.compile(HIDE_RECOMMENDCODE_PATTERN);
        Matcher matcher = pattern.matcher(withinText);
        if (matcher.find()) {
            matcher.reset();
            code[0] = matcher.group(0);
            code[1] = matcher.group(1);
        }
        return code;
    }
//    /**
//     * 通过正则验证是否是合法手机号码
//     *
//     * @param yzmStr
//     * @return
//     */
//    public static boolean isYZm(String yzmStr) {
//        if (StringUtils.isEmpty(yzmStr)) return false;
//        if (yzmStr.length() != 6) return false;
//        return StringUtils.isNumber(yzmStr);
//    }


    public static void onInactive(Context context, EditText et) {

        if (et == null)
            return;
        et.clearFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    public static void onInactive(Context context) {
        if (null == context) {
            return;
        }
        Activity activity = (Activity) context;
        if (null == activity.getCurrentFocus()) {
            return;
        }
        if (null == activity.getCurrentFocus().getApplicationWindowToken()) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

//    public static String getCode(String content) throws Exception {
//        Pattern pattern = Pattern.compile(patternString);
//        Matcher matcher = pattern.matcher(content);
//        if (!matcher.find()) {
//            return "";
//        }
//        String matcherString = matcher.group(0);
//        Log.e("getCode", "getCode = " + matcherString);
//        return matcherString;
//    }

    /**
     * @param context
     * @param et
     */
    public static void onActive(Context context, EditText et) {
        if (et == null)
            return;

        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, 0);

    }

    /**
     * 通过验证
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    /**
     * 验证editText里是否有表情
     *
     * @param content
     * @return
     */

    public static boolean hasEmoji(String content) {

        Pattern pattern = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {

            return true;
        }
        return false;
    }


    //验证身份证号码
    public static boolean idCardNumber(String number) {
        String rgx = "^\\d{15}|^\\d{17}([0-9]|X|x)$";

        return isCorrect(rgx, number);
    }

    //正则验证
    public static boolean isCorrect(String rgx, String res) {
//        Log.e("isCorrect",rgx+"   "+res);
        Pattern p = Pattern.compile(rgx);

        Matcher m = p.matcher(res);
//        Log.e("isCorrect","   "+m.matches());
        return m.matches();
    }

    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static boolean writeData2File(InputStream is, File file, long fileSize) {
        OutputStream os = null;
        try {

            if (file == null) return false;
            file.getParentFile().mkdirs();
            if (file.exists()) file.delete();


            long readLen = 0;

            os = new FileOutputStream(file);
            byte[] fileReader = new byte[4096];
            int read;
            while ((read = is.read(fileReader)) != -1) {

                os.write(fileReader, 0, read);

                readLen += read;

            }

            os.flush();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static String millisecond2otherTimeUnitStr(long msec) {

        long secs = msec / 1000;
        //<1min
        if (secs < 60) return "刚刚";

        long mins = secs / 60;
        //<1hour
        if (mins < 60) return mins + "分钟前";

        long hours = mins / 60;
        //<1 day
        if (hours < 24) return hours + "小时前";

        long days = hours / 24;
        //<1 month
        if (days < 30) return days + "天前";

        long months = days / 30;
        //<1 year
        if (months < 12) return months + "月前";

        long years = months / 12;
        return years + "年前";
    }

    /**
     * 过滤掉常见特殊字符,常见的表情
     */
    public static void setEtFilter(EditText et,int textNum) {
        if (et == null) {
            return;
        }
        //表情过滤器
        InputFilter emojiFilter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                       int dstart, int dend) {
                Pattern emoji = Pattern.compile(
                        "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                        Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    return "";
                }
                return null;
            }
        };
        //特殊字符过滤器
        InputFilter specialCharFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String regexStr = "[`~\\[\\].<>/]";
//                String regexStr = "[`~@#$%^&*()+=|{}':;'\\[\\].<>/~！@#￥%……&*（）——+|{}【】‘；：”“’。、？]";
                Pattern pattern = Pattern.compile(regexStr);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.matches()) {
                    return "";
                } else {
                    return null;
                }

            }
        };

        et.setFilters(new InputFilter[]{emojiFilter, specialCharFilter,new InputFilter.LengthFilter(textNum)});
    }
}
