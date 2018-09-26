package com.aitp.dlife.domain.enumeration;

/**
 * The CommentChannel enumeration.
 */
public enum CommentChannel {
    COOK, FIT, PIN, FAQS, POINT_PRODUCT;

    public static CommentChannel valueIn(String code){
        for (CommentChannel commentChannel : values()) {
            if(commentChannel.toString().equals(code)){
                return commentChannel;
            }
        }
        return null;
    }

}
