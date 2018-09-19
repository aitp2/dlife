package com.aitp.dlife.service.dto;

/**
 * The Class UserPointDTO.
 */
public class UserPointDTO {
	
	/** The id. */
	private Long id;
	
	/** The total point. */
	private Integer totalPoint;
	
	/** The today point. */
	private Integer todayPoint;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the total point.
	 *
	 * @return the total point
	 */
	public Integer getTotalPoint() {
		return totalPoint;
	}

	/**
	 * Sets the total point.
	 *
	 * @param totalPoint the new total point
	 */
	public void setTotalPoint(Integer totalPoint) {
		this.totalPoint = totalPoint;
	}

	/**
	 * Gets the today point.
	 *
	 * @return the today point
	 */
	public Integer getTodayPoint() {
		return todayPoint;
	}

	/**
	 * Sets the today point.
	 *
	 * @param todayPoint the new today point
	 */
	public void setTodayPoint(Integer todayPoint) {
		this.todayPoint = todayPoint;
	}
}
