 "use strict";
use(function() {
    var result = {};
	var cssClass=this.cssClass;
	var array=cssClass.split(" ");
    var mobileCssClass='';
    var desktopCssClass='';
    var tabletCssClass='';
    var pageMobLayout = currentPage.properties.mobLayout;
    var pageMobLayout2 = currentPage.properties.mobLayout2;
    var resourceName = resource.name;



    for(var i=0;i<array.length;i++){
        if(array[i].indexOf("tablet")!=-1 && array[i].indexOf("none")==-1 && array[i].indexOf("newline")==-1 && array[i].indexOf("hide")==-1){
			var width=array[i].substring(array[i].lastIndexOf("-")+1);
            tabletCssClass=tabletCssClass+' grid-col-med-'+width;
        }
		
		if(array[i].indexOf("tablet")!=-1 && array[i].indexOf("none")==-1 && array[i].indexOf("newline")!=-1 && array[i].indexOf("hide")==-1){
            tabletCssClass=tabletCssClass+' grid-col-med-newline';
        }
		
		if(array[i].indexOf("tablet")!=-1 && array[i].indexOf("none")==-1 && array[i].indexOf("newline")==-1 && array[i].indexOf("hide")!=-1){
            tabletCssClass=tabletCssClass+' grid-col-med-hide';
        }

        if(array[i].indexOf("phone")!=-1 && array[i].indexOf("none")==-1 && array[i].indexOf("newline")==-1 && array[i].indexOf("hide")==-1){
			var width=array[i].substring(array[i].lastIndexOf("-")+1);
			mobileCssClass=mobileCssClass+' grid-col-sm-'+width;
        }else {


				mobileCssClass= mobileCssClass + ' grid-col-sm-12';	



        }

		if(array[i].indexOf("phone")!=-1 && array[i].indexOf("none")==-1 && array[i].indexOf("newline")!=-1 && array[i].indexOf("hide")==-1){
			mobileCssClass=mobileCssClass+' grid-col-sm-newline';
        }
		
		if(array[i].indexOf("phone")!=-1 && array[i].indexOf("none")==-1 && array[i].indexOf("newline")==-1 && array[i].indexOf("hide")!=-1){
			mobileCssClass=mobileCssClass+' grid-col-sm-hide';
        }



        if(array[i].indexOf("default")!=-1 && array[i].indexOf("none")==-1 && array[i].indexOf("newline")==-1 && array[i].indexOf("hide")==-1){
		    	var width=array[i].substring(array[i].lastIndexOf("-")+1);
				desktopCssClass='grid-col-lg-'+width;
            	if(tabletCssClass==undefined || tabletCssClass=='undefined' || tabletCssClass==''){
				tabletCssClass='grid-col-med-'+width;
               }
        }

		if(array[i].indexOf("default")!=-1 && array[i].indexOf("none")==-1 && array[i].indexOf("newline")!=-1 && array[i].indexOf("hide")==-1){
			desktopCssClass=desktopCssClass+' grid-col-lg-newline';
        }

		if(array[i].indexOf("default")!=-1 && array[i].indexOf("none")==-1 && array[i].indexOf("newline")==-1 && array[i].indexOf("hide")!=-1){
			desktopCssClass=desktopCssClass+' grid-col-lg-hide';
        }
		

    }

    if( cssClass.indexOf("phone") == -1 && mobileCssClass.indexOf('grid-col-sm-12') != -1 && (resourceName === "marketingslot1" || resourceName==="cpdParsys" || resourceName === "responsivegrid" || resourceName === "contentslot1") && (pageMobLayout != undefined && pageMobLayout != "undefined" && pageMobLayout != "default" )){
                mobileCssClass = mobileCssClass.replace(new RegExp('grid-col-sm-12','g'), pageMobLayout);


   	    }

	if( cssClass.indexOf("aem-GridColumn--phone--12") == -1 && mobileCssClass.indexOf('grid-col-sm-12') != -1 && (resourceName === "marketingslot1" || resourceName==="cpdParsys" || resourceName === "responsivegrid" || resourceName === "contentslot1") && (pageMobLayout != undefined && pageMobLayout != "undefined" && pageMobLayout != "default" )){
                mobileCssClass = mobileCssClass.replace(new RegExp('grid-col-sm-12','g'), pageMobLayout);


   	    }

    if( cssClass.indexOf("phone") == -1 && mobileCssClass.indexOf('grid-col-sm-12') != -1 && (resourceName === "marketingslot2" || resourceName==="cpdParsys" ||  resourceName === "contentslot2") && (pageMobLayout != undefined && pageMobLayout != "undefined" && pageMobLayout != "default" )){

        mobileCssClass = mobileCssClass.replace(new RegExp('grid-col-sm-12','g'), pageMobLayout2);


   	    }

	if( cssClass.indexOf("aem-GridColumn--phone--12") == -1 && mobileCssClass.indexOf('grid-col-sm-12') != -1 && (resourceName === "marketingslot2" || resourceName === "responsivegrid" || resourceName === "contentslot2") && (pageMobLayout != undefined && pageMobLayout != "undefined" && pageMobLayout != "default" )){

        mobileCssClass = mobileCssClass.replace(new RegExp('grid-col-sm-12','g'), pageMobLayout2);


   	    }

    if(desktopCssClass!=undefined && desktopCssClass!='undefined'){
	   result.cssClass=cssClass+" "+mobileCssClass+" "+desktopCssClass +" "+ tabletCssClass;
    }else{
	   result.cssClass=cssClass + ' grid-col-sm-12'+' grid-col-lg-12'+' grid-col-med-12';
    }

    return result;
});