package com.diasonti.chatnoir.repository;

import com.diasonti.chatnoir.entity.ChatMessage;
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
public class ChatMessageRepository {

    private final DataSource dataSource;

    public Optional<ChatMessage> find(long id) {
        ChatMessage entity = null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM chat_message WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            entity = getUniqueResult(resultSet);
        } catch (SQLException e) {
            log.error("ChatMessageRepository: find error", e);
        }
        return Optional.ofNullable(entity);
    }

    public List<ChatMessage> findAll() {
        List<ChatMessage> list = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM chat_message ORDER BY id ASC");
            ResultSet resultSet = statement.executeQuery();
            list.addAll(getResultList(resultSet));
        } catch (SQLException e) {
            log.error("ChatMessageRepository: find error", e);
        }
        return list;
    }

    public void save(ChatMessage entity) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = null;
            if(entity.getId() == null) {
                statement = connection.prepareStatement("INSERT INTO chat_message (sent_at, sender_id, text) VALUES (?, ?, ?)");
            } else {
                statement = connection.prepareStatement("UPDATE chat_message SET sent_at = ?, sender_id = ?, text = ? WHERE id = ?");
                statement.setLong(4, entity.getId());
            }
            statement.setTimestamp(1, Timestamp.valueOf(entity.getSentAt()));
            statement.setLong(2, entity.getSenderId());
            statement.setString(3, entity.getText());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("ChatMessageRepository: save error", e);
        }
    }

    public void remove(ChatMessage entity) {
        if(entity.getId() == null) {
            log.error("ChatMessageRepository: remove error: entity has no id, entity: {}", entity);
            return;
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = null;
            statement = connection.prepareStatement("DELETE FROM chat_message WHERE id = ?");
            statement.setLong(1, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("ChatMessageRepository: remove error", e);
        }
    }

    public long count() {
        long count = -1L;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT count(*) FROM chat_message");
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                count = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            log.error("ChatMessageRepository: find error", e);
        }
        return count;
    }

    public Optional<ChatMessage> findOldest() {
        ChatMessage entity = null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM chat_message ORDER BY id ASC LIMIT 1");
            ResultSet resultSet = statement.executeQuery();
            entity = getUniqueResult(resultSet);
        } catch (SQLException e) {
            log.error("ChatMessageRepository: findOldest error", e);
        }
        return Optional.ofNullable(entity);
    }

    private ChatMessage getUniqueResult(ResultSet resultSet) throws SQLException {
        ChatMessage entity = null;
        if(resultSet.next()) {
            entity = new ChatMessage();
            entity.setId(resultSet.getLong("id"));
            entity.setSentAt(resultSet.getTimestamp("sent_at").toLocalDateTime());
            entity.setSenderId(resultSet.getLong("sender_id"));
            entity.setText(resultSet.getString("text"));
        }
        return entity;
    }

    private List<ChatMessage> getResultList(ResultSet resultSet) throws SQLException {
        List<ChatMessage> list = new ArrayList<>();
        while (resultSet.next()) {
            ChatMessage entity = new ChatMessage();
            entity.setId(resultSet.getLong("id"));
            entity.setSentAt(resultSet.getTimestamp("sent_at").toLocalDateTime());
            entity.setSenderId(resultSet.getLong("sender_id"));
            entity.setText(resultSet.getString("text"));
            list.add(entity);
        }
        return list;
    }

}
