"use strict";
use(function() {
    var result = {};
	var CONST = {
            PATHFIELD: "cardActionUrl"
        }
	var initialPath = granite.resource.properties[CONST.PATHFIELD];
    var shortenedPath = "";
    var cardActionUrlTargetType = (null != granite.resource.properties["cardActionUrlTargetType"] || 
                      undefined != granite.resource.properties["cardActionUrlTargetType"]) ? granite.resource.properties["cardActionUrlTargetType"] :"";


	var targetType = "_blank";
    if(!cardActionUrlTargetType){
		targetType="_self";

    }
    if (initialPath != null) {
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

    result.shortenedPath=shortenedPath;
    result.targetType=targetType;

    return result;
});