package com.aitp.web.common.service.dto;

import java.util.HashSet;
import java.util.Set;

public class PinFanActivityMessageDTO {

    private String id;

    private String activitiyTile;

    private Set<AttendeeDTO> attendees = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivitiyTile() {
        return activitiyTile;
    }

    public void setActivitiyTile(String activitiyTile) {
        this.activitiyTile = activitiyTile;
    }

    public Set<AttendeeDTO> getAttendees() {
        return attendees;
    }

    public void setAttendees(Set<AttendeeDTO> attendees) {
        this.attendees = attendees;
    }
}
