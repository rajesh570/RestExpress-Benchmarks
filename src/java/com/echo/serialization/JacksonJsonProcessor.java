package com.echo.serialization;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferInputStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.strategicgains.restexpress.ContentType;
import com.strategicgains.restexpress.serialization.DeserializationException;
import com.strategicgains.restexpress.serialization.SerializationException;
import com.strategicgains.restexpress.serialization.SerializationProcessor;

/*
    Copyright 2012, Strategic Gains, Inc.

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */

/**
 * @author toddf
 * @since Mar 2, 2012
 */
public class JacksonJsonProcessor
implements SerializationProcessor
{
	private ObjectMapper mapper;

	public JacksonJsonProcessor()
	{
		this(new ObjectMapper());
	}

	public JacksonJsonProcessor(ObjectMapper mapper)
    {
	    super();
	    this.mapper = mapper;
//	    mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
//	    mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
    }

	@Override
	public <T> T deserialize(String string, Class<T> type)
	{
		try
		{
			return mapper.readValue(string, type);
		}
        catch (JsonProcessingException e)
        {
        	throw new DeserializationException(e);
        }
        catch (IOException e)
        {
        	throw new DeserializationException(e);
        }
	}

	@Override
	public <T> T deserialize(ChannelBuffer buffer, Class<T> type)
	{
		try
        {
	        return mapper.readValue(new InputStreamReader(new ChannelBufferInputStream(buffer)), type);
        }
        catch (JsonProcessingException e)
        {
        	throw new DeserializationException(e);
        }
        catch (IOException e)
        {
        	throw new DeserializationException(e);
        }
	}

	@Override
	public String serialize(Object object)
	{
		try
        {
	        return mapper.writeValueAsString(object);
        }
        catch (JsonProcessingException e)
        {
        	throw new SerializationException(e);
        }
        catch (IOException e)
        {
        	throw new SerializationException(e);
        }
	}

	@Override
	public String getResultingContentType()
	{
		return ContentType.JSON;
	}

}

