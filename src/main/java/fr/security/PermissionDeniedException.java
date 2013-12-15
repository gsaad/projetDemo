package fr.security;

import org.springframework.core.NestedRuntimeException;

public class PermissionDeniedException extends NestedRuntimeException {

    private static final long serialVersionUID = 8068297009284976718L;

    public PermissionDeniedException(String msg) {
        super(msg);
    }
}
