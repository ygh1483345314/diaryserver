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
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author hao
 * @since 2019-09-17
 */
@Data
@Accessors(chain = true)
@TableName("sys_type_and_article")
public class TypeAndArticle extends Model<TypeAndArticle> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 类型ID
     */
    private Integer tyid;
    /**
     * 文章ID
     */
    private Integer arid;

    /**
     * 类别名称用于映射
     * */
    @TableField(exist = false)
    private String name;
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

    public TypeAndArticle(Integer tyid, Integer arid) {
        this.tyid = tyid;
        this.arid = arid;
    }

    public TypeAndArticle() {
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
