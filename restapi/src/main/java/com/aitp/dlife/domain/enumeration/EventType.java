package com.aitp.dlife.domain.enumeration;

import java.util.ArrayList;
import java.util.List;

/**
 * The EventType enumeration.
 */
public enum EventType {
    CREATE, ATTEND, QUIT, CANCEL, UPDATE, COMMENT,REPLY, CLOCKIN,COMMENTTHUMBSUP,CLOCKTHUMBSUP;


    public static List<EventType> getMessageTypeList(String code){

         List<EventType> eventTypes = new ArrayList<>();

         switch (code){

             case "0": {
                eventTypes.add(EventType.COMMENT);
                eventTypes.add(EventType.REPLY);
                eventTypes.add(EventType.ATTEND);
                eventTypes.add(EventType.QUIT);
                eventTypes.add(EventType.CLOCKIN);
                eventTypes.add(EventType.CANCEL);
                eventTypes.add(EventType.UPDATE);
                eventTypes.add(EventType.COMMENTTHUMBSUP);
                eventTypes.add(EventType.CLOCKTHUMBSUP);
                break;
             }
             case "1": {
                eventTypes.add(EventType.COMMENT);
                eventTypes.add(EventType.CLOCKIN);
                eventTypes.add(EventType.REPLY);
                break;
             }
             case "2": {
                 eventTypes.add(EventType.ATTEND);
                 eventTypes.add(EventType.QUIT);
                 eventTypes.add(EventType.CANCEL);
                 eventTypes.add(EventType.UPDATE);
                 break;
             }
             case "3": {
            	 eventTypes.add(EventType.COMMENTTHUMBSUP);
            	 eventTypes.add(EventType.CLOCKTHUMBSUP);
                 break;
             }
         }
        return eventTypes;

    }

}
