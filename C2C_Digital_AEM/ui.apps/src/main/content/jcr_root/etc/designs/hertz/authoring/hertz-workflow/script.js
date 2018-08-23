/** 
*This JS file shows/hides the scheduled activation datepicker based on the model selection event from the touch-UI  
*timeline's select workflow model dropdown. It will show it for Hertz related workflows and hide it otherwise.
*
* Custom Classes applied :- hertz-model-class & hertz-datetime-class
*/
    setTimeout(function(){ 
       $(document).on('DOMSubtreeModified','coral-select.hertz-model-class .coral3-Select-label', function (e) {
           var val = $('coral-select.hertz-model-class').find('.coral3-Select-label').html(); 
           debugger;
           if(val.indexOf('Hertz')==0 ){
               $('coral-datepicker.hertz-datetime-class').parent().show();
           } else {
               $('coral-datepicker.hertz-datetime-class').parent().hide();
           }
        });
    }, 1000);
