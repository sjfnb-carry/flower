package com.mall.common.query;

import com.mall.sys.entity.MallSysRole;
import lombok.Data;
import java.util.List;


@Data
public class CommonSave {
    List<String> list;
    MallSysRole roleId;
}
