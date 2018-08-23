package com.hertz.digital.platform.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class SystemException.This class should be used to handle the system
 * related exceptions.
 * 
 * @author n.kumar.singhal
 * @version 1.0
 */
public class SystemException extends RuntimeException {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(SystemException.class);

	/**
	 * Error code to handling error code in exception.
	 */
	private final String errorCode;

	/**
	 * Instantiates a new application exception.
	 */
	public SystemException() {
		super();
		this.errorCode = "";
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
		LOGGER.error(message, cause);
		this.errorCode = "";
	}

	/**
	 * Gets the error code.
	 * 
	 * @return the error code
	 */
	public String getErrorCode() {
		return errorCode;
	}

}
