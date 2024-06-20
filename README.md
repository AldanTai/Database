
這個項目Car Sales Management System是一個基於 Java 並使用 Swing 的 GUI 的汽車銷售管理系統。
該系統可以讓用戶與汽車銷售資料庫進行互動，執行各種SQL查詢，並查看結果

目錄  
前置需求
安裝
使用
項目結構
資料庫結構
範例SQL查詢


前置需求
在開始使用本項目之前，請確保你已經安裝了以下軟體：
Java Development Kit (JDK) 8或更高版本
MariaDB

安裝
請按照以下步驟設置並運作：

1. 複製倉庫
使用以下命令複製項目到你的本機：
git clone https://github.com/yourusername/car-sales-management.git
cd car-sales-management

2.設置資料庫
運行'CreateTables'類以創建資料庫：
java -cp bin CreateTables

3.配置資料庫的連接
確保在 DatabaseConnection.java 文件中設置正確的數據庫連接詳情：

private static final String URL = "jdbc:mariadb://0.tcp.jp.ngrok.io:11051/411177012";
private static final String USER = "411177012";
private static final String PASSWORD = "411177012";

4.編譯項目
你可以使用喜歡的IDE編譯項目，或使用命令行進行編譯：
javac -d bin src/**/*.java

5. 運行應用
你可以通過 IDE 或命令行運行應用：
java -cp bin CarSalesGUI

使用
執行預定義SQL查詢
使用 GUI 上的按鈕運行預定義查詢，並在文本區域中顯示結果

執行自定義SQL查詢
在文本框中輸入自定義 SQL 查詢，然後點擊“查詢”按鈕以執行它們

查看所有列信息
點擊“全欄位資訊”按鈕，以獲取並顯示數據庫表中的所有列信息

顯示列大小
點擊“各欄位大小”按鈕，以顯示每個表中的記錄數量

項目結構
以下是項目目錄的概述：

src/
    ├── CarSalesGUI.java       // 主 GUI 類，包含查詢執行方法
    ├── CreateTables.java      // 創建數據庫結構並插入示例數據
    ├── DatabaseConnection.java // 管理數據庫連接
    └── Queries.java  // 包含 SQL 查詢方法
    
資料庫結構
包括下表：
客戶 (Customer)
經銷商 (Dealer)
品牌 (Brand)
配置 (Configuration)
供應商 (Supplier)
工廠 (Factory)
車型 (CarModel)
車輛 (Vehicle)

範例SQL 查詢
以下是一些範例的SQL查詢，展示如何從資料庫中搜索特定訊息

查詢1：找SUV 銷量最好的月份:
"SELECT MONTH(v.銷售日期) as 月份, COUNT(*) as 銷售量 "
+ "FROM 車輛 v "
+ "JOIN 車型 m ON v.車型ID = m.車型ID "
+ "WHERE m.車輛型式 = 'SUV' "
+ "GROUP BY 月份 "
+ "ORDER BY 銷售量 DESC LIMIT 1";

查詢2：查找銷售額最高的經銷商
"SELECT d.經銷商名稱, SUM(v.售價) as 總銷售額 "
+ "FROM 車輛 v "
+ "JOIN 品牌 b ON v.品牌ID = b.品牌ID "
+ "JOIN 經銷商 d ON b.經銷商ID = d.經銷商ID "
+ "WHERE v.銷售日期 >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR) "
+ "GROUP BY d.經銷商ID "
+ "ORDER BY 總銷售額 DESC LIMIT 1";

查詢3：經銷商名稱的平均庫存天數
"SELECT d.經銷商名稱, AVG(DATEDIFF(v.銷售日期, d.庫存日期)) as 平均庫存天數 "
+ "FROM 車輛 v "
+ "JOIN 品牌 b ON v.品牌ID = b.品牌ID "
+ "JOIN 經銷商 d ON b.經銷商ID = d.經銷商ID "
+ "GROUP BY d.經銷商ID "
+ "ORDER BY 平均庫存天數 DESC";
  
查詢4：品牌銷售量
"SELECT b.品牌名稱, COUNT(*) as 銷售量 "
+ "FROM 車輛 v "
+ "JOIN 品牌 b ON v.品牌ID = b.品牌ID "
+ "WHERE v.銷售日期 >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR) "
+ "GROUP BY b.品牌ID "
+ "ORDER BY 銷售量 DESC LIMIT 2";
  
查詢5：客戶名稱查詢
"SELECT v.VIN, c.客戶名稱 "
+ "FROM 車輛 v "
+ "JOIN 配置 cfg ON v.配置ID = cfg.配置ID "
+ "JOIN 供應商 s ON cfg.供應商ID = s.供應商ID "
+ "JOIN 工廠 f ON s.供應商ID = f.供應商ID "
+ "JOIN 客戶 c ON v.客戶ID = c.客戶ID "
+ "WHERE s.供應商名稱 = 'Getrag' "
+ "AND cfg.生產日期 BETWEEN '2023-01-01' AND '2023-12-31' "
+ "AND f.工廠名稱 = 'Getrag Plant 1'";


