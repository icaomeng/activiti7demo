package com.cao.activti.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommUtils {

    /**
     * 判断一个对象(包括String、以及Collection下的各个子类集合) 是否为空, 为空则返回true, 否则会返回false
     *
     * @param obj
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object obj) {
        if (obj == null)
            return true;
        if (obj instanceof String) {
            if (((String) obj).equals(""))
                return true;
        }
        if (obj instanceof Collection<?>) {
            if (((Collection) obj).size() <= 0)
                return true;
        }
        if (obj instanceof String[]) {
            if (((String[]) obj).length <= 0) {
                return true;
            }
        }
        if (obj instanceof Object[]) {
            if (((Object[]) obj).length <= 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * 生成uuid
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 将指定日期按照指定的格式以字符串的形式进行返回
     *
     * @param pattern
     * @param date
     * @return
     */
    public static String getDate(String pattern, Date date) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }


    /**
     * 将当前日期按照 "年-月-日 时:分:秒" 的日期格式以字符串的形式返回
     *
     * @return
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd HH:mm:ss", new Date());
    }


    public static String getMD5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    /**
     * 下载文件
     *
     * @param path
     * @param response
     */
    public static void downloadFile(String path, HttpServletResponse response) {
        File file = new File(path);

        byte[] buffer = new byte[(int) file.length()];
        FileInputStream fis = null; //文件输入流
        BufferedInputStream bis = null;

        OutputStream os = null; //输出流
        try {
            os = response.getOutputStream();
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer);
                i = bis.read(buffer);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("----------file download----------" + path);
        try {
            bis.close();
            fis.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //递归删文件
    public static boolean delAllFile(File file) {
        if (!file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    delAllFile(f);
                }
            }
        }
        return file.delete();
    }

    public static void delDiskFile(String filePath) {
        //删除磁盘上的附件文件
        try {
            File file = new File(filePath);
            if (file.exists()) {
                CommUtils.delAllFile(new File(filePath));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static File multipartFileToFile(MultipartFile multipartFile) {
        try {
            // 获取文件名
            String fileName = multipartFile.getOriginalFilename();
            // 获取文件后缀
            String prefix = fileName.substring(fileName.lastIndexOf("."));
            // 用uuid作为文件名，防止生成的临时文件重复
            File tempFile = File.createTempFile(CommUtils.getUUID(), prefix);
            multipartFile.transferTo(tempFile);
            return tempFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 逆向递归删除空目录
    public void delEmptyPath(String path) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0)
                return;
            if (file.delete()) {
                delEmptyPath(file.getParent());
            }
        }
    }

    //拷贝到临时文件夹
    public static void copyDir(String sourcePath, String newPath) throws IOException {
        File file = new File(sourcePath);
        String[] filePath = file.list();
        if (!(new File(newPath)).exists()) {
            (new File(newPath)).mkdir();
        } else {
            delAllFile(new File(newPath));
            (new File(newPath)).mkdir();
        }
        if (filePath != null) {
            for (int i = 0; i < filePath.length; i++) {
                if ((new File(sourcePath + file.separator + filePath[i])).isDirectory()) {
                    copyDir(sourcePath + file.separator + filePath[i], newPath + file.separator + filePath[i]);
                }
                if (new File(sourcePath + file.separator + filePath[i]).isFile() && !new File(sourcePath + file.separator + filePath[i]).getName().contains("ys_")) {
                    copyFile(sourcePath + file.separator + filePath[i], newPath + file.separator + filePath[i]);
                }
            }
        }
    }

    public static void copyFile(String oldPath, String newPath) throws IOException {
        File oldFile = new File(oldPath);
        File file = new File(newPath);
        FileInputStream in = new FileInputStream(oldFile);
        FileOutputStream out = new FileOutputStream(file);
        byte[] buffer = new byte[2097152];
        while ((in.read(buffer)) != -1) {
            out.write(buffer);

        }
        out.close();
        in.close();

    }

    public static File getTemplateFile(String templatePath) throws Exception {
        File file = File.createTempFile("temp", null);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(templatePath);
        if (resources.length == 1) {
            InputStream inputStream = resources[0].getInputStream();
            inputStreamToFile(inputStream, file);
        } else {
            System.out.println("请检查模板文件是否存在");
        }
        return file;
    }

    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static File searchFile(File dir) {
        File[] subFiles = dir.listFiles();       //获取e盘下所有的文件或文件夹对象
        File resFile = dir;
        if (null != subFiles) {
            for (File subFile : subFiles) {
                if (subFile.isDirectory()) {        //文件夹则递归寻找，后缀为jpg文件则输出名字
                    resFile = searchFile(subFile);

                } else if (subFile.isFile() && subFile.getName().endsWith(".shp")) {
                    resFile = subFile;
                    break;
                }
            }
        }
        return resFile;
    }

    /**
     * byte数组转换成16进制字符串
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 根据文件流判断图片类型
     *
     * @param fis
     * @return jpg/png/gif/bmp
     */
    public static String getPicType(FileInputStream fis) {
        //读取文件的前几个字节来判断图片格式
        byte[] b = new byte[4];
        try {
            fis.read(b, 0, b.length);
            String type = bytesToHexString(b).toUpperCase();
            if (type.contains("FFD8FF")) {
                return "jpg";
            } else if (type.contains("89504E47")) {
                return "png";
            } else if (type.contains("47494638")) {
                return "gif";
            } else if (type.contains("424D")) {
                return "bmp";
            } else {
                return "unknown";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static boolean isPicBySuffix(String suffix) {
        if (".JPG".equals(suffix.toUpperCase()) || ".PNG".equals(suffix.toUpperCase())
                || ".JPEG".equals(suffix.toUpperCase()) || ".GIF".equals(suffix.toUpperCase())
                || ".BMP".equals(suffix.toUpperCase())) {
            return true;
        }
        return false;
    }

    public static boolean isWordBySuffix(String suffix) {
        if (".DOC".equals(suffix.toUpperCase()) || ".DOCX".equals(suffix.toUpperCase())) {
            return true;
        }
        return false;
    }

    public static boolean isDxfBySuffix(String suffix) {
        if (".DXF".equals(suffix.toUpperCase())) {
            return true;
        }
        return false;
    }

    public static boolean isZipBySuffix(String suffix) {
        if (".ZIP".equals(suffix.toUpperCase())) {
            return true;
        }
        return false;
    }

    public static boolean isExcelBySuffix(String suffix) {
        if (".XLS".equals(suffix.toUpperCase()) || ".XLSX".equals(suffix.toUpperCase())) {
            return true;
        }
        return false;
    }


    public static JSONObject xAxisSeries(List<Object> xAxisDataList, List<Object> seriesDataList) {
        JSONObject jsonObject = new JSONObject();
        JSONObject xAxisData = new JSONObject();
        xAxisData.put("data", xAxisDataList);
        jsonObject.put("xAxis", xAxisData);

        JSONObject dd = new JSONObject();
        dd.put("data", seriesDataList);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(dd);
        jsonObject.put("series", jsonArray);
        return jsonObject;
    }

    public static JSONObject legendSeries(List<Object> legendDataList, List<Object> seriesDataList) {
        JSONObject jsonObject = new JSONObject();
        JSONObject legendData = new JSONObject();
        legendData.put("data", legendDataList);
        jsonObject.put("legend", legendData);

        JSONObject dd = new JSONObject();
        JSONArray ja = new JSONArray();

        for (int i = 0; i < seriesDataList.size(); i++) {
            JSONObject jo = new JSONObject();
            jo.put("name", legendDataList.get(i));
            jo.put("value", seriesDataList.get(i));
            ja.add(jo);
        }

        dd.put("data", ja);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(dd);
        jsonObject.put("series", jsonArray);
        return jsonObject;
    }


    public static void getSignPIdAndIndex(JSONArray jsonArray, String suuid, Map<String, Integer> map) {
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONArray children = jsonObject.getJSONArray("children");
            if (!ObjectUtils.isEmpty(children) && children.size() > 0) {
                getSignPIdAndIndex(children, suuid, map);
            } else {
                if (suuid.equals(jsonObject.getString("id"))) {
                    map.put(jsonObject.getString("parentTId"), i);
                }
            }
        }
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    public static String toUTF8(String str) {
        if (isEmpty(str)) {
            return "";
        }
        try {
            if (str.equals(new String(str.getBytes("GB2312"), "GB2312"))) {
                str = new String(str.getBytes("GB2312"), "utf-8");
                return str;
            }
        } catch (Exception exception) {
        }
        try {
            if (str.equals(new String(str.getBytes("ISO-8859-1"), "ISO-8859-1"))) {
                str = new String(str.getBytes("ISO-8859-1"), "utf-8");
                return str;
            }
        } catch (Exception exception1) {
        }
        try {
            if (str.equals(new String(str.getBytes("GBK"), "GBK"))) {
                str = new String(str.getBytes("GBK"), "utf-8");
                return str;
            }
        } catch (Exception exception3) {
        }
        return str;
    }

    // 文件和视频
    public static boolean suffixName(String suffixName) {
        if ((".txt").equalsIgnoreCase(suffixName) || (".dwg").equalsIgnoreCase(suffixName)
                || (".jpg").equalsIgnoreCase(suffixName) || (".jpeg").equalsIgnoreCase(suffixName) || (".png").equalsIgnoreCase(suffixName)
                || (".bmp").equalsIgnoreCase(suffixName) || (".gif").equalsIgnoreCase(suffixName)
                || (".mp4").equalsIgnoreCase(suffixName) || (".avi").equalsIgnoreCase(suffixName)
                || (".3gp").equalsIgnoreCase(suffixName) || (".flv").equalsIgnoreCase(suffixName)
                || (".rar").equalsIgnoreCase(suffixName) || (".zip").equalsIgnoreCase(suffixName)
                || (".doc").equalsIgnoreCase(suffixName) || (".docx").equalsIgnoreCase(suffixName)
                || (".xlsx").equalsIgnoreCase(suffixName) || (".xls").equalsIgnoreCase(suffixName) || (".pdf").equalsIgnoreCase(suffixName)
                || (".ppt").equalsIgnoreCase(suffixName) || (".pps").equalsIgnoreCase(suffixName) || (".pptx").equalsIgnoreCase(suffixName)) {
            return true;
        } else {
            return false;
        }
    }

    // 图片
    public static boolean suffixNameImage(String suffixName) {
        if ((".tiff").equalsIgnoreCase(suffixName) || (".pjp").equalsIgnoreCase(suffixName)
                || (".jfif").equalsIgnoreCase(suffixName) || (".gif").equalsIgnoreCase(suffixName) || (".svg").equalsIgnoreCase(suffixName)
                || (".bmp").equalsIgnoreCase(suffixName) || (".png").equalsIgnoreCase(suffixName)
                || (".jpeg").equalsIgnoreCase(suffixName) || (".svgz").equalsIgnoreCase(suffixName)
                || (".jpg").equalsIgnoreCase(suffixName) || (".webp").equalsIgnoreCase(suffixName)
                || (".ico").equalsIgnoreCase(suffixName) || (".xbm").equalsIgnoreCase(suffixName)
                || (".dib").equalsIgnoreCase(suffixName) || (".tif").equalsIgnoreCase(suffixName)
                || (".pjpeg").equalsIgnoreCase(suffixName) || (".avif").equalsIgnoreCase(suffixName)) {
            return true;
        } else {
            return false;
        }
    }

    // 文件不包含视频
    public static boolean suffixNameFile(String suffixName) {
        if ((".txt").equalsIgnoreCase(suffixName) || (".dwg").equalsIgnoreCase(suffixName)
                || (".jpg").equalsIgnoreCase(suffixName) || (".jpeg").equalsIgnoreCase(suffixName) || (".png").equalsIgnoreCase(suffixName)
                || (".bmp").equalsIgnoreCase(suffixName) || (".gif").equalsIgnoreCase(suffixName)
                || (".rar").equalsIgnoreCase(suffixName) || (".zip").equalsIgnoreCase(suffixName)
                || (".doc").equalsIgnoreCase(suffixName) || (".docx").equalsIgnoreCase(suffixName)
                || (".xlsx").equalsIgnoreCase(suffixName) || (".xls").equalsIgnoreCase(suffixName) || (".pdf").equalsIgnoreCase(suffixName)
                || (".ppt").equalsIgnoreCase(suffixName) || (".pps").equalsIgnoreCase(suffixName) || (".pptx").equalsIgnoreCase(suffixName)) {
            return true;
        } else {
            return false;
        }
    }

    // 文件不包含视频
    public static boolean suffixNameExcel(String suffixName) {
        if ((".xlsx").equalsIgnoreCase(suffixName) || (".xls").equalsIgnoreCase(suffixName) || (".pdf").equalsIgnoreCase(suffixName)) {
            return true;
        } else {
            return false;
        }
    }
}
