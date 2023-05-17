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
import java.sql.Timestamp;
import java.io.Serializable;

/**
 * @website https://eladmin.vip
 * @description /
 * @author renrui
 * @date 2023-05-17
 **/
@Entity
@Data
@Table(name="activity_sign_list")
public class ActivitySignList implements Serializable {

    @Column(name = "`acti_id`")
    @ApiModelProperty(value = "活动ID")
    private Long actiId;

    @Column(name = "`true_name`")
    @ApiModelProperty(value = "姓名")
    private String trueName;

    @Column(name = "`nick_name`")
    @ApiModelProperty(value = "昵称")
    private String nickName;

    @Column(name = "`head_image`")
    @ApiModelProperty(value = "头像")
    private String headImage;

    @Column(name = "`sign_date`")
    @ApiModelProperty(value = "签到时间")
    private Timestamp signDate;

    @Column(name = "`sign_url`")
    @ApiModelProperty(value = "签到二维码")
    private String signUrl;

    @Column(name = "`job_id`")
    @ApiModelProperty(value = "工号")
    private String jobId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "id")
    private Long id;

    public void copy(ActivitySignList source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
