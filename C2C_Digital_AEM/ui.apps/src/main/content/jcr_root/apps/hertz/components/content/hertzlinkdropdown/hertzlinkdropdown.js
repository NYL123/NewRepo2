"use strict";
use(function() {
    var result = {};
    var linkType = this.linkType;
    var seqId = this.seqId;
    var targetType = this.targetType;
    var cdpCode = this.cdpCode;
    var pcCode = this.pcCode;
    var rqCode = this.rqCode;
	
    var resultUrl = "link.html?id="+seqId+"&LinkType="+linkType;
    
    if(targetType!=undefined && targetType!='undefined' && targetType!=""){
    	resultUrl = resultUrl+"&TargetType="+targetType;
    }
    
    if(cdpCode!=undefined && cdpCode!='undefined' && cdpCode!=""){
    	resultUrl = resultUrl+"&CDP="+cdpCode;
    }
    
    if(pcCode!=undefined && pcCode!='undefined' && pcCode!=""){
    	resultUrl = resultUrl+"&PC="+pcCode;
    }
    
    if(rqCode!=undefined && rqCode!='undefined' && rqCode!=""){
    	resultUrl = resultUrl+"&RQ="+rqCode;
    }
    
    
    result.resultUrl=resultUrl;

    return result;
});