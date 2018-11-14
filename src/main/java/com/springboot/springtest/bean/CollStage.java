package com.springboot.springtest.bean;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 催收-催收阶段表
 * </p>
 *
 * @author D.Yang
 * @since 2018-10-29
 */
@TableName("coll_stage")
@Data
public class CollStage extends Model<CollStage> {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 阶段名称
     */
    @TableField("stage_name")
    private String stageName;
    /**
     * 最小天数
     */
    @TableField("min_days")
    private Integer minDays;
    /**
     * 最大天数
     */
    @TableField("max_days")
    private Integer maxDays;
    /**
     * 创建时间
     */
    @TableField("create_date")
    private Date createDate;
    /**
     * 创建人ID
     */
    @TableField("create_user")
    private String createUser;
    /**
     * 修改人ID
     */
    @TableField("update_user")
    private String updateUser;
    /**
     * 修改时间
     */
    @TableField("update_date")
    private Date updateDate;
    /**
     * 是否地催 0否  1.是
     */
    @TableField("is_visit")
    private Integer isVisit;
    /**
     * 保留天数
     */
    @TableField("keep_days")
    private Integer keepDays;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CollStage{" +
        "id=" + id +
        ", stageName=" + stageName +
        ", minDays=" + minDays +
        ", maxDays=" + maxDays +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateUser=" + updateUser +
        ", updateDate=" + updateDate +
        ", isVisit=" + isVisit +
        ", keepDays=" + keepDays +
        "}";
    }
}
