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

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.activity.domain.ActivitySignList;
import me.zhengjie.modules.activity.service.ActivitySignListService;
import me.zhengjie.modules.activity.service.dto.ActivitySignListQueryCriteria;
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
* @author renrui
* @date 2023-05-16
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "签到列表管理")
@RequestMapping("/api/activitySignList")
public class ActivitySignListController {

    private final ActivitySignListService activitySignListService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('activitySignList:list')")
    public void exportActivitySignList(HttpServletResponse response, ActivitySignListQueryCriteria criteria) throws IOException {
        activitySignListService.download(activitySignListService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询签到列表")
    @ApiOperation("查询签到列表")
    @PreAuthorize("@el.check('activitySignList:list')")
    public ResponseEntity<Object> queryActivitySignList(ActivitySignListQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(activitySignListService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增签到列表")
    @ApiOperation("新增签到列表")
    @PreAuthorize("@el.check('activitySignList:add')")
    public ResponseEntity<Object> createActivitySignList(@Validated @RequestBody ActivitySignList resources){
        return new ResponseEntity<>(activitySignListService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改签到列表")
    @ApiOperation("修改签到列表")
    @PreAuthorize("@el.check('activitySignList:edit')")
    public ResponseEntity<Object> updateActivitySignList(@Validated @RequestBody ActivitySignList resources){
        activitySignListService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除签到列表")
    @ApiOperation("删除签到列表")
    @PreAuthorize("@el.check('activitySignList:del')")
    public ResponseEntity<Object> deleteActivitySignList(@RequestBody Long[] ids) {
        activitySignListService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}