package com.echo;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.jboss.netty.handler.codec.http.HttpMethod;

import com.echo.serialization.ResponseProcessors;
import com.strategicgains.restexpress.Format;
import com.strategicgains.restexpress.Parameters;
import com.strategicgains.restexpress.RestExpress;
import com.strategicgains.restexpress.pipeline.SimpleConsoleLogMessageObserver;
import com.strategicgains.restexpress.plugin.RoutesMetadataPlugin;
import com.strategicgains.restexpress.util.Environment;

/**
 * The main entry-point into the RestExpress Benchmark example services.
 * 
 * @author toddf
 * @since Aug 16, 2012
 */
public class Main
{
	public static void main(String[] args) throws Exception
	{
		Configuration config = loadEnvironment(args);
		RestExpress server = new RestExpress()
		    .setName(config.getName())
		    .setPort(config.getPort())
		    .putResponseProcessor(Format.JSON, ResponseProcessors.json())
		    .putResponseProcessor(Format.XML, ResponseProcessors.xml())
		    .putResponseProcessor(Format.WRAPPED_JSON, ResponseProcessors.wrappedJson())
		    .putResponseProcessor(Format.WRAPPED_XML, ResponseProcessors.wrappedXml())
		    .setDefaultFormat(config.getDefaultFormat());

		if (config.shouldDisplayOutput())
		{
			server.addMessageObserver(new SimpleConsoleLogMessageObserver());
		}

		if (config.getWorkerCount() > 0)
		{
			server.setExecutorThreadCount(config.getWorkerCount());
		}

		if (config.getExecutorThreadCount() > 0)
		{
			server.setExecutorThreadCount(config.getExecutorThreadCount());
		}

		defineRoutes(server, config);

		new RoutesMetadataPlugin()
			.register(server)
			.parameter(Parameters.Cache.MAX_AGE, 86400); // Cache for 1 day (24 hours).

		mapExceptions(server);
		server.bind();
		server.awaitShutdown();
	}

	/**
	 * @param server
	 * @param config
	 */
	private static void defineRoutes(RestExpress server, Configuration config)
	{
		server.uri("/echo", config.getEchoController())
		    .action("echoBenchmark", HttpMethod.GET)
		    .noSerialization();
		
		server.uri("/mongoEcho", config.getMongoEchoController())
	    	.action("mongoEchoBenchmark", HttpMethod.GET)
	    	.noSerialization();
		
		server.uri("/fileIO", config.getFileIOController(config.getFileDirectory()))
	    	.action("fileIOBenchmark", HttpMethod.GET)
	    	.noSerialization();
		
		server.uri("/serialization", config.getSerializationController())
	    	.action("serializationBenchmark", HttpMethod.GET)
	    	.noSerialization();

		// This route supports POST and PUT, echoing the body in the response.
		// GET and DELETE are also supported but require an 'echo' header or
		// query-string parameter.
//		server.uri("/echo/{delay_ms}", config.getEchoController());
	}

	/**
	 * @param server
	 */
	private static void mapExceptions(RestExpress server)
	{
		// server
		// .mapException(ItemNotFoundException.class, NotFoundException.class)
		// .mapException(DuplicateItemException.class, ConflictException.class)
		// .mapException(ValidationException.class, BadRequestException.class);
	}

	private static Configuration loadEnvironment(String[] args)
	throws FileNotFoundException,
	    IOException
	{
		if (args.length > 0)
		{
			return Environment.from(args[0], Configuration.class);
		}

		return Environment.fromDefault(Configuration.class);
	}
}
