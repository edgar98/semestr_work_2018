package ru.itis.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.models.Need;

import javax.sql.DataSource;
import java.util.List;


public class NeedsRepositoryImpl implements CrudRepository<Need> {

    private JdbcTemplate jdbcTemplate;

    //language=SQL
    private static final String SQL_UPDATE_NEED =
            "update user_need SET kals = ?, fat = ?, prot = ?, carbon = ?, water = ?" +
                    "where user_id=? ";

    //language=SQL
    private static final String SQL_SELECT_NEEDS_BY_USER_ID =
            "select * from user_need where user_id = ?";

    //language=SQL
    private static final String SQL_INSERT_NEED =
            "insert into user_need(user_id, kals, fat, prot, carbon, water) values (?, ?, ?, ?, ?, ?)";

    public NeedsRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Need> needRowMapper = (resultSet, i) -> Need.builder()
            .user_id(resultSet.getLong("user_id"))
            .carbon(resultSet.getFloat("carbon"))
            .fat(resultSet.getFloat("fat"))
            .kal(resultSet.getFloat("kals"))
            .prot(resultSet.getFloat("prot"))
            .water(resultSet.getFloat("water"))
            .build();

    public void update(Need model) {
        if (find(model.getUser_id()) != null) {
            jdbcTemplate.update(SQL_UPDATE_NEED,
                    model.getKal(),
                    model.getFat(),
                    model.getProt(),
                    model.getCarbon(),
                    model.getWater(),
                    model.getUser_id()
            );
        } else {
            save(model);
        }
    }

    public void save(Need model) {
        jdbcTemplate.update(SQL_INSERT_NEED,
                model.getUser_id(),
                model.getKal(),
                model.getFat(),
                model.getProt(),
                model.getCarbon(),
                model.getWater()
        );
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Need> findAll(Long id) {
        return null;
    }

    public Need find(Long id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_NEEDS_BY_USER_ID,
                    needRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
