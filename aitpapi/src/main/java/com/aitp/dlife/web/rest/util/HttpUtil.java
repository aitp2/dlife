package com.aitp.dlife.web.rest.util;

import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http 工具类
 */
public class HttpUtil {
    static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    public static String baseEncoder(String text){
        final Base64.Encoder encoder = Base64.getEncoder();
        String encoderData=text;
        try {
            final byte[] textByte = text.getBytes("UTF-8");
            encoderData=encoder.encodeToString(textByte);
        } catch (Exception e) {
            LOGGER.error("Encoder error:{}",text);
        }
        return encoderData;
    }

    public static String baseDecoder(String encodeText){
        final Base64.Decoder decoder = Base64.getDecoder();
        String decodeData=encodeText;
        try {
            decodeData=new String(decoder.decode(encodeText), "UTF-8");
        } catch (Exception e) {
            LOGGER.error("Decoder error:{}",encodeText);
        }
        return decodeData;
    }

}
