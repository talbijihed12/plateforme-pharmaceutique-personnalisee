$(document).ready(function() {

  // Change active item
  function doActiveItem(elem, enableScroll = true) {

    var $this = elem,
      elementClicked = $this.attr("href"),
      destination = $(elementClicked).offset().top,
      latestActive = $('.documentation-menu li a.active');

    if ($this.closest('.menu-item-has-children').find('.active').attr('href') != elementClicked) {

      $('.documentation-menu li a').removeClass('active');
      $this.addClass('active');

      if (enableScroll == true) {
        $("html, body").animate({
          scrollTop: destination - 98
        }, 0);
      }

      toggleCollapse($this);

    }

  }

  // Scroll bar progress
  function scrollProgress() {
    var winScroll = document.body.scrollTop || document.documentElement.scrollTop;
    var height = document.documentElement.scrollHeight - document.documentElement.clientHeight;
    var scrolled = (winScroll / height) * 100;
    $("#scrollProgress").css({
      "width": scrolled + "%"
    });
  }

  // Track which Item we are currently on
  function trackActiveOnScroll() {
    var currentTop = $(window).scrollTop();
    var elems = $('section.hentry');
    elems.each(function(index) {
      var elemTop = $(this).offset().top;
      var elemBottom = elemTop + $(this).height();
      if (currentTop >= (elemTop - 98) && currentTop <= (elemBottom - 98)) {

        var id = $(this).attr('id'),
        navElem = $('.documentation-menu li a[href="#' + id + '"]');

        $('.documentation-menu li a').removeClass('active');
        navElem.addClass('active');

        toggleCollapse(navElem);

      }
    });
  }

  // Toggles the collapse on and off in the documentation menu
  function toggleCollapse(elem){

    var hasChildren = elem.parent().hasClass('menu-item-has-children');

    if (hasChildren) {
      $('.documentation-menu .children').not(elem.next()).slideUp();
      elem.next().slideDown();
    }

  }

  // Gallery
  $('.gallery-thumb').magnificPopup({
    type: 'image',
    gallery: {
      enabled: true
    }
  });

  // Scroll to Menu item section and set Active
  $('.documentation-menu li a').click(function(e) {
    doActiveItem($(this));
    return false;
  });

  // On scroll events
  $(window).on('scroll', function() {
    scrollProgress();
    trackActiveOnScroll();
  });

  // Initialize Progress bar on load
  scrollProgress();

  window.onload = function () {
    dp.SyntaxHighlighter.ClipboardSwf = '/script/clipboard.swf';
    dp.SyntaxHighlighter.HighlightAll('code');
	}

});
