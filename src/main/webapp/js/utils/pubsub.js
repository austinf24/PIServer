define(["underscore"],function(e){var t={},n={},r={};return t.publish=function(r,i){n[r]&&e.each(n[r],function(e){e.apply(t,i||[])})},t.subscribe=function(t,i){var s;return e.isFunction(i)?s=[]:(s=arguments[1],i=arguments[2]),n[t]||(n[t]={},r[t]=0),n[t][r[t]++]=i,s.push([t,i]),[t,i]},t.unsubscribe=function(t){var r=t[0];n[r]&&e.each(n[r],function(e,i){e==t[1]&&delete n[r][i]})},t});