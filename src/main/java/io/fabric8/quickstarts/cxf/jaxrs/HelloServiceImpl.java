/**
 *  Copyright 2005-2016 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package io.fabric8.quickstarts.cxf.jaxrs;

import java.net.InetAddress;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import com.google.gson.JsonObject;

import net.logstash.logback.marker.LogstashMarker;
import net.logstash.logback.marker.Markers;

import io.swagger.annotations.Api;

@Api("/sayHello")
public class HelloServiceImpl implements HelloService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public String welcome() {
		return createMessage("Welcome to the CXF RS Spring Boot application, append /{name} to call the hello service",
				"Unknown");
	}

	public String sayHello(String a) {
		return createMessage("Hello " + a + ", Welcome to CXF RS Spring Boot World!!!", a);
	}

	private String createMessage(String content,String a) {
    	
    	JsonObject message = new JsonObject();
    	
    	message.addProperty("message", content);
    	String appPodIp = "";
    	
		try {
			InetAddress localhost = InetAddress.getLocalHost();
			appPodIp=localhost.getHostAddress().trim();
			message.addProperty("podIp", appPodIp);
		}catch(Exception e) {
			message.addProperty("podIp", "Unknown");
		}
    	
    	String messageString =message.toString();
    	
    	LogstashMarker marker = Markers.append("appPodIp", appPodIp)
    			.and(Markers.append("appGreeterName", a));
    	
    	logger.info(marker,messageString);
    	
    	//logger.info(messageString);
    	

    	//System.out.println(messageString);
    	return messageString;
    }

}
