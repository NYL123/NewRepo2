"use strict";
use(function() {
    var result = {};
    var patternchoice = (null != granite.resource.properties["patternchoice"] || 
                      undefined != granite.resource.properties["patternchoice"]) ? granite.resource.properties["patternchoice"] :"";
	result.resourcepath = "hertz/components/content/" + patternchoice;
    return result;
});