package com.hertz.digital.platform.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * This is the constants file for hertz.
 *
 * @author a.dhingra
 */
public interface HertzConstants {
	
	/** constant for service name component resource type variable. */
    String SERVICE_NAME_COMPONENT_RES_TYPE =  "hertz/components/content/servicename";
    
    /** constant for service name variable. */
    String SERVICE_NAME =  "serviceName";
	
	/** constant for partner home page variable. */
    String PARTNER_HOME_PAGE =  "partnerHomePage";
	
	/** constant for home page resource type variable. */
    String HOME_PAGE_RES_TYPE =  "hertz/components/pages/home";
	
	/** constant for partner base page path variable. */
    String PARTNER_BASE_PAGE_PATH =  "partnerBasePagePath";
    
    /** constant for hero component resource type variable. */
    String HERO_RES_TYPE =  "hertz/components/content/hero";
    
    /** constant for fixed content slot resource type variable. */
    String FIXED_CONTENT_SLOT_RES_TYPE =  "hertz/components/content/fixedcontentslot";
	
	/** constant for header resource type variable. */
    String HEADER_RES_TYPE = "/apps/hertz/components/pages/header";
    
    /** constant for footer resource type variable. */
    String FOOTER_RES_TYPE = "/apps/hertz/components/pages/footer";
	
	/** constant for brand variable. */
    String BRAND = "brand";
    
    /** constant for envconfig variable. */
    String ENV_CONFIG = "envconfig";
	
	/** constant for secrets filepath variable. */
    String HERTZ_SECRETS_FILEPATH = "dirPath";
	
	/** constant for api key variable. */
    String HERTZ_API_KEY = "hertz.api.key";
    
    /** constant for api iv variable. */
    String HERTZ_API_IV = "hertz.api.iv";
	
	/** constant for configured secrets variable. */
    String CONFIGURED_SECRETS = "configuredSecrets";
	
	/** constant for slash topnav variable. */
    String SLASH_TOPNAV = "/topnav";
	
	 /** constant for header reference variable. */
    String HEADER_REFERENCE = "headerReference";
    
    /** constant for footer reference variable. */
    String FOOTER_REFERENCE = "footerReference";

    /** constant for linkdropdownvalues variable. */
    String LINK_DROPDOWN_VALUES = "dropDownValues";

    /** constant for linkdropdown variable. */
    String LINK_DROPDOWN = "linkdropdown";

    /** constant for responsive grid variable. */
    String RESPONSIVE_GRID = "responsivegrid";

    /** constant for linkdropdown country name variable. */
    String LINK_DROPDOWN_COUNTRY_NAME = "countryName";

    /** constant for linkdropdown country order variable. */
    String LINK_DROPDOWN_COUNTRY_ORDER = "countryOrder";

    /** constant for linkdropdown country url variable. */
    String LINK_DROPDOWN_COUNTRY_URL = "countryURL";
    
    /** constant for linkdropdown country url variable. */
    String LINK_DROPDOWN_CODE = "dropdownCode";

    /** constant for alt. */
    String ALT = "alt";
    
    /** constant for tiername. */
    String TIER_NAME = "tiername";

    /** constant for fileReference. */
    String FILE_REFERENCE = "fileReference";

    /** constant for resource type value of link dropdown component. */
    String LINK_DROPDOWN_RESOURCE_TYPE = "hertz/components/content/linkdropdown";

    /** constant for content variable of link dropdown component. */
    String LINK_DROPDOWN_CONTENT = "content";

    /** constant for data variable of link dropdown component. */
    String LINK_DROPDOWN_DATA = "data";

    /** constant for page url variable of link dropdown component. */
    String LINK_DROPDOWN_PAGE_URL = "pageUrl";

    /** constant for dropdown value list of link dropdown component. */
    String DROPDOWN_VALUE_LIST = "dropdownValueList";

    /** constant for link dropdown id value of link dropdown component. */
    String LINK_DROPDOWN_ID = "linkId";

    /** constant for dropdown label value of link dropdown component. */
    String DROPDOWN_LABEL = "label";

    /** constant for dropdown value of link dropdown component. */
    String DROPDOWN_VALUE = "dropdownValue";

    /** constant for order of link dropdown component. */
    String ORDER = "order";

    /** constant for rich text pair component resource type. */
    String RICH_TEXT_PAIR_RESOURCETYPE = "hertz/components/content/richtextpair";

    /** constant for country name. */
    String COUNTRY_NAME = "country-name";

    /** constant for languages variable. */
    String LANGUAGES = "languages";

    /** constant for locale variable. */
    String LOCALE = "locale";

    /** constant for languageName variable. */
    String LANGUAGE_NAME = "languageName";

    /** constant for languageCode variable. */
    String LANGUAGE_CODE = "languageCode";

    /** constant for isDefaultLanguage variable. */
    String IS_DEFAULT_LANGUAGE = "isDefaultLanguage";

    /** constant for useLocaleInURL variable. */
    String USE_LOCALE_IN_URL = "useLocaleInURL";

    /** constant for url variable. */
    String URL = "url";

    /** constant for imagepair variable. */
    String IMAGE_PAIR = "imagepair";

    /** constant for category name. */
    String CATEGORY_NAME = "category-name";

    /** constant for category id. */
    String CATEGORY_ID = "categoryId";

    /** constant for preselected value. */
    String PRESELECTED_VALUE = "preselectedValue";

    /** constant for configurable links. */
    String CONFIGURABLE_LINKS = "configurableLinks";

    /** constant for country list component. */
    String COMPONENT_COUNTRY_LIST = "/apps/hertz/components/content/countrylist";
    
    /** constant for residency country list component. */
    String COMPONENT_RESIDENCY_COUNTRY_LIST = "/apps/hertz/components/content/residencycountrylist";
    

    /** constant for country list component. */
    String COMPONENT_ERROR_MSG = "/apps/hertz/components/content/errormsg";

    /** constant for state list component. */
    String COMPONENT_STATE_LIST = "/apps/hertz/components/content/statelist";

