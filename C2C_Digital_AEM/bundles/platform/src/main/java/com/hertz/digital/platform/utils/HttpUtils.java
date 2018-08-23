package com.hertz.digital.platform.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import javax.mail.internet.ContentType;
import javax.mail.internet.ParseException;
import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Base64;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hertz.digital.platform.constants.HertzConstants;
import com.hertz.digital.platform.exceptions.SystemException;

/**
 * <p>
 * This class contains the utility methods for creating TCP connections. More
 * specifically, this class contains connection utilities for
 * <ul>
 * <li>HTTP GET</li>
 * <li>HTTP POST</li>
 * </ul>
 * </p>
 * 
 * @author n.kumar.singhal
 * @version 1.0
 *
 */
public final class HttpUtils {

	private HttpUtils() {
		// private constructor
	}

	/** LOGGER Instantiation. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(HttpUtils.class);
	
	/**
	 * Constant for accept charset
	 */
	private static final String ACCEPT_CHARSET="Accept-Charset";
	
	/**
	 * Constant for logger request message
	 */
	private static final String LOGGER_REQUEST_MESSAGE="Request has been sent @ {} with query params {} ";
	
	/**
	 * Constant for logger response message
	 */
	private static final String LOGGER_RESPONSE_MESSAGE="Response has been received from end point for GET request";

