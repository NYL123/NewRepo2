"use strict";
use(function() {
    var result = {};

     var patternAType = (null != granite.resource.properties["patternAType"] || 
                      undefined != granite.resource.properties["patternAType"]) ? granite.resource.properties["patternAType"] :"";

	var childImageResource;
    var childImageResourcePath;
    var childLogoResource;
    var childLogoResourcePath;
    var imageFileReference;
    var altText;
    var logoFileReference;
	var CONST = {
            PATHFIELD: "linkUrl"
        }
	var initialPath = granite.resource.properties[CONST.PATHFIELD];
    var shortenedPath = "";
    if(patternAType != ""){
        childImageResource=resource.getChild('image');
        childImageResourcePath=childImageResource.getPath();
        childLogoResource=resource.getChild('logo');
		childLogoResourcePath=childLogoResource.getPath();
        imageFileReference=childImageResource.properties.get('fileReference');
        altText=childImageResource.properties.get('imageAltText');
        logoFileReference=childLogoResource.properties.get('fileReference');
    }

    if (initialPath != null) {
        var linkResource = resolver.getResource(initialPath);
        if(linkResource!=null){
			var tokens = initialPath.split("/");
            for(var item in tokens){
                if(item > 4){
                      shortenedPath = shortenedPath + "/" + tokens[item];  
                }
            }
            if(shortenedPath === ""){
                shortenedPath = initialPath;
            }
        }
        else{
        	shortenedPath = initialPath;
        } 
    }

    result.shortenedPath=shortenedPath;

    result.strFileReference=imageFileReference;
    result.strImageAltText=altText;
    result.strLogoFileReference=logoFileReference;
	result.imageResourcePath=childImageResourcePath;
	result.logoResourcePath=childLogoResourcePath;

    return result;
});