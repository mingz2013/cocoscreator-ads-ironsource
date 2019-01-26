/**
 *
 *
 *https://github.com/mingz2013/cordova-plugin-ironsource-ads
 *
 *
 * */



// var log = require('../sdk_log');
// var bi = require('../sdk_bi');
var config = require('./sdk_config');
var ironsourceAds = require('./ironsourceads');

window['IronSourceAds'] = ironsourceAds;

var showlog = function (message) {
    cc.log("<IronSourceAdsPlugin>------------", message);
};

var showerror = function (message) {
    cc.log("<IronSourceAdsPlugin>------------", message);
};

var IronSourceAdsPluginNew = function (config) {
    this.config = config;

    // this.rewardVideoAvailable = false;
    this._rewardVideoSuccessCallback = null;
    this._rewardVideoFailureCallback = null;
    this._rewardVideoRewarded = false;

    this._interstitialSuccessCallback = null;
    this._interstitialFailureCallback = null;
    this._interstitialRewarded = false;

    this._offerwallSuccessCallback = null;
    this._offerwallFailureCallback = null;
    this._offerwallRewarded = false;


    this._bannerLoaded = false;


    this.init();


};

IronSourceAdsPluginNew.prototype.init = function () {
    window['IronSourceAds']['init']({
        "appKey": cc.sys.os === cc.sys.OS_IOS ? this.config.IOS_KEY : this.config.ANDROID_KEY,
        "userId": this.config.userId,
        "debug": this.config.isDebug,
        "onSuccess": function () {

            showlog("init.....onSuccess....");


            /**
             * ********************* EVENTS ***********************************
             */


            //Rewarded Video
            window.addEventListener("rewardedVideoFailed", function () {
                showlog("rewardedVideoFailed");
                // this.rewardVideoAvailable = false;
                this._rewardVideoRewarded = false;
                this._rewardVideoSuccessCallback = null;
                try {
                    this._rewardVideoFailureCallback && this._rewardVideoFailureCallback();
                } catch (e) {
                    showerror(e);
                }

                this._rewardVideoFailureCallback = null;

            }.bind(this));

            window.addEventListener("rewardedVideoRewardReceived", function (event) {
                showlog("rewardedVideoRewardReceived", event);
                this._rewardVideoRewarded = true;

            }.bind(this));

            window.addEventListener("rewardedVideoEnded", function () {
                showlog("rewardedVideoEnded")
            }.bind(this));
            window.addEventListener("rewardedVideoStarted", function () {
                showlog("rewardedVideoStarted")
            }.bind(this));

            window.addEventListener("rewardedVideoAvailabilityChanged", function (event) {
                showlog("rewardedVideoAvailabilityChanged", event.available);
                // this.rewardVideoAvailable = event.available;

            }.bind(this));
            window.addEventListener("rewardedVideoClosed", function () {
                showlog("rewardedVideoClosed");
                if (this._rewardVideoRewarded) {
                    this._rewardVideoFailureCallback = null;
                    this._rewardVideoRewarded = false;
                    try {
                        this._rewardVideoSuccessCallback && this._rewardVideoSuccessCallback();
                    } catch (e) {
                        showerror(e);
                    }
                    this._rewardVideoSuccessCallback = null;
                } else {
                    this._rewardVideoSuccessCallback = null;
                    this._rewardVideoRewarded = false;
                    try {
                        this._rewardVideoFailureCallback && this._rewardVideoFailureCallback();
                    } catch (e) {
                        showerror(e);
                    }
                    this._rewardVideoFailureCallback = null;
                }

            }.bind(this));
            window.addEventListener("rewardedVideoOpened", function () {
                showlog("rewardedVideoOpened");

            });

            //Interstitial
            window.addEventListener("interstitialLoaded", function () {
                showlog("interstitialLoaded");
            }.bind(this));

            window.addEventListener("interstitialShown", function () {
                showlog("interstitialShown");
                this._interstitialRewarded = true;
            }.bind(this));
            window.addEventListener("interstitialShowFailed", function () {
                showlog("interstitialShowFailed");
                this._interstitialRewarded = false;
                this._interstitialSuccessCallback = null;
                try {
                    this._interstitialFailureCallback && this._interstitialFailureCallback();
                } catch (e) {
                    showerror(e);
                }
                this._interstitialFailureCallback = null;

            }.bind(this));
            window.addEventListener("interstitialClicked", function () {
                showlog("interstitialClicked");
            }.bind(this));
            window.addEventListener("interstitialClosed", function () {
                showlog("interstitialClosed");
                if (this._interstitialRewarded) {
                    this._rewardVideoRewarded = false;
                    this._interstitialFailureCallback = null;
                    try {
                        this._interstitialSuccessCallback && this._interstitialSuccessCallback();
                    } catch (e) {
                        showerror(e);
                    }
                    this._interstitialSuccessCallback = null;
                } else {
                    this._rewardVideoRewarded = false;
                    this._interstitialSuccessCallback = null;
                    try {
                        this._interstitialFailureCallback && this._interstitialFailureCallback();
                    } catch (e) {
                        showerror(e);
                    }
                    this._interstitialFailureCallback = null;
                }

            }.bind(this));
            window.addEventListener("interstitialWillOpen", function () {
                showlog("interstitialWillOpen");
            }.bind(this));
            window.addEventListener("interstitialFailedToLoad", function () {
                showlog("interstitialFailedToLoad");
            }.bind(this));

            //Offerwall
            window.addEventListener("offerwallClosed", function () {
                showlog("offerwallClosed");
                if (this._offerwallRewarded) {
                    this._offerwallRewarded = false;
                    this._offerwallFailureCallback = null;
                    try {
                        this._offerwallSuccessCallback && this._offerwallSuccessCallback();
                    } catch (e) {
                        showerror(e);
                    }
                    this._offerwallSuccessCallback = null;
                } else {
                    this._offerwallRewarded = false;
                    this._offerwallSuccessCallback = null;
                    try {
                        this._offerwallFailureCallback && this._offerwallFailureCallback();
                    } catch (e) {
                        showerror(e);
                    }
                    this._offerwallFailureCallback = null;
                }

            }.bind(this));
            window.addEventListener("offerwallCreditFailed", function () {
                showlog("offerwallCreditFailed");
                this._offerwallRewarded = false;
                this._offerwallSuccessCallback = null;
                try {
                    this._offerwallFailureCallback && this._offerwallFailureCallback();
                } catch (e) {
                    showerror(e);
                }
                this._offerwallFailureCallback = null;
            }.bind(this));
            window.addEventListener("offerwallCreditReceived", function (event) {
                showlog("offerwallCreditReceived", event);
                this._offerwallRewarded = true;
            }.bind(this));
            window.addEventListener("offerwallShowFailed", function () {
                showlog("offerwallShowFailed");
                this._offerwallRewarded = false;
                this._offerwallSuccessCallback = null;
                try {
                    this._offerwallFailureCallback && this._offerwallFailureCallback();
                } catch (e) {
                    showerror(e);
                }
                this._offerwallFailureCallback = null;
            }.bind(this));
            window.addEventListener("offerwallShown", function () {
                showlog("offerwallShown");
                this._offerwallRewarded = true;
            }.bind(this));
            window.addEventListener("offerwallAvailabilityChanged", function (event) {
                showlog("offerwallAvailabilityChanged", event.available);
            }.bind(this));

            //Banner
            window.addEventListener("bannerDidLoad", function () {
                showlog("bannerDidLoad");
                this._bannerLoaded = true;
                window['IronSourceAds']['showBanner']();

            }.bind(this));
            window.addEventListener("bannerFailedToLoad", function () {
                showlog("bannerFailedToLoad");
                this._bannerLoaded = false;
            }.bind(this));
            window.addEventListener("bannerDidClick", function () {
                showlog("bannerDidClick");
                // bi.sendEvent(config.BIConfig.IRONSRC_BANNER_TOUCH, {});

            }.bind(this));
            window.addEventListener("bannerWillPresentScreen", function () {
                showlog("bannerWillPresentScreen")
            }.bind(this));
            window.addEventListener("bannerDidDismissScreen", function () {
                showlog("bannerDidDismissScreen")
            }.bind(this));
            window.addEventListener("bannerWillLeaveApplication", function () {
                showlog("bannerWillLeaveApplication")
            }.bind(this));


            /**
             * Validate Integration
             */
            window['IronSourceAds']['validateIntegration']();


            /**
             * Set user Id (optional)
             */
            window['IronSourceAds']['setDynamicUserId']({'userId': ''});


            window['IronSourceAds']['loadBanner']();
            window['IronSourceAds']['loadInterstitial']();


        }.bind(this),
        "onFailure": function (data) {
            showlog("onFailure...." + data);
        }.bind(this)
    })
};

