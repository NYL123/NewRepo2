"use strict";
use(function() {
        var CONST = {
            END_DATE: "endDateText",
            START_DATE: "startDateText",
            END_GROUP: "endGroup",
            PATHFIELD: "cardActionUrl"
        }

		console.log("startDate   --------" + granite.resource.properties[CONST.END_DATE] );
        var startDate = new Date((null != granite.resource.properties[CONST.START_DATE] || undefined != granite.resource.properties[CONST.START_DATE]) ? granite.resource.properties[CONST.START_DATE] :"");
		var endDate = new Date((null != granite.resource.properties[CONST.END_DATE] || undefined != granite.resource.properties[CONST.END_DATE]) ? granite.resource.properties[CONST.END_DATE]:"");
		var endRow = (null != granite.resource.properties[CONST.END_GROUP] || undefined != granite.resource.properties[CONST.END_GROUP]) ? granite.resource.properties[CONST.END_GROUP]:"";
		var initialPath = granite.resource.properties[CONST.PATHFIELD];
    	var shortenedPath = "";

		// console.log("startDate   --------" + startDate);
    	console.log("endDate  ---------" + endDate);
        var result = {};
		var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
		if(startDate != "Invalid Date"){
			result.startDate = monthNames[startDate.getMonth()] + " " + startDate.getDate() +", " + startDate.getFullYear();
        }
        if(endDate != "Invalid Date"){
            result.endDate = monthNames[endDate.getMonth()] + " " + endDate.getDate() +", " + endDate.getFullYear();
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
    	result.endRow = endRow;
		return result;

    });