/*
 * sdk的配置文件
 *
 * create by zengxx on 2018-11-22
 */

let config = {};

// 项目配置的一些基本信息
config.baseInfo = {
    debug: true, // 是否debug模式，上线必须设置为false
    biBaseEventId: 100000, // 各项目组bi事件ID必须大于这个值
    updateLog: false, // 调用log.upload是是否上报log，true 表示是(只在debug为true时生效)
};

console.log("mode:debug=" + config.baseInfo.debug);


config.IronSrcConfig = {
    // appKey: "",
    IOS_KEY: "",
    ANDROID_KEY: "867b48cd",
    userId: "",
    isDebug: config.baseInfo.debug
};

config.AdsConfig = {
    'bannerPos': 'BOTTOM_CENTER',
    isTest: config.baseInfo.debug, // 上线必须设置为false,否则不计算收益
    admobIds: {
        android: { // for Android
            banner: 'ca-app-pub-3180535698853578/6151398702',
            interstitial: 'ca-app-pub-3180535698853578/8366498509',
            rewardvideo: 'ca-app-pub-3180535698853578/5712415003',
        },
        ios: { // for iOS
            banner: 'ca-app-pub-3180535698853578/8600510219',
            interstitial: 'ca-app-pub-3180535698853578/2865801952',
            rewardvideo: 'ca-app-pub-3180535698853578/1932952092',
        },
        wp: { // for windows phone
            banner: '',
            interstitial: '',
            rewardvideo: '',
        },
    },
};


// Facebook Audience Network
config.FBAdsConfig = {
    'bannerPos': 'BOTTOM_CENTER',
    isTest: config.baseInfo.debug, // 上线必须设置为false,否则不计算收益
    ad_units: {
        android: { // for Android
            banner: '569904683431316_570432796711838',
            interstitial: '569904683431316_569910350097416',
            rewardvideo: '569904683431316_570516513370133',
        },
        ios: { // for iOS
            banner: '',
            interstitial: '',
            rewardvideo: '',
        },
        wp: { // for windows phone
            banner: '',
            interstitial: '',
            rewardvideo: '',
        },
    },
};


// fb instent game
config.fbConfig = {
    interstitial: '330444431110531_333245534163754', // 插屏广告id
    rewardvideo: '330444431110531_332847420870232',  // 奖励广告id
    appId: '330444431110531', // fb的appid
};

//基础状态信息
config.StateInfo = {
    networkConnected: true,   //网络状态
    networkType: 'none',      //网络类型
    isOnForeground: true      //当前是否是在前台
};

// BI 打点
config.BIConfig = {
    // admob 广告
    ADMOB_BANNER_SHOW: config.baseInfo.biBaseEventId + 1, // 显示 banner 广告
    ADMOB_BANNER_TOUCH: config.baseInfo.biBaseEventId + 2, // 点击 banner 广告
    ADMOB_INTERSTITIAL_SHOW: config.baseInfo.biBaseEventId + 3, // 显示 全屏 广告
    ADMOB_INTERSTITIAL_SUCCESS: config.baseInfo.biBaseEventId + 4, // 显示 全屏 广告成功
    ADMOB_INTERSTITIAL_FAIL: config.baseInfo.biBaseEventId + 5, // 显示 全屏 广告失败
    ADMOB_REWARD_SHOW: config.baseInfo.biBaseEventId + 6, // 显示 奖励 广告
    ADMOB_REWARD_SUCCESS: config.baseInfo.biBaseEventId + 7, // 显示 奖励 广告成功
    ADMOB_REWARD_FAIL: config.baseInfo.biBaseEventId + 8, // 显示 奖励 广告失败


    // facebook小游戏 广告
    FBINSTANT_INTERSTITIAL_SHOW: config.baseInfo.biBaseEventId + 9, // 显示 全屏 广告
    FBINSTANT_INTERSTITIAL_SUCCESS: config.baseInfo.biBaseEventId + 10, // 显示 全屏 广告成功
    FBINSTANT_INTERSTITIAL_FAIL: config.baseInfo.biBaseEventId + 11, // 显示 全屏 广告失败
    FBINSTANT_REWARD_SHOW: config.baseInfo.biBaseEventId + 12, // 显示 奖励 广告
    FBINSTANT_REWARD_SUCCESS: config.baseInfo.biBaseEventId + 13, // 显示 奖励 广告成功
    FBINSTANT_REWARD_FAIL: config.baseInfo.biBaseEventId + 14, // 显示 奖励 广告失败

    // Facebook 原生广告

    // facebook小游戏 bot 订阅 直接调用
    FBINSTANT_BOT_SUBSCRIBE_0: config.baseInfo.biBaseEventId + 15, // bot 订阅发起
    FBINSTANT_BOT_SUBSCRIBE_SUCCESS_0: config.baseInfo.biBaseEventId + 16, // bot 订阅成功
    FBINSTANT_BOT_SUBSCRIBE_FAIL_0: config.baseInfo.biBaseEventId + 17, // bot 订阅失败

    // facebook小游戏 bot 订阅 从游戏自己的授权弹窗发起的
    FBINSTANT_BOT_SUBSCRIBE_1: config.baseInfo.biBaseEventId + 18, // bot 订阅发起
    FBINSTANT_BOT_SUBSCRIBE_SUCCESS_1: config.baseInfo.biBaseEventId + 19, // bot 订阅成功
    FBINSTANT_BOT_SUBSCRIBE_FAIL_1: config.baseInfo.biBaseEventId + 20, // bot 订阅失败

    // ironsource ads
    IRONSRC_BANNER_SHOW: config.baseInfo.biBaseEventId + 21,
    IRONSRC_BANNER_TOUCH: config.baseInfo.biBaseEventId + 22,
    IRONSRC_INTERSTITIAL_SHOW: config.baseInfo.biBaseEventId + 23,
    IRONSRC_INTERSTITIAL_SUCCESS: config.baseInfo.biBaseEventId + 24,
    IRONSRC_INTERSTITIAL_FAIL: config.baseInfo.biBaseEventId + 25,
    IRONSRC_REWARD_SHOW: config.baseInfo.biBaseEventId + 26,
    IRONSRC_REWARD_SUCCESS: config.baseInfo.biBaseEventId + 27,
    IRONSRC_REWARD_FAIL: config.baseInfo.biBaseEventId + 28,

};

