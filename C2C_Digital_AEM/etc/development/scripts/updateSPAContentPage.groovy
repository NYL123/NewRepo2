import org.apache.jackrabbit.commons.JcrUtils
import com.day.cq.commons.jcr.JcrUtil
import com.day.cq.commons.jcr.JcrConstants
/* 
*   Info    :-
*       To move the content saved in page properties(i.e. jcr:content) to the the respective component node under "configtext-parsys" node.
*   @author
*       Deepak.parma
*   Issue Faced :-
*       1  Hypen in Page name causing Issue in reaching to the actual page path so not using Pagebuilder provided by OOTB
*       2  Issues in impoting dependencies for Httpbuilder so used URLConnection in place of that hit the spa json
*   How to Use the Script   :-
*       domainUrl       <Enter the Environment URL>
*       authString      <Username:Password>
*       pagePath        <List of Page Url's to Modify the Content>
*       oldPropertyList <List of properties to be delete>
*       oldNodeList     <List of Node names to be deleted>
*   Note    :-
*       At Line number 208, deleteOldProperties() is comment by default().
*       If you need to delete the exsiting Page properties.
*       Please make sure to uncomment it before running the script.
*/
def domainUrl = "https://author.c2c-dev.hertz.com"
def authString = "admin:kevinsetthis".getBytes().encodeBase64().toString();
def pagePath = ["/content/hertz-rac/rac-web/us/en/home/testpage"]
def oldPropertyList = ["configurableCheckboxAriaLabelKey","configurableCheckboxAriaLabelValue","configurableCheckboxElementGroup","configurableCheckboxElementName","configurableCheckboxIsDefaultKey","configurableCheckboxIsDefaultValue","configurableCheckboxKey","configurableCheckboxOrderKey","configurableCheckboxOrderValue","configurableCheckboxValue","configurableLinkAriaLabelKey","configurableLinkAriaLabelValue","configurableLinkElement","configurableLinkElementGroup","configurableLinkTargetTypeKey","configurableLinkTextKey","configurableLinkTextValue","configurableLinkURLKey","configurableLinkURLValue","configurableMultiCheckboxItems","configurableMultiGroupItems","configurableMultiGroupsElementGroup","configurableMultiGroupsElementName","configurableMultiLinkItems","configurableMultiRadioButtonItems","configurableMultiTextItems","configurableRadioButtonAriaLabelKey","configurableRadioButtonAriaLabelValue","configurableRadioButtonElement","configurableRadioButtonElementGroup","configurableRadioButtonIsDefaultKey","configurableRadioButtonIsDefaultValue","configurableRadioButtonKey","configurableRadioButtonOrderKey","configurableRadioButtonOrderValue","configurableRadioButtonValue","configurableTextAriaLabelKey","configurableTextAriaLabelValue","configurableTextElementName","configurableTextErrorKey","configurableTextErrorValue","configurableTextLabelKey","configurableTextLabelValue","configurableTextAreaValue","configurableTextAreaKey","configurableTextAreaElementName","configurableMultiTextAreaItems"]
def oldNodeList = ["configurableMultiImageItems","multiGroups"]

