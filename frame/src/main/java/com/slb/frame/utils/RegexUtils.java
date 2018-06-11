package com.slb.frame.utils;

import com.orhanobut.logger.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.blankj.utilcode.utils.ConstUtils.*;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/2
 *     desc  : 正则相关工具类
 * </pre>
 */
public class RegexUtils {

    private RegexUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /**
     * If u want more please visit http://toutiao.com/i6231678548520731137/
     */

    public static boolean isPwd(String string){
        return !StringUtils.isEmpty(string) && Pattern.matches("^(?![a-zA-Z]+$)^(?![0-9]+$)^(?![^a-zA-Z0-9]+$)[\\x1f-~]{6,15}$", string);
    }

    public static boolean isVerifyCode(String string){
        return !StringUtils.isEmpty(string) && Pattern.matches("^[0-9]{1,6}$", string);
    }

    public static boolean isMobile(String string){
        return !StringUtils.isEmpty(string)
                && Pattern.matches("^(1[3-9])\\d{9}$", string);
    }

    /**
     * 验证手机号（简单）
     *
     * @param string 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMobileSimple(String string) {
        return isMatch(REGEX_MOBILE_SIMPLE, string);
    }

    /**
     * 验证手机号（精确）
     *
     * @param string 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMobileExact(String string) {
        return isMatch(REGEX_MOBILE_EXACT, string);
    }

    /**
     * 验证电话号码
     *
     * @param string 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isTel(String string) {
        Logger.d(isMatch(REGEX_TEL, string));
        return isMatch(REGEX_TEL, string);
    }

    /**
     * 验证身份证号码15位
     *
     * @param string 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard15(String string) {
        return isMatch(REGEX_IDCARD15, string);
    }

    /**
     * 验证身份证号码18位
     *
     * @param string 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard18(String string) {
        return isMatch(REGEX_IDCARD18, string);
    }

    /**
     * 验证邮箱
     *
     * @param string 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isEmail(String string) {
        return isMatch(REGEX_EMAIL, string);
    }

    /**
     * 验证URL
     *
     * @param string 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isURL(String string) {
        return isMatch(REGEX_URL, string);
    }

    /**
     * 验证汉字
     *
     * @param string 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isChz(String string) {
        return isMatch(REGEX_CHZ, string);
    }

    /**
     * 验证用户名
     * <p>取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾,用户名必须是6-20位</p>
     *
     * @param string 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isUsername(String string) {
        return isMatch(REGEX_USERNAME, string);
    }

    /**
     * 验证yyyy-MM-dd格式的日期校验，已考虑平闰年
     *
     * @param string 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isDate(String string) {
        return isMatch(REGEX_DATE, string);
    }

    /**
     * 验证IP地址
     *
     * @param string 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIP(String string) {
        return isMatch(REGEX_IP, string);
    }

    /**
     * string是否匹配regex
     *
     * @param regex  正则表达式字符串
     * @param string 要匹配的字符串
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMatch(String regex, String string) {
        return !StringUtils.isEmpty(string) && Pattern.matches(regex, string);
    }

    /**
     * 验证银行卡卡号
     *
     * @param cardNo
     * @return
     */
    public static boolean isBankCard(String cardNo) {
        Pattern p = Pattern.compile("^\\d{16,19}$|^\\d{6}[- ]\\d{10,13}$|^\\d{4}[- ]\\d{4}[- ]\\d{4}[- ]\\d{4,7}$");
        Matcher m = p.matcher(cardNo);

        return m.matches();
    }
}