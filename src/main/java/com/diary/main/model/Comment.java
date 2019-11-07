package com.diary.main.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 
 * </p>
 *
 * @author hao
 * @since 2019-10-05
 */
@Data
@Accessors(chain = true)
@TableName("sys_comment")
public class Comment extends Model<Comment> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 文章ID
     */
    @NotNull(message="文章ID不能为空！")
    private Integer arid;
    /**
     * 楼中楼指定父节点
     */
    private Integer parentId;
    /**
     * 提交人
     */
    @NotBlank(message="提交人不能为空")
    private String name;
    /**
     * 邮件地址
     */
    @Email(message = "邮件格式错误")
    private String email;
    /**
     * 评论内容
     */
    @NotBlank(message="评论不能为空")
    private String content;


    private Integer isAuthor;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
