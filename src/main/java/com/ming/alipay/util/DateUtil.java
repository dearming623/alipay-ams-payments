/*
 * The MIT License
 * Copyright © 2019 Alipay.com Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.ming.alipay.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


public class DateUtil {

    /**
     * ISO datetime最小长度，必须带有时区，可能还有微秒
     */
    private final static int ISO_DATE_LENGTH = 25;

    /**
     * UTC 0时区的ISO datetime最小长度，时区信息是字符Z，不是+00:00格式
     */
    private final static int UTC_0_ISO_DATE_LENGTH = 20;

    /**
     * UTC 0时区的timezone标识
     */
    private final static String UTC_0_TIMEZONE = "Z";

    public final static String longFormat = "yyyyMMddHHmmss";

    public static String getISODateTimeStrNow(){
        return getISODateTimeStr(new Date());
    }
    /**
     * 获取ISO的时间字符串
     *
     * @param date
     * @return ISO的时间字符串
     */
    public static String getISODateTimeStr(Date date) {
        return getISODateTimeStr(date, null);
    }

    /**
     * 获取指定时区的ISO的时间字符串
     *
     * @param date     日期
     * @param timeZone 时区
     * @return ISO的时间字符串
     */
    public static String getISODateTimeStr(Date date, TimeZone timeZone) {
        if (date == null) {
            return null;
        }

        Calendar cal = new GregorianCalendar();
        if (timeZone != null) {
            cal.setTimeZone(timeZone);
        }
        cal.setTime(date);
        cal.set(Calendar.MILLISECOND, 0);
        return ISO8601Utils.format(date, false, cal.getTimeZone());
    }

    /**
     * 解析ISO的时间字符串
     *
     * @param isoDateStr
     * @return ISO的时间字符串
     */
    public static Date parseISODateTimeStr(String isoDateStr) {
        if (StringUtils.isBlank(isoDateStr)) {
            return null;
        }

        int minDateStrLength;
        // UTC 0时区字符串，时区标识是字符Z，不是+00:00
        if (isoDateStr.endsWith(UTC_0_TIMEZONE)) {
            minDateStrLength = UTC_0_ISO_DATE_LENGTH;

        } else {
            minDateStrLength = ISO_DATE_LENGTH;
        }

        if (isoDateStr.length() < minDateStrLength) {
            return null;
        }

//        Calendar cal = DatatypeConverter.parseDate(isoDateStr);
//        return cal.getTime();
        try {
            return ISO8601Utils.parse(isoDateStr, new ParsePosition(0));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getISO8601DateTimeStrNow() {
        return getISO8601DateTimeStr(ZonedDateTime.now());
    }

    public static String getISO8601DateTimeStr(ZonedDateTime dateTime) {
        return dateTime
                .withNano(0)
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public static ZonedDateTime parseISO8601DateTimeStr(String isoDateTimeStr){
        DateTimeFormatter isoOffsetDateTime =
                DateTimeFormatter.ISO_OFFSET_DATE_TIME
                .withZone(ZoneId.systemDefault());

        return ZonedDateTime.parse(isoDateTimeStr);//ofInstant(Instant.parse(isoDateTimeStr,isoOffsetDateTime), ZoneId.systemDefault());
    }
}
