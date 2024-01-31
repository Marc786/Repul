package ca.ulaval.glo4003;

import ca.ulaval.glo4003.context.resource.ResourceConfigFactory;
import java.net.URI;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepulServer implements Runnable {

    public static final int PORT = 8080;
    public static final String BASE_URI = "http://localhost:" + PORT + "/";
    private static final Logger LOGGER = LoggerFactory.getLogger(RepulServer.class);

    public void run() {
        final ResourceConfig config = new ResourceConfigFactory().create();
        startServer(config);
    }

    private void startServer(ResourceConfig config) {
        try {
            final Server server = JettyHttpContainerFactory.createServer(
                URI.create(BASE_URI),
                config
            );

            Runtime
                .getRuntime()
                .addShutdownHook(
                    new Thread(() -> {
                        try {
                            LOGGER.info("Shutting down the application...");
                            server.stop();
                            LOGGER.info("Done, exit.");
                        } catch (Exception e) {
                            LOGGER.error("Error shutting down the server", e);
                        }
                    })
                );
            LOGGER.info("Application started");

            Thread.currentThread().join();
        } catch (InterruptedException e) {
            LOGGER.error("Error starting up the server", e);
        }
    }
}
