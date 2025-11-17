package org.example.socialmediaapp.exception;

public class ResourceNotFoundException extends RuntimeException {
    private final String resourceName;
    private final Object id;

    public ResourceNotFoundException(String resourceName, Object id) {
        super(resourceName + " with id=" + id + " not found");
        this.resourceName = resourceName;
        this.id = id;
    }

    public Object getId() {
        return id;
    }

    public String getResourceName() {
        return resourceName;
    }
}
