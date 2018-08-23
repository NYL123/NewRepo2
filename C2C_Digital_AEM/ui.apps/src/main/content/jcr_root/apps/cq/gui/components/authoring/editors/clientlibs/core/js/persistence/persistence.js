/*
 * ADOBE CONFIDENTIAL
 *
 * Copyright 2013 Adobe Systems Incorporated
 * All Rights Reserved.
 *
 * NOTICE: All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any. The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 */
;
(function ($, ns, channel, window, undefined) {

    /**
     * An {@link Granite.author.Editable} is about to be updated
     *
     * @event Document#cq-persistence-before-update
     */

    /**
     * An {@link Granite.author.Editable} has been updated
     *
     * @event Document#cq-persistence-after-update
     */

    /**
     * An {@link Granite.author.Editable} is about to be created
     *
     * @event Document#cq-persistence-before-create
     */

    /**
     * An {@link Granite.author.Editable} has been created
     *
     * @event Document#cq-persistence-after-create
     */

    /**
     * An {@link Granite.author.Editable} is about to be moved
     *
     * @event Document#cq-persistence-before-move
     */

    /**
     * An {@link Granite.author.Editable} has been moved
     *
     * @event Document#cq-persistence-after-move
     */

    /**
     * An {@link Granite.author.Editable} is about to be copied
     *
     * @event Document#cq-persistence-before-copy
     */

    /**
     * An {@link Granite.author.Editable} has been copied
     *
     * @event Document#cq-persistence-after-copy
     */

    /**
     * An {@link Granite.author.Editable} is about to be deleted
     *
     * @event Document#cq-persistence-before-delete
     */

    /**
     * An {@link Granite.author.Editable} has been deleted
     *
     * @event Document#cq-persistence-after-delete
     */

    /**
     *  Create/Read/Update/Delete paragraphs and corresponding editables.
     *  */
    var self = ns.persistence;

    /**
     * Create a new paragraph.
     *
     * @fires Document#cq-persistence-before-create
     * @fires Document#cq-persistence-after-create
     *
     * @param {Object} component The component that will be instantiated.
     * @param {String} relativePosition The position (after/before/last) of the new paragraph relative to its targeted neighbor.
     * @param {Object} editableNeighbor The editable adjacent to the new paragraph to create.
     * @param {Object} [additionalData] Additional data to be saved
     * @return {$.Promise} A deferred object that will be resolved when the request is completed.
     */
    self.createParagraph = function (component, relativePosition, editableNeighbor, additionalData) {
        var args = arguments;

        channel.trigger("cq-persistence-before-create", args);

        return (
            sendCreateParagraph({
                    resourceType: component.getResourceType(),
                    parentPath: editableNeighbor.getParentPath(),
                    parentResourceType: editableNeighbor.getParentResourceType(),
                    relativePosition: relativePosition,
                    neighborName: editableNeighbor.getNodeName(),
                    configParams: component.getConfigParams(),
                    extraParams: component.getExtraParams(),
                    templatePath: component.getTemplatePath()
                }, additionalData)
                .done(function () {
                    channel.trigger("cq-persistence-after-create", args);
                })
                .fail(function () {
                    ns.ui.helpers.notify(Granite.I18n.get("Paragraph create operation failed."));
                })
            );

    };

    /**
     * Move a paragraph.
     *
     * @fires Document#cq-persistence-before-move
     * @fires Document#cq-persistence-after-move
     *
     * @param {Object} editable The editable corresponding to the paragraph that will be moved.
     * @param {String} relativePosition The new position (after/before/last) of the paragraph relative to its targeted neighbor.
     * @param {Object} editableNeighbor The editable adjacent to the paragraph to move.
     * @return {$.Promise} A deferred object that will be resolved when the request is completed.
     */
    self.moveParagraph = function (editable, relativePosition, editableNeighbor) {
        var args = arguments;

        channel.trigger("cq-persistence-before-move", args);

        return (
           sendMoveParagraph({
                    path: editable.path,
                    parentPath: editableNeighbor.getParentPath(),
                    relativePosition: relativePosition,
                    neighborName: editableNeighbor.getNodeName(),
                    resourceType: editable.type
                })
                .done(function () {
                    channel.trigger("cq-persistence-after-move", args);
                })
                .fail(function () {
                    //ns.ui.helpers.notify(Granite.I18n.get("Paragraph move operation failed 1234576."));
                })
            );
    };

    /**
     * Copy a paragraph.
     *
     *
     * @fires Document#cq-persistence-before-copy
     * @fires Document#cq-persistence-after-copy
     *
     * @param {Object} editable The editable corresponding to the paragraph that will be copied.
     * @param {String} relativePosition The position (after/before/last) of the copied paragraph relative to its targeted neighbor.
     * @param {Object} editableNeighbor The editable adjacent to the paragraph to copy.
     * @return {$.Promise} A deferred object that will be resolved when the request is completed.
     */
    self.copyParagraph = function (editable, relativePosition, editableNeighbor) {
        var args = arguments;

        channel.trigger("cq-persistence-before-copy", args);

        return (
            sendCopyParagraph({
                    path: editable.path,
                    parentPath: editableNeighbor.getParentPath(),
                    relativePosition: relativePosition,
                    neighborName: editableNeighbor.getNodeName()
                })
                .done(function (data, textStatus, jqXHR) {
                    sendReadParagraph({
                        // Read Path data from the Sling POST HTML response
                        path: $(data).find("#Path").text()
                    })
                    .done(function () {
                        channel.trigger("cq-persistence-after-copy", args);
                    });
                })
                .fail(function () {
                    ns.ui.helpers.notify(Granite.I18n.get("Paragraph copy operation failed."));
                })
            );
    };

    /**
     * Update a paragraph (i.e., its properties).
     *
     * @fires Document#cq-persistence-before-update
     * @fires Document#cq-persistence-after-update
     *
     *
     * @param {Object} editable The editable corresponding to the paragraph to update.
     * @param {Object} properties The properties to update.
     * @return {$.Promise} A deferred object that will be resolved when the request is completed.
     */
    self.updateParagraph = function (editable, properties) {
        var args = arguments;

        channel.trigger("cq-persistence-before-update", args);

        return (
            sendUpdateParagraph({
                    path: editable.path,
                    properties: properties
                })
            ).done(function () {
                channel.trigger("cq-persistence-after-update", args);
            });
    };

    /**
     * Update a paragraph property (convenience method).
     *
     * @fires Document#cq-persistence-before-delete
     * @fires Document#cq-persistence-after-delete
     *
     * @param {Object} editable The editable corresponding to the paragraph to update.
     * @param {String} property The property name to update.
     * @param {String} content The property content to update.
     * @return {$.Promise} A deferred object that will be resolved when the request is completed.
     */
    self.updateParagraphProperty = function (editable, property, content) {
        var properties = {};
        properties[property] = content;
        return self.updateParagraph(editable, properties);
    };

    /**
     * Delete a paragraph.
     * @param {Object} editable The editable corresponding to the paragraph to delete.
     * @return {$.Promise} A deferred object that will be resolved when the request is completed.
     */
    self.deleteParagraph = function (editable) {
        var args = arguments;

        channel.trigger("cq-persistence-before-delete", args);

        return (
            sendDeleteParagraph({
                    path: editable.path
                })
                .done(function () {
                    channel.trigger("cq-persistence-after-delete", args);
                })
                .fail(function () {
                    ns.ui.helpers.notify(Granite.I18n.get("Paragraph delete operation failed."));
                })
            );
    };

    /**
     * Read a paragraph.
     * @param {Object} editable The editable corresponding to the paragraph to read.
     * @return {$.Promise} A deferred object that will be resolved when the request is completed.
     */
    self.readParagraph = function (editable, config) {
        var config = config || {};
        config.path = editable.slingPath ? editable.slingPath : editable.path;

        return sendReadParagraph(config);
    };

    /**
     * Read a paragraph's content.
     * @param {Object} editable The editable corresponding to the paragraph's content to read.
     * @param {Boolean} async false to make the read request synchronous. Default value is true.
     * @return {$.Promise} A deferred object that will be resolved when the request is completed.
     */
    self.readParagraphContent = function (editable, async) {
        if (async == undefined) {
            // set async to true(default value), if its null or undefined.
            async = true;
        }
        return sendReadParagraphContent({
            path: editable.slingPath ? editable.slingPath : editable.path
        }, async);
    };


    /**
     * Send a request to create a new paragraph.
     * @param {String} config.resourceType The resource type of the new paragraph.
     * @param {String} config.parentPath The path to the parent container of the new paragraph.
     * @param {String} config.parentResourceType The resource type of the parent container of the new paragraph.
     * @param {String} config.relativePosition The position (after/before/last) of the new paragraph relative to its targeted neighbor.
     * @param {String} [config.neighborName] The name of the paragraph adjacent to the new paragraph.
     * @param {Object} [config.configParams] The config parameters to be set upon the new paragraph's creation.
     * @param {Object} [config.extraParams] The extra parameters (would override other params) to be set upon the new paragraph's creation.
     * @param {String} [config.templatePath] The path to the template definition that should be used.
     * @param {String} [config.nameHint] A hint to generate a name for the new paragraph.
     * @return {$.Promise} A deferred object that will be resolved when the request is completed.
     */
    var sendCreateParagraph = function (config, additionalData) {
        return (
            new ns.persistence.PostRequest()
                .prepareCreateParagraph(config)
                .setParams(additionalData)
                .send()
            );
    };

    /**
     * Send a request to delete a paragraph.
     * @param {String} config.path The path to the paragraph to be deleted.
     * @return {$.Promise} A deferred object that will be resolved when the request is completed.
     */
    var sendDeleteParagraph = function (config) {
        return (
            new ns.persistence.PostRequest()
                .prepareDeleteParagraph(config)
                .send()
            );
    };

    /**
     * Send a request to copy a paragraph.
     * @param {String} config.path The path to the paragraph to copy.
     * @param {String} config.parentPath The path to the parent container of the new paragraph.
     * @param {String} config.relativePosition The position (after/before/last) of the copied paragraph relative to its targeted neighbor.
     * @param {String} [config.neighborName] The name of the paragraph adjacent to the new paragraph.
     * @return {$.Promise} A deferred object that will be resolved when the request is completed.
     */
    var sendCopyParagraph = function (config) {
        return (
            new ns.persistence.PostRequest()
                .prepareCopyParagraph(config)
                .send()
            );
    };

    /**
     * Send a request to move a paragraph.
     * @param {String} config.path The path to the paragraph to move.
     * @param {String} config.parentPath The path to the parent container of the new paragraph.
     * @param {String} config.relativePosition The new position (after/before/last) of the paragraph relative to its targeted neighbor.
     * @param {String} [config.neighborName] The name of the paragraph adjacent to the new paragraph.
     * @param {String} [config.resourceType] The resource type of the new paragraph.
     * @return {$.Promise} A deferred object that will be resolved when the request is completed.
     */
    var sendMoveParagraph = function (config) {
        return (
            new ns.persistence.PostRequest()
                .prepareMoveParagraph(config)
                .send()
            );
    };

    /**
     * Send a request to update a paragraph (i.e., update its properties).
     * @param {String} config.path The path to the paragraph to update.
     * @param {Object} config.properties The properties to update.
     * @return {$.Promise} A deferred object that will be resolved when the request is completed.
     */
    var sendUpdateParagraph = function (config) {
        return (
            new ns.persistence.PostRequest()
                .prepareUpdateParagraph(config)
                .send()
            );
    };

    /**
     * Send a request to update a property of a paragraph (convenience method).
     * @param {String} config.path The path to the paragraph to update.
     * @param {String} config.name The name of the property to update.
     * @param {String} config.value The value of the property to update.
     * @return {$.Promise} A deferred object that will be resolved when the request is completed.
     */
    var sendUpdateParagraphProperty = function (config) {
        return this.updateParagraph(config);
    };

    /**
     * Send a request to read a paragraph.
     * @param {String} config.path The path to the paragraph to read.
     * @return {$.Promise} A deferred object that will be resolved when the request is completed.
     */
    var sendReadParagraph = function (config) {
        return (
            new ns.persistence.GetRequest()
                .prepareReadParagraph(config)
                .send()
            );
    };

    /**
     * Send a request to read the content of a paragraph.
     * @param {String} config.path The path to the paragraph's content to read.
     * @param {Boolean} async false to make the read request synchronous
     * @return {$.Promise} A deferred object that will be resolved when the request is completed.
     */
    var sendReadParagraphContent = function (config, async) {
        return (
            new ns.persistence.GetRequest({async: async})
                .prepareReadParagraphContent(config)
                .send()
        );
    };

}(jQuery, Granite.author, jQuery(document), this));