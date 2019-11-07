package com.diary.main.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
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
@TableName("sys_type")
public class Type extends Model<Type> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    @ApiModelProperty(value = "主键Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 类别名称
     */
    @ApiModelProperty(value = "类别名称")
    @NotBlank(message = "类别名称不能为空.")
    private String name;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    @JsonIgnore
    private Date createTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    @JsonIgnore
    private Date updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
