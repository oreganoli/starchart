var n, l, u, t, i, o, r, f = {}, e = [], c = /acit|ex(?:s|g|n|p|$)|rph|grid|ows|mnc|ntw|ine[ch]|zoo|^ord/i;

function s(n, l) {
    for (var u in l) n[u] = l[u];
    return n
}

function a(n) {
    var l = n.parentNode;
    l && l.removeChild(n);
}

function h(n, l, u) {
    var t, i = arguments, o = {};
    for (t in l) "key" !== t && "ref" !== t && (o[t] = l[t]);
    if (arguments.length > 3) for (u = [u], t = 3; t < arguments.length; t++) u.push(i[t]);
    if (null != u && (o.children = u), "function" == typeof n && null != n.defaultProps) for (t in n.defaultProps) void 0 === o[t] && (o[t] = n.defaultProps[t]);
    return v(n, o, l && l.key, l && l.ref)
}

function v(l, u, t, i) {
    var o = {
        type: l,
        props: u,
        key: t,
        ref: i,
        __k: null,
        __: null,
        __b: 0,
        __e: null,
        __d: null,
        __c: null,
        constructor: void 0
    };
    return n.vnode && n.vnode(o), o
}

function p() {
    return {}
}

function y(n) {
    return n.children
}

function d(n, l) {
    this.props = n, this.context = l;
}

function m(n, l) {
    if (null == l) return n.__ ? m(n.__, n.__.__k.indexOf(n) + 1) : null;
    for (var u; l < n.__k.length; l++) if (null != (u = n.__k[l]) && null != u.__e) return u.__e;
    return "function" == typeof n.type ? m(n) : null
}

function w(n) {
    var l, u;
    if (null != (n = n.__) && null != n.__c) {
        for (n.__e = n.__c.base = null, l = 0; l < n.__k.length; l++) if (null != (u = n.__k[l]) && null != u.__e) {
            n.__e = n.__c.base = u.__e;
            break
        }
        return w(n)
    }
}

function g(l) {
    (!l.__d && (l.__d = !0) && 1 === u.push(l) || i !== n.debounceRendering) && ((i = n.debounceRendering) || t)(k);
}

function k() {
    var n, l, t, i, o, r, f;
    for (u.sort(function (n, l) {
        return l.__v.__b - n.__v.__b
    }); n = u.pop();) n.__d && (t = void 0, i = void 0, r = (o = (l = n).__v).__e, (f = l.__P) && (t = [], i = T(f, o, s({}, o), l.__n, void 0 !== f.ownerSVGElement, null, t, null == r ? m(o) : r), $(t, o), i != r && w(o)));
}

function _(n, l, u, t, i, o, r, c, s) {
    var h, v, p, y, d, w, g, k = u && u.__k || e, _ = k.length;
    if (c == f && (c = null != o ? o[0] : _ ? m(u, 0) : null), h = 0, l.__k = b(l.__k, function (u) {
        if (null != u) {
            if (u.__ = l, u.__b = l.__b + 1, null === (p = k[h]) || p && u.key == p.key && u.type === p.type) k[h] = void 0; else for (v = 0; v < _; v++) {
                if ((p = k[v]) && u.key == p.key && u.type === p.type) {
                    k[v] = void 0;
                    break
                }
                p = null;
            }
            if (y = T(n, u, p = p || f, t, i, o, r, c, s), (v = u.ref) && p.ref != v && (g || (g = []), p.ref && g.push(p.ref, null, u), g.push(v, u.__c || y, u)), null != y) {
                if (null == w && (w = y), null != u.__d) y = u.__d, u.__d = null; else if (o == p || y != c || null == y.parentNode) {
                    n:if (null == c || c.parentNode !== n) n.appendChild(y); else {
                        for (d = c, v = 0; (d = d.nextSibling) && v < _; v += 2) if (d == y) break n;
                        n.insertBefore(y, c);
                    }
                    "option" == l.type && (n.value = "");
                }
                c = y.nextSibling, "function" == typeof l.type && (l.__d = y);
            }
        }
        return h++, u
    }), l.__e = w, null != o && "function" != typeof l.type) for (h = o.length; h--;) null != o[h] && a(o[h]);
    for (h = _; h--;) null != k[h] && A(k[h], k[h]);
    if (g) for (h = 0; h < g.length; h++) z(g[h], g[++h], g[++h]);
}

function b(n, l, u) {
    if (null == u && (u = []), null == n || "boolean" == typeof n) l && u.push(l(null)); else if (Array.isArray(n)) for (var t = 0; t < n.length; t++) b(n[t], l, u); else u.push(l ? l("string" == typeof n || "number" == typeof n ? v(null, n, null, null) : null != n.__e || null != n.__c ? v(n.type, n.props, n.key, null) : n) : n);
    return u
}

function x(n, l, u, t, i) {
    var o;
    for (o in u) o in l || P(n, o, null, u[o], t);
    for (o in l) i && "function" != typeof l[o] || "value" === o || "checked" === o || u[o] === l[o] || P(n, o, l[o], u[o], t);
}

