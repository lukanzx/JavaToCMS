package com.example.servlet.imagesServlet;

import com.example.entity.Images;
import com.example.config.ConfigReader;
import com.example.service.ImagesService;
import com.example.service.impl.ImagesServiceImpl;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "FileUploadServlet", urlPatterns = {"/FileUploadServlet"})
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    private final static Logger LOGGER = Logger.getLogger(FileUploadServlet.class.getCanonicalName());
    //    private static final String SAVE_DIR = "C:\\Users\\ax1-use-update\\src\\main\\webapp\\images\\update";
    private static final String SAVE_DIR = ConfigReader.getUploadImagePath();
    private final ImagesService imagesService = new ImagesServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "File upload servlet";
    }

    private String getFileName(final Part part) {
        try {
            final String partHeader = part.getHeader("content-disposition");
            LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
            for (String content : part.getHeader("content-disposition").split(";")) {
                if (content.trim().startsWith("filename")) {
                    String fileName = content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
                    return fileName;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/html;charset=UTF-8");

        Part filePart = null;
        try {
            filePart = request.getPart("file");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String fileName = null;
        try {
            fileName = getFileName(filePart);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OutputStream out = null;
        InputStream filecontent = null;
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            File saveDir = new File(SAVE_DIR);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }

            out = new FileOutputStream(new File(SAVE_DIR + File.separator + fileName));
            filecontent = filePart.getInputStream();

            int read;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            // --------------------------------------------------------
            // 在文件保存逻辑后添加
            File uploadedFile = new File(SAVE_DIR + File.separator + fileName);

            // 文件大小，单位为字节
            long fileSize = uploadedFile.length();

            // 文件类型，简单通过扩展名判断
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);

            BufferedImage image = ImageIO.read(uploadedFile);
            int width = image.getWidth();
            int height = image.getHeight();

            // 文件尺寸
            String dimensions = width + "x" + height;

            // 打印信息
//            System.out.println("文件名称: " + fileName);
//            System.out.println("文件大小: " + fileSize + " 字节");
//            System.out.println("文件尺寸: " + dimensions);
//            System.out.println("文件类型: " + fileType);
            // --------------------------------------------------------
            writer.println("<!DOCTYPE html>");
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<title>Upload Successful</title>");
            writer.println("<meta charset='UTF-8'>");
            writer.println("<style>");
            writer.println("body { font-family: Arial, sans-serif; margin: 40px; }");
            writer.println("h1 { color: green; }");
            writer.println(".file-info { margin-top: 20px; }");
            writer.println("</style>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<h1>File Upload Successful!</h1>");
            writer.println("<p>Your file <strong>" + fileName + "</strong> has been successfully uploaded.</p>");
            writer.println("<div class='file-info'>");
            writer.println("<p>Location: " + SAVE_DIR + File.separator + fileName + "</p>");
            writer.println("<p>Size: " + fileSize + " bytes</p>");
            writer.println("<p>Dimensions: " + dimensions + "</p>");
            writer.println("<p>Type: " + fileType + "</p>");
            writer.println("</div>");
            writer.println("</body>");
            writer.println("</html>");
            // --------------------------------------------------------
            // 保存数据到数据库
            Images images = new Images();
            images.setImageUrl("../../../images/my/" + fileName);
            images.setImageName(fileName);
            images.setImageSize((int) fileSize);
            images.setDimensions(dimensions);
            images.setImageStatus(true);
            images.setImageType(fileType);
            images.setUploaderId(10000);
            imagesService.addImage(images);
            // --------------------------------------------------------
            LOGGER.log(Level.INFO, "File{0} being uploaded to {1}", new Object[]{fileName, SAVE_DIR});
            // --------------------------------------------------------
        } catch (FileNotFoundException fne) {
            writer.println("You either did not specify a file to upload or are trying to upload a file to a protected or nonexistent location.");
            writer.println("<br/> ERROR: " + fne.getMessage());
            LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}", new Object[]{fne.getMessage()});
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (filecontent != null) {
                try {
                    filecontent.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (writer != null) {
                writer.close();
            }
        }
    }
}
