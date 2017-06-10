package de.dawid.test.auth.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public class RestEasyServices extends Application {
 
    private Set<Object> singletons = new HashSet<Object>();
 
    public RestEasyServices() {
        singletons.add(new AuthSampleService());
    }
 
    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}