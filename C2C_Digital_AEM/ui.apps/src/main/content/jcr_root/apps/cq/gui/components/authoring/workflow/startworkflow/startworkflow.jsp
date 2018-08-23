<%--
  ADOBE CONFIDENTIAL

  Copyright 2014 Adobe Systems Incorporated
  All Rights Reserved.

  NOTICE:  All information contained herein is, and remains
  the property of Adobe Systems Incorporated and its suppliers,
  if any.  The intellectual and technical concepts contained
  herein are proprietary to Adobe Systems Incorporated and its
  suppliers and may be covered by U.S. and Foreign Patents,
  patents in process, and are protected by trade secret or copyright law.
  Dissemination of this information or reproduction of this material
  is strictly forbidden unless prior written permission is obtained
  from Adobe Systems Incorporated.

  The modal to start workflows.


--%><%
%><%@page import="com.adobe.granite.ui.components.Config,
                  com.adobe.granite.workflow.WorkflowException,
                  com.adobe.granite.workflow.WorkflowSession,
                  com.adobe.granite.workflow.metadata.MetaDataMap,
                  com.adobe.granite.workflow.model.WorkflowModel,
                  com.day.cq.i18n.I18n,
                  com.day.cq.wcm.api.Page,
                  org.apache.sling.api.resource.Resource,
                  java.util.Arrays"%><%
