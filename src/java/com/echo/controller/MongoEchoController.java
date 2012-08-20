package com.echo.controller;

import com.echo.dao.MongoDao;
import com.strategicgains.restexpress.Request;
import com.strategicgains.restexpress.Response;

public class MongoEchoController extends AbstractDelayingController 
{
	private static final long serialVersionUID = 1L;

	public String mongoEchoBenchmark(Request request, Response response)  
	{
		String echoValue =  request.getUrlDecodedHeader("echo");
		String result = MongoDao.readWrite(echoValue);
		return result;
	}
	
}


