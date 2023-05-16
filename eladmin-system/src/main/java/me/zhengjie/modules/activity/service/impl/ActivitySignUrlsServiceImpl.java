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

import me.zhengjie.modules.activity.domain.ActivitySignUrls;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.activity.repository.ActivitySignUrlsRepository;
import me.zhengjie.modules.activity.service.ActivitySignUrlsService;
import me.zhengjie.modules.activity.service.dto.ActivitySignUrlsDto;
import me.zhengjie.modules.activity.service.dto.ActivitySignUrlsQueryCriteria;
import me.zhengjie.modules.activity.service.mapstruct.ActivitySignUrlsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
* @date 2023-05-16
**/
@Service
@RequiredArgsConstructor
public class ActivitySignUrlsServiceImpl implements ActivitySignUrlsService {

    private final ActivitySignUrlsRepository activitySignUrlsRepository;
    private final ActivitySignUrlsMapper activitySignUrlsMapper;

    @Override
    public Map<String,Object> queryAll(ActivitySignUrlsQueryCriteria criteria, Pageable pageable){
        Page<ActivitySignUrls> page = activitySignUrlsRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(activitySignUrlsMapper::toDto));
    }

    @Override
    public List<ActivitySignUrlsDto> queryAll(ActivitySignUrlsQueryCriteria criteria){
        return activitySignUrlsMapper.toDto(activitySignUrlsRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ActivitySignUrlsDto findById(Long id) {
        ActivitySignUrls activitySignUrls = activitySignUrlsRepository.findById(id).orElseGet(ActivitySignUrls::new);
        ValidationUtil.isNull(activitySignUrls.getId(),"ActivitySignUrls","id",id);
        return activitySignUrlsMapper.toDto(activitySignUrls);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActivitySignUrlsDto create(ActivitySignUrls resources) {
        return activitySignUrlsMapper.toDto(activitySignUrlsRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ActivitySignUrls resources) {
        ActivitySignUrls activitySignUrls = activitySignUrlsRepository.findById(resources.getId()).orElseGet(ActivitySignUrls::new);
        ValidationUtil.isNull( activitySignUrls.getId(),"ActivitySignUrls","id",resources.getId());
        activitySignUrls.copy(resources);
        activitySignUrlsRepository.save(activitySignUrls);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            activitySignUrlsRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ActivitySignUrlsDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ActivitySignUrlsDto activitySignUrls : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" actiId",  activitySignUrls.getActiId());
            map.put(" signUrl",  activitySignUrls.getSignUrl());
            map.put(" createDate",  activitySignUrls.getCreateDate());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}