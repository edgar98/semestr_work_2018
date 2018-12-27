package ru.itis.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.models.Product;
import ru.itis.models.Stat;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class StatsRepository implements CrudRepository<Stat> {

    private JdbcTemplate jdbcTemplate;

    //language=SQL
    private static final String SQL_UPDATE_STAT =
            "update user_stat SET total_kal = ?, total_fat = ?, total_prot = ?, total_carbon = ?, total_water = ?" +
                    "where id=? ";

    //language=SQL
    private static final String SQL_SELECT_ALL_STATS =
            "select * from user_stat where user_id = ?";

    //language=SQL
    private static final String SQL_SELECT_STAT_BY_USER_ID =
            "select * from user_stat where user_id = ? and user_stat.date >= now()::date";

    //language=SQL
    private static final String SQL_INSERT_STAT =
            "insert into user_stat(user_id, total_kal, total_fat, total_prot, total_carbon, total_water, date) values (?, ?, ?, ?, ?, ?, ?)";

    public StatsRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Stat> statRowMapper = (resultSet, i) -> Stat.builder()
            .id(resultSet.getLong("id"))
            .user_id(resultSet.getLong("user_id"))
            .kal(resultSet.getFloat("total_kal"))
            .fat(resultSet.getFloat("total_fat"))
            .prot(resultSet.getFloat("total_prot"))
            .carbon(resultSet.getFloat("total_carbon"))
            .water(resultSet.getFloat("total_water"))
            .date(resultSet.getDate("date").toLocalDate())
            .build();

    @Override
    public List<Stat> findAll(Long id) {
        try {
            return jdbcTemplate.query(SQL_SELECT_ALL_STATS, statRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Stat find(Long id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_STAT_BY_USER_ID, statRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void save(Stat model) {
        jdbcTemplate.update(SQL_INSERT_STAT, model.getUser_id(), model.getKal(), model.getFat(), model.getProt(), model.getCarbon(), model.getWater(),
                model.getDate());
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Stat model) {
        Stat stat = find(model.getUser_id());
        if (stat != null) {
            stat.setKal(stat.getKal() + model.getKal());
            stat.setFat(stat.getFat() + model.getFat());
            stat.setProt(stat.getProt() + model.getProt());
            stat.setCarbon(stat.getCarbon() + model.getCarbon());
            stat.setWater(stat.getWater() + model.getWater());
            jdbcTemplate.update(SQL_UPDATE_STAT, stat.getKal(), stat.getFat(), stat.getProt(), stat.getCarbon(),
                    stat.getWater(), stat.getId());
        } else {
            save(model);
        }
    }
}
