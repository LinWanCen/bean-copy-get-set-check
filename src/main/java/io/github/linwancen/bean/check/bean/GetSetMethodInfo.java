package io.github.linwancen.bean.check.bean;

import java.util.Set;

public class GetSetMethodInfo {
    public final String className;
    public final String methodName;
    public final String varSimpleClass;
    private Set<String> varClasses;

    public GetSetMethodInfo(String className, String varSimpleClass, String methodName) {
        this.className = className;
        this.varSimpleClass = varSimpleClass;
        this.methodName = methodName;
    }

    public Set<String> getVarClasses() {
        return varClasses;
    }

    public void setVarClasses(Set<String> varClasses) {
        this.varClasses = varClasses;
    }
}
