package com.diary.main.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.diary.main.config.QiniuFileConfig;
import com.diary.main.enums.MsgEnum;
import com.diary.main.exception.DiaryException;
import com.diary.main.model.ConstantQiniu;
import com.diary.main.model.ImageFile;
import com.diary.main.service.ImageFileService;
import com.diary.main.vo.ResultVo;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import sun.security.util.KeyUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hao
 * @since 2019-10-28
 */
@RestController
@RequestMapping("/upload")
@Slf4j
public class ImageFileController {

    @Autowired
    private ConstantQiniu constantQiniu;

    @Autowired
    private ImageFileService imageFileService;

    @Autowired
    private QiniuFileConfig qiniuFileConfig;

    @RequestMapping(method =RequestMethod.GET)
    @ApiOperation(value="查询所有附件", notes="查询所有附件。")
    public ResultVo getData() throws IOException {
        Wrapper<ImageFile> wrapper=  new EntityWrapper<ImageFile>();
        List<ImageFile> list=imageFileService.selectList(wrapper);
        return  ResultVo.GETDATA_SUCCESS(list);
    }


    @RequestMapping(method =RequestMethod.POST)
    @ApiOperation(value="新增附件", notes="新增附件。")
    @ApiImplicitParam(name = "file", value = "file", required = false, dataType = "MultipartFile")
    public ResultVo upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {

        if(multipartFile==null){
            throw  new DiaryException(MsgEnum.NOT_File);
        }
        FileInputStream inputStream = (FileInputStream) multipartFile.getInputStream();
        String name=multipartFile.getOriginalFilename();
        String keyID=UUID.randomUUID().toString().replaceAll("-","");
        String path = uploadQNImg(inputStream,keyID);
        String url=path;
        int begin=name.indexOf(".");
        int end=name.length();
        String fileType=name.substring(begin,end);//文件类型
        if(!fileType.equalsIgnoreCase(".jpg")&&!fileType.equalsIgnoreCase(".png")){
            url="/src/assets/images/attach.png";
        }
        ImageFile imageFile=new ImageFile(keyID,path,url,name);
        imageFileService.insert(imageFile);
        return  ResultVo.SUCCESS(imageFile);
    }


    @RequestMapping(value ="{keyid}",method =RequestMethod.DELETE)
    @ApiOperation(value="删除附件", notes="删除附件。")
    @ApiImplicitParam(name = "keyid", value = "keyid", required = false, dataType = "String")
    public ResultVo del(@RequestBody @PathVariable("keyid")String keyid){
        Response response=null;
        try {
             response = qiniuFileConfig.bucketManager().delete(constantQiniu.getBucket(),keyid);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        }catch (QiniuException e){
            log.error("七牛云删除图片异常:error={}",e.getMessage());
        }
        Wrapper<ImageFile> wrapper=new EntityWrapper<>();
        wrapper.eq("key_id",keyid);
        imageFileService.delete(wrapper);
        return  ResultVo.SUCCESS(null);
    }




    /**
     * 将图片上传到七牛云
     */
    private String uploadQNImg(FileInputStream file, String key) {
//        // 构造一个带指定Zone对象的配置类
//        Configuration cfg = new Configuration(Zone.zone2());
//        // 其他参数参考类注释
//        UploadManager uploadManager = new UploadManager(cfg);
//        // 生成上传凭证，然后准备上传

        try {
//            Auth auth = Auth.create(constantQiniu.getAccessKey(), constantQiniu.getSecretKey());
            Auth auth=qiniuFileConfig.auth();
            String upToken = auth.uploadToken(constantQiniu.getBucket());
            try {
                Response response = qiniuFileConfig.uploadManager().put(file, key, upToken, null, null);
                // 解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                String returnPath = constantQiniu.getPath() + "/" + putRet.key;
                return returnPath;
            } catch (QiniuException ex) {
                log.error("ImageFileController 层 七牛云上传图片异常 error={}",ex.error());
                throw  new DiaryException(MsgEnum.ERROR);
            }
        } catch (Exception e) {
            log.error("ImageFileController 层 七牛云上传图片异常 error={}",e.getMessage());
            throw  new DiaryException(MsgEnum.ERROR);
        }
//        return "";
    }

}

