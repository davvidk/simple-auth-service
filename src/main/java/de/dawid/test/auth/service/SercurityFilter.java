package de.dawid.test.auth.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

// This filter is not working 
@Provider
public class SercurityFilter implements ContainerRequestFilter {

	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
	private static final String SECURED_URL_PREFIX = "secured";

	public void filter(ContainerRequestContext requestContext) throws IOException {
		System.out.println("Filter called");
		if (requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {
			List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);

			if (authHeader != null && authHeader.size() > 0) {
				String authToken = authHeader.get(0);
				authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
				byte[] asBytes = Base64.getDecoder().decode(authToken);
				String decodedString = new String(asBytes, "utf-8");
				StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
				String login = tokenizer.nextToken();
				String password = tokenizer.nextToken();

				if ("dawid".equals(login) && "secret".equals(password)) {
					return;
				}
			}
			abortWithUnauthorized(requestContext);
		}

	}

	private void abortWithUnauthorized(ContainerRequestContext requestContext) {
		Response unauthorizedStatus = Response.status(Status.UNAUTHORIZED).entity("User cannot access resource")
				.build();
		requestContext.abortWith(unauthorizedStatus);
	}
}
