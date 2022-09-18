package io.github.linwancen.bean.check.copy;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import io.github.linwancen.bean.check.GetSetInfo;
import io.github.linwancen.bean.check.bean.GetSetMethodInfo;
import io.github.linwancen.bean.check.copy.line.BeanCopyCheckLine;
import io.github.linwancen.bean.check.copy.line.BeanCopyCheckOtherLine;
import io.github.linwancen.bean.check.util.JFileUtils;
import io.github.linwancen.bean.check.util.excel.ExcelUtils;

import java.io.File;
import java.nio.file.Path;

public class BeanCopyCheckToExcel extends BeanCopyCheck {
    ExcelWriter excelWriter;
    WriteSheet copySheet;
    WriteSheet otherSheet;

    public static void main(String... args) {
        if (args.length > 0) {
            new BeanCopyCheckToExcel().scan(new File(String.join(" ", args)).toPath());
            return;
        }
        File[] files = JFileUtils.chooser();
        if (files.length > 0) {
            new BeanCopyCheckToExcel().scan(files[0].toPath());
        }
    }

    public void scan(Path rootPath) {
        try (ExcelWriter w = ExcelUtils.excel("BeanCopyCheck")) {
            // Override method use it
            excelWriter = w;
            copySheet = ExcelUtils.sheet(excelWriter, 1, "copy", BeanCopyCheckLine.class);
            otherSheet = ExcelUtils.sheet(excelWriter, 2, "other", BeanCopyCheckOtherLine.class);
            run(rootPath);
            ExcelUtils.save(excelWriter);
        }
    }

    @Override
    public void less(String copyClass, GetSetInfo fromInfo, GetSetMethodInfo toMethod) {
        BeanCopyCheckLine line = forNotFound(copyClass, fromInfo.getClassName(), toMethod.className);
        line.setToMethod(toMethod.methodName);
        ExcelUtils.write(excelWriter, copySheet, line);
    }

    @Override
    public void more(String copyClass, GetSetMethodInfo fromMethod, GetSetInfo toInfo) {
        BeanCopyCheckLine line = forNotFound(copyClass, fromMethod.className, toInfo.getClassName());
        line.setFromMethod(fromMethod.methodName);
        ExcelUtils.write(excelWriter, copySheet, line);
    }

    private BeanCopyCheckLine forNotFound(String copyClass, String fromClass, String forClass) {
        BeanCopyCheckLine line = new BeanCopyCheckLine();
        line.setFromClass(fromClass);
        line.setCopyClass(copyClass);
        line.setToClass(forClass);
        return line;
    }

    @Override
    public void other(String className, String methodName, String varName) {
        BeanCopyCheckOtherLine line = new BeanCopyCheckOtherLine();
        line.setClassName(className);
        line.setMethod(methodName);
        line.setVarName(varName);
        ExcelUtils.write(excelWriter, otherSheet, line);
    }
}