    /** constant for airline train carrier component. */
    String COMPONENT_AIRLINE_TRAIN_CARRIER = "/apps/hertz/components/content/airlinetraincarrier";

    /** constant for link name. */
    String LINK_NAME = "linkName";

    /** constant for link url. */
    String LINK_URL = "linkURL";

    /** constant for link alt text. */
    String LINK_ALT_TEXT = "linkAltText";

    /** constant for section name. */
    String SECTION_NAME = "sectionName";

    /** constant for modal. */
    String MODAL = "modal";

    /** constant for modal template path. */
    String MODAL_TEMPLATE_PATH = "/apps/hertz/templates/modal";

    /** constant for utf-8. */
    String UTF8 = "UTF-8";

    /** constant for application/json content type. */
    String APPLICATION_JSON = "application/json";

    /** constant for content path in dam. */
    String CONTENT_PATH_DAM = "/content/dam/hertz";

    /** constant for par node name. */
    String PAR = "par";

    /** constant for jcr:title node property. */
    String JCR_TITLE_PROPERTY = "jcr:title";

    /** constant for textfield node. */
    String TEXTFIELD = "textfield";

    /** constant for cq:template property. */
    String JCR_CQ_TEMPLATE = "cq:template";

    /**
     * constant for socialLinkSubtitle property of the social links component.
     */
    String SOCIAL_LINKS_SUBTITLE = "socialLinksSubtitle";

    /** constant for header. */
    String HEADER = "header";

    /** constant for logo . */
    String LOGO = "logo";

    /** constant for header-search. */
    String HEADER_SEARCH = "headersearch";

    /** constant for login. */
    String LOGIN = "login";

    /** constant for logouts. */
    String LOGOUT = "logout";

    /** constant for countrylanguage. */
    String COUNTRY_LANGUAGE = "countrylanguage";

    /** constant for content path. */
    String CONTENT = "/content/";

    /** constant for footer link property. */
    String FOOTER_LINK_URL = "linkurl";

    /** constant for openURLNewWindow property. */
    String OPEN_URL_NEW_WINDOW = "openURLNewWindow";

    /** constant for loginItems property. */
    String LOGIN_ITEMS = "loginItems";

    /** constant for html. */
    String HTML = "html";

    /** constant for dot. */
    String DOT = ".";

    /** constant for slash. */
    String SLASH = "/";

    /** constant for footer. */
    String FOOTER = "footer";

    /** constant for cq:PageContent property. */
    String CQ_PAGE_CONTENT = "cq:PageContent";
    /** constant for footerlinks. */
    String FOOTER_LINKS = "footerlinks";

    /** constant for footerLinks property. */
    String FOOTER_LINKS_ARRAY = "footerLinks";

    /** constant for sociallinks. */
    String SOCIAL_LINKS = "sociallinks";

    /** constant for socialLinks. */
    String SOCIAL_LINKS_ARRAY = "socialLinks";

    /** constant for emailsignup. */
    String EMAIL_SIGNUP = "emailsignup";

    /** constant for links. */
    String LINKS = "links";

    /** constant for heading property. */
    String HEADING = "heading";

    /** constant for displayDesktopHeading property. */
    String DISPLAY_DESKTOP_HEADING = "displayDesktopHeading";

    /** constant for displayMobileHeading property. */
    String DISPLAY_MOBILE_HEADING = "displayMobileHeading";

    /** constant for displayTabletHeading property. */
    String DISPLAY_TABLET_HEADING = "displayTabletHeading";

    /** constant for displayAppHeading property. */
    String DISPLAY_APP_HEADING = "displayAppHeading";

    /** constant for displayDesktopLink property. */
    String DISPLAY_DESKTOP_LINK = "displayDesktopLink";

    /** constant for displayMobileLink property. */
    String DISPLAY_MOBILE_LINK = "displayMobileLink";

    /** constant for displayTabletLink property. */
    String DISPLAY_TABLET_LINK = "displayTabletLink";

    /** constant for displayAppLink property. */
    String DISPLAY_APP_LINK = "displayAppLink";

    /** constant for heading property. */
    String SOCIAL_ICON = "socialIcon";

    /** constant for socialSite property. */
    String SOCIAL_ICON_IMAGE_REFERENCE = "socialIconImageReference";

    /** constant for socialURL property. */
    String SOCIAL_URL = "socialURL";

    /** constant for openIconNewWindow property. */
    String OPEN_ICON_NEW_WINDOW = "openIconNewWindow";

    /** constant for altText property. */
    String ALT_TEXT = "altText";

    /** constant for signupForEmailTitle property. */
    String SIGNUP_EMAIL_TITLE = "signupForEmailTitle";

    /** constant for signupForEmailSubtitle property. */
    String SIGNUP_EMAIL_SUBTITLE = "signupForEmailSubtitle";

    /** constant for signupForEmailPlaceholderText property. */
    String SIGNUP_EMAIL_PLACEHOLDER_TEXT = "signupForEmailPlaceholderText";

    /** constant for signupForEmailButtonText property. */
    String SIGNUP_EMAIL_BUTTON_TEXT = "signupForEmailButtonText";

    /** constant for targetURL property. */
    String TARGET_URL = "targetURL";

    /** constant for linkText property. */
    String LINK_TEXT = "linkText";

    /** constant for chat. */
    String CHAT_NODE = "chat";

    /** constant for forward slashing. */
    String FRWD_SLASH = "/";

    /** constant for semi colon. */
    String SEMI_COLON = ";";

    /** constant for period. */
    String PERIOD = ".";

    /** constant for hash. */
    String HASH = "#";

    /** constant for underscore. */
    String UNDERSCORE = "_";

    /** constant for content type html. */
    String CONTENT_TYPE_HTML = "html";

    /** constant for charset UTF-8. */
    String CHARSET_UTF_8 = "UTF-8";

    /** constant for Accept-Charset. */
    String ACCEPT_CHARSET = "Accept-Charset";

    /** constant for Content-Disposition. */
    String CONTENT_DISPOSITION = "Content-Disposition";

    /** The Constant HTTP. */
    String HTTP = "http:";

    /** The Constant HTTPS. */
    String HTTPS = "https:";

