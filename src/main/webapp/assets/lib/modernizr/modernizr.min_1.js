/* Modernizr (Custom Build) | MIT & BSD
 * Build: http://modernizr.com/download/#-inputtypes-touch-fullscreen_api-localstorage-load-cssclasses-addtest-prefixed-teststyles-testprops-testallprops-prefixes-domprefixes-canvas
 */
;
window.Modernizr = function (e, t, n) {
    function C(e) {
        f.cssText = e
    }
    function k(e, t) {
        return C(p.join(e + ";") + (t || ""))
    }
    function L(e, t) {
        return typeof e === t
    }
    function A(e, t) {
        return!!~("" + e).indexOf(t)
    }
    function O(e, t) {
        for (var r in e) {
            var i = e[r];
            if (!A(i, "-") && f[i] !== n)
                return t == "pfx" ? i : !0
        }
        return!1
    }
    function M(e, t, r) {
        for (var i in e) {
            var s = t[e[i]];
            if (s !== n)
                return r === !1 ? e[i] : L(s, "function") ? s.bind(r || t) : s
        }
        return!1
    }
    function _(e, t, n) {
        var r = e.charAt(0).toUpperCase() + e.slice(1), i = (e + " " + v.join(r + " ") + r).split(" ");
        return L(t, "string") || L(t, "undefined") ? O(i, t) : (i = (e + " " + m.join(r + " ") + r).split(" "), M(i, t, n))
    }
    function D() {
        i.inputtypes = function (e) {
            for (var r = 0, i, s, u, a = e.length; r < a; r++)
                l.setAttribute("type", s = e[r]), i = l.type !== "text", i && (l.value = c, l.style.cssText = "position:absolute;visibility:hidden;", /^range$/.test(s) && l.style.WebkitAppearance !== n ? (o.appendChild(l), u = t.defaultView, i = u.getComputedStyle && u.getComputedStyle(l, null).WebkitAppearance !== "textfield" && l.offsetHeight !== 0, o.removeChild(l)) : /^(search|tel)$/.test(s) || (/^(url|email)$/.test(s) ? i = l.checkValidity && l.checkValidity() === !1 : i = l.value != c)), y[e[r]] = !!i;
            return y
        }("search tel url email datetime date month week time datetime-local number range color".split(" "))
    }
    var r = "2.8.3", i = {}, s = !0, o = t.documentElement, u = "modernizr", a = t.createElement(u), f = a.style, l = t.createElement("input"), c = ":)", h = {}.toString, p = " -webkit- -moz- -o- -ms- ".split(" "), d = "Webkit Moz O ms", v = d.split(" "), m = d.toLowerCase().split(" "), g = {}, y = {}, b = {}, w = [], E = w.slice, S, x = function (e, n, r, i) {
        var s, a, f, l, c = t.createElement("div"), h = t.body, p = h || t.createElement("body");
        if (parseInt(r, 10))
            while (r--)
                f = t.createElement("div"), f.id = i ? i[r] : u + (r + 1), c.appendChild(f);
        return s = ["&#173;", '<style id="s', u, '">', e, "</style>"].join(""), c.id = u, (h ? c : p).innerHTML += s, p.appendChild(c), h || (p.style.background = "", p.style.overflow = "hidden", l = o.style.overflow, o.style.overflow = "hidden", o.appendChild(p)), a = n(c, e), h ? c.parentNode.removeChild(c) : (p.parentNode.removeChild(p), o.style.overflow = l), !!a
    }, T = {}.hasOwnProperty, N;
    !L(T, "undefined") && !L(T.call, "undefined") ? N = function (e, t) {
        return T.call(e, t)
    } : N = function (e, t) {
        return t in e && L(e.constructor.prototype[t], "undefined")
    }, Function.prototype.bind || (Function.prototype.bind = function (t) {
        var n = this;
        if (typeof n != "function")
            throw new TypeError;
        var r = E.call(arguments, 1), i = function () {
            if (this instanceof i) {
                var e = function () {
                };
                e.prototype = n.prototype;
                var s = new e, o = n.apply(s, r.concat(E.call(arguments)));
                return Object(o) === o ? o : s
            }
            return n.apply(t, r.concat(E.call(arguments)))
        };
        return i
    }), g.canvas = function () {
        var e = t.createElement("canvas");
        return!!e.getContext && !!e.getContext("2d")
    }, g.touch = function () {
        var n;
        return"ontouchstart"in e || e.DocumentTouch && t instanceof DocumentTouch ? n = !0 : x(["@media (", p.join("touch-enabled),("), u, ")", "{#modernizr{top:9px;position:absolute}}"].join(""), function (e) {
            n = e.offsetTop === 9
        }), n
    }, g.localstorage = function () {
        try {
            return localStorage.setItem(u, u), localStorage.removeItem(u), !0
        } catch (e) {
            return!1
        }
    };
    for (var P in g)
        N(g, P) && (S = P.toLowerCase(), i[S] = g[P](), w.push((i[S] ? "" : "no-") + S));
    return i.input || D(), i.addTest = function (e, t) {
        if (typeof e == "object")
            for (var r in e)
                N(e, r) && i.addTest(r, e[r]);
        else {
            e = e.toLowerCase();
            if (i[e] !== n)
                return i;
            t = typeof t == "function" ? t() : t, typeof s != "undefined" && s && (o.className += " " + (t ? "" : "no-") + e), i[e] = t
        }
        return i
    }, C(""), a = l = null, i._version = r, i._prefixes = p, i._domPrefixes = m, i._cssomPrefixes = v, i.testProp = function (e) {
        return O([e])
    }, i.testAllProps = _, i.testStyles = x, i.prefixed = function (e, t, n) {
        return t ? _(e, t, n) : _(e, "pfx")
    }, o.className = o.className.replace(/(^|\s)no-js(\s|$)/, "$1$2") + (s ? " js " + w.join(" ") : ""), i
}(this, this.document), function (e, t, n) {
    function r(e) {
        return"[object Function]" == d.call(e)
    }
    function i(e) {
        return"string" == typeof e
    }
    function s() {
    }
    function o(e) {
        return!e || "loaded" == e || "complete" == e || "uninitialized" == e
    }
    function u() {
        var e = v.shift();
        m = 1, e ? e.t ? h(function () {
            ("c" == e.t ? k.injectCss : k.injectJs)(e.s, 0, e.a, e.x, e.e, 1)
        }, 0) : (e(), u()) : m = 0
    }
    function a(e, n, r, i, s, a, f) {
        function l(t) {
            if (!d && o(c.readyState) && (w.r = d = 1, !m && u(), c.onload = c.onreadystatechange = null, t)) {
                "img" != e && h(function () {
                    b.removeChild(c)
                }, 50);
                for (var r in T[n])
                    T[n].hasOwnProperty(r) && T[n][r].onload()
            }
        }
        var f = f || k.errorTimeout, c = t.createElement(e), d = 0, g = 0, w = {t: r, s: n, e: s, a: a, x: f};
        1 === T[n] && (g = 1, T[n] = []), "object" == e ? c.data = n : (c.src = n, c.type = e), c.width = c.height = "0", c.onerror = c.onload = c.onreadystatechange = function () {
            l.call(this, g)
        }, v.splice(i, 0, w), "img" != e && (g || 2 === T[n] ? (b.insertBefore(c, y ? null : p), h(l, f)) : T[n].push(c))
    }
    function f(e, t, n, r, s) {
        return m = 0, t = t || "j", i(e) ? a("c" == t ? E : w, e, t, this.i++, n, r, s) : (v.splice(this.i++, 0, e), 1 == v.length && u()), this
    }
    function l() {
        var e = k;
        return e.loader = {load: f, i: 0}, e
    }
    var c = t.documentElement, h = e.setTimeout, p = t.getElementsByTagName("script")[0], d = {}.toString, v = [], m = 0, g = "MozAppearance"in c.style, y = g && !!t.createRange().compareNode, b = y ? c : p.parentNode, c = e.opera && "[object Opera]" == d.call(e.opera), c = !!t.attachEvent && !c, w = g ? "object" : c ? "script" : "img", E = c ? "script" : w, S = Array.isArray || function (e) {
        return"[object Array]" == d.call(e)
    }, x = [], T = {}, N = {timeout: function (e, t) {
            return t.length && (e.timeout = t[0]), e
        }}, C, k;
    k = function (e) {
        function t(e) {
            var e = e.split("!"), t = x.length, n = e.pop(), r = e.length, n = {url: n, origUrl: n, prefixes: e}, i, s, o;
            for (s = 0; s < r; s++)
                o = e[s].split("="), (i = N[o.shift()]) && (n = i(n, o));
            for (s = 0; s < t; s++)
                n = x[s](n);
            return n
        }
        function o(e, i, s, o, u) {
            var a = t(e), f = a.autoCallback;
            a.url.split(".").pop().split("?").shift(), a.bypass || (i && (i = r(i) ? i : i[e] || i[o] || i[e.split("/").pop().split("?")[0]]), a.instead ? a.instead(e, i, s, o, u) : (T[a.url] ? a.noexec = !0 : T[a.url] = 1, s.load(a.url, a.forceCSS || !a.forceJS && "css" == a.url.split(".").pop().split("?").shift() ? "c" : n, a.noexec, a.attrs, a.timeout), (r(i) || r(f)) && s.load(function () {
                l(), i && i(a.origUrl, u, o), f && f(a.origUrl, u, o), T[a.url] = 2
            })))
        }
        function u(e, t) {
            function n(e, n) {
                if (e) {
                    if (i(e))
                        n || (f = function () {
                            var e = [].slice.call(arguments);
                            l.apply(this, e), c()
                        }), o(e, f, t, 0, u);
                    else if (Object(e) === e)
                        for (p in h = function () {
                            var t = 0, n;
                            for (n in e)
                                e.hasOwnProperty(n) && t++;
                            return t
                        }(), e)
                            e.hasOwnProperty(p) && (!n && !--h && (r(f) ? f = function () {
                                var e = [].slice.call(arguments);
                                l.apply(this, e), c()
                            } : f[p] = function (e) {
                                return function () {
                                    var t = [].slice.call(arguments);
                                    e && e.apply(this, t), c()
                                }
                            }(l[p])), o(e[p], f, t, p, u))
                } else
                    !n && c()
            }
            var u = !!e.test, a = e.load || e.both, f = e.callback || s, l = f, c = e.complete || s, h, p;
            n(u ? e.yep : e.nope, !!a), a && n(a)
        }
        var a, f, c = this.yepnope.loader;
        if (i(e))
            o(e, 0, c, 0);
        else if (S(e))
            for (a = 0; a < e.length; a++)
                f = e[a], i(f) ? o(f, 0, c, 0) : S(f) ? k(f) : Object(f) === f && u(f, c);
        else
            Object(e) === e && u(e, c)
    }, k.addPrefix = function (e, t) {
        N[e] = t
    }, k.addFilter = function (e) {
        x.push(e)
    }, k.errorTimeout = 1e4, null == t.readyState && t.addEventListener && (t.readyState = "loading", t.addEventListener("DOMContentLoaded", C = function () {
        t.removeEventListener("DOMContentLoaded", C, 0), t.readyState = "complete"
    }, 0)), e.yepnope = l(), e.yepnope.executeStack = u, e.yepnope.injectJs = function (e, n, r, i, a, f) {
        var l = t.createElement("script"), c, d, i = i || k.errorTimeout;
        l.src = e;
        for (d in r)
            l.setAttribute(d, r[d]);
        n = f ? u : n || s, l.onreadystatechange = l.onload = function () {
            !c && o(l.readyState) && (c = 1, n(), l.onload = l.onreadystatechange = null)
        }, h(function () {
            c || (c = 1, n(1))
        }, i), a ? l.onload() : p.parentNode.insertBefore(l, p)
    }, e.yepnope.injectCss = function (e, n, r, i, o, a) {
        var i = t.createElement("link"), f, n = a ? u : n || s;
        i.href = e, i.rel = "stylesheet", i.type = "text/css";
        for (f in r)
            i.setAttribute(f, r[f]);
        o || (p.parentNode.insertBefore(i, p), h(n, 0))
    }
}(this, document), Modernizr.load = function () {
    yepnope.apply(window, [].slice.call(arguments, 0))
}, Modernizr.addTest("fullscreen", function () {
    for (var e = 0; e < Modernizr._domPrefixes.length; e++)
        if (document[Modernizr._domPrefixes[e].toLowerCase() + "CancelFullScreen"])
            return!0;
    return!!document.cancelFullScreen || !1
});