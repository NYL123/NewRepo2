<%--
  ADOBE CONFIDENTIAL

  Copyright 2012 Adobe Systems Incorporated
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
--%><%
%><%@page session="false" contentType="text/html; charset=utf-8"%><%
%><%@page import="java.util.Iterator,
                  java.util.List,
                  java.util.Vector,
                  java.util.Calendar,
                  org.apache.sling.api.resource.Resource,
                  org.apache.sling.api.resource.ValueMap,
                  org.apache.sling.commons.json.JSONObject,
                  com.adobe.granite.xss.XSSAPI,
                  com.day.cq.i18n.I18n,
                  com.day.cq.wcm.api.Page"%><%

%><%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.0"%><%
%><%@taglib prefix="cq" uri="http://www.day.com/taglibs/cq/1.0"%><%
%><%@taglib prefix="ui" uri="http://www.adobe.com/taglibs/granite/ui/1.0"%><%
%><cq:defineObjects /><%
%><script type="text/template" id="inboxitem_template" >
    <article class="card-page foundation-collection-item {{= Granite.XSS.getXSSValue(status) }}">
        <i class="select"></i>
        <a href="{{= Granite.XSS.getXSSValue(payload) }}" target="{{= Granite.XSS.getXSSValue(payloadTarget) }}">
            <div class="label inboxitem">
                <div class="main">
                    <p class="info-{{= Granite.XSS.getXSSValue(infoType) }}">{{=Granite.XSS.getXSSValue(Granite.I18n.getVar(info)) }}</p>
                    {{ if (currentAssigneeType == "group") { }}
                    <p class="info-locked">{{= Granite.XSS.getXSSValue(currentAssignee) }}</p>
                    {{ } }}
                    <p class="info-scheduledtime">Scheduled For : {{= Granite.XSS.getXSSValue(absoluteTime) }}</p>
                    <h4{{if (comment && comment.length > 0) { }} style="margin-top: 0.4375rem;"{{ } }}>
                        <span class="primary">{{= Granite.XSS.getXSSValue(Granite.I18n.getVar(title)) }}</span>
                        <span class="secondary">{{ if (comment.length > 0) { }}{{= Granite.XSS.getXSSValue(comment) }}{{ } }}</span>
                    </h4>
                    <p class="payload"{{if (payloadSummary.description && payloadSummary.description.length > 0) { }} style="margin-top: 0.4375rem;"{{ } }}>
                        <span class="primary payload-href">{{= Granite.XSS.getXSSValue(payloadTitle) }}</span>
                        <span class="secondary">{{if (payloadSummary.description) {}}{{= Granite.XSS.getXSSValue(payloadSummary.description) }}{{ } }}</span>
                    </p>
                </div>
                <div class="info">
                    {{ if (payloadSummary.icon) { }}
                    <span class="image">
                        <img class="show-list" src="{{= Granite.XSS.getXSSValue(payloadSummary.icon) }}" alt="{{= Granite.XSS.getXSSValue(payloadSummary.description) }}">
                    </span>
                    {{ } }}
                    <p class="modified">
                        <span class="date">{{= Granite.XSS.getXSSValue(modified) }}</span>
                    </p>
                </div>
            </div>
        </a>
    </article>
</script>