package com.meituan.logan.web.service.impl;

import com.google.common.collect.Lists;
import com.meituan.logan.web.dto.LoganTaskDTO;
import com.meituan.logan.web.mapper.LoganTaskMapper;
import com.meituan.logan.web.model.LoganTaskModel;
import com.meituan.logan.web.model.request.LoganTaskRequest;
import com.meituan.logan.web.service.LoganTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service("loganTaskService")
public class LoganTaskServiceImpl implements LoganTaskService {


    @Resource
    private LoganTaskMapper taskMapper;

    @Override
    public List<LoganTaskModel> search(LoganTaskRequest request) {
        try {
            List<LoganTaskDTO> list = taskMapper.search(request);
            if (CollectionUtils.isNotEmpty(list)) {
                Collections.sort(list);
                return Lists.newArrayList(Lists.transform(list, LoganTaskDTO::transformToModel));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public LoganTaskModel getByTaskId(long taskId) {
        try {
            LoganTaskDTO taskDTO = taskMapper.selectById(taskId);
            if (taskDTO != null) {
                return taskDTO.transformToModel();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public long insertTask(LoganTaskModel taskModel) {
        try {
            LoganTaskDTO dto = taskModel.transformToDto();
            taskMapper.insert(dto);
            return dto.getId();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return 0L;
    }

    @Override
    public List<LoganTaskModel> queryLatest(int limit) {
        try {
            List<LoganTaskDTO> list = taskMapper.queryLatest(limit);
            if (CollectionUtils.isNotEmpty(list)) {
                Collections.sort(list);
                return Lists.newArrayList(Lists.transform(list, LoganTaskDTO::transformToModel));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }
}
