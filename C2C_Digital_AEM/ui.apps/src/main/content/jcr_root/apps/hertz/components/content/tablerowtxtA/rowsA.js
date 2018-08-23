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

       var items = [];
	   var counter = 0;

    

    	items=colH;
    	if (items != null && Object.prototype.toString.call(items) == CONST.STRING_JAVA_ARRAY) {
            for (i in items) {
              // columnHeaders[i] = JSON.parse(items[i]).columnname;
               ////console.log("Comparision GridJSON.parse(items)************** "+ JSON.parse(items[i]).columnname);

				var colname= JSON.parse(items[i]).columnname;

                     columnHeaders[i] = JSON.parse(items[i]).columnname;
                    console.log("****here******" + columnHeaders[i]);

                
            }
        } else if (items != null && Object.prototype.toString.call(items) == CONST.STRING_JAVA_OBJECT) {
              // columnHeaders[0] = JSON.parse(items);

				var colname= JSON.parse(items[0]).columnname;
                if(colname.lastIndexOf("/") > -1){


					 //getResourceProps(JSON.parse(items[0]).columnname,0 );

                }else{

                     columnHeaders[0] = JSON.parse(items[0]).columnname;

                }

        }

        var  title = granite.resource.properties["rowtitle"];

        var result={}
        var row = resource.resourceResolver.resolve(resource.path + "/" + CONST.ROW);
    	var col=[];
    	var sub=[];
    	var td = [];
    	var game="";
    	var class = [];
    	var text = [];
    	var rowTitle = (null !=resource.properties.rowtitle || undefined != resource.properties.rowtitle) ? resource.properties.rowtitle :"notitle"; 
        if(null !=row.properties ){

            while(counter < columncount){
                var propertyName = "column" + counter;
                var subPropertyName = "subcolumn" + counter;
                var text1Prop = "columnText1Style" + counter;
                var text2Prop = "columnText2Style" + counter;
            	var selection = (null !=row && undefined != row.properties[propertyName]) ? row.properties[propertyName] :"";
				var subtext = (null !=row && undefined != row.properties[subPropertyName]) ? row.properties[subPropertyName] :"";
				var classText1 = (null !=row && undefined != row.properties[text1Prop]) ?  row.properties[text1Prop] :"";
				var classText2 = (null !=row && undefined != row.properties[text2Prop]) ? row.properties[text2Prop] :"";
                col[counter] = selection;
                sub[counter] = subtext;
                var class1="";
                var class2="";
                if(classText1=="table-pattern-d-accordion-lbl1"){
					class1="table-pattern-a-usd-lbl";
                }else if(classText1=="table-pattern-d-accordion-lbl2"){
					class1="table-pattern-a-total-paid-lbl";
                }

 				if(classText2=="table-pattern-d-accordion-lbl1"){
					class2="table-pattern-a-usd-lbl";
                }else if(classText2=="table-pattern-d-accordion-lbl2"){
					class2="table-pattern-a-total-paid-lbl";
                }

                if(class1!="" && class2!=""){
					td[counter] = "<span class='"+class1+"'>"+ selection +"</span>"+"<span class='"+class2+"'>"+subtext+ "</span>";
                }else if(class1=="" && class2!=""){
					td[counter] = selection +"<span class='"+class2+"'>"+subtext+ "</span>";
                }else if(class1!="" && class2==""){
					td[counter] = "<span class='"+class1+"'>"+ selection +"</span>"+subtext;
                }else if(class1=="" && class2==""){
					td[counter] = selection +subtext;
                }


				//td[counter] = selection + subtext ;
				counter= counter+1;

            }



        }



		////console.log("******** columns ********game***************  " + game );
		result.col = col;
    	result.sub = sub;
    	result.td = td;
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