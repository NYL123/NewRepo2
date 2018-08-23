"use strict";
use(function() {
        var CONST = {
            REF:"refPath",

        }
	   var result={}
       var referenceProp = properties.get(CONST.REF);
    if(referenceProp!=null) {
        var res= resource.resourceResolver.resolve(referenceProp+"/jcr:content/par");
                 if(res.resourceType != "sling:nonexisting"){
                        //console.log("res********************" +res.resourceType );
                         result.resourcePath = referenceProp+"/jcr:content/par";
                        }else if (res.resourceType == "sling:nonexisting") {
                                    result.message = "Please enter correct path.Only content fragment paths can be given.";
                                }
                }else {
                    result.message = " Please choose the path of the content fragment page whose content needs to be rendered.";
      }

	return result;
});