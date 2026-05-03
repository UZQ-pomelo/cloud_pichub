package com.pichub.cloud_pichub.manager.upload;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.pichub.cloud_pichub.exception.BusinessException;
import com.pichub.cloud_pichub.exception.ErrorCode;
import com.pichub.cloud_pichub.exception.ThrowUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UrlPictureUpload extends PictureUploadTemplate {

    /**
     * Content-Type 到文件扩展名的映射表
     * 当 URL 没有文件后缀时，通过 HTTP 响应头的 Content-Type 推断文件格式
     */
    private static final Map<String, String> CONTENT_TYPE_EXTENSION_MAP = new ConcurrentHashMap<>();
    
    /**
     * 初始化 Content-Type 映射表
     * key: HTTP Content-Type（小写）
     * value: 对应的文件扩展名
     */
    static {
        CONTENT_TYPE_EXTENSION_MAP.put("image/jpeg", "jpg");
        CONTENT_TYPE_EXTENSION_MAP.put("image/jpg", "jpg");
        CONTENT_TYPE_EXTENSION_MAP.put("image/png", "png");
        CONTENT_TYPE_EXTENSION_MAP.put("image/webp", "webp");
        CONTENT_TYPE_EXTENSION_MAP.put("image/gif", "gif");
        CONTENT_TYPE_EXTENSION_MAP.put("image/bmp", "bmp");
    }
    
    /**
     * 线程本地变量，用于缓存当前请求推断出的文件扩展名
     * 在 validPicture() 方法中设置，在 getOriginFilename() 方法中使用
     * 使用 ThreadLocal 保证多线程环境下的安全性
     */
    private ThreadLocal<String> fileExtension = new ThreadLocal<>();

    @Override
    protected void validPicture(Object inputSource) {
        String fileUrl = (String) inputSource;
        // 1. 校验非空
        ThrowUtils.throwIf(StrUtil.isBlank(fileUrl), ErrorCode.PARAMS_ERROR, "文件地址为空");

        // 2. 校验 URL 格式
        try {
            new URL(fileUrl);
        } catch (MalformedURLException e) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件地址格式不正确");
        }
        // 3. 校验 URL 的协议
        ThrowUtils.throwIf(!fileUrl.startsWith("http://") && !fileUrl.startsWith("https://"),
                ErrorCode.PARAMS_ERROR, "仅支持 HTTP 或 HTTPS 协议的文件地址"
        );
        // 4. 发送 HEAD 请求验证文件是否存在
        HttpResponse httpResponse = null;
        try {
            httpResponse = HttpUtil.createRequest(Method.HEAD, fileUrl)
                    .execute();
            // 未正常返回，无需执行其他判断
            if (httpResponse.getStatus() != HttpStatus.HTTP_OK) {
                return;
            }
            // 5. 文件存在，文件类型校验
            String contentType = httpResponse.header("Content-Type");
            // 不为空，才校验是否合法，这样校验规则相对宽松
            if (StrUtil.isNotBlank(contentType)) {
                // 允许的图片类型
                final List<String> ALLOW_CONTENT_TYPES = Arrays.asList("image/jpeg", "image/jpg", "image/png", "image/webp");
                String lowerContentType = contentType.toLowerCase();
                ThrowUtils.throwIf(!ALLOW_CONTENT_TYPES.contains(lowerContentType),
                        ErrorCode.PARAMS_ERROR, "文件类型错误");
                
                String extension = CONTENT_TYPE_EXTENSION_MAP.get(lowerContentType);
                if (extension != null) {
                    fileExtension.set(extension);
                }
            }
            // 6. 文件存在，文件大小校验
            String contentLengthStr = httpResponse.header("Content-Length");
            if (StrUtil.isNotBlank(contentLengthStr)) {
                try {
                    long contentLength = Long.parseLong(contentLengthStr);
                    final long ONE_M = 1024 * 1024;
                    ThrowUtils.throwIf(contentLength > 2 * ONE_M, ErrorCode.PARAMS_ERROR, "文件大小不能超过 2MB");
                } catch (NumberFormatException e) {
                    throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件大小格式异常");
                }
            }
        } finally {
            // 记得释放资源
            if (httpResponse != null) {
                httpResponse.close();
            }
        }
    }

    @Override
    protected String getOriginFilename(Object inputSource) {
        String fileUrl = (String) inputSource;
        String mainName = FileUtil.mainName(fileUrl);
        String suffix = FileUtil.getSuffix(fileUrl);
        
        if (StrUtil.isBlank(suffix)) {
            suffix = fileExtension.get();
        }
        
        if (StrUtil.isBlank(suffix)) {
            suffix = "jpg";
        }
        
        return mainName + "." + suffix;
    }

    @Override
    protected void processFile(Object inputSource, File file) throws Exception {
        String fileUrl = (String) inputSource;
        // 下载文件到临时目录
        HttpUtil.downloadFile(fileUrl, file);
    }
}

