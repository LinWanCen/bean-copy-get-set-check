package io.github.linwancen.bean.check;

import io.github.linwancen.bean.check.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * varName -> simpleClassName -> ClassName
 */
public class VarClassInfo {
    protected static final Logger LOG = LoggerFactory.getLogger(VarClassInfo.class);
    protected static final Pattern packagePattern = Pattern.compile("package (.*\\.([\\w*]++));");
    protected static final Pattern importPattern = Pattern.compile("import (.*\\.([\\w*]++));");
    protected static final Pattern importStaticPattern = Pattern.compile("import static (.*\\.([\\w*]++));");
    protected static final Pattern varPattern = Pattern.compile("([A-Z]\\w++) ([a-z]\\w++)\\s*[,;:=)]");
    protected Charset charset = StandardCharsets.UTF_8;

    protected String packagePrefix = "";
    /** classSimpleName, className */
    protected final Map<String, String> simpleClassMap = new LinkedHashMap<>();
    protected final List<String> packageList = new ArrayList<>();
    /** varName, classSimpleName */
    protected final Map<String, Set<String>> varClassMap = new LinkedHashMap<>();

    protected VarClassInfo() {
    }

    public static VarClassInfo build(Path path, Consumer<String> consumer) {
        return new VarClassInfo().init(path, consumer);
    }

    protected VarClassInfo init(Path path, Consumer<String> consumer) {
        try {
            byte[] bytes = Files.readAllBytes(path);
            String code = new String(bytes, charset);

            Matcher pm = getPackagePattern().matcher(code);
            if (pm.find()) {
                packagePrefix = pm.group(1) + '.';
            }

            Matcher im = getImportPattern().matcher(code);
            while (im.find()) {
                String className = im.group(1);
                String classSimpleName = im.group(2);
                if ("*".equals(classSimpleName)) {
                    packageList.add(className);
                } else {
                    simpleClassMap.put(classSimpleName, className);
                }
            }

            Matcher vm = getVarPattern().matcher(code);
            while (vm.find()) {
                String classSimpleName = vm.group(1);
                String varName = vm.group(2);
                varClassMap.computeIfAbsent(varName, s -> new LinkedHashSet<>()).add(classSimpleName);
            }
            if (consumer != null) {
                consumer.accept(code);
            }
        } catch (IOException e) {
            LOG.warn("ClassParse init fail: {}\n{}", e.getLocalizedMessage(), path);
        }
        return this;
    }

    /**
     * Override it
     */
    private Pattern getVarPattern() {
        return varPattern;
    }

    /**
     * Override it
     */
    private Pattern getImportPattern() {
        return importPattern;
    }

    /**
     * Override it
     */
    private Pattern getPackagePattern() {
        return packagePattern;
    }

    public Set<String> toSimple(String varName) {
        Set<String> simpleClassList = varClassMap.get(varName);
        if (simpleClassList != null) {
            return simpleClassList;
        }
        return Collections.singleton(varName);
    }

    public Set<String> toClass(Set<String> simpleClassList) {
        Set<String> importClassList = simpleClassList.stream()
                .map(simpleClassMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        if (!importClassList.isEmpty()) {
            return importClassList;
        }
        LinkedHashSet<String> guessClassList = new LinkedHashSet<>();
        simpleClassList.forEach(s -> {
            if (Constant.getJavaType().contains(s)) {
                guessClassList.add(s);
            } else {
                packageList.forEach(packageName -> guessClassList.add(packageName + '.' + s));
                guessClassList.add(packagePrefix + s);
            }
        });
        return guessClassList;
    }

    public Set<String> toClass(String varName) {
        Set<String> simpleClassList = toSimple(varName);
        return toClass(simpleClassList);
    }
}
