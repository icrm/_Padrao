(function($, undefined) {
    $.fn.iit=function() {}
    $.fn.iit.ajaxError=function(xhr, status, exception, page) {
        window.location = page;
    };
})(jQuery);