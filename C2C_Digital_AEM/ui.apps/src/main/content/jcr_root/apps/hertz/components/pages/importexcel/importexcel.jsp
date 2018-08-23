<%@include file="/libs/foundation/global.jsp"%>
<cq:includeClientLib categories="cq.jquery" />
<script type="text/javascript">
   
jQuery(function ($) {
       
      });
   
</script>

<body>
 <%
                String damPath = properties.get("damPath", "None");
                String contentPath = properties.get("contentPath", "None");
    //String title = properties.get("jcr:title", String.class); //defaults to null
%>


    <center>

<div>


    <h2>Upload an Excel file to the AEM DAM</h2>
    <span style="color:red">
        <p>* Please configure/check the <strong>content path</strong> below before uploading any excel sheet for ingestion.</p>
        <p>Please use <strong>page properties</strong> in edit mode to configure these values</p>
    </span>
    <h3>Current DAM Path: </h3><span style="color: #3300ff;
    font-size: 17px;
    background-color: #edffca;
	background-size: 20px 20px;
    background-repeat: no-repeat;
    background-position: 2px;
    padding: 5px;
    border-radius: 5px 5px 5px;
    border: 1px solid #efff00;
    padding-left: 25px;
    position: relative;"><%= damPath %> </span><br/>
    <h3>Current Content Path: </h3><span style="color: #3300ff;
    font-size: 17px;
    background-color: #edffca;
	background-size: 20px 20px;
    background-repeat: no-repeat;
    background-position: 2px;
    padding: 5px;
    border-radius: 5px 5px 5px;
    border: 1px solid #efff00;
    padding-left: 25px;
    position: relative;"><%= contentPath %> </span><br/>
       <!--   <p id="support-notice">Your browser does not support Ajax uploads :-(The form will be submitted as normal.</p> -->

        <!-- The form starts -->
        <form action="/" method="POST" enctype="multipart/form-data" id="form-id">
            <!-- The file to upload -->
            <p style="margin-top: 2%;"><input id="file-id" type="file" name="our-file" style="background-color: aliceblue" />

                <!--
                  Also by default, we disable the upload button.
                  If Ajax uploads are supported we'll enable it.
                -->





                <input type="button" value="Upload" id="upload-button-id" class="upload" disabled="disabled" />
</p>



            <script>
                // Function that will allow us to know if Ajax uploads are supported
                function supportAjaxUploadWithProgress() {
                    return supportFileAPI() && supportAjaxUploadProgressEvents() && supportFormData();
   
                    // Is the File API supported?
                    function supportFileAPI() {
                        var fi = document.createElement('INPUT');
                        fi.type = 'file';
                        return 'files' in fi;
                    };
   
                    // Are progress events supported?
                    function supportAjaxUploadProgressEvents() {
                        var xhr = new XMLHttpRequest();
                        return !! (xhr && ('upload' in xhr) && ('onprogress' in xhr.upload));
                    };
   
                    // Is FormData supported?
                    function supportFormData() {
                        return !! window.FormData;
                    }
                }
   
                // Actually confirm support
                if (supportAjaxUploadWithProgress()) {
                    // Ajax uploads are supported!
                    // Change the support message and enable the upload button
                   // var notice = document.getElementById('support-notice');
                    var uploadBtn = document.getElementById('upload-button-id');
                   // notice.innerHTML = "Your browser supports HTML uploads to AEM.";
                    uploadBtn.removeAttribute('disabled');
   
                    // Init the Ajax form submission
                    initFullFormAjaxUpload();
   
                    // Init the single-field file upload
                    initFileOnlyAjaxUpload();
                }
   
                function initFullFormAjaxUpload() {
                    var form = document.getElementById('form-id');
                    form.onsubmit = function() {
                        // FormData receives the whole form
                        var formData = new FormData(form);
   
                        // We send the data where the form wanted
                        var action = form.getAttribute('action');
   
                        // Code common to both variants
                        sendXHRequest(formData, action);
   
                        // Avoid normal form submission
                        return false;
                    }
                }
   
                function initFileOnlyAjaxUpload() {
                    var uploadBtn = document.getElementById('upload-button-id');
                    uploadBtn.onclick = function (evt) {
                        var formData = new FormData();
   
                        // Since this is the file only, we send it to a specific location
                        //   var action = '/upload';
   
                        // FormData only has the file
                        var fileInput = document.getElementById('file-id');
                        var file = fileInput.files[0];
                        formData.append('our-file', file);
                        var damPath ="<%=damPath%>";
                        var contentPath ="<%=contentPath%>";
                        alert ("damPath "+damPath);
                        alert ("contentPath "+contentPath);
   						formData.append('damPath', damPath);
   						formData.append('contentPath', contentPath);
                        // Code common to both variants
                        sendXHRequest(formData);
                    }
                }
   
                // Once the FormData instance is ready and we know
                // where to send the data, the code is the same
                // for both variants of this technique
                function sendXHRequest(formData) {
   
                    var test = 0; 
   
                    $.ajax({
                        type: 'POST',    
                        url:'/bin/hertz/importexcel',
                        processData: false,  
                        contentType: false,  
                        data:formData,
                        success: function(msg){
                          alert(msg); //display the data returned by the servlet
                        }
                    });
                       
                }
   
                // Handle the start of the transmission
                function onloadstartHandler(evt) {
                    var div = document.getElementById('upload-status');
                    div.innerHTML = 'Upload started!';
                }
   
                // Handle the end of the transmission
                function onloadHandler(event) {
                    //Refresh the URL for Form Preview
                    var msg = event.target.responseText;
   
                   alert(msg);
                }
   
                // Handle the progress
                function onprogressHandler(evt) {
                    var div = document.getElementById('progress');
                    var percent = evt.loaded/evt.total*100;
                    div.innerHTML = 'Progress: ' + percent + '%';
                }
   
                // Handle the response from the server
                function onreadystatechangeHandler(evt) {
                    var status = null;
   
                    try {
                        status = evt.target.status;
                    }
                    catch(e) {
                        return;
                    }
   
                    if (status == '200' && evt.target.responseText) {
                        var result = document.getElementById('result');
                        result.innerHTML = '<p>The server saw it as:</p><pre>' + evt.target.responseText + '</pre>';
                    }
                }
            </script>
   
            <!-- Placeholders for messages set by event handlers -->
            <p id="upload-status"></p>
            <p id="progress"></p>
            <pre id="result"></pre>
   
        </form>
    <span>

        <p style="font-size:20px;font-family: monospace;margin-top:5%;">Excel Sheet Naming Conventions</p>
        <table>
              <tr>
                <th>Functionality</th>
                <th>Naming Contract (Prefix)</th>
                <th>Download file from DAM</th>

              </tr>
              <tr>
                  <td>US/Europe Rewards Data</td>
                <td>rewards_</td>
                <td><a href="/bin/hertz/importexcel?name=rewards" style="font-size: 15px; text-decoration: none">Download</a></td>

              </tr>
              <tr>
                <td>Country Data</td>
                <td>country_</td>
                <td><a href="/bin/hertz/importexcel?name=country" style="font-size: 15px; text-decoration: none">Download</a></td>

              </tr>
              <tr>
                <td>Ernst Handel</td>
                <td>airline_</td>
                <td><a href="/bin/hertz/importexcel?name=airline" style="font-size: 15px; text-decoration: none">Download</a></td>

              </tr>
              <tr>
                <td>States Data</td>
                <td>state_</td>
                <td><a href="/bin/hertz/importexcel?name=state" style="font-size: 15px; text-decoration: none">Download</a></td>

              </tr>
              <tr>
                <td>Error Messages Data</td>
                <td>errormsg_</td>
                <td><a href="/bin/hertz/importexcel?name=errormsg" style="font-size: 15px; text-decoration: none">Download</a></td>

              </tr>
              <tr>
                <td>Config Text Data</td>
                <td>configtext_</td>
                <td><a href="/bin/hertz/importexcel?name=configtext" style="font-size: 15px; text-decoration: none">Download</a></td>

              </tr>
              <tr>
                <td>Residency Country List</td>
                <td>residencycountry_</td>
                <td><a href="/bin/hertz/importexcel?name=residencycountry" style="font-size: 15px; text-decoration: none">Download</a></td>

              </tr>
</table>
    </span>
</div>
    </center>

</body>
<style>
table {
    font-family: arial, sans-serif;
    border-collapse: collapse;
    width: 50%;
}

td, th {
    border: 1px solid #dddddd;
    text-align: center;
    padding: 8px;

}

tr:nth-child(even) {
    background-color: #dddddd;
}
    .fileUpload {
    position: relative;
    overflow: hidden;
    margin: 10px;
}
.fileUpload input.upload {
    position: absolute;
    top: 0;
    right: 0;
    margin: 0;
    padding: 0;
    font-size: 20px;
    cursor: pointer;
    opacity: 0;
    filter: alpha(opacity=0);
}
</style>

</html>