$(document).ready(function(){
	
	//lnb
	$('.dep_menu').parent().addClass("active");
	$('.active a').addClass("act");
	var atc = $('.lnb > li.active');
		atc.addClass('hidden');
		atc.find('.dep_menu').hide();
	$('.lnb > li > .act').click (function() {
		var accatc = $(this).parents('.lnb > li:first');
		if(accatc.hasClass('hidden')) {
			accatc.removeClass('hidden').addClass('on').find('.dep_menu').slideDown(100);
		} else {
			accatc.removeClass('on').addClass('hidden').find('.dep_menu').slideUp(100);
		}
		return false;
	});
	$('.lnb li.on').removeClass('hidden').find('.dep_menu').show();

	$("#aside").bind("mouseenter focusin", function(){		
		$(this).addClass("open").removeClass("close");
	});
	$("#aside").bind("mouseleave", function(){
		$(this).removeClass("open").addClass("close");
	});

	//검색조건
	$('.btn_detail_search').click(function(){
		if (!$(this).hasClass('on'))
		{
			$(".detail_search").animate({height: "toggle"},200);
			$(this).addClass('on');
		}else{
			$(".detail_search").animate({height: "toggle"},200);
			$(this).removeClass('on');
		}
		return false;
	});

	//상세조회
	$('.btn_detail').click(function(){
		if (!$(this).hasClass('on'))
		{
			$(".detail_view").animate({height: "toggle"},200);
			$(this).addClass('on');
		}else{
			$(".detail_view").animate({height: "toggle"},200);
			$(this).removeClass('on');
		}
		return false;
	});
	$('.layer_close').on('click', function(){
	    $(".detail_view").animate({height: "toggle"},200);
		$(this).removeClass('on');
	});
	
	//자동완성
	$('.btn_edit').click(function(){
		if (!$(this).hasClass('on'))
		{
			$(".edit_view").animate({height: "toggle"},300);
			$(this).addClass('on');
			$(".edit_mask").animate({height: "toggle"},10);
		}else{
			$(".edit_view").animate({height: "toggle"},300);
			$(this).removeClass('on');
			$(".edit_mask").animate({height: "toggle"},10);
		}
		return false;
	});
	$('.edit_mask').on('click', function(){
	    $(".edit_view").animate({height: "toggle"},300);
		$(this).removeClass('on');
		$(".edit_mask").animate({height: "toggle"},10);
	});
	
	//layer popup
	$('.layer_detail').click(function(){
        $('#detail_info').bPopup({
		    modalClose: false,
		    opacity: 0.7			
		});
    });

    $(document).on('click', '.btn_layer', function (event) {
        event.stopPropagation();
        event.preventDefault();
        var popup = $(this).attr('href');
        $(popup).bPopup({
            modalClose: false,
            opacity: 0.7
        });
    });
	
    $(document).on('click', '.loading_layer', function (event) {
        event.stopPropagation();
        event.preventDefault();
        var popup = $(this).attr('href');
        $(popup).bPopup({
            modalClose: false,
            opacity: 0
        });
    });
    //파일첨부
    $(document).on('click', '.file_attach .btn_file', function() {
        $(this).closest('.file_attach').find('.form_file').click();
    });

    //접근권한 보기
    $('.btn_auth').click(function(){
		if (!$(this).hasClass('on'))
		{
			$(".auth_view").animate({height: "toggle"},200);
			$(this).addClass('on');
		}else{
			$(".auth_view").animate({height: "toggle"},200);
			$(this).removeClass('on');
		}
		return false;
	});	
	
	//tab
	var $tabsNav    = $('.tabs'),
		$tabsNavLis = $tabsNav.children('li'),
		$tabContent = $('.cont');
	$tabsNav.each(function() {
		var $this = $(this);
		$this.next().children('.cont').stop(true,true).hide().first().show();
		$this.children('li').first().addClass('on').stop(true,true).show();
	});
	$tabsNavLis.on('click', function(e) {
		var $this = $(this);
		$this.siblings().removeClass('on').end().addClass('on');
		$this.parent().next().children('.cont').stop(true,true).hide().siblings( $this.find('a').attr('href') ).fadeIn(0);
		e.preventDefault();
	});

	$('.boxgrid').hover(function(){
		$('.cover', this).fadeIn();	
	}, function() {
		$('.cover', this).fadeOut();
	});

	wow = new WOW(
      {
        animateClass: 'animated',
        offset:       0,
        callback:     function(box) {
          console.log("WOW: animating <" + box.tagName.toLowerCase() + ">")
        }
      }
    );
    wow.init();
});
