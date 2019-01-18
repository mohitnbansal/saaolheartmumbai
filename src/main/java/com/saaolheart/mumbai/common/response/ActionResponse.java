package com.saaolheart.mumbai.common.response;

import org.springframework.validation.Errors;

public class ActionResponse<T> {
	
	public Errors error;
	
	public T document;
	
	public ActionStatus actionResponse;

	public Errors getError() {
		return error;
	}

	public void setError(Errors error) {
		this.error = error;
	}

	public T getDocument() {
		return document;
	}

	public void setDocument(T document) {
		this.document = document;
	}

	public ActionStatus getActionResponse() {
		return actionResponse;
	}

	public void setActionResponse(ActionStatus actionResponse) {
		this.actionResponse = actionResponse;
	}
	
	

}
