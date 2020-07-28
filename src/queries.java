import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class queries {

    private static String fileName = "autosDB.sqlite";
    private static String url = "jdbc:sqlite:" + fileName;


    private static int getIndex(String table) {
        int index = 0;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Creating index...");
                PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM " + table + ";");
                ResultSet rs = stmt.executeQuery();
                index = rs.getInt("COUNT(*)");
                return index;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } return index;
    }

    public static void checkConnection() {

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connected");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addAccident(String date, String City, String State) {

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {

                System.out.println("Inserting records into the table...");

                int aid = getIndex("accidents") + 1;
                String str_aid = Integer.toString(aid);
                System.out.println(str_aid);

                PreparedStatement stmt = conn.prepareStatement("INSERT INTO accidents ('aid', 'accident_date', 'City', 'State') VALUES (?, ?,?,?)");
                stmt.setString(1, str_aid);
                stmt.setString(2, date);
                stmt.setString(3, City);
                stmt.setString(4, State);

                int affectedRows = stmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addVehicle(String vin, float damages, String driver_ssn){

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Inserting records into the table...");

                int aid = getIndex("accidents") + 1;
                String str_aid = Integer.toString(aid);
                System.out.println(str_aid);

                PreparedStatement stmt = conn.prepareStatement("INSERT INTO involvements ('aid', 'vin', 'damages', 'driver_ssn') VALUES (?, ?,?,?)");
                stmt.setString(1, str_aid);
                stmt.setString(2, vin);
                stmt.setFloat(3, damages);
                stmt.setString(4, driver_ssn);

                int affectedRows = stmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void findAccidentbyID(int aid){

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Searching...");




                PreparedStatement stmt = conn.prepareStatement("SELECT driver_ssn, accident_date, city, state, vin, damages\n" +
                        "FROM accidents, involvements\n" +
                        "WHERE accidents.aid = involvements.aid and involvements.aid = ?; ");
                stmt.setInt(1, aid);

                ResultSet rs = stmt.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print(",  ");
                        String columnValue = rs.getString(i);
                        System.out.print(columnValue + " " + rsmd.getColumnName(i));
                    }
                    System.out.println("");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }



    public static void findAccidentbyCriteria(String sdate, String edate, float minAvgDam, float maxAvgDam, float minTotDam, float maxTotDam){

        System.out.println("Building query...");
        System.out.println("");
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {


                PreparedStatement pstmt = conn.prepareStatement("SELECT * \n" +
                        "FROM (SELECT *\n" +
                        "\t  FROM (SELECT *, avg(damages) AS avgs, sum(damages) as totals\n" +
                        "\t\t\tFROM (SELECT accidents.aid, accident_date, city, state, damages\n" +
                        "\t\t\t\t  FROM accidents, involvements\n" +
                        "\t\t\t\t  WHERE (accidents.aid = involvements.aid) AND (accident_date BETWEEN ? AND ?))\n" +
                        "\t\t\tGROUP BY aid)\n" +
                        "\tWHERE (avgs >= ?) AND (avgs <= ?))\n" +
                        "WHERE (totals >= ?) AND (totals <= ?);");
                pstmt.setString(1, sdate);
                System.out.println("sdate");
                pstmt.setString(2, edate);
                pstmt.setFloat(3, minAvgDam);
                pstmt.setFloat(4, maxAvgDam);
                pstmt.setFloat(5, minTotDam);
                pstmt.setFloat(6, maxTotDam);

                ResultSet rs = pstmt.executeQuery();

                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print(",  ");
                        String columnValue = rs.getString(i);
                        System.out.print(columnValue + " " + rsmd.getColumnName(i));
                    }
                    System.out.println("");

                }

                System.out.println("Success!");

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }




    }
}
