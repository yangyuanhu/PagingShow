package com.kkb;

import java.sql.SQLException;
import java.util.ArrayList;

public class FoodService {
    private FoodDao dao;

    public FoodService() throws ClassNotFoundException {
        dao = new FoodDao();
    }

    public ArrayList<Food> getFood(int page, int count) throws SQLException {
        if (page <= 0){
            return null;
        }
        return dao.getFoodByLimit(page,count);
    }

    public int getttPage(int count) throws SQLException {
        int rowCount = dao.getFoodRowCount();
        if (rowCount % count == 0){
            return rowCount / count;
        }else {
            return rowCount / count + 1;
        }
    }
}
