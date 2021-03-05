package com.unicom.excelforunicom.utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileOutputStream;

public class PDFUtil extends PdfPageEventHelper {

    //pdf密码 ：只有密码输入正确才能修改内容
   public static final String PDF_PWD = "tj_normal_officesystem";
    /**
     *
     * <br>
     * <p>
     * Description: 给pdf文件添加水印 <br>
     * <p><br/>
     * <p>
     *
     * @param InPdfFile
     *            要加水印的原pdf文件路径
     * @param outPdfFile
     *            加了水印后要输出的路径
     * @param markImagePath
     *            水印图片路径
     * @param imgWidth
     *            图片横坐标
     * @param imgHeight
     *            图片纵坐标
     * @throws Exception
     * @see
     */
    public static void addPdfImgMark(String InPdfFile, String outPdfFile, String markImagePath,int imgWidth, int imgHeight,String imgPath) throws Exception {
        PdfReader reader = new PdfReader(InPdfFile);
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(new File(outPdfFile)));

        byte[] ownerPassword = PDF_PWD.getBytes();
        stamp.setEncryption(null, ownerPassword, PdfWriter.ALLOW_PRINTING, false);
        PdfContentByte under;

        PdfGState gs1 = new PdfGState();
        gs1.setFillOpacity(0.3f);// 透明度设置

        Image img = Image.getInstance(markImagePath);// 插入图片水印

        img.setAbsolutePosition(imgWidth, imgHeight); // 坐标
        img.setRotation(-20);// 旋转 弧度
        img.setRotationDegrees(45);// 旋转 角度
        // img.scaleAbsolute(200,100);//自定义大小
        // img.scalePercent(50);//依照比例缩放

        int pageSize = reader.getNumberOfPages();// 原pdf文件的总页数
        for(int i = 1; i <= pageSize; i++) {
            under = stamp.getUnderContent(i);// 水印在之前文本下
            // under = stamp.getOverContent(i);//水印在之前文本上
            under.setGState(gs1);// 图片水印 透明度
            under.addImage(img);// 图片水印
        }
        //添加头像
        Image imgHead = Image.getInstance(imgPath);
        imgHead.scaleAbsolute(75,100);
        imgHead.setAbsolutePosition(480, 650);
        imgHead.setAlignment(Image.ALIGN_RIGHT);
        under = stamp.getOverContent(1);
        under.addImage(imgHead);
        stamp.close();// 关闭

        //创建完成后将原文件删除
        File file = new File(InPdfFile);
        if(file.exists()){
            file.delete();
        }
    }

    /**
     *
     * <br>
     * <p>
     * Description: 给pdf文件添加水印 和 图片 <br>
     *
     * @param InPdfFile
     *            要加水印的原pdf文件路径
     * @param outPdfFile
     *            加了水印后要输出的路径
     * @param textMark
     *            水印文字
     * @param textWidth
     *            文字横坐标
     * @param textHeight
     *            文字纵坐标
     * @throws Exception
     * @see
     */
    public static void addPdfTextMark(Document document,String InPdfFile, String outPdfFile, String textMark,int textWidth,
                                      int textHeight,BaseFont font,String imgPath) throws Exception {
        PdfReader reader = new PdfReader(InPdfFile);
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(new File(outPdfFile)));
        //加密
        byte[] ownerPassword = PDF_PWD.getBytes();
        stamp.setEncryption(null, ownerPassword, PdfWriter.ALLOW_PRINTING, false);
        PdfGState gs1 = new PdfGState();
        // 透明度设置
        gs1.setFillOpacity(0.3f);
        PdfContentByte under;
        // 原pdf文件的总页数
        int pageSize = reader.getNumberOfPages();
        for(int i = 1; i <= pageSize; i++) {
            // 水印在之前文本下
            under = stamp.getUnderContent(i);
            // under = stamp.getOverContent(i);//水印在之前文本上
            under.beginText();
            // 文字水印 颜色
            under.setColorFill(BaseColor.GRAY);
            // 文字水印 字体及字号
            under.setFontAndSize(font, 45);
            // 文字水印 起始位置
            under.setTextMatrix(textWidth, textHeight);
            under.setGState(gs1);
            under.showTextAligned(Element.ALIGN_CENTER, textMark, textWidth, textHeight, 45);
            under.endText();
        }
        //添加头像
        if(!StringUtils.isNullOrEmpty(imgPath)){
            Image img = Image.getInstance(imgPath);
            img.scaleAbsolute(75,100);
            img.setAbsolutePosition(35, 700);
            img.setAlignment(Image.ALIGN_RIGHT);
            under = stamp.getOverContent(1);
            under.addImage(img);
        }
        stamp.close();// 关闭

        //创建完成后将原文件删除
       File file = new File(InPdfFile);
        if(file.exists()){
            file.delete();
        }
    }

    /**
     * 检查页面还有多少容量 不够就换页
     * @param yPos
     */
//    public static PDFPage checkPageSize(Document document,int yPos){
//        if(yPos<=30){
//            document.newPage();
//            return new PDFPage(true,800);
//        }
//        return new PDFPage(false,yPos);
//    }
//
//    public static boolean isNeedChange(int yPos){
//        if(yPos<=30){
//            return true;
//        }
//        return false;
//    }
}
