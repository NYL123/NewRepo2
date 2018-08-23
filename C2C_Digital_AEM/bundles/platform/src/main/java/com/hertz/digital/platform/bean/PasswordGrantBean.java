package com.hertz.digital.platform.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * For storing password grant servce response as a bean.
 * 
 * @author n.kumar.singhal
 *
 */
public class PasswordGrantBean {

	/**
	 * Variable for accessToken.
	 */
	@SerializedName("access_token")
	@Expose
	private String accessToken;
	/**
	 * Variable for tokenType.
	 */
	@SerializedName("token_type")
	@Expose
	private String tokenType;
	/**
	 * Variable for expiresIn.
	 */
	@SerializedName("expires_in")
	@Expose
	private Integer expiresIn;
	/**
	 * Variable for scope.
	 */
	@SerializedName("scope")
	@Expose
	private String scope;
	/**
	 * Variable for jti.
	 */
	@SerializedName("jti")
	@Expose
	private String jti;

	/**
	 * Default Constructors
	 */
	public PasswordGrantBean() {
		super();
	}

	/**
	 * For getting accessToken.
	 * 
	 * @return accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * For setting accessToken.
	 * 
	 * @param accessToken
	 *            accessToken.
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * For getting tokenType.
	 * 
	 * @return tokenType
	 */
	public String getTokenType() {
		return tokenType;
	}

	/**
	 * For setting tokenType.
	 * 
	 * @param tokenType
	 *            tokenType
	 */
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	/**
	 * For getting expiresIn.
	 * 
	 * @return expiresIn
	 */
	public Integer getExpiresIn() {
		return expiresIn;
	}

	/**
	 * For setting expiresIn.
	 * 
	 * @param expiresIn
	 *            expiresIn.
	 */
	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	/**
	 * For getting scope.
	 * 
	 * @return scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * For setting scope.
	 * 
	 * @param scope
	 *            scope
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * For getting jti.
	 * 
	 * @return jti
	 */
	public String getJti() {
		return jti;
	}

	/**
	 * For setting jti.
	 * 
	 * @param jti
	 *            jti
	 */
	public void setJti(String jti) {
		this.jti = jti;
	}

}
