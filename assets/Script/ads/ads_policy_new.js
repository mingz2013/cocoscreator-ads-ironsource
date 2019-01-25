var config = require('./sdk_config');


// var AdMobObj = require('./cordova_plugin_admob');
// var FBAdsObj = require('./cordova_plugin_fbads');
var IronSrcObj = require('./ironsource_ads_plugin');
var AdPolicyNew = function () {
    cc.log("AdPolicy.init.......");

    // banner 广告位置
    this.AD_POSITION_KEY_TOP_CENTER = 'TOP_CENTER';
    this.AD_POSITION_KEY_CENTER = 'CENTER';
    this.AD_POSITION_KEY_BOTTOM_CENTER = 'BOTTOM_CENTER';


    // this._admob = new AdMobObj(config.AdsConfig); // 保存一个admob对象
    // this._fbads = new FBAdsObj(config.FBAdsConfig);
    this._ironObj = new IronSrcObj(config.IronSrcConfig);
};


// Ads
AdPolicyNew.prototype.showInterstitial = function (successCallback, failureCallback) {
    cc.log("AdPolicy.showInterstitial....");
    // return this._admob.showInterstitial(successCallback, function () {
    //     this._fbads.showInterstitial(successCallback, failureCallback);
    // }.bind(this));

    this._ironObj.showInterstitial({
        "onSuccess": successCallback,
        "onFailure": failureCallback
    });

};

AdPolicyNew.prototype.showBanner = function (position) {
    // return this._admob.showBanner(position);
    this._ironObj.showBanner({
        "position": position
    });
};

// AdPolicy.prototype.showBannerAtXY = function (x, y) {
//     return this._admob.showBannerAtXY(x, y)
// };

AdPolicyNew.prototype.hideBanner = function () {
    // return this._admob.hideBanner();
    this._ironObj.hideBanner();
};

AdPolicyNew.prototype.showRewardVideoAd = function (successCallback, failureCallback) {
    cc.log("AdPolicy.showRewardVideoAd....");
    // return this._admob.showRewardVideoAd(successCallback, function () {
    //     this._fbads.showRewardVideoAd(successCallback, failureCallback)
    // }.bind(this));
    this._ironObj.showRewardedVideo({
        "onSuccess": successCallback,
        "onFailure": failureCallback
    });
};


AdPolicyNew.prototype.showAdsWithPolicy = function (successCallback, failureCallback) {
    cc.log("AdPolicy.showAdsWithPolicy....");
    this.showRewardVideoAd(successCallback, function () {
        this.showInterstitial(successCallback, failureCallback);
    }.bind(this));
};


module.exports = AdPolicyNew;