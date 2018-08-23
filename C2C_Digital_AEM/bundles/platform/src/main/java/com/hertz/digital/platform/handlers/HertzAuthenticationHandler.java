package com.hertz.digital.platform.handlers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.jackrabbit.api.security.authentication.token.TokenCredentials;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.auth.core.AuthUtil;
import org.apache.sling.auth.core.spi.AuthenticationFeedbackHandler;
import org.apache.sling.auth.core.spi.AuthenticationHandler;
import org.apache.sling.auth.core.spi.AuthenticationInfo;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.service.api.PreviewToken;

@Component(immediate = true, metatype = true, label = "Hertz Authentication Handler",
        description = "Hertz Authentication Handler")
		@Properties({ @Property(name = "service.ranking", intValue = 10000),
        @Property(name = AuthenticationHandler.PATH_PROPERTY, value = "/content/hertz-rac"),
        @Property(name = Constants.SERVICE_DESCRIPTION, value = "Hertz Authentication Handler") })
@Service
public class HertzAuthenticationHandler implements AuthenticationHandler, AuthenticationFeedbackHandler {

    public HertzAuthenticationHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	private static final Logger log = LoggerFactory.getLogger(HertzAuthenticationHandler.class);

    private static final String AUTH_TYPE = "TOKEN";

    @Reference(target = "(service.pid=com.day.crx.security.token.impl.impl.TokenAuthenticationHandler)")
    private AuthenticationHandler wrappedAuthHandler;

    @Reference
    private PreviewToken previewTokenService;

    
    @Reference
    private ResourceResolverFactory resResolverFactory;

    @Override
    public AuthenticationInfo extractCredentials(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        log.debug("HertzAuthenticationHandler.extractCredentials() method invoked");
        String authToken = StringUtils.EMPTY;
        String previewToken = getPreviewToken(httpServletRequest);
        log.debug("HertzAuthenticationHandler.extractCredentials() previewAuthToken extracted");
        if (StringUtils.isNotBlank(previewToken)) {
            authToken = previewTokenService.validate(previewToken);
        }
        if (HttpConstants.METHOD_GET.equals(httpServletRequest.getMethod()) && StringUtils.isNotEmpty(previewToken)
                && StringUtils.isNotBlank(authToken)) {
            if (!AuthUtil.isValidateRequest(httpServletRequest)) {
                AuthUtil.setLoginResourceAttribute(httpServletRequest, httpServletRequest.getContextPath());
            }
            TokenCredentials tokencred = new TokenCredentials(authToken);
            AuthenticationInfo authenticationInfo = new AuthenticationInfo(AUTH_TYPE);
            authenticationInfo.put(JcrResourceConstants.AUTHENTICATION_INFO_CREDENTIALS, tokencred);
            return authenticationInfo;
        } else {
            log.debug(
                    "HertzAuthenticationHandler.extractCredentials() :: Not valid token :: deligating to next AuthenticationHandler");
            return null;
        }
    }

    private String getPreviewToken(HttpServletRequest request) {
        log.debug("HertzAuthenticationHandler.getPreviewToken() invoked");
        String token;
        if ((token = getPreviewTokenFromHeader(request)) != null) {
            return token;
        }
        if ((token = getPreviewTokenFromCookie(request)) != null) {
            return token;
        }
        return null;
    }

    private String getPreviewTokenFromHeader(HttpServletRequest request) {
        log.debug("HertzAuthenticationHandler.getPreviewTokenFromHeader() invoked");
        if (request.getHeader(HertzConstants.PREVIEW_AUTH_TOKEN) != null) {
            return request.getHeader(HertzConstants.PREVIEW_AUTH_TOKEN);
        }
        log.debug("HertzAuthenticationHandler.getPreviewTokenFromHeader() :: returning NULL");
        return null;
    }

    private String getPreviewTokenFromCookie(HttpServletRequest request) {
        log.debug("HertzAuthenticationHandler.getPreviewTokenFromCookie() invoked");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int j = 0; j < cookies.length; j++) {
                if (HertzConstants.PREVIEW_AUTH_TOKEN.equalsIgnoreCase(cookies[j].getName())) {
                    return cookies[j].getValue();
                }
            }
        }
        log.debug("HertzAuthenticationHandler.getPreviewTokenFromCookie() :: returning NULL");
        return null;
    }

    @Override
    public boolean requestCredentials(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        return wrappedAuthHandler.requestCredentials(httpServletRequest, httpServletResponse);
    }

    @Override
    public void dropCredentials(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        wrappedAuthHandler.dropCredentials(httpServletRequest, httpServletResponse);
    }

    @Override
    public void authenticationFailed(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            AuthenticationInfo authenticationInfo) {
        log.error("Authentication failed");
    }

    @Override
    public boolean authenticationSucceeded(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, AuthenticationInfo authenticationInfo) {
        // Return true if the handler sent back a response to the client and
        // request processing should terminate.
        // Return false if the request should proceed as authenticated through
        // the framework. (This is usually the desired behavior)
        return false;
    }

    @Activate
    protected void activate(final Map<String, String> config) {
    	boolean wrappedIsAuthFeedbackHandler = false ;
        if (wrappedAuthHandler != null) {
            log.debug("Registered wrapped authentication feedback handler");
            wrappedIsAuthFeedbackHandler = wrappedAuthHandler instanceof AuthenticationFeedbackHandler;
        }
    }
}
