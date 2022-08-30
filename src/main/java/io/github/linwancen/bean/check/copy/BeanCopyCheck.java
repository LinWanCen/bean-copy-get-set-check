package io.github.linwancen.bean.check.copy;

import io.github.linwancen.bean.check.ClassFiles;
import io.github.linwancen.bean.check.GetSetInfo;
import io.github.linwancen.bean.check.bean.GetSetMethodInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * not status base
 */
public abstract class BeanCopyCheck {
    private static final Logger LOG = LoggerFactory.getLogger(BeanCopyCheck.class);

    /**
     * warn
     */
    public abstract void less(String copyClass, GetSetInfo fromInfo, GetSetMethodInfo toMethod);

    /**
     * info
     */
    public abstract void more(String copyClass, GetSetMethodInfo fromMethod, GetSetInfo toInfo);

    /**
     * warn
     */
    public abstract void other(String className, String methodName, String varName);

    protected final void run(Path rootPath) {
        long time1 = System.currentTimeMillis();
        ClassFiles classFiles = ClassFiles.build(rootPath);
        long time2 = System.currentTimeMillis();
        LOG.info("load className use: {}ms, {} class, {} simpleClass.",
                time2 - time1, classFiles.getClassFileMap().size(), classFiles.getSimpleClassMap().size());
        BeanCopyInfo beanCopyInfo = BeanCopyInfo.build(classFiles, null);
        long time3 = System.currentTimeMillis();
        LOG.info("load beanCopyClass use: {}ms, {} from.", time3 - time2, beanCopyInfo.classMap.size());
        Map<String, GetSetInfo> cache = new ConcurrentHashMap<>();
        Set<String> otherClassNameSet = new HashSet<>();
        for (Map.Entry<String, Map<String, Set<String>>> classNameFromTo : beanCopyInfo.classMap.entrySet()) {
            String className = classNameFromTo.getKey();
            for (Map.Entry<String, Set<String>> fromTo : classNameFromTo.getValue().entrySet()) {
                String fromClass = fromTo.getKey();
                GetSetInfo fromInfo = toInfo(classFiles, cache, fromClass);
                if (fromInfo == null) {
                    continue;
                }
                Set<String> toClassSet = fromTo.getValue();
                for (String toClass : toClassSet) {
                    GetSetInfo toInfo = toInfo(classFiles, cache, toClass);
                    if (toInfo == null) {
                        continue;
                    }
                    check(className, fromInfo, toInfo, otherClassNameSet);
                }
            }
        }
        long time4 = System.currentTimeMillis();
        LOG.info("scan use: {}ms.", time4 - time3);
    }

    private static GetSetInfo toInfo(ClassFiles classFiles, Map<String, GetSetInfo> cache, String className) {
        return cache.computeIfAbsent(className, s -> {
            Path fromPath = classFiles.getClassFileMap().get(className);
            if (fromPath == null) {
                return null;
            }
            return GetSetInfo.build(fromPath, className);
        });
    }

    private void check(String className, GetSetInfo fromInfo, GetSetInfo toInfo, Set<String> otherClassNameSet) {
        toInfo.getSetter().forEach((varName, toMethod) -> {
            GetSetMethodInfo fromMethod = fromInfo.getGetter().get(varName);
            if (fromMethod == null) {
                less(className, fromInfo, toMethod);
            }
        });
        fromInfo.getGetter().forEach((varName, fromMethod) -> {
            GetSetMethodInfo toMethod = toInfo.getSetter().get(varName);
            if (toMethod == null) {
                more(className, fromMethod, toInfo);
            }
        });
        otherInfo(fromInfo, otherClassNameSet);
        otherInfo(toInfo, otherClassNameSet);
    }

    private void otherInfo(GetSetInfo info, Set<String> otherClassNameSet) {
        if (otherClassNameSet.add(info.getClassName())) {
            info.getGetOther().forEach((methodName, varName) -> other(info.getClassName(), methodName, varName));
            info.getSetOther().forEach((methodName, varName) -> other(info.getClassName(), methodName, varName));
        }
    }
}
