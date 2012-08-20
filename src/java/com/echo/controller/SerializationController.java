package com.echo.controller;

import com.echo.domain.TestData;
import com.strategicgains.restexpress.Request;
import com.strategicgains.restexpress.Response;

public class SerializationController extends AbstractDelayingController 
{
	public TestData serializationBenchmark(Request request, Response response)
	{
		// Marshalling and Unmarshalling using RestExpress with Jackson.
		TestData testdata = request.getBodyAs(TestData.class);
		return testdata;
	} 
	
}
