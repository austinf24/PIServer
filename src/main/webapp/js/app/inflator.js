define(["jquery","./trial/trial_sets","./stimulus/stimulus_sets","./media/media_sets"],function(e,t,n,r){var i=function(t){return typeof t.customize=="function"&&t.customize.apply(t,[t]),t},s=function(o,u,a){var f,l,c,h;switch(u){case"trial":f=t();break;case"stimulus":f=n();break;case"media":f=r();break;default:throw new Error("Unknown set type "+u)}if(!o.inherit)return!a&&i(o),o;l=e.isPlainObject(o.inherit)?o.inherit:{set:o.inherit};if(!f[l.set])throw new Error("Unknown "+u+"Set: "+l.set);return c=s(f[l.set].inherit(l),u,!0),h=e.extend(!0,{},o),e.each(c,function(t,n){h[t]||(e.isArray(c[t])?h[t]=e.extend(!0,[],n):typeof c[t]=="object"?h[t]=e.extend(!0,{},n):h[t]=n)}),c.data&&(h.data=e.extend(!0,{},c.data,h.data)),!a&&i(h),h};return s});