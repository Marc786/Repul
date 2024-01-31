package ca.ulaval.glo4003.context.resource;

import org.glassfish.jersey.server.ResourceConfig;

public interface ResourceFactory {
    ResourceConfig create();
}
