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
package me.zhengjie.modules.activity.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

/**
* @website https://eladmin.vip
* @description /
* @author renrui
* @date 2023-05-16
**/
@Data
public class ActivityPrizeDto implements Serializable {

    /** 活动ID */
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long actiId;

    /** 奖项 */
    private String prizeTitle;

    /** 奖品 */
    private String prize;

    /** 中奖人数 */
    private Integer prizeNumber;

    /** 中奖人 */
    private String winnerNames;

    /** 工号 */
    private String winnerJobid;

    /** 数据源 */
    private String source;

    /** 日期 */
    private Timestamp createDate;

    /** 状态 */
    private String status;
}