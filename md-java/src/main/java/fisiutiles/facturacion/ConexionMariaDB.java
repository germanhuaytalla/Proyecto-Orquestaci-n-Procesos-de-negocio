package fisiutiles.facturacion;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import org.mariadb.jdbc.Connection;
import org.mariadb.jdbc.Statement;

public class ConexionMariaDB {

    private final Properties props;
    private final String url;
    private final String user;
    private final String password;

    public ConexionMariaDB() {
        this.props = new PropertiesReader().getProperties();
        this.url = "jdbc:mariadb://" + props.getProperty("MYSQL_HOST") + ":3306/" + props.getProperty("MYSQL_DB");
        this.user = props.getProperty("MYSQL_USER");
        this.password = props.getProperty("MYSQL_PASSWORD");
    }

    public void insert(Factura factura) {
        try ( Connection con = (Connection) DriverManager.getConnection(url, user, password);  PreparedStatement stm = con.prepareStatement("INSERT INTO facturas VALUE(?,?,?,?,?,?,?)")) {
            stm.setInt(1, factura.getNumeroDeFactura());
            stm.setString(2, factura.getCodigoDeCliente());
            stm.setString(3, factura.getNombreDeCliente());
            stm.setString(4, factura.getRucDeCliente());
            stm.setString(5, new Gson().toJson(factura.getItems()));
            stm.setDouble(6, factura.getTotalIGV());
            stm.setDouble(7, factura.getTotalFactura());
            stm.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void select() {
        try ( Connection con = (Connection) DriverManager.getConnection(url, user, password);  Statement stm = (Statement) con.createStatement()) {
            ResultSet rs = stm.executeQuery("SELECT * FROM facturas");
            Type type = new TypeToken<ArrayList<Item>>() {}.getType();

            while (rs.next()) {
                Factura obj = new Factura();
                obj.setNumeroDeFactura(rs.getInt("numero_factura"));
                obj.setCodigoDeCliente(rs.getString("codigo_cliente"));
                obj.setNombreDeCliente(rs.getString("nombre_cliente"));
                obj.setRucDeCliente(rs.getString("ruc_cliente"));
                obj.setItems(new Gson().fromJson(rs.getString("lista_items"), type));
                obj.setTotalIGV(rs.getDouble("total_igv"));
                obj.setTotalFactura(rs.getDouble("total_factura"));
                System.out.println(obj);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        var bbdd = new ConexionMariaDB();

        Item a = new Item("desc 1", 5, 41.2);
        Item b = new Item("desc 2", 4, 13.7);
        Item c = new Item("desc 3", 3, 8.5);

        ArrayList<Item> items = new ArrayList<>();
        items.add(a);
        items.add(b);
        items.add(c);

        // Factura fac = new Factura(11, "19200038", "juan", "ruc495", items, 153.4, 15.6);
        Factura fac = new Factura(22, "14266484", "paco", "ruc511", items, 133.4, 25.6);

        // bbdd.insert(fac);
        System.out.println(fac);
        bbdd.select();
    }
}
