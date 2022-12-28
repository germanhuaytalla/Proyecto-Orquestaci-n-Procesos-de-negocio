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
        this.url = "jdbc:mariadb://" + props.getProperty("MARIADB_HOST") + ":15501/" + props.getProperty("MARIADB_DB");
        this.user = props.getProperty("MARIADB_USER");
        this.password = props.getProperty("MARIADB_PASSWORD");
    }

    public void insert(Factura factura) {
        String sql = "INSERT INTO facturas (codigo_cliente, nombre_cliente, ruc_cliente, lista_items, total_igv, total_factura) VALUES (?,?,?,?,?,?)";
        
        try ( Connection con = (Connection) DriverManager.getConnection(url, user, password);  PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, factura.getCodigo_cliente());
            stm.setString(2, factura.getNombre_cliente());
            stm.setString(3, factura.getRuc_de_cliente());
            stm.setString(4, new Gson().toJson(factura.getLista_items()));
            stm.setDouble(5, factura.getTotal_igv());
            stm.setDouble(6, factura.getTotal_factura());
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
                obj.setNumero_factura(rs.getInt("numero_factura"));
                obj.setCodigo_cliente(rs.getString("codigo_cliente"));
                obj.setNombre_cliente(rs.getString("nombre_cliente"));
                obj.setRuc_de_cliente(rs.getString("ruc_cliente"));
                obj.setLista_items(new Gson().fromJson(rs.getString("lista_items"), type));
                System.out.println(obj);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
