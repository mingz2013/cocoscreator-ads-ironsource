var config = require('./sdk_config');
// let logManager = require('../sdk_log');

var AdMobObj = require('./admob_plugin');
var FBAdsObj = require('./fbads_plugin');


var showlog = function (msg) {
    console.log(msg);
};

var showerror = function (msg) {
    console.log(msg);
};

var AdPolicy = function () {
    showlog("AdPolicy.init.......");

    // banner 广告位置
    this.AD_POSITION_KEY_TOP_CENTER = 'TOP_CENTER';
    this.AD_POSITION_KEY_CENTER = 'CENTER';
    this.AD_POSITION_KEY_BOTTOM_CENTER = 'BOTTOM_CENTER';


    this._admob = new AdMobObj(config.AdsConfig); // 保存一个admob对象
    this._fbads = new FBAdsObj(config.FBAdsConfig);
};


// Ads
AdPolicy.prototype.showInterstitial = function (successCallback, failureCallback) {
    showlog("AdPolicy.showInterstitial....");
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
    showlog("AdPolicy.showRewardVideoAd....");
    return this._admob.showRewardVideoAd(successCallback, function () {
        this._fbads.showRewardVideoAd(successCallback, failureCallback)
    }.bind(this));
};


AdPolicy.prototype.showAdsWithPolicy = function (successCallback, failureCallback) {
    showlog("AdPolicy.showAdsWithPolicy....");
    this.showRewardVideoAd(successCallback, function () {
        this.showInterstitial(successCallback, failureCallback);
    }.bind(this));
};


module.exports = AdPolicy;