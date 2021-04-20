package com.search.pkgs.events;

import com.alibaba.fastjson.JSON;

import lombok.Data;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-20 09:57
 */
@Data
public class SysEvent {

    private String businessId;

    private String businessType;

    private String payload;

    public static SysEvent create(String businessId, String businessType, String payload) {
        SysEvent sysEvent = new SysEvent();
        sysEvent.setBusinessId(businessId);
        sysEvent.setBusinessType(businessType);
        sysEvent.setPayload(payload);

        return sysEvent;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
