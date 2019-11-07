package com.diary.main.vo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CommentVo implements Serializable{
    private static final long serialVersionUID = 8329254445589534671L;
    /**
     * 自增ID
     */
    private Integer id;
    /**
     * 文章ID
     */
    private Integer arid;
    /**
     * 楼中楼指定父节点
     */
    private Integer parentId;
    /**
     * 提交人
     */
    private String name;
    /**
     * 邮件地址
     */
    private String email;
    /**
     * 评论内容
     */
    private String content;

    private  Integer isAuthor;
    /**
    * 评论楼中楼映射对象
    * */
    private List<CommentVo> children=new ArrayList<>();


    private Date createTime;

}
