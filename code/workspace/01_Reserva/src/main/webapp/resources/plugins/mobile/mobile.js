var isMobile = {
    Android: function() {
        return navigator.userAgent.match(/Android/i);
    },
    BlackBerry: function() {
        return navigator.userAgent.match(/BlackBerry/i);
    },
    iOS: function() {
        return navigator.userAgent.match(/iPhone|iPad|iPod/i);
    },
    Opera: function() {
        return navigator.userAgent.match(/Opera Mini/i);
    },
    Windows: function() {
        return navigator.userAgent.match(/IEMobile/i);
    },
    any: function() {
        return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows());
    }
};

function isMobileDevice() {
    return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows()) 
    || (typeof window.orientation !== "undefined") 
    || (navigator.userAgent.indexOf('IEMobile') !== -1);
};


function userAgentDetect() {
  if(window.navigator.userAgent.match(/Mobile/i)
  || window.navigator.userAgent.match(/iPhone/i)
  || window.navigator.userAgent.match(/iPod/i)
  || window.navigator.userAgent.match(/IEMobile/i)
  || window.navigator.userAgent.match(/Windows Phone/i)
  || window.navigator.userAgent.match(/Android/i)
  || window.navigator.userAgent.match(/BlackBerry/i)
  || window.navigator.userAgent.match(/webOS/i)) {
    document.body.className+=' mobile';
    //alert('True - Mobile - ' + navigator.userAgent);
  } else {
    //alert('False - Mobile - ' + navigator.userAgent);
    //return false;
  }
  if(window.navigator.userAgent.match(/Tablet/i)
  || window.navigator.userAgent.match(/iPad/i)
  || window.navigator.userAgent.match(/Nexus 7/i)
  || window.navigator.userAgent.match(/Nexus 10/i)
  || window.navigator.userAgent.match(/KFAPWI/i)) {
    document.body.className-=' mobile';
    document.body.className+=' tablet';
    //alert('True - Tablet - ' + navigator.userAgent);
  } else {
    //alert('False - Tablet - ' + navigator.userAgent);
    //return false;
  }
}
window.onload = userAgentDetect;