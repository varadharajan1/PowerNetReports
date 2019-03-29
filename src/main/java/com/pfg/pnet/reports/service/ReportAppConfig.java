package com.pfg.pnet.reports.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class ReportAppConfig extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();
		classes.add(ReportMovementService.class);
		return classes;
	}
}
