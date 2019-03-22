document.addEventListener("deviceready", function () {
    var googleAnalyticsId;
    var raygunId;
    var llave;
    var salt;

    var initRaygunGA = function (rayId, gaId, key, saltKey) {
        googleAnalyticsId = gaId;
        raygunId = rayId;
        salt = CryptoJS.enc.Utf8.parse(saltKey);
        llave = CryptoJS.enc.Utf8.parse(key);
        var raygunIdDecrypt = CryptoJS.AES.decrypt(raygunId, llave, { iv: salt, padding: CryptoJS.pad.Pkcs7, mode: CryptoJS.mode.CBC }).toString(CryptoJS.enc.Utf8).trim();
        var gaIdDecrypt = CryptoJS.AES.decrypt(googleAnalyticsId, llave, { iv: salt, padding: CryptoJS.pad.Pkcs7, mode: CryptoJS.mode.CBC }).toString(CryptoJS.enc.Utf8).trim();
        //Inicializacion GA
        (function (i, s, o, g, r, a, m) {// jshint ignore:line
            i['GoogleAnalyticsObject'] = r; i[r] = i[r] || function () {// jshint ignore:line
                (i[r].q = i[r].q || []).push(arguments)// jshint ignore:line
            }, i[r].l = 1 * new Date(); a = s.createElement(o),// jshint ignore:line
            m = s.getElementsByTagName(o)[0]; a.async = 1; a.src = g; m.parentNode.insertBefore(a, m)// jshint ignore:line
        })(window, document, 'script', '../../../../lib/google-analytics/analytics.js', 'ga');// jshint ignore:line
        ga('create', gaIdDecrypt, {
            'storage': 'none',
            'clientId': device.uuid
        });
        ga('set', 'checkProtocolTask', null);
        ga('set', 'checkStorageTask', null);
        //Inicializacion Raygun
        Raygun.init(raygunIdDecrypt, {
            allowInsecureSubmissions: true,
            ignoreAjaxAbort: true,
            ignoreAjaxError: true,
            debugMode: true,
            ignore3rdPartyErrors: true,
            wrapAsynchronousCallbacks: true
            //excludedHostnames: ['localhost', '\.dev'],
            //excludedUserAgents: ['PhantomJS', 'MSIE']
        }).attach();
        window.raygunGAinit = 1;
    };

    var getSalt = function (rayId, gaId, key) {
        cordova.plugins.ConfigReader.get('salt', {
            success: function (saltKey) {
                return initRaygunGA(rayId, gaId, key, saltKey);
            }
        });
    };

    var getLlave = function (rayId, gaId) {
        cordova.plugins.ConfigReader.get('llave', {
            success: function (key) {
                return getSalt(rayId, gaId, key);
            }
        });
    };

    var getRaygun = function (gaId) {
        cordova.plugins.ConfigReader.get('raygunId', {
            success: function (rayId) {
                return getLlave(rayId, gaId);
            }
        });
    };

    var getGA = function () {
        cordova.plugins.ConfigReader.get('googleAnalyticsId', {
            success: function (gaId) {
                return getRaygun(gaId);
            }
        });
    };

    getGA();
}, true);