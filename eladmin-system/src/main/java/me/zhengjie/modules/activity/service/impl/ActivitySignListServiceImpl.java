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

import com.alibaba.fastjson.JSON;
import me.zhengjie.modules.activity.domain.ActivitySignList;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.activity.repository.ActivitySignListRepository;
import me.zhengjie.modules.activity.service.ActivitySignListService;
import me.zhengjie.modules.activity.service.dto.ActivitySignListDto;
import me.zhengjie.modules.activity.service.dto.ActivitySignListQueryCriteria;
import me.zhengjie.modules.activity.service.mapstruct.ActivitySignListMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

import java.util.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://eladmin.vip
* @description 服务实现
* @author renrui
* @date 2023-05-16
**/
@Service
@RequiredArgsConstructor
public class ActivitySignListServiceImpl implements ActivitySignListService {

    private final ActivitySignListRepository activitySignListRepository;
    private final ActivitySignListMapper activitySignListMapper;

    @Override
    public Map<String,Object> queryAll(ActivitySignListQueryCriteria criteria, Pageable pageable){
        Page<ActivitySignList> page = activitySignListRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(activitySignListMapper::toDto));
    }

    @Override
    public List<ActivitySignListDto> queryAll(ActivitySignListQueryCriteria criteria){
        return activitySignListMapper.toDto(activitySignListRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ActivitySignListDto findById(Long actiId) {
        ActivitySignList activitySignList = activitySignListRepository.findById(actiId).orElseGet(ActivitySignList::new);
        ValidationUtil.isNull(activitySignList.getActiId(),"ActivitySignList","actiId",actiId);
        return activitySignListMapper.toDto(activitySignList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActivitySignListDto create(ActivitySignList resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setActiId(snowflake.nextId()); 
        return activitySignListMapper.toDto(activitySignListRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ActivitySignList resources) {
        ActivitySignList activitySignList = activitySignListRepository.findById(resources.getActiId()).orElseGet(ActivitySignList::new);
        ValidationUtil.isNull( activitySignList.getActiId(),"ActivitySignList","id",resources.getActiId());
        activitySignList.copy(resources);
        activitySignListRepository.save(activitySignList);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long actiId : ids) {
            activitySignListRepository.deleteById(actiId);
        }
    }

    @Override
    public void download(List<ActivitySignListDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ActivitySignListDto activitySignList : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("姓名", activitySignList.getTrueName());
            map.put("昵称", activitySignList.getNickName());
            map.put("头像", activitySignList.getHeadImage());
            map.put("签到时间", activitySignList.getSignDate());
            map.put("签到二维码", activitySignList.getSignUrl());
            map.put("工号", activitySignList.getJobId());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    public ActivitySignList getRandomObject(List<ActivitySignList> objectList) {
        System.out.println("list: "+ JSON.toJSONString(objectList));
        Random random = new Random();
        int randomIndex = random.nextInt(objectList.size());
        return objectList.get(randomIndex);
    }
}