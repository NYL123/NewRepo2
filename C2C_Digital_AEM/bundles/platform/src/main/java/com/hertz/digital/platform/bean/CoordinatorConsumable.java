package com.hertz.digital.platform.bean;

/**
 * This object contains all the necessary attributes required to carry on the
 * data page replication notifications
 * 
 * @author n.kumar.singhal
 *
 */
public class CoordinatorConsumable {
	/**
	 * Variable for healthy.
	 */
	private boolean healthy;
	/**
	 * Variable for apiUrl.
	 */
	private String apiUrl;
	/**
	 * Variable for dataPath.
	 */
	private String dataPath;
	/**
	 * Variable for requestParams.
	 */
	private String requestParams;
	/**
	 * Variable for contentType.
	 */
	private String contentType;
	/**
	 * Variable for grantType.
	 */
	private String grantType;
	/**
	 * Variable for dataTemplate.
	 */
	private String dataTemplate;
	/**
	 * Variable for requestType.
	 */
	private String requestType;
	/**
	 * Variable for tokenPath.
	 */
	private String tokenPath;
	/**
	 * Variable for basePath.
	 */
	private String basePath;
	/**
	 * Variable for passwordGrant.
	 */
	private String passwordGrant;

	/**
	 * Default Constructors
	 */
	public CoordinatorConsumable() {
		super();
	}

	/**
	 * Checks whether the item is usable.
	 * 
	 * @return THe flag with the indication.
	 */
	public boolean ishealthy() {
		return healthy;
	}

	/**
	 * Sets the heath value.
	 * 
	 * @param ishealthy
	 *            The health flag.
	 */
	public void setIshealthy(boolean ishealthy) {
		this.healthy = ishealthy;
	}

	/**
	 * For getting the API url.
	 * 
	 * @return API url.
	 */
	public String getApiUrl() {
		return apiUrl;
	}

	/**
	 * For setting the API Url.
	 * 
	 * @param apiUrl
	 *            The API Url.
	 */
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	/**
	 * For getting data path.
	 * 
	 * @return Data path.
	 */
	public String getDataPath() {
		return dataPath;
	}

	/**
	 * For Setting data path.
	 * 
	 * @param dataPath
	 *            Data path.
	 */
	public void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}

	/**
	 * For getting request params.
	 * 
	 * @return request params.
	 */
	public String getRequestParams() {
		return requestParams;
	}

	/**
	 * For setting request params.
	 * 
	 * @param requestParams
	 *            Request params.
	 */
	public void setRequestParams(String requestParams) {
		this.requestParams = requestParams;
	}

	/**
	 * For getting content type.
	 * 
	 * @return Content type.
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * For setting content type.
	 * 
	 * @param contentType
	 *            Content type.
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * For getting grant type.
	 * 
	 * @return grant type.
	 */
	public String getGrantType() {
		return grantType;
	}

	/**
	 * For Setting grant type.
	 * 
	 * @param grantType
	 *            grant type.
	 */
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	/**
	 * For getting data template.
	 * 
	 * @return data template.
	 */
	public String getDataTemplate() {
		return dataTemplate;
	}

	/**
	 * For Setting data template.
	 * 
	 * @param dataTemplate
	 *            data template.
	 */
	public void setDataTemplate(String dataTemplate) {
		this.dataTemplate = dataTemplate;
	}

	/**
	 * For getting request type.
	 * 
	 * @return request type.
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * For setting request type.
	 * 
	 * @param requestType
	 *            request type.
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	/**
	 * For getting token path.
	 * 
	 * @return Token path.
	 */
	public String getTokenPath() {
		return tokenPath;
	}

	/**
	 * For setting token path.
	 * 
	 * @param tokenPath
	 *            token path.
	 */
	public void setTokenPath(String tokenPath) {
		this.tokenPath = tokenPath;
	}

	/**
	 * For getting base path.
	 * 
	 * @return base path.
	 */
	public String getBasePath() {
		return basePath;
	}

	/**
	 * For Setting base path.
	 * 
	 * @param basePath
	 *            base path.
	 */
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	/**
	 * For getting passowrd grant.
	 * 
	 * @return passowrd grant.
	 */
	public String getPasswordGrant() {
		return passwordGrant;
	}

	/**
	 * For Setting passowrd grant.
	 * 
	 * @param passwordGrant
	 *            passowrd grant.
	 */
	public void setPasswordGrant(String passwordGrant) {
		this.passwordGrant = passwordGrant;
	}
}
