package com.aitp.web.common.service;

public interface PinFanMessageService {

    boolean sendUpdateMessage(String id);

    boolean sendCancelMessage(String id);
}
