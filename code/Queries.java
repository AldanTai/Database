import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Queries {

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        try {
            // 連接到資料庫
            Connection connection = DatabaseConnection.getConnection();
            stmt = connection.createStatement();

            // 執行查詢
            findDefectiveTransmissions(stmt);
            findTopDealerBySales(stmt);
            findTopBrandsByUnitSales(stmt);
            findBestMonthForSUVs(stmt);
            findDealersWithLongestInventoryTime(stmt);

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

    private static void findDefectiveTransmissions(Statement stmt) throws SQLException {
    	String query = "SELECT v.VIN, c.客戶名稱 "
                + "FROM 車輛 v "
                + "JOIN 配置 cfg ON v.配置ID = cfg.配置ID "
                + "JOIN 供應商 s ON cfg.供應商ID = s.供應商ID "
                + "JOIN 工廠 f ON s.供應商ID = f.供應商ID "
                + "JOIN 客戶 c ON v.客戶ID = c.客戶ID "
                + "WHERE s.供應商名稱 = 'Getrag' "
                + "AND cfg.生產日期 BETWEEN '2023-01-01' AND '2023-12-31' "
                + "AND f.工廠名稱 = 'Getrag Plant 1'";

        ResultSet rs = stmt.executeQuery(query);
        boolean hasResults = false;
        while (rs.next()) {
            hasResults = true;
            System.out.println("VIN: " + rs.getString("VIN") + ", 客戶: " + rs.getString("客戶名稱"));
        }
        if (!hasResults) {
            System.out.println("沒有找到符合條件的車輛和客戶。");
        }
    }

    private static void findTopDealerBySales(Statement stmt) throws SQLException {
    	String query = "SELECT d.經銷商名稱, SUM(v.售價) as 總銷售額 "
                + "FROM 車輛 v "
                + "JOIN 品牌 b ON v.品牌ID = b.品牌ID "
                + "JOIN 經銷商 d ON b.經銷商ID = d.經銷商ID "
                + "WHERE v.銷售日期 >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR) "
                + "GROUP BY d.經銷商ID "
                + "ORDER BY 總銷售額 DESC LIMIT 1";

        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            System.out.println("銷售額最高的經銷商: " + rs.getString("經銷商名稱") + ", 總銷售額: " + rs.getDouble("總銷售額"));
        } else {
            System.out.println("沒有找到符合條件的經銷商。");
        }
    }

    private static void findTopBrandsByUnitSales(Statement stmt) throws SQLException {
    	String query = "SELECT b.品牌名稱, COUNT(*) as 銷售量 "
                + "FROM 車輛 v "
                + "JOIN 品牌 b ON v.品牌ID = b.品牌ID "
                + "WHERE v.銷售日期 >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR) "
                + "GROUP BY b.品牌ID "
                + "ORDER BY 銷售量 DESC LIMIT 2";

        ResultSet rs = stmt.executeQuery(query);
        boolean hasResults = false;
        while (rs.next()) {
            hasResults = true;
            System.out.println("品牌: " + rs.getString("品牌名稱") + ", 銷售量: " + rs.getInt("銷售量"));
        }
        if (!hasResults) {
            System.out.println("沒有找到符合條件的品牌。");
        }
    }

    private static void findBestMonthForSUVs(Statement stmt) throws SQLException {
    	String query = "SELECT MONTH(v.銷售日期) as 月份, COUNT(*) as 銷售量 "
                + "FROM 車輛 v "
                + "JOIN 車型 m ON v.車型ID = m.車型ID "
                + "WHERE m.車輛型式 = 'SUV' "
                + "GROUP BY 月份 "
                + "ORDER BY 銷售量 DESC LIMIT 1";

        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            System.out.println("SUV 銷量最好的月份: " + rs.getInt("月份") + "月, 銷售量: " + rs.getInt("銷售量"));
        } else {
            System.out.println("沒有找到符合條件的月份。");
        }
    }

    private static void findDealersWithLongestInventoryTime(Statement stmt) throws SQLException {
    	String query = "SELECT d.經銷商名稱, AVG(DATEDIFF(v.銷售日期, d.庫存日期)) as 平均庫存天數 "
                + "FROM 車輛 v "
                + "JOIN 品牌 b ON v.品牌ID = b.品牌ID "
                + "JOIN 經銷商 d ON b.經銷商ID = d.經銷商ID "
                + "GROUP BY d.經銷商ID "
                + "ORDER BY 平均庫存天數 DESC";

        ResultSet rs = stmt.executeQuery(query);
        boolean hasResults = false;
        while (rs.next()) {
            hasResults = true;
            System.out.println("經銷商: " + rs.getString("經銷商名稱") + ", 平均庫存天數: " + rs.getInt("平均庫存天數"));
        }
        if (!hasResults) {
            System.out.println("沒有找到符合條件的經銷商。");
        }
    }
}