    /** Constant for http protocol. */
    String PROTOCOL_HTTP = "http";

    /** Constant for https protocol. */
    String PROTOCOL_HTTPS = "http";

    /* General */
    /** constant for cq:Page property. */
    String CQ_PAGE = "cq:Page";

    /** constant for type. */
    String TYPE = "type";

    /** constant for path. */
    String PATH = "path";

    /** constant for p.limit. */
    String LIMIT = "p.limit";

    /** constant for @. */
    String AT = "@";

    /** constant for sling:resourceType property. */
    String SLING_RESOURCE_TYPE = "sling:resourceType";

    /** constant for jcr:lastModifiedBy property. */
    String JCR_LAST_MODIFIED_BY = "jcr:lastModifiedBy";

    /** constant for jcr:lastModified property. */
    String JCR_LAST_MODIFIED = "jcr:lastModified";

    /** constant for jcr:primaryType property. */
    String JCR_PRIMARY_TYPE = "jcr:primaryType";
    
    /** constant for jcr:primaryType property. */
    String JCR_CREATED_BY = "jcr:createdBy";
    
    /** constant for jcr:primaryType property. */
    String JCR_CREATED = "jcr:created";

    /** constant for wcm/foundation/components/parsys path. */
    String TOUCH_PARSYS_RES_TYPE = "wcm/foundation/components/parsys";

    /** constant for foundation/components/parsys path. */
    String CLASSIC_PARSYS_RES_TYPE = "foundation/components/parsys";

    /** constant for par sys not be included in HTML. */
    static final List<
            String> PAR_SYS_PATHS_EXCLUDED_FROM_HTML_GENERATION = new ArrayList<
                    String>(Arrays.asList("configtext-parsys"));

    /** constant for comma. */
    String COMMA = ",";

    /** constant for firstLegalLinkLabel property. */
    String LEGAL_LINK_LABEL_1 = "firstLegalLinkLabel";

    /** constant for firstLegalLinkURL property. */
    String LEGAL_LINK_URL_1 = "firstLegalLinkURL";

    /** constant for openFirstLegalLinkURLInNewWindow property. */
    String OPEN_LEGAL_NEW_WINDOW_1 = "openFirstLegalLinkURLInNewWindow";

    /** constant for secondLegalLinkLabel property. */
    String LEGAL_LINK_LABEL_2 = "secondLegalLinkLabel";

    /** constant for secondLegalLinkURL property. */
    String LEGAL_LINK_URL_2 = "secondLegalLinkURL";

    /** constant for openSecondLegalLinkURLInNewWindow property. */
    String OPEN_LEGAL_NEW_WINDOW_2 = "openSecondLegalLinkURLInNewWindow";

    /** constant for jcr:content node. */
    String JCR_CONTENT = "jcr:content";

    /** constant for countrylanguage. */
    String COUNTRYLANGUAGE = "countrylanguage";

    /** constant for legalDescriptionText property. */
    String LEGAL_DESRIPTION_TEXT = "legalDescriptionText";

    /** constant for taglineText property. */
    String TAGLINE_TEXT = "taglineText";

    /** constant for backgroundImage property. */
    String BACKGROUND_IMAGE = "backgroundImage";

    /** constant for pickUpLocationPlaceholder property. */
    String PICKUP_LOCATION_PLACEHOLDER = "pickUpLocationPlaceholder";

    /** constant for pickUpLocationCancelCTA property. */
    String PICKUP_LOCATION_CANCEL_CTA = "pickUpLocationCancelCTA";

    /** constant for pickUpDateFieldLabel property. */
    String PICKUP_DATEFIELD_LABEL = "pickUpDateFieldLabel";

    /** constant for pickUpDateTime property. */
    String PICKUP_DATETIME = "pickUpDateTime";

    /** constant for currentLocationLink property. */
    String CURRENT_LOCATION_LINK = "currentLocationLink";

    /** constant for dropOffLocation property. */
    String DROPOFF_LOCATION = "dropOffLocation";

    /** constant for dropOffLocationPlaceholderText property. */
    String DROPOFF_LOCATION_PLACEHOLDER_TEXT = "dropOffLocationPlaceholderText";

    /** constant for dropOffDate property. */
    String DROPOFF_DATE = "dropOffDate";

    /** constant for dropOffTime property. */
    String DROPOFF_TIME = "dropOffTime";

    /** constant for mapViewLink property. */
    String MAP_VIEW_LINK = "mapViewLink";

    /** constant for selectCTALabel property. */
    String SELECT_CTA_LABEL = "selectCTALabel";

    /** constant for listViewLinkText property. */
    String LIST_VIEW_LINK_TEXT = "listViewLinkText";

    /** constant for discountCode property. */
    String DISCOUNT_CODE = "discountCode";

    /** constant for ageSelector property. */
    String AGE_SELECTOR = "ageSelector";

    /** constant for logInCTA property. */
    String LOGIN_CTA = "logInCTA";

    /** constant for logInTargetURL property. */
    String LOGIN_TARGET_URL = "logInTargetURL";

    /** constant for logInTargetType property. */
    String LOGIN_TARGET_TYPE = "logInTargetType";

    /** constant for continueCTA property. */
    String CONTINUE_CTA = "continueCTA";

    /** constant for continueTargetURL property. */
    String CONTINUE_TARGET_URL = "continueTargetURL";

    /** constant for continueTargetType property. */
    String CONTINUE_TARGET_TYPE = "continueTargetType";

    /** Constants for Location Categories. */
    /** Constant for variable air */
    String AIR = "air";

    /** Constant for variable rail. */
    String RAIL = "rail";

    /** Constant for variable hle. */
    String HLE = "hle";

    /** Constant for variable icon. */
    String ICON = "icon";

    /** Constant for variable priority. */
    String PRIORITY = "priority";

    /** Constant for variable label. */
    String LABEL = "label";

    /** Constant for variable maximumNumberToDisplay. */
    String MAXIMUM_NUMBER_TO_DISPLAY = "maximumNumberToDisplay";