	/**
	 * <p>
	 * This method supports the HTTP GET calls.
	 * </p>
	 *
	 * @param url
	 *            the url
	 * @param queryString
	 *            the query string
	 * @return the string
	 */
	public static String get(String url, String queryString) {
		HttpURLConnection connection = null;
		String result = null;
		String finalUrl = StringUtils.EMPTY;
		try {
			finalUrl = getFinalUrl(url, queryString);
			connection = (HttpURLConnection) (new URL(finalUrl)
					.openConnection());
			connection.setRequestProperty(ACCEPT_CHARSET,
					HertzConstants.CHARSET_UTF_8);
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(120000);

			LOGGER.debug(LOGGER_REQUEST_MESSAGE,
					connection.getURL(), connection.getURL().getQuery());

			result = getBodyAsString(connection);

		} catch (IOException e) {
			LOGGER.error("Error occured while hitting the url {} {}",
					e.getMessage(), e);
		} finally {
			if (null != connection) {
				connection.disconnect();
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(LOGGER_RESPONSE_MESSAGE);
		}

		return result;
	}

	/**
	 * <p>
	 * This method supports the HTTP GET calls.
	 * </p>
	 *
	 * @param url
	 *            the url
	 * @param queryString
	 *            the query string
	 * @return the string
	 */
	public static String get(String url, String queryString, String mode) {
		String resultString = StringUtils.EMPTY;
		if (mode.equalsIgnoreCase(HertzConstants.HTTP)) {
			resultString = get(url, queryString);
		} else {
			String finalUrl = StringUtils.EMPTY;
			HttpsURLConnection connection = null;
			String result = null;
			try {
				finalUrl = getFinalUrl(url, queryString);
				connection = (HttpsURLConnection) (new URL(finalUrl)
						.openConnection());
				connection.setRequestProperty(ACCEPT_CHARSET,
						HertzConstants.CHARSET_UTF_8);
				connection.setConnectTimeout(30000);
				connection.setReadTimeout(120000);

				LOGGER.debug(LOGGER_REQUEST_MESSAGE,
						connection.getURL(), connection.getURL().getQuery());

				result = getBodyAsString(connection);

			} catch (IOException e) {
				throw new SystemException(e.getMessage(), e);
			} finally {
				if (null != connection) {
					connection.disconnect();
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(LOGGER_RESPONSE_MESSAGE);
			}
			resultString = result;
		}
		return resultString;
	}

	/**
	 * <p>
	 * This method supports the HTTP GET calls.
	 * </p>
	 *
	 * @param url
	 *            the url
	 * @param queryString
	 *            the query string
	 * @return the string
	 */
	public static String get(String url, String queryString, String mode,
			String username, String password) {
		String resultString = StringUtils.EMPTY;
		if (mode.equalsIgnoreCase(HertzConstants.HTTP)) {
			resultString = get(url, queryString);
		} else {

			HttpsURLConnection connection = null;
			String result = null;
			String finalUrl = StringUtils.EMPTY;
			try {
				finalUrl = getFinalUrl(url, queryString);
				connection = (HttpsURLConnection) (new URL(finalUrl)
						.openConnection());
				connection.setRequestProperty(ACCEPT_CHARSET,
						HertzConstants.CHARSET_UTF_8);
				connection.setRequestProperty("Authorization", "Basic "
						+ getBasicAuthenticationEncoding(username, password));
				connection.setConnectTimeout(30000);
				connection.setReadTimeout(50000);

				LOGGER.debug(LOGGER_REQUEST_MESSAGE,
						connection.getURL(), connection.getURL().getQuery());

				result = getBodyAsString(connection);

			} catch (IOException e) {
				throw new SystemException(e.getMessage(), e);
			} finally {
				if (null != connection) {
					connection.disconnect();
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(LOGGER_RESPONSE_MESSAGE);
			}
			resultString = result;
		}
		return resultString;
	}

	private static String getBasicAuthenticationEncoding(String username,
			String password) {

		String userPassword = username + ":" + password;
		return new String(Base64.encodeBase64(userPassword.getBytes()));
	}

	/**
	 * <p>
	 * This method supports the HTTP POST calls.
	 * </p>
	 *
	 * @param url
	 *            the url
	 * @param params
	 *            the query string
	 * @return the string
	 */
	public static String post(String url, String params, String contentType,
			boolean isAuthorized, String accessToken) {
		HttpURLConnection connection = null;
		DataOutputStream outStream = null;
		int timeOut = 50000;
		String result = null;
		String finalUrl = StringUtils.EMPTY;

		try {
			if (url.contains("~OneTime")) {
				finalUrl = url.replace("~OneTime", "");
				timeOut = 900000;
			} else {
				finalUrl = url;
			}
			connection = (HttpURLConnection) (new URL(finalUrl)
					.openConnection());
			connection.setRequestMethod("POST");
			connection.setRequestProperty(ACCEPT_CHARSET,
					HertzConstants.CHARSET_UTF_8);
			connection.setRequestProperty("Content-Type", contentType);

			if (isAuthorized) {
				connection.setRequestProperty("Authorization",
						"Bearer " + accessToken);
			}
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(timeOut);
			connection.setDoOutput(true);
			outStream = new DataOutputStream(connection.getOutputStream());
			outStream.writeBytes(params);
			LOGGER.debug(LOGGER_REQUEST_MESSAGE,
					connection.getURL(), params);

			result = getBodyAsString(connection);

		} catch (IOException e) {
			LOGGER.error("Error occured : - {}{}", e.getMessage(), e);
		} finally {
			if (null != outStream) {
				try {
					outStream.flush();
					outStream.close();
				} catch (IOException e) {
					LOGGER.error("Error occured : - {}{}", e.getMessage(), e);
				}
			}
			if (null != connection) {
				connection.disconnect();
			}
		}
		LOGGER.debug(
				"Response has been received from end point for POST request");
		return result;
	}

	private static String getBodyAsString(HttpURLConnection connection)
			throws IOException {
		StringBuilder result = new StringBuilder();

		InputStream response = connection.getInputStream();

		try {
			Charset charset = null;

			String contentType = connection.getContentType();
			if (contentType != null) {
				try {
					getCharset(contentType, charset);
				} catch (ParseException e) {
					LOGGER.error("paring content type \'" + contentType + "\'",
							e);
				}
			}
			if (charset == null) {
				charset = Charset.forName(HertzConstants.CHARSET_UTF_8);
			}

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(response, charset));
			try {
				String line = "";
				while (null != (line = reader.readLine())) {
					result.append(line);
				}
			} finally {
				reader.close();
			}
		} finally {
			response.close();
		}
		return result.toString();
	}
	
	/**
	 * This method gives the charset for a content type
	 * @param type
	 * @param charset
	 * @throws ParseException 
	 */
	private static void getCharset(String contentType, Charset charset) throws ParseException{
		ContentType type = new ContentType(contentType);
		if (type != null) {
			String cs = type.getParameter("charset");
			if (cs != null) {
				charset = Charset.forName(cs);
			}
		}
	}

	/**
	 * This method is used to get the final url
	 * 
	 * @param url
	 * @param queryString
	 * @return String
	 */
	private static String getFinalUrl(String url, String queryString) {
		String finalUrl;
		if (StringUtils.isNotEmpty(queryString)) {
			finalUrl = url + "?" + queryString;
		} else {
			finalUrl = url;
		}
		return finalUrl;
	}

}
