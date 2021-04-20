package com.search.pkgs.datacanal.listener;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.search.pkgs.datacanal.common.CanalConstant;
import com.search.pkgs.events.SysEvent;
import com.search.pkgs.events.SysEventPublisher;
import com.search.pkgs.events.payload.DataChangePayload;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-20 09:30
 */
@Slf4j
@Service
public class MysqlListener {

    @Async
    public void exec() {
        log.info("Function[MysqlListener.exec] startup");
        // 每次获取数量
        int batchSize = 1000;
        // 创建链接
        CanalConnector connector = createCanalConnector();
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();
            while (true) {
                // 获取指定数量的数据
                Message message = connector.getWithoutAck(batchSize);
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    sleep(1);
                } else {
                    printEntry(message.getEntries());
                }

                // 提交确认
                connector.ack(batchId);
            }
        } finally {
            connector.disconnect();
        }
    }

    private static CanalConnector createCanalConnector() {
        InetSocketAddress hostAddress = new InetSocketAddress(CanalConstant.CANAL_SERVER_HOST,
            CanalConstant.CANAL_SERVER_PORT);
        return CanalConnectors.newSingleConnector(hostAddress, CanalConstant.CANAL_DESTINATION, "", "");
    }

    private static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (Exception e) {
            // do nothing
        }
    }

    private static void printEntry(List<CanalEntry.Entry> entries) {
        for (CanalEntry.Entry entry : entries) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN
                || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }

            CanalEntry.RowChange rowChange;
            try {
                rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                    e);
            }

            CanalEntry.EventType eventType = rowChange.getEventType();
            System.out.println(String.format("================&gt; binlog[%s:%s] , name[%s,%s] , eventType : %s",
                entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                entry.getHeader().getSchemaName(), entry.getHeader().getTableName(), eventType));

            List<CanalEntry.Column> columns;
            for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                if (eventType == CanalEntry.EventType.DELETE) {
                    columns = rowData.getBeforeColumnsList();
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    columns = rowData.getAfterColumnsList();
                } else {
                    columns = rowData.getAfterColumnsList();
                }

                Map<String, Object> changeInfos = buildChangeInfos(columns);

                DataChangePayload payload = new DataChangePayload();
                payload.setDatabaseName(entry.getHeader().getSchemaName());
                payload.setTableName(entry.getHeader().getTableName());
                payload.setValueMap(changeInfos);

                String businessType = String.valueOf(eventType);
                SysEvent sysEvent = SysEvent
                    .create(String.valueOf(System.currentTimeMillis()), businessType, payload.toString());
                SysEventPublisher.publish(sysEvent);
            }
        }
    }

    private static Map<String, Object> buildChangeInfos(List<CanalEntry.Column> columns) {
        Map<String, Object> map = new HashMap<>(columns.size());
        for (CanalEntry.Column column : columns) {
            map.put(column.getName(), column.getValue());
        }
        return map;
    }

}
