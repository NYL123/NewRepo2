(function ($, $document) {
    $document.on('cq-layer-activated', refreshPage);

    function refreshPage(ev){
        if(ev.prevLayer && ev.layer !== ev.prevLayer){
			 window.location.reload();
        }
    }
}(jQuery, jQuery(document)));