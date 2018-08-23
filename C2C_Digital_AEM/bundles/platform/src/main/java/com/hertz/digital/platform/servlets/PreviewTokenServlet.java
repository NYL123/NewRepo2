package com.hertz.digital.platform.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.ConfigurationPolicy;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.jackrabbit.api.security.authentication.token.TokenCredentials;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.auth.core.spi.AuthenticationHandler;
import org.apache.sling.auth.core.spi.AuthenticationInfo;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.service.api.PreviewToken;

/**
 * Generates and return the Preview token.
 * 
 * @author deepak.parma
 *
 */
@Component(metatype = true, immediate = true, description = "Preview Token Servlet", label = "Preview Tokken Servlet",
        policy = ConfigurationPolicy.REQUIRE)
@Service(Servlet.class)
@Properties({ @Property(name = "sling.servlet.paths", value = "/bin/previewToken", propertyPrivate = true),
        @Property(name = "sling.servlet.methods", value = "GET") })
public class PreviewTokenServlet extends SlingAllMethodsServlet {

    public PreviewTokenServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(PreviewTokenServlet.class);

    @Reference
    private PreviewToken previewToken;

    @Reference(target = "(service.pid=com.day.crx.security.token.impl.impl.TokenAuthenticationHandler)")
    private AuthenticationHandler authHandler;

    /**
     * Returns the generated token.
     *
     * @param request,response
     * @return void
     *
     * @throws ServletException
     * @throws- IOException
     */
    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.debug("Entering the doGet method of PreviewTokenServlet.");

        response.setCharacterEncoding(HertzConstants.UTF8);
        response.setContentType(HertzConstants.APPLICATION_JSON);
        JSONObject jsonToken = new JSONObject();
        LOGGER.debug("TokenAuthenticationHandler.extractCredentials() invoked to get AuthenticationInfo");
        AuthenticationInfo authInfoMap = authHandler.extractCredentials(request, response);
        TokenCredentials token = (TokenCredentials) authInfoMap
                .get(JcrResourceConstants.AUTHENTICATION_INFO_CREDENTIALS);
        LOGGER.debug("TokenCredentials extracted from AuthenticationInfo");
        try {
            jsonToken.put("previewAuthToken", previewToken.generate(token.getToken()));
        } catch (JSONException jsonException) {
            LOGGER.error("JSON Exception ::{0}", jsonException);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            LOGGER.error("No   SuchAlgorithmException ::{0}", noSuchAlgorithmException);
        } finally {
            response.getWriter().write(jsonToken.toString());
        }
        LOGGER.debug("Exiting the doGet method of PreviewTokenServlet.");
    }
}
