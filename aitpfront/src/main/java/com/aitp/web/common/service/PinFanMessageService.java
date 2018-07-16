package com.aitp.web.common.service;

import com.aitp.web.common.service.dto.PinFanActivityMessageDTO;

public interface PinFanMessageService {

    boolean sendUpdateMessage(String id);

    boolean sendCancelMessage(String id);

    boolean sendRemindMessage(PinFanActivityMessageDTO dto);
}
