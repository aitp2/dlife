package com.aitp.dlife.web.rest.errors;



import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class UniquenessConflictException extends AbstractThrowableProblem {

	
	private static String ERROR_MESSAGE = "The data created is not unique.";
	
	public UniquenessConflictException() {
		
		super(ErrorConstants.UNIQUENESS_TYPE,ERROR_MESSAGE,Status.CONFLICT);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
