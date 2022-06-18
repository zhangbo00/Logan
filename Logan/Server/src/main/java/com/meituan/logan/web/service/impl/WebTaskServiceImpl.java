package com.meituan.logan.web.service.impl;

import com.meituan.logan.web.dto.WebLogTaskDTO;
import com.meituan.logan.web.enums.TaskStatusEnum;
import com.meituan.logan.web.mapper.WebLogTaskMapper;
import com.meituan.logan.web.model.WebLogTaskModel;
import com.meituan.logan.web.service.WebTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 功能描述:  <p>web上报处理服务</p>
 *
 * @version 1.0 2019-10-31
 * @since logan-admin-server 1.0
 */
@Slf4j
@Service
public class WebTaskServiceImpl implements WebTaskService {
    @Resource
    private WebLogTaskMapper webLogTaskMapper;

    @Override
    public boolean saveTask(WebLogTaskModel taskModel) {
        try {
            WebLogTaskDTO taskDTO = taskModel.transformToDto();
            WebLogTaskDTO exist = webLogTaskMapper.exist(taskDTO.getLogDate(), taskDTO.getDeviceId(),
                    taskDTO.getPageNum());
            if (exist != null) {
                webLogTaskMapper.updateContent(exist.getTaskId(), taskDTO.getContent());
            } else {
                webLogTaskMapper.insert(taskDTO);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public void deleteByAddTime(long addTime) {
        try {
            webLogTaskMapper.deleteByAddTime(addTime);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public List<WebLogTaskDTO> search(long beginTime, long endTime, String deviceId) {
        try {
            return webLogTaskMapper.query(beginTime, endTime, deviceId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<WebLogTaskDTO> queryByTaskIds(List<Long> taskIds) {
        try {
            if (CollectionUtils.isEmpty(taskIds)) {
                return Collections.emptyList();
            }
            return webLogTaskMapper.queryByIds(taskIds);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public void updateStatus(long taskId, TaskStatusEnum status) {
        try {
            webLogTaskMapper.updateStatus(taskId, status.getStatus());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public List<WebLogTaskDTO> latest(int count) {
        try {
            long maxId = webLogTaskMapper.maxId();
            long minId = Math.max(0, maxId - count);
            return webLogTaskMapper.queryByRange(minId, maxId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}
