package com.aitp.dlife.domain.enumeration;

import java.util.ArrayList;
import java.util.List;

/**
 * The EventType enumeration.
 */
public enum EventType {
    CREATE, ATTEND, QUIT, CANCEL, UPDATE, COMMENT, CLOCKIN,THUMBSUP;


    public static List<EventType> getMessageTypeList(String code){

         List<EventType> eventTypes = new ArrayList<>();

         switch (code){

             case "0": {
                eventTypes.add(EventType.COMMENT);
                eventTypes.add(EventType.ATTEND);
                eventTypes.add(EventType.QUIT);
                eventTypes.add(EventType.CANCEL);
                eventTypes.add(EventType.UPDATE);
                eventTypes.add(EventType.THUMBSUP);
                break;
             }
             case "1": {
                eventTypes.add(EventType.COMMENT);
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
            	 eventTypes.add(EventType.THUMBSUP);
                 break;
             }
         }
        return eventTypes;

    }

}
