(function (document, $, ns) {
"use strict";
		$(document).on("click", ".cq-dialog-submit", function (e) {
			var $form = $(this).closest("form.foundation-form"),
			textFields=$form.find("[type='text']");
			textFields.each(function(i, field) {
                 var fieldLabel = $(field).siblings(".coral-Form-fieldlabel").text();
                var length=field.value.length;
              var minlength=$(field).attr('data-minlength');
                if(minlength){
                    if(length<minlength){
                        e.stopPropagation();
                        e.preventDefault();
                        var ui = $(window).adaptTo("foundation-ui");
                    	ui.alert("Warning", "Minimum "+minlength + " characters required in the "+fieldLabel+" field.", "notice");
                        throw "Error Occured";
                    }

                }

		});
        });
	
})(document, Granite.$, Granite.author);