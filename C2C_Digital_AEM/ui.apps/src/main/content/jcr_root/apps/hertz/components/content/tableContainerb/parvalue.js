"use strict";
use(function() {
        var CONST = {
            PRICE:"price",
            COLUMN_HEADER: "columnheader",
            ROW:"rows",
            OK_CLASS: "glyphicon glyphicon-ok",
            REMOVE_CLASS: "glyphicon glyphicon-remove",
            BLANK_CLASS: "glyphicon",

            STRING_JAVA_ARRAY: "[object JavaArray]",
            STRING_JAVA_OBJECT: "[object JavaObject]",

        }
        var colH = resource.properties.get("columnheader");
    	var columncount = colH.length;
        var result = {};
        var parname = "tablePar/" + this.comp; 
		var headerIndex = this.count;
    	var marker =  resource.properties.get("selectionstyle");

    	var row = resource.resourceResolver.resolve(resource.path +"/"+ parname );
    	var rowNode = resource.resourceResolver.resolve(resource.path +"/"+ parname+ "/"+ CONST.ROW );
    	//console.log("parnameparnameparnameparnameparname******" + rowNode.path + "Count " + this.count);
		columnHeaders = []; 
		var checkmarks = (null !=resource.properties.get("checktext") || undefined != resource.properties.get("checktext")) ? resource.properties.get("checktext") :""; 

     	var items = [];
	    var counter = 0;
		var class ="";

    	items=colH;
    	if (items != null && Object.prototype.toString.call(items) == CONST.STRING_JAVA_ARRAY) {
            for (i in items) {
               columnHeaders[i] = JSON.parse(items[i]).columnname;
               ////console.log("Comparision GridJSON.parse(items)************** "+ JSON.parse(items[i]).columnname);
            }
        } else if (items != null && Object.prototype.toString.call(items) == CONST.STRING_JAVA_OBJECT) {
               columnHeaders[0] = JSON.parse(items);

        }


    	if(marker=="checkmarks" && checkmarks ==""){
                        class=CONST.OK_CLASS;
                }else if(marker=="crossmarks" && checkmarks ==""){
                        class=CONST.REMOVE_CLASS;
           }
    	var rowTitle = (null !=row.properties.rowtitle || undefined != row.properties.rowtitle) ? row.properties.rowtitle :"notitle"; 
    	if( null != rowNode  && null !=rowNode.properties ){


                var propertyName = "column" + headerIndex;
				////console.log("rows row.properties[propertyName] *********"+rowNode.properties[propertyName]);

            if(rowNode.properties[propertyName]=="yes"){
               // //console.log("rows *********"+ propertyName + " : Yes");
					result.name=rowTitle;
            }else{
				result.name="";
                // //console.log("rows *********"+ propertyName + " : No");
            }


        }


			////console.log("rowTitle  ******" + rowTitle + "Count " + this.count);
				result.parname=parname;
    			result.class=class;


				return result;

    });