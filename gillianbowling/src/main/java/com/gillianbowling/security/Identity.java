package com.gillianbowling.security;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;


/**
 * Date: 16-02-2015
 *
 * @Author T. Curran
 */
@Named
public class Identity implements Serializable {

	transient Subject subject = SecurityUtils.getSubject();

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
}