    /** Constant for variable flyoutItemTxt. */
    String FLYOUT_ITEM_TEXT = "flyoutItemTxt";

    /** Constant for variable flyoutItemPath. */
    String FLYOUT_ITEM_PATH = "flyoutItemPath";

    /** Constant for openUrlNewWindow property. */
    String OPEN_URL_IN_NEW_WINDOW = "openUrlNewWindow";

    /** Constant for components. */
    String COMPONENTS = "components";

    /** Constant for variable key. */
    String KEY = "key";

    /** Constant for variable value. */
    String VALUE = "value";

    /** Constant for default rq code. */
    String DEFAULT_RQ_CODE = "default-rq-code";

    /** Path of Data-template. */
    String DATA_TEMPLATE_PATH = "/apps/hertz/templates/data";

    /** Path of Data Page Component. */
    String DATA_PAGE_COMPONENT_PATH = "/apps/hertz/components/pages/data";

    /** Path of Usecase config template. */
    String USECASE_CONFIG_TEMPLATE_PATH = "/apps/hertz/templates/usecaseconfig";

    /** Path of City Data-template. */
    String CITY_DATA_TEMPLATE_PATH = "/apps/hertz/templates/citydata";

    /** Path of province Data-template. */
    String PROVINCE_DATA_TEMPLATE_PATH = "/apps/hertz/templates/provincedata";

    /** Path of Destination country-template. */
    String DESTINATION_COUNTRY_TEMPLATE_PATH = "/apps/hertz/templates/destinationcountry";

    /** Path of country language template. */
    String COUNTRY_LANGUAGE_TEMPLATE_PATH = "/apps/hertz/templates/countrylanguage";

    /** Path of rapid template. */
    String RAPID_TEMPLATE_PATH = "/apps/hertz/templates/rapid";

    /** Rapid template resource type. */
    String RAPID_RESOURCE_TYPE = "/apps/hertz/components/pages/rapid";
    
    /**  Splash template resource type. */
    String SPLASH_RESOURCE_TYPE = "/apps/hertz/components/pages/splash";

    /**
     * Constant for locale config page property (reservationConfigPagePath) that
     * locates the reservations page.
     */
    String RESERVATION_CONFIG_PAGE_PATH = "reservationConfigPagePath";

    /** constant for colon. */
    String COLON = ":";

    /** constant for linkNameKey field in link pair component. */
    String LINK_NAME_KEY = "linkNamekey";

    /** constant for linkNameValue field in link pair component. */
    String LINK_NAME_VALUE = "linkNameValue";

    /** constant for linkURLKey field in link pair component. */
    String LINK_URL_KEY = "linkURLKey";

    /** constant for linkURLValue field in link pair component. */
    String LINK_URL_VALUE = "linkURLValue";

    /** constant for openLinkInNewWindowKey field in link pair component. */
    String OPEN_NEW_WINDOW_KEY = "openLinkInNewWindowKey";

    /** constant for openLinkInNewWindowValue field in link pair component. */
    String OPEN_NEW_WINDOW_VALUE = "openLinkInNewWindowValue";

    /** constant for the name of the parsys node. */
    String PARSYS = "parsys";

    /** constant for responsive grid field resource type. */
    String RESPONSIVE_GRID_RES_TYPE = "wcm/foundation/components/responsivegrid";

    /** constant for configurableMultiTextItems property. */
    String CONFIGURED_TEXT = "configurableMultiTextItems";

    /** constant for configurableMultiLinkItems property. */
    String CONFIGURED_LINKS = "configurableMultiLinkItems";

    /** constant for configurableMultiGroupItems property. */
    String CONFIGURED_MULTI_GROUPS = "configurableMultiGroupItems";

    /** constant for configurableMultiGroupCodeKey property. */
    String CONFIGURED_MULTI_GROUPS_CODE_KEY = "configurableMultiGroupCodeKey";

    /** constant for configurableMultiGroupCodeValue property. */
    String CONFIGURED_MULTI_GROUPS_CODE_VALUE = "configurableMultiGroupCodeValue";

    /** constant for configurableMultiGroupTextKey property. */
    String CONFIGURED_MULTI_GROUPS_TEXT_KEY = "configurableMultiGroupTextKey";

    /** constant for configurableMultiGroupTextValue property. */
    String CONFIGURED_MULTI_GROUPS_TEXT_VALUE = "configurableMultiGroupTextValue";

    /** constant for configurableMultiGroupDescriptionKey property. */
    String CONFIGURED_MULTI_GROUPS_DESCRIPTION_KEY = "configurableMultiGroupDescriptionKey";

    /** constant for configurableMultiGroupDescriptionValue property. */
    String CONFIGURED_MULTI_GROUPS_DESCRIPTION_VALUE = "configurableMultiGroupDescriptionValue";

    /** constant for configurableMultiGroupsElementName property. */
    String CONFIGURED_MULTI_GROUPS_ELEMENT_NAME = "configurableMultiGroupsElementName";

    /** constant for configurableMultiGroupsElementGroup property. */
    String CONFIGURED_MULTI_GROUPS_ELEMENT_GROUP = "configurableMultiGroupsElementGroup";

    /** constant for configurableGroupName property. */
    String CONFIGURED_GROUP_NAME = "configurableGroupName";

    /** constant for configurableMultiGroupKey property. */
    String CONFIGURED_MULTI_GROUP_KEY = "configurableMultiGroupKey";

    /** constant for configurableMultiGroupValue property. */
    String CONFIGURED_MULTI_GROUP_VALUE = "configurableMultiGroupValue";

    /** constant for group name property. */
    String GROUP_NAME = "groupname";

    /** constant for multi groups property. */
    String MULTI_GROUPS = "multiGroups";

    /** constant for configurableMultiLinkItems property. */
    String CONFIGURED_RADIO_BUTTONS = "configurableMultiRadioButtonItems";

    /** constant for configurableMultiCheckboxItems property. */
    String CONFIGURED_CHECKBOXES = "configurableMultiCheckboxItems";

    /** constant for configurableMultiTextAreaItems property. */
    String CONFIGURED_TEXTAREA = "configurableMultiTextAreaItems";

