package ru.itis.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.models.Nutrition;
import ru.itis.models.Product;

import javax.sql.DataSource;
import java.util.List;

public class NutritionsRepositoryImpl implements CrudRepository<Nutrition> {

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

    public NutritionsRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Nutrition> nutritionRowMapper = (resultSet, i) -> Nutrition.builder()
            .product(Product.builder()
                    .id(resultSet.getLong("product_id"))
                    .name(resultSet.getString("name"))
                    .kal(resultSet.getFloat("kal"))
                    .fat(resultSet.getFloat("fat"))
                    .prot(resultSet.getFloat("prot"))
                    .carbon(resultSet.getFloat("carbon"))
                    .water(resultSet.getFloat("water"))
                    .build())
            .amount(resultSet.getFloat("amount"))
            .date(resultSet.getDate("date").toLocalDate())
            .build();

    @Override
    public List<Nutrition> findAll(Long id) {
        try {
            return jdbcTemplate.query(SQL_SELECT_NUTRITIONS_BY_USER_ID, nutritionRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public Nutrition find(Long id) {
        return null;
    }

    @Override
    public void save(Nutrition model) {
        jdbcTemplate.update(SQL_INSERT_NUTRITION, model.getProduct().getId(), model.getAmount(), model.getDate(), model.getUser_id());
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Nutrition model) {

    }
}
