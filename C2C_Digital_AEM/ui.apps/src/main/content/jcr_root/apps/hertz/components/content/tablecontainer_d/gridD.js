"use strict";
use(function() {
        var CONST = {
            PRICE:"price",
            COLUMN_HEADER: "columnheader",

            STRING_JAVA_ARRAY: "[object JavaArray]",
            STRING_JAVA_OBJECT: "[object JavaObject]",

        }


        columnHeaders = [];
    	modalPath = [];

        var items = [];
		
        var result={}

        var rowresource = resource.resourceResolver.resolve(resource.path +"/tablePar");

        items = granite.resource.properties[CONST.COLUMN_HEADER];
       if (items != null && Object.prototype.toString.call(items) == CONST.STRING_JAVA_ARRAY) {
            for (i in items) {
               columnHeaders[i] = JSON.parse(items[i]).columnname;
               var path = JSON.parse(items[i]).modalPath.split('/');
                if(path.length >= 4){
              		 modalPath[i] = path.slice(5,path.length + 1).join('/') ;
                } else {
					modalPath[i] = JSON.parse(items[i]).modalPath;
                    //console.log("Comparision GridJSON.parse(items)************** "+ JSON.parse(items[i]).modalPath);
                }
               //console.log("Comparision GridJSON.parse(items)************** "+ modalPath[i]);
            }
        } else if (items != null && Object.prototype.toString.call(items) == CONST.STRING_JAVA_OBJECT) {
               columnHeaders[0] = JSON.parse(items);
               modalPath[0] = JSON.parse(items);

        }
    //var resourceit=rowresource.listChildren;
    ////console.log("columnHeaders rowresource.children**********  : " + rowresource.hasext);
    //while(resourceit.hasNext){
        //console.log("columnHeaders rowresource.children**********  : " + rowresource.next);
    //}
		result.headers=columnHeaders;
    	result.link=modalPath;
		//result.rowresources=rowresource.children;
      // console.log("************* headers Table B ***********"+ result.headers);
        return result;


    }

); 