    /** constant for configurableMultiImageItems node name. */
    String CONFIGURED_IMAGE = "configurableMultiImageItems";

    /** constant for configurableImageAltTextKey property. */
    String CONFIGURED_IMAGE_ALT_TEXT_KEY = "configurableImageAltTextKey";

    /** constant for configurableImageAltTextValue property. */
    String CONFIGURED_IMAGE_ALT_TEXT_VALUE = "configurableImageAltTextValue";

    /** constant for configurableMultiValuesKey property. */
    String CONFIGURED_MULTI_VALUES_KEY = "configurableMultiValuesKey";

    /** constant for configurableMultiValueKey property. */
    String CONFIGURED_MULTI_VALUE_KEY = "configurableMultiValueKey";

    /** constant for configurableMultiValue property. */
    String CONFIGURED_MULTI_VALUE = "configurableMultiValue";

    /** constant for configurableMultiValues property. */
    String CONFIGURED_MULTI_VALUES = "configurableMultiValues";

    /** constant for configurableMultiErrorMessagesItems property. */
    String CONFIGURED_MULTI_ERROR_MESSAGES_ITEMS = "configurableMultiErrorMessagesItems";

    /** constant for configurableErrorCodeKey property. */
    String CONFIGURED_ERROR_CODE_KEY = "configurableErrorCodeKey";

    /** constant for configurableErrorCodeValue property. */
    String CONFIGURED_ERROR_CODE_VALUE = "configurableErrorCodeValue";

    /** constant for configurableErrorMessageTextKey property. */
    String CONFIGURED_ERROR_MESSAGE_KEY = "configurableErrorMessageTextKey";

    /** constant for configurableErrorMessageTextValue property. */
    String CONFIGURED_ERROR_MESSAGE_VALUE = "configurableErrorMessageTextValue";

    /** constant for configurableTextLabelKey property. */
    String KEY_TEXT = "configurableTextLabelKey";

    /** constant for configurableTextLabelValue property. */
    String VALUE_TEXT = "configurableTextLabelValue";

    /** constant for configurableTextAriaLabelKey property. */
    String CONFIGURED_TEXT_ARIA_LABEL_KEY = "configurableTextAriaLabelKey";

    /** constant for configurableTextAriaLabelKey property. */
    String CONFIGURED_TEXT_ARIA_LABEL_VALUE = "configurableTextAriaLabelValue";

    /** constant for configurableTextErrorKey property. */
    String CONFIGURED_TEXT_ERROR_KEY = "configurableTextErrorKey";

    /** constant for configurableTextErrorValue property. */
    String CONFIGURED_TEXT_ERROR_VALUE = "configurableTextErrorValue";

    /** constant for element property. */
    String ELEMENT = "element";

    /** constant for elementGroup property. */
    String ELEMENT_GROUP = "elementGroup";

    /** constant for configurableTextElementName property. */
    String CONFIGURED_TEXT_ELEMENT = "configurableTextElementName";

    /** constant for configurableTextAreaElementName property. */
    String CONFIGURED_TEXT_AREA_ELEMENT_NAME = "configurableTextAreaElementName";

    /** constant for configurableTextAreaElementName property. */
    String CONFIGURED_MULTI_VALUES_ELEMENT_NAME = "configurableMultiValuesElementName";

    /** constant for configurableCheckboxElementGroup property. */
    String CONFIGURED_CHECKBOX_ELEMENT_GROUP = "configurableCheckboxElementGroup";

    /** constant for configurableCheckboxElement property. */
    String CONFIGURED_CHECKBOX_ELEMENT = "configurableCheckboxElementName";

    /** constant for configurableCheckboxKey property. */
    String CHECKBOX_KEY = "configurableCheckboxKey";

    /** constant for configurableCheckboxValue property. */
    String CHECKBOX_VALUE = "configurableCheckboxValue";

    /** constant for configurableCheckboxAriaLabelKey property. */
    String CONFIGURED_CHECKBOX_ARIA_LABEL_KEY = "configurableCheckboxAriaLabelKey";

    /** constant for configurableCheckboxAriaLabelValue property. */
    String CONFIGURED_CHECKBOX_ARIA_LABEL_VALUE = "configurableCheckboxAriaLabelValue";

    /** constant for configurableCheckboxOrderKey property. */
    String CONFIGURED_CHECKBOX_ORDER_KEY = "configurableCheckboxOrderKey";

    /** constant for configurableCheckboxOrderValue property. */
    String CONFIGURED_CHECKBOX_ORDER_VALUE = "configurableCheckboxOrderValue";

    /** constant for configurableIsDefaultCheckbox key property. */
    String IS_DEFAULT_CHECKBOX_KEY = "configurableCheckboxIsDefaultKey";

    /** constant for configurableIsDefaultCheckbox property. */
    String IS_DEFAULT_CHECKBOX_VALUE = "configurableCheckboxIsDefaultValue";

    /** constant for configurableRadioButtonElementGroup property. */
    String CONFIGURED_RADIO_BUTTON_ELEMENT_GROUP = "configurableRadioButtonElementGroup";

    /** constant for configurableRadioButtonElement property. */
    String CONFIGURED_RADIO_BUTTON_ELEMENT = "configurableRadioButtonElement";

    /** constant for configurableRadioButtonAriaLabelKey property. */
    String CONFIGURED_RADIO_BUTTON_ARIA_LABEL_KEY = "configurableRadioButtonAriaLabelKey";

    /** constant for configurableRadioButtonAriaLabelValue property. */
    String CONFIGURED_RADIO_BUTTON_ARIA_LABEL_VALUE = "configurableRadioButtonAriaLabelValue";

    /** constant for configurableRadioButtonOrderKey property. */
    String CONFIGURED_RADIO_BUTTON_ORDER_KEY = "configurableRadioButtonOrderKey";

    /** constant for configurableRadioButtonOrderValue property. */
    String CONFIGURED_RADIO_BUTTON_ORDER_VALUE = "configurableRadioButtonOrderValue";

