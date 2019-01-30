var JsbNativeCall = (function () {

    function createEvent(type, data) {
        // var event = document.createEvent('Event');
        var event = new window.Event(type);
        // event.initEvent(type, false, false);
        if (data) {
            for (var i in data) {
                if (data.hasOwnProperty(i)) {
                    event[i] = data[i];
                }
            }
        }
        // event['type'] = type;
        return event;
    }


    function emitWindowEvent(type, data) {
        cc.log("[JsbNativeCall]", type, data);
        var evt = createEvent(type, data);


        window.dispatchEvent(evt);


    }


    var _callbackMaps = {};

    function getCallbackContext(callbackId) {
        if (_callbackMaps.hasOwnProperty(callbackId)) {
            var callbackContext = _callbackMaps[callbackId];
            delete _callbackMaps[callbackId];
            return callbackContext;
        } else {
            return null;
        }
    }

    function setCallbackContext(obj) {
        _callbackMaps[obj.callbackId] = obj;
    }


    var _callbackId = 0;

    function getCallBackId() {
        _callbackId++;
        return _callbackId + "";
    }


    function newCallBackContext(success, failure) {
        var callbackId = getCallBackId();
        return {
            success: success,
            failure: failure,
            callbackId: callbackId
        }

    }


    function _exec(service, action, params, callbackId) {
        cc.log("JsbnativeCall", "_exec", service, action, params, callbackId);

        if (cc.sys.os === cc.sys.OS_IOS) {
            cc.log("JsbnativeCall", "_exec", "ios call...");
            jsb.reflection.callStaticMethod("JsbCall", "exec:withAction:withParams:withCallbackId:", service, action, params, callbackId);

        } else if (cc.sys.os === cc.sys.OS_ANDROID) {
            jsb.reflection.callStaticMethod("me.mingz.ads.JsbCall", "exec", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", service, action, params, callbackId);

        } else {
            cc.log("JsbnativeCall", "_exec", "os error....");
        }

    }

    /**
     * exec Native method Call
     * @param {string} service
     * @param {string} action
     * @param {Object} params
     * @param {function} success
     * @param {function} failure
     */
    function exec(service, action, params, success, failure) {
        cc.log("JsbnativeCall", "exec", "....");
        var callbackContext = newCallBackContext(success, failure);
        setCallbackContext(callbackContext);

        _exec(service, action, JSON.stringify(params), callbackContext.callbackId);
    }



    function callBackCallSuccess(callbackId, params) {
        cc.log("JsbnativeCall", "callBackCallSuccess", "....", callbackId, params);
        var callbackContext = getCallbackContext(callbackId);
        if (callbackContext && callbackContext.hasOwnProperty("success")) {
            callbackContext.success(params);
        }
    }

    function callBackCallFailure(callbackId, params) {
        cc.log("JsbnativeCall", "callBackCallFailure", "....", callbackId, params);
        var callbackContext = getCallbackContext(callbackId);
        if (callbackContext && callbackContext.hasOwnProperty("failure")) {
            callbackContext.failure(params);
        }
    }


    return {
        exec,


        emitWindowEvent,
        callBackCallFailure,
        callBackCallSuccess,
    };


})();

window.JsbNativeCall = JsbNativeCall;

module.exports = JsbNativeCall;