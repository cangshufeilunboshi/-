package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class HelloController {
    @RequestMapping("/hello")
    public JSON hello(@RequestParam String username,
                      @RequestParam String password) throws SQLException, ClassNotFoundException {
        String sqls = "SELECT * FROM users where username="+username+" and "+"password="+password;
        JSONObject object = new JSONObject();
        if (sql(sqls)==1){
            object.put("id",1);
            object.put("can",sqlcan(sqls));
            object.put("text",find());
            return object;
        }
        else {
            object.put("id",0);
            object.put("can",0);
            object.put("text","密码错误！");
            return object;
        }
    }
    @RequestMapping("/getops")
    public JSON getops(@RequestParam String username,@RequestParam int id,@RequestParam int num) throws SQLException, ClassNotFoundException {
        String sqls = "SELECT * FROM options where username="+username+" and "+"voteid="+id;
        JSONObject object = new JSONObject();
        if (sql(sqls)==1){
            object.put("id",1);

            object.put("text",finds(id,num));
            return object;
        }
        else {
            object.put("id",0);

            object.put("text",finds(id,num));
            return object;
        }
    }
    @RequestMapping("/getnews")
    public JSON getnews() throws SQLException, ClassNotFoundException {
        JSONObject object = new JSONObject();
        String url = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=utf8&useSSL=true";
        String username = "root";
        String password = "123456";
        //3.连接成功，数据库对象 Connection
        Connection connection = DriverManager.getConnection(url, username, password);
        //4.执行SQL对象Statement，执行SQL的对象
        Statement statement = connection.createStatement();
        //5.执行SQL的对象去执行SQL，返回结果集
        //String sql = "SELECT *FROM studentinfo;";
        //String sqls = "SELECT * FROM users where username=2019211909127 and password=288718";
        String sql="select * from text where can=2";
        ResultSet resultSet = statement.executeQuery(sql);
        int i=0;
        //JSONObject [] arrys = new JSONObject [4];
        List<JSONObject> arrys = new ArrayList<>();
        while (resultSet.next()) {
            i++;
            JSONObject objects = new JSONObject();
            objects.put("id",resultSet.getInt("id"));
            objects.put("username",resultSet.getString("userid"));
            objects.put("title",resultSet.getString("title"));
            objects.put("text",resultSet.getString("text"));
            objects.put("img",resultSet.getString("img"));
            objects.put("up",resultSet.getString("up"));
            //object.put("obj"+i,objects);
            arrys.add(objects);
        }
        object.put("id",1);

        object.put("obj",arrys);
        return object;
    }

    @RequestMapping("/getvotes")
    public JSON getvotes() throws SQLException, ClassNotFoundException {
        JSONObject object = new JSONObject();
        String url = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=utf8&useSSL=true";
        String username = "root";
        String password = "123456";
        //3.连接成功，数据库对象 Connection
        Connection connection = DriverManager.getConnection(url, username, password);
        //4.执行SQL对象Statement，执行SQL的对象
        Statement statement = connection.createStatement();
        //5.执行SQL的对象去执行SQL，返回结果集
        //String sql = "SELECT *FROM studentinfo;";
        //String sqls = "SELECT * FROM users where username=2019211909127 and password=288718";
        String sql="select * from vote where can=1";
        ResultSet resultSet = statement.executeQuery(sql);
        int i=0;
        //JSONObject [] arrys = new JSONObject [4];
        List<JSONObject> arrys = new ArrayList<>();
        while (resultSet.next()) {
            i++;
            JSONObject objects = new JSONObject();
            objects.put("id",resultSet.getInt("id"));
            objects.put("username",resultSet.getString("username"));
            objects.put("title",resultSet.getString("title"));
            objects.put("text",resultSet.getString("text"));
            //object.put("obj"+i,objects);
            arrys.add(objects);
        }
        object.put("id",1);

        object.put("obj",arrys);
        return object;
    }
    @RequestMapping("/getcom")
    public JSON getcom(@RequestParam int id) throws SQLException, ClassNotFoundException {
        String sqls = "SELECT * FROM comment where artid="+id;
        JSONObject object = new JSONObject();
        String url = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=utf8&useSSL=true";
        String username = "root";
        String password = "123456";
        //3.连接成功，数据库对象 Connection
        Connection connection = DriverManager.getConnection(url, username, password);
        //4.执行SQL对象Statement，执行SQL的对象
        Statement statement = connection.createStatement();
        //5.执行SQL的对象去执行SQL，返回结果集
        //String sql = "SELECT *FROM studentinfo;";
        //String sqls = "SELECT * FROM users where username=2019211909127 and password=288718";
        //String sql="select * from text where can=1";
        ResultSet resultSet = statement.executeQuery(sqls);
        int i=0;
        //JSONObject [] arrys = new JSONObject [4];
        List<JSONObject> arrys = new ArrayList<>();
        while (resultSet.next()) {
            i++;
            JSONObject objects = new JSONObject();
            objects.put("username",resultSet.getString("username"));
            //objects.put("title",resultSet.getString("title"));
            objects.put("text",resultSet.getString("text"));
            //objects.put("img",resultSet.getString("img"));
            //objects.put("up",resultSet.getString("up"));
            //object.put("obj"+i,objects);
            arrys.add(objects);
        }
        object.put("obj",arrys);
        return object;
    }
    @RequestMapping("/up")
    public JSON up(@RequestParam int id) throws SQLException, ClassNotFoundException {
        String sqls = "UPDATE text SET up = up + 1  WHERE id = "+id;
        JSONObject object = new JSONObject();
        if (sqlinsert(sqls)==1){
            object.put("id",1);
            object.put("text","点赞完成");
            return object;
        }
        else {
            object.put("id",0);
            object.put("text","点赞失败");
            return object;
        }
    }
    @RequestMapping("/delete")
    public JSON delete(@RequestParam int id) throws SQLException, ClassNotFoundException {
        String sqls = "UPDATE text SET can = 0  WHERE id = "+id;
        JSONObject object = new JSONObject();
        if (sqlinsert(sqls)==1){
            object.put("id",1);
            object.put("text","删除完成");
            return object;
        }
        else {
            object.put("id",0);
            object.put("text","删除失败");
            return object;
        }
    }
    @RequestMapping("/zhuce")
    public JSON zhuce(@RequestParam String username,
                      @RequestParam String password,
                      @RequestParam int can) throws SQLException, ClassNotFoundException {
        //System.out.println(username+password+can);
        String sqls = "SELECT * FROM users where username="+username;
        JSONObject object = new JSONObject();
        if (sql(sqls)==1){
            object.put("id",1);
            object.put("text","当前账号已存在");
            return object;
        }
        else {
            object.put("id",0);
            String sqlss = "insert into users(username,password,can) values("+username+","+password+","+can+");";
            if (sqlinsert(sqlss)==1) {
                object.put("text", "注册成功");
                return object;
            }
            else {
                object.put("text", "注册失败");
                return object;
            }
        }
    }

    @RequestMapping("/inseop")
    public JSON inseop(@RequestParam String username,
                      @RequestParam int id,
                      @RequestParam int voteop) throws SQLException, ClassNotFoundException {
        //System.out.println(username+password+can);
        String sqlss = "insert into options(username,voteid,voteop) values("+username+","+id+",'"+voteop+"');";
        JSONObject object = new JSONObject();
        if (sqlinsert(sqlss)==1) {
            object.put("id",1);
            object.put("text", "投票成功");
            return object;
        }
        else {
            object.put("id",0);
            object.put("text", "投票失败");
            return object;
        }
    }
    @RequestMapping("/insertcom")
    public JSON insertcom(@RequestParam String username,@RequestParam String text,
                      @RequestParam int id) throws SQLException, ClassNotFoundException {
        //System.out.println(username+password+can);
        String sqlss = "insert into comment(username,artid,text) values("+username+","+id+",'"+text+"');";
        JSONObject object = new JSONObject();
        if (sqlinsert(sqlss)==1) {
            object.put("id",1);
            object.put("text", "评论成功");
            return object;
        }
        else {
            object.put("id",0);
            object.put("text", "评论失败");
            return object;
        }
    }
    @RequestMapping("/mmxg")
    public JSON mmxg(@RequestParam String username,
                      @RequestParam String password,
                     @RequestParam String newpassword) throws SQLException, ClassNotFoundException {
        //System.out.println(username+password+newpassword);
        String sqls = "SELECT * FROM users where username="+username+" and password="+ password;
        JSONObject object = new JSONObject();
        String sqlss = "UPDATE users SET password = "+newpassword+" WHERE username = "+username;
        if (sql(sqls)==1){
            if(sqlinsert(sqlss)==1){
                object.put("id",1);
                object.put("text", "修改成功");
                return object;
            }
            else{
                object.put("id",1);
                object.put("text", "修改失败");
                return object;
            }
//            object.put("id",1);
//            object.put("text","当前账号已存在");
//            return object;
        }
        else {
            object.put("id",0);
                            object.put("text", "密码错误");
                return object;
//            String sqlss = "insert into users(username,password,can) values('2019211909125',123456,'1');";
//            if (sqlinsert(sqlss)==1) {
//                object.put("text", "注册成功");
//                return object;
//            }
//            else {
//                object.put("text", "注册失败");
//                return object;
//            }
        }
    }

    @RequestMapping("/newart")
    public JSON newart(@RequestParam String title,
                     @RequestParam String text,
                     @RequestParam String img,
                       @RequestParam String username,@RequestParam int can) throws SQLException, ClassNotFoundException {

        //System.out.println(username+password+newpassword);
       String sqls = "insert into text(title,text,img,userid,up,can) values('"+title+"','"+text+"','"+img+"','"+username+"',0,"+can+")";
        JSONObject object = new JSONObject();
        if(sqlinsert(sqls)==1) {
            object.put("id",1);
            return object;
        }
        else{

            object.put("id",0);
            return object;
        }
        }

    @RequestMapping("/newvote")
    public JSON newvote(@RequestParam String title,
                       @RequestParam String text,
                       @RequestParam String username) throws SQLException, ClassNotFoundException {

        //System.out.println(username+password+newpassword);
        String sqls = "insert into vote(title,text,username,can) values('"+title+"','"+text+"','"+username+"',1)";
        JSONObject object = new JSONObject();
        if(sqlinsert(sqls)==1) {
            object.put("id",1);
            return object;
        }
        else{

            object.put("id",0);
            return object;
        }
    }
    @RequestMapping("/upart")
    public JSON upart(@RequestParam int id,@RequestParam String title,
                       @RequestParam String text,
                       @RequestParam String img
                      ) throws SQLException, ClassNotFoundException {

        //System.out.println(username+password+newpassword);
        String sqls = "UPDATE text SET title = '"+title+ "', text='"+text+ "', img='"+img+"' where id="+id;
        JSONObject object = new JSONObject();
        if(sqlinsert(sqls)==1) {
            object.put("id",1);
            return object;
        }
        else{

            object.put("id",0);
            return object;
        }
    }


    @RequestMapping("/upload")
    public String upload(@RequestParam("upfile") MultipartFile file) {
        //图片校验（图片是否为空,图片大小，上传的是不是图片、图片类型（例如只能上传png）等等）
        if (file.isEmpty()) {
            return "图片上传失败";
        }
        //可以自己加一点校验 例如上传的是不是图片或者上传的文件是不是png格式等等 这里省略
        //获取原来的文件名和后缀
        String originalFilename = file.getOriginalFilename();
//        String ext = "." + FilenameUtils.getExtension(orgFileName); --需要导依赖
        String ext = "."+ originalFilename.split("\\.")[1];
        //生成一个新的文件名（以防有重复的名字存在导致被覆盖）
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //拼接图片上传的路径 url+图片名
        String newName = "C:/Users/admin/Desktop/文件/毕业设计/代码/my-project/src/img/"+uuid + ext;

//        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
//        String pre = applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath()
//                + "\\src\\main\\resources\\static\\images\\";
//        String path = pre + newName;
        try {
            file.transferTo(new File(newName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uuid + ext;
    }

    public int sqlinsert(String sql) throws SQLException, ClassNotFoundException {
        //1.加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=utf8&useSSL=true";
        String username = "root";
        String password = "123456";
        //3.连接成功，数据库对象 Connection
        Connection connection = DriverManager.getConnection(url, username, password);
        //4.执行SQL对象Statement，执行SQL的对象
        Statement statement = connection.createStatement();
        //5.执行SQL的对象去执行SQL，返回结果集
        //String sql = "SELECT *FROM studentinfo;";
        //String sqls = "SELECT * FROM users where username=2019211909127 and password=288718";

            if (statement.executeUpdate(sql)==1) {
                return 1;
            } else {
                return 0;
            }

    }

    public JSON find() throws SQLException {
        JSONObject object = new JSONObject();
        String url = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=utf8&useSSL=true";
        String username = "root";
        String password = "123456";
        //3.连接成功，数据库对象 Connection
        Connection connection = DriverManager.getConnection(url, username, password);
        //4.执行SQL对象Statement，执行SQL的对象
        Statement statement = connection.createStatement();
        //5.执行SQL的对象去执行SQL，返回结果集
        //String sql = "SELECT *FROM studentinfo;";
        //String sqls = "SELECT * FROM users where username=2019211909127 and password=288718";
        String sql="select * from text where can=1";
        ResultSet resultSet = statement.executeQuery(sql);
        int i=0;
        //JSONObject [] arrys = new JSONObject [4];
        List<JSONObject> arrys = new ArrayList<>();
        while (resultSet.next()) {
            i++;
            JSONObject objects = new JSONObject();
            objects.put("id",resultSet.getInt("id"));
            objects.put("username",resultSet.getString("userid"));
            objects.put("title",resultSet.getString("title"));
            objects.put("text",resultSet.getString("text"));
            objects.put("img",resultSet.getString("img"));
            objects.put("up",resultSet.getString("up"));
            //object.put("obj"+i,objects);
            arrys.add(objects);
        }
        object.put("obj",arrys);
        return object;
    }
    public JSON finds(int id,int num) throws SQLException {
        JSONObject object = new JSONObject();
        String url = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=utf8&useSSL=true";
        String username = "root";
        String password = "123456";
        //3.连接成功，数据库对象 Connection
        Connection connection = DriverManager.getConnection(url, username, password);
        //4.执行SQL对象Statement，执行SQL的对象
        Statement statement = connection.createStatement();
        //5.执行SQL的对象去执行SQL，返回结果集
        //String sql = "SELECT *FROM studentinfo;";
        //String sqls = "SELECT * FROM users where username=2019211909127 and password=288718";
        List<JSONObject> arrys = new ArrayList<>();
        for(int i=1;i<=num;i++){
            String sql="select * from options where voteid="+id+" and voteop="+i;
            ResultSet resultSet = statement.executeQuery(sql);

            int ii=0;
            while (resultSet.next()) {
                ii++;
//                JSONObject objects = new JSONObject();
//                objects.put("id",resultSet.getInt("id"));
//                objects.put("username",resultSet.getString("userid"));
//                objects.put("title",resultSet.getString("title"));
//                objects.put("text",resultSet.getString("text"));
//                objects.put("img",resultSet.getString("img"));
//                objects.put("up",resultSet.getString("up"));
//                //object.put("obj"+i,objects);
//                arrys.add(objects);
            }
            JSONObject objects = new JSONObject();
            objects.put("name",i);
            objects.put("value",ii);
            arrys.add(objects);
        }
        object.put("obj",arrys);
        return object;
    }
    public int sql(String sql) throws SQLException, ClassNotFoundException {
        //1.加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=utf8&useSSL=true";
        String username = "root";
        String password = "123456";
        //3.连接成功，数据库对象 Connection
        Connection connection = DriverManager.getConnection(url, username, password);
        //4.执行SQL对象Statement，执行SQL的对象
        Statement statement = connection.createStatement();
        //5.执行SQL的对象去执行SQL，返回结果集
        //String sql = "SELECT *FROM studentinfo;";
        //String sqls = "SELECT * FROM users where username=2019211909127 and password=288718";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            if (resultSet.getRow()==1) {
                return 1;
            } else {
                return 0;
            }
        }
        return 0;
    }
    public int sqlcan(String sql) throws SQLException, ClassNotFoundException {
        //1.加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=utf8&useSSL=true";
        String username = "root";
        String password = "123456";
        //3.连接成功，数据库对象 Connection
        Connection connection = DriverManager.getConnection(url, username, password);
        //4.执行SQL对象Statement，执行SQL的对象
        Statement statement = connection.createStatement();
        //5.执行SQL的对象去执行SQL，返回结果集
        //String sql = "SELECT *FROM studentinfo;";
        //String sqls = "SELECT * FROM users where username=2019211909127 and password=288718";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            return resultSet.getInt("can");
        }
        return 0;
    }
}