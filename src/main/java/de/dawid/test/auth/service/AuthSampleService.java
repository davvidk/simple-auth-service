package de.dawid.test.auth.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/secured")
public class AuthSampleService {

	@GET
	@Path("/message")
	@Produces(MediaType.TEXT_PLAIN)
	public String securedMessage() {
		System.out.println("message");
		return "this is my secret";
	}
}
