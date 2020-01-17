package com.diary.main.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
@TableName("sys_article")
public class ArticleModel extends Model<ArticleModel> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "自增长ID")
    private Integer id;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    @NotBlank(message = "标题不能为空。")
    private String title;
    /**
     * 链接地址
     */
    @ApiModelProperty(value = "链接地址不填默认为ID")
    private String url;
    /**
     * markdown 文本内容
     */
//    @ApiModelProperty(value = "markdown 文本的内容")
//    private String content;
    /**
     * 富文本内容
     */
//    @ApiModelProperty(value = "markdown 转化的html区域 或者 富文本框内容")
//    private String html;


    /**
     * 用于导航栏图标
     */
    @ApiModelProperty(value = "用于导航栏图标")
    private String icon;

    /**
     * 0 为markdown 内容 1 为 文本类型
     */
    @ApiModelProperty(value = "判断当前编辑模式 0 为markdown 1 为富文本框")
    private Integer edit;
    /**
     * 页面中的创建时间
     */
    @ApiModelProperty(value = "用户定义创建时间")
//    @JsonFormat(timezone = "GTM+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateb;
    /**
     * 0 为原创 1 则相反
     */
    @ApiModelProperty(value = "是否原创 0 为原创 1 则相反")
    private String original;
    /**
     * 0 不开启评论 1 则相反
     */
    @ApiModelProperty(value = "是否开启评论 0 为开启 1 则相反")
    private String comments;
    /**
     * 0 置顶 1 则相反
     */
    @ApiModelProperty(value = "是否置顶 0置顶 1 相反")
    private String top;

    @ApiModelProperty(value = "阅读量")
    private  Integer reading;

    @ApiModelProperty(value = "文章状态：0 为草稿 1 为 已发布")
    private  Integer status;

    @ApiModelProperty(value = "0 文章 1 页面")
    @Value("#{0}")
    private  Integer page;



    @TableField(exist = false)
    @ApiModelProperty(value = "标签集合")
    private  List<Tag> dynamicTags;
    /**
     * 创建时间
     */
    @TableField("create_time")
    @ApiModelProperty(value = "系统创建时间不可修改，不用赋值。")
    @JsonIgnore
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    @JsonIgnore
    @ApiModelProperty(value = "系统修改时间 不可修改，不用赋值，数据库会自动更新时间")
    private Date updateTime;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
