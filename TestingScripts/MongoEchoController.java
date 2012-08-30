package com.echo.controller;

import com.echo.dao.MongoDao;
import com.echo.domain.TestData;
import com.echo.serialization.ResponseProcessors;
import com.mongodb.DBObject;
import com.strategicgains.restexpress.Request;
import com.strategicgains.restexpress.Response;

public class MongoEchoController extends AbstractDelayingController 
{
	private static final long serialVersionUID = 1L;

	public TestData mongoEchoBenchmark(Request request, Response response)  
	{
		String input =  request.getUrlDecodedHeader("echo");
		TestData testData = ResponseProcessors.json().getSerializer().deserialize(input, TestData.class);
		DBObject result = MongoDao.readWrite(testData);
		return new TestData(result.get("_id").toString()); 
	}
	
}


