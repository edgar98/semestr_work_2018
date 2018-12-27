package ru.itis.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.models.Nutrition;
import ru.itis.models.Need;
import ru.itis.models.Account;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


public class BasketRepositoryImpl implements BasketRepository, CrudRepository<Nutrition> {

    private JdbcTemplate jdbcTemplate;

    //language=SQL
    private static final String SQL_INSERT_INTO_BASKET_PRODUCT =
            "insert into basket_order(basket_id ,order_id) values(?,?)";

    //language=SQL
    private static final String SQL_SELECT_ID =
            "SELECT id,title,price,sum(price) FROM orders JOIN basket_order bp on orders.id = bp.order_id AND basket_id = ? GROUP BY orders.id;";

    //language=SQL
    private static final String SELECT_BASKET_BY_USER =
            "select * from basket where user_id = ?";

    //language=SQL
    private static final String INSERT_BASKET =
            "insert into basket (user_id) values (?)";


    public BasketRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    RowMapper<Need> productCountRowMapper = ((resultSet, i) ->Need.builder()
            .build());


    @Override
    public List<Nutrition> findAll(Long id) {
        return null;
    }

    @Override
    public Nutrition find(Long id) {
        return null;
    }

    @Override
    public void save(Nutrition model) {
        return;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Nutrition model) {

    }


    @Override
    public void addProduct(Long product, Long basket) {
        jdbcTemplate.update(SQL_INSERT_INTO_BASKET_PRODUCT,basket,product);
    }

    @Override
    public List<Need> getProducts(Long basketId) {
        return jdbcTemplate.query(SQL_SELECT_ID, productCountRowMapper, basketId);
    }

    private RowMapper<Nutrition> basketRowMapper = (resultSet, i) -> Nutrition.builder()
            .id(resultSet.getLong("id"))
            .build();

    //создание корзины для юзера
    @Override
    public Nutrition createCookieBasket(Account user) {
        System.out.println(user.getId());
        jdbcTemplate.update(INSERT_BASKET, user.getId());
        return getBasket(user);
    }

    //возвращаем объект,сформированный по запросу
    @Override
    public Nutrition getBasket(Account user) {
        return jdbcTemplate.queryForObject(SELECT_BASKET_BY_USER, basketRowMapper, user.getId());
    }
}