def addSelectRadioFieldPair(childConfigNode, value){
    value.groupBy { it.elementGroup }.each { k,v ->
        Node genericFieldPairNode = JcrUtils.getOrCreateByPath(childConfigNode, "selectradiofieldpair", true, "nt:unstructured", "nt:unstructured", true)
        JcrUtil.setProperty(genericFieldPairNode, "sling:resourceType", "hertz/components/configtext/selectradiofieldpair")
        JcrUtil.setProperty(genericFieldPairNode, "key", k)
        save()
        def optionDisplayTextStack =[]
        def optionValueStack =[]
        def optionsListStack =[]
        v.each { it ->
            if(it.DefaultSelection){
                JcrUtil.setProperty(genericFieldPairNode, "ariaLabel", it.ariaLabel)
                JcrUtil.setProperty(genericFieldPairNode, "defaultValue", it.Text)
                JcrUtil.setProperty(genericFieldPairNode, "label", it.Text)
            }
            optionDisplayTextStack.push(it.Text)
            optionValueStack.push(it.element)
        }
        optionDisplayTextStack.eachWithIndex { item, index ->
            def optionsListMap = [:]
            optionsListMap.put("optionValue", optionValueStack[index]);
            optionsListMap.put("optionDisplayText", item);
            optionsListStack.push(new JsonBuilder(optionsListMap).toString())
        }
        JcrUtil.setProperty(genericFieldPairNode, "optionDisplayText", optionDisplayTextStack.toArray(new String[0]))
        JcrUtil.setProperty(genericFieldPairNode, "optionValue", optionValueStack.toArray(new String[0]))
        JcrUtil.setProperty(genericFieldPairNode, "optionsList", optionsListStack.toArray(new String[0]))
        save()
        optionDisplayTextStack.clear()
        optionValueStack.clear()
        optionsListStack.clear()
    }
}
def addSelectRadioFieldPairForMultifield(childConfigNode, value){
    Node genericFieldPairNode = JcrUtils.getOrCreateByPath(childConfigNode, "selectradiofieldpair", true, "nt:unstructured", "nt:unstructured", true)
    JcrUtil.setProperty(genericFieldPairNode, "sling:resourceType", "hertz/components/configtext/selectradiofieldpair")
    save()
    value.each { k1, v1  ->
        if(k1 == "element"){
            JcrUtil.setProperty(genericFieldPairNode, "defaultValue", v1)
            JcrUtil.setProperty(genericFieldPairNode, "label", v1)
            save()
        }else {
            def optionDisplayTextStack =[]
            def optionValueStack =[]
            def optionsListStack =[]
            JcrUtil.setProperty(genericFieldPairNode, "key", k1)
            v1.each{ k  ->
                k.each{k2, v2->
                    optionDisplayTextStack.push(v2)
                    optionValueStack.push(k2)
                }
                JcrUtil.setProperty(genericFieldPairNode, "optionDisplayText", optionDisplayTextStack.toArray(new String[0]))
                JcrUtil.setProperty(genericFieldPairNode, "optionValue", optionValueStack.toArray(new String[0]))
            }
            optionDisplayTextStack.eachWithIndex { item, index ->
                def optionsListMap = [:]
                optionsListMap.put("optionValue", optionValueStack[index]);
                optionsListMap.put("optionDisplayText", item);
                optionsListStack.push(new JsonBuilder(optionsListMap).toString())
            }
            JcrUtil.setProperty(genericFieldPairNode, "optionsList", optionsListStack.toArray(new String[0]))
            save()
            optionDisplayTextStack.clear()
            optionValueStack.clear()
            optionsListStack.clear()
        }
    }
}
def addGroupNode(childConfigNode, value){
    value.groupBy { it.elementGroup }.each { k,v ->  
           v.each { item ->
            item.each{ k1,v1 ->
                if(k1 != "element"){
                    Node genericFieldPairNode = JcrUtils.getOrCreateByPath(childConfigNode, "genericfieldpair", true, "nt:unstructured", "nt:unstructured", true)
                    JcrUtil.setProperty(genericFieldPairNode, "sling:resourceType", "hertz/components/configtext/genericfieldpair")
                    JcrUtil.setProperty(genericFieldPairNode, "key", item.element)
                    JcrUtil.setProperty(genericFieldPairNode, "label", v1)
                    JcrUtil.setProperty(genericFieldPairNode, "defaultValue", v1)
                    JcrUtil.setProperty(genericFieldPairNode, "ariaLabel", v1)
                    save()
                }
            }       
        }
    }
}
def addImageNode(childConfigNode, value){
    value.each { item  ->
        Node imageNode = JcrUtils.getOrCreateByPath(childConfigNode, "imagepair", true, "nt:unstructured", "nt:unstructured", true)
        JcrUtil.setProperty(imageNode, "sling:resourceType", "hertz/components/content/imagepair");
        JcrUtil.setProperty(imageNode, "key", item.element);
        JcrUtil.setProperty(imageNode, "alt", item.ImageAltText);
        JcrUtil.setProperty(imageNode, "fileReference", item.Image);
        save()
    }
}
def addTextareaNode(childConfigNode, value){
    value.each { item  ->
        Node imageNode = JcrUtils.getOrCreateByPath(childConfigNode, "textareapair", true, "nt:unstructured", "nt:unstructured", true)
        JcrUtil.setProperty(imageNode, "sling:resourceType", "hertz/components/configtext/textareapair");
        JcrUtil.setProperty(imageNode, "key", item.element);
        JcrUtil.setProperty(imageNode, "value", item.Text);
        save()
    }
}
def addGenericFieldPair(childConfigNode, value){
    value.each { item  ->
        Node genericFieldPairNode = JcrUtils.getOrCreateByPath(childConfigNode, "genericfieldpair", true, "nt:unstructured", "nt:unstructured", true)
        JcrUtil.setProperty(genericFieldPairNode, "sling:resourceType", "hertz/components/configtext/genericfieldpair")
        JcrUtil.setProperty(genericFieldPairNode, "key", item.element)
        JcrUtil.setProperty(genericFieldPairNode, "label", item.Label)
        JcrUtil.setProperty(genericFieldPairNode, "defaultValue", item.Label)
        JcrUtil.setProperty(genericFieldPairNode, "ariaLabel", item.ariaLabel)
        JcrUtil.setProperty(genericFieldPairNode, "error", item.error)
        save()
    }
}
def addLinkPair(childConfigNode, value){
    value.each { item  ->
        Node genericFieldPairNode = JcrUtils.getOrCreateByPath(childConfigNode, "linkpair", true, "nt:unstructured", "nt:unstructured", true)
        JcrUtil.setProperty(genericFieldPairNode, "sling:resourceType", "hertz/components/content/linkpair")
        JcrUtil.setProperty(genericFieldPairNode, "key", item.element)
        JcrUtil.setProperty(genericFieldPairNode, "href", item.TargetURL)
        JcrUtil.setProperty(genericFieldPairNode, "content", item.LinkText)
        save()
    }
}
def deleteOldProperties(pagePath, oldPropList, oldNodeList){
    try{
        Node pageNode = getNode(pagePath+"/"+JcrConstants.JCR_CONTENT)
        oldPropList.each{ item ->
            if(pageNode.hasProperty("$item")){
                pageNode.setProperty("$item",(Value)null);
            }
        }
        save()
        oldNodeList.each{ node ->
            pageNode.getNode("$node").remove()
        }
    } catch(PathNotFoundException exception){
        println "Node {$node} not found at: {$pagePath}"
    }
    save()
    println "Deleted Old Properties from: $pagePath"
}
def updateNode(pagePath, domainUrl, authString, oldPropList, oldNodeList){
    pagePath.each{
        resourcePath ->
        try {
            URLConnection conn = new URL(domainUrl+resourcePath+".spa.json").openConnection()
            conn.setRequestProperty("Authorization", "Basic ${authString}")
            def json = new JsonSlurper().parse(new BufferedReader(new InputStreamReader(conn.getInputStream()))).spacontent.configuredProperties
            if(json){
                Node childConfigNode = getNode(resourcePath+"/"+JcrConstants.JCR_CONTENT)
                if(!childConfigNode.hasNode("configtext-parsys")){
                    childConfigNode = JcrUtils.getOrCreateByPath(childConfigNode, "configtext-parsys", true, "nt:unstructured", "nt:unstructured", true)
                    JcrUtil.setProperty(childConfigNode, "sling:resourceType", "wcm/foundation/components/parsys");
                    save()
                }else{
                    childConfigNode = getNode(resourcePath+"/"+JcrConstants.JCR_CONTENT+"/configtext-parsys")
                }
                def found = json.each { key, value ->
                                if(key == "images"){
                                    addImageNode(childConfigNode, value)
                                } else if(key == "textfields"){
                                    addGenericFieldPair(childConfigNode, value)
                                } else if(key == "links"){
                                    addLinkPair(childConfigNode, value)
                                } else if(key == "checkboxes" || key == "radiobuttons"){
                                    addSelectRadioFieldPair(childConfigNode, value)
                                } else if(key == "multivalues"){
                                    addSelectRadioFieldPairForMultifield(childConfigNode, value)
                                } else if(key == "groups"){
                                    addGroupNode(childConfigNode, value)
                                } else if(key == "textareas"){
                                    addTextareaNode(childConfigNode, value)
                                }
                            }
                println "Completed Property migration for: $resourcePath"
                //deleteOldProperties(resourcePath, oldPropList, oldNodeList)
            }else{
                println "Empty configuredProperties for: $resourcePath"
            }
        } catch (IOException exception) {
            println "Invalid URL: $resourcePath"
        }
    }
}
def node = updateNode(pagePath, domainUrl, authString, oldPropertyList, oldNodeList)