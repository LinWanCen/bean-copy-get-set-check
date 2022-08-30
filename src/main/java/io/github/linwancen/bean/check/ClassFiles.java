package io.github.linwancen.bean.check;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * classSimpleName -> className -> Path
 */
public class ClassFiles {
    protected static final Logger LOG = LoggerFactory.getLogger(ClassFiles.class);

    protected static final Pattern srcClassPattern = Pattern.compile("/src/(?:(?:main|test)/java/)*+(.+)\\.java");
    /** className, path */
    protected final Map<String, Path> classFileMap = new LinkedHashMap<>();
    /** classSimpleName, className */
    protected final Map<String, Set<String>> simpleClassMap = new LinkedHashMap<>();

    protected ClassFiles() {}

    public static ClassFiles build(Path rootPath) {
        return new ClassFiles().init(rootPath);
    }

    protected ClassFiles init(Path rootPath) {
        try (Stream<Path> walk = Files.walk(rootPath)) {
            walk.forEach(path -> {
                try {
                    this.addClass(path);
                } catch (Exception e) {
                    LOG.warn("addClass fail: {}\n{}", e.getLocalizedMessage(), path);
                }
            });
        } catch (IOException e) {
            LOG.warn("ClassFiles init fail: {}\n{}", e.getLocalizedMessage(), rootPath);
        }
        return this;
    }

    protected void addClass(Path path) {
        String filePath = path.toUri().getPath();
        if (!filePath.endsWith(".java")) {
            return;
        }
        // package-info.java and module-info.java
        if (filePath.endsWith("-info.java")) {
            return;
        }
        Matcher m = getSrcClassPattern().matcher(filePath);
        if (!m.find()) {
            return;
        }
        String classPath = m.group(1);
        String className = classPath.replace('/', '.');
        int i = className.lastIndexOf('.');
        String classSimpleName = className.substring(i + 1);
        classFileMap.put(className, path);
        simpleClassMap.computeIfAbsent(classSimpleName, s -> new LinkedHashSet<>()).add(className);
    }

    /**
     * Override it
     */
    protected Pattern getSrcClassPattern() {
        return srcClassPattern;
    }

    public Map<String, Path> getClassFileMap() {
        return classFileMap;
    }

    public Map<String, Set<String>> getSimpleClassMap() {
        return simpleClassMap;
    }
}
