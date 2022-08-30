package io.github.linwancen.bean.check;

import io.github.linwancen.bean.check.bean.GetSetMethodInfo;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * varName -> Type
 * <br>get/setMethodName -> otherVarName
 */
public class GetSetInfo extends VarClassInfo {
    protected static final Pattern getSetPattern = Pattern.compile(
            "(\\w++)\\s++((is|get|set)(\\w++))\\((\\w++)?[^\\n]++\\s++(?:(?:return\\s+|this\\.)(\\w+))?");
    /** varName, method */
    protected final Map<String, GetSetMethodInfo> getter = new LinkedHashMap<>();
    /** varName, method */
    protected final Map<String, GetSetMethodInfo> setter = new LinkedHashMap<>();
    /** methodName, relVarName */
    protected final Map<String, String> getOther = new LinkedHashMap<>();
    /** methodName, relVarName */
    protected final Map<String, String> setOther = new LinkedHashMap<>();

    protected final String className;

    protected GetSetInfo(String className) {
        this.className = className;
    }

    public static GetSetInfo build(Path path, String fileClassName) {
        return new GetSetInfo(fileClassName).init(path);
    }

    protected GetSetInfo init(Path path) {
        super.init(path, code -> {
            Matcher m = getGetSetPattern().matcher(code);
            while (m.find()) {
                String retType = m.group(1);
                String methodName = m.group(2);
                String type = m.group(3);
                String varName = caseFirst(m.group(4), false);
                String paramType = m.group(5);
                String relVarName = m.group(6);
                boolean set = type.equals("set");
                putGetSet(set, methodName, varName, retType, paramType);
                putOther(set, methodName, varName, relVarName);
            }
        });
        getter.forEach((k, v) -> v.setVarClasses(toClass(v.varSimpleClass)));
        setter.forEach((k, v) -> v.setVarClasses(toClass(v.varSimpleClass)));
        return this;
    }

    private void putGetSet(boolean set, String methodName, String varName, String retType, String paramType) {
        GetSetMethodInfo methodInfo = new GetSetMethodInfo(className, (set ? paramType : retType), methodName);
        (set ? setter : getter).put(varName, methodInfo);
    }

    private void putOther(boolean set, String methodName, String varName, String relVarName) {
        if (relVarName != null && !varName.equals(relVarName)) {
            (set ? setOther : getOther).put(methodName, relVarName);
        }
    }

    /**
     * Override it
     */
    protected Pattern getGetSetPattern() {
        return getSetPattern;
    }

    public static String caseFirst(String str, boolean upper) {
        char[] chars = str.toCharArray();
        chars[0] = (char) (chars[0] + (upper ? -32 : 32));
        return new String(chars);
    }

    public String getClassName() {
        return className;
    }

    public Map<String, GetSetMethodInfo> getGetter() {
        return getter;
    }

    public Map<String, GetSetMethodInfo> getSetter() {
        return setter;
    }

    public Map<String, String> getGetOther() {
        return getOther;
    }

    public Map<String, String> getSetOther() {
        return setOther;
    }
}