%><%
%><%@include file="/libs/granite/ui/global.jsp" %><%!

    private boolean doInclude(WorkflowModel model, String[] tags, boolean doStrict, String exclude) {
        if (tags.length == 0) {
            return true;
        }

        MetaDataMap metaData = model.getMetaDataMap();
        String tagStr = metaData.get("tags", String.class) != null ? metaData.get("tags", String.class) : null;
        String tagStrSplits[] = (tagStr != null && !tagStr.equals("")) ? tagStr.trim().split(",") : new String[0];
        if (exclude != null &&
            exclude.equals("excludeWorkflows") &&
            Arrays.asList(tagStrSplits).contains("publish")) {
            return false;
        }
        if (tagStrSplits.length == 0 && !doStrict) {
            // for backward compatibility
            return true;
        } else {
            for (String tag : tagStrSplits) {
                for (String checkTag : tags) {
                    if (checkTag.equals(tag)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

%><%

    Page targetPage = null;

    // get page object from suffix
    String pagePath = slingRequest.getRequestPathInfo().getSuffix();
    if (pagePath != null) {
        Resource pageResource = slingRequest.getResourceResolver().resolve(pagePath);
        if (pageResource != null) {
            targetPage = pageResource.adaptTo(Page.class);
        }
    }

    if (targetPage == null) {
        return;
    }

    I18n wfI18n = new I18n(slingRequest);
    Config wfCfg = new Config(resource);
    String exclude = wfCfg.get("exclude", String.class);

%>

<form action="<%= request.getContextPath() %>/etc/workflow/instances" method="post" class="coral-Modal coral-Form coral-Form--vertical cq-workflow-start-modal"  style="width:40%">
    <div class="coral-Modal-header">
        <i class="coral-Modal-typeIcon coral-Icon coral-Icon--sizeS"></i>
        <h2 class="coral-Modal-title coral-Heading coral-Heading--2"><%= wfI18n.get("Start Workflow") %></h2>
        <button type="reset" class="coral-MinimalButton coral-Modal-closeButton" title="Close" data-dismiss="modal">
            <i class="coral-Icon coral-Icon--sizeXS coral-Icon--close coral-MinimalButton-icon "></i>
        </button>
    </div>
    <div class="coral-Modal-body">
        <div>
            <input type="hidden" name="_charset_" value="utf-8">
            <input type="hidden" name=":status" value="browser">
            <input type="hidden" name="payloadType" value="JCR_PATH">
            <input type="hidden" name="payload" value="<%= xssAPI.encodeForHTMLAttr(request.getContextPath() + targetPage.getPath()) %>">
            <span class="coral-Select hertz-modal-class coral-Form-field cq-common-admin-timeline-toolbar-actions-workflow-select" data-init="select" aria-required="true">
                <button type="button" class="coral-Select-button coral-MinimalButton cq-common-admin-timeline-toolbar-actions-workflow-select-button">
                    <span class="coral-Select-button-text hertz-modal-selected-option"><%= wfI18n.get("Select a Workflow Model") %></span>
                </button>
                <select name="model" autocomplete="off" class="coral-Select-select">
                    <option value=""><%= wfI18n.get("Select a Workflow Model") %></option><%
                    WorkflowSession wfSession = slingRequest.getResourceResolver().adaptTo(WorkflowSession.class);
                    WorkflowModel[] models;
                    try {
                        models = wfSession.getModels();
                        String[] tags = {"wcm"};

                        for (WorkflowModel model : models) {
                            if (doInclude(model, tags, false, exclude)) {
                                %><option value="<%= model.getId() %>"><%= xssAPI.encodeForHTML(wfI18n.getVar(model.getTitle())) %></option><%
                            }
                        }
                    } catch (WorkflowException e) {
                        //throw new ServletException("Error fetching workflow models", e);
                    }
                    %>
                </select>
            </span>
            <input type="text" class="coral-Textfield coral-Form-field" name="workflowTitle" placeholder="<%= wfI18n.get("Enter title of workflow") %>">
            <div class="coral-Form-fieldwrapper datepicker-wrapper">
                <label class="coral-Form-fieldlabel">Scheduled Activation / Deactivation (Optional)</label>
                <div class="coral-Form-field coral-InputGroup coral-DatePicker" data-init="datepicker" data-min-date="today" data-displayed-format="D MMMM YYYY hh:mm a" data-stored-format="YYYY-MM-DD[T]HH:mm:ss.000Z" data-head-format="MMMM YYYY" data-month-names="[&quot;January&quot;,&quot;February&quot;,&quot;March&quot;,&quot;April&quot;,&quot;May&quot;,&quot;June&quot;,&quot;July&quot;,&quot;August&quot;,&quot;September&quot;,&quot;October&quot;,&quot;November&quot;,&quot;December&quot;]" data-day-names="[&quot;Su&quot;,&quot;Mo&quot;,&quot;Tu&quot;,&quot;We&quot;,&quot;Th&quot;,&quot;Fr&quot;,&quot;Sa&quot;]">
                    <input class="js-coral-DatePicker-input coral-InputGroup-input coral-Textfield" name="absoluteDate" type="datetime" value="" data-validation="">
                    <div class="coral-InputGroup-button">
                        <button class="coral-Button coral-Button--square" type="button" title="Date Picker">
                            <i class="coral-Icon coral-Icon--sizeS coral-Icon--calendar"></i>
                        </button>
                    </div>
                </div>
                <input type="hidden" value="Date" name="absoluteDate@TypeHint">
                <p class="granite-datepicker-timezone granite-coral-Form-fieldlongdesc" data-granite-datepicker-timezone-server="330" hidden="hidden">
                    <i class="coral-Icon coral-Icon--alert coral-Icon--sizeXS"></i>
                    <span>Your timezone (UTC<span class="granite-datepicker-timezone-client"></span>) will be used instead of the server setting (UTC+05:30)</span>
                </p><span class="coral-Form-fieldinfo coral-Icon coral-Icon--infoCircle coral-Icon--sizeS" data-init="quicktip" data-quicktip-type="info" data-quicktip-arrow="right" data-quicktip-content="Leave empty  to Activate/Deactivate immediately." aria-label="Leave empty  to Activate/Deactivate immediately." tabindex="0"></span>
            </div>
        </div>
    </div>
    <div class="coral-Modal-footer">
        <button type="reset" class="coral-Button" data-dismiss="modal"><%= wfI18n.get("Close") %></button>
        <button type="button" class="coral-Button coral-Button--primary start-workflow-activator-start" data-dismiss="modal" disabled="disabled"><%= wfI18n.get("Start Workflow") %></button>
    </div>
</form>
<script type="text/javascript">
    $('.datepicker-wrapper').hide();
	$(document).on('DOMSubtreeModified','.hertz-modal-class .hertz-modal-selected-option',function(e){
           var val = $('.hertz-modal-class').find('.hertz-modal-selected-option').html(); 
           if(val.indexOf('hertz')>-1 || val.indexOf('Hertz')>-1 ){
            $('.datepicker-wrapper').show();
           } else {
               $('.datepicker-wrapper').hide();
           }
        });
</script>