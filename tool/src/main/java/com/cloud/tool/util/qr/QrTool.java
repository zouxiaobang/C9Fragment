package com.cloud.tool.util.qr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * @Author: xb.zou
 * @Date: 2019/2/22
 * @Desc: to-> 二维码工具
 */
public class QrTool {
    private static final Map<DecodeHintType, Object> HINT_MAP = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);

    static {
        List<BarcodeFormat> allFormatList = new ArrayList<>();
        allFormatList.add(BarcodeFormat.AZTEC);
        allFormatList.add(BarcodeFormat.CODABAR);
        allFormatList.add(BarcodeFormat.CODE_39);
        allFormatList.add(BarcodeFormat.CODE_93);
        allFormatList.add(BarcodeFormat.CODE_128);
        allFormatList.add(BarcodeFormat.DATA_MATRIX);
        allFormatList.add(BarcodeFormat.EAN_8);
        allFormatList.add(BarcodeFormat.EAN_13);
        allFormatList.add(BarcodeFormat.ITF);
        allFormatList.add(BarcodeFormat.MAXICODE);
        allFormatList.add(BarcodeFormat.PDF_417);
        allFormatList.add(BarcodeFormat.QR_CODE);
        allFormatList.add(BarcodeFormat.RSS_14);
        allFormatList.add(BarcodeFormat.RSS_EXPANDED);
        allFormatList.add(BarcodeFormat.UPC_A);
        allFormatList.add(BarcodeFormat.UPC_E);
        allFormatList.add(BarcodeFormat.UPC_EAN_EXTENSION);
        HINT_MAP.put(DecodeHintType.POSSIBLE_FORMATS, allFormatList);
        HINT_MAP.put(DecodeHintType.CHARACTER_SET, "utf-8");
    }

    /**
     * 生成二维码图片
     * 这里的url可以是地址、字符串、中文
     * 默认生成 500 * 500
     */
    public static Bitmap createQrImg(String url) {
        return createQrImg(url, 500);
    }

    public static Bitmap createQrImg(String url, int size) {
        Bitmap resultBitmap = null;

        try {
            if (TextUtils.isEmpty(url)) {
                return null;
            }

            resultBitmap = generateQrImg(url, size);
        } catch (WriterException e) {
            return null;
        }

        return resultBitmap;
    }

    /**
     * 生成带有自定义logo的二维码
     * 这里的url可以是地址、字符串、中文
     * 默认生成 500 * 500
     */
    public static Bitmap createQrImgWithLogo(String url, Bitmap logo) {
        return createQrImgWithLogo(url, 500, logo);
    }

    public static Bitmap createQrImgWithLogo(String url, int size, Bitmap logo) {
        Bitmap resultBitmap = null;

        try {
            if (TextUtils.isEmpty(url)) {
                return null;
            }

            resultBitmap = generateQrImg(url, size, logo);
        } catch (WriterException e) {
            return null;
        }

        return resultBitmap;
    }

    /**
     * 解析二维码图片，生成String类型
     * 这里的字符串可以是地址、字符串、中文
     *
     */
    public static String decodeQrImg(String qrImgPath) {
        return decodeQrImg(getImgByPath(qrImgPath));
    }

    public static String decodeQrImg(Bitmap qrImg) {
        if (qrImg == null) {
            return null;
        }

        try {
            int width = qrImg.getWidth();
            int height = qrImg.getHeight();
            int[] pixels = new int[width * height];
            qrImg.getPixels(pixels, 0, width, 0, 0, width, height);
            RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
            Result result = new MultiFormatReader().decode(new BinaryBitmap(new HybridBinarizer(source)), HINT_MAP);
            if (result == null) {
                return null;
            }

            return recodeHandleGarbled(result.getText());
        } catch (NotFoundException e) {
            return null;
        }
    }


    /**
     * 解决中文乱码问题
     */
    private static String recodeHandleGarbled(String text) {
        String formart = "";
        try {
            boolean ISO = Charset.forName("ISO-8859-1").newEncoder()
                    .canEncode(text);
            if (ISO) {
                formart = new String(text.getBytes("ISO-8859-1"), "GB2312");
            } else {
                formart = text;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return formart;
    }

    private static Bitmap getImgByPath(String path) {
        Bitmap resultBitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int sampleSize = options.outHeight / 400;
            if (sampleSize <= 0)
                sampleSize = 1;
            options.inSampleSize = sampleSize;
            options.inJustDecodeBounds = false;
            resultBitmap = BitmapFactory.decodeFile(path, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultBitmap;
    }

    private static Bitmap generateQrImg(String url, int size) throws WriterException {
        return generateQrImg(url, size, null);
    }

    private static Bitmap generateQrImg(String url, int size, Bitmap logo) throws WriterException {
        int imageHalfWidth = size / 10;
        Hashtable<EncodeHintType, Object> hintTable = new Hashtable<>();
        hintTable.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hintTable.put(EncodeHintType.MARGIN, 0);
        if (logo != null) {
            //设置容错级别，默认为ErrorCorrectionLevel.L
            //因为中间加入logo所以建议把容错级别调至H,否则可能会出现识别不了
            hintTable.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        }

        BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, size, size, hintTable);

        //矩阵高度
        int width = bitMatrix.getWidth();
        //矩阵宽度
        int height = bitMatrix.getHeight();
        int halfW = width / 2;
        int halfH = height / 2;
        if (logo != null) {
            Matrix matrix = new Matrix();
            float sx = (float) 2 * imageHalfWidth / logo.getWidth();
            float sy = (float) 2 * imageHalfWidth / logo.getHeight();
            matrix.setScale(sx, sy);
            logo = Bitmap.createBitmap(logo, 0, 0, logo.getWidth(), logo.getHeight(), matrix, false);
        }


        int[] pixels = new int[size * size];
        for (int y = 0 ; y < size ; y ++) {
            for (int x = 0 ; x < size ; x ++) {
                if (logo != null
                        && x > (halfW - imageHalfWidth)
                        && x < (halfW + imageHalfWidth)
                        && y > (halfH - imageHalfWidth)
                        && y < (halfH + imageHalfWidth)) {
                    pixels[y * width + x] = logo.getPixel(x - halfW + imageHalfWidth, y - halfH + imageHalfWidth);
                } else {
                    //(x, y)为坐标
                    if (bitMatrix.get(x, y)){
                        pixels[y * size + x] = 0xff000000;
                    } else {
                        pixels[y * size + x] = 0xffffffff;
                    }
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, size, 0, 0, size, size);
        return bitmap;
    }
}
