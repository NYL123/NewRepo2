"use strict";
use(function() {

        var CONST = {

            IMAGE_CAROUSEL: "imgCarousel",

            STRING_JAVA_ARRAY: "[object JavaArray]",
            STRING_JAVA_OBJECT: "[object JavaObject]",

        }
        var imgCarousel = {};

 		imageCarousel = [];
        var multidata = [];

        multidata = granite.resource.properties[CONST.IMAGE_CAROUSEL];

        if (multidata != null && Object.prototype.toString.call(multidata) == CONST.STRING_JAVA_ARRAY) {
            for (i in multidata) {
                imageCarousel[i] = JSON.parse(multidata[i]);
            }
        } else if (multidata != null && Object.prototype.toString.call(multidata) == CONST.STRING_JAVA_OBJECT) {
           imageCarousel[0] = JSON.parse(multidata);
        }


        return imageCarousel;


    }

);