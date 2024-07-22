$(function(){
	 $('.toggle').click(function(){
		$(this).parent().parent().find('.items').toggleClass('hide');
	 });
});