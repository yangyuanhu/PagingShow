package com.kkb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FoodDao {
    private DBTool tool;
    public FoodDao() throws ClassNotFoundException {
        tool = new DBTool();
    }

    public ArrayList<Food> getFoodByLimit(int page, int rowCount) throws SQLException {
        ArrayList<Food> foods = new ArrayList<>();
        int start = (page - 1) * rowCount;
        String sql = "select *from food limit ?,?";
        List<Map<String, Object>> maps = tool.executeQuery(sql, start, rowCount);
        for (Map<String,Object> m :maps) {
            Food f = new Food();
            f.setId((Integer) m.get("id"));
            f.setName((String) m.get("name"));
            f.setPrice((float) m.get("price"));
            f.setUnit((String) m.get("unit"));
            foods.add(f);
        }
        return foods;
    }

    public int getFoodRowCount() throws SQLException {
        List<Map<String, Object>> maps = tool.executeQuery("select count(*) from food");

        return Long.valueOf(maps.get(0).get("count(*)").toString()).intValue();
    }
}
