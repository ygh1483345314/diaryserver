package com.diary.main.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author hao
 * @since 2019-10-28
 */
@Data
@Accessors(chain = true)
@TableName("sys_image_file")
public class ImageFile extends Model<ImageFile> {

    private static final long serialVersionUID = 1L;

    public ImageFile(String keyId, String path, String url, String name) {
        this.keyId = keyId;
        this.path = path;
        this.url = url;
        this.name = name;
    }

    public ImageFile(String keyId, String path, String name) {
        this.keyId = keyId;
        this.path = path;
        this.name = name;
    }

    public ImageFile() {
    }

    public ImageFile(String keyId, String path) {
        this.keyId = keyId;
        this.path = path;
    }

    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 随机key
     */
    @TableField("key_id")
    private String keyId;
    /**
     * 七牛云域名路径
     */
    private String path;

    private String url;

    private String name;

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
