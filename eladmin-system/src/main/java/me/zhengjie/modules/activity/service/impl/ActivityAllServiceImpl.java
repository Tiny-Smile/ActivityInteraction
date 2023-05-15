/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.modules.activity.service.impl;

import me.zhengjie.modules.activity.domain.ActivityAll;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.activity.repository.ActivityAllRepository;
import me.zhengjie.modules.activity.service.ActivityAllService;
import me.zhengjie.modules.activity.service.dto.ActivityAllDto;
import me.zhengjie.modules.activity.service.dto.ActivityAllQueryCriteria;
import me.zhengjie.modules.activity.service.mapstruct.ActivityAllMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://eladmin.vip
* @description 服务实现
* @author xt
* @date 2023-05-15
**/
@Service
@RequiredArgsConstructor
public class ActivityAllServiceImpl implements ActivityAllService {

    private final ActivityAllRepository activityAllRepository;
    private final ActivityAllMapper activityAllMapper;

    @Override
    public Map<String,Object> queryAll(ActivityAllQueryCriteria criteria, Pageable pageable){
        Page<ActivityAll> page = activityAllRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(activityAllMapper::toDto));
    }

    @Override
    public List<ActivityAllDto> queryAll(ActivityAllQueryCriteria criteria){
        return activityAllMapper.toDto(activityAllRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ActivityAllDto findById(Long actiId) {
        ActivityAll activityAll = activityAllRepository.findById(actiId).orElseGet(ActivityAll::new);
        ValidationUtil.isNull(activityAll.getActiId(),"ActivityAll","actiId",actiId);
        return activityAllMapper.toDto(activityAll);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActivityAllDto create(ActivityAll resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setActiId(snowflake.nextId()); 
        return activityAllMapper.toDto(activityAllRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ActivityAll resources) {
        ActivityAll activityAll = activityAllRepository.findById(resources.getActiId()).orElseGet(ActivityAll::new);
        ValidationUtil.isNull( activityAll.getActiId(),"ActivityAll","id",resources.getActiId());
        activityAll.copy(resources);
        activityAllRepository.save(activityAll);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long actiId : ids) {
            activityAllRepository.deleteById(actiId);
        }
    }

    @Override
    public void download(List<ActivityAllDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ActivityAllDto activityAll : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("活动主题", activityAll.getTheme());
            map.put("主办方", activityAll.getDeptId());
            map.put("活动地点", activityAll.getAddress());
            map.put("活动时间", activityAll.getDateTime());
            map.put("参加对象", activityAll.getJoinPeople());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}