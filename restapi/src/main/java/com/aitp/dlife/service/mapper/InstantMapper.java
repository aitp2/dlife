package com.aitp.dlife.service.mapper;

import com.aitp.dlife.web.rest.util.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.Date;

/**
 * @author jianfei.yin
 * @create 2018-06-20 1:01 AM
 **/
public class InstantMapper {
    public static Instant fromString(String date){
        if(StringUtils.isEmpty(date)){
            return null;
        }
        return DateUtil.fromYDMStringDate(date).toInstant();
    }
    public static String toDateString(Instant date){
        if(date==null){
            return null;
        }
        return DateUtil.getYMDDateString(Date.from(date));
    }
}
