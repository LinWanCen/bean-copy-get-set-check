package io.github.linwancen.bean.check.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileUtils {
    private FileUtils() {}

    private static final Logger LOG = LoggerFactory.getLogger(FileUtils.class);

    public static String read(File file) {
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(file.getAbsoluteFile().toPath());
        } catch (IOException e) {
            LOG.warn("ClassParse init fail: {}\nfile:///{}", e.getLocalizedMessage(), file.getAbsolutePath());
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static void write(File file, String text) {
        try {
            Files.write(file.toPath(), text.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            LOG.warn("write fail: {}\nfile:///{}", e.getLocalizedMessage(), file.getAbsolutePath());
        }
    }
}
