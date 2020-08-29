/*
 * Copyright 2004 - 2012 Mirko Nasato and contributors
 *           2016 - 2019 Simon Braconnier and contributors
 *
 * This file is part of JODConverter - Java OpenDocument Converter.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aoju.bus.example.spring;

import org.aoju.bus.base.consts.ErrorCode;
import org.aoju.bus.base.spring.BaseController;
import org.aoju.bus.core.key.ObjectID;
import org.aoju.bus.core.lang.exception.InstrumentException;
import org.aoju.bus.core.toolkit.StringKit;
import org.aoju.bus.crypto.Builder;
import org.aoju.bus.example.entity.CommonVersion;
import org.aoju.bus.example.service.CommonPreviewService;
import org.aoju.bus.logger.Logger;
import org.aoju.bus.office.magic.family.DefaultFormatRegistry;
import org.aoju.bus.office.magic.family.DocumentFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 版本管理
 */
@RestController
@RequestMapping("/preview")
public class CommonPreviewController extends BaseController<CommonPreviewService, CommonVersion> {

    /**
     * 文档转换
     * 将传入的文档转换为指定的格式
     *
     * @param inputFile 文件
     * @param type      格式
     * @return 文档
     */
    @PostMapping("/index")
    public Object convertToUsingParam(
            @RequestParam("data") final MultipartFile inputFile,
            @RequestParam(name = "type") final String type) {
        Logger.debug("convert > Converting file to {}", type);
        if (inputFile.isEmpty()) {
            return write(ErrorCode.EM_100506);
        }

        if (StringKit.isBlank(type)) {
            return write(ErrorCode.EM_100506);
        }

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            final DocumentFormat targetFormat = DefaultFormatRegistry.getFormatByExtension(type);
            InputStream is = inputFile.getInputStream();
            Logger.error("md5Hex:" + Builder.md5Hex(is));
            service.preview(inputFile.getInputStream(), out, type, inputFile.getOriginalFilename());

            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(targetFormat.getMediaType()));
            headers.add(
                    "Content-Disposition",
                    "attachment; filename="
                            + ObjectID.id()
                            + "."
                            + targetFormat.getExtension());

            return ResponseEntity.ok().headers(headers).body(out.toByteArray());
        } catch (InstrumentException | IOException ex) {
            return write(ErrorCode.EM_100506);
        }
    }


