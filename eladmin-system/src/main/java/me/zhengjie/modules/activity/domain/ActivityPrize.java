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
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author renrui
* @date 2023-05-16
**/
@Entity
@Data
@Table(name="activity_prize")
public class ActivityPrize implements Serializable {

    @Id
    @Column(name = "`acti_id`")
    @ApiModelProperty(value = "活动ID")
    private Long actiId;

    @Column(name = "`prize_title`")
    @ApiModelProperty(value = "奖项")
    private String prizeTitle;

    @Column(name = "`prize`")
    @ApiModelProperty(value = "奖品")
    private String prize;

    @Column(name = "`prize_number`")
    @ApiModelProperty(value = "中奖人数")
    private Integer prizeNumber;

    @Column(name = "`winner_names`")
    @ApiModelProperty(value = "中奖人")
    private String winnerNames;

    @Column(name = "`winner_jobid`")
    @ApiModelProperty(value = "工号")
    private String winnerJobid;

    @Column(name = "`source`")
    @ApiModelProperty(value = "数据源")
    private String source;

    @Column(name = "`create_date`")
    @UpdateTimestamp
    @ApiModelProperty(value = "日期")
    private Timestamp createDate;

    @Column(name = "`status`")
    @ApiModelProperty(value = "状态")
    private String status;

    public void copy(ActivityPrize source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
