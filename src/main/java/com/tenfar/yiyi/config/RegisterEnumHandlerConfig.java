package com.tenfar.yiyi.config;

import com.tenfar.yiyi.common.enums.BaseEnum;
import com.tenfar.yiyi.handler.GeneralTypeHandler;
import com.tenfar.yiyi.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author tenfar
 */
@Component
@Slf4j
public class RegisterEnumHandlerConfig implements ConfigurationCustomizer {

    @Override
    public void customize(Configuration configuration) {
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();

        try {
            final List<Class<?>> allAssignedClass = ClassUtil.getAllAssignedClass(BaseEnum.class);
            allAssignedClass.forEach((clazz) -> typeHandlerRegistry.register(clazz, GeneralTypeHandler.class));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}