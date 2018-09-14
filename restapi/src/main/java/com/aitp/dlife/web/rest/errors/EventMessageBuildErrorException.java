package com.aitp.dlife.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;

public class EventMessageBuildErrorException extends AbstractThrowableProblem {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EventMessageBuildErrorException() {
        super(ErrorConstants.EVENT_MESSAGE_BUILD_TYPE, "you build not have pop");
    }
}
