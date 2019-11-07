package com.diary.main.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

/**
 * <p>
 * 
 * </p>
 *
 * @author hao
 * @since 2019-09-14
 */
@Data
@Accessors(chain = true)
@TableName("sys_tag")
public class Tag extends Model<Tag> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 标签名称
     */
    private String name;
    /**
     * 文章ID
     */
    private Integer arid;
    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonIgnore
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    @JsonIgnore
    private Date updateTime;

    public Tag(String name, Integer arid) {
        this.name = name;
        this.arid = arid;
    }

    public Tag() {
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
