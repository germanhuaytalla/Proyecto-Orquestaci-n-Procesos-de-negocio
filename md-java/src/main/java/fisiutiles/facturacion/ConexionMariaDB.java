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
        this.url = "jdbc:mariadb://" + props.getProperty("MARIADB_HOST") + ":3306/" + props.getProperty("MARIADB_DB");
        this.user = props.getProperty("MARIADB_USER");
        this.password = props.getProperty("MARIADB_PASSWORD");
    }

    public void insert(Factura factura) {
        String sql = "INSERT INTO facturas (codigo_cliente, nombre_cliente, ruc_cliente, lista_items, total_igv, total_factura) VALUES (?,?,?,?,?,?)";
        
        try ( Connection con = (Connection) DriverManager.getConnection(url, user, password);  PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, factura.getCodigoDeCliente());
            stm.setString(2, factura.getNombreDeCliente());
            stm.setString(3, factura.getRucDeCliente());
            stm.setString(4, new Gson().toJson(factura.getItems()));
            stm.setDouble(5, factura.getTotalIGV());
            stm.setDouble(6, factura.getTotalFactura());
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
                System.out.println(obj);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