/*

    @Autowired
    PreviewProviderService previewProviderService;


    String fileDir = null;//previewProviderService.properties.getStorage().getType().get(org.aoju.bus.storage.Registry.LOCAL).getRegion();
    String demoDir = "demo";
    String demoPath = demoDir + File.separator;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String go2Index() {
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "redirect:/index";
    }


    @RequestMapping(value = "fileUpload", method = RequestMethod.POST)
    public String fileUpload(@RequestParam("file") MultipartFile file) throws JsonProcessingException {
        // 获取文件名
        String fileName = file.getOriginalFilename();
        //判断是否为IE浏览器的文件名，IE浏览器下文件名会带有盘符信息
        // Check for Unix-style path
        int unixSep = fileName.lastIndexOf('/');
        // Check for Windows-style path
        int winSep = fileName.lastIndexOf('\\');
        // Cut off at latest possible point
        int pos = (winSep > unixSep ? winSep : unixSep);
        if (pos != -1) {
            fileName = fileName.substring(pos + 1);
        }

        // 判断该文件类型是否有上传过，如果上传过则提示不允许再次上传
        if (existsTypeFile(fileName)) {
            return new ObjectMapper().writeValueAsString(new Message<String>("1", "每一种类型只可以上传一个文件，请先删除原有文件再次上传", null));
        }
        File outFile = new File(fileDir + demoPath);
        if (!outFile.exists()) {
            outFile.mkdirs();
        }
        try (InputStream in = file.getInputStream();
             OutputStream ot = new FileOutputStream(fileDir + demoPath + fileName)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((-1 != (len = in.read(buffer)))) {
                ot.write(buffer, 0, len);
            }
            return new ObjectMapper().writeValueAsString(new Message<String>("0", "SUCCESS", null));
        } catch (IOException e) {
            e.printStackTrace();
            return new ObjectMapper().writeValueAsString(new Message<String>("1", "FAILURE", null));
        }
    }

    @RequestMapping(value = "deleteFile", method = RequestMethod.GET)
    public String deleteFile(String fileName) throws JsonProcessingException {
        if (fileName.contains("/")) {
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
        }
        File file = new File(fileDir + demoPath + fileName);
        if (file.exists()) {
            file.delete();
        }
        return new ObjectMapper().writeValueAsString(new Message<String>("0", "SUCCESS", null));
    }

    @RequestMapping(value = "listFiles", method = RequestMethod.GET)
    public String getFiles() throws JsonProcessingException {
        List<Map<String, String>> list = Lists.newArrayList();
        File file = new File(fileDir + demoPath);
        if (file.exists()) {
            Arrays.stream(file.listFiles()).forEach(file1 -> list.add(ImmutableMap.of("fileName", demoDir + "/" + file1.getName())));
        }
        return new ObjectMapper().writeValueAsString(list);
    }

    private String getFileName(String name) {
        String suffix = name.substring(name.lastIndexOf("."));
        String nameNoSuffix = name.substring(0, name.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        return uuid + "-" + nameNoSuffix + suffix;
    }

    private boolean existsTypeFile(String fileName) {
        boolean result = false;
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        File file = new File(fileDir + demoPath);
        if (file.exists()) {
            for (File file1 : file.listFiles()) {
                String existsFileSuffix = file1.getName().substring(file1.getName().lastIndexOf("."));
                if (suffix.equals(existsFileSuffix)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    @RequestMapping(value = "/onlinePreview", method = RequestMethod.GET)
    public String onlinePreview(String url, Model model, HttpServletRequest req) {
        FileAttr fileAttr = Builder.getFileAttribute(url);
        req.setAttribute("fileKey", req.getParameter("fileKey"));
        model.addAttribute("officePreviewType", req.getParameter("officePreviewType"));
        Provider provider = previewProviderService.get(Registry.CAD);
        return provider.preview(url, model, fileAttr);
    }


    @RequestMapping(value = "/picturesPreview")
    public String picturesPreview(Model model, HttpServletRequest req) throws UnsupportedEncodingException {
        String urls = req.getParameter("urls");
        String currentUrl = req.getParameter("currentUrl");
        // 路径转码
        String decodedUrl = URLDecoder.decode(urls, "utf-8");
        String decodedCurrentUrl = URLDecoder.decode(currentUrl, "utf-8");
        // 抽取文件并返回文件列表
        String[] imgs = decodedUrl.split("\\|");
        List imgurls = Arrays.asList(imgs);
        model.addAttribute("imgurls", imgurls);
        model.addAttribute("currentUrl", decodedCurrentUrl);
        return "picture";
    }

    @RequestMapping(value = "/getCorsFile", method = RequestMethod.GET)
    public void getCorsFile(String urlPath, HttpServletResponse resp) {
        InputStream inputStream = null;
        try {
            String strUrl = urlPath.trim();
            URL url = new URL(new URI(strUrl).toASCIIString());
            //打开请求连接
            URLConnection connection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            inputStream = httpURLConnection.getInputStream();
            byte[] bs = new byte[1024];
            int len;
            while (-1 != (len = inputStream.read(bs))) {
                resp.getOutputStream().write(bs, 0, len);
            }
        } catch (IOException | URISyntaxException e) {
            Logger.error("下载pdf文件失败", e);
        } finally {
            if (inputStream != null) {
                IoKit.close(inputStream);
            }
        }
    }

    @GetMapping("/addTask")
    @ResponseBody
    public String addQueueTask(String url) {
        previewProviderService.previewCache.addQueueTask(url);
        return "success";
    }

*/

}