    /** constant for configurableIsDefaultCheckbox key property. */
    String IS_DEFAULT_RADIO_BUTTON_KEY = "configurableRadioButtonIsDefaultKey";

    /** constant for configurableIsDefaultCheckbox property. */
    String IS_DEFAULT_RADIO_BUTTON_VALUE = "configurableRadioButtonIsDefaultValue";

    /** constant for configurableRadioButtonKey property. */
    String RADIO_BUTTON_KEY = "configurableRadioButtonKey";

    /** constant for configurableRadioButtonValue property. */
    String RADIO_BUTTON_VALUE = "configurableRadioButtonValue";

    /** constant for configurableTextAreaKey property. */
    String KEY_TEXTAREA = "configurableTextAreaKey";

    /** constant for configurableTextAreaValue property. */
    String VALUE_TEXTAREA = "configurableTextAreaValue";

    /** constant for configurableLinkElementGroup property. */
    String CONFIGURED_LINK_ELEMENT_GROUP = "configurableLinkElementGroup";

    /** constant for configurableLinkElement property. */
    String CONFIGURED_LINK_ELEMENT = "configurableLinkElement";

    /** constant for configurableLinkAriaLabelKey property. */
    String CONFIGURED_LINK_ARIA_LABEL_KEY = "configurableLinkAriaLabelKey";

    /** constant for configurableLinkAriaLabelKey property. */
    String CONFIGURED_LINK_ARIA_LABEL_VALUE = "configurableLinkAriaLabelValue";

    /** constant for configurableLinkTextKey property. */
    String KEY_LINK_TEXT = "configurableLinkTextKey";

    /** constant for configurableLinkTextValue property. */
    String VALUE_LINK_TEXT = "configurableLinkTextValue";

    /** constant for configurableLinkURLKey property. */
    String KEY_LINK_URL = "configurableLinkURLKey";

    /** constant for configurableLinkURLValue property. */
    String VALUE_LINK_URL = "configurableLinkURLValue";

    /** constant for configurableLinkTargetTypeKey property. */
    String KEY_LINK_TARGET_TYPE = "configurableLinkTargetTypeKey";

    /** constant for configurableLinkTargetTypeValue property. */
    String VALUE_LINK_TARGET_TYPE = "configurableLinkTargetTypeValue";

    /** constant for configurableImageElementName property. */
    String CONFIGURED_IMAGE_ELEMENT = "configurableImageElementName";

    /** constant for configurableImageKey property. */
    String KEY_IMAGE = "configurableImageKey";

    /** constant for configurableImageValue property. */
    String VALUE_IMAGE = "configurableImageValue";

    /** constant for configurableImageLinkURLKey property. */
    String KEY_IMAGE_URL_LINK = "configurableImageLinkURLKey";

    /** constant for configurableImageLinkURLValue property. */
    String VALUE_IMAGE_URL_LINK = "configurableImageLinkURLValue";

    /** constant for configurableImageLinkTargetTypeKey property. */
    String KEY_IMAGE_LINK_TARGET_TYPE = "configurableImageLinkTargetTypeKey";

    /** constant for configurableImageLinkTargetTypeValue property. */
    String VALUE_IMAGE_LINK_TARGET_TYPE = "configurableImageLinkTargetTypeValue";

    /**
     * constant for _jcr_content - This is needed by multifield while fetching
     * values.
     */
    String JCR_CONTENT_REQ = "_jcr_content";

    /** constant for data property. */
    String DATA = "data";

    /** constant for city data property. */
    String CITY_DATA = "citydata";

    /** constant for province data property. */
    String PROVINCE_DATA = "provincedata";

    /** constant for destination country property. */
    String DESTINATION_COUNTRY = "destinationcountry";

    /** constant for job topic for microservices. */
    String MC_UPDATE_JOB_TOPIC = "com/sling/hertz/microservices/update";

    /** constant for success. */
    String SUCCESS = "success";

    /** constant for content type form url encoded. */
    String CONTENT_TYPE_FORM_ENCODED = "application/x-www-form-urlencoded";

    /** constant for content tye application/json. */
    String CONTENT_TYPE_APP_JSON = "application/json";

    /** constant for string failure. */
    String FAILURE = "Failure";

    /** constanr for string passwordGrant. */
    String PASS_GRANT = "passwordGrant";

    /** constant for base path. */
    String BASE_PATH = "basePath";

    /** constant for token path. */
    String TOKEN_PATH = "tokenPath";

    /** constant for resourcePath. */
    String RESOURCE_PATH = "resourcePath";

    /** constant for servicePath. */
    String SERVICE_PATH = "servicePath";

    /**
     * constant for configuration property hertz microservice password grant.
     */
    String HERTZ_MC_PASSGRANT = "hertz.mc.passwordgrant";
    
    
    /** The hertz fed passgrant. */
    String HERTZ_FED_PASSGRANT = "hertz.fed.passwordgrant";

    /** constant for configuration property hertz token ingestion path. */
    String HERTZ_TOKEN_INGESTION_PATH = "hertz.token.ingestion.path";

    /** constant for configuration property hertz mc basepath. */
    String HERTZ_MC_BASEPATH = "hertz.mc.basepath";
    
    /** The hertz fed basepath. */
    String HERTZ_FED_BASEPATH = "hertz.fed.basepath";

    /** constant for configuration property hertz data api mapping. */
    String HERTZ_DATA_API_MAPPING = "hertz.data.api.mapping";

    /** consant for configuration property hertz data basepath. */
    String HERTZ_DATA_BASEPATH = "hertz.data.basepath";

    /** constant for pwd grant token node name. */
    String PWD_GRANT_TOKEN_NODE_NAME = "pwd_grant_token";

    /** constant for author mode. */
    String AUTHOR_MODE = "author";

    /** constant for grant_type. */
    String GRANT_TYPE = "grant_type";

    /** constant for equalto. */
    String EQUALTO = "=";

    /** constant for configuration property hertz data template whitelist. */
    String HERTZ_DATA_TEMPLATE_WHITELIST = "hertz.data.template.whitelist";

    /** constant for configuration property hertz data coordinator mapping. */
    String HERTZ_DATA_COORDINATOR_MAPPING = "hertz.data.coordinator.mapping";

