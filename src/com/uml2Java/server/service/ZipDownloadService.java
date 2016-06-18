package com.uml2Java.server.service;

import com.uml2Java.server.GenerateCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Cristi on 6/16/2016.
 */
public class ZipDownloadService extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    String fileName = GenerateCode.SOURCE_FOLDER + req.getParameter("user") + ".zip";
    OutputStream outputStream = resp.getOutputStream();

    resp.setContentType("application/zip");
    resp.setHeader("Content-Disposition:", "attachment;filename=" + "\"app.zip\"");
    resp.setHeader("Connection:", "Close");

    File file = new File(fileName);
    FileInputStream fin = new FileInputStream(file);
    byte[] bytes = new byte[(int) file.length()];
    int length = fin.read(bytes);
    fin.close();
    outputStream.write(bytes);
    outputStream.close();

    resp.setContentLength(length);
  }
}
