package com.xxh.cms.common.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * @author xxh
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject,"pubdate",LocalDate.class,LocalDate.now());
        this.strictInsertFill(metaObject,"hits",Integer.class,0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
