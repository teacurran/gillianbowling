var isiPad = false;

function loaded() {
	//document.addEventListener('touchmove', function(e){ e.preventDefault(); });
	//myScroll = new iScroll('scroller');

	isiPad = navigator.userAgent.match(/iPad/i) != null;

	if (isiPad) {
		jQuery('#header').css({
			'height': '93px'
		});

		jQuery('#twitter').css({
			'top': '3px',
			'left': '575px'
		});
	}


	if (jQuery.browser.safari) {
		setTimeout(function () {
			new iScroll(document.getElementById('scroller'));
		}, 100);
	}
}

var twitterExpanded = false;
function expandTwitter() {

	var twitterExpand = jQuery("#twitterExpand");
	var twitterBubble = jQuery("#twitterBubble");
	if (!twitterExpanded) {
		jQuery("#twitterBubble").animate({
			height: "542px"
		}, {
			duration: 750,
			complete: function () {
				twitterExpand.attr("src", "#{request.contextPath}/images/expand_arrow_up.png");
				twitterExpand.css({
					'left': '275px'
				});
				jQuery(this).css({
					'overflow': 'auto',
					'scrollbars': 'auto'
				});
				twitterExpanded = true;
			}
		});
	} else {
		twitterBubble.css({
			'overflow': 'hidden',
			'scrollbars': 'none'
		});
		twitterExpand.attr("src", "#{request.contextPath}/images/expand_arrow_down.png");
		twitterExpand.css({
			'left': '295px'
		});
		twitterBubble.animate({
			height: "30px"
		}, {
			duration: 750,
			complete: function () {
				twitterExpanded = false;
			}
		});
	}
}

var currentOpenNav;
function openNav(navId) {
	if (currentOpenNav) {
		jQuery("#nav_" + currentOpenNav).slideUp(200, function () {
			if (jQuery("#nav_" + navId).length > 0) {
				jQuery("#nav_" + navId).slideDown(300);
				currentOpenNav = navId;
			}
		});
	} else {
		if (jQuery("#nav_" + navId).length > 0) {
			jQuery("#nav_" + navId).slideDown(300);
			currentOpenNav = navId;
		}
	}
}

Cufon.replace('.horiz', {fontFamily: 'FilosofiaItalic'});
Cufon.replace('.vert', {fontFamily: 'FilosofiaItalic'});

//document.addEventListener('DOMContentLoaded', loaded);
