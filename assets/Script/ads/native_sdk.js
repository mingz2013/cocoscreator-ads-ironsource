/*
 * 原生 sdk
 * NativeSDK
 *
 * create by zengxx on 2019-01-24
 */

let config = require('../sdk_config');
let logManager = require('../sdk_log');
let loginManager = require('../sdk_login');
let tool = require('../sdk_tool');

// var FileUtil = require('./cordova_plugin_file');
// var Device = require('./cordova_plugin_device');
// var AdjustObj = require('./cordova_plugin_adjust');
// var AdPolicy = require('./sdk_ad_policy');
var AdPolicyNative = require('./ad_policy_native');

var NativeSDK = function () {
    logManager.LOGD("NativeSdk.new...");
    this._adPolicy = new AdPolicyNative();

};

// Ads
NativeSDK.prototype.showInterstitial = function (successCallback, failureCallback) {
    logManager.LOGD('NativeSDK.showInterstitial....');
    return this._adPolicy.showInterstitial(successCallback, failureCallback);
};

NativeSDK.prototype.showBanner = function (position) {
    return this._adPolicy.showBanner(position);
};

NativeSDK.prototype.showBannerAtXY = function (x, y) {
    return this._adPolicy.showBannerAtXY(x, y)
};

NativeSDK.prototype.hideBanner = function () {
    return this._adPolicy.hideBanner();
};

NativeSDK.prototype.showRewardVideoAd = function (successCallback, failureCallback) {
    return this._adPolicy.showRewardVideoAd(successCallback, failureCallback);
};

NativeSDK.prototype.showAdsWithPolicy = function (successCallback, failureCallback) {
    logManager.LOGD('NativeSDK.showAdsWithPolicy....');
    return this._adPolicy.showAdsWithPolicy(successCallback, failureCallback);
};


NativeSDK.prototype.loginByGuest = function (successCallback, failureCallback) {
    config.UserInfo.device_id = tool.getUuid();

    loginManager.loginByGuest(tool.getUuid()).then((ret) => {
        logManager.LOGD('NativeSDK.loginByGuest successful ' + JSON.stringify(ret));

        successCallback(ret);
    }).catch((err) => {
        logManager.LOGD('NativeSDK.loginByGuest fail ' + JSON.stringify(err));

        failureCallback(err);
    });
};


// banner 广告位置
NativeSDK.AD_POSITION_KEY_TOP_CENTER = 'TOP_CENTER';
NativeSDK.AD_POSITION_KEY_CENTER = 'CENTER';
NativeSDK.AD_POSITION_KEY_BOTTOM_CENTER = 'BOTTOM_CENTER';

module.exports = NativeSDK;

