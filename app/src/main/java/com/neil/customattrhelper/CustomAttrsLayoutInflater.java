package com.neil.customattrhelper;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nzbao
 * @CreateTime 2018/1/12
 * @Desc
 */
public class CustomAttrsLayoutInflater extends LayoutInflater {
    private CustomAttr[] customAttrs;

    private static int tagId = R.id.customAttrTagId;

    private List<OnViewCreateListener> listeners=new ArrayList<>();

    private String TAG = this.getClass().getSimpleName();


    public CustomAttrsLayoutInflater(LayoutInflater original, Context newContext) {
        super(original, newContext);
        setFactory2(new CustomAttrsFactory(this));
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new CustomAttrsLayoutInflater(this, newContext);
    }

    public void addOnViewCreateListener(OnViewCreateListener listener){
        listeners.add(listener);
    }

    public void removeOnViewCreateListener(OnViewCreateListener listener){
        if(listeners.contains(listener)){
            listeners.remove(listener);
        }
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public void setCustomAttrs(CustomAttr... customAttrs) {
        this.customAttrs = customAttrs;
    }

    class CustomAttrsFactory implements Factory2 {
        private final String[] prefixes = {"android.view.", "android.widget.", "android.webkit."};
        private LayoutInflater inflater;


        public CustomAttrsFactory(LayoutInflater inflater) {
            this.inflater = inflater;
        }


        @Override
        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            View view = createView(name, context, attrs);
            if (view != null) {
                getCustomAttrs(context, attrs, view);
            }
            return view;
        }

        /**
         * 获取自定义属性
         *
         * @param context
         * @param attrs
         * @param view
         */
        private void getCustomAttrs(Context context, AttributeSet attrs, View view) {
            if (customAttrs == null || customAttrs.length == 0) {
                return;
            }
            ArrayList<CustomAttr> tag = new ArrayList<>();
            for (int i = 0; i < customAttrs.length; i++) {
                TypedArray typedArray = context.obtainStyledAttributes(attrs, new int[]{customAttrs[i].attrId});
                TypedValue typedValue = typedArray.peekValue(0);
                if (typedValue == null) {
                    typedArray.recycle();
                    continue;
                }
                CustomAttr attr = new CustomAttr(customAttrs[i].attrId, customAttrs[i].defaultValue);
                switch (typedValue.type) {
                    case TypedValue.TYPE_FLOAT://float
                        attr.value = typedArray.getFloat(0, (float) customAttrs[i].defaultValue);
                        break;
                    case TypedValue.TYPE_INT_DEC://integer
                        attr.value = typedArray.getInt(0, (int) customAttrs[i].defaultValue);
                        break;
                    case TypedValue.TYPE_STRING://string
                        attr.value = typedArray.getString(0);
                        break;
                    case TypedValue.TYPE_INT_BOOLEAN://boolean
                        attr.value = typedArray.getBoolean(0, (boolean) customAttrs[i].defaultValue);
                        break;
                    case TypedValue.TYPE_INT_COLOR_RGB4:
                    case TypedValue.TYPE_INT_COLOR_ARGB4:
                    case TypedValue.TYPE_INT_COLOR_ARGB8:
                    case TypedValue.TYPE_INT_COLOR_RGB8:
                        attr.value = typedArray.getColor(0, (Integer) customAttrs[i].defaultValue);
                        break;
                }
                attr.setValueType(typedValue.type);
                tag.add(attr);
                typedArray.recycle();
            }
            if (tag.size() > 0) {
                view.setTag(tagId, tag);
                if(listeners.size()>0){
                    for (int i = listeners.size() - 1; i >= 0; i--) {
                        listeners.get(i).onViewCreated(view);
                    }
                }
            }
        }

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            return onCreateView(null, name, context, attrs);
        }


        private View createView(String name, Context context, AttributeSet attrs) {
            View view = null;
            if (name.contains(".")) {
                try {
                    view = inflater.createView(name, "", attrs);
                } catch (ClassNotFoundException e) {
                }

            } else {
                for (int i = 0; i < prefixes.length; i++) {
                    try {
                        view = inflater.createView(name, prefixes[i], attrs);
                    } catch (ClassNotFoundException e) {
                    }
                    if (view != null) {
                        break;
                    }
                }
            }
            return view;
        }
    }

    public int getTagId() {
        return tagId;
    }
}
