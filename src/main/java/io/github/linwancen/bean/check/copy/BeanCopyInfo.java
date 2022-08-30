package io.github.linwancen.bean.check.copy;

import io.github.linwancen.bean.check.ClassFiles;
import io.github.linwancen.bean.check.VarClassInfo;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * fromClassName -> toClassName
 */
public class BeanCopyInfo {
    protected static Pattern copyPattern = Pattern.compile("copyProperties\\((\\w++),\\s*(\\w++)");
    protected final Map<String, Map<String, Set<String>>> classMap = new LinkedHashMap<>();

    protected BeanCopyInfo() {}

    public static BeanCopyInfo build(ClassFiles classFiles, BiPredicate<String, Path> fileFilter) {
        return new BeanCopyInfo().init(classFiles, fileFilter);
    }

    protected BeanCopyInfo init(ClassFiles classFiles, BiPredicate<String, Path> fileFilter) {
        Map<String, Set<String>> copyMap = new LinkedHashMap<>();
        classFiles.getClassFileMap().forEach((className, path) -> {
            if (fileFilter != null && !fileFilter.test(className, path)) {
                return;
            }
            VarClassInfo varClassInfo = VarClassInfo.build(path, code -> {
                Matcher m = copyPattern.matcher(code);
                while (m.find()) {
                    String from = m.group(1);
                    String to = m.group(2);
                    copyMap.computeIfAbsent(from, s -> new LinkedHashSet<>()).add(to);
                }
            });

            copyMap.forEach((from, toList) -> {
                Set<String> formClassSet = varClassInfo.toClass(from);
                toList.forEach(to -> {
                    Set<String> toClassSet = varClassInfo.toClass(to);
                    formClassSet.forEach(formClass ->
                            toClassSet.forEach(toClass ->
                                    classMap.computeIfAbsent(className, s -> new LinkedHashMap<>())
                                            .computeIfAbsent(formClass, s -> new LinkedHashSet<>()).add(toClass)
                            ));
                });
            });
        });
        return this;
    }
}
