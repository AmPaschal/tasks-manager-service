package com.tekitng.workman;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

import javax.ws.rs.core.Application;

/**
 * @author Amusuo Paschal C.
 * @since 11/4/2020 12:16 AM
 */

@SecurityScheme(
        securitySchemeName = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer"
)
public class WorkmanApplication extends Application {
}
