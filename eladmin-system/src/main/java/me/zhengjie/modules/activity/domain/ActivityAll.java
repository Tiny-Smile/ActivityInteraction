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
package me.zhengjie.modules.activity.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @website https://eladmin.vip
 * @description /
 * @author xt
 * @date 2023-05-17
 **/
@Entity
@Data
@Table(name="activity_all")
public class ActivityAll implements Serializable {

    @Id
    @Column(name = "`acti_id`")
    @ApiModelProperty(value = "活动id")
    private Long actiId;

    @Column(name = "`theme`")
    @ApiModelProperty(value = "活动主题")
    private String theme;

    @Column(name = "`dept_id`")
    @ApiModelProperty(value = "主办方")
    private Long deptId;

    @Column(name = "`address`")
    @ApiModelProperty(value = "活动地点")
    private String address;

    @Column(name = "`date_time`")
    @ApiModelProperty(value = "活动时间")
    private String dateTime;

    @Column(name = "`join_people`")
    @ApiModelProperty(value = "参加对象")
    private String joinPeople;

    @Column(name = "`type`")
    @ApiModelProperty(value = "活动类型")
    private String type;

    @Column(name = "`status`")
    @ApiModelProperty(value = "0: 结束，other：进行中")
    private String status;

    public void copy(ActivityAll source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
