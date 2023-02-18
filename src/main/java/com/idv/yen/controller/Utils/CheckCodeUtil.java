package com.idv.yen.controller.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Random;

/**
 * 動態生成圖片
 */
public class CheckCodeUtil {
    // {"宋體", "華文楷體", "黑體", "華文新魏", "華文隸書", "微軟雅黑", "楷體_GB2312"}
    private static String[] fontNames = { "宋體", "華文楷體", "黑體", "微軟雅黑",  "楷體_GB2312" };
    // 可選字元
    //"23456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
    private static String codes = "23456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
    // 背景色
    private Color bgColor = new Color(255, 255, 255);
    // 基數(一個文字所佔的空間大小)
    private int base = 30;
    // 影象寬度
    private int width = base * 4;
    // 影象高度
    private int height = base;
    // 文字個數
    private int len = 4;
    // 設定字型大小
    private int fontSize = 22;
    // 驗證碼上的文字
    private String text;

    private BufferedImage img = null;
    private Graphics2D g2 = null;

    /**
     * 生成驗證碼圖片
     */
    public String drawImage(OutputStream outputStream) {
        // 1.建立圖片緩衝區物件, 並設定寬高和影象型別
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 2.得到繪製環境
        g2 = (Graphics2D) img.getGraphics();
        // 3.開始畫圖
        // 設定背景色
        g2.setColor(bgColor);
        g2.fillRect(0, 0, width, height);

        StringBuffer sb = new StringBuffer();// 用來裝載驗證碼上的文字

        for (int i = 0; i < len; i++) {
            // 設定畫筆顏色 -- 隨機
            // g2.setColor(new Color(255, 0, 0));
            g2.setColor(new Color(getRandom(0, 150), getRandom(0, 150),getRandom(0, 150)));

            // 設定字型
            g2.setFont(new Font(fontNames[getRandom(0, fontNames.length)], Font.BOLD, fontSize));

            // 旋轉文字(-45~+45)
            int theta = getRandom(-45, 45);
            g2.rotate(theta * Math.PI / 180, 7 + i * base, height - 8);

            // 寫字
            String code = codes.charAt(getRandom(0, codes.length())) + "";
            g2.drawString(code, 7 + i * base, height - 8);
            sb.append(code);
            g2.rotate(-theta * Math.PI / 180, 7 + i * base, height - 8);
        }

        this.text = sb.toString();

        // 畫干擾線
        for (int i = 0; i < len + 2; i++) {
            // 設定畫筆顏色 -- 隨機
            // g2.setColor(new Color(255, 0, 0));
            g2.setColor(new Color(getRandom(0, 150), getRandom(0, 150),
                    getRandom(0, 150)));
            g2.drawLine(getRandom(0, 120), getRandom(0, 30), getRandom(0, 120),
                    getRandom(0, 30));
        }
        //TODO:
        g2.setColor(Color.GRAY);
        g2.drawRect(0, 0, this.width-1, this.height-1);
        // 4.儲存圖片到指定的輸出流
        try {
            ImageIO.write(this.img, "JPEG", outputStream);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally{
            // 5.釋放資源
            g2.dispose();
        }

        return getCode();
    }

    /**
     * 獲取驗證碼字串
     * @return
     */
    public String getCode() {
        return this.text;
    }

    /*
     * 生成亂數的方法
     */
    private static int getRandom(int start, int end) {
        Random random = new Random();
        return random.nextInt(end - start) + start;
    }

//	public static void main(String[] args) throws Exception {
//        CheckCodeUtil vc = new CheckCodeUtil();
//		vc.drawImage(new FileOutputStream("d:/vc.jpg"));
//		System.out.println("執行成功~!");
//	}
}
