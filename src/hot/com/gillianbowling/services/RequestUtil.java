package com.gillianbowling.services;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import javax.servlet.http.HttpServletRequest;

/**
 * Date: Jun 22, 2010
 *
 * @author T. Curran
 */
@Name("requestUtil")
public class RequestUtil {

	@In
	Object httpRequest;

	public HttpServletRequest getRequest() {
		if (httpRequest instanceof HttpServletRequest) {
			return (HttpServletRequest)httpRequest;
		}
		return null;
	}
}
