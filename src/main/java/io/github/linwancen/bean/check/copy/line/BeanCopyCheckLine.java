package io.github.linwancen.bean.check.copy.line;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;

@SuppressWarnings("unused")
@HeadStyle(wrapped = BooleanEnum.FALSE, horizontalAlignment = HorizontalAlignmentEnum.LEFT)
@HeadFontStyle(fontHeightInPoints = 11, bold = BooleanEnum.FALSE, fontName = "宋体")
public class BeanCopyCheckLine {
    @ExcelProperty("copyClass")
    private String copyClass;
    @ExcelProperty("fromClass")
    private String fromClass;
    @ExcelProperty("toClass")
    private String toClass;
    @ExcelProperty("fromMethod info")
    private String fromMethod;
    @ExcelProperty("toMethod warn")
    private String toMethod;

    public String getCopyClass() {
        return copyClass;
    }

    public void setCopyClass(String copyClass) {
        this.copyClass = copyClass;
    }

    public String getFromClass() {
        return fromClass;
    }

    public void setFromClass(String fromClass) {
        this.fromClass = fromClass;
    }

    public String getToClass() {
        return toClass;
    }

    public void setToClass(String toClass) {
        this.toClass = toClass;
    }

    public String getFromMethod() {
        return fromMethod;
    }

    public void setFromMethod(String fromMethod) {
        this.fromMethod = fromMethod;
    }

    public String getToMethod() {
        return toMethod;
    }

    public void setToMethod(String toMethod) {
        this.toMethod = toMethod;
    }
}
