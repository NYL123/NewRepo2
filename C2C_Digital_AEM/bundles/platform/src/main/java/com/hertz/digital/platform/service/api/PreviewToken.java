package com.hertz.digital.platform.service.api;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * The Interface PreviewToken.
 *
 * @author deepak.parma
 */
public interface PreviewToken {

    /**
     * Generate.
     * 
     * @param token
     *
     * @return the string
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public String generate(String token) throws NoSuchAlgorithmException, UnsupportedEncodingException;

    /**
     * Return the Valid Auth token.
     *
     * @param token
     *            the token
     * @return true, if successful
     */
    public String validate(final String token);

}
