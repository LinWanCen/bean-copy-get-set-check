package io.github.linwancen.bean.check.copy;

import org.apache.commons.beanutils.BeanUtils;

public class CopyDemo {
    public static void copy() {
        FromDO fromDO = new FromDO();
        ToDTO toDTO = new ToDTO();
        try {
            BeanUtils.copyProperties(fromDO, toDTO);

            BeanUtils.copyProperties(fromDO, BeanUtils.class);
            toDTO.setBB(fromDO.isBool());
        } catch (Exception e) {
            // ignore
        }
    }
}
