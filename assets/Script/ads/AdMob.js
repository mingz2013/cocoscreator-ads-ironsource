'use strict';


var AdMob = {};


AdMob.enum = {
    ADTYPE_BANNER: "banner",
    ADTYPE_INTERSTITIAL: "interstitial",
    ADTYPE_NATIVE: "native",
    ADTYPE_REWARDVIDEO: "rewardedvideo",

    EVENT_ON_AD_CLOSED: "onAdClosed",
    EVENT_ON_AD_FAILED_TO_LOAD: "onAdFailedToLoad",
    EVENT_ON_AD_LEFT_APPLICATION: "onAdLeftApplication",
    EVENT_ON_AD_OPENED: "onAdOpened",
    EVENT_ON_AD_LOADED: "onAdLoaded",
    EVENT_ON_AD_CLICKED: "onAdClicked",
    EVENT_ON_AD_IMPRESSION: "onAdImpression",
    EVENT_ON_REWARDED_VIDEO_STARTED: "onRewardedVideoStarted",
    EVENT_ON_REWARDED_VIDEO_COMPLETED: "onRewardedVideoCompleted",
    EVENT_ON_REWARDED: "onRewarded",


    ADSIZE_BANNER: "BANNER",//new AdSize(320, 50, "320x50_mb");
    ADSIZE_FULL_BANNER: "FULL_BANNER",//new AdSize(468, 60, "468x60_as");
    ADSIZE_LARGE_BANNER: "LARGE_BANNER",//new AdSize(320, 100, "320x100_as");
    ADSIZE_LEADERBOARD: "LEADERBOARD",//new AdSize(728, 90, "728x90_as");
    ADSIZE_MEDIUM_RECTANGLE: "MEDIUM_RECTANGLE",//new AdSize(300, 250, "300x250_as");
    ADSIZE_WIDE_SKYSCRAPER: "WIDE_SKYSCRAPER",//new AdSize(160, 600, "160x600_as");
    ADSIZE_SMART_BANNER: "SMART_BANNER",//new AdSize(-1, -2, "smart_banner");


    RUN_TYPE_PROD: "RUN_TYPE_PROD",
    RUN_TYPE_TEST_WITH_TEST_ID: "RUN_TYPE_TEST_WITH_TEST_ID",
    RUN_TYPE_TEST_WITH_DEVICE: "RUN_TYPE_TEST_WITH_DEVICE",


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


};


var initialized = false;

/**
 * Returns the state of initialization
 */
AdMob.isInitialized = function isInitialized() {
    return initialized;
};

