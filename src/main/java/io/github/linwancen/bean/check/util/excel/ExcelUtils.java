package io.github.linwancen.bean.check.util.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

public class ExcelUtils {
    private ExcelUtils() {}

    private static final Logger LOG = LoggerFactory.getLogger(ExcelUtils.class);

    public static ExcelWriter excel(String fileName) {
        SimpleDateFormat sdf = new SimpleDateFormat("-yyyy.MM.dd-HH.mm.ss");
        String time = sdf.format(new Date());
        File file = new File(fileName + time + ".xlsx");
        if (LOG.isInfoEnabled()) {
            LOG.info("file:///{}", file.getAbsolutePath().replace('\\', '/'));
        }
        return EasyExcelFactory.write(file).build();
    }

    public static WriteSheet sheet(ExcelWriter writer, int sheetNo, String sheetName, Class<?> lineClass) {
        WriteSheet sheet = EasyExcelFactory
                .writerSheet(sheetNo, sheetName)
                .head(lineClass)
                .registerWriteHandler(new FreezeAndFilter())
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .build();
        writer.write(Collections.emptyList(), sheet);
        return sheet;
    }

    public static <T> void write(ExcelWriter writer, WriteSheet sheet, T line) {
        writer.write(Collections.singletonList(line), sheet);
    }
}
