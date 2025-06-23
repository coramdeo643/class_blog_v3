package com.tenco.blog.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Date/Time-related Util. Class
 * static method 구성해서 객체 생성없이 바로 사용 가능하게 설계한다
 */
public class MyDateUtil {

    public static String timestampFormat(Timestamp time){
        // Board Entity 선언된 Timestamp > Date 객체로 변환
        // getTime() method call > 밀리초 단위로 시간 받는다 > Date 객체 생성
        Date currentDate = new Date(time.getTime());

        // Apache Commons Lib. DataFormatUtils class 활용

        return DateFormatUtils.format(currentDate, "yyyy-MM-dd HH:mm");
    }
}
