/**
 *
 *
 * 广告策略，目前，只针对cordova里的插件，也就是原生app。
 *
 * 目前只有 admob，fbads
 *
 *
 * */




var config = require('../sdk_config');
let logManager = require('../sdk_log');

var AdMobObj = require('./cordova_plugin_admob');
var FBAdsObj = require('./cordova_plugin_fbads');

var AdPolicy = function () {
    logManager.LOGD("AdPolicy.init.......");

    // banner 广告位置
    this.AD_POSITION_KEY_TOP_CENTER = 'TOP_CENTER';
    this.AD_POSITION_KEY_CENTER = 'CENTER';
    this.AD_POSITION_KEY_BOTTOM_CENTER = 'BOTTOM_CENTER';


    this._admob = new AdMobObj(config.AdsConfig); // 保存一个admob对象
    this._fbads = new FBAdsObj(config.FBAdsConfig);
};


// Ads
AdPolicy.prototype.showInterstitial = function (successCallback, failureCallback) {
    logManager.LOGD("AdPolicy.showInterstitial....");
    return this._admob.showInterstitial(successCallback, function () {
        this._fbads.showInterstitial(successCallback, failureCallback);
    }.bind(this));
};

AdPolicy.prototype.showBanner = function (position) {
    return this._admob.showBanner(position);
};

AdPolicy.prototype.showBannerAtXY = function (x, y) {
    return this._admob.showBannerAtXY(x, y)
};

AdPolicy.prototype.hideBanner = function () {
    return this._admob.hideBanner();
};

AdPolicy.prototype.showRewardVideoAd = function (successCallback, failureCallback) {
    logManager.LOGD("AdPolicy.showRewardVideoAd....");
    return this._admob.showRewardVideoAd(successCallback, function () {
        this._fbads.showRewardVideoAd(successCallback, failureCallback)
    }.bind(this));
};


AdPolicy.prototype.showAdsWithPolicy = function (successCallback, failureCallback) {
    logManager.LOGD("AdPolicy.showAdsWithPolicy....");
    this.showRewardVideoAd(successCallback, function () {
        this.showInterstitial(successCallback, failureCallback);
    }.bind(this));
};


module.exports = AdPolicy;