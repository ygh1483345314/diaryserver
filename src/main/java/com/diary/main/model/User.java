package com.diary.main.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 
 * </p>
 *
 * @author hao
 * @since 2019-09-13
 */
@Data
@Accessors(chain = true)
@TableName("sys_user")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * userID自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id;
    /**
     * 用户名
     */
    @NotBlank(message="账号不能为空！")
    @ApiModelProperty(value = "账号")
    private String username;
    /**
     * 密码
     */
    @NotBlank(message="密码不能为空！")
    @ApiModelProperty(value = "密码")
    private String password;
    /**
     * 创建时间
     */
    @TableField("create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @TableField(exist = false)
    private  String token;

    public User(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
