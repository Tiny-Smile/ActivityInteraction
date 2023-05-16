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
import me.zhengjie.modules.activity.domain.ActivityPrize;
import me.zhengjie.modules.activity.service.ActivityPrizeService;
import me.zhengjie.modules.activity.service.dto.ActivityPrizeQueryCriteria;
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
@Api(tags = "抽奖管理管理")
@RequestMapping("/api/activityPrize")
public class ActivityPrizeController {

    private final ActivityPrizeService activityPrizeService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('activityPrize:list')")
    public void exportActivityPrize(HttpServletResponse response, ActivityPrizeQueryCriteria criteria) throws IOException {
        activityPrizeService.download(activityPrizeService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询抽奖管理")
    @ApiOperation("查询抽奖管理")
    @PreAuthorize("@el.check('activityPrize:list')")
    public ResponseEntity<Object> queryActivityPrize(ActivityPrizeQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(activityPrizeService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增抽奖管理")
    @ApiOperation("新增抽奖管理")
    @PreAuthorize("@el.check('activityPrize:add')")
    public ResponseEntity<Object> createActivityPrize(@Validated @RequestBody ActivityPrize resources){
        return new ResponseEntity<>(activityPrizeService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改抽奖管理")
    @ApiOperation("修改抽奖管理")
    @PreAuthorize("@el.check('activityPrize:edit')")
    public ResponseEntity<Object> updateActivityPrize(@Validated @RequestBody ActivityPrize resources){
        activityPrizeService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除抽奖管理")
    @ApiOperation("删除抽奖管理")
    @PreAuthorize("@el.check('activityPrize:del')")
    public ResponseEntity<Object> deleteActivityPrize(@RequestBody Long[] ids) {
        activityPrizeService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}