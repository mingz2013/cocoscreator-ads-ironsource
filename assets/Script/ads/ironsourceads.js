var JsbNativeCall = require('./JsbNativeCall');

var IronSourceAds = (function () {

    var initialized = false;

    return {

        AD_POSITION: {
            NO_CHANGE: 0,
            TOP_LEFT: 1,
            TOP_CENTER: 2,
            TOP_RIGHT: 3,
            LEFT: 4,
            CENTER: 5,
            RIGHT: 6,
            BOTTOM_LEFT: 7,
            BOTTOM_CENTER: 8,
            BOTTOM_RIGHT: 9,
            POS_XY: 10
        },

        /**
         * Returns the state of initialization
         */
        isInitialized: function isInitialized() {
            return initialized;
        },


        /**
         * Sets the user id, used for Offerwall ad units or using
         * server-to-server callbacks to reward your users with our rewarded ad units
         * @param {String} params.userId user id
         * @param {Function} params.onSuccess - optional on success callback
         * @param {Function} params.onFailure - optional on failure callback
         */
        setDynamicUserId: function setUserId(params) {

            params = defaults(params, {});

            if (params.hasOwnProperty('userId') === false) {
                throw new Error("IronSourceAds::setUserId - missing userId IronSourceAds.setUserId({userId:'example'})");
            }

            callPlugin('setDynamicUserId', {userId: params.userId}, params.onSuccess, params.onFailure);
        },

        /**
         * Sets the user consent, used for displaying personalized ads and GDPR terms
         * @param {Boolean} params.consent - consent
         * @param {Function} params.onSuccess - optional on success callback
         * @param {Function} params.onFailure - optional on failure callback
         */
        setConsent: function setConsent(params) {

            params = defaults(params, {});

            if (params.hasOwnProperty('consent') === false) {
                throw new Error("IronSourceAds::setConsent - missing consent IronSourceAds.setConsent({consent:true})");
            }

            callPlugin('setConsent', {consent: params.consent}, params.onSuccess, params.onFailure);
        },

        /**
         * Validate Integration
         * @param {Function} params.onSuccess - optional on success callback
         * @param {Function} params.onFailure - optional on failure callback
         */
        validateIntegration: function validateIntegration(params) {

            params = defaults(params, {});

            callPlugin('validateIntegration', {}, params.onSuccess, params.onFailure);

        },


        /**
         * Initializes iron source
         * @param {String} params.appKey
         * @param {String} params.userId
         * @param {Boolean} params.debug
         * @param {int} params.position
         * @param {int} params.x
         * @param {int} params.y
         * @param {int} params.w
         * @param {int} params.h
         * @param {Function} params.onSuccess - optional on success callback
         * @param {Function} params.onFailure - optional on failure callback
         */
        init: function init(params) {

            params = defaults(params, {userId: '', debug: false, position: 8, x: 0, y: 0, w: 0, h: 0});

            if (params.hasOwnProperty('appKey') === false) {
                throw new Error('IronSourceAds::init - appKey is required');
            }

            callPlugin('init', {
                appKey: params.appKey,
                providedUserId: params.userId,
                debug: params.debug,
                position: params.position,
                x: params.x,
                y: params.y,
                w: params.w,
                h: params.h
            }, function () {

                initialized = true;

                if (isFunction(params.onSuccess)) {
                    params.onSuccess();
                }

            }, params.onFailure);

        },


        /**
         * Shows rewarded video
         * @param {String} params.placement - optional placement name
         * @param {Function} params.onSuccess - optional on success callback
         * @param {Function} params.onFailure - optional on failure callback
         */
        showRewardedVideo: function showRewardedVideo(params) {

            params = defaults(params, {placement: 'DefaultRewardedVideo'});

            callPlugin('showRewardedVideo', {placement: params.placement}, params.onSuccess, params.onFailure);

        },


        /**
         * Checks if rewarded video is available
         * @param {Function} params.onSuccess - function to call the result to
         * @param {Function} params.onFailure - optional on failure callback
         */
        hasRewardedVideo: function hasRewardedVideo(params) {

            params = defaults(params, {});

            if (isFunction(params.onSuccess) === false) {
                throw new Error('IronSourceAdsPlugin::hasRewardedVideo expects onSuccess');
            }

            callPlugin('hasRewardedVideo', {}, params.onSuccess, params.onFailure);

        },


        /**
         * load banner if avaialble
         * @param {string} params.placement
         * @param {string} params.size
         * @param {string} params.position
         * @param {Function} params.onSuccess
         * @param {Function} params.onFailure
         */
        loadBanner: function loadBanner(params) {
            console.log("loadBanner....", params);
            params = defaults(params, {placement: 'DefaultBanner', position: 'bottom', size: 'standard'});

            callPlugin('loadBanner', {
                placement: params.placement,
                size: params.size,
                position: params.position
            }, params.onSuccess, params.onFailure);

        },


        /**
         * Shows banner if avaialble
         // * @param {string} params.placement
         // * @param {string} params.position
         * @param {int} params.position   AD_POSITION
         * @param {int} params.x
         * @param {int} params.y
         //         * @param {int} params.w
         //         * @param {int} params.h
         * @param {Function} params.onSuccess
         * @param {Function} params.onFailure
         */
        showBanner: function showBanner(params) {
            console.log("showBanner....", params);
            // params = defaults(params, { placement: 'DefaultBanner', position: 'bottom', size: 'standard' });

            params = defaults(params, {position: 0, x: 0, y: 0});

            callPlugin('showBanner', {
                position: params.position,
                x: params.x,
                y: params.y
            }, params.onSuccess, params.onFailure);

        },

        /**
         * Hide banner
         * @param {Function} [params.onSuccess]
         * @param {Function} [params.onFailure]
         */
        hideBanner: function hideBanner(params) {
            console.log("hideBanner....", params);
            params = defaults(params, {});

            callPlugin('hideBanner', {}, params.onSuccess, params.onFailure);

        },


        /**
         * Checks if offerwall is available
         * @param {Function} params.onSuccess - function to call the result to
         * @param {Function} params.onFailure
         */
        hasOfferwall: function hasOfferwall(params) {

            params = defaults(params, {});

            callPlugin('hasOfferwall', {}, params.onSuccess, params.onFailure);
        },


        /**
         * Shows the offerwall if available
         * @param {string} params.placement
         * @param {Function} params.onSuccess
         * @param {Function} params.onFailure
         */
        showOfferwall: function showOfferwall(params) {

            params = defaults(params, {placement: 'DefaultOfferWall'});

            callPlugin('showOfferwall', {placement: params.placement}, params.onSuccess, params.onFailure);

        },


        /**
         * Loads interstitial
         */
        loadInterstitial: function loadInterstitial(params) {

            params = defaults(params, {});

            callPlugin('loadInterstitial', {}, params.onSuccess, params.onFailure);

        },


        /**
         * Show interstitial
         */
        showInterstitial: function showInterstitial(params) {

            params = defaults(params, {placement: 'DefaultInterstitial'});

            callPlugin('showInterstitial', {placement: params.placement}, params.onSuccess, params.onFailure);

        },


        /**
         * Checks to see if interstitial is loaded
         * @param {Function} params.onSuccess
         * @param {Function} params.onFailure
         */
        hasInterstitial: function isInterstitialReady(params) {

            params = defaults(params, {});

            callPlugin('hasInterstitial', {}, params.onSuccess, params.onFailure);

        },

        /**
         * Checks if rewarded video is capped for placement.
         * This should be used before showing the state of the button for rewarded video.
         * User may have capped their usage
         */
        isRewardedVideoCappedForPlacement: function isRewardedVideoCappedForPlacement(params) {

            params = defaults(params, {placement: 'DefaultRewardedVideo'});

            callPlugin('isRewardedVideoCappedForPlacement', {placement: params.placement}, params.onSuccess, params.onFailure);

        }

    }
})();


