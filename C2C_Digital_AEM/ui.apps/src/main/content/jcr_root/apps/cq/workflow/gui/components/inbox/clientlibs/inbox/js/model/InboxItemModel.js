/*
 * ADOBE CONFIDENTIAL
 *
 * Copyright 2012 Adobe Systems Incorporated
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 */
(function(Granite) {
    "use strict";

    var ui = Granite.UI.Extension,
        wf = Granite.Workflow;

    function getPayloadTitle(model) {
        var title = model.get("payloadTitle");
        if (!title) {
            title = Granite.XSS.getXSSRecordPropertyValue(model, "payloadPath");
            if (!title) {
                title = Granite.I18n.get("The resource used to start this instance does not exist anymore.");
            }
        }
        return title;
    }


    wf.domain.InboxItemModel = ui.domain.ItemModel.extend({

        initialize: function(attributes, options) {
			
            //Item Type
            var type = this.get("inboxItemType");
            if (!type) {
                type = wf.domain.InboxItemModel.TYPE_NOTIFICATION;
                this.set("inboxItemType", type);
            }
            this.set("info", type);
            this.set("infoType", type.toLowerCase());

            //Start Time
            if (wf.domain.InboxItemModel.TYPE_NOTIFICATION == type) {
                if (this.get("date")) {
                    this.set("startTime", Date.parse(this.get("date")));
                }
            }

            if (this.get("startTime")) {
                var startMoment = moment(this.get("startTime"));
                this.set("modified", ui.Utils.formatRelativeDate(startMoment.toDate()));
            }
            if(this.attributes.metaData.instance.absoluteTime){
                var absoluteTime = this.attributes.metaData.instance.absoluteTime;
                this.set("absoluteTime", new Date(absoluteTime*1));
            } 


            //Current Assignee
            if (!this.get("currentAssigneeType")) {
                this.set("currentAssigneeType", "group");
            }
            this.set("currentAssigneeType", this.get("currentAssigneeType").toLowerCase());

            //Payload
            this.set("payloadTarget", "_self");
            this.executePayloadPlugin("initialize");
            if (this.get("payloadSummary")) {
                var summary = this.get("payloadSummary");
                if (summary.icon) {
                    summary.icon = Granite.HTTP.externalize(summary.icon);
                }
            }
            if (!this.get("payload") || !this.get("payloadPath")) {
                this.set("payload", "#");
            } else {
                var context = Granite.HTTP.getContextPath()?Granite.HTTP.getContextPath():"";
                this.set("payload", Granite.HTTP.externalize(this.get("payload")));
            }

            this.executeItemTypePlugin("initialize");

            this.set("payloadTitle", getPayloadTitle(this));

        },

        executeItemTypePlugin: function(func, options) {
            return this.executePlugin("inboxItemType", func, options);
        },

        executePayloadPlugin: function(func, options) {
            return this.executePlugin("payloadType", func, options);
        },

        toJSON: function() {
            var json = this.executeItemTypePlugin("toJSON") || {};

            return $.extend({}, {
                item: this.get("item"),
                inboxItemType: this.get("inboxItemType"),
                computedItemType: this.get("computedItemType"),
                info: Granite.I18n.getVar(this.get("info")) || "",
                infoType: this.get("infoType"),
                title: Granite.I18n.getVar(this.get("title")) || "",
                description: Granite.I18n.getVar(this.get("description")) || "",
                comment: Granite.I18n.getVar(this.get("comment")) || "",
                currentAssignee: this.get("currentAssignee"),
                currentAssigneeType: this.get("currentAssigneeType"),
                startTime: moment(this.get("startTime")).format("LLL"),
                lastModified: moment(this.get("lastModified")).format("LLL"),
                modified: this.get("modified"),
                payload: this.get("payload"),
                payloadPath: this.get("payloadPath"),
                payloadTitle: Granite.I18n.getVar(this.get("payloadTitle")) || "",
                payloadTarget: this.get("payloadTarget"),
                payloadSummary: this.get("payloadSummary") || {},
                status: this.get("status"),
                absoluteTime: this.get("absoluteTime") || {}
            }, json);
        }


    }, {
        TYPE_NOTIFICATION: "NotificationItem",
        TYPE_WORKITEM: "WorkItem",
        TYPE_FAILUREITEM: "FailureItem",
        TYPE_TASK: "Task"
    });

})(Granite);
