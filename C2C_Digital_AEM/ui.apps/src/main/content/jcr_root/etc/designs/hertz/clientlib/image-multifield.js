(function() {

    var DATA_EAEM_NESTED = "data-eaem-nested";
    var CFFW = ".coral-Form-fieldwrapper";
    var THUMBNAIL_IMG_CLASS = "cq-FileUpload-thumbnail-img",
        SEP_SUFFIX = "-",
        SEL_FILE_UPLOAD = ".coral-FileUpload",
        SEL_FILE_REFERENCE = ".cq-FileUpload-filereference",
        SEL_FILE_NAME = ".cq-FileUpload-filename",
        SEL_FILE_MOVEFROM = ".cq-FileUpload-filemovefrom", validCount=0;
    function getStringBeforeAtSign1(str) {
        if (_.isEmpty(str)) {
            return str;
        }

        if (str.indexOf("@") != -1) {
            str = str.substring(0, str.indexOf("@"));
        }

        return str;
    }

    function getStringAfterAtSign1(str) {
        if (_.isEmpty(str)) {
            return str;
        }

        return (str.indexOf("@") != -1) ? str.substring(str.indexOf("@")) : "";
    }

    function getStringAfterLastSlash1(str) {
        if (!str || (str.indexOf("/") == -1)) {
            return "";
        }

        return str.substr(str.lastIndexOf("/") + 1);
    }

    function getStringBeforeLastSlash1(str) {
        if (!str || (str.indexOf("/") == -1)) {
            return "";
        }

        return str.substr(0, str.lastIndexOf("/"));
    }

    function removeFirstDot1(str) {
        if (str.indexOf(".") != 0) {
            return str;
        }

        return str.substr(1);
    }

    function modifyJcrContent1(url) {
        if (url != undefined) {
            return url.replace(new RegExp("^" + Granite.HTTP.getContextPath()), "")
                .replace("_jcr_content", "jcr:content");
        } else {
            return;
        }

    }

    function isSelectOne($field) {
        return !_.isEmpty($field) && ($field.prop("type") === "select-one");
    }

    function setSelectOne($field, value) {
        var select = $field.closest(".coral-Select").data("select");

        if (select) {
            select.setValue(value);
        }
    }

    function isCheckbox($field) {
        return ($field.type === "checkbox");
    }

    function setCheckBox($field, value) {
        $field.checked = value;
    }

    function isCheckbox1($field) {
        return ($field.prop("type") === "checkbox");
    }

    function setCheckBox1($field, value) {
        if (value == "false") {
            $field.prop("checked", false);
        } else {
            $field.prop("checked", true);
        }

    }

    function setWidgetValue1($field, value) {
        if (_.isEmpty($field)) {
            return;
        }

        if (isSelectOne($field)) {
            setSelectOne($field, value);
        } else if (isCheckbox1($field)) {
            setCheckBox1($field, value);
        } else {
            $field.val(value);
        }
    }

    /**
     * Removes multifield number suffix and returns just the fileRefName
     * Input: paintingRef-1, Output: paintingRef
     *
     * @param fileRefName
     * @returns {*}
     */
    function getJustName1(fileRefName) {
        if (!fileRefName || (fileRefName.indexOf(SEP_SUFFIX) == -1)) {
            return fileRefName;
        }

        var value = fileRefName.substring(0, fileRefName.lastIndexOf(SEP_SUFFIX));

        if (fileRefName.lastIndexOf(SEP_SUFFIX) + SEP_SUFFIX.length + 1 == fileRefName.length) {
            return value;
        }

        return value + fileRefName.substring(fileRefName.lastIndexOf(SEP_SUFFIX) + SEP_SUFFIX.length + 1);
    }

    function getMultiFieldNames1($multifields) {
        var mNames = {},
            mName;

        $multifields.each(function(i, multifield) {
            mName = $(multifield).children("[name$='@Delete']").attr("name");
            if (mName != undefined) {
                mName = mName.substring(0, mName.indexOf("@"));
                mName = mName.substring(2);
                mNames[mName] = $(multifield);
            }

        });

        return mNames;
    }

    function buildMultiField1(data, $multifield, mName) {
        if (_.isEmpty(mName) || _.isEmpty(data)) {
            return;
        }

        _.each(data, function(value, key) {
            if (key == "jcr:primaryType") {
                return;
            }

            $multifield.find(".js-coral-Multifield-add").click();

            _.each(value, function(fValue, fKey) {
                if (fKey == "jcr:primaryType" || _.isObject(fValue)) {
                    return;
                }

                var $field = $multifield.find("[name='./" + fKey + "']").last();

                if (_.isEmpty($field)) {
                    return;
                }

                setWidgetValue1($field, fValue);
            });
        });
    }

    function buildImageField1($multifield, mName) {
        $multifield.find(".coral-FileUpload:last").each(function() {
            var $element = $(this),
                widget = $element.data("fileUpload"),
                resourceURL = $element.parents("form.cq-dialog").attr("action"),
                counter = $multifield.find(SEL_FILE_UPLOAD).length;

            if (!widget) {
                return;
            }

            var fuf = new Granite.FileUploadField(widget, resourceURL);

            addThumbnail1(fuf, mName, counter);
        });
    }
	
	 function buildImageField2($multifield, mName) {
        $multifield.find(".coral-FileUpload:last").each(function() {
            var $element = $(this),
                widget = $element.data("fileUpload"),
                resourceURL = $element.parents("form.cq-siteadmin-admin-properties").attr("action"),
                counter = $multifield.find(SEL_FILE_UPLOAD).length;
            if (!widget) {
                return;
            }

            var fuf = new Granite.FileUploadField(widget, resourceURL);

            addThumbnail1(fuf, mName, counter);
        });
    }

    function addThumbnail1(imageField, mName, counter) {
        var $element = imageField.widget.$element,
            $thumbnail = $element.find("." + THUMBNAIL_IMG_CLASS),
            thumbnailDom;

        $thumbnail.empty();

        $.ajax({
            url: imageField.resourceURL + ".2.json",
            cache: false
        }).done(handler);

        function handler(data) {
            var fName = getJustName1(getStringAfterLastSlash1(imageField.fieldNames.fileName)),
                fRef = getJustName1(getStringAfterLastSlash1(imageField.fieldNames.fileReference));

            if (isFileNotFilled(data, counter, fRef)) {
                return;
            }

            var fileName = data[mName][counter][fName],
                fileRef = data[mName][counter][fRef];

            if (!fileRef) {
                return;
            }

            if (imageField._hasImageMimeType()) {
                imageField._appendThumbnail(fileRef, $thumbnail);
            }

            var $fileName = $element.find("[name=\"" + imageField.fieldNames.fileName + "\"]"),
                $fileRef = $element.find("[name=\"" + imageField.fieldNames.fileReference + "\"]");

            $fileRef.val(fileRef);
            $fileName.val(fileName);
        }

        function isFileNotFilled(data, counter, fRef) {
            return _.isEmpty(data[mName]) ||
                _.isEmpty(data[mName][counter]) ||
                _.isEmpty(data[mName][counter][fRef])
        }
    }

    //reads multifield data from server, creates the nested composite multifields and fills them
    function addDataInFields1() {
        
        $(document).on("dialog-ready", function() {

            var $multifields = $("[" + DATA_EAEM_NESTED + "]");

            if (_.isEmpty($multifields)) {
                return;
            }

            workaroundFileInputPositioning($multifields);

            var mNames = getMultiFieldNames1($multifields),
                $form = $(".cq-dialog"),
                actionUrl = $form.attr("action") + ".infinity.json";
            _.each(mNames, function($multifield, mName) {
                $multifield.on("click", ".js-coral-Multifield-add", function() {
                    buildImageField1($multifield, mName);
                });

            });
            $.ajax(actionUrl).done(postProcess);

            function postProcess(data) {
                _.each(mNames, function($multifield, mName) {

                    buildMultiField1(data[mName], $multifield, mName);
                });
            }
        });
    }
	
	    function addDataInFields4() {   
            var $multifields = $("[" + DATA_EAEM_NESTED + "]");
            if (_.isEmpty($multifields)) {
                return;
            }

            workaroundFileInputPositioning($multifields);

            var mNames = getMultiFieldNames1($multifields),
                $form = $(".cq-dialog"),
                actionUrl = $form.attr("action") + ".infinity.json";
            _.each(mNames, function($multifield, mName) {
                $multifield.on("click", ".js-coral-Multifield-add", function() {
                    buildImageField1($multifield, mName);
                });

            });
            $.ajax(actionUrl).done(postProcess);

            function postProcess(data) {
                _.each(mNames, function($multifield, mName) {

                    buildMultiField1(data[mName], $multifield, mName);
                });
            }
    }
	
	 function addDataInFields3(e) {
            var $multifields = $("[" + DATA_EAEM_NESTED + "]");

            if (_.isEmpty($multifields)) {
                return;
            }

            workaroundFileInputPositioning($multifields);

            var mNames = getMultiFieldNames1($multifields),
                $form = $(".cq-siteadmin-admin-properties"),
                actionUrl = $form.attr("action") + ".infinity.json";
            _.each(mNames, function($multifield, mName) {
                $multifield.on("click", ".js-coral-Multifield-add", function() {
                    buildImageField2($multifield, mName);
                });

            });
            $.ajax(actionUrl).done(postProcess);

            function postProcess(data) {
                _.each(mNames, function($multifield, mName) {

                    buildMultiField1(data[mName], $multifield, mName);
                });
            }
       
    }

    function workaroundFileInputPositioning($multifields) {
        //to workaround the .coral-FileUpload-input positioning issue
        $multifields.find(".js-coral-Multifield-add")
            .css("position", "relative");
    }

    function collectImageFields1($form, $fieldSet, counter) {
        var $fields = $fieldSet.children().children(CFFW).not(function(index, ele) {
            return $(ele).find(SEL_FILE_UPLOAD).length == 0;
        });

        $fields.each(function(j, field) {
            var $field = $(field),
                $widget = $field.find(SEL_FILE_UPLOAD).data("fileUpload");

            if (!$widget) {
                return;
            }

            var prefix = $fieldSet.data("name") + "/" + (counter + 1) + "/",

                $fileRef = $widget.$element.find(SEL_FILE_REFERENCE),
                refPath = prefix + getJustName1($fileRef.attr("name")),

                $fileName = $widget.$element.find(SEL_FILE_NAME),
                namePath = prefix + getJustName1($fileName.attr("name")),

                $fileMoveRef = $widget.$element.find(SEL_FILE_MOVEFROM),
                moveSuffix = $widget.inputElement.attr("name") + "/" + new Date().getTime() +
                SEP_SUFFIX + $fileName.val(),
                moveFromPath = moveSuffix + "@MoveFrom";
            if($fileRef.val()!="false" && $fileRef.val()!=false){
				 $('<input />').attr('type', 'hidden').attr('name', refPath)
                .attr('value', $fileRef.val())
                .appendTo($form);
            }else{
				 $('<input />').attr('type', 'hidden').attr('name', refPath)
                .attr('value', null)
                .appendTo($form);
            }

            if($fileName.val()!="false" && $fileName.val()!=false){
				$('<input />').attr('type', 'hidden').attr('name', namePath)
                .attr('value', $fileName.val()).appendTo($form);
            }else{
				$('<input />').attr('type', 'hidden').attr('name', namePath)
                .attr('value', null).appendTo($form);
            }

            $('<input />').attr('type', 'hidden').attr('name', moveFromPath)
                .attr('value', modifyJcrContent1($fileMoveRef.val())).appendTo($form);
            $field.remove();
        });
    }

    function collectNonImageFields1($form, $fieldSet, counter) {
        var $fields = $fieldSet.children().children(CFFW).not(function(index, ele) {
            return $(ele).find(SEL_FILE_UPLOAD).length > 0;
        });

        $fields.each(function(j, field) {
            fillValue1($form, $fieldSet.data("name"), $(field).find("[name]"), (counter + 1));
        });
    }

    function fillValue1($form, fieldSetName, $field, counter) {
        var name = $field.attr("name"),
            value;

        if (!name) {
            return;
        }

        //strip ./
        if (name.indexOf("./") == 0) {
            name = name.substring(2);
        }

        value = $field.val();

        if ($field.prop("type") == "checkbox") {

            checkedValue = $field.prop("checked");
            if (checkedValue == "on" || checkedValue == "true" || checkedValue == true) {
                value = true;
            } else {
                value = false;
            }
        }else if($field.prop("type") == "text"){

				var minLength= $field.attr("data-minlength");
            	var fieldLabel = $field.siblings(".coral-Form-fieldlabel").text();
				if(value.length<minLength){
						e.preventDefault();
                    	e.stopPropagation();
                  		var ui = $(window).adaptTo("foundation-ui");
                        ui.alert("Warning", "Minimum " + minLength + " characters are required in the "+fieldLabel+" field.","notice");
						throw "Error Occured"
                }
        }

        //remove the field, so that individual values are not POSTed
        $field.remove();

        $('<input />').attr('type', 'hidden')
            .attr('name', fieldSetName + "/" + counter + "/" + name)
            .attr('value', value)
            .appendTo($form);
    }

    //collect data from widgets in multifield and POST them to CRX
    function collectDataFromFields1() {
        $(document).on("click", ".cq-dialog-submit", function(e) {
            var $multifields = $("[" + DATA_EAEM_NESTED + "]");

            if (_.isEmpty($multifields)) {
                return;
            }

            var $form = $(this).closest("form.foundation-form"),
                $fieldSets;

            $multifields.each(function(i, multifield) {
                var minSize = $(multifield).attr("data-minSections");
                var multifieldFieldLabel = $(multifield).siblings(".coral-Form-fieldlabel").text();
                $fieldSets = $(multifield).find("[class='coral-Form-fieldset']");
                if ($fieldSets.length < minSize) {
                    var ui = $(window).adaptTo("foundation-ui");
                    ui.alert("Warning", "Minimum " + minSize + " links are required in the " + multifieldFieldLabel + " multifield.", "notice");
                    e.preventDefault();
                    e.stopPropagation();
                    return;
                }
                $fieldSets.each(function(counter, fieldSet) {

                    collectNonImageFields1($form, $(fieldSet), counter);

                    collectImageFields1($form, $(fieldSet), counter);
                });
            });
        });
    }
	
	    //collect data from widgets in multifield and POST them to CRX
    function collectDataFromFields3() {
         $(document).on("click", "[form=cq-sites-properties-form]", function(e) {
            var $multifields = $("[" + DATA_EAEM_NESTED + "]");

            if (_.isEmpty($multifields)) {
                return;
            }

            var $form = $("form#cq-sites-properties-form"),
                $fieldSets;

            $multifields.each(function(i, multifield) {
                var minSize = $(multifield).attr("data-minSections");
                var multifieldFieldLabel = $(multifield).siblings(".coral-Form-fieldlabel").text();
                $fieldSets = $(multifield).find("[class='coral-Form-fieldset']");
                if ($fieldSets.length < minSize) {
                    var ui = $(window).adaptTo("foundation-ui");
                    ui.alert("Warning", "Minimum " + minSize + " links are required in the " + multifieldFieldLabel + " multifield.", "notice");
                    e.preventDefault();
                    e.stopPropagation();
                    return;
                }
                $fieldSets.each(function(counter, fieldSet) {

                    collectNonImageFields1($form, $(fieldSet), counter);

                    collectImageFields1($form, $(fieldSet), counter);
                });
            });
        });
    }

    function overrideGranite_refreshThumbnail1() {
        var prototype = Granite.FileUploadField.prototype,
            ootbFunc = prototype._refreshThumbnail;

        prototype._refreshThumbnail = function() {
            var $imageMulti = this.widget.$element.closest("[" + DATA_EAEM_NESTED + "]");

            if (!_.isEmpty($imageMulti)) {
                return;
            }

            return ootbFunc.call(this);
        }
    }

    function overrideGranite_computeFieldNames1() {
        var prototype = Granite.FileUploadField.prototype,
            ootbFunc = prototype._computeFieldNames;

        prototype._computeFieldNames = function() {
            ootbFunc.call(this);

            var $imageMulti = this.widget.$element.closest("[" + DATA_EAEM_NESTED + "]");

            if (_.isEmpty($imageMulti)) {
                return;
            }

            var fieldNames = {},
                fileFieldName = $imageMulti.find("input[type=file]").attr("name"),
                counter = $imageMulti.find(SEL_FILE_UPLOAD).length;

            _.each(this.fieldNames, function(value, key) {
                if (value.indexOf("./jcr:") == 0) {
                    fieldNames[key] = value;
                } else if (key == "tempFileName" || key == "tempFileDelete") {
                    value = value.substring(0, value.indexOf(".sftmp")) + getStringAfterAtSign1(value);
                    fieldNames[key] = fileFieldName + removeFirstDot1(getStringBeforeAtSign1(value)) +
                        SEP_SUFFIX + counter + ".sftmp" + getStringAfterAtSign1(value);
                } else {
                    fieldNames[key] = getStringBeforeAtSign1(value) + SEP_SUFFIX +
                        counter + getStringAfterAtSign1(value);
                }
            });

            this.fieldNames = fieldNames;

            this._tempFilePath = getStringBeforeLastSlash1(this._tempFilePath);
            this._tempFilePath = getStringBeforeLastSlash1(this._tempFilePath) + removeFirstDot1(fieldNames.tempFileName);
        }
    }

    function performOverrides1() {
        overrideGranite_computeFieldNames1();
        overrideGranite_refreshThumbnail1();
    }




    //reads multifield data from server, creates the nested composite multifields and fills them
    var addDataInFields = function() {
        $(document).on("dialog-ready", function() {
			var $multifieldsParent = $("[" + "data-eaem-parentmultifield" + "]");
			 	
				if($multifieldsParent.length!=0){
					 $multifieldsParent.each(function(i, multifield){
					 var maxSize = $(multifield).attr("data-maxSections");
                	 var button=$(multifield).find('> .js-coral-Multifield-add');
					 var list=$(multifield).find('> ol:first li');
					 var size=$(list.length);
					 if(size>=maxSize){
						 $(button).attr('disabled', true);
					 }

                });
			}    
            sendCall();
        });
    };
	
	 //reads multifield data from server, creates the nested composite multifields and fills them
    var addDataInFields2 = function() {
            sendCall();      
    };


    var sendCall = function() {
        var $fieldSets = $("[" + DATA_EAEM_NESTED + "][class='coral-Form-fieldset']"),
            $form = $fieldSets.closest("form.foundation-form");

        var actionUrl = $form.attr("action") + ".json";

        var postProcess = function(data) {


            var arr = [];
            $('[data-eaem-nested]').each(function() {
                if ($(this).data('name')) {
                    arr.push($(this).data('name').split("/")[1]);
                }
            });

            arr = $.unique(arr);

            $.each(arr, function(k, mName) {
                if (!data || !data[mName]) {
                    return;
                }
                var mValues = data[mName],
                    $field, name;

                if (_.isString(mValues)) {
                    mValues = [JSON.parse(mValues)];
                }

                $.each(mValues, function(i, record) {
                    if (!record) {
                        return;
                    }

                    if (_.isString(record)) {
                        record = JSON.parse(record);
                    }

                    $.each(record, function(rKey, rValue) {
                        $field = $fieldSets.filter("[data-name='./" + mName + "']").find("[name='./" + rKey + "']")[i];

                        if (_.isArray(rValue) && !_.isEmpty(rValue)) {
                            fillNestedFields($fieldSets.filter("[data-name='./" + mName + "']").find("[data-init='multifield']")[i], rValue);
                        } else {
                            //check if it's a select, or a textfield
                            if ($($field).is("select")) {
                                //for select, update the textfield before the select.
                                $($field).prev().children().first().html(rValue);
                                // set the granite ui - select element to the saved value
                                $($field).children().removeAttr("selected");
                                $($field).children("[value='" + rValue + "']").attr("selected", "selected");
                                // set the coral ui - select element to the saved value
                                $($field).parent().find(".coral-SelectList").children().removeAttr("aria-selected");
                                $($field).parent().find(".coral-SelectList").children("[data-value='" + rValue + "']").attr("aria-selected", "true");

                            } else if (isCheckbox($field)) {
                                setCheckBox($field, rValue);
                            } else {
                                // for textfield
                                $field.value = rValue;
                            }
                        }
                    });
                });
            });
        };

        //creates & fills the nested multifield with data
        var fillNestedFields = function($multifield, valueArr) {
            _.each(valueArr, function(record, index) {
                $($multifield).find(".js-coral-Multifield-add").click();

                //a setTimeout may be needed
                _.each(record, function(value, key) {
                    var $field = $($multifield).find("[name='./" + key + "']")[index];
                    if ($field != undefined) {
                        if ($field.type === "checkbox") {
                            $field.checked = value;

                        }
                        $field.value = value;
                    }
                })
            })
        };

        $.ajax(actionUrl).done(postProcess);
    };

    var fillValue = function($field, record,e) {
        var name = $field.attr("name"), fieldErrorEl, arrow;

        if (!name) {
            return;
        }

        //strip ./
        if (name.indexOf("./") == 0) {
            name = name.substring(2);
        }
        value = $field.val();

        if ($field.prop("type") == "checkbox") {

            checkedValue = $field.prop("checked");
            if (checkedValue == "on" || checkedValue == "true" || checkedValue == true) {
                value = true;
            } else {
                value = false;
            }
        }	else if($field.prop("type")=="text"){
			var minLength= $field.attr("data-minlength");
            var fieldLabel = $field.siblings(".coral-Form-fieldlabel").text();
				if(value.length<minLength){
						e.preventDefault();
                    	e.stopPropagation();
                  		var ui = $(window).adaptTo("foundation-ui");
                        ui.alert("Warning", "Minimum " + minLength + " characters are required in the "+fieldLabel+" field.","notice");
						throw "Error Occured"
                }
		}

        	record[name] = value;

    };

    //for getting the nested multifield data as js objects
    var getRecordFromMultiField = function($multifield,e) {
        var $fieldSets = $multifield.find("[class='coral-Form-fieldset']");
        var records = [],
            record, $fields, name;
        $fieldSets.each(function(i, fieldSet) {
            $fields = $(fieldSet).find("[name]");
            record = {};
			$fields.each(function(j, field) {
                fillValue($(field), record,e);
            });

            if (!$.isEmptyObject(record)) {
                records.push(record)
            }
        });

        return records;
    };

    //collect data from widgets in multifield and POST them to CRX as JSON
    var collectDataFromFields = function() {
        $(document).on("click", ".cq-dialog-submit", function(e) {
            var $form = $(this).closest("form.foundation-form");

            var $fieldSets = $("[" + DATA_EAEM_NESTED + "][class='coral-Form-fieldset']");

            var record, $fields, $field, name, $nestedMultiField;
            if( $fieldSets.length===0){
				var $multifields = $("[" + "data-eaem-parent" + "]");
            	if (_.isEmpty($multifields)) {
                return;
            	}

                $multifields.each(function(i, multifield){
					 var minSize = $(multifield).attr("data-minSections");
                	 var multifieldFieldLabel = $(multifield).siblings(".coral-Form-fieldlabel").text();
                    if(minSize>0){
						 var ui = $(window).adaptTo("foundation-ui");
                         ui.alert("Minimum " + minSize + " links are required in the " + multifieldFieldLabel + " multifield.");
						  e.preventDefault();
                   	      e.stopPropagation();
                          return;
                    }

                });


            }else{
				  $fieldSets.each(function(i, fieldSet) {
                $fields = $(fieldSet).children().children(CFFW);

                record = {};

                $fields.each(function(j, field) {
                    $field = $(field);

                    //may be a nested multifield
                    $nestedMultiField = $field.find("[data-init='multifield']");

                    if ($nestedMultiField.length == 0) {
                        fillValue($field.find("[name]"), record,e);
                    } else {
                        name = $nestedMultiField.find("[class='coral-Form-fieldset']").data("name");

                        if (!name) {
                            return;
                        }

                        //strip ./
                        name = name.substring(2);

                        record[name] = getRecordFromMultiField($nestedMultiField,e);
                    }
                });

                if ($.isEmptyObject(record)) {
                    return;
                }

                //add the record JSON in a hidden field as string
                $('<input />').attr('type', 'hidden')
                   .attr('name', $(fieldSet).data('name'))
                    .attr('value', JSON.stringify(record))
                    .appendTo($form);
            });
            }
        });
    };

	    //collect data from widgets in multifield and POST them to CRX as JSON
    var collectDataFromFields2 = function() {
        $(document).on("click", "[form=cq-sites-properties-form]", function(e){
            var $form = $("form#cq-sites-properties-form");

            var $fieldSets = $("[" + DATA_EAEM_NESTED + "][class='coral-Form-fieldset']");

            var record, $fields, $field, name, $nestedMultiField;
            if( $fieldSets.length===0){
				var $multifields = $("[" + "data-eaem-parent" + "]");
            	if (_.isEmpty($multifields)) {
                return;
            	}

                $multifields.each(function(i, multifield){
					 var minSize = $(multifield).attr("data-minSections");
                	 var multifieldFieldLabel = $(multifield).siblings(".coral-Form-fieldlabel").text();
                    if(minSize>0){
						 var ui = $(window).adaptTo("foundation-ui");
                         ui.alert("Minimum " + minSize + " links are required in the " + multifieldFieldLabel + " multifield.");
						  e.preventDefault();
                   	      e.stopPropagation();
                          return;
                    }

                });


            }else{
				  $fieldSets.each(function(i, fieldSet) {
                $fields = $(fieldSet).children().children(CFFW);

                record = {};

                $fields.each(function(j, field) {
                    $field = $(field);

                    //may be a nested multifield
                    $nestedMultiField = $field.find("[data-init='multifield']");

                    if ($nestedMultiField.length == 0) {
                        fillValue($field.find("[name]"), record,e);
                    } else {
                        name = $nestedMultiField.find("[class='coral-Form-fieldset']").data("name");

                        if (!name) {
                            return;
                        }

                        //strip ./
                        name = name.substring(2);

                        record[name] = getRecordFromMultiField($nestedMultiField,e);
                    }
                });

                if ($.isEmptyObject(record)) {
                    return;
                }

                //add the record JSON in a hidden field as string
                $('<input />').attr('type', 'hidden')
                   .attr('name', $(fieldSet).data('name'))
                    .attr('value', JSON.stringify(record))
                    .appendTo($form);
            });
            }
        });
    };
	
	
    $(document).ready(function() {
		if($("form#cq-sites-properties-form").length === 0){
			if(window.location.pathname.indexOf("_cq_dialog.html")!=-1){
					addDataInFields2();
					collectDataFromFields();
					addDataInFields4();
					collectDataFromFields1();
					performOverrides1();					
			}else{
					addDataInFields();
					collectDataFromFields();
					addDataInFields1();
					collectDataFromFields1();
					performOverrides1();
			}	
		}
		if($("form#cq-sites-properties-form").length === 1){
			addDataInFields2();
			collectDataFromFields2();
			addDataInFields3();
			collectDataFromFields3();
			performOverrides1();
		}      
    });

    //extend otb multifield for adjusting event propagation when there are nested multifields
    //for working around the nested multifield add and reorder
    CUI.Multifield = new Class({
        toString: "Multifield",
        extend: CUI.Multifield,

        construct: function(options) {
            this.script = this.$element.find(".js-coral-Multifield-input-template:last");
        },

        _addListeners: function() {
            this.superClass._addListeners.call(this);
            var element = this.$element;
			
            //otb coral event handler is added on selector .js-coral-Multifield-add
            //any nested multifield add click events are propagated to the parent multifield
            //to prevent adding a new composite field in both nested multifield and parent multifield
            //when user clicks on add of nested multifield, stop the event propagation to parent multifield
            this.$element.on("click", ".js-coral-Multifield-add", function(e) {
                var field = $(this).parent();
                var size = field.attr("data-maxSections");
                if (size) {
                    var totalLinkCount = $(this).prev('ol').children('li').length;
                    if (totalLinkCount >= size) {
                        $(this).attr('disabled', true);
                    } else {
                        $(this).attr('disabled', false);
                    }
                }
                e.stopPropagation();
            });

            this.$element.on("click", ".js-coral-Multifield-remove", function(e) {
                $(element).find(".js-coral-Multifield-add").attr('disabled', false);
            });

            this.$element.on("drop", function(e) {
                e.stopPropagation();
            });
        }
    });

    CUI.Widget.registry.register("multifield", CUI.Multifield);
})();
