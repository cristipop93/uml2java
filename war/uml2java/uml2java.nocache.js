function Uml2Java(){
  var $wnd_0 = window;
  var $doc_0 = document;
  sendStats('bootstrap', 'begin');
  function isHostedMode(){
    var query = $wnd_0.location.search;
    return query.indexOf('gwt.codesvr.Uml2Java=') != -1 || query.indexOf('gwt.codesvr=') != -1;
  }

  function sendStats(evtGroupString, typeString){
    if ($wnd_0.__gwtStatsEvent) {
      $wnd_0.__gwtStatsEvent({moduleName:'Uml2Java', sessionId:$wnd_0.__gwtStatsSessionId, subSystem:'startup', evtGroup:evtGroupString, millis:(new Date).getTime(), type:typeString});
    }
  }

  Uml2Java.__sendStats = sendStats;
  Uml2Java.__moduleName = 'Uml2Java';
  Uml2Java.__errFn = null;
  Uml2Java.__moduleBase = 'DUMMY';
  Uml2Java.__softPermutationId = 0;
  Uml2Java.__computePropValue = null;
  Uml2Java.__getPropMap = null;
  Uml2Java.__gwtInstallCode = function(){
  }
  ;
  Uml2Java.__gwtStartLoadingFragment = function(){
    return null;
  }
  ;
  var __gwt_isKnownPropertyValue = function(){
    return false;
  }
  ;
  var __gwt_getMetaProperty = function(){
    return null;
  }
  ;
  __propertyErrorFunction = null;
  var activeModules = $wnd_0.__gwt_activeModules = $wnd_0.__gwt_activeModules || {};
  activeModules['Uml2Java'] = {moduleName:'Uml2Java'};
  var frameDoc;
  function getInstallLocationDoc(){
    setupInstallLocation();
    return frameDoc;
  }

  function getInstallLocation(){
    setupInstallLocation();
    return frameDoc.getElementsByTagName('body')[0];
  }

  function setupInstallLocation(){
    if (frameDoc) {
      return;
    }
    var scriptFrame = $doc_0.createElement('iframe');
    scriptFrame.src = 'javascript:""';
    scriptFrame.id = 'Uml2Java';
    scriptFrame.style.cssText = 'position:absolute; width:0; height:0; border:none; left: -1000px;' + ' top: -1000px;';
    scriptFrame.tabIndex = -1;
    $doc_0.body.appendChild(scriptFrame);
    frameDoc = scriptFrame.contentDocument;
    if (!frameDoc) {
      frameDoc = scriptFrame.contentWindow.document;
    }
    frameDoc.open();
    var doctype = document.compatMode == 'CSS1Compat'?'<!doctype html>':'';
    frameDoc.write(doctype + '<html><head><\/head><body><\/body><\/html>');
    frameDoc.close();
  }

  function installScript(filename){
    function setupWaitForBodyLoad(callback){
      function isBodyLoaded(){
        if (typeof $doc_0.readyState == 'undefined') {
          return typeof $doc_0.body != 'undefined' && $doc_0.body != null;
        }
        return /loaded|complete/.test($doc_0.readyState);
      }

      var bodyDone = isBodyLoaded();
      if (bodyDone) {
        callback();
        return;
      }
      function onBodyDone(){
        if (!bodyDone) {
          bodyDone = true;
          callback();
          if ($doc_0.removeEventListener) {
            $doc_0.removeEventListener('DOMContentLoaded', onBodyDone, false);
          }
          if (onBodyDoneTimerId) {
            clearInterval(onBodyDoneTimerId);
          }
        }
      }

      if ($doc_0.addEventListener) {
        $doc_0.addEventListener('DOMContentLoaded', onBodyDone, false);
      }
      var onBodyDoneTimerId = setInterval(function(){
        if (isBodyLoaded()) {
          onBodyDone();
        }
      }
      , 50);
    }

    function installCode(code){
      var docbody = getInstallLocation();
      var script = getInstallLocationDoc().createElement('script');
      script.language = 'javascript';
      script.src = code;
      sendStats('moduleStartup', 'moduleRequested');
      docbody.appendChild(script);
    }

    setupWaitForBodyLoad(function(){
      installCode(filename);
    }
    );
  }

  Uml2Java.__startLoadingFragment = function(fragmentFile){
    return computeUrlForResource(fragmentFile);
  }
  ;
  Uml2Java.__installRunAsyncCode = function(code){
    var docbody = getInstallLocation();
    var script = getInstallLocationDoc().createElement('script');
    script.language = 'javascript';
    script.text = code;
    docbody.appendChild(script);
  }
  ;
  function processMetas(){
    var metaProps = {};
    var propertyErrorFunc;
    var onLoadErrorFunc;
    var metas = $doc_0.getElementsByTagName('meta');
    for (var i = 0, n = metas.length; i < n; ++i) {
      var meta = metas[i], name_1 = meta.getAttribute('name'), content_0;
      if (name_1) {
        name_1 = name_1.replace('Uml2Java::', '');
        if (name_1.indexOf('::') >= 0) {
          continue;
        }
        if (name_1 == 'gwt:property') {
          content_0 = meta.getAttribute('content');
          if (content_0) {
            var value_0, eq = content_0.indexOf('=');
            if (eq >= 0) {
              name_1 = content_0.substring(0, eq);
              value_0 = content_0.substring(eq + 1);
            }
             else {
              name_1 = content_0;
              value_0 = '';
            }
            metaProps[name_1] = value_0;
          }
        }
         else if (name_1 == 'gwt:onPropertyErrorFn') {
          content_0 = meta.getAttribute('content');
          if (content_0) {
            try {
              propertyErrorFunc = eval(content_0);
            }
             catch (e) {
              alert('Bad handler "' + content_0 + '" for "gwt:onPropertyErrorFn"');
            }
          }
        }
         else if (name_1 == 'gwt:onLoadErrorFn') {
          content_0 = meta.getAttribute('content');
          if (content_0) {
            try {
              onLoadErrorFunc = eval(content_0);
            }
             catch (e) {
              alert('Bad handler "' + content_0 + '" for "gwt:onLoadErrorFn"');
            }
          }
        }
      }
    }
    __gwt_getMetaProperty = function(name_0){
      var value = metaProps[name_0];
      return value == null?null:value;
    }
    ;
    __propertyErrorFunction = propertyErrorFunc;
    Uml2Java.__errFn = onLoadErrorFunc;
  }

  function computeScriptBase(){
    $wnd_0.__gwt_activeModules['Uml2Java'].superdevmode = true;
    var expectedSuffix = '/Uml2Java.nocache.js';
    var scriptTags = $doc_0.getElementsByTagName('script');
    for (var i = 0;; i++) {
      var tag = scriptTags[i];
      if (!tag) {
        break;
      }
      var candidate = tag.src;
      var lastMatch = candidate.lastIndexOf(expectedSuffix);
      if (lastMatch == candidate.length - expectedSuffix.length) {
        return candidate.substring(0, lastMatch + 1);
      }
    }
    $wnd_0.alert('Unable to load Super Dev Mode version of ' + Uml2Java + '.');
  }

  function computeUrlForResource(resource){
    if (resource.match(/^\//)) {
      return resource;
    }
    if (resource.match(/^[a-zA-Z]+:\/\//)) {
      return resource;
    }
    return Uml2Java.__moduleBase + resource;
  }

  function getCompiledCodeFilename(){
    var answers = [];
    var softPermutationId;
    function unflattenKeylistIntoAnswers(propValArray, value){
      var answer = answers;
      for (var i = 0, n = propValArray.length - 1; i < n; ++i) {
        answer = answer[propValArray[i]] || (answer[propValArray[i]] = []);
      }
      answer[propValArray[n]] = value;
    }

    var values = [];
    var providers = [];
    function computePropValue(propName){
      var value = providers[propName](), allowedValuesMap = values[propName];
      if (value in allowedValuesMap) {
        return value;
      }
      var allowedValuesList = [];
      for (var k in allowedValuesMap) {
        allowedValuesList[allowedValuesMap[k]] = k;
      }
      if (__propertyErrorFunc) {
        __propertyErrorFunc(propName, allowedValuesList, value);
      }
      throw null;
    }

    providers['gxt.user.agent'] = function(){
      var ua = navigator.userAgent.toLowerCase();
      if (ua.indexOf('chrome') != -1)
        return 'chrome';
      if (ua.indexOf('opera') != -1)
        return 'opera';
      if (ua.indexOf('msie') != -1) {
        if ($doc_0.documentMode >= 10)
          return 'ie10';
        if ($doc_0.documentMode >= 9)
          return 'ie9';
        if ($doc_0.documentMode >= 8)
          return 'ie8';
        if (ua.indexOf('msie 7') != -1)
          return 'ie7';
        if (ua.indexOf('msie 6') != -1)
          return 'ie6';
        return 'ie10';
      }
      if (ua.indexOf('safari') != -1) {
        if (ua.indexOf('version/3') != -1)
          return 'safari3';
        if (ua.indexOf('version/4') != -1)
          return 'safari4';
        return 'safari5';
      }
      if (ua.indexOf('gecko') != -1) {
        if (ua.indexOf('rv:1.8') != -1)
          return 'gecko1_8';
        return 'gecko1_9';
      }
      if (ua.indexOf('adobeair') != -1)
        return 'air';
      return null;
    }
    ;
    values['gxt.user.agent'] = {air:0, chrome:1, gecko1_8:2, gecko1_9:3, ie10:4, ie6:5, ie7:6, ie8:7, ie9:8, opera:9, safari3:10, safari4:11, safari5:12};
    providers['user.agent'] = function(){
      var ua = navigator.userAgent.toLowerCase();
      var makeVersion = function(result){
        return parseInt(result[1]) * 1000 + parseInt(result[2]);
      }
      ;
      if (function(){
        return ua.indexOf('opera') != -1;
      }
      ())
        return 'opera';
      if (function(){
        return ua.indexOf('webkit') != -1;
      }
      ())
        return 'safari';
      if (function(){
        return ua.indexOf('msie') != -1 && $doc_0.documentMode >= 9;
      }
      ())
        return 'ie9';
      if (function(){
        return ua.indexOf('msie') != -1 && $doc_0.documentMode >= 8;
      }
      ())
        return 'ie8';
      if (function(){
        var result = /msie ([0-9]+)\.([0-9]+)/.exec(ua);
        if (result && result.length == 3)
          return makeVersion(result) >= 6000;
      }
      ())
        return 'ie6';
      if (function(){
        return ua.indexOf('gecko') != -1;
      }
      ())
        return 'gecko1_8';
      return 'unknown';
    }
    ;
    values['user.agent'] = {gecko1_8:0, ie6:1, ie8:2, ie9:3, opera:4, safari:5};
    providers['user.agent.os'] = function(){
      var ua = $wnd_0.navigator.userAgent.toLowerCase();
      if (ua.indexOf('macintosh') != -1 || ua.indexOf('mac os x') != -1) {
        return 'mac';
      }
      if (ua.indexOf('linux') != -1) {
        return 'linux';
      }
      if (ua.indexOf('windows') != -1 || ua.indexOf('win32') != -1) {
        return 'windows';
      }
      return 'unknown';
    }
    ;
    values['user.agent.os'] = {linux:0, mac:1, unknown:2, windows:3};
    __gwt_isKnownPropertyValue = function(propName, propValue){
      return propValue in values[propName];
    }
    ;
    Uml2Java.__getPropMap = function(){
      var result = {};
      for (var key in values) {
        if (values.hasOwnProperty(key)) {
          result[key] = computePropValue(key);
        }
      }
      return result;
    }
    ;
    Uml2Java.__computePropValue = computePropValue;
    $wnd_0.__gwt_activeModules['Uml2Java'].bindings = Uml2Java.__getPropMap;
    sendStats('bootstrap', 'selectingPermutation');
    if (isHostedMode()) {
      return computeUrlForResource('Uml2Java.devmode.js');
    }
    var strongName;
    try {
      unflattenKeylistIntoAnswers(['ie10', 'ie9', 'linux'], '35E58C2D690B8A8CA5BF12310F8E94F9');
      unflattenKeylistIntoAnswers(['ie10', 'ie9', 'mac'], '35E58C2D690B8A8CA5BF12310F8E94F9' + ':1');
      unflattenKeylistIntoAnswers(['ie10', 'ie9', 'unknown'], '35E58C2D690B8A8CA5BF12310F8E94F9' + ':2');
      unflattenKeylistIntoAnswers(['ie10', 'ie9', 'windows'], '35E58C2D690B8A8CA5BF12310F8E94F9' + ':3');
      unflattenKeylistIntoAnswers(['ie9', 'ie9', 'linux'], '35E58C2D690B8A8CA5BF12310F8E94F9' + ':4');
      unflattenKeylistIntoAnswers(['ie9', 'ie9', 'mac'], '35E58C2D690B8A8CA5BF12310F8E94F9' + ':5');
      unflattenKeylistIntoAnswers(['ie9', 'ie9', 'unknown'], '35E58C2D690B8A8CA5BF12310F8E94F9' + ':6');
      unflattenKeylistIntoAnswers(['ie9', 'ie9', 'windows'], '35E58C2D690B8A8CA5BF12310F8E94F9' + ':7');
      unflattenKeylistIntoAnswers(['air', 'safari', 'linux'], '3C4C0987827471EF65C99B671D49AB09');
      unflattenKeylistIntoAnswers(['air', 'safari', 'mac'], '3C4C0987827471EF65C99B671D49AB09' + ':1');
      unflattenKeylistIntoAnswers(['safari3', 'safari', 'unknown'], '3C4C0987827471EF65C99B671D49AB09' + ':10');
      unflattenKeylistIntoAnswers(['safari3', 'safari', 'windows'], '3C4C0987827471EF65C99B671D49AB09' + ':11');
      unflattenKeylistIntoAnswers(['safari4', 'safari', 'linux'], '3C4C0987827471EF65C99B671D49AB09' + ':12');
      unflattenKeylistIntoAnswers(['safari4', 'safari', 'mac'], '3C4C0987827471EF65C99B671D49AB09' + ':13');
      unflattenKeylistIntoAnswers(['safari4', 'safari', 'unknown'], '3C4C0987827471EF65C99B671D49AB09' + ':14');
      unflattenKeylistIntoAnswers(['safari4', 'safari', 'windows'], '3C4C0987827471EF65C99B671D49AB09' + ':15');
      unflattenKeylistIntoAnswers(['safari5', 'safari', 'linux'], '3C4C0987827471EF65C99B671D49AB09' + ':16');
      unflattenKeylistIntoAnswers(['safari5', 'safari', 'mac'], '3C4C0987827471EF65C99B671D49AB09' + ':17');
      unflattenKeylistIntoAnswers(['safari5', 'safari', 'unknown'], '3C4C0987827471EF65C99B671D49AB09' + ':18');
      unflattenKeylistIntoAnswers(['safari5', 'safari', 'windows'], '3C4C0987827471EF65C99B671D49AB09' + ':19');
      unflattenKeylistIntoAnswers(['air', 'safari', 'unknown'], '3C4C0987827471EF65C99B671D49AB09' + ':2');
      unflattenKeylistIntoAnswers(['air', 'safari', 'windows'], '3C4C0987827471EF65C99B671D49AB09' + ':3');
      unflattenKeylistIntoAnswers(['chrome', 'safari', 'linux'], '3C4C0987827471EF65C99B671D49AB09' + ':4');
      unflattenKeylistIntoAnswers(['chrome', 'safari', 'mac'], '3C4C0987827471EF65C99B671D49AB09' + ':5');
      unflattenKeylistIntoAnswers(['chrome', 'safari', 'unknown'], '3C4C0987827471EF65C99B671D49AB09' + ':6');
      unflattenKeylistIntoAnswers(['chrome', 'safari', 'windows'], '3C4C0987827471EF65C99B671D49AB09' + ':7');
      unflattenKeylistIntoAnswers(['safari3', 'safari', 'linux'], '3C4C0987827471EF65C99B671D49AB09' + ':8');
      unflattenKeylistIntoAnswers(['safari3', 'safari', 'mac'], '3C4C0987827471EF65C99B671D49AB09' + ':9');
      unflattenKeylistIntoAnswers(['ie6', 'ie6', 'linux'], '63754E224E20493ECB4877733BDA1261');
      unflattenKeylistIntoAnswers(['ie6', 'ie6', 'mac'], '63754E224E20493ECB4877733BDA1261' + ':1');
      unflattenKeylistIntoAnswers(['ie6', 'ie6', 'unknown'], '63754E224E20493ECB4877733BDA1261' + ':2');
      unflattenKeylistIntoAnswers(['ie6', 'ie6', 'windows'], '63754E224E20493ECB4877733BDA1261' + ':3');
      unflattenKeylistIntoAnswers(['ie7', 'ie6', 'linux'], '63754E224E20493ECB4877733BDA1261' + ':4');
      unflattenKeylistIntoAnswers(['ie7', 'ie6', 'mac'], '63754E224E20493ECB4877733BDA1261' + ':5');
      unflattenKeylistIntoAnswers(['ie7', 'ie6', 'unknown'], '63754E224E20493ECB4877733BDA1261' + ':6');
      unflattenKeylistIntoAnswers(['ie7', 'ie6', 'windows'], '63754E224E20493ECB4877733BDA1261' + ':7');
      unflattenKeylistIntoAnswers(['ie8', 'ie8', 'linux'], '7420BE9B56FF015DE8A7EB2355432708');
      unflattenKeylistIntoAnswers(['ie8', 'ie8', 'mac'], '7420BE9B56FF015DE8A7EB2355432708' + ':1');
      unflattenKeylistIntoAnswers(['ie8', 'ie8', 'unknown'], '7420BE9B56FF015DE8A7EB2355432708' + ':2');
      unflattenKeylistIntoAnswers(['ie8', 'ie8', 'windows'], '7420BE9B56FF015DE8A7EB2355432708' + ':3');
      unflattenKeylistIntoAnswers(['gecko1_8', 'gecko1_8', 'linux'], 'A958E8BEDEBEF84217DBF6FC422086E8');
      unflattenKeylistIntoAnswers(['gecko1_8', 'gecko1_8', 'mac'], 'A958E8BEDEBEF84217DBF6FC422086E8' + ':1');
      unflattenKeylistIntoAnswers(['gecko1_8', 'gecko1_8', 'unknown'], 'A958E8BEDEBEF84217DBF6FC422086E8' + ':2');
      unflattenKeylistIntoAnswers(['gecko1_8', 'gecko1_8', 'windows'], 'A958E8BEDEBEF84217DBF6FC422086E8' + ':3');
      unflattenKeylistIntoAnswers(['gecko1_9', 'gecko1_8', 'linux'], 'A958E8BEDEBEF84217DBF6FC422086E8' + ':4');
      unflattenKeylistIntoAnswers(['gecko1_9', 'gecko1_8', 'mac'], 'A958E8BEDEBEF84217DBF6FC422086E8' + ':5');
      unflattenKeylistIntoAnswers(['gecko1_9', 'gecko1_8', 'unknown'], 'A958E8BEDEBEF84217DBF6FC422086E8' + ':6');
      unflattenKeylistIntoAnswers(['gecko1_9', 'gecko1_8', 'windows'], 'A958E8BEDEBEF84217DBF6FC422086E8' + ':7');
      unflattenKeylistIntoAnswers(['opera', 'opera', 'linux'], 'EFF20F40000405BB9C33297030CFDDAC');
      unflattenKeylistIntoAnswers(['opera', 'opera', 'mac'], 'EFF20F40000405BB9C33297030CFDDAC' + ':1');
      unflattenKeylistIntoAnswers(['opera', 'opera', 'unknown'], 'EFF20F40000405BB9C33297030CFDDAC' + ':2');
      unflattenKeylistIntoAnswers(['opera', 'opera', 'windows'], 'EFF20F40000405BB9C33297030CFDDAC' + ':3');
      strongName = answers[computePropValue('gxt.user.agent')][computePropValue('user.agent')][computePropValue('user.agent.os')];
      var idx = strongName.indexOf(':');
      if (idx != -1) {
        softPermutationId = parseInt(strongName.substring(idx + 1), 10);
        strongName = strongName.substring(0, idx);
      }
    }
     catch (e) {
    }
    Uml2Java.__softPermutationId = softPermutationId;
    return computeUrlForResource(strongName + '.cache.js');
  }

  function loadExternalStylesheets(){
    if (!$wnd_0.__gwt_stylesLoaded) {
      $wnd_0.__gwt_stylesLoaded = {};
    }
    function installOneStylesheet(stylesheetUrl){
      if (!__gwt_stylesLoaded[stylesheetUrl]) {
        var l = $doc_0.createElement('link');
        l.setAttribute('rel', 'stylesheet');
        l.setAttribute('href', computeUrlForResource(stylesheetUrl));
        $doc_0.getElementsByTagName('head')[0].appendChild(l);
        __gwt_stylesLoaded[stylesheetUrl] = true;
      }
    }

    sendStats('loadExternalRefs', 'begin');
    installOneStylesheet('css/chart.css');
    installOneStylesheet('reset.css');
    sendStats('loadExternalRefs', 'end');
  }

  processMetas();
  Uml2Java.__moduleBase = computeScriptBase();
  activeModules['Uml2Java'].moduleBase = Uml2Java.__moduleBase;
  var filename_0 = getCompiledCodeFilename();
  loadExternalStylesheets();
  sendStats('bootstrap', 'end');
  installScript(filename_0);
  return true;
}

Uml2Java.succeeded = Uml2Java();