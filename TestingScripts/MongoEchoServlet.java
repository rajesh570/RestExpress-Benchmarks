package com.pearson.testservlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.mongodb.DBObject;
import com.pearson.testservlets.dao.MongoDao;
import com.pearson.testservlets.domain.TestData;

/**
 * Servlet implementation class EchoServlet
 */
public class MongoEchoServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	ObjectMapper mapper = new ObjectMapper();

 	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MongoEchoServlet() 
    {
        super();
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 
	 * to get here use something like this
	 * http://localhost:8080/RegularEchoServlet/EchoServlet?echo=Hello
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		TestData testData =  mapper.readValue(request.getReader(), TestData.class);
		DBObject result = MongoDao.readWrite(testData.getValue());
		response.getOutputStream().print(mapper.writeValueAsString(new TestData(result.get("_id").toString()))); 
	}
	
}


