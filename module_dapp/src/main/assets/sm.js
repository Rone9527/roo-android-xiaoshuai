/*! version:v1.3.3 1626073583245 */
!function (e, t) {
    "object" == typeof exports && "object" == typeof module ? module.exports = t() : "function" == typeof define && define.amd ? define("mTronWeb", [], t) : "object" == typeof exports ? exports.mTronWeb = t() : e.mTronWeb = t()
}(window, function () {
    return function (e) {
        var t = {};

        function r(n) {
            if (t[n]) return t[n].exports;
            var i = t[n] = {i: n, l: !1, exports: {}};
            return e[n].call(i.exports, i, i.exports, r), i.l = !0, i.exports
        }

        return r.m = e, r.c = t, r.d = function (e, t, n) {
            r.o(e, t) || Object.defineProperty(e, t, {enumerable: !0, get: n})
        }, r.r = function (e) {
            "undefined" != typeof Symbol && Symbol.toStringTag && Object.defineProperty(e, Symbol.toStringTag, {value: "Module"}), Object.defineProperty(e, "__esModule", {value: !0})
        }, r.t = function (e, t) {
            if (1 & t && (e = r(e)), 8 & t) return e;
            if (4 & t && "object" == typeof e && e && e.__esModule) return e;
            var n = Object.create(null);
            if (r.r(n), Object.defineProperty(n, "default", {
                enumerable: !0,
                value: e
            }), 2 & t && "string" != typeof e) for (var i in e) r.d(n, i, function (t) {
                return e[t]
            }.bind(null, i));
            return n
        }, r.n = function (e) {
            var t = e && e.__esModule ? function () {
                return e.default
            } : function () {
                return e
            };
            return r.d(t, "a", t), t
        }, r.o = function (e, t) {
            return Object.prototype.hasOwnProperty.call(e, t)
        }, r.p = "", r(r.s = 44)
    }([function (e, t) {
        function r(e) {
            return (r = "function" == typeof Symbol && "symbol" == typeof Symbol.iterator ? function (e) {
                return typeof e
            } : function (e) {
                return e && "function" == typeof Symbol && e.constructor === Symbol && e !== Symbol.prototype ? "symbol" : typeof e
            })(e)
        }

        function n(t) {
            return "function" == typeof Symbol && "symbol" === r(Symbol.iterator) ? e.exports = n = function (e) {
                return r(e)
            } : e.exports = n = function (e) {
                return e && "function" == typeof Symbol && e.constructor === Symbol && e !== Symbol.prototype ? "symbol" : r(e)
            }, n(t)
        }

        e.exports = n
    }, function (e, t, r) {
        "use strict";
        var n = r(5), i = r(26), a = Object.prototype.toString;

        function o(e) {
            return "[object Array]" === a.call(e)
        }

        function s(e) {
            return null !== e && "object" == typeof e
        }

        function u(e) {
            return "[object Function]" === a.call(e)
        }

        function c(e, t) {
            if (null !== e && void 0 !== e) if ("object" != typeof e && (e = [e]), o(e)) for (var r = 0, n = e.length; r < n; r++) t.call(null, e[r], r, e); else for (var i in e) Object.prototype.hasOwnProperty.call(e, i) && t.call(null, e[i], i, e)
        }

        e.exports = {
            isArray: o, isArrayBuffer: function (e) {
                return "[object ArrayBuffer]" === a.call(e)
            }, isBuffer: i, isFormData: function (e) {
                return "undefined" != typeof FormData && e instanceof FormData
            }, isArrayBufferView: function (e) {
                return "undefined" != typeof ArrayBuffer && ArrayBuffer.isView ? ArrayBuffer.isView(e) : e && e.buffer && e.buffer instanceof ArrayBuffer
            }, isString: function (e) {
                return "string" == typeof e
            }, isNumber: function (e) {
                return "number" == typeof e
            }, isObject: s, isUndefined: function (e) {
                return void 0 === e
            }, isDate: function (e) {
                return "[object Date]" === a.call(e)
            }, isFile: function (e) {
                return "[object File]" === a.call(e)
            }, isBlob: function (e) {
                return "[object Blob]" === a.call(e)
            }, isFunction: u, isStream: function (e) {
                return s(e) && u(e.pipe)
            }, isURLSearchParams: function (e) {
                return "undefined" != typeof URLSearchParams && e instanceof URLSearchParams
            }, isStandardBrowserEnv: function () {
                return ("undefined" == typeof navigator || "ReactNative" !== navigator.product) && "undefined" != typeof window && "undefined" != typeof document
            }, forEach: c, merge: function e() {
                var t = {};

                function r(r, n) {
                    "object" == typeof t[n] && "object" == typeof r ? t[n] = e(t[n], r) : t[n] = r
                }

                for (var n = 0, i = arguments.length; n < i; n++) c(arguments[n], r);
                return t
            }, extend: function (e, t, r) {
                return c(t, function (t, i) {
                    e[i] = r && "function" == typeof t ? n(t, r) : t
                }), e
            }, trim: function (e) {
                return e.replace(/^\s*/, "").replace(/\s*$/, "")
            }
        }
    }, function (e, t) {
        function r(t) {
            return e.exports = r = Object.setPrototypeOf ? Object.getPrototypeOf : function (e) {
                return e.__proto__ || Object.getPrototypeOf(e)
            }, r(t)
        }

        e.exports = r
    }, function (e, t, r) {
        "use strict";
        (function (t) {
            var n = r(1), i = r(29), a = {"Content-Type": "application/x-www-form-urlencoded"};

            function o(e, t) {
                !n.isUndefined(e) && n.isUndefined(e["Content-Type"]) && (e["Content-Type"] = t)
            }

            var s = {
                adapter: function () {
                    var e;
                    return "undefined" != typeof XMLHttpRequest ? e = r(6) : void 0 !== t && (e = r(6)), e
                }(),
                transformRequest: [function (e, t) {
                    return i(t, "Content-Type"), n.isFormData(e) || n.isArrayBuffer(e) || n.isBuffer(e) || n.isStream(e) || n.isFile(e) || n.isBlob(e) ? e : n.isArrayBufferView(e) ? e.buffer : n.isURLSearchParams(e) ? (o(t, "application/x-www-form-urlencoded;charset=utf-8"), e.toString()) : n.isObject(e) ? (o(t, "application/json;charset=utf-8"), JSON.stringify(e)) : e
                }],
                transformResponse: [function (e) {
                    if ("string" == typeof e) try {
                        e = JSON.parse(e)
                    } catch (e) {
                    }
                    return e
                }],
                timeout: 0,
                xsrfCookieName: "XSRF-TOKEN",
                xsrfHeaderName: "X-XSRF-TOKEN",
                maxContentLength: -1,
                validateStatus: function (e) {
                    return e >= 200 && e < 300
                },
                headers: {common: {Accept: "application/json, text/plain, */*"}}
            };
            n.forEach(["delete", "get", "head"], function (e) {
                s.headers[e] = {}
            }), n.forEach(["post", "put", "patch"], function (e) {
                s.headers[e] = n.merge(a)
            }), e.exports = s
        }).call(this, r(28))
    }, function (e, t, r) {
        "use strict";
        r.r(t), function (e) {
            var t = r(0), n = r.n(t);
            !function (t, i) {
                "object" == ("undefined" == typeof exports ? "undefined" : n()(exports)) && "object" == n()(e) ? e.exports = i() : "function" == typeof define && r(18) ? define("TronWeb", [], i) : "object" == ("undefined" == typeof exports ? "undefined" : n()(exports)) ? exports.TronWeb = i() : t.TronWeb = i()
            }(window, function () {
                return function (e) {
                    var t = {};

                    function r(n) {
                        if (t[n]) return t[n].exports;
                        var i = t[n] = {i: n, l: !1, exports: {}};
                        return e[n].call(i.exports, i, i.exports, r), i.l = !0, i.exports
                    }

                    return r.m = e, r.c = t, r.d = function (e, t, n) {
                        r.o(e, t) || Object.defineProperty(e, t, {enumerable: !0, get: n})
                    }, r.r = function (e) {
                        "undefined" != typeof Symbol && Symbol.toStringTag && Object.defineProperty(e, Symbol.toStringTag, {value: "Module"}), Object.defineProperty(e, "__esModule", {value: !0})
                    }, r.t = function (e, t) {
                        if (1 & t && (e = r(e)), 8 & t) return e;
                        if (4 & t && "object" == n()(e) && e && e.__esModule) return e;
                        var i = Object.create(null);
                        if (r.r(i), Object.defineProperty(i, "default", {
                            enumerable: !0,
                            value: e
                        }), 2 & t && "string" != typeof e) for (var a in e) r.d(i, a, function (t) {
                            return e[t]
                        }.bind(null, a));
                        return i
                    }, r.n = function (e) {
                        var t = e && e.__esModule ? function () {
                            return e.default
                        } : function () {
                            return e
                        };
                        return r.d(t, "a", t), t
                    }, r.o = function (e, t) {
                        return Object.prototype.hasOwnProperty.call(e, t)
                    }, r.p = "", r(r.s = 6)
                }([function (e, t, r) {
                    var n = {};
                    r.r(n), r.d(n, "byte2hexStr", function () {
                        return v
                    }), r.d(n, "bytesToString", function () {
                        return g
                    }), r.d(n, "hextoString", function () {
                        return m
                    }), r.d(n, "byteArray2hexStr", function () {
                        return y
                    }), r.d(n, "base64DecodeFromString", function () {
                        return w
                    }), r.d(n, "base64EncodeToString", function () {
                        return x
                    });
                    var i = {};
                    r.r(i), r.d(i, "bin2String", function () {
                        return S
                    }), r.d(i, "arrayEquals", function () {
                        return M
                    }), r.d(i, "stringToBytes", function () {
                        return I
                    }), r.d(i, "byte2hexStr", function () {
                        return v
                    }), r.d(i, "bytesToString", function () {
                        return g
                    }), r.d(i, "hextoString", function () {
                        return m
                    }), r.d(i, "byteArray2hexStr", function () {
                        return y
                    }), r.d(i, "base64DecodeFromString", function () {
                        return w
                    }), r.d(i, "base64EncodeToString", function () {
                        return x
                    }), r.d(i, "hexChar2byte", function () {
                        return E
                    }), r.d(i, "isHexChar", function () {
                        return N
                    }), r.d(i, "hexStr2byteArray", function () {
                        return P
                    }), r.d(i, "strToDate", function () {
                        return T
                    }), r.d(i, "isNumber", function () {
                        return O
                    }), r.d(i, "getStringType", function () {
                        return R
                    });
                    var a = {};
                    r.r(a), r.d(a, "encode58", function () {
                        return W
                    }), r.d(a, "decode58", function () {
                        return F
                    });
                    var o = {};
                    r.r(o), r.d(o, "getBase58CheckAddress", function () {
                        return D
                    }), r.d(o, "decodeBase58Address", function () {
                        return q
                    }), r.d(o, "signTransaction", function () {
                        return z
                    }), r.d(o, "arrayToBase64String", function () {
                        return H
                    }), r.d(o, "signBytes", function () {
                        return V
                    }), r.d(o, "getRowBytesFromTransactionBase64", function () {
                        return K
                    }), r.d(o, "genPriKey", function () {
                        return G
                    }), r.d(o, "computeAddress", function () {
                        return Y
                    }), r.d(o, "getAddressFromPriKey", function () {
                        return J
                    }), r.d(o, "decode58Check", function () {
                        return X
                    }), r.d(o, "isAddressValid", function () {
                        return Z
                    }), r.d(o, "getBase58CheckAddressFromPriKeyBase64String", function () {
                        return $
                    }), r.d(o, "getHexStrAddressFromPriKeyBase64String", function () {
                        return Q
                    }), r.d(o, "getAddressFromPriKeyBase64String", function () {
                        return ee
                    }), r.d(o, "getPubKeyFromPriKey", function () {
                        return te
                    }), r.d(o, "ECKeySign", function () {
                        return re
                    }), r.d(o, "SHA256", function () {
                        return ne
                    }), r.d(o, "passwordToAddress", function () {
                        return ie
                    }), r.d(o, "pkToAddress", function () {
                        return ae
                    });
                    var s = {};
                    r.r(s), r.d(s, "generateAccount", function () {
                        return oe
                    });
                    var u = {};
                    r.r(u), r.d(u, "decodeParams", function () {
                        return ce
                    }), r.d(u, "encodeParams", function () {
                        return fe
                    });
                    var c = r(7), f = r.n(c), d = r(1), h = r.n(d), l = r(2), p = r.n(l);

                    function b() {
                        var e = this;
                        this._keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=", this.encode = function (t) {
                            for (var r, n, i, a, o, s, u, c = "", f = 0; f < t.length;) a = (r = t.charCodeAt(f++)) >> 2, o = (3 & r) << 4 | (n = t.charCodeAt(f++)) >> 4, s = (15 & n) << 2 | (i = t.charCodeAt(f++)) >> 6, u = 63 & i, isNaN(n) ? s = u = 64 : isNaN(i) && (u = 64), c = c + e._keyStr.charAt(a) + e._keyStr.charAt(o) + e._keyStr.charAt(s) + e._keyStr.charAt(u);
                            return c
                        }, this.encodeIgnoreUtf8 = function (t) {
                            for (var r, n, i, a, o, s, u, c = "", f = 0; f < t.length;) a = (r = t[f++]) >> 2, o = (3 & r) << 4 | (n = t[f++]) >> 4, s = (15 & n) << 2 | (i = t[f++]) >> 6, u = 63 & i, isNaN(n) ? s = u = 64 : isNaN(i) && (u = 64), c = c + e._keyStr.charAt(a) + e._keyStr.charAt(o) + e._keyStr.charAt(s) + e._keyStr.charAt(u);
                            return c
                        }, this.decode = function (t) {
                            var r, n, i, a, o, s, u = "", c = 0;
                            for (t = t.replace(/[^A-Za-z0-9\+\/\=]/g, ""); c < t.length;) r = e._keyStr.indexOf(t.charAt(c++)) << 2 | (a = e._keyStr.indexOf(t.charAt(c++))) >> 4, n = (15 & a) << 4 | (o = e._keyStr.indexOf(t.charAt(c++))) >> 2, i = (3 & o) << 6 | (s = e._keyStr.indexOf(t.charAt(c++))), u += String.fromCharCode(r), 64 != o && (u += String.fromCharCode(n)), 64 != s && (u += String.fromCharCode(i));
                            return e._utf8_decode(u)
                        }, this.decodeToByteArray = function (t) {
                            var r, n, i, a, o, s, u = "", c = 0;
                            for (t = t.replace(/[^A-Za-z0-9\+\/\=]/g, ""); c < t.length;) r = e._keyStr.indexOf(t.charAt(c++)) << 2 | (a = e._keyStr.indexOf(t.charAt(c++))) >> 4, n = (15 & a) << 4 | (o = e._keyStr.indexOf(t.charAt(c++))) >> 2, i = (3 & o) << 6 | (s = e._keyStr.indexOf(t.charAt(c++))), u += String.fromCharCode(r), 64 != o && (u += String.fromCharCode(n)), 64 != s && (u += String.fromCharCode(i));
                            return e._out2ByteArray(u)
                        }, this._out2ByteArray = function (e) {
                            for (var t = new Array(e.length), r = 0, n = 0; r < e.length;) n = e.charCodeAt(r), t[r] = n, r++;
                            return t
                        }, this._utf8_encode = function (e) {
                            e = e.replace(/\r\n/g, "\n");
                            for (var t = "", r = 0; r < e.length; r++) {
                                var n = e.charCodeAt(r);
                                n < 128 ? t += String.fromCharCode(n) : n > 127 && n < 2048 ? (t += String.fromCharCode(n >> 6 | 192), t += String.fromCharCode(63 & n | 128)) : (t += String.fromCharCode(n >> 12 | 224), t += String.fromCharCode(n >> 6 & 63 | 128), t += String.fromCharCode(63 & n | 128))
                            }
                            return t
                        }, this._utf8_decode = function (e) {
                            for (var t = "", r = 0, n = 0, i = 0, a = 0; r < e.length;) (n = e.charCodeAt(r)) < 128 ? (t += String.fromCharCode(n), r++) : n > 191 && n < 224 ? (i = e.charCodeAt(r + 1), t += String.fromCharCode((31 & n) << 6 | 63 & i), r += 2) : (i = e.charCodeAt(r + 1), a = e.charCodeAt(r + 2), t += String.fromCharCode((15 & n) << 12 | (63 & i) << 6 | 63 & a), r += 3);
                            return t
                        }
                    }

                    function v(e) {
                        if ("number" != typeof e) throw new Error("Input must be a number");
                        if (e < 0 || e > 255) throw new Error("Input must be a byte");
                        var t = "";
                        return (t += "0123456789ABCDEF".charAt(e >> 4)) + "0123456789ABCDEF".charAt(15 & e)
                    }

                    function g(e) {
                        if ("string" == typeof e) return e;
                        for (var t = "", r = 0; r < e.length; r++) {
                            var n = e[r].toString(2), i = n.match(/^1+?(?=0)/);
                            if (i && 8 === n.length) {
                                for (var a = i[0].length, o = e[r].toString(2).slice(7 - a), s = 1; s < a; s++) o += e[s + r].toString(2).slice(2);
                                t += String.fromCharCode(parseInt(o, 2)), r += a - 1
                            } else t += String.fromCharCode(e[r])
                        }
                        return t
                    }

                    function m(e) {
                        for (var t = e.replace(/^0x/, "").split(""), r = "", n = 0; n < t.length / 2; n++) {
                            var i = "0x".concat(t[2 * n]).concat(t[2 * n + 1]);
                            r += String.fromCharCode(i)
                        }
                        return r
                    }

                    function y(e) {
                        for (var t = "", r = 0; r < e.length; r++) t += v(e[r]);
                        return t
                    }

                    function w(e) {
                        return (new b).decodeToByteArray(e)
                    }

                    function x(e) {
                        return (new b).encodeIgnoreUtf8(e)
                    }

                    var _ = r(22), A = r.n(_), k = r(3);

                    function S(e) {
                        return g(e)
                    }

                    function M(e, t, r) {
                        if (e.length != t.length) return !1;
                        var n;
                        for (n = 0; n < e.length; n++) if (r) {
                            if (e[n] != t[n]) return !1
                        } else if (JSON.stringify(e[n]) != JSON.stringify(t[n])) return !1;
                        return !0
                    }

                    function I(e) {
                        if ("string" != typeof e) throw new Error("The passed string is not a string");
                        var t, r, n = new Array;
                        t = e.length;
                        for (var i = 0; i < t; i++) (r = e.charCodeAt(i)) >= 65536 && r <= 1114111 ? (n.push(r >> 18 & 7 | 240), n.push(r >> 12 & 63 | 128), n.push(r >> 6 & 63 | 128), n.push(63 & r | 128)) : r >= 2048 && r <= 65535 ? (n.push(r >> 12 & 15 | 224), n.push(r >> 6 & 63 | 128), n.push(63 & r | 128)) : r >= 128 && r <= 2047 ? (n.push(r >> 6 & 31 | 192), n.push(63 & r | 128)) : n.push(255 & r);
                        return n
                    }

                    function E(e) {
                        var t;
                        if (e >= "A" && e <= "F" ? t = e.charCodeAt(0) - "A".charCodeAt(0) + 10 : e >= "a" && e <= "f" ? t = e.charCodeAt(0) - "a".charCodeAt(0) + 10 : e >= "0" && e <= "9" && (t = e.charCodeAt(0) - "0".charCodeAt(0)), "number" == typeof t) return t;
                        throw new Error("The passed hex char is not a valid hex char")
                    }

                    function N(e) {
                        return e >= "A" && e <= "F" || e >= "a" && e <= "f" || e >= "0" && e <= "9" ? 1 : 0
                    }

                    function P(e) {
                        if ("string" != typeof e) throw new Error("The passed string is not a string");
                        for (var t = Array(), r = 0, n = 0, i = 0, a = 0; a < e.length; a++) {
                            var o = e.charAt(a);
                            if (!N(o)) throw new Error("The passed hex char is not a valid hex string");
                            r <<= 4, r += E(o), 0 == ++n % 2 && (t[i++] = r, r = 0)
                        }
                        return t
                    }

                    function T(e) {
                        if (!/^\d{4}-\d{2}-\d{2}( \d{2}-\d{2}-\d{2}|)/.test(e)) throw new Error("The passed date string is not valid");
                        var t = e.split(" "), r = t[0].split("-"), n = parseInt(r[0], 10), i = parseInt(r[1], 10) - 1,
                            a = parseInt(r[2], 10);
                        if (t.length > 1) {
                            var o = t[1].split("-"), s = parseInt(o[0], 10), u = parseInt(o[1], 10),
                                c = parseInt(o[2], 10);
                            return new Date(n, i, a, s, u, c)
                        }
                        return new Date(n, i, a)
                    }

                    function O(e) {
                        return e >= "0" && e <= "9" ? 1 : 0
                    }

                    function R(e) {
                        if (null == e) return -1;
                        if ("string" != typeof e) return -1;
                        if (0 == e.length || "" == e) return -1;
                        var t = 0;
                        if (40 == e.length) for (; t < 40 && N(e.charAt(t)); t++) ;
                        if (40 == t) return 1;
                        for (t = 0; t < e.length && O(e.charAt(t)); t++) ;
                        if (t == e.length) return 2;
                        for (t = 0; t < e.length; t++) if (e.charAt(t) > " ") return 3;
                        return -1
                    }

                    for (var j = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz", C = {}, B = 0; B < j.length; B++) C[j.charAt(B)] = B;
                    var L = 58;

                    function W(e) {
                        if (0 === e.length) return "";
                        var t, r, n = [0];
                        for (t = 0; t < e.length; t++) {
                            for (r = 0; r < n.length; r++) n[r] <<= 8;
                            n[0] += e[t];
                            var i = 0;
                            for (r = 0; r < n.length; ++r) n[r] += i, i = n[r] / L | 0, n[r] %= L;
                            for (; i;) n.push(i % L), i = i / L | 0
                        }
                        for (t = 0; 0 === e[t] && t < e.length - 1; t++) n.push(0);
                        return n.reverse().map(function (e) {
                            return j[e]
                        }).join("")
                    }

                    function F(e) {
                        if (0 === e.length) return [];
                        var t, r, n = [0];
                        for (t = 0; t < e.length; t++) {
                            var i = e[t];
                            if (!(i in C)) throw new Error("Non-base58 character");
                            for (r = 0; r < n.length; r++) n[r] *= L;
                            n[0] += C[i];
                            var a = 0;
                            for (r = 0; r < n.length; ++r) n[r] += a, a = n[r] >> 8, n[r] &= 255;
                            for (; a;) n.push(255 & a), a >>= 8
                        }
                        for (t = 0; "1" === e[t] && t < e.length - 1; t++) n.push(0);
                        return n.reverse()
                    }

                    var U = r(9);

                    function D(e) {
                        var t = ne(ne(e)).slice(0, 4);
                        return W(t = e.concat(t))
                    }

                    function q(e) {
                        if ("string" != typeof e) return !1;
                        if (e.length <= 4) return !1;
                        var t = F(e);
                        if (e.length <= 4) return !1;
                        var r = t.length - 4, n = t.slice(r), i = ne(ne(t = t.slice(0, r))).slice(0, 4);
                        if (n[0] == i[0] && n[1] == i[1] && n[2] == i[2] && n[3] == i[3]) return t;
                        throw new Error("Invalid address provided")
                    }

                    function z(e, t) {
                        "string" == typeof e && (e = P(e));
                        var r = (P(t.txID), "");
                        return Array.isArray(t.signature) ? t.signature.includes(r) || t.signature.push(r) : t.signature = [r], t
                    }

                    function H(e) {
                        return btoa(String.fromCharCode.apply(String, A()(e)))
                    }

                    function V(e, t) {
                        return "string" == typeof e && (e = P(e)), ne(t), ""
                    }

                    function K(e) {
                        var t = w(e);
                        return proto.protocol.Transaction.deserializeBinary(t).getRawData().serializeBinary()
                    }

                    function G() {
                        return ""
                    }

                    function Y(e) {
                        65 === e.length && (e = e.slice(1));
                        var t = Object(U.keccak256)(e).toString().substring(2);
                        return P(k.a + t.substring(24))
                    }

                    function J(e) {
                        return Y("")
                    }

                    function X(e) {
                        var t = F(e);
                        if (t.length <= 4) return !1;
                        var r = t.slice(0, t.length - 4), n = ne(ne(r));
                        return n[0] === t[r.length] && n[1] === t[r.length + 1] && n[2] === t[r.length + 2] && n[3] === t[r.length + 3] && r
                    }

                    function Z(e) {
                        if ("string" != typeof e) return !1;
                        if (e.length !== k.d) return !1;
                        var t = F(e);
                        if (25 !== t.length) return !1;
                        if (t[0] !== k.b) return !1;
                        var r = t.slice(21), n = ne(ne(t = t.slice(0, 21))).slice(0, 4);
                        return r[0] == n[0] && r[1] == n[1] && r[2] == n[2] && r[3] == n[3]
                    }

                    function $(e) {
                        return w(e), D(Y(""))
                    }

                    function Q(e) {
                        return w(e), y(Y(""))
                    }

                    function ee(e) {
                        return w(e), x(Y(""))
                    }

                    function te(e) {
                        return ""
                    }

                    function re(e, t) {
                        return ""
                    }

                    function ne(e) {
                        var t = y(e);
                        return P(Object(U.sha256)("0x" + t).replace(/^0x/, ""))
                    }

                    function ie(e) {
                        return w(e), D(J())
                    }

                    function ae(e) {
                        return ""
                    }

                    function oe() {
                        var e = J();
                        return {privateKey: y(""), publicKey: y(""), address: {base58: D(e), hex: y(e)}}
                    }

                    var se = r(6), ue = new U.AbiCoder;

                    function ce(e, t, r, n) {
                        if (r && "boolean" != typeof r || (n = r, r = t, t = e, e = []), n && r.replace(/^0x/, "").length % 64 == 8 && (r = "0x" + r.replace(/^0x/, "").substring(8)), r.replace(/^0x/, "").length % 64) throw new Error("The encoded string is not valid. Its length must be a multiple of 64.");
                        return t = t.map(function (e) {
                            return /trcToken/.test(e) && (e = e.replace(/trcToken/, "uint256")), e
                        }), ue.decode(t, r).reduce(function (r, n, i) {
                            return "address" == t[i] && (n = k.a + n.substr(2).toLowerCase()), e.length ? r[e[i]] = n : r.push(n), r
                        }, e.length ? {} : [])
                    }

                    function fe(e, t) {
                        for (var r = 0; r < e.length; r++) "address" === e[r] && (t[r] = se.default.address.toHex(t[r]).replace(k.c, "0x"));
                        return ue.encode(e, t)
                    }

                    var de = r(23), he = r.n(de), le = r(69), pe = r.n(le);

                    function be(e, t) {
                        var r = Object.keys(e);
                        if (Object.getOwnPropertySymbols) {
                            var n = Object.getOwnPropertySymbols(e);
                            t && (n = n.filter(function (t) {
                                return Object.getOwnPropertyDescriptor(e, t).enumerable
                            })), r.push.apply(r, n)
                        }
                        return r
                    }

                    var ve, ge = {
                        isValidURL: function (e) {
                            return "string" == typeof e && pe()(e.toString(), {
                                protocols: ["http", "https"],
                                require_tld: !1
                            })
                        }, isObject: function (e) {
                            return e === Object(e) && "[object Array]" !== Object.prototype.toString.call(e)
                        }, isArray: function (e) {
                            return Array.isArray(e)
                        }, isJson: function (e) {
                            try {
                                return !!JSON.parse(e)
                            } catch (e) {
                                return !1
                            }
                        }, isBoolean: function (e) {
                            return "boolean" == typeof e
                        }, isBigNumber: function (e) {
                            return e && (e instanceof he.a || e.constructor && "BigNumber" === e.constructor.name)
                        }, isString: function (e) {
                            return "string" == typeof e || e && e.constructor && "String" === e.constructor.name
                        }, isFunction: function (e) {
                            return "function" == typeof e
                        }, isHex: function (e) {
                            return "string" == typeof e && !isNaN(parseInt(e, 16)) && /^(0x|)[a-fA-F0-9]+$/.test(e)
                        }, isInteger: function (e) {
                            return null !== e && Number.isInteger(Number(e))
                        }, hasProperty: function (e, t) {
                            return Object.prototype.hasOwnProperty.call(e, t)
                        }, hasProperties: function (e) {
                            for (var t = this, r = arguments.length, n = new Array(r > 1 ? r - 1 : 0), i = 1; i < r; i++) n[i - 1] = arguments[i];
                            return n.length && !n.map(function (r) {
                                return t.hasProperty(e, r)
                            }).includes(!1)
                        }, mapEvent: function (e) {
                            var t = {
                                block: e.block_number,
                                timestamp: e.block_timestamp,
                                contract: e.contract_address,
                                name: e.event_name,
                                transaction: e.transaction_id,
                                result: e.result,
                                resourceNode: e.resource_Node || (e._unconfirmed ? "fullNode" : "solidityNode")
                            };
                            return e._unconfirmed && (t.unconfirmed = e._unconfirmed), e._fingerprint && (t.fingerprint = e._fingerprint), t
                        }, parseEvent: function (e, t) {
                            var r = t.inputs;
                            if (!e.result) return e;
                            if (this.isObject(e.result)) for (var n = 0; n < r.length; n++) {
                                var i = r[n];
                                "address" == i.type && i.name in e.result && (e.result[i.name] = k.a + e.result[i.name].substr(2).toLowerCase())
                            } else this.isArray(e.result) && (e.result = e.result.reduce(function (e, t, n) {
                                var i = r[n], a = i.name;
                                return "address" == i.type && (t = k.a + t.substr(2).toLowerCase()), e[a] = t, e
                            }, {}));
                            return e
                        }, padLeft: function (e, t, r) {
                            for (var n = e.toString(); n.length < r;) n = t + n;
                            return n
                        }, isNotNullOrUndefined: function (e) {
                            return null != e
                        }, sleep: (ve = p()(h.a.mark(function e() {
                            var t, r = arguments;
                            return h.a.wrap(function (e) {
                                for (; ;) switch (e.prev = e.next) {
                                    case 0:
                                        return t = r.length > 0 && void 0 !== r[0] ? r[0] : 1e3, e.abrupt("return", new Promise(function (e) {
                                            return setTimeout(e, t)
                                        }));
                                    case 2:
                                    case"end":
                                        return e.stop()
                                }
                            }, e)
                        })), function () {
                            return ve.apply(this, arguments)
                        })
                    };
                    t.a = function (e) {
                        for (var t = 1; t < arguments.length; t++) {
                            var r = null != arguments[t] ? arguments[t] : {};
                            t % 2 ? be(r, !0).forEach(function (t) {
                                f()(e, t, r[t])
                            }) : Object.getOwnPropertyDescriptors ? Object.defineProperties(e, Object.getOwnPropertyDescriptors(r)) : be(r).forEach(function (t) {
                                Object.defineProperty(e, t, Object.getOwnPropertyDescriptor(r, t))
                            })
                        }
                        return e
                    }({}, ge, {code: i, accounts: s, base58: a, bytes: n, crypto: o, abi: u, ethersUtils: U})
                }, function (e, t, r) {
                    e.exports = r(78)
                }, function (e, t) {
                    function r(e, t, r, n, i, a, o) {
                        try {
                            var s = e[a](o), u = s.value
                        } catch (e) {
                            return void r(e)
                        }
                        s.done ? t(u) : Promise.resolve(u).then(n, i)
                    }

                    e.exports = function (e) {
                        return function () {
                            var t = this, n = arguments;
                            return new Promise(function (i, a) {
                                var o = e.apply(t, n);

                                function s(e) {
                                    r(o, i, a, s, u, "next", e)
                                }

                                function u(e) {
                                    r(o, i, a, s, u, "throw", e)
                                }

                                s(void 0)
                            })
                        }
                    }
                }, function (e, t, r) {
                    r.d(t, "d", function () {
                        return n
                    }), r.d(t, "a", function () {
                        return i
                    }), r.d(t, "b", function () {
                        return a
                    }), r.d(t, "c", function () {
                        return o
                    });
                    var n = 34, i = "41", a = 65, o = /^(41)/
                }, function (e, t) {
                    e.exports = function (e, t) {
                        if (!(e instanceof t)) throw new TypeError("Cannot call a class as a function")
                    }
                }, function (e, t) {
                    function r(e, t) {
                        for (var r = 0; r < t.length; r++) {
                            var n = t[r];
                            n.enumerable = n.enumerable || !1, n.configurable = !0, "value" in n && (n.writable = !0), Object.defineProperty(e, n.key, n)
                        }
                    }

                    e.exports = function (e, t, n) {
                        return t && r(e.prototype, t), n && r(e, n), e
                    }
                }, function (e, t, r) {
                    r.r(t), function (e) {
                        r.d(t, "default", function () {
                            return D
                        });
                        var n = r(1), i = r.n(n), a = r(2), o = r.n(a), s = r(17), u = r.n(s), c = r(4), f = r.n(c),
                            d = r(5), h = r.n(d), l = r(65), p = r.n(l), b = r(66), v = r.n(b), g = r(21), m = r.n(g),
                            y = r(67), w = r.n(y), x = r(7), _ = r.n(x), A = r(8), k = r(0), S = r(23), M = r.n(S),
                            I = r(70), E = r.n(I), N = r(71), P = r(10), T = r.n(P), O = r(35), R = r(36), j = r(38),
                            C = r(37), B = r(73), L = r(9), W = r(3), F = "3.5.0", U = 15e7, D = function (t) {
                                function r() {
                                    var e, t, n = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
                                        i = arguments.length > 1 && void 0 !== arguments[1] && arguments[1],
                                        a = arguments.length > 2 && void 0 !== arguments[2] && arguments[2],
                                        o = arguments.length > 3 && void 0 !== arguments[3] && arguments[3],
                                        s = arguments.length > 4 && void 0 !== arguments[4] && arguments[4];
                                    f()(this, r), e = p()(this, v()(r).call(this));
                                    var c = !1, d = !1;
                                    return "object" === u()(n) && (n.fullNode || n.fullHost) ? (t = n.fullNode || n.fullHost, o = i, i = n.solidityNode || n.fullHost, a = n.eventServer || n.fullHost, c = n.headers || !1, d = n.eventHeaders || c, s = n.privateKey) : t = n, k.a.isString(t) && (t = new A.a.HttpProvider(t)), k.a.isString(i) && (i = new A.a.HttpProvider(i)), k.a.isString(a) && (a = new A.a.HttpProvider(a)), e.event = new C.a(m()(e)), e.transactionBuilder = new O.a(m()(e)), e.trx = new R.a(m()(e)), e.utils = k.a, e.setFullNode(t), e.setSolidityNode(i), e.setEventServer(a), e.providers = A.a, e.BigNumber = M.a, e.defaultBlock = !1, e.defaultPrivateKey = !1, e.defaultAddress = {
                                        hex: !1,
                                        base58: !1
                                    }, ["sha3", "toHex", "toUtf8", "fromUtf8", "toAscii", "fromAscii", "toDecimal", "fromDecimal", "toSun", "fromSun", "toBigNumber", "isAddress", "createAccount", "address", "version"].forEach(function (t) {
                                        e[t] = r[t]
                                    }), "object" === u()(o) && (o.fullNode || o.fullHost) ? e.sidechain = new B.a(o, r, m()(e), s) : s = s || o, s && e.setPrivateKey(s), e.fullnodeVersion = F, e.feeLimit = U, e.injectPromise = T()(m()(e)), c && e.setFullNodeHeader(c), d && e.setEventHeader(d), e
                                }

                                var n, a, s;
                                return w()(r, t), h()(r, [{
                                    key: "getFullnodeVersion",
                                    value: (s = o()(i.a.mark(function e() {
                                        var t;
                                        return i.a.wrap(function (e) {
                                            for (; ;) switch (e.prev = e.next) {
                                                case 0:
                                                    return e.prev = 0, e.next = 3, this.trx.getNodeInfo();
                                                case 3:
                                                    t = e.sent, this.fullnodeVersion = t.configNodeInfo.codeVersion, 2 === this.fullnodeVersion.split(".").length && (this.fullnodeVersion += ".0"), e.next = 11;
                                                    break;
                                                case 8:
                                                    e.prev = 8, e.t0 = e.catch(0), this.fullnodeVersion = F;
                                                case 11:
                                                case"end":
                                                    return e.stop()
                                            }
                                        }, e, this, [[0, 8]])
                                    })), function () {
                                        return s.apply(this, arguments)
                                    })
                                }, {
                                    key: "setDefaultBlock", value: function () {
                                        var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0];
                                        if ([!1, "latest", "earliest", 0].includes(e)) return this.defaultBlock = e;
                                        if (!k.a.isInteger(e) || !e) throw new Error("Invalid block ID provided");
                                        this.defaultBlock = Math.abs(e)
                                    }
                                }, {
                                    key: "setPrivateKey", value: function (e) {
                                        try {
                                            this.setAddress(this.address.fromPrivateKey(e))
                                        } catch (e) {
                                            throw new Error("Invalid private key provided")
                                        }
                                        this.defaultPrivateKey = e, this.emit("privateKeyChanged", e)
                                    }
                                }, {
                                    key: "setAddress", value: function (e) {
                                        if (!this.isAddress(e)) throw new Error("Invalid address provided");
                                        var t = this.address.toHex(e), r = this.address.fromHex(e);
                                        this.defaultPrivateKey && this.address.fromPrivateKey(this.defaultPrivateKey) !== r && (this.defaultPrivateKey = !1), this.defaultAddress = {
                                            hex: t,
                                            base58: r
                                        }, this.emit("addressChanged", {hex: t, base58: r})
                                    }
                                }, {
                                    key: "isValidProvider", value: function (e) {
                                        return Object.values(A.a).some(function (t) {
                                            return e instanceof t
                                        })
                                    }
                                }, {
                                    key: "setFullNode", value: function (e) {
                                        if (k.a.isString(e) && (e = new A.a.HttpProvider(e)), !this.isValidProvider(e)) throw new Error("Invalid full node provided");
                                        this.fullNode = e, this.fullNode.setStatusPage("wallet/getnowblock"), this.getFullnodeVersion()
                                    }
                                }, {
                                    key: "setSolidityNode", value: function (e) {
                                        if (k.a.isString(e) && (e = new A.a.HttpProvider(e)), !this.isValidProvider(e)) throw new Error("Invalid solidity node provided");
                                        this.solidityNode = e, this.solidityNode.setStatusPage("walletsolidity/getnowblock")
                                    }
                                }, {
                                    key: "setEventServer", value: function () {
                                        var e;
                                        (e = this.event).setServer.apply(e, arguments)
                                    }
                                }, {
                                    key: "setHeader", value: function () {
                                        var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : {},
                                            t = new A.a.HttpProvider(this.fullNode.host, 3e4, !1, !1, e),
                                            r = new A.a.HttpProvider(this.solidityNode.host, 3e4, !1, !1, e),
                                            n = new A.a.HttpProvider(this.eventServer.host, 3e4, !1, !1, e);
                                        this.setFullNode(t), this.setSolidityNode(r), this.setEventServer(n)
                                    }
                                }, {
                                    key: "setFullNodeHeader", value: function () {
                                        var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : {},
                                            t = new A.a.HttpProvider(this.fullNode.host, 3e4, !1, !1, e),
                                            r = new A.a.HttpProvider(this.solidityNode.host, 3e4, !1, !1, e);
                                        this.setFullNode(t), this.setSolidityNode(r)
                                    }
                                }, {
                                    key: "setEventHeader", value: function () {
                                        var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : {},
                                            t = new A.a.HttpProvider(this.eventServer.host, 3e4, !1, !1, e);
                                        this.setEventServer(t)
                                    }
                                }, {
                                    key: "currentProviders", value: function () {
                                        return {
                                            fullNode: this.fullNode,
                                            solidityNode: this.solidityNode,
                                            eventServer: this.eventServer
                                        }
                                    }
                                }, {
                                    key: "currentProvider", value: function () {
                                        return this.currentProviders()
                                    }
                                }, {
                                    key: "getEventResult", value: function () {
                                        for (var e, t = arguments.length, r = new Array(t), n = 0; n < t; n++) r[n] = arguments[n];
                                        return "object" !== u()(r[1]) && (r[1] = {
                                            sinceTimestamp: r[1] || 0,
                                            eventName: r[2] || !1,
                                            blockNumber: r[3] || !1,
                                            size: r[4] || 20,
                                            page: r[5] || 1
                                        }, r.splice(2, 4), k.a.isFunction(r[2]) || (k.a.isFunction(r[1].page) ? (r[2] = r[1].page, r[1].page = 1) : k.a.isFunction(r[1].size) && (r[2] = r[1].size, r[1].size = 20, r[1].page = 1))), (e = this.event).getEventsByContractAddress.apply(e, r)
                                    }
                                }, {
                                    key: "getEventByTransactionID", value: function () {
                                        var e;
                                        return (e = this.event).getEventsByTransactionID.apply(e, arguments)
                                    }
                                }, {
                                    key: "contract", value: function () {
                                        var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : [],
                                            t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1];
                                        return new j.a(this, e, t)
                                    }
                                }, {
                                    key: "isConnected", value: (a = o()(i.a.mark(function e() {
                                        var t, r = arguments;
                                        return i.a.wrap(function (e) {
                                            for (; ;) switch (e.prev = e.next) {
                                                case 0:
                                                    if (t = r.length > 0 && void 0 !== r[0] && r[0]) {
                                                        e.next = 3;
                                                        break
                                                    }
                                                    return e.abrupt("return", this.injectPromise(this.isConnected));
                                                case 3:
                                                    return e.t0 = t, e.next = 6, this.fullNode.isConnected();
                                                case 6:
                                                    return e.t1 = e.sent, e.next = 9, this.solidityNode.isConnected();
                                                case 9:
                                                    if (e.t2 = e.sent, e.t3 = this.eventServer, !e.t3) {
                                                        e.next = 15;
                                                        break
                                                    }
                                                    return e.next = 14, this.eventServer.isConnected();
                                                case 14:
                                                    e.t3 = e.sent;
                                                case 15:
                                                    return e.t4 = e.t3, e.t5 = {
                                                        fullNode: e.t1,
                                                        solidityNode: e.t2,
                                                        eventServer: e.t4
                                                    }, e.abrupt("return", (0, e.t0)(null, e.t5));
                                                case 18:
                                                case"end":
                                                    return e.stop()
                                            }
                                        }, e, this)
                                    })), function () {
                                        return a.apply(this, arguments)
                                    })
                                }], [{
                                    key: "sha3", value: function (t) {
                                        return (!(arguments.length > 1 && void 0 !== arguments[1]) || arguments[1] ? "0x" : "") + Object(L.keccak256)(e.from(t, "utf-8")).toString().substring(2)
                                    }
                                }, {
                                    key: "toHex", value: function (e) {
                                        if (k.a.isBoolean(e)) return r.fromDecimal(+e);
                                        if (k.a.isBigNumber(e)) return r.fromDecimal(e);
                                        if ("object" === u()(e)) return r.fromUtf8(JSON.stringify(e));
                                        if (k.a.isString(e)) {
                                            if (/^(-|)0x/.test(e)) return e;
                                            if (!isFinite(e) || /^\s*$/.test(e)) return r.fromUtf8(e)
                                        }
                                        var t = r.fromDecimal(e);
                                        if ("0xNaN" === t) throw new Error("The passed value is not convertible to a hex string");
                                        return t
                                    }
                                }, {
                                    key: "toUtf8", value: function (t) {
                                        if (k.a.isHex(t)) return t = t.replace(/^0x/, ""), e.from(t, "hex").toString("utf8");
                                        throw new Error("The passed value is not a valid hex string")
                                    }
                                }, {
                                    key: "fromUtf8", value: function (t) {
                                        if (!k.a.isString(t)) throw new Error("The passed value is not a valid utf-8 string");
                                        return "0x" + e.from(t, "utf8").toString("hex")
                                    }
                                }, {
                                    key: "toAscii", value: function (e) {
                                        if (k.a.isHex(e)) {
                                            var t = "", r = 0, n = e.length;
                                            for ("0x" === e.substring(0, 2) && (r = 2); r < n; r += 2) {
                                                var i = parseInt(e.substr(r, 2), 16);
                                                t += String.fromCharCode(i)
                                            }
                                            return t
                                        }
                                        throw new Error("The passed value is not a valid hex string")
                                    }
                                }, {
                                    key: "fromAscii", value: function (t, r) {
                                        if (!k.a.isString(t)) throw new Error("The passed value is not a valid utf-8 string");
                                        return "0x" + e.from(t, "ascii").toString("hex").padEnd(r, "0")
                                    }
                                }, {
                                    key: "toDecimal", value: function (e) {
                                        return r.toBigNumber(e).toNumber()
                                    }
                                }, {
                                    key: "fromDecimal", value: function (e) {
                                        var t = r.toBigNumber(e), n = t.toString(16);
                                        return t.isLessThan(0) ? "-0x" + n.substr(1) : "0x" + n
                                    }
                                }, {
                                    key: "fromSun", value: function (e) {
                                        var t = r.toBigNumber(e).div(1e6);
                                        return k.a.isBigNumber(e) ? t : t.toString(10)
                                    }
                                }, {
                                    key: "toSun", value: function (e) {
                                        var t = r.toBigNumber(e).times(1e6);
                                        return k.a.isBigNumber(e) ? t : t.toString(10)
                                    }
                                }, {
                                    key: "toBigNumber", value: function () {
                                        var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : 0;
                                        return k.a.isBigNumber(e) ? e : k.a.isString(e) && /^(-|)0x/.test(e) ? new M.a(e.replace("0x", ""), 16) : new M.a(e.toString(10), 10)
                                    }
                                }, {
                                    key: "isAddress", value: function () {
                                        var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0];
                                        if (!k.a.isString(e)) return !1;
                                        if (42 === e.length) try {
                                            return r.isAddress(k.a.crypto.getBase58CheckAddress(k.a.code.hexStr2byteArray(e)))
                                        } catch (e) {
                                            return !1
                                        }
                                        try {
                                            return k.a.crypto.isAddressValid(e)
                                        } catch (e) {
                                            return !1
                                        }
                                    }
                                }, {
                                    key: "createAccount", value: (n = o()(i.a.mark(function e() {
                                        var t;
                                        return i.a.wrap(function (e) {
                                            for (; ;) switch (e.prev = e.next) {
                                                case 0:
                                                    return t = k.a.accounts.generateAccount(), e.abrupt("return", t);
                                                case 2:
                                                case"end":
                                                    return e.stop()
                                            }
                                        }, e)
                                    })), function () {
                                        return n.apply(this, arguments)
                                    })
                                }, {
                                    key: "address", get: function () {
                                        return {
                                            fromHex: function (e) {
                                                return k.a.isHex(e) ? k.a.crypto.getBase58CheckAddress(k.a.code.hexStr2byteArray(e.replace(/^0x/, W.a))) : e
                                            }, toHex: function (e) {
                                                return k.a.isHex(e) ? e.toLowerCase().replace(/^0x/, W.a) : k.a.code.byteArray2hexStr(k.a.crypto.decodeBase58Address(e)).toLowerCase()
                                            }, fromPrivateKey: function (e) {
                                                try {
                                                    return ""
                                                } catch (e) {
                                                    return !1
                                                }
                                            }
                                        }
                                    }
                                }]), r
                            }(E.a);
                        _()(D, "providers", A.a), _()(D, "BigNumber", M.a), _()(D, "TransactionBuilder", O.a), _()(D, "Trx", R.a), _()(D, "Contract", j.a), _()(D, "Event", C.a), _()(D, "version", N.a), _()(D, "utils", k.a)
                    }.call(this, r(74).Buffer)
                }, function (e, t) {
                    e.exports = function (e, t, r) {
                        return t in e ? Object.defineProperty(e, t, {
                            value: r,
                            enumerable: !0,
                            configurable: !0,
                            writable: !0
                        }) : e[t] = r, e
                    }
                }, function (e, t, r) {
                    var n = r(1), i = r.n(n), a = r(2), o = r.n(a), s = r(4), u = r.n(s), c = r(5), f = r.n(c),
                        d = r(68), h = r.n(d), l = r(0), p = function () {
                            function e(t) {
                                var r = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : 3e4,
                                    n = arguments.length > 2 && void 0 !== arguments[2] && arguments[2],
                                    i = arguments.length > 3 && void 0 !== arguments[3] && arguments[3],
                                    a = arguments.length > 4 && void 0 !== arguments[4] ? arguments[4] : {},
                                    o = arguments.length > 5 && void 0 !== arguments[5] ? arguments[5] : "/";
                                if (u()(this, e), !l.a.isValidURL(t)) throw new Error("Invalid URL provided to HttpProvider");
                                if (isNaN(r) || r < 0) throw new Error("Invalid timeout duration provided");
                                if (!l.a.isObject(a)) throw new Error("Invalid headers object provided");
                                t = t.replace(/\/+$/, ""), this.host = t, this.timeout = r, this.user = n, this.password = i, this.headers = a, this.statusPage = o, this.instance = h.a.create({
                                    baseURL: t,
                                    timeout: r,
                                    headers: a,
                                    auth: n && {user: n, password: i}
                                })
                            }

                            var t;
                            return f()(e, [{
                                key: "setStatusPage", value: function () {
                                    var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : "/";
                                    this.statusPage = e
                                }
                            }, {
                                key: "isConnected", value: (t = o()(i.a.mark(function e() {
                                    var t, r = arguments;
                                    return i.a.wrap(function (e) {
                                        for (; ;) switch (e.prev = e.next) {
                                            case 0:
                                                return t = r.length > 0 && void 0 !== r[0] ? r[0] : this.statusPage, e.abrupt("return", this.request(t).then(function (e) {
                                                    return l.a.hasProperties(e, "blockID", "block_header")
                                                }).catch(function () {
                                                    return !1
                                                }));
                                            case 2:
                                            case"end":
                                                return e.stop()
                                        }
                                    }, e, this)
                                })), function () {
                                    return t.apply(this, arguments)
                                })
                            }, {
                                key: "request", value: function (e) {
                                    var t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : {},
                                        r = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : "get";
                                    return r = r.toLowerCase(), this.instance.request({
                                        data: "post" == r && Object.keys(t).length ? t : null,
                                        params: "get" == r && t,
                                        url: e,
                                        method: r
                                    }).then(function (e) {
                                        return e.data
                                    })
                                }
                            }]), e
                        }();
                    t.a = {HttpProvider: p}
                }, function (e, t, r) {
                    r.r(t), r.d(t, "SigningKey", function () {
                        return u
                    });
                    var n = r(25);
                    r.d(t, "keccak256", function () {
                        return n.keccak256
                    });
                    var i = r(49);
                    r.d(t, "sha256", function () {
                        return i.sha256
                    });
                    var a = r(53);
                    r.d(t, "AbiCoder", function () {
                        return a.AbiCoder
                    });
                    var o = r(57);
                    r.d(t, "recoverAddress", function () {
                        return o.recoverAddress
                    });
                    var s = r(29);
                    r.d(t, "toUtf8Bytes", function () {
                        return s.toUtf8Bytes
                    });
                    var u = ""
                }, function (e, t) {
                    e.exports = function (e) {
                        return function (t) {
                            for (var r = arguments.length, n = new Array(r > 1 ? r - 1 : 0), i = 1; i < r; i++) n[i - 1] = arguments[i];
                            return function (e) {
                                for (var t = arguments.length, r = new Array(t > 1 ? t - 1 : 0), n = 1; n < t; n++) r[n - 1] = arguments[n];
                                return new Promise(function (t, n) {
                                    e.apply(void 0, r.concat([function (e, r) {
                                        e ? n(e) : t(r)
                                    }]))
                                })
                            }.apply(void 0, [t.bind(e)].concat(n))
                        }
                    }
                }, function (e, t, r) {
                    var i = r(40), a = Object.prototype.toString;

                    function o(e) {
                        return "[object Array]" === a.call(e)
                    }

                    function s(e) {
                        return void 0 === e
                    }

                    function u(e) {
                        return null !== e && "object" == n()(e)
                    }

                    function c(e) {
                        if ("[object Object]" !== a.call(e)) return !1;
                        var t = Object.getPrototypeOf(e);
                        return null === t || t === Object.prototype
                    }

                    function f(e) {
                        return "[object Function]" === a.call(e)
                    }

                    function d(e, t) {
                        if (null != e) if ("object" != n()(e) && (e = [e]), o(e)) for (var r = 0, i = e.length; r < i; r++) t.call(null, e[r], r, e); else for (var a in e) Object.prototype.hasOwnProperty.call(e, a) && t.call(null, e[a], a, e)
                    }

                    e.exports = {
                        isArray: o, isArrayBuffer: function (e) {
                            return "[object ArrayBuffer]" === a.call(e)
                        }, isBuffer: function (e) {
                            return null !== e && !s(e) && null !== e.constructor && !s(e.constructor) && "function" == typeof e.constructor.isBuffer && e.constructor.isBuffer(e)
                        }, isFormData: function (e) {
                            return "undefined" != typeof FormData && e instanceof FormData
                        }, isArrayBufferView: function (e) {
                            return "undefined" != typeof ArrayBuffer && ArrayBuffer.isView ? ArrayBuffer.isView(e) : e && e.buffer && e.buffer instanceof ArrayBuffer
                        }, isString: function (e) {
                            return "string" == typeof e
                        }, isNumber: function (e) {
                            return "number" == typeof e
                        }, isObject: u, isPlainObject: c, isUndefined: s, isDate: function (e) {
                            return "[object Date]" === a.call(e)
                        }, isFile: function (e) {
                            return "[object File]" === a.call(e)
                        }, isBlob: function (e) {
                            return "[object Blob]" === a.call(e)
                        }, isFunction: f, isStream: function (e) {
                            return u(e) && f(e.pipe)
                        }, isURLSearchParams: function (e) {
                            return "undefined" != typeof URLSearchParams && e instanceof URLSearchParams
                        }, isStandardBrowserEnv: function () {
                            return ("undefined" == typeof navigator || "ReactNative" !== navigator.product && "NativeScript" !== navigator.product && "NS" !== navigator.product) && "undefined" != typeof window && "undefined" != typeof document
                        }, forEach: d, merge: function e() {
                            var t = {};

                            function r(r, n) {
                                c(t[n]) && c(r) ? t[n] = e(t[n], r) : c(r) ? t[n] = e({}, r) : o(r) ? t[n] = r.slice() : t[n] = r
                            }

                            for (var n = 0, i = arguments.length; n < i; n++) d(arguments[n], r);
                            return t
                        }, extend: function (e, t, r) {
                            return d(t, function (t, n) {
                                e[n] = r && "function" == typeof t ? i(t, r) : t
                            }), e
                        }, trim: function (e) {
                            return e.replace(/^\s*/, "").replace(/\s*$/, "")
                        }, stripBOM: function (e) {
                            return 65279 === e.charCodeAt(0) && (e = e.slice(1)), e
                        }
                    }
                }, function (e, t) {
                    function r(e, t) {
                        if (!e) throw new Error(t || "Assertion failed")
                    }

                    e.exports = r, r.equal = function (e, t, r) {
                        if (e != t) throw new Error(r || "Assertion failed: " + e + " != " + t)
                    }
                }, function (e, t, r) {
                    var n = t, i = r(14), a = r(12), o = r(58);
                    n.assert = a, n.toArray = o.toArray, n.zero2 = o.zero2, n.toHex = o.toHex, n.encode = o.encode, n.getNAF = function (e, t, r) {
                        var n = new Array(Math.max(e.bitLength(), r) + 1);
                        n.fill(0);
                        for (var i = 1 << t + 1, a = e.clone(), o = 0; o < n.length; o++) {
                            var s, u = a.andln(i - 1);
                            a.isOdd() ? (s = u > (i >> 1) - 1 ? (i >> 1) - u : u, a.isubn(s)) : s = 0, n[o] = s, a.iushrn(1)
                        }
                        return n
                    }, n.getJSF = function (e, t) {
                        var r = [[], []];
                        e = e.clone(), t = t.clone();
                        for (var n = 0, i = 0; e.cmpn(-n) > 0 || t.cmpn(-i) > 0;) {
                            var a, o, s, u = e.andln(3) + n & 3, c = t.andln(3) + i & 3;
                            3 === u && (u = -1), 3 === c && (c = -1), a = 0 == (1 & u) ? 0 : 3 != (s = e.andln(7) + n & 7) && 5 !== s || 2 !== c ? u : -u, r[0].push(a), o = 0 == (1 & c) ? 0 : 3 != (s = t.andln(7) + i & 7) && 5 !== s || 2 !== u ? c : -c, r[1].push(o), 2 * n === a + 1 && (n = 1 - n), 2 * i === o + 1 && (i = 1 - i), e.iushrn(1), t.iushrn(1)
                        }
                        return r
                    }, n.cachedProperty = function (e, t, r) {
                        var n = "_" + t;
                        e.prototype[t] = function () {
                            return void 0 !== this[n] ? this[n] : this[n] = r.call(this)
                        }
                    }, n.parseBytes = function (e) {
                        return "string" == typeof e ? n.toArray(e, "hex") : e
                    }, n.intFromLE = function (e) {
                        return new i(e, "hex", "le")
                    }
                }, function (e, t, r) {
                    (function (e) {
                        !function (e, t) {
                            function i(e, t) {
                                if (!e) throw new Error(t || "Assertion failed")
                            }

                            function a(e, t) {
                                e.super_ = t;
                                var r = function () {
                                };
                                r.prototype = t.prototype, e.prototype = new r, e.prototype.constructor = e
                            }

                            function o(e, t, r) {
                                if (o.isBN(e)) return e;
                                this.negative = 0, this.words = null, this.length = 0, this.red = null, null !== e && ("le" !== t && "be" !== t || (r = t, t = 10), this._init(e || 0, t || 10, r || "be"))
                            }

                            var s;
                            "object" == n()(e) ? e.exports = o : t.BN = o, o.BN = o, o.wordSize = 26;
                            try {
                                s = r(110).Buffer
                            } catch (e) {
                            }

                            function u(e, t, r) {
                                for (var n = 0, i = Math.min(e.length, r), a = t; a < i; a++) {
                                    var o = e.charCodeAt(a) - 48;
                                    n <<= 4, n |= o >= 49 && o <= 54 ? o - 49 + 10 : o >= 17 && o <= 22 ? o - 17 + 10 : 15 & o
                                }
                                return n
                            }

                            function c(e, t, r, n) {
                                for (var i = 0, a = Math.min(e.length, r), o = t; o < a; o++) {
                                    var s = e.charCodeAt(o) - 48;
                                    i *= n, i += s >= 49 ? s - 49 + 10 : s >= 17 ? s - 17 + 10 : s
                                }
                                return i
                            }

                            o.isBN = function (e) {
                                return e instanceof o || null !== e && "object" == n()(e) && e.constructor.wordSize === o.wordSize && Array.isArray(e.words)
                            }, o.max = function (e, t) {
                                return e.cmp(t) > 0 ? e : t
                            }, o.min = function (e, t) {
                                return e.cmp(t) < 0 ? e : t
                            }, o.prototype._init = function (e, t, r) {
                                if ("number" == typeof e) return this._initNumber(e, t, r);
                                if ("object" == n()(e)) return this._initArray(e, t, r);
                                "hex" === t && (t = 16), i(t === (0 | t) && t >= 2 && t <= 36);
                                var a = 0;
                                "-" === (e = e.toString().replace(/\s+/g, ""))[0] && a++, 16 === t ? this._parseHex(e, a) : this._parseBase(e, t, a), "-" === e[0] && (this.negative = 1), this.strip(), "le" === r && this._initArray(this.toArray(), t, r)
                            }, o.prototype._initNumber = function (e, t, r) {
                                e < 0 && (this.negative = 1, e = -e), e < 67108864 ? (this.words = [67108863 & e], this.length = 1) : e < 4503599627370496 ? (this.words = [67108863 & e, e / 67108864 & 67108863], this.length = 2) : (i(e < 9007199254740992), this.words = [67108863 & e, e / 67108864 & 67108863, 1], this.length = 3), "le" === r && this._initArray(this.toArray(), t, r)
                            }, o.prototype._initArray = function (e, t, r) {
                                if (i("number" == typeof e.length), e.length <= 0) return this.words = [0], this.length = 1, this;
                                this.length = Math.ceil(e.length / 3), this.words = new Array(this.length);
                                for (var n = 0; n < this.length; n++) this.words[n] = 0;
                                var a, o, s = 0;
                                if ("be" === r) for (n = e.length - 1, a = 0; n >= 0; n -= 3) o = e[n] | e[n - 1] << 8 | e[n - 2] << 16, this.words[a] |= o << s & 67108863, this.words[a + 1] = o >>> 26 - s & 67108863, (s += 24) >= 26 && (s -= 26, a++); else if ("le" === r) for (n = 0, a = 0; n < e.length; n += 3) o = e[n] | e[n + 1] << 8 | e[n + 2] << 16, this.words[a] |= o << s & 67108863, this.words[a + 1] = o >>> 26 - s & 67108863, (s += 24) >= 26 && (s -= 26, a++);
                                return this.strip()
                            }, o.prototype._parseHex = function (e, t) {
                                this.length = Math.ceil((e.length - t) / 6), this.words = new Array(this.length);
                                for (var r = 0; r < this.length; r++) this.words[r] = 0;
                                var n, i, a = 0;
                                for (r = e.length - 6, n = 0; r >= t; r -= 6) i = u(e, r, r + 6), this.words[n] |= i << a & 67108863, this.words[n + 1] |= i >>> 26 - a & 4194303, (a += 24) >= 26 && (a -= 26, n++);
                                r + 6 !== t && (i = u(e, t, r + 6), this.words[n] |= i << a & 67108863, this.words[n + 1] |= i >>> 26 - a & 4194303), this.strip()
                            }, o.prototype._parseBase = function (e, t, r) {
                                this.words = [0], this.length = 1;
                                for (var n = 0, i = 1; i <= 67108863; i *= t) n++;
                                n--, i = i / t | 0;
                                for (var a = e.length - r, o = a % n, s = Math.min(a, a - o) + r, u = 0, f = r; f < s; f += n) u = c(e, f, f + n, t), this.imuln(i), this.words[0] + u < 67108864 ? this.words[0] += u : this._iaddn(u);
                                if (0 !== o) {
                                    var d = 1;
                                    for (u = c(e, f, e.length, t), f = 0; f < o; f++) d *= t;
                                    this.imuln(d), this.words[0] + u < 67108864 ? this.words[0] += u : this._iaddn(u)
                                }
                            }, o.prototype.copy = function (e) {
                                e.words = new Array(this.length);
                                for (var t = 0; t < this.length; t++) e.words[t] = this.words[t];
                                e.length = this.length, e.negative = this.negative, e.red = this.red
                            }, o.prototype.clone = function () {
                                var e = new o(null);
                                return this.copy(e), e
                            }, o.prototype._expand = function (e) {
                                for (; this.length < e;) this.words[this.length++] = 0;
                                return this
                            }, o.prototype.strip = function () {
                                for (; this.length > 1 && 0 === this.words[this.length - 1];) this.length--;
                                return this._normSign()
                            }, o.prototype._normSign = function () {
                                return 1 === this.length && 0 === this.words[0] && (this.negative = 0), this
                            }, o.prototype.inspect = function () {
                                return (this.red ? "<BN-R: " : "<BN: ") + this.toString(16) + ">"
                            };
                            var f = ["", "0", "00", "000", "0000", "00000", "000000", "0000000", "00000000", "000000000", "0000000000", "00000000000", "000000000000", "0000000000000", "00000000000000", "000000000000000", "0000000000000000", "00000000000000000", "000000000000000000", "0000000000000000000", "00000000000000000000", "000000000000000000000", "0000000000000000000000", "00000000000000000000000", "000000000000000000000000", "0000000000000000000000000"],
                                d = [0, 0, 25, 16, 12, 11, 10, 9, 8, 8, 7, 7, 7, 7, 6, 6, 6, 6, 6, 6, 6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5],
                                h = [0, 0, 33554432, 43046721, 16777216, 48828125, 60466176, 40353607, 16777216, 43046721, 1e7, 19487171, 35831808, 62748517, 7529536, 11390625, 16777216, 24137569, 34012224, 47045881, 64e6, 4084101, 5153632, 6436343, 7962624, 9765625, 11881376, 14348907, 17210368, 20511149, 243e5, 28629151, 33554432, 39135393, 45435424, 52521875, 60466176];

                            function l(e, t, r) {
                                r.negative = t.negative ^ e.negative;
                                var n = e.length + t.length | 0;
                                r.length = n, n = n - 1 | 0;
                                var i = 0 | e.words[0], a = 0 | t.words[0], o = i * a, s = 67108863 & o,
                                    u = o / 67108864 | 0;
                                r.words[0] = s;
                                for (var c = 1; c < n; c++) {
                                    for (var f = u >>> 26, d = 67108863 & u, h = Math.min(c, t.length - 1), l = Math.max(0, c - e.length + 1); l <= h; l++) {
                                        var p = c - l | 0;
                                        f += (o = (i = 0 | e.words[p]) * (a = 0 | t.words[l]) + d) / 67108864 | 0, d = 67108863 & o
                                    }
                                    r.words[c] = 0 | d, u = 0 | f
                                }
                                return 0 !== u ? r.words[c] = 0 | u : r.length--, r.strip()
                            }

                            o.prototype.toString = function (e, t) {
                                var r;
                                if (t = 0 | t || 1, 16 === (e = e || 10) || "hex" === e) {
                                    r = "";
                                    for (var n = 0, a = 0, o = 0; o < this.length; o++) {
                                        var s = this.words[o], u = (16777215 & (s << n | a)).toString(16);
                                        r = 0 != (a = s >>> 24 - n & 16777215) || o !== this.length - 1 ? f[6 - u.length] + u + r : u + r, (n += 2) >= 26 && (n -= 26, o--)
                                    }
                                    for (0 !== a && (r = a.toString(16) + r); r.length % t != 0;) r = "0" + r;
                                    return 0 !== this.negative && (r = "-" + r), r
                                }
                                if (e === (0 | e) && e >= 2 && e <= 36) {
                                    var c = d[e], l = h[e];
                                    r = "";
                                    var p = this.clone();
                                    for (p.negative = 0; !p.isZero();) {
                                        var b = p.modn(l).toString(e);
                                        r = (p = p.idivn(l)).isZero() ? b + r : f[c - b.length] + b + r
                                    }
                                    for (this.isZero() && (r = "0" + r); r.length % t != 0;) r = "0" + r;
                                    return 0 !== this.negative && (r = "-" + r), r
                                }
                                i(!1, "Base should be between 2 and 36")
                            }, o.prototype.toNumber = function () {
                                var e = this.words[0];
                                return 2 === this.length ? e += 67108864 * this.words[1] : 3 === this.length && 1 === this.words[2] ? e += 4503599627370496 + 67108864 * this.words[1] : this.length > 2 && i(!1, "Number can only safely store up to 53 bits"), 0 !== this.negative ? -e : e
                            }, o.prototype.toJSON = function () {
                                return this.toString(16)
                            }, o.prototype.toBuffer = function (e, t) {
                                return i(void 0 !== s), this.toArrayLike(s, e, t)
                            }, o.prototype.toArray = function (e, t) {
                                return this.toArrayLike(Array, e, t)
                            }, o.prototype.toArrayLike = function (e, t, r) {
                                var n = this.byteLength(), a = r || Math.max(1, n);
                                i(n <= a, "byte array longer than desired length"), i(a > 0, "Requested array length <= 0"), this.strip();
                                var o, s, u = "le" === t, c = new e(a), f = this.clone();
                                if (u) {
                                    for (s = 0; !f.isZero(); s++) o = f.andln(255), f.iushrn(8), c[s] = o;
                                    for (; s < a; s++) c[s] = 0
                                } else {
                                    for (s = 0; s < a - n; s++) c[s] = 0;
                                    for (s = 0; !f.isZero(); s++) o = f.andln(255), f.iushrn(8), c[a - s - 1] = o
                                }
                                return c
                            }, Math.clz32 ? o.prototype._countBits = function (e) {
                                return 32 - Math.clz32(e)
                            } : o.prototype._countBits = function (e) {
                                var t = e, r = 0;
                                return t >= 4096 && (r += 13, t >>>= 13), t >= 64 && (r += 7, t >>>= 7), t >= 8 && (r += 4, t >>>= 4), t >= 2 && (r += 2, t >>>= 2), r + t
                            }, o.prototype._zeroBits = function (e) {
                                if (0 === e) return 26;
                                var t = e, r = 0;
                                return 0 == (8191 & t) && (r += 13, t >>>= 13), 0 == (127 & t) && (r += 7, t >>>= 7), 0 == (15 & t) && (r += 4, t >>>= 4), 0 == (3 & t) && (r += 2, t >>>= 2), 0 == (1 & t) && r++, r
                            }, o.prototype.bitLength = function () {
                                var e = this.words[this.length - 1], t = this._countBits(e);
                                return 26 * (this.length - 1) + t
                            }, o.prototype.zeroBits = function () {
                                if (this.isZero()) return 0;
                                for (var e = 0, t = 0; t < this.length; t++) {
                                    var r = this._zeroBits(this.words[t]);
                                    if (e += r, 26 !== r) break
                                }
                                return e
                            }, o.prototype.byteLength = function () {
                                return Math.ceil(this.bitLength() / 8)
                            }, o.prototype.toTwos = function (e) {
                                return 0 !== this.negative ? this.abs().inotn(e).iaddn(1) : this.clone()
                            }, o.prototype.fromTwos = function (e) {
                                return this.testn(e - 1) ? this.notn(e).iaddn(1).ineg() : this.clone()
                            }, o.prototype.isNeg = function () {
                                return 0 !== this.negative
                            }, o.prototype.neg = function () {
                                return this.clone().ineg()
                            }, o.prototype.ineg = function () {
                                return this.isZero() || (this.negative ^= 1), this
                            }, o.prototype.iuor = function (e) {
                                for (; this.length < e.length;) this.words[this.length++] = 0;
                                for (var t = 0; t < e.length; t++) this.words[t] = this.words[t] | e.words[t];
                                return this.strip()
                            }, o.prototype.ior = function (e) {
                                return i(0 == (this.negative | e.negative)), this.iuor(e)
                            }, o.prototype.or = function (e) {
                                return this.length > e.length ? this.clone().ior(e) : e.clone().ior(this)
                            }, o.prototype.uor = function (e) {
                                return this.length > e.length ? this.clone().iuor(e) : e.clone().iuor(this)
                            }, o.prototype.iuand = function (e) {
                                var t;
                                t = this.length > e.length ? e : this;
                                for (var r = 0; r < t.length; r++) this.words[r] = this.words[r] & e.words[r];
                                return this.length = t.length, this.strip()
                            }, o.prototype.iand = function (e) {
                                return i(0 == (this.negative | e.negative)), this.iuand(e)
                            }, o.prototype.and = function (e) {
                                return this.length > e.length ? this.clone().iand(e) : e.clone().iand(this)
                            }, o.prototype.uand = function (e) {
                                return this.length > e.length ? this.clone().iuand(e) : e.clone().iuand(this)
                            }, o.prototype.iuxor = function (e) {
                                var t, r;
                                this.length > e.length ? (t = this, r = e) : (t = e, r = this);
                                for (var n = 0; n < r.length; n++) this.words[n] = t.words[n] ^ r.words[n];
                                if (this !== t) for (; n < t.length; n++) this.words[n] = t.words[n];
                                return this.length = t.length, this.strip()
                            }, o.prototype.ixor = function (e) {
                                return i(0 == (this.negative | e.negative)), this.iuxor(e)
                            }, o.prototype.xor = function (e) {
                                return this.length > e.length ? this.clone().ixor(e) : e.clone().ixor(this)
                            }, o.prototype.uxor = function (e) {
                                return this.length > e.length ? this.clone().iuxor(e) : e.clone().iuxor(this)
                            }, o.prototype.inotn = function (e) {
                                i("number" == typeof e && e >= 0);
                                var t = 0 | Math.ceil(e / 26), r = e % 26;
                                this._expand(t), r > 0 && t--;
                                for (var n = 0; n < t; n++) this.words[n] = 67108863 & ~this.words[n];
                                return r > 0 && (this.words[n] = ~this.words[n] & 67108863 >> 26 - r), this.strip()
                            }, o.prototype.notn = function (e) {
                                return this.clone().inotn(e)
                            }, o.prototype.setn = function (e, t) {
                                i("number" == typeof e && e >= 0);
                                var r = e / 26 | 0, n = e % 26;
                                return this._expand(r + 1), this.words[r] = t ? this.words[r] | 1 << n : this.words[r] & ~(1 << n), this.strip()
                            }, o.prototype.iadd = function (e) {
                                var t, r, n;
                                if (0 !== this.negative && 0 === e.negative) return this.negative = 0, t = this.isub(e), this.negative ^= 1, this._normSign();
                                if (0 === this.negative && 0 !== e.negative) return e.negative = 0, t = this.isub(e), e.negative = 1, t._normSign();
                                this.length > e.length ? (r = this, n = e) : (r = e, n = this);
                                for (var i = 0, a = 0; a < n.length; a++) t = (0 | r.words[a]) + (0 | n.words[a]) + i, this.words[a] = 67108863 & t, i = t >>> 26;
                                for (; 0 !== i && a < r.length; a++) t = (0 | r.words[a]) + i, this.words[a] = 67108863 & t, i = t >>> 26;
                                if (this.length = r.length, 0 !== i) this.words[this.length] = i, this.length++; else if (r !== this) for (; a < r.length; a++) this.words[a] = r.words[a];
                                return this
                            }, o.prototype.add = function (e) {
                                var t;
                                return 0 !== e.negative && 0 === this.negative ? (e.negative = 0, t = this.sub(e), e.negative ^= 1, t) : 0 === e.negative && 0 !== this.negative ? (this.negative = 0, t = e.sub(this), this.negative = 1, t) : this.length > e.length ? this.clone().iadd(e) : e.clone().iadd(this)
                            }, o.prototype.isub = function (e) {
                                if (0 !== e.negative) {
                                    e.negative = 0;
                                    var t = this.iadd(e);
                                    return e.negative = 1, t._normSign()
                                }
                                if (0 !== this.negative) return this.negative = 0, this.iadd(e), this.negative = 1, this._normSign();
                                var r, n, i = this.cmp(e);
                                if (0 === i) return this.negative = 0, this.length = 1, this.words[0] = 0, this;
                                i > 0 ? (r = this, n = e) : (r = e, n = this);
                                for (var a = 0, o = 0; o < n.length; o++) a = (t = (0 | r.words[o]) - (0 | n.words[o]) + a) >> 26, this.words[o] = 67108863 & t;
                                for (; 0 !== a && o < r.length; o++) a = (t = (0 | r.words[o]) + a) >> 26, this.words[o] = 67108863 & t;
                                if (0 === a && o < r.length && r !== this) for (; o < r.length; o++) this.words[o] = r.words[o];
                                return this.length = Math.max(this.length, o), r !== this && (this.negative = 1), this.strip()
                            }, o.prototype.sub = function (e) {
                                return this.clone().isub(e)
                            };
                            var p = function (e, t, r) {
                                var n, i, a, o = e.words, s = t.words, u = r.words, c = 0, f = 0 | o[0], d = 8191 & f,
                                    h = f >>> 13, l = 0 | o[1], p = 8191 & l, b = l >>> 13, v = 0 | o[2], g = 8191 & v,
                                    m = v >>> 13, y = 0 | o[3], w = 8191 & y, x = y >>> 13, _ = 0 | o[4], A = 8191 & _,
                                    k = _ >>> 13, S = 0 | o[5], M = 8191 & S, I = S >>> 13, E = 0 | o[6], N = 8191 & E,
                                    P = E >>> 13, T = 0 | o[7], O = 8191 & T, R = T >>> 13, j = 0 | o[8], C = 8191 & j,
                                    B = j >>> 13, L = 0 | o[9], W = 8191 & L, F = L >>> 13, U = 0 | s[0], D = 8191 & U,
                                    q = U >>> 13, z = 0 | s[1], H = 8191 & z, V = z >>> 13, K = 0 | s[2], G = 8191 & K,
                                    Y = K >>> 13, J = 0 | s[3], X = 8191 & J, Z = J >>> 13, $ = 0 | s[4], Q = 8191 & $,
                                    ee = $ >>> 13, te = 0 | s[5], re = 8191 & te, ne = te >>> 13, ie = 0 | s[6],
                                    ae = 8191 & ie, oe = ie >>> 13, se = 0 | s[7], ue = 8191 & se, ce = se >>> 13,
                                    fe = 0 | s[8], de = 8191 & fe, he = fe >>> 13, le = 0 | s[9], pe = 8191 & le,
                                    be = le >>> 13;
                                r.negative = e.negative ^ t.negative, r.length = 19;
                                var ve = (c + (n = Math.imul(d, D)) | 0) + ((8191 & (i = (i = Math.imul(d, q)) + Math.imul(h, D) | 0)) << 13) | 0;
                                c = ((a = Math.imul(h, q)) + (i >>> 13) | 0) + (ve >>> 26) | 0, ve &= 67108863, n = Math.imul(p, D), i = (i = Math.imul(p, q)) + Math.imul(b, D) | 0, a = Math.imul(b, q);
                                var ge = (c + (n = n + Math.imul(d, H) | 0) | 0) + ((8191 & (i = (i = i + Math.imul(d, V) | 0) + Math.imul(h, H) | 0)) << 13) | 0;
                                c = ((a = a + Math.imul(h, V) | 0) + (i >>> 13) | 0) + (ge >>> 26) | 0, ge &= 67108863, n = Math.imul(g, D), i = (i = Math.imul(g, q)) + Math.imul(m, D) | 0, a = Math.imul(m, q), n = n + Math.imul(p, H) | 0, i = (i = i + Math.imul(p, V) | 0) + Math.imul(b, H) | 0, a = a + Math.imul(b, V) | 0;
                                var me = (c + (n = n + Math.imul(d, G) | 0) | 0) + ((8191 & (i = (i = i + Math.imul(d, Y) | 0) + Math.imul(h, G) | 0)) << 13) | 0;
                                c = ((a = a + Math.imul(h, Y) | 0) + (i >>> 13) | 0) + (me >>> 26) | 0, me &= 67108863, n = Math.imul(w, D), i = (i = Math.imul(w, q)) + Math.imul(x, D) | 0, a = Math.imul(x, q), n = n + Math.imul(g, H) | 0, i = (i = i + Math.imul(g, V) | 0) + Math.imul(m, H) | 0, a = a + Math.imul(m, V) | 0, n = n + Math.imul(p, G) | 0, i = (i = i + Math.imul(p, Y) | 0) + Math.imul(b, G) | 0, a = a + Math.imul(b, Y) | 0;
                                var ye = (c + (n = n + Math.imul(d, X) | 0) | 0) + ((8191 & (i = (i = i + Math.imul(d, Z) | 0) + Math.imul(h, X) | 0)) << 13) | 0;
                                c = ((a = a + Math.imul(h, Z) | 0) + (i >>> 13) | 0) + (ye >>> 26) | 0, ye &= 67108863, n = Math.imul(A, D), i = (i = Math.imul(A, q)) + Math.imul(k, D) | 0, a = Math.imul(k, q), n = n + Math.imul(w, H) | 0, i = (i = i + Math.imul(w, V) | 0) + Math.imul(x, H) | 0, a = a + Math.imul(x, V) | 0, n = n + Math.imul(g, G) | 0, i = (i = i + Math.imul(g, Y) | 0) + Math.imul(m, G) | 0, a = a + Math.imul(m, Y) | 0, n = n + Math.imul(p, X) | 0, i = (i = i + Math.imul(p, Z) | 0) + Math.imul(b, X) | 0, a = a + Math.imul(b, Z) | 0;
                                var we = (c + (n = n + Math.imul(d, Q) | 0) | 0) + ((8191 & (i = (i = i + Math.imul(d, ee) | 0) + Math.imul(h, Q) | 0)) << 13) | 0;
                                c = ((a = a + Math.imul(h, ee) | 0) + (i >>> 13) | 0) + (we >>> 26) | 0, we &= 67108863, n = Math.imul(M, D), i = (i = Math.imul(M, q)) + Math.imul(I, D) | 0, a = Math.imul(I, q), n = n + Math.imul(A, H) | 0, i = (i = i + Math.imul(A, V) | 0) + Math.imul(k, H) | 0, a = a + Math.imul(k, V) | 0, n = n + Math.imul(w, G) | 0, i = (i = i + Math.imul(w, Y) | 0) + Math.imul(x, G) | 0, a = a + Math.imul(x, Y) | 0, n = n + Math.imul(g, X) | 0, i = (i = i + Math.imul(g, Z) | 0) + Math.imul(m, X) | 0, a = a + Math.imul(m, Z) | 0, n = n + Math.imul(p, Q) | 0, i = (i = i + Math.imul(p, ee) | 0) + Math.imul(b, Q) | 0, a = a + Math.imul(b, ee) | 0;
                                var xe = (c + (n = n + Math.imul(d, re) | 0) | 0) + ((8191 & (i = (i = i + Math.imul(d, ne) | 0) + Math.imul(h, re) | 0)) << 13) | 0;
                                c = ((a = a + Math.imul(h, ne) | 0) + (i >>> 13) | 0) + (xe >>> 26) | 0, xe &= 67108863, n = Math.imul(N, D), i = (i = Math.imul(N, q)) + Math.imul(P, D) | 0, a = Math.imul(P, q), n = n + Math.imul(M, H) | 0, i = (i = i + Math.imul(M, V) | 0) + Math.imul(I, H) | 0, a = a + Math.imul(I, V) | 0, n = n + Math.imul(A, G) | 0, i = (i = i + Math.imul(A, Y) | 0) + Math.imul(k, G) | 0, a = a + Math.imul(k, Y) | 0, n = n + Math.imul(w, X) | 0, i = (i = i + Math.imul(w, Z) | 0) + Math.imul(x, X) | 0, a = a + Math.imul(x, Z) | 0, n = n + Math.imul(g, Q) | 0, i = (i = i + Math.imul(g, ee) | 0) + Math.imul(m, Q) | 0, a = a + Math.imul(m, ee) | 0, n = n + Math.imul(p, re) | 0, i = (i = i + Math.imul(p, ne) | 0) + Math.imul(b, re) | 0, a = a + Math.imul(b, ne) | 0;
                                var _e = (c + (n = n + Math.imul(d, ae) | 0) | 0) + ((8191 & (i = (i = i + Math.imul(d, oe) | 0) + Math.imul(h, ae) | 0)) << 13) | 0;
                                c = ((a = a + Math.imul(h, oe) | 0) + (i >>> 13) | 0) + (_e >>> 26) | 0, _e &= 67108863, n = Math.imul(O, D), i = (i = Math.imul(O, q)) + Math.imul(R, D) | 0, a = Math.imul(R, q), n = n + Math.imul(N, H) | 0, i = (i = i + Math.imul(N, V) | 0) + Math.imul(P, H) | 0, a = a + Math.imul(P, V) | 0, n = n + Math.imul(M, G) | 0, i = (i = i + Math.imul(M, Y) | 0) + Math.imul(I, G) | 0, a = a + Math.imul(I, Y) | 0, n = n + Math.imul(A, X) | 0, i = (i = i + Math.imul(A, Z) | 0) + Math.imul(k, X) | 0, a = a + Math.imul(k, Z) | 0, n = n + Math.imul(w, Q) | 0, i = (i = i + Math.imul(w, ee) | 0) + Math.imul(x, Q) | 0, a = a + Math.imul(x, ee) | 0, n = n + Math.imul(g, re) | 0, i = (i = i + Math.imul(g, ne) | 0) + Math.imul(m, re) | 0, a = a + Math.imul(m, ne) | 0, n = n + Math.imul(p, ae) | 0, i = (i = i + Math.imul(p, oe) | 0) + Math.imul(b, ae) | 0, a = a + Math.imul(b, oe) | 0;
                                var Ae = (c + (n = n + Math.imul(d, ue) | 0) | 0) + ((8191 & (i = (i = i + Math.imul(d, ce) | 0) + Math.imul(h, ue) | 0)) << 13) | 0;
                                c = ((a = a + Math.imul(h, ce) | 0) + (i >>> 13) | 0) + (Ae >>> 26) | 0, Ae &= 67108863, n = Math.imul(C, D), i = (i = Math.imul(C, q)) + Math.imul(B, D) | 0, a = Math.imul(B, q), n = n + Math.imul(O, H) | 0, i = (i = i + Math.imul(O, V) | 0) + Math.imul(R, H) | 0, a = a + Math.imul(R, V) | 0, n = n + Math.imul(N, G) | 0, i = (i = i + Math.imul(N, Y) | 0) + Math.imul(P, G) | 0, a = a + Math.imul(P, Y) | 0, n = n + Math.imul(M, X) | 0, i = (i = i + Math.imul(M, Z) | 0) + Math.imul(I, X) | 0, a = a + Math.imul(I, Z) | 0, n = n + Math.imul(A, Q) | 0, i = (i = i + Math.imul(A, ee) | 0) + Math.imul(k, Q) | 0, a = a + Math.imul(k, ee) | 0, n = n + Math.imul(w, re) | 0, i = (i = i + Math.imul(w, ne) | 0) + Math.imul(x, re) | 0, a = a + Math.imul(x, ne) | 0, n = n + Math.imul(g, ae) | 0, i = (i = i + Math.imul(g, oe) | 0) + Math.imul(m, ae) | 0, a = a + Math.imul(m, oe) | 0, n = n + Math.imul(p, ue) | 0, i = (i = i + Math.imul(p, ce) | 0) + Math.imul(b, ue) | 0, a = a + Math.imul(b, ce) | 0;
                                var ke = (c + (n = n + Math.imul(d, de) | 0) | 0) + ((8191 & (i = (i = i + Math.imul(d, he) | 0) + Math.imul(h, de) | 0)) << 13) | 0;
                                c = ((a = a + Math.imul(h, he) | 0) + (i >>> 13) | 0) + (ke >>> 26) | 0, ke &= 67108863, n = Math.imul(W, D), i = (i = Math.imul(W, q)) + Math.imul(F, D) | 0, a = Math.imul(F, q), n = n + Math.imul(C, H) | 0, i = (i = i + Math.imul(C, V) | 0) + Math.imul(B, H) | 0, a = a + Math.imul(B, V) | 0, n = n + Math.imul(O, G) | 0, i = (i = i + Math.imul(O, Y) | 0) + Math.imul(R, G) | 0, a = a + Math.imul(R, Y) | 0, n = n + Math.imul(N, X) | 0, i = (i = i + Math.imul(N, Z) | 0) + Math.imul(P, X) | 0, a = a + Math.imul(P, Z) | 0, n = n + Math.imul(M, Q) | 0, i = (i = i + Math.imul(M, ee) | 0) + Math.imul(I, Q) | 0, a = a + Math.imul(I, ee) | 0, n = n + Math.imul(A, re) | 0, i = (i = i + Math.imul(A, ne) | 0) + Math.imul(k, re) | 0, a = a + Math.imul(k, ne) | 0, n = n + Math.imul(w, ae) | 0, i = (i = i + Math.imul(w, oe) | 0) + Math.imul(x, ae) | 0, a = a + Math.imul(x, oe) | 0, n = n + Math.imul(g, ue) | 0, i = (i = i + Math.imul(g, ce) | 0) + Math.imul(m, ue) | 0, a = a + Math.imul(m, ce) | 0, n = n + Math.imul(p, de) | 0, i = (i = i + Math.imul(p, he) | 0) + Math.imul(b, de) | 0, a = a + Math.imul(b, he) | 0;
                                var Se = (c + (n = n + Math.imul(d, pe) | 0) | 0) + ((8191 & (i = (i = i + Math.imul(d, be) | 0) + Math.imul(h, pe) | 0)) << 13) | 0;
                                c = ((a = a + Math.imul(h, be) | 0) + (i >>> 13) | 0) + (Se >>> 26) | 0, Se &= 67108863, n = Math.imul(W, H), i = (i = Math.imul(W, V)) + Math.imul(F, H) | 0, a = Math.imul(F, V), n = n + Math.imul(C, G) | 0, i = (i = i + Math.imul(C, Y) | 0) + Math.imul(B, G) | 0, a = a + Math.imul(B, Y) | 0, n = n + Math.imul(O, X) | 0, i = (i = i + Math.imul(O, Z) | 0) + Math.imul(R, X) | 0, a = a + Math.imul(R, Z) | 0, n = n + Math.imul(N, Q) | 0, i = (i = i + Math.imul(N, ee) | 0) + Math.imul(P, Q) | 0, a = a + Math.imul(P, ee) | 0, n = n + Math.imul(M, re) | 0, i = (i = i + Math.imul(M, ne) | 0) + Math.imul(I, re) | 0, a = a + Math.imul(I, ne) | 0, n = n + Math.imul(A, ae) | 0, i = (i = i + Math.imul(A, oe) | 0) + Math.imul(k, ae) | 0, a = a + Math.imul(k, oe) | 0, n = n + Math.imul(w, ue) | 0, i = (i = i + Math.imul(w, ce) | 0) + Math.imul(x, ue) | 0, a = a + Math.imul(x, ce) | 0, n = n + Math.imul(g, de) | 0, i = (i = i + Math.imul(g, he) | 0) + Math.imul(m, de) | 0, a = a + Math.imul(m, he) | 0;
                                var Me = (c + (n = n + Math.imul(p, pe) | 0) | 0) + ((8191 & (i = (i = i + Math.imul(p, be) | 0) + Math.imul(b, pe) | 0)) << 13) | 0;
                                c = ((a = a + Math.imul(b, be) | 0) + (i >>> 13) | 0) + (Me >>> 26) | 0, Me &= 67108863, n = Math.imul(W, G), i = (i = Math.imul(W, Y)) + Math.imul(F, G) | 0, a = Math.imul(F, Y), n = n + Math.imul(C, X) | 0, i = (i = i + Math.imul(C, Z) | 0) + Math.imul(B, X) | 0, a = a + Math.imul(B, Z) | 0, n = n + Math.imul(O, Q) | 0, i = (i = i + Math.imul(O, ee) | 0) + Math.imul(R, Q) | 0, a = a + Math.imul(R, ee) | 0, n = n + Math.imul(N, re) | 0, i = (i = i + Math.imul(N, ne) | 0) + Math.imul(P, re) | 0, a = a + Math.imul(P, ne) | 0, n = n + Math.imul(M, ae) | 0, i = (i = i + Math.imul(M, oe) | 0) + Math.imul(I, ae) | 0, a = a + Math.imul(I, oe) | 0, n = n + Math.imul(A, ue) | 0, i = (i = i + Math.imul(A, ce) | 0) + Math.imul(k, ue) | 0, a = a + Math.imul(k, ce) | 0, n = n + Math.imul(w, de) | 0, i = (i = i + Math.imul(w, he) | 0) + Math.imul(x, de) | 0, a = a + Math.imul(x, he) | 0;
                                var Ie = (c + (n = n + Math.imul(g, pe) | 0) | 0) + ((8191 & (i = (i = i + Math.imul(g, be) | 0) + Math.imul(m, pe) | 0)) << 13) | 0;
                                c = ((a = a + Math.imul(m, be) | 0) + (i >>> 13) | 0) + (Ie >>> 26) | 0, Ie &= 67108863, n = Math.imul(W, X), i = (i = Math.imul(W, Z)) + Math.imul(F, X) | 0, a = Math.imul(F, Z), n = n + Math.imul(C, Q) | 0, i = (i = i + Math.imul(C, ee) | 0) + Math.imul(B, Q) | 0, a = a + Math.imul(B, ee) | 0, n = n + Math.imul(O, re) | 0, i = (i = i + Math.imul(O, ne) | 0) + Math.imul(R, re) | 0, a = a + Math.imul(R, ne) | 0, n = n + Math.imul(N, ae) | 0, i = (i = i + Math.imul(N, oe) | 0) + Math.imul(P, ae) | 0, a = a + Math.imul(P, oe) | 0, n = n + Math.imul(M, ue) | 0, i = (i = i + Math.imul(M, ce) | 0) + Math.imul(I, ue) | 0, a = a + Math.imul(I, ce) | 0, n = n + Math.imul(A, de) | 0, i = (i = i + Math.imul(A, he) | 0) + Math.imul(k, de) | 0, a = a + Math.imul(k, he) | 0;
                                var Ee = (c + (n = n + Math.imul(w, pe) | 0) | 0) + ((8191 & (i = (i = i + Math.imul(w, be) | 0) + Math.imul(x, pe) | 0)) << 13) | 0;
                                c = ((a = a + Math.imul(x, be) | 0) + (i >>> 13) | 0) + (Ee >>> 26) | 0, Ee &= 67108863, n = Math.imul(W, Q), i = (i = Math.imul(W, ee)) + Math.imul(F, Q) | 0, a = Math.imul(F, ee), n = n + Math.imul(C, re) | 0, i = (i = i + Math.imul(C, ne) | 0) + Math.imul(B, re) | 0, a = a + Math.imul(B, ne) | 0, n = n + Math.imul(O, ae) | 0, i = (i = i + Math.imul(O, oe) | 0) + Math.imul(R, ae) | 0, a = a + Math.imul(R, oe) | 0, n = n + Math.imul(N, ue) | 0, i = (i = i + Math.imul(N, ce) | 0) + Math.imul(P, ue) | 0, a = a + Math.imul(P, ce) | 0, n = n + Math.imul(M, de) | 0, i = (i = i + Math.imul(M, he) | 0) + Math.imul(I, de) | 0, a = a + Math.imul(I, he) | 0;
                                var Ne = (c + (n = n + Math.imul(A, pe) | 0) | 0) + ((8191 & (i = (i = i + Math.imul(A, be) | 0) + Math.imul(k, pe) | 0)) << 13) | 0;
                                c = ((a = a + Math.imul(k, be) | 0) + (i >>> 13) | 0) + (Ne >>> 26) | 0, Ne &= 67108863, n = Math.imul(W, re), i = (i = Math.imul(W, ne)) + Math.imul(F, re) | 0, a = Math.imul(F, ne), n = n + Math.imul(C, ae) | 0, i = (i = i + Math.imul(C, oe) | 0) + Math.imul(B, ae) | 0, a = a + Math.imul(B, oe) | 0, n = n + Math.imul(O, ue) | 0, i = (i = i + Math.imul(O, ce) | 0) + Math.imul(R, ue) | 0, a = a + Math.imul(R, ce) | 0, n = n + Math.imul(N, de) | 0, i = (i = i + Math.imul(N, he) | 0) + Math.imul(P, de) | 0, a = a + Math.imul(P, he) | 0;
                                var Pe = (c + (n = n + Math.imul(M, pe) | 0) | 0) + ((8191 & (i = (i = i + Math.imul(M, be) | 0) + Math.imul(I, pe) | 0)) << 13) | 0;
                                c = ((a = a + Math.imul(I, be) | 0) + (i >>> 13) | 0) + (Pe >>> 26) | 0, Pe &= 67108863, n = Math.imul(W, ae), i = (i = Math.imul(W, oe)) + Math.imul(F, ae) | 0, a = Math.imul(F, oe), n = n + Math.imul(C, ue) | 0, i = (i = i + Math.imul(C, ce) | 0) + Math.imul(B, ue) | 0, a = a + Math.imul(B, ce) | 0, n = n + Math.imul(O, de) | 0, i = (i = i + Math.imul(O, he) | 0) + Math.imul(R, de) | 0, a = a + Math.imul(R, he) | 0;
                                var Te = (c + (n = n + Math.imul(N, pe) | 0) | 0) + ((8191 & (i = (i = i + Math.imul(N, be) | 0) + Math.imul(P, pe) | 0)) << 13) | 0;
                                c = ((a = a + Math.imul(P, be) | 0) + (i >>> 13) | 0) + (Te >>> 26) | 0, Te &= 67108863, n = Math.imul(W, ue), i = (i = Math.imul(W, ce)) + Math.imul(F, ue) | 0, a = Math.imul(F, ce), n = n + Math.imul(C, de) | 0, i = (i = i + Math.imul(C, he) | 0) + Math.imul(B, de) | 0, a = a + Math.imul(B, he) | 0;
                                var Oe = (c + (n = n + Math.imul(O, pe) | 0) | 0) + ((8191 & (i = (i = i + Math.imul(O, be) | 0) + Math.imul(R, pe) | 0)) << 13) | 0;
                                c = ((a = a + Math.imul(R, be) | 0) + (i >>> 13) | 0) + (Oe >>> 26) | 0, Oe &= 67108863, n = Math.imul(W, de), i = (i = Math.imul(W, he)) + Math.imul(F, de) | 0, a = Math.imul(F, he);
                                var Re = (c + (n = n + Math.imul(C, pe) | 0) | 0) + ((8191 & (i = (i = i + Math.imul(C, be) | 0) + Math.imul(B, pe) | 0)) << 13) | 0;
                                c = ((a = a + Math.imul(B, be) | 0) + (i >>> 13) | 0) + (Re >>> 26) | 0, Re &= 67108863;
                                var je = (c + (n = Math.imul(W, pe)) | 0) + ((8191 & (i = (i = Math.imul(W, be)) + Math.imul(F, pe) | 0)) << 13) | 0;
                                return c = ((a = Math.imul(F, be)) + (i >>> 13) | 0) + (je >>> 26) | 0, je &= 67108863, u[0] = ve, u[1] = ge, u[2] = me, u[3] = ye, u[4] = we, u[5] = xe, u[6] = _e, u[7] = Ae, u[8] = ke, u[9] = Se, u[10] = Me, u[11] = Ie, u[12] = Ee, u[13] = Ne, u[14] = Pe, u[15] = Te, u[16] = Oe, u[17] = Re, u[18] = je, 0 !== c && (u[19] = c, r.length++), r
                            };

                            function b(e, t, r) {
                                return (new v).mulp(e, t, r)
                            }

                            function v(e, t) {
                                this.x = e, this.y = t
                            }

                            Math.imul || (p = l), o.prototype.mulTo = function (e, t) {
                                var r = this.length + e.length;
                                return 10 === this.length && 10 === e.length ? p(this, e, t) : r < 63 ? l(this, e, t) : r < 1024 ? function (e, t, r) {
                                    r.negative = t.negative ^ e.negative, r.length = e.length + t.length;
                                    for (var n = 0, i = 0, a = 0; a < r.length - 1; a++) {
                                        var o = i;
                                        i = 0;
                                        for (var s = 67108863 & n, u = Math.min(a, t.length - 1), c = Math.max(0, a - e.length + 1); c <= u; c++) {
                                            var f = a - c, d = (0 | e.words[f]) * (0 | t.words[c]), h = 67108863 & d;
                                            s = 67108863 & (h = h + s | 0), i += (o = (o = o + (d / 67108864 | 0) | 0) + (h >>> 26) | 0) >>> 26, o &= 67108863
                                        }
                                        r.words[a] = s, n = o, o = i
                                    }
                                    return 0 !== n ? r.words[a] = n : r.length--, r.strip()
                                }(this, e, t) : b(this, e, t)
                            }, v.prototype.makeRBT = function (e) {
                                for (var t = new Array(e), r = o.prototype._countBits(e) - 1, n = 0; n < e; n++) t[n] = this.revBin(n, r, e);
                                return t
                            }, v.prototype.revBin = function (e, t, r) {
                                if (0 === e || e === r - 1) return e;
                                for (var n = 0, i = 0; i < t; i++) n |= (1 & e) << t - i - 1, e >>= 1;
                                return n
                            }, v.prototype.permute = function (e, t, r, n, i, a) {
                                for (var o = 0; o < a; o++) n[o] = t[e[o]], i[o] = r[e[o]]
                            }, v.prototype.transform = function (e, t, r, n, i, a) {
                                this.permute(a, e, t, r, n, i);
                                for (var o = 1; o < i; o <<= 1) for (var s = o << 1, u = Math.cos(2 * Math.PI / s), c = Math.sin(2 * Math.PI / s), f = 0; f < i; f += s) for (var d = u, h = c, l = 0; l < o; l++) {
                                    var p = r[f + l], b = n[f + l], v = r[f + l + o], g = n[f + l + o],
                                        m = d * v - h * g;
                                    g = d * g + h * v, v = m, r[f + l] = p + v, n[f + l] = b + g, r[f + l + o] = p - v, n[f + l + o] = b - g, l !== s && (m = u * d - c * h, h = u * h + c * d, d = m)
                                }
                            }, v.prototype.guessLen13b = function (e, t) {
                                var r = 1 | Math.max(t, e), n = 1 & r, i = 0;
                                for (r = r / 2 | 0; r; r >>>= 1) i++;
                                return 1 << i + 1 + n
                            }, v.prototype.conjugate = function (e, t, r) {
                                if (!(r <= 1)) for (var n = 0; n < r / 2; n++) {
                                    var i = e[n];
                                    e[n] = e[r - n - 1], e[r - n - 1] = i, i = t[n], t[n] = -t[r - n - 1], t[r - n - 1] = -i
                                }
                            }, v.prototype.normalize13b = function (e, t) {
                                for (var r = 0, n = 0; n < t / 2; n++) {
                                    var i = 8192 * Math.round(e[2 * n + 1] / t) + Math.round(e[2 * n] / t) + r;
                                    e[n] = 67108863 & i, r = i < 67108864 ? 0 : i / 67108864 | 0
                                }
                                return e
                            }, v.prototype.convert13b = function (e, t, r, n) {
                                for (var a = 0, o = 0; o < t; o++) a += 0 | e[o], r[2 * o] = 8191 & a, a >>>= 13, r[2 * o + 1] = 8191 & a, a >>>= 13;
                                for (o = 2 * t; o < n; ++o) r[o] = 0;
                                i(0 === a), i(0 == (-8192 & a))
                            }, v.prototype.stub = function (e) {
                                for (var t = new Array(e), r = 0; r < e; r++) t[r] = 0;
                                return t
                            }, v.prototype.mulp = function (e, t, r) {
                                var n = 2 * this.guessLen13b(e.length, t.length), i = this.makeRBT(n), a = this.stub(n),
                                    o = new Array(n), s = new Array(n), u = new Array(n), c = new Array(n),
                                    f = new Array(n), d = new Array(n), h = r.words;
                                h.length = n, this.convert13b(e.words, e.length, o, n), this.convert13b(t.words, t.length, c, n), this.transform(o, a, s, u, n, i), this.transform(c, a, f, d, n, i);
                                for (var l = 0; l < n; l++) {
                                    var p = s[l] * f[l] - u[l] * d[l];
                                    u[l] = s[l] * d[l] + u[l] * f[l], s[l] = p
                                }
                                return this.conjugate(s, u, n), this.transform(s, u, h, a, n, i), this.conjugate(h, a, n), this.normalize13b(h, n), r.negative = e.negative ^ t.negative, r.length = e.length + t.length, r.strip()
                            }, o.prototype.mul = function (e) {
                                var t = new o(null);
                                return t.words = new Array(this.length + e.length), this.mulTo(e, t)
                            }, o.prototype.mulf = function (e) {
                                var t = new o(null);
                                return t.words = new Array(this.length + e.length), b(this, e, t)
                            }, o.prototype.imul = function (e) {
                                return this.clone().mulTo(e, this)
                            }, o.prototype.imuln = function (e) {
                                i("number" == typeof e), i(e < 67108864);
                                for (var t = 0, r = 0; r < this.length; r++) {
                                    var n = (0 | this.words[r]) * e, a = (67108863 & n) + (67108863 & t);
                                    t >>= 26, t += n / 67108864 | 0, t += a >>> 26, this.words[r] = 67108863 & a
                                }
                                return 0 !== t && (this.words[r] = t, this.length++), this
                            }, o.prototype.muln = function (e) {
                                return this.clone().imuln(e)
                            }, o.prototype.sqr = function () {
                                return this.mul(this)
                            }, o.prototype.isqr = function () {
                                return this.imul(this.clone())
                            }, o.prototype.pow = function (e) {
                                var t = function (e) {
                                    for (var t = new Array(e.bitLength()), r = 0; r < t.length; r++) {
                                        var n = r / 26 | 0, i = r % 26;
                                        t[r] = (e.words[n] & 1 << i) >>> i
                                    }
                                    return t
                                }(e);
                                if (0 === t.length) return new o(1);
                                for (var r = this, n = 0; n < t.length && 0 === t[n]; n++, r = r.sqr()) ;
                                if (++n < t.length) for (var i = r.sqr(); n < t.length; n++, i = i.sqr()) 0 !== t[n] && (r = r.mul(i));
                                return r
                            }, o.prototype.iushln = function (e) {
                                i("number" == typeof e && e >= 0);
                                var t, r = e % 26, n = (e - r) / 26, a = 67108863 >>> 26 - r << 26 - r;
                                if (0 !== r) {
                                    var o = 0;
                                    for (t = 0; t < this.length; t++) {
                                        var s = this.words[t] & a, u = (0 | this.words[t]) - s << r;
                                        this.words[t] = u | o, o = s >>> 26 - r
                                    }
                                    o && (this.words[t] = o, this.length++)
                                }
                                if (0 !== n) {
                                    for (t = this.length - 1; t >= 0; t--) this.words[t + n] = this.words[t];
                                    for (t = 0; t < n; t++) this.words[t] = 0;
                                    this.length += n
                                }
                                return this.strip()
                            }, o.prototype.ishln = function (e) {
                                return i(0 === this.negative), this.iushln(e)
                            }, o.prototype.iushrn = function (e, t, r) {
                                var n;
                                i("number" == typeof e && e >= 0), n = t ? (t - t % 26) / 26 : 0;
                                var a = e % 26, o = Math.min((e - a) / 26, this.length),
                                    s = 67108863 ^ 67108863 >>> a << a, u = r;
                                if (n -= o, n = Math.max(0, n), u) {
                                    for (var c = 0; c < o; c++) u.words[c] = this.words[c];
                                    u.length = o
                                }
                                if (0 === o) ; else if (this.length > o) for (this.length -= o, c = 0; c < this.length; c++) this.words[c] = this.words[c + o]; else this.words[0] = 0, this.length = 1;
                                var f = 0;
                                for (c = this.length - 1; c >= 0 && (0 !== f || c >= n); c--) {
                                    var d = 0 | this.words[c];
                                    this.words[c] = f << 26 - a | d >>> a, f = d & s
                                }
                                return u && 0 !== f && (u.words[u.length++] = f), 0 === this.length && (this.words[0] = 0, this.length = 1), this.strip()
                            }, o.prototype.ishrn = function (e, t, r) {
                                return i(0 === this.negative), this.iushrn(e, t, r)
                            }, o.prototype.shln = function (e) {
                                return this.clone().ishln(e)
                            }, o.prototype.ushln = function (e) {
                                return this.clone().iushln(e)
                            }, o.prototype.shrn = function (e) {
                                return this.clone().ishrn(e)
                            }, o.prototype.ushrn = function (e) {
                                return this.clone().iushrn(e)
                            }, o.prototype.testn = function (e) {
                                i("number" == typeof e && e >= 0);
                                var t = e % 26, r = (e - t) / 26, n = 1 << t;
                                return !(this.length <= r || !(this.words[r] & n))
                            }, o.prototype.imaskn = function (e) {
                                i("number" == typeof e && e >= 0);
                                var t = e % 26, r = (e - t) / 26;
                                if (i(0 === this.negative, "imaskn works only with positive numbers"), this.length <= r) return this;
                                if (0 !== t && r++, this.length = Math.min(r, this.length), 0 !== t) {
                                    var n = 67108863 ^ 67108863 >>> t << t;
                                    this.words[this.length - 1] &= n
                                }
                                return this.strip()
                            }, o.prototype.maskn = function (e) {
                                return this.clone().imaskn(e)
                            }, o.prototype.iaddn = function (e) {
                                return i("number" == typeof e), i(e < 67108864), e < 0 ? this.isubn(-e) : 0 !== this.negative ? 1 === this.length && (0 | this.words[0]) < e ? (this.words[0] = e - (0 | this.words[0]), this.negative = 0, this) : (this.negative = 0, this.isubn(e), this.negative = 1, this) : this._iaddn(e)
                            }, o.prototype._iaddn = function (e) {
                                this.words[0] += e;
                                for (var t = 0; t < this.length && this.words[t] >= 67108864; t++) this.words[t] -= 67108864, t === this.length - 1 ? this.words[t + 1] = 1 : this.words[t + 1]++;
                                return this.length = Math.max(this.length, t + 1), this
                            }, o.prototype.isubn = function (e) {
                                if (i("number" == typeof e), i(e < 67108864), e < 0) return this.iaddn(-e);
                                if (0 !== this.negative) return this.negative = 0, this.iaddn(e), this.negative = 1, this;
                                if (this.words[0] -= e, 1 === this.length && this.words[0] < 0) this.words[0] = -this.words[0], this.negative = 1; else for (var t = 0; t < this.length && this.words[t] < 0; t++) this.words[t] += 67108864, this.words[t + 1] -= 1;
                                return this.strip()
                            }, o.prototype.addn = function (e) {
                                return this.clone().iaddn(e)
                            }, o.prototype.subn = function (e) {
                                return this.clone().isubn(e)
                            }, o.prototype.iabs = function () {
                                return this.negative = 0, this
                            }, o.prototype.abs = function () {
                                return this.clone().iabs()
                            }, o.prototype._ishlnsubmul = function (e, t, r) {
                                var n, a, o = e.length + r;
                                this._expand(o);
                                var s = 0;
                                for (n = 0; n < e.length; n++) {
                                    a = (0 | this.words[n + r]) + s;
                                    var u = (0 | e.words[n]) * t;
                                    s = ((a -= 67108863 & u) >> 26) - (u / 67108864 | 0), this.words[n + r] = 67108863 & a
                                }
                                for (; n < this.length - r; n++) s = (a = (0 | this.words[n + r]) + s) >> 26, this.words[n + r] = 67108863 & a;
                                if (0 === s) return this.strip();
                                for (i(-1 === s), s = 0, n = 0; n < this.length; n++) s = (a = -(0 | this.words[n]) + s) >> 26, this.words[n] = 67108863 & a;
                                return this.negative = 1, this.strip()
                            }, o.prototype._wordDiv = function (e, t) {
                                var r = (this.length, e.length), n = this.clone(), i = e, a = 0 | i.words[i.length - 1];
                                0 != (r = 26 - this._countBits(a)) && (i = i.ushln(r), n.iushln(r), a = 0 | i.words[i.length - 1]);
                                var s, u = n.length - i.length;
                                if ("mod" !== t) {
                                    (s = new o(null)).length = u + 1, s.words = new Array(s.length);
                                    for (var c = 0; c < s.length; c++) s.words[c] = 0
                                }
                                var f = n.clone()._ishlnsubmul(i, 1, u);
                                0 === f.negative && (n = f, s && (s.words[u] = 1));
                                for (var d = u - 1; d >= 0; d--) {
                                    var h = 67108864 * (0 | n.words[i.length + d]) + (0 | n.words[i.length + d - 1]);
                                    for (h = Math.min(h / a | 0, 67108863), n._ishlnsubmul(i, h, d); 0 !== n.negative;) h--, n.negative = 0, n._ishlnsubmul(i, 1, d), n.isZero() || (n.negative ^= 1);
                                    s && (s.words[d] = h)
                                }
                                return s && s.strip(), n.strip(), "div" !== t && 0 !== r && n.iushrn(r), {
                                    div: s || null,
                                    mod: n
                                }
                            }, o.prototype.divmod = function (e, t, r) {
                                return i(!e.isZero()), this.isZero() ? {
                                    div: new o(0),
                                    mod: new o(0)
                                } : 0 !== this.negative && 0 === e.negative ? (s = this.neg().divmod(e, t), "mod" !== t && (n = s.div.neg()), "div" !== t && (a = s.mod.neg(), r && 0 !== a.negative && a.iadd(e)), {
                                    div: n,
                                    mod: a
                                }) : 0 === this.negative && 0 !== e.negative ? (s = this.divmod(e.neg(), t), "mod" !== t && (n = s.div.neg()), {
                                    div: n,
                                    mod: s.mod
                                }) : 0 != (this.negative & e.negative) ? (s = this.neg().divmod(e.neg(), t), "div" !== t && (a = s.mod.neg(), r && 0 !== a.negative && a.isub(e)), {
                                    div: s.div,
                                    mod: a
                                }) : e.length > this.length || this.cmp(e) < 0 ? {
                                    div: new o(0),
                                    mod: this
                                } : 1 === e.length ? "div" === t ? {
                                    div: this.divn(e.words[0]),
                                    mod: null
                                } : "mod" === t ? {
                                    div: null,
                                    mod: new o(this.modn(e.words[0]))
                                } : {
                                    div: this.divn(e.words[0]),
                                    mod: new o(this.modn(e.words[0]))
                                } : this._wordDiv(e, t);
                                var n, a, s
                            }, o.prototype.div = function (e) {
                                return this.divmod(e, "div", !1).div
                            }, o.prototype.mod = function (e) {
                                return this.divmod(e, "mod", !1).mod
                            }, o.prototype.umod = function (e) {
                                return this.divmod(e, "mod", !0).mod
                            }, o.prototype.divRound = function (e) {
                                var t = this.divmod(e);
                                if (t.mod.isZero()) return t.div;
                                var r = 0 !== t.div.negative ? t.mod.isub(e) : t.mod, n = e.ushrn(1), i = e.andln(1),
                                    a = r.cmp(n);
                                return a < 0 || 1 === i && 0 === a ? t.div : 0 !== t.div.negative ? t.div.isubn(1) : t.div.iaddn(1)
                            }, o.prototype.modn = function (e) {
                                i(e <= 67108863);
                                for (var t = (1 << 26) % e, r = 0, n = this.length - 1; n >= 0; n--) r = (t * r + (0 | this.words[n])) % e;
                                return r
                            }, o.prototype.idivn = function (e) {
                                i(e <= 67108863);
                                for (var t = 0, r = this.length - 1; r >= 0; r--) {
                                    var n = (0 | this.words[r]) + 67108864 * t;
                                    this.words[r] = n / e | 0, t = n % e
                                }
                                return this.strip()
                            }, o.prototype.divn = function (e) {
                                return this.clone().idivn(e)
                            }, o.prototype.egcd = function (e) {
                                i(0 === e.negative), i(!e.isZero());
                                var t = this, r = e.clone();
                                t = 0 !== t.negative ? t.umod(e) : t.clone();
                                for (var n = new o(1), a = new o(0), s = new o(0), u = new o(1), c = 0; t.isEven() && r.isEven();) t.iushrn(1), r.iushrn(1), ++c;
                                for (var f = r.clone(), d = t.clone(); !t.isZero();) {
                                    for (var h = 0, l = 1; 0 == (t.words[0] & l) && h < 26; ++h, l <<= 1) ;
                                    if (h > 0) for (t.iushrn(h); h-- > 0;) (n.isOdd() || a.isOdd()) && (n.iadd(f), a.isub(d)), n.iushrn(1), a.iushrn(1);
                                    for (var p = 0, b = 1; 0 == (r.words[0] & b) && p < 26; ++p, b <<= 1) ;
                                    if (p > 0) for (r.iushrn(p); p-- > 0;) (s.isOdd() || u.isOdd()) && (s.iadd(f), u.isub(d)), s.iushrn(1), u.iushrn(1);
                                    t.cmp(r) >= 0 ? (t.isub(r), n.isub(s), a.isub(u)) : (r.isub(t), s.isub(n), u.isub(a))
                                }
                                return {a: s, b: u, gcd: r.iushln(c)}
                            }, o.prototype._invmp = function (e) {
                                i(0 === e.negative), i(!e.isZero());
                                var t = this, r = e.clone();
                                t = 0 !== t.negative ? t.umod(e) : t.clone();
                                for (var n, a = new o(1), s = new o(0), u = r.clone(); t.cmpn(1) > 0 && r.cmpn(1) > 0;) {
                                    for (var c = 0, f = 1; 0 == (t.words[0] & f) && c < 26; ++c, f <<= 1) ;
                                    if (c > 0) for (t.iushrn(c); c-- > 0;) a.isOdd() && a.iadd(u), a.iushrn(1);
                                    for (var d = 0, h = 1; 0 == (r.words[0] & h) && d < 26; ++d, h <<= 1) ;
                                    if (d > 0) for (r.iushrn(d); d-- > 0;) s.isOdd() && s.iadd(u), s.iushrn(1);
                                    t.cmp(r) >= 0 ? (t.isub(r), a.isub(s)) : (r.isub(t), s.isub(a))
                                }
                                return (n = 0 === t.cmpn(1) ? a : s).cmpn(0) < 0 && n.iadd(e), n
                            }, o.prototype.gcd = function (e) {
                                if (this.isZero()) return e.abs();
                                if (e.isZero()) return this.abs();
                                var t = this.clone(), r = e.clone();
                                t.negative = 0, r.negative = 0;
                                for (var n = 0; t.isEven() && r.isEven(); n++) t.iushrn(1), r.iushrn(1);
                                for (; ;) {
                                    for (; t.isEven();) t.iushrn(1);
                                    for (; r.isEven();) r.iushrn(1);
                                    var i = t.cmp(r);
                                    if (i < 0) {
                                        var a = t;
                                        t = r, r = a
                                    } else if (0 === i || 0 === r.cmpn(1)) break;
                                    t.isub(r)
                                }
                                return r.iushln(n)
                            }, o.prototype.invm = function (e) {
                                return this.egcd(e).a.umod(e)
                            }, o.prototype.isEven = function () {
                                return 0 == (1 & this.words[0])
                            }, o.prototype.isOdd = function () {
                                return 1 == (1 & this.words[0])
                            }, o.prototype.andln = function (e) {
                                return this.words[0] & e
                            }, o.prototype.bincn = function (e) {
                                i("number" == typeof e);
                                var t = e % 26, r = (e - t) / 26, n = 1 << t;
                                if (this.length <= r) return this._expand(r + 1), this.words[r] |= n, this;
                                for (var a = n, o = r; 0 !== a && o < this.length; o++) {
                                    var s = 0 | this.words[o];
                                    a = (s += a) >>> 26, s &= 67108863, this.words[o] = s
                                }
                                return 0 !== a && (this.words[o] = a, this.length++), this
                            }, o.prototype.isZero = function () {
                                return 1 === this.length && 0 === this.words[0]
                            }, o.prototype.cmpn = function (e) {
                                var t, r = e < 0;
                                if (0 !== this.negative && !r) return -1;
                                if (0 === this.negative && r) return 1;
                                if (this.strip(), this.length > 1) t = 1; else {
                                    r && (e = -e), i(e <= 67108863, "Number is too big");
                                    var n = 0 | this.words[0];
                                    t = n === e ? 0 : n < e ? -1 : 1
                                }
                                return 0 !== this.negative ? 0 | -t : t
                            }, o.prototype.cmp = function (e) {
                                if (0 !== this.negative && 0 === e.negative) return -1;
                                if (0 === this.negative && 0 !== e.negative) return 1;
                                var t = this.ucmp(e);
                                return 0 !== this.negative ? 0 | -t : t
                            }, o.prototype.ucmp = function (e) {
                                if (this.length > e.length) return 1;
                                if (this.length < e.length) return -1;
                                for (var t = 0, r = this.length - 1; r >= 0; r--) {
                                    var n = 0 | this.words[r], i = 0 | e.words[r];
                                    if (n !== i) {
                                        n < i ? t = -1 : n > i && (t = 1);
                                        break
                                    }
                                }
                                return t
                            }, o.prototype.gtn = function (e) {
                                return 1 === this.cmpn(e)
                            }, o.prototype.gt = function (e) {
                                return 1 === this.cmp(e)
                            }, o.prototype.gten = function (e) {
                                return this.cmpn(e) >= 0
                            }, o.prototype.gte = function (e) {
                                return this.cmp(e) >= 0
                            }, o.prototype.ltn = function (e) {
                                return -1 === this.cmpn(e)
                            }, o.prototype.lt = function (e) {
                                return -1 === this.cmp(e)
                            }, o.prototype.lten = function (e) {
                                return this.cmpn(e) <= 0
                            }, o.prototype.lte = function (e) {
                                return this.cmp(e) <= 0
                            }, o.prototype.eqn = function (e) {
                                return 0 === this.cmpn(e)
                            }, o.prototype.eq = function (e) {
                                return 0 === this.cmp(e)
                            }, o.red = function (e) {
                                return new A(e)
                            }, o.prototype.toRed = function (e) {
                                return i(!this.red, "Already a number in reduction context"), i(0 === this.negative, "red works only with positives"), e.convertTo(this)._forceRed(e)
                            }, o.prototype.fromRed = function () {
                                return i(this.red, "fromRed works only with numbers in reduction context"), this.red.convertFrom(this)
                            }, o.prototype._forceRed = function (e) {
                                return this.red = e, this
                            }, o.prototype.forceRed = function (e) {
                                return i(!this.red, "Already a number in reduction context"), this._forceRed(e)
                            }, o.prototype.redAdd = function (e) {
                                return i(this.red, "redAdd works only with red numbers"), this.red.add(this, e)
                            }, o.prototype.redIAdd = function (e) {
                                return i(this.red, "redIAdd works only with red numbers"), this.red.iadd(this, e)
                            }, o.prototype.redSub = function (e) {
                                return i(this.red, "redSub works only with red numbers"), this.red.sub(this, e)
                            }, o.prototype.redISub = function (e) {
                                return i(this.red, "redISub works only with red numbers"), this.red.isub(this, e)
                            }, o.prototype.redShl = function (e) {
                                return i(this.red, "redShl works only with red numbers"), this.red.shl(this, e)
                            }, o.prototype.redMul = function (e) {
                                return i(this.red, "redMul works only with red numbers"), this.red._verify2(this, e), this.red.mul(this, e)
                            }, o.prototype.redIMul = function (e) {
                                return i(this.red, "redMul works only with red numbers"), this.red._verify2(this, e), this.red.imul(this, e)
                            }, o.prototype.redSqr = function () {
                                return i(this.red, "redSqr works only with red numbers"), this.red._verify1(this), this.red.sqr(this)
                            }, o.prototype.redISqr = function () {
                                return i(this.red, "redISqr works only with red numbers"), this.red._verify1(this), this.red.isqr(this)
                            }, o.prototype.redSqrt = function () {
                                return i(this.red, "redSqrt works only with red numbers"), this.red._verify1(this), this.red.sqrt(this)
                            }, o.prototype.redInvm = function () {
                                return i(this.red, "redInvm works only with red numbers"), this.red._verify1(this), this.red.invm(this)
                            }, o.prototype.redNeg = function () {
                                return i(this.red, "redNeg works only with red numbers"), this.red._verify1(this), this.red.neg(this)
                            }, o.prototype.redPow = function (e) {
                                return i(this.red && !e.red, "redPow(normalNum)"), this.red._verify1(this), this.red.pow(this, e)
                            };
                            var g = {k256: null, p224: null, p192: null, p25519: null};

                            function m(e, t) {
                                this.name = e, this.p = new o(t, 16), this.n = this.p.bitLength(), this.k = new o(1).iushln(this.n).isub(this.p), this.tmp = this._tmp()
                            }

                            function y() {
                                m.call(this, "k256", "ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff fffffffe fffffc2f")
                            }

                            function w() {
                                m.call(this, "p224", "ffffffff ffffffff ffffffff ffffffff 00000000 00000000 00000001")
                            }

                            function x() {
                                m.call(this, "p192", "ffffffff ffffffff ffffffff fffffffe ffffffff ffffffff")
                            }

                            function _() {
                                m.call(this, "25519", "7fffffffffffffff ffffffffffffffff ffffffffffffffff ffffffffffffffed")
                            }

                            function A(e) {
                                if ("string" == typeof e) {
                                    var t = o._prime(e);
                                    this.m = t.p, this.prime = t
                                } else i(e.gtn(1), "modulus must be greater than 1"), this.m = e, this.prime = null
                            }

                            function k(e) {
                                A.call(this, e), this.shift = this.m.bitLength(), this.shift % 26 != 0 && (this.shift += 26 - this.shift % 26), this.r = new o(1).iushln(this.shift), this.r2 = this.imod(this.r.sqr()), this.rinv = this.r._invmp(this.m), this.minv = this.rinv.mul(this.r).isubn(1).div(this.m), this.minv = this.minv.umod(this.r), this.minv = this.r.sub(this.minv)
                            }

                            m.prototype._tmp = function () {
                                var e = new o(null);
                                return e.words = new Array(Math.ceil(this.n / 13)), e
                            }, m.prototype.ireduce = function (e) {
                                var t, r = e;
                                do {
                                    this.split(r, this.tmp), t = (r = (r = this.imulK(r)).iadd(this.tmp)).bitLength()
                                } while (t > this.n);
                                var n = t < this.n ? -1 : r.ucmp(this.p);
                                return 0 === n ? (r.words[0] = 0, r.length = 1) : n > 0 ? r.isub(this.p) : r.strip(), r
                            }, m.prototype.split = function (e, t) {
                                e.iushrn(this.n, 0, t)
                            }, m.prototype.imulK = function (e) {
                                return e.imul(this.k)
                            }, a(y, m), y.prototype.split = function (e, t) {
                                for (var r = Math.min(e.length, 9), n = 0; n < r; n++) t.words[n] = e.words[n];
                                if (t.length = r, e.length <= 9) return e.words[0] = 0, void (e.length = 1);
                                var i = e.words[9];
                                for (t.words[t.length++] = 4194303 & i, n = 10; n < e.length; n++) {
                                    var a = 0 | e.words[n];
                                    e.words[n - 10] = (4194303 & a) << 4 | i >>> 22, i = a
                                }
                                i >>>= 22, e.words[n - 10] = i, 0 === i && e.length > 10 ? e.length -= 10 : e.length -= 9
                            }, y.prototype.imulK = function (e) {
                                e.words[e.length] = 0, e.words[e.length + 1] = 0, e.length += 2;
                                for (var t = 0, r = 0; r < e.length; r++) {
                                    var n = 0 | e.words[r];
                                    t += 977 * n, e.words[r] = 67108863 & t, t = 64 * n + (t / 67108864 | 0)
                                }
                                return 0 === e.words[e.length - 1] && (e.length--, 0 === e.words[e.length - 1] && e.length--), e
                            }, a(w, m), a(x, m), a(_, m), _.prototype.imulK = function (e) {
                                for (var t = 0, r = 0; r < e.length; r++) {
                                    var n = 19 * (0 | e.words[r]) + t, i = 67108863 & n;
                                    n >>>= 26, e.words[r] = i, t = n
                                }
                                return 0 !== t && (e.words[e.length++] = t), e
                            }, o._prime = function (e) {
                                if (g[e]) return g[e];
                                var t;
                                if ("k256" === e) t = new y; else if ("p224" === e) t = new w; else if ("p192" === e) t = new x; else {
                                    if ("p25519" !== e) throw new Error("Unknown prime " + e);
                                    t = new _
                                }
                                return g[e] = t, t
                            }, A.prototype._verify1 = function (e) {
                                i(0 === e.negative, "red works only with positives"), i(e.red, "red works only with red numbers")
                            }, A.prototype._verify2 = function (e, t) {
                                i(0 == (e.negative | t.negative), "red works only with positives"), i(e.red && e.red === t.red, "red works only with red numbers")
                            }, A.prototype.imod = function (e) {
                                return this.prime ? this.prime.ireduce(e)._forceRed(this) : e.umod(this.m)._forceRed(this)
                            }, A.prototype.neg = function (e) {
                                return e.isZero() ? e.clone() : this.m.sub(e)._forceRed(this)
                            }, A.prototype.add = function (e, t) {
                                this._verify2(e, t);
                                var r = e.add(t);
                                return r.cmp(this.m) >= 0 && r.isub(this.m), r._forceRed(this)
                            }, A.prototype.iadd = function (e, t) {
                                this._verify2(e, t);
                                var r = e.iadd(t);
                                return r.cmp(this.m) >= 0 && r.isub(this.m), r
                            }, A.prototype.sub = function (e, t) {
                                this._verify2(e, t);
                                var r = e.sub(t);
                                return r.cmpn(0) < 0 && r.iadd(this.m), r._forceRed(this)
                            }, A.prototype.isub = function (e, t) {
                                this._verify2(e, t);
                                var r = e.isub(t);
                                return r.cmpn(0) < 0 && r.iadd(this.m), r
                            }, A.prototype.shl = function (e, t) {
                                return this._verify1(e), this.imod(e.ushln(t))
                            }, A.prototype.imul = function (e, t) {
                                return this._verify2(e, t), this.imod(e.imul(t))
                            }, A.prototype.mul = function (e, t) {
                                return this._verify2(e, t), this.imod(e.mul(t))
                            }, A.prototype.isqr = function (e) {
                                return this.imul(e, e.clone())
                            }, A.prototype.sqr = function (e) {
                                return this.mul(e, e)
                            }, A.prototype.sqrt = function (e) {
                                if (e.isZero()) return e.clone();
                                var t = this.m.andln(3);
                                if (i(t % 2 == 1), 3 === t) {
                                    var r = this.m.add(new o(1)).iushrn(2);
                                    return this.pow(e, r)
                                }
                                for (var n = this.m.subn(1), a = 0; !n.isZero() && 0 === n.andln(1);) a++, n.iushrn(1);
                                i(!n.isZero());
                                var s = new o(1).toRed(this), u = s.redNeg(), c = this.m.subn(1).iushrn(1),
                                    f = this.m.bitLength();
                                for (f = new o(2 * f * f).toRed(this); 0 !== this.pow(f, c).cmp(u);) f.redIAdd(u);
                                for (var d = this.pow(f, n), h = this.pow(e, n.addn(1).iushrn(1)), l = this.pow(e, n), p = a; 0 !== l.cmp(s);) {
                                    for (var b = l, v = 0; 0 !== b.cmp(s); v++) b = b.redSqr();
                                    i(v < p);
                                    var g = this.pow(d, new o(1).iushln(p - v - 1));
                                    h = h.redMul(g), d = g.redSqr(), l = l.redMul(d), p = v
                                }
                                return h
                            }, A.prototype.invm = function (e) {
                                var t = e._invmp(this.m);
                                return 0 !== t.negative ? (t.negative = 0, this.imod(t).redNeg()) : this.imod(t)
                            }, A.prototype.pow = function (e, t) {
                                if (t.isZero()) return new o(1).toRed(this);
                                if (0 === t.cmpn(1)) return e.clone();
                                var r = new Array(16);
                                r[0] = new o(1).toRed(this), r[1] = e;
                                for (var n = 2; n < r.length; n++) r[n] = this.mul(r[n - 1], e);
                                var i = r[0], a = 0, s = 0, u = t.bitLength() % 26;
                                for (0 === u && (u = 26), n = t.length - 1; n >= 0; n--) {
                                    for (var c = t.words[n], f = u - 1; f >= 0; f--) {
                                        var d = c >> f & 1;
                                        i !== r[0] && (i = this.sqr(i)), 0 !== d || 0 !== a ? (a <<= 1, a |= d, (4 == ++s || 0 === n && 0 === f) && (i = this.mul(i, r[a]), s = 0, a = 0)) : s = 0
                                    }
                                    u = 26
                                }
                                return i
                            }, A.prototype.convertTo = function (e) {
                                var t = e.umod(this.m);
                                return t === e ? t.clone() : t
                            }, A.prototype.convertFrom = function (e) {
                                var t = e.clone();
                                return t.red = null, t
                            }, o.mont = function (e) {
                                return new k(e)
                            }, a(k, A), k.prototype.convertTo = function (e) {
                                return this.imod(e.ushln(this.shift))
                            }, k.prototype.convertFrom = function (e) {
                                var t = this.imod(e.mul(this.rinv));
                                return t.red = null, t
                            }, k.prototype.imul = function (e, t) {
                                if (e.isZero() || t.isZero()) return e.words[0] = 0, e.length = 1, e;
                                var r = e.imul(t),
                                    n = r.maskn(this.shift).mul(this.minv).imaskn(this.shift).mul(this.m),
                                    i = r.isub(n).iushrn(this.shift), a = i;
                                return i.cmp(this.m) >= 0 ? a = i.isub(this.m) : i.cmpn(0) < 0 && (a = i.iadd(this.m)), a._forceRed(this)
                            }, k.prototype.mul = function (e, t) {
                                if (e.isZero() || t.isZero()) return new o(0)._forceRed(this);
                                var r = e.mul(t), n = r.maskn(this.shift).mul(this.minv).imaskn(this.shift).mul(this.m),
                                    i = r.isub(n).iushrn(this.shift), a = i;
                                return i.cmp(this.m) >= 0 ? a = i.isub(this.m) : i.cmpn(0) < 0 && (a = i.iadd(this.m)), a._forceRed(this)
                            }, k.prototype.invm = function (e) {
                                return this.imod(e._invmp(this.m).mul(this.r2))._forceRed(this)
                            }
                        }(e, this)
                    }).call(this, r(109)(e))
                }, function (e, t, r) {
                    var n = r(12), i = r(26);

                    function a(e) {
                        return (e >>> 24 | e >>> 8 & 65280 | e << 8 & 16711680 | (255 & e) << 24) >>> 0
                    }

                    function o(e) {
                        return 1 === e.length ? "0" + e : e
                    }

                    function s(e) {
                        return 7 === e.length ? "0" + e : 6 === e.length ? "00" + e : 5 === e.length ? "000" + e : 4 === e.length ? "0000" + e : 3 === e.length ? "00000" + e : 2 === e.length ? "000000" + e : 1 === e.length ? "0000000" + e : e
                    }

                    t.inherits = i, t.toArray = function (e, t) {
                        if (Array.isArray(e)) return e.slice();
                        if (!e) return [];
                        var r = [];
                        if ("string" == typeof e) if (t) {
                            if ("hex" === t) for ((e = e.replace(/[^a-z0-9]+/gi, "")).length % 2 != 0 && (e = "0" + e), n = 0; n < e.length; n += 2) r.push(parseInt(e[n] + e[n + 1], 16))
                        } else for (var n = 0; n < e.length; n++) {
                            var i = e.charCodeAt(n), a = i >> 8, o = 255 & i;
                            a ? r.push(a, o) : r.push(o)
                        } else for (n = 0; n < e.length; n++) r[n] = 0 | e[n];
                        return r
                    }, t.toHex = function (e) {
                        for (var t = "", r = 0; r < e.length; r++) t += o(e[r].toString(16));
                        return t
                    }, t.htonl = a, t.toHex32 = function (e, t) {
                        for (var r = "", n = 0; n < e.length; n++) {
                            var i = e[n];
                            "little" === t && (i = a(i)), r += s(i.toString(16))
                        }
                        return r
                    }, t.zero2 = o, t.zero8 = s, t.join32 = function (e, t, r, i) {
                        var a = r - t;
                        n(a % 4 == 0);
                        for (var o = new Array(a / 4), s = 0, u = t; s < o.length; s++, u += 4) {
                            var c;
                            c = "big" === i ? e[u] << 24 | e[u + 1] << 16 | e[u + 2] << 8 | e[u + 3] : e[u + 3] << 24 | e[u + 2] << 16 | e[u + 1] << 8 | e[u], o[s] = c >>> 0
                        }
                        return o
                    }, t.split32 = function (e, t) {
                        for (var r = new Array(4 * e.length), n = 0, i = 0; n < e.length; n++, i += 4) {
                            var a = e[n];
                            "big" === t ? (r[i] = a >>> 24, r[i + 1] = a >>> 16 & 255, r[i + 2] = a >>> 8 & 255, r[i + 3] = 255 & a) : (r[i + 3] = a >>> 24, r[i + 2] = a >>> 16 & 255, r[i + 1] = a >>> 8 & 255, r[i] = 255 & a)
                        }
                        return r
                    }, t.rotr32 = function (e, t) {
                        return e >>> t | e << 32 - t
                    }, t.rotl32 = function (e, t) {
                        return e << t | e >>> 32 - t
                    }, t.sum32 = function (e, t) {
                        return e + t >>> 0
                    }, t.sum32_3 = function (e, t, r) {
                        return e + t + r >>> 0
                    }, t.sum32_4 = function (e, t, r, n) {
                        return e + t + r + n >>> 0
                    }, t.sum32_5 = function (e, t, r, n, i) {
                        return e + t + r + n + i >>> 0
                    }, t.sum64 = function (e, t, r, n) {
                        var i = e[t], a = n + e[t + 1] >>> 0, o = (a < n ? 1 : 0) + r + i;
                        e[t] = o >>> 0, e[t + 1] = a
                    }, t.sum64_hi = function (e, t, r, n) {
                        return (t + n >>> 0 < t ? 1 : 0) + e + r >>> 0
                    }, t.sum64_lo = function (e, t, r, n) {
                        return t + n >>> 0
                    }, t.sum64_4_hi = function (e, t, r, n, i, a, o, s) {
                        var u = 0, c = t;
                        return u += (c = c + n >>> 0) < t ? 1 : 0, u += (c = c + a >>> 0) < a ? 1 : 0, e + r + i + o + (u += (c = c + s >>> 0) < s ? 1 : 0) >>> 0
                    }, t.sum64_4_lo = function (e, t, r, n, i, a, o, s) {
                        return t + n + a + s >>> 0
                    }, t.sum64_5_hi = function (e, t, r, n, i, a, o, s, u, c) {
                        var f = 0, d = t;
                        return f += (d = d + n >>> 0) < t ? 1 : 0, f += (d = d + a >>> 0) < a ? 1 : 0, f += (d = d + s >>> 0) < s ? 1 : 0, e + r + i + o + u + (f += (d = d + c >>> 0) < c ? 1 : 0) >>> 0
                    }, t.sum64_5_lo = function (e, t, r, n, i, a, o, s, u, c) {
                        return t + n + a + s + c >>> 0
                    }, t.rotr64_hi = function (e, t, r) {
                        return (t << 32 - r | e >>> r) >>> 0
                    }, t.rotr64_lo = function (e, t, r) {
                        return (e << 32 - r | t >>> r) >>> 0
                    }, t.shr64_hi = function (e, t, r) {
                        return e >>> r
                    }, t.shr64_lo = function (e, t, r) {
                        return (e << 32 - r | t >>> r) >>> 0
                    }
                }, function (e, t, r) {
                    var n = r(12), i = r(26);

                    function a(e, t) {
                        return 55296 == (64512 & e.charCodeAt(t)) && !(t < 0 || t + 1 >= e.length) && 56320 == (64512 & e.charCodeAt(t + 1))
                    }

                    function o(e) {
                        return (e >>> 24 | e >>> 8 & 65280 | e << 8 & 16711680 | (255 & e) << 24) >>> 0
                    }

                    function s(e) {
                        return 1 === e.length ? "0" + e : e
                    }

                    function u(e) {
                        return 7 === e.length ? "0" + e : 6 === e.length ? "00" + e : 5 === e.length ? "000" + e : 4 === e.length ? "0000" + e : 3 === e.length ? "00000" + e : 2 === e.length ? "000000" + e : 1 === e.length ? "0000000" + e : e
                    }

                    t.inherits = i, t.toArray = function (e, t) {
                        if (Array.isArray(e)) return e.slice();
                        if (!e) return [];
                        var r = [];
                        if ("string" == typeof e) if (t) {
                            if ("hex" === t) for ((e = e.replace(/[^a-z0-9]+/gi, "")).length % 2 != 0 && (e = "0" + e), i = 0; i < e.length; i += 2) r.push(parseInt(e[i] + e[i + 1], 16))
                        } else for (var n = 0, i = 0; i < e.length; i++) {
                            var o = e.charCodeAt(i);
                            o < 128 ? r[n++] = o : o < 2048 ? (r[n++] = o >> 6 | 192, r[n++] = 63 & o | 128) : a(e, i) ? (o = 65536 + ((1023 & o) << 10) + (1023 & e.charCodeAt(++i)), r[n++] = o >> 18 | 240, r[n++] = o >> 12 & 63 | 128, r[n++] = o >> 6 & 63 | 128, r[n++] = 63 & o | 128) : (r[n++] = o >> 12 | 224, r[n++] = o >> 6 & 63 | 128, r[n++] = 63 & o | 128)
                        } else for (i = 0; i < e.length; i++) r[i] = 0 | e[i];
                        return r
                    }, t.toHex = function (e) {
                        for (var t = "", r = 0; r < e.length; r++) t += s(e[r].toString(16));
                        return t
                    }, t.htonl = o, t.toHex32 = function (e, t) {
                        for (var r = "", n = 0; n < e.length; n++) {
                            var i = e[n];
                            "little" === t && (i = o(i)), r += u(i.toString(16))
                        }
                        return r
                    }, t.zero2 = s, t.zero8 = u, t.join32 = function (e, t, r, i) {
                        var a = r - t;
                        n(a % 4 == 0);
                        for (var o = new Array(a / 4), s = 0, u = t; s < o.length; s++, u += 4) {
                            var c;
                            c = "big" === i ? e[u] << 24 | e[u + 1] << 16 | e[u + 2] << 8 | e[u + 3] : e[u + 3] << 24 | e[u + 2] << 16 | e[u + 1] << 8 | e[u], o[s] = c >>> 0
                        }
                        return o
                    }, t.split32 = function (e, t) {
                        for (var r = new Array(4 * e.length), n = 0, i = 0; n < e.length; n++, i += 4) {
                            var a = e[n];
                            "big" === t ? (r[i] = a >>> 24, r[i + 1] = a >>> 16 & 255, r[i + 2] = a >>> 8 & 255, r[i + 3] = 255 & a) : (r[i + 3] = a >>> 24, r[i + 2] = a >>> 16 & 255, r[i + 1] = a >>> 8 & 255, r[i] = 255 & a)
                        }
                        return r
                    }, t.rotr32 = function (e, t) {
                        return e >>> t | e << 32 - t
                    }, t.rotl32 = function (e, t) {
                        return e << t | e >>> 32 - t
                    }, t.sum32 = function (e, t) {
                        return e + t >>> 0
                    }, t.sum32_3 = function (e, t, r) {
                        return e + t + r >>> 0
                    }, t.sum32_4 = function (e, t, r, n) {
                        return e + t + r + n >>> 0
                    }, t.sum32_5 = function (e, t, r, n, i) {
                        return e + t + r + n + i >>> 0
                    }, t.sum64 = function (e, t, r, n) {
                        var i = e[t], a = n + e[t + 1] >>> 0, o = (a < n ? 1 : 0) + r + i;
                        e[t] = o >>> 0, e[t + 1] = a
                    }, t.sum64_hi = function (e, t, r, n) {
                        return (t + n >>> 0 < t ? 1 : 0) + e + r >>> 0
                    }, t.sum64_lo = function (e, t, r, n) {
                        return t + n >>> 0
                    }, t.sum64_4_hi = function (e, t, r, n, i, a, o, s) {
                        var u = 0, c = t;
                        return u += (c = c + n >>> 0) < t ? 1 : 0, u += (c = c + a >>> 0) < a ? 1 : 0, e + r + i + o + (u += (c = c + s >>> 0) < s ? 1 : 0) >>> 0
                    }, t.sum64_4_lo = function (e, t, r, n, i, a, o, s) {
                        return t + n + a + s >>> 0
                    }, t.sum64_5_hi = function (e, t, r, n, i, a, o, s, u, c) {
                        var f = 0, d = t;
                        return f += (d = d + n >>> 0) < t ? 1 : 0, f += (d = d + a >>> 0) < a ? 1 : 0, f += (d = d + s >>> 0) < s ? 1 : 0, e + r + i + o + u + (f += (d = d + c >>> 0) < c ? 1 : 0) >>> 0
                    }, t.sum64_5_lo = function (e, t, r, n, i, a, o, s, u, c) {
                        return t + n + a + s + c >>> 0
                    }, t.rotr64_hi = function (e, t, r) {
                        return (t << 32 - r | e >>> r) >>> 0
                    }, t.rotr64_lo = function (e, t, r) {
                        return (e << 32 - r | t >>> r) >>> 0
                    }, t.shr64_hi = function (e, t, r) {
                        return e >>> r
                    }, t.shr64_lo = function (e, t, r) {
                        return (e << 32 - r | t >>> r) >>> 0
                    }
                }, function (e, t) {
                    function r(e) {
                        return (r = "function" == typeof Symbol && "symbol" == n()(Symbol.iterator) ? function (e) {
                            return n()(e)
                        } : function (e) {
                            return e && "function" == typeof Symbol && e.constructor === Symbol && e !== Symbol.prototype ? "symbol" : n()(e)
                        })(e)
                    }

                    function i(t) {
                        return "function" == typeof Symbol && "symbol" === r(Symbol.iterator) ? e.exports = i = function (e) {
                            return r(e)
                        } : e.exports = i = function (e) {
                            return e && "function" == typeof Symbol && e.constructor === Symbol && e !== Symbol.prototype ? "symbol" : r(e)
                        }, i(t)
                    }

                    e.exports = i
                }, function (e, t, r) {
                    var i = this && this.__importStar || function (e) {
                        if (e && e.__esModule) return e;
                        var t = {};
                        if (null != e) for (var r in e) Object.hasOwnProperty.call(e, r) && (t[r] = e[r]);
                        return t.default = e, t
                    };
                    Object.defineProperty(t, "__esModule", {value: !0});
                    var a = i(r(19));

                    function o(e) {
                        return !!e.toHexString
                    }

                    function s(e) {
                        return e.slice ? e : (e.slice = function () {
                            var t = Array.prototype.slice.call(arguments);
                            return s(new Uint8Array(Array.prototype.slice.apply(e, t)))
                        }, e)
                    }

                    function u(e) {
                        if (!e || parseInt(String(e.length)) != e.length || "string" == typeof e) return !1;
                        for (var t = 0; t < e.length; t++) {
                            var r = e[t];
                            if (r < 0 || r >= 256 || parseInt(String(r)) != r) return !1
                        }
                        return !0
                    }

                    function c(e) {
                        if (null == e && a.throwError("cannot convert null value to array", a.INVALID_ARGUMENT, {
                            arg: "value",
                            value: e
                        }), o(e) && (e = e.toHexString()), "string" == typeof e) {
                            var t = e.match(/^(0x)?[0-9a-fA-F]*$/);
                            t || a.throwError("invalid hexidecimal string", a.INVALID_ARGUMENT, {
                                arg: "value",
                                value: e
                            }), "0x" !== t[1] && a.throwError("hex string must have 0x prefix", a.INVALID_ARGUMENT, {
                                arg: "value",
                                value: e
                            }), (e = e.substring(2)).length % 2 && (e = "0" + e);
                            for (var r = [], i = 0; i < e.length; i += 2) r.push(parseInt(e.substr(i, 2), 16));
                            return s(new Uint8Array(r))
                        }
                        return u(e) ? s(new Uint8Array(e)) : (a.throwError("invalid arrayify value", null, {
                            arg: "value",
                            value: e,
                            type: n()(e)
                        }), null)
                    }

                    function f(e) {
                        for (var t = [], r = 0, n = 0; n < e.length; n++) {
                            var i = c(e[n]);
                            t.push(i), r += i.length
                        }
                        var a = new Uint8Array(r), o = 0;
                        for (n = 0; n < t.length; n++) a.set(t[n], o), o += t[n].length;
                        return s(a)
                    }

                    function d(e, t) {
                        return !("string" != typeof e || !e.match(/^0x[0-9A-Fa-f]*$/) || t && e.length !== 2 + 2 * t)
                    }

                    t.isHexable = o, t.isArrayish = u, t.arrayify = c, t.concat = f, t.stripZeros = function (e) {
                        var t = c(e);
                        if (0 === t.length) return t;
                        for (var r = 0; 0 === t[r];) r++;
                        return r && (t = t.slice(r)), t
                    }, t.padZeros = function (e, t) {
                        if (t < (e = c(e)).length) throw new Error("cannot pad");
                        var r = new Uint8Array(t);
                        return r.set(e, t - e.length), s(r)
                    }, t.isHexString = d;
                    var h = "0123456789abcdef";

                    function l(e) {
                        if (o(e)) return e.toHexString();
                        if ("number" == typeof e) {
                            e < 0 && a.throwError("cannot hexlify negative value", a.INVALID_ARGUMENT, {
                                arg: "value",
                                value: e
                            }), e >= 9007199254740991 && a.throwError("out-of-range", a.NUMERIC_FAULT, {
                                operartion: "hexlify",
                                fault: "out-of-safe-range"
                            });
                            for (var t = ""; e;) t = h[15 & e] + t, e = Math.floor(e / 16);
                            return t.length ? (t.length % 2 && (t = "0" + t), "0x" + t) : "0x00"
                        }
                        if ("string" == typeof e) {
                            var r = e.match(/^(0x)?[0-9a-fA-F]*$/);
                            return r || a.throwError("invalid hexidecimal string", a.INVALID_ARGUMENT, {
                                arg: "value",
                                value: e
                            }), "0x" !== r[1] && a.throwError("hex string must have 0x prefix", a.INVALID_ARGUMENT, {
                                arg: "value",
                                value: e
                            }), e.length % 2 && (e = "0x0" + e.substring(2)), e
                        }
                        if (u(e)) {
                            for (var n = [], i = 0; i < e.length; i++) {
                                var s = e[i];
                                n.push(h[(240 & s) >> 4] + h[15 & s])
                            }
                            return "0x" + n.join("")
                        }
                        return a.throwError("invalid hexlify value", null, {arg: "value", value: e}), "never"
                    }

                    function p(e, t) {
                        for (d(e) || a.throwError("invalid hex string", a.INVALID_ARGUMENT, {
                            arg: "value",
                            value: e
                        }); e.length < 2 * t + 2;) e = "0x0" + e.substring(2);
                        return e
                    }

                    function b(e) {
                        var t, r = 0, n = "0x", i = "0x";
                        if ((t = e) && null != t.r && null != t.s) {
                            null == e.v && null == e.recoveryParam && a.throwError("at least on of recoveryParam or v must be specified", a.INVALID_ARGUMENT, {
                                argument: "signature",
                                value: e
                            }), n = p(e.r, 32), i = p(e.s, 32), "string" == typeof (r = e.v) && (r = parseInt(r, 16));
                            var o = e.recoveryParam;
                            null == o && null != e.v && (o = 1 - r % 2), r = 27 + o
                        } else {
                            var s = c(e);
                            if (65 !== s.length) throw new Error("invalid signature");
                            n = l(s.slice(0, 32)), i = l(s.slice(32, 64)), 27 !== (r = s[64]) && 28 !== r && (r = 27 + r % 2)
                        }
                        return {r: n, s: i, recoveryParam: r - 27, v: r}
                    }

                    t.hexlify = l, t.hexDataLength = function (e) {
                        return d(e) && e.length % 2 == 0 ? (e.length - 2) / 2 : null
                    }, t.hexDataSlice = function (e, t, r) {
                        return d(e) || a.throwError("invalid hex data", a.INVALID_ARGUMENT, {
                            arg: "value",
                            value: e
                        }), e.length % 2 != 0 && a.throwError("hex data length must be even", a.INVALID_ARGUMENT, {
                            arg: "value",
                            value: e
                        }), t = 2 + 2 * t, null != r ? "0x" + e.substring(t, 2 + 2 * r) : "0x" + e.substring(t)
                    }, t.hexStripZeros = function (e) {
                        for (d(e) || a.throwError("invalid hex string", a.INVALID_ARGUMENT, {
                            arg: "value",
                            value: e
                        }); e.length > 3 && "0x0" === e.substring(0, 3);) e = "0x" + e.substring(3);
                        return e
                    }, t.hexZeroPad = p, t.splitSignature = b, t.joinSignature = function (e) {
                        return l(f([(e = b(e)).r, e.s, e.recoveryParam ? "0x1c" : "0x1b"]))
                    }
                }, function (e, t, r) {
                    Object.defineProperty(t, "__esModule", {value: !0});
                    var n = r(101);
                    t.UNKNOWN_ERROR = "UNKNOWN_ERROR", t.NOT_IMPLEMENTED = "NOT_IMPLEMENTED", t.MISSING_NEW = "MISSING_NEW", t.CALL_EXCEPTION = "CALL_EXCEPTION", t.INVALID_ARGUMENT = "INVALID_ARGUMENT", t.MISSING_ARGUMENT = "MISSING_ARGUMENT", t.UNEXPECTED_ARGUMENT = "UNEXPECTED_ARGUMENT", t.NUMERIC_FAULT = "NUMERIC_FAULT", t.INSUFFICIENT_FUNDS = "INSUFFICIENT_FUNDS", t.NONCE_EXPIRED = "NONCE_EXPIRED", t.REPLACEMENT_UNDERPRICED = "REPLACEMENT_UNDERPRICED", t.UNSUPPORTED_OPERATION = "UNSUPPORTED_OPERATION";
                    var i = !1, a = !1;

                    function o(e, r, i) {
                        if (a) throw new Error("unknown error");
                        r || (r = t.UNKNOWN_ERROR), i || (i = {});
                        var o = [];
                        Object.keys(i).forEach(function (e) {
                            try {
                                o.push(e + "=" + JSON.stringify(i[e]))
                            } catch (t) {
                                o.push(e + "=" + JSON.stringify(i[e].toString()))
                            }
                        }), o.push("version=" + n.version);
                        var s = e;
                        o.length && (e += " (" + o.join(", ") + ")");
                        var u = new Error(e);
                        throw u.reason = s, u.code = r, Object.keys(i).forEach(function (e) {
                            u[e] = i[e]
                        }), u
                    }

                    t.throwError = o, t.checkNew = function (e, r) {
                        e instanceof r || o("missing new", t.MISSING_NEW, {name: r.name})
                    }, t.checkArgumentCount = function (e, r, n) {
                        n || (n = ""), e < r && o("missing argument" + n, t.MISSING_ARGUMENT, {
                            count: e,
                            expectedCount: r
                        }), e > r && o("too many arguments" + n, t.UNEXPECTED_ARGUMENT, {count: e, expectedCount: r})
                    }, t.setCensorship = function (e, r) {
                        i && o("error censorship permanent", t.UNSUPPORTED_OPERATION, {operation: "setCensorship"}), a = !!e, i = !!r
                    }, t.checkNormalize = function () {
                        try {
                            if (["NFD", "NFC", "NFKD", "NFKC"].forEach(function (e) {
                                try {
                                    "test".normalize(e)
                                } catch (t) {
                                    throw new Error("missing " + e)
                                }
                            }), String.fromCharCode(233).normalize("NFD") !== String.fromCharCode(101, 769)) throw new Error("broken implementation")
                        } catch (e) {
                            o("platform missing String.prototype.normalize", t.UNSUPPORTED_OPERATION, {
                                operation: "String.prototype.normalize",
                                form: e.message
                            })
                        }
                    };
                    var s = {debug: 1, default: 2, info: 2, warn: 3, error: 4, off: 5}, u = s.default;

                    function c(e, t) {
                        u > s[e] || console.log.apply(console, t)
                    }

                    function f() {
                        for (var e = [], t = 0; t < arguments.length; t++) e[t] = arguments[t];
                        c("warn", e)
                    }

                    t.setLogLevel = function (e) {
                        var t = s[e];
                        null != t ? u = t : f("invliad log level - " + e)
                    }, t.warn = f, t.info = function () {
                        for (var e = [], t = 0; t < arguments.length; t++) e[t] = arguments[t];
                        c("info", e)
                    }
                }, function (e, t, r) {
                    var n = r(135), i = r(136), a = r(137);
                    e.exports = function (e, t) {
                        return n(e) || i(e, t) || a()
                    }
                }, function (e, t) {
                    e.exports = function (e) {
                        if (void 0 === e) throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
                        return e
                    }
                }, function (e, t, r) {
                    var n = r(97), i = r(98), a = r(99);
                    e.exports = function (e) {
                        return n(e) || i(e) || a()
                    }
                }, function (e, t, r) {
                    var i;
                    !function (a) {
                        var o, s = /^-?(?:\d+(?:\.\d*)?|\.\d+)(?:e[+-]?\d+)?$/i, u = Math.ceil, c = Math.floor,
                            f = "[BigNumber Error] ", d = f + "Number primitive has more than 15 significant digits: ",
                            h = 1e14, l = 14, p = 9007199254740991,
                            b = [1, 10, 100, 1e3, 1e4, 1e5, 1e6, 1e7, 1e8, 1e9, 1e10, 1e11, 1e12, 1e13], v = 1e7,
                            g = 1e9;

                        function m(e) {
                            var t = 0 | e;
                            return e > 0 || e === t ? t : t - 1
                        }

                        function y(e) {
                            for (var t, r, n = 1, i = e.length, a = e[0] + ""; n < i;) {
                                for (t = e[n++] + "", r = l - t.length; r--; t = "0" + t) ;
                                a += t
                            }
                            for (i = a.length; 48 === a.charCodeAt(--i);) ;
                            return a.slice(0, i + 1 || 1)
                        }

                        function w(e, t) {
                            var r, n, i = e.c, a = t.c, o = e.s, s = t.s, u = e.e, c = t.e;
                            if (!o || !s) return null;
                            if (r = i && !i[0], n = a && !a[0], r || n) return r ? n ? 0 : -s : o;
                            if (o != s) return o;
                            if (r = o < 0, n = u == c, !i || !a) return n ? 0 : !i ^ r ? 1 : -1;
                            if (!n) return u > c ^ r ? 1 : -1;
                            for (s = (u = i.length) < (c = a.length) ? u : c, o = 0; o < s; o++) if (i[o] != a[o]) return i[o] > a[o] ^ r ? 1 : -1;
                            return u == c ? 0 : u > c ^ r ? 1 : -1
                        }

                        function x(e, t, r, n) {
                            if (e < t || e > r || e !== (e < 0 ? u(e) : c(e))) throw Error(f + (n || "Argument") + ("number" == typeof e ? e < t || e > r ? " out of range: " : " not an integer: " : " not a primitive number: ") + e)
                        }

                        function _(e) {
                            return "[object Array]" == Object.prototype.toString.call(e)
                        }

                        function A(e) {
                            var t = e.c.length - 1;
                            return m(e.e / l) == t && e.c[t] % 2 != 0
                        }

                        function k(e, t) {
                            return (e.length > 1 ? e.charAt(0) + "." + e.slice(1) : e) + (t < 0 ? "e" : "e+") + t
                        }

                        function S(e, t, r) {
                            var n, i;
                            if (t < 0) {
                                for (i = r + "."; ++t; i += r) ;
                                e = i + e
                            } else if (++t > (n = e.length)) {
                                for (i = r, t -= n; --t; i += r) ;
                                e += i
                            } else t < n && (e = e.slice(0, t) + "." + e.slice(t));
                            return e
                        }

                        (o = function e(t) {
                            var r, i, a, o, M, I, E, N, P,
                                T = H.prototype = {constructor: H, toString: null, valueOf: null}, O = new H(1), R = 20,
                                j = 4, C = -7, B = 21, L = -1e7, W = 1e7, F = !1, U = 1, D = 0, q = {
                                    decimalSeparator: ".",
                                    groupSeparator: ",",
                                    groupSize: 3,
                                    secondaryGroupSize: 0,
                                    fractionGroupSeparator: " ",
                                    fractionGroupSize: 0
                                }, z = "0123456789abcdefghijklmnopqrstuvwxyz";

                            function H(e, t) {
                                var r, n, o, u, f, h, b, v, g = this;
                                if (!(g instanceof H)) return new H(e, t);
                                if (null == t) {
                                    if (e instanceof H) return g.s = e.s, g.e = e.e, void (g.c = (e = e.c) ? e.slice() : e);
                                    if ((h = "number" == typeof e) && 0 * e == 0) {
                                        if (g.s = 1 / e < 0 ? (e = -e, -1) : 1, e === ~~e) {
                                            for (u = 0, f = e; f >= 10; f /= 10, u++) ;
                                            return g.e = u, void (g.c = [e])
                                        }
                                        v = e + ""
                                    } else {
                                        if (!s.test(v = e + "")) return a(g, v, h);
                                        g.s = 45 == v.charCodeAt(0) ? (v = v.slice(1), -1) : 1
                                    }
                                    (u = v.indexOf(".")) > -1 && (v = v.replace(".", "")), (f = v.search(/e/i)) > 0 ? (u < 0 && (u = f), u += +v.slice(f + 1), v = v.substring(0, f)) : u < 0 && (u = v.length)
                                } else {
                                    if (x(t, 2, z.length, "Base"), v = e + "", 10 == t) return Y(g = new H(e instanceof H ? e : v), R + g.e + 1, j);
                                    if (h = "number" == typeof e) {
                                        if (0 * e != 0) return a(g, v, h, t);
                                        if (g.s = 1 / e < 0 ? (v = v.slice(1), -1) : 1, H.DEBUG && v.replace(/^0\.0*|\./, "").length > 15) throw Error(d + e);
                                        h = !1
                                    } else g.s = 45 === v.charCodeAt(0) ? (v = v.slice(1), -1) : 1;
                                    for (r = z.slice(0, t), u = f = 0, b = v.length; f < b; f++) if (r.indexOf(n = v.charAt(f)) < 0) {
                                        if ("." == n) {
                                            if (f > u) {
                                                u = b;
                                                continue
                                            }
                                        } else if (!o && (v == v.toUpperCase() && (v = v.toLowerCase()) || v == v.toLowerCase() && (v = v.toUpperCase()))) {
                                            o = !0, f = -1, u = 0;
                                            continue
                                        }
                                        return a(g, e + "", h, t)
                                    }
                                    (u = (v = i(v, t, 10, g.s)).indexOf(".")) > -1 ? v = v.replace(".", "") : u = v.length
                                }
                                for (f = 0; 48 === v.charCodeAt(f); f++) ;
                                for (b = v.length; 48 === v.charCodeAt(--b);) ;
                                if (v = v.slice(f, ++b)) {
                                    if (b -= f, h && H.DEBUG && b > 15 && (e > p || e !== c(e))) throw Error(d + g.s * e);
                                    if ((u = u - f - 1) > W) g.c = g.e = null; else if (u < L) g.c = [g.e = 0]; else {
                                        if (g.e = u, g.c = [], f = (u + 1) % l, u < 0 && (f += l), f < b) {
                                            for (f && g.c.push(+v.slice(0, f)), b -= l; f < b;) g.c.push(+v.slice(f, f += l));
                                            v = v.slice(f), f = l - v.length
                                        } else f -= b;
                                        for (; f--; v += "0") ;
                                        g.c.push(+v)
                                    }
                                } else g.c = [g.e = 0]
                            }

                            function V(e, t, r, n) {
                                var i, a, o, s, u;
                                if (null == r ? r = j : x(r, 0, 8), !e.c) return e.toString();
                                if (i = e.c[0], o = e.e, null == t) u = y(e.c), u = 1 == n || 2 == n && o <= C ? k(u, o) : S(u, o, "0"); else if (a = (e = Y(new H(e), t, r)).e, s = (u = y(e.c)).length, 1 == n || 2 == n && (t <= a || a <= C)) {
                                    for (; s < t; u += "0", s++) ;
                                    u = k(u, a)
                                } else if (t -= o, u = S(u, a, "0"), a + 1 > s) {
                                    if (--t > 0) for (u += "."; t--; u += "0") ;
                                } else if ((t += a - s) > 0) for (a + 1 == s && (u += "."); t--; u += "0") ;
                                return e.s < 0 && i ? "-" + u : u
                            }

                            function K(e, t) {
                                var r, n, i = 0;
                                for (_(e[0]) && (e = e[0]), r = new H(e[0]); ++i < e.length;) {
                                    if (!(n = new H(e[i])).s) {
                                        r = n;
                                        break
                                    }
                                    t.call(r, n) && (r = n)
                                }
                                return r
                            }

                            function G(e, t, r) {
                                for (var n = 1, i = t.length; !t[--i]; t.pop()) ;
                                for (i = t[0]; i >= 10; i /= 10, n++) ;
                                return (r = n + r * l - 1) > W ? e.c = e.e = null : r < L ? e.c = [e.e = 0] : (e.e = r, e.c = t), e
                            }

                            function Y(e, t, r, n) {
                                var i, a, o, s, f, d, p, v = e.c, g = b;
                                if (v) {
                                    e:{
                                        for (i = 1, s = v[0]; s >= 10; s /= 10, i++) ;
                                        if ((a = t - i) < 0) a += l, o = t, p = (f = v[d = 0]) / g[i - o - 1] % 10 | 0; else if ((d = u((a + 1) / l)) >= v.length) {
                                            if (!n) break e;
                                            for (; v.length <= d; v.push(0)) ;
                                            f = p = 0, i = 1, o = (a %= l) - l + 1
                                        } else {
                                            for (f = s = v[d], i = 1; s >= 10; s /= 10, i++) ;
                                            p = (o = (a %= l) - l + i) < 0 ? 0 : f / g[i - o - 1] % 10 | 0
                                        }
                                        if (n = n || t < 0 || null != v[d + 1] || (o < 0 ? f : f % g[i - o - 1]), n = r < 4 ? (p || n) && (0 == r || r == (e.s < 0 ? 3 : 2)) : p > 5 || 5 == p && (4 == r || n || 6 == r && (a > 0 ? o > 0 ? f / g[i - o] : 0 : v[d - 1]) % 10 & 1 || r == (e.s < 0 ? 8 : 7)), t < 1 || !v[0]) return v.length = 0, n ? (t -= e.e + 1, v[0] = g[(l - t % l) % l], e.e = -t || 0) : v[0] = e.e = 0, e;
                                        if (0 == a ? (v.length = d, s = 1, d--) : (v.length = d + 1, s = g[l - a], v[d] = o > 0 ? c(f / g[i - o] % g[o]) * s : 0), n) for (; ;) {
                                            if (0 == d) {
                                                for (a = 1, o = v[0]; o >= 10; o /= 10, a++) ;
                                                for (o = v[0] += s, s = 1; o >= 10; o /= 10, s++) ;
                                                a != s && (e.e++, v[0] == h && (v[0] = 1));
                                                break
                                            }
                                            if (v[d] += s, v[d] != h) break;
                                            v[d--] = 0, s = 1
                                        }
                                        for (a = v.length; 0 === v[--a]; v.pop()) ;
                                    }
                                    e.e > W ? e.c = e.e = null : e.e < L && (e.c = [e.e = 0])
                                }
                                return e
                            }

                            return H.clone = e, H.ROUND_UP = 0, H.ROUND_DOWN = 1, H.ROUND_CEIL = 2, H.ROUND_FLOOR = 3, H.ROUND_HALF_UP = 4, H.ROUND_HALF_DOWN = 5, H.ROUND_HALF_EVEN = 6, H.ROUND_HALF_CEIL = 7, H.ROUND_HALF_FLOOR = 8, H.EUCLID = 9, H.config = H.set = function (e) {
                                var t, r;
                                if (null != e) {
                                    if ("object" != n()(e)) throw Error(f + "Object expected: " + e);
                                    if (e.hasOwnProperty(t = "DECIMAL_PLACES") && (x(r = e[t], 0, g, t), R = r), e.hasOwnProperty(t = "ROUNDING_MODE") && (x(r = e[t], 0, 8, t), j = r), e.hasOwnProperty(t = "EXPONENTIAL_AT") && (_(r = e[t]) ? (x(r[0], -g, 0, t), x(r[1], 0, g, t), C = r[0], B = r[1]) : (x(r, -g, g, t), C = -(B = r < 0 ? -r : r))), e.hasOwnProperty(t = "RANGE")) if (_(r = e[t])) x(r[0], -g, -1, t), x(r[1], 1, g, t), L = r[0], W = r[1]; else {
                                        if (x(r, -g, g, t), !r) throw Error(f + t + " cannot be zero: " + r);
                                        L = -(W = r < 0 ? -r : r)
                                    }
                                    if (e.hasOwnProperty(t = "CRYPTO")) {
                                        if ((r = e[t]) !== !!r) throw Error(f + t + " not true or false: " + r);
                                        if (r) {
                                            if ("undefined" == typeof crypto || !crypto || !crypto.getRandomValues && !crypto.randomBytes) throw F = !r, Error(f + "crypto unavailable");
                                            F = r
                                        } else F = r
                                    }
                                    if (e.hasOwnProperty(t = "MODULO_MODE") && (x(r = e[t], 0, 9, t), U = r), e.hasOwnProperty(t = "POW_PRECISION") && (x(r = e[t], 0, g, t), D = r), e.hasOwnProperty(t = "FORMAT")) {
                                        if ("object" != n()(r = e[t])) throw Error(f + t + " not an object: " + r);
                                        q = r
                                    }
                                    if (e.hasOwnProperty(t = "ALPHABET")) {
                                        if ("string" != typeof (r = e[t]) || /^.$|\.|(.).*\1/.test(r)) throw Error(f + t + " invalid: " + r);
                                        z = r
                                    }
                                }
                                return {
                                    DECIMAL_PLACES: R,
                                    ROUNDING_MODE: j,
                                    EXPONENTIAL_AT: [C, B],
                                    RANGE: [L, W],
                                    CRYPTO: F,
                                    MODULO_MODE: U,
                                    POW_PRECISION: D,
                                    FORMAT: q,
                                    ALPHABET: z
                                }
                            }, H.isBigNumber = function (e) {
                                return e instanceof H || e && !0 === e._isBigNumber || !1
                            }, H.maximum = H.max = function () {
                                return K(arguments, T.lt)
                            }, H.minimum = H.min = function () {
                                return K(arguments, T.gt)
                            }, H.random = (o = 9007199254740992 * Math.random() & 2097151 ? function () {
                                return c(9007199254740992 * Math.random())
                            } : function () {
                                return 8388608 * (1073741824 * Math.random() | 0) + (8388608 * Math.random() | 0)
                            }, function (e) {
                                var t, r, n, i, a, s = 0, d = [], h = new H(O);
                                if (null == e ? e = R : x(e, 0, g), i = u(e / l), F) if (crypto.getRandomValues) {
                                    for (t = crypto.getRandomValues(new Uint32Array(i *= 2)); s < i;) (a = 131072 * t[s] + (t[s + 1] >>> 11)) >= 9e15 ? (r = crypto.getRandomValues(new Uint32Array(2)), t[s] = r[0], t[s + 1] = r[1]) : (d.push(a % 1e14), s += 2);
                                    s = i / 2
                                } else {
                                    if (!crypto.randomBytes) throw F = !1, Error(f + "crypto unavailable");
                                    for (t = crypto.randomBytes(i *= 7); s < i;) (a = 281474976710656 * (31 & t[s]) + 1099511627776 * t[s + 1] + 4294967296 * t[s + 2] + 16777216 * t[s + 3] + (t[s + 4] << 16) + (t[s + 5] << 8) + t[s + 6]) >= 9e15 ? crypto.randomBytes(7).copy(t, s) : (d.push(a % 1e14), s += 7);
                                    s = i / 7
                                }
                                if (!F) for (; s < i;) (a = o()) < 9e15 && (d[s++] = a % 1e14);
                                for (i = d[--s], e %= l, i && e && (a = b[l - e], d[s] = c(i / a) * a); 0 === d[s]; d.pop(), s--) ;
                                if (s < 0) d = [n = 0]; else {
                                    for (n = -1; 0 === d[0]; d.splice(0, 1), n -= l) ;
                                    for (s = 1, a = d[0]; a >= 10; a /= 10, s++) ;
                                    s < l && (n -= l - s)
                                }
                                return h.e = n, h.c = d, h
                            }), i = function () {
                                function e(e, t, r, n) {
                                    for (var i, a, o = [0], s = 0, u = e.length; s < u;) {
                                        for (a = o.length; a--; o[a] *= t) ;
                                        for (o[0] += n.indexOf(e.charAt(s++)), i = 0; i < o.length; i++) o[i] > r - 1 && (null == o[i + 1] && (o[i + 1] = 0), o[i + 1] += o[i] / r | 0, o[i] %= r)
                                    }
                                    return o.reverse()
                                }

                                return function (t, n, i, a, o) {
                                    var s, u, c, f, d, h, l, p, b = t.indexOf("."), v = R, g = j;
                                    for (b >= 0 && (f = D, D = 0, t = t.replace(".", ""), h = (p = new H(n)).pow(t.length - b), D = f, p.c = e(S(y(h.c), h.e, "0"), 10, i, "0123456789"), p.e = p.c.length), c = f = (l = e(t, n, i, o ? (s = z, "0123456789") : (s = "0123456789", z))).length; 0 == l[--f]; l.pop()) ;
                                    if (!l[0]) return s.charAt(0);
                                    if (b < 0 ? --c : (h.c = l, h.e = c, h.s = a, l = (h = r(h, p, v, g, i)).c, d = h.r, c = h.e), b = l[u = c + v + 1], f = i / 2, d = d || u < 0 || null != l[u + 1], d = g < 4 ? (null != b || d) && (0 == g || g == (h.s < 0 ? 3 : 2)) : b > f || b == f && (4 == g || d || 6 == g && 1 & l[u - 1] || g == (h.s < 0 ? 8 : 7)), u < 1 || !l[0]) t = d ? S(s.charAt(1), -v, s.charAt(0)) : s.charAt(0); else {
                                        if (l.length = u, d) for (--i; ++l[--u] > i;) l[u] = 0, u || (++c, l = [1].concat(l));
                                        for (f = l.length; !l[--f];) ;
                                        for (b = 0, t = ""; b <= f; t += s.charAt(l[b++])) ;
                                        t = S(t, c, s.charAt(0))
                                    }
                                    return t
                                }
                            }(), r = function () {
                                function e(e, t, r) {
                                    var n, i, a, o, s = 0, u = e.length, c = t % v, f = t / v | 0;
                                    for (e = e.slice(); u--;) s = ((i = c * (a = e[u] % v) + (n = f * a + (o = e[u] / v | 0) * c) % v * v + s) / r | 0) + (n / v | 0) + f * o, e[u] = i % r;
                                    return s && (e = [s].concat(e)), e
                                }

                                function t(e, t, r, n) {
                                    var i, a;
                                    if (r != n) a = r > n ? 1 : -1; else for (i = a = 0; i < r; i++) if (e[i] != t[i]) {
                                        a = e[i] > t[i] ? 1 : -1;
                                        break
                                    }
                                    return a
                                }

                                function r(e, t, r, n) {
                                    for (var i = 0; r--;) e[r] -= i, i = e[r] < t[r] ? 1 : 0, e[r] = i * n + e[r] - t[r];
                                    for (; !e[0] && e.length > 1; e.splice(0, 1)) ;
                                }

                                return function (n, i, a, o, s) {
                                    var u, f, d, p, b, v, g, y, w, x, _, A, k, S, M, I, E, N = n.s == i.s ? 1 : -1,
                                        P = n.c, T = i.c;
                                    if (!(P && P[0] && T && T[0])) return new H(n.s && i.s && (P ? !T || P[0] != T[0] : T) ? P && 0 == P[0] || !T ? 0 * N : N / 0 : NaN);
                                    for (w = (y = new H(N)).c = [], N = a + (f = n.e - i.e) + 1, s || (s = h, f = m(n.e / l) - m(i.e / l), N = N / l | 0), d = 0; T[d] == (P[d] || 0); d++) ;
                                    if (T[d] > (P[d] || 0) && f--, N < 0) w.push(1), p = !0; else {
                                        for (S = P.length, I = T.length, d = 0, N += 2, (b = c(s / (T[0] + 1))) > 1 && (T = e(T, b, s), P = e(P, b, s), I = T.length, S = P.length), k = I, _ = (x = P.slice(0, I)).length; _ < I; x[_++] = 0) ;
                                        E = T.slice(), E = [0].concat(E), M = T[0], T[1] >= s / 2 && M++;
                                        do {
                                            if (b = 0, (u = t(T, x, I, _)) < 0) {
                                                if (A = x[0], I != _ && (A = A * s + (x[1] || 0)), (b = c(A / M)) > 1) for (b >= s && (b = s - 1), g = (v = e(T, b, s)).length, _ = x.length; 1 == t(v, x, g, _);) b--, r(v, I < g ? E : T, g, s), g = v.length, u = 1; else 0 == b && (u = b = 1), g = (v = T.slice()).length;
                                                if (g < _ && (v = [0].concat(v)), r(x, v, _, s), _ = x.length, -1 == u) for (; t(T, x, I, _) < 1;) b++, r(x, I < _ ? E : T, _, s), _ = x.length
                                            } else 0 === u && (b++, x = [0]);
                                            w[d++] = b, x[0] ? x[_++] = P[k] || 0 : (x = [P[k]], _ = 1)
                                        } while ((k++ < S || null != x[0]) && N--);
                                        p = null != x[0], w[0] || w.splice(0, 1)
                                    }
                                    if (s == h) {
                                        for (d = 1, N = w[0]; N >= 10; N /= 10, d++) ;
                                        Y(y, a + (y.e = d + f * l - 1) + 1, o, p)
                                    } else y.e = f, y.r = +p;
                                    return y
                                }
                            }(), M = /^(-?)0([xbo])(?=\w[\w.]*$)/i, I = /^([^.]+)\.$/, E = /^\.([^.]+)$/, N = /^-?(Infinity|NaN)$/, P = /^\s*\+(?=[\w.])|^\s+|\s+$/g, a = function (e, t, r, n) {
                                var i, a = r ? t : t.replace(P, "");
                                if (N.test(a)) e.s = isNaN(a) ? null : a < 0 ? -1 : 1, e.c = e.e = null; else {
                                    if (!r && (a = a.replace(M, function (e, t, r) {
                                        return i = "x" == (r = r.toLowerCase()) ? 16 : "b" == r ? 2 : 8, n && n != i ? e : t
                                    }), n && (i = n, a = a.replace(I, "$1").replace(E, "0.$1")), t != a)) return new H(a, i);
                                    if (H.DEBUG) throw Error(f + "Not a" + (n ? " base " + n : "") + " number: " + t);
                                    e.c = e.e = e.s = null
                                }
                            }, T.absoluteValue = T.abs = function () {
                                var e = new H(this);
                                return e.s < 0 && (e.s = 1), e
                            }, T.comparedTo = function (e, t) {
                                return w(this, new H(e, t))
                            }, T.decimalPlaces = T.dp = function (e, t) {
                                var r, n, i, a = this;
                                if (null != e) return x(e, 0, g), null == t ? t = j : x(t, 0, 8), Y(new H(a), e + a.e + 1, t);
                                if (!(r = a.c)) return null;
                                if (n = ((i = r.length - 1) - m(this.e / l)) * l, i = r[i]) for (; i % 10 == 0; i /= 10, n--) ;
                                return n < 0 && (n = 0), n
                            }, T.dividedBy = T.div = function (e, t) {
                                return r(this, new H(e, t), R, j)
                            }, T.dividedToIntegerBy = T.idiv = function (e, t) {
                                return r(this, new H(e, t), 0, 1)
                            }, T.exponentiatedBy = T.pow = function (e, t) {
                                var r, n, i, a, o, s, d, h = this;
                                if ((e = new H(e)).c && !e.isInteger()) throw Error(f + "Exponent not an integer: " + e);
                                if (null != t && (t = new H(t)), a = e.e > 14, !h.c || !h.c[0] || 1 == h.c[0] && !h.e && 1 == h.c.length || !e.c || !e.c[0]) return d = new H(Math.pow(+h.valueOf(), a ? 2 - A(e) : +e)), t ? d.mod(t) : d;
                                if (o = e.s < 0, t) {
                                    if (t.c ? !t.c[0] : !t.s) return new H(NaN);
                                    (n = !o && h.isInteger() && t.isInteger()) && (h = h.mod(t))
                                } else {
                                    if (e.e > 9 && (h.e > 0 || h.e < -1 || (0 == h.e ? h.c[0] > 1 || a && h.c[1] >= 24e7 : h.c[0] < 8e13 || a && h.c[0] <= 9999975e7))) return i = h.s < 0 && A(e) ? -0 : 0, h.e > -1 && (i = 1 / i), new H(o ? 1 / i : i);
                                    D && (i = u(D / l + 2))
                                }
                                for (a ? (r = new H(.5), s = A(e)) : s = e % 2, o && (e.s = 1), d = new H(O); ;) {
                                    if (s) {
                                        if (!(d = d.times(h)).c) break;
                                        i ? d.c.length > i && (d.c.length = i) : n && (d = d.mod(t))
                                    }
                                    if (a) {
                                        if (Y(e = e.times(r), e.e + 1, 1), !e.c[0]) break;
                                        a = e.e > 14, s = A(e)
                                    } else {
                                        if (!(e = c(e / 2))) break;
                                        s = e % 2
                                    }
                                    h = h.times(h), i ? h.c && h.c.length > i && (h.c.length = i) : n && (h = h.mod(t))
                                }
                                return n ? d : (o && (d = O.div(d)), t ? d.mod(t) : i ? Y(d, D, j, void 0) : d)
                            }, T.integerValue = function (e) {
                                var t = new H(this);
                                return null == e ? e = j : x(e, 0, 8), Y(t, t.e + 1, e)
                            }, T.isEqualTo = T.eq = function (e, t) {
                                return 0 === w(this, new H(e, t))
                            }, T.isFinite = function () {
                                return !!this.c
                            }, T.isGreaterThan = T.gt = function (e, t) {
                                return w(this, new H(e, t)) > 0
                            }, T.isGreaterThanOrEqualTo = T.gte = function (e, t) {
                                return 1 === (t = w(this, new H(e, t))) || 0 === t
                            }, T.isInteger = function () {
                                return !!this.c && m(this.e / l) > this.c.length - 2
                            }, T.isLessThan = T.lt = function (e, t) {
                                return w(this, new H(e, t)) < 0
                            }, T.isLessThanOrEqualTo = T.lte = function (e, t) {
                                return -1 === (t = w(this, new H(e, t))) || 0 === t
                            }, T.isNaN = function () {
                                return !this.s
                            }, T.isNegative = function () {
                                return this.s < 0
                            }, T.isPositive = function () {
                                return this.s > 0
                            }, T.isZero = function () {
                                return !!this.c && 0 == this.c[0]
                            }, T.minus = function (e, t) {
                                var r, n, i, a, o = this, s = o.s;
                                if (t = (e = new H(e, t)).s, !s || !t) return new H(NaN);
                                if (s != t) return e.s = -t, o.plus(e);
                                var u = o.e / l, c = e.e / l, f = o.c, d = e.c;
                                if (!u || !c) {
                                    if (!f || !d) return f ? (e.s = -t, e) : new H(d ? o : NaN);
                                    if (!f[0] || !d[0]) return d[0] ? (e.s = -t, e) : new H(f[0] ? o : 3 == j ? -0 : 0)
                                }
                                if (u = m(u), c = m(c), f = f.slice(), s = u - c) {
                                    for ((a = s < 0) ? (s = -s, i = f) : (c = u, i = d), i.reverse(), t = s; t--; i.push(0)) ;
                                    i.reverse()
                                } else for (n = (a = (s = f.length) < (t = d.length)) ? s : t, s = t = 0; t < n; t++) if (f[t] != d[t]) {
                                    a = f[t] < d[t];
                                    break
                                }
                                if (a && (i = f, f = d, d = i, e.s = -e.s), (t = (n = d.length) - (r = f.length)) > 0) for (; t--; f[r++] = 0) ;
                                for (t = h - 1; n > s;) {
                                    if (f[--n] < d[n]) {
                                        for (r = n; r && !f[--r]; f[r] = t) ;
                                        --f[r], f[n] += h
                                    }
                                    f[n] -= d[n]
                                }
                                for (; 0 == f[0]; f.splice(0, 1), --c) ;
                                return f[0] ? G(e, f, c) : (e.s = 3 == j ? -1 : 1, e.c = [e.e = 0], e)
                            }, T.modulo = T.mod = function (e, t) {
                                var n, i, a = this;
                                return e = new H(e, t), !a.c || !e.s || e.c && !e.c[0] ? new H(NaN) : !e.c || a.c && !a.c[0] ? new H(a) : (9 == U ? (i = e.s, e.s = 1, n = r(a, e, 0, 3), e.s = i, n.s *= i) : n = r(a, e, 0, U), (e = a.minus(n.times(e))).c[0] || 1 != U || (e.s = a.s), e)
                            }, T.multipliedBy = T.times = function (e, t) {
                                var r, n, i, a, o, s, u, c, f, d, p, b, g, y, w, x = this, _ = x.c,
                                    A = (e = new H(e, t)).c;
                                if (!(_ && A && _[0] && A[0])) return !x.s || !e.s || _ && !_[0] && !A || A && !A[0] && !_ ? e.c = e.e = e.s = null : (e.s *= x.s, _ && A ? (e.c = [0], e.e = 0) : e.c = e.e = null), e;
                                for (n = m(x.e / l) + m(e.e / l), e.s *= x.s, (u = _.length) < (d = A.length) && (g = _, _ = A, A = g, i = u, u = d, d = i), i = u + d, g = []; i--; g.push(0)) ;
                                for (y = h, w = v, i = d; --i >= 0;) {
                                    for (r = 0, p = A[i] % w, b = A[i] / w | 0, a = i + (o = u); a > i;) r = ((c = p * (c = _[--o] % w) + (s = b * c + (f = _[o] / w | 0) * p) % w * w + g[a] + r) / y | 0) + (s / w | 0) + b * f, g[a--] = c % y;
                                    g[a] = r
                                }
                                return r ? ++n : g.splice(0, 1), G(e, g, n)
                            }, T.negated = function () {
                                var e = new H(this);
                                return e.s = -e.s || null, e
                            }, T.plus = function (e, t) {
                                var r, n = this, i = n.s;
                                if (t = (e = new H(e, t)).s, !i || !t) return new H(NaN);
                                if (i != t) return e.s = -t, n.minus(e);
                                var a = n.e / l, o = e.e / l, s = n.c, u = e.c;
                                if (!a || !o) {
                                    if (!s || !u) return new H(i / 0);
                                    if (!s[0] || !u[0]) return u[0] ? e : new H(s[0] ? n : 0 * i)
                                }
                                if (a = m(a), o = m(o), s = s.slice(), i = a - o) {
                                    for (i > 0 ? (o = a, r = u) : (i = -i, r = s), r.reverse(); i--; r.push(0)) ;
                                    r.reverse()
                                }
                                for ((i = s.length) - (t = u.length) < 0 && (r = u, u = s, s = r, t = i), i = 0; t;) i = (s[--t] = s[t] + u[t] + i) / h | 0, s[t] = h === s[t] ? 0 : s[t] % h;
                                return i && (s = [i].concat(s), ++o), G(e, s, o)
                            }, T.precision = T.sd = function (e, t) {
                                var r, n, i, a = this;
                                if (null != e && e !== !!e) return x(e, 1, g), null == t ? t = j : x(t, 0, 8), Y(new H(a), e, t);
                                if (!(r = a.c)) return null;
                                if (n = (i = r.length - 1) * l + 1, i = r[i]) {
                                    for (; i % 10 == 0; i /= 10, n--) ;
                                    for (i = r[0]; i >= 10; i /= 10, n++) ;
                                }
                                return e && a.e + 1 > n && (n = a.e + 1), n
                            }, T.shiftedBy = function (e) {
                                return x(e, -p, p), this.times("1e" + e)
                            }, T.squareRoot = T.sqrt = function () {
                                var e, t, n, i, a, o = this, s = o.c, u = o.s, c = o.e, f = R + 4, d = new H("0.5");
                                if (1 !== u || !s || !s[0]) return new H(!u || u < 0 && (!s || s[0]) ? NaN : s ? o : 1 / 0);
                                if (0 == (u = Math.sqrt(+o)) || u == 1 / 0 ? (((t = y(s)).length + c) % 2 == 0 && (t += "0"), u = Math.sqrt(t), c = m((c + 1) / 2) - (c < 0 || c % 2), n = new H(t = u == 1 / 0 ? "1e" + c : (t = u.toExponential()).slice(0, t.indexOf("e") + 1) + c)) : n = new H(u + ""), n.c[0]) for ((u = (c = n.e) + f) < 3 && (u = 0); ;) if (a = n, n = d.times(a.plus(r(o, a, f, 1))), y(a.c).slice(0, u) === (t = y(n.c)).slice(0, u)) {
                                    if (n.e < c && --u, "9999" != (t = t.slice(u - 3, u + 1)) && (i || "4999" != t)) {
                                        +t && (+t.slice(1) || "5" != t.charAt(0)) || (Y(n, n.e + R + 2, 1), e = !n.times(n).eq(o));
                                        break
                                    }
                                    if (!i && (Y(a, a.e + R + 2, 0), a.times(a).eq(o))) {
                                        n = a;
                                        break
                                    }
                                    f += 4, u += 4, i = 1
                                }
                                return Y(n, n.e + R + 1, j, e)
                            }, T.toExponential = function (e, t) {
                                return null != e && (x(e, 0, g), e++), V(this, e, t, 1)
                            }, T.toFixed = function (e, t) {
                                return null != e && (x(e, 0, g), e = e + this.e + 1), V(this, e, t)
                            }, T.toFormat = function (e, t) {
                                var r = this.toFixed(e, t);
                                if (this.c) {
                                    var n, i = r.split("."), a = +q.groupSize, o = +q.secondaryGroupSize,
                                        s = q.groupSeparator, u = i[0], c = i[1], f = this.s < 0,
                                        d = f ? u.slice(1) : u, h = d.length;
                                    if (o && (n = a, a = o, o = n, h -= n), a > 0 && h > 0) {
                                        for (n = h % a || a, u = d.substr(0, n); n < h; n += a) u += s + d.substr(n, a);
                                        o > 0 && (u += s + d.slice(n)), f && (u = "-" + u)
                                    }
                                    r = c ? u + q.decimalSeparator + ((o = +q.fractionGroupSize) ? c.replace(new RegExp("\\d{" + o + "}\\B", "g"), "$&" + q.fractionGroupSeparator) : c) : u
                                }
                                return r
                            }, T.toFraction = function (e) {
                                var t, n, i, a, o, s, u, c, d, h, p, v, g = this, m = g.c;
                                if (null != e && (!(c = new H(e)).isInteger() && (c.c || 1 !== c.s) || c.lt(O))) throw Error(f + "Argument " + (c.isInteger() ? "out of range: " : "not an integer: ") + e);
                                if (!m) return g.toString();
                                for (n = new H(O), h = i = new H(O), a = d = new H(O), v = y(m), s = n.e = v.length - g.e - 1, n.c[0] = b[(u = s % l) < 0 ? l + u : u], e = !e || c.comparedTo(n) > 0 ? s > 0 ? n : h : c, u = W, W = 1 / 0, c = new H(v), d.c[0] = 0; p = r(c, n, 0, 1), 1 != (o = i.plus(p.times(a))).comparedTo(e);) i = a, a = o, h = d.plus(p.times(o = h)), d = o, n = c.minus(p.times(o = n)), c = o;
                                return o = r(e.minus(i), a, 0, 1), d = d.plus(o.times(h)), i = i.plus(o.times(a)), d.s = h.s = g.s, t = r(h, a, s *= 2, j).minus(g).abs().comparedTo(r(d, i, s, j).minus(g).abs()) < 1 ? [h.toString(), a.toString()] : [d.toString(), i.toString()], W = u, t
                            }, T.toNumber = function () {
                                return +this
                            }, T.toPrecision = function (e, t) {
                                return null != e && x(e, 1, g), V(this, e, t, 2)
                            }, T.toString = function (e) {
                                var t, r = this, n = r.s, a = r.e;
                                return null === a ? n ? (t = "Infinity", n < 0 && (t = "-" + t)) : t = "NaN" : (t = y(r.c), null == e ? t = a <= C || a >= B ? k(t, a) : S(t, a, "0") : (x(e, 2, z.length, "Base"), t = i(S(t, a, "0"), 10, e, n, !0)), n < 0 && r.c[0] && (t = "-" + t)), t
                            }, T.valueOf = T.toJSON = function () {
                                var e, t = this, r = t.e;
                                return null === r ? t.toString() : (e = y(t.c), e = r <= C || r >= B ? k(e, r) : S(e, r, "0"), t.s < 0 ? "-" + e : e)
                            }, T._isBigNumber = !0, null != t && H.set(t), H
                        }()).default = o.BigNumber = o, void 0 === (i = function () {
                            return o
                        }.call(t, r, t, e)) || (e.exports = i)
                    }()
                }, function (e, t, r) {
                    r.d(t, "a", function () {
                        return c
                    });
                    var n = r(4), i = r.n(n), a = r(5), o = r.n(a), s = r(6), u = r(0), c = function () {
                        function e() {
                            var t = arguments.length > 0 && void 0 !== arguments[0] && arguments[0];
                            if (i()(this, e), !t || !t instanceof s.default) throw new Error("Expected instance of TronWeb");
                            this.tronWeb = t
                        }

                        return o()(e, [{
                            key: "invalid", value: function (e) {
                                return e.msg || "Invalid ".concat(e.name).concat("address" === e.type ? " address" : "", " provided")
                            }
                        }, {
                            key: "notPositive", value: function (e) {
                                return "".concat(e.name, " must be a positive integer")
                            }
                        }, {
                            key: "notEqual", value: function (e) {
                                return e.msg || "".concat(e.names[0], " can not be equal to ").concat(e.names[1])
                            }
                        }, {
                            key: "notValid", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : [],
                                    t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : new Function,
                                    r = {}, n = !1, i = !0, a = !1, o = void 0;
                                try {
                                    for (var s, c = e[Symbol.iterator](); !(i = (s = c.next()).done); i = !0) {
                                        var f = s.value, d = f.name, h = f.names, l = f.value, p = f.type, b = f.gt,
                                            v = f.lt, g = f.gte, m = f.lte;
                                        if (!(f.se, f.optional) || u.a.isNotNullOrUndefined(l) && ("boolean" === p || !1 !== l)) {
                                            switch (r[f.name] = f.value, p) {
                                                case"address":
                                                    this.tronWeb.isAddress(l) ? r[d] = this.tronWeb.address.toHex(l) : n = !0;
                                                    break;
                                                case"integer":
                                                    (!u.a.isInteger(l) || "number" == typeof b && l <= f.gt || "number" == typeof v && l >= f.lt || "number" == typeof g && l < f.gte || "number" == typeof m && l > f.lte) && (n = !0);
                                                    break;
                                                case"positive-integer":
                                                    if (!u.a.isInteger(l) || l <= 0) return void t(this.notPositive(f));
                                                    break;
                                                case"tokenId":
                                                    u.a.isString(l) && l.length || (n = !0);
                                                    break;
                                                case"notEmptyObject":
                                                    u.a.isObject(l) && Object.keys(l).length || (n = !0);
                                                    break;
                                                case"notEqual":
                                                    if (r[h[0]] === r[h[1]]) return t(this.notEqual(f)), !0;
                                                    break;
                                                case"resource":
                                                    ["BANDWIDTH", "ENERGY"].includes(l) || (n = !0);
                                                    break;
                                                case"url":
                                                    u.a.isValidURL(l) || (n = !0);
                                                    break;
                                                case"hex":
                                                    u.a.isHex(l) || (n = !0);
                                                    break;
                                                case"array":
                                                    Array.isArray(l) || (n = !0);
                                                    break;
                                                case"not-empty-string":
                                                    u.a.isString(l) && l.length || (n = !0);
                                                    break;
                                                case"boolean":
                                                    u.a.isBoolean(l) || (n = !0);
                                                    break;
                                                case"string":
                                                    (!u.a.isString(l) || "number" == typeof b && l.length <= f.gt || "number" == typeof v && l.length >= f.lt || "number" == typeof g && l.length < f.gte || "number" == typeof m && l.length > f.lte) && (n = !0)
                                            }
                                            if (n) return t(this.invalid(f)), !0
                                        }
                                    }
                                } catch (e) {
                                    a = !0, o = e
                                } finally {
                                    try {
                                        i || null == c.return || c.return()
                                    } finally {
                                        if (a) throw o
                                    }
                                }
                                return !1
                            }
                        }]), e
                    }()
                }, function (e, t, r) {
                    Object.defineProperty(t, "__esModule", {value: !0});
                    var n = r(100), i = r(18);
                    t.keccak256 = function (e) {
                        return "0x" + n.keccak_256(i.arrayify(e))
                    }
                }, function (e, t) {
                    "function" == typeof Object.create ? e.exports = function (e, t) {
                        t && (e.super_ = t, e.prototype = Object.create(t.prototype, {
                            constructor: {
                                value: e,
                                enumerable: !1,
                                writable: !0,
                                configurable: !0
                            }
                        }))
                    } : e.exports = function (e, t) {
                        if (t) {
                            e.super_ = t;
                            var r = function () {
                            };
                            r.prototype = t.prototype, e.prototype = new r, e.prototype.constructor = e
                        }
                    }
                }, function (e, t, r) {
                    var n = r(15), i = r(12);

                    function a() {
                        this.pending = null, this.pendingTotal = 0, this.blockSize = this.constructor.blockSize, this.outSize = this.constructor.outSize, this.hmacStrength = this.constructor.hmacStrength, this.padLength = this.constructor.padLength / 8, this.endian = "big", this._delta8 = this.blockSize / 8, this._delta32 = this.blockSize / 32
                    }

                    t.BlockHash = a, a.prototype.update = function (e, t) {
                        if (e = n.toArray(e, t), this.pending ? this.pending = this.pending.concat(e) : this.pending = e, this.pendingTotal += e.length, this.pending.length >= this._delta8) {
                            var r = (e = this.pending).length % this._delta8;
                            this.pending = e.slice(e.length - r, e.length), 0 === this.pending.length && (this.pending = null), e = n.join32(e, 0, e.length - r, this.endian);
                            for (var i = 0; i < e.length; i += this._delta32) this._update(e, i, i + this._delta32)
                        }
                        return this
                    }, a.prototype.digest = function (e) {
                        return this.update(this._pad()), i(null === this.pending), this._digest(e)
                    }, a.prototype._pad = function () {
                        var e = this.pendingTotal, t = this._delta8, r = t - (e + this.padLength) % t,
                            n = new Array(r + this.padLength);
                        n[0] = 128;
                        for (var i = 1; i < r; i++) n[i] = 0;
                        if (e <<= 3, "big" === this.endian) {
                            for (var a = 8; a < this.padLength; a++) n[i++] = 0;
                            n[i++] = 0, n[i++] = 0, n[i++] = 0, n[i++] = 0, n[i++] = e >>> 24 & 255, n[i++] = e >>> 16 & 255, n[i++] = e >>> 8 & 255, n[i++] = 255 & e
                        } else for (n[i++] = 255 & e, n[i++] = e >>> 8 & 255, n[i++] = e >>> 16 & 255, n[i++] = e >>> 24 & 255, n[i++] = 0, n[i++] = 0, n[i++] = 0, n[i++] = 0, a = 8; a < this.padLength; a++) n[i++] = 0;
                        return n
                    }
                }, function (e, t, r) {
                    var n = r(16), i = r(12);

                    function a() {
                        this.pending = null, this.pendingTotal = 0, this.blockSize = this.constructor.blockSize, this.outSize = this.constructor.outSize, this.hmacStrength = this.constructor.hmacStrength, this.padLength = this.constructor.padLength / 8, this.endian = "big", this._delta8 = this.blockSize / 8, this._delta32 = this.blockSize / 32
                    }

                    t.BlockHash = a, a.prototype.update = function (e, t) {
                        if (e = n.toArray(e, t), this.pending ? this.pending = this.pending.concat(e) : this.pending = e, this.pendingTotal += e.length, this.pending.length >= this._delta8) {
                            var r = (e = this.pending).length % this._delta8;
                            this.pending = e.slice(e.length - r, e.length), 0 === this.pending.length && (this.pending = null), e = n.join32(e, 0, e.length - r, this.endian);
                            for (var i = 0; i < e.length; i += this._delta32) this._update(e, i, i + this._delta32)
                        }
                        return this
                    }, a.prototype.digest = function (e) {
                        return this.update(this._pad()), i(null === this.pending), this._digest(e)
                    }, a.prototype._pad = function () {
                        var e = this.pendingTotal, t = this._delta8, r = t - (e + this.padLength) % t,
                            n = new Array(r + this.padLength);
                        n[0] = 128;
                        for (var i = 1; i < r; i++) n[i] = 0;
                        if (e <<= 3, "big" === this.endian) {
                            for (var a = 8; a < this.padLength; a++) n[i++] = 0;
                            n[i++] = 0, n[i++] = 0, n[i++] = 0, n[i++] = 0, n[i++] = e >>> 24 & 255, n[i++] = e >>> 16 & 255, n[i++] = e >>> 8 & 255, n[i++] = 255 & e
                        } else for (n[i++] = 255 & e, n[i++] = e >>> 8 & 255, n[i++] = e >>> 16 & 255, n[i++] = e >>> 24 & 255, n[i++] = 0, n[i++] = 0, n[i++] = 0, n[i++] = 0, a = 8; a < this.padLength; a++) n[i++] = 0;
                        return n
                    }
                }, function (e, t, r) {
                    Object.defineProperty(t, "__esModule", {value: !0});
                    var n, i = r(54), a = r(19), o = r(18);

                    function s(e, t) {
                        void 0 === t && (t = n.current), t != n.current && (a.checkNormalize(), e = e.normalize(t));
                        for (var r = [], i = 0; i < e.length; i++) {
                            var s = e.charCodeAt(i);
                            if (s < 128) r.push(s); else if (s < 2048) r.push(s >> 6 | 192), r.push(63 & s | 128); else if (55296 == (64512 & s)) {
                                i++;
                                var u = e.charCodeAt(i);
                                if (i >= e.length || 56320 != (64512 & u)) throw new Error("invalid utf-8 string");
                                s = 65536 + ((1023 & s) << 10) + (1023 & u), r.push(s >> 18 | 240), r.push(s >> 12 & 63 | 128), r.push(s >> 6 & 63 | 128), r.push(63 & s | 128)
                            } else r.push(s >> 12 | 224), r.push(s >> 6 & 63 | 128), r.push(63 & s | 128)
                        }
                        return o.arrayify(r)
                    }

                    function u(e, t) {
                        e = o.arrayify(e);
                        for (var r = "", n = 0; n < e.length;) {
                            var i = e[n++];
                            if (i >> 7 != 0) {
                                var a = null, s = null;
                                if (192 == (224 & i)) a = 1, s = 127; else if (224 == (240 & i)) a = 2, s = 2047; else {
                                    if (240 != (248 & i)) {
                                        if (!t) {
                                            if (128 == (192 & i)) throw new Error("invalid utf8 byte sequence; unexpected continuation byte");
                                            throw new Error("invalid utf8 byte sequence; invalid prefix")
                                        }
                                        continue
                                    }
                                    a = 3, s = 65535
                                }
                                if (n + a > e.length) {
                                    if (!t) throw new Error("invalid utf8 byte sequence; too short");
                                    for (; n < e.length && e[n] >> 6 == 2; n++) ;
                                } else {
                                    for (var u = i & (1 << 8 - a - 1) - 1, c = 0; c < a; c++) {
                                        var f = e[n];
                                        if (128 != (192 & f)) {
                                            u = null;
                                            break
                                        }
                                        u = u << 6 | 63 & f, n++
                                    }
                                    if (null !== u) if (u <= s) {
                                        if (!t) throw new Error("invalid utf8 byte sequence; overlong")
                                    } else if (u > 1114111) {
                                        if (!t) throw new Error("invalid utf8 byte sequence; out-of-range")
                                    } else if (u >= 55296 && u <= 57343) {
                                        if (!t) throw new Error("invalid utf8 byte sequence; utf-16 surrogate")
                                    } else u <= 65535 ? r += String.fromCharCode(u) : (u -= 65536, r += String.fromCharCode(55296 + (u >> 10 & 1023), 56320 + (1023 & u))); else if (!t) throw new Error("invalid utf8 byte sequence; invalid continuation byte")
                                }
                            } else r += String.fromCharCode(i)
                        }
                        return r
                    }

                    !function (e) {
                        e.current = "", e.NFC = "NFC", e.NFD = "NFD", e.NFKC = "NFKC", e.NFKD = "NFKD"
                    }(n = t.UnicodeNormalizationForm || (t.UnicodeNormalizationForm = {})), t.toUtf8Bytes = s, t.toUtf8String = u, t.formatBytes32String = function (e) {
                        var t = s(e);
                        if (t.length > 31) throw new Error("bytes32 string must be less than 32 bytes");
                        return o.hexlify(o.concat([t, i.HashZero]).slice(0, 32))
                    }, t.parseBytes32String = function (e) {
                        var t = o.arrayify(e);
                        if (32 !== t.length) throw new Error("invalid bytes32 - not 32 bytes long");
                        if (0 !== t[31]) throw new Error("invalid bytes32 string - no null terminator");
                        for (var r = 31; 0 === t[r - 1];) r--;
                        return u(t.slice(0, r))
                    }
                }, function (e, t, r) {
                    var n = r(14), i = r(13), a = i.getNAF, o = i.getJSF, s = i.assert;

                    function u(e, t) {
                        this.type = e, this.p = new n(t.p, 16), this.red = t.prime ? n.red(t.prime) : n.mont(this.p), this.zero = new n(0).toRed(this.red), this.one = new n(1).toRed(this.red), this.two = new n(2).toRed(this.red), this.n = t.n && new n(t.n, 16), this.g = t.g && this.pointFromJSON(t.g, t.gRed), this._wnafT1 = new Array(4), this._wnafT2 = new Array(4), this._wnafT3 = new Array(4), this._wnafT4 = new Array(4), this._bitLength = this.n ? this.n.bitLength() : 0;
                        var r = this.n && this.p.div(this.n);
                        !r || r.cmpn(100) > 0 ? this.redN = null : (this._maxwellTrick = !0, this.redN = this.n.toRed(this.red))
                    }

                    function c(e, t) {
                        this.curve = e, this.type = t, this.precomputed = null
                    }

                    e.exports = u, u.prototype.point = function () {
                        throw new Error("Not implemented")
                    }, u.prototype.validate = function () {
                        throw new Error("Not implemented")
                    }, u.prototype._fixedNafMul = function (e, t) {
                        s(e.precomputed);
                        var r = e._getDoubles(), n = a(t, 1, this._bitLength),
                            i = (1 << r.step + 1) - (r.step % 2 == 0 ? 2 : 1);
                        i /= 3;
                        for (var o = [], u = 0; u < n.length; u += r.step) {
                            var c = 0;
                            for (t = u + r.step - 1; t >= u; t--) c = (c << 1) + n[t];
                            o.push(c)
                        }
                        for (var f = this.jpoint(null, null, null), d = this.jpoint(null, null, null), h = i; h > 0; h--) {
                            for (u = 0; u < o.length; u++) (c = o[u]) === h ? d = d.mixedAdd(r.points[u]) : c === -h && (d = d.mixedAdd(r.points[u].neg()));
                            f = f.add(d)
                        }
                        return f.toP()
                    }, u.prototype._wnafMul = function (e, t) {
                        var r = 4, n = e._getNAFPoints(r);
                        r = n.wnd;
                        for (var i = n.points, o = a(t, r, this._bitLength), u = this.jpoint(null, null, null), c = o.length - 1; c >= 0; c--) {
                            for (t = 0; c >= 0 && 0 === o[c]; c--) t++;
                            if (c >= 0 && t++, u = u.dblp(t), c < 0) break;
                            var f = o[c];
                            s(0 !== f), u = "affine" === e.type ? f > 0 ? u.mixedAdd(i[f - 1 >> 1]) : u.mixedAdd(i[-f - 1 >> 1].neg()) : f > 0 ? u.add(i[f - 1 >> 1]) : u.add(i[-f - 1 >> 1].neg())
                        }
                        return "affine" === e.type ? u.toP() : u
                    }, u.prototype._wnafMulAdd = function (e, t, r, n, i) {
                        for (var s = this._wnafT1, u = this._wnafT2, c = this._wnafT3, f = 0, d = 0; d < n; d++) {
                            var h = (S = t[d])._getNAFPoints(e);
                            s[d] = h.wnd, u[d] = h.points
                        }
                        for (d = n - 1; d >= 1; d -= 2) {
                            var l = d - 1, p = d;
                            if (1 === s[l] && 1 === s[p]) {
                                var b = [t[l], null, null, t[p]];
                                0 === t[l].y.cmp(t[p].y) ? (b[1] = t[l].add(t[p]), b[2] = t[l].toJ().mixedAdd(t[p].neg())) : 0 === t[l].y.cmp(t[p].y.redNeg()) ? (b[1] = t[l].toJ().mixedAdd(t[p]), b[2] = t[l].add(t[p].neg())) : (b[1] = t[l].toJ().mixedAdd(t[p]), b[2] = t[l].toJ().mixedAdd(t[p].neg()));
                                var v = [-3, -1, -5, -7, 0, 7, 5, 1, 3], g = o(r[l], r[p]);
                                f = Math.max(g[0].length, f), c[l] = new Array(f), c[p] = new Array(f);
                                for (var m = 0; m < f; m++) {
                                    var y = 0 | g[0][m], w = 0 | g[1][m];
                                    c[l][m] = v[3 * (y + 1) + (w + 1)], c[p][m] = 0, u[l] = b
                                }
                            } else c[l] = a(r[l], s[l], this._bitLength), c[p] = a(r[p], s[p], this._bitLength), f = Math.max(c[l].length, f), f = Math.max(c[p].length, f)
                        }
                        var x = this.jpoint(null, null, null), _ = this._wnafT4;
                        for (d = f; d >= 0; d--) {
                            for (var A = 0; d >= 0;) {
                                var k = !0;
                                for (m = 0; m < n; m++) _[m] = 0 | c[m][d], 0 !== _[m] && (k = !1);
                                if (!k) break;
                                A++, d--
                            }
                            if (d >= 0 && A++, x = x.dblp(A), d < 0) break;
                            for (m = 0; m < n; m++) {
                                var S, M = _[m];
                                0 !== M && (M > 0 ? S = u[m][M - 1 >> 1] : M < 0 && (S = u[m][-M - 1 >> 1].neg()), x = "affine" === S.type ? x.mixedAdd(S) : x.add(S))
                            }
                        }
                        for (d = 0; d < n; d++) u[d] = null;
                        return i ? x : x.toP()
                    }, u.BasePoint = c, c.prototype.eq = function () {
                        throw new Error("Not implemented")
                    }, c.prototype.validate = function () {
                        return this.curve.validate(this)
                    }, u.prototype.decodePoint = function (e, t) {
                        e = i.toArray(e, t);
                        var r = this.p.byteLength();
                        if ((4 === e[0] || 6 === e[0] || 7 === e[0]) && e.length - 1 == 2 * r) return 6 === e[0] ? s(e[e.length - 1] % 2 == 0) : 7 === e[0] && s(e[e.length - 1] % 2 == 1), this.point(e.slice(1, 1 + r), e.slice(1 + r, 1 + 2 * r));
                        if ((2 === e[0] || 3 === e[0]) && e.length - 1 === r) return this.pointFromX(e.slice(1, 1 + r), 3 === e[0]);
                        throw new Error("Unknown point format")
                    }, c.prototype.encodeCompressed = function (e) {
                        return this.encode(e, !0)
                    }, c.prototype._encode = function (e) {
                        var t = this.curve.p.byteLength(), r = this.getX().toArray("be", t);
                        return e ? [this.getY().isEven() ? 2 : 3].concat(r) : [4].concat(r, this.getY().toArray("be", t))
                    }, c.prototype.encode = function (e, t) {
                        return i.encode(this._encode(t), e)
                    }, c.prototype.precompute = function (e) {
                        if (this.precomputed) return this;
                        var t = {doubles: null, naf: null, beta: null};
                        return t.naf = this._getNAFPoints(8), t.doubles = this._getDoubles(4, e), t.beta = this._getBeta(), this.precomputed = t, this
                    }, c.prototype._hasDoubles = function (e) {
                        if (!this.precomputed) return !1;
                        var t = this.precomputed.doubles;
                        return !!t && t.points.length >= Math.ceil((e.bitLength() + 1) / t.step)
                    }, c.prototype._getDoubles = function (e, t) {
                        if (this.precomputed && this.precomputed.doubles) return this.precomputed.doubles;
                        for (var r = [this], n = this, i = 0; i < t; i += e) {
                            for (var a = 0; a < e; a++) n = n.dbl();
                            r.push(n)
                        }
                        return {step: e, points: r}
                    }, c.prototype._getNAFPoints = function (e) {
                        if (this.precomputed && this.precomputed.naf) return this.precomputed.naf;
                        for (var t = [this], r = (1 << e) - 1, n = 1 === r ? null : this.dbl(), i = 1; i < r; i++) t[i] = t[i - 1].add(n);
                        return {wnd: e, points: t}
                    }, c.prototype._getBeta = function () {
                        return null
                    }, c.prototype.dblp = function (e) {
                        for (var t = this, r = 0; r < e; r++) t = t.dbl();
                        return t
                    }
                }, function (e, t, r) {
                    var i = this && this.__importStar || function (e) {
                        if (e && e.__esModule) return e;
                        var t = {};
                        if (null != e) for (var r in e) Object.hasOwnProperty.call(e, r) && (t[r] = e[r]);
                        return t.default = e, t
                    };
                    Object.defineProperty(t, "__esModule", {value: !0});
                    var a = i(r(19));

                    function o(e, t, r) {
                        Object.defineProperty(e, t, {enumerable: !0, value: r, writable: !1})
                    }

                    function s(e, t) {
                        return e && e._ethersType === t
                    }

                    t.defineReadOnly = o, t.setType = function (e, t) {
                        Object.defineProperty(e, "_ethersType", {configurable: !1, value: t, writable: !1})
                    }, t.isType = s, t.resolveProperties = function (e) {
                        var t = {}, r = [];
                        return Object.keys(e).forEach(function (n) {
                            var i = e[n];
                            i instanceof Promise ? r.push(i.then(function (e) {
                                return t[n] = e, null
                            })) : t[n] = i
                        }), Promise.all(r).then(function () {
                            return t
                        })
                    }, t.checkProperties = function (e, t) {
                        e && "object" == n()(e) || a.throwError("invalid object", a.INVALID_ARGUMENT, {
                            argument: "object",
                            value: e
                        }), Object.keys(e).forEach(function (r) {
                            t[r] || a.throwError("invalid object key - " + r, a.INVALID_ARGUMENT, {
                                argument: "transaction",
                                value: e,
                                key: r
                            })
                        })
                    }, t.shallowCopy = function (e) {
                        var t = {};
                        for (var r in e) t[r] = e[r];
                        return t
                    };
                    var u = {boolean: !0, number: !0, string: !0};
                    t.deepCopy = function e(t, r) {
                        if (null == t || u[n()(t)]) return t;
                        if (Array.isArray(t)) {
                            var i = t.map(function (t) {
                                return e(t, r)
                            });
                            return r && Object.freeze(i), i
                        }
                        if ("object" == n()(t)) {
                            if (s(t, "BigNumber")) return t;
                            if (s(t, "Description")) return t;
                            if (s(t, "Indexed")) return t;
                            for (var a in i = {}, t) {
                                var c = t[a];
                                void 0 !== c && o(i, a, e(c, r))
                            }
                            return r && Object.freeze(i), i
                        }
                        if ("function" == typeof t) return t;
                        throw new Error("Cannot deepCopy " + n()(t))
                    }, t.inheritable = function e(t) {
                        return function (r) {
                            var n, i;
                            i = t, (n = r).super_ = i, n.prototype = Object.create(i.prototype, {
                                constructor: {
                                    value: n,
                                    enumerable: !1,
                                    writable: !0,
                                    configurable: !0
                                }
                            }), o(r, "inherits", e(r))
                        }
                    }
                }, function (e, t, r) {
                    var n, i = t, a = r(33), o = r(60), s = r(13).assert;

                    function u(e) {
                        "short" === e.type ? this.curve = new o.short(e) : "edwards" === e.type ? this.curve = new o.edwards(e) : this.curve = new o.mont(e), this.g = this.curve.g, this.n = this.curve.n, this.hash = e.hash, s(this.g.validate(), "Invalid curve"), s(this.g.mul(this.n).isInfinity(), "Invalid curve, G*N != O")
                    }

                    function c(e, t) {
                        Object.defineProperty(i, e, {
                            configurable: !0, enumerable: !0, get: function () {
                                var r = new u(t);
                                return Object.defineProperty(i, e, {configurable: !0, enumerable: !0, value: r}), r
                            }
                        })
                    }

                    i.PresetCurve = u, c("p192", {
                        type: "short",
                        prime: "p192",
                        p: "ffffffff ffffffff ffffffff fffffffe ffffffff ffffffff",
                        a: "ffffffff ffffffff ffffffff fffffffe ffffffff fffffffc",
                        b: "64210519 e59c80e7 0fa7e9ab 72243049 feb8deec c146b9b1",
                        n: "ffffffff ffffffff ffffffff 99def836 146bc9b1 b4d22831",
                        hash: a.sha256,
                        gRed: !1,
                        g: ["188da80e b03090f6 7cbf20eb 43a18800 f4ff0afd 82ff1012", "07192b95 ffc8da78 631011ed 6b24cdd5 73f977a1 1e794811"]
                    }), c("p224", {
                        type: "short",
                        prime: "p224",
                        p: "ffffffff ffffffff ffffffff ffffffff 00000000 00000000 00000001",
                        a: "ffffffff ffffffff ffffffff fffffffe ffffffff ffffffff fffffffe",
                        b: "b4050a85 0c04b3ab f5413256 5044b0b7 d7bfd8ba 270b3943 2355ffb4",
                        n: "ffffffff ffffffff ffffffff ffff16a2 e0b8f03e 13dd2945 5c5c2a3d",
                        hash: a.sha256,
                        gRed: !1,
                        g: ["b70e0cbd 6bb4bf7f 321390b9 4a03c1d3 56c21122 343280d6 115c1d21", "bd376388 b5f723fb 4c22dfe6 cd4375a0 5a074764 44d58199 85007e34"]
                    }), c("p256", {
                        type: "short",
                        prime: null,
                        p: "ffffffff 00000001 00000000 00000000 00000000 ffffffff ffffffff ffffffff",
                        a: "ffffffff 00000001 00000000 00000000 00000000 ffffffff ffffffff fffffffc",
                        b: "5ac635d8 aa3a93e7 b3ebbd55 769886bc 651d06b0 cc53b0f6 3bce3c3e 27d2604b",
                        n: "ffffffff 00000000 ffffffff ffffffff bce6faad a7179e84 f3b9cac2 fc632551",
                        hash: a.sha256,
                        gRed: !1,
                        g: ["6b17d1f2 e12c4247 f8bce6e5 63a440f2 77037d81 2deb33a0 f4a13945 d898c296", "4fe342e2 fe1a7f9b 8ee7eb4a 7c0f9e16 2bce3357 6b315ece cbb64068 37bf51f5"]
                    }), c("p384", {
                        type: "short",
                        prime: null,
                        p: "ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff fffffffe ffffffff 00000000 00000000 ffffffff",
                        a: "ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff fffffffe ffffffff 00000000 00000000 fffffffc",
                        b: "b3312fa7 e23ee7e4 988e056b e3f82d19 181d9c6e fe814112 0314088f 5013875a c656398d 8a2ed19d 2a85c8ed d3ec2aef",
                        n: "ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff c7634d81 f4372ddf 581a0db2 48b0a77a ecec196a ccc52973",
                        hash: a.sha384,
                        gRed: !1,
                        g: ["aa87ca22 be8b0537 8eb1c71e f320ad74 6e1d3b62 8ba79b98 59f741e0 82542a38 5502f25d bf55296c 3a545e38 72760ab7", "3617de4a 96262c6f 5d9e98bf 9292dc29 f8f41dbd 289a147c e9da3113 b5f0b8c0 0a60b1ce 1d7e819d 7a431d7c 90ea0e5f"]
                    }), c("p521", {
                        type: "short",
                        prime: null,
                        p: "000001ff ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff",
                        a: "000001ff ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff fffffffc",
                        b: "00000051 953eb961 8e1c9a1f 929a21a0 b68540ee a2da725b 99b315f3 b8b48991 8ef109e1 56193951 ec7e937b 1652c0bd 3bb1bf07 3573df88 3d2c34f1 ef451fd4 6b503f00",
                        n: "000001ff ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff fffffffa 51868783 bf2f966b 7fcc0148 f709a5d0 3bb5c9b8 899c47ae bb6fb71e 91386409",
                        hash: a.sha512,
                        gRed: !1,
                        g: ["000000c6 858e06b7 0404e9cd 9e3ecb66 2395b442 9c648139 053fb521 f828af60 6b4d3dba a14b5e77 efe75928 fe1dc127 a2ffa8de 3348b3c1 856a429b f97e7e31 c2e5bd66", "00000118 39296a78 9a3bc004 5c8a5fb4 2c7d1bd9 98f54449 579b4468 17afbd17 273e662c 97ee7299 5ef42640 c550b901 3fad0761 353c7086 a272c240 88be9476 9fd16650"]
                    }), c("curve25519", {
                        type: "mont",
                        prime: "p25519",
                        p: "7fffffffffffffff ffffffffffffffff ffffffffffffffff ffffffffffffffed",
                        a: "76d06",
                        b: "1",
                        n: "1000000000000000 0000000000000000 14def9dea2f79cd6 5812631a5cf5d3ed",
                        hash: a.sha256,
                        gRed: !1,
                        g: ["9"]
                    }), c("ed25519", {
                        type: "edwards",
                        prime: "p25519",
                        p: "7fffffffffffffff ffffffffffffffff ffffffffffffffff ffffffffffffffed",
                        a: "-1",
                        c: "1",
                        d: "52036cee2b6ffe73 8cc740797779e898 00700a4d4141d8ab 75eb4dca135978a3",
                        n: "1000000000000000 0000000000000000 14def9dea2f79cd6 5812631a5cf5d3ed",
                        hash: a.sha256,
                        gRed: !1,
                        g: ["216936d3cd6e53fec0a4e231fdd6dc5c692cc7609525a7b2c9562d608f25d51a", "6666666666666666666666666666666666666666666666666666666666666658"]
                    });
                    try {
                        n = r(124)
                    } catch (e) {
                        n = void 0
                    }
                    c("secp256k1", {
                        type: "short",
                        prime: "k256",
                        p: "ffffffff ffffffff ffffffff ffffffff ffffffff ffffffff fffffffe fffffc2f",
                        a: "0",
                        b: "7",
                        n: "ffffffff ffffffff ffffffff fffffffe baaedce6 af48a03b bfd25e8c d0364141",
                        h: "1",
                        hash: a.sha256,
                        beta: "7ae96a2b657c07106e64479eac3434e99cf0497512f58995c1396c28719501ee",
                        lambda: "5363ad4cc05c30e0a5261c028812645a122e22ea20816678df02967c1b23bd72",
                        basis: [{
                            a: "3086d221a7d46bcde86c90e49284eb15",
                            b: "-e4437ed6010e88286f547fa90abfe4c3"
                        }, {a: "114ca50f7a8e2f3f657c1108d9d44cfd8", b: "3086d221a7d46bcde86c90e49284eb15"}],
                        gRed: !1,
                        g: ["79be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798", "483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8", n]
                    })
                }, function (e, t, r) {
                    var n = t;
                    n.utils = r(16), n.common = r(28), n.sha = r(118), n.ripemd = r(122), n.hmac = r(123), n.sha1 = n.sha.sha1, n.sha256 = n.sha.sha256, n.sha224 = n.sha.sha224, n.sha384 = n.sha.sha384, n.sha512 = n.sha.sha512, n.ripemd160 = n.ripemd.ripemd160
                }, function (e, t, r) {
                    function i(e) {
                        return (i = "function" == typeof Symbol && "symbol" == n()(Symbol.iterator) ? function (e) {
                            return n()(e)
                        } : function (e) {
                            return e && "function" == typeof Symbol && e.constructor === Symbol && e !== Symbol.prototype ? "symbol" : n()(e)
                        })(e)
                    }

                    Object.defineProperty(t, "__esModule", {value: !0}), t.default = function (e) {
                        var t;
                        if (!("string" == typeof e || e instanceof String)) throw t = null === e ? "null" : "object" === (t = i(e)) && e.constructor && e.constructor.hasOwnProperty("name") ? e.constructor.name : "a ".concat(t), new TypeError("Expected string but received ".concat(t, "."))
                    }, e.exports = t.default, e.exports.default = t.default
                }, function (e, t, r) {
                    r.d(t, "a", function () {
                        return M
                    });
                    var n, i = r(1), a = r.n(i), o = r(2), s = r.n(o), u = r(17), c = r.n(u), f = r(20), d = r.n(f),
                        h = r(4), l = r.n(h), p = r(5), b = r.n(p), v = r(6), g = r(0), m = r(9), y = r(24), w = r(3),
                        x = r(10), _ = r.n(x);

                    function A(e) {
                        return v.default.address.toHex(e)
                    }

                    function k(e) {
                        return n.tronWeb.fromUtf8(e)
                    }

                    function S(e, t) {
                        return e.Error ? t(e.Error) : e.result && e.result.message ? t(n.tronWeb.toUtf8(e.result.message)) : t(null, e)
                    }

                    var M = function () {
                        function e() {
                            var t = arguments.length > 0 && void 0 !== arguments[0] && arguments[0];
                            if (l()(this, e), !t || !t instanceof v.default) throw new Error("Expected instance of TronWeb");
                            n = this, this.tronWeb = t, this.injectPromise = _()(this), this.validator = new y.a(t)
                        }

                        var t, r, i, o;
                        return b()(e, [{
                            key: "sendTrx", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
                                    t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : 0,
                                    r = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : this.tronWeb.defaultAddress.hex,
                                    n = arguments.length > 3 ? arguments[3] : void 0,
                                    i = arguments.length > 4 && void 0 !== arguments[4] && arguments[4];
                                if (g.a.isFunction(n) && (i = n, n = {}), g.a.isFunction(r) ? (i = r, r = this.tronWeb.defaultAddress.hex) : g.a.isObject(r) && (n = r, r = this.tronWeb.defaultAddress.hex), !i) return this.injectPromise(this.sendTrx, e, t, r, n);
                                if (t = parseInt(t), !this.validator.notValid([{
                                    name: "recipient",
                                    type: "address",
                                    value: e
                                }, {name: "origin", type: "address", value: r}, {
                                    names: ["recipient", "origin"],
                                    type: "notEqual",
                                    msg: "Cannot transfer TRX to the same account"
                                }, {name: "amount", type: "integer", gt: 0, value: t}], i)) {
                                    var a = {to_address: A(e), owner_address: A(r), amount: t};
                                    n && n.permissionId && (a.Permission_id = n.permissionId), this.tronWeb.fullNode.request("wallet/createtransaction", a, "post").then(function (e) {
                                        return S(e, i)
                                    }).catch(function (e) {
                                        return i(e)
                                    })
                                }
                            }
                        }, {
                            key: "sendToken", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
                                    t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : 0,
                                    r = arguments.length > 2 && void 0 !== arguments[2] && arguments[2],
                                    n = arguments.length > 3 && void 0 !== arguments[3] ? arguments[3] : this.tronWeb.defaultAddress.hex,
                                    i = arguments.length > 4 ? arguments[4] : void 0,
                                    a = arguments.length > 5 && void 0 !== arguments[5] && arguments[5];
                                if (g.a.isFunction(i) && (a = i, i = {}), g.a.isFunction(n) ? (a = n, n = this.tronWeb.defaultAddress.hex) : g.a.isObject(n) && (i = n, n = this.tronWeb.defaultAddress.hex), !a) return this.injectPromise(this.sendToken, e, t, r, n, i);
                                if (t = parseInt(t), !this.validator.notValid([{
                                    name: "recipient",
                                    type: "address",
                                    value: e
                                }, {name: "origin", type: "address", value: n}, {
                                    names: ["recipient", "origin"],
                                    type: "notEqual",
                                    msg: "Cannot transfer tokens to the same account"
                                }, {name: "amount", type: "integer", gt: 0, value: t}, {
                                    name: "token ID",
                                    type: "tokenId",
                                    value: r
                                }], a)) {
                                    var o = {
                                        to_address: A(e),
                                        owner_address: A(n),
                                        asset_name: k(r),
                                        amount: parseInt(t)
                                    };
                                    i && i.permissionId && (o.Permission_id = i.permissionId), this.tronWeb.fullNode.request("wallet/transferasset", o, "post").then(function (e) {
                                        return S(e, a)
                                    }).catch(function (e) {
                                        return a(e)
                                    })
                                }
                            }
                        }, {
                            key: "purchaseToken", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
                                    t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1],
                                    r = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : 0,
                                    n = arguments.length > 3 && void 0 !== arguments[3] ? arguments[3] : this.tronWeb.defaultAddress.hex,
                                    i = arguments.length > 4 ? arguments[4] : void 0,
                                    a = arguments.length > 5 && void 0 !== arguments[5] && arguments[5];
                                if (g.a.isFunction(i) && (a = i, i = {}), g.a.isFunction(n) ? (a = n, n = this.tronWeb.defaultAddress.hex) : g.a.isObject(n) && (i = n, n = this.tronWeb.defaultAddress.hex), !a) return this.injectPromise(this.purchaseToken, e, t, r, n, i);
                                if (!this.validator.notValid([{
                                    name: "buyer",
                                    type: "address",
                                    value: n
                                }, {name: "issuer", type: "address", value: e}, {
                                    names: ["buyer", "issuer"],
                                    type: "notEqual",
                                    msg: "Cannot purchase tokens from same account"
                                }, {name: "amount", type: "integer", gt: 0, value: r}, {
                                    name: "token ID",
                                    type: "tokenId",
                                    value: t
                                }], a)) {
                                    var o = {
                                        to_address: A(e),
                                        owner_address: A(n),
                                        asset_name: k(t),
                                        amount: parseInt(r)
                                    };
                                    i && i.permissionId && (o.Permission_id = i.permissionId), this.tronWeb.fullNode.request("wallet/participateassetissue", o, "post").then(function (e) {
                                        return S(e, a)
                                    }).catch(function (e) {
                                        return a(e)
                                    })
                                }
                            }
                        }, {
                            key: "freezeBalance", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : 0,
                                    t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : 3,
                                    r = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : "BANDWIDTH",
                                    n = arguments.length > 3 && void 0 !== arguments[3] ? arguments[3] : this.tronWeb.defaultAddress.hex,
                                    i = arguments.length > 4 && void 0 !== arguments[4] ? arguments[4] : void 0,
                                    a = arguments.length > 5 ? arguments[5] : void 0,
                                    o = arguments.length > 6 && void 0 !== arguments[6] && arguments[6];
                                if (g.a.isFunction(a) && (o = a, a = {}), g.a.isFunction(i) ? (o = i, i = void 0) : g.a.isObject(i) && (a = i, i = void 0), g.a.isFunction(n) ? (o = n, n = this.tronWeb.defaultAddress.hex) : g.a.isObject(n) && (a = n, n = this.tronWeb.defaultAddress.hex), g.a.isFunction(t) && (o = t, t = 3), g.a.isFunction(r) && (o = r, r = "BANDWIDTH"), !o) return this.injectPromise(this.freezeBalance, e, t, r, n, i, a);
                                if (!this.validator.notValid([{
                                    name: "origin",
                                    type: "address",
                                    value: n
                                }, {name: "receiver", type: "address", value: i, optional: !0}, {
                                    name: "amount",
                                    type: "integer",
                                    gt: 0,
                                    value: e
                                }, {name: "duration", type: "integer", gte: 3, value: t}, {
                                    name: "resource",
                                    type: "resource",
                                    value: r,
                                    msg: 'Invalid resource provided: Expected "BANDWIDTH" or "ENERGY'
                                }], o)) {
                                    var s = {
                                        owner_address: A(n),
                                        frozen_balance: parseInt(e),
                                        frozen_duration: parseInt(t),
                                        resource: r
                                    };
                                    g.a.isNotNullOrUndefined(i) && A(i) !== A(n) && (s.receiver_address = A(i)), a && a.permissionId && (s.Permission_id = a.permissionId), this.tronWeb.fullNode.request("wallet/freezebalance", s, "post").then(function (e) {
                                        return S(e, o)
                                    }).catch(function (e) {
                                        return o(e)
                                    })
                                }
                            }
                        }, {
                            key: "unfreezeBalance", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : "BANDWIDTH",
                                    t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : this.tronWeb.defaultAddress.hex,
                                    r = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : void 0,
                                    n = arguments.length > 3 ? arguments[3] : void 0,
                                    i = arguments.length > 4 && void 0 !== arguments[4] && arguments[4];
                                if (g.a.isFunction(n) && (i = n, n = {}), g.a.isFunction(r) ? (i = r, r = void 0) : g.a.isObject(r) && (n = r, r = void 0), g.a.isFunction(t) ? (i = t, t = this.tronWeb.defaultAddress.hex) : g.a.isObject(t) && (n = t, t = this.tronWeb.defaultAddress.hex), g.a.isFunction(e) && (i = e, e = "BANDWIDTH"), !i) return this.injectPromise(this.unfreezeBalance, e, t, r, n);
                                if (!this.validator.notValid([{
                                    name: "origin",
                                    type: "address",
                                    value: t
                                }, {name: "receiver", type: "address", value: r, optional: !0}, {
                                    name: "resource",
                                    type: "resource",
                                    value: e,
                                    msg: 'Invalid resource provided: Expected "BANDWIDTH" or "ENERGY'
                                }], i)) {
                                    var a = {owner_address: A(t), resource: e};
                                    g.a.isNotNullOrUndefined(r) && A(r) !== A(t) && (a.receiver_address = A(r)), n && n.permissionId && (a.Permission_id = n.permissionId), this.tronWeb.fullNode.request("wallet/unfreezebalance", a, "post").then(function (e) {
                                        return S(e, i)
                                    }).catch(function (e) {
                                        return i(e)
                                    })
                                }
                            }
                        }, {
                            key: "withdrawBlockRewards", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : this.tronWeb.defaultAddress.hex,
                                    t = arguments.length > 1 ? arguments[1] : void 0,
                                    r = arguments.length > 2 && void 0 !== arguments[2] && arguments[2];
                                if (g.a.isFunction(t) && (r = t, t = {}), g.a.isFunction(e) ? (r = e, e = this.tronWeb.defaultAddress.hex) : g.a.isObject(e) && (t = e, e = this.tronWeb.defaultAddress.hex), !r) return this.injectPromise(this.withdrawBlockRewards, e, t);
                                if (!this.validator.notValid([{name: "origin", type: "address", value: e}], r)) {
                                    var n = {owner_address: A(e)};
                                    t && t.permissionId && (n.Permission_id = t.permissionId), this.tronWeb.fullNode.request("wallet/withdrawbalance", n, "post").then(function (e) {
                                        return S(e, r)
                                    }).catch(function (e) {
                                        return r(e)
                                    })
                                }
                            }
                        }, {
                            key: "applyForSR", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : this.tronWeb.defaultAddress.hex,
                                    t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1],
                                    r = arguments.length > 2 ? arguments[2] : void 0,
                                    n = arguments.length > 3 && void 0 !== arguments[3] && arguments[3];
                                if (g.a.isFunction(r) && (n = r, r = {}), g.a.isObject(t) && g.a.isValidURL(e) && (r = t, t = e, e = this.tronWeb.defaultAddress.hex), !n) return this.injectPromise(this.applyForSR, e, t, r);
                                if (!this.validator.notValid([{name: "origin", type: "address", value: e}, {
                                    name: "url",
                                    type: "url",
                                    value: t,
                                    msg: "Invalid url provided"
                                }], n)) {
                                    var i = {owner_address: A(e), url: k(t)};
                                    r && r.permissionId && (i.Permission_id = r.permissionId), this.tronWeb.fullNode.request("wallet/createwitness", i, "post").then(function (e) {
                                        return S(e, n)
                                    }).catch(function (e) {
                                        return n(e)
                                    })
                                }
                            }
                        }, {
                            key: "vote", value: function () {
                                var e = this, t = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : {},
                                    r = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : this.tronWeb.defaultAddress.hex,
                                    n = arguments.length > 2 ? arguments[2] : void 0,
                                    i = arguments.length > 3 && void 0 !== arguments[3] && arguments[3];
                                if (g.a.isFunction(n) && (i = n, n = {}), g.a.isFunction(r) ? (i = r, r = this.tronWeb.defaultAddress.hex) : g.a.isObject(r) && (n = r, r = this.tronWeb.defaultAddress.hex), !i) return this.injectPromise(this.vote, t, r, n);
                                if (!this.validator.notValid([{
                                    name: "voter",
                                    type: "address",
                                    value: r
                                }, {name: "votes", type: "notEmptyObject", value: t}], i)) {
                                    var a = !1;
                                    if (t = Object.entries(t).map(function (t) {
                                        var r = d()(t, 2), n = r[0], i = r[1];
                                        if (!a) return e.validator.notValid([{
                                            name: "SR",
                                            type: "address",
                                            value: n
                                        }, {
                                            name: "vote count",
                                            type: "integer",
                                            gt: 0,
                                            value: i,
                                            msg: "Invalid vote count provided for SR: " + n
                                        }]) ? a = !0 : {vote_address: A(n), vote_count: parseInt(i)}
                                    }), !a) {
                                        var o = {owner_address: A(r), votes: t};
                                        n && n.permissionId && (o.Permission_id = n.permissionId), this.tronWeb.fullNode.request("wallet/votewitnessaccount", o, "post").then(function (e) {
                                            return S(e, i)
                                        }).catch(function (e) {
                                            return i(e)
                                        })
                                    }
                                }
                            }
                        }, {
                            key: "createSmartContract", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : {},
                                    t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : this.tronWeb.defaultAddress.hex,
                                    r = arguments.length > 2 && void 0 !== arguments[2] && arguments[2];
                                if (g.a.isFunction(t) && (r = t, t = this.tronWeb.defaultAddress.hex), !r) return this.injectPromise(this.createSmartContract, e, t);
                                var n = e.feeLimit || this.tronWeb.feeLimit, i = e.userFeePercentage;
                                "number" == typeof i || i || (i = 100);
                                var a = e.originEnergyLimit || 1e7, o = e.callValue || 0, s = e.tokenValue,
                                    u = e.tokenId || e.token_id, c = e.abi, f = void 0 !== c && c, d = e.bytecode,
                                    h = void 0 !== d && d, l = e.parameters, p = void 0 === l ? [] : l, b = e.name,
                                    v = void 0 === b ? "" : b;
                                if (f && g.a.isString(f)) try {
                                    f = JSON.parse(f)
                                } catch (e) {
                                    return r("Invalid options.abi provided")
                                }
                                if (f.entrys && (f = f.entrys), !g.a.isArray(f)) return r("Invalid options.abi provided");
                                var y = f.some(function (e) {
                                    return "constructor" == e.type && e.payable
                                });
                                if (!this.validator.notValid([{
                                    name: "bytecode",
                                    type: "hex",
                                    value: h
                                }, {name: "feeLimit", type: "integer", value: n, gt: 0, lte: 5e9}, {
                                    name: "callValue",
                                    type: "integer",
                                    value: o,
                                    gte: 0
                                }, {
                                    name: "userFeePercentage",
                                    type: "integer",
                                    value: i,
                                    gte: 0,
                                    lte: 100
                                }, {
                                    name: "originEnergyLimit",
                                    type: "integer",
                                    value: a,
                                    gte: 0,
                                    lte: 1e7
                                }, {name: "parameters", type: "array", value: p}, {
                                    name: "issuer",
                                    type: "address",
                                    value: t
                                }, {
                                    name: "tokenValue",
                                    type: "integer",
                                    value: s,
                                    gte: 0,
                                    optional: !0
                                }, {name: "tokenId", type: "integer", value: u, gte: 0, optional: !0}], r)) {
                                    if (y && 0 == o && 0 == s) return r("When contract is payable, options.callValue or options.tokenValue must be a positive integer");
                                    if (!y && (o > 0 || s > 0)) return r("When contract is not payable, options.callValue and options.tokenValue must be 0");
                                    var x = f.find(function (e) {
                                        return "constructor" === e.type
                                    });
                                    if (void 0 !== x && x) {
                                        var _ = new m.AbiCoder, k = [], M = [];
                                        if (x = x.inputs, p.length != x.length) return r("constructor needs ".concat(x.length, " but ").concat(p.length, " provided"));
                                        for (var I = 0; I < p.length; I++) {
                                            var E = x[I].type, N = p[I];
                                            if (!E || !g.a.isString(E) || !E.length) return r("Invalid parameter type provided: " + E);
                                            "address" == E ? N = A(N).replace(w.c, "0x") : "address[]" == E && (N = N.map(function (e) {
                                                return A(e).replace(w.c, "0x")
                                            })), k.push(E), M.push(N)
                                        }
                                        try {
                                            p = _.encode(k, M).replace(/^(0x)/, "")
                                        } catch (e) {
                                            return r(e)
                                        }
                                    } else p = "";
                                    var P = {
                                        owner_address: A(t),
                                        fee_limit: parseInt(n),
                                        call_value: parseInt(o),
                                        consume_user_resource_percent: i,
                                        origin_energy_limit: a,
                                        abi: JSON.stringify(f),
                                        bytecode: h,
                                        parameter: p,
                                        name: v
                                    };
                                    g.a.isNotNullOrUndefined(s) && (P.call_token_value = parseInt(s)), g.a.isNotNullOrUndefined(u) && (P.token_id = parseInt(u)), e && e.permissionId && (P.Permission_id = e.permissionId), this.tronWeb.fullNode.request("wallet/deploycontract", P, "post").then(function (e) {
                                        return S(e, r)
                                    }).catch(function (e) {
                                        return r(e)
                                    })
                                }
                            }
                        }, {
                            key: "triggerSmartContract", value: function () {
                                for (var e = arguments.length, t = new Array(e), r = 0; r < e; r++) t[r] = arguments[r];
                                return "object" !== c()(t[2]) && (t[2] = {
                                    feeLimit: t[2],
                                    callValue: t[3]
                                }, t.splice(3, 1)), this._triggerSmartContract.apply(this, t)
                            }
                        }, {
                            key: "triggerConstantContract", value: function () {
                                for (var e = arguments.length, t = new Array(e), r = 0; r < e; r++) t[r] = arguments[r];
                                return t[2]._isConstant = !0, this.triggerSmartContract.apply(this, t)
                            }
                        }, {
                            key: "triggerConfirmedConstantContract", value: function () {
                                for (var e = arguments.length, t = new Array(e), r = 0; r < e; r++) t[r] = arguments[r];
                                return t[2]._isConstant = !0, t[2].confirmed = !0, this.triggerSmartContract.apply(this, t)
                            }
                        }, {
                            key: "_triggerSmartContract", value: function (e, t) {
                                var r = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : {},
                                    n = arguments.length > 3 && void 0 !== arguments[3] ? arguments[3] : [],
                                    i = arguments.length > 4 && void 0 !== arguments[4] ? arguments[4] : this.tronWeb.defaultAddress.hex,
                                    a = arguments.length > 5 && void 0 !== arguments[5] && arguments[5];
                                if (g.a.isFunction(i) && (a = i, i = this.tronWeb.defaultAddress.hex), g.a.isFunction(n) && (a = n, n = []), !a) return this.injectPromise(this._triggerSmartContract, e, t, r, n, i);
                                var o = Object.assign({callValue: 0, feeLimit: this.tronWeb.feeLimit}, r),
                                    s = o.tokenValue, u = o.tokenId, c = o.callValue, f = o.feeLimit;
                                if (!this.validator.notValid([{
                                    name: "feeLimit",
                                    type: "integer",
                                    value: f,
                                    gt: 0,
                                    lte: 5e9
                                }, {name: "callValue", type: "integer", value: c, gte: 0}, {
                                    name: "parameters",
                                    type: "array",
                                    value: n
                                }, {name: "contract", type: "address", value: e}, {
                                    name: "issuer",
                                    type: "address",
                                    value: i,
                                    optional: !0
                                }, {
                                    name: "tokenValue",
                                    type: "integer",
                                    value: s,
                                    gte: 0,
                                    optional: !0
                                }, {name: "tokenId", type: "integer", value: u, gte: 0, optional: !0}], a)) {
                                    var d = {contract_address: A(e), owner_address: A(i)};
                                    if (t && g.a.isString(t)) {
                                        if (t = t.replace("/s*/g", ""), n.length) {
                                            for (var h = new m.AbiCoder, l = [], p = [], b = 0; b < n.length; b++) {
                                                var v = n[b], y = v.type, x = v.value;
                                                if (!y || !g.a.isString(y) || !y.length) return a("Invalid parameter type provided: " + y);
                                                "address" == y ? x = A(x).replace(w.c, "0x") : "address[]" == y && (x = x.map(function (e) {
                                                    return A(e).replace(w.c, "0x")
                                                })), l.push(y), p.push(x)
                                            }
                                            try {
                                                l = l.map(function (e) {
                                                    return /trcToken/.test(e) && (e = e.replace(/trcToken/, "uint256")), e
                                                }), n = h.encode(l, p).replace(/^(0x)/, "")
                                            } catch (e) {
                                                return a(e)
                                            }
                                        } else n = "";
                                        r.shieldedParameter && (n = r.shieldedParameter.replace(/^(0x)/, "")), d.function_selector = t, d.parameter = n
                                    }
                                    r._isConstant || (d.call_value = parseInt(c), d.fee_limit = parseInt(f), g.a.isNotNullOrUndefined(s) && (d.call_token_value = parseInt(s)), g.a.isNotNullOrUndefined(u) && (d.token_id = parseInt(u))), r.permissionId && (d.Permission_id = r.permissionId), this.tronWeb[r.confirmed ? "solidityNode" : "fullNode"].request("wallet".concat(r.confirmed ? "solidity" : "", "/trigger").concat(r._isConstant ? "constant" : "smart", "contract"), d, "post").then(function (e) {
                                        return S(e, a)
                                    }).catch(function (e) {
                                        return a(e)
                                    })
                                }
                            }
                        }, {
                            key: "clearABI", value: function (e) {
                                var t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : this.tronWeb.defaultAddress.hex,
                                    r = arguments.length > 2 && void 0 !== arguments[2] && arguments[2];
                                if (!r) return this.injectPromise(this.clearABI, e, t);
                                if (!this.tronWeb.isAddress(e)) return r("Invalid contract address provided");
                                if (!this.tronWeb.isAddress(t)) return r("Invalid owner address provided");
                                var n = {contract_address: A(e), owner_address: A(t)};
                                this.tronWeb.trx.cache.contracts[e] && delete this.tronWeb.trx.cache.contracts[e], this.tronWeb.fullNode.request("wallet/clearabi", n, "post").then(function (e) {
                                    return S(e, r)
                                }).catch(function (e) {
                                    return r(e)
                                })
                            }
                        }, {
                            key: "updateBrokerage", value: function (e) {
                                var t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : this.tronWeb.defaultAddress.hex,
                                    r = arguments.length > 2 && void 0 !== arguments[2] && arguments[2];
                                if (!r) return this.injectPromise(this.updateBrokerage, e, t);
                                if (!g.a.isNotNullOrUndefined(e)) return r("Invalid brokerage provided");
                                if (!g.a.isInteger(e) || e < 0 || e > 100) return r("Brokerage must be an integer between 0 and 100");
                                if (!this.tronWeb.isAddress(t)) return r("Invalid owner address provided");
                                var n = {brokerage: parseInt(e), owner_address: A(t)};
                                this.tronWeb.fullNode.request("wallet/updateBrokerage", n, "post").then(function (e) {
                                    return S(e, r)
                                }).catch(function (e) {
                                    return r(e)
                                })
                            }
                        }, {
                            key: "createToken", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : {},
                                    t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : this.tronWeb.defaultAddress.hex,
                                    r = arguments.length > 2 && void 0 !== arguments[2] && arguments[2];
                                if (g.a.isFunction(t) && (r = t, t = this.tronWeb.defaultAddress.hex), !r) return this.injectPromise(this.createToken, e, t);
                                var n = e.name, i = void 0 !== n && n, a = e.abbreviation, o = void 0 !== a && a,
                                    s = e.description, u = void 0 !== s && s, c = e.url, f = void 0 !== c && c,
                                    d = e.totalSupply, h = void 0 === d ? 0 : d, l = e.trxRatio,
                                    p = void 0 === l ? 1 : l, b = e.tokenRatio, v = void 0 === b ? 1 : b,
                                    m = e.saleStart, y = void 0 === m ? Date.now() : m, w = e.saleEnd,
                                    x = void 0 !== w && w, _ = e.freeBandwidth, M = void 0 === _ ? 0 : _,
                                    I = e.freeBandwidthLimit, E = void 0 === I ? 0 : I, N = e.frozenAmount,
                                    P = void 0 === N ? 0 : N, T = e.frozenDuration, O = void 0 === T ? 0 : T,
                                    R = e.voteScore, j = e.precision;
                                if (!this.validator.notValid([{
                                    name: "Supply amount",
                                    type: "positive-integer",
                                    value: h
                                }, {name: "TRX ratio", type: "positive-integer", value: p}, {
                                    name: "Token ratio",
                                    type: "positive-integer",
                                    value: v
                                }, {
                                    name: "token abbreviation",
                                    type: "not-empty-string",
                                    value: o
                                }, {name: "token name", type: "not-empty-string", value: i}, {
                                    name: "token description",
                                    type: "not-empty-string",
                                    value: u
                                }, {name: "token url", type: "url", value: f}, {
                                    name: "issuer",
                                    type: "address",
                                    value: t
                                }, {
                                    name: "sale start timestamp",
                                    type: "integer",
                                    value: y,
                                    gte: Date.now()
                                }, {
                                    name: "sale end timestamp",
                                    type: "integer",
                                    value: x,
                                    gt: y
                                }, {
                                    name: "Free bandwidth amount",
                                    type: "integer",
                                    value: M,
                                    gte: 0
                                }, {
                                    name: "Free bandwidth limit",
                                    type: "integer",
                                    value: E,
                                    gte: 0
                                }, {name: "Frozen supply", type: "integer", value: P, gte: 0}, {
                                    name: "Frozen duration",
                                    type: "integer",
                                    value: O,
                                    gte: 0
                                }], r)) {
                                    if (g.a.isNotNullOrUndefined(R) && (!g.a.isInteger(R) || R <= 0)) return r("voteScore must be a positive integer greater than 0");
                                    if (g.a.isNotNullOrUndefined(j) && (!g.a.isInteger(j) || j < 0 || j > 6)) return r("precision must be a positive integer >= 0 and <= 6");
                                    var C = {
                                        owner_address: A(t),
                                        name: k(i),
                                        abbr: k(o),
                                        description: k(u),
                                        url: k(f),
                                        total_supply: parseInt(h),
                                        trx_num: parseInt(p),
                                        num: parseInt(v),
                                        start_time: parseInt(y),
                                        end_time: parseInt(x),
                                        free_asset_net_limit: parseInt(M),
                                        public_free_asset_net_limit: parseInt(E),
                                        frozen_supply: {frozen_amount: parseInt(P), frozen_days: parseInt(O)}
                                    };
                                    !this.tronWeb.fullnodeSatisfies(">=3.5.0") || parseInt(P) > 0 || delete C.frozen_supply, j && !isNaN(parseInt(j)) && (C.precision = parseInt(j)), R && !isNaN(parseInt(R)) && (C.vote_score = parseInt(R)), e && e.permissionId && (C.Permission_id = e.permissionId), this.tronWeb.fullNode.request("wallet/createassetissue", C, "post").then(function (e) {
                                        return S(e, r)
                                    }).catch(function (e) {
                                        return r(e)
                                    })
                                }
                            }
                        }, {
                            key: "updateAccount", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
                                    t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : this.tronWeb.defaultAddress.hex,
                                    r = arguments.length > 2 ? arguments[2] : void 0,
                                    n = arguments.length > 3 && void 0 !== arguments[3] && arguments[3];
                                if (g.a.isFunction(r) && (n = r, r = {}), g.a.isFunction(t) ? (n = t, t = this.tronWeb.defaultAddress.hex) : g.a.isObject(t) && (r = t, t = this.tronWeb.defaultAddress.hex), !n) return this.injectPromise(this.updateAccount, e, t, r);
                                if (!this.validator.notValid([{
                                    name: "Name",
                                    type: "not-empty-string",
                                    value: e
                                }, {name: "origin", type: "address", value: t}], n)) {
                                    var i = {account_name: k(e), owner_address: A(t)};
                                    r && r.permissionId && (i.Permission_id = r.permissionId), this.tronWeb.fullNode.request("wallet/updateaccount", i, "post").then(function (e) {
                                        return S(e, n)
                                    }).catch(function (e) {
                                        return n(e)
                                    })
                                }
                            }
                        }, {
                            key: "setAccountId", value: function (e) {
                                var t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : this.tronWeb.defaultAddress.hex,
                                    r = arguments.length > 2 && void 0 !== arguments[2] && arguments[2];
                                if (g.a.isFunction(t) && (r = t, t = this.tronWeb.defaultAddress.hex), !r) return this.injectPromise(this.setAccountId, e, t);
                                e && g.a.isString(e) && e.startsWith("0x") && (e = e.slice(2)), this.validator.notValid([{
                                    name: "accountId",
                                    type: "hex",
                                    value: e
                                }, {name: "accountId", type: "string", lte: 32, gte: 8, value: e}, {
                                    name: "origin",
                                    type: "address",
                                    value: t
                                }], r) || this.tronWeb.fullNode.request("wallet/setaccountid", {
                                    account_id: e,
                                    owner_address: A(t)
                                }, "post").then(function (e) {
                                    return S(e, r)
                                }).catch(function (e) {
                                    return r(e)
                                })
                            }
                        }, {
                            key: "updateToken", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : {},
                                    t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : this.tronWeb.defaultAddress.hex,
                                    r = arguments.length > 2 && void 0 !== arguments[2] && arguments[2];
                                if (g.a.isFunction(t) ? (r = t, t = this.tronWeb.defaultAddress.hex) : g.a.isObject(t) && (e = t, t = this.tronWeb.defaultAddress.hex), !r) return this.injectPromise(this.updateToken, e, t);
                                var n = e, i = n.description, a = void 0 !== i && i, o = n.url, s = void 0 !== o && o,
                                    u = n.freeBandwidth, c = void 0 === u ? 0 : u, f = n.freeBandwidthLimit,
                                    d = void 0 === f ? 0 : f;
                                if (!this.validator.notValid([{
                                    name: "token description",
                                    type: "not-empty-string",
                                    value: a
                                }, {name: "token url", type: "url", value: s}, {
                                    name: "issuer",
                                    type: "address",
                                    value: t
                                }, {
                                    name: "Free bandwidth amount",
                                    type: "positive-integer",
                                    value: c
                                }, {name: "Free bandwidth limit", type: "positive-integer", value: d}], r)) {
                                    var h = {
                                        owner_address: A(t),
                                        description: k(a),
                                        url: k(s),
                                        new_limit: parseInt(c),
                                        new_public_limit: parseInt(d)
                                    };
                                    e && e.permissionId && (h.Permission_id = e.permissionId), this.tronWeb.fullNode.request("wallet/updateasset", h, "post").then(function (e) {
                                        return S(e, r)
                                    }).catch(function (e) {
                                        return r(e)
                                    })
                                }
                            }
                        }, {
                            key: "sendAsset", value: function () {
                                return this.sendToken.apply(this, arguments)
                            }
                        }, {
                            key: "purchaseAsset", value: function () {
                                return this.purchaseToken.apply(this, arguments)
                            }
                        }, {
                            key: "createAsset", value: function () {
                                return this.createToken.apply(this, arguments)
                            }
                        }, {
                            key: "updateAsset", value: function () {
                                return this.updateToken.apply(this, arguments)
                            }
                        }, {
                            key: "createProposal", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
                                    t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : this.tronWeb.defaultAddress.hex,
                                    r = arguments.length > 2 ? arguments[2] : void 0,
                                    n = arguments.length > 3 && void 0 !== arguments[3] && arguments[3];
                                if (g.a.isFunction(r) && (n = r, r = {}), g.a.isFunction(t) ? (n = t, t = this.tronWeb.defaultAddress.hex) : g.a.isObject(t) && (r = t, t = this.tronWeb.defaultAddress.hex), !n) return this.injectPromise(this.createProposal, e, t, r);
                                if (!this.validator.notValid([{name: "issuer", type: "address", value: t}], n)) {
                                    var i = "Invalid proposal parameters provided";
                                    if (!e) return n(i);
                                    g.a.isArray(e) || (e = [e]);
                                    var a = !0, o = !1, s = void 0;
                                    try {
                                        for (var u, c = e[Symbol.iterator](); !(a = (u = c.next()).done); a = !0) {
                                            var f = u.value;
                                            if (!g.a.isObject(f)) return n(i)
                                        }
                                    } catch (e) {
                                        o = !0, s = e
                                    } finally {
                                        try {
                                            a || null == c.return || c.return()
                                        } finally {
                                            if (o) throw s
                                        }
                                    }
                                    var d = {owner_address: A(t), parameters: e};
                                    r && r.permissionId && (d.Permission_id = r.permissionId), this.tronWeb.fullNode.request("wallet/proposalcreate", d, "post").then(function (e) {
                                        return S(e, n)
                                    }).catch(function (e) {
                                        return n(e)
                                    })
                                }
                            }
                        }, {
                            key: "deleteProposal", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
                                    t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : this.tronWeb.defaultAddress.hex,
                                    r = arguments.length > 2 ? arguments[2] : void 0,
                                    n = arguments.length > 3 && void 0 !== arguments[3] && arguments[3];
                                if (g.a.isFunction(r) && (n = r, r = {}), g.a.isFunction(t) ? (n = t, t = this.tronWeb.defaultAddress.hex) : g.a.isObject(t) && (r = t, t = this.tronWeb.defaultAddress.hex), !n) return this.injectPromise(this.deleteProposal, e, t, r);
                                if (!this.validator.notValid([{
                                    name: "issuer",
                                    type: "address",
                                    value: t
                                }, {name: "proposalID", type: "integer", value: e, gte: 0}], n)) {
                                    var i = {owner_address: A(t), proposal_id: parseInt(e)};
                                    r && r.permissionId && (i.Permission_id = r.permissionId), this.tronWeb.fullNode.request("wallet/proposaldelete", i, "post").then(function (e) {
                                        return S(e, n)
                                    }).catch(function (e) {
                                        return n(e)
                                    })
                                }
                            }
                        }, {
                            key: "voteProposal", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
                                    t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1],
                                    r = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : this.tronWeb.defaultAddress.hex,
                                    n = arguments.length > 3 ? arguments[3] : void 0,
                                    i = arguments.length > 4 && void 0 !== arguments[4] && arguments[4];
                                if (g.a.isFunction(n) && (i = n, n = {}), g.a.isFunction(r) ? (i = r, r = this.tronWeb.defaultAddress.hex) : g.a.isObject(r) && (n = r, r = this.tronWeb.defaultAddress.hex), !i) return this.injectPromise(this.voteProposal, e, t, r, n);
                                if (!this.validator.notValid([{
                                    name: "voter",
                                    type: "address",
                                    value: r
                                }, {name: "proposalID", type: "integer", value: e, gte: 0}, {
                                    name: "has approval",
                                    type: "boolean",
                                    value: t
                                }], i)) {
                                    var a = {owner_address: A(r), proposal_id: parseInt(e), is_add_approval: t};
                                    n && n.permissionId && (a.Permission_id = n.permissionId), this.tronWeb.fullNode.request("wallet/proposalapprove", a, "post").then(function (e) {
                                        return S(e, i)
                                    }).catch(function (e) {
                                        return i(e)
                                    })
                                }
                            }
                        }, {
                            key: "createTRXExchange", value: function (e, t, r) {
                                var n = arguments.length > 3 && void 0 !== arguments[3] ? arguments[3] : this.tronWeb.defaultAddress.hex,
                                    i = arguments.length > 4 ? arguments[4] : void 0,
                                    a = arguments.length > 5 && void 0 !== arguments[5] && arguments[5];
                                if (g.a.isFunction(i) && (a = i, i = {}), g.a.isFunction(n) ? (a = n, n = this.tronWeb.defaultAddress.hex) : g.a.isObject(n) && (i = n, n = this.tronWeb.defaultAddress.hex), !a) return this.injectPromise(this.createTRXExchange, e, t, r, n, i);
                                if (!this.validator.notValid([{
                                    name: "owner",
                                    type: "address",
                                    value: n
                                }, {name: "token name", type: "not-empty-string", value: e}, {
                                    name: "token balance",
                                    type: "positive-integer",
                                    value: t
                                }, {name: "trx balance", type: "positive-integer", value: r}], a)) {
                                    var o = {
                                        owner_address: A(n),
                                        first_token_id: k(e),
                                        first_token_balance: t,
                                        second_token_id: "5f",
                                        second_token_balance: r
                                    };
                                    i && i.permissionId && (o.Permission_id = i.permissionId), this.tronWeb.fullNode.request("wallet/exchangecreate", o, "post").then(function (e) {
                                        a(null, e)
                                    }).catch(function (e) {
                                        return a(e)
                                    })
                                }
                            }
                        }, {
                            key: "createTokenExchange", value: function (e, t, r, n) {
                                var i = arguments.length > 4 && void 0 !== arguments[4] ? arguments[4] : this.tronWeb.defaultAddress.hex,
                                    a = arguments.length > 5 ? arguments[5] : void 0,
                                    o = arguments.length > 6 && void 0 !== arguments[6] && arguments[6];
                                if (g.a.isFunction(a) && (o = a, a = {}), g.a.isFunction(i) ? (o = i, i = this.tronWeb.defaultAddress.hex) : g.a.isObject(i) && (a = i, i = this.tronWeb.defaultAddress.hex), !o) return this.injectPromise(this.createTokenExchange, e, t, r, n, i, a);
                                if (!this.validator.notValid([{
                                    name: "owner",
                                    type: "address",
                                    value: i
                                }, {
                                    name: "first token name",
                                    type: "not-empty-string",
                                    value: e
                                }, {
                                    name: "second token name",
                                    type: "not-empty-string",
                                    value: r
                                }, {
                                    name: "first token balance",
                                    type: "positive-integer",
                                    value: t
                                }, {name: "second token balance", type: "positive-integer", value: n}], o)) {
                                    var s = {
                                        owner_address: A(i),
                                        first_token_id: k(e),
                                        first_token_balance: t,
                                        second_token_id: k(r),
                                        second_token_balance: n
                                    };
                                    a && a.permissionId && (s.Permission_id = a.permissionId), this.tronWeb.fullNode.request("wallet/exchangecreate", s, "post").then(function (e) {
                                        o(null, e)
                                    }).catch(function (e) {
                                        return o(e)
                                    })
                                }
                            }
                        }, {
                            key: "injectExchangeTokens", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
                                    t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1],
                                    r = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : 0,
                                    n = arguments.length > 3 && void 0 !== arguments[3] ? arguments[3] : this.tronWeb.defaultAddress.hex,
                                    i = arguments.length > 4 ? arguments[4] : void 0,
                                    a = arguments.length > 5 && void 0 !== arguments[5] && arguments[5];
                                if (g.a.isFunction(i) && (a = i, i = {}), g.a.isFunction(n) ? (a = n, n = this.tronWeb.defaultAddress.hex) : g.a.isObject(n) && (i = n, n = this.tronWeb.defaultAddress.hex), !a) return this.injectPromise(this.injectExchangeTokens, e, t, r, n, i);
                                if (!this.validator.notValid([{
                                    name: "owner",
                                    type: "address",
                                    value: n
                                }, {name: "token name", type: "not-empty-string", value: t}, {
                                    name: "token amount",
                                    type: "integer",
                                    value: r,
                                    gte: 1
                                }, {name: "exchangeID", type: "integer", value: e, gte: 0}], a)) {
                                    var o = {
                                        owner_address: A(n),
                                        exchange_id: parseInt(e),
                                        token_id: k(t),
                                        quant: parseInt(r)
                                    };
                                    i && i.permissionId && (o.Permission_id = i.permissionId), this.tronWeb.fullNode.request("wallet/exchangeinject", o, "post").then(function (e) {
                                        return S(e, a)
                                    }).catch(function (e) {
                                        return a(e)
                                    })
                                }
                            }
                        }, {
                            key: "withdrawExchangeTokens", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
                                    t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1],
                                    r = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : 0,
                                    n = arguments.length > 3 && void 0 !== arguments[3] ? arguments[3] : this.tronWeb.defaultAddress.hex,
                                    i = arguments.length > 4 ? arguments[4] : void 0,
                                    a = arguments.length > 5 && void 0 !== arguments[5] && arguments[5];
                                if (g.a.isFunction(i) && (a = i, i = {}), g.a.isFunction(n) ? (a = n, n = this.tronWeb.defaultAddress.hex) : g.a.isObject(n) && (i = n, n = this.tronWeb.defaultAddress.hex), !a) return this.injectPromise(this.withdrawExchangeTokens, e, t, r, n, i);
                                if (!this.validator.notValid([{
                                    name: "owner",
                                    type: "address",
                                    value: n
                                }, {name: "token name", type: "not-empty-string", value: t}, {
                                    name: "token amount",
                                    type: "integer",
                                    value: r,
                                    gte: 1
                                }, {name: "exchangeID", type: "integer", value: e, gte: 0}], a)) {
                                    var o = {
                                        owner_address: A(n),
                                        exchange_id: parseInt(e),
                                        token_id: k(t),
                                        quant: parseInt(r)
                                    };
                                    i && i.permissionId && (o.Permission_id = i.permissionId), this.tronWeb.fullNode.request("wallet/exchangewithdraw", o, "post").then(function (e) {
                                        return S(e, a)
                                    }).catch(function (e) {
                                        return a(e)
                                    })
                                }
                            }
                        }, {
                            key: "tradeExchangeTokens", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
                                    t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1],
                                    r = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : 0,
                                    n = arguments.length > 3 && void 0 !== arguments[3] ? arguments[3] : 0,
                                    i = arguments.length > 4 && void 0 !== arguments[4] ? arguments[4] : this.tronWeb.defaultAddress.hex,
                                    a = arguments.length > 5 ? arguments[5] : void 0,
                                    o = arguments.length > 6 && void 0 !== arguments[6] && arguments[6];
                                if (g.a.isFunction(a) && (o = a, a = {}), g.a.isFunction(i) ? (o = i, i = this.tronWeb.defaultAddress.hex) : g.a.isObject(i) && (a = i, i = this.tronWeb.defaultAddress.hex), !o) return this.injectPromise(this.tradeExchangeTokens, e, t, r, n, i, a);
                                if (!this.validator.notValid([{
                                    name: "owner",
                                    type: "address",
                                    value: i
                                }, {name: "token name", type: "not-empty-string", value: t}, {
                                    name: "tokenAmountSold",
                                    type: "integer",
                                    value: r,
                                    gte: 1
                                }, {
                                    name: "tokenAmountExpected",
                                    type: "integer",
                                    value: n,
                                    gte: 1
                                }, {name: "exchangeID", type: "integer", value: e, gte: 0}], o)) {
                                    var s = {
                                        owner_address: A(i),
                                        exchange_id: parseInt(e),
                                        token_id: this.tronWeb.fromAscii(t),
                                        quant: parseInt(r),
                                        expected: parseInt(n)
                                    };
                                    a && a.permissionId && (s.Permission_id = a.permissionId), this.tronWeb.fullNode.request("wallet/exchangetransaction", s, "post").then(function (e) {
                                        return S(e, o)
                                    }).catch(function (e) {
                                        return o(e)
                                    })
                                }
                            }
                        }, {
                            key: "updateSetting", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
                                    t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1],
                                    r = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : this.tronWeb.defaultAddress.hex,
                                    n = arguments.length > 3 ? arguments[3] : void 0,
                                    i = arguments.length > 4 && void 0 !== arguments[4] && arguments[4];
                                if (g.a.isFunction(n) && (i = n, n = {}), g.a.isFunction(r) ? (i = r, r = this.tronWeb.defaultAddress.hex) : g.a.isObject(r) && (n = r, r = this.tronWeb.defaultAddress.hex), !i) return this.injectPromise(this.updateSetting, e, t, r, n);
                                if (!this.validator.notValid([{
                                    name: "owner",
                                    type: "address",
                                    value: r
                                }, {name: "contract", type: "address", value: e}, {
                                    name: "userFeePercentage",
                                    type: "integer",
                                    value: t,
                                    gte: 0,
                                    lte: 100
                                }], i)) {
                                    var a = {
                                        owner_address: A(r),
                                        contract_address: A(e),
                                        consume_user_resource_percent: t
                                    };
                                    n && n.permissionId && (a.Permission_id = n.permissionId), this.tronWeb.fullNode.request("wallet/updatesetting", a, "post").then(function (e) {
                                        return S(e, i)
                                    }).catch(function (e) {
                                        return i(e)
                                    })
                                }
                            }
                        }, {
                            key: "updateEnergyLimit", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
                                    t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1],
                                    r = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : this.tronWeb.defaultAddress.hex,
                                    n = arguments.length > 3 ? arguments[3] : void 0,
                                    i = arguments.length > 4 && void 0 !== arguments[4] && arguments[4];
                                if (g.a.isFunction(n) && (i = n, n = {}), g.a.isFunction(r) ? (i = r, r = this.tronWeb.defaultAddress.hex) : g.a.isObject(r) && (n = r, r = this.tronWeb.defaultAddress.hex), !i) return this.injectPromise(this.updateEnergyLimit, e, t, r, n);
                                if (!this.validator.notValid([{
                                    name: "owner",
                                    type: "address",
                                    value: r
                                }, {name: "contract", type: "address", value: e}, {
                                    name: "originEnergyLimit",
                                    type: "integer",
                                    value: t,
                                    gte: 0,
                                    lte: 1e7
                                }], i)) {
                                    var a = {owner_address: A(r), contract_address: A(e), origin_energy_limit: t};
                                    n && n.permissionId && (a.Permission_id = n.permissionId), this.tronWeb.fullNode.request("wallet/updateenergylimit", a, "post").then(function (e) {
                                        return S(e, i)
                                    }).catch(function (e) {
                                        return i(e)
                                    })
                                }
                            }
                        }, {
                            key: "checkPermissions", value: function (e, t) {
                                if (e) {
                                    if (e.type !== t || !e.permission_name || !g.a.isString(e.permission_name) || !g.a.isInteger(e.threshold) || e.threshold < 1 || !e.keys) return !1;
                                    var r = !0, n = !1, i = void 0;
                                    try {
                                        for (var a, o = e.keys[Symbol.iterator](); !(r = (a = o.next()).done); r = !0) {
                                            var s = a.value;
                                            if (!this.tronWeb.isAddress(s.address) || !g.a.isInteger(s.weight) || s.weight > e.threshold || s.weight < 1 || 2 === t && !e.operations) return !1
                                        }
                                    } catch (e) {
                                        n = !0, i = e
                                    } finally {
                                        try {
                                            r || null == o.return || o.return()
                                        } finally {
                                            if (n) throw i
                                        }
                                    }
                                }
                                return !0
                            }
                        }, {
                            key: "updateAccountPermissions", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : this.tronWeb.defaultAddress.hex,
                                    t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1],
                                    r = arguments.length > 2 && void 0 !== arguments[2] && arguments[2],
                                    n = arguments.length > 3 && void 0 !== arguments[3] && arguments[3],
                                    i = arguments.length > 4 && void 0 !== arguments[4] && arguments[4];
                                if (g.a.isFunction(n) && (i = n, n = !1), g.a.isFunction(r) && (i = r, r = n = !1), g.a.isFunction(t) && (i = t, t = r = n = !1), !i) return this.injectPromise(this.updateAccountPermissions, e, t, r, n);
                                if (!this.tronWeb.isAddress(e)) return i("Invalid ownerAddress provided");
                                if (!this.checkPermissions(t, 0)) return i("Invalid ownerPermissions provided");
                                if (!this.checkPermissions(r, 1)) return i("Invalid witnessPermissions provided");
                                Array.isArray(n) || (n = [n]);
                                var a = !0, o = !1, s = void 0;
                                try {
                                    for (var u, c = n[Symbol.iterator](); !(a = (u = c.next()).done); a = !0) {
                                        var f = u.value;
                                        if (!this.checkPermissions(f, 2)) return i("Invalid activesPermissions provided")
                                    }
                                } catch (e) {
                                    o = !0, s = e
                                } finally {
                                    try {
                                        a || null == c.return || c.return()
                                    } finally {
                                        if (o) throw s
                                    }
                                }
                                var d = {owner_address: e};
                                t && (d.owner = t), r && (d.witness = r), n && (d.actives = 1 === n.length ? n[0] : n), this.tronWeb.fullNode.request("wallet/accountpermissionupdate", d, "post").then(function (e) {
                                    return S(e, i)
                                }).catch(function (e) {
                                    return i(e)
                                })
                            }
                        }, {
                            key: "newTxID", value: (o = s()(a.a.mark(function e(t, r) {
                                return a.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (r) {
                                                e.next = 2;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this.newTxID, t));
                                        case 2:
                                            this.tronWeb.fullNode.request("wallet/getsignweight", t, "post").then(function (e) {
                                                e = e.transaction.transaction, "boolean" == typeof t.visible && (e.visible = t.visible), r(null, e)
                                            }).catch(function (e) {
                                                return r("Error generating a new transaction id.")
                                            });
                                        case 3:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function (e, t) {
                                return o.apply(this, arguments)
                            })
                        }, {
                            key: "alterTransaction", value: (i = s()(a.a.mark(function e(t) {
                                var r, n, i = arguments;
                                return a.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (r = i.length > 1 && void 0 !== i[1] ? i[1] : {}, n = i.length > 2 && void 0 !== i[2] && i[2]) {
                                                e.next = 4;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this.alterTransaction, t, r));
                                        case 4:
                                            if (!t.signature) {
                                                e.next = 6;
                                                break
                                            }
                                            return e.abrupt("return", n("You can not extend the expiration of a signed transaction."));
                                        case 6:
                                            if (!r.data) {
                                                e.next = 12;
                                                break
                                            }
                                            if ("hex" !== r.dataFormat && (r.data = this.tronWeb.toHex(r.data)), r.data = r.data.replace(/^0x/, ""), 0 !== r.data.length) {
                                                e.next = 11;
                                                break
                                            }
                                            return e.abrupt("return", n("Invalid data provided"));
                                        case 11:
                                            t.raw_data.data = r.data;
                                        case 12:
                                            if (!r.extension) {
                                                e.next = 17;
                                                break
                                            }
                                            if (r.extension = parseInt(1e3 * r.extension), !(isNaN(r.extension) || t.raw_data.expiration + r.extension <= Date.now() + 3e3)) {
                                                e.next = 16;
                                                break
                                            }
                                            return e.abrupt("return", n("Invalid extension provided"));
                                        case 16:
                                            t.raw_data.expiration += r.extension;
                                        case 17:
                                            this.newTxID(t, n);
                                        case 18:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function (e) {
                                return i.apply(this, arguments)
                            })
                        }, {
                            key: "extendExpiration", value: (r = s()(a.a.mark(function e(t, r) {
                                var n, i = arguments;
                                return a.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (n = i.length > 2 && void 0 !== i[2] && i[2]) {
                                                e.next = 3;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this.extendExpiration, t, r));
                                        case 3:
                                            this.alterTransaction(t, {extension: r}, n);
                                        case 4:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function (e, t) {
                                return r.apply(this, arguments)
                            })
                        }, {
                            key: "addUpdateData", value: (t = s()(a.a.mark(function e(t, r) {
                                var n, i, o = arguments;
                                return a.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (n = o.length > 2 && void 0 !== o[2] ? o[2] : "utf8", i = o.length > 3 && void 0 !== o[3] && o[3], g.a.isFunction(n) && (i = n, n = "utf8"), i) {
                                                e.next = 5;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this.addUpdateData, t, r, n));
                                        case 5:
                                            this.alterTransaction(t, {data: r, dataFormat: n}, i);
                                        case 6:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function (e, r) {
                                return t.apply(this, arguments)
                            })
                        }]), e
                    }()
                }, function (e, t, r) {
                    r.d(t, "a", function () {
                        return I
                    });
                    var n = r(1), i = r.n(n), a = r(22), o = r.n(a), s = r(20), u = r.n(s), c = r(2), f = r.n(c),
                        d = r(7), h = r.n(d), l = r(4), p = r.n(l), b = r(5), v = r.n(b), g = r(6), m = r(0), y = r(9),
                        w = r(3), x = r(24), _ = r(10), A = r.n(_);

                    function k(e, t) {
                        var r = Object.keys(e);
                        if (Object.getOwnPropertySymbols) {
                            var n = Object.getOwnPropertySymbols(e);
                            t && (n = n.filter(function (t) {
                                return Object.getOwnPropertyDescriptor(e, t).enumerable
                            })), r.push.apply(r, n)
                        }
                        return r
                    }

                    function S(e) {
                        for (var t = 1; t < arguments.length; t++) {
                            var r = null != arguments[t] ? arguments[t] : {};
                            t % 2 ? k(r, !0).forEach(function (t) {
                                h()(e, t, r[t])
                            }) : Object.getOwnPropertyDescriptors ? Object.defineProperties(e, Object.getOwnPropertyDescriptors(r)) : k(r).forEach(function (t) {
                                Object.defineProperty(e, t, Object.getOwnPropertyDescriptor(r, t))
                            })
                        }
                        return e
                    }

                    function M(e) {
                        return g.default.address.toHex(e)
                    }

                    var I = function () {
                        function e() {
                            var t = arguments.length > 0 && void 0 !== arguments[0] && arguments[0];
                            if (p()(this, e), !t || !t instanceof g.default) throw new Error("Expected instance of TronWeb");
                            this.tronWeb = t, this.injectPromise = A()(this), this.cache = {contracts: {}}, this.validator = new x.a(t)
                        }

                        var t, r, n, a, s, c, d, h, l, b, _, k, I, E, N, P, T;
                        return v()(e, [{
                            key: "_parseToken", value: function (e) {
                                return S({}, e, {
                                    name: this.tronWeb.toUtf8(e.name),
                                    abbr: e.abbr && this.tronWeb.toUtf8(e.abbr),
                                    description: e.description && this.tronWeb.toUtf8(e.description),
                                    url: e.url && this.tronWeb.toUtf8(e.url)
                                })
                            }
                        }, {
                            key: "getCurrentBlock", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0];
                                if (!e) return this.injectPromise(this.getCurrentBlock);
                                this.tronWeb.fullNode.request("wallet/getnowblock").then(function (t) {
                                    e(null, t)
                                }).catch(function (t) {
                                    return e(t)
                                })
                            }
                        }, {
                            key: "getConfirmedCurrentBlock", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0];
                                if (!e) return this.injectPromise(this.getConfirmedCurrentBlock);
                                this.tronWeb.solidityNode.request("walletsolidity/getnowblock").then(function (t) {
                                    e(null, t)
                                }).catch(function (t) {
                                    return e(t)
                                })
                            }
                        }, {
                            key: "getBlock", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : this.tronWeb.defaultBlock,
                                    t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1];
                                return m.a.isFunction(e) && (t = e, e = this.tronWeb.defaultBlock), t ? !1 === e ? t("No block identifier provided") : ("earliest" == e && (e = 0), "latest" == e ? this.getCurrentBlock(t) : isNaN(e) && m.a.isHex(e) ? this.getBlockByHash(e, t) : void this.getBlockByNumber(e, t)) : this.injectPromise(this.getBlock, e)
                            }
                        }, {
                            key: "getBlockByHash", value: function (e) {
                                var t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1];
                                if (!t) return this.injectPromise(this.getBlockByHash, e);
                                this.tronWeb.fullNode.request("wallet/getblockbyid", {value: e}, "post").then(function (e) {
                                    if (!Object.keys(e).length) return t("Block not found");
                                    t(null, e)
                                }).catch(function (e) {
                                    return t(e)
                                })
                            }
                        }, {
                            key: "getBlockByNumber", value: function (e) {
                                var t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1];
                                return t ? !m.a.isInteger(e) || e < 0 ? t("Invalid block number provided") : void this.tronWeb.fullNode.request("wallet/getblockbynum", {num: parseInt(e)}, "post").then(function (e) {
                                    if (!Object.keys(e).length) return t("Block not found");
                                    t(null, e)
                                }).catch(function (e) {
                                    return t(e)
                                }) : this.injectPromise(this.getBlockByNumber, e)
                            }
                        }, {
                            key: "getBlockTransactionCount", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : this.tronWeb.defaultBlock,
                                    t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1];
                                if (m.a.isFunction(e) && (t = e, e = this.tronWeb.defaultBlock), !t) return this.injectPromise(this.getBlockTransactionCount, e);
                                this.getBlock(e).then(function (e) {
                                    var r = e.transactions;
                                    t(null, (void 0 === r ? [] : r).length)
                                }).catch(function (e) {
                                    return t(e)
                                })
                            }
                        }, {
                            key: "getTransactionFromBlock", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : this.tronWeb.defaultBlock,
                                    t = arguments.length > 1 ? arguments[1] : void 0,
                                    r = arguments.length > 2 && void 0 !== arguments[2] && arguments[2];
                                if (m.a.isFunction(t) && (r = t, t = 0), m.a.isFunction(e) && (r = e, e = this.tronWeb.defaultBlock), !r) return this.injectPromise(this.getTransactionFromBlock, e, t);
                                this.getBlock(e).then(function (e) {
                                    var n = e.transactions, i = void 0 !== n && n;
                                    i ? "number" == typeof t ? t >= 0 && t < i.length ? r(null, i[t]) : r("Invalid transaction index provided") : r(null, i) : r("Transaction not found in block")
                                }).catch(function (e) {
                                    return r(e)
                                })
                            }
                        }, {
                            key: "getTransaction", value: function (e) {
                                var t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1];
                                if (!t) return this.injectPromise(this.getTransaction, e);
                                this.tronWeb.fullNode.request("wallet/gettransactionbyid", {value: e}, "post").then(function (e) {
                                    if (!Object.keys(e).length) return t("Transaction not found");
                                    t(null, e)
                                }).catch(function (e) {
                                    return t(e)
                                })
                            }
                        }, {
                            key: "getConfirmedTransaction", value: function (e) {
                                var t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1];
                                if (!t) return this.injectPromise(this.getConfirmedTransaction, e);
                                this.tronWeb.solidityNode.request("walletsolidity/gettransactionbyid", {value: e}, "post").then(function (e) {
                                    if (!Object.keys(e).length) return t("Transaction not found");
                                    t(null, e)
                                }).catch(function (e) {
                                    return t(e)
                                })
                            }
                        }, {
                            key: "getUnconfirmedTransactionInfo", value: function (e) {
                                var t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1];
                                return this._getTransactionInfoById(e, {confirmed: !1}, t)
                            }
                        }, {
                            key: "getTransactionInfo", value: function (e) {
                                var t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1];
                                return this._getTransactionInfoById(e, {confirmed: !0}, t)
                            }
                        }, {
                            key: "_getTransactionInfoById", value: function (e, t) {
                                var r = arguments.length > 2 && void 0 !== arguments[2] && arguments[2];
                                if (!r) return this.injectPromise(this._getTransactionInfoById, e, t);
                                this.tronWeb[t.confirmed ? "solidityNode" : "fullNode"].request("wallet".concat(t.confirmed ? "solidity" : "", "/gettransactioninfobyid"), {value: e}, "post").then(function (e) {
                                    r(null, e)
                                }).catch(function (e) {
                                    return r(e)
                                })
                            }
                        }, {
                            key: "getTransactionsToAddress", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : this.tronWeb.defaultAddress.hex,
                                    t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : 30,
                                    r = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : 0,
                                    n = arguments.length > 3 && void 0 !== arguments[3] && arguments[3];
                                return m.a.isFunction(r) && (n = r, r = 0), m.a.isFunction(t) && (n = t, t = 30), n ? (e = this.tronWeb.address.toHex(e), this.getTransactionsRelated(e, "to", t, r, n)) : this.injectPromise(this.getTransactionsToAddress, e, t, r)
                            }
                        }, {
                            key: "getTransactionsFromAddress", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : this.tronWeb.defaultAddress.hex,
                                    t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : 30,
                                    r = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : 0,
                                    n = arguments.length > 3 && void 0 !== arguments[3] && arguments[3];
                                return m.a.isFunction(r) && (n = r, r = 0), m.a.isFunction(t) && (n = t, t = 30), n ? (e = this.tronWeb.address.toHex(e), this.getTransactionsRelated(e, "from", t, r, n)) : this.injectPromise(this.getTransactionsFromAddress, e, t, r)
                            }
                        }, {
                            key: "getTransactionsRelated", value: (T = f()(i.a.mark(function e() {
                                var t, r, n, a, s, c, f, d, h, l = arguments;
                                return i.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (t = l.length > 0 && void 0 !== l[0] ? l[0] : this.tronWeb.defaultAddress.hex, r = l.length > 1 && void 0 !== l[1] ? l[1] : "all", n = l.length > 2 && void 0 !== l[2] ? l[2] : 30, a = l.length > 3 && void 0 !== l[3] ? l[3] : 0, s = l.length > 4 && void 0 !== l[4] && l[4], m.a.isFunction(a) && (s = a, a = 0), m.a.isFunction(n) && (s = n, n = 30), m.a.isFunction(r) && (s = r, r = "all"), m.a.isFunction(t) && (s = t, t = this.tronWeb.defaultAddress.hex), s) {
                                                e.next = 11;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this.getTransactionsRelated, t, r, n, a));
                                        case 11:
                                            if (["to", "from", "all"].includes(r)) {
                                                e.next = 13;
                                                break
                                            }
                                            return e.abrupt("return", s('Invalid direction provided: Expected "to", "from" or "all"'));
                                        case 13:
                                            if ("all" != r) {
                                                e.next = 27;
                                                break
                                            }
                                            return e.prev = 14, e.next = 17, Promise.all([this.getTransactionsRelated(t, "from", n, a), this.getTransactionsRelated(t, "to", n, a)]);
                                        case 17:
                                            return c = e.sent, f = u()(c, 2), d = f[0], h = f[1], e.abrupt("return", s(null, [].concat(o()(d.map(function (e) {
                                                return e.direction = "from", e
                                            })), o()(h.map(function (e) {
                                                return e.direction = "to", e
                                            }))).sort(function (e, t) {
                                                return t.raw_data.timestamp - e.raw_data.timestamp
                                            })));
                                        case 24:
                                            return e.prev = 24, e.t0 = e.catch(14), e.abrupt("return", s(e.t0));
                                        case 27:
                                            if (this.tronWeb.isAddress(t)) {
                                                e.next = 29;
                                                break
                                            }
                                            return e.abrupt("return", s("Invalid address provided"));
                                        case 29:
                                            if (!(!m.a.isInteger(n) || n < 0 || a && n < 1)) {
                                                e.next = 31;
                                                break
                                            }
                                            return e.abrupt("return", s("Invalid limit provided"));
                                        case 31:
                                            if (m.a.isInteger(a) && !(a < 0)) {
                                                e.next = 33;
                                                break
                                            }
                                            return e.abrupt("return", s("Invalid offset provided"));
                                        case 33:
                                            t = this.tronWeb.address.toHex(t), this.tronWeb.solidityNode.request("walletextension/gettransactions".concat(r, "this"), {
                                                account: {address: t},
                                                offset: a,
                                                limit: n
                                            }, "post").then(function (e) {
                                                var t = e.transaction;
                                                s(null, t)
                                            }).catch(function (e) {
                                                return s(e)
                                            });
                                        case 35:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this, [[14, 24]])
                            })), function () {
                                return T.apply(this, arguments)
                            })
                        }, {
                            key: "getAccount", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : this.tronWeb.defaultAddress.hex,
                                    t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1];
                                return m.a.isFunction(e) && (t = e, e = this.tronWeb.defaultAddress.hex), t ? this.tronWeb.isAddress(e) ? (e = this.tronWeb.address.toHex(e), void this.tronWeb.solidityNode.request("walletsolidity/getaccount", {address: e}, "post").then(function (e) {
                                    t(null, e)
                                }).catch(function (e) {
                                    return t(e)
                                })) : t("Invalid address provided") : this.injectPromise(this.getAccount, e)
                            }
                        }, {
                            key: "getAccountById", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
                                    t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1];
                                if (!t) return this.injectPromise(this.getAccountById, e);
                                this.getAccountInfoById(e, {confirmed: !0}, t)
                            }
                        }, {
                            key: "getAccountInfoById", value: function (e, t, r) {
                                this.validator.notValid([{name: "accountId", type: "hex", value: e}, {
                                    name: "accountId",
                                    type: "string",
                                    lte: 32,
                                    gte: 8,
                                    value: e
                                }], r) || (e.startsWith("0x") && (e = e.slice(2)), this.tronWeb[t.confirmed ? "solidityNode" : "fullNode"].request("wallet".concat(t.confirmed ? "solidity" : "", "/getaccountbyid"), {account_id: e}, "post").then(function (e) {
                                    r(null, e)
                                }).catch(function (e) {
                                    return r(e)
                                }))
                            }
                        }, {
                            key: "getBalance", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : this.tronWeb.defaultAddress.hex,
                                    t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1];
                                if (m.a.isFunction(e) && (t = e, e = this.tronWeb.defaultAddress.hex), !t) return this.injectPromise(this.getBalance, e);
                                this.getAccount(e).then(function (e) {
                                    var r = e.balance;
                                    t(null, void 0 === r ? 0 : r)
                                }).catch(function (e) {
                                    return t(e)
                                })
                            }
                        }, {
                            key: "getUnconfirmedAccount", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : this.tronWeb.defaultAddress.hex,
                                    t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1];
                                return m.a.isFunction(e) && (t = e, e = this.tronWeb.defaultAddress.hex), t ? this.tronWeb.isAddress(e) ? (e = this.tronWeb.address.toHex(e), void this.tronWeb.fullNode.request("wallet/getaccount", {address: e}, "post").then(function (e) {
                                    t(null, e)
                                }).catch(function (e) {
                                    return t(e)
                                })) : t("Invalid address provided") : this.injectPromise(this.getUnconfirmedAccount, e)
                            }
                        }, {
                            key: "getUnconfirmedAccountById", value: function (e) {
                                var t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1];
                                if (!t) return this.injectPromise(this.getUnconfirmedAccountById, e);
                                this.getAccountInfoById(e, {confirmed: !1}, t)
                            }
                        }, {
                            key: "getUnconfirmedBalance", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : this.tronWeb.defaultAddress.hex,
                                    t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1];
                                if (m.a.isFunction(e) && (t = e, e = this.tronWeb.defaultAddress.hex), !t) return this.injectPromise(this.getUnconfirmedBalance, e);
                                this.getUnconfirmedAccount(e).then(function (e) {
                                    var r = e.balance;
                                    t(null, void 0 === r ? 0 : r)
                                }).catch(function (e) {
                                    return t(e)
                                })
                            }
                        }, {
                            key: "getBandwidth", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : this.tronWeb.defaultAddress.hex,
                                    t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1];
                                return m.a.isFunction(e) && (t = e, e = this.tronWeb.defaultAddress.hex), t ? this.tronWeb.isAddress(e) ? (e = this.tronWeb.address.toHex(e), void this.tronWeb.fullNode.request("wallet/getaccountnet", {address: e}, "post").then(function (e) {
                                    var r = e.freeNetUsed, n = void 0 === r ? 0 : r, i = e.freeNetLimit,
                                        a = void 0 === i ? 0 : i, o = e.NetUsed, s = void 0 === o ? 0 : o,
                                        u = e.NetLimit;
                                    t(null, a - n + ((void 0 === u ? 0 : u) - s))
                                }).catch(function (e) {
                                    return t(e)
                                })) : t("Invalid address provided") : this.injectPromise(this.getBandwidth, e)
                            }
                        }, {
                            key: "getTokensIssuedByAddress", value: function () {
                                var e = this,
                                    t = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : this.tronWeb.defaultAddress.hex,
                                    r = arguments.length > 1 && void 0 !== arguments[1] && arguments[1];
                                return m.a.isFunction(t) && (r = t, t = this.tronWeb.defaultAddress.hex), r ? this.tronWeb.isAddress(t) ? (t = this.tronWeb.address.toHex(t), void this.tronWeb.fullNode.request("wallet/getassetissuebyaccount", {address: t}, "post").then(function (t) {
                                    var n = t.assetIssue, i = void 0 !== n && n;
                                    if (!i) return r(null, {});
                                    var a = i.map(function (t) {
                                        return e._parseToken(t)
                                    }).reduce(function (e, t) {
                                        return e[t.name] = t, e
                                    }, {});
                                    r(null, a)
                                }).catch(function (e) {
                                    return r(e)
                                })) : r("Invalid address provided") : this.injectPromise(this.getTokensIssuedByAddress, t)
                            }
                        }, {
                            key: "getTokenFromID", value: function () {
                                var e = this, t = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
                                    r = arguments.length > 1 && void 0 !== arguments[1] && arguments[1];
                                return r ? (m.a.isInteger(t) && (t = t.toString()), m.a.isString(t) && t.length ? void this.tronWeb.fullNode.request("wallet/getassetissuebyname", {value: this.tronWeb.fromUtf8(t)}, "post").then(function (t) {
                                    if (!t.name) return r("Token does not exist");
                                    r(null, e._parseToken(t))
                                }).catch(function (e) {
                                    return r(e)
                                }) : r("Invalid token ID provided")) : this.injectPromise(this.getTokenFromID, t)
                            }
                        }, {
                            key: "listNodes", value: function () {
                                var e = this, t = arguments.length > 0 && void 0 !== arguments[0] && arguments[0];
                                if (!t) return this.injectPromise(this.listNodes);
                                this.tronWeb.fullNode.request("wallet/listnodes").then(function (r) {
                                    var n = r.nodes;
                                    t(null, (void 0 === n ? [] : n).map(function (t) {
                                        var r = t.address, n = r.host, i = r.port;
                                        return "".concat(e.tronWeb.toUtf8(n), ":").concat(i)
                                    }))
                                }).catch(function (e) {
                                    return t(e)
                                })
                            }
                        }, {
                            key: "getBlockRange", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : 0,
                                    t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : 30,
                                    r = arguments.length > 2 && void 0 !== arguments[2] && arguments[2];
                                return m.a.isFunction(t) && (r = t, t = 30), m.a.isFunction(e) && (r = e, e = 0), r ? !m.a.isInteger(e) || e < 0 ? r("Invalid start of range provided") : !m.a.isInteger(t) || t <= e ? r("Invalid end of range provided") : void this.tronWeb.fullNode.request("wallet/getblockbylimitnext", {
                                    startNum: parseInt(e),
                                    endNum: parseInt(t) + 1
                                }, "post").then(function (e) {
                                    var t = e.block;
                                    r(null, void 0 === t ? [] : t)
                                }).catch(function (e) {
                                    return r(e)
                                }) : this.injectPromise(this.getBlockRange, e, t)
                            }
                        }, {
                            key: "listSuperRepresentatives", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0];
                                if (!e) return this.injectPromise(this.listSuperRepresentatives);
                                this.tronWeb.fullNode.request("wallet/listwitnesses").then(function (t) {
                                    var r = t.witnesses;
                                    e(null, void 0 === r ? [] : r)
                                }).catch(function (t) {
                                    return e(t)
                                })
                            }
                        }, {
                            key: "listTokens", value: function () {
                                var e = this, t = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : 0,
                                    r = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : 0,
                                    n = arguments.length > 2 && void 0 !== arguments[2] && arguments[2];
                                return m.a.isFunction(r) && (n = r, r = 0), m.a.isFunction(t) && (n = t, t = 0), n ? !m.a.isInteger(t) || t < 0 || r && t < 1 ? n("Invalid limit provided") : !m.a.isInteger(r) || r < 0 ? n("Invalid offset provided") : t ? void this.tronWeb.fullNode.request("wallet/getpaginatedassetissuelist", {
                                    offset: parseInt(r),
                                    limit: parseInt(t)
                                }, "post").then(function (t) {
                                    var r = t.assetIssue;
                                    n(null, (void 0 === r ? [] : r).map(function (t) {
                                        return e._parseToken(t)
                                    }))
                                }).catch(function (e) {
                                    return n(e)
                                }) : this.tronWeb.fullNode.request("wallet/getassetissuelist").then(function (t) {
                                    var r = t.assetIssue;
                                    n(null, (void 0 === r ? [] : r).map(function (t) {
                                        return e._parseToken(t)
                                    }))
                                }).catch(function (e) {
                                    return n(e)
                                }) : this.injectPromise(this.listTokens, t, r)
                            }
                        }, {
                            key: "timeUntilNextVoteCycle", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0];
                                if (!e) return this.injectPromise(this.timeUntilNextVoteCycle);
                                this.tronWeb.fullNode.request("wallet/getnextmaintenancetime").then(function (t) {
                                    var r = t.num, n = void 0 === r ? -1 : r;
                                    if (-1 == n) return e("Failed to get time until next vote cycle");
                                    e(null, Math.floor(n / 1e3))
                                }).catch(function (t) {
                                    return e(t)
                                })
                            }
                        }, {
                            key: "getContract", value: function (e) {
                                var t = this, r = arguments.length > 1 && void 0 !== arguments[1] && arguments[1];
                                return r ? this.tronWeb.isAddress(e) ? void (this.cache.contracts[e] ? r(null, this.cache.contracts[e]) : (e = this.tronWeb.address.toHex(e), this.tronWeb.fullNode.request("wallet/getcontract", {value: e}).then(function (n) {
                                    if (n.Error) return r("Contract does not exist");
                                    t.cache.contracts[e] = n, r(null, n)
                                }).catch(function (e) {
                                    return r(e)
                                }))) : r("Invalid contract address provided") : this.injectPromise(this.getContract, e)
                            }
                        }, {
                            key: "verifyMessage", value: (P = f()(i.a.mark(function t() {
                                var r, n, a, o, s, u = arguments;
                                return i.a.wrap(function (t) {
                                    for (; ;) switch (t.prev = t.next) {
                                        case 0:
                                            if (r = u.length > 0 && void 0 !== u[0] && u[0], n = u.length > 1 && void 0 !== u[1] && u[1], a = u.length > 2 && void 0 !== u[2] ? u[2] : this.tronWeb.defaultAddress.base58, o = !(u.length > 3 && void 0 !== u[3]) || u[3], s = u.length > 4 && void 0 !== u[4] && u[4], m.a.isFunction(a) && (s = a, a = this.tronWeb.defaultAddress.base58, o = !0), m.a.isFunction(o) && (s = o, o = !0), s) {
                                                t.next = 9;
                                                break
                                            }
                                            return t.abrupt("return", this.injectPromise(this.verifyMessage, r, n, a, o));
                                        case 9:
                                            if (m.a.isHex(r)) {
                                                t.next = 11;
                                                break
                                            }
                                            return t.abrupt("return", s("Expected hex message input"));
                                        case 11:
                                            if (!e.verifySignature(r, a, n, o)) {
                                                t.next = 13;
                                                break
                                            }
                                            return t.abrupt("return", s(null, !0));
                                        case 13:
                                            s("Signature does not match");
                                        case 14:
                                        case"end":
                                            return t.stop()
                                    }
                                }, t, this)
                            })), function () {
                                return P.apply(this, arguments)
                            })
                        }, {
                            key: "sign", value: (N = f()(i.a.mark(function t() {
                                var r, n, a, o, s, u, c = arguments;
                                return i.a.wrap(function (t) {
                                    for (; ;) switch (t.prev = t.next) {
                                        case 0:
                                            if (r = c.length > 0 && void 0 !== c[0] && c[0], n = c.length > 1 && void 0 !== c[1] ? c[1] : this.tronWeb.defaultPrivateKey, a = !(c.length > 2 && void 0 !== c[2]) || c[2], o = c.length > 3 && void 0 !== c[3] && c[3], s = c.length > 4 && void 0 !== c[4] && c[4], m.a.isFunction(o) && (s = o, o = !1), m.a.isFunction(a) && (s = a, a = !0, o = !1), m.a.isFunction(n) && (s = n, n = this.tronWeb.defaultPrivateKey, a = !0, o = !1), s) {
                                                t.next = 10;
                                                break
                                            }
                                            return t.abrupt("return", this.injectPromise(this.sign, r, n, a, o));
                                        case 10:
                                            if (!m.a.isString(r)) {
                                                t.next = 21;
                                                break
                                            }
                                            if (m.a.isHex(r)) {
                                                t.next = 13;
                                                break
                                            }
                                            return t.abrupt("return", s("Expected hex message input"));
                                        case 13:
                                            return t.prev = 13, u = e.signString(r, n, a), t.abrupt("return", s(null, u));
                                        case 18:
                                            t.prev = 18, t.t0 = t.catch(13), s(t.t0);
                                        case 21:
                                            if (m.a.isObject(r)) {
                                                t.next = 23;
                                                break
                                            }
                                            return t.abrupt("return", s("Invalid transaction provided"));
                                        case 23:
                                            if (o || !r.signature) {
                                                t.next = 25;
                                                break
                                            }
                                            return t.abrupt("return", s("Transaction is already signed"));
                                        case 25:
                                            if (t.prev = 25, o) {
                                                t.next = 30;
                                                break
                                            }
                                            if (this.tronWeb.address.toHex(this.tronWeb.address.fromPrivateKey(n)).toLowerCase() === this.tronWeb.address.toHex(r.raw_data.contract[0].parameter.value.owner_address)) {
                                                t.next = 30;
                                                break
                                            }
                                            return t.abrupt("return", s("Private key does not match address in transaction"));
                                        case 30:
                                            return t.abrupt("return", s(null, m.a.crypto.signTransaction(n, r)));
                                        case 33:
                                            t.prev = 33, t.t1 = t.catch(25), s(t.t1);
                                        case 36:
                                        case"end":
                                            return t.stop()
                                    }
                                }, t, this, [[13, 18], [25, 33]])
                            })), function () {
                                return N.apply(this, arguments)
                            })
                        }, {
                            key: "multiSign", value: (E = f()(i.a.mark(function e() {
                                var t, r, n, a, o, s, u, c = arguments;
                                return i.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (t = c.length > 0 && void 0 !== c[0] && c[0], r = c.length > 1 && void 0 !== c[1] ? c[1] : this.tronWeb.defaultPrivateKey, n = c.length > 2 && void 0 !== c[2] && c[2], a = c.length > 3 && void 0 !== c[3] && c[3], m.a.isFunction(n) && (a = n, n = 0), m.a.isFunction(r) && (a = r, r = this.tronWeb.defaultPrivateKey, n = 0), a) {
                                                e.next = 8;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this.multiSign, t, r, n));
                                        case 8:
                                            if (m.a.isObject(t) && t.raw_data && t.raw_data.contract) {
                                                e.next = 10;
                                                break
                                            }
                                            return e.abrupt("return", a("Invalid transaction provided"));
                                        case 10:
                                            if (t.raw_data.contract[0].Permission_id || !(n > 0)) {
                                                e.next = 30;
                                                break
                                            }
                                            return t.raw_data.contract[0].Permission_id = n, o = this.tronWeb.address.toHex(this.tronWeb.address.fromPrivateKey(r)).toLowerCase(), e.next = 15, this.getSignWeight(t, n);
                                        case 15:
                                            if ("PERMISSION_ERROR" !== (s = e.sent).result.code) {
                                                e.next = 18;
                                                break
                                            }
                                            return e.abrupt("return", a(s.result.message));
                                        case 18:
                                            if (u = !1, s.permission.keys.map(function (e) {
                                                e.address === o && (u = !0)
                                            }), u) {
                                                e.next = 22;
                                                break
                                            }
                                            return e.abrupt("return", a(r + " has no permission to sign"));
                                        case 22:
                                            if (!s.approved_list || -1 == s.approved_list.indexOf(o)) {
                                                e.next = 24;
                                                break
                                            }
                                            return e.abrupt("return", a(r + " already sign transaction"));
                                        case 24:
                                            if (!s.transaction || !s.transaction.transaction) {
                                                e.next = 29;
                                                break
                                            }
                                            t = s.transaction.transaction, n > 0 && (t.raw_data.contract[0].Permission_id = n), e.next = 30;
                                            break;
                                        case 29:
                                            return e.abrupt("return", a("Invalid transaction provided"));
                                        case 30:
                                            return e.prev = 30, e.abrupt("return", a(null, m.a.crypto.signTransaction(r, t)));
                                        case 34:
                                            e.prev = 34, e.t0 = e.catch(30), a(e.t0);
                                        case 37:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this, [[30, 34]])
                            })), function () {
                                return E.apply(this, arguments)
                            })
                        }, {
                            key: "getApprovedList", value: (I = f()(i.a.mark(function e(t) {
                                var r, n = arguments;
                                return i.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (r = n.length > 1 && void 0 !== n[1] && n[1]) {
                                                e.next = 3;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this.getApprovedList, t));
                                        case 3:
                                            if (m.a.isObject(t)) {
                                                e.next = 5;
                                                break
                                            }
                                            return e.abrupt("return", r("Invalid transaction provided"));
                                        case 5:
                                            this.tronWeb.fullNode.request("wallet/getapprovedlist", t, "post").then(function (e) {
                                                r(null, e)
                                            }).catch(function (e) {
                                                return r(e)
                                            });
                                        case 6:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function (e) {
                                return I.apply(this, arguments)
                            })
                        }, {
                            key: "getSignWeight", value: (k = f()(i.a.mark(function e(t, r) {
                                var n, a = arguments;
                                return i.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (n = a.length > 2 && void 0 !== a[2] && a[2], m.a.isFunction(r) && (n = r, r = void 0), n) {
                                                e.next = 4;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this.getSignWeight, t, r));
                                        case 4:
                                            if (m.a.isObject(t) && t.raw_data && t.raw_data.contract) {
                                                e.next = 6;
                                                break
                                            }
                                            return e.abrupt("return", n("Invalid transaction provided"));
                                        case 6:
                                            if (m.a.isInteger(r) ? t.raw_data.contract[0].Permission_id = parseInt(r) : "number" != typeof t.raw_data.contract[0].Permission_id && (t.raw_data.contract[0].Permission_id = 0), m.a.isObject(t)) {
                                                e.next = 9;
                                                break
                                            }
                                            return e.abrupt("return", n("Invalid transaction provided"));
                                        case 9:
                                            this.tronWeb.fullNode.request("wallet/getsignweight", t, "post").then(function (e) {
                                                n(null, e)
                                            }).catch(function (e) {
                                                return n(e)
                                            });
                                        case 10:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function (e, t) {
                                return k.apply(this, arguments)
                            })
                        }, {
                            key: "sendRawTransaction", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
                                    t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : {},
                                    r = arguments.length > 2 && void 0 !== arguments[2] && arguments[2];
                                return m.a.isFunction(t) && (r = t, t = {}), r ? m.a.isObject(e) ? m.a.isObject(t) ? e.signature && m.a.isArray(e.signature) ? void this.tronWeb.fullNode.request("wallet/broadcasttransaction", e, "post").then(function (t) {
                                    t.result && (t.transaction = e), r(null, t)
                                }).catch(function (e) {
                                    return r(e)
                                }) : r("Transaction is not signed") : r("Invalid options provided") : r("Invalid transaction provided") : this.injectPromise(this.sendRawTransaction, e, t)
                            }
                        }, {
                            key: "sendTransaction", value: (_ = f()(i.a.mark(function e() {
                                var t, r, n, a, o, s, u, c, f = arguments;
                                return i.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (t = f.length > 0 && void 0 !== f[0] && f[0], r = f.length > 1 && void 0 !== f[1] && f[1], n = f.length > 2 && void 0 !== f[2] ? f[2] : {}, a = f.length > 3 && void 0 !== f[3] && f[3], m.a.isFunction(n) && (a = n, n = {}), "string" == typeof n && (n = {privateKey: n}), a) {
                                                e.next = 8;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this.sendTransaction, t, r, n));
                                        case 8:
                                            if (this.tronWeb.isAddress(t)) {
                                                e.next = 10;
                                                break
                                            }
                                            return e.abrupt("return", a("Invalid recipient provided"));
                                        case 10:
                                            if (m.a.isInteger(r) && !(r <= 0)) {
                                                e.next = 12;
                                                break
                                            }
                                            return e.abrupt("return", a("Invalid amount provided"));
                                        case 12:
                                            if ((n = S({
                                                privateKey: this.tronWeb.defaultPrivateKey,
                                                address: this.tronWeb.defaultAddress.hex
                                            }, n)).privateKey || n.address) {
                                                e.next = 15;
                                                break
                                            }
                                            return e.abrupt("return", a("Function requires either a private key or address to be set"));
                                        case 15:
                                            return e.prev = 15, o = n.privateKey ? this.tronWeb.address.fromPrivateKey(n.privateKey) : n.address, e.next = 19, this.tronWeb.transactionBuilder.sendTrx(t, r, o);
                                        case 19:
                                            return s = e.sent, e.next = 22, this.sign(s, n.privateKey || void 0);
                                        case 22:
                                            return u = e.sent, e.next = 25, this.sendRawTransaction(u);
                                        case 25:
                                            return c = e.sent, e.abrupt("return", a(null, c));
                                        case 29:
                                            return e.prev = 29, e.t0 = e.catch(15), e.abrupt("return", a(e.t0));
                                        case 32:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this, [[15, 29]])
                            })), function () {
                                return _.apply(this, arguments)
                            })
                        }, {
                            key: "sendToken", value: (b = f()(i.a.mark(function e() {
                                var t, r, n, a, o, s, u, c, f, d = arguments;
                                return i.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (t = d.length > 0 && void 0 !== d[0] && d[0], r = d.length > 1 && void 0 !== d[1] && d[1], n = d.length > 2 && void 0 !== d[2] && d[2], a = d.length > 3 && void 0 !== d[3] ? d[3] : {}, o = d.length > 4 && void 0 !== d[4] && d[4], m.a.isFunction(a) && (o = a, a = {}), "string" == typeof a && (a = {privateKey: a}), o) {
                                                e.next = 9;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this.sendToken, t, r, n, a));
                                        case 9:
                                            if (this.tronWeb.isAddress(t)) {
                                                e.next = 11;
                                                break
                                            }
                                            return e.abrupt("return", o("Invalid recipient provided"));
                                        case 11:
                                            if (m.a.isInteger(r) && !(r <= 0)) {
                                                e.next = 13;
                                                break
                                            }
                                            return e.abrupt("return", o("Invalid amount provided"));
                                        case 13:
                                            if (m.a.isInteger(n) && (n = n.toString()), m.a.isString(n)) {
                                                e.next = 16;
                                                break
                                            }
                                            return e.abrupt("return", o("Invalid token ID provided"));
                                        case 16:
                                            if ((a = S({
                                                privateKey: this.tronWeb.defaultPrivateKey,
                                                address: this.tronWeb.defaultAddress.hex
                                            }, a)).privateKey || a.address) {
                                                e.next = 19;
                                                break
                                            }
                                            return e.abrupt("return", o("Function requires either a private key or address to be set"));
                                        case 19:
                                            return e.prev = 19, s = a.privateKey ? this.tronWeb.address.fromPrivateKey(a.privateKey) : a.address, e.next = 23, this.tronWeb.transactionBuilder.sendToken(t, r, n, s);
                                        case 23:
                                            return u = e.sent, e.next = 26, this.sign(u, a.privateKey || void 0);
                                        case 26:
                                            return c = e.sent, e.next = 29, this.sendRawTransaction(c);
                                        case 29:
                                            return f = e.sent, e.abrupt("return", o(null, f));
                                        case 33:
                                            return e.prev = 33, e.t0 = e.catch(19), e.abrupt("return", o(e.t0));
                                        case 36:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this, [[19, 33]])
                            })), function () {
                                return b.apply(this, arguments)
                            })
                        }, {
                            key: "freezeBalance", value: (l = f()(i.a.mark(function e() {
                                var t, r, n, a, o, s, u, c, f, d, h = arguments;
                                return i.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (t = h.length > 0 && void 0 !== h[0] ? h[0] : 0, r = h.length > 1 && void 0 !== h[1] ? h[1] : 3, n = h.length > 2 && void 0 !== h[2] ? h[2] : "BANDWIDTH", a = h.length > 3 && void 0 !== h[3] ? h[3] : {}, o = h.length > 4 && void 0 !== h[4] ? h[4] : void 0, s = h.length > 5 && void 0 !== h[5] && h[5], m.a.isFunction(o) && (s = o, o = void 0), m.a.isFunction(r) && (s = r, r = 3), m.a.isFunction(n) && (s = n, n = "BANDWIDTH"), m.a.isFunction(a) && (s = a, a = {}), "string" == typeof a && (a = {privateKey: a}), s) {
                                                e.next = 13;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this.freezeBalance, t, r, n, a, o));
                                        case 13:
                                            if (["BANDWIDTH", "ENERGY"].includes(n)) {
                                                e.next = 15;
                                                break
                                            }
                                            return e.abrupt("return", s('Invalid resource provided: Expected "BANDWIDTH" or "ENERGY"'));
                                        case 15:
                                            if (m.a.isInteger(t) && !(t <= 0)) {
                                                e.next = 17;
                                                break
                                            }
                                            return e.abrupt("return", s("Invalid amount provided"));
                                        case 17:
                                            if (m.a.isInteger(r) && !(r < 3)) {
                                                e.next = 19;
                                                break
                                            }
                                            return e.abrupt("return", s("Invalid duration provided, minimum of 3 days"));
                                        case 19:
                                            if ((a = S({
                                                privateKey: this.tronWeb.defaultPrivateKey,
                                                address: this.tronWeb.defaultAddress.hex
                                            }, a)).privateKey || a.address) {
                                                e.next = 22;
                                                break
                                            }
                                            return e.abrupt("return", s("Function requires either a private key or address to be set"));
                                        case 22:
                                            return e.prev = 22, u = a.privateKey ? this.tronWeb.address.fromPrivateKey(a.privateKey) : a.address, e.next = 26, this.tronWeb.transactionBuilder.freezeBalance(t, r, n, u, o);
                                        case 26:
                                            return c = e.sent, e.next = 29, this.sign(c, a.privateKey || void 0);
                                        case 29:
                                            return f = e.sent, e.next = 32, this.sendRawTransaction(f);
                                        case 32:
                                            return d = e.sent, e.abrupt("return", s(null, d));
                                        case 36:
                                            return e.prev = 36, e.t0 = e.catch(22), e.abrupt("return", s(e.t0));
                                        case 39:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this, [[22, 36]])
                            })), function () {
                                return l.apply(this, arguments)
                            })
                        }, {
                            key: "unfreezeBalance", value: (h = f()(i.a.mark(function e() {
                                var t, r, n, a, o, s, u, c, f = arguments;
                                return i.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (t = f.length > 0 && void 0 !== f[0] ? f[0] : "BANDWIDTH", r = f.length > 1 && void 0 !== f[1] ? f[1] : {}, n = f.length > 2 && void 0 !== f[2] ? f[2] : void 0, a = f.length > 3 && void 0 !== f[3] && f[3], m.a.isFunction(n) && (a = n, n = void 0), m.a.isFunction(t) && (a = t, t = "BANDWIDTH"), m.a.isFunction(r) && (a = r, r = {}), "string" == typeof r && (r = {privateKey: r}), a) {
                                                e.next = 10;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this.unfreezeBalance, t, r, n));
                                        case 10:
                                            if (["BANDWIDTH", "ENERGY"].includes(t)) {
                                                e.next = 12;
                                                break
                                            }
                                            return e.abrupt("return", a('Invalid resource provided: Expected "BANDWIDTH" or "ENERGY"'));
                                        case 12:
                                            if ((r = S({
                                                privateKey: this.tronWeb.defaultPrivateKey,
                                                address: this.tronWeb.defaultAddress.hex
                                            }, r)).privateKey || r.address) {
                                                e.next = 15;
                                                break
                                            }
                                            return e.abrupt("return", a("Function requires either a private key or address to be set"));
                                        case 15:
                                            return e.prev = 15, o = r.privateKey ? this.tronWeb.address.fromPrivateKey(r.privateKey) : r.address, e.next = 19, this.tronWeb.transactionBuilder.unfreezeBalance(t, o, n);
                                        case 19:
                                            return s = e.sent, e.next = 22, this.sign(s, r.privateKey || void 0);
                                        case 22:
                                            return u = e.sent, e.next = 25, this.sendRawTransaction(u);
                                        case 25:
                                            return c = e.sent, e.abrupt("return", a(null, c));
                                        case 29:
                                            return e.prev = 29, e.t0 = e.catch(15), e.abrupt("return", a(e.t0));
                                        case 32:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this, [[15, 29]])
                            })), function () {
                                return h.apply(this, arguments)
                            })
                        }, {
                            key: "updateAccount", value: (d = f()(i.a.mark(function e() {
                                var t, r, n, a, o, s, u, c = arguments;
                                return i.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (t = c.length > 0 && void 0 !== c[0] && c[0], r = c.length > 1 && void 0 !== c[1] ? c[1] : {}, n = c.length > 2 && void 0 !== c[2] && c[2], m.a.isFunction(r) && (n = r, r = {}), "string" == typeof r && (r = {privateKey: r}), n) {
                                                e.next = 7;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this.updateAccount, t, r));
                                        case 7:
                                            if (m.a.isString(t) && t.length) {
                                                e.next = 9;
                                                break
                                            }
                                            return e.abrupt("return", n("Name must be a string"));
                                        case 9:
                                            if ((r = S({
                                                privateKey: this.tronWeb.defaultPrivateKey,
                                                address: this.tronWeb.defaultAddress.hex
                                            }, r)).privateKey || r.address) {
                                                e.next = 12;
                                                break
                                            }
                                            return e.abrupt("return", n("Function requires either a private key or address to be set"));
                                        case 12:
                                            return e.prev = 12, a = r.privateKey ? this.tronWeb.address.fromPrivateKey(r.privateKey) : r.address, e.next = 16, this.tronWeb.transactionBuilder.updateAccount(t, a);
                                        case 16:
                                            return o = e.sent, e.next = 19, this.sign(o, r.privateKey || void 0);
                                        case 19:
                                            return s = e.sent, e.next = 22, this.sendRawTransaction(s);
                                        case 22:
                                            return u = e.sent, e.abrupt("return", n(null, u));
                                        case 26:
                                            return e.prev = 26, e.t0 = e.catch(12), e.abrupt("return", n(e.t0));
                                        case 29:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this, [[12, 26]])
                            })), function () {
                                return d.apply(this, arguments)
                            })
                        }, {
                            key: "signMessage", value: function () {
                                return this.sign.apply(this, arguments)
                            }
                        }, {
                            key: "sendAsset", value: function () {
                                return this.sendToken.apply(this, arguments)
                            }
                        }, {
                            key: "send", value: function () {
                                return this.sendTransaction.apply(this, arguments)
                            }
                        }, {
                            key: "sendTrx", value: function () {
                                return this.sendTransaction.apply(this, arguments)
                            }
                        }, {
                            key: "broadcast", value: function () {
                                return this.sendRawTransaction.apply(this, arguments)
                            }
                        }, {
                            key: "signTransaction", value: function () {
                                return this.sign.apply(this, arguments)
                            }
                        }, {
                            key: "getProposal", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
                                    t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1];
                                return t ? !m.a.isInteger(e) || e < 0 ? t("Invalid proposalID provided") : void this.tronWeb.fullNode.request("wallet/getproposalbyid", {id: parseInt(e)}, "post").then(function (e) {
                                    t(null, e)
                                }).catch(function (e) {
                                    return t(e)
                                }) : this.injectPromise(this.getProposal, e)
                            }
                        }, {
                            key: "listProposals", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0];
                                if (!e) return this.injectPromise(this.listProposals);
                                this.tronWeb.fullNode.request("wallet/listproposals", {}, "post").then(function (t) {
                                    var r = t.proposals;
                                    e(null, void 0 === r ? [] : r)
                                }).catch(function (t) {
                                    return e(t)
                                })
                            }
                        }, {
                            key: "getChainParameters", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0];
                                if (!e) return this.injectPromise(this.getChainParameters);
                                this.tronWeb.fullNode.request("wallet/getchainparameters", {}, "post").then(function (t) {
                                    var r = t.chainParameter;
                                    e(null, void 0 === r ? [] : r)
                                }).catch(function (t) {
                                    return e(t)
                                })
                            }
                        }, {
                            key: "getAccountResources", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : this.tronWeb.defaultAddress.hex,
                                    t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1];
                                return t ? this.tronWeb.isAddress(e) ? void this.tronWeb.fullNode.request("wallet/getaccountresource", {address: this.tronWeb.address.toHex(e)}, "post").then(function (e) {
                                    t(null, e)
                                }).catch(function (e) {
                                    return t(e)
                                }) : t("Invalid address provided") : this.injectPromise(this.getAccountResources, e)
                            }
                        }, {
                            key: "getExchangeByID", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
                                    t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1];
                                return t ? !m.a.isInteger(e) || e < 0 ? t("Invalid exchangeID provided") : void this.tronWeb.fullNode.request("wallet/getexchangebyid", {id: e}, "post").then(function (e) {
                                    t(null, e)
                                }).catch(function (e) {
                                    return t(e)
                                }) : this.injectPromise(this.getExchangeByID, e)
                            }
                        }, {
                            key: "listExchanges", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0];
                                if (!e) return this.injectPromise(this.listExchanges);
                                this.tronWeb.fullNode.request("wallet/listexchanges", {}, "post").then(function (t) {
                                    var r = t.exchanges;
                                    e(null, void 0 === r ? [] : r)
                                }, "post").catch(function (t) {
                                    return e(t)
                                })
                            }
                        }, {
                            key: "listExchangesPaginated", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : 10,
                                    t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : 0,
                                    r = arguments.length > 2 && void 0 !== arguments[2] && arguments[2];
                                if (m.a.isFunction(t) && (r = t, t = 0), m.a.isFunction(e) && (r = e, e = 10), !r) return this.injectPromise(this.listExchangesPaginated, e, t);
                                this.tronWeb.fullNode.request("wallet/getpaginatedexchangelist", {
                                    limit: e,
                                    offset: t
                                }, "post").then(function (e) {
                                    var t = e.exchanges;
                                    r(null, void 0 === t ? [] : t)
                                }).catch(function (e) {
                                    return r(e)
                                })
                            }
                        }, {
                            key: "getNodeInfo", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0];
                                if (!e) return this.injectPromise(this.getNodeInfo);
                                this.tronWeb.fullNode.request("wallet/getnodeinfo", {}, "post").then(function (t) {
                                    e(null, t)
                                }, "post").catch(function (t) {
                                    return e(t)
                                })
                            }
                        }, {
                            key: "getTokenListByName", value: function () {
                                var e = this, t = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
                                    r = arguments.length > 1 && void 0 !== arguments[1] && arguments[1];
                                return r ? (m.a.isInteger(t) && (t = t.toString()), m.a.isString(t) && t.length ? void this.tronWeb.fullNode.request("wallet/getassetissuelistbyname", {value: this.tronWeb.fromUtf8(t)}, "post").then(function (t) {
                                    if (Array.isArray(t.assetIssue)) r(null, t.assetIssue.map(function (t) {
                                        return e._parseToken(t)
                                    })); else if (!t.name) return r("Token does not exist");
                                    r(null, e._parseToken(t))
                                }).catch(function (e) {
                                    return r(e)
                                }) : r("Invalid token ID provided")) : this.injectPromise(this.getTokenListByName, t)
                            }
                        }, {
                            key: "getTokenByID", value: function () {
                                var e = this, t = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
                                    r = arguments.length > 1 && void 0 !== arguments[1] && arguments[1];
                                return r ? (m.a.isInteger(t) && (t = t.toString()), m.a.isString(t) && t.length ? void this.tronWeb.fullNode.request("wallet/getassetissuebyid", {value: t}, "post").then(function (t) {
                                    if (!t.name) return r("Token does not exist");
                                    r(null, e._parseToken(t))
                                }).catch(function (e) {
                                    return r(e)
                                }) : r("Invalid token ID provided")) : this.injectPromise(this.getTokenByID, t)
                            }
                        }, {
                            key: "getReward", value: (c = f()(i.a.mark(function e(t) {
                                var r, n, a = arguments;
                                return i.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            return r = a.length > 1 && void 0 !== a[1] ? a[1] : {}, n = a.length > 2 && void 0 !== a[2] && a[2], r.confirmed = !0, e.abrupt("return", this._getReward(t, r, n));
                                        case 4:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function (e) {
                                return c.apply(this, arguments)
                            })
                        }, {
                            key: "getUnconfirmedReward", value: (s = f()(i.a.mark(function e(t) {
                                var r, n, a = arguments;
                                return i.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            return r = a.length > 1 && void 0 !== a[1] ? a[1] : {}, n = a.length > 2 && void 0 !== a[2] && a[2], r.confirmed = !1, e.abrupt("return", this._getReward(t, r, n));
                                        case 4:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function (e) {
                                return s.apply(this, arguments)
                            })
                        }, {
                            key: "getBrokerage", value: (a = f()(i.a.mark(function e(t) {
                                var r, n, a = arguments;
                                return i.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            return r = a.length > 1 && void 0 !== a[1] ? a[1] : {}, n = a.length > 2 && void 0 !== a[2] && a[2], r.confirmed = !0, e.abrupt("return", this._getBrokerage(t, r, n));
                                        case 4:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function (e) {
                                return a.apply(this, arguments)
                            })
                        }, {
                            key: "getUnconfirmedBrokerage", value: (n = f()(i.a.mark(function e(t) {
                                var r, n, a = arguments;
                                return i.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            return r = a.length > 1 && void 0 !== a[1] ? a[1] : {}, n = a.length > 2 && void 0 !== a[2] && a[2], r.confirmed = !1, e.abrupt("return", this._getBrokerage(t, r, n));
                                        case 4:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function (e) {
                                return n.apply(this, arguments)
                            })
                        }, {
                            key: "_getReward", value: (r = f()(i.a.mark(function e() {
                                var t, r, n, a, o = arguments;
                                return i.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (t = o.length > 0 && void 0 !== o[0] ? o[0] : this.tronWeb.defaultAddress.hex, r = o.length > 1 ? o[1] : void 0, n = o.length > 2 && void 0 !== o[2] && o[2], m.a.isFunction(r) && (n = r, r = {}), m.a.isFunction(t) ? (n = t, t = this.tronWeb.defaultAddress.hex) : m.a.isObject(t) && (r = t, t = this.tronWeb.defaultAddress.hex), n) {
                                                e.next = 7;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this._getReward, t, r));
                                        case 7:
                                            if (!this.validator.notValid([{
                                                name: "origin",
                                                type: "address",
                                                value: t
                                            }], n)) {
                                                e.next = 9;
                                                break
                                            }
                                            return e.abrupt("return");
                                        case 9:
                                            a = {address: M(t)}, this.tronWeb[r.confirmed ? "solidityNode" : "fullNode"].request("wallet".concat(r.confirmed ? "solidity" : "", "/getReward"), a, "post").then(function () {
                                                var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : {};
                                                if (void 0 === e.reward) return n("Not found.");
                                                n(null, e.reward)
                                            }).catch(function (e) {
                                                return n(e)
                                            });
                                        case 11:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function () {
                                return r.apply(this, arguments)
                            })
                        }, {
                            key: "_getBrokerage", value: (t = f()(i.a.mark(function e() {
                                var t, r, n, a, o = arguments;
                                return i.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (t = o.length > 0 && void 0 !== o[0] ? o[0] : this.tronWeb.defaultAddress.hex, r = o.length > 1 ? o[1] : void 0, n = o.length > 2 && void 0 !== o[2] && o[2], m.a.isFunction(r) && (n = r, r = {}), m.a.isFunction(t) ? (n = t, t = this.tronWeb.defaultAddress.hex) : m.a.isObject(t) && (r = t, t = this.tronWeb.defaultAddress.hex), n) {
                                                e.next = 7;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this._getBrokerage, t, r));
                                        case 7:
                                            if (!this.validator.notValid([{
                                                name: "origin",
                                                type: "address",
                                                value: t
                                            }], n)) {
                                                e.next = 9;
                                                break
                                            }
                                            return e.abrupt("return");
                                        case 9:
                                            a = {address: M(t)}, this.tronWeb[r.confirmed ? "solidityNode" : "fullNode"].request("wallet".concat(r.confirmed ? "solidity" : "", "/getBrokerage"), a, "post").then(function () {
                                                var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : {};
                                                if (void 0 === e.brokerage) return n("Not found.");
                                                n(null, e.brokerage)
                                            }).catch(function (e) {
                                                return n(e)
                                            });
                                        case 11:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function () {
                                return t.apply(this, arguments)
                            })
                        }], [{
                            key: "verifySignature", value: function (e, t, r) {
                                var n = !(arguments.length > 3 && void 0 !== arguments[3]) || arguments[3];
                                e = e.replace(/^0x/, ""), r = r.replace(/^0x/, "");
                                var i = [].concat(o()(Object(y.toUtf8Bytes)(n ? "TRON Signed Message:\n32" : "Ethereum Signed Message:\n32")), o()(m.a.code.hexStr2byteArray(e))),
                                    a = Object(y.keccak256)(i), s = Object(y.recoverAddress)(a, {
                                        recoveryParam: "1c" == r.substring(128, 130) ? 1 : 0,
                                        r: "0x" + r.substring(0, 64),
                                        s: "0x" + r.substring(64, 128)
                                    }), u = w.a + s.substr(2);
                                return g.default.address.fromHex(u) == g.default.address.fromHex(t)
                            }
                        }, {
                            key: "signString", value: function (e, t) {
                                return !(arguments.length > 2 && void 0 !== arguments[2]) || arguments[2], ""
                            }
                        }]), e
                    }()
                }, function (e, t, r) {
                    r.d(t, "a", function () {
                        return v
                    });
                    var n = r(17), i = r.n(n), a = r(4), o = r.n(a), s = r(5), u = r.n(s), c = r(6), f = r(0), d = r(8),
                        h = r(72), l = r.n(h), p = r(10), b = r.n(p), v = function () {
                            function e() {
                                var t = arguments.length > 0 && void 0 !== arguments[0] && arguments[0];
                                if (o()(this, e), !(t && t instanceof c.default)) throw new Error("Expected instance of TronWeb");
                                this.tronWeb = t, this.injectPromise = b()(this)
                            }

                            return u()(e, [{
                                key: "setServer", value: function () {
                                    var e = this, t = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
                                        r = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : "healthcheck";
                                    if (!t) return this.tronWeb.eventServer = !1;
                                    if (f.a.isString(t) && (t = new d.a.HttpProvider(t)), !this.tronWeb.isValidProvider(t)) throw new Error("Invalid event server provided");
                                    this.tronWeb.eventServer = t, this.tronWeb.eventServer.isConnected = function () {
                                        return e.tronWeb.eventServer.request(r).then(function () {
                                            return !0
                                        }).catch(function () {
                                            return !1
                                        })
                                    }
                                }
                            }, {
                                key: "getEventsByContractAddress", value: function () {
                                    var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
                                        t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : {},
                                        r = arguments.length > 2 && void 0 !== arguments[2] && arguments[2],
                                        n = Object.assign({
                                            sinceTimestamp: 0,
                                            eventName: !1,
                                            blockNumber: !1,
                                            size: 20,
                                            page: 1
                                        }, t), a = n.sinceTimestamp, o = n.since, s = n.fromTimestamp, u = n.eventName,
                                        c = n.blockNumber, d = n.size, h = n.page, p = n.onlyConfirmed,
                                        b = n.onlyUnconfirmed, v = n.previousLastEventFingerprint,
                                        g = n.previousFingerprint, m = n.fingerprint, y = n.rawResponse, w = n.sort,
                                        x = n.filters;
                                    if (!r) return this.injectPromise(this.getEventsByContractAddress, e, t);
                                    if (s = s || a || o, !this.tronWeb.eventServer) return r("No event server configured");
                                    var _ = [];
                                    if (!this.tronWeb.isAddress(e)) return r("Invalid contract address provided");
                                    if (u && !e) return r("Usage of event name filtering requires a contract address");
                                    if (void 0 !== s && !f.a.isInteger(s)) return r("Invalid fromTimestamp provided");
                                    if (!f.a.isInteger(d)) return r("Invalid size provided");
                                    if (d > 200 && (console.warn("Defaulting to maximum accepted size: 200"), d = 200), !f.a.isInteger(h)) return r("Invalid page provided");
                                    if (c && !u) return r("Usage of block number filtering requires an event name");
                                    e && _.push(this.tronWeb.address.fromHex(e)), u && _.push(u), c && _.push(c);
                                    var A = {size: d, page: h};
                                    return "object" === i()(x) && Object.keys(x).length > 0 && (A.filters = JSON.stringify(x)), s && (A.fromTimestamp = A.since = s), p && (A.onlyConfirmed = p), b && !p && (A.onlyUnconfirmed = b), w && (A.sort = w), (m = m || g || v) && (A.fingerprint = m), this.tronWeb.eventServer.request("event/contract/".concat(_.join("/"), "?").concat(l.a.stringify(A))).then(function () {
                                        var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0];
                                        return e ? f.a.isArray(e) ? r(null, !0 === y ? e : e.map(function (e) {
                                            return f.a.mapEvent(e)
                                        })) : r(e) : r("Unknown error occurred")
                                    }).catch(function (e) {
                                        return r(e.response && e.response.data || e)
                                    })
                                }
                            }, {
                                key: "getEventsByTransactionID", value: function () {
                                    var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
                                        t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : {},
                                        r = arguments.length > 2 && void 0 !== arguments[2] && arguments[2];
                                    return f.a.isFunction(t) && (r = t, t = {}), r ? this.tronWeb.eventServer ? this.tronWeb.eventServer.request("event/transaction/".concat(e)).then(function () {
                                        var e = arguments.length > 0 && void 0 !== arguments[0] && arguments[0];
                                        return e ? f.a.isArray(e) ? r(null, !0 === t.rawResponse ? e : e.map(function (e) {
                                            return f.a.mapEvent(e)
                                        })) : r(e) : r("Unknown error occurred")
                                    }).catch(function (e) {
                                        return r(e.response && e.response.data || e)
                                    }) : r("No event server configured") : this.injectPromise(this.getEventsByTransactionID, e, t)
                                }
                            }]), e
                        }()
                }, function (e, t, r) {
                    var n = r(1), i = r.n(n), a = r(20), o = r.n(a), s = r(2), u = r.n(s), c = r(4), f = r.n(c),
                        d = r(5), h = r.n(d), l = r(6), p = r(0), b = r(7), v = r.n(b), g = r(3), m = r(10), y = r.n(m);

                    function w(e, t) {
                        var r = Object.keys(e);
                        if (Object.getOwnPropertySymbols) {
                            var n = Object.getOwnPropertySymbols(e);
                            t && (n = n.filter(function (t) {
                                return Object.getOwnPropertyDescriptor(e, t).enumerable
                            })), r.push.apply(r, n)
                        }
                        return r
                    }

                    function x(e) {
                        for (var t = 1; t < arguments.length; t++) {
                            var r = null != arguments[t] ? arguments[t] : {};
                            t % 2 ? w(r, !0).forEach(function (t) {
                                v()(e, t, r[t])
                            }) : Object.getOwnPropertyDescriptors ? Object.defineProperties(e, Object.getOwnPropertyDescriptors(r)) : w(r).forEach(function (t) {
                                Object.defineProperty(e, t, Object.getOwnPropertyDescriptor(r, t))
                            })
                        }
                        return e
                    }

                    var _ = function (e) {
                        return e.name + "(" + A(e.inputs || []).join(",") + ")"
                    }, A = function (e) {
                        return e.map(function (e) {
                            return e.type
                        })
                    }, k = function (e, t) {
                        var r = e.map(function (e) {
                            return e.name
                        }).filter(function (e) {
                            return !!e
                        }), n = e.map(function (e) {
                            return e.type
                        });
                        return p.a.abi.decodeParams(r, n, t)
                    }, S = function () {
                        function e(t, r) {
                            f()(this, e), this.tronWeb = t.tronWeb, this.contract = t, this.abi = r, this.name = r.name || (r.name = r.type), this.inputs = r.inputs || [], this.outputs = r.outputs || [], this.functionSelector = _(r), this.signature = this.tronWeb.sha3(this.functionSelector, !1).slice(0, 8), this.injectPromise = y()(this), this.defaultOptions = {
                                feeLimit: this.tronWeb.feeLimit,
                                callValue: 0,
                                userFeePercentage: 100,
                                shouldPollResponse: !1
                            }
                        }

                        var t, r, n;
                        return h()(e, [{
                            key: "decodeInput", value: function (e) {
                                return k(this.inputs, "0x" + e)
                            }
                        }, {
                            key: "onMethod", value: function () {
                                for (var e = this, t = arguments.length, r = new Array(t), n = 0; n < t; n++) r[n] = arguments[n];
                                var i = A(this.inputs);
                                return r.forEach(function (t, n) {
                                    "address" == i[n] && (r[n] = e.tronWeb.address.toHex(t).replace(g.c, "0x")), "address[]" == i[n] && (r[n] = r[n].map(function (t) {
                                        return e.tronWeb.address.toHex(t).replace(g.c, "0x")
                                    }))
                                }), {
                                    call: function () {
                                        for (var t = arguments.length, n = new Array(t), a = 0; a < t; a++) n[a] = arguments[a];
                                        return e._call.apply(e, [i, r].concat(n))
                                    }, send: function () {
                                        for (var t = arguments.length, n = new Array(t), a = 0; a < t; a++) n[a] = arguments[a];
                                        return e._send.apply(e, [i, r].concat(n))
                                    }, watch: function () {
                                        return e._watch.apply(e, arguments)
                                    }
                                }
                            }
                        }, {
                            key: "_call", value: (n = u()(i.a.mark(function e(t, r) {
                                var n, a, o, s, u = this, c = arguments;
                                return i.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (n = c.length > 2 && void 0 !== c[2] ? c[2] : {}, a = c.length > 3 && void 0 !== c[3] && c[3], p.a.isFunction(n) && (a = n, n = {}), a) {
                                                e.next = 5;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this._call, t, r, n));
                                        case 5:
                                            if (t.length === r.length) {
                                                e.next = 7;
                                                break
                                            }
                                            return e.abrupt("return", a("Invalid argument count provided"));
                                        case 7:
                                            if (this.contract.address) {
                                                e.next = 9;
                                                break
                                            }
                                            return e.abrupt("return", a("Smart contract is missing address"));
                                        case 9:
                                            if (this.contract.deployed) {
                                                e.next = 11;
                                                break
                                            }
                                            return e.abrupt("return", a("Calling smart contracts requires you to load the contract first"));
                                        case 11:
                                            if (o = this.abi.stateMutability, ["pure", "view"].includes(o.toLowerCase())) {
                                                e.next = 14;
                                                break
                                            }
                                            return e.abrupt("return", a('Methods with state mutability "'.concat(o, '" must use send()')));
                                        case 14:
                                            n = x({}, this.defaultOptions, {from: this.tronWeb.defaultAddress.hex}, n), s = r.map(function (e, r) {
                                                return {type: t[r], value: e}
                                            }), this.tronWeb.transactionBuilder.triggerSmartContract(this.contract.address, this.functionSelector, n, s, !!n.from && this.tronWeb.address.toHex(n.from), function (e, t) {
                                                if (e) return a(e);
                                                if (!p.a.hasProperty(t, "constant_result")) return a("Failed to execute");
                                                try {
                                                    var r = t.constant_result[0].length;
                                                    if (0 === r || r % 64 == 8) {
                                                        var n = "The call has been reverted or has thrown an error.";
                                                        if (0 !== r) {
                                                            n += " Error message: ";
                                                            for (var i = "", o = t.constant_result[0].substring(8), s = 0; s < r - 8; s += 64) i += u.tronWeb.toUtf8(o.substring(s, s + 64));
                                                            n += i.replace(/(\u0000|\u000b|\f)+/g, " ").replace(/ +/g, " ").replace(/\s+$/g, "")
                                                        }
                                                        return a(n)
                                                    }
                                                    var c = k(u.outputs, "0x" + t.constant_result[0]);
                                                    return 1 === c.length && (c = c[0]), a(null, c)
                                                } catch (e) {
                                                    return a(e)
                                                }
                                            });
                                        case 17:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function (e, t) {
                                return n.apply(this, arguments)
                            })
                        }, {
                            key: "_send", value: (r = u()(i.a.mark(function e(t, r) {
                                var n, a, o, s, c, f, d, h, l, b, v, g = this, m = arguments;
                                return i.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (n = m.length > 2 && void 0 !== m[2] ? m[2] : {}, a = m.length > 3 && void 0 !== m[3] ? m[3] : this.tronWeb.defaultPrivateKey, o = m.length > 4 && void 0 !== m[4] && m[4], p.a.isFunction(a) && (o = a, a = this.tronWeb.defaultPrivateKey), p.a.isFunction(n) && (o = n, n = {}), o) {
                                                e.next = 7;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this._send, t, r, n, a));
                                        case 7:
                                            if (t.length === r.length) {
                                                e.next = 9;
                                                break
                                            }
                                            throw new Error("Invalid argument count provided");
                                        case 9:
                                            if (this.contract.address) {
                                                e.next = 11;
                                                break
                                            }
                                            return e.abrupt("return", o("Smart contract is missing address"));
                                        case 11:
                                            if (this.contract.deployed) {
                                                e.next = 13;
                                                break
                                            }
                                            return e.abrupt("return", o("Calling smart contracts requires you to load the contract first"));
                                        case 13:
                                            if (s = this.abi.stateMutability, !["pure", "view"].includes(s.toLowerCase())) {
                                                e.next = 16;
                                                break
                                            }
                                            return e.abrupt("return", o('Methods with state mutability "'.concat(s, '" must use call()')));
                                        case 16:
                                            return ["payable"].includes(s.toLowerCase()) || (n.callValue = 0), n = x({}, this.defaultOptions, {from: this.tronWeb.defaultAddress.hex}, n), c = r.map(function (e, r) {
                                                return {type: t[r], value: e}
                                            }), e.prev = 19, f = a ? this.tronWeb.address.fromPrivateKey(a) : this.tronWeb.defaultAddress.base58, e.next = 23, this.tronWeb.transactionBuilder.triggerSmartContract(this.contract.address, this.functionSelector, n, c, this.tronWeb.address.toHex(f));
                                        case 23:
                                            if ((d = e.sent).result && d.result.result) {
                                                e.next = 26;
                                                break
                                            }
                                            return e.abrupt("return", o("Unknown error: " + JSON.stringify(d, null, 2)));
                                        case 26:
                                            return e.next = 28, this.tronWeb.trx.sign(d.transaction, a);
                                        case 28:
                                            if ((h = e.sent).signature) {
                                                e.next = 33;
                                                break
                                            }
                                            if (a) {
                                                e.next = 32;
                                                break
                                            }
                                            return e.abrupt("return", o("Transaction was not signed properly"));
                                        case 32:
                                            return e.abrupt("return", o("Invalid private key provided"));
                                        case 33:
                                            return e.next = 35, this.tronWeb.trx.sendRawTransaction(h);
                                        case 35:
                                            if (!(l = e.sent).code) {
                                                e.next = 40;
                                                break
                                            }
                                            return b = {
                                                error: l.code,
                                                message: l.code
                                            }, l.message && (b.message = this.tronWeb.toUtf8(l.message)), e.abrupt("return", o(b));
                                        case 40:
                                            if (n.shouldPollResponse) {
                                                e.next = 42;
                                                break
                                            }
                                            return e.abrupt("return", o(null, h.txID));
                                        case 42:
                                            (v = function () {
                                                var e = u()(i.a.mark(function e() {
                                                    var t, r, a, s = arguments;
                                                    return i.a.wrap(function (e) {
                                                        for (; ;) switch (e.prev = e.next) {
                                                            case 0:
                                                                if (20 !== (t = s.length > 0 && void 0 !== s[0] ? s[0] : 0)) {
                                                                    e.next = 3;
                                                                    break
                                                                }
                                                                return e.abrupt("return", o({
                                                                    error: "Cannot find result in solidity node",
                                                                    transaction: h
                                                                }));
                                                            case 3:
                                                                return e.next = 5, g.tronWeb.trx.getTransactionInfo(h.txID);
                                                            case 5:
                                                                if (r = e.sent, Object.keys(r).length) {
                                                                    e.next = 8;
                                                                    break
                                                                }
                                                                return e.abrupt("return", setTimeout(function () {
                                                                    v(t + 1)
                                                                }, 3e3));
                                                            case 8:
                                                                if (!r.result || "FAILED" !== r.result) {
                                                                    e.next = 10;
                                                                    break
                                                                }
                                                                return e.abrupt("return", o({
                                                                    error: g.tronWeb.toUtf8(r.resMessage),
                                                                    transaction: h,
                                                                    output: r
                                                                }));
                                                            case 10:
                                                                if (p.a.hasProperty(r, "contractResult")) {
                                                                    e.next = 12;
                                                                    break
                                                                }
                                                                return e.abrupt("return", o({
                                                                    error: "Failed to execute: " + JSON.stringify(r, null, 2),
                                                                    transaction: h,
                                                                    output: r
                                                                }));
                                                            case 12:
                                                                if (!n.rawResponse) {
                                                                    e.next = 14;
                                                                    break
                                                                }
                                                                return e.abrupt("return", o(null, r));
                                                            case 14:
                                                                if (1 === (a = k(g.outputs, "0x" + r.contractResult[0])).length && (a = a[0]), !n.keepTxID) {
                                                                    e.next = 18;
                                                                    break
                                                                }
                                                                return e.abrupt("return", o(null, [h.txID, a]));
                                                            case 18:
                                                                return e.abrupt("return", o(null, a));
                                                            case 19:
                                                            case"end":
                                                                return e.stop()
                                                        }
                                                    }, e)
                                                }));
                                                return function () {
                                                    return e.apply(this, arguments)
                                                }
                                            }())(), e.next = 49;
                                            break;
                                        case 46:
                                            return e.prev = 46, e.t0 = e.catch(19), e.abrupt("return", o(e.t0));
                                        case 49:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this, [[19, 46]])
                            })), function (e, t) {
                                return r.apply(this, arguments)
                            })
                        }, {
                            key: "_watch", value: (t = u()(i.a.mark(function e() {
                                var t, r, n, a, s, c, f, d = this, h = arguments;
                                return i.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (t = h.length > 0 && void 0 !== h[0] ? h[0] : {}, r = h.length > 1 && void 0 !== h[1] && h[1], p.a.isFunction(t) && (r = t, t = {}), p.a.isFunction(r)) {
                                                e.next = 5;
                                                break
                                            }
                                            throw new Error("Expected callback to be provided");
                                        case 5:
                                            if (this.contract.address) {
                                                e.next = 7;
                                                break
                                            }
                                            return e.abrupt("return", r("Smart contract is missing address"));
                                        case 7:
                                            if (this.abi.type && /event/i.test(this.abi.type)) {
                                                e.next = 9;
                                                break
                                            }
                                            return e.abrupt("return", r("Invalid method type for event watching"));
                                        case 9:
                                            if (this.tronWeb.eventServer) {
                                                e.next = 11;
                                                break
                                            }
                                            return e.abrupt("return", r("No event server configured"));
                                        case 11:
                                            return n = !1, a = !1, s = Date.now() - 1e3, c = function () {
                                                var e = u()(i.a.mark(function e() {
                                                    var r, n, u, c, f, h;
                                                    return i.a.wrap(function (e) {
                                                        for (; ;) switch (e.prev = e.next) {
                                                            case 0:
                                                                return e.prev = 0, r = {
                                                                    since: s,
                                                                    eventName: d.name,
                                                                    sort: "block_timestamp",
                                                                    blockNumber: "latest",
                                                                    filters: t.filters
                                                                }, t.resourceNode && (/full/i.test(t.resourceNode) ? r.onlyUnconfirmed = !0 : r.onlyConfirmed = !0), e.next = 5, d.tronWeb.event.getEventsByContractAddress(d.contract.address, r);
                                                            case 5:
                                                                return n = e.sent, u = n.sort(function (e, t) {
                                                                    return t.block - e.block
                                                                }), c = o()(u, 1), f = c[0], h = n.filter(function (e, r) {
                                                                    return !(t.resourceNode && e.resourceNode && t.resourceNode.toLowerCase() !== e.resourceNode.toLowerCase() || n.slice(0, r).some(function (t) {
                                                                        return JSON.stringify(t) == JSON.stringify(e)
                                                                    }) || a && !(e.block > a))
                                                                }), f && (a = f.block), e.abrupt("return", h);
                                                            case 12:
                                                                return e.prev = 12, e.t0 = e.catch(0), e.abrupt("return", Promise.reject(e.t0));
                                                            case 15:
                                                            case"end":
                                                                return e.stop()
                                                        }
                                                    }, e, null, [[0, 12]])
                                                }));
                                                return function () {
                                                    return e.apply(this, arguments)
                                                }
                                            }(), f = function () {
                                                n && clearInterval(n), n = setInterval(function () {
                                                    c().then(function (e) {
                                                        return e.forEach(function (e) {
                                                            r(null, p.a.parseEvent(e, d.abi))
                                                        })
                                                    }).catch(function (e) {
                                                        return r(e)
                                                    })
                                                }, 3e3)
                                            }, e.next = 18, c();
                                        case 18:
                                            return f(), e.abrupt("return", {
                                                start: f, stop: function () {
                                                    n && (clearInterval(n), n = !1)
                                                }
                                            });
                                        case 20:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function () {
                                return t.apply(this, arguments)
                            })
                        }]), e
                    }();
                    r.d(t, "a", function () {
                        return M
                    });
                    var M = function () {
                        function e() {
                            var t = arguments.length > 0 && void 0 !== arguments[0] && arguments[0],
                                r = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : [],
                                n = arguments.length > 2 && void 0 !== arguments[2] && arguments[2];
                            if (f()(this, e), !t || !t instanceof l.default) throw new Error("Expected instance of TronWeb");
                            this.tronWeb = t, this.injectPromise = y()(this), this.address = n, this.abi = r, this.eventListener = !1, this.bytecode = !1, this.deployed = !1, this.lastBlock = !1, this.methods = {}, this.methodInstances = {}, this.props = [], this.tronWeb.isAddress(n) ? this.deployed = !0 : this.address = !1, this.loadAbi(r)
                        }

                        var t, r, n, a;
                        return h()(e, [{
                            key: "_getEvents", value: (a = u()(i.a.mark(function e() {
                                var t, r, n, a, s, u, c = this, f = arguments;
                                return i.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            return t = f.length > 0 && void 0 !== f[0] ? f[0] : {}, e.next = 3, this.tronWeb.event.getEventsByContractAddress(this.address, t);
                                        case 3:
                                            return r = e.sent, n = r.sort(function (e, t) {
                                                return t.block - e.block
                                            }), a = o()(n, 1), s = a[0], u = r.filter(function (e, n) {
                                                return !(t.resourceNode && e.resourceNode && t.resourceNode.toLowerCase() !== e.resourceNode.toLowerCase() || r.slice(0, n).some(function (t) {
                                                    return JSON.stringify(t) == JSON.stringify(e)
                                                }) || c.lastBlock && !(e.block > c.lastBlock))
                                            }), s && (this.lastBlock = s.block), e.abrupt("return", u);
                                        case 8:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function () {
                                return a.apply(this, arguments)
                            })
                        }, {
                            key: "_startEventListener", value: (n = u()(i.a.mark(function e() {
                                var t, r, n = this, a = arguments;
                                return i.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (t = a.length > 0 && void 0 !== a[0] ? a[0] : {}, r = a.length > 1 ? a[1] : void 0, p.a.isFunction(t) && (r = t, t = {}), this.eventListener && clearInterval(this.eventListener), this.tronWeb.eventServer) {
                                                e.next = 6;
                                                break
                                            }
                                            throw new Error("Event server is not configured");
                                        case 6:
                                            if (this.address) {
                                                e.next = 8;
                                                break
                                            }
                                            throw new Error("Contract is not configured with an address");
                                        case 8:
                                            return this.eventCallback = r, e.next = 11, this._getEvents(t);
                                        case 11:
                                            this.eventListener = setInterval(function () {
                                                n._getEvents(t).then(function (e) {
                                                    return e.forEach(function (e) {
                                                        n.eventCallback && n.eventCallback(e)
                                                    })
                                                }).catch(function (e) {
                                                    console.error("Failed to get event list", e)
                                                })
                                            }, 3e3);
                                        case 12:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function () {
                                return n.apply(this, arguments)
                            })
                        }, {
                            key: "_stopEventListener", value: function () {
                                this.eventListener && (clearInterval(this.eventListener), this.eventListener = !1, this.eventCallback = !1)
                            }
                        }, {
                            key: "hasProperty", value: function (e) {
                                return this.hasOwnProperty(e) || this.__proto__.hasOwnProperty(e)
                            }
                        }, {
                            key: "loadAbi", value: function (e) {
                                var t = this;
                                this.abi = e, this.methods = {}, this.props.forEach(function (e) {
                                    return delete t[e]
                                }), e.forEach(function (e) {
                                    if (e.type && !/constructor/i.test(e.type)) {
                                        var r = new S(t, e), n = r.onMethod.bind(r), i = r.name, a = r.functionSelector,
                                            o = r.signature;
                                        t.methods[i] = n, t.methods[a] = n, t.methods[o] = n, t.methodInstances[i] = r, t.methodInstances[a] = r, t.methodInstances[o] = r, t.hasProperty(i) || (t[i] = n, t.props.push(i)), t.hasProperty(a) || (t[a] = n, t.props.push(a)), t.hasProperty(o) || (t[o] = n, t.props.push(o))
                                    }
                                })
                            }
                        }, {
                            key: "decodeInput", value: function (e) {
                                var t = e.substring(0, 8), r = e.substring(8);
                                if (!this.methodInstances[t]) throw new Error("Contract method " + t + " not found");
                                return {
                                    name: this.methodInstances[t].name,
                                    params: this.methodInstances[t].decodeInput(r)
                                }
                            }
                        }, {
                            key: "new", value: (r = u()(i.a.mark(function e(t) {
                                var r, n, a, o, s, u, c = arguments;
                                return i.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (r = c.length > 1 && void 0 !== c[1] ? c[1] : this.tronWeb.defaultPrivateKey, n = c.length > 2 && void 0 !== c[2] && c[2], p.a.isFunction(r) && (n = r, r = this.tronWeb.defaultPrivateKey), n) {
                                                e.next = 5;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this.new, t, r));
                                        case 5:
                                            return e.prev = 5, a = this.tronWeb.address.fromPrivateKey(r), e.next = 9, this.tronWeb.transactionBuilder.createSmartContract(t, a);
                                        case 9:
                                            return o = e.sent, e.next = 12, this.tronWeb.trx.sign(o, r);
                                        case 12:
                                            return s = e.sent, e.next = 15, this.tronWeb.trx.sendRawTransaction(s);
                                        case 15:
                                            if (!(u = e.sent).code) {
                                                e.next = 18;
                                                break
                                            }
                                            return e.abrupt("return", n({
                                                error: u.code,
                                                message: this.tronWeb.toUtf8(u.message)
                                            }));
                                        case 18:
                                            return e.next = 20, p.a.sleep(3e3);
                                        case 20:
                                            return e.abrupt("return", this.at(s.contract_address, n));
                                        case 23:
                                            return e.prev = 23, e.t0 = e.catch(5), e.abrupt("return", n(e.t0));
                                        case 26:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this, [[5, 23]])
                            })), function (e) {
                                return r.apply(this, arguments)
                            })
                        }, {
                            key: "at", value: (t = u()(i.a.mark(function e(t) {
                                var r, n, a = arguments;
                                return i.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (r = a.length > 1 && void 0 !== a[1] && a[1]) {
                                                e.next = 3;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this.at, t));
                                        case 3:
                                            return e.prev = 3, e.next = 6, this.tronWeb.trx.getContract(t);
                                        case 6:
                                            if ((n = e.sent).contract_address) {
                                                e.next = 9;
                                                break
                                            }
                                            return e.abrupt("return", r("Unknown error: " + JSON.stringify(n, null, 2)));
                                        case 9:
                                            return this.address = n.contract_address, this.bytecode = n.bytecode, this.deployed = !0, this.loadAbi(n.abi && n.abi.entrys ? n.abi.entrys : []), e.abrupt("return", r(null, this));
                                        case 16:
                                            if (e.prev = 16, e.t0 = e.catch(3), !e.t0.toString().includes("does not exist")) {
                                                e.next = 20;
                                                break
                                            }
                                            return e.abrupt("return", r("Contract has not been deployed on the network"));
                                        case 20:
                                            return e.abrupt("return", r(e.t0));
                                        case 21:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this, [[3, 16]])
                            })), function (e) {
                                return t.apply(this, arguments)
                            })
                        }, {
                            key: "events", value: function () {
                                var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : {},
                                    t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1];
                                if (p.a.isFunction(e) && (t = e, e = {}), !p.a.isFunction(t)) throw new Error("Callback function expected");
                                var r = this;
                                return {
                                    start: function () {
                                        var n = arguments.length > 0 && void 0 !== arguments[0] && arguments[0];
                                        return n ? (r._startEventListener(e, t).then(function () {
                                            n()
                                        }).catch(function (e) {
                                            n(e)
                                        }), this) : (r._startEventListener(e, t), this)
                                    }, stop: function () {
                                        r._stopEventListener()
                                    }
                                }
                            }
                        }]), e
                    }()
                }, function (e, t) {
                    var r;
                    r = function () {
                        return this
                    }();
                    try {
                        r = r || new Function("return this")()
                    } catch (e) {
                        "object" == ("undefined" == typeof window ? "undefined" : n()(window)) && (r = window)
                    }
                    e.exports = r
                }, function (e, t, r) {
                    e.exports = function (e, t) {
                        return function () {
                            for (var r = new Array(arguments.length), n = 0; n < r.length; n++) r[n] = arguments[n];
                            return e.apply(t, r)
                        }
                    }
                }, function (e, t, r) {
                    var n = r(11);

                    function i(e) {
                        return encodeURIComponent(e).replace(/%3A/gi, ":").replace(/%24/g, "$").replace(/%2C/gi, ",").replace(/%20/g, "+").replace(/%5B/gi, "[").replace(/%5D/gi, "]")
                    }

                    e.exports = function (e, t, r) {
                        if (!t) return e;
                        var a;
                        if (r) a = r(t); else if (n.isURLSearchParams(t)) a = t.toString(); else {
                            var o = [];
                            n.forEach(t, function (e, t) {
                                null != e && (n.isArray(e) ? t += "[]" : e = [e], n.forEach(e, function (e) {
                                    n.isDate(e) ? e = e.toISOString() : n.isObject(e) && (e = JSON.stringify(e)), o.push(i(t) + "=" + i(e))
                                }))
                            }), a = o.join("&")
                        }
                        if (a) {
                            var s = e.indexOf("#");
                            -1 !== s && (e = e.slice(0, s)), e += (-1 === e.indexOf("?") ? "?" : "&") + a
                        }
                        return e
                    }
                }, function (e, t, r) {
                    e.exports = function (e) {
                        return !(!e || !e.__CANCEL__)
                    }
                }, function (e, t, r) {
                    (function (t) {
                        var n = r(11), i = r(85), a = {"Content-Type": "application/x-www-form-urlencoded"};

                        function o(e, t) {
                            !n.isUndefined(e) && n.isUndefined(e["Content-Type"]) && (e["Content-Type"] = t)
                        }

                        var s, u = {
                            adapter: ("undefined" != typeof XMLHttpRequest ? s = r(45) : void 0 !== t && "[object process]" === Object.prototype.toString.call(t) && (s = r(45)), s),
                            transformRequest: [function (e, t) {
                                return i(t, "Accept"), i(t, "Content-Type"), n.isFormData(e) || n.isArrayBuffer(e) || n.isBuffer(e) || n.isStream(e) || n.isFile(e) || n.isBlob(e) ? e : n.isArrayBufferView(e) ? e.buffer : n.isURLSearchParams(e) ? (o(t, "application/x-www-form-urlencoded;charset=utf-8"), e.toString()) : n.isObject(e) ? (o(t, "application/json;charset=utf-8"), JSON.stringify(e)) : e
                            }],
                            transformResponse: [function (e) {
                                if ("string" == typeof e) try {
                                    e = JSON.parse(e)
                                } catch (e) {
                                }
                                return e
                            }],
                            timeout: 0,
                            xsrfCookieName: "XSRF-TOKEN",
                            xsrfHeaderName: "X-XSRF-TOKEN",
                            maxContentLength: -1,
                            maxBodyLength: -1,
                            validateStatus: function (e) {
                                return e >= 200 && e < 300
                            },
                            headers: {common: {Accept: "application/json, text/plain, */*"}}
                        };
                        n.forEach(["delete", "get", "head"], function (e) {
                            u.headers[e] = {}
                        }), n.forEach(["post", "put", "patch"], function (e) {
                            u.headers[e] = n.merge(a)
                        }), e.exports = u
                    }).call(this, r(44))
                }, function (e, t) {
                    var r, n, i = e.exports = {};

                    function a() {
                        throw new Error("setTimeout has not been defined")
                    }

                    function o() {
                        throw new Error("clearTimeout has not been defined")
                    }

                    function s(e) {
                        if (r === setTimeout) return setTimeout(e, 0);
                        if ((r === a || !r) && setTimeout) return r = setTimeout, setTimeout(e, 0);
                        try {
                            return r(e, 0)
                        } catch (t) {
                            try {
                                return r.call(null, e, 0)
                            } catch (t) {
                                return r.call(this, e, 0)
                            }
                        }
                    }

                    !function () {
                        try {
                            r = "function" == typeof setTimeout ? setTimeout : a
                        } catch (e) {
                            r = a
                        }
                        try {
                            n = "function" == typeof clearTimeout ? clearTimeout : o
                        } catch (e) {
                            n = o
                        }
                    }();
                    var u, c = [], f = !1, d = -1;

                    function h() {
                        f && u && (f = !1, u.length ? c = u.concat(c) : d = -1, c.length && l())
                    }

                    function l() {
                        if (!f) {
                            var e = s(h);
                            f = !0;
                            for (var t = c.length; t;) {
                                for (u = c, c = []; ++d < t;) u && u[d].run();
                                d = -1, t = c.length
                            }
                            u = null, f = !1, function (e) {
                                if (n === clearTimeout) return clearTimeout(e);
                                if ((n === o || !n) && clearTimeout) return n = clearTimeout, clearTimeout(e);
                                try {
                                    n(e)
                                } catch (t) {
                                    try {
                                        return n.call(null, e)
                                    } catch (t) {
                                        return n.call(this, e)
                                    }
                                }
                            }(e)
                        }
                    }

                    function p(e, t) {
                        this.fun = e, this.array = t
                    }

                    function b() {
                    }

                    i.nextTick = function (e) {
                        var t = new Array(arguments.length - 1);
                        if (arguments.length > 1) for (var r = 1; r < arguments.length; r++) t[r - 1] = arguments[r];
                        c.push(new p(e, t)), 1 !== c.length || f || s(l)
                    }, p.prototype.run = function () {
                        this.fun.apply(null, this.array)
                    }, i.title = "browser", i.browser = !0, i.env = {}, i.argv = [], i.version = "", i.versions = {}, i.on = b, i.addListener = b, i.once = b, i.off = b, i.removeListener = b, i.removeAllListeners = b, i.emit = b, i.prependListener = b, i.prependOnceListener = b, i.listeners = function (e) {
                        return []
                    }, i.binding = function (e) {
                        throw new Error("process.binding is not supported")
                    }, i.cwd = function () {
                        return "/"
                    }, i.chdir = function (e) {
                        throw new Error("process.chdir is not supported")
                    }, i.umask = function () {
                        return 0
                    }
                }, function (e, t, r) {
                    var n = r(11), i = r(86), a = r(88), o = r(41), s = r(89), u = r(92), c = r(93), f = r(46);
                    e.exports = function (e) {
                        return new Promise(function (t, r) {
                            var d = e.data, h = e.headers;
                            n.isFormData(d) && delete h["Content-Type"];
                            var l = new XMLHttpRequest;
                            if (e.auth) {
                                var p = e.auth.username || "",
                                    b = e.auth.password ? unescape(encodeURIComponent(e.auth.password)) : "";
                                h.Authorization = "Basic " + btoa(p + ":" + b)
                            }
                            var v = s(e.baseURL, e.url);
                            if (l.open(e.method.toUpperCase(), o(v, e.params, e.paramsSerializer), !0), l.timeout = e.timeout, l.onreadystatechange = function () {
                                if (l && 4 === l.readyState && (0 !== l.status || l.responseURL && 0 === l.responseURL.indexOf("file:"))) {
                                    var n = "getAllResponseHeaders" in l ? u(l.getAllResponseHeaders()) : null, a = {
                                        data: e.responseType && "text" !== e.responseType ? l.response : l.responseText,
                                        status: l.status,
                                        statusText: l.statusText,
                                        headers: n,
                                        config: e,
                                        request: l
                                    };
                                    i(t, r, a), l = null
                                }
                            }, l.onabort = function () {
                                l && (r(f("Request aborted", e, "ECONNABORTED", l)), l = null)
                            }, l.onerror = function () {
                                r(f("Network Error", e, null, l)), l = null
                            }, l.ontimeout = function () {
                                var t = "timeout of " + e.timeout + "ms exceeded";
                                e.timeoutErrorMessage && (t = e.timeoutErrorMessage), r(f(t, e, "ECONNABORTED", l)), l = null
                            }, n.isStandardBrowserEnv()) {
                                var g = (e.withCredentials || c(v)) && e.xsrfCookieName ? a.read(e.xsrfCookieName) : void 0;
                                g && (h[e.xsrfHeaderName] = g)
                            }
                            if ("setRequestHeader" in l && n.forEach(h, function (e, t) {
                                void 0 === d && "content-type" === t.toLowerCase() ? delete h[t] : l.setRequestHeader(t, e)
                            }), n.isUndefined(e.withCredentials) || (l.withCredentials = !!e.withCredentials), e.responseType) try {
                                l.responseType = e.responseType
                            } catch (t) {
                                if ("json" !== e.responseType) throw t
                            }
                            "function" == typeof e.onDownloadProgress && l.addEventListener("progress", e.onDownloadProgress), "function" == typeof e.onUploadProgress && l.upload && l.upload.addEventListener("progress", e.onUploadProgress), e.cancelToken && e.cancelToken.promise.then(function (e) {
                                l && (l.abort(), r(e), l = null)
                            }), d || (d = null), l.send(d)
                        })
                    }
                }, function (e, t, r) {
                    var n = r(87);
                    e.exports = function (e, t, r, i, a) {
                        var o = new Error(e);
                        return n(o, t, r, i, a)
                    }
                }, function (e, t, r) {
                    var n = r(11);
                    e.exports = function (e, t) {
                        t = t || {};
                        var r = {}, i = ["url", "method", "data"], a = ["headers", "auth", "proxy", "params"],
                            o = ["baseURL", "transformRequest", "transformResponse", "paramsSerializer", "timeout", "timeoutMessage", "withCredentials", "adapter", "responseType", "xsrfCookieName", "xsrfHeaderName", "onUploadProgress", "onDownloadProgress", "decompress", "maxContentLength", "maxBodyLength", "maxRedirects", "transport", "httpAgent", "httpsAgent", "cancelToken", "socketPath", "responseEncoding"],
                            s = ["validateStatus"];

                        function u(e, t) {
                            return n.isPlainObject(e) && n.isPlainObject(t) ? n.merge(e, t) : n.isPlainObject(t) ? n.merge({}, t) : n.isArray(t) ? t.slice() : t
                        }

                        function c(i) {
                            n.isUndefined(t[i]) ? n.isUndefined(e[i]) || (r[i] = u(void 0, e[i])) : r[i] = u(e[i], t[i])
                        }

                        n.forEach(i, function (e) {
                            n.isUndefined(t[e]) || (r[e] = u(void 0, t[e]))
                        }), n.forEach(a, c), n.forEach(o, function (i) {
                            n.isUndefined(t[i]) ? n.isUndefined(e[i]) || (r[i] = u(void 0, e[i])) : r[i] = u(void 0, t[i])
                        }), n.forEach(s, function (n) {
                            n in t ? r[n] = u(e[n], t[n]) : n in e && (r[n] = u(void 0, e[n]))
                        });
                        var f = i.concat(a).concat(o).concat(s),
                            d = Object.keys(e).concat(Object.keys(t)).filter(function (e) {
                                return -1 === f.indexOf(e)
                            });
                        return n.forEach(d, c), r
                    }
                }, function (e, t, r) {
                    function n(e) {
                        this.message = e
                    }

                    n.prototype.toString = function () {
                        return "Cancel" + (this.message ? ": " + this.message : "")
                    }, n.prototype.__CANCEL__ = !0, e.exports = n
                }, function (e, t, r) {
                    var n = this && this.__importDefault || function (e) {
                        return e && e.__esModule ? e : {default: e}
                    };
                    Object.defineProperty(t, "__esModule", {value: !0});
                    var i = n(r(102)), a = r(18);
                    t.ripemd160 = function (e) {
                        return "0x" + i.default.ripemd160().update(a.arrayify(e)).digest("hex")
                    }, t.sha256 = function (e) {
                        return "0x" + i.default.sha256().update(a.arrayify(e)).digest("hex")
                    }, t.sha512 = function (e) {
                        return "0x" + i.default.sha512().update(a.arrayify(e)).digest("hex")
                    }
                }, function (e, t, r) {
                    var n = r(15).rotr32;

                    function i(e, t, r) {
                        return e & t ^ ~e & r
                    }

                    function a(e, t, r) {
                        return e & t ^ e & r ^ t & r
                    }

                    function o(e, t, r) {
                        return e ^ t ^ r
                    }

                    t.ft_1 = function (e, t, r, n) {
                        return 0 === e ? i(t, r, n) : 1 === e || 3 === e ? o(t, r, n) : 2 === e ? a(t, r, n) : void 0
                    }, t.ch32 = i, t.maj32 = a, t.p32 = o, t.s0_256 = function (e) {
                        return n(e, 2) ^ n(e, 13) ^ n(e, 22)
                    }, t.s1_256 = function (e) {
                        return n(e, 6) ^ n(e, 11) ^ n(e, 25)
                    }, t.g0_256 = function (e) {
                        return n(e, 7) ^ n(e, 18) ^ e >>> 3
                    }, t.g1_256 = function (e) {
                        return n(e, 17) ^ n(e, 19) ^ e >>> 10
                    }
                }, function (e, t, r) {
                    var n = r(15), i = r(27), a = r(50), o = r(12), s = n.sum32, u = n.sum32_4, c = n.sum32_5,
                        f = a.ch32, d = a.maj32, h = a.s0_256, l = a.s1_256, p = a.g0_256, b = a.g1_256,
                        v = i.BlockHash,
                        g = [1116352408, 1899447441, 3049323471, 3921009573, 961987163, 1508970993, 2453635748, 2870763221, 3624381080, 310598401, 607225278, 1426881987, 1925078388, 2162078206, 2614888103, 3248222580, 3835390401, 4022224774, 264347078, 604807628, 770255983, 1249150122, 1555081692, 1996064986, 2554220882, 2821834349, 2952996808, 3210313671, 3336571891, 3584528711, 113926993, 338241895, 666307205, 773529912, 1294757372, 1396182291, 1695183700, 1986661051, 2177026350, 2456956037, 2730485921, 2820302411, 3259730800, 3345764771, 3516065817, 3600352804, 4094571909, 275423344, 430227734, 506948616, 659060556, 883997877, 958139571, 1322822218, 1537002063, 1747873779, 1955562222, 2024104815, 2227730452, 2361852424, 2428436474, 2756734187, 3204031479, 3329325298];

                    function m() {
                        if (!(this instanceof m)) return new m;
                        v.call(this), this.h = [1779033703, 3144134277, 1013904242, 2773480762, 1359893119, 2600822924, 528734635, 1541459225], this.k = g, this.W = new Array(64)
                    }

                    n.inherits(m, v), e.exports = m, m.blockSize = 512, m.outSize = 256, m.hmacStrength = 192, m.padLength = 64, m.prototype._update = function (e, t) {
                        for (var r = this.W, n = 0; n < 16; n++) r[n] = e[t + n];
                        for (; n < r.length; n++) r[n] = u(b(r[n - 2]), r[n - 7], p(r[n - 15]), r[n - 16]);
                        var i = this.h[0], a = this.h[1], v = this.h[2], g = this.h[3], m = this.h[4], y = this.h[5],
                            w = this.h[6], x = this.h[7];
                        for (o(this.k.length === r.length), n = 0; n < r.length; n++) {
                            var _ = c(x, l(m), f(m, y, w), this.k[n], r[n]), A = s(h(i), d(i, a, v));
                            x = w, w = y, y = m, m = s(g, _), g = v, v = a, a = i, i = s(_, A)
                        }
                        this.h[0] = s(this.h[0], i), this.h[1] = s(this.h[1], a), this.h[2] = s(this.h[2], v), this.h[3] = s(this.h[3], g), this.h[4] = s(this.h[4], m), this.h[5] = s(this.h[5], y), this.h[6] = s(this.h[6], w), this.h[7] = s(this.h[7], x)
                    }, m.prototype._digest = function (e) {
                        return "hex" === e ? n.toHex32(this.h, "big") : n.split32(this.h, "big")
                    }
                }, function (e, t, r) {
                    var n = r(15), i = r(27), a = r(12), o = n.rotr64_hi, s = n.rotr64_lo, u = n.shr64_hi,
                        c = n.shr64_lo, f = n.sum64, d = n.sum64_hi, h = n.sum64_lo, l = n.sum64_4_hi, p = n.sum64_4_lo,
                        b = n.sum64_5_hi, v = n.sum64_5_lo, g = i.BlockHash,
                        m = [1116352408, 3609767458, 1899447441, 602891725, 3049323471, 3964484399, 3921009573, 2173295548, 961987163, 4081628472, 1508970993, 3053834265, 2453635748, 2937671579, 2870763221, 3664609560, 3624381080, 2734883394, 310598401, 1164996542, 607225278, 1323610764, 1426881987, 3590304994, 1925078388, 4068182383, 2162078206, 991336113, 2614888103, 633803317, 3248222580, 3479774868, 3835390401, 2666613458, 4022224774, 944711139, 264347078, 2341262773, 604807628, 2007800933, 770255983, 1495990901, 1249150122, 1856431235, 1555081692, 3175218132, 1996064986, 2198950837, 2554220882, 3999719339, 2821834349, 766784016, 2952996808, 2566594879, 3210313671, 3203337956, 3336571891, 1034457026, 3584528711, 2466948901, 113926993, 3758326383, 338241895, 168717936, 666307205, 1188179964, 773529912, 1546045734, 1294757372, 1522805485, 1396182291, 2643833823, 1695183700, 2343527390, 1986661051, 1014477480, 2177026350, 1206759142, 2456956037, 344077627, 2730485921, 1290863460, 2820302411, 3158454273, 3259730800, 3505952657, 3345764771, 106217008, 3516065817, 3606008344, 3600352804, 1432725776, 4094571909, 1467031594, 275423344, 851169720, 430227734, 3100823752, 506948616, 1363258195, 659060556, 3750685593, 883997877, 3785050280, 958139571, 3318307427, 1322822218, 3812723403, 1537002063, 2003034995, 1747873779, 3602036899, 1955562222, 1575990012, 2024104815, 1125592928, 2227730452, 2716904306, 2361852424, 442776044, 2428436474, 593698344, 2756734187, 3733110249, 3204031479, 2999351573, 3329325298, 3815920427, 3391569614, 3928383900, 3515267271, 566280711, 3940187606, 3454069534, 4118630271, 4000239992, 116418474, 1914138554, 174292421, 2731055270, 289380356, 3203993006, 460393269, 320620315, 685471733, 587496836, 852142971, 1086792851, 1017036298, 365543100, 1126000580, 2618297676, 1288033470, 3409855158, 1501505948, 4234509866, 1607167915, 987167468, 1816402316, 1246189591];

                    function y() {
                        if (!(this instanceof y)) return new y;
                        g.call(this), this.h = [1779033703, 4089235720, 3144134277, 2227873595, 1013904242, 4271175723, 2773480762, 1595750129, 1359893119, 2917565137, 2600822924, 725511199, 528734635, 4215389547, 1541459225, 327033209], this.k = m, this.W = new Array(160)
                    }

                    function w(e, t, r, n, i) {
                        var a = e & r ^ ~e & i;
                        return a < 0 && (a += 4294967296), a
                    }

                    function x(e, t, r, n, i, a) {
                        var o = t & n ^ ~t & a;
                        return o < 0 && (o += 4294967296), o
                    }

                    function _(e, t, r, n, i) {
                        var a = e & r ^ e & i ^ r & i;
                        return a < 0 && (a += 4294967296), a
                    }

                    function A(e, t, r, n, i, a) {
                        var o = t & n ^ t & a ^ n & a;
                        return o < 0 && (o += 4294967296), o
                    }

                    function k(e, t) {
                        var r = o(e, t, 28) ^ o(t, e, 2) ^ o(t, e, 7);
                        return r < 0 && (r += 4294967296), r
                    }

                    function S(e, t) {
                        var r = s(e, t, 28) ^ s(t, e, 2) ^ s(t, e, 7);
                        return r < 0 && (r += 4294967296), r
                    }

                    function M(e, t) {
                        var r = o(e, t, 14) ^ o(e, t, 18) ^ o(t, e, 9);
                        return r < 0 && (r += 4294967296), r
                    }

                    function I(e, t) {
                        var r = s(e, t, 14) ^ s(e, t, 18) ^ s(t, e, 9);
                        return r < 0 && (r += 4294967296), r
                    }

                    function E(e, t) {
                        var r = o(e, t, 1) ^ o(e, t, 8) ^ u(e, t, 7);
                        return r < 0 && (r += 4294967296), r
                    }

                    function N(e, t) {
                        var r = s(e, t, 1) ^ s(e, t, 8) ^ c(e, t, 7);
                        return r < 0 && (r += 4294967296), r
                    }

                    function P(e, t) {
                        var r = o(e, t, 19) ^ o(t, e, 29) ^ u(e, t, 6);
                        return r < 0 && (r += 4294967296), r
                    }

                    function T(e, t) {
                        var r = s(e, t, 19) ^ s(t, e, 29) ^ c(e, t, 6);
                        return r < 0 && (r += 4294967296), r
                    }

                    n.inherits(y, g), e.exports = y, y.blockSize = 1024, y.outSize = 512, y.hmacStrength = 192, y.padLength = 128, y.prototype._prepareBlock = function (e, t) {
                        for (var r = this.W, n = 0; n < 32; n++) r[n] = e[t + n];
                        for (; n < r.length; n += 2) {
                            var i = P(r[n - 4], r[n - 3]), a = T(r[n - 4], r[n - 3]), o = r[n - 14], s = r[n - 13],
                                u = E(r[n - 30], r[n - 29]), c = N(r[n - 30], r[n - 29]), f = r[n - 32], d = r[n - 31];
                            r[n] = l(i, a, o, s, u, c, f, d), r[n + 1] = p(i, a, o, s, u, c, f, d)
                        }
                    }, y.prototype._update = function (e, t) {
                        this._prepareBlock(e, t);
                        var r = this.W, n = this.h[0], i = this.h[1], o = this.h[2], s = this.h[3], u = this.h[4],
                            c = this.h[5], l = this.h[6], p = this.h[7], g = this.h[8], m = this.h[9], y = this.h[10],
                            E = this.h[11], N = this.h[12], P = this.h[13], T = this.h[14], O = this.h[15];
                        a(this.k.length === r.length);
                        for (var R = 0; R < r.length; R += 2) {
                            var j = T, C = O, B = M(g, m), L = I(g, m), W = w(g, 0, y, 0, N), F = x(0, m, 0, E, 0, P),
                                U = this.k[R], D = this.k[R + 1], q = r[R], z = r[R + 1],
                                H = b(j, C, B, L, W, F, U, D, q, z), V = v(j, C, B, L, W, F, U, D, q, z);
                            j = k(n, i), C = S(n, i), B = _(n, 0, o, 0, u), L = A(0, i, 0, s, 0, c);
                            var K = d(j, C, B, L), G = h(j, C, B, L);
                            T = N, O = P, N = y, P = E, y = g, E = m, g = d(l, p, H, V), m = h(p, p, H, V), l = u, p = c, u = o, c = s, o = n, s = i, n = d(H, V, K, G), i = h(H, V, K, G)
                        }
                        f(this.h, 0, n, i), f(this.h, 2, o, s), f(this.h, 4, u, c), f(this.h, 6, l, p), f(this.h, 8, g, m), f(this.h, 10, y, E), f(this.h, 12, N, P), f(this.h, 14, T, O)
                    }, y.prototype._digest = function (e) {
                        return "hex" === e ? n.toHex32(this.h, "big") : n.split32(this.h, "big")
                    }
                }, function (e, t, r) {
                    var i,
                        a = this && this.__extends || (i = Object.setPrototypeOf || {__proto__: []} instanceof Array && function (e, t) {
                            e.__proto__ = t
                        } || function (e, t) {
                            for (var r in t) t.hasOwnProperty(r) && (e[r] = t[r])
                        }, function (e, t) {
                            function r() {
                                this.constructor = e
                            }

                            i(e, t), e.prototype = null === t ? Object.create(t) : (r.prototype = t.prototype, new r)
                        }), o = this && this.__importStar || function (e) {
                            if (e && e.__esModule) return e;
                            var t = {};
                            if (null != e) for (var r in e) Object.hasOwnProperty.call(e, r) && (t[r] = e[r]);
                            return t.default = e, t
                        };
                    Object.defineProperty(t, "__esModule", {value: !0});
                    var s = r(54), u = o(r(19)), c = r(56), f = r(55), d = r(18), h = r(29), l = r(31),
                        p = new RegExp(/^bytes([0-9]*)$/), b = new RegExp(/^(u?int)([0-9]*)$/),
                        v = new RegExp(/^(.*)\[([0-9]*)\]$/);
                    t.defaultCoerceFunc = function (e, t) {
                        var r = e.match(b);
                        return r && parseInt(r[2]) <= 48 ? t.toNumber() : t
                    };
                    var g = new RegExp("^([^)(]*)\\((.*)\\)([^)(]*)$"), m = new RegExp("^[A-Za-z_][A-Za-z0-9_]*$");

                    function y(e) {
                        return e.match(/^uint($|[^1-9])/) ? e = "uint256" + e.substring(4) : e.match(/^int($|[^1-9])/) && (e = "int256" + e.substring(3)), e
                    }

                    function w(e, t) {
                        var r = e;

                        function n(e) {
                            throw new Error('unexpected character "' + r[e] + '" at position ' + e + ' in "' + r + '"')
                        }

                        e = e.replace(/\s/g, " ");
                        for (var i = {type: "", name: "", state: {allowType: !0}}, a = i, o = 0; o < e.length; o++) {
                            var s = e[o];
                            switch (s) {
                                case"(":
                                    a.state.allowParams || n(o), a.state.allowType = !1, a.type = y(a.type), a.components = [{
                                        type: "",
                                        name: "",
                                        parent: a,
                                        state: {allowType: !0}
                                    }], a = a.components[0];
                                    break;
                                case")":
                                    delete a.state, t && "indexed" === a.name && (a.indexed = !0, a.name = ""), a.type = y(a.type);
                                    var u = a;
                                    (a = a.parent) || n(o), delete u.parent, a.state.allowParams = !1, a.state.allowName = !0, a.state.allowArray = !0;
                                    break;
                                case",":
                                    delete a.state, t && "indexed" === a.name && (a.indexed = !0, a.name = ""), a.type = y(a.type);
                                    var c = {type: "", name: "", parent: a.parent, state: {allowType: !0}};
                                    a.parent.components.push(c), delete a.parent, a = c;
                                    break;
                                case" ":
                                    a.state.allowType && "" !== a.type && (a.type = y(a.type), delete a.state.allowType, a.state.allowName = !0, a.state.allowParams = !0), a.state.allowName && "" !== a.name && (t && "indexed" === a.name ? (a.indexed = !0, a.name = "") : a.state.allowName = !1);
                                    break;
                                case"[":
                                    a.state.allowArray || n(o), a.type += s, a.state.allowArray = !1, a.state.allowName = !1, a.state.readArray = !0;
                                    break;
                                case"]":
                                    a.state.readArray || n(o), a.type += s, a.state.readArray = !1, a.state.allowArray = !0, a.state.allowName = !0;
                                    break;
                                default:
                                    a.state.allowType ? (a.type += s, a.state.allowParams = !0, a.state.allowArray = !0) : a.state.allowName ? (a.name += s, delete a.state.allowArray) : a.state.readArray ? a.type += s : n(o)
                            }
                        }
                        if (a.parent) throw new Error("unexpected eof");
                        return delete i.state, t && "indexed" === a.name && (a.indexed = !0, a.name = ""), i.type = y(i.type), i
                    }

                    function x(e) {
                        return D(t.defaultCoerceFunc, e).type
                    }

                    t.parseParamType = function (e) {
                        return w(e, !0)
                    }, t.formatParamType = x, t.formatSignature = function (e) {
                        return e.name + "(" + e.inputs.map(function (e) {
                            return x(e)
                        }).join(",") + ")"
                    }, t.parseSignature = function (e) {
                        if ("string" == typeof e) return "event " === (e = (e = (e = e.replace(/\s/g, " ")).replace(/\(/g, " (").replace(/\)/g, ") ").replace(/\s+/g, " ")).trim()).substring(0, 6) ? function (e) {
                            var t = {anonymous: !1, inputs: [], name: "", type: "event"}, r = e.match(g);
                            if (!r) throw new Error("invalid event: " + e);
                            if (t.name = r[1].trim(), F(r[2]).forEach(function (e) {
                                (e = w(e, !0)).indexed = !!e.indexed, t.inputs.push(e)
                            }), r[3].split(" ").forEach(function (e) {
                                switch (e) {
                                    case"anonymous":
                                        t.anonymous = !0;
                                        break;
                                    case"":
                                        break;
                                    default:
                                        u.info("unknown modifier: " + e)
                                }
                            }), t.name && !t.name.match(m)) throw new Error('invalid identifier: "' + t.name + '"');
                            return t
                        }(e.substring(6).trim()) : ("function " === e.substring(0, 9) && (e = e.substring(9)), function (e) {
                            var t = {
                                constant: !1,
                                gas: null,
                                inputs: [],
                                name: "",
                                outputs: [],
                                payable: !1,
                                stateMutability: null,
                                type: "function"
                            }, r = e.split("@");
                            if (1 !== r.length) {
                                if (r.length > 2) throw new Error("invalid signature");
                                if (!r[1].match(/^[0-9]+$/)) throw new Error("invalid signature gas");
                                t.gas = f.bigNumberify(r[1]), e = r[0]
                            }
                            var n = (r = e.split(" returns "))[0].match(g);
                            if (!n) throw new Error("invalid signature");
                            if (t.name = n[1].trim(), !t.name.match(m)) throw new Error('invalid identifier: "' + n[1] + '"');
                            if (F(n[2]).forEach(function (e) {
                                t.inputs.push(w(e))
                            }), n[3].split(" ").forEach(function (e) {
                                switch (e) {
                                    case"constant":
                                        t.constant = !0;
                                        break;
                                    case"payable":
                                        t.payable = !0, t.stateMutability = "payable";
                                        break;
                                    case"pure":
                                        t.constant = !0, t.stateMutability = "pure";
                                        break;
                                    case"view":
                                        t.constant = !0, t.stateMutability = "view";
                                        break;
                                    case"external":
                                    case"public":
                                    case"":
                                        break;
                                    default:
                                        u.info("unknown modifier: " + e)
                                }
                            }), r.length > 1) {
                                var i = r[1].match(g);
                                if ("" != i[1].trim() || "" != i[3].trim()) throw new Error("unexpected tokens");
                                F(i[2]).forEach(function (e) {
                                    t.outputs.push(w(e))
                                })
                            }
                            if ("constructor" === t.name) {
                                if (t.type = "constructor", t.outputs.length) throw new Error("constructor may not have outputs");
                                delete t.name, delete t.outputs
                            }
                            return t
                        }(e.trim()));
                        throw new Error("unknown signature")
                    };
                    var _ = function (e, t, r, n, i) {
                        this.coerceFunc = e, this.name = t, this.type = r, this.localName = n, this.dynamic = i
                    }, A = function (e) {
                        function t(t) {
                            var r = e.call(this, t.coerceFunc, t.name, t.type, void 0, t.dynamic) || this;
                            return l.defineReadOnly(r, "coder", t), r
                        }

                        return a(t, e), t.prototype.encode = function (e) {
                            return this.coder.encode(e)
                        }, t.prototype.decode = function (e, t) {
                            return this.coder.decode(e, t)
                        }, t
                    }(_), k = function (e) {
                        function t(t, r) {
                            return e.call(this, t, "null", "", r, !1) || this
                        }

                        return a(t, e), t.prototype.encode = function (e) {
                            return d.arrayify([])
                        }, t.prototype.decode = function (e, t) {
                            if (t > e.length) throw new Error("invalid null");
                            return {consumed: 0, value: this.coerceFunc("null", void 0)}
                        }, t
                    }(_), S = function (e) {
                        function t(t, r, n, i) {
                            var a = this, o = (n ? "int" : "uint") + 8 * r;
                            return (a = e.call(this, t, o, o, i, !1) || this).size = r, a.signed = n, a
                        }

                        return a(t, e), t.prototype.encode = function (e) {
                            try {
                                var t = f.bigNumberify(e);
                                if (this.signed) {
                                    var r = s.MaxUint256.maskn(8 * this.size - 1);
                                    if (t.gt(r)) throw new Error("out-of-bounds");
                                    if (r = r.add(s.One).mul(s.NegativeOne), t.lt(r)) throw new Error("out-of-bounds")
                                } else if (t.lt(s.Zero) || t.gt(s.MaxUint256.maskn(8 * this.size))) throw new Error("out-of-bounds");
                                return t = t.toTwos(8 * this.size).maskn(8 * this.size), this.signed && (t = t.fromTwos(8 * this.size).toTwos(256)), d.padZeros(d.arrayify(t), 32)
                            } catch (t) {
                                u.throwError("invalid number value", u.INVALID_ARGUMENT, {
                                    arg: this.localName,
                                    coderType: this.name,
                                    value: e
                                })
                            }
                            return null
                        }, t.prototype.decode = function (e, t) {
                            e.length < t + 32 && u.throwError("insufficient data for " + this.name + " type", u.INVALID_ARGUMENT, {
                                arg: this.localName,
                                coderType: this.name,
                                value: d.hexlify(e.slice(t, t + 32))
                            });
                            var r = 32 - this.size, n = f.bigNumberify(e.slice(t + r, t + 32));
                            return n = this.signed ? n.fromTwos(8 * this.size) : n.maskn(8 * this.size), {
                                consumed: 32,
                                value: this.coerceFunc(this.name, n)
                            }
                        }, t
                    }(_), M = new S(function (e, t) {
                        return t
                    }, 32, !1, "none"), I = function (e) {
                        function t(t, r) {
                            return e.call(this, t, "bool", "bool", r, !1) || this
                        }

                        return a(t, e), t.prototype.encode = function (e) {
                            return M.encode(e ? 1 : 0)
                        }, t.prototype.decode = function (e, t) {
                            try {
                                var r = M.decode(e, t)
                            } catch (e) {
                                throw"insufficient data for uint256 type" === e.reason && u.throwError("insufficient data for boolean type", u.INVALID_ARGUMENT, {
                                    arg: this.localName,
                                    coderType: "boolean",
                                    value: e.value
                                }), e
                            }
                            return {consumed: r.consumed, value: this.coerceFunc("bool", !r.value.isZero())}
                        }, t
                    }(_), E = function (e) {
                        function t(t, r, n) {
                            var i, a = "bytes" + r;
                            return (i = e.call(this, t, a, a, n, !1) || this).length = r, i
                        }

                        return a(t, e), t.prototype.encode = function (e) {
                            var t = new Uint8Array(32);
                            try {
                                var r = d.arrayify(e);
                                if (r.length !== this.length) throw new Error("incorrect data length");
                                t.set(r)
                            } catch (t) {
                                u.throwError("invalid " + this.name + " value", u.INVALID_ARGUMENT, {
                                    arg: this.localName,
                                    coderType: this.name,
                                    value: t.value || e
                                })
                            }
                            return t
                        }, t.prototype.decode = function (e, t) {
                            return e.length < t + 32 && u.throwError("insufficient data for " + this.name + " type", u.INVALID_ARGUMENT, {
                                arg: this.localName,
                                coderType: this.name,
                                value: d.hexlify(e.slice(t, t + 32))
                            }), {
                                consumed: 32,
                                value: this.coerceFunc(this.name, d.hexlify(e.slice(t, t + this.length)))
                            }
                        }, t
                    }(_), N = function (e) {
                        function t(t, r) {
                            return e.call(this, t, "address", "address", r, !1) || this
                        }

                        return a(t, e), t.prototype.encode = function (e) {
                            var t = new Uint8Array(32);
                            try {
                                t.set(d.arrayify(c.getAddress(e)), 12)
                            } catch (t) {
                                u.throwError("invalid address", u.INVALID_ARGUMENT, {
                                    arg: this.localName,
                                    coderType: "address",
                                    value: e
                                })
                            }
                            return t
                        }, t.prototype.decode = function (e, t) {
                            return e.length < t + 32 && u.throwError("insufficient data for address type", u.INVALID_ARGUMENT, {
                                arg: this.localName,
                                coderType: "address",
                                value: d.hexlify(e.slice(t, t + 32))
                            }), {
                                consumed: 32,
                                value: this.coerceFunc("address", c.getAddress(d.hexlify(e.slice(t + 12, t + 32))))
                            }
                        }, t
                    }(_);

                    function P(e) {
                        var t = 32 * Math.ceil(e.length / 32), r = new Uint8Array(t - e.length);
                        return d.concat([M.encode(e.length), e, r])
                    }

                    function T(e, t, r) {
                        e.length < t + 32 && u.throwError("insufficient data for dynamicBytes length", u.INVALID_ARGUMENT, {
                            arg: r,
                            coderType: "dynamicBytes",
                            value: d.hexlify(e.slice(t, t + 32))
                        });
                        var n = M.decode(e, t).value;
                        try {
                            n = n.toNumber()
                        } catch (e) {
                            u.throwError("dynamic bytes count too large", u.INVALID_ARGUMENT, {
                                arg: r,
                                coderType: "dynamicBytes",
                                value: n.toString()
                            })
                        }
                        return e.length < t + 32 + n && u.throwError("insufficient data for dynamicBytes type", u.INVALID_ARGUMENT, {
                            arg: r,
                            coderType: "dynamicBytes",
                            value: d.hexlify(e.slice(t, t + 32 + n))
                        }), {consumed: 32 + 32 * Math.ceil(n / 32), value: e.slice(t + 32, t + 32 + n)}
                    }

                    var O = function (e) {
                        function t(t, r) {
                            return e.call(this, t, "bytes", "bytes", r, !0) || this
                        }

                        return a(t, e), t.prototype.encode = function (e) {
                            try {
                                return P(d.arrayify(e))
                            } catch (e) {
                                u.throwError("invalid bytes value", u.INVALID_ARGUMENT, {
                                    arg: this.localName,
                                    coderType: "bytes",
                                    value: e.value
                                })
                            }
                            return null
                        }, t.prototype.decode = function (e, t) {
                            var r = T(e, t, this.localName);
                            return r.value = this.coerceFunc("bytes", d.hexlify(r.value)), r
                        }, t
                    }(_), R = function (e) {
                        function t(t, r) {
                            return e.call(this, t, "string", "string", r, !0) || this
                        }

                        return a(t, e), t.prototype.encode = function (e) {
                            return "string" != typeof e && u.throwError("invalid string value", u.INVALID_ARGUMENT, {
                                arg: this.localName,
                                coderType: "string",
                                value: e
                            }), P(h.toUtf8Bytes(e))
                        }, t.prototype.decode = function (e, t) {
                            var r = T(e, t, this.localName);
                            return r.value = this.coerceFunc("string", h.toUtf8String(r.value)), r
                        }, t
                    }(_);

                    function j(e) {
                        return 32 * Math.ceil(e / 32)
                    }

                    function C(e, t) {
                        if (Array.isArray(t)) ; else if (t && "object" == n()(t)) {
                            var r = [];
                            e.forEach(function (e) {
                                r.push(t[e.localName])
                            }), t = r
                        } else u.throwError("invalid tuple value", u.INVALID_ARGUMENT, {coderType: "tuple", value: t});
                        e.length !== t.length && u.throwError("types/value length mismatch", u.INVALID_ARGUMENT, {
                            coderType: "tuple",
                            value: t
                        });
                        var i = [];
                        e.forEach(function (e, r) {
                            i.push({dynamic: e.dynamic, value: e.encode(t[r])})
                        });
                        var a = 0, o = 0;
                        i.forEach(function (e) {
                            e.dynamic ? (a += 32, o += j(e.value.length)) : a += j(e.value.length)
                        });
                        var s = 0, c = a, f = new Uint8Array(a + o);
                        return i.forEach(function (e) {
                            e.dynamic ? (f.set(M.encode(c), s), s += 32, f.set(e.value, c), c += j(e.value.length)) : (f.set(e.value, s), s += j(e.value.length))
                        }), f
                    }

                    function B(e, t, r) {
                        var n = r, i = 0, a = [];
                        return e.forEach(function (e) {
                            if (e.dynamic) {
                                var o = M.decode(t, r);
                                (s = e.decode(t, n + o.value.toNumber())).consumed = o.consumed
                            } else var s = e.decode(t, r);
                            null != s.value && a.push(s.value), r += s.consumed, i += s.consumed
                        }), e.forEach(function (e, t) {
                            var r = e.localName;
                            r && ("length" === r && (r = "_length"), null == a[r] && (a[r] = a[t]))
                        }), {value: a, consumed: i}
                    }

                    var L = function (e) {
                        function t(t, r, n, i) {
                            var a = this, o = r.type + "[" + (n >= 0 ? n : "") + "]", s = -1 === n || r.dynamic;
                            return (a = e.call(this, t, "array", o, i, s) || this).coder = r, a.length = n, a
                        }

                        return a(t, e), t.prototype.encode = function (e) {
                            Array.isArray(e) || u.throwError("expected array value", u.INVALID_ARGUMENT, {
                                arg: this.localName,
                                coderType: "array",
                                value: e
                            });
                            var t = this.length, r = new Uint8Array(0);
                            -1 === t && (t = e.length, r = M.encode(t)), u.checkArgumentCount(t, e.length, " in coder array" + (this.localName ? " " + this.localName : ""));
                            for (var n = [], i = 0; i < e.length; i++) n.push(this.coder);
                            return d.concat([r, C(n, e)])
                        }, t.prototype.decode = function (e, t) {
                            var r = 0, n = this.length;
                            if (-1 === n) {
                                try {
                                    var i = M.decode(e, t)
                                } catch (e) {
                                    u.throwError("insufficient data for dynamic array length", u.INVALID_ARGUMENT, {
                                        arg: this.localName,
                                        coderType: "array",
                                        value: e.value
                                    })
                                }
                                try {
                                    n = i.value.toNumber()
                                } catch (e) {
                                    u.throwError("array count too large", u.INVALID_ARGUMENT, {
                                        arg: this.localName,
                                        coderType: "array",
                                        value: i.value.toString()
                                    })
                                }
                                r += i.consumed, t += i.consumed
                            }
                            for (var a = [], o = 0; o < n; o++) a.push(new A(this.coder));
                            var s = B(a, e, t);
                            return s.consumed += r, s.value = this.coerceFunc(this.type, s.value), s
                        }, t
                    }(_), W = function (e) {
                        function t(t, r, n) {
                            var i, a = !1, o = [];
                            r.forEach(function (e) {
                                e.dynamic && (a = !0), o.push(e.type)
                            });
                            var s = "tuple(" + o.join(",") + ")";
                            return (i = e.call(this, t, "tuple", s, n, a) || this).coders = r, i
                        }

                        return a(t, e), t.prototype.encode = function (e) {
                            return C(this.coders, e)
                        }, t.prototype.decode = function (e, t) {
                            var r = B(this.coders, e, t);
                            return r.value = this.coerceFunc(this.type, r.value), r
                        }, t
                    }(_);

                    function F(e) {
                        e = e.trim();
                        for (var t = [], r = "", n = 0, i = 0; i < e.length; i++) {
                            var a = e[i];
                            if ("," === a && 0 === n) t.push(r), r = ""; else if (r += a, "(" === a) n++; else if (")" === a && -1 == --n) throw new Error("unbalanced parenthsis")
                        }
                        return r && t.push(r), t
                    }

                    var U = {address: N, bool: I, string: R, bytes: O};

                    function D(e, t) {
                        var r, n = U[t.type];
                        if (n) return new n(e, t.name);
                        if (r = t.type.match(b)) return (0 === (i = parseInt(r[2] || "256")) || i > 256 || i % 8 != 0) && u.throwError("invalid " + r[1] + " bit length", u.INVALID_ARGUMENT, {
                            arg: "param",
                            value: t
                        }), new S(e, i / 8, "int" === r[1], t.name);
                        if (r = t.type.match(p)) return (0 === (i = parseInt(r[1])) || i > 32) && u.throwError("invalid bytes length", u.INVALID_ARGUMENT, {
                            arg: "param",
                            value: t
                        }), new E(e, i, t.name);
                        if (r = t.type.match(v)) {
                            var i = parseInt(r[2] || "-1");
                            return (t = l.shallowCopy(t)).type = r[1], t = l.deepCopy(t), new L(e, D(e, t), i, t.name)
                        }
                        return "tuple" === t.type.substring(0, 5) ? function (e, t, r) {
                            t || (t = []);
                            var n = [];
                            return t.forEach(function (t) {
                                n.push(D(e, t))
                            }), new W(e, n, r)
                        }(e, t.components, t.name) : "" === t.type ? new k(e, t.name) : (u.throwError("invalid type", u.INVALID_ARGUMENT, {
                            arg: "type",
                            value: t.type
                        }), null)
                    }

                    var q = function () {
                        function e(r) {
                            u.checkNew(this, e), r || (r = t.defaultCoerceFunc), l.defineReadOnly(this, "coerceFunc", r)
                        }

                        return e.prototype.encode = function (e, t) {
                            e.length !== t.length && u.throwError("types/values length mismatch", u.INVALID_ARGUMENT, {
                                count: {
                                    types: e.length,
                                    values: t.length
                                }, value: {types: e, values: t}
                            });
                            var r = [];
                            return e.forEach(function (e) {
                                var t;
                                t = "string" == typeof e ? w(e) : e, r.push(D(this.coerceFunc, t))
                            }, this), d.hexlify(new W(this.coerceFunc, r, "_").encode(t))
                        }, e.prototype.decode = function (e, t) {
                            var r = [];
                            return e.forEach(function (e) {
                                var t;
                                t = "string" == typeof e ? w(e) : l.deepCopy(e), r.push(D(this.coerceFunc, t))
                            }, this), new W(this.coerceFunc, r, "_").decode(d.arrayify(t), 0).value
                        }, e
                    }();
                    t.AbiCoder = q, t.defaultAbiCoder = new q
                }, function (e, t, r) {
                    Object.defineProperty(t, "__esModule", {value: !0});
                    var n = r(55);
                    t.AddressZero = "0x0000000000000000000000000000000000000000", t.HashZero = "0x0000000000000000000000000000000000000000000000000000000000000000", t.EtherSymbol = "Ξ";
                    var i = n.bigNumberify(-1);
                    t.NegativeOne = i;
                    var a = n.bigNumberify(0);
                    t.Zero = a;
                    var o = n.bigNumberify(1);
                    t.One = o;
                    var s = n.bigNumberify(2);
                    t.Two = s;
                    var u = n.bigNumberify("1000000000000000000");
                    t.WeiPerEther = u;
                    var c = n.bigNumberify("0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");
                    t.MaxUint256 = c
                }, function (e, t, r) {
                    var n = this && this.__importDefault || function (e) {
                        return e && e.__esModule ? e : {default: e}
                    }, i = this && this.__importStar || function (e) {
                        if (e && e.__esModule) return e;
                        var t = {};
                        if (null != e) for (var r in e) Object.hasOwnProperty.call(e, r) && (t[r] = e[r]);
                        return t.default = e, t
                    };
                    Object.defineProperty(t, "__esModule", {value: !0});
                    var a = n(r(14)), o = r(18), s = r(31), u = i(r(19)), c = new a.default.BN(-1);

                    function f(e) {
                        var t = e.toString(16);
                        return "-" === t[0] ? t.length % 2 == 0 ? "-0x0" + t.substring(1) : "-0x" + t.substring(1) : t.length % 2 == 1 ? "0x0" + t : "0x" + t
                    }

                    function d(e) {
                        return l(b(e))
                    }

                    function h(e) {
                        return new p(f(e))
                    }

                    function l(e) {
                        var t = e._hex;
                        return "-" === t[0] ? new a.default.BN(t.substring(3), 16).mul(c) : new a.default.BN(t.substring(2), 16)
                    }

                    var p = function () {
                        function e(t) {
                            if (u.checkNew(this, e), s.setType(this, "BigNumber"), "string" == typeof t) o.isHexString(t) ? ("0x" == t && (t = "0x0"), s.defineReadOnly(this, "_hex", t)) : "-" === t[0] && o.isHexString(t.substring(1)) ? s.defineReadOnly(this, "_hex", t) : t.match(/^-?[0-9]*$/) ? ("" == t && (t = "0"), s.defineReadOnly(this, "_hex", f(new a.default.BN(t)))) : u.throwError("invalid BigNumber string value", u.INVALID_ARGUMENT, {
                                arg: "value",
                                value: t
                            }); else if ("number" == typeof t) {
                                parseInt(String(t)) !== t && u.throwError("underflow", u.NUMERIC_FAULT, {
                                    operation: "setValue",
                                    fault: "underflow",
                                    value: t,
                                    outputValue: parseInt(String(t))
                                });
                                try {
                                    s.defineReadOnly(this, "_hex", f(new a.default.BN(t)))
                                } catch (e) {
                                    u.throwError("overflow", u.NUMERIC_FAULT, {
                                        operation: "setValue",
                                        fault: "overflow",
                                        details: e.message
                                    })
                                }
                            } else t instanceof e ? s.defineReadOnly(this, "_hex", t._hex) : t.toHexString ? s.defineReadOnly(this, "_hex", f(d(t.toHexString()))) : t._hex && o.isHexString(t._hex) ? s.defineReadOnly(this, "_hex", t._hex) : o.isArrayish(t) ? s.defineReadOnly(this, "_hex", f(new a.default.BN(o.hexlify(t).substring(2), 16))) : u.throwError("invalid BigNumber value", u.INVALID_ARGUMENT, {
                                arg: "value",
                                value: t
                            })
                        }

                        return e.prototype.fromTwos = function (e) {
                            return h(l(this).fromTwos(e))
                        }, e.prototype.toTwos = function (e) {
                            return h(l(this).toTwos(e))
                        }, e.prototype.abs = function () {
                            return "-" === this._hex[0] ? h(l(this).mul(c)) : this
                        }, e.prototype.add = function (e) {
                            return h(l(this).add(d(e)))
                        }, e.prototype.sub = function (e) {
                            return h(l(this).sub(d(e)))
                        }, e.prototype.div = function (e) {
                            return b(e).isZero() && u.throwError("division by zero", u.NUMERIC_FAULT, {
                                operation: "divide",
                                fault: "division by zero"
                            }), h(l(this).div(d(e)))
                        }, e.prototype.mul = function (e) {
                            return h(l(this).mul(d(e)))
                        }, e.prototype.mod = function (e) {
                            return h(l(this).mod(d(e)))
                        }, e.prototype.pow = function (e) {
                            return h(l(this).pow(d(e)))
                        }, e.prototype.maskn = function (e) {
                            return h(l(this).maskn(e))
                        }, e.prototype.eq = function (e) {
                            return l(this).eq(d(e))
                        }, e.prototype.lt = function (e) {
                            return l(this).lt(d(e))
                        }, e.prototype.lte = function (e) {
                            return l(this).lte(d(e))
                        }, e.prototype.gt = function (e) {
                            return l(this).gt(d(e))
                        }, e.prototype.gte = function (e) {
                            return l(this).gte(d(e))
                        }, e.prototype.isZero = function () {
                            return l(this).isZero()
                        }, e.prototype.toNumber = function () {
                            try {
                                return l(this).toNumber()
                            } catch (e) {
                                u.throwError("overflow", u.NUMERIC_FAULT, {
                                    operation: "setValue",
                                    fault: "overflow",
                                    details: e.message
                                })
                            }
                            return null
                        }, e.prototype.toString = function () {
                            return l(this).toString(10)
                        }, e.prototype.toHexString = function () {
                            return this._hex
                        }, e.isBigNumber = function (e) {
                            return s.isType(e, "BigNumber")
                        }, e
                    }();

                    function b(e) {
                        return p.isBigNumber(e) ? e : new p(e)
                    }

                    t.BigNumber = p, t.bigNumberify = b
                }, function (e, t, r) {
                    var n = this && this.__importDefault || function (e) {
                        return e && e.__esModule ? e : {default: e}
                    };
                    Object.defineProperty(t, "__esModule", {value: !0});
                    var i = n(r(14)), a = r(18), o = r(25), s = r(111), u = r(19);

                    function c(e) {
                        "string" == typeof e && e.match(/^0x[0-9A-Fa-f]{40}$/) || u.throwError("invalid address", u.INVALID_ARGUMENT, {
                            arg: "address",
                            value: e
                        });
                        for (var t = (e = e.toLowerCase()).substring(2).split(""), r = new Uint8Array(40), n = 0; n < 40; n++) r[n] = t[n].charCodeAt(0);
                        r = a.arrayify(o.keccak256(r));
                        for (var i = 0; i < 40; i += 2) r[i >> 1] >> 4 >= 8 && (t[i] = t[i].toUpperCase()), (15 & r[i >> 1]) >= 8 && (t[i + 1] = t[i + 1].toUpperCase());
                        return "0x" + t.join("")
                    }

                    for (var f = {}, d = 0; d < 10; d++) f[String(d)] = String(d);
                    for (d = 0; d < 26; d++) f[String.fromCharCode(65 + d)] = String(10 + d);
                    var h, l = Math.floor((h = 9007199254740991, Math.log10 ? Math.log10(h) : Math.log(h) / Math.LN10));

                    function p(e) {
                        var t = "";
                        for ((e = (e = e.toUpperCase()).substring(4) + e.substring(0, 2) + "00").split("").forEach(function (e) {
                            t += f[e]
                        }); t.length >= l;) {
                            var r = t.substring(0, l);
                            t = parseInt(r, 10) % 97 + t.substring(r.length)
                        }
                        for (var n = String(98 - parseInt(t, 10) % 97); n.length < 2;) n = "0" + n;
                        return n
                    }

                    function b(e) {
                        var t = null;
                        if ("string" != typeof e && u.throwError("invalid address", u.INVALID_ARGUMENT, {
                            arg: "address",
                            value: e
                        }), e.match(/^(0x)?[0-9a-fA-F]{40}$/)) "0x" !== e.substring(0, 2) && (e = "0x" + e), t = c(e), e.match(/([A-F].*[a-f])|([a-f].*[A-F])/) && t !== e && u.throwError("bad address checksum", u.INVALID_ARGUMENT, {
                            arg: "address",
                            value: e
                        }); else if (e.match(/^XE[0-9]{2}[0-9A-Za-z]{30,31}$/)) {
                            for (e.substring(2, 4) !== p(e) && u.throwError("bad icap checksum", u.INVALID_ARGUMENT, {
                                arg: "address",
                                value: e
                            }), t = new i.default.BN(e.substring(4), 36).toString(16); t.length < 40;) t = "0" + t;
                            t = c("0x" + t)
                        } else u.throwError("invalid address", u.INVALID_ARGUMENT, {arg: "address", value: e});
                        return t
                    }

                    t.getAddress = b, t.getIcapAddress = function (e) {
                        for (var t = new i.default.BN(b(e).substring(2), 16).toString(36).toUpperCase(); t.length < 30;) t = "0" + t;
                        return "XE" + p("XE00" + t) + t
                    }, t.getContractAddress = function (e) {
                        if (!e.from) throw new Error("missing from address");
                        var t = e.nonce;
                        return b("0x" + o.keccak256(s.encode([b(e.from), a.stripZeros(a.hexlify(t))])).substring(26))
                    }, t.getCreate2Address = function (e) {
                        var t = e.initCodeHash;
                        e.initCode && (t ? o.keccak256(e.initCode) !== t && u.throwError("initCode/initCodeHash mismatch", u.INVALID_ARGUMENT, {
                            arg: "options",
                            value: e
                        }) : t = o.keccak256(e.initCode)), t || u.throwError("missing initCode or initCodeHash", u.INVALID_ARGUMENT, {
                            arg: "options",
                            value: e
                        });
                        var r = b(e.from), n = a.arrayify(e.salt);
                        return 32 !== n.length && u.throwError("invalid salt", u.INVALID_ARGUMENT, {
                            arg: "options",
                            value: e
                        }), b("0x" + o.keccak256(a.concat(["0xff", r, n, t])).substring(26))
                    }
                }, function (e, t, r) {
                    var n = this && this.__importStar || function (e) {
                        if (e && e.__esModule) return e;
                        var t = {};
                        if (null != e) for (var r in e) Object.hasOwnProperty.call(e, r) && (t[r] = e[r]);
                        return t.default = e, t
                    };
                    Object.defineProperty(t, "__esModule", {value: !0});
                    var i = r(112), a = r(56), o = r(18), s = r(132), u = r(25), c = r(31), f = n(r(19)), d = null;

                    function h() {
                        return d || (d = new i.ec("secp256k1")), d
                    }

                    var l = function () {
                        function e(e) {
                            var t = h().keyFromPrivate(o.arrayify(e));
                            c.defineReadOnly(this, "privateKey", o.hexlify(t.priv.toArray("be", 32))), c.defineReadOnly(this, "publicKey", "0x" + t.getPublic(!1, "hex")), c.defineReadOnly(this, "compressedPublicKey", "0x" + t.getPublic(!0, "hex")), c.defineReadOnly(this, "publicKeyBytes", t.getPublic().encode(null, !0))
                        }

                        return e.prototype.sign = function (e) {
                            var t = h().keyFromPrivate(o.arrayify(this.privateKey)).sign(o.arrayify(e), {canonical: !0});
                            return {
                                recoveryParam: t.recoveryParam,
                                r: o.hexZeroPad("0x" + t.r.toString(16), 32),
                                s: o.hexZeroPad("0x" + t.s.toString(16), 32),
                                v: 27 + t.recoveryParam
                            }
                        }, e.prototype.computeSharedSecret = function (e) {
                            var t = h().keyFromPrivate(o.arrayify(this.privateKey)),
                                r = h().keyFromPublic(o.arrayify(p(e)));
                            return o.hexZeroPad("0x" + t.derive(r.getPublic()).toString(16), 32)
                        }, e.prototype._addPoint = function (e) {
                            var t = h().keyFromPublic(o.arrayify(this.publicKey)), r = h().keyFromPublic(o.arrayify(e));
                            return "0x" + t.pub.add(r.pub).encodeCompressed("hex")
                        }, e
                    }();

                    function p(e, t) {
                        var r = o.arrayify(e);
                        if (32 === r.length) {
                            var n = new l(r);
                            return t ? n.compressedPublicKey : n.publicKey
                        }
                        return 33 === r.length ? t ? o.hexlify(r) : "0x" + h().keyFromPublic(r).getPublic(!1, "hex") : 65 === r.length ? t ? "0x" + h().keyFromPublic(r).getPublic(!0, "hex") : o.hexlify(r) : (f.throwError("invalid public or private key", f.INVALID_ARGUMENT, {
                            arg: "key",
                            value: "[REDACTED]"
                        }), null)
                    }

                    function b(e) {
                        var t = "0x" + p(e).slice(4);
                        return a.getAddress("0x" + u.keccak256(t).substring(26))
                    }

                    function v(e, t) {
                        var r = o.splitSignature(t), n = {r: o.arrayify(r.r), s: o.arrayify(r.s)};
                        return "0x" + h().recoverPubKey(o.arrayify(e), n, r.recoveryParam).encode("hex", !1)
                    }

                    function g(e, t) {
                        return b(v(o.arrayify(e), t))
                    }

                    t.KeyPair = l, t.computePublicKey = p, t.computeAddress = b, t.recoverPublicKey = v, t.recoverAddress = g, t.verifyMessage = function (e, t) {
                        return g(s.hashMessage(e), t)
                    }
                }, function (e, t, r) {
                    var n = t;

                    function i(e) {
                        return 1 === e.length ? "0" + e : e
                    }

                    function a(e) {
                        for (var t = "", r = 0; r < e.length; r++) t += i(e[r].toString(16));
                        return t
                    }

                    n.toArray = function (e, t) {
                        if (Array.isArray(e)) return e.slice();
                        if (!e) return [];
                        var r = [];
                        if ("string" != typeof e) {
                            for (var n = 0; n < e.length; n++) r[n] = 0 | e[n];
                            return r
                        }
                        if ("hex" === t) for ((e = e.replace(/[^a-z0-9]+/gi, "")).length % 2 != 0 && (e = "0" + e), n = 0; n < e.length; n += 2) r.push(parseInt(e[n] + e[n + 1], 16)); else for (n = 0; n < e.length; n++) {
                            var i = e.charCodeAt(n), a = i >> 8, o = 255 & i;
                            a ? r.push(a, o) : r.push(o)
                        }
                        return r
                    }, n.zero2 = i, n.toHex = a, n.encode = function (e, t) {
                        return "hex" === t ? a(e) : e
                    }
                }, function (e, t, r) {
                    var i;

                    function a(e) {
                        this.rand = e
                    }

                    if (e.exports = function (e) {
                        return i || (i = new a(null)), i.generate(e)
                    }, e.exports.Rand = a, a.prototype.generate = function (e) {
                        return this._rand(e)
                    }, a.prototype._rand = function (e) {
                        if (this.rand.getBytes) return this.rand.getBytes(e);
                        for (var t = new Uint8Array(e), r = 0; r < t.length; r++) t[r] = this.rand.getByte();
                        return t
                    }, "object" == ("undefined" == typeof self ? "undefined" : n()(self))) self.crypto && self.crypto.getRandomValues ? a.prototype._rand = function (e) {
                        var t = new Uint8Array(e);
                        return self.crypto.getRandomValues(t), t
                    } : self.msCrypto && self.msCrypto.getRandomValues ? a.prototype._rand = function (e) {
                        var t = new Uint8Array(e);
                        return self.msCrypto.getRandomValues(t), t
                    } : "object" == ("undefined" == typeof window ? "undefined" : n()(window)) && (a.prototype._rand = function () {
                        throw new Error("Not implemented yet")
                    }); else try {
                        var o = r(114);
                        if ("function" != typeof o.randomBytes) throw new Error("Not supported");
                        a.prototype._rand = function (e) {
                            return o.randomBytes(e)
                        }
                    } catch (e) {
                    }
                }, function (e, t, r) {
                    var n = t;
                    n.base = r(30), n.short = r(115), n.mont = r(116), n.edwards = r(117)
                }, function (e, t, r) {
                    var n = r(16).rotr32;

                    function i(e, t, r) {
                        return e & t ^ ~e & r
                    }

                    function a(e, t, r) {
                        return e & t ^ e & r ^ t & r
                    }

                    function o(e, t, r) {
                        return e ^ t ^ r
                    }

                    t.ft_1 = function (e, t, r, n) {
                        return 0 === e ? i(t, r, n) : 1 === e || 3 === e ? o(t, r, n) : 2 === e ? a(t, r, n) : void 0
                    }, t.ch32 = i, t.maj32 = a, t.p32 = o, t.s0_256 = function (e) {
                        return n(e, 2) ^ n(e, 13) ^ n(e, 22)
                    }, t.s1_256 = function (e) {
                        return n(e, 6) ^ n(e, 11) ^ n(e, 25)
                    }, t.g0_256 = function (e) {
                        return n(e, 7) ^ n(e, 18) ^ e >>> 3
                    }, t.g1_256 = function (e) {
                        return n(e, 17) ^ n(e, 19) ^ e >>> 10
                    }
                }, function (e, t, r) {
                    var n = r(16), i = r(28), a = r(61), o = r(12), s = n.sum32, u = n.sum32_4, c = n.sum32_5,
                        f = a.ch32, d = a.maj32, h = a.s0_256, l = a.s1_256, p = a.g0_256, b = a.g1_256,
                        v = i.BlockHash,
                        g = [1116352408, 1899447441, 3049323471, 3921009573, 961987163, 1508970993, 2453635748, 2870763221, 3624381080, 310598401, 607225278, 1426881987, 1925078388, 2162078206, 2614888103, 3248222580, 3835390401, 4022224774, 264347078, 604807628, 770255983, 1249150122, 1555081692, 1996064986, 2554220882, 2821834349, 2952996808, 3210313671, 3336571891, 3584528711, 113926993, 338241895, 666307205, 773529912, 1294757372, 1396182291, 1695183700, 1986661051, 2177026350, 2456956037, 2730485921, 2820302411, 3259730800, 3345764771, 3516065817, 3600352804, 4094571909, 275423344, 430227734, 506948616, 659060556, 883997877, 958139571, 1322822218, 1537002063, 1747873779, 1955562222, 2024104815, 2227730452, 2361852424, 2428436474, 2756734187, 3204031479, 3329325298];

                    function m() {
                        if (!(this instanceof m)) return new m;
                        v.call(this), this.h = [1779033703, 3144134277, 1013904242, 2773480762, 1359893119, 2600822924, 528734635, 1541459225], this.k = g, this.W = new Array(64)
                    }

                    n.inherits(m, v), e.exports = m, m.blockSize = 512, m.outSize = 256, m.hmacStrength = 192, m.padLength = 64, m.prototype._update = function (e, t) {
                        for (var r = this.W, n = 0; n < 16; n++) r[n] = e[t + n];
                        for (; n < r.length; n++) r[n] = u(b(r[n - 2]), r[n - 7], p(r[n - 15]), r[n - 16]);
                        var i = this.h[0], a = this.h[1], v = this.h[2], g = this.h[3], m = this.h[4], y = this.h[5],
                            w = this.h[6], x = this.h[7];
                        for (o(this.k.length === r.length), n = 0; n < r.length; n++) {
                            var _ = c(x, l(m), f(m, y, w), this.k[n], r[n]), A = s(h(i), d(i, a, v));
                            x = w, w = y, y = m, m = s(g, _), g = v, v = a, a = i, i = s(_, A)
                        }
                        this.h[0] = s(this.h[0], i), this.h[1] = s(this.h[1], a), this.h[2] = s(this.h[2], v), this.h[3] = s(this.h[3], g), this.h[4] = s(this.h[4], m), this.h[5] = s(this.h[5], y), this.h[6] = s(this.h[6], w), this.h[7] = s(this.h[7], x)
                    }, m.prototype._digest = function (e) {
                        return "hex" === e ? n.toHex32(this.h, "big") : n.split32(this.h, "big")
                    }
                }, function (e, t, r) {
                    var n = r(16), i = r(28), a = r(12), o = n.rotr64_hi, s = n.rotr64_lo, u = n.shr64_hi,
                        c = n.shr64_lo, f = n.sum64, d = n.sum64_hi, h = n.sum64_lo, l = n.sum64_4_hi, p = n.sum64_4_lo,
                        b = n.sum64_5_hi, v = n.sum64_5_lo, g = i.BlockHash,
                        m = [1116352408, 3609767458, 1899447441, 602891725, 3049323471, 3964484399, 3921009573, 2173295548, 961987163, 4081628472, 1508970993, 3053834265, 2453635748, 2937671579, 2870763221, 3664609560, 3624381080, 2734883394, 310598401, 1164996542, 607225278, 1323610764, 1426881987, 3590304994, 1925078388, 4068182383, 2162078206, 991336113, 2614888103, 633803317, 3248222580, 3479774868, 3835390401, 2666613458, 4022224774, 944711139, 264347078, 2341262773, 604807628, 2007800933, 770255983, 1495990901, 1249150122, 1856431235, 1555081692, 3175218132, 1996064986, 2198950837, 2554220882, 3999719339, 2821834349, 766784016, 2952996808, 2566594879, 3210313671, 3203337956, 3336571891, 1034457026, 3584528711, 2466948901, 113926993, 3758326383, 338241895, 168717936, 666307205, 1188179964, 773529912, 1546045734, 1294757372, 1522805485, 1396182291, 2643833823, 1695183700, 2343527390, 1986661051, 1014477480, 2177026350, 1206759142, 2456956037, 344077627, 2730485921, 1290863460, 2820302411, 3158454273, 3259730800, 3505952657, 3345764771, 106217008, 3516065817, 3606008344, 3600352804, 1432725776, 4094571909, 1467031594, 275423344, 851169720, 430227734, 3100823752, 506948616, 1363258195, 659060556, 3750685593, 883997877, 3785050280, 958139571, 3318307427, 1322822218, 3812723403, 1537002063, 2003034995, 1747873779, 3602036899, 1955562222, 1575990012, 2024104815, 1125592928, 2227730452, 2716904306, 2361852424, 442776044, 2428436474, 593698344, 2756734187, 3733110249, 3204031479, 2999351573, 3329325298, 3815920427, 3391569614, 3928383900, 3515267271, 566280711, 3940187606, 3454069534, 4118630271, 4000239992, 116418474, 1914138554, 174292421, 2731055270, 289380356, 3203993006, 460393269, 320620315, 685471733, 587496836, 852142971, 1086792851, 1017036298, 365543100, 1126000580, 2618297676, 1288033470, 3409855158, 1501505948, 4234509866, 1607167915, 987167468, 1816402316, 1246189591];

                    function y() {
                        if (!(this instanceof y)) return new y;
                        g.call(this), this.h = [1779033703, 4089235720, 3144134277, 2227873595, 1013904242, 4271175723, 2773480762, 1595750129, 1359893119, 2917565137, 2600822924, 725511199, 528734635, 4215389547, 1541459225, 327033209], this.k = m, this.W = new Array(160)
                    }

                    function w(e, t, r, n, i) {
                        var a = e & r ^ ~e & i;
                        return a < 0 && (a += 4294967296), a
                    }

                    function x(e, t, r, n, i, a) {
                        var o = t & n ^ ~t & a;
                        return o < 0 && (o += 4294967296), o
                    }

                    function _(e, t, r, n, i) {
                        var a = e & r ^ e & i ^ r & i;
                        return a < 0 && (a += 4294967296), a
                    }

                    function A(e, t, r, n, i, a) {
                        var o = t & n ^ t & a ^ n & a;
                        return o < 0 && (o += 4294967296), o
                    }

                    function k(e, t) {
                        var r = o(e, t, 28) ^ o(t, e, 2) ^ o(t, e, 7);
                        return r < 0 && (r += 4294967296), r
                    }

                    function S(e, t) {
                        var r = s(e, t, 28) ^ s(t, e, 2) ^ s(t, e, 7);
                        return r < 0 && (r += 4294967296), r
                    }

                    function M(e, t) {
                        var r = o(e, t, 14) ^ o(e, t, 18) ^ o(t, e, 9);
                        return r < 0 && (r += 4294967296), r
                    }

                    function I(e, t) {
                        var r = s(e, t, 14) ^ s(e, t, 18) ^ s(t, e, 9);
                        return r < 0 && (r += 4294967296), r
                    }

                    function E(e, t) {
                        var r = o(e, t, 1) ^ o(e, t, 8) ^ u(e, t, 7);
                        return r < 0 && (r += 4294967296), r
                    }

                    function N(e, t) {
                        var r = s(e, t, 1) ^ s(e, t, 8) ^ c(e, t, 7);
                        return r < 0 && (r += 4294967296), r
                    }

                    function P(e, t) {
                        var r = o(e, t, 19) ^ o(t, e, 29) ^ u(e, t, 6);
                        return r < 0 && (r += 4294967296), r
                    }

                    function T(e, t) {
                        var r = s(e, t, 19) ^ s(t, e, 29) ^ c(e, t, 6);
                        return r < 0 && (r += 4294967296), r
                    }

                    n.inherits(y, g), e.exports = y, y.blockSize = 1024, y.outSize = 512, y.hmacStrength = 192, y.padLength = 128, y.prototype._prepareBlock = function (e, t) {
                        for (var r = this.W, n = 0; n < 32; n++) r[n] = e[t + n];
                        for (; n < r.length; n += 2) {
                            var i = P(r[n - 4], r[n - 3]), a = T(r[n - 4], r[n - 3]), o = r[n - 14], s = r[n - 13],
                                u = E(r[n - 30], r[n - 29]), c = N(r[n - 30], r[n - 29]), f = r[n - 32], d = r[n - 31];
                            r[n] = l(i, a, o, s, u, c, f, d), r[n + 1] = p(i, a, o, s, u, c, f, d)
                        }
                    }, y.prototype._update = function (e, t) {
                        this._prepareBlock(e, t);
                        var r = this.W, n = this.h[0], i = this.h[1], o = this.h[2], s = this.h[3], u = this.h[4],
                            c = this.h[5], l = this.h[6], p = this.h[7], g = this.h[8], m = this.h[9], y = this.h[10],
                            E = this.h[11], N = this.h[12], P = this.h[13], T = this.h[14], O = this.h[15];
                        a(this.k.length === r.length);
                        for (var R = 0; R < r.length; R += 2) {
                            var j = T, C = O, B = M(g, m), L = I(g, m), W = w(g, 0, y, 0, N), F = x(0, m, 0, E, 0, P),
                                U = this.k[R], D = this.k[R + 1], q = r[R], z = r[R + 1],
                                H = b(j, C, B, L, W, F, U, D, q, z), V = v(j, C, B, L, W, F, U, D, q, z);
                            j = k(n, i), C = S(n, i), B = _(n, 0, o, 0, u), L = A(0, i, 0, s, 0, c);
                            var K = d(j, C, B, L), G = h(j, C, B, L);
                            T = N, O = P, N = y, P = E, y = g, E = m, g = d(l, p, H, V), m = h(p, p, H, V), l = u, p = c, u = o, c = s, o = n, s = i, n = d(H, V, K, G), i = h(H, V, K, G)
                        }
                        f(this.h, 0, n, i), f(this.h, 2, o, s), f(this.h, 4, u, c), f(this.h, 6, l, p), f(this.h, 8, g, m), f(this.h, 10, y, E), f(this.h, 12, N, P), f(this.h, 14, T, O)
                    }, y.prototype._digest = function (e) {
                        return "hex" === e ? n.toHex32(this.h, "big") : n.split32(this.h, "big")
                    }
                }, function (e, t, r) {
                    Object.defineProperty(t, "__esModule", {value: !0}), t.default = function () {
                        var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : {},
                            t = arguments.length > 1 ? arguments[1] : void 0;
                        for (var r in t) void 0 === e[r] && (e[r] = t[r]);
                        return e
                    }, e.exports = t.default, e.exports.default = t.default
                }, function (e, t, r) {
                    var n = r(17), i = r(21);
                    e.exports = function (e, t) {
                        return !t || "object" !== n(t) && "function" != typeof t ? i(e) : t
                    }
                }, function (e, t) {
                    function r(t) {
                        return e.exports = r = Object.setPrototypeOf ? Object.getPrototypeOf : function (e) {
                            return e.__proto__ || Object.getPrototypeOf(e)
                        }, r(t)
                    }

                    e.exports = r
                }, function (e, t, r) {
                    var n = r(79);
                    e.exports = function (e, t) {
                        if ("function" != typeof t && null !== t) throw new TypeError("Super expression must either be null or a function");
                        e.prototype = Object.create(t && t.prototype, {
                            constructor: {
                                value: e,
                                writable: !0,
                                configurable: !0
                            }
                        }), t && n(e, t)
                    }
                }, function (e, t, r) {
                    e.exports = r(80)
                }, function (e, t, r) {
                    Object.defineProperty(t, "__esModule", {value: !0}), t.default = function (e, t) {
                        if ((0, n.default)(e), !e || e.length >= 2083 || /[\s<>]/.test(e)) return !1;
                        if (0 === e.indexOf("mailto:")) return !1;
                        var r, s, d, h, l, p, b, v;
                        if (t = (0, o.default)(t, u), (b = (e = (b = (e = (b = e.split("#")).shift()).split("?")).shift()).split("://")).length > 1) {
                            if (r = b.shift().toLowerCase(), t.require_valid_protocol && -1 === t.protocols.indexOf(r)) return !1
                        } else {
                            if (t.require_protocol) return !1;
                            if ("//" === e.substr(0, 2)) {
                                if (!t.allow_protocol_relative_urls) return !1;
                                b[0] = e.substr(2)
                            }
                        }
                        if ("" === (e = b.join("://"))) return !1;
                        if ("" === (e = (b = e.split("/")).shift()) && !t.require_host) return !0;
                        if ((b = e.split("@")).length > 1) {
                            if (t.disallow_auth) return !1;
                            if ((s = b.shift()).indexOf(":") >= 0 && s.split(":").length > 2) return !1
                        }
                        p = null, v = null;
                        var g = (h = b.join("@")).match(c);
                        return g ? (d = "", v = g[1], p = g[2] || null) : (d = (b = h.split(":")).shift(), b.length && (p = b.join(":"))), !(null !== p && (l = parseInt(p, 10), !/^[0-9]+$/.test(p) || l <= 0 || l > 65535)) && (!!((0, a.default)(d) || (0, i.default)(d, t) || v && (0, a.default)(v, 6)) && (d = d || v, !(t.host_whitelist && !f(d, t.host_whitelist)) && (!t.host_blacklist || !f(d, t.host_blacklist))))
                    };
                    var n = s(r(34)), i = s(r(133)), a = s(r(134)), o = s(r(64));

                    function s(e) {
                        return e && e.__esModule ? e : {default: e}
                    }

                    var u = {
                        protocols: ["http", "https", "ftp"],
                        require_tld: !0,
                        require_protocol: !1,
                        require_host: !0,
                        require_valid_protocol: !0,
                        allow_underscores: !1,
                        allow_trailing_dot: !1,
                        allow_protocol_relative_urls: !1
                    }, c = /^\[([^\]]+)\](?::([0-9]+))?$/;

                    function f(e, t) {
                        for (var r = 0; r < t.length; r++) {
                            var n = t[r];
                            if (e === n || (i = n, "[object RegExp]" === Object.prototype.toString.call(i) && n.test(e))) return !0
                        }
                        var i;
                        return !1
                    }

                    e.exports = t.default, e.exports.default = t.default
                }, function (e, t, r) {
                    var n = Object.prototype.hasOwnProperty, i = "~";

                    function a() {
                    }

                    function o(e, t, r, n, a) {
                        if ("function" != typeof r) throw new TypeError("The listener must be a function");
                        var o = new function (e, t, r) {
                            this.fn = e, this.context = t, this.once = r || !1
                        }(r, n || e, a), s = i ? i + t : t;
                        return e._events[s] ? e._events[s].fn ? e._events[s] = [e._events[s], o] : e._events[s].push(o) : (e._events[s] = o, e._eventsCount++), e
                    }

                    function s(e, t) {
                        0 == --e._eventsCount ? e._events = new a : delete e._events[t]
                    }

                    function u() {
                        this._events = new a, this._eventsCount = 0
                    }

                    Object.create && (a.prototype = Object.create(null), (new a).__proto__ || (i = !1)), u.prototype.eventNames = function () {
                        var e, t, r = [];
                        if (0 === this._eventsCount) return r;
                        for (t in e = this._events) n.call(e, t) && r.push(i ? t.slice(1) : t);
                        return Object.getOwnPropertySymbols ? r.concat(Object.getOwnPropertySymbols(e)) : r
                    }, u.prototype.listeners = function (e) {
                        var t = i ? i + e : e, r = this._events[t];
                        if (!r) return [];
                        if (r.fn) return [r.fn];
                        for (var n = 0, a = r.length, o = new Array(a); n < a; n++) o[n] = r[n].fn;
                        return o
                    }, u.prototype.listenerCount = function (e) {
                        var t = i ? i + e : e, r = this._events[t];
                        return r ? r.fn ? 1 : r.length : 0
                    }, u.prototype.emit = function (e, t, r, n, a, o) {
                        var s = i ? i + e : e;
                        if (!this._events[s]) return !1;
                        var u, c, f = this._events[s], d = arguments.length;
                        if (f.fn) {
                            switch (f.once && this.removeListener(e, f.fn, void 0, !0), d) {
                                case 1:
                                    return f.fn.call(f.context), !0;
                                case 2:
                                    return f.fn.call(f.context, t), !0;
                                case 3:
                                    return f.fn.call(f.context, t, r), !0;
                                case 4:
                                    return f.fn.call(f.context, t, r, n), !0;
                                case 5:
                                    return f.fn.call(f.context, t, r, n, a), !0;
                                case 6:
                                    return f.fn.call(f.context, t, r, n, a, o), !0
                            }
                            for (c = 1, u = new Array(d - 1); c < d; c++) u[c - 1] = arguments[c];
                            f.fn.apply(f.context, u)
                        } else {
                            var h, l = f.length;
                            for (c = 0; c < l; c++) switch (f[c].once && this.removeListener(e, f[c].fn, void 0, !0), d) {
                                case 1:
                                    f[c].fn.call(f[c].context);
                                    break;
                                case 2:
                                    f[c].fn.call(f[c].context, t);
                                    break;
                                case 3:
                                    f[c].fn.call(f[c].context, t, r);
                                    break;
                                case 4:
                                    f[c].fn.call(f[c].context, t, r, n);
                                    break;
                                default:
                                    if (!u) for (h = 1, u = new Array(d - 1); h < d; h++) u[h - 1] = arguments[h];
                                    f[c].fn.apply(f[c].context, u)
                            }
                        }
                        return !0
                    }, u.prototype.on = function (e, t, r) {
                        return o(this, e, t, r, !1)
                    }, u.prototype.once = function (e, t, r) {
                        return o(this, e, t, r, !0)
                    }, u.prototype.removeListener = function (e, t, r, n) {
                        var a = i ? i + e : e;
                        if (!this._events[a]) return this;
                        if (!t) return s(this, a), this;
                        var o = this._events[a];
                        if (o.fn) o.fn !== t || n && !o.once || r && o.context !== r || s(this, a); else {
                            for (var u = 0, c = [], f = o.length; u < f; u++) (o[u].fn !== t || n && !o[u].once || r && o[u].context !== r) && c.push(o[u]);
                            c.length ? this._events[a] = 1 === c.length ? c[0] : c : s(this, a)
                        }
                        return this
                    }, u.prototype.removeAllListeners = function (e) {
                        var t;
                        return e ? (t = i ? i + e : e, this._events[t] && s(this, t)) : (this._events = new a, this._eventsCount = 0), this
                    }, u.prototype.off = u.prototype.removeListener, u.prototype.addListener = u.prototype.on, u.prefixed = i, u.EventEmitter = u, e.exports = u
                }, function (e) {
                    e.exports = JSON.parse('{"a":"3.2.6-s-1.0"}')
                }, function (e, t, r) {
                    t.decode = t.parse = r(138), t.encode = t.stringify = r(139)
                }, function (e, t, r) {
                    r.d(t, "a", function () {
                        return m
                    });
                    var n = r(7), i = r.n(n), a = r(1), o = r.n(a), s = r(2), u = r.n(s), c = r(4), f = r.n(c),
                        d = r(5), h = r.n(d), l = r(10), p = r.n(l), b = r(24);

                    function v(e, t) {
                        var r = Object.keys(e);
                        if (Object.getOwnPropertySymbols) {
                            var n = Object.getOwnPropertySymbols(e);
                            t && (n = n.filter(function (t) {
                                return Object.getOwnPropertyDescriptor(e, t).enumerable
                            })), r.push.apply(r, n)
                        }
                        return r
                    }

                    function g(e) {
                        for (var t = 1; t < arguments.length; t++) {
                            var r = null != arguments[t] ? arguments[t] : {};
                            t % 2 ? v(r, !0).forEach(function (t) {
                                i()(e, t, r[t])
                            }) : Object.getOwnPropertyDescriptors ? Object.defineProperties(e, Object.getOwnPropertyDescriptors(r)) : v(r).forEach(function (t) {
                                Object.defineProperty(e, t, Object.getOwnPropertyDescriptor(r, t))
                            })
                        }
                        return e
                    }

                    var m = function () {
                        function e(t) {
                            var r = arguments.length > 1 && void 0 !== arguments[1] && arguments[1],
                                n = arguments.length > 2 && void 0 !== arguments[2] && arguments[2],
                                i = arguments.length > 3 && void 0 !== arguments[3] && arguments[3];
                            f()(this, e), this.mainchain = n;
                            var a = t.fullHost, o = t.fullNode, s = t.solidityNode, u = t.eventServer,
                                c = t.mainGatewayAddress, d = t.sideGatewayAddress, h = t.sideChainId;
                            this.sidechain = new r(a || o, a || s, a || u, i), this.isAddress = this.mainchain.isAddress, this.utils = this.mainchain.utils, this.setMainGatewayAddress(c), this.setSideGatewayAddress(d), this.setChainId(h), this.injectPromise = p()(this), this.validator = new b.a(this.sidechain);
                            var l = this;
                            this.sidechain.trx.sign = function () {
                                return l.sign.apply(l, arguments)
                            }, this.sidechain.trx.multiSign = function () {
                                return l.multiSign.apply(l, arguments)
                            }
                        }

                        var t, r, n, i, a, s, c, d, l, v, m, y, w, x, _, A, k, S, M, I, E;
                        return h()(e, [{
                            key: "setMainGatewayAddress", value: function (e) {
                                if (!this.isAddress(e)) throw new Error("Invalid main gateway address provided");
                                this.mainGatewayAddress = e
                            }
                        }, {
                            key: "setSideGatewayAddress", value: function (e) {
                                if (!this.isAddress(e)) throw new Error("Invalid side gateway address provided");
                                this.sideGatewayAddress = e
                            }
                        }, {
                            key: "setChainId", value: function (e) {
                                if (!this.utils.isString(e) || !e) throw new Error("Invalid side chainId provided");
                                this.chainId = e
                            }
                        }, {
                            key: "signTransaction", value: function (e, t) {
                                "string" == typeof e && (e = this.utils.code.hexStr2byteArray(e));
                                var r = this.utils.code.hexStr2byteArray(this.chainId),
                                    n = this.utils.code.hexStr2byteArray(t.txID).concat(r),
                                    i = this.sidechain.utils.ethersUtils.sha256(n),
                                    a = this.utils.crypto.ECKeySign(this.utils.code.hexStr2byteArray(i.replace(/^0x/, "")), e);
                                return Array.isArray(t.signature) ? t.signature.includes(a) || t.signature.push(a) : t.signature = [a], t
                            }
                        }, {
                            key: "multiSign", value: (E = u()(o.a.mark(function e() {
                                var t, r, n, i, a, s, u, c = arguments;
                                return o.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (t = c.length > 0 && void 0 !== c[0] && c[0], r = c.length > 1 && void 0 !== c[1] ? c[1] : this.sidechain.defaultPrivateKey, n = c.length > 2 && void 0 !== c[2] && c[2], i = c.length > 3 && void 0 !== c[3] && c[3], this.utils.isFunction(n) && (i = n, n = 0), this.utils.isFunction(r) && (i = r, r = this.mainchain.defaultPrivateKey, n = 0), i) {
                                                e.next = 8;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this.multiSign, t, r, n));
                                        case 8:
                                            if (this.utils.isObject(t) && t.raw_data && t.raw_data.contract) {
                                                e.next = 10;
                                                break
                                            }
                                            return e.abrupt("return", i("Invalid transaction provided"));
                                        case 10:
                                            if (t.raw_data.contract[0].Permission_id || !(n > 0)) {
                                                e.next = 30;
                                                break
                                            }
                                            return t.raw_data.contract[0].Permission_id = n, a = this.sidechain.address.toHex(this.sidechain.address.fromPrivateKey(r)).toLowerCase(), e.next = 15, this.sidechain.trx.getSignWeight(t, n);
                                        case 15:
                                            if ("PERMISSION_ERROR" !== (s = e.sent).result.code) {
                                                e.next = 18;
                                                break
                                            }
                                            return e.abrupt("return", i(s.result.message));
                                        case 18:
                                            if (u = !1, s.permission.keys.map(function (e) {
                                                e.address === a && (u = !0)
                                            }), u) {
                                                e.next = 22;
                                                break
                                            }
                                            return e.abrupt("return", i(r + " has no permission to sign"));
                                        case 22:
                                            if (!s.approved_list || -1 == s.approved_list.indexOf(a)) {
                                                e.next = 24;
                                                break
                                            }
                                            return e.abrupt("return", i(r + " already sign transaction"));
                                        case 24:
                                            if (!s.transaction || !s.transaction.transaction) {
                                                e.next = 29;
                                                break
                                            }
                                            (t = s.transaction.transaction).raw_data.contract[0].Permission_id = n, e.next = 30;
                                            break;
                                        case 29:
                                            return e.abrupt("return", i("Invalid transaction provided"));
                                        case 30:
                                            return e.prev = 30, e.abrupt("return", i(null, this.signTransaction(r, t)));
                                        case 34:
                                            e.prev = 34, e.t0 = e.catch(30), i(e.t0);
                                        case 37:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this, [[30, 34]])
                            })), function () {
                                return E.apply(this, arguments)
                            })
                        }, {
                            key: "sign", value: (I = u()(o.a.mark(function e() {
                                var t, r, n, i, a, s, u = arguments;
                                return o.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (t = u.length > 0 && void 0 !== u[0] && u[0], r = u.length > 1 && void 0 !== u[1] ? u[1] : this.sidechain.defaultPrivateKey, n = !(u.length > 2 && void 0 !== u[2]) || u[2], i = u.length > 3 && void 0 !== u[3] && u[3], a = u.length > 4 && void 0 !== u[4] && u[4], this.utils.isFunction(i) && (a = i, i = !1), this.utils.isFunction(n) && (a = n, n = !0, i = !1), this.utils.isFunction(r) && (a = r, r = this.sidechain.defaultPrivateKey, n = !0, i = !1), a) {
                                                e.next = 10;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this.sign, t, r, n, i));
                                        case 10:
                                            if (!this.utils.isString(t)) {
                                                e.next = 21;
                                                break
                                            }
                                            if (this.utils.isHex(t)) {
                                                e.next = 13;
                                                break
                                            }
                                            return e.abrupt("return", a("Expected hex message input"));
                                        case 13:
                                            return e.prev = 13, s = this.sidechain.trx.signString(t, r, n), e.abrupt("return", a(null, s));
                                        case 18:
                                            e.prev = 18, e.t0 = e.catch(13), a(e.t0);
                                        case 21:
                                            if (this.utils.isObject(t)) {
                                                e.next = 23;
                                                break
                                            }
                                            return e.abrupt("return", a("Invalid transaction provided"));
                                        case 23:
                                            if (i || !t.signature) {
                                                e.next = 25;
                                                break
                                            }
                                            return e.abrupt("return", a("Transaction is already signed"));
                                        case 25:
                                            if (e.prev = 25, i) {
                                                e.next = 30;
                                                break
                                            }
                                            if (this.sidechain.address.toHex(this.sidechain.address.fromPrivateKey(r)).toLowerCase() === this.sidechain.address.toHex(t.raw_data.contract[0].parameter.value.owner_address)) {
                                                e.next = 30;
                                                break
                                            }
                                            return e.abrupt("return", a("Private key does not match address in transaction"));
                                        case 30:
                                            return e.abrupt("return", a(null, this.signTransaction(r, t)));
                                        case 33:
                                            e.prev = 33, e.t1 = e.catch(25), a(e.t1);
                                        case 36:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this, [[13, 18], [25, 33]])
                            })), function () {
                                return I.apply(this, arguments)
                            })
                        }, {
                            key: "depositTrx", value: (M = u()(o.a.mark(function e(t, r, n) {
                                var i, a, s, u, c, f = arguments;
                                return o.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (i = f.length > 3 && void 0 !== f[3] ? f[3] : {}, a = f.length > 4 && void 0 !== f[4] ? f[4] : this.mainchain.defaultPrivateKey, s = f.length > 5 && void 0 !== f[5] && f[5], this.utils.isFunction(a) && (s = a, a = this.mainchain.defaultPrivateKey), this.utils.isFunction(i) && (s = i, i = {}), s) {
                                                e.next = 7;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this.depositTrx, t, r, n, i, a));
                                        case 7:
                                            if (!this.validator.notValid([{
                                                name: "callValue",
                                                type: "integer",
                                                value: t,
                                                gte: 0
                                            }, {
                                                name: "depositFee",
                                                type: "integer",
                                                value: r,
                                                gte: 0
                                            }, {name: "feeLimit", type: "integer", value: n, gte: 0, lte: 5e9}], s)) {
                                                e.next = 9;
                                                break
                                            }
                                            return e.abrupt("return");
                                        case 9:
                                            return i = g({
                                                callValue: Number(t) + Number(r),
                                                feeLimit: n
                                            }, i), e.prev = 10, e.next = 13, this.mainchain.contract().at(this.mainGatewayAddress);
                                        case 13:
                                            return u = e.sent, e.next = 16, u.depositTRX().send(i, a);
                                        case 16:
                                            return c = e.sent, e.abrupt("return", s(null, c));
                                        case 20:
                                            return e.prev = 20, e.t0 = e.catch(10), e.abrupt("return", s(e.t0));
                                        case 23:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this, [[10, 20]])
                            })), function (e, t, r) {
                                return M.apply(this, arguments)
                            })
                        }, {
                            key: "depositTrc10", value: (S = u()(o.a.mark(function e(t, r, n, i) {
                                var a, s, u, c, f, d = arguments;
                                return o.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (a = d.length > 4 && void 0 !== d[4] ? d[4] : {}, s = d.length > 5 && void 0 !== d[5] ? d[5] : this.mainchain.defaultPrivateKey, u = d.length > 6 && void 0 !== d[6] && d[6], this.utils.isFunction(s) && (u = s, s = this.mainchain.defaultPrivateKey), this.utils.isFunction(a) && (u = a, a = {}), u) {
                                                e.next = 7;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this.depositTrc10, t, r, n, i, a, s));
                                        case 7:
                                            if (!this.validator.notValid([{
                                                name: "tokenValue",
                                                type: "integer",
                                                value: r,
                                                gte: 0
                                            }, {
                                                name: "depositFee",
                                                type: "integer",
                                                value: n,
                                                gte: 0
                                            }, {
                                                name: "feeLimit",
                                                type: "integer",
                                                value: i,
                                                gte: 0,
                                                lte: 5e9
                                            }, {name: "tokenId", type: "integer", value: t, gte: 0}], u)) {
                                                e.next = 9;
                                                break
                                            }
                                            return e.abrupt("return");
                                        case 9:
                                            return a = g({
                                                tokenId: t,
                                                tokenValue: r,
                                                feeLimit: i
                                            }, a, {callValue: n}), e.prev = 10, e.next = 13, this.mainchain.contract().at(this.mainGatewayAddress);
                                        case 13:
                                            return c = e.sent, e.next = 16, c.depositTRC10(t, r).send(a, s);
                                        case 16:
                                            f = e.sent, u(null, f), e.next = 23;
                                            break;
                                        case 20:
                                            return e.prev = 20, e.t0 = e.catch(10), e.abrupt("return", u(e.t0));
                                        case 23:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this, [[10, 20]])
                            })), function (e, t, r, n) {
                                return S.apply(this, arguments)
                            })
                        }, {
                            key: "depositTrc", value: (k = u()(o.a.mark(function e(t, r, n, i, a) {
                                var s, u, c, f, d, h, l = arguments;
                                return o.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (s = l.length > 5 && void 0 !== l[5] ? l[5] : {}, u = l.length > 6 && void 0 !== l[6] ? l[6] : this.mainchain.defaultPrivateKey, c = l.length > 7 && void 0 !== l[7] && l[7], this.utils.isFunction(u) && (c = u, u = this.mainchain.defaultPrivateKey), this.utils.isFunction(s) && (c = s, s = {}), c) {
                                                e.next = 7;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this.depositTrc, t, r, n, i, a, s, u));
                                        case 7:
                                            if (!this.validator.notValid([{
                                                name: "functionSelector",
                                                type: "not-empty-string",
                                                value: t
                                            }, {name: "num", type: "integer", value: r, gte: 0}, {
                                                name: "fee",
                                                type: "integer",
                                                value: n,
                                                gte: 0
                                            }, {
                                                name: "feeLimit",
                                                type: "integer",
                                                value: i,
                                                gte: 0,
                                                lte: 5e9
                                            }, {name: "contractAddress", type: "address", value: a}], c)) {
                                                e.next = 9;
                                                break
                                            }
                                            return e.abrupt("return");
                                        case 9:
                                            if (s = g({feeLimit: i}, s, {
                                                callValue: n,
                                                tokenId: "",
                                                tokenValue: 0
                                            }), e.prev = 10, f = null, "approve" !== t) {
                                                e.next = 21;
                                                break
                                            }
                                            return e.next = 15, this.mainchain.contract().at(a);
                                        case 15:
                                            return d = e.sent, e.next = 18, d.approve(this.mainGatewayAddress, r).send(s, u);
                                        case 18:
                                            f = e.sent, e.next = 44;
                                            break;
                                        case 21:
                                            return e.next = 23, this.mainchain.contract().at(this.mainGatewayAddress);
                                        case 23:
                                            h = e.sent, e.t0 = t, e.next = "depositTRC20" === e.t0 ? 27 : "depositTRC721" === e.t0 ? 31 : "retryDeposit" === e.t0 ? 35 : "retryMapping" === e.t0 ? 39 : 43;
                                            break;
                                        case 27:
                                            return e.next = 29, h.depositTRC20(a, r).send(s, u);
                                        case 29:
                                            return f = e.sent, e.abrupt("break", 44);
                                        case 31:
                                            return e.next = 33, h.depositTRC721(a, r).send(s, u);
                                        case 33:
                                            return f = e.sent, e.abrupt("break", 44);
                                        case 35:
                                            return e.next = 37, h.retryDeposit(r).send(s, u);
                                        case 37:
                                            return f = e.sent, e.abrupt("break", 44);
                                        case 39:
                                            return e.next = 41, h.retryMapping(r).send(s, u);
                                        case 41:
                                            return f = e.sent, e.abrupt("break", 44);
                                        case 43:
                                            return e.abrupt("break", 44);
                                        case 44:
                                            c(null, f), e.next = 50;
                                            break;
                                        case 47:
                                            return e.prev = 47, e.t1 = e.catch(10), e.abrupt("return", c(e.t1));
                                        case 50:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this, [[10, 47]])
                            })), function (e, t, r, n, i) {
                                return k.apply(this, arguments)
                            })
                        }, {
                            key: "approveTrc20", value: (A = u()(o.a.mark(function e(t, r, n) {
                                var i, a, s, u, c = arguments;
                                return o.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            return i = c.length > 3 && void 0 !== c[3] ? c[3] : {}, a = c.length > 4 && void 0 !== c[4] ? c[4] : this.mainchain.defaultPrivateKey, s = c.length > 5 && void 0 !== c[5] && c[5], u = "approve", e.abrupt("return", this.depositTrc(u, t, 0, r, n, i, a, s));
                                        case 5:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function (e, t, r) {
                                return A.apply(this, arguments)
                            })
                        }, {
                            key: "approveTrc721", value: (_ = u()(o.a.mark(function e(t, r, n) {
                                var i, a, s, u, c = arguments;
                                return o.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            return i = c.length > 3 && void 0 !== c[3] ? c[3] : {}, a = c.length > 4 && void 0 !== c[4] ? c[4] : this.mainchain.defaultPrivateKey, s = c.length > 5 && void 0 !== c[5] && c[5], u = "approve", e.abrupt("return", this.depositTrc(u, t, 0, r, n, i, a, s));
                                        case 5:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function (e, t, r) {
                                return _.apply(this, arguments)
                            })
                        }, {
                            key: "depositTrc20", value: (x = u()(o.a.mark(function e(t, r, n, i) {
                                var a, s, u, c, f = arguments;
                                return o.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            return a = f.length > 4 && void 0 !== f[4] ? f[4] : {}, s = f.length > 5 && void 0 !== f[5] ? f[5] : this.mainchain.defaultPrivateKey, u = f.length > 6 && void 0 !== f[6] && f[6], c = "depositTRC20", e.abrupt("return", this.depositTrc(c, t, r, n, i, a, s, u));
                                        case 5:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function (e, t, r, n) {
                                return x.apply(this, arguments)
                            })
                        }, {
                            key: "depositTrc721", value: (w = u()(o.a.mark(function e(t, r, n, i) {
                                var a, s, u, c, f = arguments;
                                return o.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            return a = f.length > 4 && void 0 !== f[4] ? f[4] : {}, s = f.length > 5 && void 0 !== f[5] ? f[5] : this.mainchain.defaultPrivateKey, u = f.length > 6 && void 0 !== f[6] && f[6], c = "depositTRC721", e.abrupt("return", this.depositTrc(c, t, r, n, i, a, s, u));
                                        case 5:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function (e, t, r, n) {
                                return w.apply(this, arguments)
                            })
                        }, {
                            key: "mappingTrc", value: (y = u()(o.a.mark(function e(t, r, n, i) {
                                var a, s, u, c, f, d = arguments;
                                return o.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (a = d.length > 4 && void 0 !== d[4] ? d[4] : {}, s = d.length > 5 && void 0 !== d[5] ? d[5] : this.mainchain.defaultPrivateKey, u = d.length > 6 ? d[6] : void 0, this.utils.isFunction(s) && (u = s, s = this.mainchain.defaultPrivateKey), this.utils.isFunction(a) && (u = a, a = {}), u) {
                                                e.next = 7;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this.mappingTrc, t, r, n, i, a, s));
                                        case 7:
                                            if (!this.validator.notValid([{
                                                name: "trxHash",
                                                type: "not-empty-string",
                                                value: t
                                            }, {
                                                name: "mappingFee",
                                                type: "integer",
                                                value: r,
                                                gte: 0
                                            }, {name: "feeLimit", type: "integer", value: n, gte: 0, lte: 5e9}], u)) {
                                                e.next = 9;
                                                break
                                            }
                                            return e.abrupt("return");
                                        case 9:
                                            return t = t.startsWith("0x") ? t : "0x" + t, a = g({feeLimit: n}, a, {callValue: r}), e.prev = 11, e.next = 14, this.mainchain.contract().at(this.mainGatewayAddress);
                                        case 14:
                                            if (c = e.sent, f = null, "mappingTRC20" !== i) {
                                                e.next = 22;
                                                break
                                            }
                                            return e.next = 19, c.mappingTRC20(t).send(a, s);
                                        case 19:
                                            f = e.sent, e.next = 29;
                                            break;
                                        case 22:
                                            if ("mappingTRC721" !== i) {
                                                e.next = 28;
                                                break
                                            }
                                            return e.next = 25, c.mappingTRC721(t).send(a, s);
                                        case 25:
                                            f = e.sent, e.next = 29;
                                            break;
                                        case 28:
                                            u(new Error("type must be trc20 or trc721"));
                                        case 29:
                                            u(null, f), e.next = 35;
                                            break;
                                        case 32:
                                            return e.prev = 32, e.t0 = e.catch(11), e.abrupt("return", u(e.t0));
                                        case 35:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this, [[11, 32]])
                            })), function (e, t, r, n) {
                                return y.apply(this, arguments)
                            })
                        }, {
                            key: "mappingTrc20", value: (m = u()(o.a.mark(function e(t, r, n) {
                                var i, a, s, u, c = arguments;
                                return o.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            return i = c.length > 3 && void 0 !== c[3] ? c[3] : {}, a = c.length > 4 && void 0 !== c[4] ? c[4] : this.mainchain.defaultPrivateKey, s = c.length > 5 && void 0 !== c[5] && c[5], u = "mappingTRC20", e.abrupt("return", this.mappingTrc(t, r, n, u, i, a, s));
                                        case 5:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function (e, t, r) {
                                return m.apply(this, arguments)
                            })
                        }, {
                            key: "mappingTrc721", value: (v = u()(o.a.mark(function e(t, r, n) {
                                var i, a, s, u, c = arguments;
                                return o.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            return i = c.length > 3 && void 0 !== c[3] ? c[3] : {}, a = c.length > 4 && void 0 !== c[4] ? c[4] : this.mainchain.defaultPrivateKey, s = c.length > 5 && void 0 !== c[5] && c[5], u = "mappingTRC721", e.abrupt("return", this.mappingTrc(t, r, n, u, i, a, s));
                                        case 5:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function (e, t, r) {
                                return v.apply(this, arguments)
                            })
                        }, {
                            key: "withdrawTrx", value: (l = u()(o.a.mark(function e(t, r, n) {
                                var i, a, s, u, c, f = arguments;
                                return o.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (i = f.length > 3 && void 0 !== f[3] ? f[3] : {}, a = f.length > 4 && void 0 !== f[4] ? f[4] : this.mainchain.defaultPrivateKey, s = f.length > 5 && void 0 !== f[5] && f[5], this.utils.isFunction(a) && (s = a, a = this.mainchain.defaultPrivateKey), this.utils.isFunction(i) && (s = i, i = {}), s) {
                                                e.next = 7;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this.withdrawTrx, t, r, n, i, a));
                                        case 7:
                                            if (!this.validator.notValid([{
                                                name: "callValue",
                                                type: "integer",
                                                value: t,
                                                gte: 0
                                            }, {
                                                name: "withdrawFee",
                                                type: "integer",
                                                value: r,
                                                gte: 0
                                            }, {name: "feeLimit", type: "integer", value: n, gte: 0, lte: 1e9}], s)) {
                                                e.next = 9;
                                                break
                                            }
                                            return e.abrupt("return");
                                        case 9:
                                            return i = g({
                                                callValue: Number(t) + Number(r),
                                                feeLimit: n
                                            }, i), e.prev = 10, e.next = 13, this.sidechain.contract().at(this.sideGatewayAddress);
                                        case 13:
                                            return u = e.sent, e.next = 16, u.withdrawTRX().send(i, a);
                                        case 16:
                                            return c = e.sent, e.abrupt("return", s(null, c));
                                        case 20:
                                            return e.prev = 20, e.t0 = e.catch(10), e.abrupt("return", s(e.t0));
                                        case 23:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this, [[10, 20]])
                            })), function (e, t, r) {
                                return l.apply(this, arguments)
                            })
                        }, {
                            key: "withdrawTrc10", value: (d = u()(o.a.mark(function e(t, r, n, i) {
                                var a, s, u, c, f, d = arguments;
                                return o.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (a = d.length > 4 && void 0 !== d[4] ? d[4] : {}, s = d.length > 5 && void 0 !== d[5] ? d[5] : this.mainchain.defaultPrivateKey, u = d.length > 6 && void 0 !== d[6] && d[6], this.utils.isFunction(s) && (u = s, s = this.mainchain.defaultPrivateKey), this.utils.isFunction(a) && (u = a, a = {}), u) {
                                                e.next = 7;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this.withdrawTrc10, t, r, n, i, a, s));
                                        case 7:
                                            if (!this.validator.notValid([{
                                                name: "tokenId",
                                                type: "integer",
                                                value: t,
                                                gte: 0
                                            }, {
                                                name: "tokenValue",
                                                type: "integer",
                                                value: r,
                                                gte: 0
                                            }, {
                                                name: "withdrawFee",
                                                type: "integer",
                                                value: n,
                                                gte: 0
                                            }, {name: "feeLimit", type: "integer", value: i, gte: 0, lte: 1e9}], u)) {
                                                e.next = 9;
                                                break
                                            }
                                            return e.abrupt("return");
                                        case 9:
                                            return a = g({
                                                tokenValue: r,
                                                tokenId: t,
                                                callValue: n,
                                                feeLimit: i
                                            }, a), e.prev = 10, e.next = 13, this.sidechain.contract().at(this.sideGatewayAddress);
                                        case 13:
                                            return c = e.sent, e.next = 16, c.withdrawTRC10(t, r).send(a, s);
                                        case 16:
                                            return f = e.sent, e.abrupt("return", u(null, f));
                                        case 20:
                                            return e.prev = 20, e.t0 = e.catch(10), e.abrupt("return", u(e.t0));
                                        case 23:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this, [[10, 20]])
                            })), function (e, t, r, n) {
                                return d.apply(this, arguments)
                            })
                        }, {
                            key: "withdrawTrc", value: (c = u()(o.a.mark(function e(t, r, n, i, a) {
                                var s, c, f, d, h, l, p, b, v, m, y = this, w = arguments;
                                return o.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (s = w.length > 5 && void 0 !== w[5] ? w[5] : {}, c = w.length > 6 && void 0 !== w[6] ? w[6] : this.mainchain.defaultPrivateKey, f = w.length > 7 && void 0 !== w[7] && w[7], this.utils.isFunction(c) && (f = c, c = this.mainchain.defaultPrivateKey), this.utils.isFunction(s) && (f = s, s = {}), f) {
                                                e.next = 7;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this.withdrawTrc, t, r, n, i, a, s, c));
                                        case 7:
                                            if (!this.validator.notValid([{
                                                name: "functionSelector",
                                                type: "not-empty-string",
                                                value: t
                                            }, {
                                                name: "numOrId",
                                                type: "integer",
                                                value: r,
                                                gte: 0
                                            }, {
                                                name: "withdrawFee",
                                                type: "integer",
                                                value: n,
                                                gte: 0
                                            }, {
                                                name: "feeLimit",
                                                type: "integer",
                                                value: i,
                                                gte: 0,
                                                lte: 1e9
                                            }, {name: "contractAddress", type: "address", value: a}], f)) {
                                                e.next = 9;
                                                break
                                            }
                                            return e.abrupt("return");
                                        case 9:
                                            return s = g({feeLimit: i}, s, {callValue: n}), d = [{
                                                type: "uint256",
                                                value: r
                                            }], e.prev = 11, h = c ? this.sidechain.address.fromPrivateKey(c) : this.sidechain.defaultAddress.base58, e.next = 15, this.sidechain.transactionBuilder.triggerSmartContract(a, t, s, d, this.sidechain.address.toHex(h));
                                        case 15:
                                            if ((l = e.sent).result && l.result.result) {
                                                e.next = 18;
                                                break
                                            }
                                            return e.abrupt("return", f("Unknown error: " + JSON.stringify(l.transaction, null, 2)));
                                        case 18:
                                            return e.next = 20, this.sidechain.trx.sign(l.transaction, c);
                                        case 20:
                                            if ((p = e.sent).signature) {
                                                e.next = 25;
                                                break
                                            }
                                            if (c) {
                                                e.next = 24;
                                                break
                                            }
                                            return e.abrupt("return", f("Transaction was not signed properly"));
                                        case 24:
                                            return e.abrupt("return", f("Invalid private key provided"));
                                        case 25:
                                            return e.next = 27, this.sidechain.trx.sendRawTransaction(p);
                                        case 27:
                                            if (!(b = e.sent).code) {
                                                e.next = 32;
                                                break
                                            }
                                            return v = {
                                                error: b.code,
                                                message: b.code
                                            }, b.message && (v.message = this.sidechain.toUtf8(b.message)), e.abrupt("return", f(v));
                                        case 32:
                                            if (s.shouldPollResponse) {
                                                e.next = 34;
                                                break
                                            }
                                            return e.abrupt("return", f(null, p.txID));
                                        case 34:
                                            (m = function () {
                                                var e = u()(o.a.mark(function e() {
                                                    var t, r, n, i = arguments;
                                                    return o.a.wrap(function (e) {
                                                        for (; ;) switch (e.prev = e.next) {
                                                            case 0:
                                                                if (20 != (t = i.length > 0 && void 0 !== i[0] ? i[0] : 0)) {
                                                                    e.next = 3;
                                                                    break
                                                                }
                                                                return e.abrupt("return", f({
                                                                    error: "Cannot find result in solidity node",
                                                                    transaction: p
                                                                }));
                                                            case 3:
                                                                return e.next = 5, y.sidechain.trx.getTransactionInfo(p.txID);
                                                            case 5:
                                                                if (r = e.sent, Object.keys(r).length) {
                                                                    e.next = 8;
                                                                    break
                                                                }
                                                                return e.abrupt("return", setTimeout(function () {
                                                                    m(t + 1)
                                                                }, 3e3));
                                                            case 8:
                                                                if (!r.result || "FAILED" != r.result) {
                                                                    e.next = 10;
                                                                    break
                                                                }
                                                                return e.abrupt("return", f({
                                                                    error: y.sidechain.toUtf8(r.resMessage),
                                                                    transaction: p,
                                                                    output: r
                                                                }));
                                                            case 10:
                                                                if (y.utils.hasProperty(r, "contractResult")) {
                                                                    e.next = 12;
                                                                    break
                                                                }
                                                                return e.abrupt("return", f({
                                                                    error: "Failed to execute: " + JSON.stringify(r, null, 2),
                                                                    transaction: p,
                                                                    output: r
                                                                }));
                                                            case 12:
                                                                if (!s.rawResponse) {
                                                                    e.next = 14;
                                                                    break
                                                                }
                                                                return e.abrupt("return", f(null, r));
                                                            case 14:
                                                                return 1 === (n = decodeOutput(y.outputs, "0x" + r.contractResult[0])).length && (n = n[0]), e.abrupt("return", f(null, n));
                                                            case 17:
                                                            case"end":
                                                                return e.stop()
                                                        }
                                                    }, e)
                                                }));
                                                return function () {
                                                    return e.apply(this, arguments)
                                                }
                                            }())(), e.next = 41;
                                            break;
                                        case 38:
                                            return e.prev = 38, e.t0 = e.catch(11), e.abrupt("return", f(e.t0));
                                        case 41:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this, [[11, 38]])
                            })), function (e, t, r, n, i) {
                                return c.apply(this, arguments)
                            })
                        }, {
                            key: "withdrawTrc20", value: (s = u()(o.a.mark(function e(t, r, n, i, a) {
                                var s, u, c, f = arguments;
                                return o.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            return s = f.length > 5 && void 0 !== f[5] ? f[5] : this.mainchain.defaultPrivateKey, u = f.length > 6 && void 0 !== f[6] && f[6], c = "withdrawal(uint256)", e.abrupt("return", this.withdrawTrc(c, t, r, n, i, a, s, u));
                                        case 4:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function (e, t, r, n, i) {
                                return s.apply(this, arguments)
                            })
                        }, {
                            key: "withdrawTrc721", value: (a = u()(o.a.mark(function e(t, r, n, i, a) {
                                var s, u, c, f = arguments;
                                return o.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            return s = f.length > 5 && void 0 !== f[5] ? f[5] : this.mainchain.defaultPrivateKey, u = f.length > 6 && void 0 !== f[6] && f[6], c = "withdrawal(uint256)", e.abrupt("return", this.withdrawTrc(c, t, r, n, i, a, s, u));
                                        case 4:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function (e, t, r, n, i) {
                                return a.apply(this, arguments)
                            })
                        }, {
                            key: "injectFund", value: (i = u()(o.a.mark(function e(t, r, n) {
                                var i, a, s, u, c, f, d, h, l = arguments;
                                return o.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            if (i = l.length > 3 && void 0 !== l[3] ? l[3] : this.mainchain.defaultPrivateKey, a = l.length > 4 && void 0 !== l[4] && l[4], this.utils.isFunction(i) && (a = i, i = this.mainchain.defaultPrivateKey), this.utils.isFunction(n) && (a = n, n = {}), a) {
                                                e.next = 6;
                                                break
                                            }
                                            return e.abrupt("return", this.injectPromise(this.injectFund, t, r, n, i));
                                        case 6:
                                            if (!this.validator.notValid([{
                                                name: "num",
                                                type: "integer",
                                                value: t,
                                                gte: 0
                                            }, {name: "feeLimit", type: "integer", value: r, gte: 0, lte: 1e9}], a)) {
                                                e.next = 8;
                                                break
                                            }
                                            return e.abrupt("return");
                                        case 8:
                                            return e.prev = 8, s = this.sidechain.address.fromPrivateKey(i), u = this.sidechain.address.toHex(s), e.next = 13, this.sidechain.fullNode.request("/wallet/fundinject", {
                                                owner_address: u,
                                                amount: t
                                            }, "post");
                                        case 13:
                                            return c = e.sent, e.next = 16, this.sidechain.trx.sign(c, i);
                                        case 16:
                                            if ((f = e.sent).signature) {
                                                e.next = 21;
                                                break
                                            }
                                            if (i) {
                                                e.next = 20;
                                                break
                                            }
                                            return e.abrupt("return", a("Transaction was not signed properly"));
                                        case 20:
                                            return e.abrupt("return", a("Invalid private key provided"));
                                        case 21:
                                            return e.next = 23, this.sidechain.trx.sendRawTransaction(f);
                                        case 23:
                                            if (!(d = e.sent).code) {
                                                e.next = 28;
                                                break
                                            }
                                            return h = {
                                                error: d.code,
                                                message: d.code
                                            }, d.message && (h.message = this.mainchain.toUtf8(d.message)), e.abrupt("return", a(h));
                                        case 28:
                                            return e.abrupt("return", a(null, f.txID));
                                        case 31:
                                            return e.prev = 31, e.t0 = e.catch(8), e.abrupt("return", a(e.t0));
                                        case 34:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this, [[8, 31]])
                            })), function (e, t, r) {
                                return i.apply(this, arguments)
                            })
                        }, {
                            key: "retryWithdraw", value: (n = u()(o.a.mark(function e(t, r, n) {
                                var i, a, s, u, c = arguments;
                                return o.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            return i = c.length > 3 && void 0 !== c[3] ? c[3] : {}, a = c.length > 4 && void 0 !== c[4] ? c[4] : this.sidechain.defaultPrivateKey, s = c.length > 5 && void 0 !== c[5] && c[5], u = "retryWithdraw(uint256)", e.abrupt("return", this.withdrawTrc(u, t, r, n, this.sideGatewayAddress, i, a, s));
                                        case 5:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function (e, t, r) {
                                return n.apply(this, arguments)
                            })
                        }, {
                            key: "retryDeposit", value: (r = u()(o.a.mark(function e(t, r, n) {
                                var i, a, s, u, c = arguments;
                                return o.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            return i = c.length > 3 && void 0 !== c[3] ? c[3] : {}, a = c.length > 4 && void 0 !== c[4] ? c[4] : this.mainchain.defaultPrivateKey, s = c.length > 5 && void 0 !== c[5] && c[5], u = "retryDeposit", e.abrupt("return", this.depositTrc(u, t, r, n, this.mainGatewayAddress, i, a, s));
                                        case 5:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function (e, t, n) {
                                return r.apply(this, arguments)
                            })
                        }, {
                            key: "retryMapping", value: (t = u()(o.a.mark(function e(t, r, n) {
                                var i, a, s, u, c = arguments;
                                return o.a.wrap(function (e) {
                                    for (; ;) switch (e.prev = e.next) {
                                        case 0:
                                            return i = c.length > 3 && void 0 !== c[3] ? c[3] : {}, a = c.length > 4 && void 0 !== c[4] ? c[4] : this.mainchain.defaultPrivateKey, s = c.length > 5 && void 0 !== c[5] && c[5], u = "retryMapping", e.abrupt("return", this.depositTrc(u, t, r, n, this.mainGatewayAddress, i, a, s));
                                        case 5:
                                        case"end":
                                            return e.stop()
                                    }
                                }, e, this)
                            })), function (e, r, n) {
                                return t.apply(this, arguments)
                            })
                        }]), e
                    }()
                }, function (e, t, r) {
                    (function (e) {
                        /*!
       * The buffer module from node.js, for the browser.
       *
       * @author   Feross Aboukhadijeh <feross@feross.org> <http://feross.org>
       * @license  MIT
       */
                        var n = r(75), i = r(76), a = r(77);

                        function o() {
                            return u.TYPED_ARRAY_SUPPORT ? 2147483647 : 1073741823
                        }

                        function s(e, t) {
                            if (o() < t) throw new RangeError("Invalid typed array length");
                            return u.TYPED_ARRAY_SUPPORT ? (e = new Uint8Array(t)).__proto__ = u.prototype : (null === e && (e = new u(t)), e.length = t), e
                        }

                        function u(e, t, r) {
                            if (!(u.TYPED_ARRAY_SUPPORT || this instanceof u)) return new u(e, t, r);
                            if ("number" == typeof e) {
                                if ("string" == typeof t) throw new Error("If encoding is specified then the first argument must be a string");
                                return d(this, e)
                            }
                            return c(this, e, t, r)
                        }

                        function c(e, t, r, n) {
                            if ("number" == typeof t) throw new TypeError('"value" argument must not be a number');
                            return "undefined" != typeof ArrayBuffer && t instanceof ArrayBuffer ? function (e, t, r, n) {
                                if (t.byteLength, r < 0 || t.byteLength < r) throw new RangeError("'offset' is out of bounds");
                                if (t.byteLength < r + (n || 0)) throw new RangeError("'length' is out of bounds");
                                return t = void 0 === r && void 0 === n ? new Uint8Array(t) : void 0 === n ? new Uint8Array(t, r) : new Uint8Array(t, r, n), u.TYPED_ARRAY_SUPPORT ? (e = t).__proto__ = u.prototype : e = h(e, t), e
                            }(e, t, r, n) : "string" == typeof t ? function (e, t, r) {
                                if ("string" == typeof r && "" !== r || (r = "utf8"), !u.isEncoding(r)) throw new TypeError('"encoding" must be a valid string encoding');
                                var n = 0 | p(t, r), i = (e = s(e, n)).write(t, r);
                                return i !== n && (e = e.slice(0, i)), e
                            }(e, t, r) : function (e, t) {
                                if (u.isBuffer(t)) {
                                    var r = 0 | l(t.length);
                                    return 0 === (e = s(e, r)).length ? e : (t.copy(e, 0, 0, r), e)
                                }
                                if (t) {
                                    if ("undefined" != typeof ArrayBuffer && t.buffer instanceof ArrayBuffer || "length" in t) return "number" != typeof t.length || (n = t.length) != n ? s(e, 0) : h(e, t);
                                    if ("Buffer" === t.type && a(t.data)) return h(e, t.data)
                                }
                                var n;
                                throw new TypeError("First argument must be a string, Buffer, ArrayBuffer, Array, or array-like object.")
                            }(e, t)
                        }

                        function f(e) {
                            if ("number" != typeof e) throw new TypeError('"size" argument must be a number');
                            if (e < 0) throw new RangeError('"size" argument must not be negative')
                        }

                        function d(e, t) {
                            if (f(t), e = s(e, t < 0 ? 0 : 0 | l(t)), !u.TYPED_ARRAY_SUPPORT) for (var r = 0; r < t; ++r) e[r] = 0;
                            return e
                        }

                        function h(e, t) {
                            var r = t.length < 0 ? 0 : 0 | l(t.length);
                            e = s(e, r);
                            for (var n = 0; n < r; n += 1) e[n] = 255 & t[n];
                            return e
                        }

                        function l(e) {
                            if (e >= o()) throw new RangeError("Attempt to allocate Buffer larger than maximum size: 0x" + o().toString(16) + " bytes");
                            return 0 | e
                        }

                        function p(e, t) {
                            if (u.isBuffer(e)) return e.length;
                            if ("undefined" != typeof ArrayBuffer && "function" == typeof ArrayBuffer.isView && (ArrayBuffer.isView(e) || e instanceof ArrayBuffer)) return e.byteLength;
                            "string" != typeof e && (e = "" + e);
                            var r = e.length;
                            if (0 === r) return 0;
                            for (var n = !1; ;) switch (t) {
                                case"ascii":
                                case"latin1":
                                case"binary":
                                    return r;
                                case"utf8":
                                case"utf-8":
                                case void 0:
                                    return U(e).length;
                                case"ucs2":
                                case"ucs-2":
                                case"utf16le":
                                case"utf-16le":
                                    return 2 * r;
                                case"hex":
                                    return r >>> 1;
                                case"base64":
                                    return D(e).length;
                                default:
                                    if (n) return U(e).length;
                                    t = ("" + t).toLowerCase(), n = !0
                            }
                        }

                        function b(e, t, r) {
                            var n = e[t];
                            e[t] = e[r], e[r] = n
                        }

                        function v(e, t, r, n, i) {
                            if (0 === e.length) return -1;
                            if ("string" == typeof r ? (n = r, r = 0) : r > 2147483647 ? r = 2147483647 : r < -2147483648 && (r = -2147483648), r = +r, isNaN(r) && (r = i ? 0 : e.length - 1), r < 0 && (r = e.length + r), r >= e.length) {
                                if (i) return -1;
                                r = e.length - 1
                            } else if (r < 0) {
                                if (!i) return -1;
                                r = 0
                            }
                            if ("string" == typeof t && (t = u.from(t, n)), u.isBuffer(t)) return 0 === t.length ? -1 : g(e, t, r, n, i);
                            if ("number" == typeof t) return t &= 255, u.TYPED_ARRAY_SUPPORT && "function" == typeof Uint8Array.prototype.indexOf ? i ? Uint8Array.prototype.indexOf.call(e, t, r) : Uint8Array.prototype.lastIndexOf.call(e, t, r) : g(e, [t], r, n, i);
                            throw new TypeError("val must be string, number or Buffer")
                        }

                        function g(e, t, r, n, i) {
                            var a, o = 1, s = e.length, u = t.length;
                            if (void 0 !== n && ("ucs2" === (n = String(n).toLowerCase()) || "ucs-2" === n || "utf16le" === n || "utf-16le" === n)) {
                                if (e.length < 2 || t.length < 2) return -1;
                                o = 2, s /= 2, u /= 2, r /= 2
                            }

                            function c(e, t) {
                                return 1 === o ? e[t] : e.readUInt16BE(t * o)
                            }

                            if (i) {
                                var f = -1;
                                for (a = r; a < s; a++) if (c(e, a) === c(t, -1 === f ? 0 : a - f)) {
                                    if (-1 === f && (f = a), a - f + 1 === u) return f * o
                                } else -1 !== f && (a -= a - f), f = -1
                            } else for (r + u > s && (r = s - u), a = r; a >= 0; a--) {
                                for (var d = !0, h = 0; h < u; h++) if (c(e, a + h) !== c(t, h)) {
                                    d = !1;
                                    break
                                }
                                if (d) return a
                            }
                            return -1
                        }

                        function m(e, t, r, n) {
                            r = Number(r) || 0;
                            var i = e.length - r;
                            n ? (n = Number(n)) > i && (n = i) : n = i;
                            var a = t.length;
                            if (a % 2 != 0) throw new TypeError("Invalid hex string");
                            n > a / 2 && (n = a / 2);
                            for (var o = 0; o < n; ++o) {
                                var s = parseInt(t.substr(2 * o, 2), 16);
                                if (isNaN(s)) return o;
                                e[r + o] = s
                            }
                            return o
                        }

                        function y(e, t, r, n) {
                            return q(U(t, e.length - r), e, r, n)
                        }

                        function w(e, t, r, n) {
                            return q(function (e) {
                                for (var t = [], r = 0; r < e.length; ++r) t.push(255 & e.charCodeAt(r));
                                return t
                            }(t), e, r, n)
                        }

                        function x(e, t, r, n) {
                            return w(e, t, r, n)
                        }

                        function _(e, t, r, n) {
                            return q(D(t), e, r, n)
                        }

                        function A(e, t, r, n) {
                            return q(function (e, t) {
                                for (var r, n, i, a = [], o = 0; o < e.length && !((t -= 2) < 0); ++o) n = (r = e.charCodeAt(o)) >> 8, i = r % 256, a.push(i), a.push(n);
                                return a
                            }(t, e.length - r), e, r, n)
                        }

                        function k(e, t, r) {
                            return 0 === t && r === e.length ? n.fromByteArray(e) : n.fromByteArray(e.slice(t, r))
                        }

                        function S(e, t, r) {
                            r = Math.min(e.length, r);
                            for (var n = [], i = t; i < r;) {
                                var a, o, s, u, c = e[i], f = null, d = c > 239 ? 4 : c > 223 ? 3 : c > 191 ? 2 : 1;
                                if (i + d <= r) switch (d) {
                                    case 1:
                                        c < 128 && (f = c);
                                        break;
                                    case 2:
                                        128 == (192 & (a = e[i + 1])) && (u = (31 & c) << 6 | 63 & a) > 127 && (f = u);
                                        break;
                                    case 3:
                                        a = e[i + 1], o = e[i + 2], 128 == (192 & a) && 128 == (192 & o) && (u = (15 & c) << 12 | (63 & a) << 6 | 63 & o) > 2047 && (u < 55296 || u > 57343) && (f = u);
                                        break;
                                    case 4:
                                        a = e[i + 1], o = e[i + 2], s = e[i + 3], 128 == (192 & a) && 128 == (192 & o) && 128 == (192 & s) && (u = (15 & c) << 18 | (63 & a) << 12 | (63 & o) << 6 | 63 & s) > 65535 && u < 1114112 && (f = u)
                                }
                                null === f ? (f = 65533, d = 1) : f > 65535 && (f -= 65536, n.push(f >>> 10 & 1023 | 55296), f = 56320 | 1023 & f), n.push(f), i += d
                            }
                            return function (e) {
                                var t = e.length;
                                if (t <= M) return String.fromCharCode.apply(String, e);
                                for (var r = "", n = 0; n < t;) r += String.fromCharCode.apply(String, e.slice(n, n += M));
                                return r
                            }(n)
                        }

                        t.Buffer = u, t.SlowBuffer = function (e) {
                            return +e != e && (e = 0), u.alloc(+e)
                        }, t.INSPECT_MAX_BYTES = 50, u.TYPED_ARRAY_SUPPORT = void 0 !== e.TYPED_ARRAY_SUPPORT ? e.TYPED_ARRAY_SUPPORT : function () {
                            try {
                                var e = new Uint8Array(1);
                                return e.__proto__ = {
                                    __proto__: Uint8Array.prototype, foo: function () {
                                        return 42
                                    }
                                }, 42 === e.foo() && "function" == typeof e.subarray && 0 === e.subarray(1, 1).byteLength
                            } catch (e) {
                                return !1
                            }
                        }(), t.kMaxLength = o(), u.poolSize = 8192, u._augment = function (e) {
                            return e.__proto__ = u.prototype, e
                        }, u.from = function (e, t, r) {
                            return c(null, e, t, r)
                        }, u.TYPED_ARRAY_SUPPORT && (u.prototype.__proto__ = Uint8Array.prototype, u.__proto__ = Uint8Array, "undefined" != typeof Symbol && Symbol.species && u[Symbol.species] === u && Object.defineProperty(u, Symbol.species, {
                            value: null,
                            configurable: !0
                        })), u.alloc = function (e, t, r) {
                            return function (e, t, r, n) {
                                return f(t), t <= 0 ? s(e, t) : void 0 !== r ? "string" == typeof n ? s(e, t).fill(r, n) : s(e, t).fill(r) : s(e, t)
                            }(null, e, t, r)
                        }, u.allocUnsafe = function (e) {
                            return d(null, e)
                        }, u.allocUnsafeSlow = function (e) {
                            return d(null, e)
                        }, u.isBuffer = function (e) {
                            return !(null == e || !e._isBuffer)
                        }, u.compare = function (e, t) {
                            if (!u.isBuffer(e) || !u.isBuffer(t)) throw new TypeError("Arguments must be Buffers");
                            if (e === t) return 0;
                            for (var r = e.length, n = t.length, i = 0, a = Math.min(r, n); i < a; ++i) if (e[i] !== t[i]) {
                                r = e[i], n = t[i];
                                break
                            }
                            return r < n ? -1 : n < r ? 1 : 0
                        }, u.isEncoding = function (e) {
                            switch (String(e).toLowerCase()) {
                                case"hex":
                                case"utf8":
                                case"utf-8":
                                case"ascii":
                                case"latin1":
                                case"binary":
                                case"base64":
                                case"ucs2":
                                case"ucs-2":
                                case"utf16le":
                                case"utf-16le":
                                    return !0;
                                default:
                                    return !1
                            }
                        }, u.concat = function (e, t) {
                            if (!a(e)) throw new TypeError('"list" argument must be an Array of Buffers');
                            if (0 === e.length) return u.alloc(0);
                            var r;
                            if (void 0 === t) for (t = 0, r = 0; r < e.length; ++r) t += e[r].length;
                            var n = u.allocUnsafe(t), i = 0;
                            for (r = 0; r < e.length; ++r) {
                                var o = e[r];
                                if (!u.isBuffer(o)) throw new TypeError('"list" argument must be an Array of Buffers');
                                o.copy(n, i), i += o.length
                            }
                            return n
                        }, u.byteLength = p, u.prototype._isBuffer = !0, u.prototype.swap16 = function () {
                            var e = this.length;
                            if (e % 2 != 0) throw new RangeError("Buffer size must be a multiple of 16-bits");
                            for (var t = 0; t < e; t += 2) b(this, t, t + 1);
                            return this
                        }, u.prototype.swap32 = function () {
                            var e = this.length;
                            if (e % 4 != 0) throw new RangeError("Buffer size must be a multiple of 32-bits");
                            for (var t = 0; t < e; t += 4) b(this, t, t + 3), b(this, t + 1, t + 2);
                            return this
                        }, u.prototype.swap64 = function () {
                            var e = this.length;
                            if (e % 8 != 0) throw new RangeError("Buffer size must be a multiple of 64-bits");
                            for (var t = 0; t < e; t += 8) b(this, t, t + 7), b(this, t + 1, t + 6), b(this, t + 2, t + 5), b(this, t + 3, t + 4);
                            return this
                        }, u.prototype.toString = function () {
                            var e = 0 | this.length;
                            return 0 === e ? "" : 0 === arguments.length ? S(this, 0, e) : function (e, t, r) {
                                var n = !1;
                                if ((void 0 === t || t < 0) && (t = 0), t > this.length) return "";
                                if ((void 0 === r || r > this.length) && (r = this.length), r <= 0) return "";
                                if ((r >>>= 0) <= (t >>>= 0)) return "";
                                for (e || (e = "utf8"); ;) switch (e) {
                                    case"hex":
                                        return N(this, t, r);
                                    case"utf8":
                                    case"utf-8":
                                        return S(this, t, r);
                                    case"ascii":
                                        return I(this, t, r);
                                    case"latin1":
                                    case"binary":
                                        return E(this, t, r);
                                    case"base64":
                                        return k(this, t, r);
                                    case"ucs2":
                                    case"ucs-2":
                                    case"utf16le":
                                    case"utf-16le":
                                        return P(this, t, r);
                                    default:
                                        if (n) throw new TypeError("Unknown encoding: " + e);
                                        e = (e + "").toLowerCase(), n = !0
                                }
                            }.apply(this, arguments)
                        }, u.prototype.equals = function (e) {
                            if (!u.isBuffer(e)) throw new TypeError("Argument must be a Buffer");
                            return this === e || 0 === u.compare(this, e)
                        }, u.prototype.inspect = function () {
                            var e = "", r = t.INSPECT_MAX_BYTES;
                            return this.length > 0 && (e = this.toString("hex", 0, r).match(/.{2}/g).join(" "), this.length > r && (e += " ... ")), "<Buffer " + e + ">"
                        }, u.prototype.compare = function (e, t, r, n, i) {
                            if (!u.isBuffer(e)) throw new TypeError("Argument must be a Buffer");
                            if (void 0 === t && (t = 0), void 0 === r && (r = e ? e.length : 0), void 0 === n && (n = 0), void 0 === i && (i = this.length), t < 0 || r > e.length || n < 0 || i > this.length) throw new RangeError("out of range index");
                            if (n >= i && t >= r) return 0;
                            if (n >= i) return -1;
                            if (t >= r) return 1;
                            if (this === e) return 0;
                            for (var a = (i >>>= 0) - (n >>>= 0), o = (r >>>= 0) - (t >>>= 0), s = Math.min(a, o), c = this.slice(n, i), f = e.slice(t, r), d = 0; d < s; ++d) if (c[d] !== f[d]) {
                                a = c[d], o = f[d];
                                break
                            }
                            return a < o ? -1 : o < a ? 1 : 0
                        }, u.prototype.includes = function (e, t, r) {
                            return -1 !== this.indexOf(e, t, r)
                        }, u.prototype.indexOf = function (e, t, r) {
                            return v(this, e, t, r, !0)
                        }, u.prototype.lastIndexOf = function (e, t, r) {
                            return v(this, e, t, r, !1)
                        }, u.prototype.write = function (e, t, r, n) {
                            if (void 0 === t) n = "utf8", r = this.length, t = 0; else if (void 0 === r && "string" == typeof t) n = t, r = this.length, t = 0; else {
                                if (!isFinite(t)) throw new Error("Buffer.write(string, encoding, offset[, length]) is no longer supported");
                                t |= 0, isFinite(r) ? (r |= 0, void 0 === n && (n = "utf8")) : (n = r, r = void 0)
                            }
                            var i = this.length - t;
                            if ((void 0 === r || r > i) && (r = i), e.length > 0 && (r < 0 || t < 0) || t > this.length) throw new RangeError("Attempt to write outside buffer bounds");
                            n || (n = "utf8");
                            for (var a = !1; ;) switch (n) {
                                case"hex":
                                    return m(this, e, t, r);
                                case"utf8":
                                case"utf-8":
                                    return y(this, e, t, r);
                                case"ascii":
                                    return w(this, e, t, r);
                                case"latin1":
                                case"binary":
                                    return x(this, e, t, r);
                                case"base64":
                                    return _(this, e, t, r);
                                case"ucs2":
                                case"ucs-2":
                                case"utf16le":
                                case"utf-16le":
                                    return A(this, e, t, r);
                                default:
                                    if (a) throw new TypeError("Unknown encoding: " + n);
                                    n = ("" + n).toLowerCase(), a = !0
                            }
                        }, u.prototype.toJSON = function () {
                            return {type: "Buffer", data: Array.prototype.slice.call(this._arr || this, 0)}
                        };
                        var M = 4096;

                        function I(e, t, r) {
                            var n = "";
                            r = Math.min(e.length, r);
                            for (var i = t; i < r; ++i) n += String.fromCharCode(127 & e[i]);
                            return n
                        }

                        function E(e, t, r) {
                            var n = "";
                            r = Math.min(e.length, r);
                            for (var i = t; i < r; ++i) n += String.fromCharCode(e[i]);
                            return n
                        }

                        function N(e, t, r) {
                            var n = e.length;
                            (!t || t < 0) && (t = 0), (!r || r < 0 || r > n) && (r = n);
                            for (var i = "", a = t; a < r; ++a) i += F(e[a]);
                            return i
                        }

                        function P(e, t, r) {
                            for (var n = e.slice(t, r), i = "", a = 0; a < n.length; a += 2) i += String.fromCharCode(n[a] + 256 * n[a + 1]);
                            return i
                        }

                        function T(e, t, r) {
                            if (e % 1 != 0 || e < 0) throw new RangeError("offset is not uint");
                            if (e + t > r) throw new RangeError("Trying to access beyond buffer length")
                        }

                        function O(e, t, r, n, i, a) {
                            if (!u.isBuffer(e)) throw new TypeError('"buffer" argument must be a Buffer instance');
                            if (t > i || t < a) throw new RangeError('"value" argument is out of bounds');
                            if (r + n > e.length) throw new RangeError("Index out of range")
                        }

                        function R(e, t, r, n) {
                            t < 0 && (t = 65535 + t + 1);
                            for (var i = 0, a = Math.min(e.length - r, 2); i < a; ++i) e[r + i] = (t & 255 << 8 * (n ? i : 1 - i)) >>> 8 * (n ? i : 1 - i)
                        }

                        function j(e, t, r, n) {
                            t < 0 && (t = 4294967295 + t + 1);
                            for (var i = 0, a = Math.min(e.length - r, 4); i < a; ++i) e[r + i] = t >>> 8 * (n ? i : 3 - i) & 255
                        }

                        function C(e, t, r, n, i, a) {
                            if (r + n > e.length) throw new RangeError("Index out of range");
                            if (r < 0) throw new RangeError("Index out of range")
                        }

                        function B(e, t, r, n, a) {
                            return a || C(e, 0, r, 4), i.write(e, t, r, n, 23, 4), r + 4
                        }

                        function L(e, t, r, n, a) {
                            return a || C(e, 0, r, 8), i.write(e, t, r, n, 52, 8), r + 8
                        }

                        u.prototype.slice = function (e, t) {
                            var r, n = this.length;
                            if ((e = ~~e) < 0 ? (e += n) < 0 && (e = 0) : e > n && (e = n), (t = void 0 === t ? n : ~~t) < 0 ? (t += n) < 0 && (t = 0) : t > n && (t = n), t < e && (t = e), u.TYPED_ARRAY_SUPPORT) (r = this.subarray(e, t)).__proto__ = u.prototype; else {
                                var i = t - e;
                                r = new u(i, void 0);
                                for (var a = 0; a < i; ++a) r[a] = this[a + e]
                            }
                            return r
                        }, u.prototype.readUIntLE = function (e, t, r) {
                            e |= 0, t |= 0, r || T(e, t, this.length);
                            for (var n = this[e], i = 1, a = 0; ++a < t && (i *= 256);) n += this[e + a] * i;
                            return n
                        }, u.prototype.readUIntBE = function (e, t, r) {
                            e |= 0, t |= 0, r || T(e, t, this.length);
                            for (var n = this[e + --t], i = 1; t > 0 && (i *= 256);) n += this[e + --t] * i;
                            return n
                        }, u.prototype.readUInt8 = function (e, t) {
                            return t || T(e, 1, this.length), this[e]
                        }, u.prototype.readUInt16LE = function (e, t) {
                            return t || T(e, 2, this.length), this[e] | this[e + 1] << 8
                        }, u.prototype.readUInt16BE = function (e, t) {
                            return t || T(e, 2, this.length), this[e] << 8 | this[e + 1]
                        }, u.prototype.readUInt32LE = function (e, t) {
                            return t || T(e, 4, this.length), (this[e] | this[e + 1] << 8 | this[e + 2] << 16) + 16777216 * this[e + 3]
                        }, u.prototype.readUInt32BE = function (e, t) {
                            return t || T(e, 4, this.length), 16777216 * this[e] + (this[e + 1] << 16 | this[e + 2] << 8 | this[e + 3])
                        }, u.prototype.readIntLE = function (e, t, r) {
                            e |= 0, t |= 0, r || T(e, t, this.length);
                            for (var n = this[e], i = 1, a = 0; ++a < t && (i *= 256);) n += this[e + a] * i;
                            return n >= (i *= 128) && (n -= Math.pow(2, 8 * t)), n
                        }, u.prototype.readIntBE = function (e, t, r) {
                            e |= 0, t |= 0, r || T(e, t, this.length);
                            for (var n = t, i = 1, a = this[e + --n]; n > 0 && (i *= 256);) a += this[e + --n] * i;
                            return a >= (i *= 128) && (a -= Math.pow(2, 8 * t)), a
                        }, u.prototype.readInt8 = function (e, t) {
                            return t || T(e, 1, this.length), 128 & this[e] ? -1 * (255 - this[e] + 1) : this[e]
                        }, u.prototype.readInt16LE = function (e, t) {
                            t || T(e, 2, this.length);
                            var r = this[e] | this[e + 1] << 8;
                            return 32768 & r ? 4294901760 | r : r
                        }, u.prototype.readInt16BE = function (e, t) {
                            t || T(e, 2, this.length);
                            var r = this[e + 1] | this[e] << 8;
                            return 32768 & r ? 4294901760 | r : r
                        }, u.prototype.readInt32LE = function (e, t) {
                            return t || T(e, 4, this.length), this[e] | this[e + 1] << 8 | this[e + 2] << 16 | this[e + 3] << 24
                        }, u.prototype.readInt32BE = function (e, t) {
                            return t || T(e, 4, this.length), this[e] << 24 | this[e + 1] << 16 | this[e + 2] << 8 | this[e + 3]
                        }, u.prototype.readFloatLE = function (e, t) {
                            return t || T(e, 4, this.length), i.read(this, e, !0, 23, 4)
                        }, u.prototype.readFloatBE = function (e, t) {
                            return t || T(e, 4, this.length), i.read(this, e, !1, 23, 4)
                        }, u.prototype.readDoubleLE = function (e, t) {
                            return t || T(e, 8, this.length), i.read(this, e, !0, 52, 8)
                        }, u.prototype.readDoubleBE = function (e, t) {
                            return t || T(e, 8, this.length), i.read(this, e, !1, 52, 8)
                        }, u.prototype.writeUIntLE = function (e, t, r, n) {
                            e = +e, t |= 0, r |= 0, n || O(this, e, t, r, Math.pow(2, 8 * r) - 1, 0);
                            var i = 1, a = 0;
                            for (this[t] = 255 & e; ++a < r && (i *= 256);) this[t + a] = e / i & 255;
                            return t + r
                        }, u.prototype.writeUIntBE = function (e, t, r, n) {
                            e = +e, t |= 0, r |= 0, n || O(this, e, t, r, Math.pow(2, 8 * r) - 1, 0);
                            var i = r - 1, a = 1;
                            for (this[t + i] = 255 & e; --i >= 0 && (a *= 256);) this[t + i] = e / a & 255;
                            return t + r
                        }, u.prototype.writeUInt8 = function (e, t, r) {
                            return e = +e, t |= 0, r || O(this, e, t, 1, 255, 0), u.TYPED_ARRAY_SUPPORT || (e = Math.floor(e)), this[t] = 255 & e, t + 1
                        }, u.prototype.writeUInt16LE = function (e, t, r) {
                            return e = +e, t |= 0, r || O(this, e, t, 2, 65535, 0), u.TYPED_ARRAY_SUPPORT ? (this[t] = 255 & e, this[t + 1] = e >>> 8) : R(this, e, t, !0), t + 2
                        }, u.prototype.writeUInt16BE = function (e, t, r) {
                            return e = +e, t |= 0, r || O(this, e, t, 2, 65535, 0), u.TYPED_ARRAY_SUPPORT ? (this[t] = e >>> 8, this[t + 1] = 255 & e) : R(this, e, t, !1), t + 2
                        }, u.prototype.writeUInt32LE = function (e, t, r) {
                            return e = +e, t |= 0, r || O(this, e, t, 4, 4294967295, 0), u.TYPED_ARRAY_SUPPORT ? (this[t + 3] = e >>> 24, this[t + 2] = e >>> 16, this[t + 1] = e >>> 8, this[t] = 255 & e) : j(this, e, t, !0), t + 4
                        }, u.prototype.writeUInt32BE = function (e, t, r) {
                            return e = +e, t |= 0, r || O(this, e, t, 4, 4294967295, 0), u.TYPED_ARRAY_SUPPORT ? (this[t] = e >>> 24, this[t + 1] = e >>> 16, this[t + 2] = e >>> 8, this[t + 3] = 255 & e) : j(this, e, t, !1), t + 4
                        }, u.prototype.writeIntLE = function (e, t, r, n) {
                            if (e = +e, t |= 0, !n) {
                                var i = Math.pow(2, 8 * r - 1);
                                O(this, e, t, r, i - 1, -i)
                            }
                            var a = 0, o = 1, s = 0;
                            for (this[t] = 255 & e; ++a < r && (o *= 256);) e < 0 && 0 === s && 0 !== this[t + a - 1] && (s = 1), this[t + a] = (e / o >> 0) - s & 255;
                            return t + r
                        }, u.prototype.writeIntBE = function (e, t, r, n) {
                            if (e = +e, t |= 0, !n) {
                                var i = Math.pow(2, 8 * r - 1);
                                O(this, e, t, r, i - 1, -i)
                            }
                            var a = r - 1, o = 1, s = 0;
                            for (this[t + a] = 255 & e; --a >= 0 && (o *= 256);) e < 0 && 0 === s && 0 !== this[t + a + 1] && (s = 1), this[t + a] = (e / o >> 0) - s & 255;
                            return t + r
                        }, u.prototype.writeInt8 = function (e, t, r) {
                            return e = +e, t |= 0, r || O(this, e, t, 1, 127, -128), u.TYPED_ARRAY_SUPPORT || (e = Math.floor(e)), e < 0 && (e = 255 + e + 1), this[t] = 255 & e, t + 1
                        }, u.prototype.writeInt16LE = function (e, t, r) {
                            return e = +e, t |= 0, r || O(this, e, t, 2, 32767, -32768), u.TYPED_ARRAY_SUPPORT ? (this[t] = 255 & e, this[t + 1] = e >>> 8) : R(this, e, t, !0), t + 2
                        }, u.prototype.writeInt16BE = function (e, t, r) {
                            return e = +e, t |= 0, r || O(this, e, t, 2, 32767, -32768), u.TYPED_ARRAY_SUPPORT ? (this[t] = e >>> 8, this[t + 1] = 255 & e) : R(this, e, t, !1), t + 2
                        }, u.prototype.writeInt32LE = function (e, t, r) {
                            return e = +e, t |= 0, r || O(this, e, t, 4, 2147483647, -2147483648), u.TYPED_ARRAY_SUPPORT ? (this[t] = 255 & e, this[t + 1] = e >>> 8, this[t + 2] = e >>> 16, this[t + 3] = e >>> 24) : j(this, e, t, !0), t + 4
                        }, u.prototype.writeInt32BE = function (e, t, r) {
                            return e = +e, t |= 0, r || O(this, e, t, 4, 2147483647, -2147483648), e < 0 && (e = 4294967295 + e + 1), u.TYPED_ARRAY_SUPPORT ? (this[t] = e >>> 24, this[t + 1] = e >>> 16, this[t + 2] = e >>> 8, this[t + 3] = 255 & e) : j(this, e, t, !1), t + 4
                        }, u.prototype.writeFloatLE = function (e, t, r) {
                            return B(this, e, t, !0, r)
                        }, u.prototype.writeFloatBE = function (e, t, r) {
                            return B(this, e, t, !1, r)
                        }, u.prototype.writeDoubleLE = function (e, t, r) {
                            return L(this, e, t, !0, r)
                        }, u.prototype.writeDoubleBE = function (e, t, r) {
                            return L(this, e, t, !1, r)
                        }, u.prototype.copy = function (e, t, r, n) {
                            if (r || (r = 0), n || 0 === n || (n = this.length), t >= e.length && (t = e.length), t || (t = 0), n > 0 && n < r && (n = r), n === r) return 0;
                            if (0 === e.length || 0 === this.length) return 0;
                            if (t < 0) throw new RangeError("targetStart out of bounds");
                            if (r < 0 || r >= this.length) throw new RangeError("sourceStart out of bounds");
                            if (n < 0) throw new RangeError("sourceEnd out of bounds");
                            n > this.length && (n = this.length), e.length - t < n - r && (n = e.length - t + r);
                            var i, a = n - r;
                            if (this === e && r < t && t < n) for (i = a - 1; i >= 0; --i) e[i + t] = this[i + r]; else if (a < 1e3 || !u.TYPED_ARRAY_SUPPORT) for (i = 0; i < a; ++i) e[i + t] = this[i + r]; else Uint8Array.prototype.set.call(e, this.subarray(r, r + a), t);
                            return a
                        }, u.prototype.fill = function (e, t, r, n) {
                            if ("string" == typeof e) {
                                if ("string" == typeof t ? (n = t, t = 0, r = this.length) : "string" == typeof r && (n = r, r = this.length), 1 === e.length) {
                                    var i = e.charCodeAt(0);
                                    i < 256 && (e = i)
                                }
                                if (void 0 !== n && "string" != typeof n) throw new TypeError("encoding must be a string");
                                if ("string" == typeof n && !u.isEncoding(n)) throw new TypeError("Unknown encoding: " + n)
                            } else "number" == typeof e && (e &= 255);
                            if (t < 0 || this.length < t || this.length < r) throw new RangeError("Out of range index");
                            if (r <= t) return this;
                            var a;
                            if (t >>>= 0, r = void 0 === r ? this.length : r >>> 0, e || (e = 0), "number" == typeof e) for (a = t; a < r; ++a) this[a] = e; else {
                                var o = u.isBuffer(e) ? e : U(new u(e, n).toString()), s = o.length;
                                for (a = 0; a < r - t; ++a) this[a + t] = o[a % s]
                            }
                            return this
                        };
                        var W = /[^+\/0-9A-Za-z-_]/g;

                        function F(e) {
                            return e < 16 ? "0" + e.toString(16) : e.toString(16)
                        }

                        function U(e, t) {
                            var r;
                            t = t || 1 / 0;
                            for (var n = e.length, i = null, a = [], o = 0; o < n; ++o) {
                                if ((r = e.charCodeAt(o)) > 55295 && r < 57344) {
                                    if (!i) {
                                        if (r > 56319) {
                                            (t -= 3) > -1 && a.push(239, 191, 189);
                                            continue
                                        }
                                        if (o + 1 === n) {
                                            (t -= 3) > -1 && a.push(239, 191, 189);
                                            continue
                                        }
                                        i = r;
                                        continue
                                    }
                                    if (r < 56320) {
                                        (t -= 3) > -1 && a.push(239, 191, 189), i = r;
                                        continue
                                    }
                                    r = 65536 + (i - 55296 << 10 | r - 56320)
                                } else i && (t -= 3) > -1 && a.push(239, 191, 189);
                                if (i = null, r < 128) {
                                    if ((t -= 1) < 0) break;
                                    a.push(r)
                                } else if (r < 2048) {
                                    if ((t -= 2) < 0) break;
                                    a.push(r >> 6 | 192, 63 & r | 128)
                                } else if (r < 65536) {
                                    if ((t -= 3) < 0) break;
                                    a.push(r >> 12 | 224, r >> 6 & 63 | 128, 63 & r | 128)
                                } else {
                                    if (!(r < 1114112)) throw new Error("Invalid code point");
                                    if ((t -= 4) < 0) break;
                                    a.push(r >> 18 | 240, r >> 12 & 63 | 128, r >> 6 & 63 | 128, 63 & r | 128)
                                }
                            }
                            return a
                        }

                        function D(e) {
                            return n.toByteArray(function (e) {
                                if ((e = function (e) {
                                    return e.trim ? e.trim() : e.replace(/^\s+|\s+$/g, "")
                                }(e).replace(W, "")).length < 2) return "";
                                for (; e.length % 4 != 0;) e += "=";
                                return e
                            }(e))
                        }

                        function q(e, t, r, n) {
                            for (var i = 0; i < n && !(i + r >= t.length || i >= e.length); ++i) t[i + r] = e[i];
                            return i
                        }
                    }).call(this, r(39))
                }, function (e, t, r) {
                    t.byteLength = function (e) {
                        var t = c(e), r = t[0], n = t[1];
                        return 3 * (r + n) / 4 - n
                    }, t.toByteArray = function (e) {
                        for (var t, r = c(e), n = r[0], o = r[1], s = new a(3 * (n + o) / 4 - o), u = 0, f = o > 0 ? n - 4 : n, d = 0; d < f; d += 4) t = i[e.charCodeAt(d)] << 18 | i[e.charCodeAt(d + 1)] << 12 | i[e.charCodeAt(d + 2)] << 6 | i[e.charCodeAt(d + 3)], s[u++] = t >> 16 & 255, s[u++] = t >> 8 & 255, s[u++] = 255 & t;
                        return 2 === o && (t = i[e.charCodeAt(d)] << 2 | i[e.charCodeAt(d + 1)] >> 4, s[u++] = 255 & t), 1 === o && (t = i[e.charCodeAt(d)] << 10 | i[e.charCodeAt(d + 1)] << 4 | i[e.charCodeAt(d + 2)] >> 2, s[u++] = t >> 8 & 255, s[u++] = 255 & t), s
                    }, t.fromByteArray = function (e) {
                        for (var t, r = e.length, i = r % 3, a = [], o = 0, s = r - i; o < s; o += 16383) a.push(f(e, o, o + 16383 > s ? s : o + 16383));
                        return 1 === i ? (t = e[r - 1], a.push(n[t >> 2] + n[t << 4 & 63] + "==")) : 2 === i && (t = (e[r - 2] << 8) + e[r - 1], a.push(n[t >> 10] + n[t >> 4 & 63] + n[t << 2 & 63] + "=")), a.join("")
                    };
                    for (var n = [], i = [], a = "undefined" != typeof Uint8Array ? Uint8Array : Array, o = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", s = 0, u = o.length; s < u; ++s) n[s] = o[s], i[o.charCodeAt(s)] = s;

                    function c(e) {
                        var t = e.length;
                        if (t % 4 > 0) throw new Error("Invalid string. Length must be a multiple of 4");
                        var r = e.indexOf("=");
                        return -1 === r && (r = t), [r, r === t ? 0 : 4 - r % 4]
                    }

                    function f(e, t, r) {
                        for (var i, a, o = [], s = t; s < r; s += 3) i = (e[s] << 16 & 16711680) + (e[s + 1] << 8 & 65280) + (255 & e[s + 2]), o.push(n[(a = i) >> 18 & 63] + n[a >> 12 & 63] + n[a >> 6 & 63] + n[63 & a]);
                        return o.join("")
                    }

                    i["-".charCodeAt(0)] = 62, i["_".charCodeAt(0)] = 63
                }, function (e, t) {
                    t.read = function (e, t, r, n, i) {
                        var a, o, s = 8 * i - n - 1, u = (1 << s) - 1, c = u >> 1, f = -7, d = r ? i - 1 : 0,
                            h = r ? -1 : 1, l = e[t + d];
                        for (d += h, a = l & (1 << -f) - 1, l >>= -f, f += s; f > 0; a = 256 * a + e[t + d], d += h, f -= 8) ;
                        for (o = a & (1 << -f) - 1, a >>= -f, f += n; f > 0; o = 256 * o + e[t + d], d += h, f -= 8) ;
                        if (0 === a) a = 1 - c; else {
                            if (a === u) return o ? NaN : 1 / 0 * (l ? -1 : 1);
                            o += Math.pow(2, n), a -= c
                        }
                        return (l ? -1 : 1) * o * Math.pow(2, a - n)
                    }, t.write = function (e, t, r, n, i, a) {
                        var o, s, u, c = 8 * a - i - 1, f = (1 << c) - 1, d = f >> 1,
                            h = 23 === i ? Math.pow(2, -24) - Math.pow(2, -77) : 0, l = n ? 0 : a - 1, p = n ? 1 : -1,
                            b = t < 0 || 0 === t && 1 / t < 0 ? 1 : 0;
                        for (t = Math.abs(t), isNaN(t) || t === 1 / 0 ? (s = isNaN(t) ? 1 : 0, o = f) : (o = Math.floor(Math.log(t) / Math.LN2), t * (u = Math.pow(2, -o)) < 1 && (o--, u *= 2), (t += o + d >= 1 ? h / u : h * Math.pow(2, 1 - d)) * u >= 2 && (o++, u /= 2), o + d >= f ? (s = 0, o = f) : o + d >= 1 ? (s = (t * u - 1) * Math.pow(2, i), o += d) : (s = t * Math.pow(2, d - 1) * Math.pow(2, i), o = 0)); i >= 8; e[r + l] = 255 & s, l += p, s /= 256, i -= 8) ;
                        for (o = o << i | s, c += i; c > 0; e[r + l] = 255 & o, l += p, o /= 256, c -= 8) ;
                        e[r + l - p] |= 128 * b
                    }
                }, function (e, t) {
                    var r = {}.toString;
                    e.exports = Array.isArray || function (e) {
                        return "[object Array]" == r.call(e)
                    }
                }, function (e, t, r) {
                    var i = function (e) {
                        var t, r = Object.prototype, i = r.hasOwnProperty,
                            a = "function" == typeof Symbol ? Symbol : {}, o = a.iterator || "@@iterator",
                            s = a.asyncIterator || "@@asyncIterator", u = a.toStringTag || "@@toStringTag";

                        function c(e, t, r, n) {
                            var i = t && t.prototype instanceof v ? t : v, a = Object.create(i.prototype),
                                o = new E(n || []);
                            return a._invoke = function (e, t, r) {
                                var n = d;
                                return function (i, a) {
                                    if (n === l) throw new Error("Generator is already running");
                                    if (n === p) {
                                        if ("throw" === i) throw a;
                                        return P()
                                    }
                                    for (r.method = i, r.arg = a; ;) {
                                        var o = r.delegate;
                                        if (o) {
                                            var s = S(o, r);
                                            if (s) {
                                                if (s === b) continue;
                                                return s
                                            }
                                        }
                                        if ("next" === r.method) r.sent = r._sent = r.arg; else if ("throw" === r.method) {
                                            if (n === d) throw n = p, r.arg;
                                            r.dispatchException(r.arg)
                                        } else "return" === r.method && r.abrupt("return", r.arg);
                                        n = l;
                                        var u = f(e, t, r);
                                        if ("normal" === u.type) {
                                            if (n = r.done ? p : h, u.arg === b) continue;
                                            return {value: u.arg, done: r.done}
                                        }
                                        "throw" === u.type && (n = p, r.method = "throw", r.arg = u.arg)
                                    }
                                }
                            }(e, r, o), a
                        }

                        function f(e, t, r) {
                            try {
                                return {type: "normal", arg: e.call(t, r)}
                            } catch (e) {
                                return {type: "throw", arg: e}
                            }
                        }

                        e.wrap = c;
                        var d = "suspendedStart", h = "suspendedYield", l = "executing", p = "completed", b = {};

                        function v() {
                        }

                        function g() {
                        }

                        function m() {
                        }

                        var y = {};
                        y[o] = function () {
                            return this
                        };
                        var w = Object.getPrototypeOf, x = w && w(w(N([])));
                        x && x !== r && i.call(x, o) && (y = x);
                        var _ = m.prototype = v.prototype = Object.create(y);

                        function A(e) {
                            ["next", "throw", "return"].forEach(function (t) {
                                e[t] = function (e) {
                                    return this._invoke(t, e)
                                }
                            })
                        }

                        function k(e) {
                            var t;
                            this._invoke = function (r, a) {
                                function o() {
                                    return new Promise(function (t, o) {
                                        !function t(r, a, o, s) {
                                            var u = f(e[r], e, a);
                                            if ("throw" !== u.type) {
                                                var c = u.arg, d = c.value;
                                                return d && "object" == n()(d) && i.call(d, "__await") ? Promise.resolve(d.__await).then(function (e) {
                                                    t("next", e, o, s)
                                                }, function (e) {
                                                    t("throw", e, o, s)
                                                }) : Promise.resolve(d).then(function (e) {
                                                    c.value = e, o(c)
                                                }, function (e) {
                                                    return t("throw", e, o, s)
                                                })
                                            }
                                            s(u.arg)
                                        }(r, a, t, o)
                                    })
                                }

                                return t = t ? t.then(o, o) : o()
                            }
                        }

                        function S(e, r) {
                            var n = e.iterator[r.method];
                            if (n === t) {
                                if (r.delegate = null, "throw" === r.method) {
                                    if (e.iterator.return && (r.method = "return", r.arg = t, S(e, r), "throw" === r.method)) return b;
                                    r.method = "throw", r.arg = new TypeError("The iterator does not provide a 'throw' method")
                                }
                                return b
                            }
                            var i = f(n, e.iterator, r.arg);
                            if ("throw" === i.type) return r.method = "throw", r.arg = i.arg, r.delegate = null, b;
                            var a = i.arg;
                            return a ? a.done ? (r[e.resultName] = a.value, r.next = e.nextLoc, "return" !== r.method && (r.method = "next", r.arg = t), r.delegate = null, b) : a : (r.method = "throw", r.arg = new TypeError("iterator result is not an object"), r.delegate = null, b)
                        }

                        function M(e) {
                            var t = {tryLoc: e[0]};
                            1 in e && (t.catchLoc = e[1]), 2 in e && (t.finallyLoc = e[2], t.afterLoc = e[3]), this.tryEntries.push(t)
                        }

                        function I(e) {
                            var t = e.completion || {};
                            t.type = "normal", delete t.arg, e.completion = t
                        }

                        function E(e) {
                            this.tryEntries = [{tryLoc: "root"}], e.forEach(M, this), this.reset(!0)
                        }

                        function N(e) {
                            if (e) {
                                var r = e[o];
                                if (r) return r.call(e);
                                if ("function" == typeof e.next) return e;
                                if (!isNaN(e.length)) {
                                    var n = -1, a = function r() {
                                        for (; ++n < e.length;) if (i.call(e, n)) return r.value = e[n], r.done = !1, r;
                                        return r.value = t, r.done = !0, r
                                    };
                                    return a.next = a
                                }
                            }
                            return {next: P}
                        }

                        function P() {
                            return {value: t, done: !0}
                        }

                        return g.prototype = _.constructor = m, m.constructor = g, m[u] = g.displayName = "GeneratorFunction", e.isGeneratorFunction = function (e) {
                            var t = "function" == typeof e && e.constructor;
                            return !!t && (t === g || "GeneratorFunction" === (t.displayName || t.name))
                        }, e.mark = function (e) {
                            return Object.setPrototypeOf ? Object.setPrototypeOf(e, m) : (e.__proto__ = m, u in e || (e[u] = "GeneratorFunction")), e.prototype = Object.create(_), e
                        }, e.awrap = function (e) {
                            return {__await: e}
                        }, A(k.prototype), k.prototype[s] = function () {
                            return this
                        }, e.AsyncIterator = k, e.async = function (t, r, n, i) {
                            var a = new k(c(t, r, n, i));
                            return e.isGeneratorFunction(r) ? a : a.next().then(function (e) {
                                return e.done ? e.value : a.next()
                            })
                        }, A(_), _[u] = "Generator", _[o] = function () {
                            return this
                        }, _.toString = function () {
                            return "[object Generator]"
                        }, e.keys = function (e) {
                            var t = [];
                            for (var r in e) t.push(r);
                            return t.reverse(), function r() {
                                for (; t.length;) {
                                    var n = t.pop();
                                    if (n in e) return r.value = n, r.done = !1, r
                                }
                                return r.done = !0, r
                            }
                        }, e.values = N, E.prototype = {
                            constructor: E, reset: function (e) {
                                if (this.prev = 0, this.next = 0, this.sent = this._sent = t, this.done = !1, this.delegate = null, this.method = "next", this.arg = t, this.tryEntries.forEach(I), !e) for (var r in this) "t" === r.charAt(0) && i.call(this, r) && !isNaN(+r.slice(1)) && (this[r] = t)
                            }, stop: function () {
                                this.done = !0;
                                var e = this.tryEntries[0].completion;
                                if ("throw" === e.type) throw e.arg;
                                return this.rval
                            }, dispatchException: function (e) {
                                if (this.done) throw e;
                                var r = this;

                                function n(n, i) {
                                    return s.type = "throw", s.arg = e, r.next = n, i && (r.method = "next", r.arg = t), !!i
                                }

                                for (var a = this.tryEntries.length - 1; a >= 0; --a) {
                                    var o = this.tryEntries[a], s = o.completion;
                                    if ("root" === o.tryLoc) return n("end");
                                    if (o.tryLoc <= this.prev) {
                                        var u = i.call(o, "catchLoc"), c = i.call(o, "finallyLoc");
                                        if (u && c) {
                                            if (this.prev < o.catchLoc) return n(o.catchLoc, !0);
                                            if (this.prev < o.finallyLoc) return n(o.finallyLoc)
                                        } else if (u) {
                                            if (this.prev < o.catchLoc) return n(o.catchLoc, !0)
                                        } else {
                                            if (!c) throw new Error("try statement without catch or finally");
                                            if (this.prev < o.finallyLoc) return n(o.finallyLoc)
                                        }
                                    }
                                }
                            }, abrupt: function (e, t) {
                                for (var r = this.tryEntries.length - 1; r >= 0; --r) {
                                    var n = this.tryEntries[r];
                                    if (n.tryLoc <= this.prev && i.call(n, "finallyLoc") && this.prev < n.finallyLoc) {
                                        var a = n;
                                        break
                                    }
                                }
                                a && ("break" === e || "continue" === e) && a.tryLoc <= t && t <= a.finallyLoc && (a = null);
                                var o = a ? a.completion : {};
                                return o.type = e, o.arg = t, a ? (this.method = "next", this.next = a.finallyLoc, b) : this.complete(o)
                            }, complete: function (e, t) {
                                if ("throw" === e.type) throw e.arg;
                                return "break" === e.type || "continue" === e.type ? this.next = e.arg : "return" === e.type ? (this.rval = this.arg = e.arg, this.method = "return", this.next = "end") : "normal" === e.type && t && (this.next = t), b
                            }, finish: function (e) {
                                for (var t = this.tryEntries.length - 1; t >= 0; --t) {
                                    var r = this.tryEntries[t];
                                    if (r.finallyLoc === e) return this.complete(r.completion, r.afterLoc), I(r), b
                                }
                            }, catch: function (e) {
                                for (var t = this.tryEntries.length - 1; t >= 0; --t) {
                                    var r = this.tryEntries[t];
                                    if (r.tryLoc === e) {
                                        var n = r.completion;
                                        if ("throw" === n.type) {
                                            var i = n.arg;
                                            I(r)
                                        }
                                        return i
                                    }
                                }
                                throw new Error("illegal catch attempt")
                            }, delegateYield: function (e, r, n) {
                                return this.delegate = {
                                    iterator: N(e),
                                    resultName: r,
                                    nextLoc: n
                                }, "next" === this.method && (this.arg = t), b
                            }
                        }, e
                    }(e.exports);
                    try {
                        regeneratorRuntime = i
                    } catch (e) {
                        Function("r", "regeneratorRuntime = r")(i)
                    }
                }, function (e, t) {
                    function r(t, n) {
                        return e.exports = r = Object.setPrototypeOf || function (e, t) {
                            return e.__proto__ = t, e
                        }, r(t, n)
                    }

                    e.exports = r
                }, function (e, t, r) {
                    var n = r(11), i = r(40), a = r(81), o = r(47);

                    function s(e) {
                        var t = new a(e), r = i(a.prototype.request, t);
                        return n.extend(r, a.prototype, t), n.extend(r, t), r
                    }

                    var u = s(r(43));
                    u.Axios = a, u.create = function (e) {
                        return s(o(u.defaults, e))
                    }, u.Cancel = r(48), u.CancelToken = r(94), u.isCancel = r(42), u.all = function (e) {
                        return Promise.all(e)
                    }, u.spread = r(95), u.isAxiosError = r(96), e.exports = u, e.exports.default = u
                }, function (e, t, r) {
                    var n = r(11), i = r(41), a = r(82), o = r(83), s = r(47);

                    function u(e) {
                        this.defaults = e, this.interceptors = {request: new a, response: new a}
                    }

                    u.prototype.request = function (e) {
                        "string" == typeof e ? (e = arguments[1] || {}).url = arguments[0] : e = e || {}, (e = s(this.defaults, e)).method ? e.method = e.method.toLowerCase() : this.defaults.method ? e.method = this.defaults.method.toLowerCase() : e.method = "get";
                        var t = [o, void 0], r = Promise.resolve(e);
                        for (this.interceptors.request.forEach(function (e) {
                            t.unshift(e.fulfilled, e.rejected)
                        }), this.interceptors.response.forEach(function (e) {
                            t.push(e.fulfilled, e.rejected)
                        }); t.length;) r = r.then(t.shift(), t.shift());
                        return r
                    }, u.prototype.getUri = function (e) {
                        return e = s(this.defaults, e), i(e.url, e.params, e.paramsSerializer).replace(/^\?/, "")
                    }, n.forEach(["delete", "get", "head", "options"], function (e) {
                        u.prototype[e] = function (t, r) {
                            return this.request(s(r || {}, {method: e, url: t, data: (r || {}).data}))
                        }
                    }), n.forEach(["post", "put", "patch"], function (e) {
                        u.prototype[e] = function (t, r, n) {
                            return this.request(s(n || {}, {method: e, url: t, data: r}))
                        }
                    }), e.exports = u
                }, function (e, t, r) {
                    var n = r(11);

                    function i() {
                        this.handlers = []
                    }

                    i.prototype.use = function (e, t) {
                        return this.handlers.push({fulfilled: e, rejected: t}), this.handlers.length - 1
                    }, i.prototype.eject = function (e) {
                        this.handlers[e] && (this.handlers[e] = null)
                    }, i.prototype.forEach = function (e) {
                        n.forEach(this.handlers, function (t) {
                            null !== t && e(t)
                        })
                    }, e.exports = i
                }, function (e, t, r) {
                    var n = r(11), i = r(84), a = r(42), o = r(43);

                    function s(e) {
                        e.cancelToken && e.cancelToken.throwIfRequested()
                    }

                    e.exports = function (e) {
                        return s(e), e.headers = e.headers || {}, e.data = i(e.data, e.headers, e.transformRequest), e.headers = n.merge(e.headers.common || {}, e.headers[e.method] || {}, e.headers), n.forEach(["delete", "get", "head", "post", "put", "patch", "common"], function (t) {
                            delete e.headers[t]
                        }), (e.adapter || o.adapter)(e).then(function (t) {
                            return s(e), t.data = i(t.data, t.headers, e.transformResponse), t
                        }, function (t) {
                            return a(t) || (s(e), t && t.response && (t.response.data = i(t.response.data, t.response.headers, e.transformResponse))), Promise.reject(t)
                        })
                    }
                }, function (e, t, r) {
                    var n = r(11);
                    e.exports = function (e, t, r) {
                        return n.forEach(r, function (r) {
                            e = r(e, t)
                        }), e
                    }
                }, function (e, t, r) {
                    var n = r(11);
                    e.exports = function (e, t) {
                        n.forEach(e, function (r, n) {
                            n !== t && n.toUpperCase() === t.toUpperCase() && (e[t] = r, delete e[n])
                        })
                    }
                }, function (e, t, r) {
                    var n = r(46);
                    e.exports = function (e, t, r) {
                        var i = r.config.validateStatus;
                        r.status && i && !i(r.status) ? t(n("Request failed with status code " + r.status, r.config, null, r.request, r)) : e(r)
                    }
                }, function (e, t, r) {
                    e.exports = function (e, t, r, n, i) {
                        return e.config = t, r && (e.code = r), e.request = n, e.response = i, e.isAxiosError = !0, e.toJSON = function () {
                            return {
                                message: this.message,
                                name: this.name,
                                description: this.description,
                                number: this.number,
                                fileName: this.fileName,
                                lineNumber: this.lineNumber,
                                columnNumber: this.columnNumber,
                                stack: this.stack,
                                config: this.config,
                                code: this.code
                            }
                        }, e
                    }
                }, function (e, t, r) {
                    var n = r(11);
                    e.exports = n.isStandardBrowserEnv() ? {
                        write: function (e, t, r, i, a, o) {
                            var s = [];
                            s.push(e + "=" + encodeURIComponent(t)), n.isNumber(r) && s.push("expires=" + new Date(r).toGMTString()), n.isString(i) && s.push("path=" + i), n.isString(a) && s.push("domain=" + a), !0 === o && s.push("secure"), document.cookie = s.join("; ")
                        }, read: function (e) {
                            var t = document.cookie.match(new RegExp("(^|;\\s*)(" + e + ")=([^;]*)"));
                            return t ? decodeURIComponent(t[3]) : null
                        }, remove: function (e) {
                            this.write(e, "", Date.now() - 864e5)
                        }
                    } : {
                        write: function () {
                        }, read: function () {
                            return null
                        }, remove: function () {
                        }
                    }
                }, function (e, t, r) {
                    var n = r(90), i = r(91);
                    e.exports = function (e, t) {
                        return e && !n(t) ? i(e, t) : t
                    }
                }, function (e, t, r) {
                    e.exports = function (e) {
                        return /^([a-z][a-z\d\+\-\.]*:)?\/\//i.test(e)
                    }
                }, function (e, t, r) {
                    e.exports = function (e, t) {
                        return t ? e.replace(/\/+$/, "") + "/" + t.replace(/^\/+/, "") : e
                    }
                }, function (e, t, r) {
                    var n = r(11),
                        i = ["age", "authorization", "content-length", "content-type", "etag", "expires", "from", "host", "if-modified-since", "if-unmodified-since", "last-modified", "location", "max-forwards", "proxy-authorization", "referer", "retry-after", "user-agent"];
                    e.exports = function (e) {
                        var t, r, a, o = {};
                        return e ? (n.forEach(e.split("\n"), function (e) {
                            if (a = e.indexOf(":"), t = n.trim(e.substr(0, a)).toLowerCase(), r = n.trim(e.substr(a + 1)), t) {
                                if (o[t] && i.indexOf(t) >= 0) return;
                                o[t] = "set-cookie" === t ? (o[t] ? o[t] : []).concat([r]) : o[t] ? o[t] + ", " + r : r
                            }
                        }), o) : o
                    }
                }, function (e, t, r) {
                    var n = r(11);
                    e.exports = n.isStandardBrowserEnv() ? function () {
                        var e, t = /(msie|trident)/i.test(navigator.userAgent), r = document.createElement("a");

                        function i(e) {
                            var n = e;
                            return t && (r.setAttribute("href", n), n = r.href), r.setAttribute("href", n), {
                                href: r.href,
                                protocol: r.protocol ? r.protocol.replace(/:$/, "") : "",
                                host: r.host,
                                search: r.search ? r.search.replace(/^\?/, "") : "",
                                hash: r.hash ? r.hash.replace(/^#/, "") : "",
                                hostname: r.hostname,
                                port: r.port,
                                pathname: "/" === r.pathname.charAt(0) ? r.pathname : "/" + r.pathname
                            }
                        }

                        return e = i(window.location.href), function (t) {
                            var r = n.isString(t) ? i(t) : t;
                            return r.protocol === e.protocol && r.host === e.host
                        }
                    }() : function () {
                        return !0
                    }
                }, function (e, t, r) {
                    var n = r(48);

                    function i(e) {
                        if ("function" != typeof e) throw new TypeError("executor must be a function.");
                        var t;
                        this.promise = new Promise(function (e) {
                            t = e
                        });
                        var r = this;
                        e(function (e) {
                            r.reason || (r.reason = new n(e), t(r.reason))
                        })
                    }

                    i.prototype.throwIfRequested = function () {
                        if (this.reason) throw this.reason
                    }, i.source = function () {
                        var e;
                        return {
                            token: new i(function (t) {
                                e = t
                            }), cancel: e
                        }
                    }, e.exports = i
                }, function (e, t, r) {
                    e.exports = function (e) {
                        return function (t) {
                            return e.apply(null, t)
                        }
                    }
                }, function (e, t, r) {
                    e.exports = function (e) {
                        return "object" == n()(e) && !0 === e.isAxiosError
                    }
                }, function (e, t) {
                    e.exports = function (e) {
                        if (Array.isArray(e)) {
                            for (var t = 0, r = new Array(e.length); t < e.length; t++) r[t] = e[t];
                            return r
                        }
                    }
                }, function (e, t) {
                    e.exports = function (e) {
                        if (Symbol.iterator in Object(e) || "[object Arguments]" === Object.prototype.toString.call(e)) return Array.from(e)
                    }
                }, function (e, t) {
                    e.exports = function () {
                        throw new TypeError("Invalid attempt to spread non-iterable instance")
                    }
                }, function (e, t, r) {
                    (function (t, r) {
                        /**
                         * [js-sha3]{@link https://github.com/emn178/js-sha3}
                         *
                         * @version 0.5.7
                         * @author Chen, Yi-Cyuan [emn178@gmail.com]
                         * @copyright Chen, Yi-Cyuan 2015-2016
                         * @license MIT
                         */
                        !function () {
                            var i = "object" == ("undefined" == typeof window ? "undefined" : n()(window)) ? window : {};
                            !i.JS_SHA3_NO_NODE_JS && "object" == n()(t) && t.versions && t.versions.node && (i = r);
                            for (var a = !i.JS_SHA3_NO_COMMON_JS && "object" == n()(e) && e.exports, o = "0123456789abcdef".split(""), s = [0, 8, 16, 24], u = [1, 0, 32898, 0, 32906, 2147483648, 2147516416, 2147483648, 32907, 0, 2147483649, 0, 2147516545, 2147483648, 32777, 2147483648, 138, 0, 136, 0, 2147516425, 0, 2147483658, 0, 2147516555, 0, 139, 2147483648, 32905, 2147483648, 32771, 2147483648, 32770, 2147483648, 128, 2147483648, 32778, 0, 2147483658, 2147483648, 2147516545, 2147483648, 32896, 2147483648, 2147483649, 0, 2147516424, 2147483648], c = [224, 256, 384, 512], f = ["hex", "buffer", "arrayBuffer", "array"], d = function (e, t, r) {
                                return function (n) {
                                    return new _(e, t, e).update(n)[r]()
                                }
                            }, h = function (e, t, r) {
                                return function (n, i) {
                                    return new _(e, t, i).update(n)[r]()
                                }
                            }, l = function (e, t) {
                                var r = d(e, t, "hex");
                                r.create = function () {
                                    return new _(e, t, e)
                                }, r.update = function (e) {
                                    return r.create().update(e)
                                };
                                for (var n = 0; n < f.length; ++n) {
                                    var i = f[n];
                                    r[i] = d(e, t, i)
                                }
                                return r
                            }, p = [{
                                name: "keccak",
                                padding: [1, 256, 65536, 16777216],
                                bits: c,
                                createMethod: l
                            }, {
                                name: "sha3",
                                padding: [6, 1536, 393216, 100663296],
                                bits: c,
                                createMethod: l
                            }, {
                                name: "shake",
                                padding: [31, 7936, 2031616, 520093696],
                                bits: [128, 256],
                                createMethod: function (e, t) {
                                    var r = h(e, t, "hex");
                                    r.create = function (r) {
                                        return new _(e, t, r)
                                    }, r.update = function (e, t) {
                                        return r.create(t).update(e)
                                    };
                                    for (var n = 0; n < f.length; ++n) {
                                        var i = f[n];
                                        r[i] = h(e, t, i)
                                    }
                                    return r
                                }
                            }], b = {}, v = [], g = 0; g < p.length; ++g) for (var m = p[g], y = m.bits, w = 0; w < y.length; ++w) {
                                var x = m.name + "_" + y[w];
                                v.push(x), b[x] = m.createMethod(y[w], m.padding)
                            }

                            function _(e, t, r) {
                                this.blocks = [], this.s = [], this.padding = t, this.outputBits = r, this.reset = !0, this.block = 0, this.start = 0, this.blockCount = 1600 - (e << 1) >> 5, this.byteCount = this.blockCount << 2, this.outputBlocks = r >> 5, this.extraBytes = (31 & r) >> 3;
                                for (var n = 0; n < 50; ++n) this.s[n] = 0
                            }

                            _.prototype.update = function (e) {
                                var t = "string" != typeof e;
                                t && e.constructor === ArrayBuffer && (e = new Uint8Array(e));
                                for (var r, n, i = e.length, a = this.blocks, o = this.byteCount, u = this.blockCount, c = 0, f = this.s; c < i;) {
                                    if (this.reset) for (this.reset = !1, a[0] = this.block, r = 1; r < u + 1; ++r) a[r] = 0;
                                    if (t) for (r = this.start; c < i && r < o; ++c) a[r >> 2] |= e[c] << s[3 & r++]; else for (r = this.start; c < i && r < o; ++c) (n = e.charCodeAt(c)) < 128 ? a[r >> 2] |= n << s[3 & r++] : n < 2048 ? (a[r >> 2] |= (192 | n >> 6) << s[3 & r++], a[r >> 2] |= (128 | 63 & n) << s[3 & r++]) : n < 55296 || n >= 57344 ? (a[r >> 2] |= (224 | n >> 12) << s[3 & r++], a[r >> 2] |= (128 | n >> 6 & 63) << s[3 & r++], a[r >> 2] |= (128 | 63 & n) << s[3 & r++]) : (n = 65536 + ((1023 & n) << 10 | 1023 & e.charCodeAt(++c)), a[r >> 2] |= (240 | n >> 18) << s[3 & r++], a[r >> 2] |= (128 | n >> 12 & 63) << s[3 & r++], a[r >> 2] |= (128 | n >> 6 & 63) << s[3 & r++], a[r >> 2] |= (128 | 63 & n) << s[3 & r++]);
                                    if (this.lastByteIndex = r, r >= o) {
                                        for (this.start = r - o, this.block = a[u], r = 0; r < u; ++r) f[r] ^= a[r];
                                        A(f), this.reset = !0
                                    } else this.start = r
                                }
                                return this
                            }, _.prototype.finalize = function () {
                                var e = this.blocks, t = this.lastByteIndex, r = this.blockCount, n = this.s;
                                if (e[t >> 2] |= this.padding[3 & t], this.lastByteIndex === this.byteCount) for (e[0] = e[r], t = 1; t < r + 1; ++t) e[t] = 0;
                                for (e[r - 1] |= 2147483648, t = 0; t < r; ++t) n[t] ^= e[t];
                                A(n)
                            }, _.prototype.toString = _.prototype.hex = function () {
                                this.finalize();
                                for (var e, t = this.blockCount, r = this.s, n = this.outputBlocks, i = this.extraBytes, a = 0, s = 0, u = ""; s < n;) {
                                    for (a = 0; a < t && s < n; ++a, ++s) e = r[a], u += o[e >> 4 & 15] + o[15 & e] + o[e >> 12 & 15] + o[e >> 8 & 15] + o[e >> 20 & 15] + o[e >> 16 & 15] + o[e >> 28 & 15] + o[e >> 24 & 15];
                                    s % t == 0 && (A(r), a = 0)
                                }
                                return i && (e = r[a], i > 0 && (u += o[e >> 4 & 15] + o[15 & e]), i > 1 && (u += o[e >> 12 & 15] + o[e >> 8 & 15]), i > 2 && (u += o[e >> 20 & 15] + o[e >> 16 & 15])), u
                            }, _.prototype.arrayBuffer = function () {
                                this.finalize();
                                var e, t = this.blockCount, r = this.s, n = this.outputBlocks, i = this.extraBytes,
                                    a = 0, o = 0, s = this.outputBits >> 3;
                                e = i ? new ArrayBuffer(n + 1 << 2) : new ArrayBuffer(s);
                                for (var u = new Uint32Array(e); o < n;) {
                                    for (a = 0; a < t && o < n; ++a, ++o) u[o] = r[a];
                                    o % t == 0 && A(r)
                                }
                                return i && (u[a] = r[a], e = e.slice(0, s)), e
                            }, _.prototype.buffer = _.prototype.arrayBuffer, _.prototype.digest = _.prototype.array = function () {
                                this.finalize();
                                for (var e, t, r = this.blockCount, n = this.s, i = this.outputBlocks, a = this.extraBytes, o = 0, s = 0, u = []; s < i;) {
                                    for (o = 0; o < r && s < i; ++o, ++s) e = s << 2, t = n[o], u[e] = 255 & t, u[e + 1] = t >> 8 & 255, u[e + 2] = t >> 16 & 255, u[e + 3] = t >> 24 & 255;
                                    s % r == 0 && A(n)
                                }
                                return a && (e = s << 2, t = n[o], a > 0 && (u[e] = 255 & t), a > 1 && (u[e + 1] = t >> 8 & 255), a > 2 && (u[e + 2] = t >> 16 & 255)), u
                            };
                            var A = function (e) {
                                var t, r, n, i, a, o, s, c, f, d, h, l, p, b, v, g, m, y, w, x, _, A, k, S, M, I, E, N,
                                    P, T, O, R, j, C, B, L, W, F, U, D, q, z, H, V, K, G, Y, J, X, Z, $, Q, ee, te, re,
                                    ne, ie, ae, oe, se, ue, ce, fe;
                                for (n = 0; n < 48; n += 2) i = e[0] ^ e[10] ^ e[20] ^ e[30] ^ e[40], a = e[1] ^ e[11] ^ e[21] ^ e[31] ^ e[41], o = e[2] ^ e[12] ^ e[22] ^ e[32] ^ e[42], s = e[3] ^ e[13] ^ e[23] ^ e[33] ^ e[43], c = e[4] ^ e[14] ^ e[24] ^ e[34] ^ e[44], f = e[5] ^ e[15] ^ e[25] ^ e[35] ^ e[45], d = e[6] ^ e[16] ^ e[26] ^ e[36] ^ e[46], h = e[7] ^ e[17] ^ e[27] ^ e[37] ^ e[47], t = (l = e[8] ^ e[18] ^ e[28] ^ e[38] ^ e[48]) ^ (o << 1 | s >>> 31), r = (p = e[9] ^ e[19] ^ e[29] ^ e[39] ^ e[49]) ^ (s << 1 | o >>> 31), e[0] ^= t, e[1] ^= r, e[10] ^= t, e[11] ^= r, e[20] ^= t, e[21] ^= r, e[30] ^= t, e[31] ^= r, e[40] ^= t, e[41] ^= r, t = i ^ (c << 1 | f >>> 31), r = a ^ (f << 1 | c >>> 31), e[2] ^= t, e[3] ^= r, e[12] ^= t, e[13] ^= r, e[22] ^= t, e[23] ^= r, e[32] ^= t, e[33] ^= r, e[42] ^= t, e[43] ^= r, t = o ^ (d << 1 | h >>> 31), r = s ^ (h << 1 | d >>> 31), e[4] ^= t, e[5] ^= r, e[14] ^= t, e[15] ^= r, e[24] ^= t, e[25] ^= r, e[34] ^= t, e[35] ^= r, e[44] ^= t, e[45] ^= r, t = c ^ (l << 1 | p >>> 31), r = f ^ (p << 1 | l >>> 31), e[6] ^= t, e[7] ^= r, e[16] ^= t, e[17] ^= r, e[26] ^= t, e[27] ^= r, e[36] ^= t, e[37] ^= r, e[46] ^= t, e[47] ^= r, t = d ^ (i << 1 | a >>> 31), r = h ^ (a << 1 | i >>> 31), e[8] ^= t, e[9] ^= r, e[18] ^= t, e[19] ^= r, e[28] ^= t, e[29] ^= r, e[38] ^= t, e[39] ^= r, e[48] ^= t, e[49] ^= r, b = e[0], v = e[1], G = e[11] << 4 | e[10] >>> 28, Y = e[10] << 4 | e[11] >>> 28, N = e[20] << 3 | e[21] >>> 29, P = e[21] << 3 | e[20] >>> 29, se = e[31] << 9 | e[30] >>> 23, ue = e[30] << 9 | e[31] >>> 23, z = e[40] << 18 | e[41] >>> 14, H = e[41] << 18 | e[40] >>> 14, C = e[2] << 1 | e[3] >>> 31, B = e[3] << 1 | e[2] >>> 31, g = e[13] << 12 | e[12] >>> 20, m = e[12] << 12 | e[13] >>> 20, J = e[22] << 10 | e[23] >>> 22, X = e[23] << 10 | e[22] >>> 22, T = e[33] << 13 | e[32] >>> 19, O = e[32] << 13 | e[33] >>> 19, ce = e[42] << 2 | e[43] >>> 30, fe = e[43] << 2 | e[42] >>> 30, te = e[5] << 30 | e[4] >>> 2, re = e[4] << 30 | e[5] >>> 2, L = e[14] << 6 | e[15] >>> 26, W = e[15] << 6 | e[14] >>> 26, y = e[25] << 11 | e[24] >>> 21, w = e[24] << 11 | e[25] >>> 21, Z = e[34] << 15 | e[35] >>> 17, $ = e[35] << 15 | e[34] >>> 17, R = e[45] << 29 | e[44] >>> 3, j = e[44] << 29 | e[45] >>> 3, S = e[6] << 28 | e[7] >>> 4, M = e[7] << 28 | e[6] >>> 4, ne = e[17] << 23 | e[16] >>> 9,ie = e[16] << 23 | e[17] >>> 9,F = e[26] << 25 | e[27] >>> 7,U = e[27] << 25 | e[26] >>> 7,x = e[36] << 21 | e[37] >>> 11,_ = e[37] << 21 | e[36] >>> 11,Q = e[47] << 24 | e[46] >>> 8,ee = e[46] << 24 | e[47] >>> 8,V = e[8] << 27 | e[9] >>> 5,K = e[9] << 27 | e[8] >>> 5,I = e[18] << 20 | e[19] >>> 12,E = e[19] << 20 | e[18] >>> 12,ae = e[29] << 7 | e[28] >>> 25,oe = e[28] << 7 | e[29] >>> 25,D = e[38] << 8 | e[39] >>> 24,q = e[39] << 8 | e[38] >>> 24,A = e[48] << 14 | e[49] >>> 18,k = e[49] << 14 | e[48] >>> 18,e[0] = b ^ ~g & y,e[1] = v ^ ~m & w,e[10] = S ^ ~I & N,e[11] = M ^ ~E & P,e[20] = C ^ ~L & F,e[21] = B ^ ~W & U,e[30] = V ^ ~G & J,e[31] = K ^ ~Y & X,e[40] = te ^ ~ne & ae,e[41] = re ^ ~ie & oe,e[2] = g ^ ~y & x,e[3] = m ^ ~w & _,e[12] = I ^ ~N & T,e[13] = E ^ ~P & O,e[22] = L ^ ~F & D,e[23] = W ^ ~U & q,e[32] = G ^ ~J & Z,e[33] = Y ^ ~X & $,e[42] = ne ^ ~ae & se,e[43] = ie ^ ~oe & ue,e[4] = y ^ ~x & A,e[5] = w ^ ~_ & k,e[14] = N ^ ~T & R,e[15] = P ^ ~O & j,e[24] = F ^ ~D & z,e[25] = U ^ ~q & H,e[34] = J ^ ~Z & Q,e[35] = X ^ ~$ & ee,e[44] = ae ^ ~se & ce,e[45] = oe ^ ~ue & fe,e[6] = x ^ ~A & b,e[7] = _ ^ ~k & v,e[16] = T ^ ~R & S,e[17] = O ^ ~j & M,e[26] = D ^ ~z & C,e[27] = q ^ ~H & B,e[36] = Z ^ ~Q & V,e[37] = $ ^ ~ee & K,e[46] = se ^ ~ce & te,e[47] = ue ^ ~fe & re,e[8] = A ^ ~b & g,e[9] = k ^ ~v & m,e[18] = R ^ ~S & I,e[19] = j ^ ~M & E,e[28] = z ^ ~C & L,e[29] = H ^ ~B & W,e[38] = Q ^ ~V & G,e[39] = ee ^ ~K & Y,e[48] = ce ^ ~te & ne,e[49] = fe ^ ~re & ie,e[0] ^= u[n],e[1] ^= u[n + 1]
                            };
                            if (a) e.exports = b; else for (g = 0; g < v.length; ++g) i[v[g]] = b[v[g]]
                        }()
                    }).call(this, r(44), r(39))
                }, function (e, t, r) {
                    Object.defineProperty(t, "__esModule", {value: !0}), t.version = "4.0.48"
                }, function (e, t, r) {
                    var n = t;
                    n.utils = r(15), n.common = r(27), n.sha = r(103), n.ripemd = r(107), n.hmac = r(108), n.sha1 = n.sha.sha1, n.sha256 = n.sha.sha256, n.sha224 = n.sha.sha224, n.sha384 = n.sha.sha384, n.sha512 = n.sha.sha512, n.ripemd160 = n.ripemd.ripemd160
                }, function (e, t, r) {
                    t.sha1 = r(104), t.sha224 = r(105), t.sha256 = r(51), t.sha384 = r(106), t.sha512 = r(52)
                }, function (e, t, r) {
                    var n = r(15), i = r(27), a = r(50), o = n.rotl32, s = n.sum32, u = n.sum32_5, c = a.ft_1,
                        f = i.BlockHash, d = [1518500249, 1859775393, 2400959708, 3395469782];

                    function h() {
                        if (!(this instanceof h)) return new h;
                        f.call(this), this.h = [1732584193, 4023233417, 2562383102, 271733878, 3285377520], this.W = new Array(80)
                    }

                    n.inherits(h, f), e.exports = h, h.blockSize = 512, h.outSize = 160, h.hmacStrength = 80, h.padLength = 64, h.prototype._update = function (e, t) {
                        for (var r = this.W, n = 0; n < 16; n++) r[n] = e[t + n];
                        for (; n < r.length; n++) r[n] = o(r[n - 3] ^ r[n - 8] ^ r[n - 14] ^ r[n - 16], 1);
                        var i = this.h[0], a = this.h[1], f = this.h[2], h = this.h[3], l = this.h[4];
                        for (n = 0; n < r.length; n++) {
                            var p = ~~(n / 20), b = u(o(i, 5), c(p, a, f, h), l, r[n], d[p]);
                            l = h, h = f, f = o(a, 30), a = i, i = b
                        }
                        this.h[0] = s(this.h[0], i), this.h[1] = s(this.h[1], a), this.h[2] = s(this.h[2], f), this.h[3] = s(this.h[3], h), this.h[4] = s(this.h[4], l)
                    }, h.prototype._digest = function (e) {
                        return "hex" === e ? n.toHex32(this.h, "big") : n.split32(this.h, "big")
                    }
                }, function (e, t, r) {
                    var n = r(15), i = r(51);

                    function a() {
                        if (!(this instanceof a)) return new a;
                        i.call(this), this.h = [3238371032, 914150663, 812702999, 4144912697, 4290775857, 1750603025, 1694076839, 3204075428]
                    }

                    n.inherits(a, i), e.exports = a, a.blockSize = 512, a.outSize = 224, a.hmacStrength = 192, a.padLength = 64, a.prototype._digest = function (e) {
                        return "hex" === e ? n.toHex32(this.h.slice(0, 7), "big") : n.split32(this.h.slice(0, 7), "big")
                    }
                }, function (e, t, r) {
                    var n = r(15), i = r(52);

                    function a() {
                        if (!(this instanceof a)) return new a;
                        i.call(this), this.h = [3418070365, 3238371032, 1654270250, 914150663, 2438529370, 812702999, 355462360, 4144912697, 1731405415, 4290775857, 2394180231, 1750603025, 3675008525, 1694076839, 1203062813, 3204075428]
                    }

                    n.inherits(a, i), e.exports = a, a.blockSize = 1024, a.outSize = 384, a.hmacStrength = 192, a.padLength = 128, a.prototype._digest = function (e) {
                        return "hex" === e ? n.toHex32(this.h.slice(0, 12), "big") : n.split32(this.h.slice(0, 12), "big")
                    }
                }, function (e, t, r) {
                    var n = r(15), i = r(27), a = n.rotl32, o = n.sum32, s = n.sum32_3, u = n.sum32_4, c = i.BlockHash;

                    function f() {
                        if (!(this instanceof f)) return new f;
                        c.call(this), this.h = [1732584193, 4023233417, 2562383102, 271733878, 3285377520], this.endian = "little"
                    }

                    function d(e, t, r, n) {
                        return e <= 15 ? t ^ r ^ n : e <= 31 ? t & r | ~t & n : e <= 47 ? (t | ~r) ^ n : e <= 63 ? t & n | r & ~n : t ^ (r | ~n)
                    }

                    function h(e) {
                        return e <= 15 ? 0 : e <= 31 ? 1518500249 : e <= 47 ? 1859775393 : e <= 63 ? 2400959708 : 2840853838
                    }

                    function l(e) {
                        return e <= 15 ? 1352829926 : e <= 31 ? 1548603684 : e <= 47 ? 1836072691 : e <= 63 ? 2053994217 : 0
                    }

                    n.inherits(f, c), t.ripemd160 = f, f.blockSize = 512, f.outSize = 160, f.hmacStrength = 192, f.padLength = 64, f.prototype._update = function (e, t) {
                        for (var r = this.h[0], n = this.h[1], i = this.h[2], c = this.h[3], f = this.h[4], m = r, y = n, w = i, x = c, _ = f, A = 0; A < 80; A++) {
                            var k = o(a(u(r, d(A, n, i, c), e[p[A] + t], h(A)), v[A]), f);
                            r = f, f = c, c = a(i, 10), i = n, n = k, k = o(a(u(m, d(79 - A, y, w, x), e[b[A] + t], l(A)), g[A]), _), m = _, _ = x, x = a(w, 10), w = y, y = k
                        }
                        k = s(this.h[1], i, x), this.h[1] = s(this.h[2], c, _), this.h[2] = s(this.h[3], f, m), this.h[3] = s(this.h[4], r, y), this.h[4] = s(this.h[0], n, w), this.h[0] = k
                    }, f.prototype._digest = function (e) {
                        return "hex" === e ? n.toHex32(this.h, "little") : n.split32(this.h, "little")
                    };
                    var p = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 7, 4, 13, 1, 10, 6, 15, 3, 12, 0, 9, 5, 2, 14, 11, 8, 3, 10, 14, 4, 9, 15, 8, 1, 2, 7, 0, 6, 13, 11, 5, 12, 1, 9, 11, 10, 0, 8, 12, 4, 13, 3, 7, 15, 14, 5, 6, 2, 4, 0, 5, 9, 7, 12, 2, 10, 14, 1, 3, 8, 11, 6, 15, 13],
                        b = [5, 14, 7, 0, 9, 2, 11, 4, 13, 6, 15, 8, 1, 10, 3, 12, 6, 11, 3, 7, 0, 13, 5, 10, 14, 15, 8, 12, 4, 9, 1, 2, 15, 5, 1, 3, 7, 14, 6, 9, 11, 8, 12, 2, 10, 0, 4, 13, 8, 6, 4, 1, 3, 11, 15, 0, 5, 12, 2, 13, 9, 7, 10, 14, 12, 15, 10, 4, 1, 5, 8, 7, 6, 2, 13, 14, 0, 3, 9, 11],
                        v = [11, 14, 15, 12, 5, 8, 7, 9, 11, 13, 14, 15, 6, 7, 9, 8, 7, 6, 8, 13, 11, 9, 7, 15, 7, 12, 15, 9, 11, 7, 13, 12, 11, 13, 6, 7, 14, 9, 13, 15, 14, 8, 13, 6, 5, 12, 7, 5, 11, 12, 14, 15, 14, 15, 9, 8, 9, 14, 5, 6, 8, 6, 5, 12, 9, 15, 5, 11, 6, 8, 13, 12, 5, 12, 13, 14, 11, 8, 5, 6],
                        g = [8, 9, 9, 11, 13, 15, 15, 5, 7, 7, 8, 11, 14, 14, 12, 6, 9, 13, 15, 7, 12, 8, 9, 11, 7, 7, 12, 7, 6, 15, 13, 11, 9, 7, 15, 11, 8, 6, 6, 14, 12, 13, 5, 14, 13, 13, 7, 5, 15, 5, 8, 11, 14, 14, 6, 14, 6, 9, 12, 9, 12, 5, 15, 8, 8, 5, 12, 9, 12, 5, 14, 6, 8, 13, 6, 5, 15, 13, 11, 11]
                }, function (e, t, r) {
                    var n = r(15), i = r(12);

                    function a(e, t, r) {
                        if (!(this instanceof a)) return new a(e, t, r);
                        this.Hash = e, this.blockSize = e.blockSize / 8, this.outSize = e.outSize / 8, this.inner = null, this.outer = null, this._init(n.toArray(t, r))
                    }

                    e.exports = a, a.prototype._init = function (e) {
                        e.length > this.blockSize && (e = (new this.Hash).update(e).digest()), i(e.length <= this.blockSize);
                        for (var t = e.length; t < this.blockSize; t++) e.push(0);
                        for (t = 0; t < e.length; t++) e[t] ^= 54;
                        for (this.inner = (new this.Hash).update(e), t = 0; t < e.length; t++) e[t] ^= 106;
                        this.outer = (new this.Hash).update(e)
                    }, a.prototype.update = function (e, t) {
                        return this.inner.update(e, t), this
                    }, a.prototype.digest = function (e) {
                        return this.outer.update(this.inner.digest()), this.outer.digest(e)
                    }
                }, function (e, t) {
                    e.exports = function (e) {
                        return e.webpackPolyfill || (e.deprecate = function () {
                        }, e.paths = [], e.children || (e.children = []), Object.defineProperty(e, "loaded", {
                            enumerable: !0,
                            get: function () {
                                return e.l
                            }
                        }), Object.defineProperty(e, "id", {
                            enumerable: !0, get: function () {
                                return e.i
                            }
                        }), e.webpackPolyfill = 1), e
                    }
                }, function (e, t) {
                }, function (e, t, r) {
                    Object.defineProperty(t, "__esModule", {value: !0});
                    var n = r(18);

                    function i(e) {
                        for (var t = []; e;) t.unshift(255 & e), e >>= 8;
                        return t
                    }

                    function a(e, t, r) {
                        for (var n = 0, i = 0; i < r; i++) n = 256 * n + e[t + i];
                        return n
                    }

                    function o(e, t, r, n) {
                        for (var i = []; r < t + 1 + n;) {
                            var a = s(e, r);
                            if (i.push(a.result), (r += a.consumed) > t + 1 + n) throw new Error("invalid rlp")
                        }
                        return {consumed: 1 + n, result: i}
                    }

                    function s(e, t) {
                        if (0 === e.length) throw new Error("invalid rlp data");
                        if (e[t] >= 248) {
                            if (t + 1 + (r = e[t] - 247) > e.length) throw new Error("too short");
                            if (t + 1 + r + (i = a(e, t + 1, r)) > e.length) throw new Error("to short");
                            return o(e, t, t + 1 + r, r + i)
                        }
                        if (e[t] >= 192) {
                            if (t + 1 + (i = e[t] - 192) > e.length) throw new Error("invalid rlp data");
                            return o(e, t, t + 1, i)
                        }
                        if (e[t] >= 184) {
                            var r;
                            if (t + 1 + (r = e[t] - 183) > e.length) throw new Error("invalid rlp data");
                            if (t + 1 + r + (i = a(e, t + 1, r)) > e.length) throw new Error("invalid rlp data");
                            return {consumed: 1 + r + i, result: n.hexlify(e.slice(t + 1 + r, t + 1 + r + i))}
                        }
                        if (e[t] >= 128) {
                            var i;
                            if (t + 1 + (i = e[t] - 128) > e.length) throw new Error("invalid rlp data");
                            return {consumed: 1 + i, result: n.hexlify(e.slice(t + 1, t + 1 + i))}
                        }
                        return {consumed: 1, result: n.hexlify(e[t])}
                    }

                    t.encode = function (e) {
                        return n.hexlify(function e(t) {
                            if (Array.isArray(t)) {
                                var r = [];
                                return t.forEach(function (t) {
                                    r = r.concat(e(t))
                                }), r.length <= 55 ? (r.unshift(192 + r.length), r) : ((a = i(r.length)).unshift(247 + a.length), a.concat(r))
                            }
                            var a, o = Array.prototype.slice.call(n.arrayify(t));
                            return 1 === o.length && o[0] <= 127 ? o : o.length <= 55 ? (o.unshift(128 + o.length), o) : ((a = i(o.length)).unshift(183 + a.length), a.concat(o))
                        }(e))
                    }, t.decode = function (e) {
                        var t = n.arrayify(e), r = s(t, 0);
                        if (r.consumed !== t.length) throw new Error("invalid rlp data");
                        return r.result
                    }
                }, function (e, t, r) {
                    var n = t;
                    n.version = r(113).version, n.utils = r(13), n.rand = r(59), n.curve = r(60), n.curves = r(32), n.ec = r(125), n.eddsa = r(129)
                }, function (e) {
                    e.exports = JSON.parse('{"name":"elliptic","version":"6.5.3","description":"EC cryptography","main":"lib/elliptic.js","files":["lib"],"scripts":{"jscs":"jscs benchmarks/*.js lib/*.js lib/**/*.js lib/**/**/*.js test/index.js","jshint":"jscs benchmarks/*.js lib/*.js lib/**/*.js lib/**/**/*.js test/index.js","lint":"npm run jscs && npm run jshint","unit":"istanbul test _mocha --reporter=spec test/index.js","test":"npm run lint && npm run unit","version":"grunt dist && git add dist/"},"repository":{"type":"git","url":"git@github.com:indutny/elliptic"},"keywords":["EC","Elliptic","curve","Cryptography"],"author":"Fedor Indutny <fedor@indutny.com>","license":"MIT","bugs":{"url":"https://github.com/indutny/elliptic/issues"},"homepage":"https://github.com/indutny/elliptic","devDependencies":{"brfs":"^1.4.3","coveralls":"^3.0.8","grunt":"^1.0.4","grunt-browserify":"^5.0.0","grunt-cli":"^1.2.0","grunt-contrib-connect":"^1.0.0","grunt-contrib-copy":"^1.0.0","grunt-contrib-uglify":"^1.0.1","grunt-mocha-istanbul":"^3.0.1","grunt-saucelabs":"^9.0.1","istanbul":"^0.4.2","jscs":"^3.0.7","jshint":"^2.10.3","mocha":"^6.2.2"},"dependencies":{"bn.js":"^4.4.0","brorand":"^1.0.1","hash.js":"^1.0.0","hmac-drbg":"^1.0.0","inherits":"^2.0.1","minimalistic-assert":"^1.0.0","minimalistic-crypto-utils":"^1.0.0"}}')
                }, function (e, t) {
                }, function (e, t, r) {
                    var n = r(13), i = r(14), a = r(26), o = r(30), s = n.assert;

                    function u(e) {
                        o.call(this, "short", e), this.a = new i(e.a, 16).toRed(this.red), this.b = new i(e.b, 16).toRed(this.red), this.tinv = this.two.redInvm(), this.zeroA = 0 === this.a.fromRed().cmpn(0), this.threeA = 0 === this.a.fromRed().sub(this.p).cmpn(-3), this.endo = this._getEndomorphism(e), this._endoWnafT1 = new Array(4), this._endoWnafT2 = new Array(4)
                    }

                    function c(e, t, r, n) {
                        o.BasePoint.call(this, e, "affine"), null === t && null === r ? (this.x = null, this.y = null, this.inf = !0) : (this.x = new i(t, 16), this.y = new i(r, 16), n && (this.x.forceRed(this.curve.red), this.y.forceRed(this.curve.red)), this.x.red || (this.x = this.x.toRed(this.curve.red)), this.y.red || (this.y = this.y.toRed(this.curve.red)), this.inf = !1)
                    }

                    function f(e, t, r, n) {
                        o.BasePoint.call(this, e, "jacobian"), null === t && null === r && null === n ? (this.x = this.curve.one, this.y = this.curve.one, this.z = new i(0)) : (this.x = new i(t, 16), this.y = new i(r, 16), this.z = new i(n, 16)), this.x.red || (this.x = this.x.toRed(this.curve.red)), this.y.red || (this.y = this.y.toRed(this.curve.red)), this.z.red || (this.z = this.z.toRed(this.curve.red)), this.zOne = this.z === this.curve.one
                    }

                    a(u, o), e.exports = u, u.prototype._getEndomorphism = function (e) {
                        if (this.zeroA && this.g && this.n && 1 === this.p.modn(3)) {
                            var t, r;
                            if (e.beta) t = new i(e.beta, 16).toRed(this.red); else {
                                var n = this._getEndoRoots(this.p);
                                t = (t = n[0].cmp(n[1]) < 0 ? n[0] : n[1]).toRed(this.red)
                            }
                            if (e.lambda) r = new i(e.lambda, 16); else {
                                var a = this._getEndoRoots(this.n);
                                0 === this.g.mul(a[0]).x.cmp(this.g.x.redMul(t)) ? r = a[0] : (r = a[1], s(0 === this.g.mul(r).x.cmp(this.g.x.redMul(t))))
                            }
                            return {
                                beta: t, lambda: r, basis: e.basis ? e.basis.map(function (e) {
                                    return {a: new i(e.a, 16), b: new i(e.b, 16)}
                                }) : this._getEndoBasis(r)
                            }
                        }
                    }, u.prototype._getEndoRoots = function (e) {
                        var t = e === this.p ? this.red : i.mont(e), r = new i(2).toRed(t).redInvm(), n = r.redNeg(),
                            a = new i(3).toRed(t).redNeg().redSqrt().redMul(r);
                        return [n.redAdd(a).fromRed(), n.redSub(a).fromRed()]
                    }, u.prototype._getEndoBasis = function (e) {
                        for (var t, r, n, a, o, s, u, c, f, d = this.n.ushrn(Math.floor(this.n.bitLength() / 2)), h = e, l = this.n.clone(), p = new i(1), b = new i(0), v = new i(0), g = new i(1), m = 0; 0 !== h.cmpn(0);) {
                            var y = l.div(h);
                            c = l.sub(y.mul(h)), f = v.sub(y.mul(p));
                            var w = g.sub(y.mul(b));
                            if (!n && c.cmp(d) < 0) t = u.neg(), r = p, n = c.neg(), a = f; else if (n && 2 == ++m) break;
                            u = c, l = h, h = c, v = p, p = f, g = b, b = w
                        }
                        o = c.neg(), s = f;
                        var x = n.sqr().add(a.sqr());
                        return o.sqr().add(s.sqr()).cmp(x) >= 0 && (o = t, s = r), n.negative && (n = n.neg(), a = a.neg()), o.negative && (o = o.neg(), s = s.neg()), [{
                            a: n,
                            b: a
                        }, {a: o, b: s}]
                    }, u.prototype._endoSplit = function (e) {
                        var t = this.endo.basis, r = t[0], n = t[1], i = n.b.mul(e).divRound(this.n),
                            a = r.b.neg().mul(e).divRound(this.n), o = i.mul(r.a), s = a.mul(n.a), u = i.mul(r.b),
                            c = a.mul(n.b);
                        return {k1: e.sub(o).sub(s), k2: u.add(c).neg()}
                    }, u.prototype.pointFromX = function (e, t) {
                        (e = new i(e, 16)).red || (e = e.toRed(this.red));
                        var r = e.redSqr().redMul(e).redIAdd(e.redMul(this.a)).redIAdd(this.b), n = r.redSqrt();
                        if (0 !== n.redSqr().redSub(r).cmp(this.zero)) throw new Error("invalid point");
                        var a = n.fromRed().isOdd();
                        return (t && !a || !t && a) && (n = n.redNeg()), this.point(e, n)
                    }, u.prototype.validate = function (e) {
                        if (e.inf) return !0;
                        var t = e.x, r = e.y, n = this.a.redMul(t), i = t.redSqr().redMul(t).redIAdd(n).redIAdd(this.b);
                        return 0 === r.redSqr().redISub(i).cmpn(0)
                    }, u.prototype._endoWnafMulAdd = function (e, t, r) {
                        for (var n = this._endoWnafT1, i = this._endoWnafT2, a = 0; a < e.length; a++) {
                            var o = this._endoSplit(t[a]), s = e[a], u = s._getBeta();
                            o.k1.negative && (o.k1.ineg(), s = s.neg(!0)), o.k2.negative && (o.k2.ineg(), u = u.neg(!0)), n[2 * a] = s, n[2 * a + 1] = u, i[2 * a] = o.k1, i[2 * a + 1] = o.k2
                        }
                        for (var c = this._wnafMulAdd(1, n, i, 2 * a, r), f = 0; f < 2 * a; f++) n[f] = null, i[f] = null;
                        return c
                    }, a(c, o.BasePoint), u.prototype.point = function (e, t, r) {
                        return new c(this, e, t, r)
                    }, u.prototype.pointFromJSON = function (e, t) {
                        return c.fromJSON(this, e, t)
                    }, c.prototype._getBeta = function () {
                        if (this.curve.endo) {
                            var e = this.precomputed;
                            if (e && e.beta) return e.beta;
                            var t = this.curve.point(this.x.redMul(this.curve.endo.beta), this.y);
                            if (e) {
                                var r = this.curve, n = function (e) {
                                    return r.point(e.x.redMul(r.endo.beta), e.y)
                                };
                                e.beta = t, t.precomputed = {
                                    beta: null,
                                    naf: e.naf && {wnd: e.naf.wnd, points: e.naf.points.map(n)},
                                    doubles: e.doubles && {step: e.doubles.step, points: e.doubles.points.map(n)}
                                }
                            }
                            return t
                        }
                    }, c.prototype.toJSON = function () {
                        return this.precomputed ? [this.x, this.y, this.precomputed && {
                            doubles: this.precomputed.doubles && {
                                step: this.precomputed.doubles.step,
                                points: this.precomputed.doubles.points.slice(1)
                            },
                            naf: this.precomputed.naf && {
                                wnd: this.precomputed.naf.wnd,
                                points: this.precomputed.naf.points.slice(1)
                            }
                        }] : [this.x, this.y]
                    }, c.fromJSON = function (e, t, r) {
                        "string" == typeof t && (t = JSON.parse(t));
                        var n = e.point(t[0], t[1], r);
                        if (!t[2]) return n;

                        function i(t) {
                            return e.point(t[0], t[1], r)
                        }

                        var a = t[2];
                        return n.precomputed = {
                            beta: null,
                            doubles: a.doubles && {step: a.doubles.step, points: [n].concat(a.doubles.points.map(i))},
                            naf: a.naf && {wnd: a.naf.wnd, points: [n].concat(a.naf.points.map(i))}
                        }, n
                    }, c.prototype.inspect = function () {
                        return this.isInfinity() ? "<EC Point Infinity>" : "<EC Point x: " + this.x.fromRed().toString(16, 2) + " y: " + this.y.fromRed().toString(16, 2) + ">"
                    }, c.prototype.isInfinity = function () {
                        return this.inf
                    }, c.prototype.add = function (e) {
                        if (this.inf) return e;
                        if (e.inf) return this;
                        if (this.eq(e)) return this.dbl();
                        if (this.neg().eq(e)) return this.curve.point(null, null);
                        if (0 === this.x.cmp(e.x)) return this.curve.point(null, null);
                        var t = this.y.redSub(e.y);
                        0 !== t.cmpn(0) && (t = t.redMul(this.x.redSub(e.x).redInvm()));
                        var r = t.redSqr().redISub(this.x).redISub(e.x), n = t.redMul(this.x.redSub(r)).redISub(this.y);
                        return this.curve.point(r, n)
                    }, c.prototype.dbl = function () {
                        if (this.inf) return this;
                        var e = this.y.redAdd(this.y);
                        if (0 === e.cmpn(0)) return this.curve.point(null, null);
                        var t = this.curve.a, r = this.x.redSqr(), n = e.redInvm(),
                            i = r.redAdd(r).redIAdd(r).redIAdd(t).redMul(n),
                            a = i.redSqr().redISub(this.x.redAdd(this.x)),
                            o = i.redMul(this.x.redSub(a)).redISub(this.y);
                        return this.curve.point(a, o)
                    }, c.prototype.getX = function () {
                        return this.x.fromRed()
                    }, c.prototype.getY = function () {
                        return this.y.fromRed()
                    }, c.prototype.mul = function (e) {
                        return e = new i(e, 16), this.isInfinity() ? this : this._hasDoubles(e) ? this.curve._fixedNafMul(this, e) : this.curve.endo ? this.curve._endoWnafMulAdd([this], [e]) : this.curve._wnafMul(this, e)
                    }, c.prototype.mulAdd = function (e, t, r) {
                        var n = [this, t], i = [e, r];
                        return this.curve.endo ? this.curve._endoWnafMulAdd(n, i) : this.curve._wnafMulAdd(1, n, i, 2)
                    }, c.prototype.jmulAdd = function (e, t, r) {
                        var n = [this, t], i = [e, r];
                        return this.curve.endo ? this.curve._endoWnafMulAdd(n, i, !0) : this.curve._wnafMulAdd(1, n, i, 2, !0)
                    }, c.prototype.eq = function (e) {
                        return this === e || this.inf === e.inf && (this.inf || 0 === this.x.cmp(e.x) && 0 === this.y.cmp(e.y))
                    }, c.prototype.neg = function (e) {
                        if (this.inf) return this;
                        var t = this.curve.point(this.x, this.y.redNeg());
                        if (e && this.precomputed) {
                            var r = this.precomputed, n = function (e) {
                                return e.neg()
                            };
                            t.precomputed = {
                                naf: r.naf && {wnd: r.naf.wnd, points: r.naf.points.map(n)},
                                doubles: r.doubles && {step: r.doubles.step, points: r.doubles.points.map(n)}
                            }
                        }
                        return t
                    }, c.prototype.toJ = function () {
                        return this.inf ? this.curve.jpoint(null, null, null) : this.curve.jpoint(this.x, this.y, this.curve.one)
                    }, a(f, o.BasePoint), u.prototype.jpoint = function (e, t, r) {
                        return new f(this, e, t, r)
                    }, f.prototype.toP = function () {
                        if (this.isInfinity()) return this.curve.point(null, null);
                        var e = this.z.redInvm(), t = e.redSqr(), r = this.x.redMul(t), n = this.y.redMul(t).redMul(e);
                        return this.curve.point(r, n)
                    }, f.prototype.neg = function () {
                        return this.curve.jpoint(this.x, this.y.redNeg(), this.z)
                    }, f.prototype.add = function (e) {
                        if (this.isInfinity()) return e;
                        if (e.isInfinity()) return this;
                        var t = e.z.redSqr(), r = this.z.redSqr(), n = this.x.redMul(t), i = e.x.redMul(r),
                            a = this.y.redMul(t.redMul(e.z)), o = e.y.redMul(r.redMul(this.z)), s = n.redSub(i),
                            u = a.redSub(o);
                        if (0 === s.cmpn(0)) return 0 !== u.cmpn(0) ? this.curve.jpoint(null, null, null) : this.dbl();
                        var c = s.redSqr(), f = c.redMul(s), d = n.redMul(c),
                            h = u.redSqr().redIAdd(f).redISub(d).redISub(d),
                            l = u.redMul(d.redISub(h)).redISub(a.redMul(f)), p = this.z.redMul(e.z).redMul(s);
                        return this.curve.jpoint(h, l, p)
                    }, f.prototype.mixedAdd = function (e) {
                        if (this.isInfinity()) return e.toJ();
                        if (e.isInfinity()) return this;
                        var t = this.z.redSqr(), r = this.x, n = e.x.redMul(t), i = this.y,
                            a = e.y.redMul(t).redMul(this.z), o = r.redSub(n), s = i.redSub(a);
                        if (0 === o.cmpn(0)) return 0 !== s.cmpn(0) ? this.curve.jpoint(null, null, null) : this.dbl();
                        var u = o.redSqr(), c = u.redMul(o), f = r.redMul(u),
                            d = s.redSqr().redIAdd(c).redISub(f).redISub(f),
                            h = s.redMul(f.redISub(d)).redISub(i.redMul(c)), l = this.z.redMul(o);
                        return this.curve.jpoint(d, h, l)
                    }, f.prototype.dblp = function (e) {
                        if (0 === e) return this;
                        if (this.isInfinity()) return this;
                        if (!e) return this.dbl();
                        if (this.curve.zeroA || this.curve.threeA) {
                            for (var t = this, r = 0; r < e; r++) t = t.dbl();
                            return t
                        }
                        var n = this.curve.a, i = this.curve.tinv, a = this.x, o = this.y, s = this.z,
                            u = s.redSqr().redSqr(), c = o.redAdd(o);
                        for (r = 0; r < e; r++) {
                            var f = a.redSqr(), d = c.redSqr(), h = d.redSqr(),
                                l = f.redAdd(f).redIAdd(f).redIAdd(n.redMul(u)), p = a.redMul(d),
                                b = l.redSqr().redISub(p.redAdd(p)), v = p.redISub(b), g = l.redMul(v);
                            g = g.redIAdd(g).redISub(h);
                            var m = c.redMul(s);
                            r + 1 < e && (u = u.redMul(h)), a = b, s = m, c = g
                        }
                        return this.curve.jpoint(a, c.redMul(i), s)
                    }, f.prototype.dbl = function () {
                        return this.isInfinity() ? this : this.curve.zeroA ? this._zeroDbl() : this.curve.threeA ? this._threeDbl() : this._dbl()
                    }, f.prototype._zeroDbl = function () {
                        var e, t, r;
                        if (this.zOne) {
                            var n = this.x.redSqr(), i = this.y.redSqr(), a = i.redSqr(),
                                o = this.x.redAdd(i).redSqr().redISub(n).redISub(a);
                            o = o.redIAdd(o);
                            var s = n.redAdd(n).redIAdd(n), u = s.redSqr().redISub(o).redISub(o), c = a.redIAdd(a);
                            c = (c = c.redIAdd(c)).redIAdd(c), e = u, t = s.redMul(o.redISub(u)).redISub(c), r = this.y.redAdd(this.y)
                        } else {
                            var f = this.x.redSqr(), d = this.y.redSqr(), h = d.redSqr(),
                                l = this.x.redAdd(d).redSqr().redISub(f).redISub(h);
                            l = l.redIAdd(l);
                            var p = f.redAdd(f).redIAdd(f), b = p.redSqr(), v = h.redIAdd(h);
                            v = (v = v.redIAdd(v)).redIAdd(v), e = b.redISub(l).redISub(l), t = p.redMul(l.redISub(e)).redISub(v), r = (r = this.y.redMul(this.z)).redIAdd(r)
                        }
                        return this.curve.jpoint(e, t, r)
                    }, f.prototype._threeDbl = function () {
                        var e, t, r;
                        if (this.zOne) {
                            var n = this.x.redSqr(), i = this.y.redSqr(), a = i.redSqr(),
                                o = this.x.redAdd(i).redSqr().redISub(n).redISub(a);
                            o = o.redIAdd(o);
                            var s = n.redAdd(n).redIAdd(n).redIAdd(this.curve.a), u = s.redSqr().redISub(o).redISub(o);
                            e = u;
                            var c = a.redIAdd(a);
                            c = (c = c.redIAdd(c)).redIAdd(c), t = s.redMul(o.redISub(u)).redISub(c), r = this.y.redAdd(this.y)
                        } else {
                            var f = this.z.redSqr(), d = this.y.redSqr(), h = this.x.redMul(d),
                                l = this.x.redSub(f).redMul(this.x.redAdd(f));
                            l = l.redAdd(l).redIAdd(l);
                            var p = h.redIAdd(h), b = (p = p.redIAdd(p)).redAdd(p);
                            e = l.redSqr().redISub(b), r = this.y.redAdd(this.z).redSqr().redISub(d).redISub(f);
                            var v = d.redSqr();
                            v = (v = (v = v.redIAdd(v)).redIAdd(v)).redIAdd(v), t = l.redMul(p.redISub(e)).redISub(v)
                        }
                        return this.curve.jpoint(e, t, r)
                    }, f.prototype._dbl = function () {
                        var e = this.curve.a, t = this.x, r = this.y, n = this.z, i = n.redSqr().redSqr(),
                            a = t.redSqr(), o = r.redSqr(), s = a.redAdd(a).redIAdd(a).redIAdd(e.redMul(i)),
                            u = t.redAdd(t), c = (u = u.redIAdd(u)).redMul(o), f = s.redSqr().redISub(c.redAdd(c)),
                            d = c.redISub(f), h = o.redSqr();
                        h = (h = (h = h.redIAdd(h)).redIAdd(h)).redIAdd(h);
                        var l = s.redMul(d).redISub(h), p = r.redAdd(r).redMul(n);
                        return this.curve.jpoint(f, l, p)
                    }, f.prototype.trpl = function () {
                        if (!this.curve.zeroA) return this.dbl().add(this);
                        var e = this.x.redSqr(), t = this.y.redSqr(), r = this.z.redSqr(), n = t.redSqr(),
                            i = e.redAdd(e).redIAdd(e), a = i.redSqr(),
                            o = this.x.redAdd(t).redSqr().redISub(e).redISub(n),
                            s = (o = (o = (o = o.redIAdd(o)).redAdd(o).redIAdd(o)).redISub(a)).redSqr(),
                            u = n.redIAdd(n);
                        u = (u = (u = u.redIAdd(u)).redIAdd(u)).redIAdd(u);
                        var c = i.redIAdd(o).redSqr().redISub(a).redISub(s).redISub(u), f = t.redMul(c);
                        f = (f = f.redIAdd(f)).redIAdd(f);
                        var d = this.x.redMul(s).redISub(f);
                        d = (d = d.redIAdd(d)).redIAdd(d);
                        var h = this.y.redMul(c.redMul(u.redISub(c)).redISub(o.redMul(s)));
                        h = (h = (h = h.redIAdd(h)).redIAdd(h)).redIAdd(h);
                        var l = this.z.redAdd(o).redSqr().redISub(r).redISub(s);
                        return this.curve.jpoint(d, h, l)
                    }, f.prototype.mul = function (e, t) {
                        return e = new i(e, t), this.curve._wnafMul(this, e)
                    }, f.prototype.eq = function (e) {
                        if ("affine" === e.type) return this.eq(e.toJ());
                        if (this === e) return !0;
                        var t = this.z.redSqr(), r = e.z.redSqr();
                        if (0 !== this.x.redMul(r).redISub(e.x.redMul(t)).cmpn(0)) return !1;
                        var n = t.redMul(this.z), i = r.redMul(e.z);
                        return 0 === this.y.redMul(i).redISub(e.y.redMul(n)).cmpn(0)
                    }, f.prototype.eqXToP = function (e) {
                        var t = this.z.redSqr(), r = e.toRed(this.curve.red).redMul(t);
                        if (0 === this.x.cmp(r)) return !0;
                        for (var n = e.clone(), i = this.curve.redN.redMul(t); ;) {
                            if (n.iadd(this.curve.n), n.cmp(this.curve.p) >= 0) return !1;
                            if (r.redIAdd(i), 0 === this.x.cmp(r)) return !0
                        }
                    }, f.prototype.inspect = function () {
                        return this.isInfinity() ? "<EC JPoint Infinity>" : "<EC JPoint x: " + this.x.toString(16, 2) + " y: " + this.y.toString(16, 2) + " z: " + this.z.toString(16, 2) + ">"
                    }, f.prototype.isInfinity = function () {
                        return 0 === this.z.cmpn(0)
                    }
                }, function (e, t, r) {
                    var n = r(14), i = r(26), a = r(30), o = r(13);

                    function s(e) {
                        a.call(this, "mont", e), this.a = new n(e.a, 16).toRed(this.red), this.b = new n(e.b, 16).toRed(this.red), this.i4 = new n(4).toRed(this.red).redInvm(), this.two = new n(2).toRed(this.red), this.a24 = this.i4.redMul(this.a.redAdd(this.two))
                    }

                    function u(e, t, r) {
                        a.BasePoint.call(this, e, "projective"), null === t && null === r ? (this.x = this.curve.one, this.z = this.curve.zero) : (this.x = new n(t, 16), this.z = new n(r, 16), this.x.red || (this.x = this.x.toRed(this.curve.red)), this.z.red || (this.z = this.z.toRed(this.curve.red)))
                    }

                    i(s, a), e.exports = s, s.prototype.validate = function (e) {
                        var t = e.normalize().x, r = t.redSqr(), n = r.redMul(t).redAdd(r.redMul(this.a)).redAdd(t);
                        return 0 === n.redSqrt().redSqr().cmp(n)
                    }, i(u, a.BasePoint), s.prototype.decodePoint = function (e, t) {
                        return this.point(o.toArray(e, t), 1)
                    }, s.prototype.point = function (e, t) {
                        return new u(this, e, t)
                    }, s.prototype.pointFromJSON = function (e) {
                        return u.fromJSON(this, e)
                    }, u.prototype.precompute = function () {
                    }, u.prototype._encode = function () {
                        return this.getX().toArray("be", this.curve.p.byteLength())
                    }, u.fromJSON = function (e, t) {
                        return new u(e, t[0], t[1] || e.one)
                    }, u.prototype.inspect = function () {
                        return this.isInfinity() ? "<EC Point Infinity>" : "<EC Point x: " + this.x.fromRed().toString(16, 2) + " z: " + this.z.fromRed().toString(16, 2) + ">"
                    }, u.prototype.isInfinity = function () {
                        return 0 === this.z.cmpn(0)
                    }, u.prototype.dbl = function () {
                        var e = this.x.redAdd(this.z).redSqr(), t = this.x.redSub(this.z).redSqr(), r = e.redSub(t),
                            n = e.redMul(t), i = r.redMul(t.redAdd(this.curve.a24.redMul(r)));
                        return this.curve.point(n, i)
                    }, u.prototype.add = function () {
                        throw new Error("Not supported on Montgomery curve")
                    }, u.prototype.diffAdd = function (e, t) {
                        var r = this.x.redAdd(this.z), n = this.x.redSub(this.z), i = e.x.redAdd(e.z),
                            a = e.x.redSub(e.z).redMul(r), o = i.redMul(n), s = t.z.redMul(a.redAdd(o).redSqr()),
                            u = t.x.redMul(a.redISub(o).redSqr());
                        return this.curve.point(s, u)
                    }, u.prototype.mul = function (e) {
                        for (var t = e.clone(), r = this, n = this.curve.point(null, null), i = []; 0 !== t.cmpn(0); t.iushrn(1)) i.push(t.andln(1));
                        for (var a = i.length - 1; a >= 0; a--) 0 === i[a] ? (r = r.diffAdd(n, this), n = n.dbl()) : (n = r.diffAdd(n, this), r = r.dbl());
                        return n
                    }, u.prototype.mulAdd = function () {
                        throw new Error("Not supported on Montgomery curve")
                    }, u.prototype.jumlAdd = function () {
                        throw new Error("Not supported on Montgomery curve")
                    }, u.prototype.eq = function (e) {
                        return 0 === this.getX().cmp(e.getX())
                    }, u.prototype.normalize = function () {
                        return this.x = this.x.redMul(this.z.redInvm()), this.z = this.curve.one, this
                    }, u.prototype.getX = function () {
                        return this.normalize(), this.x.fromRed()
                    }
                }, function (e, t, r) {
                    var n = r(13), i = r(14), a = r(26), o = r(30), s = n.assert;

                    function u(e) {
                        this.twisted = 1 != (0 | e.a), this.mOneA = this.twisted && -1 == (0 | e.a), this.extended = this.mOneA, o.call(this, "edwards", e), this.a = new i(e.a, 16).umod(this.red.m), this.a = this.a.toRed(this.red), this.c = new i(e.c, 16).toRed(this.red), this.c2 = this.c.redSqr(), this.d = new i(e.d, 16).toRed(this.red), this.dd = this.d.redAdd(this.d), s(!this.twisted || 0 === this.c.fromRed().cmpn(1)), this.oneC = 1 == (0 | e.c)
                    }

                    function c(e, t, r, n, a) {
                        o.BasePoint.call(this, e, "projective"), null === t && null === r && null === n ? (this.x = this.curve.zero, this.y = this.curve.one, this.z = this.curve.one, this.t = this.curve.zero, this.zOne = !0) : (this.x = new i(t, 16), this.y = new i(r, 16), this.z = n ? new i(n, 16) : this.curve.one, this.t = a && new i(a, 16), this.x.red || (this.x = this.x.toRed(this.curve.red)), this.y.red || (this.y = this.y.toRed(this.curve.red)), this.z.red || (this.z = this.z.toRed(this.curve.red)), this.t && !this.t.red && (this.t = this.t.toRed(this.curve.red)), this.zOne = this.z === this.curve.one, this.curve.extended && !this.t && (this.t = this.x.redMul(this.y), this.zOne || (this.t = this.t.redMul(this.z.redInvm()))))
                    }

                    a(u, o), e.exports = u, u.prototype._mulA = function (e) {
                        return this.mOneA ? e.redNeg() : this.a.redMul(e)
                    }, u.prototype._mulC = function (e) {
                        return this.oneC ? e : this.c.redMul(e)
                    }, u.prototype.jpoint = function (e, t, r, n) {
                        return this.point(e, t, r, n)
                    }, u.prototype.pointFromX = function (e, t) {
                        (e = new i(e, 16)).red || (e = e.toRed(this.red));
                        var r = e.redSqr(), n = this.c2.redSub(this.a.redMul(r)),
                            a = this.one.redSub(this.c2.redMul(this.d).redMul(r)), o = n.redMul(a.redInvm()),
                            s = o.redSqrt();
                        if (0 !== s.redSqr().redSub(o).cmp(this.zero)) throw new Error("invalid point");
                        var u = s.fromRed().isOdd();
                        return (t && !u || !t && u) && (s = s.redNeg()), this.point(e, s)
                    }, u.prototype.pointFromY = function (e, t) {
                        (e = new i(e, 16)).red || (e = e.toRed(this.red));
                        var r = e.redSqr(), n = r.redSub(this.c2), a = r.redMul(this.d).redMul(this.c2).redSub(this.a),
                            o = n.redMul(a.redInvm());
                        if (0 === o.cmp(this.zero)) {
                            if (t) throw new Error("invalid point");
                            return this.point(this.zero, e)
                        }
                        var s = o.redSqrt();
                        if (0 !== s.redSqr().redSub(o).cmp(this.zero)) throw new Error("invalid point");
                        return s.fromRed().isOdd() !== t && (s = s.redNeg()), this.point(s, e)
                    }, u.prototype.validate = function (e) {
                        if (e.isInfinity()) return !0;
                        e.normalize();
                        var t = e.x.redSqr(), r = e.y.redSqr(), n = t.redMul(this.a).redAdd(r),
                            i = this.c2.redMul(this.one.redAdd(this.d.redMul(t).redMul(r)));
                        return 0 === n.cmp(i)
                    }, a(c, o.BasePoint), u.prototype.pointFromJSON = function (e) {
                        return c.fromJSON(this, e)
                    }, u.prototype.point = function (e, t, r, n) {
                        return new c(this, e, t, r, n)
                    }, c.fromJSON = function (e, t) {
                        return new c(e, t[0], t[1], t[2])
                    }, c.prototype.inspect = function () {
                        return this.isInfinity() ? "<EC Point Infinity>" : "<EC Point x: " + this.x.fromRed().toString(16, 2) + " y: " + this.y.fromRed().toString(16, 2) + " z: " + this.z.fromRed().toString(16, 2) + ">"
                    }, c.prototype.isInfinity = function () {
                        return 0 === this.x.cmpn(0) && (0 === this.y.cmp(this.z) || this.zOne && 0 === this.y.cmp(this.curve.c))
                    }, c.prototype._extDbl = function () {
                        var e = this.x.redSqr(), t = this.y.redSqr(), r = this.z.redSqr();
                        r = r.redIAdd(r);
                        var n = this.curve._mulA(e), i = this.x.redAdd(this.y).redSqr().redISub(e).redISub(t),
                            a = n.redAdd(t), o = a.redSub(r), s = n.redSub(t), u = i.redMul(o), c = a.redMul(s),
                            f = i.redMul(s), d = o.redMul(a);
                        return this.curve.point(u, c, d, f)
                    }, c.prototype._projDbl = function () {
                        var e, t, r, n = this.x.redAdd(this.y).redSqr(), i = this.x.redSqr(), a = this.y.redSqr();
                        if (this.curve.twisted) {
                            var o = (c = this.curve._mulA(i)).redAdd(a);
                            if (this.zOne) e = n.redSub(i).redSub(a).redMul(o.redSub(this.curve.two)), t = o.redMul(c.redSub(a)), r = o.redSqr().redSub(o).redSub(o); else {
                                var s = this.z.redSqr(), u = o.redSub(s).redISub(s);
                                e = n.redSub(i).redISub(a).redMul(u), t = o.redMul(c.redSub(a)), r = o.redMul(u)
                            }
                        } else {
                            var c = i.redAdd(a);
                            s = this.curve._mulC(this.z).redSqr(), u = c.redSub(s).redSub(s), e = this.curve._mulC(n.redISub(c)).redMul(u), t = this.curve._mulC(c).redMul(i.redISub(a)), r = c.redMul(u)
                        }
                        return this.curve.point(e, t, r)
                    }, c.prototype.dbl = function () {
                        return this.isInfinity() ? this : this.curve.extended ? this._extDbl() : this._projDbl()
                    }, c.prototype._extAdd = function (e) {
                        var t = this.y.redSub(this.x).redMul(e.y.redSub(e.x)),
                            r = this.y.redAdd(this.x).redMul(e.y.redAdd(e.x)),
                            n = this.t.redMul(this.curve.dd).redMul(e.t), i = this.z.redMul(e.z.redAdd(e.z)),
                            a = r.redSub(t), o = i.redSub(n), s = i.redAdd(n), u = r.redAdd(t), c = a.redMul(o),
                            f = s.redMul(u), d = a.redMul(u), h = o.redMul(s);
                        return this.curve.point(c, f, h, d)
                    }, c.prototype._projAdd = function (e) {
                        var t, r, n = this.z.redMul(e.z), i = n.redSqr(), a = this.x.redMul(e.x),
                            o = this.y.redMul(e.y), s = this.curve.d.redMul(a).redMul(o), u = i.redSub(s),
                            c = i.redAdd(s), f = this.x.redAdd(this.y).redMul(e.x.redAdd(e.y)).redISub(a).redISub(o),
                            d = n.redMul(u).redMul(f);
                        return this.curve.twisted ? (t = n.redMul(c).redMul(o.redSub(this.curve._mulA(a))), r = u.redMul(c)) : (t = n.redMul(c).redMul(o.redSub(a)), r = this.curve._mulC(u).redMul(c)), this.curve.point(d, t, r)
                    }, c.prototype.add = function (e) {
                        return this.isInfinity() ? e : e.isInfinity() ? this : this.curve.extended ? this._extAdd(e) : this._projAdd(e)
                    }, c.prototype.mul = function (e) {
                        return this._hasDoubles(e) ? this.curve._fixedNafMul(this, e) : this.curve._wnafMul(this, e)
                    }, c.prototype.mulAdd = function (e, t, r) {
                        return this.curve._wnafMulAdd(1, [this, t], [e, r], 2, !1)
                    }, c.prototype.jmulAdd = function (e, t, r) {
                        return this.curve._wnafMulAdd(1, [this, t], [e, r], 2, !0)
                    }, c.prototype.normalize = function () {
                        if (this.zOne) return this;
                        var e = this.z.redInvm();
                        return this.x = this.x.redMul(e), this.y = this.y.redMul(e), this.t && (this.t = this.t.redMul(e)), this.z = this.curve.one, this.zOne = !0, this
                    }, c.prototype.neg = function () {
                        return this.curve.point(this.x.redNeg(), this.y, this.z, this.t && this.t.redNeg())
                    }, c.prototype.getX = function () {
                        return this.normalize(), this.x.fromRed()
                    }, c.prototype.getY = function () {
                        return this.normalize(), this.y.fromRed()
                    }, c.prototype.eq = function (e) {
                        return this === e || 0 === this.getX().cmp(e.getX()) && 0 === this.getY().cmp(e.getY())
                    }, c.prototype.eqXToP = function (e) {
                        var t = e.toRed(this.curve.red).redMul(this.z);
                        if (0 === this.x.cmp(t)) return !0;
                        for (var r = e.clone(), n = this.curve.redN.redMul(this.z); ;) {
                            if (r.iadd(this.curve.n), r.cmp(this.curve.p) >= 0) return !1;
                            if (t.redIAdd(n), 0 === this.x.cmp(t)) return !0
                        }
                    }, c.prototype.toP = c.prototype.normalize, c.prototype.mixedAdd = c.prototype.add
                }, function (e, t, r) {
                    t.sha1 = r(119), t.sha224 = r(120), t.sha256 = r(62), t.sha384 = r(121), t.sha512 = r(63)
                }, function (e, t, r) {
                    var n = r(16), i = r(28), a = r(61), o = n.rotl32, s = n.sum32, u = n.sum32_5, c = a.ft_1,
                        f = i.BlockHash, d = [1518500249, 1859775393, 2400959708, 3395469782];

                    function h() {
                        if (!(this instanceof h)) return new h;
                        f.call(this), this.h = [1732584193, 4023233417, 2562383102, 271733878, 3285377520], this.W = new Array(80)
                    }

                    n.inherits(h, f), e.exports = h, h.blockSize = 512, h.outSize = 160, h.hmacStrength = 80, h.padLength = 64, h.prototype._update = function (e, t) {
                        for (var r = this.W, n = 0; n < 16; n++) r[n] = e[t + n];
                        for (; n < r.length; n++) r[n] = o(r[n - 3] ^ r[n - 8] ^ r[n - 14] ^ r[n - 16], 1);
                        var i = this.h[0], a = this.h[1], f = this.h[2], h = this.h[3], l = this.h[4];
                        for (n = 0; n < r.length; n++) {
                            var p = ~~(n / 20), b = u(o(i, 5), c(p, a, f, h), l, r[n], d[p]);
                            l = h, h = f, f = o(a, 30), a = i, i = b
                        }
                        this.h[0] = s(this.h[0], i), this.h[1] = s(this.h[1], a), this.h[2] = s(this.h[2], f), this.h[3] = s(this.h[3], h), this.h[4] = s(this.h[4], l)
                    }, h.prototype._digest = function (e) {
                        return "hex" === e ? n.toHex32(this.h, "big") : n.split32(this.h, "big")
                    }
                }, function (e, t, r) {
                    var n = r(16), i = r(62);

                    function a() {
                        if (!(this instanceof a)) return new a;
                        i.call(this), this.h = [3238371032, 914150663, 812702999, 4144912697, 4290775857, 1750603025, 1694076839, 3204075428]
                    }

                    n.inherits(a, i), e.exports = a, a.blockSize = 512, a.outSize = 224, a.hmacStrength = 192, a.padLength = 64, a.prototype._digest = function (e) {
                        return "hex" === e ? n.toHex32(this.h.slice(0, 7), "big") : n.split32(this.h.slice(0, 7), "big")
                    }
                }, function (e, t, r) {
                    var n = r(16), i = r(63);

                    function a() {
                        if (!(this instanceof a)) return new a;
                        i.call(this), this.h = [3418070365, 3238371032, 1654270250, 914150663, 2438529370, 812702999, 355462360, 4144912697, 1731405415, 4290775857, 2394180231, 1750603025, 3675008525, 1694076839, 1203062813, 3204075428]
                    }

                    n.inherits(a, i), e.exports = a, a.blockSize = 1024, a.outSize = 384, a.hmacStrength = 192, a.padLength = 128, a.prototype._digest = function (e) {
                        return "hex" === e ? n.toHex32(this.h.slice(0, 12), "big") : n.split32(this.h.slice(0, 12), "big")
                    }
                }, function (e, t, r) {
                    var n = r(16), i = r(28), a = n.rotl32, o = n.sum32, s = n.sum32_3, u = n.sum32_4, c = i.BlockHash;

                    function f() {
                        if (!(this instanceof f)) return new f;
                        c.call(this), this.h = [1732584193, 4023233417, 2562383102, 271733878, 3285377520], this.endian = "little"
                    }

                    function d(e, t, r, n) {
                        return e <= 15 ? t ^ r ^ n : e <= 31 ? t & r | ~t & n : e <= 47 ? (t | ~r) ^ n : e <= 63 ? t & n | r & ~n : t ^ (r | ~n)
                    }

                    function h(e) {
                        return e <= 15 ? 0 : e <= 31 ? 1518500249 : e <= 47 ? 1859775393 : e <= 63 ? 2400959708 : 2840853838
                    }

                    function l(e) {
                        return e <= 15 ? 1352829926 : e <= 31 ? 1548603684 : e <= 47 ? 1836072691 : e <= 63 ? 2053994217 : 0
                    }

                    n.inherits(f, c), t.ripemd160 = f, f.blockSize = 512, f.outSize = 160, f.hmacStrength = 192, f.padLength = 64, f.prototype._update = function (e, t) {
                        for (var r = this.h[0], n = this.h[1], i = this.h[2], c = this.h[3], f = this.h[4], m = r, y = n, w = i, x = c, _ = f, A = 0; A < 80; A++) {
                            var k = o(a(u(r, d(A, n, i, c), e[p[A] + t], h(A)), v[A]), f);
                            r = f, f = c, c = a(i, 10), i = n, n = k, k = o(a(u(m, d(79 - A, y, w, x), e[b[A] + t], l(A)), g[A]), _), m = _, _ = x, x = a(w, 10), w = y, y = k
                        }
                        k = s(this.h[1], i, x), this.h[1] = s(this.h[2], c, _), this.h[2] = s(this.h[3], f, m), this.h[3] = s(this.h[4], r, y), this.h[4] = s(this.h[0], n, w), this.h[0] = k
                    }, f.prototype._digest = function (e) {
                        return "hex" === e ? n.toHex32(this.h, "little") : n.split32(this.h, "little")
                    };
                    var p = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 7, 4, 13, 1, 10, 6, 15, 3, 12, 0, 9, 5, 2, 14, 11, 8, 3, 10, 14, 4, 9, 15, 8, 1, 2, 7, 0, 6, 13, 11, 5, 12, 1, 9, 11, 10, 0, 8, 12, 4, 13, 3, 7, 15, 14, 5, 6, 2, 4, 0, 5, 9, 7, 12, 2, 10, 14, 1, 3, 8, 11, 6, 15, 13],
                        b = [5, 14, 7, 0, 9, 2, 11, 4, 13, 6, 15, 8, 1, 10, 3, 12, 6, 11, 3, 7, 0, 13, 5, 10, 14, 15, 8, 12, 4, 9, 1, 2, 15, 5, 1, 3, 7, 14, 6, 9, 11, 8, 12, 2, 10, 0, 4, 13, 8, 6, 4, 1, 3, 11, 15, 0, 5, 12, 2, 13, 9, 7, 10, 14, 12, 15, 10, 4, 1, 5, 8, 7, 6, 2, 13, 14, 0, 3, 9, 11],
                        v = [11, 14, 15, 12, 5, 8, 7, 9, 11, 13, 14, 15, 6, 7, 9, 8, 7, 6, 8, 13, 11, 9, 7, 15, 7, 12, 15, 9, 11, 7, 13, 12, 11, 13, 6, 7, 14, 9, 13, 15, 14, 8, 13, 6, 5, 12, 7, 5, 11, 12, 14, 15, 14, 15, 9, 8, 9, 14, 5, 6, 8, 6, 5, 12, 9, 15, 5, 11, 6, 8, 13, 12, 5, 12, 13, 14, 11, 8, 5, 6],
                        g = [8, 9, 9, 11, 13, 15, 15, 5, 7, 7, 8, 11, 14, 14, 12, 6, 9, 13, 15, 7, 12, 8, 9, 11, 7, 7, 12, 7, 6, 15, 13, 11, 9, 7, 15, 11, 8, 6, 6, 14, 12, 13, 5, 14, 13, 13, 7, 5, 15, 5, 8, 11, 14, 14, 6, 14, 6, 9, 12, 9, 12, 5, 15, 8, 8, 5, 12, 9, 12, 5, 14, 6, 8, 13, 6, 5, 15, 13, 11, 11]
                }, function (e, t, r) {
                    var n = r(16), i = r(12);

                    function a(e, t, r) {
                        if (!(this instanceof a)) return new a(e, t, r);
                        this.Hash = e, this.blockSize = e.blockSize / 8, this.outSize = e.outSize / 8, this.inner = null, this.outer = null, this._init(n.toArray(t, r))
                    }

                    e.exports = a, a.prototype._init = function (e) {
                        e.length > this.blockSize && (e = (new this.Hash).update(e).digest()), i(e.length <= this.blockSize);
                        for (var t = e.length; t < this.blockSize; t++) e.push(0);
                        for (t = 0; t < e.length; t++) e[t] ^= 54;
                        for (this.inner = (new this.Hash).update(e), t = 0; t < e.length; t++) e[t] ^= 106;
                        this.outer = (new this.Hash).update(e)
                    }, a.prototype.update = function (e, t) {
                        return this.inner.update(e, t), this
                    }, a.prototype.digest = function (e) {
                        return this.outer.update(this.inner.digest()), this.outer.digest(e)
                    }
                }, function (e, t) {
                    e.exports = {
                        doubles: {
                            step: 4,
                            points: [["e60fce93b59e9ec53011aabc21c23e97b2a31369b87a5ae9c44ee89e2a6dec0a", "f7e3507399e595929db99f34f57937101296891e44d23f0be1f32cce69616821"], ["8282263212c609d9ea2a6e3e172de238d8c39cabd5ac1ca10646e23fd5f51508", "11f8a8098557dfe45e8256e830b60ace62d613ac2f7b17bed31b6eaff6e26caf"], ["175e159f728b865a72f99cc6c6fc846de0b93833fd2222ed73fce5b551e5b739", "d3506e0d9e3c79eba4ef97a51ff71f5eacb5955add24345c6efa6ffee9fed695"], ["363d90d447b00c9c99ceac05b6262ee053441c7e55552ffe526bad8f83ff4640", "4e273adfc732221953b445397f3363145b9a89008199ecb62003c7f3bee9de9"], ["8b4b5f165df3c2be8c6244b5b745638843e4a781a15bcd1b69f79a55dffdf80c", "4aad0a6f68d308b4b3fbd7813ab0da04f9e336546162ee56b3eff0c65fd4fd36"], ["723cbaa6e5db996d6bf771c00bd548c7b700dbffa6c0e77bcb6115925232fcda", "96e867b5595cc498a921137488824d6e2660a0653779494801dc069d9eb39f5f"], ["eebfa4d493bebf98ba5feec812c2d3b50947961237a919839a533eca0e7dd7fa", "5d9a8ca3970ef0f269ee7edaf178089d9ae4cdc3a711f712ddfd4fdae1de8999"], ["100f44da696e71672791d0a09b7bde459f1215a29b3c03bfefd7835b39a48db0", "cdd9e13192a00b772ec8f3300c090666b7ff4a18ff5195ac0fbd5cd62bc65a09"], ["e1031be262c7ed1b1dc9227a4a04c017a77f8d4464f3b3852c8acde6e534fd2d", "9d7061928940405e6bb6a4176597535af292dd419e1ced79a44f18f29456a00d"], ["feea6cae46d55b530ac2839f143bd7ec5cf8b266a41d6af52d5e688d9094696d", "e57c6b6c97dce1bab06e4e12bf3ecd5c981c8957cc41442d3155debf18090088"], ["da67a91d91049cdcb367be4be6ffca3cfeed657d808583de33fa978bc1ec6cb1", "9bacaa35481642bc41f463f7ec9780e5dec7adc508f740a17e9ea8e27a68be1d"], ["53904faa0b334cdda6e000935ef22151ec08d0f7bb11069f57545ccc1a37b7c0", "5bc087d0bc80106d88c9eccac20d3c1c13999981e14434699dcb096b022771c8"], ["8e7bcd0bd35983a7719cca7764ca906779b53a043a9b8bcaeff959f43ad86047", "10b7770b2a3da4b3940310420ca9514579e88e2e47fd68b3ea10047e8460372a"], ["385eed34c1cdff21e6d0818689b81bde71a7f4f18397e6690a841e1599c43862", "283bebc3e8ea23f56701de19e9ebf4576b304eec2086dc8cc0458fe5542e5453"], ["6f9d9b803ecf191637c73a4413dfa180fddf84a5947fbc9c606ed86c3fac3a7", "7c80c68e603059ba69b8e2a30e45c4d47ea4dd2f5c281002d86890603a842160"], ["3322d401243c4e2582a2147c104d6ecbf774d163db0f5e5313b7e0e742d0e6bd", "56e70797e9664ef5bfb019bc4ddaf9b72805f63ea2873af624f3a2e96c28b2a0"], ["85672c7d2de0b7da2bd1770d89665868741b3f9af7643397721d74d28134ab83", "7c481b9b5b43b2eb6374049bfa62c2e5e77f17fcc5298f44c8e3094f790313a6"], ["948bf809b1988a46b06c9f1919413b10f9226c60f668832ffd959af60c82a0a", "53a562856dcb6646dc6b74c5d1c3418c6d4dff08c97cd2bed4cb7f88d8c8e589"], ["6260ce7f461801c34f067ce0f02873a8f1b0e44dfc69752accecd819f38fd8e8", "bc2da82b6fa5b571a7f09049776a1ef7ecd292238051c198c1a84e95b2b4ae17"], ["e5037de0afc1d8d43d8348414bbf4103043ec8f575bfdc432953cc8d2037fa2d", "4571534baa94d3b5f9f98d09fb990bddbd5f5b03ec481f10e0e5dc841d755bda"], ["e06372b0f4a207adf5ea905e8f1771b4e7e8dbd1c6a6c5b725866a0ae4fce725", "7a908974bce18cfe12a27bb2ad5a488cd7484a7787104870b27034f94eee31dd"], ["213c7a715cd5d45358d0bbf9dc0ce02204b10bdde2a3f58540ad6908d0559754", "4b6dad0b5ae462507013ad06245ba190bb4850f5f36a7eeddff2c27534b458f2"], ["4e7c272a7af4b34e8dbb9352a5419a87e2838c70adc62cddf0cc3a3b08fbd53c", "17749c766c9d0b18e16fd09f6def681b530b9614bff7dd33e0b3941817dcaae6"], ["fea74e3dbe778b1b10f238ad61686aa5c76e3db2be43057632427e2840fb27b6", "6e0568db9b0b13297cf674deccb6af93126b596b973f7b77701d3db7f23cb96f"], ["76e64113f677cf0e10a2570d599968d31544e179b760432952c02a4417bdde39", "c90ddf8dee4e95cf577066d70681f0d35e2a33d2b56d2032b4b1752d1901ac01"], ["c738c56b03b2abe1e8281baa743f8f9a8f7cc643df26cbee3ab150242bcbb891", "893fb578951ad2537f718f2eacbfbbbb82314eef7880cfe917e735d9699a84c3"], ["d895626548b65b81e264c7637c972877d1d72e5f3a925014372e9f6588f6c14b", "febfaa38f2bc7eae728ec60818c340eb03428d632bb067e179363ed75d7d991f"], ["b8da94032a957518eb0f6433571e8761ceffc73693e84edd49150a564f676e03", "2804dfa44805a1e4d7c99cc9762808b092cc584d95ff3b511488e4e74efdf6e7"], ["e80fea14441fb33a7d8adab9475d7fab2019effb5156a792f1a11778e3c0df5d", "eed1de7f638e00771e89768ca3ca94472d155e80af322ea9fcb4291b6ac9ec78"], ["a301697bdfcd704313ba48e51d567543f2a182031efd6915ddc07bbcc4e16070", "7370f91cfb67e4f5081809fa25d40f9b1735dbf7c0a11a130c0d1a041e177ea1"], ["90ad85b389d6b936463f9d0512678de208cc330b11307fffab7ac63e3fb04ed4", "e507a3620a38261affdcbd9427222b839aefabe1582894d991d4d48cb6ef150"], ["8f68b9d2f63b5f339239c1ad981f162ee88c5678723ea3351b7b444c9ec4c0da", "662a9f2dba063986de1d90c2b6be215dbbea2cfe95510bfdf23cbf79501fff82"], ["e4f3fb0176af85d65ff99ff9198c36091f48e86503681e3e6686fd5053231e11", "1e63633ad0ef4f1c1661a6d0ea02b7286cc7e74ec951d1c9822c38576feb73bc"], ["8c00fa9b18ebf331eb961537a45a4266c7034f2f0d4e1d0716fb6eae20eae29e", "efa47267fea521a1a9dc343a3736c974c2fadafa81e36c54e7d2a4c66702414b"], ["e7a26ce69dd4829f3e10cec0a9e98ed3143d084f308b92c0997fddfc60cb3e41", "2a758e300fa7984b471b006a1aafbb18d0a6b2c0420e83e20e8a9421cf2cfd51"], ["b6459e0ee3662ec8d23540c223bcbdc571cbcb967d79424f3cf29eb3de6b80ef", "67c876d06f3e06de1dadf16e5661db3c4b3ae6d48e35b2ff30bf0b61a71ba45"], ["d68a80c8280bb840793234aa118f06231d6f1fc67e73c5a5deda0f5b496943e8", "db8ba9fff4b586d00c4b1f9177b0e28b5b0e7b8f7845295a294c84266b133120"], ["324aed7df65c804252dc0270907a30b09612aeb973449cea4095980fc28d3d5d", "648a365774b61f2ff130c0c35aec1f4f19213b0c7e332843967224af96ab7c84"], ["4df9c14919cde61f6d51dfdbe5fee5dceec4143ba8d1ca888e8bd373fd054c96", "35ec51092d8728050974c23a1d85d4b5d506cdc288490192ebac06cad10d5d"], ["9c3919a84a474870faed8a9c1cc66021523489054d7f0308cbfc99c8ac1f98cd", "ddb84f0f4a4ddd57584f044bf260e641905326f76c64c8e6be7e5e03d4fc599d"], ["6057170b1dd12fdf8de05f281d8e06bb91e1493a8b91d4cc5a21382120a959e5", "9a1af0b26a6a4807add9a2daf71df262465152bc3ee24c65e899be932385a2a8"], ["a576df8e23a08411421439a4518da31880cef0fba7d4df12b1a6973eecb94266", "40a6bf20e76640b2c92b97afe58cd82c432e10a7f514d9f3ee8be11ae1b28ec8"], ["7778a78c28dec3e30a05fe9629de8c38bb30d1f5cf9a3a208f763889be58ad71", "34626d9ab5a5b22ff7098e12f2ff580087b38411ff24ac563b513fc1fd9f43ac"], ["928955ee637a84463729fd30e7afd2ed5f96274e5ad7e5cb09eda9c06d903ac", "c25621003d3f42a827b78a13093a95eeac3d26efa8a8d83fc5180e935bcd091f"], ["85d0fef3ec6db109399064f3a0e3b2855645b4a907ad354527aae75163d82751", "1f03648413a38c0be29d496e582cf5663e8751e96877331582c237a24eb1f962"], ["ff2b0dce97eece97c1c9b6041798b85dfdfb6d8882da20308f5404824526087e", "493d13fef524ba188af4c4dc54d07936c7b7ed6fb90e2ceb2c951e01f0c29907"], ["827fbbe4b1e880ea9ed2b2e6301b212b57f1ee148cd6dd28780e5e2cf856e241", "c60f9c923c727b0b71bef2c67d1d12687ff7a63186903166d605b68baec293ec"], ["eaa649f21f51bdbae7be4ae34ce6e5217a58fdce7f47f9aa7f3b58fa2120e2b3", "be3279ed5bbbb03ac69a80f89879aa5a01a6b965f13f7e59d47a5305ba5ad93d"], ["e4a42d43c5cf169d9391df6decf42ee541b6d8f0c9a137401e23632dda34d24f", "4d9f92e716d1c73526fc99ccfb8ad34ce886eedfa8d8e4f13a7f7131deba9414"], ["1ec80fef360cbdd954160fadab352b6b92b53576a88fea4947173b9d4300bf19", "aeefe93756b5340d2f3a4958a7abbf5e0146e77f6295a07b671cdc1cc107cefd"], ["146a778c04670c2f91b00af4680dfa8bce3490717d58ba889ddb5928366642be", "b318e0ec3354028add669827f9d4b2870aaa971d2f7e5ed1d0b297483d83efd0"], ["fa50c0f61d22e5f07e3acebb1aa07b128d0012209a28b9776d76a8793180eef9", "6b84c6922397eba9b72cd2872281a68a5e683293a57a213b38cd8d7d3f4f2811"], ["da1d61d0ca721a11b1a5bf6b7d88e8421a288ab5d5bba5220e53d32b5f067ec2", "8157f55a7c99306c79c0766161c91e2966a73899d279b48a655fba0f1ad836f1"], ["a8e282ff0c9706907215ff98e8fd416615311de0446f1e062a73b0610d064e13", "7f97355b8db81c09abfb7f3c5b2515888b679a3e50dd6bd6cef7c73111f4cc0c"], ["174a53b9c9a285872d39e56e6913cab15d59b1fa512508c022f382de8319497c", "ccc9dc37abfc9c1657b4155f2c47f9e6646b3a1d8cb9854383da13ac079afa73"], ["959396981943785c3d3e57edf5018cdbe039e730e4918b3d884fdff09475b7ba", "2e7e552888c331dd8ba0386a4b9cd6849c653f64c8709385e9b8abf87524f2fd"], ["d2a63a50ae401e56d645a1153b109a8fcca0a43d561fba2dbb51340c9d82b151", "e82d86fb6443fcb7565aee58b2948220a70f750af484ca52d4142174dcf89405"], ["64587e2335471eb890ee7896d7cfdc866bacbdbd3839317b3436f9b45617e073", "d99fcdd5bf6902e2ae96dd6447c299a185b90a39133aeab358299e5e9faf6589"], ["8481bde0e4e4d885b3a546d3e549de042f0aa6cea250e7fd358d6c86dd45e458", "38ee7b8cba5404dd84a25bf39cecb2ca900a79c42b262e556d64b1b59779057e"], ["13464a57a78102aa62b6979ae817f4637ffcfed3c4b1ce30bcd6303f6caf666b", "69be159004614580ef7e433453ccb0ca48f300a81d0942e13f495a907f6ecc27"], ["bc4a9df5b713fe2e9aef430bcc1dc97a0cd9ccede2f28588cada3a0d2d83f366", "d3a81ca6e785c06383937adf4b798caa6e8a9fbfa547b16d758d666581f33c1"], ["8c28a97bf8298bc0d23d8c749452a32e694b65e30a9472a3954ab30fe5324caa", "40a30463a3305193378fedf31f7cc0eb7ae784f0451cb9459e71dc73cbef9482"], ["8ea9666139527a8c1dd94ce4f071fd23c8b350c5a4bb33748c4ba111faccae0", "620efabbc8ee2782e24e7c0cfb95c5d735b783be9cf0f8e955af34a30e62b945"], ["dd3625faef5ba06074669716bbd3788d89bdde815959968092f76cc4eb9a9787", "7a188fa3520e30d461da2501045731ca941461982883395937f68d00c644a573"], ["f710d79d9eb962297e4f6232b40e8f7feb2bc63814614d692c12de752408221e", "ea98e67232d3b3295d3b535532115ccac8612c721851617526ae47a9c77bfc82"]]
                        }, naf: {
                            wnd: 7,
                            points: [["f9308a019258c31049344f85f89d5229b531c845836f99b08601f113bce036f9", "388f7b0f632de8140fe337e62a37f3566500a99934c2231b6cb9fd7584b8e672"], ["2f8bde4d1a07209355b4a7250a5c5128e88b84bddc619ab7cba8d569b240efe4", "d8ac222636e5e3d6d4dba9dda6c9c426f788271bab0d6840dca87d3aa6ac62d6"], ["5cbdf0646e5db4eaa398f365f2ea7a0e3d419b7e0330e39ce92bddedcac4f9bc", "6aebca40ba255960a3178d6d861a54dba813d0b813fde7b5a5082628087264da"], ["acd484e2f0c7f65309ad178a9f559abde09796974c57e714c35f110dfc27ccbe", "cc338921b0a7d9fd64380971763b61e9add888a4375f8e0f05cc262ac64f9c37"], ["774ae7f858a9411e5ef4246b70c65aac5649980be5c17891bbec17895da008cb", "d984a032eb6b5e190243dd56d7b7b365372db1e2dff9d6a8301d74c9c953c61b"], ["f28773c2d975288bc7d1d205c3748651b075fbc6610e58cddeeddf8f19405aa8", "ab0902e8d880a89758212eb65cdaf473a1a06da521fa91f29b5cb52db03ed81"], ["d7924d4f7d43ea965a465ae3095ff41131e5946f3c85f79e44adbcf8e27e080e", "581e2872a86c72a683842ec228cc6defea40af2bd896d3a5c504dc9ff6a26b58"], ["defdea4cdb677750a420fee807eacf21eb9898ae79b9768766e4faa04a2d4a34", "4211ab0694635168e997b0ead2a93daeced1f4a04a95c0f6cfb199f69e56eb77"], ["2b4ea0a797a443d293ef5cff444f4979f06acfebd7e86d277475656138385b6c", "85e89bc037945d93b343083b5a1c86131a01f60c50269763b570c854e5c09b7a"], ["352bbf4a4cdd12564f93fa332ce333301d9ad40271f8107181340aef25be59d5", "321eb4075348f534d59c18259dda3e1f4a1b3b2e71b1039c67bd3d8bcf81998c"], ["2fa2104d6b38d11b0230010559879124e42ab8dfeff5ff29dc9cdadd4ecacc3f", "2de1068295dd865b64569335bd5dd80181d70ecfc882648423ba76b532b7d67"], ["9248279b09b4d68dab21a9b066edda83263c3d84e09572e269ca0cd7f5453714", "73016f7bf234aade5d1aa71bdea2b1ff3fc0de2a887912ffe54a32ce97cb3402"], ["daed4f2be3a8bf278e70132fb0beb7522f570e144bf615c07e996d443dee8729", "a69dce4a7d6c98e8d4a1aca87ef8d7003f83c230f3afa726ab40e52290be1c55"], ["c44d12c7065d812e8acf28d7cbb19f9011ecd9e9fdf281b0e6a3b5e87d22e7db", "2119a460ce326cdc76c45926c982fdac0e106e861edf61c5a039063f0e0e6482"], ["6a245bf6dc698504c89a20cfded60853152b695336c28063b61c65cbd269e6b4", "e022cf42c2bd4a708b3f5126f16a24ad8b33ba48d0423b6efd5e6348100d8a82"], ["1697ffa6fd9de627c077e3d2fe541084ce13300b0bec1146f95ae57f0d0bd6a5", "b9c398f186806f5d27561506e4557433a2cf15009e498ae7adee9d63d01b2396"], ["605bdb019981718b986d0f07e834cb0d9deb8360ffb7f61df982345ef27a7479", "2972d2de4f8d20681a78d93ec96fe23c26bfae84fb14db43b01e1e9056b8c49"], ["62d14dab4150bf497402fdc45a215e10dcb01c354959b10cfe31c7e9d87ff33d", "80fc06bd8cc5b01098088a1950eed0db01aa132967ab472235f5642483b25eaf"], ["80c60ad0040f27dade5b4b06c408e56b2c50e9f56b9b8b425e555c2f86308b6f", "1c38303f1cc5c30f26e66bad7fe72f70a65eed4cbe7024eb1aa01f56430bd57a"], ["7a9375ad6167ad54aa74c6348cc54d344cc5dc9487d847049d5eabb0fa03c8fb", "d0e3fa9eca8726909559e0d79269046bdc59ea10c70ce2b02d499ec224dc7f7"], ["d528ecd9b696b54c907a9ed045447a79bb408ec39b68df504bb51f459bc3ffc9", "eecf41253136e5f99966f21881fd656ebc4345405c520dbc063465b521409933"], ["49370a4b5f43412ea25f514e8ecdad05266115e4a7ecb1387231808f8b45963", "758f3f41afd6ed428b3081b0512fd62a54c3f3afbb5b6764b653052a12949c9a"], ["77f230936ee88cbbd73df930d64702ef881d811e0e1498e2f1c13eb1fc345d74", "958ef42a7886b6400a08266e9ba1b37896c95330d97077cbbe8eb3c7671c60d6"], ["f2dac991cc4ce4b9ea44887e5c7c0bce58c80074ab9d4dbaeb28531b7739f530", "e0dedc9b3b2f8dad4da1f32dec2531df9eb5fbeb0598e4fd1a117dba703a3c37"], ["463b3d9f662621fb1b4be8fbbe2520125a216cdfc9dae3debcba4850c690d45b", "5ed430d78c296c3543114306dd8622d7c622e27c970a1de31cb377b01af7307e"], ["f16f804244e46e2a09232d4aff3b59976b98fac14328a2d1a32496b49998f247", "cedabd9b82203f7e13d206fcdf4e33d92a6c53c26e5cce26d6579962c4e31df6"], ["caf754272dc84563b0352b7a14311af55d245315ace27c65369e15f7151d41d1", "cb474660ef35f5f2a41b643fa5e460575f4fa9b7962232a5c32f908318a04476"], ["2600ca4b282cb986f85d0f1709979d8b44a09c07cb86d7c124497bc86f082120", "4119b88753c15bd6a693b03fcddbb45d5ac6be74ab5f0ef44b0be9475a7e4b40"], ["7635ca72d7e8432c338ec53cd12220bc01c48685e24f7dc8c602a7746998e435", "91b649609489d613d1d5e590f78e6d74ecfc061d57048bad9e76f302c5b9c61"], ["754e3239f325570cdbbf4a87deee8a66b7f2b33479d468fbc1a50743bf56cc18", "673fb86e5bda30fb3cd0ed304ea49a023ee33d0197a695d0c5d98093c536683"], ["e3e6bd1071a1e96aff57859c82d570f0330800661d1c952f9fe2694691d9b9e8", "59c9e0bba394e76f40c0aa58379a3cb6a5a2283993e90c4167002af4920e37f5"], ["186b483d056a033826ae73d88f732985c4ccb1f32ba35f4b4cc47fdcf04aa6eb", "3b952d32c67cf77e2e17446e204180ab21fb8090895138b4a4a797f86e80888b"], ["df9d70a6b9876ce544c98561f4be4f725442e6d2b737d9c91a8321724ce0963f", "55eb2dafd84d6ccd5f862b785dc39d4ab157222720ef9da217b8c45cf2ba2417"], ["5edd5cc23c51e87a497ca815d5dce0f8ab52554f849ed8995de64c5f34ce7143", "efae9c8dbc14130661e8cec030c89ad0c13c66c0d17a2905cdc706ab7399a868"], ["290798c2b6476830da12fe02287e9e777aa3fba1c355b17a722d362f84614fba", "e38da76dcd440621988d00bcf79af25d5b29c094db2a23146d003afd41943e7a"], ["af3c423a95d9f5b3054754efa150ac39cd29552fe360257362dfdecef4053b45", "f98a3fd831eb2b749a93b0e6f35cfb40c8cd5aa667a15581bc2feded498fd9c6"], ["766dbb24d134e745cccaa28c99bf274906bb66b26dcf98df8d2fed50d884249a", "744b1152eacbe5e38dcc887980da38b897584a65fa06cedd2c924f97cbac5996"], ["59dbf46f8c94759ba21277c33784f41645f7b44f6c596a58ce92e666191abe3e", "c534ad44175fbc300f4ea6ce648309a042ce739a7919798cd85e216c4a307f6e"], ["f13ada95103c4537305e691e74e9a4a8dd647e711a95e73cb62dc6018cfd87b8", "e13817b44ee14de663bf4bc808341f326949e21a6a75c2570778419bdaf5733d"], ["7754b4fa0e8aced06d4167a2c59cca4cda1869c06ebadfb6488550015a88522c", "30e93e864e669d82224b967c3020b8fa8d1e4e350b6cbcc537a48b57841163a2"], ["948dcadf5990e048aa3874d46abef9d701858f95de8041d2a6828c99e2262519", "e491a42537f6e597d5d28a3224b1bc25df9154efbd2ef1d2cbba2cae5347d57e"], ["7962414450c76c1689c7b48f8202ec37fb224cf5ac0bfa1570328a8a3d7c77ab", "100b610ec4ffb4760d5c1fc133ef6f6b12507a051f04ac5760afa5b29db83437"], ["3514087834964b54b15b160644d915485a16977225b8847bb0dd085137ec47ca", "ef0afbb2056205448e1652c48e8127fc6039e77c15c2378b7e7d15a0de293311"], ["d3cc30ad6b483e4bc79ce2c9dd8bc54993e947eb8df787b442943d3f7b527eaf", "8b378a22d827278d89c5e9be8f9508ae3c2ad46290358630afb34db04eede0a4"], ["1624d84780732860ce1c78fcbfefe08b2b29823db913f6493975ba0ff4847610", "68651cf9b6da903e0914448c6cd9d4ca896878f5282be4c8cc06e2a404078575"], ["733ce80da955a8a26902c95633e62a985192474b5af207da6df7b4fd5fc61cd4", "f5435a2bd2badf7d485a4d8b8db9fcce3e1ef8e0201e4578c54673bc1dc5ea1d"], ["15d9441254945064cf1a1c33bbd3b49f8966c5092171e699ef258dfab81c045c", "d56eb30b69463e7234f5137b73b84177434800bacebfc685fc37bbe9efe4070d"], ["a1d0fcf2ec9de675b612136e5ce70d271c21417c9d2b8aaaac138599d0717940", "edd77f50bcb5a3cab2e90737309667f2641462a54070f3d519212d39c197a629"], ["e22fbe15c0af8ccc5780c0735f84dbe9a790badee8245c06c7ca37331cb36980", "a855babad5cd60c88b430a69f53a1a7a38289154964799be43d06d77d31da06"], ["311091dd9860e8e20ee13473c1155f5f69635e394704eaa74009452246cfa9b3", "66db656f87d1f04fffd1f04788c06830871ec5a64feee685bd80f0b1286d8374"], ["34c1fd04d301be89b31c0442d3e6ac24883928b45a9340781867d4232ec2dbdf", "9414685e97b1b5954bd46f730174136d57f1ceeb487443dc5321857ba73abee"], ["f219ea5d6b54701c1c14de5b557eb42a8d13f3abbcd08affcc2a5e6b049b8d63", "4cb95957e83d40b0f73af4544cccf6b1f4b08d3c07b27fb8d8c2962a400766d1"], ["d7b8740f74a8fbaab1f683db8f45de26543a5490bca627087236912469a0b448", "fa77968128d9c92ee1010f337ad4717eff15db5ed3c049b3411e0315eaa4593b"], ["32d31c222f8f6f0ef86f7c98d3a3335ead5bcd32abdd94289fe4d3091aa824bf", "5f3032f5892156e39ccd3d7915b9e1da2e6dac9e6f26e961118d14b8462e1661"], ["7461f371914ab32671045a155d9831ea8793d77cd59592c4340f86cbc18347b5", "8ec0ba238b96bec0cbdddcae0aa442542eee1ff50c986ea6b39847b3cc092ff6"], ["ee079adb1df1860074356a25aa38206a6d716b2c3e67453d287698bad7b2b2d6", "8dc2412aafe3be5c4c5f37e0ecc5f9f6a446989af04c4e25ebaac479ec1c8c1e"], ["16ec93e447ec83f0467b18302ee620f7e65de331874c9dc72bfd8616ba9da6b5", "5e4631150e62fb40d0e8c2a7ca5804a39d58186a50e497139626778e25b0674d"], ["eaa5f980c245f6f038978290afa70b6bd8855897f98b6aa485b96065d537bd99", "f65f5d3e292c2e0819a528391c994624d784869d7e6ea67fb18041024edc07dc"], ["78c9407544ac132692ee1910a02439958ae04877151342ea96c4b6b35a49f51", "f3e0319169eb9b85d5404795539a5e68fa1fbd583c064d2462b675f194a3ddb4"], ["494f4be219a1a77016dcd838431aea0001cdc8ae7a6fc688726578d9702857a5", "42242a969283a5f339ba7f075e36ba2af925ce30d767ed6e55f4b031880d562c"], ["a598a8030da6d86c6bc7f2f5144ea549d28211ea58faa70ebf4c1e665c1fe9b5", "204b5d6f84822c307e4b4a7140737aec23fc63b65b35f86a10026dbd2d864e6b"], ["c41916365abb2b5d09192f5f2dbeafec208f020f12570a184dbadc3e58595997", "4f14351d0087efa49d245b328984989d5caf9450f34bfc0ed16e96b58fa9913"], ["841d6063a586fa475a724604da03bc5b92a2e0d2e0a36acfe4c73a5514742881", "73867f59c0659e81904f9a1c7543698e62562d6744c169ce7a36de01a8d6154"], ["5e95bb399a6971d376026947f89bde2f282b33810928be4ded112ac4d70e20d5", "39f23f366809085beebfc71181313775a99c9aed7d8ba38b161384c746012865"], ["36e4641a53948fd476c39f8a99fd974e5ec07564b5315d8bf99471bca0ef2f66", "d2424b1b1abe4eb8164227b085c9aa9456ea13493fd563e06fd51cf5694c78fc"], ["336581ea7bfbbb290c191a2f507a41cf5643842170e914faeab27c2c579f726", "ead12168595fe1be99252129b6e56b3391f7ab1410cd1e0ef3dcdcabd2fda224"], ["8ab89816dadfd6b6a1f2634fcf00ec8403781025ed6890c4849742706bd43ede", "6fdcef09f2f6d0a044e654aef624136f503d459c3e89845858a47a9129cdd24e"], ["1e33f1a746c9c5778133344d9299fcaa20b0938e8acff2544bb40284b8c5fb94", "60660257dd11b3aa9c8ed618d24edff2306d320f1d03010e33a7d2057f3b3b6"], ["85b7c1dcb3cec1b7ee7f30ded79dd20a0ed1f4cc18cbcfcfa410361fd8f08f31", "3d98a9cdd026dd43f39048f25a8847f4fcafad1895d7a633c6fed3c35e999511"], ["29df9fbd8d9e46509275f4b125d6d45d7fbe9a3b878a7af872a2800661ac5f51", "b4c4fe99c775a606e2d8862179139ffda61dc861c019e55cd2876eb2a27d84b"], ["a0b1cae06b0a847a3fea6e671aaf8adfdfe58ca2f768105c8082b2e449fce252", "ae434102edde0958ec4b19d917a6a28e6b72da1834aff0e650f049503a296cf2"], ["4e8ceafb9b3e9a136dc7ff67e840295b499dfb3b2133e4ba113f2e4c0e121e5", "cf2174118c8b6d7a4b48f6d534ce5c79422c086a63460502b827ce62a326683c"], ["d24a44e047e19b6f5afb81c7ca2f69080a5076689a010919f42725c2b789a33b", "6fb8d5591b466f8fc63db50f1c0f1c69013f996887b8244d2cdec417afea8fa3"], ["ea01606a7a6c9cdd249fdfcfacb99584001edd28abbab77b5104e98e8e3b35d4", "322af4908c7312b0cfbfe369f7a7b3cdb7d4494bc2823700cfd652188a3ea98d"], ["af8addbf2b661c8a6c6328655eb96651252007d8c5ea31be4ad196de8ce2131f", "6749e67c029b85f52a034eafd096836b2520818680e26ac8f3dfbcdb71749700"], ["e3ae1974566ca06cc516d47e0fb165a674a3dabcfca15e722f0e3450f45889", "2aeabe7e4531510116217f07bf4d07300de97e4874f81f533420a72eeb0bd6a4"], ["591ee355313d99721cf6993ffed1e3e301993ff3ed258802075ea8ced397e246", "b0ea558a113c30bea60fc4775460c7901ff0b053d25ca2bdeee98f1a4be5d196"], ["11396d55fda54c49f19aa97318d8da61fa8584e47b084945077cf03255b52984", "998c74a8cd45ac01289d5833a7beb4744ff536b01b257be4c5767bea93ea57a4"], ["3c5d2a1ba39c5a1790000738c9e0c40b8dcdfd5468754b6405540157e017aa7a", "b2284279995a34e2f9d4de7396fc18b80f9b8b9fdd270f6661f79ca4c81bd257"], ["cc8704b8a60a0defa3a99a7299f2e9c3fbc395afb04ac078425ef8a1793cc030", "bdd46039feed17881d1e0862db347f8cf395b74fc4bcdc4e940b74e3ac1f1b13"], ["c533e4f7ea8555aacd9777ac5cad29b97dd4defccc53ee7ea204119b2889b197", "6f0a256bc5efdf429a2fb6242f1a43a2d9b925bb4a4b3a26bb8e0f45eb596096"], ["c14f8f2ccb27d6f109f6d08d03cc96a69ba8c34eec07bbcf566d48e33da6593", "c359d6923bb398f7fd4473e16fe1c28475b740dd098075e6c0e8649113dc3a38"], ["a6cbc3046bc6a450bac24789fa17115a4c9739ed75f8f21ce441f72e0b90e6ef", "21ae7f4680e889bb130619e2c0f95a360ceb573c70603139862afd617fa9b9f"], ["347d6d9a02c48927ebfb86c1359b1caf130a3c0267d11ce6344b39f99d43cc38", "60ea7f61a353524d1c987f6ecec92f086d565ab687870cb12689ff1e31c74448"], ["da6545d2181db8d983f7dcb375ef5866d47c67b1bf31c8cf855ef7437b72656a", "49b96715ab6878a79e78f07ce5680c5d6673051b4935bd897fea824b77dc208a"], ["c40747cc9d012cb1a13b8148309c6de7ec25d6945d657146b9d5994b8feb1111", "5ca560753be2a12fc6de6caf2cb489565db936156b9514e1bb5e83037e0fa2d4"], ["4e42c8ec82c99798ccf3a610be870e78338c7f713348bd34c8203ef4037f3502", "7571d74ee5e0fb92a7a8b33a07783341a5492144cc54bcc40a94473693606437"], ["3775ab7089bc6af823aba2e1af70b236d251cadb0c86743287522a1b3b0dedea", "be52d107bcfa09d8bcb9736a828cfa7fac8db17bf7a76a2c42ad961409018cf7"], ["cee31cbf7e34ec379d94fb814d3d775ad954595d1314ba8846959e3e82f74e26", "8fd64a14c06b589c26b947ae2bcf6bfa0149ef0be14ed4d80f448a01c43b1c6d"], ["b4f9eaea09b6917619f6ea6a4eb5464efddb58fd45b1ebefcdc1a01d08b47986", "39e5c9925b5a54b07433a4f18c61726f8bb131c012ca542eb24a8ac07200682a"], ["d4263dfc3d2df923a0179a48966d30ce84e2515afc3dccc1b77907792ebcc60e", "62dfaf07a0f78feb30e30d6295853ce189e127760ad6cf7fae164e122a208d54"], ["48457524820fa65a4f8d35eb6930857c0032acc0a4a2de422233eeda897612c4", "25a748ab367979d98733c38a1fa1c2e7dc6cc07db2d60a9ae7a76aaa49bd0f77"], ["dfeeef1881101f2cb11644f3a2afdfc2045e19919152923f367a1767c11cceda", "ecfb7056cf1de042f9420bab396793c0c390bde74b4bbdff16a83ae09a9a7517"], ["6d7ef6b17543f8373c573f44e1f389835d89bcbc6062ced36c82df83b8fae859", "cd450ec335438986dfefa10c57fea9bcc521a0959b2d80bbf74b190dca712d10"], ["e75605d59102a5a2684500d3b991f2e3f3c88b93225547035af25af66e04541f", "f5c54754a8f71ee540b9b48728473e314f729ac5308b06938360990e2bfad125"], ["eb98660f4c4dfaa06a2be453d5020bc99a0c2e60abe388457dd43fefb1ed620c", "6cb9a8876d9cb8520609af3add26cd20a0a7cd8a9411131ce85f44100099223e"], ["13e87b027d8514d35939f2e6892b19922154596941888336dc3563e3b8dba942", "fef5a3c68059a6dec5d624114bf1e91aac2b9da568d6abeb2570d55646b8adf1"], ["ee163026e9fd6fe017c38f06a5be6fc125424b371ce2708e7bf4491691e5764a", "1acb250f255dd61c43d94ccc670d0f58f49ae3fa15b96623e5430da0ad6c62b2"], ["b268f5ef9ad51e4d78de3a750c2dc89b1e626d43505867999932e5db33af3d80", "5f310d4b3c99b9ebb19f77d41c1dee018cf0d34fd4191614003e945a1216e423"], ["ff07f3118a9df035e9fad85eb6c7bfe42b02f01ca99ceea3bf7ffdba93c4750d", "438136d603e858a3a5c440c38eccbaddc1d2942114e2eddd4740d098ced1f0d8"], ["8d8b9855c7c052a34146fd20ffb658bea4b9f69e0d825ebec16e8c3ce2b526a1", "cdb559eedc2d79f926baf44fb84ea4d44bcf50fee51d7ceb30e2e7f463036758"], ["52db0b5384dfbf05bfa9d472d7ae26dfe4b851ceca91b1eba54263180da32b63", "c3b997d050ee5d423ebaf66a6db9f57b3180c902875679de924b69d84a7b375"], ["e62f9490d3d51da6395efd24e80919cc7d0f29c3f3fa48c6fff543becbd43352", "6d89ad7ba4876b0b22c2ca280c682862f342c8591f1daf5170e07bfd9ccafa7d"], ["7f30ea2476b399b4957509c88f77d0191afa2ff5cb7b14fd6d8e7d65aaab1193", "ca5ef7d4b231c94c3b15389a5f6311e9daff7bb67b103e9880ef4bff637acaec"], ["5098ff1e1d9f14fb46a210fada6c903fef0fb7b4a1dd1d9ac60a0361800b7a00", "9731141d81fc8f8084d37c6e7542006b3ee1b40d60dfe5362a5b132fd17ddc0"], ["32b78c7de9ee512a72895be6b9cbefa6e2f3c4ccce445c96b9f2c81e2778ad58", "ee1849f513df71e32efc3896ee28260c73bb80547ae2275ba497237794c8753c"], ["e2cb74fddc8e9fbcd076eef2a7c72b0ce37d50f08269dfc074b581550547a4f7", "d3aa2ed71c9dd2247a62df062736eb0baddea9e36122d2be8641abcb005cc4a4"], ["8438447566d4d7bedadc299496ab357426009a35f235cb141be0d99cd10ae3a8", "c4e1020916980a4da5d01ac5e6ad330734ef0d7906631c4f2390426b2edd791f"], ["4162d488b89402039b584c6fc6c308870587d9c46f660b878ab65c82c711d67e", "67163e903236289f776f22c25fb8a3afc1732f2b84b4e95dbda47ae5a0852649"], ["3fad3fa84caf0f34f0f89bfd2dcf54fc175d767aec3e50684f3ba4a4bf5f683d", "cd1bc7cb6cc407bb2f0ca647c718a730cf71872e7d0d2a53fa20efcdfe61826"], ["674f2600a3007a00568c1a7ce05d0816c1fb84bf1370798f1c69532faeb1a86b", "299d21f9413f33b3edf43b257004580b70db57da0b182259e09eecc69e0d38a5"], ["d32f4da54ade74abb81b815ad1fb3b263d82d6c692714bcff87d29bd5ee9f08f", "f9429e738b8e53b968e99016c059707782e14f4535359d582fc416910b3eea87"], ["30e4e670435385556e593657135845d36fbb6931f72b08cb1ed954f1e3ce3ff6", "462f9bce619898638499350113bbc9b10a878d35da70740dc695a559eb88db7b"], ["be2062003c51cc3004682904330e4dee7f3dcd10b01e580bf1971b04d4cad297", "62188bc49d61e5428573d48a74e1c655b1c61090905682a0d5558ed72dccb9bc"], ["93144423ace3451ed29e0fb9ac2af211cb6e84a601df5993c419859fff5df04a", "7c10dfb164c3425f5c71a3f9d7992038f1065224f72bb9d1d902a6d13037b47c"], ["b015f8044f5fcbdcf21ca26d6c34fb8197829205c7b7d2a7cb66418c157b112c", "ab8c1e086d04e813744a655b2df8d5f83b3cdc6faa3088c1d3aea1454e3a1d5f"], ["d5e9e1da649d97d89e4868117a465a3a4f8a18de57a140d36b3f2af341a21b52", "4cb04437f391ed73111a13cc1d4dd0db1693465c2240480d8955e8592f27447a"], ["d3ae41047dd7ca065dbf8ed77b992439983005cd72e16d6f996a5316d36966bb", "bd1aeb21ad22ebb22a10f0303417c6d964f8cdd7df0aca614b10dc14d125ac46"], ["463e2763d885f958fc66cdd22800f0a487197d0a82e377b49f80af87c897b065", "bfefacdb0e5d0fd7df3a311a94de062b26b80c61fbc97508b79992671ef7ca7f"], ["7985fdfd127c0567c6f53ec1bb63ec3158e597c40bfe747c83cddfc910641917", "603c12daf3d9862ef2b25fe1de289aed24ed291e0ec6708703a5bd567f32ed03"], ["74a1ad6b5f76e39db2dd249410eac7f99e74c59cb83d2d0ed5ff1543da7703e9", "cc6157ef18c9c63cd6193d83631bbea0093e0968942e8c33d5737fd790e0db08"], ["30682a50703375f602d416664ba19b7fc9bab42c72747463a71d0896b22f6da3", "553e04f6b018b4fa6c8f39e7f311d3176290d0e0f19ca73f17714d9977a22ff8"], ["9e2158f0d7c0d5f26c3791efefa79597654e7a2b2464f52b1ee6c1347769ef57", "712fcdd1b9053f09003a3481fa7762e9ffd7c8ef35a38509e2fbf2629008373"], ["176e26989a43c9cfeba4029c202538c28172e566e3c4fce7322857f3be327d66", "ed8cc9d04b29eb877d270b4878dc43c19aefd31f4eee09ee7b47834c1fa4b1c3"], ["75d46efea3771e6e68abb89a13ad747ecf1892393dfc4f1b7004788c50374da8", "9852390a99507679fd0b86fd2b39a868d7efc22151346e1a3ca4726586a6bed8"], ["809a20c67d64900ffb698c4c825f6d5f2310fb0451c869345b7319f645605721", "9e994980d9917e22b76b061927fa04143d096ccc54963e6a5ebfa5f3f8e286c1"], ["1b38903a43f7f114ed4500b4eac7083fdefece1cf29c63528d563446f972c180", "4036edc931a60ae889353f77fd53de4a2708b26b6f5da72ad3394119daf408f9"]]
                        }
                    }
                }, function (e, t, r) {
                    var i = r(14), a = r(126), o = r(13), s = r(32), u = r(59), c = o.assert, f = r(127), d = r(128);

                    function h(e) {
                        if (!(this instanceof h)) return new h(e);
                        "string" == typeof e && (c(s.hasOwnProperty(e), "Unknown curve " + e), e = s[e]), e instanceof s.PresetCurve && (e = {curve: e}), this.curve = e.curve.curve, this.n = this.curve.n, this.nh = this.n.ushrn(1), this.g = this.curve.g, this.g = e.curve.g, this.g.precompute(e.curve.n.bitLength() + 1), this.hash = e.hash || e.curve.hash
                    }

                    e.exports = h, h.prototype.keyPair = function (e) {
                        return new f(this, e)
                    }, h.prototype.keyFromPrivate = function (e, t) {
                        return f.fromPrivate(this, e, t)
                    }, h.prototype.keyFromPublic = function (e, t) {
                        return f.fromPublic(this, e, t)
                    }, h.prototype.genKeyPair = function (e) {
                        e || (e = {});
                        for (var t = new a({
                            hash: this.hash,
                            pers: e.pers,
                            persEnc: e.persEnc || "utf8",
                            entropy: e.entropy || u(this.hash.hmacStrength),
                            entropyEnc: e.entropy && e.entropyEnc || "utf8",
                            nonce: this.n.toArray()
                        }), r = this.n.byteLength(), n = this.n.sub(new i(2)); ;) {
                            var o = new i(t.generate(r));
                            if (!(o.cmp(n) > 0)) return o.iaddn(1), this.keyFromPrivate(o)
                        }
                    }, h.prototype._truncateToN = function (e, t) {
                        var r = 8 * e.byteLength() - this.n.bitLength();
                        return r > 0 && (e = e.ushrn(r)), !t && e.cmp(this.n) >= 0 ? e.sub(this.n) : e
                    }, h.prototype.sign = function (e, t, r, o) {
                        "object" == n()(r) && (o = r, r = null), o || (o = {}), t = this.keyFromPrivate(t, r), e = this._truncateToN(new i(e, 16));
                        for (var s = this.n.byteLength(), u = t.getPrivate().toArray("be", s), c = e.toArray("be", s), f = new a({
                            hash: this.hash,
                            entropy: u,
                            nonce: c,
                            pers: o.pers,
                            persEnc: o.persEnc || "utf8"
                        }), h = this.n.sub(new i(1)), l = 0; ; l++) {
                            var p = o.k ? o.k(l) : new i(f.generate(this.n.byteLength()));
                            if (!((p = this._truncateToN(p, !0)).cmpn(1) <= 0 || p.cmp(h) >= 0)) {
                                var b = this.g.mul(p);
                                if (!b.isInfinity()) {
                                    var v = b.getX(), g = v.umod(this.n);
                                    if (0 !== g.cmpn(0)) {
                                        var m = p.invm(this.n).mul(g.mul(t.getPrivate()).iadd(e));
                                        if (0 !== (m = m.umod(this.n)).cmpn(0)) {
                                            var y = (b.getY().isOdd() ? 1 : 0) | (0 !== v.cmp(g) ? 2 : 0);
                                            return o.canonical && m.cmp(this.nh) > 0 && (m = this.n.sub(m), y ^= 1), new d({
                                                r: g,
                                                s: m,
                                                recoveryParam: y
                                            })
                                        }
                                    }
                                }
                            }
                        }
                    }, h.prototype.verify = function (e, t, r, n) {
                        e = this._truncateToN(new i(e, 16)), r = this.keyFromPublic(r, n);
                        var a = (t = new d(t, "hex")).r, o = t.s;
                        if (a.cmpn(1) < 0 || a.cmp(this.n) >= 0) return !1;
                        if (o.cmpn(1) < 0 || o.cmp(this.n) >= 0) return !1;
                        var s, u = o.invm(this.n), c = u.mul(e).umod(this.n), f = u.mul(a).umod(this.n);
                        return this.curve._maxwellTrick ? !(s = this.g.jmulAdd(c, r.getPublic(), f)).isInfinity() && s.eqXToP(a) : !(s = this.g.mulAdd(c, r.getPublic(), f)).isInfinity() && 0 === s.getX().umod(this.n).cmp(a)
                    }, h.prototype.recoverPubKey = function (e, t, r, n) {
                        c((3 & r) === r, "The recovery param is more than two bits"), t = new d(t, n);
                        var a = this.n, o = new i(e), s = t.r, u = t.s, f = 1 & r, h = r >> 1;
                        if (s.cmp(this.curve.p.umod(this.curve.n)) >= 0 && h) throw new Error("Unable to find sencond key candinate");
                        s = h ? this.curve.pointFromX(s.add(this.curve.n), f) : this.curve.pointFromX(s, f);
                        var l = t.r.invm(a), p = a.sub(o).mul(l).umod(a), b = u.mul(l).umod(a);
                        return this.g.mulAdd(p, s, b)
                    }, h.prototype.getKeyRecoveryParam = function (e, t, r, n) {
                        if (null !== (t = new d(t, n)).recoveryParam) return t.recoveryParam;
                        for (var i = 0; i < 4; i++) {
                            var a;
                            try {
                                a = this.recoverPubKey(e, t, i)
                            } catch (e) {
                                continue
                            }
                            if (a.eq(r)) return i
                        }
                        throw new Error("Unable to find valid recovery factor")
                    }
                }, function (e, t, r) {
                    var n = r(33), i = r(58), a = r(12);

                    function o(e) {
                        if (!(this instanceof o)) return new o(e);
                        this.hash = e.hash, this.predResist = !!e.predResist, this.outLen = this.hash.outSize, this.minEntropy = e.minEntropy || this.hash.hmacStrength, this._reseed = null, this.reseedInterval = null, this.K = null, this.V = null;
                        var t = i.toArray(e.entropy, e.entropyEnc || "hex"),
                            r = i.toArray(e.nonce, e.nonceEnc || "hex"), n = i.toArray(e.pers, e.persEnc || "hex");
                        a(t.length >= this.minEntropy / 8, "Not enough entropy. Minimum is: " + this.minEntropy + " bits"), this._init(t, r, n)
                    }

                    e.exports = o, o.prototype._init = function (e, t, r) {
                        var n = e.concat(t).concat(r);
                        this.K = new Array(this.outLen / 8), this.V = new Array(this.outLen / 8);
                        for (var i = 0; i < this.V.length; i++) this.K[i] = 0, this.V[i] = 1;
                        this._update(n), this._reseed = 1, this.reseedInterval = 281474976710656
                    }, o.prototype._hmac = function () {
                        return new n.hmac(this.hash, this.K)
                    }, o.prototype._update = function (e) {
                        var t = this._hmac().update(this.V).update([0]);
                        e && (t = t.update(e)), this.K = t.digest(), this.V = this._hmac().update(this.V).digest(), e && (this.K = this._hmac().update(this.V).update([1]).update(e).digest(), this.V = this._hmac().update(this.V).digest())
                    }, o.prototype.reseed = function (e, t, r, n) {
                        "string" != typeof t && (n = r, r = t, t = null), e = i.toArray(e, t), r = i.toArray(r, n), a(e.length >= this.minEntropy / 8, "Not enough entropy. Minimum is: " + this.minEntropy + " bits"), this._update(e.concat(r || [])), this._reseed = 1
                    }, o.prototype.generate = function (e, t, r, n) {
                        if (this._reseed > this.reseedInterval) throw new Error("Reseed is required");
                        "string" != typeof t && (n = r, r = t, t = null), r && (r = i.toArray(r, n || "hex"), this._update(r));
                        for (var a = []; a.length < e;) this.V = this._hmac().update(this.V).digest(), a = a.concat(this.V);
                        var o = a.slice(0, e);
                        return this._update(r), this._reseed++, i.encode(o, t)
                    }
                }, function (e, t, r) {
                    var n = r(14), i = r(13).assert;

                    function a(e, t) {
                        this.ec = e, this.priv = null, this.pub = null, t.priv && this._importPrivate(t.priv, t.privEnc), t.pub && this._importPublic(t.pub, t.pubEnc)
                    }

                    e.exports = a, a.fromPublic = function (e, t, r) {
                        return t instanceof a ? t : new a(e, {pub: t, pubEnc: r})
                    }, a.fromPrivate = function (e, t, r) {
                        return t instanceof a ? t : new a(e, {priv: t, privEnc: r})
                    }, a.prototype.validate = function () {
                        var e = this.getPublic();
                        return e.isInfinity() ? {
                            result: !1,
                            reason: "Invalid public key"
                        } : e.validate() ? e.mul(this.ec.curve.n).isInfinity() ? {
                            result: !0,
                            reason: null
                        } : {result: !1, reason: "Public key * N != O"} : {
                            result: !1,
                            reason: "Public key is not a point"
                        }
                    }, a.prototype.getPublic = function (e, t) {
                        return "string" == typeof e && (t = e, e = null), this.pub || (this.pub = this.ec.g.mul(this.priv)), t ? this.pub.encode(t, e) : this.pub
                    }, a.prototype.getPrivate = function (e) {
                        return "hex" === e ? this.priv.toString(16, 2) : this.priv
                    }, a.prototype._importPrivate = function (e, t) {
                        this.priv = new n(e, t || 16), this.priv = this.priv.umod(this.ec.curve.n)
                    }, a.prototype._importPublic = function (e, t) {
                        if (e.x || e.y) return "mont" === this.ec.curve.type ? i(e.x, "Need x coordinate") : "short" !== this.ec.curve.type && "edwards" !== this.ec.curve.type || i(e.x && e.y, "Need both x and y coordinate"), void (this.pub = this.ec.curve.point(e.x, e.y));
                        this.pub = this.ec.curve.decodePoint(e, t)
                    }, a.prototype.derive = function (e) {
                        return e.mul(this.priv).getX()
                    }, a.prototype.sign = function (e, t, r) {
                        return this.ec.sign(e, this, t, r)
                    }, a.prototype.verify = function (e, t) {
                        return this.ec.verify(e, t, this)
                    }, a.prototype.inspect = function () {
                        return "<Key priv: " + (this.priv && this.priv.toString(16, 2)) + " pub: " + (this.pub && this.pub.inspect()) + " >"
                    }
                }, function (e, t, r) {
                    var n = r(14), i = r(13), a = i.assert;

                    function o(e, t) {
                        if (e instanceof o) return e;
                        this._importDER(e, t) || (a(e.r && e.s, "Signature without r or s"), this.r = new n(e.r, 16), this.s = new n(e.s, 16), void 0 === e.recoveryParam ? this.recoveryParam = null : this.recoveryParam = e.recoveryParam)
                    }

                    function s(e, t) {
                        var r = e[t.place++];
                        if (!(128 & r)) return r;
                        var n = 15 & r;
                        if (0 === n || n > 4) return !1;
                        for (var i = 0, a = 0, o = t.place; a < n; a++, o++) i <<= 8, i |= e[o], i >>>= 0;
                        return !(i <= 127) && (t.place = o, i)
                    }

                    function u(e) {
                        for (var t = 0, r = e.length - 1; !e[t] && !(128 & e[t + 1]) && t < r;) t++;
                        return 0 === t ? e : e.slice(t)
                    }

                    function c(e, t) {
                        if (t < 128) e.push(t); else {
                            var r = 1 + (Math.log(t) / Math.LN2 >>> 3);
                            for (e.push(128 | r); --r;) e.push(t >>> (r << 3) & 255);
                            e.push(t)
                        }
                    }

                    e.exports = o, o.prototype._importDER = function (e, t) {
                        e = i.toArray(e, t);
                        var r = new function () {
                            this.place = 0
                        };
                        if (48 !== e[r.place++]) return !1;
                        var a = s(e, r);
                        if (!1 === a) return !1;
                        if (a + r.place !== e.length) return !1;
                        if (2 !== e[r.place++]) return !1;
                        var o = s(e, r);
                        if (!1 === o) return !1;
                        var u = e.slice(r.place, o + r.place);
                        if (r.place += o, 2 !== e[r.place++]) return !1;
                        var c = s(e, r);
                        if (!1 === c) return !1;
                        if (e.length !== c + r.place) return !1;
                        var f = e.slice(r.place, c + r.place);
                        if (0 === u[0]) {
                            if (!(128 & u[1])) return !1;
                            u = u.slice(1)
                        }
                        if (0 === f[0]) {
                            if (!(128 & f[1])) return !1;
                            f = f.slice(1)
                        }
                        return this.r = new n(u), this.s = new n(f), this.recoveryParam = null, !0
                    }, o.prototype.toDER = function (e) {
                        var t = this.r.toArray(), r = this.s.toArray();
                        for (128 & t[0] && (t = [0].concat(t)), 128 & r[0] && (r = [0].concat(r)), t = u(t), r = u(r); !(r[0] || 128 & r[1]);) r = r.slice(1);
                        var n = [2];
                        c(n, t.length), (n = n.concat(t)).push(2), c(n, r.length);
                        var a = n.concat(r), o = [48];
                        return c(o, a.length), o = o.concat(a), i.encode(o, e)
                    }
                }, function (e, t, r) {
                    var n = r(33), i = r(32), a = r(13), o = a.assert, s = a.parseBytes, u = r(130), c = r(131);

                    function f(e) {
                        if (o("ed25519" === e, "only tested with ed25519 so far"), !(this instanceof f)) return new f(e);
                        e = i[e].curve, this.curve = e, this.g = e.g, this.g.precompute(e.n.bitLength() + 1), this.pointClass = e.point().constructor, this.encodingLength = Math.ceil(e.n.bitLength() / 8), this.hash = n.sha512
                    }

                    e.exports = f, f.prototype.sign = function (e, t) {
                        e = s(e);
                        var r = this.keyFromSecret(t), n = this.hashInt(r.messagePrefix(), e), i = this.g.mul(n),
                            a = this.encodePoint(i), o = this.hashInt(a, r.pubBytes(), e).mul(r.priv()),
                            u = n.add(o).umod(this.curve.n);
                        return this.makeSignature({R: i, S: u, Rencoded: a})
                    }, f.prototype.verify = function (e, t, r) {
                        e = s(e), t = this.makeSignature(t);
                        var n = this.keyFromPublic(r), i = this.hashInt(t.Rencoded(), n.pubBytes(), e),
                            a = this.g.mul(t.S());
                        return t.R().add(n.pub().mul(i)).eq(a)
                    }, f.prototype.hashInt = function () {
                        for (var e = this.hash(), t = 0; t < arguments.length; t++) e.update(arguments[t]);
                        return a.intFromLE(e.digest()).umod(this.curve.n)
                    }, f.prototype.keyFromPublic = function (e) {
                        return u.fromPublic(this, e)
                    }, f.prototype.keyFromSecret = function (e) {
                        return u.fromSecret(this, e)
                    }, f.prototype.makeSignature = function (e) {
                        return e instanceof c ? e : new c(this, e)
                    }, f.prototype.encodePoint = function (e) {
                        var t = e.getY().toArray("le", this.encodingLength);
                        return t[this.encodingLength - 1] |= e.getX().isOdd() ? 128 : 0, t
                    }, f.prototype.decodePoint = function (e) {
                        var t = (e = a.parseBytes(e)).length - 1, r = e.slice(0, t).concat(-129 & e[t]),
                            n = 0 != (128 & e[t]), i = a.intFromLE(r);
                        return this.curve.pointFromY(i, n)
                    }, f.prototype.encodeInt = function (e) {
                        return e.toArray("le", this.encodingLength)
                    }, f.prototype.decodeInt = function (e) {
                        return a.intFromLE(e)
                    }, f.prototype.isPoint = function (e) {
                        return e instanceof this.pointClass
                    }
                }, function (e, t, r) {
                    var n = r(13), i = n.assert, a = n.parseBytes, o = n.cachedProperty;

                    function s(e, t) {
                        this.eddsa = e, this._secret = a(t.secret), e.isPoint(t.pub) ? this._pub = t.pub : this._pubBytes = a(t.pub)
                    }

                    s.fromPublic = function (e, t) {
                        return t instanceof s ? t : new s(e, {pub: t})
                    }, s.fromSecret = function (e, t) {
                        return t instanceof s ? t : new s(e, {secret: t})
                    }, s.prototype.secret = function () {
                        return this._secret
                    }, o(s, "pubBytes", function () {
                        return this.eddsa.encodePoint(this.pub())
                    }), o(s, "pub", function () {
                        return this._pubBytes ? this.eddsa.decodePoint(this._pubBytes) : this.eddsa.g.mul(this.priv())
                    }), o(s, "privBytes", function () {
                        var e = this.eddsa, t = this.hash(), r = e.encodingLength - 1, n = t.slice(0, e.encodingLength);
                        return n[0] &= 248, n[r] &= 127, n[r] |= 64, n
                    }), o(s, "priv", function () {
                        return this.eddsa.decodeInt(this.privBytes())
                    }), o(s, "hash", function () {
                        return this.eddsa.hash().update(this.secret()).digest()
                    }), o(s, "messagePrefix", function () {
                        return this.hash().slice(this.eddsa.encodingLength)
                    }), s.prototype.sign = function (e) {
                        return i(this._secret, "KeyPair can only verify"), this.eddsa.sign(e, this)
                    }, s.prototype.verify = function (e, t) {
                        return this.eddsa.verify(e, t, this)
                    }, s.prototype.getSecret = function (e) {
                        return i(this._secret, "KeyPair is public only"), n.encode(this.secret(), e)
                    }, s.prototype.getPublic = function (e) {
                        return n.encode(this.pubBytes(), e)
                    }, e.exports = s
                }, function (e, t, r) {
                    var i = r(14), a = r(13), o = a.assert, s = a.cachedProperty, u = a.parseBytes;

                    function c(e, t) {
                        this.eddsa = e, "object" != n()(t) && (t = u(t)), Array.isArray(t) && (t = {
                            R: t.slice(0, e.encodingLength),
                            S: t.slice(e.encodingLength)
                        }), o(t.R && t.S, "Signature without R or S"), e.isPoint(t.R) && (this._R = t.R), t.S instanceof i && (this._S = t.S), this._Rencoded = Array.isArray(t.R) ? t.R : t.Rencoded, this._Sencoded = Array.isArray(t.S) ? t.S : t.Sencoded
                    }

                    s(c, "S", function () {
                        return this.eddsa.decodeInt(this.Sencoded())
                    }), s(c, "R", function () {
                        return this.eddsa.decodePoint(this.Rencoded())
                    }), s(c, "Rencoded", function () {
                        return this.eddsa.encodePoint(this.R())
                    }), s(c, "Sencoded", function () {
                        return this.eddsa.encodeInt(this.S())
                    }), c.prototype.toBytes = function () {
                        return this.Rencoded().concat(this.Sencoded())
                    }, c.prototype.toHex = function () {
                        return a.encode(this.toBytes(), "hex").toUpperCase()
                    }, e.exports = c
                }, function (e, t, r) {
                    var n = this && this.__importStar || function (e) {
                        if (e && e.__esModule) return e;
                        var t = {};
                        if (null != e) for (var r in e) Object.hasOwnProperty.call(e, r) && (t[r] = e[r]);
                        return t.default = e, t
                    };
                    Object.defineProperty(t, "__esModule", {value: !0});
                    var i = n(r(19)), a = r(18), o = r(29), s = r(25),
                        u = new Uint8Array([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]),
                        c = new RegExp("^((.*)\\.)?([^.]+)$"), f = new RegExp("^[a-z0-9.-]*$");
                    t.namehash = function (e) {
                        "string" != typeof e && i.throwError("invalid address - " + String(e), i.INVALID_ARGUMENT, {
                            argument: "name",
                            value: e
                        }), (e = e.toLowerCase()).match(f) || i.throwError("contains invalid UseSTD3ASCIIRules characters", i.INVALID_ARGUMENT, {
                            argument: "name",
                            value: e
                        });
                        for (var t = u; e.length;) {
                            var r = e.match(c), n = o.toUtf8Bytes(r[3]);
                            t = s.keccak256(a.concat([t, s.keccak256(n)])), e = r[2] || ""
                        }
                        return a.hexlify(t)
                    }, t.id = function (e) {
                        return s.keccak256(o.toUtf8Bytes(e))
                    }, t.hashMessage = function (e) {
                        return s.keccak256(a.concat([o.toUtf8Bytes("Ethereum Signed Message:\n"), o.toUtf8Bytes(String(e.length)), "string" == typeof e ? o.toUtf8Bytes(e) : e]))
                    }
                }, function (e, t, r) {
                    Object.defineProperty(t, "__esModule", {value: !0}), t.default = function (e, t) {
                        (0, n.default)(e), (t = (0, i.default)(t, o)).allow_trailing_dot && "." === e[e.length - 1] && (e = e.substring(0, e.length - 1));
                        for (var r = e.split("."), a = 0; a < r.length; a++) if (r[a].length > 63) return !1;
                        if (t.require_tld) {
                            var s = r.pop();
                            if (!r.length || !/^([a-z\u00a1-\uffff]{2,}|xn[a-z0-9-]{2,})$/i.test(s)) return !1;
                            if (/[\s\u2002-\u200B\u202F\u205F\u3000\uFEFF\uDB40\uDC20]/.test(s)) return !1
                        }
                        for (var u, c = 0; c < r.length; c++) {
                            if (u = r[c], t.allow_underscores && (u = u.replace(/_/g, "")), !/^[a-z\u00a1-\uffff0-9-]+$/i.test(u)) return !1;
                            if (/[\uff01-\uff5e]/.test(u)) return !1;
                            if ("-" === u[0] || "-" === u[u.length - 1]) return !1
                        }
                        return !0
                    };
                    var n = a(r(34)), i = a(r(64));

                    function a(e) {
                        return e && e.__esModule ? e : {default: e}
                    }

                    var o = {require_tld: !0, allow_underscores: !1, allow_trailing_dot: !1};
                    e.exports = t.default, e.exports.default = t.default
                }, function (e, t, r) {
                    Object.defineProperty(t, "__esModule", {value: !0}), t.default = function e(t) {
                        var r = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : "";
                        if ((0, i.default)(t), !(r = String(r))) return e(t, 4) || e(t, 6);
                        if ("4" === r) return !!a.test(t) && t.split(".").sort(function (e, t) {
                            return e - t
                        })[3] <= 255;
                        if ("6" === r) {
                            var n = t.split(":"), s = !1, u = e(n[n.length - 1], 4), c = u ? 7 : 8;
                            if (n.length > c) return !1;
                            if ("::" === t) return !0;
                            "::" === t.substr(0, 2) ? (n.shift(), n.shift(), s = !0) : "::" === t.substr(t.length - 2) && (n.pop(), n.pop(), s = !0);
                            for (var f = 0; f < n.length; ++f) if ("" === n[f] && f > 0 && f < n.length - 1) {
                                if (s) return !1;
                                s = !0
                            } else if (u && f === n.length - 1) ; else if (!o.test(n[f])) return !1;
                            return s ? n.length >= 1 : n.length === c
                        }
                        return !1
                    };
                    var n, i = (n = r(34)) && n.__esModule ? n : {default: n},
                        a = /^(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})$/, o = /^[0-9A-F]{1,4}$/i;
                    e.exports = t.default, e.exports.default = t.default
                }, function (e, t) {
                    e.exports = function (e) {
                        if (Array.isArray(e)) return e
                    }
                }, function (e, t) {
                    e.exports = function (e, t) {
                        var r = [], n = !0, i = !1, a = void 0;
                        try {
                            for (var o, s = e[Symbol.iterator](); !(n = (o = s.next()).done) && (r.push(o.value), !t || r.length !== t); n = !0) ;
                        } catch (e) {
                            i = !0, a = e
                        } finally {
                            try {
                                n || null == s.return || s.return()
                            } finally {
                                if (i) throw a
                            }
                        }
                        return r
                    }
                }, function (e, t) {
                    e.exports = function () {
                        throw new TypeError("Invalid attempt to destructure non-iterable instance")
                    }
                }, function (e, t, r) {
                    function n(e, t) {
                        return Object.prototype.hasOwnProperty.call(e, t)
                    }

                    e.exports = function (e, t, r, a) {
                        t = t || "&", r = r || "=";
                        var o = {};
                        if ("string" != typeof e || 0 === e.length) return o;
                        var s = /\+/g;
                        e = e.split(t);
                        var u = 1e3;
                        a && "number" == typeof a.maxKeys && (u = a.maxKeys);
                        var c = e.length;
                        u > 0 && c > u && (c = u);
                        for (var f = 0; f < c; ++f) {
                            var d, h, l, p, b = e[f].replace(s, "%20"), v = b.indexOf(r);
                            v >= 0 ? (d = b.substr(0, v), h = b.substr(v + 1)) : (d = b, h = ""), l = decodeURIComponent(d), p = decodeURIComponent(h), n(o, l) ? i(o[l]) ? o[l].push(p) : o[l] = [o[l], p] : o[l] = p
                        }
                        return o
                    };
                    var i = Array.isArray || function (e) {
                        return "[object Array]" === Object.prototype.toString.call(e)
                    }
                }, function (e, t, r) {
                    var i = function (e) {
                        switch (n()(e)) {
                            case"string":
                                return e;
                            case"boolean":
                                return e ? "true" : "false";
                            case"number":
                                return isFinite(e) ? e : "";
                            default:
                                return ""
                        }
                    };
                    e.exports = function (e, t, r, u) {
                        return t = t || "&", r = r || "=", null === e && (e = void 0), "object" == n()(e) ? o(s(e), function (n) {
                            var s = encodeURIComponent(i(n)) + r;
                            return a(e[n]) ? o(e[n], function (e) {
                                return s + encodeURIComponent(i(e))
                            }).join(t) : s + encodeURIComponent(i(e[n]))
                        }).join(t) : u ? encodeURIComponent(i(u)) + r + encodeURIComponent(i(e)) : ""
                    };
                    var a = Array.isArray || function (e) {
                        return "[object Array]" === Object.prototype.toString.call(e)
                    };

                    function o(e, t) {
                        if (e.map) return e.map(t);
                        for (var r = [], n = 0; n < e.length; n++) r.push(t(e[n], n));
                        return r
                    }

                    var s = Object.keys || function (e) {
                        var t = [];
                        for (var r in e) Object.prototype.hasOwnProperty.call(e, r) && t.push(r);
                        return t
                    }
                }]).default
            })
        }.call(this, r(17)(e))
    }, function (e, t, r) {
        "use strict";
        e.exports = function (e, t) {
            return function () {
                for (var r = new Array(arguments.length), n = 0; n < r.length; n++) r[n] = arguments[n];
                return e.apply(t, r)
            }
        }
    }, function (e, t, r) {
        "use strict";
        var n = r(1), i = r(30), a = r(32), o = r(33), s = r(34), u = r(7),
            c = "undefined" != typeof window && window.btoa && window.btoa.bind(window) || r(35);
        e.exports = function (e) {
            return new Promise(function (t, f) {
                var d = e.data, h = e.headers;
                n.isFormData(d) && delete h["Content-Type"];
                var l = new XMLHttpRequest, p = "onreadystatechange", b = !1;
                if ("undefined" == typeof window || !window.XDomainRequest || "withCredentials" in l || s(e.url) || (l = new window.XDomainRequest, p = "onload", b = !0, l.onprogress = function () {
                }, l.ontimeout = function () {
                }), e.auth) {
                    var v = e.auth.username || "", g = e.auth.password || "";
                    h.Authorization = "Basic " + c(v + ":" + g)
                }
                if (l.open(e.method.toUpperCase(), a(e.url, e.params, e.paramsSerializer), !0), l.timeout = e.timeout, l[p] = function () {
                    if (l && (4 === l.readyState || b) && (0 !== l.status || l.responseURL && 0 === l.responseURL.indexOf("file:"))) {
                        var r = "getAllResponseHeaders" in l ? o(l.getAllResponseHeaders()) : null, n = {
                            data: e.responseType && "text" !== e.responseType ? l.response : l.responseText,
                            status: 1223 === l.status ? 204 : l.status,
                            statusText: 1223 === l.status ? "No Content" : l.statusText,
                            headers: r,
                            config: e,
                            request: l
                        };
                        i(t, f, n), l = null
                    }
                }, l.onerror = function () {
                    f(u("Network Error", e, null, l)), l = null
                }, l.ontimeout = function () {
                    f(u("timeout of " + e.timeout + "ms exceeded", e, "ECONNABORTED", l)), l = null
                }, n.isStandardBrowserEnv()) {
                    var m = r(36),
                        y = (e.withCredentials || s(e.url)) && e.xsrfCookieName ? m.read(e.xsrfCookieName) : void 0;
                    y && (h[e.xsrfHeaderName] = y)
                }
                if ("setRequestHeader" in l && n.forEach(h, function (e, t) {
                    void 0 === d && "content-type" === t.toLowerCase() ? delete h[t] : l.setRequestHeader(t, e)
                }), e.withCredentials && (l.withCredentials = !0), e.responseType) try {
                    l.responseType = e.responseType
                } catch (t) {
                    if ("json" !== e.responseType) throw t
                }
                "function" == typeof e.onDownloadProgress && l.addEventListener("progress", e.onDownloadProgress), "function" == typeof e.onUploadProgress && l.upload && l.upload.addEventListener("progress", e.onUploadProgress), e.cancelToken && e.cancelToken.promise.then(function (e) {
                    l && (l.abort(), f(e), l = null)
                }), void 0 === d && (d = null), l.send(d)
            })
        }
    }, function (e, t, r) {
        "use strict";
        var n = r(31);
        e.exports = function (e, t, r, i, a) {
            var o = new Error(e);
            return n(o, t, r, i, a)
        }
    }, function (e, t, r) {
        "use strict";
        e.exports = function (e) {
            return !(!e || !e.__CANCEL__)
        }
    }, function (e, t, r) {
        "use strict";

        function n(e) {
            this.message = e
        }

        n.prototype.toString = function () {
            return "Cancel" + (this.message ? ": " + this.message : "")
        }, n.prototype.__CANCEL__ = !0, e.exports = n
    }, function (e, t, r) {
        var n = r(19), i = r(20), a = r(21);
        e.exports = function (e) {
            return n(e) || i(e) || a()
        }
    }, function (e, t) {
        e.exports = function (e, t) {
            if (!(e instanceof t)) throw new TypeError("Cannot call a class as a function")
        }
    }, function (e, t) {
        function r(e, t) {
            for (var r = 0; r < t.length; r++) {
                var n = t[r];
                n.enumerable = n.enumerable || !1, n.configurable = !0, "value" in n && (n.writable = !0), Object.defineProperty(e, n.key, n)
            }
        }

        e.exports = function (e, t, n) {
            return t && r(e.prototype, t), n && r(e, n), e
        }
    }, function (e, t, r) {
        var n = r(0), i = r(22);
        e.exports = function (e, t) {
            return !t || "object" !== n(t) && "function" != typeof t ? i(e) : t
        }
    }, function (e, t, r) {
        r(2);
        var n = r(23);

        function i(t, r, a) {
            return "undefined" != typeof Reflect && Reflect.get ? e.exports = i = Reflect.get : e.exports = i = function (e, t, r) {
                var i = n(e, t);
                if (i) {
                    var a = Object.getOwnPropertyDescriptor(i, t);
                    return a.get ? a.get.call(r) : a.value
                }
            }, i(t, r, a || t)
        }

        e.exports = i
    }, function (e, t, r) {
        var n = r(24);
        e.exports = function (e, t) {
            if ("function" != typeof t && null !== t) throw new TypeError("Super expression must either be null or a function");
            e.prototype = Object.create(t && t.prototype, {
                constructor: {
                    value: e,
                    writable: !0,
                    configurable: !0
                }
            }), t && n(e, t)
        }
    }, function (e, t, r) {
        e.exports = r(25)
    }, function (e, t) {
        e.exports = function (e) {
            if (!e.webpackPolyfill) {
                var t = Object.create(e);
                t.children || (t.children = []), Object.defineProperty(t, "loaded", {
                    enumerable: !0, get: function () {
                        return t.l
                    }
                }), Object.defineProperty(t, "id", {
                    enumerable: !0, get: function () {
                        return t.i
                    }
                }), Object.defineProperty(t, "exports", {enumerable: !0}), t.webpackPolyfill = 1
            }
            return t
        }
    }, function (e, t) {
        (function (t) {
            e.exports = t
        }).call(this, {})
    }, function (e, t) {
        e.exports = function (e) {
            if (Array.isArray(e)) {
                for (var t = 0, r = new Array(e.length); t < e.length; t++) r[t] = e[t];
                return r
            }
        }
    }, function (e, t) {
        e.exports = function (e) {
            if (Symbol.iterator in Object(e) || "[object Arguments]" === Object.prototype.toString.call(e)) return Array.from(e)
        }
    }, function (e, t) {
        e.exports = function () {
            throw new TypeError("Invalid attempt to spread non-iterable instance")
        }
    }, function (e, t) {
        e.exports = function (e) {
            if (void 0 === e) throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
            return e
        }
    }, function (e, t, r) {
        var n = r(2);
        e.exports = function (e, t) {
            for (; !Object.prototype.hasOwnProperty.call(e, t) && null !== (e = n(e));) ;
            return e
        }
    }, function (e, t) {
        function r(t, n) {
            return e.exports = r = Object.setPrototypeOf || function (e, t) {
                return e.__proto__ = t, e
            }, r(t, n)
        }

        e.exports = r
    }, function (e, t, r) {
        "use strict";
        var n = r(1), i = r(5), a = r(27), o = r(3);

        function s(e) {
            var t = new a(e), r = i(a.prototype.request, t);
            return n.extend(r, a.prototype, t), n.extend(r, t), r
        }

        var u = s(o);
        u.Axios = a, u.create = function (e) {
            return s(n.merge(o, e))
        }, u.Cancel = r(9), u.CancelToken = r(42), u.isCancel = r(8), u.all = function (e) {
            return Promise.all(e)
        }, u.spread = r(43), e.exports = u, e.exports.default = u
    }, function (e, t) {
        function r(e) {
            return !!e.constructor && "function" == typeof e.constructor.isBuffer && e.constructor.isBuffer(e)
        }

        /*!
 * Determine if an object is a Buffer
 *
 * @author   Feross Aboukhadijeh <https://feross.org>
 * @license  MIT
 */
        e.exports = function (e) {
            return null != e && (r(e) || function (e) {
                return "function" == typeof e.readFloatLE && "function" == typeof e.slice && r(e.slice(0, 0))
            }(e) || !!e._isBuffer)
        }
    }, function (e, t, r) {
        "use strict";
        var n = r(3), i = r(1), a = r(37), o = r(38);

        function s(e) {
            this.defaults = e, this.interceptors = {request: new a, response: new a}
        }

        s.prototype.request = function (e) {
            "string" == typeof e && (e = i.merge({url: arguments[0]}, arguments[1])), (e = i.merge(n, {method: "get"}, this.defaults, e)).method = e.method.toLowerCase();
            var t = [o, void 0], r = Promise.resolve(e);
            for (this.interceptors.request.forEach(function (e) {
                t.unshift(e.fulfilled, e.rejected)
            }), this.interceptors.response.forEach(function (e) {
                t.push(e.fulfilled, e.rejected)
            }); t.length;) r = r.then(t.shift(), t.shift());
            return r
        }, i.forEach(["delete", "get", "head", "options"], function (e) {
            s.prototype[e] = function (t, r) {
                return this.request(i.merge(r || {}, {method: e, url: t}))
            }
        }), i.forEach(["post", "put", "patch"], function (e) {
            s.prototype[e] = function (t, r, n) {
                return this.request(i.merge(n || {}, {method: e, url: t, data: r}))
            }
        }), e.exports = s
    }, function (e, t) {
        var r, n, i = e.exports = {};

        function a() {
            throw new Error("setTimeout has not been defined")
        }

        function o() {
            throw new Error("clearTimeout has not been defined")
        }

        function s(e) {
            if (r === setTimeout) return setTimeout(e, 0);
            if ((r === a || !r) && setTimeout) return r = setTimeout, setTimeout(e, 0);
            try {
                return r(e, 0)
            } catch (t) {
                try {
                    return r.call(null, e, 0)
                } catch (t) {
                    return r.call(this, e, 0)
                }
            }
        }

        !function () {
            try {
                r = "function" == typeof setTimeout ? setTimeout : a
            } catch (e) {
                r = a
            }
            try {
                n = "function" == typeof clearTimeout ? clearTimeout : o
            } catch (e) {
                n = o
            }
        }();
        var u, c = [], f = !1, d = -1;

        function h() {
            f && u && (f = !1, u.length ? c = u.concat(c) : d = -1, c.length && l())
        }

        function l() {
            if (!f) {
                var e = s(h);
                f = !0;
                for (var t = c.length; t;) {
                    for (u = c, c = []; ++d < t;) u && u[d].run();
                    d = -1, t = c.length
                }
                u = null, f = !1, function (e) {
                    if (n === clearTimeout) return clearTimeout(e);
                    if ((n === o || !n) && clearTimeout) return n = clearTimeout, clearTimeout(e);
                    try {
                        n(e)
                    } catch (t) {
                        try {
                            return n.call(null, e)
                        } catch (t) {
                            return n.call(this, e)
                        }
                    }
                }(e)
            }
        }

        function p(e, t) {
            this.fun = e, this.array = t
        }

        function b() {
        }

        i.nextTick = function (e) {
            var t = new Array(arguments.length - 1);
            if (arguments.length > 1) for (var r = 1; r < arguments.length; r++) t[r - 1] = arguments[r];
            c.push(new p(e, t)), 1 !== c.length || f || s(l)
        }, p.prototype.run = function () {
            this.fun.apply(null, this.array)
        }, i.title = "browser", i.browser = !0, i.env = {}, i.argv = [], i.version = "", i.versions = {}, i.on = b, i.addListener = b, i.once = b, i.off = b, i.removeListener = b, i.removeAllListeners = b, i.emit = b, i.prependListener = b, i.prependOnceListener = b, i.listeners = function (e) {
            return []
        }, i.binding = function (e) {
            throw new Error("process.binding is not supported")
        }, i.cwd = function () {
            return "/"
        }, i.chdir = function (e) {
            throw new Error("process.chdir is not supported")
        }, i.umask = function () {
            return 0
        }
    }, function (e, t, r) {
        "use strict";
        var n = r(1);
        e.exports = function (e, t) {
            n.forEach(e, function (r, n) {
                n !== t && n.toUpperCase() === t.toUpperCase() && (e[t] = r, delete e[n])
            })
        }
    }, function (e, t, r) {
        "use strict";
        var n = r(7);
        e.exports = function (e, t, r) {
            var i = r.config.validateStatus;
            r.status && i && !i(r.status) ? t(n("Request failed with status code " + r.status, r.config, null, r.request, r)) : e(r)
        }
    }, function (e, t, r) {
        "use strict";
        e.exports = function (e, t, r, n, i) {
            return e.config = t, r && (e.code = r), e.request = n, e.response = i, e
        }
    }, function (e, t, r) {
        "use strict";
        var n = r(1);

        function i(e) {
            return encodeURIComponent(e).replace(/%40/gi, "@").replace(/%3A/gi, ":").replace(/%24/g, "$").replace(/%2C/gi, ",").replace(/%20/g, "+").replace(/%5B/gi, "[").replace(/%5D/gi, "]")
        }

        e.exports = function (e, t, r) {
            if (!t) return e;
            var a;
            if (r) a = r(t); else if (n.isURLSearchParams(t)) a = t.toString(); else {
                var o = [];
                n.forEach(t, function (e, t) {
                    null !== e && void 0 !== e && (n.isArray(e) ? t += "[]" : e = [e], n.forEach(e, function (e) {
                        n.isDate(e) ? e = e.toISOString() : n.isObject(e) && (e = JSON.stringify(e)), o.push(i(t) + "=" + i(e))
                    }))
                }), a = o.join("&")
            }
            return a && (e += (-1 === e.indexOf("?") ? "?" : "&") + a), e
        }
    }, function (e, t, r) {
        "use strict";
        var n = r(1),
            i = ["age", "authorization", "content-length", "content-type", "etag", "expires", "from", "host", "if-modified-since", "if-unmodified-since", "last-modified", "location", "max-forwards", "proxy-authorization", "referer", "retry-after", "user-agent"];
        e.exports = function (e) {
            var t, r, a, o = {};
            return e ? (n.forEach(e.split("\n"), function (e) {
                if (a = e.indexOf(":"), t = n.trim(e.substr(0, a)).toLowerCase(), r = n.trim(e.substr(a + 1)), t) {
                    if (o[t] && i.indexOf(t) >= 0) return;
                    o[t] = "set-cookie" === t ? (o[t] ? o[t] : []).concat([r]) : o[t] ? o[t] + ", " + r : r
                }
            }), o) : o
        }
    }, function (e, t, r) {
        "use strict";
        var n = r(1);
        e.exports = n.isStandardBrowserEnv() ? function () {
            var e, t = /(msie|trident)/i.test(navigator.userAgent), r = document.createElement("a");

            function i(e) {
                var n = e;
                return t && (r.setAttribute("href", n), n = r.href), r.setAttribute("href", n), {
                    href: r.href,
                    protocol: r.protocol ? r.protocol.replace(/:$/, "") : "",
                    host: r.host,
                    search: r.search ? r.search.replace(/^\?/, "") : "",
                    hash: r.hash ? r.hash.replace(/^#/, "") : "",
                    hostname: r.hostname,
                    port: r.port,
                    pathname: "/" === r.pathname.charAt(0) ? r.pathname : "/" + r.pathname
                }
            }

            return e = i(window.location.href), function (t) {
                var r = n.isString(t) ? i(t) : t;
                return r.protocol === e.protocol && r.host === e.host
            }
        }() : function () {
            return !0
        }
    }, function (e, t, r) {
        "use strict";
        var n = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

        function i() {
            this.message = "String contains an invalid character"
        }

        i.prototype = new Error, i.prototype.code = 5, i.prototype.name = "InvalidCharacterError", e.exports = function (e) {
            for (var t, r, a = String(e), o = "", s = 0, u = n; a.charAt(0 | s) || (u = "=", s % 1); o += u.charAt(63 & t >> 8 - s % 1 * 8)) {
                if ((r = a.charCodeAt(s += .75)) > 255) throw new i;
                t = t << 8 | r
            }
            return o
        }
    }, function (e, t, r) {
        "use strict";
        var n = r(1);
        e.exports = n.isStandardBrowserEnv() ? {
            write: function (e, t, r, i, a, o) {
                var s = [];
                s.push(e + "=" + encodeURIComponent(t)), n.isNumber(r) && s.push("expires=" + new Date(r).toGMTString()), n.isString(i) && s.push("path=" + i), n.isString(a) && s.push("domain=" + a), !0 === o && s.push("secure"), document.cookie = s.join("; ")
            }, read: function (e) {
                var t = document.cookie.match(new RegExp("(^|;\\s*)(" + e + ")=([^;]*)"));
                return t ? decodeURIComponent(t[3]) : null
            }, remove: function (e) {
                this.write(e, "", Date.now() - 864e5)
            }
        } : {
            write: function () {
            }, read: function () {
                return null
            }, remove: function () {
            }
        }
    }, function (e, t, r) {
        "use strict";
        var n = r(1);

        function i() {
            this.handlers = []
        }

        i.prototype.use = function (e, t) {
            return this.handlers.push({fulfilled: e, rejected: t}), this.handlers.length - 1
        }, i.prototype.eject = function (e) {
            this.handlers[e] && (this.handlers[e] = null)
        }, i.prototype.forEach = function (e) {
            n.forEach(this.handlers, function (t) {
                null !== t && e(t)
            })
        }, e.exports = i
    }, function (e, t, r) {
        "use strict";
        var n = r(1), i = r(39), a = r(8), o = r(3), s = r(40), u = r(41);

        function c(e) {
            e.cancelToken && e.cancelToken.throwIfRequested()
        }

        e.exports = function (e) {
            return c(e), e.baseURL && !s(e.url) && (e.url = u(e.baseURL, e.url)), e.headers = e.headers || {}, e.data = i(e.data, e.headers, e.transformRequest), e.headers = n.merge(e.headers.common || {}, e.headers[e.method] || {}, e.headers || {}), n.forEach(["delete", "get", "head", "post", "put", "patch", "common"], function (t) {
                delete e.headers[t]
            }), (e.adapter || o.adapter)(e).then(function (t) {
                return c(e), t.data = i(t.data, t.headers, e.transformResponse), t
            }, function (t) {
                return a(t) || (c(e), t && t.response && (t.response.data = i(t.response.data, t.response.headers, e.transformResponse))), Promise.reject(t)
            })
        }
    }, function (e, t, r) {
        "use strict";
        var n = r(1);
        e.exports = function (e, t, r) {
            return n.forEach(r, function (r) {
                e = r(e, t)
            }), e
        }
    }, function (e, t, r) {
        "use strict";
        e.exports = function (e) {
            return /^([a-z][a-z\d\+\-\.]*:)?\/\//i.test(e)
        }
    }, function (e, t, r) {
        "use strict";
        e.exports = function (e, t) {
            return t ? e.replace(/\/+$/, "") + "/" + t.replace(/^\/+/, "") : e
        }
    }, function (e, t, r) {
        "use strict";
        var n = r(9);

        function i(e) {
            if ("function" != typeof e) throw new TypeError("executor must be a function.");
            var t;
            this.promise = new Promise(function (e) {
                t = e
            });
            var r = this;
            e(function (e) {
                r.reason || (r.reason = new n(e), t(r.reason))
            })
        }

        i.prototype.throwIfRequested = function () {
            if (this.reason) throw this.reason
        }, i.source = function () {
            var e;
            return {
                token: new i(function (t) {
                    e = t
                }), cancel: e
            }
        }, e.exports = i
    }, function (e, t, r) {
        "use strict";
        e.exports = function (e) {
            return function (t) {
                return e.apply(null, t)
            }
        }
    }, function (e, t, r) {
        "use strict";
        r.r(t);
        var n = r(10), i = r.n(n), a = r(11), o = r.n(a), s = r(12), u = r.n(s), c = r(13), f = r.n(c), d = r(2),
            h = r.n(d), l = r(14), p = r.n(l), b = r(15), v = r.n(b), g = r(16), m = r.n(g);
        r(4);
        var y = function (e) {
            function t(e) {
                var r;
                return o()(this, t), (r = f()(this, h()(t).call(this, e))).ready = !1, r.queue = [], r
            }

            return v()(t, e), u()(t, [{
                key: "configure", value: function (e) {
                    for (this.host = e, this.instance = m.a.create({
                        baseURL: e,
                        timeout: 3e4
                    }), this.ready = !0; this.queue.length;) {
                        var t = this.queue.shift(), r = t.args, n = t.resolve, a = t.reject;
                        this.request.apply(this, i()(r)).then(n).catch(a).then(function () {
                        })
                    }
                }
            }, {
                key: "request", value: function (e) {
                    var r = this, n = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : {},
                        i = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : "get";
                    return this.ready ? p()(h()(t.prototype), "request", this).call(this, e, n, i).then(function (e) {
                        var t = e.transaction || e;
                        return Object.defineProperty(t, "__payload__", {
                            writable: !1,
                            enumerable: !1,
                            configurable: !1,
                            value: n
                        }), e
                    }) : new Promise(function (t, a) {
                        r.queue.push({args: [e, n, i], resolve: t, reject: a})
                    })
                }
            }]), t
        }(window.TronWeb.providers.HttpProvider);
        r(4);
        window.TronWeb.providers.HttpProvider, navigator.userAgent.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/);
        var w = window.iTron ? window.iTron.isTest() : "",
            x = window.iTron && window.iTron.getChainId ? window.iTron.getChainId() : "",
            _ = w ? "https://api.shasta.trongrid.io" : "https://api.trongrid.io", A = new y(_), k = new y(_),
            S = new y(_), M = null, I = null;
        if ("DAppChain" == x) {
            var E = window.iTron && window.iTron.getCurrentChainNode && window.iTron.getCurrentChainNode() ? JSON.parse(window.iTron.getCurrentChainNode()) : "";
            (M = new TronWeb({
                fullNode: A,
                solidityNode: k,
                eventServer: S,
                sideOptions: {
                    fullNode: "https://sun.tronex.io",
                    solidityNode: "https://sun.tronex.io",
                    eventServer: "https://sun.tronex.io",
                    mainGatewayAddress: E.mainGatewayAddress,
                    sideGatewayAddress: E.sideGatewayAddress,
                    sideChainId: E.sideChainId
                }
            })).fullNode.configure(_), M.solidityNode.configure(_), M.eventServer.configure(_), I = M.sidechain
        } else (M = new TronWeb({
            fullNode: A,
            solidityNode: k,
            eventServer: S
        })).fullNode.configure(_), M.solidityNode.configure(_), M.eventServer.configure(_);

        function N(e, t) {
            var r = "[object String]" === Object.prototype.toString.call(e), n = e;
            return r || (console.log("1111111", e, e.__payload__), M && M.trx.cache && M.trx.cache.contracts && e.__payload__ && e.__payload__.contract_address && M.trx.cache.contracts[e.__payload__.contract_address] && (e.__payload__.abi = M.trx.cache.contracts[e.__payload__.contract_address].abi), e.payInfo = e.__payload__, console.log("22222222", e.payInfo), n = JSON.stringify(e)), new Promise(function (e, i) {
                window.callback = function (n) {
                    r ? t ? t(null, n) : e(n) : e(JSON.parse(n))
                }, window.onerror = function (e) {
                    i(e)
                }, window.iTron.sign(n, "callback")
            })
        }

        M.setHeader = function () {
            arguments.length > 0 && void 0 !== arguments[0] && arguments[0]
        }, M.trx.sign = N, M.trx.signTransaction = N, I && (I.mainchain.trx.sign = N, I.mainchain.trx.signTransaction = N, I.sidechain.trx.sign = N, I.sidechain.trx.signTransaction = N);
        var P = window.iTron ? window.iTron.getCurrentAccount() : "";
        console.log("inject tronWeb start"), M.setAddress(P), M.ready = !0, window.tronWeb = M, I && (I.mainchain.setAddress(P), I.sidechain.setAddress(P), window.sunWeb = I), console.log("inject tronWeb success")
    }]).default
});