function C(n, l, u) {
    "-" === l[0] ? n.setProperty(l, u) : n[l] = "number" == typeof u && !1 === c.test(l) ? u + "px" : null == u ? "" : u;
}

function P(n, l, u, t, i) {
    var o, r, f, e, c;
    if (i ? "className" === l && (l = "class") : "class" === l && (l = "className"), "key" === l || "children" === l) ; else if ("style" === l) if (o = n.style, "string" == typeof u) o.cssText = u; else {
        if ("string" == typeof t && (o.cssText = "", t = null), t) for (r in t) u && r in u || C(o, r, "");
        if (u) for (f in u) t && u[f] === t[f] || C(o, f, u[f]);
    } else "o" === l[0] && "n" === l[1] ? (e = l !== (l = l.replace(/Capture$/, "")), c = l.toLowerCase(), l = (c in n ? c : l).slice(2), u ? (t || n.addEventListener(l, N, e), (n.l || (n.l = {}))[l] = u) : n.removeEventListener(l, N, e)) : "list" !== l && "tagName" !== l && "form" !== l && "type" !== l && !i && l in n ? n[l] = null == u ? "" : u : "function" != typeof u && "dangerouslySetInnerHTML" !== l && (l !== (l = l.replace(/^xlink:?/, "")) ? null == u || !1 === u ? n.removeAttributeNS("http://www.w3.org/1999/xlink", l.toLowerCase()) : n.setAttributeNS("http://www.w3.org/1999/xlink", l.toLowerCase(), u) : null == u || !1 === u ? n.removeAttribute(l) : n.setAttribute(l, u));
}

function N(l) {
    this.l[l.type](n.event ? n.event(l) : l);
}

function T(l, u, t, i, o, r, f, e, c) {
    var a, h, v, p, m, w, g, k, x, C, P = u.type;
    if (void 0 !== u.constructor) return null;
    (a = n.__b) && a(u);
    try {
        n:if ("function" == typeof P) {
            if (k = u.props, x = (a = P.contextType) && i[a.__c], C = a ? x ? x.props.value : a.__ : i, t.__c ? g = (h = u.__c = t.__c).__ = h.__E : ("prototype" in P && P.prototype.render ? u.__c = h = new P(k, C) : (u.__c = h = new d(k, C), h.constructor = P, h.render = D), x && x.sub(h), h.props = k, h.state || (h.state = {}), h.context = C, h.__n = i, v = h.__d = !0, h.__h = []), null == h.__s && (h.__s = h.state), null != P.getDerivedStateFromProps && (h.__s == h.state && (h.__s = s({}, h.__s)), s(h.__s, P.getDerivedStateFromProps(k, h.__s))), p = h.props, m = h.state, v) null == P.getDerivedStateFromProps && null != h.componentWillMount && h.componentWillMount(), null != h.componentDidMount && h.__h.push(h.componentDidMount); else {
                if (null == P.getDerivedStateFromProps && k !== p && null != h.componentWillReceiveProps && h.componentWillReceiveProps(k, C), !h.__e && null != h.shouldComponentUpdate && !1 === h.shouldComponentUpdate(k, h.__s, C)) {
                    for (h.props = k, h.state = h.__s, h.__d = !1, h.__v = u, u.__e = t.__e, u.__k = t.__k, h.__h.length && f.push(h), a = 0; a < u.__k.length; a++) u.__k[a] && (u.__k[a].__ = u);
                    break n
                }
                null != h.componentWillUpdate && h.componentWillUpdate(k, h.__s, C), null != h.componentDidUpdate && h.__h.push(function () {
                    h.componentDidUpdate(p, m, w);
                });
            }
            h.context = C, h.props = k, h.state = h.__s, (a = n.__r) && a(u), h.__d = !1, h.__v = u, h.__P = l, a = h.render(h.props, h.state, h.context), u.__k = b(null != a && a.type == y && null == a.key ? a.props.children : a), null != h.getChildContext && (i = s(s({}, i), h.getChildContext())), v || null == h.getSnapshotBeforeUpdate || (w = h.getSnapshotBeforeUpdate(p, m)), _(l, u, t, i, o, r, f, e, c), h.base = u.__e, h.__h.length && f.push(h), g && (h.__E = h.__ = null), h.__e = null;
        } else u.__e = j(t.__e, u, t, i, o, r, f, c);
        (a = n.diffed) && a(u);
    } catch (l) {
        n.__e(l, u, t);
    }
    return u.__e
}

function $(l, u) {
    n.__c && n.__c(u, l), l.some(function (u) {
        try {
            l = u.__h, u.__h = [], l.some(function (n) {
                n.call(u);
            });
        } catch (l) {
            n.__e(l, u.__v);
        }
    });
}