//应用系统信息
config.SystemInfo = {
    clientIds: { // 不同平台clientid不同
        fb: 'FB_5.0_facebook.facebook.0-hall20227.facebook.gem1010',
        cordova: {
            ios: 'IOS_5.0_tyGuest,facebook.appStore.0-hall20227.appStore.gem1010',
            android: 'Android_5.0_tyGuest,facebook.googleplay.0-hall20227.googleplay.gem1010'
        },
        native: {
            ios: 'IOS_5.0_tyGuest,facebook.appStore.0-hall20227.appStore.gem1010',
            android: 'Android_5.0_tyGuest,facebook.googleplay.0-hall20227.googleplay.gem1010'
        },
        wx: '',
        web: 'Android_5.0_tyGuest,facebook.googleplay.0-hall20227.googleplay.gem1010'
    },
    intClientIds: {
        fb: 24040,
        cordova: {
            ios: 24041,
            android: 24042
        },
        native: {
            ios: 24041,
            android: 24042
        },
        wx: 0,
        web: 24042
    },
    cloudId: 50,
    version: 1.00,
    // 英语  wx855d88e1f302519c
    // 国学  wxcf10c80dadd5bf0d
    // webSocketUrl: 'ws://192.168.10.21:8014/', // 测试
    // loginUrl: 'http://192.168.10.21:8000/',

    webSocketUrl: 'wss://waxiaofzwss.nalrer.cn', // 仿真
    loginUrl: 'https://openhwxx.nalrer.cn/',

    // webSocketUrl: 'wss://waxiaowss.nalrer.cn', // 线上
    // loginUrl : 'https://openwaxiao.nalrer.cn/',

    shareManagerUrl: 'https://hwmarket.nalrer.cn/',
    deviceId: '',
    wxAppId: '',//'wxcf10c80dadd5bf0d',
    appId: 20227,
    gameId: 20227,
    hall_version: "hall37",
    cdnPath: "https://elsfkws.nalrer.cn/teris/",
    remotePackPath: "remote_res/res.zip",
    biLogServer: "https://hwcbi.nalrer.cn/api/bilog5/text",
    biJsonLogServer: "https://hwcbi.nalrer.cn/api/bilog5/report",
    errorLogServer: "https://hwcbi.nalrer.cn/api/bilog5/clientlog",
    errorLog: 'https://hwcbi.nalrer.cn/api/bilog5/text/error',
    tyVersion: 1.1,
};

config.UserInfo = {
    uuid: '',
    userId: 0,
    playerId: 0,
    userName: 'TuWechatGame',
    userPic: '',
    authorCode: '',
    systemType: 0, //1:苹果非iPhone X  2:iPhone X 3、安卓
    wechatType: "6.6.1",//微信版本号
    model: "未知设备",
    system: "iOS 10.0.1",
    loc: '',
    scene_id: "",
    scene_param: "",
    device_id: '',
    invite_id: 0,
    wxgame_session_key: ""
};

config.AdjustInfo = {
    isTest: config.baseInfo.debug, // 上线必须设置为false
    appToken: 'y37hhbk3h0jk',
    eventToken: {
        START: '3zgi11',     // 启动
        REGISTER: 'rf81mo',  // 注册
        LOGIN: '9zhccv',     // 登录
    },
};

// 分享失败返回的错误码
config.ShareErrorCode = {
    'UNKNOWN': 0, // 未知错误
    'SAME_CONTEXT': 1,  // facebook重复分享给同一个玩家
};

module.exports = config;

