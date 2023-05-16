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
package me.zhengjie.modules.activity.rest;

import com.alibaba.fastjson.JSON;
import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.activity.domain.ActivityAll;
import me.zhengjie.modules.activity.domain.ActivitySignUrls;
import me.zhengjie.modules.activity.service.ActivityAllService;
import me.zhengjie.modules.activity.service.ActivitySignUrlsService;
import me.zhengjie.modules.activity.service.dto.ActivityAllQueryCriteria;
import me.zhengjie.modules.activity.service.dto.ActivitySignUrlsDto;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://eladmin.vip
* @author xt
* @date 2023-05-15
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "所有活动管理")
@RequestMapping("/api/activityAll")
public class ActivityAllController {

    private final ActivityAllService activityAllService;
    private final ActivitySignUrlsService activitySignUrlsService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('activityAll:list')")
    public void exportActivityAll(HttpServletResponse response, ActivityAllQueryCriteria criteria) throws IOException {
        activityAllService.download(activityAllService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询所有活动")
    @ApiOperation("查询所有活动")
    @PreAuthorize("@el.check('activityAll:list')")
    public ResponseEntity<Object> queryActivityAll(ActivityAllQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(activityAllService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增所有活动")
    @ApiOperation("新增所有活动")
    @PreAuthorize("@el.check('activityAll:add')")
    public ResponseEntity<Object> createActivityAll(@Validated @RequestBody ActivityAll resources){
        return new ResponseEntity<>(activityAllService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改所有活动")
    @ApiOperation("修改所有活动")
    @PreAuthorize("@el.check('activityAll:edit')")
    public ResponseEntity<Object> updateActivityAll(@Validated @RequestBody ActivityAll resources){
        activityAllService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除所有活动")
    @ApiOperation("删除所有活动")
    @PreAuthorize("@el.check('activityAll:del')")
    public ResponseEntity<Object> deleteActivityAll(@RequestBody Long[] ids) {
        activityAllService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/generate_sign_qrcode")
    @Log("生成活动签到二维码")
    @ApiOperation("生成活动签到二维码")
    @AnonymousAccess
    public ResponseEntity<Object> generateSignQrcode(String acti_id){
        String url = "http://localhost:8013/activity/sign?acti_id="+acti_id;
        ActivitySignUrls activitySignUrls = new ActivitySignUrls();
        activitySignUrls.setSignUrl(url);
        activitySignUrls.setActiId(Long.getLong(acti_id));
        System.out.println(JSON.toJSON(activitySignUrls));
        activitySignUrlsService.create(activitySignUrls);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}