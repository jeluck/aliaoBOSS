package com.ssctrl.interface4;

import com.hz.vliao1.mA.vliaoInOutFace;
import com.hz.vliao1.mA.vliaoInoutBoss_01150;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/uiface/ExportEnrollExamine")
public class ExportAchieveExamineServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ExcelUtils excelUtils;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{


    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        JyLogDetect log = new JyLogDetect(request);
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
//        try {
//            String[] arg = null;
//            List<List<String>> listForExport = new ArrayList<List<String>>();
//            vliaoInOutFace inOutFace = new vliaoInoutBoss_01150(arg, request, response);
//            inOutFace.searchface();
//            excelUtils.export("xx", new ArrayList<>(), request, response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }


}