/**
 * hasInterstitial
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
IronSourceAdsPluginNew.prototype.hasInterstitial = function (params) {
    window['IronSourceAds']['hasInterstitial']({
        "onSuccess": function (available) {
            if (available) {
                params.onSuccess();
            } else {
                params.onFailure();
            }
        }.bind(this),
        "onFailure": function () {
            params.onFailure();
        }.bind(this)
    });
};


/**
 * show interstitial
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
IronSourceAdsPluginNew.prototype.showInterstitial = function (params) {
    // bi.sendEvent(config.BIConfig.IRONSRC_INTERSTITIAL_SHOW, {});

    var successCallback = function () {
        // bi.sendEvent(config.BIConfig.IRONSRC_INTERSTITIAL_SUCCESS, {});
        params.onSuccess();
        window['IronSourceAds']['loadInterstitial']();
    }.bind(this);

    var failureCallback = function () {
        // bi.sendEvent(config.BIConfig.IRONSRC_INTERSTITIAL_FAIL, {});
        params.onFailure();
        window['IronSourceAds']['loadInterstitial']();
    }.bind(this);

    window['IronSourceAds']['hasInterstitial']({
        'onSuccess': function (available) {
            if (available) {
                //Show Interstitial
                window['IronSourceAds']['showInterstitial']({
                    "onSuccess": function () {
                        this._interstitialFailureCallback = failureCallback;
                        this._interstitialSuccessCallback = successCallback;
                    }.bind(this),
                    "onFailure": function () {
                        failureCallback();
                    }.bind(this)
                });
            } else {
                failureCallback();
            }
        }.bind(this),
        "onFailure": function () {
            failureCallback();
        }.bind(this)
    });
};

/**
 * hasRewardedVideo
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
IronSourceAdsPluginNew.prototype.hasRewardedVideo = function (params) {
    window['IronSourceAds']['hasRewardedVideo']({
        "onSuccess": function (available) {
            if (available) {
                params.onSuccess();
            } else {
                params.onFailure();
            }
        }.bind(this),
        "onFailure": function () {
            params.onFailure();
        }.bind(this)
    });
};

/**
 * show rewardvideo
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
IronSourceAdsPluginNew.prototype.showRewardedVideo = function (params) {

    // bi.sendEvent(config.BIConfig.IRONSRC_REWARD_SHOW, {});

    var successCallback = function () {
        // bi.sendEvent(config.BIConfig.IRONSRC_REWARD_SUCCESS, {});
        params.onSuccess();
    }.bind(this);

    var failureCallback = function () {
        // bi.sendEvent(config.BIConfig.IRONSRC_REWARD_FAIL, {});
        params.onFailure();
    }.bind(this);

    window['IronSourceAds']['hasRewardedVideo']({
        'onSuccess': function (available) {
            if (available) {
                //Show Interstitial
                window['IronSourceAds']['showRewardedVideo']({
                    "onFailure": function () {
                        // params.onFailure();
                        failureCallback();
                    }.bind(this),
                    "onSuccess": function () {
                        this._rewardVideoSuccessCallback = successCallback;
                        this._rewardVideoFailureCallback = failureCallback;
                    }.bind(this)
                });
            } else {
                // params.onFailure();
                failureCallback();
            }
        }.bind(this),
        "onFailure": function () {
            // params.onFailure();
            failureCallback();
        }.bind(this)
    });
};

/**
 * show offerwall
 * @param {Function} params.onSuccess - optional on success callback
 * @param {Function} params.onFailure - optional on failure callback
 */
IronSourceAdsPluginNew.prototype.showOfferwall = function (params) {
    window['IronSourceAds']['hasOfferwall']({
        'onSuccess': function (available) {
            if (available) {
                //Show Interstitial
                window['IronSourceAds']['showOfferwall']({
                    "onSuccess": function () {
                        this._offerwallSuccessCallback = params.onSuccess;
                        this._offerwallFailureCallback = params.onFailure;
                    }.bind(this),
                    "onFailure": function () {
                        params.onFailure();
                    }.bind(this)
                });
            } else {
                params.onFailure();
            }
        }.bind(this),
        'onFailure': function () {
            params.onFailure();
        }.bind(this)
    });
};


IronSourceAdsPluginNew.prototype.loadBanner = function () {
    window['IronSourceAds']['loadBanner']();
};


IronSourceAdsPluginNew.prototype.showBanner = function () {
    if (this._bannerLoaded) {
        window['IronSourceAds']['showBanner']();
    } else {
        this.loadBanner();
    }

};

IronSourceAdsPluginNew.prototype.hideBanner = function () {
    window['IronSourceAds']['hideBanner']();

};

module.exports = IronSourceAdsPluginNew;