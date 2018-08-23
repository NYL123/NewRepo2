"use strict";
use(function() {
        var CONST = {
            ROW:"rows",
            OK_CLASS: "glyphicon glyphicon-ok",
            REMOVE_CLASS: "glyphicon glyphicon-remove",
            BLANK_CLASS: "glyphicon",
  			JCR_NODE :"jcr:content",
            JCR_TITLE:"jcr:title",
            STRING_JAVA_ARRAY: "[object JavaArray]",
            STRING_JAVA_OBJECT: "[object JavaObject]",

        }

       columnHeaders = [];
       var headerResource = resource.parent.parent;
	   var colH = (null !=headerResource.properties.get("columnheader") || undefined != headerResource.properties.get("columnheader")) ? headerResource.properties.get("columnheader") :""; 
	   var columncount = colH.length;
       var marker =  (null !=headerResource.properties.get("selectionstyle") || undefined != headerResource.properties.get("selectionstyle")) ? headerResource.properties.get("selectionstyle") :""; 



       var checkmarks = (null !=headerResource.properties.get("checktext") || undefined != headerResource.properties.get("checktext")) ? headerResource.properties.get("checktext") :""; 
      // //console.log("  ********  checkmarks" +  checkmarks);
       ////console.log("marker" +  marker);
       var items = [];
	   var counter = 0;

    	function getResourceProps(path, i ){
            ////console.log("**********path ************"  + path);
    			var res= resource.resourceResolver.resolve(path +"/" +CONST.JCR_NODE);
            	////console.log("**********res ************"  + res.path);
                if(null != res || undefined != res){
                     //console.log("res title" +res.properties["jcr:title"]);
                     //console.log("res color " +res.properties["color"]);
                     columnHeaders[i] = res.properties["jcr:title"];
                }
        }

    	items=colH;
    	if (items != null && Object.prototype.toString.call(items) == CONST.STRING_JAVA_ARRAY) {
            for (i in items) {
              // columnHeaders[i] = JSON.parse(items[i]).columnname;
               ////console.log("Comparision GridJSON.parse(items)************** "+ JSON.parse(items[i]).columnname);

				var colname= JSON.parse(items[i]).columnname;
                if(colname.lastIndexOf("/") > -1){

					getResourceProps(JSON.parse(items[i]).columnname,i );

                }else{
                     columnHeaders[i] = JSON.parse(items[i]).columnname;

                }
            }
        } else if (items != null && Object.prototype.toString.call(items) == CONST.STRING_JAVA_OBJECT) {
              // columnHeaders[0] = JSON.parse(items);

				var colname= JSON.parse(items[0]).columnname;
                if(colname.lastIndexOf("/") > -1){


					 getResourceProps(JSON.parse(items[0]).columnname,0 );

                }else{

                     columnHeaders[0] = JSON.parse(items[0]).columnname;

                }

        }

        var  title = granite.resource.properties["rowtitle"];

        var result={}
        var row = resource.resourceResolver.resolve(resource.path +"/" + CONST.ROW);
    	var col=[];
    	var game="";
    	var class = [];
    	var text = [];
   		//var ships =[];
    	////console.log("row ***********prop"+ row.properties);
    	var rowTitle = (null !=resource.properties.rowtitle || undefined != resource.properties.rowtitle) ? resource.properties.rowtitle :"notitle"; 
        if(null !=row.properties ){

            while(counter < columncount){
                var propertyName = "column" + counter
				////console.log("rows *********"+row.properties[propertyName]);
            	var selection= (null !=row && undefined != row.properties[propertyName]) ? row.properties[propertyName] :"";
                col[counter] =selection;
                
               /* if(selection=="yes" ){

                    if(marker=="checkmarks" && checkmarks ==""){
                        class[counter]=CONST.OK_CLASS;
                    }else if(marker=="crossmarks" && checkmarks ==""){
                        class[counter]=CONST.REMOVE_CLASS;
                    }else if(checkmarks !=""){
                        class[counter]=CONST.BLANK_CLASS;
						text[counter] = checkmarks;
                    }


                }else if(selection=="no" ){

                    if(marker=="crossmarks" && checkmarks ==""){
                        class[counter]=CONST.BLANK_CLASS;
                    }else if(marker=="checkmarks" && checkmarks ==""){
						class[counter]=CONST.BLANK_CLASS;
                    }else if(checkmarks !=""){
                        	class[counter]=CONST.BLANK_CLASS;
							text[counter] = "";
                    }

                } */
				counter= counter+1;
                /////console.log("counter in if " + counter  +" selection : "+ selection);
            }
            /*if(counter < columncount){
                while(counter < columncount){
                    //console.log("while******** columns " + counter);

					class[counter]=CONST.BLANK_CLASS;

                    counter= counter+1;}
            }*/


        }


        /*for (var i = 0; i< columnHeaders.length; i++){ 
            if(class[i] == CONST.REMOVE_CLASS || class[i] == CONST.OK_CLASS ){
				game="yes";

            }else if(class[i] == CONST.BLANK_CLASS ){
				game="no";
            }
			////console.log("class[i] ************   " +rowTitle + "  ********game***************  " + game );
        }*/
		////console.log("******** columns ********game***************  " + game );
		result.col = col;
    	//result.class=class;
    	result.header=columnHeaders; 
		//result.game=game;
    	result.title=rowTitle;
		//result.text=text ; 
    	result.hasText = (checkmarks !="") ? true :false;
		////console.log ("Comparision grid result hasText" + result.hasText);
		//game="";
        return result;


    }

);