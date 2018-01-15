package com.neil.customattrhelper;

import android.util.TypedValue;

/**
 * @author nzbao
 * @CreateTime 2018/1/11
 * @Desc
 */
public class CustomAttr {
    public int attrId;
    public Object value;
    public Object defaultValue;
    private int valueType;

    public CustomAttr(int attrId, Object defaultValue) {
        this.attrId = attrId;
        this.defaultValue = defaultValue;
    }

    public int getIntValue() {
        if (valueType == TypedValue.TYPE_INT_DEC
                || valueType == TypedValue.TYPE_INT_COLOR_RGB4
                || valueType == TypedValue.TYPE_INT_COLOR_ARGB4
                || valueType == TypedValue.TYPE_INT_COLOR_ARGB8
                || valueType == TypedValue.TYPE_INT_COLOR_RGB8
                )
            return (int) value;
        return 0;
    }

    public float getFloatValue() {
        if (valueType == TypedValue.TYPE_FLOAT)
            return (float) value;
        return 0f;
    }


    public String getStringValue() {
        if (valueType == TypedValue.TYPE_STRING)
            return (String) value;
        return "";
    }

    public boolean getBooleanValue() {
        if (valueType == TypedValue.TYPE_INT_BOOLEAN)
            return (boolean) value;
        return false;
    }


    public int getDefaultIntValue() {
        if (valueType == TypedValue.TYPE_INT_DEC)
            return (int) defaultValue;
        return 0;
    }

    public float getDefaultFloatValue() {
        if (valueType == TypedValue.TYPE_FLOAT)
            return (float) defaultValue;
        return 0f;
    }


    public String getDefaultStringValue() {
        if (valueType == TypedValue.TYPE_STRING)
            return (String) defaultValue;
        return "";
    }

    public boolean getDefaultBooleanValue() {
        if (valueType == TypedValue.TYPE_INT_BOOLEAN)
            return (boolean) defaultValue;
        return false;
    }

    public int getValueType() {
        return valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
    }

    public <T> T getValue() {
        return (T) value;
    }

    public <T> T getDefaultVale() {
        return (T) defaultValue;
    }

    public boolean isDefaultValue() {
        return defaultValue.equals(value);
    }


    @Override
    public String toString() {
        return "CustomAttr{" +
                "attrId=" + attrId +
                ", value=" + value +
                ", defaultValue=" + defaultValue +
                ", valueType=" + valueType +
                '}';
    }
}