/**
 * Helper function to call plugin
 * @param {String} action - function name to call
 * @param {Object} params - optional params
 * @param {Function} onSuccess - optional on sucess function
 * @param {Function} onFailure - optional on failure functioin
 */
function callPlugin(action, params, onSuccess, onFailure) {
    JsbNativeCall.exec('IronsourcePlugin',
        action, params,
        function callPluginSuccess(result) {

            if (isFunction(onSuccess)) {
                onSuccess(result);
            }
        },
        function callPluginFailure(error) {
            if (isFunction(onFailure)) {
                onFailure(error)
            }
        });
}


/**
 * Helper function to check if a function is a function
 * @param {Object} functionToCheck - function to check if is function
 */
function isFunction(functionToCheck) {
    var getType = {};
    var isFunction = functionToCheck && getType.toString.call(functionToCheck) === '[object Function]';
    return isFunction === true;
}


/**
 * Helper function to do a shallow defaults (merge). Does not create a new object, simply extends it
 * @param {Object} o - object to extend
 * @param {Object} defaultObject - defaults to extend o with
 */
function defaults(o, defaultObject) {

    if (typeof o === 'undefined') {
        return defaults({}, defaultObject);
    }

    for (var j in defaultObject) {
        if (defaultObject.hasOwnProperty(j) && o.hasOwnProperty(j) === false) {
            o[j] = defaultObject[j];
        }
    }

    return o;
}


module.exports = IronSourceAds;


window.IronSourceAds = IronSourceAds;
