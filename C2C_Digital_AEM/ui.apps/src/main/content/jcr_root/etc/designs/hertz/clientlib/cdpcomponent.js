(function(document, $, ns) {
    "use strict";
    $(document).on("click", ".cq-dialog-submit", function(e) {
        var cdpFieldFilled = false;
        var counter1 = 0;
        var counter2 = 0;
        var pcFieldFilled = false;
        var $cdpFields = $("[data-cdpField]");
        var $pcFields = $("[data-pcfield]");
        if (_.isEmpty($cdpFields) && _.isEmpty($pcFields)) {
            return;
        }
        $cdpFields.each(function(i, cdpField) {
            if (cdpField.value.length > 0) {
                cdpFieldFilled = true;
                ++counter1;
            }
        });

        $pcFields.each(function(i, pcField) {
            if (pcField.value.length > 0) {
                pcFieldFilled = true;
                ++counter2;
            }
        });

        if (!cdpFieldFilled && pcFieldFilled) {
            if (counter2 < 2) {
                e.stopPropagation();
                e.preventDefault();
                var ui = $(window).adaptTo("foundation-ui");
                ui.alert("Warning", "Both the PC code fields are required.", "notice");
                throw "Error Occured";
            }
        }

        if (cdpFieldFilled && !pcFieldFilled) {
            if (counter1 < 2) {
                e.stopPropagation();
                e.preventDefault();
                var ui = $(window).adaptTo("foundation-ui");
                ui.alert("Warning", "Both the CDP code fields are required.", "notice");
                throw "Error Occured";
            }
        }

        if (!cdpFieldFilled && !pcFieldFilled) {
            e.stopPropagation();
            e.preventDefault();
            var ui = $(window).adaptTo("foundation-ui");
            ui.alert("Warning", "Please fill either or both the CDP code or PC Code.", "notice");
            throw "Error Occured";
        }

        if (cdpFieldFilled && pcFieldFilled) {
            if (counter1 < 2) {
                e.stopPropagation();
                e.preventDefault();
                var ui = $(window).adaptTo("foundation-ui");
                ui.alert("Warning", "Both the CDP code fields are required.", "notice");
                throw "Error Occured";
            }

            if (counter2 < 2) {
                e.stopPropagation();
                e.preventDefault();
                var ui = $(window).adaptTo("foundation-ui");
                ui.alert("Warning", "Both the PC code fields are required.", "notice");
                throw "Error Occured";
            }

        }
    });

})(document, Granite.$, Granite.author);