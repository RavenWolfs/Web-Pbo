import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DBHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/db_racikankopi";
    private static final String USER = "bintang123";
    private static final String PASS = "Bintang123@";

    public static void saveOrder(String nama, String email, String kopi, int jumlah) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            String sql = "INSERT INTO orders (nama, email, kopi, jumlah) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nama);
            stmt.setString(2, email);
            stmt.setString(3, kopi);
            stmt.setInt(4, jumlah);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}