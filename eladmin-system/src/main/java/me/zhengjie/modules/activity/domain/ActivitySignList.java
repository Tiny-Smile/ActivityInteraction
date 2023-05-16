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
* @date 2023-05-16
**/
@Entity
@Data
@Table(name="activity_sign_list")
public class ActivitySignList implements Serializable {

    @Id
    @Column(name = "`id`")
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "`acti_id`")
    @ApiModelProperty(value = "actiId")
    private Long actiId;

    @Column(name = "`true_name`")
    @ApiModelProperty(value = "trueName")
    private String trueName;

    @Column(name = "`nick_name`")
    @ApiModelProperty(value = "nickName")
    private String nickName;

    @Column(name = "`head_image`")
    @ApiModelProperty(value = "headImage")
    private String headImage;

    @Column(name = "`sign_date`")
    @ApiModelProperty(value = "signDate")
    private Timestamp signDate;

    @Column(name = "`sign_url`")
    @ApiModelProperty(value = "signUrl")
    private String signUrl;

    public void copy(ActivitySignList source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
