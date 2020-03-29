package com.springboot.springtest.common;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@Api(description = "二维码生成接口")
@RequestMapping(value = "create")
@Slf4j
public class QrCodeController {
    private static final Logger log = LoggerFactory.getLogger(QrCodeController.class);
    /**
     * 动态生成二维码流
     * @param response
     * @param url 生成推荐码的url
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/qrcode", method = RequestMethod.GET)
    @ApiOperation("二维码生成接口:参数用base64加密")
    public Object qrcode(HttpServletResponse response, String url) throws Exception {
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");
        ServletOutputStream sos = null;
        url = new String(Base64Utils.decodeFromString(url));
        try {
            // 将图像输出到 Servlet输出流中。
            sos = response.getOutputStream();
            int width = 300; // 图像宽度
            int height = 300; // 图像高度
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url,BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵

            ImageIO.write(MatrixToImageWriter.toBufferedImage(bitMatrix), "PNG", sos);
        } catch (Exception e) {
            log.error("生成二维码时发生异常",e);
        } finally {
            if (null != sos) {
                try {
                    sos.close();
                } catch (IOException e) {
                    log.error("生成二维码关闭输出流发生异常",e);
                }
            }
        }
        return null;
    }
}
