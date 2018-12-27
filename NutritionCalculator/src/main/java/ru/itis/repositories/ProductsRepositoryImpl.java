package ru.itis.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.models.Product;

import javax.sql.DataSource;
import java.util.List;

public class ProductsRepositoryImpl implements CrudRepository<Product> {

    private JdbcTemplate jdbcTemplate;

    //language=SQL
    private static final String SQL_UPDATE_NUTRITION =
            "update nutrition SET kals = ?, fat = ?, prot = ?, carbon = ?, water = ?" +
                    "where user_id=? ";

    //language=SQL
    private static final String SQL_SELECT_ALL_PRODUCTS =
            "select * from product";

    //language=SQL
    private static final String SQL_SELECT_PRODUCT_BY_ID =
            "select * from product where id = ?";

    //language=SQL
    private static final String SQL_INSERT_PRODUCT =
            "insert into product(name, kal, fat, prot, carbon, water) values (?, ?, ?, ?, ?, ?)";

    public ProductsRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Product> productRowMapper = (resultSet, i) -> Product.builder()
            .id(resultSet.getLong("id"))
            .name(resultSet.getString("name"))
            .kal(resultSet.getFloat("kal"))
            .fat(resultSet.getFloat("fat"))
            .prot(resultSet.getFloat("prot"))
            .carbon(resultSet.getFloat("carbon"))
            .water(resultSet.getFloat("water"))
            .build();


    @Override
    public List<Product> findAll(Long id) {
        try {
            return jdbcTemplate.query(SQL_SELECT_ALL_PRODUCTS, productRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Product find(Long id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_PRODUCT_BY_ID, productRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void save(Product model) {
        jdbcTemplate.update(SQL_INSERT_PRODUCT,
                model.getName(),
                model.getKal(),
                model.getFat(),
                model.getProt(),
                model.getCarbon(),
                model.getWater());
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Product model) {

    }
}