    /** constant for jsonString. */
    String JSON_STRING = "jsonString";

    /** constant for dataPath. */
    String DATA_PATH = "dataPath";

    /** constant for string identifier. */
    String IDENTIFIER = "identifier";

    /** constant for dam. */
    String DAM_IDENTIFIER = "dam";

    /** constant for partnerPagePath property. */
    String PARTNER_PAGE_PATH = "partnerPagePath";

    /** constant for hero node name under page node. */
    String HERO = "hero";

    /** constant for reservation node name under page node. */
    String RESERVATION = "reservation";

    /** constant for rateTypesConfig property. */
    String RATE_TYPE_CONFIG = "rateTypesConfig";

    /** constant for ratetype path property. */
    String RATE_TYPE = "ratetype";

    /** constant for orderNumber property. */
    String ORDER_NUMBER = "orderNumber";

    /** constant for authorableFields. */
    String AUTHORABLE_FIELDS = "authorableFields";

    /** constant for property configurableImageKey2. */
    String CONFIGURABLE_IMAGE_KEY_2 = "configurableImageKey2";

    /** constant for property configurableImageValue2. */
    String CONFIGURABLE_IMAGE_VALUE_2 = "configurableImageValue2";

    /** Constant for footer template path. */
    String FOOTER_TEMPLATE_PATH = "/apps/hertz/templates/footer";

    /** Constant for DataContent constant for data json. */
    String DATA_CONTENT = "datacontent";

    /** Constant for SpaContent constant for spa json. */

    String SPA_CONTENT = "spacontent";

    /** Constant for text property of rich text. */
    String TEXT = "text";

    /** constant for configuration property hertz.spa.allowedComponents */
    String Hertz_SPA_ALLOWED_COMPONENTS = "hertz.spa.allowedComponents";

    /** constant for configuration property hertz.data.allowedComponents */
    String Hertz_DATA_ALLOWED_COMPONENTS = "hertz.data.allowedComponents";

    /** The not. */
    String NOT = "not";

    /** constant for configtext-parsys. */
    String CONFIG_TEXT_PARSYS = "configtext-parsys";

    /** constant for property configurableMultiSEOItems. */
    String CONFIGURABLE_MULTI_SEO_ITEMS = "configurableMultiSEOItems";

    /** constant for property configurableMultiSEOItems. */
    String CONFIGURABLE_SEO_BING_VERIFICATION_TAG = "bingVerificationTag";

    /** constant for property configurableMultiSEOItems. */
    String CONFIGURABLE_SEO_GOOGLE_VERIFICATION_TAG = "googleVerificationTag";

    /** constant for configurableSeoKey. */
    String CONFIGURABLE_SEO_KEY = "configurableSeoKey";

    /** constant for configurableSeoValue. */
    String CONFIGURABLE_SEO_VALUE = "configurableSeoValue";

    /** constant for title. */
    String TITLE = "title";

    /** constant for google-site-verification. */
    String GOOGLE_SITE_VERIFICATION = "google-site-verification";

    /** constant for bing-site-verification. */
    String BING_SITE_VERIFICATION = "msvalidate.01";

    /** constant for description. */
    String DESCRIPTION = "description";

    /** constant for website. */
    String WEBSITE = "website";

    /** constant for "og:type". */
    String OG_TYPE = "og:type";

    /** constant for configured properties. */
    String CONFIGURED_PROPS = "configuredProps";

    /** component path for responsive grid. */
    String RESPONSIVE_GRID_PATH = "wcm/foundation/components/responsivegrid";

    /** component select radio field pair. */
    String SELECT_RADIO_FIELD_PAIR = "selectradiofieldpair";

    /** DEFAULT VALUE. */
    String DEFAULT_VALUE = "defaultValue";

    /** ARIA LABEL. */
    String ARIA_LABEL = "ariaLabel";

    /** OPTIONS LIST. */
    String OPTIONS_LIST = "optionsList";

    /** categoryname. */
    String CATEGORYNAME = "categoryname";

    /** RANK. */
    String RANK = "rank";

    /** headline. */
    String HEADLINE = "headline";
    
    /** headline first line. */
    String HEADLINE_FIRST = "headlineFirstLine";
    
    /** headline second line. */
    String HEADLINE_SECOND = "headlineSecondLine";

    /** subhead. */
    String SUBHEAD = "subhead";

    /** fileBadgeReference. */
    String FILE_BADGE_REFERENCE = "fileBadgeReference";

    /** fileLogoReference. */
    String FILE_LOGO_REFERENCE = "fileLogoReference";

    /** fileImageReference. */
    String FILE_IMAGE_REFERENCE = "fileImageReference";

    /** ctaLabel. */
    String CTA_LABEL = "actionHref";

    /** ctaAction. */
    String CTA_ACTION = "actionCTA";

    /** attributes. */
    String ATTRIBUTES = "attributes";

    /** filter. */
    String FILTER = "filter";

    /** offerCategory. */
    String OFFER_CATEGORY = "offerCategory";

    /** NAME. */
    String NAME = "name";

    /** constant for pcCode property on offer details page. */
    String PC_CODE = "pcCode";

    /** constant for please select property in hertz links dropdown. */
    String PLEASE_SELECT_TEXT = "pleaseSelectText";

    /** constant for cdpCode property on offer details page. */
    String CDP_CODE = "cdpCode";

    /** constant for widgetToDisplay property on offer details page. */
    String WIDGET_TO_DISPLAY = "widgetToDisplay";

    /**
     * constant for configurablePartnerMultiValue property on offer details
     * page.
     */
    String CONFIGURABLE_PARTNER_VALUES = "configurablePartnerMultiValue";

    /** String constant for Partner Program Lookup value. */
    String PARTNER_PROGRAM_VALUE = "Partner Program Lookup";

    /** constant for partnerInfo key in the json. */
    String PARTNER_INFO = "partnerInfo";

    /** constant for ctaHref property on the offer details page. */
    String CTA_HREF = "ctaHref";

    /** constant for image node name. */
    String IMAGE = "image";

