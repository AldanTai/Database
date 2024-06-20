import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTables {

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        try {
            // 連接到資料庫
            Connection connection = DatabaseConnection.getConnection();
            System.out.println("成功連接資料庫！");
            stmt = connection.createStatement();

            // 創建資料庫和表
            String sql;

            sql = "CREATE DATABASE IF NOT EXISTS car_sales";
            System.out.println("資料庫創建成功...");
            
            sql = "CREATE TABLE IF NOT EXISTS 客戶 ("
                    + "客戶ID INT PRIMARY KEY,"
                    + "客戶名稱 VARCHAR(100),"
                    + "地址 VARCHAR(200),"
                    + "電話 VARCHAR(20),"
                    + "性別 CHAR(1),"
                    + "年收入 DECIMAL(10, 2),"
                    + "個人或公司 VARCHAR(50)"
                    + ")";
            stmt.executeUpdate(sql);
            System.out.println("客戶表創建成功...");

            sql = "CREATE TABLE IF NOT EXISTS 經銷商 ("
                    + "經銷商ID INT PRIMARY KEY,"
                    + "經銷商名稱 VARCHAR(100),"
                    + "地址 VARCHAR(200),"
                    + "電話 VARCHAR(20),"
                    + "拒絕服務的品牌 VARCHAR(100),"
                    + "營銷結算記錄 TEXT,"
                    + "自有品牌 VARCHAR(100),"
                    + "客戶ID INT,"
                    + "庫存日期 DATE,"
                    + "FOREIGN KEY (客戶ID) REFERENCES 客戶(客戶ID)"
                    + ")";
            stmt.executeUpdate(sql);
            System.out.println("經銷商表創建成功...");

            sql = "CREATE TABLE IF NOT EXISTS 品牌 ("
                    + "品牌ID INT PRIMARY KEY,"
                    + "品牌名稱 VARCHAR(100),"
                    + "國家 VARCHAR(50),"
                    + "製造商名稱 VARCHAR(100),"
                    + "經銷商ID INT,"
                    + "FOREIGN KEY (經銷商ID) REFERENCES 經銷商(經銷商ID)"
                    + ")";
            stmt.executeUpdate(sql);
            System.out.println("品牌表創建成功...");
            
            sql = "CREATE TABLE IF NOT EXISTS 供應商 ("
                    + "供應商ID INT PRIMARY KEY,"
                    + "供應商名稱 VARCHAR(100),"
                    + "供應的零件 VARCHAR(100)"
                    + ")";
            stmt.executeUpdate(sql);
            System.out.println("供應商表創建成功...");

            sql = "CREATE TABLE IF NOT EXISTS 配置 ("
                    + "配置ID INT PRIMARY KEY,"
                    + "顏色 VARCHAR(50),"
                    + "引擎 VARCHAR(100),"
                    + "變速箱 VARCHAR(100),"
                    + "底盤 VARCHAR(100),"
                    + "剎車系統 VARCHAR(100),"
                    + "轉向系統 VARCHAR(100),"
                    + "懸吊系統 VARCHAR(100),"
                    + "電子控制器 VARCHAR(100),"
                    + "供應商ID INT,"
                    + "生產日期 DATE,"
                    + "FOREIGN KEY (供應商ID) REFERENCES 供應商(供應商ID)"
                    + ")";
            stmt.executeUpdate(sql);
            System.out.println("配置表創建成功...");
            
            

            sql = "CREATE TABLE IF NOT EXISTS 工廠 ("
                    + "工廠ID INT PRIMARY KEY,"
                    + "工廠名稱 VARCHAR(100),"
                    + "生產的零件 VARCHAR(200),"
                    + "工廠類型 VARCHAR(50),"
                    + "供應商ID INT,"
                    + "FOREIGN KEY (供應商ID) REFERENCES 供應商(供應商ID)"
                    + ")";
            stmt.executeUpdate(sql);
            System.out.println("工廠表創建成功...");

            sql = "CREATE TABLE IF NOT EXISTS 車型 ("
                    + "車型ID INT PRIMARY KEY,"
                    + "品牌ID INT,"
                    + "車型名稱 VARCHAR(100),"
                    + "車輛型式 VARCHAR(50),"
                    + "供應商ID INT,"
                    + "工廠ID INT,"
                    + "配置ID INT,"
                    + "FOREIGN KEY (品牌ID) REFERENCES 品牌(品牌ID),"
                    + "FOREIGN KEY (供應商ID) REFERENCES 供應商(供應商ID),"
                    + "FOREIGN KEY (工廠ID) REFERENCES 工廠(工廠ID)"
                    + ")";
            stmt.executeUpdate(sql);
            System.out.println("車型表創建成功...");

            sql = "CREATE TABLE IF NOT EXISTS 車輛 ("
                    + "VIN VARCHAR(20) PRIMARY KEY,"
                    + "品牌ID INT,"
                    + "車型ID INT,"
                    + "配置ID INT,"
                    + "客戶ID INT,"
                    + "售價 INT,"
                    + "銷售日期 DATE,"
                    + "FOREIGN KEY (品牌ID) REFERENCES 品牌(品牌ID),"
                    + "FOREIGN KEY (車型ID) REFERENCES 車型(車型ID),"
                    + "FOREIGN KEY (配置ID) REFERENCES 配置(配置ID),"
                    + "FOREIGN KEY (客戶ID) REFERENCES 客戶(客戶ID)"
                    + ")";
            stmt.executeUpdate(sql);
            System.out.println("車輛表創建成功...");

            // 插入範例數據
            insertSampleData(stmt);

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    private static void insertSampleData(Statement stmt) throws SQLException {
        String sql;

        sql = "INSERT INTO 客戶 VALUES (1, '張三', '台北市信義區', '0912345678', '男', 1000000, '個人')";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO 客戶 VALUES (2, 'Jane Smith', '高雄市前鎮區', '0987654321', '女', 1200000, '公司')";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO 經銷商 VALUES (1, '遠大汽車', '台北市內湖區', '02-12345678', NULL, '年銷售額超過1億', 'Lexus', 1, '2023-01-15')";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO 經銷商 VALUES (2, '東風汽車', '台中市西屯區', '04-87654321', 'BMW', '年銷售額超過8千萬', NULL, 2, '2023-02-01')";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO 品牌 VALUES (1, 'Toyota', 'Japan', 'Toyota Motor Corporation', 1)";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO 品牌 VALUES (2, 'Honda', 'Japan', 'Honda Motor Co., Ltd.', 2)";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO 供應商 VALUES (1, 'Denso', '電子系統')";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO 供應商 VALUES (2, 'Bosch', '剎車系統')";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO 供應商 VALUES (3, 'Getrag', '變速箱')";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO 配置 VALUES (1, '銀色', 'V6', 'Getrag 6速自動', '高剛性', 'Bosch', 'EPAS', '獨立懸架', 'ECU-v1', 3, '2023-06-15')";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO 配置 VALUES (2, '白色', 'I4', 'Getrag 8速自動', '標準', 'Bosch', 'EPAS', '麥弗遜', 'ECU-v2', 3, '2023-07-20')";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO 工廠 VALUES (1, 'Toyota Tsutsumi', '車身', '整車製造',1)";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO 工廠 VALUES (2, 'BMW Dingolfing', '車身', '整車製造',2)";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO 工廠 VALUES (3, 'Getrag Plant 1', '變速箱', '零件製造',3)";
        stmt.executeUpdate(sql);
        

        sql = "INSERT INTO 車型 VALUES (1, 1, 'Camry', '四門', 1, 1, 1)";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO 車型 VALUES (2, 1, 'RAV4', 'SUV', 2, 2, 2)";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO 車型 VALUES (3, 2, 'X5', 'SUV', 3, 3, 3)";
        stmt.executeUpdate(sql);
        

        sql = "INSERT INTO 車輛 VALUES ('1G1ZD5ST3JF129842', 1, 1, 1, 1, 800000, '2023-12-15')";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO 車輛 VALUES ('5UXWX7C5XL0S84675', 2, 3, 2, 2, 1200000, '2023-12-20')";
        stmt.executeUpdate(sql);
    }
}
