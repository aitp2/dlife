package com.aitp.web.common.service;

import com.aitp.web.common.service.dto.ActivityParticipationDTO;

import java.util.List;

public interface FitnessMessageService {

    boolean sendUpdateMessage(String id);

    List<ActivityParticipationDTO> getActivityParticipationByActivityIdFromAPI(String id);

}