    /** constant for badge node name. */
    String BADGE = "badge";

    /** constant for newBurstStartDate property on the offer details page. */
    String NEW_BURST_START_DATE = "newBurstStartDate";

    /** constant for newBurstEndDate property on the offer details page. */
    String NEW_BURST_END_DATE = "newBurstEndDate";

    /** constant for memberType property on the offer details page. */
    String MEMBER_TYPE = "memberType";

    /** constant for posCountries property on the offer details page. */
    String POS_COUNTRIES = "posCountries";

    /** constant for channel property on the offer details page. */
    String CHANNEL = "channel";

    /** constant for cpCode property on the offer details page. */
    String CP_CODE = "cpCode";

    /** constant for indexPageStartDate property on the offer details page. */
    String INDEX_PAGE_START_DATE = "indexPageStartDate";

    /** constant for indexPageExpiryDate property on the offer details page. */
    String INDEX_PAGE_EXPIRY_DATE = "indexPageExpiryDate";

    /** constant for contentExpiryDate property on the offer details page. */
    String CONTENT_EXPIRY_DATE = "contentExpiryDate";

    /** constant for cdpRestriction property on the offer details page. */
    String CDP_RESTRICTION = "cdpRestriction";

    /** constant for categoryPath property on the offer details page. */
    String CATEGORY_PATH = "categoryPath";

    /** constant for offers node in the json. */
    String OFFERS = "offers";

    /** constant for partnerName property. */
    String PARTNER_NAME = "partnerName";

    /** constant for partnerProgramCdpCode property. */
    String PARTNER_CDP_CODE = "partnerProgramCdpCode";

    /** Node name for parsys. */
    String CONFIGTEXT_PARSYS = "configtext-parsys";

    /** Node name for marketing slot 1 on offer details page. */
    String MARKETING_SLOT_1 = "marketingslot1";

    /** Node name for marketing slot 2 on offer details page. */
    String MARKETING_SLOT_2 = "marketingslot2";

    /** Path for page component of OfferDetailsPage Template. */
    String OFFER_DETAILS_PAGE = "/apps/hertz/components/pages/offerdetailspage";

    /** Path of language template. */
    String OFFER_CATEGORY_TEMPLATE_PATH = "/apps/hertz/templates/offercategory";

    /** cta label for fferDetails. */
    String CTA_LABEL_OFFER = "ctaLabel";

    /** constant for categories. */
    String CATEGORIES = "categories";

    /** The seo nofollow. */
    String SEO_NOFOLLOW = "rel";

    /** Template path for partner category template. */
    String PARTNER_CATEGORY_TEMPLATE = "/apps/hertz/templates/partnercategory";

    /** Template for partner details page. */
    String PARTNER_DETAIL_TEMPLATE = "/apps/hertz/templates/partnerdetail";

    /** Constant for hero subTagLineText. */
    String HERO_SUB_TAGLINE_TEXT = "subTagLineText";

    /** The native parsys. */
    String NATIVE_PARSYS = "native-parsys";

    /** The native content. */
    String NATIVE_CONTENT = "nativecontent";

    /** The bck slash. */
    String BCK_SLASH = "\\";

    /** The default. */
    String DEFAULT = "default";

    /** The sub title. */
    String SUB_TITLE = "subtitle";

    /** The link. */
    String LINK = "link";

    /** The link type. */
    String LINK_TYPE = "linkType";

    /** The sequence id. */
    String SEQUENCE_ID = "sequenceId";

    /** The target type. */
    String TARGET_TYPE = "targetType";

    /** The rq code. */
    String RQ_CODE = "rqCode";

    /** The country label. */
    String COUNTRY_LABEL = "countryLabel";

    /** The language label. */
    String LANGUAGE_LABEL = "languageLabel";

    /** The button label. */
    String BUTTON_LABEL = "buttonLabel";

    /** The update message. */
    String UPDATE_MESSAGE = "updateMessage";

    /** The no results message. */
    String NO_RESULTS_MESSAGE = "noresultsMessage";

    /** The URI. */
    String URI = "URI";
    
    /** The json script. */
    String JSON_SCRIPT = "jsonScript";

    /** The IRAC. */
    String IRAC = "irac";
	 /** The iracLink. */
    String IRACLINK = "iracLink";
    /** The service. */
    String SERVICE="service";
	
    /** The Update Heading message. */
    String UPDATE_HEADING_MESSAGE = "updateHeading";
    
    /** The Cancel button label. */
    String CANCEL_BUTTON_LABEL = "cancelButton";
    
    /** The Continue button label. */
    String CONTINUE_BUTTON_LABEL = "continueButton";
    
    /** The error code. */
    String ERROR_CODE="errorCode";
    
    /** The error value. */
    String ERROR_VALUE="errorValue";
    
    /** constant for countrylanguageRedirect. */
    String COUNTRYLANGUAGEREDIRECT = "countrylangredirect";
    
    /** The Cancel button label. */
    String REDIRECT_HEADING = "redirectHeading";
    
    /** The Cancel button label. */
    String REDIRECT_MESSAGE = "redirectMessage";
    
    /** constant for countryconfigcomp. */
    String COUNTRY_CONFIG_COMP = "countryconfigcomp";
    
    /** constant for pos. */
    String POS = "pos";
    
    /** constant for isirac. */
    String IS_IRAC = "isirac";
    
    /** constant for db2Category*/
    String DB2_CATEGORY="db2Category";
    
    /** constant for contentPath*/
    String CONTENT_PATH="contentPath";
    
    /** The preview mode path. */
    String PREVIEW_MODE_PATH="previewModePath";
    
    /** The hertz fed homepage path. */
    String HERTZ_FED_HOMEPAGE_PATH="hertz.fed.homepage.path";

    /** constant for linkdropdown country Code variable. */
	String LINK_DROPDOWN_COUNTRY_CODE = "countryCode";
	
    /** constant for preview auth token variable. */
	String PREVIEW_AUTH_TOKEN = "preview-auth-token";
	
	/** The secret file name. */
	String SECRET_FILE_NAME="secrets.bin";

}
