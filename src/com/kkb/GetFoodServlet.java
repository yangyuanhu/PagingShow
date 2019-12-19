package com.kkb;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetFoodServlet extends javax.servlet.http.HttpServlet {
    //默认条数
    private int count = 3;
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        int page,count;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        }catch (Exception e){
            page = 1;
        }
        try{
            count = Integer.parseInt(request.getParameter("count"));
        }catch (Exception e){
            count = this.count;
        }
        try {
            FoodService foodService = new FoodService();
            ArrayList<Food> foods = foodService.getFood(page,count);
            int ttPage = foodService.getttPage(count);

            request.setAttribute("foods",foods);
            request.setAttribute("page",page);
            request.setAttribute("ttPage",ttPage);
            request.getRequestDispatcher("index.jsp").forward(request,response);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
