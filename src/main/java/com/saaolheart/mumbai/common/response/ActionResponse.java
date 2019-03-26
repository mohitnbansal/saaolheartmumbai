package com.saaolheart.mumbai.common.response;

import java.util.Set;

import org.springframework.validation.Errors;

public class ActionResponse<T> {
	
	public Set<String> error;
	
	public T document;
	
	public ActionStatus actionResponse;

	
	public Set<String> getError() {
		return error;
	}

	public void setError(Set<String> error) {
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