/**
 * init
 * @param {Boolean} params.overlap
 * @param {Boolean} params.runType
 * @param {String} params.testDevice
 * @param {String} params.admobAppId
 * @param {String} params.admobUnitIdBanner
 * @param {String} params.admobUnitIdInterstitial
 * @param {String} params.admobUnitIdInterstitialVideo
 * @param {String} params.admobUnitIdRewardedVideo
 * @param {String} params.admobUnitIdNativeAdvanced
 * @param {String} params.admobUnitIdNativeAdvancedVideo
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
AdMob.init = function (params) {
    console.log("ADmob.init...params:" + JSON.stringify(params));
    params = defaults(params, {
        overlap: true,

        runType: this.enum.RUN_TYPE_PROD,
        testDevice: "",
        admobAppId: "",
        admobUnitIdBanner: "",
        admobUnitIdInterstitial: "",
        admobUnitIdInterstitialVideo: "",
        admobUnitIdRewardedVideo: "",
        admobUnitIdNativeAdvanced: "",
        admobUnitIdNativeAdvancedVideo: ""
    });

    // if (params.hasOwnProperty('admobAppId') === false) {
    //     throw new Error('AdMob.init - admobAppId is required');
    // }

    callPlugin('init', {
        overlap: params.overlap,

        runType: params.runType,
        testDevice: params.testDevice,
        admobAppId: params.admobAppId,
        admobUnitIdBanner: params.admobUnitIdBanner,
        admobUnitIdInterstitial: params.admobUnitIdInterstitial,
        admobUnitIdInterstitialVideo: params.admobUnitIdInterstitialVideo,
        admobUnitIdRewardedVideo: params.admobUnitIdRewardedVideo,
        admobUnitIdNativeAdvanced: params.admobUnitIdNativeAdvanced,
        admobUnitIdNativeAdvancedVideo: params.admobUnitIdNativeAdvancedVideo

    }, function () {

        initialized = true;

        if (isFunction(params.onSuccess)) {
            params.onSuccess();
        }

    }, params.onFailure);

};

/**
 * createInterstitial
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
AdMob.createInterstitial = function (params) {
    // "ca-app-pub-3940256099942544/1033173712"
    // params = defaults(params, {unitId: ""});

    callPlugin('createInterstitial', {}, function () {


        if (isFunction(params.onSuccess)) {
            params.onSuccess();
        }

    }, params.onFailure);
};

/**
 * createRewardedVideo
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
AdMob.createRewardedVideo = function (params) {
    callPlugin('createRewardedVideo', {}, function () {


        if (isFunction(params.onSuccess)) {
            params.onSuccess();
        }

    }, params.onFailure);
};

/**
 * loadRewardedVideo
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
AdMob.loadRewardedVideo = function (params) {
    callPlugin('loadRewardedVideo', {}, function () {


        if (isFunction(params.onSuccess)) {
            params.onSuccess();
        }

    }, params.onFailure);
};


/**
 * showRewardedVideo
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
AdMob.showRewardedVideo = function (params) {
    callPlugin('showRewardedVideo', {}, function () {


        if (isFunction(params.onSuccess)) {
            params.onSuccess();
        }

    }, params.onFailure);
};


/**
 * loadInterstitial
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
AdMob.loadInterstitial = function (params) {

    callPlugin('loadInterstitial', {}, function () {


        if (isFunction(params.onSuccess)) {
            params.onSuccess();
        }

    }, params.onFailure);
};

/**
 * isInterstitialLoading
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
AdMob.isInterstitialLoading = function (params) {

    callPlugin('isInterstitialLoading', {}, function (isLoading) {


        if (isFunction(params.onSuccess)) {
            params.onSuccess(isLoading);
        }

    }, params.onFailure);


};

/**
 * isInterstitialLoaded
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
AdMob.isInterstitialLoaded = function (params) {

    callPlugin('isInterstitialLoaded', {}, function (isLoaded) {


        if (isFunction(params.onSuccess)) {
            params.onSuccess(isLoaded);
        }

    }, params.onFailure);

};


/**
 * showInterstitial
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
AdMob.showInterstitial = function (params) {

    callPlugin('showInterstitial', {}, function () {


        if (isFunction(params.onSuccess)) {
            params.onSuccess();
        }

    }, params.onFailure);
};

/**
 * createBanner
 * @param {String} params.size
 * @param {int} params.w
 * @param {int} params.h
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
AdMob.createBanner = function (params) {

    params = defaults(params, {size: this.enum.ADSIZE_SMART_BANNER, w: 0, h: 0});

    // if (params.hasOwnProperty('unitId') === false) {
    //     throw new Error('AdMob.createBanner - unitId is required');
    // }

    callPlugin('createBanner', {size: params.size, w: params.w, h: params.h}, function () {


        if (isFunction(params.onSuccess)) {
            params.onSuccess();
        }

    }, params.onFailure);
};

/**
 * loadBanner
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
AdMob.loadBanner = function (params) {

    callPlugin('loadBanner', {}, function () {


        if (isFunction(params.onSuccess)) {
            params.onSuccess();
        }

    }, params.onFailure);
};

/**
 * showBanner
 * @param {int} params.pos
 * @param {int} params.x
 * @param {int} params.y
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
AdMob.showBanner = function (params) {

    params = defaults(params, {pos: this.enum.AD_POSITION.BOTTOM_CENTER, x: 0, y: 0});

    callPlugin('showBanner', {
        pos: params.pos,
        x: params.x,
        y: params.y
    }, function () {


        if (isFunction(params.onSuccess)) {
            params.onSuccess();
        }

    }, params.onFailure);
};


/**
 * hideBanner
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
AdMob.hideBanner = function (params) {

    callPlugin('hideBanner', {}, function () {


        if (isFunction(params.onSuccess)) {
            params.onSuccess();
        }

    }, params.onFailure);
};


/**
 * Helper function to call  plugin
 * @param {String} name - function name to call
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


if (typeof module !== undefined && module.exports) {
    module.exports = AdMob;
}


