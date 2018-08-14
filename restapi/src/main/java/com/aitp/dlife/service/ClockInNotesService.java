package com.aitp.dlife.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aitp.dlife.domain.ClockIn;
import com.aitp.dlife.domain.Pics;
import com.aitp.dlife.repository.ClockInNotesRepository;
import com.aitp.dlife.response.ClockInNotesResponse;
import com.aitp.dlife.response.PicsResponse;
import com.aitp.dlife.service.mapper.InstantMapper;

@Service
@Transactional
public class ClockInNotesService {

	private final Logger log = LoggerFactory.getLogger(ClockInNotesService.class);

	@Autowired
	private ClockInNotesRepository clockInNotesRepository;

   /**
    * 查询用户指定活动指定月份的打卡日历
    * @param wechatUserId
    * @param activityId
    * @param ym
    * @return
    */
    @Transactional(readOnly = true)
    public List<String> getColckInCalendarByWechatUserIdAndAcitivityIdAndYearMonth(String wechatUserId, Long activityId,
			String ym) {
		return clockInNotesRepository.getColckInCalendarByWechatUserIdAndAcitivityIdAndYearMonth(wechatUserId,activityId,ym);
	}
    
    /**
     * 查询用户指定活动的打卡记录
     * @param wechatUserId
     * @param activityId
     * @return
     */
    @Transactional(readOnly = true)
    public List<ClockInNotesResponse> getColckInNotesByWechatUserIdAndAcitivityId(Pageable pageable,String wechatUserId, Long activityId) {
    	List<ClockInNotesResponse> response = new ArrayList<ClockInNotesResponse>();
    	Page<ClockIn> clockInResult = clockInNotesRepository.getColckInNotesByWechatUserIdAndAcitivityId(pageable,wechatUserId,activityId);
    	populateClockInNotesResponse(response,clockInResult.getContent());
		return response;
	}
    
    /**
     * 查询用户所有活动的打卡记录
     * @param pageable 
     * @param wechatUserId
     * @return
     */
    @Transactional(readOnly = true)
    public List<ClockInNotesResponse> getColckInNotesByWechatUserId(Pageable pageable, String wechatUserId) {
    	List<ClockInNotesResponse> response = new ArrayList<ClockInNotesResponse>();
    	Page<ClockIn> clockInResult = clockInNotesRepository.getColckInNotesByWechatUserId(pageable,wechatUserId);
    	populateClockInNotesResponse(response,clockInResult.getContent());
		return response;
	}
    
    /**
     * 查询用户指定活动指定时间的打卡记录
     * @param wechatUserId
     * @return
     */
    @Transactional(readOnly = true)
    public List<ClockInNotesResponse> getColckInNotesByWechatUserIdAndAcitivityIdAndYearMonthDay(String wechatUserId,Long activityId,String ymd) {
    	List<ClockInNotesResponse> response = new ArrayList<ClockInNotesResponse>();
    	List<ClockIn> clockInResult = clockInNotesRepository.getColckInNotesByWechatUserIdAndAcitivityIdAndYearMonthDay(wechatUserId, activityId, ymd);
    	populateClockInNotesResponse(response,clockInResult);
		return response;
	}
    
    
    

    /**
     * entity -> response
     * @param response
     * @param clockInResult
     */
	private void populateClockInNotesResponse(List<ClockInNotesResponse> response, List<ClockIn> clockInResult) {
		for (ClockIn clockInEntity : clockInResult) {
			ClockInNotesResponse clockInNotesResponse = new ClockInNotesResponse();
			clockInNotesResponse.setPunchDateTime(InstantMapper.toDateString(clockInEntity.getPunchDateTime()));
			clockInNotesResponse.setSignNote(clockInEntity.getSignNote());
			clockInNotesResponse.setTitle(clockInEntity.getTitle());
			List<PicsResponse> picsResponse = new ArrayList<PicsResponse>();
			populatePicsResponse(picsResponse,clockInEntity.getPics());
			clockInNotesResponse.setPics(picsResponse);
			response.add(clockInNotesResponse);
		}
	}

	/**
	 * entity -> response
	 * @param picsResponse
	 * @param pics
	 */
	private void populatePicsResponse(List<PicsResponse> picsResponse, Set<Pics> pics) {
		for (Pics pic : pics) {
			PicsResponse picResponse = new PicsResponse();
			picResponse.setOssPath(pic.getOssPath());
			picsResponse.add(picResponse);
		}
	}


}