function j(n, l, u, t, i, o, r, c) {
    var s, a, h, v, p, y = u.props, d = l.props;
    if (i = "svg" === l.type || i, null == n && null != o) for (s = 0; s < o.length; s++) if (null != (a = o[s]) && (null === l.type ? 3 === a.nodeType : a.localName === l.type)) {
        n = a, o[s] = null;
        break
    }
    if (null == n) {
        if (null === l.type) return document.createTextNode(d);
        n = i ? document.createElementNS("http://www.w3.org/2000/svg", l.type) : document.createElement(l.type), o = null;
    }
    if (null === l.type) null != o && (o[o.indexOf(n)] = null), y !== d && n.data != d && (n.data = d); else if (l !== u) {
        if (null != o && (o = e.slice.call(n.childNodes)), h = (y = u.props || f).dangerouslySetInnerHTML, v = d.dangerouslySetInnerHTML, !c) {
            if (y === f) for (y = {}, p = 0; p < n.attributes.length; p++) y[n.attributes[p].name] = n.attributes[p].value;
            (v || h) && (v && h && v.__html == h.__html || (n.innerHTML = v && v.__html || ""));
        }
        x(n, d, y, i, c), l.__k = l.props.children, v || _(n, l, u, t, "foreignObject" !== l.type && i, o, r, f, c), c || ("value" in d && void 0 !== d.value && d.value !== n.value && (n.value = null == d.value ? "" : d.value), "checked" in d && void 0 !== d.checked && d.checked !== n.checked && (n.checked = d.checked));
    }
    return n
}

function z(l, u, t) {
    try {
        "function" == typeof l ? l(u) : l.current = u;
    } catch (l) {
        n.__e(l, t);
    }
}

function A(l, u, t) {
    var i, o, r;
    if (n.unmount && n.unmount(l), (i = l.ref) && (i.current && i.current !== l.__e || z(i, null, u)), t || "function" == typeof l.type || (t = null != (o = l.__e)), l.__e = l.__d = null, null != (i = l.__c)) {
        if (i.componentWillUnmount) try {
            i.componentWillUnmount();
        } catch (l) {
            n.__e(l, u);
        }
        i.base = i.__P = null;
    }
    if (i = l.__k) for (r = 0; r < i.length; r++) i[r] && A(i[r], u, t);
    null != o && a(o);
}

function D(n, l, u) {
    return this.constructor(n, u)
}

function E(l, u, t) {
    var i, r, c;
    n.__ && n.__(l, u), r = (i = t === o) ? null : t && t.__k || u.__k, l = h(y, null, [l]), c = [], T(u, (i ? u : t || u).__k = l, r || f, f, void 0 !== u.ownerSVGElement, t && !i ? [t] : r ? null : e.slice.call(u.childNodes), c, t || f, i), $(c, l);
}

function H(n, l) {
    E(n, l, o);
}

function I(n, l) {
    return l = s(s({}, n.props), l), arguments.length > 2 && (l.children = e.slice.call(arguments, 2)), v(n.type, l, l.key || n.key, l.ref || n.ref)
}

function L(n) {
    var l = {}, u = {
        __c: "__cC" + r++, __: n, Consumer: function (n, l) {
            return n.children(l)
        }, Provider: function (n) {
            var t, i = this;
            return this.getChildContext || (t = [], this.getChildContext = function () {
                return l[u.__c] = i, l
            }, this.shouldComponentUpdate = function (l) {
                n.value !== l.value && t.some(function (n) {
                    n.context = l.value, g(n);
                });
            }, this.sub = function (n) {
                t.push(n);
                var l = n.componentWillUnmount;
                n.componentWillUnmount = function () {
                    t.splice(t.indexOf(n), 1), l && l.call(n);
                };
            }), n.children
        }
    };
    return u.Consumer.contextType = u, u
}

n = {
    __e: function (n, l) {
        for (var u, t; l = l.__;) if ((u = l.__c) && !u.__) try {
            if (u.constructor && null != u.constructor.getDerivedStateFromError && (t = !0, u.setState(u.constructor.getDerivedStateFromError(n))), null != u.componentDidCatch && (t = !0, u.componentDidCatch(n)), t) return g(u.__E = u)
        } catch (l) {
            n = l;
        }
        throw n
    }
}, l = function (n) {
    return null != n && void 0 === n.constructor
}, d.prototype.setState = function (n, l) {
    var u;
    u = this.__s !== this.state ? this.__s : this.__s = s({}, this.state), "function" == typeof n && (n = n(u, this.props)), n && s(u, n), null != n && this.__v && (this.__e = !1, l && this.__h.push(l), g(this));
}, d.prototype.forceUpdate = function (n) {
    this.__v && (this.__e = !0, n && this.__h.push(n), g(this));
}, d.prototype.render = y, u = [], t = "function" == typeof Promise ? Promise.prototype.then.bind(Promise.resolve()) : setTimeout, o = f, r = 0;

export {
    d as Component,
    y as Fragment,
    A as _unmount,
    I as cloneElement,
    L as createContext,
    h as createElement,
    p as createRef,
    h,
    H as hydrate,
    l as isValidElement,
    n as options,
    E as render,
    b as toChildArray
};
