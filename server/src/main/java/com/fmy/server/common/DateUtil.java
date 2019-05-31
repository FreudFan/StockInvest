package com.fmy.server.common;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil extends DateUtils {
    private static Date date = new Date();
    private static final String[] NUMBERS = new String[]{"〇", "一", "二", "三", "四", "五", "六", "七", "八", "九"};

    private DateUtil() {
    }

    public static Date getNextWeekMonday() {
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(5, getMondayPlus() + 7);
        Date monday = currentDate.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return sdf.parse(sdf.format(monday));
        } catch (ParseException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static Date getNextWeekSunday() {
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(5, getMondayPlus() + 7 + 6);
        Date sunday = currentDate.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return sdf.parse(sdf.format(sunday));
        } catch (ParseException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        int dayOfWeek = cd.get(7) - 1;
        return dayOfWeek == 1 ? 0 : 1 - dayOfWeek;
    }

    public static Date getQuarterDate(int year, int quarter) {
        Calendar cldr = Calendar.getInstance();
        cldr.set(2, quarter * 3 - 1);
        cldr.set(1, year);
        switch(quarter) {
            case 1:
                cldr.set(5, 31);
                break;
            case 2:
                cldr.set(5, 30);
                break;
            case 3:
                cldr.set(5, 30);
                break;
            case 4:
                cldr.set(5, 31);
        }

        return DateUtils.truncate(cldr.getTime(), 5);
    }

    public static String toChinese(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str = format.format(date);
        StringBuffer sb = new StringBuffer();
        sb.append(getSplitDateStr(str, 0)).append("年").append(getSplitDateStr(str, 1)).append("月").append(getSplitDateStr(str, 2)).append("日");
        return sb.toString();
    }

    public static String toChinese(String str) {
        StringBuffer sb = new StringBuffer();
        sb.append(getSplitDateStr(str, 0)).append("年").append(getSplitDateStr(str, 1)).append("月").append(getSplitDateStr(str, 2)).append("日");
        return sb.toString();
    }

    public static String getSplitDateStr(String str, int unit) {
        String[] DateStr = str.split("-");
        if (unit > DateStr.length) {
            unit = 0;
        }

        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < DateStr[unit].length(); ++i) {
            if ((unit == 1 || unit == 2) && Integer.valueOf(DateStr[unit]) > 9) {
                sb.append(convertNum(DateStr[unit].substring(0, 1))).append("十").append(convertNum(DateStr[unit].substring(1, 2)));
                break;
            }

            sb.append(convertNum(DateStr[unit].substring(i, i + 1)));
        }

        return unit != 1 && unit != 2 ? sb.toString() : sb.toString().replaceAll("^一", "").replace("〇", "");
    }

    private static String convertNum(String str) {
        return NUMBERS[Integer.valueOf(str)];
    }

    public static void main(String[] args) {
        System.out.println(toChinese("2012-10-24"));
    }

    public static String getQuarter(String month) {
        String quarter = "";
        if (!"01".equals(month) && !"02".equals(month) && !"03".equals(month)) {
            if (!"04".equals(month) && !"05".equals(month) && !"06".equals(month)) {
                if (!"07".equals(month) && !"08".equals(month) && !"09".equals(month)) {
                    if ("10".equals(month) || "11".equals(month) || "12".equals(month)) {
                        quarter = "4";
                    }
                } else {
                    quarter = "3";
                }
            } else {
                quarter = "2";
            }
        } else {
            quarter = "1";
        }

        return quarter;
    }

    public static Date clearTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            date = format.parse(format.format(date));
        } catch (ParseException var3) {
        }

        return date;
    }

    public static int getDateField(Date date, int field) {
        Calendar cldr = Calendar.getInstance();
        cldr.setTime(date);
        return cldr.get(field);
    }
}
