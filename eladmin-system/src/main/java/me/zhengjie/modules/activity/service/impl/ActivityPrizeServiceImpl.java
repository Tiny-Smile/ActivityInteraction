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

import me.zhengjie.modules.activity.domain.ActivityPrize;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.activity.repository.ActivityPrizeRepository;
import me.zhengjie.modules.activity.service.ActivityPrizeService;
import me.zhengjie.modules.activity.service.dto.ActivityPrizeDto;
import me.zhengjie.modules.activity.service.dto.ActivityPrizeQueryCriteria;
import me.zhengjie.modules.activity.service.mapstruct.ActivityPrizeMapper;
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
public class ActivityPrizeServiceImpl implements ActivityPrizeService {

    private final ActivityPrizeRepository activityPrizeRepository;
    private final ActivityPrizeMapper activityPrizeMapper;

    @Override
    public Map<String,Object> queryAll(ActivityPrizeQueryCriteria criteria, Pageable pageable){
        Page<ActivityPrize> page = activityPrizeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(activityPrizeMapper::toDto));
    }

    @Override
    public List<ActivityPrizeDto> queryAll(ActivityPrizeQueryCriteria criteria){
        return activityPrizeMapper.toDto(activityPrizeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ActivityPrizeDto findById(Long actiId) {
        ActivityPrize activityPrize = activityPrizeRepository.findById(actiId).orElseGet(ActivityPrize::new);
        ValidationUtil.isNull(activityPrize.getActiId(),"ActivityPrize","actiId",actiId);
        return activityPrizeMapper.toDto(activityPrize);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActivityPrizeDto create(ActivityPrize resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setActiId(snowflake.nextId()); 
        return activityPrizeMapper.toDto(activityPrizeRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ActivityPrize resources) {
        ActivityPrize activityPrize = activityPrizeRepository.findById(resources.getActiId()).orElseGet(ActivityPrize::new);
        ValidationUtil.isNull( activityPrize.getActiId(),"ActivityPrize","id",resources.getActiId());
        activityPrize.copy(resources);
        activityPrizeRepository.save(activityPrize);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long actiId : ids) {
            activityPrizeRepository.deleteById(actiId);
        }
    }

    @Override
    public void download(List<ActivityPrizeDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ActivityPrizeDto activityPrize : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("奖项", activityPrize.getPrizeTitle());
            map.put("奖品", activityPrize.getPrize());
            map.put("中奖人数", activityPrize.getPrizeNumber());
            map.put("中奖人", activityPrize.getWinnerNames());
            map.put("工号", activityPrize.getWinnerJobid());
            map.put("数据源", activityPrize.getSource());
            map.put("日期", activityPrize.getCreateDate());
            map.put("状态", activityPrize.getStatus());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

}