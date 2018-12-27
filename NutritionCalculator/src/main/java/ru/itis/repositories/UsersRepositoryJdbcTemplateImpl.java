package ru.itis.repositories;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.models.Account;
import ru.itis.models.Need;

import javax.sql.DataSource;
import java.util.LinkedList;
import java.util.List;


public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

    private JdbcTemplate jdbcTemplate;

    //language=SQL
    private static final String SQL_SELECT_USER_BY_ID =
            "select * from account where id = ?";

    //language=SQL
    private static final String SQL_SELECT_ALL_USERS =
            "select * from account";

    //language=SQL
    private static final String SQL_INSERT =
            "insert into account(username, password_hash) values (?, ?)";

    //language=SQL
    private static final String SQL_SELECT_BY_NAME =
            "select * from account join user_need un on account.id = un.user_id where account.username = ?;";

    //language=SQL
    private static final String SQL_SELECT_COOKIE =
                    "SELECT * FROM user_need join (SELECT account.* FROM account,auth WHERE auth.user_id = account.id AND cookie_value = ?) as acc on acc.id = user_need.user_id;";

    //language=SQL
    private static final String SQL_INSERT_INF=
            "update account SET first_name=?,last_name=?,weight=?,height=?,age=?, is_woman=?, activity=? " +
                    "where id=? ";

    private RowMapper<Account> userRowMapper = (resultSet, i) -> Account.builder()
            .id(resultSet.getLong("id"))
            .username(resultSet.getString("username"))
            .passwordHash(resultSet.getString("password_hash"))
            .firstname(resultSet.getString("first_name"))
            .lastname(resultSet.getString("last_name"))
            .weight(resultSet.getFloat("weight"))
            .height(resultSet.getInt("height"))
            .age(resultSet.getInt("age"))
            .activity(resultSet.getFloat("activity"))
            .is_woman(resultSet.getBoolean("is_woman"))
            .needs(Need.builder()
                    .kal(resultSet.getFloat("kals"))
                    .fat(resultSet.getFloat("fat"))
                    .prot(resultSet.getFloat("prot"))
                    .carbon(resultSet.getFloat("carbon"))
                    .water(resultSet.getFloat("water"))
                    .build())
            .build();

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Account> findAll(Long id) {
        List<Account> list = new LinkedList<>();
        list.add(find(id));
        return list;
    }

    @Override
    public Account find(Long id) {
        return jdbcTemplate.queryForObject(SQL_SELECT_USER_BY_ID,
                userRowMapper, id);
    }

    @Override
    public void save(Account model) {
        jdbcTemplate.update(SQL_INSERT, model.getUsername(), model.getPasswordHash());
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Account model) {
        jdbcTemplate.update(SQL_INSERT_INF,model.getFirstname(),model.getLastname(),
                model.getWeight(),model.getHeight(),model.getAge(), model.getIs_woman(), model.getActivity(),model.getId());
    }

    @Override
    public Account findByName(String name) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_BY_NAME, userRowMapper, name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Account getUserByUUID(String uuid) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_COOKIE, userRowMapper, uuid);
        } catch (DataAccessException e) {
            return null;

        }
    }

    @Override
    public Account getUser(String cookieValue) { return getUserByUUID(cookieValue); }
}
