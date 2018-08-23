"use strict";
use(function() {

        var CONST = {
            PATHFIELD: "buttoncta"
        }
        var result = {};
		var initialPath = granite.resource.properties[CONST.PATHFIELD];
		var shortenedPath = "";
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
    return result;
    }

);