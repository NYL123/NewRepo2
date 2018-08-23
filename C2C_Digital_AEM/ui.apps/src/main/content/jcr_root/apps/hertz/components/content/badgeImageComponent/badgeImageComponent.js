"use strict";
use(function() {
    var result = {};

    

              var childImageResource;
    var imageFileReference;

        childImageResource=resource.getChild('image');
                             if(null!=childImageResource){
                                        imageFileReference=childImageResource.properties.get('fileReference');
                             }
        
    result.strFileReference=imageFileReference;

    return result;
});