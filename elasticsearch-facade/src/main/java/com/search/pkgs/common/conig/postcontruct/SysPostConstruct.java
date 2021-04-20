package com.search.pkgs.common.conig.postcontruct;

import com.search.pkgs.datacanal.listener.MysqlListener;
import com.search.pkgs.eventlistener.SysEventListener;
import com.search.pkgs.events.SysEventPublisher;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

/**
 * @author huapeng.huang
 * @version V1.0
 * @since 2021-04-20 13:51
 */
@Component
public class SysPostConstruct {

    @Resource
    private MysqlListener mysqlListener;

    @Resource
    private SysEventListener sysEventListener;

    @PostConstruct
    public void startupCanal() {
        mysqlListener.exec();
    }

    @PostConstruct
    public void register() {
        SysEventPublisher.register(sysEventListener);
    }
}
