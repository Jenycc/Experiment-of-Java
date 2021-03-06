package cn.edu.sicnu.cs.yuxin.exp8.infosytem;

import java.sql.*;
import java.util.ArrayList;

public class SchoolDatabase {
    private String URL = "jdbc:mysql://";
    private String HOST = "119.23.61.148";
    private String PORT = "3306";
    private String DATABASE = "minecraft";
    private final static String DRIVER = "com.mysql.jdbc.Driver";
    private String USER = "minecraft";
    private String PASSWORD = "Woshiyuxin123.";
    private Connection connection = null;

    SchoolDatabase() {
        URL = URL + HOST + ":" + PORT + "/" + DATABASE;
    }

    SchoolDatabase(String HOST, String DATABASE, String PORT, String USER, String PASSWORD) {
        this.HOST = HOST;
        this.DATABASE = DATABASE;
        this.PORT = PORT;
        this.USER = USER;
        this.PASSWORD = PASSWORD;
        this.URL = URL + HOST + ":" + PORT + "/" + DATABASE;
    }

    public boolean connect() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insert(School school) {
        String sql = "insert into school(idCode, name, state) values('%s','%s','%d');";
        PreparedStatement preparedStatement;
        try {
            sql = String.format(sql, String.valueOf(school.getIdentificationCode()), school.getName(), school.getState());
            preparedStatement = connection.prepareStatement(sql);
            int rows = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean update(School school) {
        String sql = String.format("update school set name = '%s' , state = '%d' where idCode = '%s';", school.getName(), school.getState(), String.valueOf(school.getIdentificationCode()));
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            int rows = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public School select(char[] idCode) {
        String sql = String.format("select * from school where idCode = '%s';", String.valueOf(idCode));
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                School school = new School(resultSet.getString(1).toCharArray(), resultSet.getString(2), Integer.valueOf(resultSet.getString(3)));
                preparedStatement.close();
                return school;
            } else {
                preparedStatement.close();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<School> getAll() {
        String sql = "select * from school;";
        PreparedStatement preparedStatement;
        ArrayList<School> schools = new ArrayList<School>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                schools.add(new School(resultSet.getString(1).toCharArray(), resultSet.getString(2), Integer.valueOf(resultSet.getString(3))));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schools;
    }

    public boolean delete(char[] idCode) {
        String sql = String.format("delete from school where idcode = '%s';", String.valueOf(idCode));
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            int rows = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        SchoolDatabase database = new SchoolDatabase();
        if (database.connect()) {
            System.out.println("连接成功！");
            database.close();
        } else {
            System.out.println("连接失败！");
        }
    }
}
