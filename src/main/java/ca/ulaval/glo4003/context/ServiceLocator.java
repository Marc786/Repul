package ca.ulaval.glo4003.context;

import java.util.HashMap;

public final class ServiceLocator {

    private static ServiceLocator instance;
    private final HashMap<Class<?>, Object> repositories = new HashMap<>();

    private ServiceLocator() {}

    public static ServiceLocator getInstance() {
        if (instance == null) {
            instance = new ServiceLocator();
        }
        return instance;
    }

    public <T> T getService(Class<T> repositoryClass) {
        return (T) repositories.get(repositoryClass);
    }

    public <T> void registerService(Class<T> repositoryClass, T repository) {
        repositories.put(repositoryClass, repository);
    }
}
