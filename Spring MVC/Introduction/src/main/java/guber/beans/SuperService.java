package guber.beans;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Alexey_Zinovyev on 12-Jun-17.
 */
public class SuperService {

    public static final String URL = "jdbc:mysql://localhost:3306/guber";
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "pass";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    private static final RowMapper cabRowMapper = (rs, rowNumber) -> new Cab(
            rs.getDate("manufacture_year"),
            rs.getString("car_make"),
            rs.getString("licence_plate"),
            rs.getInt("capacity"),
            rs.getBoolean("has_baby_chair")
    );

    public void setDataSource(DataSource dataSource) {  // <---- Inject DataSource through setter
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.insertTemplate = new SimpleJdbcInsert(dataSource).withTableName("cab");
    }

    public List<Cab> getCabs() {
        return jdbcTemplate.query("SELECT * FROM cab", cabRowMapper);
    }

    public Cab findByYear(int manufactureYear) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("manufactureYear", manufactureYear);
        List<Cab> result = namedParameterJdbcTemplate.query(
                "SELECT * FROM cab WHERE YEAR(manufactureYear) = :manufactureYear", namedParameters,
                cabRowMapper);

        if(result.size() > 0) {
            return result.get(0);
        }

        return new Cab();
    }
}
