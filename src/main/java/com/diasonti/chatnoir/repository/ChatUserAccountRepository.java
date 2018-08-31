package com.diasonti.chatnoir.repository;

import com.diasonti.chatnoir.entity.ChatUserAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ChatUserAccountRepository {

    private final DataSource dataSource;

    public Optional<ChatUserAccount> find(long id) {
        ChatUserAccount entity = null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM chat_user WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            entity = getUniqueResult(resultSet);
        } catch (SQLException e) {
            log.error("ChatUserAccountRepository: find error", e);
        }
        return Optional.ofNullable(entity);
    }

    public List<ChatUserAccount> findAll() {
        List<ChatUserAccount> list = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM chat_user ORDER BY id DESC");
            ResultSet resultSet = statement.executeQuery();
            list.addAll(getResultList(resultSet));
        } catch (SQLException e) {
            log.error("ChatUserAccountRepository: find error", e);
        }
        return list;
    }

    public Optional<ChatUserAccount> findByLogin(String login) {
        ChatUserAccount entity = null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM chat_user WHERE login = ?");
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            entity = getUniqueResult(resultSet);
        } catch (SQLException e) {
            log.error("ChatUserAccountRepository: find error", e);
        }
        return Optional.ofNullable(entity);
    }

    public void save(ChatUserAccount entity) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = null;
            if(entity.getId() == null) {
                statement = connection.prepareStatement("INSERT INTO chat_user (login, password, name, role) VALUES (?, ?, ?, ?)");
            } else {
                statement = connection.prepareStatement("UPDATE chat_user SET login = ?, password = ?, name = ?, role = ? WHERE id = ?");
                statement.setLong(4, entity.getId());
            }
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getPassword());
            statement.setString(3, entity.getName());
            statement.setString(4, entity.getRole());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("ChatUserAccountRepository: save error", e);
        }
    }

    public void remove(ChatUserAccount entity) {
        if(entity.getId() == null) {
            log.error("ChatUserAccountRepository: remove error: entity has no id, entity: {}", entity);
            return;
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = null;
            statement = connection.prepareStatement("DELETE FROM chat_user WHERE id = ?");
            statement.setLong(1, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("ChatUserAccountRepository: remove error", e);
        }
    }

    public boolean exists(String login) {
        long count = 0L;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT count(*) FROM chat_user WHERE login = ?");
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                count = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            log.error("ChatUserAccountRepository: find error", e);
        }
        return count > 0L;
    }

    public long count() {
        long count = -1L;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT count(*) FROM chat_user");
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                count = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            log.error("ChatUserAccountRepository: find error", e);
        }
        return count;
    }

    private ChatUserAccount getUniqueResult(ResultSet resultSet) throws SQLException {
        ChatUserAccount entity = null;
        if(resultSet.next()) {
            entity = new ChatUserAccount();
            entity.setId(resultSet.getLong("id"));
            entity.setName(resultSet.getString("name"));
            entity.setLogin(resultSet.getString("login"));
            entity.setPassword(resultSet.getString("password"));
            entity.setRole(resultSet.getString("role"));
        }
        return entity;
    }

    private List<ChatUserAccount> getResultList(ResultSet resultSet) throws SQLException {
        List<ChatUserAccount> list = new ArrayList<>();
        while (resultSet.next()) {
            ChatUserAccount entity = new ChatUserAccount();
            entity.setId(resultSet.getLong("id"));
            entity.setName(resultSet.getString("name"));
            entity.setLogin(resultSet.getString("login"));
            entity.setPassword(resultSet.getString("password"));
            entity.setRole(resultSet.getString("role"));
            list.add(entity);
        }
        return list;
    }

    public String getName(long senderId) {
        String name = null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT name FROM chat_user WHERE id = ?");
            statement.setLong(1, senderId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
                name = resultSet.getString("name");
        } catch (SQLException e) {
            log.error("ChatUserAccountRepository: find error", e);
        }
        return name;
    }
}
