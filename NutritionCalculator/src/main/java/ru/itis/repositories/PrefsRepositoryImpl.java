package ru.itis.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.models.Nutrition;
import ru.itis.models.Product;

import javax.sql.DataSource;
import java.util.List;

public class PrefsRepositoryImpl implements CrudRepository<Product> {

    private JdbcTemplate jdbcTemplate;

    //language=SQL
    private static final String SQL_UPDATE_NUTRITION =
            "update nutrition SET kals = ?, fat = ?, prot = ?, carbon = ?, water = ?" +
                    "where user_id=? ";

    //language=SQL
    private static final String SQL_SELECT_NUTRITIONS_BY_USER_ID =
            "select * from nutrition n join product on n.product_id = product.id where user_id = ?";

    //language=SQL
    private static final String SQL_INSERT_NUTRITION =
            "insert into nutrition(product_id, amount, date, user_id) values (?, ?, ?, ?)";

    public PrefsRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Product> productRowMapper = (resultSet, i) -> Product.builder()
            .id(resultSet.getLong("product_id"))
            .name(resultSet.getString("name"))
            .kal(resultSet.getFloat("kal"))
            .fat(resultSet.getFloat("fat"))
            .prot(resultSet.getFloat("prot"))
            .carbon(resultSet.getFloat("carbon"))
            .water(resultSet.getFloat("water"))
            .build();


    @Override
    public List<Product> findAll(Long id) {
        return null;
    }

    @Override
    public Product find(Long id) {
        return null;
    }

    @Override
    public void save(Product model) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Product model) {

    }
}
