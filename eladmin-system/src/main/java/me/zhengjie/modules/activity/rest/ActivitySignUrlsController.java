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

import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.activity.domain.ActivitySignUrls;
import me.zhengjie.modules.activity.service.ActivitySignUrlsService;
import me.zhengjie.modules.activity.service.dto.ActivitySignUrlsQueryCriteria;
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
* @date 2023-05-16
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "签到二维码管理")
@RequestMapping("/api/activitySignUrls")
public class ActivitySignUrlsController {

    private final ActivitySignUrlsService activitySignUrlsService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('activitySignUrls:list')")
    public void exportActivitySignUrls(HttpServletResponse response, ActivitySignUrlsQueryCriteria criteria) throws IOException {
        activitySignUrlsService.download(activitySignUrlsService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询签到二维码")
    @ApiOperation("查询签到二维码")
    @PreAuthorize("@el.check('activitySignUrls:list')")
    public ResponseEntity<Object> queryActivitySignUrls(ActivitySignUrlsQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(activitySignUrlsService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增签到二维码")
    @ApiOperation("新增签到二维码")
    @PreAuthorize("@el.check('activitySignUrls:add')")
    public ResponseEntity<Object> createActivitySignUrls(@Validated @RequestBody ActivitySignUrls resources){
        return new ResponseEntity<>(activitySignUrlsService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改签到二维码")
    @ApiOperation("修改签到二维码")
    @PreAuthorize("@el.check('activitySignUrls:edit')")
    public ResponseEntity<Object> updateActivitySignUrls(@Validated @RequestBody ActivitySignUrls resources){
        activitySignUrlsService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除签到二维码")
    @ApiOperation("删除签到二维码")
    @PreAuthorize("@el.check('activitySignUrls:del')")
    public ResponseEntity<Object> deleteActivitySignUrls(@RequestBody Long[] ids) {
        activitySignUrlsService.deleteAll(ids);
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
        activitySignUrls.setActiId(Long.parseLong(acti_id));
        activitySignUrlsService.createOrUpdate(activitySignUrls);
        return new ResponseEntity<>(url, HttpStatus.OK);
    }
}