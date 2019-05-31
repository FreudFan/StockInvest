package com.fmy.server.common;

import com.sun.crypto.provider.SunJCE;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil extends StringUtils {

    private static Logger logger = LoggerFactory.getLogger(StringUtil.class);

    public StringUtil() {
    }

    public static boolean isOverLength(String s) {
        return s != null && s.trim().length() > 20 ? Boolean.TRUE : Boolean.FALSE;
    }

    public static String getElideString(String s) {
        return s.trim().substring(0, 18).concat("...");
    }

    public static String formatListToString(List list) {
        if (list == null) {
            return null;
        } else if (list.size() == 0) {
            return "";
        } else {
            StringBuffer buffer = new StringBuffer();
            Iterator i$ = list.iterator();

            while(i$.hasNext()) {
                Object obj = i$.next();
                buffer.append(",").append(obj == null ? "" : obj.toString().trim());
            }

            if (buffer.length() > 0) {
                buffer.deleteCharAt(0);
            }

            return buffer.toString();
        }
    }

    public static List<String> formatStringToList(String s, String regex) {
        List<String> rstList = new ArrayList();
        if (isStrEmpty(regex)) {
            regex = ",";
        }

        if (isStrEmpty(s)) {
            return rstList;
        } else {
            String[] array = s.split(regex);
            String[] arr$ = array;
            int len$ = array.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String str = arr$[i$];
                rstList.add(str);
            }

            return rstList;
        }
    }

    public static List<String> formatStringToListByComma(String s) {
        List<String> rstList = new ArrayList();
        getListElementByString(rstList, s);
        return rstList;
    }

    private static void getListElementByString(List list, String s) {
        if (isStrEmpty(s)) {
            list.add("");
        } else if (s.indexOf(",") == -1) {
            list.add(s);
        } else {
            String temp = s.substring(0, s.indexOf(","));
            list.add(isStrEmpty(temp) ? "" : temp);
            getListElementByString(list, s.substring(s.indexOf(",") + 1));
        }

    }

    public static String getTrimedStringBySpilth(String str1, String str2) {
        StringBuffer rst = new StringBuffer();
        List l = stringToArrayList(str1, ",");

        while(l.contains(str2)) {
            l.remove(str2);
        }

        int v = l.size();

        for(int i = 0; i < v; ++i) {
            rst.append(l.get(i)).append(",");
        }

        return getTrimedStringByComma(rst.toString()).toString();
    }

    public static StringBuffer getTrimedStringBufferBySpilth(StringBuffer str1, String str2) {
        return new StringBuffer(getTrimedStringBySpilth(str1.toString(), str2));
    }

    public static String getTrimedStringByRepeatedStr(String str1, String str2, StringBuffer str) {
        String rst = "";
        if (str1 != null && str2 != null) {
            int l = str2.length();
            int k = str1.length();
            int m = str1.indexOf(str2);
            String str3 = new String();
            if (k > m + 2 * l) {
                str3 = str1.substring(m + l, m + 2 * l);
            }

            if (m > 0) {
                if (str3.equals(str2)) {
                    str.append(str1.substring(0, m + l));
                    str1 = str1.substring(m + l, k);
                } else {
                    str.append(str1.substring(0, m + l)).append(str3);
                    if (m + l + 1 <= k) {
                        str1 = str1.substring(m + l, k);
                    } else {
                        str1 = "";
                    }
                }

                return getTrimedStringByRepeatedStr(str1, str2, str);
            }

            if (m == 0) {
                if (str3.equals(str2)) {
                    str1 = str1.substring(m + l, k);
                    if (str.length() == 0) {
                        str.append(str2);
                    } else if (str.lastIndexOf(str2) != str.length() - l) {
                        str.append(str2);
                    }
                } else if (str.lastIndexOf(str2) == str.length() - l) {
                    str1 = str1.substring(m + l, k);
                } else {
                    str.append(str2).append(str3);
                    str1 = str1.substring(m + l, k);
                }

                return getTrimedStringByRepeatedStr(str1, str2, str);
            }

            str.append(str1);
        }

        if (str != null) {
            rst = str.toString();
        }

        return rst;
    }

    public static StringBuffer getTrimedStringByComma(String str1) {
        StringBuffer rst = new StringBuffer();
        if (str1 != null) {
            int j = str1.indexOf(",");
            if (j == 0) {
                str1 = str1.substring(1, str1.length());
            }

            j = str1.lastIndexOf(",");
            if (j == str1.length() - 1 && str1.length() - 1 >= 0) {
                str1 = str1.substring(0, str1.length() - 1);
            }

            rst.append(str1);
        }

        return rst;
    }

    public static List compareList(String str1, String str2) {
        List rst = new ArrayList();
        if (str1 != null && (str1.equals("null") || str1.equals(""))) {
            str1 = null;
        }

        if (str2 != null && (str2.equals("null") || str2.equals(""))) {
            str2 = null;
        }

        if (str1 == null) {
            rst.add((Object)null);
            rst.add(str2);
        } else if (str2 == null) {
            rst.add(str1);
            rst.add((Object)null);
        } else {
            StringBuffer rst0 = new StringBuffer();
            StringBuffer rst1 = new StringBuffer();
            List lst1 = stringToArrayList(str1, ",");
            List lst2 = stringToArrayList(str2, ",");
            int j = lst1.size();

            int i;
            String s;
            for(i = 0; i < j; ++i) {
                s = ((String)lst1.get(i)).trim();
                if (!lst2.contains(s)) {
                    rst0.append(s).append(",");
                }
            }

            j = lst2.size();

            for(i = 0; i < j; ++i) {
                s = ((String)lst2.get(i)).trim();
                if (!lst1.contains(s)) {
                    rst1.append(s).append(",");
                }
            }

            rst0 = getTrimedStringByComma(rst0.toString());
            rst1 = getTrimedStringByComma(rst1.toString());
            if (rst0.length() == 0) {
                rst.add((Object)null);
            } else {
                rst.add(rst0.toString());
            }

            if (rst1.length() == 0) {
                rst.add((Object)null);
            } else {
                rst.add(rst1.toString());
            }
        }

        return rst;
    }

    public static boolean isUnixOS(String fileName) {
        if (!isStrEmpty(fileName)) {
            String separator = fileName.substring(0, 1);
            return "/".equals(separator);
        } else {
            return false;
        }
    }

    public static String getRandomLengthNum(int length) {
        StringBuffer str = new StringBuffer();
        Random random = new Random();

        for(int i = 0; i < length; ++i) {
            str.append(Math.abs(random.nextInt(10)));
        }

        return str.toString();
    }

    public static boolean isStrEmpty(String str) {
        return StringUtils.isEmpty(str);
    }

    public static boolean isStrTrimEmpty(String str) {
        return str == null || str.trim().equals("");
    }

    public static List stringToArrayList(String str, String delim) {
        List list = new ArrayList();
        if (!isStrEmpty(str) && delim != null) {
            if (str.endsWith(delim)) {
                str = str.substring(0, str.length() - 1);
            }

            int index = str.indexOf(delim);
            if (index == -1) {
                list.add(str);
                return list;
            }

            while(index != -1) {
                String temp = str.substring(0, index);
                if (temp != null) {
                    if (temp.equals(delim)) {
                        list.add((Object)null);
                        index -= delim.length();
                    } else {
                        list.add(temp);
                    }

                    str = str.substring(index + 1);
                    index = str.indexOf(delim);
                } else {
                    ++index;
                }
            }

            list.add(str.trim());
        }

        return list;
    }

    public static String parseChinese(String input) throws UnsupportedEncodingException {
        if (input != null && !input.equals("")) {
            Object var1 = null;

            byte[] temp;
            try {
                temp = input.getBytes("ISO-8859-1");
            } catch (UnsupportedEncodingException var3) {
                throw new UnsupportedEncodingException(var3.getMessage());
            }

            return temp == null ? "" : new String(temp, "GBK");
        } else {
            return "";
        }
    }

    public static String[] stringToStringArray(String str, String delim) {
        List list = stringToArrayList(str, delim);
        Object[] objectStr = list.toArray();
        int len = objectStr.length;
        String[] strAarray = new String[len];
        System.arraycopy(objectStr, 0, strAarray, 0, len);
        return strAarray;
    }

    public static List compareString(String oStr, String nStr) {
        List rst = new ArrayList();
        StringBuffer rst0 = new StringBuffer();
        StringBuffer rst1 = new StringBuffer();
        if (oStr != null && nStr != null) {
            List ol = stringToArrayList(oStr, ",");
            List nl = stringToArrayList(nStr, ",");
            int ov = ol.size();
            int nv = nl.size();
            int i;
            String s;
            if (oStr.indexOf(" ") >= 0) {
                for(i = 0; i < ov; ++i) {
                    s = (String)ol.get(i);
                    s = s.trim();
                    ol.set(i, s);
                }
            }

            if (nStr.indexOf(" ") >= 0) {
                for(i = 0; i < nv; ++i) {
                    s = (String)nl.get(i);
                    s = s.trim();
                    nl.set(i, s);
                }
            }

            for(i = 0; i < ol.size(); ++i) {
                s = (String)ol.get(i);
                if (nl.contains(s)) {
                    nl.remove(s);
                    ol.remove(s);
                    --i;
                } else {
                    rst0.append(s).append(",");
                }
            }

            for(i = 0; i < nl.size(); ++i) {
                s = (String)nl.get(i);
                rst1.append(s).append(",");
            }

            rst0 = getTrimedStringByComma(rst0.toString());
            rst1 = getTrimedStringByComma(rst1.toString());
        }

        rst.add(rst0.toString());
        rst.add(rst1.toString());
        return rst;
    }

    public static String getDcolumnString(List fieldList) {
        StringBuffer rst = new StringBuffer();
        int fv = fieldList.size();

        for(int i = 0; i < fv; ++i) {
            String field = (String)fieldList.get(i);
            rst.append(field + " AS F" + i);
            if (i < fv - 1) {
                rst.append(",");
            }
        }

        return rst.toString();
    }

    public static String getRandomNumAndChar(int length) {
        String rst = "";
        Random random = new Random();
        String str1 = "0123456789";
        String str2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int start1 = random.nextInt(str1.length());
        int start2 = random.nextInt(str2.length());
        str1.substring(start1, start1 + 1);
        str2.substring(start2, start2 + 1);

        for(int i = 0; i < length; ++i) {
            start1 = random.nextInt(str1.length());
            start2 = random.nextInt(str2.length());
            String c1 = str1.substring(start1, start1 + 1);
            String c2 = str2.substring(start2, start2 + 1);
            rst = rst + c1;
            if (rst.length() >= length) {
                break;
            }

            rst = rst + c2;
            if (rst.length() >= length) {
                break;
            }
        }

        return rst;
    }

    public static String getEditedStr(String s) {
        StringBuffer rst = new StringBuffer();
        List l = stringToArrayList(s, ",");
        int v = l.size();
        String temp = null;

        for(int i = 0; i < v; ++i) {
            temp = (String)l.get(i);
            if (temp.indexOf("null") < 0 && !temp.endsWith("=") && temp.indexOf("=未知") <= 0) {
                rst.append(temp).append(",");
            }
        }

        rst = getTrimedStringByComma(rst.toString());
        return rst.toString();
    }

    public static String formateStringArrayToString(String[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return null;
        } else {
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < array.length; ++i) {
                sb.append("," + array[i]);
            }

            return sb.substring(1);
        }
    }

    public static String valueOf(Object obj) {
        return obj == null ? "" : String.valueOf(obj);
    }

    public static boolean hasContain(String[] array, String s) {
        if (isStrEmpty(s)) {
            return Boolean.FALSE;
        } else if (array != null && array.length != 0) {
            String[] arr$ = array;
            int len$ = array.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String e = arr$[i$];
                if (s.equals(e)) {
                    return Boolean.TRUE;
                }
            }

            return Boolean.FALSE;
        } else {
            return Boolean.FALSE;
        }
    }

    public static void formatAdvancedQueryConditon(String[] array) {
        if (Integer.parseInt(array[4]) == 0) {
            if (array[2].indexOf("IN") != -1) {
                array[3] = " ('".concat(array[3]).concat("') ");
            } else if (array[2].indexOf("LIKE") != -1) {
                array[3] = " '%".concat(array[3]).concat("%' ");
            } else {
                array[3] = " '".concat(array[3]).concat("' ");
            }
        } else if (array[2].indexOf("IN") != -1) {
            array[3] = " (".concat(array[3]).concat(") ");
        } else if (array[2].indexOf("LIKE") != -1) {
            array[3] = " '%".concat(array[3]).concat("%' ");
        } else {
            array[3] = " ".concat(array[3]).concat(" ");
        }

        array[2] = " ".concat(array[2]).concat(" ");
    }

    private static int parseStyles(String parseStyle) {
        int parseStyleLen = 0;

        try {
            int index = parseStyle.lastIndexOf(".");
            parseStyleLen = parseStyle.substring(index + 1).length();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return parseStyleLen;
    }

    public static String holdDoubleDigit(String strNumber) {
        String strTemp = "";
        if (isStrEmpty(strNumber)) {
            return strTemp;
        } else {
            if (strNumber.substring(strNumber.length() - 1).equals("%")) {
                strTemp = parseDec(strNumber.substring(0, strNumber.length() - 1));
                strTemp = strTemp.concat("%");
            } else {
                strTemp = parseDec(strNumber);
            }

            return strTemp;
        }
    }

    public static String parseDec(String strNumber) {
        return parseDec(strNumber, (String)null);
    }

    public static String parseDec(String dec, String parseStyle) {
        String newObj = "";
        DecimalFormat df = null;

        try {
            if (isStrEmpty(dec)) {
                newObj = "";
            } else if (!dec.equals("0")) {
                if (isStrEmpty(parseStyle)) {
                    parseStyle = "##,###,###.00";
                }

                if (Float.parseFloat(dec) < 1.0F && Float.parseFloat(dec) > -1.0F) {
                    int parseLen = parseStyles(parseStyle);
                    StringBuffer parses = new StringBuffer("0.");

                    for(int i = 0; i < parseLen; ++i) {
                        parses.append("0");
                    }

                    df = new DecimalFormat(parses.toString());
                } else {
                    df = new DecimalFormat(parseStyle);
                }

                newObj = df.format(new Double(dec));
            } else {
                newObj = "0.00";
            }
        } catch (NumberFormatException var7) {
            var7.printStackTrace();
        } catch (IllegalArgumentException var8) {
            var8.printStackTrace();
        }

        return newObj;
    }

    public static String stringArrayToString(List array, int index) {
        if (array == null) {
            return null;
        } else if (array.size() == 0) {
            return null;
        } else {
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < array.size(); ++i) {
                String[][] str = (String[][])((String[][])array.get(i));
                sb.append("," + str[0][index]);
            }

            return sb.substring(1);
        }
    }

    public static boolean isEmpty(List list) {
        boolean isEmpty = Boolean.TRUE;
        if (list != null && !list.isEmpty()) {
            Iterator i$ = list.iterator();

            while(i$.hasNext()) {
                Object o = i$.next();
                if (o != null && !isStrEmpty(o.toString())) {
                    isEmpty = Boolean.FALSE;
                    break;
                }
            }

            return isEmpty;
        } else {
            return isEmpty;
        }
    }

    public static boolean isOnlyDisplay(String disabled) {
        return !"disabled".equals(disabled) && !"yes".equals(disabled) && !"true".equals(disabled) ? Boolean.FALSE : Boolean.TRUE;
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^([0-9]\\d*)(\\.\\d*)?$");
        if (isStrEmpty(str)) {
            return false;
        } else {
            Matcher isNum = pattern.matcher(str);
            return isNum.matches();
        }
    }

    public static String getIndicatorCode(int code) {
        return String.format("%04d", code);
    }

    public static String specialCharReplace(String paraValue) {
        String[] typeXOne = new String[]{"1、", "2、", "3、", "4、", "5、", "6、", "7、", "8、", "9、", "10、", "11、", "12、", "13、", "14、", "15、", "16、", "17、", "18、", "19、", "20、"};
        String[] typeDone = new String[]{"一、", "二、", "三、", "四、", "五、", "六、", "七、", "八、", "九、", "十、", "十一、", "十二、", "十三、", "十四、", "十五、", "十六、", "十七、", "十八、", "十九、", "二十、"};
        String[] typeDKOne = new String[]{"（一）", "（二）", "（三）", "（四）", "（五）", "（六）", "（七）", "（八）", "（九）", "（十）", "（十一）", "（十二）", "（十三）", "（十四）", "（十五）", "（十六）", "（十七）", "（十八）", "（十九）", "（二十）"};
        String[] typeXDOne = new String[]{"1．", "2．", "3．", "4．", "5．", "6．", "7．", "8．", "9．", "10．", "11．", "12．", "13．", "14．", "15．", "16．", "17．", "18．", "19．", "20．"};
        String[] typeDKXOne = new String[]{"（1）", "（2）", "（3）", "（4）", "（5）", "（6）", "（7）", "（8）", "（9）", "（10）", "（11）", "（12）", "（13）", "（14）", "（15）", "（16）", "（17）", "（18）", "（19）", "（20）"};
        int i;
        if (paraValue.indexOf("、") != -1) {
            for(i = 0; i < typeXOne.length; ++i) {
                paraValue = paraValue.replace(typeXOne[i], "");
            }

            for(i = 0; i < typeDone.length; ++i) {
                paraValue = paraValue.replace(typeDone[i], "");
            }
        }

        if (paraValue.indexOf("（") != -1) {
            for(i = 0; i < typeDKOne.length; ++i) {
                paraValue = paraValue.replace(typeDKOne[i], "");
            }

            for(i = 0; i < typeDKXOne.length; ++i) {
                paraValue = paraValue.replace(typeDKXOne[i], "");
            }
        }

        if (paraValue.indexOf("．") != -1) {
            for(i = 0; i < typeXDOne.length; ++i) {
                paraValue = paraValue.replace(typeXDOne[i], "");
            }
        }

        return paraValue;
    }

    public static String encodeFileName(String fileName) throws UnsupportedEncodingException {
        String prefix = fileName.substring(0, fileName.lastIndexOf(46));
        String suffix = fileName.substring(fileName.lastIndexOf(46));
        byte[] bytes = fileName.getBytes("gb2312");
        String encoded = new String(bytes, "iso8859-1");
        return encoded;
    }

    public static String replaceSpecialChar(String str) {
        return null != str && !str.trim().equals("") ? str.replace("\\/", "").replace("<", "").replace(">", "").replace(":", "").replace("?", "").replace("|", "").replace("\"", "").replace("*", "") : "nulltitle";
    }

    public static String replaceXSS(String str) {
        if (str == null) {
            return str;
        } else {
            String r = new String(str);
            String[] strs = new String[]{"|", "&", ";", "$", "%", "@", "'", "\"", "\\'", "\\\"", "<", ">", "(", ")", "+", "\n", "\r", ",", "\\"};
            String[] arr$ = strs;
            int len$ = strs.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String s = arr$[i$];
                r = r.replace(s, "");
            }

            return r;
        }
    }

    public static String replaceEscape(String str) {
        if (str == null) {
            return str;
        } else {
            StringBuffer sb = new StringBuffer();
            char[] chars = str.toCharArray();
            char[] arr$ = chars;
            int len$ = chars.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                char c = arr$[i$];
                boolean isok = c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9';
                if (isok) {
                    sb.append(c);
                }
            }

            return sb.toString();
        }
    }

    public static String convertMapKeyToString(Map<String, String> map, String key, String defaultValue) {
        return map.containsKey(key) ? (String)map.get(key) : defaultValue;
    }

    public static String convertMapKeyToInt(Map<String, String> map, String key) {
        return convertMapKeyToString(map, key, "");
    }

    public static String safeToString(Object o, String dv) {
        String r = dv;
        if (o != null) {
            r = String.valueOf(o);
        }

        return r;
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";

        for(int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 255);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }

        return hs;
    }

    public static String genKey() {
        String _result = null;
        KeyGenerator keygen = null;

        try {
            keygen = KeyGenerator.getInstance("DES");
            SecretKey deskey = keygen.generateKey();
            _result = byte2hex(deskey.getEncoded());
        } catch (NoSuchAlgorithmException var3) {
        }

        return _result;
    }

    public static String ToDBC(String input) {
        char[] c = input.toCharArray();

        for(int i = 0; i < c.length; ++i) {
            if (c[i] == 12288) {
                c[i] = ' ';
            } else if (c[i] > '\uff00' && c[i] < '｟') {
                c[i] -= 'ﻠ';
            }
        }

        return new String(c);
    }

    public static String escapeHtml(String o) {
        return o == null ? "" : o.replace(">", "&gt;").replace(" ", "&nbsp;").replace("<", "&lt;").replace("\n", "<br/>");
    }

    public static String replaceVariables(String str, Map data) {
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotBlank(str)) {
            int k = 0;
            int j = 0;
            int i = str.indexOf("${");

            while(i >= 0 && j >= 0) {
                sb.append(str.substring(j, i));
                j = str.indexOf("}", i);
                if (j > 0) {
                    String name = str.substring(i + 2, j);
                    if (data.containsKey(name)) {
                        sb.append(safeToString(data.get(name), ""));
                    } else {
                        sb.append("");
                    }

                    i = str.indexOf("${", j + 1);
                    k = j + 1;
                    ++j;
                }
            }

            sb.append(str.substring(k, str.length()));
        }

        return sb.toString();
    }

    public static String subStringByLengthAndType(String string, int type, int length) {
        return subStringByLengthAndType(string, type, length, 0);
    }

    public static String subStringByLengthAndType(String string, int type, int length, int start) {
        StringBuffer _string = new StringBuffer();
        int _length = 0;
        if (type == 0) {
            return start + length >= string.length() ? string.substring(start) : string.substring(start, start + length);
        } else {
            if (type == 1) {
                for(int i = start; i < string.length() && _length < length; ++i) {
                    if (string.charAt(i) >= 127) {
                        _length += 2;
                    } else {
                        ++_length;
                    }

                    if (length >= _length) {
                        _string.append(string.charAt(i));
                    }
                }
            }

            return _string.toString();
        }
    }

    public static int getStringLengthByType(String string, int type) {
        int _length = 0;
        if (type == 0) {
            return string.length();
        } else {
            if (type == 1) {
                for(int i = 0; i < string.length(); ++i) {
                    if (string.charAt(i) >= 127) {
                        _length += 2;
                    } else {
                        ++_length;
                    }
                }
            }

            return _length;
        }
    }

    public static String formatFileSize(double size) {
        String[] rank = new String[]{"B", "K", "M", "G"};

        int c;
        for(c = 0; size > 1024.0D; ++c) {
            size /= 1024.0D;
        }

        DecimalFormat df = new DecimalFormat("0.00");
        String result = df.format(size) + (c > rank.length ? rank[rank.length - 1] : rank[c]);
        return result;
    }

    public static List<String> search(String text, List<String> keywordList) {
        List<String> retList = new ArrayList();
        if (text != null && !"".equals(text)) {
            if (keywordList != null && !keywordList.isEmpty()) {
                Iterator i$ = keywordList.iterator();

                while(i$.hasNext()) {
                    String keyword = (String)i$.next();
                    if (keyword != null && !"".equals(keyword) && text.indexOf(keyword) != -1) {
                        retList.add(keyword);
                    }
                }

                return retList;
            } else {
                return retList;
            }
        } else {
            return retList;
        }
    }

    public static String searchRetString(String text, List<String> keywordList) {
        if (text != null && !"".equals(text)) {
            if (keywordList != null && !keywordList.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                Iterator i$ = keywordList.iterator();

                while(i$.hasNext()) {
                    String keyword = (String)i$.next();
                    if (keyword != null && !"".equals(keyword) && text.indexOf(keyword) != -1) {
                        if (sb.length() == 0) {
                            sb.append(keyword);
                        } else {
                            sb.append(",").append(keyword);
                        }
                    }
                }

                return sb.length() == 0 ? null : sb.toString();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static String replaceHtml(String str) {
        String pattern1 = "<(.[^>]*)>";
        return str.replaceAll(pattern1, "");
    }

    public static String toString(Object o) {
        return o != null ? o.toString() : "";
    }

    public static String decodestr(String str) {
        try {
            return URLDecoder.decode(str, "utf-8");
        } catch (UnsupportedEncodingException var2) {
            logger.warn("decode str failed.[" + str + "]", var2);
            return str;
        }
    }
}
