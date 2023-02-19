package com.janty.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.janty.entity.HouseImage;
import com.janty.result.Result;
import com.janty.service.HouseImageService;
import com.janty.util.QiniuUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @author: Janty
 * @projectName: shf-parent
 * @date: 2023/2/14 16:29
 * @description:
 */

@Controller
@RequestMapping("/houseImage")
public class HouseImageController {

    @Reference
    private HouseImageService houseImageService;

    @RequestMapping("/uploadShow/{houseId}/{type}")
    public String goUploadPage(@PathVariable("houseId") Long houseId,
                               @PathVariable("type") Integer type, Map map){
        map.put("houseId",houseId);
        map.put("type",type);
        return "house/upload";
    }

    //删除图片
    @RequestMapping("/upload/{houseId}/{type}")
    @ResponseBody
    public Result upload (@PathVariable("houseId") Long houseId,
                          @PathVariable("type") Integer type,
                          @RequestParam("file") MultipartFile[] files){
        try {
            if(files != null && files.length > 0){
                for (MultipartFile file : files) {
                    byte[] bytes = file.getBytes();
                    String originalFilename = file.getOriginalFilename();
                    //UUID作为文件名
                    String newFileName = UUID.randomUUID().toString();
                    QiniuUtils.upload2Qiniu(bytes,newFileName);
                    //创建
                    HouseImage houseImage = new HouseImage();
                    houseImage.setHouseId(houseId);
                    houseImage.setType(type);
                    houseImage.setImageName(originalFilename);
                    houseImage.setImageUrl("http://rq28dhr3f.hn-bkt.clouddn.com/" + newFileName);
                    houseImageService.insert(houseImage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }

    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId,
                         @PathVariable("id") Long id){
          houseImageService.delete(id);
          return "redirect:/house/" + houseId;
    }

}
